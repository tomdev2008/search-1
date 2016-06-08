package com.xiu.search.core.service;

import java.util.List;
import java.util.Map;

import com.xiu.search.dao.model.XTag;

public interface XTagService {

	Map<Integer,XTag> findXTagMapByIds(List<Integer> xTagIds);
	
	XTag findXTagMapById(Integer xTagId);
	
	XTag findXTagMapByName(String xTagName);
	
	Map<Integer,Integer> findXTagItemCountByIdsFromIndex(List<Integer> xTagIds);
	
}
