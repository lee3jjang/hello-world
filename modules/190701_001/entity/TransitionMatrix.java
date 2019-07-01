package entity;

import java.io.Serializable;

import javax.persistence.*;

import util.ECreditGrade;

/**
 * @author 11700205
 *
 */
@Entity
@Table(name="EAS_USER_CORP_CRD_GRD_TM")
public class TransitionMatrix implements Serializable {
	
	private static final long serialVersionUID = 879791737358688381L;
	private String baseYyyy;
	private String tmType;
	private String fromCrdGrdCd;
	private String toCrdGrdCd;
	private double probRate;
	
	@Id
	public String getBaseYyyy() {
		return baseYyyy;
	}
	public void setBaseYyyy(String baseYyyy) {
		this.baseYyyy = baseYyyy;
	}
	@Id
	public String getTmType() {
		return tmType;
	}
	public void setTmType(String tmType) {
		this.tmType = tmType;
	}
	@Id
	public String getFromCrdGrdCd() {
		return fromCrdGrdCd;
	}
	public void setFromCrdGrdCd(String fromCrdGrdCd) {
		this.fromCrdGrdCd = fromCrdGrdCd;
	}
	@Id
	public String getToCrdGrdCd() {
		return toCrdGrdCd;
	}
	public void setToCrdGrdCd(String toCrdGrdCd) {
		this.toCrdGrdCd = toCrdGrdCd;
	}
	public double getProbRate() {
		return probRate;
	}
	public void setProbRate(double probRate) {
		this.probRate = probRate;
	}
	@Transient
	public ECreditGrade getFromGradeEnum() {
		return ECreditGrade.getECreditGrade(this.fromCrdGrdCd);
	}
	@Transient
	public ECreditGrade getToGradeEnum() {
		return ECreditGrade.getECreditGrade(this.toCrdGrdCd);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((baseYyyy == null) ? 0 : baseYyyy.hashCode());
		result = prime * result + ((fromCrdGrdCd == null) ? 0 : fromCrdGrdCd.hashCode());
		long temp;
		temp = Double.doubleToLongBits(probRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((tmType == null) ? 0 : tmType.hashCode());
		result = prime * result + ((toCrdGrdCd == null) ? 0 : toCrdGrdCd.hashCode());
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
		TransitionMatrix other = (TransitionMatrix) obj;
		if (baseYyyy == null) {
			if (other.baseYyyy != null)
				return false;
		} else if (!baseYyyy.equals(other.baseYyyy))
			return false;
		if (fromCrdGrdCd == null) {
			if (other.fromCrdGrdCd != null)
				return false;
		} else if (!fromCrdGrdCd.equals(other.fromCrdGrdCd))
			return false;
		if (Double.doubleToLongBits(probRate) != Double.doubleToLongBits(other.probRate))
			return false;
		if (tmType == null) {
			if (other.tmType != null)
				return false;
		} else if (!tmType.equals(other.tmType))
			return false;
		if (toCrdGrdCd == null) {
			if (other.toCrdGrdCd != null)
				return false;
		} else if (!toCrdGrdCd.equals(other.toCrdGrdCd))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "TransitionMatrix [baseYyyy=" + baseYyyy + ", tmType=" + tmType + ", fromCrdGrdCd=" + fromCrdGrdCd
				+ ", toCrdGrdCd=" + toCrdGrdCd + ", probRate=" + probRate + "]";
	}	
	
}
