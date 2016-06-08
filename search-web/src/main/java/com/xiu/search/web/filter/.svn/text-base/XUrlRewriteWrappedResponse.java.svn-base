package com.xiu.search.web.filter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tuckey.web.filters.urlrewrite.UrlRewriteWrappedResponse;
import org.tuckey.web.filters.urlrewrite.UrlRewriter;
import org.tuckey.web.filters.urlrewrite.utils.Log;

import com.xiu.search.web.config.XiuWebConstant;
import com.xiu.search.web.filter.XUrlRewriter.OutboundInfo;

public class XUrlRewriteWrappedResponse extends UrlRewriteWrappedResponse {
	private static Log log = Log.getLog(XUrlRewriteWrappedResponse.class);

	private static final String ENCODE = "UTF-8";
	
	private UrlRewriter urlRerwiter;
	private HttpServletRequest httpServletRequest;
	
	public XUrlRewriteWrappedResponse(HttpServletResponse httpServletResponse,HttpServletRequest httpServletRequest, UrlRewriter urlRerwiter) {
		super(httpServletResponse, httpServletRequest, urlRerwiter);
		this.httpServletRequest = httpServletRequest;
		this.urlRerwiter = urlRerwiter;
	}

	@Override
	public String encodeURL(String s) {
		if(null==s || !(urlRerwiter instanceof XUrlRewriter))
			return super.encodeURL(s);
		
		int questIndex = s.indexOf('?');
		if(questIndex == -1 || questIndex == s.length())
			return super.encodeURL(s);
		
		XUrlRewriter xUrlRewriter = (XUrlRewriter)urlRerwiter;
		StringBuffer urlSb = new StringBuffer();
		String pathStr = s.substring(0,questIndex);
		String contextPath = xUrlRewriter.getContextPath(httpServletRequest);
		pathStr = pathStr.replaceFirst(contextPath, "");
		if(!xUrlRewriter.containsOutboundAction(pathStr))
			return super.encodeURL(s);
		urlSb.append(pathStr);
		String queryStr = s.substring(questIndex+1,s.length());
		int ampersandIndex,lastAmpersandIndex = 0,pvIndex;
		String subStr,param,value;
		Map<String,String[]> paramsMap = new HashMap<String, String[]>();
		String[] valueArrTemp;
		do{
			ampersandIndex = queryStr.indexOf('&', lastAmpersandIndex)+1;
			if(ampersandIndex>0){
				subStr = queryStr.substring(lastAmpersandIndex, ampersandIndex-1);
				lastAmpersandIndex = ampersandIndex;
			}else{
				subStr = queryStr.substring(lastAmpersandIndex);
			}
			pvIndex = subStr.indexOf("=");
			if(pvIndex == -1 || pvIndex == subStr.length()-1)
				continue;
			param = subStr.substring(0,pvIndex);
			value = subStr.substring(pvIndex+1,subStr.length());
//			param = pvIndex>=0 ? subStr.substring(0,pvIndex) : subStr;
//			value = pvIndex>=0 ? subStr.substring(pvIndex+1,subStr.length()): "";
			if(paramsMap.containsKey(param)){
				valueArrTemp = Arrays.copyOf(paramsMap.get(param), paramsMap.get(param).length+1);
				valueArrTemp[paramsMap.get(param).length] = value;
				paramsMap.put(param, valueArrTemp);
			}else{
				valueArrTemp = new String[]{value};
				paramsMap.put(param, valueArrTemp);
			}
		}while(ampersandIndex>0);
		String[] paramArr = paramsMap.keySet().toArray(new String[0]);
		OutboundInfo outboundInfo = xUrlRewriter.getOutboundInfo(pathStr, paramArr);
		if(null == outboundInfo){
			return super.encodeURL(s);
		}
		List<String> paramList = outboundInfo.getParams();
		StringBuffer querySb = new StringBuffer();
		StringBuffer extQuerySb = new StringBuffer();
		// 增加默认params
		Map<String,String[]> paramsMegreMap;
		if(null == outboundInfo.getDefaultParams()){
			paramsMegreMap = paramsMap;
		}else{
			paramsMegreMap = new HashMap<String, String[]>();
			paramsMegreMap.putAll(outboundInfo.getDefaultParams());
			paramsMegreMap.putAll(paramsMap);
		}
		
		for (int i = 0,len= paramList.size(); i < len; i++) {
			param = paramList.get(i);
			if(paramsMegreMap.containsKey(param)){
				appendQuery(querySb, param, paramsMegreMap.get(param)[0]);
			}else{
				appendQuery(querySb, param, "");
			}
		}
		for (int i = 0,len = paramArr.length; i < len; i++) {
			if(!paramList.contains(paramArr[i]))
				appendQuery(extQuerySb, paramArr[i], paramsMegreMap.get(paramArr[i]));
		}
		
//		if(urlParamMap.containsKey(pathStr)){
//			String queryStr = s.substring(questIndex+1,s.length());
//			if(null != queryStr && queryStr.length()>0){
//				List<String> paramList = urlParamMap.get(pathStr);
//				Set<String> paramSet = new HashSet<String>(paramList);
//				Map<String,String> matchValueMap = new HashMap<String, String>();
//				int ampersandIndex,lastAmpersandIndex = 0,pvIndex;
//				String subStr,param,value;
//				Map<String,String[]> paramsMap = new HashMap<String, String[]>();
//				String[] valueArrTemp;
//				do{
//					ampersandIndex = queryStr.indexOf('&', lastAmpersandIndex)+1;
//					if(ampersandIndex>0){
//						subStr = queryStr.substring(lastAmpersandIndex, ampersandIndex-1);
//						lastAmpersandIndex = ampersandIndex;
//					}else{
//						subStr = queryStr.substring(lastAmpersandIndex);
//					}
//					pvIndex = subStr.indexOf("=");
//					param = pvIndex>=0 ? subStr.substring(0,pvIndex) : subStr;
//					value = pvIndex>=0 ? subStr.substring(pvIndex+1,subStr.length()): "";
//					if(paramsMap.containsKey(param)){
//						valueArrTemp = Arrays.copyOf(paramsMap.get(param), paramsMap.get(param).length+1);
//						valueArrTemp[paramsMap.get(param).length] = value;
//						paramsMap.put(param, valueArrTemp);
//					}else{
//						valueArrTemp = new String[1];
//						valueArrTemp[0] = value;
//						paramsMap.put(param, valueArrTemp);
//					}
//					if(paramSet.contains(param)){
////						try {
////							value = URLEncoder.encode(value, ENCODE);
////							matchValueMap.put(param, value);
////						} catch (Exception e) {
////							matchValueMap.put(param, value);
////							log.error("Can't encode value : "+value+"\n"+e.getMessage());
////						}
//						matchValueMap.put(param, value);
//					}else if(null != value && !"".equals(value)){
//						try {
////							value = URLEncoder.encode(value, ENCODE);
//							extQuerySb.append('&').append(param).append('=').append(value);
//						} catch (Exception e) {
//							log.error("Can't encode value : "+value+"\n"+e.getMessage());
//						}
//					}
//				}while(ampersandIndex>0);
//				for (int i = 0,len=paramList.size(); i < len; i++) {
//					if(i>0)urlSb.append('&');
//					param = paramList.get(i);
//					urlSb.append(param).append('=');
//					if(matchValueMap.containsKey(param)){
//						urlSb.append(matchValueMap.get(param));
//					}
//				}
//			}
//		}else{
//			return super.encodeURL(s);
//		}
		if(querySb.length()>0){
			urlSb.append('?').append(querySb);
		}
		s = urlSb.toString();
		String encodeUrl = super.encodeURL(s);
		if(extQuerySb.length()>0){
			if(encodeUrl.indexOf('?')<0){
				encodeUrl = encodeUrl + "?" + extQuerySb.toString();
			}else{
				encodeUrl = encodeUrl + "&" + extQuerySb.toString();
			}
		}
		if(XiuWebConstant.isDevelop())
			return contextPath + encodeUrl;
		return encodeUrl;
	}
	
	private void appendQuery(StringBuffer sb,String param,String... values){
		for (String v : values) {
			if(sb.length()>0)
				sb.append('&');
			sb.append(param).append('=').append(v);
		}
	}
	
}
