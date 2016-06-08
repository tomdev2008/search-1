package com.xiu.search.dao;

import java.util.List;

import com.xiu.search.dao.model.XiuCatRecommend;

public interface XiuCatRecommendDAO {
	
//	XiuCatItemRecommend selectByPrimaryKey(Integer recommendId);
	
	/**
	 * 查询运营设置的一些条件
	 * @param catalogId
	 * @return
	 */
	public List<XiuCatRecommend> selectByPrimaryKeyForBusiness(Integer catalogId,Integer[] types,Integer mktType);
	
	/**
	 * 查询所有分类推荐数据（ID，bizType,mktType）
	 * @return
	 */
	public List<XiuCatRecommend> selectAllCatRecommend();
}