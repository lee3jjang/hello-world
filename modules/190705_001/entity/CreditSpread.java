package entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="EAS_CRD_SPREAD")
public class CreditSpread implements Serializable {
	
	private static final long serialVersionUID = -8704703043411355519L;
	private String baseYymm;
	private String crdGrdCd;
	private String matCd;
	private double crdSpread;
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
	public String getCrdGrdCd() {
		return crdGrdCd;
	}
	public void setCrdGrdCd(String crdGrdCd) {
		this.crdGrdCd = crdGrdCd;
	}
	@Id
	public String getMatCd() {
		return matCd;
	}
	public void setMatCd(String matCd) {
		this.matCd = matCd;
	}
	public double getCrdSpread() {
		return crdSpread;
	}
	public void setCrdSpread(double crdSpread) {
		this.crdSpread = crdSpread;
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
		result = prime * result + ((crdGrdCd == null) ? 0 : crdGrdCd.hashCode());
		long temp;
		temp = Double.doubleToLongBits(crdSpread);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((lastModifiedBy == null) ? 0 : lastModifiedBy.hashCode());
		result = prime * result + ((lastUpdateDate == null) ? 0 : lastUpdateDate.hashCode());
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
		CreditSpread other = (CreditSpread) obj;
		if (baseYymm == null) {
			if (other.baseYymm != null)
				return false;
		} else if (!baseYymm.equals(other.baseYymm))
			return false;
		if (crdGrdCd == null) {
			if (other.crdGrdCd != null)
				return false;
		} else if (!crdGrdCd.equals(other.crdGrdCd))
			return false;
		if (Double.doubleToLongBits(crdSpread) != Double.doubleToLongBits(other.crdSpread))
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
		return true;
	}
	@Override
	public String toString() {
		return "CreditSpread [baseYymm=" + baseYymm + ", crdGrdCd=" + crdGrdCd + ", matCd=" + matCd + ", crdSpread="
				+ crdSpread + ", lastModifiedBy=" + lastModifiedBy + ", lastUpdateDate=" + lastUpdateDate + "]";
	}

}
