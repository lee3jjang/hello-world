package esg;

public class KalmanFilter {
	
	private Matrix A;
	private Vector u;
	private Matrix Q;
	private Matrix H;
	private Matrix R;
	
	private Vector state;
	private Matrix error;
	
	// Initialization of Model
	public KalmanFilter(double[][] A, double[] u, double[][] Q, double[][] H, double[][] R) {
		
		this.A = new Matrix(A);
		this.u = new Vector(u);
		this.Q = new Matrix(Q);
		this.H = new Matrix(H);
		this.R = new Matrix(R);
		
	}
	// Set State and Error	
	public void setState(double[] state, double[][] error) {
		this.state = new Vector(state);
		this.error = new Matrix(error);
	}

	// Next State and Error	
	private void next(double[] measurement) {
		Vector zMeas = new Vector(measurement);
		
		// Prediction
		Vector xPred = this.A.operate(this.state).add(u);
		Matrix PPred = A.multiply(this.error).multiply(A.transpose()).add(Q);
		
		// Update
		Vector v = zMeas.subtract(H.operate(xPred));
		Matrix F = H.multiply(PPred).multiply(H.transpose()).add(R);
		Matrix K = PPred.multiply(H.transpose()).multiply(F.inverse());
		Vector xUpdate = xPred.add(K.operate(v));
		Matrix PUpdate = PPred.subtract(K.multiply(H).multiply(PPred));
		
		this.state = xUpdate;
		this.error = PUpdate;
	}
	
	// Get State
	public double[] getState() {
		return this.state.getData();
	}

	// Get Error
	public double[][] getError() {
		return this.error.getData();
	}
}