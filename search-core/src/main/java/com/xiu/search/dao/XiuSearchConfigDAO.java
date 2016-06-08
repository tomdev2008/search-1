package com.xiu.search.dao;

import java.sql.SQLException;
import java.util.List;

import com.xiu.search.dao.model.XiuSearchConfigModel;

/**
 * 获取搜索数据库中配置
 * @author Wuyongqi
 *
 */
public interface XiuSearchConfigDAO {

    /** 
     * 从数据库(XIU_SEARCH_CONFIG)获取搜索配置
     * @return
     * @throws SQLException
     */
    public List<XiuSearchConfigModel> selectAllXiuSearchConfig() throws SQLException ;

}