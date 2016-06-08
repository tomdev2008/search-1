package com.xiu.search.core.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.TermQuery;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.springframework.stereotype.Service;

import com.xiu.search.core.model.CatalogBusinessOptModel;
import com.xiu.search.core.service.GoodsSolrService;
import com.xiu.search.core.solr.enumeration.MktTypeEnum;
import com.xiu.search.core.solr.index.GoodsIndexFieldEnum;
import com.xiu.search.core.solr.index.SKUIndexFieldEnum;
import com.xiu.search.core.solr.model.GoodsSolrModel;
import com.xiu.search.core.solr.model.XiuSKUIndexModel;
import com.xiu.search.core.solr.params.SearchFatParams;
import com.xiu.search.core.solr.query.GoodsSolrConditionBuilder;
import com.xiu.search.dao.cache.CacheManage;
import com.xiu.search.dao.cache.CacheTypeEnum;
import com.xiu.search.dao.config.XiuSearchConfig;
import com.xiu.search.solrj.query.QueryFieldCondition;
import com.xiu.search.solrj.query.QueryFieldFacetCondition;
import com.xiu.search.solrj.query.QueryFieldSortCondition;
import com.xiu.search.solrj.query.SolrQueryBuilder;
import com.xiu.search.solrj.service.GenericSolrServiceImpl;
import com.xiu.search.solrj.service.SearchResult;

@Service("goodsSolrService")
public class GoodsSolrServiceImpl extends GenericSolrServiceImpl implements GoodsSolrService {

	private Logger logger = Logger.getLogger(getClass());
	
