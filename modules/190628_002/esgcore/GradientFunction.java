package esgcore;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class GradientFunction implements UnaryOperator<Vector> {
	private Function<Vector, Double> fn;
	private double eps = 1e-5;
	
	public GradientFunction(Function<Vector, Double> fn) {
		this.fn = fn;
	}
	
	@Override
	public Vector apply(Vector point) {
		int n = point.getDimension();
		Vector xi;
		double y0 = this.fn.apply(point);
		double yi;
		double[] v = new double[n]; 
		for(int i=0 ;i<n; i++) {
			xi = point.copy();
			xi.addToEntry(i, this.eps);
			yi = this.fn.apply(xi);
			v[i] = (yi-y0)/eps;
		}
		return new Vector(v);
	}
}
