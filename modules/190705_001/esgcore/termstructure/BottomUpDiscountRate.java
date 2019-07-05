package esgcore.termstructure;

public class BottomUpDiscountRate extends TermStructure {
	
	TermStructure rf;
	LiquidPremium lp;
	
	public BottomUpDiscountRate(TermStructure rf, LiquidPremium lp) {
		this.rf = rf;
		this.lp = lp;
	}
	
	public double bond(double t, int order) {
		if(order != 0)
			throw new RuntimeException("미분값 계산은 불가능합니다.");
		return Math.pow(1/(1+this.rf.spot(t)+this.lp.getLiquidPremium(t)), t);
	}

}
