package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FinUtil {
	
	public static String addMonth(String bssd, int n) {
		Integer year = Integer.parseInt(bssd.substring(0, 4));
		Integer month = Integer.parseInt(bssd.substring(4, 6));
		return LocalDate.of(year, month, 1).plusMonths(n).format(DateTimeFormatter.ofPattern("yyyyMM"));
	}

}
