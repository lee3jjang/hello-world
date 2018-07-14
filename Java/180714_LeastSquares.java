package esg;

// Defining objective function 
class objFunc {
	double[] theta;
	
	// Initialization
	public objFunc(double[] theta) {
		this.theta = Matrix.copy(theta);
	}
	
	/* Vector Function */
	// function value
	public double value(double[] x) {
		double y = Matrix.dot(x, this.theta);
		return y;
	}
	
	// gradient of parameter
	public double[] grad(double[] x) {
		double[] y = new double[this.theta.length];
		y = Matrix.copy(x);
		return y;
	}
}


public class LeastSquares {
	
	/* Ordinary Least squares regression (OLS) */
	// Linear function
	public static double[] ols(double[] y, double[][] X) {
		double[] beta = Matrix.multiply(Matrix.multiply(Matrix.inverse(Matrix.multiply(Matrix.transpose(X), X)), Matrix.transpose(X)), y);
		return beta;
	}
	
	// Non-linear function
	// Gradient Descent
	public static double[] gd(objFunc f, double[] y, double[][] X, double lr, int maxiter, double eps) {
		int iter = 0;
		
		double[] theta_p = Matrix.copy(f.theta);
		double[] theta;
		objFunc g = new objFunc(theta_p);
		
		while(true) {
			// iteration
			theta = Matrix.copy(theta_p);
			g.theta = Matrix.copy(theta);
			theta_p = Matrix.subtract(theta, Matrix.scalar(2*lr, Matrix.multiply(Matrix.transpose(jacobian(g, X)), residual(g, y, X)))); 
			
			// break
			iter += 1;
			if(Matrix.norm(Matrix.subtract(theta_p, theta), 2) < eps | iter >= maxiter)
				break;
		}
		return theta_p;
	}
	
	// Gauss-Newton
	public static double[] gn(objFunc f, double[] y, double[][] X, int maxiter, double eps) {
		int iter = 0;
		
		double[] theta_p = Matrix.copy(f.theta);
		double[] theta;
		objFunc g = new objFunc(theta_p);
		double jac[][];
		
		while(true) {
			// iteration
			theta = Matrix.copy(theta_p);
			g.theta = Matrix.copy(theta);
			jac = jacobian(g, X);
			theta_p = Matrix.subtract(theta, Matrix.multiply(Matrix.multiply(Matrix.inverse(Matrix.multiply(Matrix.transpose(jac),jac)),Matrix.transpose(jac)),residual(g, y, X)));
			
			// break
			iter += 1;
			if(Matrix.norm(Matrix.subtract(theta_p, theta), 2) < eps | iter >= maxiter)
				break;
		}
		return theta_p;
	}
	
	// Levenberg-Marquardt
	public static double[] lm(objFunc f, double[] y, double[][] X, double mu, int maxiter, double eps) {
		int iter = 0;
		
		double[] theta_p = Matrix.copy(f.theta);
		double[] theta;
		objFunc g = new objFunc(theta_p);
		double jac[][];
		double temp[][];
		
		while(true) {
			// iteration
			theta = Matrix.copy(theta_p);
			g.theta = Matrix.copy(theta);
			jac = jacobian(g, X);
			temp = Matrix.multiply(Matrix.transpose(jac),jac);
			theta_p = Matrix.subtract(theta, Matrix.multiply(Matrix.multiply(Matrix.inverse(Matrix.add(temp, Matrix.scalar(mu, Matrix.diag(temp)))), Matrix.transpose(jac)), residual(g, y, X)));
			
			// break
			iter += 1;
			if(Matrix.norm(Matrix.subtract(theta_p, theta), 2) < eps | iter >= maxiter)
				break;
		}
		return theta_p;
	}
	
	
	
	// Predict
	public static double[] predict(objFunc f, double[][] X) {
		int m = X.length;
		int n = X[0].length;
		double[] x = new double[n];
		double[] y = new double[m];
		for(int i=0; i<m; i++) {
			for(int j=0; j<n; j++)
				x[j] = X[i][j];
			y[i] = f.value(x);
		}
		return y;
	}
	
	// Residual
	public static double[] residual(objFunc f, double[] y, double[][] X) {
		double[] r = Matrix.subtract(y, predict(f, X)); 
		return r;
	}
	
	// Jacobian of residual
	public static double[][] jacobian(objFunc f, double[][] X){
		int m = X.length;
		int n = X[0].length;
		int l = f.theta.length;
		double[] gr;
		double[] x = new double[n];
		double[][] jac = new double[m][l];
		for (int i=0; i<m; i++) {
			for (int j=0; j<n; j++)
				x[j] = X[i][j];
			gr = Matrix.copy(f.grad(x));
			for (int k=0; k<l; k++)
				jac[i][k] = -gr[k];
		}
		return jac;
	}
	
	// Main
	public static void main(String args[]) {
		double beta0[] = {1,2};
		objFunc f = new objFunc(beta0);
		double[] y = {1+0.01,4-0.01,9-0.01,16+0.01};
		double[][] X = {{0,1},{1,3},{2,-1},{-2,1}};
		Matrix.print(gd(f, y, X, 0.001, 10000, 1e-15));
		Matrix.print(gn(f, y, X, 10000, 1e-15));
		Matrix.print(lm(f, y, X, 0.01, 10000, 1e-15));
		Matrix.print(ols(y, X));
	}
}
