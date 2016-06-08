package com.xiu.search.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danga.MemCached.MemCachedClient;
import com.xiu.search.core.service.MemCachedService;

@Service("memCachedService")
public class MemCachedServiceImpl implements MemCachedService {
	
	@Autowired
	private MemCachedClient memCachedClient;
	
	@Override
	public Object get(String key) {
		return memCachedClient.get(key);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map getMulti(List<String> keys) {
		if(keys == null || keys.size() == 0){ 
            return new HashMap(0); 
        } else { 
            String strKeys[] = new String[keys.size()]; 
            strKeys = (String[])keys.toArray(strKeys); 
            return memCachedClient.getMulti(strKeys); 
        } 
	}

	@Override
	public Map<String, Map<String, String>> getStatsItems() {
		return memCachedClient.statsItems();
	}

	@Override
	public Map<String, Map<String, String>> getStatsCacheDump(int dump, int num) {
		return memCachedClient.statsCacheDump(dump, num);
	}
	
	

}
