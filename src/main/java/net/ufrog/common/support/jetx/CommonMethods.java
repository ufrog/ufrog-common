package net.ufrog.common.support.jetx;

import java.math.BigDecimal;

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
}
