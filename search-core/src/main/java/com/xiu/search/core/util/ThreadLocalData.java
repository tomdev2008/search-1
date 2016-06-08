package com.xiu.search.core.util;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalData {

	public enum ThreadLocalKey{
		/**
		 * 请求url
		 */
		REQUEST_URL
	}
	
	private static ThreadLocalData threadLocalData = new ThreadLocalData();
	
	private static ThreadLocal<Map> threadLocal = new ThreadLocal<Map>();
	
	public static ThreadLocalData getInstance(){
		return threadLocalData;
	}
	
	public Object get(ThreadLocalKey key) {
		Map data = threadLocal.get();
		if(null == data)
			return null;
		return data.get(key);
	}
	
	public void put(ThreadLocalKey key,Object value){
		Map data = threadLocal.get();
		if(null == data){
			data = new HashMap();
			threadLocal.set(data);
		}
		data.put(key, value);
	}
	
	public void clear(){
		Map data = threadLocal.get();
		if (data != null){
			data.clear();
		}
	}
	
}
