package net.ufrog.common;

import net.ufrog.common.exception.ServiceException;

/**
 * 断言
 * 
 * @author ultrafrog
 * @version 1.0, 2014-03-04
 * @since 1.0
 */
public abstract class Assert {

	/**
	 * 断言相等
	 * 
	 * @param one
	 * @param another
	 * @param message
	 * @param key
	 */
	public static void equals(Object one, Object another, String message, String key) {
		if (one != another && (one == null || !one.equals(another))) {
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
}
