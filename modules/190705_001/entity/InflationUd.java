package entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="EAS_USER_INFLATION")
public class InflationUd implements Serializable {
	
	private static final long serialVersionUID = 6204718447736643350L;
	private String baseYymm;
	private String indexId;
	private double inflationIndex;
	
	@Id
	public String getBaseYymm() {
		return baseYymm;
	}
	public void setBaseYymm(String baseYymm) {
		this.baseYymm = baseYymm;
	}
	@Id
	public String getIndexId() {
		return indexId;
	}
	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}
	public double getInflationIndex() {
		return inflationIndex;
	}
	public void setInflationIndex(double inflationIndex) {
		this.inflationIndex = inflationIndex;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((baseYymm == null) ? 0 : baseYymm.hashCode());
		result = prime * result + ((indexId == null) ? 0 : indexId.hashCode());
		long temp;
		temp = Double.doubleToLongBits(inflationIndex);
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
		InflationUd other = (InflationUd) obj;
		if (baseYymm == null) {
			if (other.baseYymm != null)
				return false;
		} else if (!baseYymm.equals(other.baseYymm))
			return false;
		if (indexId == null) {
			if (other.indexId != null)
				return false;
		} else if (!indexId.equals(other.indexId))
			return false;
		if (Double.doubleToLongBits(inflationIndex) != Double.doubleToLongBits(other.inflationIndex))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "InflationUd [baseYymm=" + baseYymm + ", indexId=" + indexId + ", inflationIndex=" + inflationIndex
				+ "]";
	}
	

}
