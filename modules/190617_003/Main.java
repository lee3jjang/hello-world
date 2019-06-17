package esg;


public class Main {

	public static void main(String[] args) {
		
		
		//double[] term = {1,2,3,5,7,10,20};
		double[][] term = {{1, 3}, {2,3}, {5,7}, {10, 20}, {-5, -4}, {-3, 5} , {4, 7}};
		double[] rate = {0.01, 0.015, 0.02 ,0.025, 0.03, 0.02, 0.03};
		
		LinearRegression reg = new LinearRegression(term, rate);
		Vector v = new Vector(reg.getBeta());
		v.print();
	}

}
