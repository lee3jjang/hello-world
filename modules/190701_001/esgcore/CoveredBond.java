package esgcore;

public class CoveredBond {
	private Vector maturity;
	private Matrix kdbRates;
	private Matrix ktbRates;
	private Vector ktbRatesCurrent;
	
	private PolynomialRegression model;
	
	private final int lpMax = 20;
	
	// 1 Day = 1 Row
	public CoveredBond(double[] maturity, double[][] kdbRates, double[][] ktbRates, double[] ktbRatesCurrent) {
		this.maturity = new Vector(maturity);
		this.maturity.insertElement(0, 0.);
		this.kdbRates = new Matrix(kdbRates);
		this.ktbRates = new Matrix(ktbRates);
		this.ktbRatesCurrent = new Vector(ktbRatesCurrent);
	}
	
	public void calcBeta() {
		Vector inputRates = kdbRates.eleDivide(ktbRates).meanColumnVector().scalarAdd(-1.).eleMultiply(ktbRatesCurrent);
		int n = inputRates.getDimension();
		inputRates.insertElement(0, 0.);
		inputRates.setEntry(n, 0.);
		this.model = new PolynomialRegression(maturity.getData(), inputRates.getData(), 4);
	}
	
	public double[] getBeta() {
		return this.model.getBeta();
	}
	
	public double[] getLiquidPremium(double[] maturities) {
		int n = maturities.length;
		double[] values = new double[n];
		for(int i=0; i<n; i++) {
			if(maturities[i] <= lpMax)
				values[i] = this.model.predict(maturities[i]);
			else
				values[i] = 0.;
		}
		return values;
			
	}
	
}
