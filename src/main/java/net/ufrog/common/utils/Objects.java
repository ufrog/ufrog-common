package net.ufrog.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 对象工具
 * 
 * @author ultrafrog
 * @version 1.0, 2014-02-10
 * @since 1.0
 */
public abstract class Objects {

	/**
	 * 强制转换
	 * 
	 * @param obj
	 * @param clazz
	 * @return
	 */
	public static <T> T cast(Object obj, Class<T> clazz) {
		return clazz.cast(obj);
	}
	
	/**
	 * 生成映射表
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static <K, V> Map<K, V> map(K key, V value) {
		Map<K, V> map = new HashMap<K, V>();
		map.put(key, value);
		return map;
	}
}
