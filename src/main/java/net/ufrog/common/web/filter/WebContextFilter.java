package net.ufrog.common.web.filter;

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
import net.ufrog.common.web.WebContext;

/**
 * 互联网上下文过滤器
 *
 * @author ultrafrog
 * @version 1.0, 2013-8-23
 * @since 1.0
 */
public class WebContextFilter implements Filter {

	public static final String PARAM_CONTEXT = "context";
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		Logger.info("initialize application filter");
		WebContext.initialize(config.getServletContext());
		Logger.info("application filter is running...");
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		if (!WebContext.isResource(req)) {
			req.setAttribute(PARAM_CONTEXT, WebContext.create((HttpServletRequest) req, (HttpServletResponse) resp));
		}
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
