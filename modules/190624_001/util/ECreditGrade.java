package util;

import java.util.Arrays;

public enum ECreditGrade {
	RF(0, "RF", "000"),
	AAA(1, "AAA", "101"),
	
	AAP(2, "AA+", "102"),
	AA(3, "AA+", "103"),
	AAM(4, "AA+", "104"),
	
	AP(5, "AA+", "105"),
	A(6, "AA+", "106"),
	AM(7, "AA+", "107"),
	
	BBBP(8, "AA+", "108"),
	BBB(9, "AA+", "109"),
	BBBM(10, "AA+", "110"),
	
	BBP(11, "AA+", "111"),
	BB(12, "AA+", "112"),
	BBM(13, "AA+", "113"),
	
	BP(14, "AA+", "114"),
	B(15, "AA+", "115"),
	BM(16, "NR", "116"),
	
	CCC(17, "AA+", "117"),
	D(18, "AA+", "900"),
	NR(99, "NR", "999");
	
	private int order;
	private String alias;
	private String legacyCode;
	
	private ECreditGrade(int order, String alias, String legacyCode) {
		this.order = order;
		this.alias = alias;
		this.legacyCode = legacyCode;
	}

	public int getOrder() {
		return order;
	}

	public String getAlias() {
		return alias;
	}

	public String getLegacyCode() {
		return legacyCode;
	}
	
	public static ECreditGrade getECreditGrade(String grade) {
		return Arrays.stream(ECreditGrade.values()).filter(e -> e.getAlias().equals(grade)).findFirst().get();
	}
	
	
	
	
	
}
