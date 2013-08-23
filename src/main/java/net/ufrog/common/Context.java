package net.ufrog.common;

import java.util.Locale;

/**
 * 上下文抽象
 *
 * @author ultrafrog
 * @version 1.0, 2013-4-19
 * @since 1.0
 */
public abstract class Context {

	private static ThreadLocal<Context> current = new ThreadLocal<Context>();
	
	/**
	 * 读取用户
	 * 
	 * @return
	 */
	public abstract ContextUser getUser();
	
	/**
	 * 设置用户
	 * 
	 * @param user
	 */
	public abstract void setUser(ContextUser user);
	
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
	public abstract String nextToken();
	
	/**
	 * 读取位置
	 * 
	 * @return
	 */
	public abstract Locale getLocale();
	
	/**
	 * 读取消息
	 * 
	 * @param key
	 * @param args
	 * @return
	 */
	public String getMessage(String key, Object... args) {
		return Messages.get(getLocale(), key, args);
	}
	
	/**
	 * 当前线程实例
	 * 
	 * @return
	 */
	public static Context current() {
		return current.get();
	}
	
	/**
	 * 当前线程实例
	 * 
	 * @param requiredType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Context> T current(Class<T> requiredType) {
		return (T) current();
	}
	
	/**
	 * 设置线程实例
	 * 
	 * @param context
	 */
	public static void set(Context context) {
		current.set(context);
	}

	/**
	 * 读取用户编号
	 * 
	 * @return
	 */
	public static String getUserId() {
		try {
			return Context.current().getUser().getId();
		} catch (Exception e) {
			Logger.debug(e.getMessage());
			return null;
		}
	}

	/**
	 * 读取用户账号
	 * 
	 * @return
	 */
	public static String getUserAccount() {
		try {
			return Context.current().getUser().getAccount();
		} catch (Exception e) {
			Logger.debug(e.getMessage());
			return null;
		}
	}

	/**
	 * 读取用户名称
	 * 
	 * @return
	 */
	public static String getUserName() {
		try {
			return Context.current().getUser().getName();
		} catch (Exception e) {
			Logger.debug(e.getMessage());
			return null;
		}
	}
	
	/**
	 * 读取令牌
	 * 
	 * @return
	 */
	public static String token() {
		return token(false);
	}
	
	/**
	 * 读取令牌
	 * 
	 * @param next
	 * @return
	 */
	public static String token(boolean next) {
		return next ? Context.current().nextToken() : Context.current().getToken();
	}
	
	/**
	 * 消息
	 * 
	 * @param key
	 * @param args
	 * @return
	 */
	public static String message(String key, Object... args) {
		return Context.current().getMessage(key, args);
	}
}
