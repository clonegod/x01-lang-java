package EnumSet;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public class EnumSetExample {
	/** enum */
	private enum Days {
		Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday;

		// allDays will contain days declared in enum above.
		public static Set<Days> allDays = EnumSet.allOf(Days.class);

		// weekDays will contain days between Monday to Friday.
		public static Set<Days> weekDays = EnumSet.range(Monday, Friday);

		// Method will return true current day is weekDay
		public boolean isWeekDay() {
			return weekDays.contains(this);
		}

	}

	/** Main */
	public static final void main(final String args[]) {

		System.out.println("\n--------0. Print days enum -----------");
		System.out.println(Days.allDays);
		System.out.println("days in allDays = " + Days.allDays.size());

		System.out.println("\n--------1. iterate over allDays -----------");
		// iterate over allDays
		for (Days day : Days.allDays) {
			System.out.println("Day-" + (day.ordinal() + 1) + " " + day);
		}

		System.out.println("\n--------2. weekDay or weekEnd -----------");
		/** Check whether passed day is weekDay or weekEnd */

		// Pass Monday And Check whether Monday is weekDay or weekEnd
		Days day = Days.Monday;
		System.out.println(day + (day.isWeekDay() ? " is WeekDay" : " is weekEnd"));

		// Pass Sunday And Check whether Sunday is weekDay or weekEnd
		day = Days.Sunday;
		System.out.println(day + (day.isWeekDay() ? " is WeekDay" : " is weekEnd"));

		System.out.println("\n----3. contains --------");
		day = Days.Monday;
		System.out.println("allDays contains Monday : " + Days.allDays.contains(day));

		System.out.println("\n----4. ordinal --------");
		day = Days.Monday;
		System.out.println("ordinal of Monday : " + day.ordinal());

		System.out.println("\n-------5. Find number of days in weekEnd ---------");
		/** find weekEnd days by removing weekdays from allDays */

		// weeEnd currently consists of allDays
		Set<Days> weekEnd = Days.allDays;

		// Now, remove weekDays from allDays
		weekEnd.removeAll(Days.weekDays);

		// Currently weeEnd consists of Saturday and Sunday
		System.out.println("days in weekEnd = " + weekEnd);
		System.out.println("The weekEnd is " + weekEnd.size() + " days long");

		System.out.println("\n-------6. synchronizing EnumSet ---------");
		Set<Days> s = Collections.synchronizedSet(EnumSet.noneOf(Days.class));
	}

}
