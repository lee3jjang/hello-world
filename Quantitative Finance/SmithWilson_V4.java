package esgcore;

import java.util.stream.DoubleStream;

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
	public RealVector term;
	public RealVector rate;
	public double alpha;
	public double ufr;
	public double llp;
	public int n;
	public RealVector zeta;
	
	public SmithWilson(RealVector term, RealVector rate, double alpha, double ufr, double llp) {
		this.term = term;
		this.rate = rate;
		this.ufr = ufr;
		this.llp = llp;
		this.n = rate.getDimension();
		setAlpha(alpha);
	}

	public SmithWilson(double[] term, double[] rate, double alpha, double ufr, double llp) {
		this.ufr = ufr;
		this.llp = llp;
		this.term = new ArrayRealVector(term);
		this.rate = new ArrayRealVector(rate);
		this.n = rate.length;
		setAlpha(calcAlpha());
	}
	
	private double Wilson(double t, double u, int order) {
		double wilson;
		if (order==0) {
			wilson = Math.exp(-this.ufr*(t+u)) * (this.alpha*Math.min(t, u) - Math.exp(-this.alpha*Math.max(t, u))*Math.sinh(this.alpha*Math.min(t, u)));
		} else if (order==1) {
			if (t<u)
				wilson = Math.exp(-this.ufr*t-(this.alpha+this.ufr)*u)*(this.ufr*Math.sinh(this.alpha*t)-this.alpha*Math.cosh(this.alpha*t)-this.alpha*(this.ufr*t-1)*Math.exp(this.alpha*u));
			else
				wilson = Math.exp(-this.ufr*u-(this.alpha+this.ufr)*t)*((this.alpha+this.ufr)*Math.sinh(this.alpha*u)-this.alpha*this.ufr*u*Math.exp(this.alpha*t));		
		} else if (order==2) {
			if (t<u)
				wilson = Math.exp(-this.ufr*t-(this.alpha+this.ufr)*u)*(-(this.alpha*this.alpha+this.ufr*this.ufr)*Math.sinh(this.alpha*t)+2*this.alpha*this.ufr*Math.cosh(this.alpha*t)+this.alpha*this.ufr*(this.ufr*t-2)*Math.exp(this.alpha*u));
			else
				wilson = Math.exp(-this.ufr*u-(this.alpha+this.ufr)*t)*(this.alpha*this.ufr*this.ufr*u*Math.exp(this.alpha*t)-(this.alpha+this.ufr)*(this.alpha+this.ufr)*Math.sinh(this.alpha*u));
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
		double[] r = DoubleStream.of(t.toArray())
				.map(s->spot(s))
				.toArray();
		return new ArrayRealVector(r);
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
	
	public double getError(double alpha) {
		SmithWilson sw = new SmithWilson(term, rate, alpha, ufr, llp);
		return Math.abs(0.0001 - Math.abs(sw.forward(Math.max(llp+40, 60), 0)-ufr));
	}
	
	public void setAlpha(double alpha) {
		this.alpha = alpha;
		calcZeta();
	}
	
	public double calcAlpha() {
		UnivariateOptimizer optimizer = new BrentOptimizer(1e-4, 1e-6);
		UnivariatePointValuePair result = optimizer.optimize(
				new MaxEval(20),
				new UnivariateObjectiveFunction(a -> getError(a)),
				GoalType.MINIMIZE,
				new SearchInterval(0.01, 1)
			);
		return result.getPoint();
	}
}
