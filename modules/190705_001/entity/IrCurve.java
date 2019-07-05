package entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="EAS_IR_CURVE")
public class IrCurve implements Serializable {
	
	private static final long serialVersionUID = 7434092850535648878L;
	private String irCurveId;
	private String irCurveNm;
	private String curCd;
	private String applMethDvcd;
	private String useYn;
	private String crdGrdCd;
	
	@Id
	public String getIrCurveId() {
		return irCurveId;
	}
	public void setIrCurveId(String irCurveId) {
		this.irCurveId = irCurveId;
	}
	public String getIrCurveNm() {
		return irCurveNm;
	}
	public void setIrCurveNm(String irCurveNm) {
		this.irCurveNm = irCurveNm;
	}
	public String getCurCd() {
		return curCd;
	}
	public void setCurCd(String curCd) {
		this.curCd = curCd;
	}
	public String getApplMethDvcd() {
		return applMethDvcd;
	}
	public void setApplMethDvcd(String applMethDvcd) {
		this.applMethDvcd = applMethDvcd;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getCrdGrdCd() {
		return crdGrdCd;
	}
	public void setCrdGrdCd(String crdGrdCd) {
		this.crdGrdCd = crdGrdCd;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((applMethDvcd == null) ? 0 : applMethDvcd.hashCode());
		result = prime * result + ((crdGrdCd == null) ? 0 : crdGrdCd.hashCode());
		result = prime * result + ((curCd == null) ? 0 : curCd.hashCode());
		result = prime * result + ((irCurveId == null) ? 0 : irCurveId.hashCode());
		result = prime * result + ((irCurveNm == null) ? 0 : irCurveNm.hashCode());
		result = prime * result + ((useYn == null) ? 0 : useYn.hashCode());
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
		IrCurve other = (IrCurve) obj;
		if (applMethDvcd == null) {
			if (other.applMethDvcd != null)
				return false;
		} else if (!applMethDvcd.equals(other.applMethDvcd))
			return false;
		if (crdGrdCd == null) {
			if (other.crdGrdCd != null)
				return false;
		} else if (!crdGrdCd.equals(other.crdGrdCd))
			return false;
		if (curCd == null) {
			if (other.curCd != null)
				return false;
		} else if (!curCd.equals(other.curCd))
			return false;
		if (irCurveId == null) {
			if (other.irCurveId != null)
				return false;
		} else if (!irCurveId.equals(other.irCurveId))
			return false;
		if (irCurveNm == null) {
			if (other.irCurveNm != null)
				return false;
		} else if (!irCurveNm.equals(other.irCurveNm))
			return false;
		if (useYn == null) {
			if (other.useYn != null)
				return false;
		} else if (!useYn.equals(other.useYn))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "IrCurve [irCurveId=" + irCurveId + ", irCurveNm=" + irCurveNm + ", curCd=" + curCd + ", applMethDvcd="
				+ applMethDvcd + ", useYn=" + useYn + ", crdGrdCd=" + crdGrdCd + "]";
	}
	
}
