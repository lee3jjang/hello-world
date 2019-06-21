package esg;

public class KalmanFilter {
	
	protected Matrix A;
	protected Vector u;
	protected Matrix Q;
	protected Matrix H;
	protected Matrix R;
	
	private Vector state;
	private Matrix error;
	
	public KalmanFilter() {}
	
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
	
	// Estimate
	public double estimate(double[][] measurements) {
		double value = 0.;
		int m = measurements.length;
		for(int i=0; i<m; i++) {
			value += this.next(measurements[i]);
		}
		return value;
	}
	
	// Generate State
	public double[][] generateState(int t){
		int n = u.getDimension();
		Vector futureState, stateNoise;
		NormalDistribution norm = new NormalDistribution();
		double[][] generatedStates = new double[t][n]; 
		futureState = state.copy();
		for(int i=0; i<t; i++) {
			stateNoise = Q.cholesky().operate(new Vector(norm.sample(n)));
			futureState = A.operate(futureState).add(u).add(stateNoise);
			generatedStates[i] = futureState.getData();
		}
		return generatedStates;
	}
	
	// Generate Measurement
	public double[][] generateMeasurement(int t){
		int m = H.getRowDimension();
		double[][] generatedStates = this.generateState(t);
		NormalDistribution norm = new NormalDistribution();
		Vector futureState, futureMeasurement, measurementNoise;
		double[][] generatedMeasurements= new double[t][m]; 
		for(int i=0; i<t; i++) {
			futureState = new Vector(generatedStates[i]);
			measurementNoise = R.cholesky().operate(new Vector(norm.sample(m)));
			futureMeasurement = H.operate(futureState).add(measurementNoise);
			generatedMeasurements[i] = futureMeasurement.getData();
		}
		return generatedMeasurements;
	}

	// Next State and Error
	private double next(double[] measurement) {
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
		
		// Log-Likelihood
		double detF = F.determinant();	 /* Determinant 계산이 느려서 안 됨 */
		
		if(detF <= 0.)
			detF = 1e-15;
		double logL = -0.5*Math.log(2*Math.PI)-0.5*Math.log(detF)-0.5*v.dotProduct(F.inverse().operate(v));
		return logL;
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