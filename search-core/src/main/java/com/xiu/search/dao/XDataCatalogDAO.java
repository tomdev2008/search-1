package com.xiu.search.dao;

import java.util.List;

import com.xiu.search.dao.model.XDataCatalog;

/**
 * 运营分类DAO接口
 * @author wuyongqi
 */
public interface XDataCatalogDAO { 
	
    /**
     * 根据运营分类Id，从数据库中查找当前分类及所有子节点的查询query
     * @param catalogId 运营分类ID
     * @return List<XDataCatalog> 
     */
    public List<XDataCatalog> selectCatalogQueryByCatalogId(Long catalogId);
    
}