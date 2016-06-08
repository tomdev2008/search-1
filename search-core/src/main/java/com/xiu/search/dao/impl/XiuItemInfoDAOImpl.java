package com.xiu.search.dao.impl;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.xiu.search.dao.XiuItemInfoDAO;

public class XiuItemInfoDAOImpl extends SqlSessionDaoSupport implements XiuItemInfoDAO {

	@Override
	public String selectByPartNumberForSimilarGroupId(String partNumber) {
		Object obj=getSqlSession().selectOne("XIU_ITEM_INFO.selectByPartNumberForSimilarGroupId",partNumber);
		return obj==null?null:obj.toString();
	}

	@Override
	public long selectCountForId() {
		Object obj=getSqlSession().selectOne("XIU_ITEM_INFO.selectCountForId");
		return obj==null?0l:((Long)obj).longValue();
	}
	
	

}
