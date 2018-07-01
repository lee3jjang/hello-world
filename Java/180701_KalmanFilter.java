package esg;

public class KalmanFilter {
	private double[][] A;
	private double[][] B;
	private double[][] Q;
	private double[][] H;
	private double[][] R;
	
	public KalmanFilter(double [][] A, double [][] B, double [][] Q, double [][] H, double [][] R) {
		int n, m;
		
		// A 초기화
		n = A.length;
		m = A[0].length;
		this.A = new double[n][m];
		for(int i=0; i<n; i++)
			for(int j=0; j<m; j++)
				this.A[i][j] = A[i][j];
		
		// B 초기화
		n = B.length;
		m = B[0].length;
		this.B = new double[n][m];
		for(int i=0; i<n; i++)
			for(int j=0; j<m; j++)
				this.B[i][j] = B[i][j];
		
		// Q 초기화
		n = Q.length;
		m = Q[0].length;
		this.Q = new double[n][m];
		for(int i=0; i<n; i++)
			for(int j=0; j<m; j++)
				this.Q[i][j] = Q[i][j];
		
		// H 초기화
		n = H.length;
		m = H[0].length;
		this.H = new double[n][m];
		for(int i=0; i<n; i++)
			for(int j=0; j<m; j++)
				this.H[i][j] = H[i][j];
		
		// R 초기화
		n = R.length;
		m = R[0].length;
		this.R = new double[n][m];
		for(int i=0; i<n; i++)
			for(int j=0; j<m; j++)
				this.R[i][j] = R[i][j];
		
	}

	private State next(double[] x, double[][] P, double[] z_meas) {
		
		// Prediction
		double[] x_pred = Matrix.multiply(this.A, x);
		double[][] P_pred = Matrix.add(Matrix.multiply(Matrix.multiply(this.A, P), Matrix.transpose(this.A)), this.Q);
		
		// Update
		double[] v = Matrix.subtract(z_meas, Matrix.multiply(this.H, x_pred));
		double[][] F = Matrix.add(Matrix.multiply(Matrix.multiply(this.H, P_pred), Matrix.transpose(this.H)), this.R);
		double[][] K = Matrix.multiply(Matrix.multiply(P_pred, Matrix.transpose(this.H)), Matrix.inverse(F));
		double[] x_update = Matrix.add(x_pred, Matrix.multiply(K, v));
		double[][] P_update = Matrix.subtract(P_pred, Matrix.multiply(Matrix.multiply(K, this.H), P_pred));
		
		// Log-likelihood
		double dF = Math.max(Matrix.determinant(F),10e-10);
		double logl = -0.5*Math.log(2*Math.PI)-0.5*Math.log(dF)-0.5*Matrix.multiply(Matrix.multiply(Matrix.transpose(v), Matrix.inverse(F)), v)[0];
		
		return new State(x_update, P_update, logl);
	}
	
	// Main 
	public static void main(String args[]) {
		
		// Set parameters
		double dt = 0.1;
		double[][] A = {{1,dt},
						{2,1}};
		double[][] B = {{dt*dt/2},
						{dt}};
		double[][] Q = Matrix.identity(2);
		double[][] H = {{1,0}};
		double[][] R = {{10}};

		double[] x = {1,0};
		double[][] P = {{0.1, 0},
					    {0, 0.1}};
		double[] z = {0.7};
		
		// Initialize
		KalmanFilter kf = new KalmanFilter(A, B, Q, H, R);
		
		// Update next state
		State state_next = kf.next(x, P, z);
		double[] x_n = state_next.x;
		double[][] P_n = state_next.P;
		double logl = state_next.logl;
		
		// Print
		Matrix.print(x_n);
		Matrix.print(P_n);
		System.out.println(logl);

	}
}

class State {
	double [] x;
	double [][] P;
	double logl;
	
	State(double[] x, double[][] P, double logl){
		int n = x.length;
		this.x = new double[n];
		this.P = new double[n][n];
		this.logl = logl;
		
		for(int i=0; i<n; i++)
			this.x[i] = x[i];
		for(int i=0; i<n; i++)
			for(int j=0; j<n; j++)
				this.P[i][j] = P[i][j];
	}
	
}