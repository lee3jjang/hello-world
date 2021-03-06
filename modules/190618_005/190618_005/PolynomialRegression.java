package esg;

public class PolynomialRegression {
	private Matrix regressors;
	private Vector regressand;
	private Vector beta;
	private int order;
	
	// Polynomial of order k
	public PolynomialRegression(double[] regressors, double[] regressand, int k) {
		this.order = k;
		this.regressand = new Vector(regressand);
		int n = regressors.length;
		double[][] A = new double[n][k+1];
		for(int i=0; i<n; i++)
			for(int j=0; j<k+1; j++)
				A[i][j] = Math.pow(regressors[i], j);
		this.regressors = new Matrix(A);
		this.setBeta();
	}
	
	public void setBeta() {
		this.beta = regressors.transpose().multiply(regressors).inverse().multiply(regressors.transpose()).operate(regressand);
	}
	
	public double[] getBeta() {
		return this.beta.getData();
	}
	
	public double[] predict(double[] regressors) {
		int n = regressors.length;
		double[][] A = new double[n][this.order+1];
		for(int i=0; i<n; i++)
			for(int j=0; j<this.order+1; j++)
				A[i][j] = Math.pow(regressors[i], j);
		return (new Matrix(A)).operate(this.beta).getData();
	}

}
