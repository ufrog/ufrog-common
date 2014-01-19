package net.ufrog.common.app;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ufrog.common.Logger;

/**
 * @author ultrafrog
 * @version 0.1, 2014-01-19
 * @since 0.1
 */
public class WebAppFilter implements Filter {

	public static final String PARAM_APP = "app";
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		Logger.info("initialize application filter");
		WebApp.initialize(config.getServletContext());
		Logger.info("application filter is running...");
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		if (!WebApp.resource(req)) req.setAttribute(PARAM_APP, WebApp.create(HttpServletRequest.class.cast(req), HttpServletResponse.class.cast(resp)));
		chain.doFilter(req, resp);
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		Logger.info("destroyed application filter!");
	}
}
