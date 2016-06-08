package com.xiu.search.core.bof.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.search.core.bo.MktItemInfoBo;
import com.xiu.search.core.bof.MktItemInfoBof;
import com.xiu.search.core.service.CatalogService;
import com.xiu.search.core.service.GoodsSolrService;
import com.xiu.search.core.solr.model.GoodsSolrModel;
import com.xiu.search.core.solr.params.MktItemInfoFatParams;
import com.xiu.search.dao.cache.CacheManage;
import com.xiu.search.dao.cache.CacheTypeEnum;
import com.xiu.search.solrj.service.SearchResult;

@Service("mktItemInfoBof")
public class MktItemInfoBofImpl implements MktItemInfoBof{

//	public MktItemInfoBo findItemInfo(MktItemInfoFatParams params) {
//		MktItemInfoBo bo = new MktItemInfoBo();
//		params.getPage().setPageSize(0);
//		if(null != params.getCatalogId()){
//			String catalogQueryStr = catalogService.getCatalogQueryByCache(params.getCatalogId().longValue());
//			if(StringUtils.isBlank(catalogQueryStr)){
//				return bo;
//			}
//			params.setCatalogQueryStr(catalogQueryStr);
//		}
//		bo.setItemCount(goodsSolrService.findGoodsCount(params, true));
////		String key = params.cacheKey();
////		Integer count = (Integer) CacheManage.get(key, CacheTypeEnum.MKT_TAB_ITEM_COUNT);
////		if(null != count){
////			bo.setItemCount(count);
////		}else{
////			SearchResult<GoodsSolrModel> result = goodsSolrService.findSearchXiuSolr(params, false, null, null);
////			if(result != null)
////				bo.setItemCount((int)result.getTotalHits());
////		}
//		return bo;
//	}
	
	@Autowired
	private GoodsSolrService goodsSolrService;
	@Autowired
	private CatalogService catalogService;
	
}
