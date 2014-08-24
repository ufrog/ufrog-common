package net.ufrog.common.support.jetx;

import java.util.Date;

import net.ufrog.common.utils.Calendars;
import net.ufrog.common.utils.Numbers;

/**
 * @author ultrafrog
 * @version 1.0, 2014-06-17
 * @since 1.0
 */
public final class CommonMethods {

	/**
	 * @param value
	 * @param pattern
	 * @return
	 */
	public static String formatNumber(Number value, String pattern) {
		return Numbers.format(pattern, value);
	}
	
	/**
	 * @param value
	 * @return
	 */
	public static String currency(Number value) {
		return Numbers.currency(value);
	}
	
	/**
	 * @param value
	 * @return
	 */
	public static String percent(Number value) {
		return Numbers.percent(value);
	}
	
	/**
	 * @param value
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date value, String pattern) {
		return Calendars.format(pattern, value);
	}
	
	/**
	 * @param value
	 * @return
	 */
	public static String date(Date value) {
		return Calendars.date(value);
	}
	
	/**
	 * @param value
	 * @return
	 */
	public static String time(Date value) {
		return Calendars.time(value);
	}
	
	/**
	 * @param value
	 * @return
	 */
	public static String datetime(Date value) {
		return Calendars.datetime(value);
	}
}
