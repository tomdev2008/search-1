package com.xiu.search.core.recom;

import com.xiu.search.core.model.BrandBusinessOptModel;
import com.xiu.search.core.model.CatalogBusinessOptModel;
import com.xiu.search.core.solr.enumeration.MktTypeEnum;

/**
 * 
 * com.xiu.search.core.recom.XiuRecommonCache.java

 * @Description: TODO  运营推荐数据缓存接口

 * @author lvshuding   

 * @date 2013-7-23 上午10:01:36

 * @version V1.0
 */
public abstract class XiuRecommonCache {
	
	private static XiuRecommonCache instance;
	
	public static XiuRecommonCache getInstance(){
		if(instance==null){
			instance=new XiuRecommonCacheImpl();
		}
		return instance;
	}
	
	/**
	 * 初始化数据，只有子类能调用
	 */
	protected abstract void init();
	
	/**
	 * 重载数据，只有子类能调用 
	 */
	protected abstract void reload();
	
	/**
	 * 根据品牌ID和MktType加载品牌下的推荐数据
	 * @param brandID
	 * @param mktType
	 * @return
	 */
	
	public abstract BrandBusinessOptModel loadBrandRecommenedByIdAndMkt(Long brandID,MktTypeEnum mktType);
	
	/**
	 * 根据运营分类ID和MktType加载运营分类下的推荐数据
	 * @param catID
	 * @param mktType
	 * @return
	 */
	public abstract CatalogBusinessOptModel loadCatlogRecommenedByIdAndMkt(Long catID,MktTypeEnum mktType);
	
}
