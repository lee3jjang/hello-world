package entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="EAS_DISC_RATE_CALC_SETTING")
public class DiscRateCalcSetting implements Serializable {
	
	private static final long serialVersionUID = 926936173304439919L;
	private String intRateCd;
	private String discRateCalcTyp;
	
	@Id
	public String getIntRateCd() {
		return intRateCd;
	}
	public void setIntRateCd(String intRateCd) {
		this.intRateCd = intRateCd;
	}
	public String getDiscRateCalcTyp() {
		return discRateCalcTyp;
	}
	public void setDiscRateCalcTyp(String discRateCalcTyp) {
		this.discRateCalcTyp = discRateCalcTyp;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((discRateCalcTyp == null) ? 0 : discRateCalcTyp.hashCode());
		result = prime * result + ((intRateCd == null) ? 0 : intRateCd.hashCode());
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
		DiscRateCalcSetting other = (DiscRateCalcSetting) obj;
		if (discRateCalcTyp == null) {
			if (other.discRateCalcTyp != null)
				return false;
		} else if (!discRateCalcTyp.equals(other.discRateCalcTyp))
			return false;
		if (intRateCd == null) {
			if (other.intRateCd != null)
				return false;
		} else if (!intRateCd.equals(other.intRateCd))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "DiscRateCalcSetting [intRateCd=" + intRateCd + ", discRateCalcTyp=" + discRateCalcTyp + "]";
	}
	
}
