package optim;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.analysis.UnivariateFunction;

public class Newton {
	
	public List<Double> x;
	
	public Newton() {}
	
	public void solve(double x0, UnivariateFunction derivFun, UnivariateFunction secondDerivFun) {
		double dx;
		double derivEval;
		double secondDerivEval;
		
		double tol = 1e-10;
		int maxIter = 1000;
		
		x = new ArrayList<>();
		
		x.add(x0);
		for(int i=0; i<maxIter; i++) {
			derivEval = derivFun.value(x.get(i));
			secondDerivEval = secondDerivFun.value(x.get(i));
			if(secondDerivEval < tol) {
				break;
			}
			
			dx = -derivEval/secondDerivEval;
			x.add(i+1, x.get(i) + dx);
		}
	}

}
