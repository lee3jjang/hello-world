package esg;

public class CoveredBond {
	private Vector maturity;
	private Matrix kdbRates;
	private Matrix ktbRates;
	private Vector ktbRatesCurrent;
	
	private PolynomialRegression model;
	
	// 1 Day = 1 Row
	public CoveredBond(double[] maturity, double[][] kdbRates, double[][] ktbRates, double[] ktbRatesCurrent) {
		this.maturity = new Vector(maturity);
		this.kdbRates = new Matrix(kdbRates);
		this.ktbRates = new Matrix(ktbRates);
		this.ktbRatesCurrent = new Vector(ktbRatesCurrent);
	}
	
	public void calcBeta() {
		Vector inputRates = kdbRates.eleDivide(ktbRates).meanColumnVector().scalarAdd(-1.).eleMultiply(ktbRatesCurrent);
		int n = inputRates.getDimension();
		inputRates.setEntry(0, 0.);
		inputRates.setEntry(n-1, 0.);
		this.model = new PolynomialRegression(maturity.getData(), inputRates.getData(), 4);
	}
	
	public double[] getBeta() {
		return this.model.getBeta();
	}
	
	public double[] getLiquidPremium(double[] maturities) {
		return this.model.predict(maturities);
	}
	
}
