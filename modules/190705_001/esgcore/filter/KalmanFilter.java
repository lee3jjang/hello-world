package esgcore.filter;

import esgcore.distribution.NormalDistribution;
import esgcore.linalg.Matrix;
import esgcore.linalg.Vector;

public class KalmanFilter {
	
	protected Matrix A;
	protected Vector u;
	protected Matrix Q;
	protected Matrix H;
	protected Matrix R;
	
	private Vector state;
	private Matrix error;
	private Matrix measurements;
	
	public KalmanFilter() {}
	
	// Initialization of Model
	public KalmanFilter(Matrix A, Vector u, Matrix Q, Matrix H, Matrix R) {
		this.A = A.copy();
		this.u = u.copy();
		this.Q = Q.copy();
		this.H = H.copy();
		this.R = R.copy();
	}
	
	// Set State and Error
	public void setState(Vector state, Matrix error) {
		this.state = state.copy();
		this.error = error.copy();
	}
	
	// Get State
	public Vector getState() {
		return this.state;
	}

	// Get Error
	public Matrix getError() {
		return this.error;
	}
	
	// Set Measurements
	public void setMeasurements(Matrix measurements) {
		this.measurements = measurements.copy();
	}
	
	// Get Measurements
	public Matrix getMeasurements(){
		return this.measurements;
	}
	
	// Get Log-Likelihood
	public double getLogLikelihood() {
		double value = 0.;
		int m = this.measurements.getRowDimension();
		for(int i=0; i<m; i++) {
			value += this.next(this.measurements.getRowVector(i));
		}
		return value;
	}
	
	// Generate State
	public Matrix generateState(int t){
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
		return new Matrix(generatedStates);
	}
	
	// Generate Measurement
	public Matrix generateMeasurement(int t){
		int m = H.getRowDimension();
		Matrix generatedStates = this.generateState(t);
		NormalDistribution norm = new NormalDistribution();
		Vector futureState, futureMeasurement, measurementNoise;
		
		Matrix generatedMeasurements = Matrix.createZeroMatrix(t, m); 
		for(int i=0; i<t; i++) {
			futureState = generatedStates.getRowVector(i);
			measurementNoise = R.cholesky().operate(new Vector(norm.sample(m)));
			futureMeasurement = H.operate(futureState).add(measurementNoise);
			generatedMeasurements.setRowVector(i, futureMeasurement);
		}
		return generatedMeasurements;
	}

	// Next State and Error
	private double next(Vector zMeas) {
		
		// Prediction
		Vector xPred = this.A.operate(this.state).add(this.u);
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
		/*
		double detF = F.determinant();	 // Determinant 계산이 느려서 안 됨
		
		if(detF <= 0.)
			detF = 1e-15;
		double logL = -0.5*Math.log(2*Math.PI)-0.5*Math.log(detF)-0.5*v.dotProduct(F.inverse().operate(v));
		return logL;
		*/
		return 1;
	}

}