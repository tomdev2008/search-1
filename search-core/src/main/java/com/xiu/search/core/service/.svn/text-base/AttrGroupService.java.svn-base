package com.xiu.search.core.service;

import java.util.Map;

import com.xiu.search.core.model.AttrGroupJsonModel;

public interface AttrGroupService {
	/**
	 * 查询属性项值
	 * Map<String,String>  Map[key:attr_*,value:name}
	 * Map为有序的LinkedHashMap,数据库为空时返回Null
	 * @param attrGroupId 运营分类ID
	 * @return
	 */
//	public Map<String, AttrGroupJsonModel> getAttrGroupIdNameList(Long attrGroupId);
	
	/**
	 * 带继承关系的查询属性项值
	 * @param attrGroupIds
	 * @return
	 */
	public Map<String, AttrGroupJsonModel> getAttrGroupIdNameListWithInherit(Long categoryId);
	
	
}
