package entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="EAS_CORP_CRD_GRD_CUM_PD")
public class CorpCumPd implements Serializable {
	
	private static final long serialVersionUID = -5501791244658547580L;
	private String baseYymm;
	private String tmType;
	private String gradeCode;
	private String matCd;
	private double cumPd;
	private double fwdPd;
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
	public String getTmType() {
		return tmType;
	}
	public void setTmType(String tmType) {
		this.tmType = tmType;
	}
	@Id
	public String getGradeCode() {
		return gradeCode;
	}
	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}
	@Id
	public String getMatCd() {
		return matCd;
	}
	public void setMatCd(String matCd) {
		this.matCd = matCd;
	}
	public double getCumPd() {
		return cumPd;
	}
	public void setCumPd(double cumPd) {
		this.cumPd = cumPd;
	}
	public double getFwdPd() {
		return fwdPd;
	}
	public void setFwdPd(double fwdPd) {
		this.fwdPd = fwdPd;
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
		long temp;
		temp = Double.doubleToLongBits(cumPd);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(fwdPd);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((gradeCode == null) ? 0 : gradeCode.hashCode());
		result = prime * result + ((lastModifiedBy == null) ? 0 : lastModifiedBy.hashCode());
		result = prime * result + ((lastUpdateDate == null) ? 0 : lastUpdateDate.hashCode());
		result = prime * result + ((matCd == null) ? 0 : matCd.hashCode());
		result = prime * result + ((tmType == null) ? 0 : tmType.hashCode());
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
		CorpCumPd other = (CorpCumPd) obj;
		if (baseYymm == null) {
			if (other.baseYymm != null)
				return false;
		} else if (!baseYymm.equals(other.baseYymm))
			return false;
		if (Double.doubleToLongBits(cumPd) != Double.doubleToLongBits(other.cumPd))
			return false;
		if (Double.doubleToLongBits(fwdPd) != Double.doubleToLongBits(other.fwdPd))
			return false;
		if (gradeCode == null) {
			if (other.gradeCode != null)
				return false;
		} else if (!gradeCode.equals(other.gradeCode))
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
		if (tmType == null) {
			if (other.tmType != null)
				return false;
		} else if (!tmType.equals(other.tmType))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CorpCumPd [baseYymm=" + baseYymm + ", tmType=" + tmType + ", gradeCode=" + gradeCode + ", matCd="
				+ matCd + ", cumPd=" + cumPd + ", fwdPd=" + fwdPd + ", lastModifiedBy=" + lastModifiedBy
				+ ", lastUpdateDate=" + lastUpdateDate + "]";
	}
	
}
