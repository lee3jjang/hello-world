package esg;

public class SimpleLinearRegression {
	private Matrix regressors;
	private Vector regressand;
	private Vector beta;
	
	public SimpleLinearRegression(double[] regressors, double[] regressand) {
		int n = regressors.length;
		double[][] A = new double[n][2];
		for(int i=0; i<n; i++) {
			A[i][0] = 1.;
			A[i][1] = regressors[i];			
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
	
	public double[] predict(double[] regressors) {
		int m = this.regressors.getRowDimension();
		int n = this.regressors.getColumnDimension();
		double[][] A = new double[m][n];
		for(int i=0; i<m; i++) {
			A[i][0] = 1.;
			A[i][1] = regressors[i];
		}
		return (new Matrix(A)).operate(this.beta).getData();
	}
}
