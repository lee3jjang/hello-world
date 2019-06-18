package esg;

import java.util.Arrays;
import java.util.Comparator;

public class NelderMead {
	private double alpha = 1.0;
	private double gamma = 2.0;
	private double rho = 0.5;
	private double sigma = 0.5;
	private double eps = 1e-10;
	
	private NormalDistribution norm = new NormalDistribution();

	public Vector optimize(MultivariateFunction fn, Vector x0) {
		int n = x0.getDimension();
		Vector x_o, x_r, x_e, x_c, temp;
		Matrix X;
		Vector[] x = new Vector[n+1];
		x[0] = x0.copy();
		for(int i=1; i<n+1; i++) {
			temp = x0.copy();
			temp.addToEntry(i-1, norm.sample());
			x[i] = temp;
		}
		while(Matrix.concatenateColumnVector(x).applyColumnVector(fn).std() > this.eps) {
			x[0].print();
			//1. Order
			Arrays.sort(x, new CompareVectorByFunction(fn));
			//2. Centroid
			X = Matrix.concatenateRowVector(x);
			X.deleteRowVector(n);
			x_o = X.meanColumnVector();
			//3. Reflection
			x_r = x_o.add(x_o.subtract(x[n]).scalarMultiply(this.alpha));
			if(fn.value(x_r) >= fn.value(x[0]) & fn.value(x_r) < fn.value(x[n-1])) {
				x[n] = x_r;
			} else if (fn.value(x_r) < fn.value(x[0])) {
				//4. Expansion
				x_e = x_o.add(x_r.subtract(x_o).scalarMultiply(this.gamma));
				if(fn.value(x_e) < fn.value(x_r)) {
					x[n] = x_e;
				} else {
					x[n] = x_r;
				}
			} else {
				//5. Contraction
				x_c = x_o.add(x[n].subtract(x_o).scalarMultiply(this.rho));
				if(fn.value(x_c) < fn.value(x[n])) {
					x[n] = x_c;
				} else {
					//6. Shrink
					for(int i=1; i<n+1; i++) {
						x[i] = x[0].add(x[i].subtract(x[0]).scalarMultiply(this.sigma));
					}
				}
			}
		}
		return x[0];
	}
	
	class CompareVectorByFunction implements Comparator<Vector>{
		private MultivariateFunction fn;
		
		public CompareVectorByFunction(MultivariateFunction fn) {
			this.fn = fn;
		}
		
		public int compare(Vector v, Vector w) {
			if(fn.value(v) > fn.value(w)){
				return 1;
			} else if(fn.value(v) < fn.value(w)) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}