package com.xiu.search.dao.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.xiu.search.core.model.AttrGroupJsonModel;
import com.xiu.search.dao.XSalesCatalogCondDAO;
import com.xiu.search.dao.config.XiuSearchConfig;
import com.xiu.search.dao.model.XSalesCatalogCond;

public class XSalesCatalogCondDAOImpl extends SqlSessionDaoSupport implements XSalesCatalogCondDAO {

	@Override
	public XSalesCatalogCond selectByPrimaryKey(Long catGroupId) {
		long begin=System.currentTimeMillis();
		XSalesCatalogCond xSalesCatalogCond=(XSalesCatalogCond)getSqlSession().selectOne("XIU_GOODS_X_SALES_CATALOG_COND.selectByPrimaryKey",catGroupId);
		long end=System.currentTimeMillis();
		logger.info("selectByPrimaryKey query attrGroup object execute time:"+(end-begin));
		if(Boolean.valueOf(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_SWITCH))&&
				Integer.parseInt(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_OVERTIME))<(end-begin)){
			logger.warn(" selectByPrimaryKey query attrGroup object execute overtime:"+(end-begin));
		}
		return xSalesCatalogCond;
	}

	@Override
	public String selectByPrimaryKeyForJson(Long catGroupId) {
		long begin=System.currentTimeMillis();
		String json=(String)getSqlSession().selectOne("XIU_GOODS_X_SALES_CATALOG_COND.selectByPrimaryKeyForJson",catGroupId);
		long end=System.currentTimeMillis();
		logger.info("selectByPrimaryKeyForJson query attrGroup info execute time:"+(end-begin));
		if(Boolean.valueOf(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_SWITCH))&&
				Integer.parseInt(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_OVERTIME))<(end-begin)){
			logger.warn("selectByPrimaryKeyForJson query attrGroup name execute overtime:"+(end-begin));
		}
		return json;
	}

	@Override
	public Map<Long, String> selectByPrimaryKeysForJson(List<Long> catGroupIds) {
		long begin=System.currentTimeMillis();
		List<XSalesCatalogCond> xSalesCatalogCond=getSqlSession().selectList("XIU_GOODS_X_SALES_CATALOG_COND.selectByPrimaryKeysForJson",catGroupIds);
		long end=System.currentTimeMillis();
		logger.info("selectByPrimaryKeysForJson query attrGroup info execute time:"+(end-begin));
		if(Boolean.valueOf(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_SWITCH))&&
				Integer.parseInt(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_OVERTIME))<(end-begin)){
			logger.warn("selectByPrimaryKeysForJson query attrGroup name execute overtime:"+(end-begin));
		}
		if(null == xSalesCatalogCond || xSalesCatalogCond.size()==0)
			return Collections.emptyMap();
		Map<Long, String> ret = new HashMap<Long, String>();
		for (XSalesCatalogCond xSalesCatalogCond2 : xSalesCatalogCond) {
			if(null != xSalesCatalogCond2 
					&& xSalesCatalogCond2.getCatgroupId() != null
					)
				ret.put(xSalesCatalogCond2.getCatgroupId(), xSalesCatalogCond2.getField2());
		}
		return ret;
	}

	@Override
	public Map<Long, String> selectAllForAttrGroup() {
		long begin=System.currentTimeMillis();
		List<XSalesCatalogCond> xSalesCatalogCond=getSqlSession().selectList("XIU_GOODS_X_SALES_CATALOG_COND.selectAllForAttrGroup");
		long end=System.currentTimeMillis();
		logger.info("selectAllForAttrGroup query attrGroup info execute time:"+(end-begin));
		if(Boolean.valueOf(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_SWITCH))&&
				Integer.parseInt(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_OVERTIME))<(end-begin)){
			logger.warn("selectAllForAttrGroup query attrGroup name execute overtime:"+(end-begin));
		}
		Map<Long, String> ret = new HashMap<Long, String>();
		for (XSalesCatalogCond xSalesCatalogCond2 : xSalesCatalogCond) {
			if(null != xSalesCatalogCond2 
					&& xSalesCatalogCond2.getCatgroupId() != null && xSalesCatalogCond2.getField2()!=null
					)
				ret.put(xSalesCatalogCond2.getCatgroupId(), xSalesCatalogCond2.getField2());
		}
		return ret;	
	}

    /**
     * 取得全部的属性项 的值		William.zhang	20130705
     * @return
     */
	@Override
	public Map<Long, Integer> selectAllForAttrValue() {
		Map<Long,Integer> ret = new HashMap<Long,Integer>();
		long begin=System.currentTimeMillis();
		List<XSalesCatalogCond> xSalesCatalogCond=getSqlSession().selectList("XIU_GOODS_X_SALES_CATALOG_COND.selectAllForAttrValue");
		long end=System.currentTimeMillis();
		logger.info("selectAllForAttrValue query attrGroup info execute time:"+(end-begin));
		if(Boolean.valueOf(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_SWITCH))&&
				Integer.parseInt(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_OVERTIME))<(end-begin)){
			logger.warn("selectAllForAttrValue query attrGroup name execute overtime:"+(end-begin));
		}
		if(xSalesCatalogCond != null){
			for(XSalesCatalogCond xSalesCatalogCond2 : xSalesCatalogCond){
				if(xSalesCatalogCond2 != null && xSalesCatalogCond2.getAttrdictgrpId() != null){
					ret.put(xSalesCatalogCond2.getAttrdictgrpId(), xSalesCatalogCond2.getOrderBy());
				}
			}	
		}
		return ret;
	}
  
}