package model;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.optim.InitialGuess;
import org.apache.commons.math3.optim.MaxEval;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.SimpleBounds;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.nonlinear.scalar.MultivariateOptimizer;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.NelderMeadSimplex;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.SimplexOptimizer;
import org.apache.commons.math3.optim.univariate.BrentOptimizer;
import org.apache.commons.math3.optim.univariate.SearchInterval;
import org.apache.commons.math3.optim.univariate.UnivariateObjectiveFunction;
import org.apache.commons.math3.optim.univariate.UnivariateOptimizer;
import org.apache.commons.math3.optim.univariate.UnivariatePointValuePair;

public class HullWhite {
	public double alpha;
	public double sigma;
	public SmithWilson curve;
	public Map<Double, Map<Double, Double>> HullWhiteRS;
	
	public HullWhite(SmithWilson curve) {
		this.curve = curve;
	}

	public HullWhite(double alpha, double sigma, SmithWilson curve) {
		this.alpha = alpha;
		this.sigma = sigma;
		this.curve = curve;
	}
	
	public double B(double t, double T) {
		return (1-Math.exp(-alpha*(T-t)))/alpha;
	}
	
	public double A(double t, double T) {
		double term1 = curve.bond(T, 0)/curve.bond(t, 0);
		double term2 = B(t,T)*curve.forwardRates.get(t)-Math.pow(sigma,2)/(4*alpha)*Math.pow(B(t,T), 2)*(1-Math.exp(-2*alpha*t));
		return term1*Math.exp(term2);
	}
	
	public double sigma_p(double T, double S) {
		return sigma/alpha*(1-Math.exp(-alpha*(S-T)))*Math.sqrt((1-Math.exp(-2*alpha*T))/(2*alpha));
	}
	
	public double bond(double t, double T, double r) {
		return A(t, T)*Math.exp(-B(t, T)*r);
	}
	
	public double Jamshidian(double start, double tenor) {
		double[] c = curve.fswapCashFlows.get(start).get(tenor).toArray();
		double[] T = curve.fswapTerms.get(start).get(tenor).toArray();
		int n = T.length;
		UnivariateOptimizer optimizer = new BrentOptimizer(1e-4, 1e-6);
		UnivariatePointValuePair result = optimizer.optimize(
				new MaxEval(100),
				new UnivariateObjectiveFunction(r ->  {
					return Math.abs(IntStream.iterate(0, i->i+1)
							.limit(n)
							.mapToDouble(i -> c[i]*bond(start, T[i], r))
							.sum()-1);
				}),
				GoalType.MINIMIZE,
				new SearchInterval(0, 1)
			);
		return result.getPoint();
	}
	
	public double rsHullWhite(double maturity, double tenor) {
		double[] c = curve.fswapCashFlows.get(maturity).get(tenor).toArray();
		double[] T = curve.fswapTerms.get(maturity).get(tenor).toArray();
		double r = Jamshidian(maturity, tenor);
		int n = T.length;
		
		NormalDistribution norm = new NormalDistribution();
		
		return IntStream.iterate(0, i->i+1)
				.limit(n)
				.mapToDouble(i -> {
					double X = bond(maturity, T[i], r);
					double dPos = 1/sigma_p(maturity, T[i])*Math.log(curve.bondPrices.get(T[i])/curve.bondPrices.get(maturity)/X) + sigma_p(maturity, T[i])/2;
					double dNeg = dPos - sigma_p(maturity, T[i]);
					double terms = c[i]*(curve.bondPrices.get(T[i])*norm.cumulativeProbability(dPos) - X*curve.bondPrices.get(maturity)*norm.cumulativeProbability(dNeg));
					return terms;
				}).sum();
	}
	
	public void calcHullWhiteRS() {
		HullWhiteRS = new HashMap<>();
		Map<Double, Double> HullWhiteRS2;
		double T, S;
		
		for(int i=0; i<curve.m1; i++) {
			T = curve.swaptionMaturity.getEntry(i);
			HullWhiteRS2 = new HashMap<>();
			for(int j=0; j<curve.m2; j++) {
				S = curve.swapTenor.getEntry(j);
				HullWhiteRS2.put(S, rsHullWhite(T, S));
			}
			HullWhiteRS.put(T, HullWhiteRS2);
		}
	}
	
	private static class HullWhiteError implements MultivariateFunction {
		SmithWilson curve;
		
		public HullWhiteError(SmithWilson curve) {
			this.curve = curve;
		}
		
		@Override
		public double value(double[] point) {
			if(point[0] <= 0 || point[1] <=0 || point[0] > 1 || point[1] > 1)
				return 1e10;
			HullWhite hw = new HullWhite(point[0], point[1], curve);
			double err = 0;
			double T, S;
			hw.calcHullWhiteRS();
			
			for(int i=0; i<hw.curve.m1; i++) {
				T = hw.curve.swaptionMaturity.getEntry(i);
				for(int j=0; j<hw.curve.m2; j++) {
					S = hw.curve.swapTenor.getEntry(j);
					err += Math.pow(hw.HullWhiteRS.get(T).get(S) - hw.curve.blackRS.get(T).get(S), 2);
				}
			}
			System.out.println(Math.sqrt(err));
			return  Math.sqrt(err);
			
		}
	}
	
	public double[] calibration() {

		SimplexOptimizer optimizer = new SimplexOptimizer(1e-30, 1e-30);
		PointValuePair result = null;
		try {
			result = optimizer.optimize(
					new MaxEval(100),
					new ObjectiveFunction(new HullWhiteError(curve)),
					new NelderMeadSimplex(2),
					GoalType.MINIMIZE,
					new InitialGuess(new double[] {0.01, 0.006})
				);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result.getPoint();
		
		
	}
}
