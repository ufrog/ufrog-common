package net.ufrog.common.dictionary;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import net.ufrog.common.Logger;
import net.ufrog.common.cache.Caches;
import net.ufrog.common.utils.Strings;

/**
 * 字典工具
 * 
 * @author ultrafrog
 * @version 1.0, 2012-11-29
 * @since 1.0
 */
public class Dicts {

	private static final String CACHE_PERFIX = "net.ufrog.common.cache.dict.";
	
	/**
	 * 元素映射
	 * 
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<Object, String> elements(Class<?> type) {
		// 从缓存中读取数据
		Map<Object, String> elements = Caches.get(CACHE_PERFIX + type.getName(), Map.class);
		
		// 判断数据是否已经缓存
		// 若尚未缓存则加载并进行缓存
		if (elements == null) {
			// 初始化
			Logger.info("cache dictionary '%s' start...", type.getName());
			elements = new LinkedHashMap<Object, String>();
			Field[] fields = type.getDeclaredFields();
			
			// 遍历并处理字段
			for (Field field: fields) {
				Element element = field.getAnnotation(Element.class);
				if (element != null) {
					try {
						Object key = field.get(type);
						String value = element.value();
						elements.put(key, value);
						Logger.info("cache dictionary '%s' field '%s: %s'.", type.getName(), key, value);
					} catch (IllegalArgumentException e) {
						Logger.error(e.getMessage(), e);
					} catch (IllegalAccessException e) {
						Logger.error(e.getMessage(), e);
					}
				}
			}
			
			// 缓存数据
			Caches.add(CACHE_PERFIX + type.getName(), elements);
			Logger.info("cache dictionary '%s' completed!", type.getName());
		}
		
		// 返回结果
		return elements;
	}
	
	/**
	 * 元素映射
	 * 
	 * @param type
	 * @param excludes
	 * @return
	 */
	public static Map<Object, String> elements(Class<?> type, Object... excludes) {
		Map<Object, String> copy = new LinkedHashMap<Object, String>(elements(type));
		for (Object e: excludes) copy.remove(e);
		return copy;
	}
	
	/**
	 * 元素名称
	 * 
	 * @param key
	 * @param type
	 * @return
	 */
	public static String name(Object key, Class<?> type) {
		return elements(type).get(key);
	}
	
	/**
	 * 读取字典元素名称
	 * 
	 * @param key
	 * @param defaultValue
	 * @param type
	 * @return
	 */
	public static String name(Object key, String defaultValue, Class<?> type) {
		return Strings.empty(name(key, type), defaultValue);
	}
	
	/**
	 * 布尔值
	 * 
	 * @author ultrafrog
	 * @version 0.1, 2014-01-17
	 * @since 0.1
	 */
	public static final class Bool {
		
		@Element("否")
		public static final String FALSE	= "00";
		
		@Element("是")
		public static final String TRUE		= "01";
	}
}
