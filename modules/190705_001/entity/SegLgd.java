package entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="EAS_SEG_LGD")
public class SegLgd implements Serializable {
	
	private static final long serialVersionUID = 9037820507789312031L;
	private String baseYymm;
	private String segId;
	private double lgd;
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
	public String getSegId() {
		return segId;
	}
	public void setSegId(String segId) {
		this.segId = segId;
	}
	public double getLgd() {
		return lgd;
	}
	public void setLgd(double lgd) {
		this.lgd = lgd;
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
		result = prime * result + ((lastModifiedBy == null) ? 0 : lastModifiedBy.hashCode());
		result = prime * result + ((lastUpdateDate == null) ? 0 : lastUpdateDate.hashCode());
		long temp;
		temp = Double.doubleToLongBits(lgd);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((segId == null) ? 0 : segId.hashCode());
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
		SegLgd other = (SegLgd) obj;
		if (baseYymm == null) {
			if (other.baseYymm != null)
				return false;
		} else if (!baseYymm.equals(other.baseYymm))
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
		if (Double.doubleToLongBits(lgd) != Double.doubleToLongBits(other.lgd))
			return false;
		if (segId == null) {
			if (other.segId != null)
				return false;
		} else if (!segId.equals(other.segId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "SegLgd [baseYymm=" + baseYymm + ", segId=" + segId + ", lgd=" + lgd + ", lastModifiedBy="
				+ lastModifiedBy + ", lastUpdateDate=" + lastUpdateDate + "]";
	}
	
}
