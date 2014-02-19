package net.ufrog.common.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求参数
 * 
 * @author ultrafrog
 * @version 1.0, 2014-02-19
 * @since 1.0
 */
public class RequestParam {

	/** 参数 */
	private Map<String, String[]> param;
	
	/** 构造函数 */
	protected RequestParam() {}
	
	/**
	 * 构造函数
	 * 
	 * @param request
	 */
	public RequestParam(HttpServletRequest request) {
		param = request.getParameterMap();
	}
	
	/**
	 * 读取参数
	 * 
	 * @param key
	 * @return
	 */
	public String getValue(String key) {
		if (param.containsKey(key)) return param.get(key)[0];
		return null;
	}
	
	/**
	 * 读取参数数组
	 * 
	 * @param key
	 * @return
	 */
	public String[] getValues(String key) {
		if (param.containsKey(key)) return param.get(key);
		return null;
	}
}
