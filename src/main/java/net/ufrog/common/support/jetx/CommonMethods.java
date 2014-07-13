package net.ufrog.common.support.jetx;

import java.math.BigDecimal;
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
	 * @return
	 */
	public static String currency(Object value) {
		if (value instanceof Number) {
			return Numbers.currency(Number.class.cast(value));
		}
		return Numbers.currency(new BigDecimal(value.toString()));
	}
	
	/**
	 * @param value
	 * @return
	 */
	public static String percent(Object value) {
		if (value instanceof Number) {
			return Numbers.percent(Number.class.cast(value));
		}
		return Numbers.percent(new BigDecimal(value.toString()));
	}
	
	/**
	 * @param value
	 * @return
	 */
	public static String date(Object value) {
		return Calendars.date(Date.class.cast(value));
	}
	
	/**
	 * @param value
	 * @return
	 */
	public static String time(Object value) {
		return Calendars.time(Date.class.cast(value));
	}
	
	/**
	 * @param value
	 * @return
	 */
	public static String datetime(Object value) {
		return Calendars.datetime(Date.class.cast(value));
	}
}
