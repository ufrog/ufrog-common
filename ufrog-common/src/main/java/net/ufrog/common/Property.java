package net.ufrog.common;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import net.ufrog.common.exception.ServiceException;
import net.ufrog.common.utils.Strings;

/**
 * 属性
 * 
 * @author ultrafrog
 * @version 1.0, 2012-11-30
 * @since 1.0
 */
public class Property {

	protected static final String PROP_FILE = "/application.conf";
	
	/** 缓存 */
	protected static Properties properties;
	
	/**
	 * 设置属性
	 * 
	 * @param key
	 * @param value
	 */
	public static void put(String key, Object value) {
		properties.put(key, value);
	}
	
	/**
	 * 设置属性
	 * 
	 * @param values
	 */
	public static void putAll(Map<Object, Object> values) {
		properties.putAll(values);
	}
	
	/**
	 * 读取属性值
	 * 
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {
		if (properties == null) {
			try {
				properties = new Properties();
				properties.load(Property.class.getResourceAsStream(PROP_FILE));
			} catch (IOException e) {
				throw new ServiceException(String.format("could not load properties file: %s.", PROP_FILE), "exception.config");
			}
		}
		return properties.getProperty(key);
	}
	
	/**
	 * 读取属性值
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getValue(String key, String defaultValue) {
		return Strings.empty(getValue(key), defaultValue);
	}
	
	/**
	 * 读取属性整型值
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static Integer getInteger(String key, Integer defaultValue) {
		return Integer.valueOf(getValue(key, String.valueOf(defaultValue)));
	}
}
