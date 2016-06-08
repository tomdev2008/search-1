package com.xiu.search.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.xiu.search.dao.XDataCatalogDAO;
import com.xiu.search.dao.config.XiuSearchConfig;
import com.xiu.search.dao.model.XDataCatalog;

/**
 * 运营分类DAO类
 * @author wuyongqi
 */
public class XDataCatalogDAOImpl extends SqlSessionDaoSupport implements XDataCatalogDAO{

    /**
     * 根据运营分类Id，从数据库中查找当前分类及所有子节点的查询query
     * @param catalogId 运营分类ID
     * @return List<XDataCatalog> 
     */
    public List<XDataCatalog> selectCatalogQueryByCatalogId(Long catalogId) {
    	long begin=System.currentTimeMillis();
    	List<XDataCatalog> dataCataLog = getSqlSession().selectList("XIU_GOODS_CATGROUP.selectCatalogQueryListByCatalogId", catalogId);
    	long end=System.currentTimeMillis();
		logger.info("query catalog execute time:"+(end-begin));
		if(Boolean.valueOf(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_SWITCH))&&
				Integer.parseInt(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_OVERTIME))<(end-begin)){
			logger.warn("query catalog name execute overtime:"+(end-begin));
		}
        return null!=dataCataLog?dataCataLog:null;
    }
    
}
