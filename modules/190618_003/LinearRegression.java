package esg;

public class LinearRegression {
	private Matrix regressors;
	private Vector regressand;
	private Vector beta;
	
	// Multilinear
	public LinearRegression(double[][] regressors, double[] regressand) {
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
	
	// Polynomial of order k
	public LinearRegression(double[] regressors, double[] regressand, int k) {
		int n = regressors.length;
		double[][] A = new double[n][k+1];
		for(int i=0; i<n; i++)
			for(int j=0; j<k+1; j++)
				A[i][j] = Math.pow(regressors[i], j);
		this.regressors = new Matrix(A);
		this.regressand = new Vector(regressand);
		this.setBeta();
	}
	
	// Linear
	public LinearRegression(double[] regressors, double[] regressand) {
		int n = regressors.length;
		double[][] A = new double[n][2];
		for(int i=0; i<n; i++)
			for(int j=0; j<2; j++)
				A[i][j] = Math.pow(regressors[i], j);
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
}
