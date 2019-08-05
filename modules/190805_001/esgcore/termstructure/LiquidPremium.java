package esgcore.termstructure;

import esgcore.linalg.Vector;

public abstract class LiquidPremium {
	
	public abstract double getLiquidPremium(double t);
	
	public Vector getLiquidPremium(Vector maturities) {
		return maturities.map(x -> getLiquidPremium(x));
	}

}
