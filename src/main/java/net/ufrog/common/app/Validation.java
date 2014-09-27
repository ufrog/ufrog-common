package net.ufrog.common.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import net.ufrog.common.utils.Objects;
import net.ufrog.common.utils.Strings;

/**
 * 验证
 * 
 * @author ultrafrog
 * @version 1.0, 2014-08-24
 * @since 1.0
 */
public class Validation {

	/** 错误映射 */
	private Map<String, List<Error>> errors;
	
	/** 构造函数 */
	public Validation() {
		errors = new HashMap<String, List<Error>>();
	}
	
	/**
	 * 添加错误
	 * 
	 * @param error
	 */
	public void addError(Error error) {
		if (!errors.containsKey(error.getName())) {
			errors.put(error.getName(), new ArrayList<Error>());
		}
		errors.get(error.getName()).add(error);
	}
	
	/**
	 * 读取错误映射
	 * 
	 * @return
	 * @see #errors
	 */
	public Map<String, List<Error>> getErrorMap() {
		return errors;
	}
	
	/**
	 * 读取错误列表
	 * 
	 * @param name
	 * @return
	 */
	public List<Error> getErrors(String name) {
		return errors.get(name);
	}
	
	/**
	 * 读取所有错误
	 * 
	 * @return
	 */
	public List<Error> getAllErrors() {
		List<Error> errs = new ArrayList<Error>();
		for (Entry<String, List<Error>> entry: errors.entrySet()) {
			errs.addAll(entry.getValue());
		}
		return errs;
	}
	
	/**
	 * 判断是否存在错误
	 * 
	 * @return
	 */
	public Boolean hasErrors() {
		return (errors.size() > 0);
	}
	
	/**
	 * 读取当前验证
	 * 
	 * @return
	 */
	public static Validation current() {
		return App.current().getValidation();
	}
	
	/**
	 * 添加错误
	 * 
	 * @param error
	 */
	public static Error add(Error error) {
		current().addError(error);
		return error;
	}
	
	/**
	 * 添加错误
	 * 
	 * @param name
	 * @param message
	 * @param args
	 */
	public static Error add(String name, String message, Object... args) {
		return add(new Error(message, name, args));
	}
	
	/**
	 * 判断字符串为空
	 * 
	 * @param value
	 * @param name
	 * @param message
	 * @param args
	 * @return
	 */
	public static Error isEmpty(String value, String name, String message, Object... args) {
		if (!Strings.empty(value)) {
			return add(message, name, args);
		}
		return null;
	}
	
	/**
	 * 判断字符串非空
	 * 
	 * @param value
	 * @param name
	 * @param message
	 * @param args
	 * @return
	 */
	public static Error notEmpty(String value, String name, String message, Object... args) {
		if (Strings.empty(value)) {
			return add(message, name, args);
		}
		return null;
	}
	
	/**
	 * 判断对象为空
	 * 
	 * @param value
	 * @param name
	 * @param message
	 * @param args
	 * @return
	 */
	public static Error isNull(Object value, String name, String message, Object... args) {
		if (value != null) {
			return add(message, name, args);
		}
		return null;
	}
	
	/**
	 * 判断对象非空
	 * 
	 * @param value
	 * @param name
	 * @param message
	 * @param args
	 * @return
	 */
	public static Error notNull(Object value, String name, String message, Object... args) {
		if (value == null) {
			return add(message, name, args);
		}
		return null;
	}
	
	/**
	 * 判断对象相同
	 * 
	 * @param value
	 * @param excepted
	 * @param name
	 * @param message
	 * @param args
	 * @return
	 */
	public static Error equals(Object value, Object excepted, String name, String message, Object... args) {
		if (!Objects.equals(value, excepted)) {
			return add(name, message, args);
		}
		return null;
	}
	
	/**
	 * 判断对象不同
	 * 
	 * @param value
	 * @param excepted
	 * @param name
	 * @param message
	 * @param args
	 * @return
	 */
	public static Error notEquals(Object value, Object excepted, String name, String message, Object... args) {
		if (Objects.equals(value, excepted)) {
			return add(name, message, args);
		}
		return null;
	}
	
	/**
	 * 判断真
	 * 
	 * @param bool
	 * @param name
	 * @param message
	 * @param args
	 * @return
	 */
	public static Error isTrue(Boolean bool, String name, String message, Object... args) {
		if (!bool) {
			return add(name, message, args);
		}
		return null;
	}
	
	/**
	 * 判断假
	 * 
	 * @param bool
	 * @param name
	 * @param message
	 * @param args
	 * @return
	 */
	public static Error isFalse(Boolean bool, String name, String message, Object... args) {
		if (bool) {
			return add(name, message, args);
		}
		return null;
	}
	
	/**
	 * 判断正则
	 * 
	 * @param value
	 * @param regex
	 * @param name
	 * @param message
	 * @param args
	 * @return
	 */
	public static Error regex(String value, String regex, String name, String message, Object... args) {
		if (!Pattern.compile(regex).matcher(value).find()) {
			return add(name, message, args);
		}
		return null;
	}
	
	/**
	 * 所有错误
	 * 
	 * @return
	 */
	public static Map<String, List<Error>> errors() {
		return Validation.current().getErrorMap();
	}
	
	/**
	 * 验证错误
	 * 
	 * @author ultrafrog
	 * @version 1.0, 2014-08-24
	 * @since 1.0
	 */
	public static class Error {
		
		/** 错误信息 */
		private String message;
		
		/** 名称<br>对应输入项 */
		private String name;
		
		/** 消息参数 */
		private Object[] args;
		
		/** 构造函数 */
		public Error() {}
		
		/**
		 * 构造函数
		 * 
		 * @param message
		 * @param name
		 */
		public Error(String message, String name) {
			this.message = message;
			this.name = name;
		}
		
		/**
		 * 构造函数
		 * 
		 * @param message
		 * @param name
		 * @param args
		 */
		public Error(String message, String name, Object... args) {
			this(message, name);
			this.args = args;
		}
		
		/**
		 * 读取错误信息
		 * 
		 * @return
		 * @see #message
		 */
		public String getMessage() {
			return App.message(message);
		}

		/**
		 * 设置错误信息
		 * 
		 * @param message
		 * @see #message
		 */
		public void setMessage(String message) {
			this.message = message;
		}

		/**
		 * 读取键值
		 * 
		 * @return
		 * @see #name
		 */
		public String getName() {
			return name;
		}

		/**
		 * 设置键值
		 * 
		 * @param name
		 * @see #name
		 */
		public void setName(String name) {
			this.name = name;
		}
		
		/**
		 * @return
		 */
		public String message() {
			return App.message(getMessage(), args);
		}
	}
}
