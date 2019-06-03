package model;

import org.apache.commons.math3.filter.DefaultMeasurementModel;
import org.apache.commons.math3.filter.DefaultProcessModel;
import org.apache.commons.math3.filter.KalmanFilter;
import org.apache.commons.math3.filter.MeasurementModel;
import org.apache.commons.math3.filter.ProcessModel;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DiagonalMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class DynamicNelsonSiegel {
	
	private final double dt = 1./12.;
	private final int m;	// 만기 길이
	private final int n;	// 데이터 길이
	
	private double[] term;
	private double[][] rates;
	
	private double[] initParams;
	public RealMatrix A, B, Q, H, R;
	
	public RealVector currentState;
	public RealMatrix currentError;
	
	
	public DynamicNelsonSiegel(double[] initParams, double[] term, double[][] rates) {
		this.initParams = initParams;
		this.term = term;
		this.rates = rates;
		
		this.n = rates.length;
		this.m = term.length;
		
		setModel(initParams);
		setCurrentState();
	}
	
	private void setModel(double[] params) {
		// params : 0, 1,  2,  3,  4,  5,  6,  7,   8,   9,  10,  11,  12,  13
		// params : λ, ε, κ1, κ2, κ3, θ1, θ2, θ3, σ11, σ21, σ22, σ31, σ32, σ33
		
		A = new DiagonalMatrix(new double[] {1-params[2]*dt, 1-params[3]*dt,  1-params[4]*dt});
		B = new Array2DRowRealMatrix(new double[] {params[2]*params[5]*dt, params[3]*params[6]*dt, params[4]*params[7]*dt});
		H = new Array2DRowRealMatrix(new double[m][3]);
		for(int i=0; i<m; i++) {
			H.setEntry(i, 0, 1);
			H.setEntry(i, 1, (1-Math.exp(-params[0]*term[i]))/(params[0]*term[i]));
			H.setEntry(i, 2, (1-Math.exp(-params[0]*term[i]))/(params[0]*term[i])-Math.exp(-params[0]*term[i]));
		}
		RealMatrix S = new Array2DRowRealMatrix(new double[][] {{params[8], 0., 0.}, {params[9], params[10], 0}, {params[11], params[12], params[13]}});
		Q = S.multiply(S.transpose()).scalarMultiply(dt);
		R = MatrixUtils.createRealIdentityMatrix(m).scalarMultiply(params[1]*params[1]);
	}
	
	private void setCurrentState() {
		RealVector x = new ArrayRealVector(new double[3]);
		RealMatrix P = MatrixUtils.createRealIdentityMatrix(3);
		ProcessModel pm = new DefaultProcessModel(A, B, Q, x, P);
		MeasurementModel mm = new DefaultMeasurementModel(H, R);
		KalmanFilter filter = new KalmanFilter(pm, mm);
		RealVector u = new ArrayRealVector(new double[] {1.});
		
		for(int i=0; i<n; i++) {
			filter.predict(u);
			filter.correct(rates[i]);
			x = filter.getStateEstimationVector();
			P = (filter.getErrorCovarianceMatrix().add(filter.getErrorCovarianceMatrix().transpose()).scalarMultiply(0.5));	// 연산 시 대칭행렬조건에 벗어나게 되어, Cholesky 분해가 되지 않음. 강제 예외처리.
			pm = new DefaultProcessModel(A, B, Q, x, P);
			filter = new KalmanFilter(pm, mm);
		}
		
		currentState = x;
		currentError = P;
	}
}