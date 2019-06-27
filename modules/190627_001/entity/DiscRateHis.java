package entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="EAS_DISC_RATE_HIS")
public class DiscRateHis implements Serializable {
	
	private static final long serialVersionUID = -2532881893843426094L;
	private String baseYymm;
	private String intRateCd;
	private double baseDiscRate;
	private double applDiscRate;
	
	@Id
	public String getBaseYymm() {
		return baseYymm;
	}
	public void setBaseYymm(String baseYymm) {
		this.baseYymm = baseYymm;
	}
	@Id
	public String getIntRateCd() {
		return intRateCd;
	}
	public void setIntRateCd(String intRateCd) {
		this.intRateCd = intRateCd;
	}
	public double getBaseDiscRate() {
		return baseDiscRate;
	}
	public void setBaseDiscRate(double baseDiscRate) {
		this.baseDiscRate = baseDiscRate;
	}
	public double getApplDiscRate() {
		return applDiscRate;
	}
	public void setApplDiscRate(double applDiscRate) {
		this.applDiscRate = applDiscRate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(applDiscRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(baseDiscRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((baseYymm == null) ? 0 : baseYymm.hashCode());
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
		DiscRateHis other = (DiscRateHis) obj;
		if (Double.doubleToLongBits(applDiscRate) != Double.doubleToLongBits(other.applDiscRate))
			return false;
		if (Double.doubleToLongBits(baseDiscRate) != Double.doubleToLongBits(other.baseDiscRate))
			return false;
		if (baseYymm == null) {
			if (other.baseYymm != null)
				return false;
		} else if (!baseYymm.equals(other.baseYymm))
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
		return "DiscRateHis [baseYymm=" + baseYymm + ", intRateCd=" + intRateCd + ", baseDiscRate=" + baseDiscRate
				+ ", applDiscRate=" + applDiscRate + "]";
	}
	
}
