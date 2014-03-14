package net.ufrog.common.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 字符串工具
 * 
 * @author ultrafrog
 * @version	1.0, 2011-12-19
 * @since 1.0
 */
public abstract class Strings {

	/**
	 * 判断是否为空
	 * 
	 * @param str
	 * @return 
	 */
	public static boolean empty(String str) {
		return (str == null || str.length() == 0); 
	}
	
	/**
	 * 判断是否为空<br>
	 * 如果为空则返回默认值
	 * 
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static String empty(String str, String defaultValue) {
		return empty(str) ? defaultValue : str;
	}
	
	/**
	 * 判断是否为空白
	 * 
	 * @param str
	 * @return
	 */
	public static boolean blank(String str) {
		return (str == null || str.trim().length() == 0);
	}
	
	/**
	 * 判断是否为空白<br>
	 * 如果为空白则返回默认值
	 * 
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static String blank(String str, String defaultValue) {
		return blank(str) ? defaultValue : str;
	}
	
	/**
	 * 判断是否相同
	 * 
	 * @param one
	 * @param another
	 * @return	
	 * @see java.lang.String#equals(Object)
	 */
	public static boolean equals(String one, String another) {
		return (one == another || (one != null && one.equals(another)));
	}
	
	/**
	 * 随机值
	 * 
	 * @param length
	 * @param set
	 * @param sets
	 * @return
	 */
	public static String random(int length, String set, String... sets) {
		StringBuilder result = builder(length);
		StringBuilder builder = builder(sets).append(set);
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			result.append(builder.charAt(random.nextInt(builder.length())));
		}
		return result.toString();
	}

	/**
	 * 截断
	 * 
	 * @param str
	 * @param len
	 * @param suffix
	 * @return
	 */
	public static String truncate(String str, int len, String suffix) {
		if (!empty(str) && str.length() > len) {
			str = str.substring(0, len);
			str = str + empty(suffix, "");
		}
		return str;
	}
	
	/**
	 * 拆分
	 * 
	 * @param str
	 * @param regex
	 * @return
	 */
	public static List<String> explode(String str, String regex) {
		return Arrays.asList(str.split(regex));
	}
	
	/**
	 * 合并
	 * 
	 * @param objs
	 * @param symbol
	 * @return
	 */
	public static String implode(Collection<Object> objs, String symbol) {
		StringBuffer buffer = buffer();
		for (Object obj: objs) buffer.append(symbol).append(obj.toString());
		return buffer.substring(symbol.length());
	}
	
	/**
	 * 转换数组
	 * 
	 * @param strs
	 * @return
	 */
	public static String[] array(String... strs) {
		return strs;
	}
	
	/**
	 * 创建 <code>StringBuffer</code> 实例
	 * 
	 * @param capacity
	 * @return
	 * @see java.lang.StringBuffer
	 */
	public static StringBuffer buffer(int capacity) {
		return new StringBuffer(capacity);
	}
	
	/**
	 * 创建 <code>StringBuffer</code> 实例
	 * 
	 * @param str
	 * @return
	 * @see java.lang.StringBuffer
	 */
	public static StringBuffer buffer(String str) {
		return buffer((str.length() + 1) * 2).append(str);
	}
	
	/**
	 * 创建 <code>StringBuffer</code> 实例
	 * 
	 * @return
	 * @see java.lang.StringBuffer
	 */
	public static StringBuffer buffer() {
		return buffer(16);
	}
	
	/**
	 * 创建 <code>StringBuffer</code> 实例
	 * 
	 * @param strs
	 * @return
	 */
	public static StringBuffer buffer(String... strs) {
		StringBuffer buffer = buffer();
		for (String s: strs) buffer.append(s);
		return buffer;
	}
	
	/**
	 * 创建 <code>StringBuilder</code> 实例
	 * 
	 * @param capacity
	 * @return
	 * @see java.lang.StringBuilder
	 */
	public static StringBuilder builder(int capacity) {
		return new StringBuilder(capacity);
	}
	
	/**
	 * 创建 <code>StringBuilder</code> 实例
	 * 
	 * @param str
	 * @return
	 * @see java.lang.StringBuilder
	 */
	public static StringBuilder builder(String str) {
		return builder((str.length() + 1) * 2).append(str);
	}
	
	/**
	 * 创建 <code>StringBuilder</code> 实例
	 * 
	 * @return
	 * @see java.lang.StringBuilder
	 */
	public static StringBuilder builder() {
		return builder(16);
	}
	
	/**
	 * 创建 <code>StringBuilder</code> 实例
	 * 
	 * @param strs
	 * @return
	 */
	public static StringBuilder builder(String... strs) {
		StringBuilder builder = builder();
		for (String s: strs) builder.append(s);
		return builder;
	}
	
	/**
	 * 转换成字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		// 根据类型进行判断处理
		if (obj instanceof String) {
			String str = String.class.cast(obj);
			if (str.length() > 100) return str.length() + " chars string.";
		} else if (obj instanceof List) {
			List<?> list = List.class.cast(obj);
			if (list.size() > 10) return list.size() + " size list.";
		} else if (obj instanceof Map) {
			Map<?, ?> map = Map.class.cast(obj);
			if (map.size() > 5) return map.size() + " size map.";
		}
		
		// 返回默认结果
		return String.valueOf(obj);
	}
	
	/**
	 * 字符串集合
	 * 
	 * @author ultrafrog
	 * @version 1.0, 2011-12-30
	 * @since 1.0
	 */
	public static final class StringSet {
		
		/** 数字 */
		public static final String NUMERIC			= "1234567890";
		
		/** 小写字母 */
		public static final String LOWER_ALPHABET 	= "abcdefghijklmnopqrstuvwxyz";
		
		/** 大写字母 */
		public static final String UPPER_ALPHABET 	= "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		/** 符号 */
		public static final String SYMBOL			= "!@#$%^&*_+-=|:;?";
	}
}
