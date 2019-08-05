package esgcore.analysis;

import java.util.function.Function;
import java.util.function.UnaryOperator;

import esgcore.linalg.Matrix;
import esgcore.linalg.Vector;

public class JacobianFunction implements Function<Vector, Matrix> {
	private Function<Vector, Vector> fn;
	private double eps = 1e-5;

	public JacobianFunction(UnaryOperator<Vector> fn) {
		this.fn = fn;
	}
	
	public Matrix apply(Vector point) {
		Vector y0 = this.fn.apply(point);
		int n = point.getDimension();
		Vector xj, yi;
		Vector[] v = new Vector[n];
		for(int j=0; j<n; j++) {
			xj = point.copy();
			xj.addToEntry(j, this.eps);
			yi = this.fn.apply(xj);
			v[j] = yi.subtract(y0).scalarMultiply(1/this.eps); 
		}
		return Matrix.concatenateColumnVector(v);
	}
}
