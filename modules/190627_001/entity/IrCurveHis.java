package entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="EAS_IR_CURVE_HIS")
public class IrCurveHis implements Serializable {

	private static final long serialVersionUID = 1882122549403477406L;
	private String baseDate;
	private String irCurveId;
	private String matCd;
	private double intRate;
	
	@Id
	public String getBaseDate() {
		return baseDate;
	}
	public void setBaseDate(String baseDate) {
		this.baseDate = baseDate;
	}
	@Id
	public String getIrCurveId() {
		return irCurveId;
	}
	public void setIrCurveId(String irCurveId) {
		this.irCurveId = irCurveId;
	}
	@Id
	public String getMatCd() {
		return matCd;
	}
	public void setMatCd(String matCd) {
		this.matCd = matCd;
	}
	public double getIntRate() {
		return intRate;
	}
	public void setIntRate(double intRate) {
		this.intRate = intRate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((baseDate == null) ? 0 : baseDate.hashCode());
		long temp;
		temp = Double.doubleToLongBits(intRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((irCurveId == null) ? 0 : irCurveId.hashCode());
		result = prime * result + ((matCd == null) ? 0 : matCd.hashCode());
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
		IrCurveHis other = (IrCurveHis) obj;
		if (baseDate == null) {
			if (other.baseDate != null)
				return false;
		} else if (!baseDate.equals(other.baseDate))
			return false;
		if (Double.doubleToLongBits(intRate) != Double.doubleToLongBits(other.intRate))
			return false;
		if (irCurveId == null) {
			if (other.irCurveId != null)
				return false;
		} else if (!irCurveId.equals(other.irCurveId))
			return false;
		if (matCd == null) {
			if (other.matCd != null)
				return false;
		} else if (!matCd.equals(other.matCd))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "IrCurveHis [baseDate=" + baseDate + ", irCurveId=" + irCurveId + ", matCd=" + matCd + ", intRate="
				+ intRate + "]";
	}
	
	

}
