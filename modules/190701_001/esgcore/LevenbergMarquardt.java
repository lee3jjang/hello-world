package esgcore;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class LevenbergMarquardt {
	private double eps = 1e-15;
	private double mu0 = 0.01;
	
	public Vector optimize(UnaryOperator<Vector> fn, Vector p0, Vector y) {
		Matrix J;
		Vector delta;
		Function<Vector, Matrix> jacobian = new JacobianFunction(x -> fn.apply(x).scalarMultiply(-1.));
		UnaryOperator<Vector> residual = p -> y.subtract(fn.apply(p));
		Vector p = p0.copy();
		Vector r = residual.apply(p0);
		double E;
		double mu = this.mu0;
		double Ep = r.dotProduct(r); 
		while(true) {
			J = jacobian.apply(p);
			r = residual.apply(p);
			E = Ep;
			Ep = r.dotProduct(r);
			if(Ep > E)
				mu  += this.mu0;
			else
				mu = this.mu0;
			delta = J.transpose().multiply(J).add(J.transpose().multiply(J).diag().diag().scalarMultiply(mu)).inverse().multiply(J.transpose()).operate(r).scalarMultiply(-1.);
			p = p.add(delta);
			if(delta.dotProduct(delta) < eps)
				break;
		}
		return p;
	}
}
