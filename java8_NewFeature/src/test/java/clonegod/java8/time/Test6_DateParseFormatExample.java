package clonegod.java8.time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 1、将一个日期格式转换为不同的格式
 * 2、解析一个字符串，得到日期时间对象
 *
 */
public class Test6_DateParseFormatExample {

	public static void main(String[] args) {

		// Format examples
		LocalDate date = LocalDate.now();
		// default format
		System.out.println("Default format of LocalDate=" + date);
		// specific format
		System.out.println(date.format(DateTimeFormatter.ofPattern("d::MMM::uuuu")));
		System.out.println(date.format(DateTimeFormatter.BASIC_ISO_DATE));

		LocalDateTime dateTime = LocalDateTime.now();
		// default format
		System.out.println("Default format of LocalDateTime=" + dateTime);
		// specific format
		System.out.println(dateTime.format(DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss")));
		System.out.println(dateTime.format(DateTimeFormatter.BASIC_ISO_DATE));

		Instant timestamp = Instant.now();
		// default format
		System.out.println("Default format of Instant=" + timestamp);

		// Parse examples
        LocalDate ld = LocalDate.parse("2018-03-25", DateTimeFormatter.ISO_DATE);
        System.out.println("Default format after parsing = "+ld);
        
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    System.out.println(LocalDateTime.parse("2018-03-25 01:02:03", dtf));

	}

}