package esgcore;

public class MultilinearRegression {
	private Matrix regressors;
	private Vector regressand;
	private Vector beta;
	
	public MultilinearRegression(double[][] regressors, double[] regressand) {
		int n = regressors.length;
		int k = regressors[0].length;
		double[][] A = new double[n][k+1];
		for(int i=0; i<n; i++) {
			A[i][0] = 1;
			for(int j=0; j<k; j++)
				A[i][j+1] = regressors[i][j];
		}
		this.regressors = new Matrix(A);
		this.regressand = new Vector(regressand);
		this.setBeta();
	}
	
	public void setBeta() {
		this.beta = regressors.transpose().multiply(regressors).inverse().multiply(regressors.transpose()).operate(regressand);
	}
	
	public double[] getBeta() {
		return this.beta.getData();
	}
	
	public double[] predict(double[][] regressors) {
		int m = this.regressors.getRowDimension();
		int n = this.regressors.getColumnDimension();
		double[][] A = new double[m][n];
		for(int i=0; i<m; i++) {
			A[i][0] = 1.;
			for(int j=0; j<n-1; j++)
				A[i][j+1] = regressors[i][j];
		}
		return (new Matrix(A)).operate(this.beta).getData();
	}
}
