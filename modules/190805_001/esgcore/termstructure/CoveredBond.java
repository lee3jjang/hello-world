package esgcore.termstructure;

import esgcore.linalg.Matrix;
import esgcore.linalg.Vector;
import esgcore.regression.PolynomialRegression;

public class CoveredBond extends LiquidPremium {
	
	private Vector maturity;
	private Matrix kdbRates;
	private Matrix ktbRates;
	private Vector ktbRatesCurrent;
	
	private PolynomialRegression model;
	
	private final int lpMax = 20;
	
	// 1 Day = 1 Row
	public CoveredBond(Vector maturity, Matrix kdbRates, Matrix ktbRates, Vector ktbRatesCurrent) {
		this.maturity = maturity.copy();
		this.maturity.insertElement(0, 0.);
		this.kdbRates = kdbRates.copy();
		this.ktbRates = ktbRates.copy();
		this.ktbRatesCurrent = ktbRatesCurrent.copy();
		calculateBeta();
	}
	
	public void calculateBeta() {
		Vector inputRates = kdbRates.eleDivide(ktbRates).meanColumnVector().scalarAdd(-1.).eleMultiply(ktbRatesCurrent);
		int n = inputRates.getDimension();
		inputRates.insertElement(0, 0.);
		inputRates.setEntry(n, 0.);
		this.model = new PolynomialRegression(maturity, inputRates, 4);
	}
	
	public Vector getBeta() {
		return this.model.getBeta();
	}
	
	public double getLiquidPremium(double t) {
		if(t < this.lpMax)
			return this.model.predict(t);
		else
			return 0.;
	}
	
}
