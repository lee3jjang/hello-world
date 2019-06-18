package esg;

import java.util.Arrays;
import java.util.Comparator;

public class NelderMead {
	private double alpha = 1.0;
	private double gamma = 1.0;
	private double rho = 1.0;
	private double sigma = 1.0;
	
	private NormalDistribution norm = new NormalDistribution();

	public Vector optimize(MultivariateFunction fn, Vector x0) {
		int n = x0.getDimension();
		Vector temp;
		Vector[] x = new Vector[n+1];
		temp = x0.copy();
		for(int i=1; i<n+1; i++) {
			temp = x0.copy();
			temp.addToEntry(i-1, norm.sample());
			x[i] = temp;
		}
		//1. Order
		Arrays.sort(x, new CompareVectorByFunction(fn));
		
		return null;
	}
	
	class CompareVectorByFunction implements Comparator<Vector>{
		private MultivariateFunction fn;
		
		public CompareVectorByFunction(MultivariateFunction fn) {
			this.fn = fn;
		}
		
		public int compare(Vector v, Vector w) {
			return (int)(fn.value(v) - fn.value(w));
		}
		
	}
	
}