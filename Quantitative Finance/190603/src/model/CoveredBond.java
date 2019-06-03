package model;

import org.apache.commons.math3.analysis.function.Power;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

public class CoveredBond {
	public final double tau = 0.25;
	
	public RealVector baseDate;
	public RealVector term;
	public RealMatrix ktbRate;
	public RealMatrix kdbRate;
	public final int n;
	public final int m;
	public RealVector beta;
	
	public CoveredBond(double[] term, double[][] ktbRate, double[][] kdbRate) {
		
		this.term = new ArrayRealVector(term);
		this.ktbRate = new Array2DRowRealMatrix(ktbRate);
		this.kdbRate = new Array2DRowRealMatrix(kdbRate);
		this.n = ktbRate.length;
		this.m = term.length;
		
		this.calcBeta();
		
	}
	
	public CoveredBond(RealVector term, RealMatrix ktbRate, RealMatrix kdbRate) {
		
		this.term = term;
		this.ktbRate = ktbRate;
		this.kdbRate = kdbRate;
		this.n = ktbRate.getRowDimension();
		this.m = term.getDimension();
		
		this.calcBeta();
		
	}
	
	private void calcBeta() {
		
		//1. 일별, 만기별 스프레드 계산
		RealMatrix spread = MatrixUtils.createRealMatrix(n, m);
		for(int i=0; i<m; i++) {
			spread.setColumnVector(i, this.kdbRate.getColumnVector(i).ebeDivide(this.ktbRate.getColumnVector(i)).mapAdd(-1.));
		}

		//2. 만기별 평균 스프레드 계산
		SummaryStatistics stats;
		RealVector spreadAvg = new ArrayRealVector(new double[m]);
		
		for(int i=0; i<m; i++) {
			stats = new SummaryStatistics();
			for(int j=0; j<n; j++) {
				stats.addValue(spread.getColumn(i)[j]);
			}
			spreadAvg.setEntry(i, stats.getMean());
		}
		spreadAvg = ktbRate.getRowVector(n-1).ebeMultiply(spreadAvg);
		spreadAvg.setEntry(0, 0);
		spreadAvg.setEntry(m-1, 0);
		
		//3. 베타 계산
		RealMatrix X = MatrixUtils.createRealMatrix(m, 5);
		for(int i=0; i<5; i++) {
			X.setColumnVector(i, this.term.map(new Power(i)));
		}
		this.beta = MatrixUtils.inverse(X.transpose().multiply(X)).multiply(X.transpose()).operate(spreadAvg);

	}
	
	public double liqPrem(double t) {
		
		if(t <= 0 | t >= term.getEntry(m-1)) {
			return 0;
		} else {
			RealVector x = new ArrayRealVector(new double[] {1, t, Math.pow(t, 2), Math.pow(t, 3), Math.pow(t, 4)});
			return beta.dotProduct(x);	
		}
		
	}
	
	public RealVector liqPrem(double[] t) {
		return liqPrem(new ArrayRealVector(t));
	}
	
	public RealVector liqPrem(RealVector t) {
		return t.map(s -> liqPrem(s));
	}
}
