package esgcore.termstructure;

import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

import esgcore.linalg.Matrix;
import esgcore.linalg.Vector;
import esgcore.optim.GoldenSectionSearch;

/**
 * @author 11700205
 *
 */
public class SmithWilson extends TermStructure {
	
	private Vector term;
	private Vector rate;
	private double alpha;
	private double ufr;
	private double llp;
	private Vector zeta;
	
	private Vector swaptionMaturity;
	private Vector swapTenor;
	private Matrix blackVol;
	private Matrix blackRS;
	
	private Map<Double, Map<Double, Vector>> fswapTerms;
	private Map<Double, Map<Double, Vector>> fswapCashFlows;
	
	
	// Initialization
	private SmithWilson(Vector term, Vector rate, double alpha, double ufr, double llp) {
		this.ufr = ufr;
		this.llp = llp;
		this.term = term.copy();
		this.rate = rate.copy();
		this.setAlpha(alpha);
	}
	
	public SmithWilson(Vector term, Vector rate, double ufr, double llp) {
		this.ufr = ufr;
		this.llp = llp;
		this.term = term.copy();
		this.rate = rate.copy();
		this.setAlpha(this.calculateAlpha());
	}
	
	// Wilson Function
	private double Wilson(double t, double u, int order) {
		double wilson;
		if (order==0) {
			wilson = Math.exp(-ufr*(t+u)) * (alpha*Math.min(t, u) - Math.exp(-alpha*Math.max(t, u))*Math.sinh(alpha*Math.min(t, u)));
		} else if (order==1) {
			if (t<u)
				wilson = Math.exp(-ufr*t-(alpha+ufr)*u)*(ufr*Math.sinh(alpha*t)-alpha*Math.cosh(alpha*t)-alpha*(ufr*t-1)*Math.exp(alpha*u));
			else
				wilson = Math.exp(-ufr*u-(alpha+ufr)*t)*((alpha+ufr)*Math.sinh(alpha*u)-alpha*ufr*u*Math.exp(alpha*t));		
		} else if (order==2) {
			if (t<u)
				wilson = Math.exp(-ufr*t-(alpha+ufr)*u)*(-(alpha*alpha+ufr*ufr)*Math.sinh(alpha*t)+2*alpha*ufr*Math.cosh(alpha*t)+alpha*ufr*(ufr*t-2)*Math.exp(alpha*u));
			else
				wilson = Math.exp(-ufr*u-(alpha+ufr)*t)*(alpha*ufr*ufr*u*Math.exp(alpha*t)-(alpha+ufr)*(alpha+ufr)*Math.sinh(alpha*u));
		} else {
			throw new RuntimeException("유효하지 않은 차수입니다.");
		}
		return wilson;
	}
	
	private Vector Wilson(double t, Vector columns, int order) {
		return columns.map(x -> Wilson(t, x, order));
	}

	private Matrix Wilson(Vector rows, Vector columns, int order) {
		int n = rows.getDimension();
		Vector[] v = new Vector[n];
		for(int i=0; i<n; i++)
			v[i] = Wilson(rows.getEntry(i), columns, order);
		return Matrix.concatenateRowVector(v);
	}
	
	// Calculate Zeta
	private Vector calculateZeta() {
		Matrix InvW = this.Wilson(this.term, this.term, 0).inverse();
		Vector m_mu = this.rate.map(x -> 1/(1+x))
								.binaryMap(this.term, (x, y) -> Math.pow(x, y))
								.add(this.term.map(x -> -Math.exp(-this.ufr*x)));
		return InvW.operate(m_mu);
	}
	