	/**
	 *  new String[]{"oclasspath"}
	 *  goodsSolrService.findSearchXiuSolr(params, true, attrFacetFields, null, businessModel, false);
	 */
	@Override
	public SearchResult<GoodsSolrModel> findSearchXiuSolr(SearchFatParams solrParams,boolean needFilterFacetFlag,String[] attrFacetFields,String[] extFacetFields,CatalogBusinessOptModel businessModel,boolean needMoreLinkSort){
		if(solrParams == null)
			return null;
		long begin=System.currentTimeMillis();
		
		SolrQueryBuilder builder = new SolrQueryBuilder();
		// 构建主参数
		List<QueryFieldCondition> mainConds = GoodsSolrConditionBuilder.createMainCondition(solrParams,businessModel);
		if(null == mainConds || mainConds.size()==0){
			logger.error("查询solr主参数为空！");
			return null;
		}
		for (QueryFieldCondition cond : mainConds) {
			builder.addMainQueryClauses(cond);
		}
		
		// 构建筛选区分组
		if(needFilterFacetFlag){
			boolean needBrandFacet = false;
			boolean needPriceFacet = false;
			// 品牌分组，品牌ID未选中
			// TODO 暂时未能实现品牌从缓存中读取，后期当选中品牌ID后，不再参与分组
//			if(null == solrParams.getBrandId()){
				needBrandFacet = true;
//			}
			// 价格区间分组，价格区间未输入或选中
			if(null == solrParams.getPriceRangeEnum() && null == solrParams.getStartPrice() && null == solrParams.getEndPrice()){
				needPriceFacet = true;
			}
			//构建分组条件
			List<QueryFieldFacetCondition> facetConds = GoodsSolrConditionBuilder.createFilterFacetCondition(needBrandFacet, needPriceFacet, attrFacetFields);
			if(null !=facetConds && facetConds.size()>0){
				for (QueryFieldFacetCondition cond : facetConds) {
					builder.addFacetQueryClauses(cond);
				}
			}
		}
		
		// 构建扩展分组
		if(null != extFacetFields && extFacetFields.length>0){
			List<String> ffList = null;
			boolean hasLabs = false;
			int len = extFacetFields.length;
			while (len-->0) {
				if(GoodsIndexFieldEnum.ITEM_LABELS.fieldName().equals(extFacetFields[len])){
					if(ffList == null)
						ffList = new ArrayList<String>(Arrays.asList(extFacetFields));
					hasLabs = true;
					ffList.remove(len);
				}
			}
			QueryFieldFacetCondition facetCond = GoodsSolrConditionBuilder.createExtFacetCondition(
					null == ffList ? extFacetFields : ffList.toArray(new String[ffList.size()]), 600);
			if(null != facetCond){
				builder.addFacetQueryClauses(facetCond);
			}
			if(hasLabs){
				facetCond = GoodsSolrConditionBuilder.createExtFacetCondition( new String[]{GoodsIndexFieldEnum.ITEM_LABELS.fieldName()}, 100);
				if(null != facetCond){
					builder.addFacetQueryClauses(facetCond);
				}
			}
		}
		
		// 排序
		if(null != solrParams.getSort()){
			List<QueryFieldSortCondition> sortConds = GoodsSolrConditionBuilder.createSortCondition(solrParams, businessModel,needMoreLinkSort);
			for (QueryFieldSortCondition sortCond : sortConds) {
				// 后面需要扩展addAll的方法
				builder.addSortQueryClauses(sortCond);
			}
		}
		// 翻页
		builder.setStart(solrParams.getPage().getFirstRow());
		builder.setRows(solrParams.getPage().getPageSize());
		
		/**
		 * 获取SolrQuery
		 */
		SolrQuery solrQuery = builder.solrQueryBuilder();
		SearchResult<GoodsSolrModel> ret = null;
		try {
			ret = super.findAll(GoodsSolrModel.class, solrQuery);
		} catch (SolrServerException e) {
			logger.error("查询索引失败\tSolrQuery:"+solrQuery.toString()+"\tclass:"+GoodsSolrModel.class,e);
		}
		
		long end=System.currentTimeMillis();
		logger.info("query solr for goods info execute findSearchXiuSolr method time:"+(end-begin));
		if(Boolean.valueOf(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_SWITCH))&&
				Integer.parseInt(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_OVERTIME))<(end-begin)){
			logger.warn("query solr for goods info execute findSearchXiuSolr method overtime:"+(end-begin));
		}
		return ret;
	}
	
	@Override
	public SearchResult<XiuSKUIndexModel> findSearchSKUSolr(List<String> idList) {
		
		long begin=System.currentTimeMillis();
		
		BooleanQuery query = new BooleanQuery();
        for (String id:idList) {
        	query.add(new TermQuery(new Term(SKUIndexFieldEnum.ITEM_ID.fieldName(), id)),Occur.SHOULD); 
        }

        SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery(query.toString());
		solrQuery.setStart(0);
		solrQuery.setRows(Integer.MAX_VALUE);
		
		//此处不按库存排序的原因：索引中是按skuSize字符串排序的
		//import org.apache.solr.client.solrj.SolrQuery.ORDER;
		//solrQuery.setSortField(SKUIndexFieldEnum.SKU_QTY.fieldName(), ORDER.desc);
		
		/*
		 * 添加以下两个排序 2014-07-01 14:20
		 */
		solrQuery.addSortField(SKUIndexFieldEnum.ITEM_ID.fieldName(), ORDER.asc);
		solrQuery.addSortField(SKUIndexFieldEnum.ORDER_BY.fieldName(), ORDER.asc);
		
		SearchResult<XiuSKUIndexModel> ret = null;
		try {
			ret = super.findAll(XiuSKUIndexModel.class, solrQuery);
		} catch (SolrServerException e) {
			logger.error("查询SKU索引失败\tSolrQuery:"+solrQuery.toString()+"\tclass:"+XiuSKUIndexModel.class,e);
		}
		
		long end=System.currentTimeMillis();
		logger.info("query solr for SKU info execute findSearchSKUSolr method time:"+(end-begin));
		return ret;
	}

	@Override
	public SearchResult<XiuSKUIndexModel> findSearchSKUSolrList(List<String> skuList) {
		
		long begin=System.currentTimeMillis();
		
		BooleanQuery query = new BooleanQuery();
        for (String sku:skuList) {
        	query.add(new TermQuery(new Term(SKUIndexFieldEnum.SKU_CODE.fieldName(), sku)),Occur.SHOULD); 
        }

        SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery(query.toString());
		solrQuery.setStart(0);
		solrQuery.setRows(Integer.MAX_VALUE);
		
		SearchResult<XiuSKUIndexModel> ret = null;
		try {
			ret = super.findAll(XiuSKUIndexModel.class, solrQuery);
		} catch (SolrServerException e) {
			logger.error("查询SKU索引失败\tSolrQuery:"+solrQuery.toString()+"\tclass:"+XiuSKUIndexModel.class,e);
		}
		
		long end=System.currentTimeMillis();
		logger.info("query solr for SKU info execute findSearchSKUSolr method time:"+(end-begin));
		return ret;
	}
	


	@Override
	public SearchResult<GoodsSolrModel> findSearchXiuSolr(SearchFatParams solrParams,boolean needFilterFacetFlag,String[] attrFacetFields,String[] extFacetFields,boolean needMoreLinkSort){
		return this.findSearchXiuSolr(solrParams, needFilterFacetFlag, attrFacetFields, extFacetFields, null,needMoreLinkSort);
	}
	
	@Override
	public SearchResult<GoodsSolrModel> findSearchXiuSolr(SearchFatParams solrParams,boolean needFilterFacetFlag,String[] attrFacetFields,String[] extFacetFields){
		return this.findSearchXiuSolr(solrParams, needFilterFacetFlag, attrFacetFields, extFacetFields, null,false);
	}
	@Override
	public SearchResult<GoodsSolrModel> findSearchXiuSolr(SearchFatParams solrParams,boolean needFilterFacetFlag,String[] attrFacetFields){
		return this.findSearchXiuSolr(solrParams, needFilterFacetFlag, attrFacetFields, null);
	}
	@Override
	public SearchResult<GoodsSolrModel> findSearchFacetFieldXiuSolr(SearchFatParams solrParams,String[] facetFields) {
		if(null == solrParams)
			return null;
		SearchFatParams fSolrParams = solrParams.clone();
		return this.findSearchXiuSolr(fSolrParams, false, null, facetFields);
	}

	@Override
	public int findGoodsCount(SearchFatParams solrParams,boolean withCache) {
		Integer count = Integer.valueOf(0);
		String key = null;
		if(withCache){
			key = solrParams.cacheKey();
			count = (Integer) CacheManage.get(key, CacheTypeEnum.MKT_TAB_ITEM_COUNT);
			if(null != count){
				return count;
			}
		}
		SearchResult<GoodsSolrModel> resultTemp = this.findSearchXiuSolr(solrParams, false, null, null);
		if(null != resultTemp){
			count = (int) resultTemp.getTotalHits();
			if(withCache){
				CacheManage.put(key, count, CacheTypeEnum.MKT_TAB_ITEM_COUNT);
			}
		} 
		return count;
	}
	@Override
	public Map<MktTypeEnum, Integer> findAllMktGoodsCount(SearchFatParams solrParams, boolean withCache) {
		Map<MktTypeEnum, Integer> ret = new HashMap<MktTypeEnum, Integer>();
		// 需要从索引查询的类型列表
		List<MktTypeEnum> needFindIndexMktTypeList = new ArrayList<MktTypeEnum>();
		Integer itemCount;
		SearchFatParams cloneParams = solrParams.clone();
		MktTypeEnum mktType;
		Map<MktTypeEnum, String> cacheKeyMap = null;
		if(withCache){
			cacheKeyMap = new HashMap<MktTypeEnum, String>();
			String key;
			MktTypeEnum[] mktTypeArr = MktTypeEnum.values();
			for (int i = 0,len = mktTypeArr.length; i < len; i++) {
				mktType = mktTypeArr[i];
				if(MktTypeEnum.ALL == mktType)
					continue;
				cloneParams.setMktType(mktType);
				key = cloneParams.cacheKey();
				cacheKeyMap.put(mktType, key);
				itemCount = (Integer) CacheManage.get(key, CacheTypeEnum.MKT_TAB_ITEM_COUNT);
				if(null == itemCount){
					needFindIndexMktTypeList.add(mktType);
				}else{
					ret.put(mktType, itemCount);
				}
			}
		}
		SearchResult<GoodsSolrModel> resultTemp = null;
		if(!withCache || needFindIndexMktTypeList.size()>1){
			// 通过facet查询mkt数量
			SolrQueryBuilder builder = new SolrQueryBuilder();
			// 构建主参数
			cloneParams.setMktType(null);
			cloneParams.setSearchTags(null);
			cloneParams.setProviderCode(null);
			List<QueryFieldCondition> mainConds = GoodsSolrConditionBuilder.createMainCondition(cloneParams);
			if(null != mainConds && mainConds.size() > 0){
				for (QueryFieldCondition cond : mainConds) {
					builder.addMainQueryClauses(cond);
				}
				QueryFieldFacetCondition facetCond = GoodsSolrConditionBuilder.createExtFacetCondition(new String[]{"mktType"}, 300);
				if(null != facetCond){
					builder.addFacetQueryClauses(facetCond);
					builder.setStart(0);
					builder.setRows(0);
					SolrQuery solrQuery = builder.solrQueryBuilder();
					try {
						resultTemp = super.findAll(GoodsSolrModel.class, solrQuery);
					} catch (Exception e) {
						// Ignore this exception
					}
					if(null != resultTemp 
							&& null != resultTemp.getFacetFields() 
							&& resultTemp.getFacetFields().size()>0){
						// 处理facet结果
						for (FacetField ff : resultTemp.getFacetFields()) {
							if(GoodsIndexFieldEnum.MKT_TYPE.fieldName().equals(ff.getName()) 
									&& null != ff.getValues()){
								for (Count _count : ff.getValues()) {
									if(null != _count 
											&& StringUtils.isNotBlank(_count.getName())
											&& StringUtils.isNumeric(_count.getName())){
										mktType = MktTypeEnum.valueof(Integer.valueOf(_count.getName()));
										if(null != mktType)
											ret.put(mktType,(int) _count.getCount());
									}
								}
								break;
							}
						}
					}
				}
			}
		}else if(needFindIndexMktTypeList.size() == 1){
			mktType = needFindIndexMktTypeList.get(0);
			cloneParams.setMktType(mktType);
			cloneParams.setSearchTags(null);
			cloneParams.setProviderCode(null);
			resultTemp = this.findSearchXiuSolr(cloneParams, false, null, null);
			if(null != resultTemp){
				ret.put(mktType, (int) resultTemp.getTotalHits());
			} else {
				ret.put(mktType, 0);
			}
		}
		if(withCache && needFindIndexMktTypeList.size()>0){
			for (MktTypeEnum mktTypeEnum : needFindIndexMktTypeList) {
				if(ret.containsKey(mktTypeEnum)){
					CacheManage.put(cacheKeyMap.get(mktTypeEnum), ret.get(mktTypeEnum), CacheTypeEnum.MKT_TAB_ITEM_COUNT);
				}else{
					ret.put(mktTypeEnum, Integer.valueOf(0));
					CacheManage.put(cacheKeyMap.get(mktTypeEnum), Integer.valueOf(0), CacheTypeEnum.MKT_TAB_ITEM_COUNT);
				}
			}
		}
		return ret;
	}
	
	public SearchResult<GoodsSolrModel> findCurrentCatalogOtherAttrCount(SearchFatParams params, List<Long> attrIds){
		if (params == null || params.getCatalogId()==null) {
			return null;
		}
		SolrQueryBuilder builder = new SolrQueryBuilder();
		SearchResult<GoodsSolrModel> results = null;
		try {
			// 构建主参数
			List<QueryFieldCondition> mainConds = GoodsSolrConditionBuilder.createMainCondition(params,null);
			if(null == mainConds || mainConds.size()==0){
				logger.error("查询solr主参数为空！");
				return null;
			}
			for (QueryFieldCondition cond : mainConds) {
				builder.addMainQueryClauses(cond);
			}
			
			SolrQuery solrQuery = builder.solrQueryBuilder();
			solrQuery.setFacet(true);
			solrQuery.setFacetMinCount(1);
			solrQuery.addFacetField("attrs");
			solrQuery.setRows(0);
			if (attrIds != null && !attrIds.isEmpty()) {
				for (int i = 0; i < attrIds.size(); i++) {
					if (attrIds.get(i) != null) {
						solrQuery.addFacetQuery("attrs:"+params.getCatalogId()+""+attrIds.get(i));
					}
				}
				solrQuery.setFacetLimit(1);
			}else {
				solrQuery.addFacetQuery("attrs:"+params.getCatalogId()+"*");
				solrQuery.setFacetLimit(Integer.MAX_VALUE);
			}
			results = super.findAll(GoodsSolrModel.class, solrQuery);
		} catch (SolrServerException e) {
			logger.error("获取分类筛选项异常----->",e);
			e.printStackTrace();
		}
		return results;
	}
	
	
}
