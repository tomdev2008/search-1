package com.xiu.search.web.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.xiu.search.core.util.ThreadLocalData;
import com.xiu.search.core.util.ThreadLocalData.ThreadLocalKey;
import com.xiu.search.web.analytics.SearchAnalyticsBucket;
import com.xiu.search.web.config.XiuWebConstant;


/**
 * 作为统一的分析入口<br>
 * 为了便于后期扩展，使用springSecurity
 * @author Leon
 *
 */
public class AnalyticsFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpRequest = (HttpServletRequest) request;
//		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpRequest.setCharacterEncoding("UTF-8");
		String requestUrl = httpRequest.getRequestURL().toString();
		String fullRequestUrl = null;
		if (null == httpRequest.getQueryString()) {
			fullRequestUrl = requestUrl;
		}else {
			fullRequestUrl = requestUrl + "?" + httpRequest.getQueryString();
		}
		ThreadLocalData.getInstance().put(ThreadLocalKey.REQUEST_URL, fullRequestUrl);
		
		// 处理SearchAnalyticsBucket
		Map params = request.getParameterMap();
		if(XiuWebConstant.SEARCH_URL.equals(request.getServerName())
				&& null != params 
				&& params.containsKey("kw")
				&& params.containsKey("src")
				){
			String src = null;
			String[] pArr = (String[])params.get(src);
			if(pArr!= null && pArr.length==1)
				src = pArr[0];
			SearchAnalyticsBucket.increment(src);
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		ThreadLocalData.getInstance().clear();
	}

}
