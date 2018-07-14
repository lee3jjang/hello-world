package esg;

class objFunc {
	// value at x
	public double value(double x) {
		return der(x, 0);
	}
	// derivatives
	public double der(double x, int order) {
		double y = 0;
		if (order == 0)
			y = 2*Math.sin(x) - x*x/10;
		else if (order == 1)
			y = 2*Math.cos(x) - 0.2*x; 
		else if (order == 2)
			y = -2*Math.sin(x) - 0.2;
		else 
			throw new RuntimeException("유효하지 않은 차수입니다.");
		return y;
	}
}


public class Optimization {
	
	// Newton's method
	public static double newton(objFunc f, double x0, int maxiter, double eps) {
		int iter = 0;
		double x = 0;
		double xp = x0;
		while(true) {
			// iteration
			x = xp;
			xp = x - f.der(x,1)/f.der(x,2);
			
			// break
			iter += 1;
			if(Math.abs(xp - x) < eps | iter >= maxiter)
				break;
		}
		return xp;
	}
	
	// Gradient descent
	public static double grad(objFunc f, double x0, double lr, int maxiter, double eps) {
		int iter = 0;
		double x = 0; 
		double xp = x0;
		while(true) {
			// iteration
			x = xp;
			xp = x + lr*f.der(x,1);
			
			// break
			iter += 1;
			if(Math.abs(xp - x) < eps | iter >= maxiter)
				break;
		}
		return xp;
	}
	
	// Main
	public static void main(String args[]) {
		double x0 = 2.5;
		objFunc f = new objFunc();
		double result = newton(f, x0, 1000, 1e-15);
		System.out.println(result);
	}
}
