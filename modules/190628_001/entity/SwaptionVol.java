package entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="EAS_SWAPTION_VOL")
public class SwaptionVol implements Serializable {
	
	private static final long serialVersionUID = 1901726516496578752L;
	private String baseYymm;
	private int swaptionMaturity;
	private int swapTenor;
	private double vol;
	
	@Id
	public String getBaseYymm() {
		return baseYymm;
	}
	public void setBaseYymm(String baseYymm) {
		this.baseYymm = baseYymm;
	}
	@Id
	public int getSwaptionMaturity() {
		return swaptionMaturity;
	}
	public void setSwaptionMaturity(int swaptionMaturity) {
		this.swaptionMaturity = swaptionMaturity;
	}
	@Id
	public int getSwapTenor() {
		return swapTenor;
	}
	public void setSwapTenor(int swapTenor) {
		this.swapTenor = swapTenor;
	}
	public double getVol() {
		return vol;
	}
	public void setVol(double vol) {
		this.vol = vol;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((baseYymm == null) ? 0 : baseYymm.hashCode());
		result = prime * result + swapTenor;
		result = prime * result + swaptionMaturity;
		long temp;
		temp = Double.doubleToLongBits(vol);
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
		SwaptionVol other = (SwaptionVol) obj;
		if (baseYymm == null) {
			if (other.baseYymm != null)
				return false;
		} else if (!baseYymm.equals(other.baseYymm))
			return false;
		if (swapTenor != other.swapTenor)
			return false;
		if (swaptionMaturity != other.swaptionMaturity)
			return false;
		if (Double.doubleToLongBits(vol) != Double.doubleToLongBits(other.vol))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "SwaptionVol [baseYymm=" + baseYymm + ", swaptionMaturity=" + swaptionMaturity + ", swapTenor="
				+ swapTenor + ", vol=" + vol + "]";
	}
	

}
