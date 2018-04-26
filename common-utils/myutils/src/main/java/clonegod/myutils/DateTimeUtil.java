package clonegod.myutils;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;

public class DateTimeUtil {

	public static Date getCurrentDate() {
		return DateTime.now().toDate();
	}

	public static String formatDate(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}

	public static long now() {
		return new DateTime().getMillis();
	}
	
	private static final String PATTERN_NYRSFM = "YYYY-MM-dd HH:mm:ss.SSS";
    private static final String PATTERN_NYR_SHORT = "YYYYMMdd";
    
	public static String parseCurrentDateTime(Date otherDate) {
		return new SimpleDateFormat(PATTERN_NYRSFM).format(otherDate);
	}
	
	public static String parseCurrentDateTime() {
		return parseCurrentDateTime(new Date());
	}
	
	public static String parseCurrentDate(Date otherDate) {
		return new SimpleDateFormat(PATTERN_NYR_SHORT).format(otherDate);
	}
	
	public static String parseCurrentDate() {
		return parseCurrentDate(new Date());
	}
    
	public static String formatCurrency(double amt) {
		return NumberFormat.getCurrencyInstance().format(amt);
	}
}
