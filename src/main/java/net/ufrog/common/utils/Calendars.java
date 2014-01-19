package net.ufrog.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.ufrog.common.Logger;
import net.ufrog.common.app.App;
import net.ufrog.common.cache.Caches;

/**
 * 日历工具
 * 
 * @author ultrafrog
 * @version 1.0, 2011-12-19
 * @since 1.0
 */
public abstract class Calendars {

	private static final String CACHE_PREFIX		= "net.ufrog.common.cache.data.format.";
	
	private static final String PATTERN_DATE 		= App.config("app.format.data", "yyyy-MM-dd");
	private static final String PATTERN_TIME		= App.config("app.format.time", "HH:mm:ss");
	private static final String PATTERN_DATETIME	= App.config("app.format.datetime", "yyyy-MM-dd HH:mm:ss");
	
	private static final Pattern PARSE_DAYS 		= Pattern.compile("^([0-9]+)d$");
	private static final Pattern PARSE_HOURS 		= Pattern.compile("^([0-9]+)h$");
	private static final Pattern PARSE_MINUTES 		= Pattern.compile("^([0-9]+)mi?n$");
	private static final Pattern PARSE_SECONDS 		= Pattern.compile("^([0-9]+)s$");
	
	/**
	 * 读取格式器
	 * 
	 * @param pattern
	 * @return
	 */
	public static DateFormat getFormat(String pattern) {
		DateFormat format = Caches.get(CACHE_PREFIX + pattern, DateFormat.class);
		if (format == null) {
			format = new SimpleDateFormat(pattern);
			Caches.add(CACHE_PREFIX + pattern, format);
		}
		return format;
	}
	
	/**
	 * 格式化时间日期
	 * 
	 * @param pattern
	 * @param date
	 * @return
	 */
	public static String format(String pattern, Date date) {
		return getFormat(pattern).format(date);
	}
	
	/**
	 * 格式化当前时间日期
	 * 
	 * @param pattern
	 * @return
	 */
	public static String format(String pattern) {
		return format(pattern, new Date());
	}
	
	/**
	 * 解析时间日期字符串
	 * 
	 * @param pattern
	 * @param source
	 * @return
	 * @throws ParseException
	 */
	public static Date parse(String pattern, String source) throws ParseException {
		return getFormat(pattern).parse(source);
	}
	
	/**
	 * 解析并格式化时间日期字符串
	 * 
	 * @param parsePattern
	 * @param formatPattern
	 * @param source
	 * @return
	 * @throws ParseException
	 */
	public static String parseAndFormat(String parsePattern, String formatPattern, String source) throws ParseException {
		return format(formatPattern, parse(parsePattern, source));
	}
	
	/**
	 * 解析并格式化时间日期字符串<br>
	 * 如果发生异常返回默认字符串
	 * 
	 * @param parsePattern
	 * @param formatPattern
	 * @param source
	 * @param def
	 * @return
	 */
	public static String parseAndFormat(String parsePattern, String formatPattern, String source, String def) {
		try {
			return parseAndFormat(parsePattern, formatPattern, source);
		} catch (ParseException e) {
			Logger.warn(e.getMessage());
			return def;
		}
	}
	
	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @return
	 */
	public static String date(Date date) {
		return format(PATTERN_DATE, date);
	}
	
	/**
	 * 格式化当前日期
	 * 
	 * @return
	 */
	public static String date() {
		return date(new Date());
	}
	
	/**
	 * 格式化时间
	 * 
	 * @param date
	 * @return
	 */
	public static String time(Date date) {
		return format(PATTERN_TIME, date);
	}
	
	/**
	 * 格式化当前时间
	 * 
	 * @return
	 */
	public static String time() {
		return time(new Date());
	}
	
	/**
	 * 格式化日期时间
	 * 
	 * @param date
	 * @return
	 */
	public static String datetime(Date date) {
		return format(PATTERN_DATETIME, date);
	}
	
	/**
	 * 格式化当前日期时间
	 * 
	 * @return
	 */
	public static String datetime() {
		return datetime(new Date());
	}
	
	/**
	 * 解析日期
	 * 
	 * @param source
	 * @return
	 * @throws ParseException 
	 */
	public static Date parseDate(String source) throws ParseException {
		return parse(PATTERN_DATE, source);
	}
	
	/**
	 * 解析时间
	 * 
	 * @param source
	 * @return
	 * @throws ParseException 
	 */
	public static Date parseTime(String source) throws ParseException {
		return parse(PATTERN_TIME, source);
	}
	
	/**
	 * 解析日期时间
	 * 
	 * @param source
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDatetime(String source) throws ParseException {
		return parse(PATTERN_DATETIME, source);
	}
	
	/**
	 * 解析持续时间<br>
	 * 样例：3d, 4h, 5mn, 6s
	 * 
	 * @param duration
	 * @return
	 */
	public static int parseDuration(String duration) {
		// 初始化
		if (Strings.empty(duration)) duration = "30d";
		Matcher matcher = null;
		
		// 判断周期类型并转换成秒数
		if ((matcher = PARSE_DAYS.matcher(duration)).matches()) {
			return Integer.parseInt(matcher.group(1)) * 60 * 60 * 24;
		} else if ((matcher = PARSE_HOURS.matcher(duration)).matches()) {
			return Integer.parseInt(matcher.group(1)) * 60 * 60;
		} else if ((matcher = PARSE_MINUTES.matcher(duration)).matches()) {
			return Integer.parseInt(matcher.group(1)) * 60;
		} else if ((matcher = PARSE_SECONDS.matcher(duration)).matches()) {
			return Integer.parseInt(matcher.group(1));
		} else {
			throw new IllegalArgumentException(String.format("invalid duration pattern '%s'", duration));
		}
	}
}
