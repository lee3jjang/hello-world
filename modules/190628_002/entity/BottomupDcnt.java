package entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="EAS_BOTTOMUP_DCNT")
public class BottomupDcnt implements Serializable {
	private static final long serialVersionUID = 5046999269015410632L;
	private String baseYymm;
	private String irCurveId;
	private String matCd;
	private double riskFreeSpotRate;
	private double riskFreeFwdRate;
	private double liqPrem;
	private double riskAdjSpotRate;
	private double riskAdjFwdRate;
	private String lastModifiedBy;
	private String lastUpdateDate;
	
	@Id
	public String getBaseYymm() {
		return baseYymm;
	}
	public void setBaseYymm(String baseYymm) {
		this.baseYymm = baseYymm;
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
	public double getRiskFreeSpotRate() {
		return riskFreeSpotRate;
	}
	public void setRiskFreeSpotRate(double riskFreeSpotRate) {
		this.riskFreeSpotRate = riskFreeSpotRate;
	}
	public double getRiskFreeFwdRate() {
		return riskFreeFwdRate;
	}
	public void setRiskFreeFwdRate(double riskFreeFwdRate) {
		this.riskFreeFwdRate = riskFreeFwdRate;
	}
	public double getLiqPrem() {
		return liqPrem;
	}
	public void setLiqPrem(double liqPrem) {
		this.liqPrem = liqPrem;
	}
	public double getRiskAdjSpotRate() {
		return riskAdjSpotRate;
	}
	public void setRiskAdjSpotRate(double riskAdjSpotRate) {
		this.riskAdjSpotRate = riskAdjSpotRate;
	}
	public double getRiskAdjFwdRate() {
		return riskAdjFwdRate;
	}
	public void setRiskAdjFwdRate(double riskAdjFwdRate) {
		this.riskAdjFwdRate = riskAdjFwdRate;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((baseYymm == null) ? 0 : baseYymm.hashCode());
		result = prime * result + ((irCurveId == null) ? 0 : irCurveId.hashCode());
		result = prime * result + ((lastModifiedBy == null) ? 0 : lastModifiedBy.hashCode());
		result = prime * result + ((lastUpdateDate == null) ? 0 : lastUpdateDate.hashCode());
		long temp;
		temp = Double.doubleToLongBits(liqPrem);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((matCd == null) ? 0 : matCd.hashCode());
		temp = Double.doubleToLongBits(riskAdjFwdRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(riskAdjSpotRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(riskFreeFwdRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(riskFreeSpotRate);
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
		BottomupDcnt other = (BottomupDcnt) obj;
		if (baseYymm == null) {
			if (other.baseYymm != null)
				return false;
		} else if (!baseYymm.equals(other.baseYymm))
			return false;
		if (irCurveId == null) {
			if (other.irCurveId != null)
				return false;
		} else if (!irCurveId.equals(other.irCurveId))
			return false;
		if (lastModifiedBy == null) {
			if (other.lastModifiedBy != null)
				return false;
		} else if (!lastModifiedBy.equals(other.lastModifiedBy))
			return false;
		if (lastUpdateDate == null) {
			if (other.lastUpdateDate != null)
				return false;
		} else if (!lastUpdateDate.equals(other.lastUpdateDate))
			return false;
		if (Double.doubleToLongBits(liqPrem) != Double.doubleToLongBits(other.liqPrem))
			return false;
		if (matCd == null) {
			if (other.matCd != null)
				return false;
		} else if (!matCd.equals(other.matCd))
			return false;
		if (Double.doubleToLongBits(riskAdjFwdRate) != Double.doubleToLongBits(other.riskAdjFwdRate))
			return false;
		if (Double.doubleToLongBits(riskAdjSpotRate) != Double.doubleToLongBits(other.riskAdjSpotRate))
			return false;
		if (Double.doubleToLongBits(riskFreeFwdRate) != Double.doubleToLongBits(other.riskFreeFwdRate))
			return false;
		if (Double.doubleToLongBits(riskFreeSpotRate) != Double.doubleToLongBits(other.riskFreeSpotRate))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "BottomupDcnt [baseYymm=" + baseYymm + ", irCurveId=" + irCurveId + ", matCd=" + matCd
				+ ", riskFreeSpotRate=" + riskFreeSpotRate + ", riskFreeFwdRate=" + riskFreeFwdRate + ", liqPrem="
				+ liqPrem + ", riskAdjSpotRate=" + riskAdjSpotRate + ", riskAdjFwdRate=" + riskAdjFwdRate
				+ ", lastModifiedBy=" + lastModifiedBy + ", lastUpdateDate=" + lastUpdateDate + "]";
	}
	
	
	

}
