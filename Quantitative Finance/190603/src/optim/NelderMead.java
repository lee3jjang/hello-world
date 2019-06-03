package optim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

public class NelderMead {
	
	MultivariateFunction objFun;
	Comparator<RealVector> objFunComparator;
	
	
	public NelderMead(MultivariateFunction objFun) {
		this.objFun = objFun;
		
		// 최소값이 맨 앞으로 가게 정렬
		objFunComparator = new Comparator<RealVector>() {
			@Override
			public int compare(RealVector u, RealVector v) {
				return (int) (objFun.value(u.toArray()) - objFun.value(v.toArray()));
			}
		};
	}
	
	
	public void solve(double[][] x0) {
		
		List<RealVector> initPts = new ArrayList<>();
		List<RealVector> nextPts = new ArrayList<>();
		List<RealVector> pts;
		
		for(int i=0; i<3; i++) {
			initPts.add(new ArrayRealVector(x0[i]));
		}
		Collections.sort(initPts, objFunComparator);
		for(int i=0; i<3; i++) {
			nextPts.add(initPts.get(i));
		}
		
		
		for(int j=0; j<100; j++) {
			RealVector B = nextPts.get(0);
			RealVector G = nextPts.get(1);
			RealVector W = nextPts.get(2);
			RealVector M = B.add(G).mapDivide(2);
			RealVector R = M.add(M).subtract(W);
			RealVector E = R.add(R).subtract(M);
			RealVector C1 = M.add(W).mapDivide(2);
			RealVector C2 = M.add(R).mapDivide(2);
			RealVector S = B.add(W).mapDivide(2);
			
			pts = new ArrayList<>();
			pts.add(B);
			pts.add(G);
			pts.add(W);
			pts.add(M);
			pts.add(R);
			pts.add(E);
			pts.add(C1);
			pts.add(C2);
			pts.add(S);
			
			Collections.sort(pts, objFunComparator);
			System.out.println(pts);
			nextPts = new ArrayList<>();
			for(int i=0; i<3; i++) {
				nextPts.add(pts.get(i));
			}
			
			//System.out.println(nextPts);
		}

		
	}
	
}
