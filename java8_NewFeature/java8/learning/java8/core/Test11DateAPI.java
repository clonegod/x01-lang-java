package java8.core;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

public class Test11DateAPI {
	/**
	 * Java 8 contains a brand new date and time API under the package java.time
	 * 
	 * API is comparable with the Joda-Time library, however it's not the same. 
	 */
	
	/**
	 * Clock
	 * 		Clock provides access to the current date and time.
	 */
	@Test
	public void testClock() {
		// Clocks are aware of a timezone and may be used instead of System.currentTimeMillis() to retrieve the current milliseconds. 
		Clock clock = Clock.systemDefaultZone();
		long millis = clock.millis();
		System.out.println(millis);

		// Instants can be used to create legacy java.util.Date objects.
		Instant instant = clock.instant();
		Date legacyDate = Date.from(instant);   // legacy java.util.Date
		System.out.println(legacyDate);
	}
	
	
	/**
	 * Timezones
	 * 		Timezones define the offsets which are important to convert between instants and local dates and times.
	 */
	@Test
	public void testTimezones() {
		System.out.println(ZoneId.getAvailableZoneIds());
		// prints all available timezone ids

		ZoneId zone1 = ZoneId.of("Asia/Shanghai");
		ZoneId zone2 = ZoneId.of("Japan");
		System.out.println(zone1.getRules());
		System.out.println(zone2.getRules());
	}
	
	
	
	/**
	 * LocalTime
	 * 		LocalTime represents a time without a timezone, e.g. 10pm or 17:30:15.
	 * 
	 * 		LocalTime comes with various factory method to simplify the creation of new instances, including parsing of time strings.
	 */	
	@Test
	public void testLocalTime() {
		ZoneId zone1 = ZoneId.of("Asia/Shanghai");
		ZoneId zone2 = ZoneId.of("Japan");
		
		// The following example creates two local times for the timezones defined above. 
		// Then we compare both times and calculate the difference in hours and minutes between both times.
		LocalTime now1 = LocalTime.now(zone1);
		LocalTime now2 = LocalTime.now(zone2);

		System.out.println(now1.isBefore(now2));  // true

		// Chrono - 计时
		long hoursBetween = ChronoUnit.HOURS.between(now1, now2);
		long minutesBetween = ChronoUnit.MINUTES.between(now1, now2);

		System.out.println(hoursBetween);       // -3
		System.out.println(minutesBetween);     // -239
		
		
		// creating and parse
		LocalTime late = LocalTime.of(23, 59, 59);
		System.out.println(late);       // 23:59:59

		DateTimeFormatter chinaFormatter =
		    DateTimeFormatter
		        .ofLocalizedTime(FormatStyle.MEDIUM)
		        .withLocale(Locale.CHINA);

		LocalTime leetTime = LocalTime.parse("13:37:28", chinaFormatter);
		System.out.println(leetTime);   // 13:37:28
	}
	
	
	
	/**
	 * LocalDate
	 * 		LocalDate represents a distinct date, e.g. 2014-03-11.
	 * 		It's immutable and works exactly analog to LocalTime.
	 */
	@Test
	public void testLocalDate() {
		// The sample demonstrates how to calculate new dates by adding or substracting days, months or years. 
		// Keep in mind that each manipulation returns a new instance.
		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
		LocalDate yesterday = tomorrow.minusDays(2);

		LocalDate independenceDay = LocalDate.of(2018, Month.APRIL, 28);
		DayOfWeek dayOfWeek = independenceDay.getDayOfWeek();
		System.out.println(dayOfWeek);    // SATURDAY
		
		
		// Parsing a LocalDate from a string is just as simple as parsing a LocalTime:
		DateTimeFormatter chinaFormatter =
			    DateTimeFormatter
			        .ofLocalizedDate(FormatStyle.MEDIUM)
			        .withLocale(Locale.CHINA);

			LocalDate myDate = LocalDate.parse("2018-4-28", chinaFormatter);
			System.out.println(myDate);   // 2018-04-28
			
	}
	
	
	/**
	 * LocalDateTime
	 * 		LocalDateTime represents a date-time
	 * 		LocalDateTime is immutable and works similar to LocalTime and LocalDate.
	 * 		
	 * 		DateTimeFormatter is immutable and thread-safe.
	 */
	@Test
	public void testLocalDateTime() {
		// We can utilize methods for retrieving certain fields from a date-time:
		LocalDateTime sylvester = LocalDateTime.of(2018, Month.DECEMBER, 31, 23, 59, 59);

		DayOfWeek dayOfWeek = sylvester.getDayOfWeek();
		System.out.println(dayOfWeek);      // MONDAY

		Month month = sylvester.getMonth();
		System.out.println(month);          // DECEMBER

		long minuteOfDay = sylvester.getLong(ChronoField.MINUTE_OF_DAY);
		System.out.println(minuteOfDay);    // 1439
		
		long secondOfDay = sylvester.getLong(ChronoField.SECOND_OF_DAY);
		System.out.println(secondOfDay);    // 86399
		
		
		// With the additional information of a timezone it can be converted to an instant.
		// LocalDateTime -> Instant -> Date
		Instant instant = sylvester
		        .atZone(ZoneId.systemDefault())
		        .toInstant();

		Date legacyDate = Date.from(instant);
		System.out.println(legacyDate);     // Wed Dec 31 23:59:59 CET 2014
		
		
		// Formatting date-times works just like formatting dates or times. 
		// Instead of using pre-defined formats we can create formatters from custom patterns.
		DateTimeFormatter formatter =
			    DateTimeFormatter
			        .ofPattern("yyyy-MM-dd HH:mm:ss");

		LocalDateTime parsed = LocalDateTime.parse("2018-04-28 17:13:50", formatter);
		String string = formatter.format(parsed);
		System.out.println(string);     // 2018-04-28 17:13:50
		
		
	}
	
	
	
	
}
