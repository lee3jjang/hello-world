package esgcore.regression;

import esgcore.linalg.Matrix;
import esgcore.linalg.Vector;

public class SimpleLinearRegression {
	private Matrix regressors;
	private Vector regressand;
	private Vector beta;
	
	public SimpleLinearRegression(Vector regressors, Vector regressand) {
		int n = regressors.getDimension();
		double[][] A = new double[n][2];
		for(int i=0; i<n; i++) {
			A[i][0] = 1.;
			A[i][1] = regressors.getEntry(i);			
		}
		this.regressors = new Matrix(A);
		this.regressand = regressand.copy();
		this.setBeta();
	}
	
	public void setBeta() {
		this.beta = regressors.transpose().multiply(regressors).inverse().multiply(regressors.transpose()).operate(regressand);
	}
	
	public Vector getBeta() {
		return this.beta;
	}
	
	public double predict(double regressor) {
		return this.beta.getEntry(0)+this.beta.getEntry(1)*regressor;
	}
	
	public Vector predict(Vector regressors) {
		return regressors.map(x -> predict(x));
	}
}
