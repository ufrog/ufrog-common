package net.ufrog.common;

import org.slf4j.LoggerFactory;

/**
 * 日志
 * 
 * @author ultrafrog
 * @version 1.0, 2012-11-29
 * @since 1.0
 */
public class Logger {

	protected static org.slf4j.Logger logger = LoggerFactory.getLogger(Property.getValue("app.logger.name", "app"));
	
	protected static Boolean caller = Boolean.FALSE;
	
	/**
	 * @return
	 */
	public static boolean isTraceEnabled() {
		return getLogger().isTraceEnabled();
	}
	
	/**
	 * @param msg
	 */
	public static void trace(String msg) {
		if (!isTraceEnabled()) return;
		getLogger().trace(msg);
	}
	
	/**
	 * @param msg
	 * @param objs
	 */
	public static void trace(String msg, Object... objs) {
		if (!isTraceEnabled()) return;
		getLogger().trace(String.format(msg, objs));
	}
	
	/**
	 * @param msg
	 * @param t
	 */
	public static void trace(String msg, Throwable t) {
		if (!isTraceEnabled()) return;
		getLogger().trace(msg, t);
	}
	
	/**
	 * @return
	 */
	public static boolean isDebugEnabled() {
		return getLogger().isDebugEnabled();
	}
	
	/**
	 * @param msg
	 */
	public static void debug(String msg) {
		if (!isDebugEnabled()) return;
		getLogger().debug(msg);
	}
	
	/**
	 * @param msg
	 * @param objs
	 */
	public static void debug(String msg, Object... objs) {
		if (!isDebugEnabled()) return;
		getLogger().debug(String.format(msg, objs));
	}
	
	/**
	 * @param msg
	 * @param t
	 */
	public static void debug(String msg, Throwable t) {
		if (!isDebugEnabled()) return;
		getLogger().debug(msg, t);
	}
	
	/**
	 * @return
	 */
	public static boolean isInfoEnabled() {
		return getLogger().isInfoEnabled();
	}
	
	/**
	 * @param msg
	 */
	public static void info(String msg) {
		if (!isInfoEnabled()) return;
		getLogger().info(msg);
	}
	
	/**
	 * @param msg
	 * @param objs
	 */
	public static void info(String msg, Object... objs) {
		if (!isInfoEnabled()) return;
		getLogger().info(String.format(msg, objs));
	}
	
	/**
	 * @param msg
	 * @param t
	 */
	public static void info(String msg, Throwable t) {
		if (!isInfoEnabled()) return;
		getLogger().info(msg, t);
	}
	
	/**
	 * @return
	 */
	public static boolean isWarnEnabled() {
		return getLogger().isWarnEnabled();
	}
	
	/**
	 * @param msg
	 */
	public static void warn(String msg) {
		if (!isWarnEnabled()) return;
		getLogger().warn(msg);
	}
	
	/**
	 * @param msg
	 * @param objs
	 */
	public static void warn(String msg, Object... objs) {
		if (!isWarnEnabled()) return;
		getLogger().warn(String.format(msg, objs));
	}
	
	/**
	 * @param msg
	 * @param t
	 */
	public static void warn(String msg, Throwable t) {
		if (!isWarnEnabled()) return;
		getLogger().warn(msg, t);
	}
	
	/**
	 * @return
	 */
	public static boolean isErrorEnabled() {
		return getLogger().isErrorEnabled();
	}
	
	/**
	 * @param msg
	 */
	public static void error(String msg) {
		if (!isErrorEnabled()) return;
		getLogger().error(msg);
	}
	
	/**
	 * @param msg
	 * @param objs
	 */
	public static void error(String msg, Object... objs) {
		if (!isErrorEnabled()) return;
		getLogger().error(String.format(msg, objs));
	}
	
	/**
	 * @param msg
	 * @param t
	 */
	public static void error(String msg, Throwable t) {
		if (!isErrorEnabled()) return;
		getLogger().error(msg, t);
	}
	
	/**
	 * @param caller
	 * @return
	 */
	public static org.slf4j.Logger getLogger(Boolean caller) {
		if (caller) {
			return LoggerFactory.getLogger(Caller.caller(3).getClassName());
		}
		return logger;
	}
	
	/**
	 * @return
	 */
	public static org.slf4j.Logger getLogger() {
		return getLogger(caller);
	}
}