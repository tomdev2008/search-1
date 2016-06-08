package com.xiu.search.web.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tuckey.web.filters.urlrewrite.Condition;
import org.tuckey.web.filters.urlrewrite.Conf;
import org.tuckey.web.filters.urlrewrite.OutboundRule;
import org.tuckey.web.filters.urlrewrite.RewrittenOutboundUrl;
import org.tuckey.web.filters.urlrewrite.UrlRewriter;
import org.tuckey.web.filters.urlrewrite.utils.Log;

public class XUrlRewriter extends UrlRewriter {

	private static Log log = Log.getLog(XUrlRewriter.class);
	
//	private Map<String, List<String>> outboundUrlParamMap;
	private List<OutboundInfo> outboundInfoList;
	
	public XUrlRewriter(Conf conf) {
		super(conf);
//		outboundUrlParamMap = this.getFromParamsMap(conf.getOutboundRules());
		this.outboundInfoList = this.getOutboundInfoList(conf.getOutboundRules());
	}
	
	public static class OutboundInfo{
		private String actionName;
		private List<String> params;
		private Map<String, String[]> defaultParams;
		public void setActionName(String actionName){
			this.actionName = actionName;
		}
		
		public void setParams(List<String> params){
			this.params = params;
		}
		
		public String getActionName(){
			return this.actionName;
		}
		
		public List<String> getParams(){
			return this.params;
		}
		
		public boolean containsParams(String param){
			return null != params && params.contains(param);
		}

		public Map<String, String[]> getDefaultParams() {
			return defaultParams;
		}

		public void setDefaultParams(Map<String, String[]> defaultParams) {
			this.defaultParams = defaultParams;
		}
		
	}
	
	@Override
	protected RewrittenOutboundUrl processEncodeURL(HttpServletResponse hsResponse, HttpServletRequest hsRequest,
            boolean encodeUrlHasBeenRun, String outboundUrl) {
		return super.processEncodeURL(hsResponse, hsRequest, encodeUrlHasBeenRun, outboundUrl);
	}

