package com.xiu.search.core.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.search.core.attrval.XiuAttrGroupCache;
import com.xiu.search.core.bo.CatalogBo;
import com.xiu.search.core.bof.CatalogBof;
import com.xiu.search.core.model.AttrGroupJsonModel;
import com.xiu.search.core.model.AttrGroupJsonModel.AttrGroupTypeEnum;
import com.xiu.search.core.service.AttrGroupService;
import com.xiu.search.core.service.CatalogService;
import com.xiu.search.core.solr.index.GoodsIndexFieldEnum;
import com.xiu.search.dao.XSalesCatalogCondDAO;
import com.xiu.search.dao.cache.CacheManage;
import com.xiu.search.dao.cache.CacheTypeEnum;

@Service("attrGroupService")
public class AttrGroupServiceImpl implements AttrGroupService {

	private static String[] EMPTY_STRING_ARRAY = new String[0];
	
	private Logger logger = Logger.getLogger(getClass());
	
	
//	@SuppressWarnings("unchecked")
//	public Map<String, AttrGroupJsonModel> getAttrGroupIdNameList(Long attrGroupId) {
//		String attrGroupIdStr = Long.toString(attrGroupId);
////		if(CacheManage.containsKey(attrGroupIdStr,CacheTypeEnum.CATAGROUP_ATTR)){
////			Object o=CacheManage.get(attrGroupIdStr,CacheTypeEnum.CATAGROUP_ATTR);
//////			Collections.EMPTY_MAP;
////			return null!=o?(Map<String, String>)o:null;
////		}
//		Object o = CacheManage.get(attrGroupIdStr,CacheTypeEnum.CATAGROUP_ATTR);
//		if(o != null)
//			return (Map<String,AttrGroupJsonModel>)o;
//		String json=null;
//		try {
//			// 商品中心这4个表attrdictgrpdesc   attrdictgrpattrrel  attrvalrel  attrdictgrp
//			json=this.xSalesCatalogCondDAO.selectByPrimaryKeyForJson(attrGroupId);
//			//测试用假数据	William.zhang	 20130524
//		//	json="[{'id':'001','name':'a001',order:1,'display':'1'},{'id':'003','name':'a003',order:'3','display':'1'},{'id':'002','name':'a002','display':'1'},{'id':'004','name':'a004',order:'4','display':'1'}]";
//		} catch (Exception e) {
//			logger.error("查询属性项出错,属性项ID:"+attrGroupId,e);
//		}
//		Map<String, AttrGroupJsonModel> attrGroups=this.parseJsonToAttrCatalogLinkMap(json);
//		CacheManage.put(attrGroupIdStr, attrGroups,CacheTypeEnum.CATAGROUP_ATTR);
//		return attrGroups;
//	}


	@Override
	public Map<String, AttrGroupJsonModel> getAttrGroupIdNameListWithInherit(Long categoryId) {
		if(null == categoryId)
			return Collections.emptyMap();
		
		//新的业务逻辑，使用缓存存储AttrGroupJsonModel		 William.zhang	20130705
		/*
	      * 新旧运营分类切换 2013-05-09 15：22
	      */
//		List<CatalogBo> planCatalogList = null;
//		 // 获取 运营分类
//		CatalogBo catalogBo = catalogBof.fetchCatalogBoTreeById(categoryId.intValue()); 
//		if(catalogBo != null){
//			List<CatalogBo> tempBoList = new ArrayList<CatalogBo>();
//			tempBoList.add(catalogBo);
//			planCatalogList = catalogBof.parsePlaneSelectCatalogBo(tempBoList);
//			tempBoList.clear();
//			tempBoList = null;
//			catalogBo = null;
//		}
//		if(null == planCatalogList || planCatalogList.size()<=1){
		return xiuAttrGroupCache.selectByPrimaryKeyForJson(categoryId);
//		}
//		List<Long> attrCatalogIds = new ArrayList<Long>();
//		for (CatalogBo cat : planCatalogList) {
//			attrCatalogIds.add((long)cat.getCatalogId());
//		}
//		return xiuAttrGroupCache.selectByPrimaryKeysForJson(attrCatalogIds);
		 
	}	
		
	@Autowired
	private XSalesCatalogCondDAO xSalesCatalogCondDAO;
	@Autowired
	private XiuAttrGroupCache xiuAttrGroupCache;
	@Autowired
    private CatalogBof catalogBof;
	@Autowired
    private  CatalogService catalogService;
	
}
