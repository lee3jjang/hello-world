package esg;

public class HullWhite {
	SmithWilson curve;
	double alpha;
	double sigma;
	public HullWhite(SmithWilson curve, double alpha, double sigma) {
		this.curve = curve;
		this.alpha = alpha;
		this.sigma = sigma;
	}
	
	// Sigma
	public double Sigma(double T, double S) {
		double sigma_p = (Math.exp(-alpha*T)-Math.exp(-alpha*S))*(sigma/alpha)*Math.sqrt((Math.exp(2*alpha*T)-1)/(2*alpha));
		return sigma_p;
				
	}
	
	// A
	public double A(double t, double T) {
		double a = curve.bond(T,0)/curve.bond(t,0)*Math.exp(B(t,T)*curve.forward(t,0)-Math.pow(sigma,2)*(Math.pow((Math.exp(-alpha*T)-Math.exp(-alpha*t)),2)*(Math.exp(2*alpha*t)-1))/(4*Math.pow(alpha,3)));
		return a;
	}
	
	// B
	public double B(double t, double T) {
		double b = (1-Math.exp(-alpha*(T-t)))/alpha;
		return b;
	}
	
	// 채권가격
	public double bond(double t, double T, double r) {
		double bond = A(t,T)*Math.exp(-B(t,T)*r);
		return bond;
	}
	
	
	// 선도스왑 현금흐름
	public double[] fswapCashFlow(double T, double S, double tau) {
		int n = (int)((S-T)/tau);
		double[] cf = Matrix.scalar(curve.fswaprate(T, S, tau), Matrix.ones(n));
		return cf;
	}	
	
	public static void main(String args[]) {
		double alpha = 0.05;
		double UFR = 0.045;
		double[] term = {1,2,3,4,5,7,10,12,15,20};
		double[] rate = {0.0334,0.0326,0.0327,0.0328,0.033,0.0335,0.0342,0.0345,0.0347,0.0352};
		
		double a = 0.01;
		double vol = 0.005;
		double[][] blackVol = {{0.1815,0.172,0.17,0.1605,0.158,0.155},
		                       {0.177,0.1715,0.17,0.158,0.1545,0.15},
		                       {0.174,0.168,0.1655,0.1555,0.1495,0.1475},
		                       {0.165,0.1575,0.157,0.1495,0.1455,0.144},
		                       {0.165,0.1575,0.157,0.1535,0.1455,0.144},
		                       {0.1665,0.159,0.1575,0.15,0.1455,0.14}};
		
		SmithWilson sw = new SmithWilson(alpha, UFR, term, rate);
		HullWhite hw = new HullWhite(sw, a, vol);
		System.out.println(hw.Sigma(4, 7));
		System.out.println(hw.A(3,6));
		System.out.println(hw.bond(3,6,0.05));
		Matrix.print(hw.fswapCashFlow(1, 3, 0.25));
		Matrix.print(blackVol);
		
	}
	
	
}
