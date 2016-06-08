package com.xiu.search.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.xiu.search.dao.XiuSEOKeywordDAO;
import com.xiu.search.dao.model.XiuSeoKeywordModel;

public class XiuSEOKeywordDAOImpl extends SqlSessionDaoSupport implements
		XiuSEOKeywordDAO {

	@Override
	public XiuSeoKeywordModel selectById(int id) {
		return getSqlSession().selectOne("XIU_SEO_KEYWORD.selectById",id);
	}

	@Override
	public List<XiuSeoKeywordModel> selectPageList(int firstRow, int endRow) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("firstRow", firstRow);
		paraMap.put("endRow", endRow);
		return getSqlSession().selectList("XIU_SEO_KEYWORD.selectPageList", paraMap);
	}

	@Override
	public int selectTotCount() {
		Object obj=getSqlSession().selectOne("XIU_SEO_KEYWORD.selectTotCount");
		return obj==null?0:((Integer)obj).intValue();
	}
	
}
