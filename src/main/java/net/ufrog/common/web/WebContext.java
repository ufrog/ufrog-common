package net.ufrog.common.web;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.ufrog.common.Context;
import net.ufrog.common.ContextUser;
import net.ufrog.common.Logger;
import net.ufrog.common.Property;
import net.ufrog.common.cache.Cache;
import net.ufrog.common.utils.Calendars;
import net.ufrog.common.utils.Codecs;
import net.ufrog.common.utils.Strings;

/**
 * 互联网上下文
 *
 * @author ultrafrog
 * @version 1.0, 2013-4-19
 * @since 1.0
 */
public class WebContext extends Context {

	protected static final String SESSION_USER		= "net.ufrog.web.session.USER";
	protected static final String SESSION_FLASH		= "net.ufrog.web.session.FLASH";
	protected static final String CACHE_COPYRIGHT	= "net.ufrog.web.cache.copyright";
	protected static final String PREFIX_TOKEN		= "TOKEN.";
	
	/** 上下文路径 */
	protected static String contextPath;
	
	/** 资源路径 */
	protected static String resourcePath;
	
	/** 图片路径 */
	protected static String imagePath;
	
	/** 脚本路径 */
	protected static String javascriptPath;
	
	/** 样式路径 */
	protected static String stylesheetPath;
	
	/** 请求 */
	protected HttpServletRequest request;
	
	/**
	 * 构造函数
	 * 
	 * @param request
	 * @param response
	 */
	protected WebContext(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		Flash.prepare(this.request);
	}
	
	/* (non-Javadoc)
	 * @see net.ufrog.common.Context#getUser()
	 */
	@Override
	public ContextUser getUser() {
		return (ContextUser) this.request.getSession().getAttribute(SESSION_USER);
	}
	
	/* (non-Javadoc)
	 * @see net.ufrog.common.Context#setUser(net.ufrog.common.ContextUser)
	 */
	public void setUser(ContextUser user) {
		if (user == null) {
			HttpSession session = this.request.getSession(false);
			if (session != null) {
				session.removeAttribute(SESSION_USER);
			}
			return;
		}
		this.request.getSession().setAttribute(SESSION_USER, user);
	}
	
	/* (non-Javadoc)
	 * @see net.ufrog.common.Context#getToken()
	 */
	@Override
	public String getToken() {
		if (Cache.get(PREFIX_TOKEN + getUser()) == null) {
			Cache.add(PREFIX_TOKEN + getUser(), Codecs.uuid());
		}
		return Cache.get(PREFIX_TOKEN + getUser(), String.class);
	}
	
	/* (non-Javadoc)
	 * @see net.ufrog.common.Context#nextToken()
	 */
	@Override
	public String nextToken() {
		Cache.set(PREFIX_TOKEN + getUser(), Codecs.uuid());
		return Cache.get(PREFIX_TOKEN + getUser(), String.class);
	}
	
	/* (non-Javadoc)
	 * @see net.ufrog.common.Context#getLocale()
	 */
	@Override
	public Locale getLocale() {
		return this.request.getLocale();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getPath();
	}
	
	/**
	 * 读取上下文路径
	 * 
	 * @return
	 */
	public String getPath() {
		return WebContext.contextPath;
	}
	
	/**
	 * 读取资源路径
	 * 
	 * @return
	 */
	public String getResource() {
		return WebContext.resourcePath;
	}
	
	/**
	 * 读取图片路径
	 * 
	 * @return
	 */
	public String getImg() {
		return WebContext.imagePath;
	}
	
	/**
	 * 读取脚本路径
	 * 
	 * @return
	 */
	public String getJs() {
		return WebContext.javascriptPath;
	}
	
	/**
	 * 读取资源路径
	 * 
	 * @return
	 */
	public String getCss() {
		return WebContext.stylesheetPath;
	}
	
	/**
	 * 读取版权信息
	 * 
	 * @return
	 */
	public String getCopyright() {
		if (Cache.get(CACHE_COPYRIGHT) == null) {
			String year = Calendars.format("yyyy");
			String from = Property.getValue("app.from.year", year);
			String copyright = getMessage("app.copyright", Strings.equals(year, from) ? year : from + "-" + year);
			Cache.add(CACHE_COPYRIGHT, copyright, "1d");
		}
		return Cache.get(CACHE_COPYRIGHT, String.class);
	}
	
	/**
	 * 读取闪存
	 * 
	 * @return
	 */
	public Flash getFlash() {
		return (Flash) this.request.getSession().getAttribute(SESSION_FLASH);
	}
	
	/**
	 * 初始化
	 * 
	 * @param context
	 */
	public static void initialize(ServletContext context) {
		contextPath = context.getContextPath();
		resourcePath = Property.getValue("path.resource", contextPath + "/resources");
		imagePath = Property.getValue("path.image", contextPath + "/resources/images");
		javascriptPath = Property.getValue("path.javascript", contextPath + "/resources/javascripts");
		stylesheetPath = Property.getValue("path.stylesheet", contextPath + "/resources/stylesheets");
		
		Logger.info("initialize context path: %s", contextPath);
		Logger.info("initialize resource path: %s", resourcePath);
		Logger.info("initialize image path: %s", imagePath);
		Logger.info("initialize javascript path: %s", javascriptPath);
		Logger.info("initialize stylesheet path: %s", stylesheetPath);
	}
	
	/**
	 * 创建线程实例
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public static Context create(HttpServletRequest request, HttpServletResponse response) {
		Context.set(new WebContext(request, response));
		return current();
	}
	
	/**
	 * 是否为资源路径
	 * 
	 * @param req
	 * @return
	 */
	public static boolean isResource(ServletRequest req) {
		HttpServletRequest request = (HttpServletRequest) req;
		return request.getRequestURI().startsWith(resourcePath);
	}
	
	/**
	 * 闪存作用域
	 *
	 * @author ultrafrog
	 * @version 1.0, 2013-5-13
	 * @since 1.0
	 */
	public static final class Flash {
		
		private Map<String, Object> data = new HashMap<String, Object>();
		private Map<String, Object> out = new HashMap<String, Object>();
		
		/**
		 * 切换
		 */
		protected void next() {
			data.clear();
			data = new HashMap<String, Object>(out);
			out.clear();
		}
		
		/**
		 * 设置
		 * 
		 * @param key
		 * @param value
		 */
		public void put(String key, Object value) {
			data.put(key, value);
			out.put(key, value);
		}
		
		/**
		 * 读取
		 * 
		 * @param key
		 * @return
		 */
		public Object get(String key) {
			return data.get(key);
		}
		
		/**
		 * 成功消息
		 * 
		 * @param message
		 * @param args
		 */
		public void success(String message, Object... args) {
			put("success", String.format(message, args));
		}
		
		/**
		 * 错误消息
		 * 
		 * @param message
		 * @param args
		 */
		public void error(String message, Object... args) {
			put("error", String.format(message, args));
		}
		
		/**
		 * 准备
		 * 
		 * @param request
		 */
		protected static void prepare(HttpServletRequest request) {
			Flash flash = (Flash) request.getSession().getAttribute(SESSION_FLASH);
			if (flash != null) flash.next();
			else request.getSession().setAttribute(SESSION_FLASH, new Flash());
		}
	}
}
