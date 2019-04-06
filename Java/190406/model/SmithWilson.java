package model;

import java.util.stream.DoubleStream;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.optim.MaxEval;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.univariate.BrentOptimizer;
import org.apache.commons.math3.optim.univariate.SearchInterval;
import org.apache.commons.math3.optim.univariate.UnivariateObjectiveFunction;
import org.apache.commons.math3.optim.univariate.UnivariateOptimizer;
import org.apache.commons.math3.optim.univariate.UnivariatePointValuePair;

public class SmithWilson {
	private final double tau = 0.25;
	
	private RealVector term;
	private RealVector rate;
	private double alpha;
	private double ufr;
	private double llp;
	private int n;
	
	private RealVector zeta;
	private RealVector maturities;
	private RealVector spotRates;
	private RealVector bondPrices;
	private RealVector forwardRates;
	
	private RealVector swaptionMaturity;
	private RealVector swapTenor;
	private RealMatrix blackVol;
	private RealMatrix blackRS;
	private int m1, m2;
	
	public SmithWilson(double[] term, double[] rate, double alpha, double ufr, double llp) {
		this.ufr = Math.log(1+ufr);
		this.llp = llp;
		this.term = new ArrayRealVector(term);
		this.rate = new ArrayRealVector(rate);
		this.n = rate.length;
		setAlpha(calcAlpha());
		genRates(100);
	}
	
	public SmithWilson(RealVector term, RealVector rate, double alpha, double ufr, double llp) {
		this.term = term;
		this.rate = rate;
		this.ufr = ufr;
		this.llp = llp;
		this.n = rate.getDimension();
		setAlpha(alpha);
	}

	private double Wilson(double t, double u, int order) {
		double wilson;
		if (order==0) {
			wilson = Math.exp(-ufr*(t+u)) * (alpha*Math.min(t, u) - Math.exp(-alpha*Math.max(t, u))*Math.sinh(alpha*Math.min(t, u)));
		} else if (order==1) {
			if (t<u)
				wilson = Math.exp(-ufr*t-(alpha+ufr)*u)*(ufr*Math.sinh(alpha*t)-alpha*Math.cosh(alpha*t)-alpha*(ufr*t-1)*Math.exp(alpha*u));
			else
				wilson = Math.exp(-ufr*u-(alpha+ufr)*t)*((alpha+ufr)*Math.sinh(alpha*u)-alpha*ufr*u*Math.exp(alpha*t));		
		} else if (order==2) {
			if (t<u)
				wilson = Math.exp(-ufr*t-(alpha+ufr)*u)*(-(alpha*alpha+ufr*ufr)*Math.sinh(alpha*t)+2*alpha*ufr*Math.cosh(alpha*t)+alpha*ufr*(ufr*t-2)*Math.exp(alpha*u));
			else
				wilson = Math.exp(-ufr*u-(alpha+ufr)*t)*(alpha*ufr*ufr*u*Math.exp(alpha*t)-(alpha+ufr)*(alpha+ufr)*Math.sinh(alpha*u));
		} else {
			throw new RuntimeException("유효하지 않은 차수입니다.");
		}
		return wilson;
	}
	
	private RealMatrix Wilson(RealVector rows, RealVector columns, int order) {
		RealMatrix W = MatrixUtils.createRealMatrix(rows.getDimension(), columns.getDimension());
		for(int i=0; i<rows.getDimension(); i++)
			for(int j=0; j<columns.getDimension(); j++)
				W.setEntry(i, j, Wilson(rows.getEntry(i), rows.getEntry(j), order));
		return W;
	}
	
	private RealVector Wilson(double t, RealVector columns, int order) {
		RealVector W = new ArrayRealVector(columns.getDimension());
		for(int i=0; i<columns.getDimension(); i++)
			W.setEntry(i, Wilson(t, columns.getEntry(i), order));
		return W;
	}
	
	private void calcZeta() {
		RealMatrix W = MatrixUtils.createRealMatrix(n, n);
		for (int i=0; i<n; i++)
			for (int j=0; j<n; j++)
				W.setEntry(i, j, Wilson(term.getEntry(i), term.getEntry(j), 0));
		RealMatrix InvW = new LUDecomposition(W).getSolver().getInverse();
		RealVector m_mu = new ArrayRealVector(n);
		for (int i=0; i<n; i++)
			m_mu.setEntry(i, Math.pow(1/(1+rate.getEntry(i)), term.getEntry(i)) - Math.exp(-ufr*term.getEntry(i)));
		zeta =  InvW.transpose().preMultiply(m_mu);
	}
	
	public RealVector bond(RealVector t, int order) {
		RealMatrix W = Wilson(t, term, order);
		RealVector term1 = t.map(s -> Math.exp(-ufr*s)).mapMultiply(Math.pow(-ufr, order));
		RealVector term2 = W.transpose().preMultiply(zeta);
		return term1.add(term2);
	}
	
	public RealVector bond(double[] t, int order) {
		return bond(new ArrayRealVector(t), order);
	}
	
	public double bond(double t, int order) {
		double terms1 = Math.pow(-ufr, order)*Math.exp(-ufr*t);
		double terms2 = Wilson(t, term, order).dotProduct(zeta);
		return terms1+terms2;
	}
	
