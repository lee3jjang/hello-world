package esgcore.termstructure;

import esgcore.distribution.NormalDistribution;
import esgcore.linalg.Vector;

public abstract class TermStructure {
	
	public abstract double bond(double t, int order);

	// Bond Price
	public Vector bond(Vector t, int order) {
		return t.map(x -> this.bond(x, order));
	}
	
	public double bond(double t) {
		return bond(t, 0);
	}
	
	public Vector bond(Vector t) {
		return bond(t, 0);
	}
	
	// Spot Rate
	public double spot(double t) {
		double u = Math.max(t, 1e-6);
		return Math.pow(1/bond(u), 1/u)-1;
	}
	
	public Vector spot(Vector t) {
		return t.map(x -> this.spot(x));
	}
	
	// Forward Rate at t	(Continuously Compounded)
	public double forward(double t, int order) {
		if(order==0)
			return -bond(t, 1)/bond(t);
		else if(order==1)
			return 1/bond(t)*(bond(t, 1)*bond(t, 1)/bond(t)+bond(t, 2));
		else
			throw new RuntimeException("유효하지 않은 차수입니다.");
	}

	public Vector forward(Vector t, int order) {
		return t.map(x -> this.forward(x, order));
	}
	
	public double forward(double t) {
		return forward(t, 0);
	}
	
	public Vector forward(Vector t) {
		return forward(t, 0);
	}
	
	// Forward Rate between t1 and t2 (Annually Compounded)
	public double forwardBtw(double t1, double t2) {
		if(t1 > t2) {
			throw new RuntimeException("조건 t1 <= t2 위반입니다.");
		} else if (t1 == t2) {
			return Math.exp(forward(t1))-1;
		} else {
			return Math.pow(Math.pow(1+this.spot(t2), t2)/Math.pow(1+this.spot(t1), t1), 1/(t2-t1))-1;
		}
	}
	
	// 1M-Forward Rate (Annually Compounded)
	public double forward1M(double t) {
		return forwardBtw(t, t+1./12.);
	}
	
	public Vector forward1M(Vector t) {
		return t.map(x -> this.forward1M(x));
	}
	
	// Forward Swap Rate
	public double fswap(double start, double tenor, double tau) {
		int l = (int)(tenor/tau);
		double term1 = (bond(start) - bond(start+tenor))/tau;
		double term2 = .0;
		for(int i=1; i<=l; i++)
			term2 += bond(start+tau*i);
		return term1/term2;
	}
	
	public double fswap(double start, double tenor) {
		return fswap(start, tenor, 0.25);
	}
	
	// Forward Swap Terms
	public Vector fswapTerms(double start, double tenor, double tau) {
		int l = (int)(tenor/tau);
		Vector t = Vector.createZeroVector(l);
		for(int i=0; i<l; i++) 
			t.setEntry(i, tau*(i+1)+start);
		return t;
	}
	
	public Vector fswapTerms(double start, double tenor) {
		return fswapTerms(start, tenor, 0.25);
	}
	
	// Forward Swap Cash Flow
	public Vector fswapCashFlow(double start, double tenor, double tau) {
		int l = (int)(tenor/tau);
		Vector cf = Vector.createZeroVector(l);
		cf.set(fswap(start, tenor)*tau);
		cf.addToEntry(l-1, 1);
		return cf;
	}
	
	public Vector fswapCashFlow(double start, double tenor) {
		return fswapCashFlow(start, tenor, 0.25);
	}
	
	// Black Receiver Swaption Price
	public double rsBlack(double maturity, double tenor, double blackVol) {
		double term1 = bond(maturity) - bond(maturity+tenor);
		double d1 = 0.5*blackVol*Math.sqrt(maturity);
		double cumProb = (new NormalDistribution()).cumulativeProbability(d1); 
		return term1*(2*cumProb-1);
	}
	
}
