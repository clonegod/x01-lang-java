package com.gc.letter.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DateUtil {

	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
	
	public static final Locale location = Locale.CHINESE;
	public static final String datePatternToday = "yyyy年MM月dd日";
	public static final String datePatternNow = "yyyy年MM月dd日 HH:mm:ss";

	public static Date getToday() {
		return new DateTime().toDate();
	}

	public static Date getTomorrow() {
		return new DateTime().plusDays(1).toDate();
	}

	public static String formatDate2String(Date date, String pattern) {
		return new DateTime(date).toString(pattern);
	}

	public static Date getBirthday(String birthday) {
		return new DateTime(birthday).toDate();
	}

	public static Date getSpeicfyDate(String specifyDate) {
		if (specifyDate == null) {
			throw new RuntimeException("日期不能为空");
		}
		if (!specifyDate.contains("-")) {
			String year = specifyDate.substring(0, 4);
			String month = specifyDate.substring(4, 6);
			String day = specifyDate.substring(6);
			specifyDate = year.concat("-").concat(month).concat("-")
					.concat(day);
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(specifyDate);
		} catch (ParseException e) {
			logger.error("日期转换错误：" + specifyDate, e);
			throw new RuntimeException(e);
		}
		return date;
	}

	public static Date getSpeicfyDate(int specifyDate) {
		return getSpeicfyDate(String.valueOf(specifyDate));
	}

	public static void main(String[] args) {
		System.out.println(formatDate2String(getToday(), "yyyy年MM月dd日"));
		System.out
				.println(formatDate2String(getToday(), "yyyy年MM月dd日 HH:mm:ss"));
		System.out.println(formatDate2String(getTomorrow(), "yyyy年MM月dd日"));
		System.out.println(formatDate2String(getTomorrow(),
				"yyyy年MM月dd日 HH:mm:ss"));
		System.out.println(getSpeicfyDate("1986-05-04"));
		System.out.println(getSpeicfyDate("2014-01-02"));
	}
}
