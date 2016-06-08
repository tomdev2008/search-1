package com.xiu.search.web.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.xiu.search.web.util.IPv4Util;

/**
 * 防恶意爬虫过滤器
 * @author William.zhang	20130703
 *
 */
public class ReptileFilter implements Filter {
	
	private static Logger logger = Logger.getLogger(ReptileFilter.class);
	
//	private static Map<Integer, Integer> map = new ConcurrentHashMap<Integer, Integer>(10240);
	private static final int bufferMaxSize = 10240;
	
	private volatile static Map<Integer,AtomicLong> bufferMap = Collections.synchronizedMap(new LinkedHashMap<Integer, AtomicLong>(10240, 0.75f, true){
		protected boolean removeEldestEntry(Map.Entry<Integer,AtomicLong> eldest){
			if(size()>bufferMaxSize){
				return true;
			}
			return false;
		}
	});
	
	private static final Integer MAX_NUM = 100; 									//时间段内最大访问次数
	private static String[] whiteIPList = new String[]{"127.0.0.1","10.0.0.163","113.106.63.1"}; 			//ip白名单
	private static String[] blackIPList = new String[]{};		//ip黑名单
	private static String[] AgentList = new String[]{"Google", "Yahoo", "spider", "msn", "alexa.com","baidu","msn","Sougou","mozzila","bing","Soso","Youdao"};	//User-Agent白名单
	
	private static int[] whiteList ;
	private static int[] blackList ;
	private static String[] userAgentList;	//User-Agent白名单
	private static long LAST_CLEAR_TIME = 0;
	private static final long CLEAN_TIME_CIRCLE = 24*60*60*1000L;
	static{
		
		//初始化黑白名单，转化为INT类型存储,AgentList全部转化为小写
		logger.debug("初始化各种名单开始。。。。。。。。。。。。");
		whiteList = new int[whiteIPList.length];
		blackList = new int[blackIPList.length];
		userAgentList = new  String[AgentList.length];
		
		for(int i=0;i<whiteIPList.length;i++){
			whiteList[i] = IPv4Util.ipToInt(whiteIPList[i]);
		}
		
		for(int i=0;i<blackIPList.length;i++){
			blackList[i] = IPv4Util.ipToInt(blackIPList[i]);
		}
		
		for(int i=0;i<AgentList.length;i++){
			userAgentList[i] = AgentList[i].toLowerCase();
		}
		LAST_CLEAR_TIME = System.currentTimeMillis();
		logger.debug("初始化各种名单结束。。。。。。。。。。。。。");
		//转化结束
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		if(this.pass(req,res)){
			chain.doFilter(request, response);
			return;
		}
		return;
	}

	@Override
	public void destroy() {
		
	}

	private boolean pass(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		String userIP = request.getHeader("x-forwarded-for");
		if(userIP == null || userIP.length() == 0 || "unknown".equalsIgnoreCase(userIP)) {
	    	userIP = request.getHeader("Proxy-Client-IP");
	    }
	    if(userIP == null || userIP.length() == 0 || "unknown".equalsIgnoreCase(userIP)) {
	    	userIP = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(userIP == null || userIP.length() == 0 || "unknown".equalsIgnoreCase(userIP)) {
	    	userIP = request.getRemoteAddr();
	    }
	    userIP = userIP.split(",")[0];
		String userAgent = request.getHeader("User-Agent");
		String referer = request.getHeader("referer");
		if(StringUtils.isNotBlank(userAgent)){
			userAgent = userAgent.toLowerCase();
		}
		
		int ipInt = IPv4Util.ipToInt(userIP);
		Integer ipINT = Integer.valueOf(ipInt);
		//如果IP在白名单中，则直接通过
		for(int white : whiteList){
			if(white == ipInt){
				logger.debug("当前IP在白名单");
				return true;
			}
		}
		//如果IP在黑名单中，则直接拦截
		for(int black : blackList){
			if(black == ipInt){
				logger.debug("当前IP在黑名单");
				return false;
			}
		}
		//检查访问标识
		for(String agent : userAgentList){
			if(userAgent.indexOf(agent) >= 0){
				logger.debug("该User-Agent是正常爬虫，通过过滤器");
				//如果本次正常通过，则清空收集的黑名单中的统计次数	
//				bufferMap.remove(ipINT);
				return true;
			}
		}
		//判断是否站内跳转
		if(StringUtils.isNotBlank(referer)){
			logger.debug("该访问时站内跳转，通过过滤器");
			//如果本次正常通过，则清空收集的黑名单中的统计次数	
//			bufferMap.remove(ipINT);
			return true;
		}
		
		
		//判断是否清空bufferMap
		if(System.currentTimeMillis() - LAST_CLEAR_TIME > CLEAN_TIME_CIRCLE){
			LAST_CLEAR_TIME = System.currentTimeMillis();
			bufferMap.clear();
		}
		
		//判断该访问是否在自动统计的黑名单中
		//判断当前访问次数是否超过阀值
		AtomicLong count = null;
		if((count = bufferMap.get(ipINT)) != null
				&& count.longValue() >= ReptileFilter.MAX_NUM){
			logger.warn("当前IP在在统计的黑名单中，并且访问量超过最大访问值:"+count.incrementAndGet());
			count.incrementAndGet();
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print("<html><body><p>403 禁止访问!<br>IP:"+ userIP +"</p></body></html>");
			return false;
		}else{
			if(count == null){
				count = new AtomicLong(1);
				bufferMap.put(ipInt, count);
			}else{
				count.incrementAndGet();
			}
			return true;
		}
		
	}

}
