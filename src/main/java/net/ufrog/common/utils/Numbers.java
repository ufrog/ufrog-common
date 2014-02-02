package net.ufrog.common.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

import net.ufrog.common.app.App;
import net.ufrog.common.cache.Caches;

/**
 * 数字工具
 *
 * @author ultrafrog
 * @version 1.0, 2013-06-09
 * @since 1.0
 */
public abstract class Numbers {

	private static final String CACHE_PREFIX		= "net.ufrog.common.cache.numbers.format.";
	
	private static final String PATTERN_CURRENCY	= App.config("app.format.currency", "#,##0.00");
	private static final String PATTERN_PERCENT		= App.config("app.format.percent", "#,##0.00%");
	
	/**
	 * 读取格式器
	 * 
	 * @param pattern
	 * @return
	 */
	public static NumberFormat getFormat(String pattern) {
		NumberFormat format = Caches.get(CACHE_PREFIX + pattern, NumberFormat.class);
		if (format == null) {
			format = new DecimalFormat(pattern);
			Caches.add(CACHE_PREFIX + pattern, format);
		}
		return format;
	}
	
	/**
	 * 格式化数字
	 * 
	 * @param pattern
	 * @param number
	 * @return
	 */
	public static String format(String pattern, Number number) {
		return getFormat(pattern).format(number.doubleValue());
	}
	
	/**
	 * 解析数字字符串
	 * 
	 * @param pattern
	 * @param source
	 * @return
	 * @throws ParseException
	 */
	public static Number parse(String pattern, String source) throws ParseException {
		return getFormat(pattern).parse(source);
	}
	
	/**
	 * 格式化金额
	 * 
	 * @param number
	 * @return
	 */
	public static String currency(Number number) {
		return format(PATTERN_CURRENCY, number);
	}
	
	/**
	 * 格式化百分比
	 * 
	 * @param number
	 * @return
	 */
	public static String percent(Number number) {
		return format(PATTERN_PERCENT, number);
	}
}
