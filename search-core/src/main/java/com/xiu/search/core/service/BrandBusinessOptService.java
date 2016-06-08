package com.xiu.search.core.service;

import java.util.Map;

import com.xiu.search.core.model.BrandBusinessOptModel;

/**
 * 关于运营分类，运营人员业务设置相关的服务类
 * @author Leon
 *
 */
public interface BrandBusinessOptService {

	/**
	 * 根据品牌ID查询该运营分类下运营设置
	 * @param brandId	品牌ID
	 * @param types  	业务类型，1：品牌下分类；2：品牌下商品
	 * @param mktType 	业务范围 0：官网，2：Ebay
	 * @return
	 */
	public BrandBusinessOptModel getGoodsItemsTopShow(Integer brandId,Integer mktType);
	
	/**
	 * 根据品牌ID查询该品牌下运营分类设置
	 * @param brandId	品牌ID
	 * @param mktType 	业务范围 0：官网，2：Ebay
	 * @return
	 */
	public BrandBusinessOptModel getCatItemsTopShow(Integer brandId,Integer mktType);
	
	/**
	 * 加载所有品牌下的推荐数据【放入本地缓存时用】
	 * @return key: brandId_mktType  
	 */
	public Map<String, BrandBusinessOptModel> getAllTopShow();
	
}