	private List<OutboundInfo> getOutboundInfoList(List outboundRules){
		List<OutboundInfo> ret = new ArrayList<OutboundInfo>();
		if(null == outboundRules) return ret;
		OutboundInfo o;
		List<String> paramList;
		try {
			for (int i = 0; i < outboundRules.size(); i++) {
				OutboundRule outboundRule = (OutboundRule) outboundRules.get(i);
				String from = outboundRule.getFrom();
				if (null!=from && !"".equals(from)) {
					String[] regex = from.split("\\?", 2);
					if (regex.length > 1) {
						String key = regex[0];
						if(key.startsWith("^")){
							key = key.substring(1, key.length());
						}
						if (!key.startsWith("/"))
							key = "/" + key;
						if (key.endsWith("\\"))
							key = key.substring(0, key.length() - 1);
						paramList = new ArrayList<String>();
						String[] params = regex[1].split("&");
						for (int j = 0; j < params.length; j++) {
							String param = params[j].split("=", 2)[0];
							paramList.add(param);
						}
						o = new OutboundInfo();
						o.setActionName(key);
						o.setParams(paramList);
//						Map<String,String[]> defaultParams = this.getFromParamsMap(outboundRules)
						o.setDefaultParams(this.formatDefaultParams(outboundRule));
						ret.add(o);
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ret;
	}
	
	private Map<String,String[]> formatDefaultParams(OutboundRule outboundRule){
		if(null == outboundRule || null == outboundRule.getConditions())
			return null;
		List<Condition> conditionList = (List<Condition>)outboundRule.getConditions();
		String dq;
		Condition con;
		int len = conditionList.size();
		while(len-->0){
			con = conditionList.get(len);
			if("default-query".equals(con.getName())){
				dq = con.getValue();
				if(null == dq)
					continue;
				Map<String, String[]> dqMap = this.parseParamsFromQuery(dq);
				conditionList.remove(len);
				return dqMap;
			}
		}
//		for (Condition con : conditionList) {
//			if("default-query".equals(con.getName())){
//				dq = con.getValue();
//				if(null == dq)
//					continue;
//				Map<String, String[]> dqMap = this.parseParamsFromQuery(dq);
//				return dqMap;
//			}
//		}
		return null;
	}
	
	private Map getFromParamsMap(List outboundRules) {
		Map ret = new HashMap<String, List<String>>();
		if(null == outboundRules) return ret;
		try {
			for (int i = 0; i < outboundRules.size(); i++) {
				OutboundRule outboundRule = (OutboundRule) outboundRules.get(i);
				String from = outboundRule.getFrom();
				if (null!=from && !"".equals(from)) {
					String[] regex = from.split("\\?", 2);
					if (regex.length > 1) {
						String key = regex[0];
						if(key.startsWith("^")){
							key = key.substring(1, key.length());
						}
						if (!key.startsWith("/"))
							key = "/" + key;
						if (key.endsWith("\\"))
							key = key.substring(0, key.length() - 1);
						List<String> paramList = new ArrayList<String>();
						String[] params = regex[1].split("&");
						for (int j = 0; j < params.length; j++) {
							String param = params[j].split("=", 2)[0];
							paramList.add(param);
						}
						ret.put(key, paramList);
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ret;
	}

//	public Map<String, List<String>> getOutboundUrlParamMap() {
//		return outboundUrlParamMap;
//	}
	
	public List<OutboundInfo> getOutboundInfoList(){
		return this.outboundInfoList;
	} 
	
	public boolean containsOutboundAction(String actionName){
		if(null != this.outboundInfoList && null != actionName){
			for (OutboundInfo o : outboundInfoList) {
				if(o.getActionName().equals(actionName))
					return true;
			}
		}
		return false;
	}
	
	public OutboundInfo getOutboundInfo(String actionName,String[] params){
		if(null == actionName || null == params)
			return null;
		OutboundInfo lastMatchBound=null,ret = null;
		boolean eq;
		int ops,ips,lastMatchNum=0,matchNum=0;
		List<String> oparams;
		for (OutboundInfo o : outboundInfoList) {
			oparams = o.getParams();
			ops = oparams.size();
			ips = params.length;
			if(o.getActionName().equals(actionName)){
				lastMatchBound = o;
				matchNum = 0;
				if(ops == ips){
					eq = true;
					for (String p : params) {
						if(o.containsParams(p)){
							matchNum++;
						}else{
							eq = false;
						}
					}
					if(eq)
						return o;
				}
//				else if(ops > ips){
//					eq = true;
//					for (int i = 0; i < ips; i++) {
//						if(oparams.contains(params[i])){
//							matchNum++;
//						}
//					}
//					if(eq)
//						ret = o;
//				} else if(null == ret){
//					eq = true;
//					for (int i = 0; i < ops; i++) {
//						if(oparams.get(i).equals(params[i])){
//							matchNum++;
//						}
//					}
//					if(eq)
//						ret = o;
//				}
				else{
					for (int i = 0; i < ips; i++) {
						if(oparams.contains(params[i])){
							matchNum++;
						}
					}
				}
				if(matchNum > lastMatchNum){
					lastMatchNum = matchNum;
					ret = o;
				}
			}
		}
		return ret != null ? ret : lastMatchBound;
	}
	
//	private boolean containValue(String[] params,String param){
//		for (String s : params) {
//			if(s.equals(param))
//				return true;
//		}
//		return false;
//	}
	
	private Map<String,String[]> parseParamsFromQuery(String queryStr){
		if(null == queryStr)
			return null;
		queryStr = queryStr.trim();
		if("".equals(queryStr))
			return null;
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
			if(paramsMap.containsKey(param)){
				valueArrTemp = Arrays.copyOf(paramsMap.get(param), paramsMap.get(param).length+1);
				valueArrTemp[paramsMap.get(param).length] = value;
				paramsMap.put(param, valueArrTemp);
			}else{
				valueArrTemp = new String[]{value};
				paramsMap.put(param, valueArrTemp);
			}
		}while(ampersandIndex>0);
		return paramsMap;
	}
}
