package entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="EAS_REAL_WORLD_SCE")
public class RealWorldSce implements Serializable {
	
	private static final long serialVersionUID = 2707086917985638034L;
	private String baseYymm;
	private String irCurveId;
	private String irModelId;
	private int sceNo;
	private String time;
	private String matCd;
	private double rwSpotRate;
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
	public String getIrModelId() {
		return irModelId;
	}
	public void setIrModelId(String irModelId) {
		this.irModelId = irModelId;
	}
	@Id
	public int getSceNo() {
		return sceNo;
	}
	public void setSceNo(int sceNo) {
		this.sceNo = sceNo;
	}
	@Id
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Id
	public String getMatCd() {
		return matCd;
	}
	public void setMatCd(String matCd) {
		this.matCd = matCd;
	}
	public double getRwSpotRate() {
		return rwSpotRate;
	}
	public void setRwSpotRate(double rwSpotRate) {
		this.rwSpotRate = rwSpotRate;
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
		result = prime * result + ((irModelId == null) ? 0 : irModelId.hashCode());
		result = prime * result + ((lastModifiedBy == null) ? 0 : lastModifiedBy.hashCode());
		result = prime * result + ((lastUpdateDate == null) ? 0 : lastUpdateDate.hashCode());
		result = prime * result + ((matCd == null) ? 0 : matCd.hashCode());
		long temp;
		temp = Double.doubleToLongBits(rwSpotRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + sceNo;
		result = prime * result + ((time == null) ? 0 : time.hashCode());
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
		RealWorldSce other = (RealWorldSce) obj;
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
		if (irModelId == null) {
			if (other.irModelId != null)
				return false;
		} else if (!irModelId.equals(other.irModelId))
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
		if (matCd == null) {
			if (other.matCd != null)
				return false;
		} else if (!matCd.equals(other.matCd))
			return false;
		if (Double.doubleToLongBits(rwSpotRate) != Double.doubleToLongBits(other.rwSpotRate))
			return false;
		if (sceNo != other.sceNo)
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "RealWorldSce [baseYymm=" + baseYymm + ", irCurveId=" + irCurveId + ", irModelId=" + irModelId
				+ ", sceNo=" + sceNo + ", time=" + time + ", matCd=" + matCd + ", rwSpotRate=" + rwSpotRate
				+ ", lastModifiedBy=" + lastModifiedBy + ", lastUpdateDate=" + lastUpdateDate + "]";
	}

}
