package esgcore.optim;

import java.util.function.UnaryOperator;

import esgcore.linalg.Vector;

public class GradientDescent {
	private double eps = 1e-5;
	private double learningRate = 1e-2;
	private double gamma = 0.;
	
	public Vector optimize(UnaryOperator<Vector> gradFn, Vector x0) {
		int n = x0.getDimension();
		Vector v = Vector.createZeroVector(n);
		Vector xCurrent = x0.copy();
		Vector xNext = xCurrent.add(gradFn.apply(xCurrent).scalarMultiply(this.learningRate));
		while(xNext.getDistance(xCurrent) > this.eps) {
			xCurrent = xNext.copy();
			v = v.scalarMultiply(this.gamma).add(gradFn.apply(xCurrent).scalarMultiply(this.learningRate));
			xNext = xCurrent.subtract(v);
		}
		return xNext;
	}
}
