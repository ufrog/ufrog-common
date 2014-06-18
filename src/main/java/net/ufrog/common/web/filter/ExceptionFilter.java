package net.ufrog.common.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import net.ufrog.common.Logger;
import net.ufrog.common.app.App;
import net.ufrog.common.exception.ServiceException;
import net.ufrog.common.exception.UnsignException;
import net.ufrog.common.utils.Strings;

/**
 * 异常过滤器
 * 
 * @author ultrafrog
 * @version 1.0, 2014-06-18
 * @since 1.0
 */
public class ExceptionFilter implements Filter {

	private static final String REQUEST_TYPE_PARAM	= App.config("app.request.type", "requestType");
	private static final String REQUEST_TYPE_JSON	= App.config("app.request.type.json", "json");
	private static final String REQUEST_TYPE_HTML	= App.config("app.request.type.html", "html");
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		Logger.info("exception filter is working...");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		// 初始化
		ServiceException exception = null;
		String uri = App.config("path.error");
		String requestType = req.getParameter(REQUEST_TYPE_PARAM);
		
		// 业务处理并拦截异常
		try {
			chain.doFilter(req, resp);
		} catch (UnsignException e) {
			exception = e;
			uri = App.config("path.sign");
		} catch (ServiceException e) {
			exception = e;
			Logger.error(exception.getMessage(), exception);
		} catch (Exception e) {
			exception = new ServiceException(e.getMessage(), e);
			Logger.error(exception.getMessage(), exception);
		}
		
		// 根据异常进行处理
		if (exception != null) {
			if (Strings.equals(requestType, REQUEST_TYPE_JSON)) {
				//TODO
			} else if (Strings.equals(requestType, REQUEST_TYPE_HTML)) {
				//TODO
			} else {
				//TODO
			}
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		Logger.info("exception filter has been destroyed!");
	}
}
