package esg;

import java.util.Random;

public class NormalDistribution {
	private double mu;
	private double sigma;
	
	public NormalDistribution() {
		this.mu = 0;
		this.sigma = 1;
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
		return Math.exp(-z*z)/(2*Math.sqrt(2*Math.PI);
	}
	
	// Cumulative Probability
	public double cumulativeProbability(double z) {
		double sign = 1;
		if(z < 0) sign = -1;
		return 0.5*(1.0 + sign*this.erf(Math.abs(z)/Math.sqrt(2)));
	}

	// Sampling
	public double sample() {
		return (new Random()).nextGaussian();
	}
	
	// Get Mean
	public double getMean() {
		return this.mu;
	}
	
	// Get Standard Deviation
	public double getStandardDeviation() {
		return this.sigma;
	}
	
}
