package net.ufrog.common.app;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ufrog.common.Logger;
import net.ufrog.common.Messages;
import net.ufrog.common.cache.Caches;
import net.ufrog.common.utils.Codecs;
import net.ufrog.common.utils.Strings;

/**
 * 互联网应用信息
 *
 * @author ultrafrog
 * @version 1.0, 2013-10-01
 * @since 1.0
 */
public class WebApp extends App {

	private static final String COOKIE_SESSION_ID		= "net.ufrog.cookie.session.id";
	private static final String SESSION_USER			= "net.ufrog.session.user";
	private static final String SESSION_TOKEN			= "net.ufrog.session.token";
	
	private static final String CONF_PATH_HOST			= "path.host";
	private static final String CONF_PATH_RESOURCE		= "path.resource";
	private static final String CONF_PATH_IMAGE			= "path.image";
	private static final String CONF_PATH_JAVASCRIPT	= "path.javascript";
	private static final String CONF_PATH_STYLESHEET	= "path.stylesheet";
	private static final String CONF_PATH_THEME			= "path.theme";
	
	private static String tempPath;
	private static String contextPath;
	private static String homePath;
	private static String resourcePath;
	private static String imagePath;
	private static String javascriptPath;
	private static String stylesheetPath;
	private static String themePath;
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	
	/**
	 * 构造函数
	 * 
	 * @param request
	 * @param response
	 */
	public WebApp(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}
	
	/* (non-Javadoc)
	 * @see net.ufrog.common.app.App#getUser()
	 */
	@Override
	public AppUser getUser() {
		return AppUser.class.cast(session(SESSION_USER));
	}
	
	/* (non-Javadoc)
	 * @see net.ufrog.common.app.App#setUser(net.ufrog.common.app.AppUser)
	 */
	@Override
	public AppUser setUser(AppUser user) {
		session(SESSION_USER, user);
		return user;
	}
	
	/* (non-Javadoc)
	 * @see net.ufrog.common.app.App#getToken()
	 */
	@Override
	public String getToken() {
		if (request.getSession().getAttribute(SESSION_TOKEN) == null) {
			return updateToken();
		}
		return String.class.cast(request.getSession().getAttribute(SESSION_TOKEN));
	}
	
	/* (non-Javadoc)
	 * @see net.ufrog.common.app.App#updateToken()
	 */
	@Override
	public String updateToken() {
		String token = Codecs.uuid();
		request.getSession().setAttribute(SESSION_TOKEN, token);
		return token;
	}
	
	/* (non-Javadoc)
	 * @see net.ufrog.common.app.App#getLocale()
	 */
	@Override
	public Locale getLocale() {
		return request.getLocale();
	}
	
	/* (non-Javadoc)
	 * @see net.ufrog.common.app.App#getMessage(java.lang.String, java.lang.Object[])
	 */
	@Override
	public String getMessage(String key, Object... args) {
		return Messages.get(getLocale(), key, args);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getPath();
	}
	
	/**
	 * 读取临时路径
	 * 
	 * @return
	 */
	public String getTempPath() {
		return tempPath;
	}
	
	/**
	 * 读取应用路径
	 * 
	 * @return
	 */
	public String getPath() {
		return contextPath;
	}
	
	/**
	 * 读取资源路径
	 * 
	 * @return
	 */
	public String getResource() {
		return resourcePath;
	}
	
	/**
	 * 读取图片路径
	 * 
	 * @return
	 */
	public String getImg() {
		return imagePath;
	}
	
	/**
	 * 读取脚本路径
	 * 
	 * @return
	 */
	public String getJs() {
		return javascriptPath;
	}
	
	/**
	 * 读取样式路径
	 * 
	 * @return
	 */
	public String getCss() {
		return stylesheetPath;
	}
	
	/**
	 * 读取主题路径
	 * 
	 * @return
	 */
	public String getTheme() {
		return themePath;
	}
	
