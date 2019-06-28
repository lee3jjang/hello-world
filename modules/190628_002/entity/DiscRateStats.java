package entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="EAS_DISC_RATE_STATS")
public class DiscRateStats implements Serializable {
	
	private static final long serialVersionUID = -2731189228637601242L;
	private String baseYymm;
	private String discRateCalcTyp;
	private String intRateCd;
	private String indepnVariable;
	private String depnVariable;
	private double regrConstant;
	private double regrCoef;
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
	public String getDiscRateCalcTyp() {
		return discRateCalcTyp;
	}
	public void setDiscRateCalcTyp(String discRateCalcTyp) {
		this.discRateCalcTyp = discRateCalcTyp;
	}
	@Id
	public String getIntRateCd() {
		return intRateCd;
	}
	public void setIntRateCd(String intRateCd) {
		this.intRateCd = intRateCd;
	}
	@Id
	public String getIndepnVariable() {
		return indepnVariable;
	}
	public void setIndepnVariable(String indepnVariable) {
		this.indepnVariable = indepnVariable;
	}
	@Id
	public String getDepnVariable() {
		return depnVariable;
	}
	public void setDepnVariable(String depnVariable) {
		this.depnVariable = depnVariable;
	}
	public double getRegrConstant() {
		return regrConstant;
	}
	public void setRegrConstant(double regrConstant) {
		this.regrConstant = regrConstant;
	}
	public double getRegrCoef() {
		return regrCoef;
	}
	public void setRegrCoef(double regrCoef) {
		this.regrCoef = regrCoef;
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
		result = prime * result + ((depnVariable == null) ? 0 : depnVariable.hashCode());
		result = prime * result + ((discRateCalcTyp == null) ? 0 : discRateCalcTyp.hashCode());
		result = prime * result + ((indepnVariable == null) ? 0 : indepnVariable.hashCode());
		result = prime * result + ((intRateCd == null) ? 0 : intRateCd.hashCode());
		result = prime * result + ((lastModifiedBy == null) ? 0 : lastModifiedBy.hashCode());
		result = prime * result + ((lastUpdateDate == null) ? 0 : lastUpdateDate.hashCode());
		long temp;
		temp = Double.doubleToLongBits(regrCoef);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(regrConstant);
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
		DiscRateStats other = (DiscRateStats) obj;
		if (baseYymm == null) {
			if (other.baseYymm != null)
				return false;
		} else if (!baseYymm.equals(other.baseYymm))
			return false;
		if (depnVariable == null) {
			if (other.depnVariable != null)
				return false;
		} else if (!depnVariable.equals(other.depnVariable))
			return false;
		if (discRateCalcTyp == null) {
			if (other.discRateCalcTyp != null)
				return false;
		} else if (!discRateCalcTyp.equals(other.discRateCalcTyp))
			return false;
		if (indepnVariable == null) {
			if (other.indepnVariable != null)
				return false;
		} else if (!indepnVariable.equals(other.indepnVariable))
			return false;
		if (intRateCd == null) {
			if (other.intRateCd != null)
				return false;
		} else if (!intRateCd.equals(other.intRateCd))
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
		if (Double.doubleToLongBits(regrCoef) != Double.doubleToLongBits(other.regrCoef))
			return false;
		if (Double.doubleToLongBits(regrConstant) != Double.doubleToLongBits(other.regrConstant))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "DiscRateStats [baseYymm=" + baseYymm + ", discRateCalcTyp=" + discRateCalcTyp + ", intRateCd="
				+ intRateCd + ", indepnVariable=" + indepnVariable + ", depnVariable=" + depnVariable
				+ ", regrConstant=" + regrConstant + ", regrCoef=" + regrCoef + ", lastModifiedBy=" + lastModifiedBy
				+ ", lastUpdateDate=" + lastUpdateDate + "]";
	}

}
