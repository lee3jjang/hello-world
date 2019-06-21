package esg;

public class DynamicNelsonSiegel extends KalmanFilter {
	
	private double dt = 1./12.;
	private Vector tau = new Vector(new double[] {0.25, 0.5, 0.75, 1., 1.5, 2., 2.5, 3., 5., 7., 10., 15., 20.}); 
	
	public DynamicNelsonSiegel(double[] param) {
		// Get parameters
		double lambda = param[0];
		double eps = param[1];
		double kappa1 = param[2];
		double kappa2 = param[3];
		double kappa3 = param[4];
		double theta1 = param[5];
		double theta2 = param[6];
		double theta3 = param[7];
		double sigma11 = param[8];
		double sigma21 = param[9];
		double sigma22 = param[10];
		double sigma31 = param[11];
		double sigma32 = param[12];
		double sigma33 = param[13];
		
		// Set States
		this.A = (new Vector(new double[] {1-kappa1*dt, 1-kappa2*dt, 1-kappa3*dt})).diag();
		this.u = new Vector(new double[] {kappa1*theta1*dt, kappa2*theta2*dt, kappa3*theta3*dt});
		Matrix S = new Matrix(new double[][] {{sigma11, 0., 0.}, {sigma21, sigma22, 0}, {sigma31, sigma32, sigma33}});
		this.Q = S.multiply(S.transpose()).scalarMultiply(dt);
		int m = tau.getDimension();
		this.H = Matrix.createZeroMatrix(m, 3);
		H.setColumnVector(0, tau.map(t -> 1.));
		H.setColumnVector(1, tau.map(t -> (1.-Math.exp(-lambda*t))/(lambda*t)));
		H.setColumnVector(2, tau.map(t -> (1.-Math.exp(-lambda*t))/(lambda*t)-Math.exp(-lambda*t)));
		this.R = Matrix.createIdentityMatrix(m).scalarMultiply(Math.pow(eps, 2)); 
	}
}