	/**
	 * 读取当前地址
	 * 
	 * @return
	 */
	public String getHome() {
		// 判断地址是否已经生成
		if (homePath == null) {
			// 初始化
			String scheme = request.getScheme();
			Integer port = request.getServerPort();
			StringBuilder url = Strings.builder(scheme);
			
			// 生成地址
			url.append("://");
			url.append(request.getServerName());
			if (!(port == 80 && Strings.equals("http", scheme)) && !(port == 443 && Strings.equals("https", scheme))) {
				url.append(":").append(port);
			}
			url.append(request.getContextPath());
			
			// 地址设置
			homePath = url.toString();
		}
		
		// 返回结果
		return homePath;
	}
	
	/**
	 * @param name
	 * @param obj
	 */
	public void session(String name, Object obj) {
		// 读取编号
		String cookieSessionId = getCookieValue(COOKIE_SESSION_ID);
		if (cookieSessionId == null) {
			cookieSessionId = Codecs.uuid();
			Cookie cookie = new Cookie(COOKIE_SESSION_ID, cookieSessionId);
			cookie.setMaxAge(60 * 60 * 24 * 14);
			response.addCookie(cookie);
			Logger.debug("create cookie session id: %s", cookieSessionId);
		}
		
		// 当对象为空时判定为删除相关会话
		if (obj == null) {
			Caches.delete(cookieSessionId + "@" + name);
			return;
		}
		
		// 设置缓存
		Caches.set(cookieSessionId + "@" + name, obj, App.config("app.session.timeout", "30mn"));
	}
	
	/**
	 * @param name
	 * @return
	 */
	public Object session(String name) {
		// 读取会话编号
		// 若没有会话编号返回空
		String cookieSessionId = getCookieValue(COOKIE_SESSION_ID);
		if (cookieSessionId == null) return null;
		
		// 读取缓存
		// 若缓存不存在则返回空
		Object obj = Caches.get(cookieSessionId + "@" + name);
		if (obj == null) return null;
		
		// 重置缓存时间并返回结果
		session(name, obj);
		return obj;
	}
	
	/**
	 * @param name
	 * @return
	 */
	protected String getCookieValue(String name) {
		if (request.getCookies() == null) return null;
		for (Cookie cookie: request.getCookies()) {
			if (Strings.equals(cookie.getName(), name)) {
				return cookie.getValue();
			}
		}
		return null;
	}
	
	/**
	 * 初始化
	 * 
	 * @param context
	 */
	public static void initialize(ServletContext context) {
		tempPath = String.valueOf(context.getAttribute("javax.servlet.context.tempdir"));
		contextPath = context.getContextPath();
		resourcePath = config(CONF_PATH_HOST, contextPath) + config(CONF_PATH_RESOURCE, "/resources");
		imagePath = config(CONF_PATH_HOST, contextPath) + config(CONF_PATH_IMAGE, "/resources/images");
		javascriptPath = config(CONF_PATH_HOST, contextPath) + config(CONF_PATH_JAVASCRIPT, "/resources/javascripts");
		stylesheetPath = config(CONF_PATH_HOST, contextPath) + config(CONF_PATH_STYLESHEET, "/resources/stylesheets");
		themePath = config(CONF_PATH_HOST, contextPath) + config(CONF_PATH_THEME, "/theme/default");
		
		Logger.info("initialize temp path: %s", tempPath);
		Logger.info("initialize context path: %s", contextPath);
		Logger.info("initialize resource path: %s", resourcePath);
		Logger.info("initialize image path: %s", imagePath);
		Logger.info("initialize javascript path: %s", javascriptPath);
		Logger.info("initialize stylesheet path: %s", stylesheetPath);
		Logger.info("initialize theme path: %s", themePath);
	}
	
	/**
	 * 创建实例
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public static App create(HttpServletRequest request, HttpServletResponse response) {
		current(new WebApp(request, response));
		return current();
	}
	
	/**
	 * 判断是否资源请求
	 * 
	 * @param req
	 * @return
	 */
	public static boolean resource(ServletRequest req) {
		return HttpServletRequest.class.cast(req).getRequestURI().startsWith(resourcePath);
	}
}
