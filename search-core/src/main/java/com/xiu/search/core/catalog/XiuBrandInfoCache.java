package com.xiu.search.core.catalog;


public abstract class XiuBrandInfoCache {
	private static XiuBrandInfoCache instance;
	
	public static XiuBrandInfoCache getInstance(){
		if(instance == null)
			instance = new XiuBrandInfoCacheImpl();
		return instance;
	}
	
	protected abstract void init();
	
	public abstract BrandModel getBrandById(Long brandId);
	
	
}
