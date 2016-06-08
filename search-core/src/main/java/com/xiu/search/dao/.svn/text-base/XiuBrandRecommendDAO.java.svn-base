package com.xiu.search.dao;

import java.util.List;

import com.xiu.search.dao.model.XiuBrandRecommend;

public interface XiuBrandRecommendDAO {
	
	/**
	 * 查询品牌对应的运营设置的一些条件
	 * @param brandId
	 * @return
	 */
	public List<XiuBrandRecommend> selectByPrimaryKeyForBusiness(Integer brandId,Integer[] types);
	
	/**
	 * 查询品牌对应的运营设置的条件
	 * @param brandId
	 * @param types   业务类型，1：品牌下分类；2：品牌下商品
	 * @param mktType 业务范围 0：官网，2：Ebay
	 * @return
	 */
	public List<XiuBrandRecommend> selectByPKAndTypesAndMktTypeForBusiness(Integer brandId,Integer[] types,Integer mktType);
	
	/**
	 * 查询所有品牌信息，（ID,bizType,mktType）
	 * @return
	 */
	public List<XiuBrandRecommend> selectAllBrandRecommend();
}