package com.xiu.search.core.service;

import java.util.List;
import java.util.Map;

/**
 * 获得memcache值
 * @author Lipsion
 *
 */
public interface MemCachedService {
	
	public Object get(String key);
	
	@SuppressWarnings("rawtypes")
	public Map getMulti(List<String> keys);
	
	/**
	 * 辅助方法【XiuRecommonCache.reload()使用】
	 * @return
	 */
	public Map<String, Map<String, String>> getStatsItems();
	
	/**
	 * 辅助方法【XiuRecommonCache.reload()使用】
	 * @param dump
	 * @param num
	 * @return
	 */
	public Map<String, Map<String, String>> getStatsCacheDump(int dump,int num);
}
