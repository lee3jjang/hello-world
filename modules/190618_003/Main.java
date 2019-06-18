package esg;


public class Main {

	public static void main(String[] args) {
		
		double[] term = {1, 2, 3, 5, 7, 10, 20};
		//double[][] term = {{1, 3}, {2, 3}, {5, 7}, {10, 20}, {-5, -4}, {-3, 5} , {4, 7}};
		double[] rate = {0.01, 0.015, 0.02 ,0.025, 0.03, 0.02, 0.03};
		
		LinearRegression reg = new LinearRegression(term, rate);
		Vector v = new Vector(reg.getBeta());
		v.print();
		
		GradientDescent gd = new GradientDescent();
		Vector x0 = new Vector(new double[] {Math.PI});
		MultivariateVectorFunction fn = x -> new Vector(new double[] {2*Math.cos(x.getEntry(0))-3.9*Math.sin(1.3*x.getEntry(0))+0.03*Math.pow(x.getEntry(0)-3, 2)});
		
		//NelderMead nm = new NelderMead();
		//nm.optimize(new Rosenbrock(), new Vector(new double[] {1., 3., 2.})).print();
		
		double[] param = new double[] {0.60731, 0.001, 0.12844, 0.34156, 0.70498, 0.04029, -0.02018, -0.00900, 0.00653, -0.00610, 0.00420, 0.00009, -0.00468, 0.01036};
		DynamicNelsonSiegel dns = new DynamicNelsonSiegel(param);
		double[] initialState = new double[] {0.0258,  -0.0104, 0.0032};
		double[][] initialError = new double[][] {{1., 0., 0.}, {0., 1., 0.}, {0., 0., 1.}};
		dns.setState(initialState, initialError);
		(new Matrix(dns.generateState(12))).print();
		
	}
}
