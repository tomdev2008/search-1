package com.xiu.search.core.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.TermQuery;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.search.core.service.GoodsSolrService;
import com.xiu.search.core.service.XTagService;
import com.xiu.search.core.solr.index.GoodsIndexFieldEnum;
import com.xiu.search.core.solr.model.GoodsSolrModel;
import com.xiu.search.dao.XTagDAO;
import com.xiu.search.dao.cache.CacheManage;
import com.xiu.search.dao.cache.CacheTypeEnum;
import com.xiu.search.dao.model.XTag;
import com.xiu.search.solrj.service.SearchResult;

@Service("xTagService")
public class XTagServiceImpl implements XTagService {

	private final static XTag NULL_XTAGS = new XTag();
	
	@Override
	public Map<Integer, Integer> findXTagItemCountByIdsFromIndex(List<Integer> xTagIds) {
		if(null == xTagIds || xTagIds.size()==0)
			return Collections.emptyMap();
		Integer count;
		Map<Integer, Integer> ret = new HashMap<Integer, Integer>();
		List<Integer> emptyCountTagIds = new ArrayList<Integer>();
		for (Integer id : xTagIds) {
			count = (Integer) CacheManage.get(id.toString(), CacheTypeEnum.X_TAGS_ITEM_COUNT);
			if(count == null){
				emptyCountTagIds.add(id);
			}else{
				ret.put(id, count);
			}
		}
		Map<Integer, Integer> itemCountMap = this.findXTagsBoWithCountByTagsIdsFromIndex(emptyCountTagIds);
		for (Integer id : emptyCountTagIds) {
			if(itemCountMap.containsKey(id)){
				count = itemCountMap.get(id);
			}else{
				count = 0;
			}
			CacheManage.put(id.toString(), count, CacheTypeEnum.X_TAGS_ITEM_COUNT);
			ret.put(id, count);
		}
		return ret;
	}
	
	@Override
	public Map<Integer, XTag> findXTagMapByIds(List<Integer> xTagIds) {
		if(xTagIds == null || xTagIds.size()==0)
			return Collections.emptyMap();
		Map<Integer, XTag> ret = new HashMap<Integer, XTag>();
		XTag xt;
		List<Integer> emptyList = new ArrayList<Integer>();
		Integer id;
		int len = xTagIds.size();
		for (int i = 0; i < len; i++) {
			if((id = xTagIds.get(i)) == null)
				continue;
			xt = (XTag)CacheManage.get(id.toString(), CacheTypeEnum.X_TAGS_INFO);
			if(null == xt){
				emptyList.add(id);
			}else if(xt.getId() != null){
				ret.put(xt.getId(), xt);
			}
		}
		if((len = emptyList.size())==1){
			id = emptyList.get(0);
			xt = xTagDAO.selectById(id);
			if(xt != null){
				CacheManage.put(id.toString(), xt, CacheTypeEnum.X_TAGS_INFO);
				CacheManage.put(xt.getName(), xt, CacheTypeEnum.X_TAGS_INFO);
			}else{
				CacheManage.put(id.toString(), NULL_XTAGS, CacheTypeEnum.X_TAGS_INFO);
			}
		}else if(len > 1){
			List<XTag> xts = xTagDAO.selectByIds(emptyList);
			Map<Integer,XTag> empMap = new HashMap<Integer, XTag>(len);
			if(null != xts){
				for (XTag x : xts) {
					empMap.put(x.getId(), x);
				}
			}
			for (Integer o : emptyList) {
				if(empMap.containsKey(o)){
					xt = empMap.get(o);
					CacheManage.put(o.toString(), xt, CacheTypeEnum.X_TAGS_INFO);
					CacheManage.put(xt.getName(), xt, CacheTypeEnum.X_TAGS_INFO);
					ret.put(xt.getId(), xt);
				}else{
					CacheManage.put(o.toString(), NULL_XTAGS, CacheTypeEnum.X_TAGS_INFO);
				}
			}
		}
		return ret;
	}

	@Override
	public XTag findXTagMapByName(String xTagName) {
		if(StringUtils.isBlank(xTagName))
			return null;
		XTag ret = (XTag)CacheManage.get(xTagName, CacheTypeEnum.X_TAGS_INFO);
		if(ret != null)
			return ret;
		ret = xTagDAO.selectByName(xTagName);
		if(ret != null){
			CacheManage.put(ret.getId().toString(), ret, CacheTypeEnum.X_TAGS_INFO);
			CacheManage.put(ret.getName(), ret, CacheTypeEnum.X_TAGS_INFO);
		}else{
			CacheManage.put(xTagName, NULL_XTAGS, CacheTypeEnum.X_TAGS_INFO);
		}
		return ret;
	}

	@Autowired
	private XTagDAO xTagDAO;

	@Override
	public XTag findXTagMapById(Integer xTagId) {
		if(null == xTagId)
			return null;
		XTag ret = (XTag)CacheManage.get(xTagId.toString(), CacheTypeEnum.X_TAGS_INFO);
		if(ret != null)
			return ret.clone();
		ret = xTagDAO.selectById(xTagId);
		if(ret != null){
			CacheManage.put(ret.getId().toString(), ret, CacheTypeEnum.X_TAGS_INFO);
			CacheManage.put(ret.getName(), ret, CacheTypeEnum.X_TAGS_INFO);
		}else{
			CacheManage.put(xTagId.toString(), NULL_XTAGS, CacheTypeEnum.X_TAGS_INFO);
		}
		return NULL_XTAGS.clone();
	}
	
	private Map<Integer,Integer> findXTagsBoWithCountByTagsIdsFromIndex(List<Integer> tagsIdList) {
		if(CollectionUtils.isEmpty(tagsIdList))
			return Collections.emptyMap();
		SolrQuery solrQuery = new SolrQuery();
		BooleanQuery bq = new BooleanQuery();
		for (Integer id : tagsIdList) {
			bq.add(new TermQuery(new Term(GoodsIndexFieldEnum.ITEM_LABELS.fieldName(),id.toString())),Occur.SHOULD);
			solrQuery.addFacetQuery(GoodsIndexFieldEnum.ITEM_LABELS.fieldName()+":"+id);
		}
		solrQuery.setQuery(bq.toString());
		solrQuery.setFacet(true);
		solrQuery.setFacetMinCount(1);
		solrQuery.setRows(0);
		SearchResult<GoodsSolrModel> result = null;
		Map<Integer,Integer> ret = new HashMap<Integer, Integer>(tagsIdList.size());
		try {
			result = goodsSolrService.findAll(GoodsSolrModel.class, solrQuery);
		} catch (SolrServerException e) {
		}
		Map<String,Integer> facetQuery;
		if(result == null
				|| (facetQuery = result.getFacetQuery()) == null 
				|| facetQuery.size()==0){
			return Collections.emptyMap();
		}
		String key;
		for (Integer id : tagsIdList) {
			key = GoodsIndexFieldEnum.ITEM_LABELS.fieldName()+":"+id;
			if(facetQuery.containsKey(key)){
				ret.put(id, facetQuery.get(key));
			}
		}
		return ret;
	}
	
	@Autowired
	private GoodsSolrService goodsSolrService;

}
