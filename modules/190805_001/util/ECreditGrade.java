package util;

import java.util.Arrays;

public enum ECreditGrade {
	RF(0, "RF", "000"),
	AAA(1, "AAA", "101"),
	
	AAP(2, "AA+", "102"),
	AA(3, "AA", "103"),
	AAM(4, "AA-", "104"),
	
	AP(5, "A+", "105"),
	A(6, "A", "106"),
	AM(7, "A-", "107"),
	
	BBBP(8, "BBB+", "108"),
	BBB(9, "BBB", "109"),
	BBBM(10, "BBB-", "110"),
	
	BBP(11, "BB+", "111"),
	BB(12, "BB", "112"),
	BBM(13, "BB-", "113"),
	
	BP(14, "B+", "114"),
	B(15, "B", "115"),
	BM(16, "B-", "116"),
	
	CCCP(17, "CCC+", "118"),
	CCC(18, "CCC", "119"),
	CCCM(19, "CCC", "120"),
	D(90, "D", "900"),
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
