package net.ufrog.common.cache;

import java.io.Serializable;
import java.util.Map;

import net.ufrog.common.Logger;
import net.ufrog.common.app.App;
import net.ufrog.common.exception.ServiceException;
import net.ufrog.common.utils.Calendars;

/**
 * 缓存工具
 * 
 * @author ultrafrog
 * @version 1.0, 2012-11-30
 * @since 1.0
 */
public class Caches {

	private static final String CONF_CACHE_TYPE = App.config("cache.type", "ehcache");
	
	private static Cache cacheImpl;
	
	/**
	 * 添加数据
	 * 
	 * @param key
	 * @param value
	 * @param expiration
	 */
	public static void add(String key, Object value, int expiration) {
		checkSerializable(value);
		Logger.info("add '%s - %s' into cache for '%s' seconds!", key, value, expiration);
		getImpl().add(key, value, expiration);
	}

	/**
	 * 添加数据
	 * 
	 * @param key
	 * @param value
	 * @param duration
	 */
	public static void add(String key, Object value, String duration) {
		add(key, value, Calendars.parseDuration(duration));
	}

	/**
	 * 添加数据
	 * 
	 * @param key
	 * @param value
	 */
	public static void add(String key, Object value) {
		add(key, value, "7d");
	}

	/**
	 * 安全添加数据
	 * 
	 * @param key
	 * @param value
	 * @param expiration
	 * @return
	 */
	public static boolean safeAdd(String key, Object value, int expiration) {
		checkSerializable(value);
		Logger.info("add '%s - %s' into cache for '%s' seconds!", key, value, expiration);
		if (getImpl().safeAdd(key, value, expiration)) {
			return true;
		}
		Logger.warn("add '%s - %s' into cache failure!", key, value);
		return false;
	}

	/**
	 * 安全添加数据
	 * 
	 * @param key
	 * @param value
	 * @param duration
	 * @return
	 */
	public static boolean safeAdd(String key, Object value, String duration) {
		return safeAdd(key, value, Calendars.parseDuration(duration));
	}

	/**
	 * 安全添加数据
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean safeAdd(String key, Object value) {
		return safeAdd(key, value, "7d");
	}

	/**
	 * 设置数据
	 * 
	 * @param key
	 * @param value
	 * @param expiration
	 */
	public static void set(String key, Object value, int expiration) {
		checkSerializable(value);
		Logger.info("set '%s - %s' into cache for '%s' seconds!", key, value, expiration);
		getImpl().set(key, value, expiration);
	}
	
	/**
	 * 设置数据
	 * 
	 * @param key
	 * @param value
	 * @param duration
	 */
	public static void set(String key, Object value, String duration) {
		set(key, value, Calendars.parseDuration(duration));
	}
	
	/**
	 * 设置数据
	 * 
	 * @param key
	 * @param value
	 */
	public static void set(String key, Object value) {
		set(key, value, "7d");
	}
	
	/**
	 * 安全设置数据
	 * 
	 * @param key
	 * @param value
	 * @param expiration
	 * @return
	 */
	public static boolean safeSet(String key, Object value, int expiration) {
		checkSerializable(value);
		Logger.info("set '%s - %s' into cache for '%s' seconds!", key, value, expiration);
		if (getImpl().safeSet(key, value, expiration)) {
			return true;
		}
		Logger.warn("set '%s - %s' into cache failure!", key, value);
		return false;
	}
	
	/**
	 * 安全设置数据
	 * 
	 * @param key
	 * @param value
	 * @param duration
	 * @return
	 */
	public static boolean safeSet(String key, Object value, String duration) {
		return safeSet(key, value, Calendars.parseDuration(duration));
	}
	
	/**
	 * 安全设置数据
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean safeSet(String key, Object value) {
		return safeSet(key, value, "7d");
	}
	
	/**
	 * 替换数据
	 * 
	 * @param key
	 * @param value
	 * @param expiration
	 */
	public static void replace(String key, Object value, int expiration) {
		checkSerializable(value);
		Logger.info("replace '%s - %s' into cache for '%s' seconds!", key, value, expiration);
		getImpl().replace(key, value, expiration);
	}
	
