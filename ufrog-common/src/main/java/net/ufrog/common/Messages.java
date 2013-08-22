package net.ufrog.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

/**
 * 消息
 * 
 * @author ultrafrog
 * @version 1.0, 2012-11-30
 * @since 1.0
 */
public class Messages {

	protected static final String DEFAULT_BASENAME = "messages";
	
	protected static final String baseName = Property.getValue("app.i18n.basename", DEFAULT_BASENAME);
	
	/**
	 * 读取资源包
	 * 
	 * @param locale
	 * @return
	 */
	public static ResourceBundle getBundle(Locale locale) {
		if (locale == null) {
			locale = Locale.getDefault();
		}
		return ResourceBundle.getBundle(baseName, locale);
	}
	
	/**
	 * 读取资源包
	 * 
	 * @param locale
	 * @return
	 */
	public static ResourceBundle getBundle(String locale) {
		return getBundle(parseLocaleString(locale));
	}
	
	/**
	 * 读取资源
	 * 
	 * @param key
	 * @param locale
	 * @return
	 */
	public static String get(Locale locale, String key) {
		try {
			return getBundle(locale).getString(key);
		} catch (MissingResourceException e) {
			Logger.warn(e.getMessage());
			return key;
		}
	}
	
	/**
	 * 读取资源
	 * 
	 * @param locale
	 * @param key
	 * @param objs
	 * @return
	 */
	public static String get(Locale locale, String key, Object... args) {
		return String.format(get(locale, key), args);
	}
	
	/**
	 * 解析位置字符串
	 * 
	 * @param locale
	 * @return
	 */
	public static Locale parseLocaleString(String locale) {
		String parts[] = toArray(locale);
		String language = (parts.length > 0 ? parts[0] : "");
		String country = (parts.length > 1 ? parts[1] : "");
		return (language.length() > 0 ? new Locale(language, country) : null);
	}
	
	/**
	 * 转换成数组
	 * 
	 * @param locale
	 * @return
	 */
	protected static String[] toArray(String locale) {
		StringTokenizer st = new StringTokenizer(locale, "_ ");
		List<String> tokens = new ArrayList<String>(st.countTokens());
		while (st.hasMoreElements()) {
			tokens.add(st.nextToken());
		}
		return tokens.toArray(new String[tokens.size()]);
	}
}
