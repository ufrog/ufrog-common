package net.ufrog.common;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import net.ufrog.common.app.App;
import net.ufrog.common.utils.Strings;

/**
 * 消息
 * 
 * @author ultrafrog
 * @version 1.0, 2012-11-30
 * @since 1.0
 */
public class Messages {

	private static final String CONF_BASENAME = App.config("app.i18n.basename", "messages");
	
	/**
	 * 读取资源包
	 * 
	 * @param locale
	 * @return
	 */
	public static ResourceBundle getBundle(Locale locale) {
		if (locale == null) locale = Locale.getDefault();
		return ResourceBundle.getBundle(CONF_BASENAME, locale);
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
		String parts[] = locale.split("_");
		String language = (parts.length > 0) ? parts[0] : "";
		String country = (parts.length > 1) ? parts[1] : "";
		return (!Strings.empty(language) ? new Locale(language, country) : null);
	}
}
