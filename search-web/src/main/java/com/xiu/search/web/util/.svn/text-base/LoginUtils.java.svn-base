package com.xiu.search.web.util;

import java.net.URLDecoder;
import java.security.MessageDigest;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.xiu.search.dao.config.XiuSearchConfig;

public final class LoginUtils {

	public final static String COOKIE_DOMAIN_ROOT = ".xiu.com";
	public final static String COOKIE_DOMAIN_PORTAL = "portal.xiu.com";

	public final static String COOKIE_NAME_SSOTOKENID = "xiu.login.tokenId";
	public final static String COOKIE_NAME_SSOCUSID = "xiu.login.cusId";
	public final static String COOKIE_NAME_SSOUSERID = "xiu.login.userId"; // userId
	public final static String COOKIE_NAME_SSOCREATETIME = "xiu.login.regDate"; // 注册时间
	// TODO:需要确认
	public final static String COOKIE_NAME_LOGINFLAG = "xiu.login.proxyLoginFlag"; // 代理登录标志
	public final static String COOKIE_NAME_LOGINNAME = "xiu.login.loginName"; // 登录页面要记住且显示的登录名
	public final static String COOKIE_NAME_USERNAME = "xiu.login.userName"; // 其他页面需要显示的登录名（昵称）
	
	public final static String COOKIE_NAME_UNIONFLAG = "xiu.login.unionFlag";// 联合登陆标识：001支付宝，002平安万里通，003
																				// 139邮箱，004芒果，
																				// 005
																				// 新浪微博，
																				// 006
																				// 51返利
	public final static String COOKIE_NAME_UNIONPARTNERID = "xiu.login.partnerId";// 联合登录伙伴id

	public final static String COOKIE_NAME_XUSERID_ENCRYPTION = "xuserId2";// 对userId加密后cookieName
	public final static String COOKIE_NEW_NAME_XUSERID_ENCRYPTION = "xid";// 对userId加密后cookieName
	public final static String COOKIE_NAME_CPS_MEDIA = "fromid";// 对userId加密后cookieName
	public final static String COOKIE_NAME_CPS_TYPE = "cps_type";// cps_type 1
	public final static String COOKIE_NAME_CHANNEL = "xiu.login.channel"; // 用户渠道

	public final static String COOKIE_NAME_MERGER = "";
	
	public final static String COOKIE_NAME_LASTLOGINDATE = "xiu.login.lastDate"; // 上次登录时间
	
	public static String getSsoTokenId(HttpServletRequest request) {
		return CookieUtils.getValue(request, COOKIE_NAME_SSOTOKENID);
	}
 
	public static String getUserId(HttpServletRequest request){
		return CookieUtils.getValue(request, COOKIE_NAME_SSOUSERID );
	}
	
	public static boolean checkDataIntegrityOfUser(HttpServletRequest request) {
		String originalUserId = CookieUtils.getValue(request, COOKIE_NAME_SSOUSERID );
		if (StringUtils.isBlank(originalUserId)) {// userId为空
			return false;
		}
		// 加密后的userId
//		String encryption_userId = CookieUtils.getValue(request, COOKIE_NAME_XUSERID_ENCRYPTION);
		String encryption_userId = getUserMergerCookieValue(request, COOKIE_NEW_NAME_XUSERID_ENCRYPTION);
		if (!StringUtils.isBlank(encryption_userId)) {
			String md5Code = XiuSearchConfig.getPropertieValue(XiuSearchConfig.ENCRYPTION_AUTH_MD5CODE);
			originalUserId = getMD5(originalUserId + md5Code);
			if (encryption_userId.equals(originalUserId)) {
				return true;
			}
		} else {
		}
		return false;
	}
	
	/**
	 * 得到MD5值
	 * 
	 * @param str
	 * @return
	 */
	private static String getMD5(String str) {
		if (str == null || str.length() == 0) {
			throw new IllegalArgumentException(
					"String to encript cannot be null or zero length");
		}
		StringBuffer ret = new StringBuffer();
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(str.getBytes("UTF-8"));
			byte[] hash = digest.digest();
			// 转为ASCII码,byte为8位,计算机为32位,采用补码形式,所以要补全.
			for (int i = 0; i < hash.length; i++) {
				if ((0xFF & hash[i]) < 0x10) {
					ret.append("0" + Integer.toHexString((hash[i] & 0xFF)));
				} else {
					ret.append(Integer.toHexString(hash[i] & 0xFF));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret.toString();
	}
	
	private static String getUserMergerCookieValue(HttpServletRequest request,String key){
		String mergerValue = CookieUtils.getValue(request, "xuser" );
		if(StringUtils.isBlank(mergerValue))
			return null;
		try {
			mergerValue = URLDecoder.decode(mergerValue, "UTF-8");
		} catch (Exception e) {
			return null;
		}
		String[] kvp = StringUtils.split(mergerValue, "|");
		if(null == kvp || kvp.length==0)
			return null;
		key = key + "=";
		for (String kv : kvp) {
			if(kv!= null && kv.indexOf(key) == 0){
				return kv.substring(kv.indexOf("=")+1, kv.length());
			}
		}
		return null;
	}
}
