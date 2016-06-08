package com.xiu.search.dao;

import java.util.List;

import com.xiu.search.dao.model.XTag;

public interface XTagDAO {

	XTag selectById(Integer id);
	
	List<XTag> selectByIds(List<Integer> ids);
	
	XTag selectByName(String name);
	
	List<XTag> selectByNames(List<String> names);
	
}
