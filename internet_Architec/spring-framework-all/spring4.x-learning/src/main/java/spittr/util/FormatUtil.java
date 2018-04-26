package spittr.util;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class FormatUtil {
	
	static final Locale locale = new Locale("zh", "CN");
	
	public static String formatCurrrency(Number number) {
		NumberFormat currfmt = NumberFormat.getCurrencyInstance(locale);
		String text = currfmt.format(number);
		return text;
	}
	
	public static String formatDate(Date date) {
		DateFormat datefmt = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
		String text = datefmt.format(date);
		return text;
	}
	
	/**
	 * MessageFormat 中根据参数变量的索引，对文件进行内容填充，用于构造动态内容的字符串
	 * 
	 * @param pattern
	 * @param args
	 * @return
	 */
	public static String formatMessage(String pattern, Object[] args) {
		MessageFormat messagefmt = new MessageFormat(pattern, locale);
		String message = messagefmt.format(args);
		return message;
	}
	
	public static void main(String[] args) {
		System.out.println(formatCurrrency(9999100.208)); // ￥9,999,100.21
		
		System.out.println(formatDate(new Date())); 
		
		Object[] params = new Object[]{"John", new GregorianCalendar().getTime(), 1.01E3};
		System.out.println(formatMessage("{0},于{1}在xxx银行存入{2}元", params));
		System.out.println(formatMessage("At {1,time,short} On {1,date,long}, {0} paid {2,number,currency}", params));
	}
}