	public double spot(double t) {
		double u = Math.max(t, 1e-6);
		return Math.pow(1/bond(u, 0), 1/u)-1;
	}
	
	public RealVector spot(RealVector t) {
		return new ArrayRealVector(DoubleStream.of(t.toArray()).map(s->spot(s)).toArray());
	}
	
	public RealVector spot(double[] t) {
		return spot(new ArrayRealVector(t));
	}
	
	public double forward(double t, int order) {
		if(order==0)
			return -bond(t,1)/bond(t,0);
		else if(order==1)
			return 1/bond(t,0)*(bond(t,1)*bond(t,1)/bond(t,0)+bond(t,2));
		else
			throw new RuntimeException("유효하지 않은 차수입니다.");
	}
	
	public RealVector forward(RealVector t, int order) {
		double[] f = DoubleStream.of(t.toArray())
				.map(s->forward(s, order))
				.toArray();
		return new ArrayRealVector(f);
	}
	
	public RealVector forward(double[]  t, int order) {
		return forward(new ArrayRealVector(t), order);
	}
	
	private double getError(double alpha) {
		SmithWilson sw = new SmithWilson(term, rate, alpha, ufr, llp);
		return Math.abs(0.0001 - Math.abs(sw.forward(Math.max(llp+40, 60), 0)-ufr));
	}
	
	private void setAlpha(double alpha) {
		this.alpha = alpha;
		calcZeta();
	}
	
	private double calcAlpha() {
		UnivariateOptimizer optimizer = new BrentOptimizer(1e-4, 1e-6);
		UnivariatePointValuePair result = optimizer.optimize(
				new MaxEval(20),
				new UnivariateObjectiveFunction(a -> getError(a)),
				GoalType.MINIMIZE,
				new SearchInterval(0.01, 1)
			);
		return result.getPoint();
	}
	
	public double fswap(double start, double tenor) {
		int l = (int)(tenor/tau);
		double term1 = (bond(start, 0) - bond(start+tenor, 0))/tau;
		double term2 = DoubleStream.iterate(start+tau, i->i+tau)
				.limit(l)
				.map(t -> bond(t,0))
				.sum();
		return term1/term2;
	}
	
	public RealVector fswapCashFlow(double start, double tenor) {
		int l = (int)(tenor/tau);
		RealVector cf = new ArrayRealVector(l);
		cf.set(fswap(start, tenor)*tau);
		cf.addToEntry(l-1, 1);
		return cf;
	}
	
	public double rsBlack(double maturity, double tenor, double blackVol) {
		double term1 = bond(maturity,0) - bond(maturity+tenor, 0);
		double d1 = 0.5*blackVol*Math.sqrt(maturity);
		double cumProb = (new NormalDistribution()).cumulativeProbability(d1); 
		return term1*(2*cumProb-1);
	}
	
	private void genRates(double max) {
		int l = (int)(max/tau)+1;
		maturities = new ArrayRealVector(DoubleStream.iterate(0, i->i+tau).limit(l).toArray());
		spotRates = spot(maturities);
		bondPrices = bond(maturities, 0);
		forwardRates = forward(maturities, 0).map(s -> Math.exp(s)-1);
	}
	
	public void setBlackVol(double[] swapTenor, double[] swaptionMaturity, double[][] blackVol) {
		this.swaptionMaturity = new ArrayRealVector(swaptionMaturity);
		this.m1 = swaptionMaturity.length;
		this.swapTenor = new ArrayRealVector(swapTenor);
		this.m2 = swapTenor.length;
		this.blackVol = MatrixUtils.createRealMatrix(blackVol);
		calcBlackRS();
	}
	
	private void calcBlackRS() {
		blackRS = MatrixUtils.createRealMatrix(m1, m2);
		for(int i=0; i<m1; i++)
			for(int j=0; j<m2; j++)
				blackRS.setEntry(i, j, rsBlack(swaptionMaturity.getEntry(i), swapTenor.getEntry(j), blackVol.getEntry(i, j)));
	}

	public RealVector getTerm() {
		return term;
	}

	public RealVector getRate() {
		return rate;
	}

	public double getUfr() {
		return ufr;
	}

	public double getLlp() {
		return llp;
	}

	public int getN() {
		return n;
	}

	public RealVector getZeta() {
		return zeta;
	}

	public RealVector getMaturities() {
		return maturities;
	}

	public RealVector getSpotRates() {
		return spotRates;
	}

	public RealVector getBondPrices() {
		return bondPrices;
	}

	public RealVector getForwardRates() {
		return forwardRates;
	}

	public RealVector getSwaptionMaturity() {
		return swaptionMaturity;
	}

	public RealVector getSwapTenor() {
		return swapTenor;
	}

	public RealMatrix getBlackVol() {
		return blackVol;
	}

	public RealMatrix getBlackRS() {
		return blackRS;
	}

	public int getM1() {
		return m1;
	}

	public int getM2() {
		return m2;
	}

	public double getTau() {
		return tau;
	}

	public double getAlpha() {
		return alpha;
	}
	
	
	
}
