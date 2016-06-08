package com.xiu.search.core.attrval;

/** 
 * @Title: XiuAttrGroupInfoCache.java 
 *
 * @Package com.xiu.search.core.attrval 
 *
 * @Description:  属性项名称缓存
 *
 * @author lvshuding 
 *
 * @date 2014年7月21日 上午11:13:07 
 *
 * @version V1.0 
 */
public abstract class XiuAttrGroupInfoCache {

	private static XiuAttrGroupInfoCache instance;
	
	public static XiuAttrGroupInfoCache getInstance(){
		if(instance==null){
			instance = new XiuAttrGroupInfoCacheImpl();
		}
		return instance;
	}
	
	/**
	 * 初始化属性项名称数据
	 */
	protected abstract void init();
	/**
	 * 根据属性项ID获取数属项名称数据
	 */
	protected abstract String getAttrGrouNameByID(String id);
	
	/**
	 * @Description: 获取属性项名称缓存大小
	 * @return    
	 * @return int
	 */
	protected abstract int getCacheSize();
}
