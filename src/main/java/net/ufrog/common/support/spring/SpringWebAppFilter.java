package net.ufrog.common.support.spring;

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
import net.ufrog.common.app.WebApp;
import net.ufrog.common.app.WebAppFilter;

/**
 * @author ultrafrog
 * @version 0.1, 2014-01-19
 * @since 0.1
 */
public class SpringWebAppFilter implements Filter {

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		Logger.info("initialize spring web application filter");
		SpringWebApp.initialize(config.getServletContext());
		Logger.info("spring web application filter is running...");
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		if (!WebApp.resource(req)) req.setAttribute(WebAppFilter.PARAM_APP, SpringWebApp.create(HttpServletRequest.class.cast(req), HttpServletResponse.class.cast(resp)));
		chain.doFilter(req, resp);
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		Logger.info("destroyed spring application filter!");
	}
}
