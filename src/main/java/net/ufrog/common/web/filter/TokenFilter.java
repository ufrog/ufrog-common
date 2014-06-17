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
import net.ufrog.common.app.WebApp;
import net.ufrog.common.exception.ServiceException;
import net.ufrog.common.utils.Strings;

/**
 * 令牌过滤器
 *
 * @author ultrafrog
 * @version 1.0, 2013-04-24
 * @since 1.0
 */
public class TokenFilter implements Filter {

	protected static final String PARAM_TOKEN	= "token";
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		Logger.info("token filter is working...");
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		if (!WebApp.resource(req)) {
			String token = req.getParameter(PARAM_TOKEN);
			if (!Strings.empty(token)) {
				if (!Strings.equals(token, App.current().getToken())) {
					Logger.warn("can not match between '%s' and '%s'", token, App.current().getToken());
					throw new ServiceException("the token is not match!", "exception.token");
				}
				App.current().updateToken();
			}
		}
		chain.doFilter(req, resp);
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		Logger.info("token filter has been destroyed!");
	}
}
