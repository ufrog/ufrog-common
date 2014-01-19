package net.ufrog.common.exception;

import net.ufrog.common.utils.Strings;
import net.ufrog.common.utils.Strings.StringSet;

/**
 * 业务异常
 *
 * @author ultrafrog
 * @version 1.0, 2013-4-7
 * @since 1.0
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID 	= 3212842635807077979L;
	private static final String EX_DEFAULT		= "exception.default";
	
	/** 代码 */
	private String code;
	
	/** 消息键值 */
	private String key;
	
	/**
	 * 构造函数
	 */
	public ServiceException() {
		super();
		generateCode();
	}
	
	/**
	 * 构造函数
	 * 
	 * @param message
	 */
	public ServiceException(String message) {
		super(message);
		generateCode();
	}
	
	/**
	 * 构造函数
	 * 
	 * @param message
	 * @param cause
	 */
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
		generateCode();
	}
	
	/**
	 * 构造函数
	 * 
	 * @param message
	 * @param key
	 */
	public ServiceException(String message, String key) {
		this(message);
		this.key = key;
	}
	
	/**
	 * 构造函数
	 * 
	 * @param message
	 * @param key
	 * @param cause
	 */
	public ServiceException(String message, String key, Throwable cause) {
		this(message, cause);
		this.key = key;
	}
	
	/**
	 * 读取代码
	 * 
	 * @return
	 * @see #code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 读取键值
	 * 
	 * @return
	 * @see #key
	 */
	public String getKey() {
		return Strings.empty(key, EX_DEFAULT);
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getLocalizedMessage()
	 */
	@Override
	public String getLocalizedMessage() {
		return this.code + " " + this.getMessage();
	}
	
	/**
	 * 生成代码
	 */
	protected void generateCode() {
		this.code = "@" + Strings.random(16, StringSet.NUMERIC, StringSet.LOWER_ALPHABET, StringSet.UPPER_ALPHABET);
	}
}
