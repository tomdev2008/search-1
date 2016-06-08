package com.xiu.search.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.xiu.search.dao.XiuCatRecommendDAO;
import com.xiu.search.dao.model.XiuCatRecommend;

/**
 * 绑定分类相关的设置，由SearchAdmin后台设置
 * @author Leon
 *
 */
public class XiuCatRecommendDAOImpl extends SqlSessionDaoSupport implements XiuCatRecommendDAO{
	
//	XiuCatItemRecommend selectByPrimaryKey(Integer recommendId);
	
	/**
	 * 查询运营设置的一些条件
	 * @param recommendId
	 * @return
	 */
	public List<XiuCatRecommend> selectByPrimaryKeyForBusiness(Integer catalogId,Integer[] types,Integer mktType){
		if(null == catalogId)
			return null;
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("catalogId", catalogId);
		paraMap.put("types", types);
		paraMap.put("mktType", mktType);
		return getSqlSession().selectList("XIU_CAT_RECOMMEND.selectByPrimaryKeyForBusiness",paraMap);
	}

	@Override
	public List<XiuCatRecommend> selectAllCatRecommend() {
		return getSqlSession().selectList("XIU_CAT_RECOMMEND.selectAllCatRecommend");
	}

}