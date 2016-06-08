package com.xiu.search.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.MDC;
import org.tuckey.web.filters.urlrewrite.Conf;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;
import org.tuckey.web.filters.urlrewrite.UrlRewriteWrappedResponse;
import org.tuckey.web.filters.urlrewrite.UrlRewriter;
import org.tuckey.web.filters.urlrewrite.utils.Log;
import org.tuckey.web.filters.urlrewrite.utils.ServerNameMatcher;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

public class XUrlRewriteFilter extends UrlRewriteFilter implements Filter{
	private static Log log = Log.getLog(XUrlRewriteFilter.class);
	
	private UrlRewriter urlRewriter;
	
	private String statusPath = "/rewrite-status";
	private ServerNameMatcher statusServerNameMatcher;
    private static final String DEFAULT_STATUS_ENABLED_ON_HOSTS = "localhost, local, 127.0.0.1";
	
    public void init(final FilterConfig filterConfig) throws ServletException {
    	super.init(filterConfig);
    	String statusEnabledOnHosts = filterConfig
				.getInitParameter("statusEnabledOnHosts");
		if (StringUtils.isBlank(statusEnabledOnHosts)) {
			statusEnabledOnHosts = DEFAULT_STATUS_ENABLED_ON_HOSTS;
		}
		statusServerNameMatcher = new ServerNameMatcher(statusEnabledOnHosts);
    }
    
	public void doFilter(final ServletRequest request,final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		UrlRewriter urlRewriter = getUrlRewriter(request, response, chain);

        final HttpServletRequest hsRequest = (HttpServletRequest) request;
        final HttpServletResponse hsResponse = (HttpServletResponse) response;
        if(hsRequest.isRequestedSessionIdFromURL()){
        	HttpSession session = hsRequest.getSession();
        	if (session != null)
        		session.invalidate();
        }
        UrlRewriteWrappedResponse urlRewriteWrappedResponse = new XUrlRewriteWrappedResponse(hsResponse, hsRequest, urlRewriter);
//        MDC.put("requestURI", hsRequest.getScheme()+"://"+hsRequest.getServerName()+":"+hsRequest.getServerPort()+hsRequest.getRequestURL().toString());
        MDC.put("requestURI", hsRequest.getRequestURL()+(null == hsRequest.getQueryString()? "" : "?" + hsRequest.getQueryString()));
        // check for status request
        if (super.isStatusEnabled() && statusServerNameMatcher.isMatch(request.getServerName())) {
            String uri = hsRequest.getRequestURI();
            if (log.isDebugEnabled()) {
                log.debug("checking for status path on " + uri);
            }
            String contextPath = hsRequest.getContextPath();
            if (uri != null && uri.startsWith(contextPath + statusPath)) {
                super.doFilter(hsRequest, urlRewriteWrappedResponse, chain);
                return;
            }
        }

        boolean requestRewritten = false;
        if (urlRewriter != null) {

            // process the request
            requestRewritten = urlRewriter.processRequest(hsRequest, urlRewriteWrappedResponse, chain);

        } else {
            if (log.isDebugEnabled()) {
                log.debug("urlRewriter engine not loaded ignoring request (could be a conf file problem)");
            }
        }

        // if no rewrite has taken place continue as normal
        if (!requestRewritten) {
            chain.doFilter(hsRequest, urlRewriteWrappedResponse);
        }
        MDC.remove("requestURI");
	}
	
	@Override
	protected UrlRewriter getUrlRewriter(ServletRequest request, ServletResponse response, FilterChain chain) {
        // check to see if the conf needs reloading
        if (isTimeToReloadConf()) {
            reloadConf();
        }
        return urlRewriter;
    }
	
	
	@Override
	protected void checkConf(Conf conf) {
		super.checkConf(conf);
        if (conf.isOk() && conf.isEngineEnabled()) {
            urlRewriter = new XUrlRewriter(conf);
        } else {
        	urlRewriter = null;
        }
    }
	
	@Override
	protected void destroyUrlRewriter() {
		super.destroyUrlRewriter();
        if (urlRewriter != null) {
            urlRewriter.destroy();
            urlRewriter = null;
        }
    }
	
	@Override
	public void destroy() {
		MDC.clear();
		super.destroy();
	}
	
}
