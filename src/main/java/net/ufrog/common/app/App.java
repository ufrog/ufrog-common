package net.ufrog.common.app;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import net.ufrog.common.exception.UnsignException;
import net.ufrog.common.exception.ServiceException;
import net.ufrog.common.utils.Strings;

/**
 * 应用信息
 *
 * @author ultrafrog
 * @version 1.0, 2013-10-01
 * @since 1.0
 */
public abstract class App {

	private static final String PATH_CONFIG 	= "/application.conf";
	
	private static ThreadLocal<App> current 	= new ThreadLocal<App>();
	private static Properties properties 		= null;
	
	/**
	 * 读取用户
	 * 
	 * @return
	 */
	public abstract AppUser getUser();
	
	/**
	 * 设置用户
	 * 
	 * @param user
	 * @return
	 */
	public abstract AppUser setUser(AppUser user);
	
	/**
	 * 读取令牌
	 * 
	 * @return
	 */
	public abstract String getToken();
	
	/**
	 * 更新令牌
	 * 
	 * @return
	 */
	public abstract String updateToken();
	
	/**
	 * 读取位置
	 * 
	 * @return
	 */
	public abstract Locale getLocale();
	
	/**
	 * 读取验证信息
	 * 
	 * @return
	 */
	public abstract Validation getValidation();
	
	/**
	 * 读取国际化消息
	 * 
	 * @param key
	 * @param args
	 * @return
	 */
	public abstract String getMessage(String key, Object... args);
	
	/**
	 * 当前信息
	 * 
	 * @return
	 */
	public static App current() {
		return current.get();
	}
	
	/**
	 * 当前信息
	 * 
	 * @param requiredType
	 * @return
	 */
	public static <T extends App> T current(Class<T> requiredType) {
		return requiredType.cast(current());
	}
	
	/**
	 * 当前信息
	 * 
	 * @param app
	 * @return
	 */
	public static <T extends App> T current(T value) {
		current.set(value);
		return value;
	}
	
	/**
	 * 当前用户
	 * 
	 * @return
	 */
	public static AppUser user() {
		AppUser user = current().getUser();
		if (user == null) throw new UnsignException();
		return user;
	}
	
	/**
	 * 国际化消息
	 * 
	 * @param key
	 * @param args
	 * @return
	 */
	public static String message(String key, Object... args) {
		return current().getMessage(key, args);
	}
	
	/**
	 * 配置
	 * 
	 * @param key
	 * @return
	 */
	public static String config(String key) {
		if (properties == null) {
			try {
				properties = new Properties();
				properties.load(App.class.getResourceAsStream(PATH_CONFIG));
			} catch (IOException e) {
				throw new ServiceException("can not load config file.", e);
			}
		}
		return properties.getProperty(key);
	}
	
	/**
	 * 配置
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String config(String key, String defaultValue) {
		return Strings.empty(config(key), defaultValue);
	}
	
	/**
	 * 设置配置
	 * 
	 * @param values
	 */
	public static void config(Map<String, String> values) {
		if (properties == null) config("0");
		properties.putAll(values);
	}
}
