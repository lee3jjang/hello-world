package esg;

public class JacobianFunction implements MultivariateMatrixFunction {
	private MultivariateVectorFunction fn;
	private double eps = 1e-5;

	public JacobianFunction(MultivariateVectorFunction fn) {
		this.fn = fn;
	}
	
	public Matrix value(Vector point) {
		Vector y0 = this.fn.value(point);
		int n = point.getDimension();
		Vector xj, yi;
		Vector[] v = new Vector[n];
		for(int j=0; j<n; j++) {
			xj = point.copy();
			xj.addToEntry(j, this.eps);
			yi = this.fn.value(xj);
			v[j] = yi.subtract(y0).scalarMultiply(1/this.eps); 
		}
		return Matrix.concatenateColumnVector(v);
	}
}
