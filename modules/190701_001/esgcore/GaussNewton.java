package esgcore;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class GaussNewton {
	private double eps = 1e-15;
	
	public Vector optimize(UnaryOperator<Vector> fn, Vector p0, Vector y) {
		Matrix J;
		Vector delta, r;
		Function<Vector, Matrix> jacobian = new JacobianFunction(x -> fn.apply(x).scalarMultiply(-1.));
		UnaryOperator<Vector> residual = p -> y.subtract(fn.apply(p));
		Vector p = p0.copy();
		while(true) {
			J = jacobian.apply(p);
			r = residual.apply(p);
			delta = J.transpose().multiply(J).inverse().multiply(J.transpose()).operate(r).scalarMultiply(-1.);
			p = p.add(delta);
			if(delta.dotProduct(delta) < eps)
				break;
		}
		return p;
	}
}
