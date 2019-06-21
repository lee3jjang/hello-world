package esg;

public class NormalDistribution {
	//private double mu;
	//private double sigma;
	
	public NormalDistribution() {
		//this.mu = 0;
		//this.sigma = 1;
	}
	
	private double erf(double x) {
		//A&S formula 7.1.26
	    double a1 = 0.254829592;
	    double a2 = -0.284496736;
	    double a3 = 1.421413741;
	    double a4 = -1.453152027;
	    double a5 = 1.061405429;
	    double p = 0.3275911;
	    x = Math.abs(x);
	    double t = 1 / (1 + p * x);
	    //Direct calculation using formula 7.1.26 is absolutely correct
	    //But calculation of nth order polynomial takes O(n^2) operations
	    //return 1 - (a1 * t + a2 * t * t + a3 * t * t * t + a4 * t * t * t * t + a5 * t * t * t * t * t) * Math.Exp(-1 * x * x);

	    //Horner's method, takes O(n) operations for nth order polynomial
	    return 1 - ((((((a5 * t + a4) * t) + a3) * t + a2) * t) + a1) * t * Math.exp(-1 * x * x);
	}
	
	// Density
	public double density(double z) {
		return Math.exp(-z*z)/(2*Math.sqrt(2*Math.PI));
	}
	
	// Cumulative Probability
	public double cumulativeProbability(double z) {
		double sign = 1;
		if(z < 0) sign = -1;
		return 0.5*(1.0 + sign*this.erf(Math.abs(z)/Math.sqrt(2)));
	}
	
	// (Private) Box-Muller Transform
	private double[] boxMullerTransform() {
		double U1, U2, R, theta, Z0, Z1;
		U1 = Math.random();
		U2 = Math.random();
		R = Math.sqrt(-2*Math.log(U1));
		theta = 2*Math.PI*U2;
		Z0 = R*Math.cos(theta);
		Z1 = R*Math.sin(theta);
		return new double[] {Z0, Z1};
	}
	
	// (Private) Marsaglia Polar Method
	private double [] marsagliaPolarMethod() {
		double u, v, s;
		while(true) {
			u = 2*Math.random() -1;
			v = 2*Math.random() -1;
			s = u*u + v*v;
			if(s < 1 & s > 0)
				break;
		}
		double mul = Math.sqrt(-2.*Math.log(s)/s);
		return new double[] {u*mul, v*mul};
	}

	// Sampling
	public double sample() {
		return boxMullerTransform()[0];
	}
	
	// Sampling n times
	public double[] sample(int n) {
		double[] values = new double[n];
		int m = n/2;
		double[] Z;
		for(int i=0; i<m; i++) {
			Z = boxMullerTransform();
			values[2*i] = Z[0];
			values[2*i+1] = Z[1];
		}
		if(n%2 == 1)
			values[n-1] = boxMullerTransform()[0];
		return values;
	}
	
	// Get Mean
	public double getMean() {
		//return this.mu;
		return 0.;
	}
	
	// Get Standard Deviation
	public double getStandardDeviation() {
		//return this.sigma;
		return 1.;
	}
	
}
