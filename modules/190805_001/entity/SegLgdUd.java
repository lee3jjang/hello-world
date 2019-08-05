package entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="EAS_USER_SEG_LGD")
public class SegLgdUd implements Serializable {
	
	private static final long serialVersionUID = 863460083007745947L;
	private String baseYymm;
	private String segId;
	private double lgd;
	
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((baseYymm == null) ? 0 : baseYymm.hashCode());
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
		SegLgdUd other = (SegLgdUd) obj;
		if (baseYymm == null) {
			if (other.baseYymm != null)
				return false;
		} else if (!baseYymm.equals(other.baseYymm))
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
		return "SegLgdUd [baseYymm=" + baseYymm + ", segId=" + segId + ", lgd=" + lgd + "]";
	}
	
}
