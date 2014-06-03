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

/**
 * 异常过滤器
 * 
 * @author ultrafrog
 * @version 1.0, 2014-05-27
 * @since 1.0
 */
public class ExceptionFilter implements Filter {

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Logger.info("exception filter is working...");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} catch (UnsignException e) {
			request.setAttribute("error", App.message(e.getKey()));
			Logger.debug("user unsign.");
			request.getRequestDispatcher("/sign-in").forward(request, response);
		} catch (ServiceException e) {
			request.setAttribute("error", App.message(e.getKey()));
			request.setAttribute("code", e.getCode());
			Logger.error(e.getMessage(), e);
			request.getRequestDispatcher("/error-500").forward(request, response);
		} catch (Exception e) {
			ServiceException se = new ServiceException(e.getMessage(), e);
			request.setAttribute("error", App.message(se.getKey()));
			request.setAttribute("code", se.getCode());
			Logger.error(se.getMessage(), se);
			request.getRequestDispatcher("/error-500").forward(request, response);
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		Logger.info("exception filter has been shutdown.");
	}
}
