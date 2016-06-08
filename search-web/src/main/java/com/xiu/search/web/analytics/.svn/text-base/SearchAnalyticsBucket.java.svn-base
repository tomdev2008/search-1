package com.xiu.search.web.analytics;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 
 * 记录搜索入口信息，用于短期分析搜索入口用户行为
 * 
 * @author Leon
 *
 */
public class SearchAnalyticsBucket {

	private final static String PREFIX_SUGGEST = "s_";
	
	/**
	 * 搜索总次数
	 */
	private static AtomicLong totalLookups;
	
	/**
	 * 通过suggest搜索的次数
	 */
	private static Map<String, AtomicLong> srcLookups;
	
	static{
		totalLookups = new AtomicLong();
		srcLookups = new HashMap<String, AtomicLong>();
		srcLookups.put(PREFIX_SUGGEST+"ori", new AtomicLong());
		for (int i = 1; i <= 12; i++) {
			srcLookups.put(PREFIX_SUGGEST+i, new AtomicLong());
		}
	}
	
	public static void increment(String src){
		totalLookups.incrementAndGet();
		if(null != src && srcLookups.containsKey(src)){
			srcLookups.get(src).incrementAndGet();
		}else{
			srcLookups.get(PREFIX_SUGGEST+"ori").incrementAndGet();
		}
	}
	
	public static long getTotalLookups(){
		return totalLookups.longValue();
	}
	
	public static Map<String,Long> getSrcLookups(){
		Map<String,Long> ret = new HashMap<String, Long>();
		for (Entry<String, AtomicLong> e : srcLookups.entrySet()) {
			ret.put(e.getKey(), e.getValue().longValue());
		}
		return ret;
	}
}
