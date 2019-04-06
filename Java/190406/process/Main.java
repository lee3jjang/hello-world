package process;

import model.DynamicNelsonSiegel;
import model.SmithWilson;

public class Main {

	public static void main(String[] args) {
		
		// 1. Smith-Wilson Method
		double[] term = {1., 3., 5., 10., 20.};
		double[] rate = {0.01752, 0.01735, 0.01777, 0.01893, 0.01918};
		
		double ufr = 0.045;
		double alpha = 0.01;
		double llp = 20.;
		
		SmithWilson sw = new SmithWilson(term, rate, alpha, ufr, llp);
		
		
		// 2. Dynamic Nelson-Siegel
		double[][] rates = {
				{0.01740, 0.01726, 0.01742, 0.01866, 0.01895},
				{0.01733, 0.01706, 0.01730, 0.01856, 0.01898},
				{0.01743, 0.01720, 0.01754, 0.01889, 0.01919},
				{0.01744, 0.01720, 0.01754, 0.01877, 0.01915},
				{0.01752, 0.01735, 0.01777, 0.01893, 0.01918}
		};
		double[] params = new double[] {0.60731, 1e-3, 0.12844, 0.34156, 0.70498, 0.04029, -0.02018, -0.00900, 0.00653, -0.00610, 0.00420, 0.00009, -0.00468, 0.01036};
		DynamicNelsonSiegel dns = new DynamicNelsonSiegel(params, term, rates);
		dns.getClass();
		System.out.println(dns.currentState);
		System.out.println(dns.currentError);
	}

}
