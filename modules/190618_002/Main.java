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
		
		NelderMead nm = new NelderMead();
		nm.optimize(new Rosenbrock(), new Vector(new double[] {1., 3., 2.})).print();
		
		
	}

}
