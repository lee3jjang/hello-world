package esg;

public class SmithWilson {
	public double alpha;
	public double UFR;
	public double[] term;
	public double[] rate;
	public double[] zeta;
	
	// Initialization
	public SmithWilson(double alpha, double UFR, double[] term, double[] rate) {
		int n = term.length;
		int i, j;
		
		// Set parameters
		this.alpha = alpha;
		this.UFR = UFR;
		this.term = new double[n];
		this.rate = new double[n];
		
		for (i=0; i<n; i++)
			this.term[i] = term[i];
		for (i=0; i<n; i++)
			this.rate[i] = rate[i];
		
		// Calculation of zeta
		double[][] W = new double[n][n];
		for (i=0; i<n; i++)
			for (j=0; j<n; j++)
				W[i][j] = Wilson(this.term[i], this.term[j], 0);
		double[] m = new double[n];
		double[] mu = new double[n];
		for (i=0; i<n; i++) {
			m[i] = Math.pow(1/(1+this.rate[i]), this.term[i]);
			mu[i] = Math.exp(-this.UFR*this.term[i]);
		}
		this.zeta = Matrix.multiply(Matrix.inverse(W), Matrix.subtract(m, mu));
	}
	
	// Wilson function and its derivatives
	public double Wilson(double t, double u, int order) {
		double wilson;
		if (order==0) {
			wilson = Math.exp(-this.UFR*(t+u)) * (this.alpha*Math.min(t, u) - Math.exp(-this.alpha*Math.max(t, u))*Math.sinh(this.alpha*Math.min(t, u)));
		} else if (order==1) {
			if (t<u)
				wilson = Math.exp(-this.UFR*t-(this.alpha+this.UFR)*u)*(this.UFR*Math.sinh(this.alpha*t)-this.alpha*Math.cosh(this.alpha*t)-this.alpha*(this.UFR*t-1)*Math.exp(this.alpha*u));
			else
				wilson = Math.exp(-this.UFR*u-(this.alpha+this.UFR)*t)*((this.alpha+this.UFR)*Math.sinh(this.alpha*u)-this.alpha*this.UFR*u*Math.exp(this.alpha*t));		
		} else if (order==2) {
			if (t<u)
				wilson = Math.exp(-this.UFR*t-(this.alpha+this.UFR)*u)*(-(this.alpha*this.alpha+this.UFR*this.UFR)*Math.sinh(this.alpha*t)+2*this.alpha*this.UFR*Math.cosh(this.alpha*t)+this.alpha*this.UFR*(this.UFR*t-2)*Math.exp(this.alpha*u));
			else
				wilson = Math.exp(-this.UFR*u-(this.alpha+this.UFR)*t)*(this.alpha*this.UFR*this.UFR*u*Math.exp(this.alpha*t)-(this.alpha+this.UFR)*(this.alpha+this.UFR)*Math.sinh(this.alpha*u));
		} else {
			throw new RuntimeException("유효하지 않은 차수입니다.");
		}
		return wilson;
	}
	
	// Bond price
	public double bond(double t, int order) {
		int n = this.term.length;
		double bond = Math.pow(-this.UFR, order)*Math.exp(-this.UFR*t);
		for (int i=0; i<n; i++)
			bond += Wilson(t, this.term[i], order)*this.zeta[i];
		return bond;
	}
	
	// Spot rate
	public double spot(double t) {
		t = Math.max(t, 1e-6);
		return Math.pow(1/bond(t,0), 1/t) - 1;
	}
	
	// Forward rate and its 1st derivative
	public double forward(double t, int order) {
		double forward;
		if (order==0)
			forward = -bond(t,1)/bond(t,0);
		else if (order==1)
			forward = 1/bond(t,0)*(-bond(t,1)*bond(t,1)/bond(t,0)+bond(t,2));
		else
			throw new RuntimeException("유효하지 않은 차수입니다.");
		return forward;
	}
	
	// Forward swap rate
	public double fswaprate(double T, double S, double tau) {
		double sum = 0;
		for (double x=T; T<=S; x++)
			sum += bond(x,0);
		return (bond(T,0) - bond(S,0))/(tau*sum);

	}
	
	// main
	public static void main(String args[]) {
		double alpha = 0.05;
		double UFR = 0.045;
		double[] term = {1,2,3,4,5,7,10,12,15,20};
		double[] rate = {0.0334,0.0326,0.0327,0.0328,0.033,0.0335,0.0342,0.0345,0.0347,0.0352};
		
		SmithWilson sw = new SmithWilson(alpha, UFR, term, rate);
		System.out.println(sw.UFR);
		System.out.println(sw.Wilson(4, 2, 1));
		System.out.println(sw.spot(10));
		System.out.println(sw.forward(110,0));
		System.out.println(sw.fswaprate(1,1,0.25));
	}
}
