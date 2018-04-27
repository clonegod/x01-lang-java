package clonegod.java8.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 *  旧的日期时间支持：旧的日期/时间类已经在几乎所有的应用程序中使用，因此做到向下兼容是必须的。
 *  这也是为什么会有若干工具方法帮助我们将旧的类转换为新的类，反之亦然。
 *
 */
public class Test07DateAPILegacySupport {

	public static void main(String[] args) {

		// Date to Instant
		Instant timestamp = new Date().toInstant();
		// Now we can convert Instant to LocalDateTime or other similar classes
		LocalDateTime date = LocalDateTime.ofInstant(timestamp, ZoneId.of(ZoneId.SHORT_IDS.get("CTT"))); // Asia/Shanghai
		System.out.println("Date = " + date);

		// Calendar to Instant
		Instant time = Calendar.getInstance().toInstant();
		System.out.println(time);
		// TimeZone to ZoneId
		ZoneId defaultZone = TimeZone.getDefault().toZoneId();
		System.out.println(defaultZone);

		// ZonedDateTime from specific Calendar
		ZonedDateTime gregorianCalendarDateTime = new GregorianCalendar().toZonedDateTime();
		System.out.println(gregorianCalendarDateTime);

		// Date API to Legacy classes
		Date dt = Date.from(Instant.now());
		System.out.println(dt);

		TimeZone tz = TimeZone.getTimeZone(defaultZone);
		System.out.println(tz);

		GregorianCalendar gc = GregorianCalendar.from(gregorianCalendarDateTime);
		System.out.println(gc);

	}

}