	// Calculate Alpha
	// ISSUE : 오차 1bp의 기준이 연단위인지 연속단위인지? (시스템은 연속단위 기준으로 오차 계산)
	public double calculateAlpha() {
		GoldenSectionSearch optimizer = new GoldenSectionSearch();
		UnaryOperator<Double> fn = a -> {
			SmithWilson sw = new SmithWilson(this.term, this.rate, a.doubleValue(), this.ufr, this.llp);
			// log(1+0.045) - 60년 시점, 연속단위 순간선도금리
			return Math.abs(0.0001 - Math.abs(sw.forward(Math.max(llp+40, 60))-ufr));
//			return Math.abs(0.0001 - Math.abs(Math.exp(sw.forward(Math.max(llp+40, 60), 0))-Math.exp(ufr)));
		};
		return Math.round(optimizer.optimize(fn, 0.001, 1)*1e6)/1e6;
	}	
	
	// Bond Price
	public double bond(double t, int order) {
		double terms1 = Math.pow(-ufr, order)*Math.exp(-ufr*t);
		double terms2 = Wilson(t, term, order).dotProduct(zeta);
		return terms1+terms2;
	}
	
	
	/* Black Swaption */
	
	// Set Swaption Volatility
	public void setBlackVol(Vector swaptionMaturity, Vector swapTenor, Matrix blackVol) {
		this.swaptionMaturity = swaptionMaturity.copy();
		this.swapTenor = swapTenor.copy();
		this.blackVol = blackVol.copy();
		this.blackRS = this.calculateBlackRS();
		generateFswapCashFlow();
	}
	
	// Calculate Black Receiver Swaption Price
	private Matrix calculateBlackRS() {
		int m = swaptionMaturity.getDimension();
		int n = swapTenor.getDimension();
		blackRS = Matrix.createZeroMatrix(m, n);
		for(int i=0; i<m; i++)
			for(int j=0; j<n; j++)
				blackRS.setEntry(i, j, rsBlack(swaptionMaturity.getEntry(i), swapTenor.getEntry(j), blackVol.getEntry(i, j)));
		return blackRS;
	}
		
	// Generate Forward Swap Cash Flow
	// f : (Start Date, Swap Tenor) -> Forward Swap Cash Flow
	private void generateFswapCashFlow() {
		Map<Double, Vector> temp, temp2;
		this.fswapTerms = new HashMap<>();
		this.fswapCashFlows = new HashMap<>();
		int m = this.swaptionMaturity.getDimension();
		int n = this.swapTenor.getDimension();
		for(int i=0; i<m; i++) {
			temp = new HashMap<>();
			for(int j=0; j<n; j++)
				temp.put(this.swapTenor.getEntry(j), fswapCashFlow(this.swaptionMaturity.getEntry(i), this.swapTenor.getEntry(j)));
			this.fswapCashFlows.put(this.swaptionMaturity.getEntry(i), temp);
			
			temp2 = new HashMap<>();
			for(int j=0; j<n; j++)
				temp2.put(this.swapTenor.getEntry(j), fswapTerms(this.swaptionMaturity.getEntry(i), this.swaptionMaturity.getEntry(j)));
			this.fswapTerms.put(this.swaptionMaturity.getEntry(i), temp2);
		}
	}
	
	
	/* Getter, Setter */

	public Vector getTerm() {
		return term;
	}

	public void setTerm(Vector term) {
		this.term = term;
	}

	public Vector getRate() {
		return rate;
	}

	public void setRate(Vector rate) {
		this.rate = rate;
	}

	public double getUfr() {
		return ufr;
	}

	public void setUfr(double ufr) {
		this.ufr = ufr;
	}

	public double getLlp() {
		return llp;
	}

	public void setLlp(double llp) {
		this.llp = llp;
	}

	public Vector getZeta() {
		return zeta;
	}

	public double getAlpha() {
		return alpha;
	}
	
	public void setAlpha(double alpha) {
		this.alpha = alpha;
		this.zeta = calculateZeta();
	}

	public Map<Double, Map<Double, Vector>> getFswapTerms() {
		return fswapTerms;
	}

	public Map<Double, Map<Double, Vector>> getFswapCashFlows() {
		return fswapCashFlows;
	}

	public Vector getSwaptionMaturity() {
		return swaptionMaturity;
	}

	public Vector getSwapTenor() {
		return swapTenor;
	}

	public Matrix getBlackRS() {
		return blackRS;
	}
	
}