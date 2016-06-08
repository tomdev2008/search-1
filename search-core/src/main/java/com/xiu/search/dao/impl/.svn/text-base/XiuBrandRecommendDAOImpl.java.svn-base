package com.xiu.search.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.xiu.search.dao.XiuBrandRecommendDAO;
import com.xiu.search.dao.model.XiuBrandRecommend;

/**
 * 绑定分类相关的设置，由SearchAdmin后台设置
 * @author Leon
 *
 */
public class XiuBrandRecommendDAOImpl extends SqlSessionDaoSupport implements XiuBrandRecommendDAO{
	
	/**
	 * 查询运营设置的一些条件
	 * @param recommendId
	 * @return
	 */
	public List<XiuBrandRecommend> selectByPrimaryKeyForBusiness(Integer brandId,Integer[] types){
		if(null == brandId)
			return null;
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("brandId", brandId);
		paraMap.put("types", types);
		return getSqlSession().selectList("XIU_BRAND_RECOMMEND.selectByPrimaryKeyForBusiness",paraMap);
	}

	@Override
	public List<XiuBrandRecommend> selectByPKAndTypesAndMktTypeForBusiness(
			Integer brandId, Integer[] types, Integer mktType) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("brandId", brandId);
		paraMap.put("types", types);
		paraMap.put("mktType", mktType);
		return getSqlSession().selectList("XIU_BRAND_RECOMMEND.selectByPKAndTypesAndMktTypeForBusiness",paraMap);
	}

	/* (non-Javadoc)
	 * @see com.xiu.search.dao.XiuBrandRecommendDAO#selectAllBrand()
	 */
	@Override
	public List<XiuBrandRecommend> selectAllBrandRecommend() {
		return getSqlSession().selectList("XIU_BRAND_RECOMMEND.selectAllBrandRecommend");
	}
	
}