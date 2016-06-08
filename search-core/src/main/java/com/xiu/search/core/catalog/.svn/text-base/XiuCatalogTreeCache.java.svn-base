package com.xiu.search.core.catalog;

import java.util.List;

import com.xiu.search.core.solr.enumeration.ItemShowTypeEnum;

/**
 * 
 * com.xiu.search.core.catalog.XiuCatalogTreeCache.java

 * @Description: TODO SearchWeb 对应的运营分类树相关操作

 * @author lvshuding   

 * @date 2013-6-27 下午12:05:21

 * @version V1.0
 */
public abstract class XiuCatalogTreeCache {

	private static XiuCatalogTreeCache instance;
	
	public static XiuCatalogTreeCache getInstance(){
		if(instance==null){
			instance=new XiuCatalogTreeCacheImpl();
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
	 * 从缓存中加载所有运营分类树 display=1
	 * @return
	 */
	public abstract List<CatalogModel> getTree();
	
	/**
	 * 从缓存中加载所有运营分类树 display=1,2
	 * @return
	 */
	public abstract List<CatalogModel> getTree12();
	
	/**
	 * 根据ItemShowType从缓存中加载所有运营分类树
	 * @param itemShoTypeEnum
	 * @return
	 */
	public abstract List<CatalogModel> getTree(ItemShowTypeEnum itemShoTypeEnum);
	
	/**
	 * 根据运营分类ID从缓存中加载对应的运营分类对象 display=1
	 * @param catalogID
	 * @return
	 */
	public abstract CatalogModel getTreeNodeById(String catalogID);
	
	/**
	 * 根据运营分类ID从缓存中加载对应的运营分类对象 display=12
	 * @param catalogID
	 * @return
	 */
	public abstract CatalogModel getTreeNodeById12(String catalogID);
	
	/**
	 * 根据运营分类ID从缓存中加载对应的运营分类对象  查询treeMap_0
	 * @param catalogID
	 * @return
	 */
	public abstract CatalogModel getTreeNodeByIdFromTreeMap0(String catalogID);
	
	
	/**
	 * 根据运营分类ID和ItemShowType从缓存中加载对应的运营分类对象
	 * @param catalogId
	 * @param itemShowTypeEnum
	 * @return
	 */
	public abstract CatalogModel getTreeNodeById(String catalogId,ItemShowTypeEnum itemShoTypeEnum);
	
	/**
	 * 获取所有显示的一级分类
	 * @return
	 */
	public abstract List<CatalogModel> getAllFirstCataList();
	
}
