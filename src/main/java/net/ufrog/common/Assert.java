package net.ufrog.common;

import net.ufrog.common.exception.ServiceException;
import net.ufrog.common.utils.Objects;
import net.ufrog.common.utils.Strings;

/**
 * 断言
 * 
 * @author ultrafrog
 * @version 1.0, 2014-03-04
 * @since 1.0
 */
public abstract class Assert {

	/**
	 * 断言为空
	 * 
	 * @param obj
	 * @param message
	 * @param key
	 */
	public static void isNull(Object obj, String message, String key) {
		if (obj != null) {
			Logger.warn("%s is not null", obj);
			throw new ServiceException(message, key);
		}
	}
	
	/**
	 * 断言为空
	 * 
	 * @param obj
	 * @param message
	 */
	public static void isNull(Object obj, String message) {
		isNull(obj, message, null);
	}
	
	/**
	 * 断言为空
	 * 
	 * @param obj
	 */
	public static void isNull(Object obj) {
		isNull(obj, null);
	}
	
	/**
	 * 断言不为空
	 * 
	 * @param obj
	 * @param message
	 * @param key
	 */
	public static void notNull(Object obj, String message, String key) {
		if (obj == null) {
			Logger.warn("%s is null", obj);
			throw new ServiceException(message, key);
		}
	}
	
	/**
	 * 断言不为空
	 * 
	 * @param obj
	 * @param message
	 */
	public static void notNull(Object obj, String message) {
		notNull(obj, message, null);
	}
	
	/**
	 * 断言不为空
	 * 
	 * @param obj
	 */
	public static void notNull(Object obj) {
		notNull(obj, null);
	}
	
	/**
	 * 断言字符串为空
	 * 
	 * @param str
	 * @param message
	 * @param key
	 */
	public static void isEmpty(String str, String message, String key) {
		if (!Strings.empty(str)) {
			Logger.warn("%s is not empty", str);
			throw new ServiceException(message, key);
		}
	}
	
	/**
	 * 断言字符串为空
	 * 
	 * @param str
	 * @param message
	 */
	public static void isEmpty(String str, String message) {
		isEmpty(str, message, null);
	}
	
	/**
	 * 断言字符串为空
	 * 
	 * @param str
	 */
	public static void isEmpty(String str) {
		isEmpty(str, null);
	}
	
	/**
	 * 断言字符串不为空
	 * 
	 * @param str
	 * @param message
	 * @param key
	 */
	public static void notEmpty(String str, String message, String key) {
		if (Strings.empty(str)) {
			Logger.warn("%s is empty", str);
			throw new ServiceException(message, key); 
		}
	}
	
	/**
	 * 断言字符串不为空
	 * 
	 * @param str
	 * @param message
	 */
	public static void notEmpty(String str, String message) {
		notEmpty(str, message, null);
	}
	
	/**
	 * 断言字符串不为空
	 * 
	 * @param str
	 */
	public static void notEmpty(String str) {
		notEmpty(str, null);
	}
	
	/**
	 * 判断为真
	 * 
	 * @param bool
	 * @param message
	 * @param key
	 */
	public static void isTrue(Boolean bool, String message, String key) {
		if (!bool) {
			Logger.warn("it is false.");
			throw new ServiceException(message, key);
		}
	}
	
	/**
	 * 判断为真
	 * 
	 * @param bool
	 * @param message
	 */
	public static void isTrue(Boolean bool, String message) {
		isTrue(bool, message, null);
	}
	
	/**
	 * 判断为真
	 * 
	 * @param bool
	 */
	public static void isTrue(Boolean bool) {
		isTrue(bool, null);
	}
	
	/**
	 * 断言相等
	 * 
	 * @param one
	 * @param another
	 * @param message
	 * @param key
	 */
	public static void equals(Object one, Object another, String message, String key) {
		if (!Objects.equals(one, another)) {
			Logger.warn("%s is not equals %s", one, another);
			throw new ServiceException(message, key);
		}
	}
	
	/**
	 * 断言相等
	 * 
	 * @param one
	 * @param another
	 */
	public static void equals(Object one, Object another, String message) {
		equals(one, another, message, null);
	}
	
	/**
	 * 断言相等
	 * 
	 * @param one
	 * @param another
	 */
	public static void equals(Object one, Object another) {
		equals(one, another, null);
	}
	
	/**
	 * 断言不相等
	 * 
	 * @param one
	 * @param another
	 * @param message
	 * @param key
	 */
	public static void notEquals(Object one, Object another, String message, String key) {
		if (Objects.equals(one, another)) {
			Logger.warn("%s is equals %s", one, another);
			throw new ServiceException(message, key);
		}
	}
	
	/**
	 * 断言不相等
	 * 
	 * @param one
	 * @param another
	 * @param message
	 */
	public static void notEquals(Object one, Object another, String message) {
		notEquals(one, another, message, null);
	}
	
	/**
	 * 断言不相等
	 * 
	 * @param one
	 * @param another
	 */
	public static void notEquals(Object one, Object another) {
		notEquals(one, another, null);
	}
}
