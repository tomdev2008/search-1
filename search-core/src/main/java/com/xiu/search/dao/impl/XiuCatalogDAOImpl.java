package com.xiu.search.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.xiu.search.dao.XiuCatalogDAO;
import com.xiu.search.dao.model.XiuCatalogDBModel;

/**
 * 运营分类DAO类
 * @author wuyongqi
 */
public class XiuCatalogDAOImpl extends SqlSessionDaoSupport implements XiuCatalogDAO {

    /**
     * 从商品中心数据库查询运营分类列表
     * @return
     */
    @Override
    public List<XiuCatalogDBModel> selectAllLevel3CatalogListByDB() {
        List<XiuCatalogDBModel> list =super.getSqlSession().selectList("XIU_CATALOG.getAllLevel3CatalogByDB");
        return list;
    }
    
    @Override
    public List<XiuCatalogDBModel> selectAllLevel3CatalogListByDB2() {
        List<XiuCatalogDBModel> list =super.getSqlSession().selectList("XIU_CATALOG.getAllLevel3CatalogByDB2");
        return list;
    }
    
}
