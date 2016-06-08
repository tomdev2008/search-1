package com.xiu.search.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.xiu.search.dao.XTagDAO;
import com.xiu.search.dao.config.XiuSearchConfig;
import com.xiu.search.dao.model.XTag;

public class XTagDAOImpl extends SqlSessionDaoSupport implements XTagDAO{

	@Override
	public XTag selectById(Integer id) {
		if(id == null)
			return null;
		long begin=System.currentTimeMillis();
		XTag xTags=(XTag)getSqlSession().selectOne("XIU_TAG.selectById",id);
		long end=System.currentTimeMillis();
		logger.info("XTagsDAOImpl.selectById execute time:"+(end-begin));
		if(Boolean.valueOf(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_SWITCH))&&
				Integer.parseInt(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_OVERTIME))<(end-begin)){
			logger.warn("XTagsDAOImpl.selectById execute overtime:"+(end-begin));
		}
		return xTags;
	}
	
	@Override
	public List<XTag> selectByIds(List<Integer> ids) {
		if(ids == null || ids.size()==0)
			return null;
		long begin=System.currentTimeMillis();
		List<XTag> xTags=getSqlSession().selectList("XIU_TAG.selectByIds",ids);
		long end=System.currentTimeMillis();
		logger.info("XTagsDAOImpl.selectByIds execute time:"+(end-begin));
		if(Boolean.valueOf(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_SWITCH))&&
				Integer.parseInt(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_OVERTIME))<(end-begin)){
			logger.warn("XTagsDAOImpl.selectByIds execute overtime:"+(end-begin));
		}
		return xTags;
	}

	@Override
	public XTag selectByName(String name) {
		if(null == name || name.length() == 0)
			return null;
		long begin=System.currentTimeMillis();
		XTag xTags=(XTag)getSqlSession().selectOne("XIU_TAG.selectByName",name);
		long end=System.currentTimeMillis();
		logger.info("XTagsDAOImpl.selectByName execute time:"+(end-begin));
		if(Boolean.valueOf(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_SWITCH))&&
				Integer.parseInt(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_OVERTIME))<(end-begin)){
			logger.warn("XTagsDAOImpl.selectByName execute overtime:"+(end-begin));
		}
		return xTags;
	}

	@Override
	public List<XTag> selectByNames(List<String> names) {
		if(null == names || names.size()==0)
			return null;
		long begin=System.currentTimeMillis();
		List<XTag> xTags= getSqlSession().selectList("XIU_TAG.selectByNames",names);
		long end=System.currentTimeMillis();
		logger.info("XTagsDAOImpl.selectByNames execute time:"+(end-begin));
		if(Boolean.valueOf(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_SWITCH))&&
				Integer.parseInt(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_OVERTIME))<(end-begin)){
			logger.warn("XTagsDAOImpl.selectByNames execute overtime:"+(end-begin));
		}
		return xTags;
	}

}
