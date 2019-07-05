package entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="EAS_USER_INDI_CRD_GRD_PD")
public class IndiCrdGrdPdUd implements Serializable {
	
	private static final long serialVersionUID = -8676382131042349076L;
	private String baseYymm;
	private String cbGrdCd;
	private double kcbPd;
	private double nicePd;
	private double y1;
	private double y2;
	private double y3;
	private double y4;
	private double y5;
	private double y6;
	private double y7;
	private double y8;
	private double y9;
	private double y10;
	private double y11;
	private double y12;
	private double y13;
	private double y14;
	private double y15;
	private double y16;
	private double y17;
	private double y18;
	private double y19;
	private double y20;
	private double y21;
	private double y22;
	private double y23;
	private double y24;
	private double y25;
	private double y26;
	private double y27;
	private double y28;
	private double y29;
	private double y30;
	
	@Id
	public String getBaseYymm() {
		return baseYymm;
	}
	public void setBaseYymm(String baseYymm) {
		this.baseYymm = baseYymm;
	}
	@Id
	public String getCbGrdCd() {
		return cbGrdCd;
	}
	public void setCbGrdCd(String cbGrdCd) {
		this.cbGrdCd = cbGrdCd;
	}
	public double getKcbPd() {
		return kcbPd;
	}
	public void setKcbPd(double kcbPd) {
		this.kcbPd = kcbPd;
	}
	public double getNicePd() {
		return nicePd;
	}
	public void setNicePd(double nicePd) {
		this.nicePd = nicePd;
	}
	public double getY1() {
		return y1;
	}
	public void setY1(double y1) {
		this.y1 = y1;
	}
	public double getY2() {
		return y2;
	}
	public void setY2(double y2) {
		this.y2 = y2;
	}
	public double getY3() {
		return y3;
	}
	public void setY3(double y3) {
		this.y3 = y3;
	}
	public double getY4() {
		return y4;
	}
	public void setY4(double y4) {
		this.y4 = y4;
	}
	public double getY5() {
		return y5;
	}
	public void setY5(double y5) {
		this.y5 = y5;
	}
	public double getY6() {
		return y6;
	}
	public void setY6(double y6) {
		this.y6 = y6;
	}
	public double getY7() {
		return y7;
	}
	public void setY7(double y7) {
		this.y7 = y7;
	}
	public double getY8() {
		return y8;
	}
	public void setY8(double y8) {
		this.y8 = y8;
	}
	public double getY9() {
		return y9;
	}
	public void setY9(double y9) {
		this.y9 = y9;
	}
	public double getY10() {
		return y10;
	}
	public void setY10(double y10) {
		this.y10 = y10;
	}
	public double getY11() {
		return y11;
	}
	public void setY11(double y11) {
		this.y11 = y11;
	}
	public double getY12() {
		return y12;
	}
	public void setY12(double y12) {
		this.y12 = y12;
	}
	public double getY13() {
		return y13;
	}
	public void setY13(double y13) {
		this.y13 = y13;
	}
	public double getY14() {
		return y14;
	}
	public void setY14(double y14) {
		this.y14 = y14;
	}
	public double getY15() {
		return y15;
	}
	public void setY15(double y15) {
		this.y15 = y15;
	}
	public double getY16() {
		return y16;
	}
	public void setY16(double y16) {
		this.y16 = y16;
	}
	public double getY17() {
		return y17;
	}
	public void setY17(double y17) {
		this.y17 = y17;
	}
	public double getY18() {
		return y18;
	}
	public void setY18(double y18) {
		this.y18 = y18;
	}
	public double getY19() {
		return y19;
	}
	public void setY19(double y19) {
		this.y19 = y19;
	}
	public double getY20() {
		return y20;
	}
	public void setY20(double y20) {
		this.y20 = y20;
	}
	public double getY21() {
		return y21;
	}
	public void setY21(double y21) {
		this.y21 = y21;
	}
	public double getY22() {
		return y22;
	}
	public void setY22(double y22) {
		this.y22 = y22;
	}
	public double getY23() {
		return y23;
	}
	public void setY23(double y23) {
		this.y23 = y23;
	}
	public double getY24() {
		return y24;
	}
	public void setY24(double y24) {
		this.y24 = y24;
	}
	public double getY25() {
		return y25;
	}
	public void setY25(double y25) {
		this.y25 = y25;
	}
	public double getY26() {
		return y26;
	}
	public void setY26(double y26) {
		this.y26 = y26;
	}
	public double getY27() {
		return y27;
	}
	public void setY27(double y27) {
		this.y27 = y27;
	}
	public double getY28() {
		return y28;
	}
	public void setY28(double y28) {
		this.y28 = y28;
	}
	public double getY29() {
		return y29;
	}
	public void setY29(double y29) {
		this.y29 = y29;
	}
	public double getY30() {
		return y30;
	}
	public void setY30(double y30) {
		this.y30 = y30;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((baseYymm == null) ? 0 : baseYymm.hashCode());
		result = prime * result + ((cbGrdCd == null) ? 0 : cbGrdCd.hashCode());
		long temp;
		temp = Double.doubleToLongBits(kcbPd);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(nicePd);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y1);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y10);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y11);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y12);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y13);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y14);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y15);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y16);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y17);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y18);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y19);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y2);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y20);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y21);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y22);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y23);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y24);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y25);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y26);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y27);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y28);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y29);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y3);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y30);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y4);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y5);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y6);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y7);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y8);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y9);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IndiCrdGrdPdUd other = (IndiCrdGrdPdUd) obj;
		if (baseYymm == null) {
			if (other.baseYymm != null)
				return false;
		} else if (!baseYymm.equals(other.baseYymm))
			return false;
		if (cbGrdCd == null) {
			if (other.cbGrdCd != null)
				return false;
		} else if (!cbGrdCd.equals(other.cbGrdCd))
			return false;
		if (Double.doubleToLongBits(kcbPd) != Double.doubleToLongBits(other.kcbPd))
			return false;
		if (Double.doubleToLongBits(nicePd) != Double.doubleToLongBits(other.nicePd))
			return false;
		if (Double.doubleToLongBits(y1) != Double.doubleToLongBits(other.y1))
			return false;
		if (Double.doubleToLongBits(y10) != Double.doubleToLongBits(other.y10))
			return false;
		if (Double.doubleToLongBits(y11) != Double.doubleToLongBits(other.y11))
			return false;
		if (Double.doubleToLongBits(y12) != Double.doubleToLongBits(other.y12))
			return false;
		if (Double.doubleToLongBits(y13) != Double.doubleToLongBits(other.y13))
			return false;
		if (Double.doubleToLongBits(y14) != Double.doubleToLongBits(other.y14))
			return false;
		if (Double.doubleToLongBits(y15) != Double.doubleToLongBits(other.y15))
			return false;
		if (Double.doubleToLongBits(y16) != Double.doubleToLongBits(other.y16))
			return false;
		if (Double.doubleToLongBits(y17) != Double.doubleToLongBits(other.y17))
			return false;
		if (Double.doubleToLongBits(y18) != Double.doubleToLongBits(other.y18))
			return false;
		if (Double.doubleToLongBits(y19) != Double.doubleToLongBits(other.y19))
			return false;
		if (Double.doubleToLongBits(y2) != Double.doubleToLongBits(other.y2))
			return false;
		if (Double.doubleToLongBits(y20) != Double.doubleToLongBits(other.y20))
			return false;
		if (Double.doubleToLongBits(y21) != Double.doubleToLongBits(other.y21))
			return false;
		if (Double.doubleToLongBits(y22) != Double.doubleToLongBits(other.y22))
			return false;
		if (Double.doubleToLongBits(y23) != Double.doubleToLongBits(other.y23))
			return false;
		if (Double.doubleToLongBits(y24) != Double.doubleToLongBits(other.y24))
			return false;
		if (Double.doubleToLongBits(y25) != Double.doubleToLongBits(other.y25))
			return false;
		if (Double.doubleToLongBits(y26) != Double.doubleToLongBits(other.y26))
			return false;
		if (Double.doubleToLongBits(y27) != Double.doubleToLongBits(other.y27))
			return false;
		if (Double.doubleToLongBits(y28) != Double.doubleToLongBits(other.y28))
			return false;
		if (Double.doubleToLongBits(y29) != Double.doubleToLongBits(other.y29))
			return false;
		if (Double.doubleToLongBits(y3) != Double.doubleToLongBits(other.y3))
			return false;
		if (Double.doubleToLongBits(y30) != Double.doubleToLongBits(other.y30))
			return false;
		if (Double.doubleToLongBits(y4) != Double.doubleToLongBits(other.y4))
			return false;
		if (Double.doubleToLongBits(y5) != Double.doubleToLongBits(other.y5))
			return false;
		if (Double.doubleToLongBits(y6) != Double.doubleToLongBits(other.y6))
			return false;
		if (Double.doubleToLongBits(y7) != Double.doubleToLongBits(other.y7))
			return false;
		if (Double.doubleToLongBits(y8) != Double.doubleToLongBits(other.y8))
			return false;
		if (Double.doubleToLongBits(y9) != Double.doubleToLongBits(other.y9))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "IndiCrdGrdPdUd [baseYymm=" + baseYymm + ", cbGrdCd=" + cbGrdCd + ", kcbPd=" + kcbPd + ", nicePd="
				+ nicePd + ", y1=" + y1 + ", y2=" + y2 + ", y3=" + y3 + ", y4=" + y4 + ", y5=" + y5 + ", y6=" + y6
				+ ", y7=" + y7 + ", y8=" + y8 + ", y9=" + y9 + ", y10=" + y10 + ", y11=" + y11 + ", y12=" + y12
				+ ", y13=" + y13 + ", y14=" + y14 + ", y15=" + y15 + ", y16=" + y16 + ", y17=" + y17 + ", y18=" + y18
				+ ", y19=" + y19 + ", y20=" + y20 + ", y21=" + y21 + ", y22=" + y22 + ", y23=" + y23 + ", y24=" + y24
				+ ", y25=" + y25 + ", y26=" + y26 + ", y27=" + y27 + ", y28=" + y28 + ", y29=" + y29 + ", y30=" + y30
				+ "]";
	}
	
	

}
