package esg;

public class GaussNewton {
	private double eps = 1e-15;
	
	public Vector optimize(MultivariateVectorFunction fn, Vector p0, Vector y) {
		Matrix J;
		Vector delta, r;
		MultivariateMatrixFunction jacobian = new JacobianFunction(x -> fn.value(x).scalarMultiply(-1.));
		MultivariateVectorFunction residual = p -> y.subtract(fn.value(p));
		Vector p = p0.copy();
		while(true) {
			J = jacobian.value(p);
			r = residual.value(p);
			delta = J.transpose().multiply(J).inverse().multiply(J.transpose()).operate(r).scalarMultiply(-1.);
			p = p.add(delta);
			if(delta.dotProduct(delta) < eps)
				break;
		}
		return p;
	}
}
