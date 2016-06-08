package com.xiu.search.dao;

import java.util.List;

import com.xiu.search.dao.model.XiuCatalogDBModel;

/**
 * 运营分类DAO接口
 * @author wuyongqi
 */
public interface XiuCatalogDAO { 
	
    
    /**
     * 从商品中心数据库查询运营分类列表(只查询diaplay=1)
     * @return
     */
    public List<XiuCatalogDBModel> selectAllLevel3CatalogListByDB() ;
    
    /**
     * 从商品中心数据库查询所有运营分类列表(查询diaplay=1、0)
     * @return
     */
    public List<XiuCatalogDBModel> selectAllLevel3CatalogListByDB2() ;
    
    
}