	/**
	 * 替换数据
	 * 
	 * @param key
	 * @param value
	 * @param duration
	 */
	public static void replace(String key, Object value, String duration) {
		replace(key, value, Calendars.parseDuration(duration));
	}
	
	/**
	 * 替换数据
	 * 
	 * @param key
	 * @param value
	 */
	public static void replace(String key, Object value) {
		replace(key, value, "7d");
	}
	
	/**
	 * 安全替换数据
	 * 
	 * @param key
	 * @param value
	 * @param expiration
	 * @return
	 */
	public static boolean safeReplace(String key, Object value, int expiration) {
		checkSerializable(value);
		Logger.info("replace '%s - %s' into cache for '%s' seconds!", key, value, expiration);
		if (getImpl().safeReplace(key, value, expiration)) {
			return true;
		}
		Logger.warn("replace '%s - %s' into cache failure!", key, value);
		return false;
	}
	
	/**
	 * 安全替换数据
	 * 
	 * @param key
	 * @param value
	 * @param duration
	 * @return
	 */
	public static boolean safeReplace(String key, Object value, String duration) {
		return safeReplace(key, value, Calendars.parseDuration(duration));
	}
	
	/**
	 * 安全替换数据
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean safeReplace(String key, Object value) {
		return safeReplace(key, value, "7d");
	}
	
	/**
	 * 读取数据
	 * 
	 * @param key
	 * @return
	 */
	public static Object get(String key) {
		return getImpl().get(key);
	}
	
	/**
	 * 读取数据
	 * 
	 * @param key
	 * @param requiredType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(String key, Class<T> requiredType) {
		return (T) get(key);
	}
	
	/**
	 * 读取数据
	 * 
	 * @param keys
	 * @return
	 */
	public static Map<String, Object> get(String... keys) {
		return getImpl().get(keys);
	}
	
	/**
	 * 递增
	 * 
	 * @param key
	 * @param by
	 * @return
	 */
	public static long incr(String key, int by) {
		return getImpl().incr(key, by);
	}
	
	/**
	 * 递减
	 * 
	 * @param key
	 * @param by
	 * @return
	 */
	public static long decr(String key, int by) {
		return getImpl().decr(key, by);
	}
	
	/**
	 * 移除数据
	 * 
	 * @param key
	 * @return
	 */
	public static void delete(String key) {
		Logger.info("remove '%s' from cache!", key);
		getImpl().delete(key);
	}
	
	/**
	 * 安全移除数据
	 * 
	 * @param key
	 * @return
	 */
	public static boolean safeDelete(String key) {
		Logger.info("remove '%s' from cache!", key);
		if (getImpl().safeDelete(key)) {
			return true;
		}
		Logger.warn("remove '%s' from cache failure!", key);
		return false;
	}
	
	/**
	 * 清空缓存数据
	 */
	public static void clear() {
		Logger.info("clear all cache!");
		getImpl().clear();
	}
	
	/**
	 * 检查值是否序列化
	 * 
	 * @param value
	 */
	protected static void checkSerializable(Object value) {
		if (value != null && !(value instanceof Serializable)) {
			throw new ServiceException(String.format("the object '%s' is not serialized.", value), "exception.serialize");
		}
	}
	
	/**
	 * 初始化缓存
	 */
	protected static Cache getImpl() {
		if (cacheImpl == null) {
			if ("ehcache".equals(CONF_CACHE_TYPE)) {
				cacheImpl = EhCacheImpl.getInstance();
				Logger.info("ehcache is ready...");
			} else if ("memcached".equals(CONF_CACHE_TYPE)) {
				cacheImpl = MemcachedImpl.getInstance();
				Logger.info("memcached is ready...");
			}
		}
		return cacheImpl;
	}
}
