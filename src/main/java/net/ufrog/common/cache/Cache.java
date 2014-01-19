package net.ufrog.common.cache;

import java.util.Map;

/**
 * 缓存实现接口
 * 
 * @author ultrafrog
 * @version 1.0, 2012-11-30
 * @since 1.0
 */
public interface Cache {

	/**
	 * @param key
	 * @param value
	 * @param expiration
	 */
	void add(String key, Object value, int expiration);
	
	/**
	 * @param key
	 * @param value
	 * @param expiration
	 * @return
	 */
	boolean safeAdd(String key, Object value, int expiration);
	
	/**
	 * @param key
	 * @param value
	 * @param expiration
	 */
	void set(String key, Object value, int expiration);
	
	/**
	 * @param key
	 * @param value
	 * @param expiration
	 * @return
	 */
	boolean safeSet(String key, Object value, int expiration);
	
	/**
	 * @param key
	 * @param value
	 * @param expiration
	 */
	void replace(String key, Object value, int expiration);
	
	/**
	 * @param key
	 * @param value
	 * @param expiration
	 * @return
	 */
	boolean safeReplace(String key, Object value, int expiration);
	
	/**
	 * @param key
	 * @return
	 */
	Object get(String key);
	
	/**
	 * @param keys
	 * @return
	 */
	Map<String, Object> get(String... keys);
	
	/**
	 * @param key
	 * @param by
	 * @return
	 */
	long incr(String key, int by);
	
	/**
	 * @param key
	 * @param by
	 * @return
	 */
	long decr(String key, int by);
	
	/**
	 * @param key
	 * @return
	 */
	void delete(String key);
	
	/**
	 * @param key
	 * @return
	 */
	boolean safeDelete(String key);
	
	/**
	 * @return
	 */
	void clear();
	
	/**
	 * 
	 */
	void stop();
}
