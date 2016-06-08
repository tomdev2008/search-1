package com.xiu.search.core.service;

import java.util.Map;

import com.xiu.search.core.model.CatalogBusinessOptModel;
import com.xiu.solr.lexicon.client.model.QueryParseModel;

/**
 * 关于运营分类，运营人员业务设置相关的服务类
 * @author Leon
 *
 */
public interface CatalogBusinessOptService {

	/**
	 * 根据运营分类ID查询该运营分类下运营设置
	 * @return
	 */
	@Deprecated
	public CatalogBusinessOptModel getGoodsItemsTopShow(Integer catalogId,Integer[] types,Integer mktType);
	
	/**
	 * 获取某品牌下的商业配置
	 * @param brandId
	 * @return
	 */
	public CatalogBusinessOptModel getBrandHotBusinessInfo(Integer brandId);
	
	/**
	 * 暂时仅支持品牌
	 * @param queryParseModel
	 * @return
	 */
	public CatalogBusinessOptModel getBusinessByQueryParseModel(QueryParseModel queryParseModel);
	
	/**
	 * 加载所有分类下的推荐数据【放入本地缓存时用】
	 * @return key: catalogId_mktType  
	 */
	public Map<String,CatalogBusinessOptModel> getAllTopShow();
		
}
