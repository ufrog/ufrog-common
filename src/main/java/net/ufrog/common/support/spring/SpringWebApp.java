package net.ufrog.common.support.spring;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ufrog.common.Logger;
import net.ufrog.common.app.App;
import net.ufrog.common.app.WebApp;

import org.springframework.context.ApplicationContext;
import org.springframework.context.NoSuchMessageException;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;

/**
 * @author ultrafrog
 * @version 1.0, 2013-08-23
 * @since 1.0
 */
public class SpringWebApp extends WebApp {

	/** 容器上下文 */
	private static ApplicationContext applicationContext;
	
	/**
	 * 构造函数
	 * 
	 * @param request
	 * @param response
	 */
	protected SpringWebApp(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
	}
	
	/* (non-Javadoc)
	 * @see net.ufrog.web.WebContext#getLocale()
	 */
	@Override
	public Locale getLocale() {
		Locale locale = Locale.class.cast(WebUtils.getSessionAttribute(this.request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME));
		if (locale == null) locale = this.request.getLocale();
		return locale;
	}
	
	/* (non-Javadoc)
	 * @see net.ufrog.common.Context#getMessage(java.lang.String, java.lang.Object[])
	 */
	@Override
	public String getMessage(String key, Object... args) {
		try {
			return String.format(applicationContext.getMessage(key, null, getLocale()), args);
		} catch (NoSuchMessageException e) {
			Logger.warn(e.getMessage());
			return key;
		}
	}
	
	/**
	 * 读取实例
	 * 
	 * @param name
	 * @return
	 */
	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}
	
	/**
	 * 读取实例
	 * 
	 * @param name
	 * @param args
	 * @return
	 */
	public static Object getBean(String name, Object... args) {
		return applicationContext.getBean(name, args);
	}
	
	/**
	 * 读取实例
	 * 
	 * @param name
	 * @param requiredType
	 * @return
	 */
	public static <T> T getBean(String name, Class<T> requiredType) {
		return applicationContext.getBean(name, requiredType);
	}
	
	/**
	 * 读取实例
	 * 
	 * @param requiredType
	 * @return
	 */
	public static <T> T getBean(Class<T> requiredType) {
		return applicationContext.getBean(requiredType);
	}

	/**
	 * 初始化
	 * 
	 * @param context
	 */
	public static void initialize(ServletContext context) {
		WebApp.initialize(context);
		applicationContext = WebApplicationContextUtils.getWebApplicationContext(context);
		Logger.info("initialize application context: %s", applicationContext);
	}
	
	/**
	 * 创建实例
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public static App create(HttpServletRequest request, HttpServletResponse response) {
		current(new SpringWebApp(request, response));
		return current();
	}
}
