package com.xiu.search.web.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie工具类<br>
 * 注意：<br>
 * 1. httpOnly 仅支持servlet3.0以上版本
 * @author Leon
 *
 */
public class CookieUtils {

	/**
	 * 点击流系统记录搜索结果cookie
	 */
	public final static String TEMP_PARAM = "temp_param";
	
	
	/**
	 * Cookie 有效期
	 * @author Leon
	 *
	 */
	public enum CookieExpireEnum {
		/**
		 * 会话
		 */
		CURRENT(-1),
		/**
		 * 一小时
		 */
		ONE_HOUR(60*60), 
		/**
		 * 一天
		 */
		ONE_DAY(24 * 60 * 60), 
		/**
		 * 三天
		 */
		THREE_DAY(3 * 24 * 60 * 60), 
		/**
		 * 一周
		 */
		ONE_WEEK(7 * 24 * 60 * 60), 
		/**
		 * 两周
		 */
		TWO_WEEK(14 * 24 * 60 * 60), 
		/**
		 * 一个月
		 */
		ONE_MONTH(30 * 24 * 60 * 60),
		/**
		 * 截至今天
		 */
		TODAY(0), 
		/**
		 * 永不过期
		 */
		NEVER(10 * 365 * 24 * 60 * 60);
		
		private int value;

		CookieExpireEnum(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
	
	/**
	 * 存入cookie,根据已有类型<br>
	 * 当前domain
	 * @param response
	 * @param name  cookie名
	 * @param value  cookie值
	 * @param cookieExpireEnum 过期时间
	 */
	public static void putCookie(HttpServletResponse response, String name,String value, CookieExpireEnum cookieExpireEnum) {
		putCookie(response, name, value, null,cookieExpireEnum, false);
	}

	/**
	 * 存入cookie,根据已有类型
	 * 
	 * @param response
	 * @param name cookie名
	 * @param value cookie值
	 * @param domain 域名
	 * @param cookieExpireEnum 过期时间
	 */
	public static void putCookie(HttpServletResponse response, String name,String value,String domain, CookieExpireEnum cookieExpireEnum) {
		putCookie(response, name, value, domain,cookieExpireEnum, false);
	}
	
	/**
	 * 存入cookie
	 * 
	 * @param response
	 * @param name
	 * @param value
	 * @param domain 
	 * @param cookieExpireEnum
	 * @param httpOnly 是否是http安全模式
	 */
	public static void putCookie(HttpServletResponse response, String name,String value,String domain, CookieExpireEnum cookieExpireEnum,boolean httpOnly) {
		Cookie cookie = null;
		if (cookieExpireEnum.equals(CookieExpireEnum.TODAY)) {
			long start = new Date().getTime();
			Calendar cal = new GregorianCalendar();
			cal.set(GregorianCalendar.MILLISECOND, 0);
			cal.set(GregorianCalendar.SECOND, 0);
			cal.set(GregorianCalendar.MINUTE, 0);
			cal.set(GregorianCalendar.HOUR_OF_DAY, 0);
			cal.add(GregorianCalendar.DAY_OF_WEEK, 1);
			long time = cal.getTime().getTime() - start;
			cal.clear();
			cookie = createCookie(name, value, domain,(int) time / 1000,httpOnly);
		} else {
			cookie = createCookie(name, value,domain, cookieExpireEnum.getValue(),httpOnly);
		}
		if (null != cookie) {
			response.addCookie(cookie);
		}
	}

	/**
	 * 存入cookie,自定义过期时间
	 * 
	 * @param response
	 * @param name
	 * @param value
	 * @param domain
	 * @param expireSecond 过期时间为秒
	 */
	public static void putCookie(HttpServletResponse response, String name,	String value,int expireSecond) {
		putCookie(response, name, value, null,expireSecond);
	}
	/**
	 * 存入cookie,自定义过期时间
	 * 
	 * @param response
	 * @param name
	 * @param value
	 * @param expireSecond
	 */
	public static void putCookie(HttpServletResponse response, String name, String value, String domain,int expireSecond) {
		Cookie cookie = createCookie(name, value,null, expireSecond,false);
		if (null != cookie) {
			response.addCookie(cookie);
		}
	}
	/**
	 * 存入cookie,自定义过期时间
	 * 
	 * @param response
	 * @param name
	 * @param value
	 * @param expireSecond
	 * @param secure
	 */
	public static void putCookie(HttpServletResponse response, String name, String value, String domain,int expireSecond,boolean httpOnly) {
		Cookie cookie = createCookie(name, value,null, expireSecond, httpOnly);
		if (null != cookie) {
			response.addCookie(cookie);
		}
	}

	/**
	 * 删除cookie
	 * 
	 * @param request 可以为null
	 * @param response
	 * @param name
	 *            待删除的cookie名称
	 */
	public static void delCookie(HttpServletResponse response, String name) {
		delCookie(response, name, null);
	}
	
	/**
	 * 删除cookie
	 * 
	 * @param request 可以为null
	 * @param response
	 * @param name 待删除的cookie名称
	 * @param domain 域名
	 */
	public static void delCookie(HttpServletResponse response, String name,String domain) {
		delCookie(null, response, name, domain);
	}
	
	/**
	 * 删除cookie
	 * 
	 * @param request 可以为null
	 * @param response
	 * @param name
	 *            待删除的cookie名称
	 */
	public static void delCookie(HttpServletRequest request,HttpServletResponse response, String name, String domain) {
		if(null != request){
			Cookie cookie = getCookie(request, name);
			if (null == cookie) {
				return;
			}
		}
		Cookie cookie = new Cookie(name, null);
		cookie.setPath("/");
		cookie.setMaxAge(0);
		if(null != domain)
			cookie.setDomain(domain);
		response.addCookie(cookie);
	}

	/**
	 * 获得cookie的value
	 * 
	 * @param request
	 * @param name cookie名
	 * @return
	 */
	public static String getValue(HttpServletRequest request, String name) {
		Cookie cookie = getCookie(request, name);
		if (null == cookie) {
			return "";
		}
		return cookie.getValue();
	}
	
	/**
	 * 获得cookie
	 * 
	 * @param request
	 * @param name
	 * @param domain
	 * @return
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		Cookie curCookie = null;
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				if (name.equals(cookie.getName())) {
					curCookie = cookie;
					break;
				}
			}
		}
		return curCookie;
	}

	/**
	 * 创建cookie
	 * 
	 * @param name
	 * @param value
	 * @param expireSecond 过期时间,单位秒,>0正常,=0删除,<0会话
	 * @return
	 */
	private static Cookie createCookie(String name, String value,String domain,int expireSecond,boolean httpOnly) {
		Cookie cookie = null;
		if (null != name && !"".equals(name) && null != value && !"".equals(value)) {
			cookie = new Cookie(name, value);
			cookie.setPath("/");
			if(null != domain)
				cookie.setDomain(domain);
			cookie.setMaxAge(expireSecond);
			// TODO 何时开始使用???
//			cookie.setHttpOnly(httpOnly);
		}
		return cookie;

	}
	
}
