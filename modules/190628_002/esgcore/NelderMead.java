package esgcore;

import java.util.Arrays;
import java.util.function.Function;

public class NelderMead {
	private double alpha = 1.0;
	private double gamma = 2.0;
	private double rho = 0.5;
	private double sigma = 0.5;
	private double eps = 1e-10;
	
	private NormalDistribution norm = new NormalDistribution();

	public Vector optimize(Function<Vector, Double> fn, Vector x0) {
		int n = x0.getDimension();
		Vector x_o, x_r, x_e, x_c, temp;
		Matrix X;
		Vector[] x = new Vector[n+1];
		x[0] = x0.copy();
		for(int i=0; i<n; i++) {
			temp = x0.copy();
			temp.addToEntry(i, norm.sample());
			x[i+1] = temp;
		}
		while(Matrix.concatenateColumnVector(x).applyColumnVector(fn).std() > this.eps) {
			x[0].print();    // Temp
			//1. Order
			//Arrays.sort(x, new CompareVectorByFunction(fn));
			Arrays.sort(x, (v, w) -> {
				double sign = fn.apply(v) - fn.apply(w);
				if(sign > 0) return 1;
				else if(sign < 0) return -1;
				else return 0;
			});
			//2. Centroid
			X = Matrix.concatenateRowVector(x);
			X.deleteRowVector(n);
			x_o = X.meanColumnVector();
			//3. Reflection
			x_r = x_o.add(x_o.subtract(x[n]).scalarMultiply(this.alpha));
			if(fn.apply(x_r) >= fn.apply(x[0]) & fn.apply(x_r) < fn.apply(x[n-1])) {
				x[n] = x_r;
			} else if (fn.apply(x_r) < fn.apply(x[0])) {
				//4. Expansion
				x_e = x_o.add(x_r.subtract(x_o).scalarMultiply(this.gamma));
				if(fn.apply(x_e) < fn.apply(x_r)) {
					x[n] = x_e;
				} else {
					x[n] = x_r;
				}
			} else {
				//5. Contraction
				x_c = x_o.add(x[n].subtract(x_o).scalarMultiply(this.rho));
				if(fn.apply(x_c) < fn.apply(x[n])) {
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

}