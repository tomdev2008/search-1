package com.xiu.search.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.xiu.search.dao.XiuSearchConfigDAO;
import com.xiu.search.dao.model.XiuSearchConfigModel;

public class XiuSearchConfigDAOImpl extends SqlSessionDaoSupport implements XiuSearchConfigDAO {

    @Override
    public List<XiuSearchConfigModel> selectAllXiuSearchConfig() throws SQLException {
        return super.getSqlSession().selectList("XIU_SEARCH_CONFIG.selectAllXiuSearchConfig");
    }

}