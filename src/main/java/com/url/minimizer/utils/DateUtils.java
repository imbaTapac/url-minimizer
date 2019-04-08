package com.url.minimizer.utils;

import java.util.Date;

import org.joda.time.DateTime;

public class DateUtils {

	private DateUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static boolean isDateExpired(Date date) {
		return DateTime.now().isAfter(date.getTime());
	}

	public static boolean isDaysExpired(Date dateCreated, int days) {
		DateTime created = new DateTime(dateCreated);
		DateTime resultDate = created.plusDays(days);
		return DateTime.now().isAfter(resultDate);
	}
}
