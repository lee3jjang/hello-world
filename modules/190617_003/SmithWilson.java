package esg;

import java.util.stream.DoubleStream;

public class SmithWilson {
	public final double tau = 0.25;
	
	public Vector term;
	public Vector rate;
	public double alpha;
	public double ufr;
	public double llp;
	public int n;
	
	public Vector zeta;
	public Vector maturities;
	public Vector spotRates;
	public Vector bondPrices;
	public Vector forwardRates;
	
	public Vector swaptionMaturity;
	public Vector swapTenor;
	public Matrix blackVol;
	public Matrix blackRS;
	public int m1, m2;
	
	public SmithWilson(double[] term, double[] rate, double alpha, double ufr, double llp) {
		this.ufr = Math.log(1+ufr);
		this.llp = llp;
		this.term = new Vector(term);
		this.rate = new Vector(rate);
		this.n = rate.length;
		setAlpha(alpha);
		setRates(100);
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
	
	private Matrix Wilson(Vector rows, Vector columns, int order) {
		Matrix W = Matrix.createZeroMatrix(rows.getDimension(), columns.getDimension());
		for(int i=0; i<rows.getDimension(); i++)
			for(int j=0; j<columns.getDimension(); j++)
				W.setEntry(i, j, Wilson(rows.getEntry(i), rows.getEntry(j), order));
		return W;
	}
	
	private Vector Wilson(double t, Vector columns, int order) {
		Vector W = Vector.createZeroVector(columns.getDimension());
		for(int i=0; i<columns.getDimension(); i++)
			W.setEntry(i, Wilson(t, columns.getEntry(i), order));
		return W;
	}
	
	private void calcZeta() {
		Matrix W = Matrix.createZeroMatrix(n);
		for (int i=0; i<n; i++)
			for (int j=0; j<n; j++)
				W.setEntry(i, j, Wilson(term.getEntry(i), term.getEntry(j), 0));
		Matrix InvW = W.inverse();
		Vector m_mu = Vector.createZeroVector(n);
		for (int i=0; i<n; i++)
			m_mu.setEntry(i, Math.pow(1/(1+rate.getEntry(i)), term.getEntry(i)) - Math.exp(-ufr*term.getEntry(i)));
		this.zeta =  InvW.operate(m_mu);
	}
	
	public Vector bond(Vector t, int order) {
		Matrix W = Wilson(t, term, order);
		Vector term1 = t.map(s -> Math.exp(-ufr*s)).scalarMultiply(Math.pow(-ufr, order));
		Vector term2 = W.operate(zeta);
		return term1.add(term2);
	}
	
	public Vector bond(double[] t, int order) {
		return bond(new Vector(t), order);
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
	
	public Vector spot(Vector t) {
		return t.map(x -> this.spot(x));
	}
	
	public Vector spot(double[] t) {
		return spot(new Vector(t));
	}
	
	public double forward(double t, int order) {
		if(order==0)
			return -bond(t,1)/bond(t,0);
		else if(order==1)
			return 1/bond(t,0)*(bond(t,1)*bond(t,1)/bond(t,0)+bond(t,2));
		else
			throw new RuntimeException("유효하지 않은 차수입니다.");
	}
	
	public Vector forward(Vector t, int order) {
		return t.map(x -> this.forward(x, order));
	}
	
	public Vector forward(double[]  t, int order) {
		return forward(new Vector(t), order);
	}
	
	private double getError(double alpha) {
		SmithWilson sw = new SmithWilson(term.getData(), rate.getData(), alpha, ufr, llp);
		return Math.abs(0.0001 - Math.abs(sw.forward(Math.max(llp+40, 60), 0)-ufr));
	}
	
	public void setAlpha(double alpha) {
		this.alpha = alpha;
		calcZeta();
	}
	
	public void calcAlpha() {
		GoldenSectionSearch optimizer = new GoldenSectionSearch();
		UnivariateFunction fn = x->this.getError(x);
		double result = optimizer.optimize(fn, 0.01, 1);
		this.setAlpha(result);
	}
	
	public double fswap(double start, double tenor) {
		int l = (int)(tenor/tau);
		double term1 = (bond(start, 0) - bond(start+tenor, 0))/tau;
		double term2 = .0;
		for(int i=1; i<=l; i++)
			term2 += bond(start+tau*i, 0);
		return term1/term2;
	}
	
	public Vector fswapCashFlow(double start, double tenor) {
		int l = (int)(tenor/tau);
		Vector cf = Vector.createZeroVector(l);
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
	
	private void setRates(double max) {
		int l = (int)(max/tau)+1;
		maturities = new Vector(DoubleStream.iterate(0, i->i+tau).limit(l).toArray());
		spotRates = spot(maturities);
		bondPrices = bond(maturities, 0);
		forwardRates = forward(maturities, 0).map(s -> Math.exp(s)-1);
	}
	
	public void setBlackVol(double[] swapTenor, double[] swaptionMaturity, double[][] blackVol) {
		this.swaptionMaturity = new Vector(swaptionMaturity);
		this.m1 = swaptionMaturity.length;
		this.swapTenor = new Vector(swapTenor);
		this.m2 = swapTenor.length;
		this.blackVol = new Matrix(blackVol);
		calcBlackRS();
	}
	
	public void setBlackVol(double[] tenor, double[][] blackVol) {
		setBlackVol(tenor, tenor, blackVol);
	}
	
	private void calcBlackRS() {
		blackRS = Matrix.createZeroMatrix(m1, m2);
		for(int i=0; i<m1; i++)
			for(int j=0; j<m2; j++)
				blackRS.setEntry(i, j, rsBlack(swaptionMaturity.getEntry(i), swapTenor.getEntry(j), blackVol.getEntry(i, j)));
	}
	
}