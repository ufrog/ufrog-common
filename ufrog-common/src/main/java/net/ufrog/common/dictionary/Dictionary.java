package net.ufrog.common.dictionary;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import net.ufrog.common.Logger;
import net.ufrog.common.cache.Cache;
import net.ufrog.common.dictionary.annotation.Element;
import net.ufrog.common.utils.Strings;

/**
 * 字典
 * 
 * @author ultrafrog
 * @version 1.0, 2012-11-29
 * @since 1.0
 */
public class Dictionary {

	/** 缓存前缀 */
	private static final String SUFFIX = ".DICT";
	
	/**
	 * 读取字典元素映射
	 * 
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<Object, String> getElements(Class<?> type) {
		// 判断是否已经缓存相关类型
		// 若尚未缓存则处理类型并缓存
		if (Cache.get(type.getName() + SUFFIX) == null) {
			// 初始化
			Logger.info("cache dictionary '%s' start...", type.getName());
			Map<Object, String> elems = new LinkedHashMap<Object, String>();
			Field[] fields = type.getDeclaredFields();
			
			// 遍历处理类型字段
			for (Field f: fields) {
				// 仅处理注解为元素的字段
				Element elem = f.getAnnotation(Element.class);
				if (elem != null) {
					try {
						Object key = f.get(type);
						String value = elem.value();
						elems.put(key, value);
						Logger.info("cache dictionary field '%s: %s' from '%s'", key, value, type.getName());
					} catch (IllegalArgumentException e) {
						Logger.error(e.getMessage());
					} catch (IllegalAccessException e) {
						Logger.error(e.getMessage());
					}
				}
			}
			
			// 缓存数据
			Cache.add(type.getName() + SUFFIX, elems);
			Logger.info("cache dictionary '%s' completed!", type.getName());
		}
		
		// 返回结果
		return Cache.get(type.getName() + SUFFIX, Map.class);
	}
	
	/**
	 * 读取字典元素映射
	 * 
	 * @param type
	 * @param excludes
	 * @return
	 */
	public static Map<Object, String> getElements(Class<?> type, Object... excludes) {
		Map<Object, String> copy = new LinkedHashMap<Object, String>(getElements(type));
		for (Object e: excludes) {
			copy.remove(e);
		}
		return copy;
	}
	
	/**
	 * 读取字典元素名称
	 * 
	 * @param key
	 * @param type
	 * @return
	 */
	public static String getName(Object key, Class<?> type) {
		return getElements(type).get(key);
	}
	
	/**
	 * 读取字典元素名称
	 * 
	 * @param key
	 * @param defaultValue
	 * @param type
	 * @return
	 */
	public static String getName(Object key, String defaultValue, Class<?> type) {
		return Strings.empty(getName(key, type), defaultValue);
	}
}
