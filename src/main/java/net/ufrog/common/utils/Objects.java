package net.ufrog.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	 * 判断对象是否相同
	 * 
	 * @param one
	 * @param another
	 * @return
	 */
	public static boolean equals(Object one, Object another) {
		return (one == another || (one != null && one.equals(another)));
	}
	
	/**
	 * 生成列表
	 * 
	 * @param ts
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> list(T... ts) {
		List<T> list = new ArrayList<T>(ts.length);
		for (T t: ts) list.add(t);
		return list;
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
