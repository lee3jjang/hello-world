package esg;

public class Main {

	public static void main(String[] args) {
		
		
		double[] term = {1,2,3,5,7,10,20};
		double[] rate = {0.01, 0.015, 0.02 ,0.025, 0.03, 0.02, 0.03};
		SmithWilson sw = new SmithWilson(term, rate, 0.1, 0.045, 20);
		System.out.println(sw.forward(60, 0));
		sw.calcAlpha();
		sw.forwardRates.print();
		
		
	}

}
