package net.ufrog.common.app;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.ufrog.common.Logger;
import net.ufrog.common.Messages;
import net.ufrog.common.utils.Codecs;

/**
 * 互联网应用信息
 *
 * @author ultrafrog
 * @version 1.0, 2013-10-01
 * @since 1.0
 */
public class WebApp extends App {

	private static final String SESSION_USER			= "net.ufrog.session.user";
	private static final String SESSION_TOKEN			= "net.ufrog.session.token";
	
	private static final String CONF_PATH_RESOURCE		= "app.resource";
	private static final String CONF_PATH_IMAGE			= "app.image";
	private static final String CONF_PATH_JAVASCRIPT	= "app.javascript";
	private static final String CONF_PATH_STYLESHEET	= "app.stylesheet";
	private static final String CONF_PATH_THEME			= "app.theme";
	
	private static String contextPath;
	private static String resourcePath;
	private static String imagePath;
	private static String javascriptPath;
	private static String stylesheetPath;
	private static String themePath;
	
	protected HttpServletRequest request;
	
	/**
	 * 构造函数
	 * 
	 * @param request
	 * @param response
	 */
	public WebApp(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
	}
	
	/* (non-Javadoc)
	 * @see net.ufrog.common.app.App#getUser()
	 */
	@Override
	public AppUser getUser() {
		return AppUser.class.cast(request.getSession().getAttribute(SESSION_USER));
	}
	
	/* (non-Javadoc)
	 * @see net.ufrog.common.app.App#setUser(net.ufrog.common.app.AppUser)
	 */
	@Override
	public AppUser setUser(AppUser user) {
		if (user == null) {
			HttpSession session = request.getSession(false);
			if (session != null) session.removeAttribute(SESSION_USER);
		} else {
			request.getSession().setAttribute(SESSION_USER, user);
		}
		return user;
	}
	
	/* (non-Javadoc)
	 * @see net.ufrog.common.app.App#getToken()
	 */
	@Override
	public String getToken() {
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
	 * 初始化
	 * 
	 * @param context
	 */
	public static void initialize(ServletContext context) {
		contextPath = context.getContextPath();
		resourcePath = config(CONF_PATH_RESOURCE, contextPath + "/resources");
		imagePath = config(CONF_PATH_IMAGE, contextPath + "/resources/images");
		javascriptPath = config(CONF_PATH_JAVASCRIPT, contextPath + "/resources/javascripts");
		stylesheetPath = config(CONF_PATH_STYLESHEET, contextPath + "/resources/stylesheets");
		themePath = config(CONF_PATH_THEME, contextPath + "/theme/default");
		
		Logger.info("initialize context path: %s", contextPath);
		Logger.info("initialize resource path: %s", resourcePath);
		Logger.info("initialize image path: %s", imagePath);
		Logger.info("initialize javascript path: %s", javascriptPath);
		Logger.info("initialize stylesheet path: %s", stylesheetPath);
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