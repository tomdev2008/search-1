package com.xiu.search.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieIdentityFilter implements Filter {
	private final String VERSION_NAME="versionName";
	private final String VERSION_VALUE="2.5";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		final HttpServletRequest hsRequest = (HttpServletRequest) request;
        final HttpServletResponse hsResponse = (HttpServletResponse) response;
        
        String version=null;
        Cookie[] cookies=hsRequest.getCookies();
        if(null!=cookies&&cookies.length>0){
        	for (int i = 0; i < cookies.length; i++) {
        		Cookie temp=cookies[i];
        		if(VERSION_NAME.equals(temp.getName())){
        			version=temp.getName();
        			break;
        		}
        	}
        }
       
        String path=hsRequest.getServletPath();
        String types="js css jpg gif png htm";
        int begin=path.lastIndexOf(".");
        boolean flag=false;
        if(begin>0){
        	String type=path.substring(begin+1).toLowerCase();
        	if(types.contains(type)){
        		flag=true;
        	}
        }
        
        
        if(null==version&&flag==false){
        	Cookie cookie=new Cookie(VERSION_NAME,VERSION_VALUE);
        	cookie.setPath("/");
        	cookie.setMaxAge(10*24*60*60);
    		cookie.setDomain(request.getServerName());
    		hsResponse.addCookie(cookie);
        }
        
		chain.doFilter(hsRequest, hsResponse);
	}

	@Override
	public void destroy() {
		
	}

}
