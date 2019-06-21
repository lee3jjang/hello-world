package esg;

public class GradientFunction implements MultivariateVectorFunction {
	private MultivariateFunction fn;
	private double eps = 1e-5;
	
	public GradientFunction(MultivariateFunction fn) {
		this.fn = fn;
	}
	
	public Vector value(Vector point) {
		int n = point.getDimension();
		Vector xi;
		double y0 = this.fn.value(point);
		double yi;
		double[] v = new double[n]; 
		for(int i=0 ;i<n; i++) {
			xi = point.copy();
			xi.addToEntry(i, this.eps);
			yi = this.fn.value(xi);
			v[i] = (yi-y0)/eps;
		}
		return new Vector(v);
	}
}
