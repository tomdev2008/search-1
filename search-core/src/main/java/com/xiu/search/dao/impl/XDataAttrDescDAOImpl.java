package com.xiu.search.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.xiu.search.dao.XDataAttrDescDAO;
import com.xiu.search.dao.config.XiuSearchConfig;
import com.xiu.search.dao.model.XDataAttrDesc;

/** 
 * @Title: XDataAttrDescDAOImpl.java 
 *
 * @Package com.xiu.search.dao.impl 
 *
 * @Description:   
 *
 * @author lvshuding 
 *
 * @date 2014年7月21日 上午11:38:17 
 *
 * @version V1.0 
 */
public class XDataAttrDescDAOImpl extends SqlSessionDaoSupport implements XDataAttrDescDAO {

	@Override
	public Map<String, String> getAllAttrGroupName() {
		long begin=System.currentTimeMillis();
		List<XDataAttrDesc> list = getSqlSession().selectList("XIU_AttrGroup_DESC.selectAllGroupName");
		long end=System.currentTimeMillis();
		logger.info("query brand name execute time:"+(end-begin));
		if(Boolean.valueOf(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_SWITCH))&&
				Integer.parseInt(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_OVERTIME))<(end-begin)){
			logger.warn("query brand name execute overtime:"+(end-begin));
		}
		
		if(list==null || list.size()==0){
			return null;
		}
		Map<String, String> rstMap = new HashMap<String, String>();
		for(XDataAttrDesc d:list){
			rstMap.put(d.getId(), d.getName());
		}
		
		return rstMap;
	}

}
