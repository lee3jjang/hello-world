package esg;

public class Gradient {
	private MultivariateFunction fn;
	private double eps = 1e-5;
	
	public Gradient(MultivariateFunction fn) {
		this.fn = fn;
	}
	
	public MultivariateVectorFunction evaluate(Vector x) {
		
		int n = x.getDimension();
		this.fn
	}
}
