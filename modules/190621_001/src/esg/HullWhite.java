package esg;

import java.util.function.Function;

public class HullWhite {
	public double alpha;
	public double sigma;
	public SmithWilson curve;
	public Matrix HullWhiteRS;
	
	public HullWhite(SmithWilson curve) {
		this.curve = curve;
	}

	public HullWhite(double alpha, double sigma, SmithWilson curve) {
		this.setParams(alpha, sigma);
		this.curve = curve;
	}
	
	public void setParams(double alpha, double sigma) {
		this.alpha = alpha;
		this.sigma = sigma;
	}
	
	public double B(double t, double T) {
		return (1-Math.exp(-alpha*(T-t)))/alpha;
	}
	
	public double A(double t, double T) {
		int i = (int)(t/curve.tau);
		double term1 = curve.bond(T, 0)/curve.bond(t, 0);
		double term2 = B(t,T)*curve.forwardRates.getEntry(i)-Math.pow(sigma,2)/(4*alpha)*Math.pow(B(t,T), 2)*(1-Math.exp(-2*alpha*t));
		return term1*Math.exp(term2);
	}
	
	public double sigma_p(double T, double S) {
		return sigma/alpha*(1-Math.exp(-alpha*(S-T)))*Math.sqrt((1-Math.exp(-2*alpha*T))/(2*alpha));
	}
	
	public double bond(double t, double T, double r) {
		return A(t, T)*Math.exp(-B(t, T)*r);
	}
	
	public double Jamshidian(double start, double tenor) {
		Vector c = curve.fswapCashFlows.get(start).get(tenor);
		Vector T = curve.fswapTerms.get(start).get(tenor);
		Function<Double, Double> fn = r ->  Math.abs(c.eleMultiply(T.map(t -> this.bond(start, t, r))).sum()-1);
		GoldenSectionSearch optimizer = new GoldenSectionSearch();
		double result = optimizer.optimize(fn, 0, 0.1);
		return result;
	}
	
	public double rsHullWhite(double maturity, double tenor) {
		Vector c = this.curve.fswapCashFlows.get(maturity).get(tenor);
		Vector T = this.curve.fswapTerms.get(maturity).get(tenor);
		double r = Jamshidian(maturity, tenor);
		NormalDistribution norm = new NormalDistribution();
		Vector bondPrices = T.map(t -> this.curve.bond(t, 0));
		Vector X = T.map(t -> this.bond(maturity,  t, r));
		Vector sigmaP = T.map(t -> sigma_p(maturity, t));
		Vector dPos = bondPrices.scalarMultiply(1/this.curve.bond(maturity, 0)).eleDivide(X).map(x -> Math.log(x)).eleDivide(sigmaP).add(sigmaP.scalarMultiply(0.5));
		Vector dNeg = dPos.subtract(sigmaP);
		Vector term1 = bondPrices.eleMultiply(dPos.map(x -> norm.cumulativeProbability(x)));
		Vector term2 = X.eleMultiply(dNeg.map(x -> norm.cumulativeProbability(x))).scalarMultiply(this.curve.bond(maturity,  0));
		return term1.subtract(term2).eleMultiply(c).sum();
	}
	
	public void calculateHullWhiteRS() {
		int m = this.curve.swaptionMaturity.getDimension();
		int n = this.curve.swapTenor.getDimension();
		double maturity, tenor;
		HullWhiteRS = Matrix.createZeroMatrix(m, n);
		for(int i=0; i<m; i++)
			for(int j=0; j<n; j++) {
				maturity = this.curve.swaptionMaturity.getEntry(i);
				tenor = this.curve.swapTenor.getEntry(j);
				HullWhiteRS.setEntry(i, j, rsHullWhite(maturity, tenor));
			}
	}
	
	public double[] calibration(double alpha, double sigma) {
		Vector x0 = new Vector(new double[] {alpha, sigma});
		Function<Vector, Double> fn = x -> {
			HullWhite hw = new HullWhite(x.getEntry(0), x.getEntry(1), this.curve);
			hw.calculateHullWhiteRS();
			return Math.sqrt(hw.curve.blackRS.subtract(hw.HullWhiteRS).power(2).sum());
		};
		NelderMead nm = new NelderMead();
		return nm.optimize(fn, x0).getData();
	}
	
	private double theta(double t) {
		return this.curve.forward(t, 1)+this.alpha*this.curve.forward(t, 0)+Math.pow(this.sigma, 2)/(2*this.alpha)*(1-Math.exp(-2*this.alpha*t));
	}
	
	public double[] simulation(int max) {
		double dt = 1./12.;
		int n = (int)(max/dt);
		double t = 0.;
		NormalDistribution norm = new NormalDistribution();
		double[] N = norm.sample(n);
		double dW;
		Vector r = new Vector(new double[n+1]);
		r.setEntry(0, Math.log(1+this.curve.forwardBtw(0, dt)));
		for(int i=0; i<n; i++) {
			dW = Math.sqrt(dt)*N[i];
			r.setEntry(i+1, r.getEntry(i)+(this.theta(t)-this.alpha*r.getEntry(i))*dt+this.sigma*dW);
			t += dt;
		}
		return r.map(x -> Math.exp(x)-1).getData();
	}
	
	public double[][] simulation(int max, int n) {
		Vector[] v = new Vector[n];
		for(int i=0; i<n; i++)
			v[i] = new Vector(this.simulation(max));
		return Matrix.concatenateRowVector(v).getData();
	}

}