package com.xiu.search.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.xiu.search.dao.XDataBrandDAO;
import com.xiu.search.dao.config.XiuSearchConfig;
import com.xiu.search.dao.model.XDataBrand;

public class XDataBrandDAOImpl extends SqlSessionDaoSupport implements XDataBrandDAO {
	
	@Override
	public XDataBrand selectByPrimaryKey(Long brandId) {
		long begin=System.currentTimeMillis();
		XDataBrand xDataBrand=(XDataBrand) getSqlSession().selectOne("XIU_GOODS_X_DATA_BRAND.selectByPrimaryKey", brandId);
		long end=System.currentTimeMillis();
		logger.info("query brand object execute time:"+(end-begin));
		if(Boolean.valueOf(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_SWITCH))&&
				Integer.parseInt(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_OVERTIME))<(end-begin)){
			logger.warn("query brand object execute overtime:"+(end-begin));
		}
		return xDataBrand;
	}

	@Override
	public String selectByPrimaryKeyForMainName(Long brandId) {
		long begin=System.currentTimeMillis();
		String brandName=(String)getSqlSession().selectOne("XIU_GOODS_X_DATA_BRAND.selectByPrimaryKeyForMainName",brandId);
		long end=System.currentTimeMillis();
		logger.info("query brand name execute time:"+(end-begin));
		if(Boolean.valueOf(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_SWITCH))&&
				Integer.parseInt(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_OVERTIME))<(end-begin)){
			logger.warn("query brand name execute overtime:"+(end-begin));
		}
		return brandName;
	}

	@Override
	public List<XDataBrand> selectAllBrandInfo() {
		long begin=System.currentTimeMillis();
		List<XDataBrand> ret = getSqlSession().selectList("XIU_GOODS_X_DATA_BRAND.selectAllBrandInfo");
		long end=System.currentTimeMillis();
		logger.info("query all brand object execute time:"+(end-begin));
		if(Boolean.valueOf(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_SWITCH))&&
				Integer.parseInt(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_OVERTIME))<(end-begin)){
			logger.warn("query all brand object execute overtime:"+(end-begin));
		}
		return ret;
	}

	
	@Override
	public List<XDataBrand> selectAllByShowFlag1AndHasGoods() {
		long begin=System.currentTimeMillis();
		List<XDataBrand> ret = getSqlSession().selectList("XIU_GOODS_X_DATA_BRAND.selectAllByShowFlag1AndHasGoods");
		long end=System.currentTimeMillis();
		logger.info("query all brand object execute time:"+(end-begin));
		if(Boolean.valueOf(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_SWITCH))&&
				Integer.parseInt(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_OVERTIME))<(end-begin)){
			logger.warn("query all brand object execute overtime:"+(end-begin));
		}
		return ret;
	}
	
	
}