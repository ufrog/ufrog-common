package net.ufrog.common.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
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
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return 
	 */
	public static boolean empty(String str) {
		if (null == str || str.length() == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断字符串是否为空<br>
	 * 如果字符串为空则返回默认值
	 * 
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static String empty(String str, String defaultValue) {
		return empty(str) ? defaultValue : str;
	}
	
	/**
	 * 判断字符串是否为空白
	 * 
	 * @param str
	 * @return
	 */
	public static boolean blank(String str) {
		if (null == str || str.trim().length() == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断字符串是否为空白<br>
	 * 如果字符串为空白则返回默认值
	 * 
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static String blank(String str, String defaultValue) {
		return blank(str) ? defaultValue : str;
	}
	
	/**
	 * 判断两个字符串是否相同
	 * 
	 * @param one
	 * @param another
	 * @return	
	 * @see java.lang.String#equals(Object)
	 */
	public static boolean equals(String one, String another) {
		if (one == another || (one != null && one.equals(another))) {
			return true;
		}
		return false;
	}
	
	/**
	 * 截断字符串
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
	 * 生成随机字符串
	 * 
	 * @param length
	 * @param set
	 * @param sets
	 * @return
	 */
	public static String random(int length, String set, String... sets) {
		// 初始化
		StringBuffer result = buffer(length);
		StringBuffer buffer = buffer(sets).append(set);
		Random random = new Random();
		
		// 生成随机字符并组成字符串
		for (int i = 0; i < length; i++) {
			result.append(buffer.charAt(random.nextInt(buffer.length())));
		}
		
		// 返回结果
		return result.toString();
	}
	
	/**
	 * 拆分字符串
	 * 
	 * @param str
	 * @param regex
	 * @return
	 */
	public static List<String> explode(String str, String regex) {
		return Arrays.asList(str.split(regex));
	}
	
	/**
	 * 合并集合成字符串
	 * 
	 * @param objs
	 * @param symbol
	 * @return
	 */
	public static String implode(Collection<Object> objs, String symbol) {
		StringBuffer buffer = buffer(50);
		for (Object obj: objs) {
			buffer.append(symbol).append(obj.toString());
		}
		return buffer.substring(symbol.length());
	}
	
	/**
	 * 转换字符串数组
	 * 
	 * @param	strs
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
		for (String s: strs) {
			buffer.append(s);
		}
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
