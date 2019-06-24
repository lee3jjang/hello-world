package esg;

import java.util.function.UnaryOperator;

public class GoldenSectionSearch {
	private double eps = 1e-10;
	
	public double optimize(UnaryOperator<Double> fn, double x1, double x2) {
		double phi = (1+Math.sqrt(5))/2;
		double x3, x4;
		x3 = x2 - (x2-x1)/phi;
		x4 = x1 + (x2-x1)/phi;
		while(Math.abs(x3-x4) > this.eps) {
			if(fn.apply(x3) < fn.apply(x4)) {
				x2 = x4;
				x4 = x3;
				x3 = x2 - (x2-x1)/phi;
			} else {
				x1 = x3;
				x3 = x4;
				x4 = x1 + (x2-x1)/phi;
			}
		}
		return (x1+x2)/2;
	}
}
