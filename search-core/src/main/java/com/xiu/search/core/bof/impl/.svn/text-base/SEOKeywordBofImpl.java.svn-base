package com.xiu.search.core.bof.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.search.core.bo.GoodsItemBo;
import com.xiu.search.core.bo.SeoKeywordBo;
import com.xiu.search.core.bof.SEOKeywordBof;
import com.xiu.search.core.service.GoodsSolrService;
import com.xiu.search.core.solr.model.GoodsSolrModel;
import com.xiu.search.core.solr.params.SearchFatParams;
import com.xiu.search.core.util.CommonUtil;
import com.xiu.search.core.util.Page;
import com.xiu.search.dao.XiuSEOKeywordDAO;
import com.xiu.search.dao.model.XiuSeoKeywordModel;
import com.xiu.search.solrj.service.SearchResult;

/**
 * com.xiu.search.core.bof.impl.SEOKeywordBofImpl.java

 * @Description: TODO SEO关键字 页面的BO接口实现 

 * @author lvshuding   

 * @date 2013-10-28 下午3:46:39

 * @version V1.0
 */

@Service("seoKeywordBof")
public class SEOKeywordBofImpl implements SEOKeywordBof {

	@Autowired
	private XiuSEOKeywordDAO xiuSEOKeywordDAO;

	@Autowired
	private GoodsSolrService goodsSolrService;
	
	
	@Override
	public SeoKeywordBo findSEOKwById(int id,int pageNumber,int pageSize) {
		
		XiuSeoKeywordModel kw = xiuSEOKeywordDAO.selectById(id);
		if(kw==null){
			return null;
		}
		SeoKeywordBo bo = new SeoKeywordBo();
		List<XiuSeoKeywordModel> kwList=new ArrayList<XiuSeoKeywordModel>();
		kwList.add(kw);
		bo.setKwList(kwList);
		
		Page page=new Page();
		page.setPageNo(pageNumber);
		page.setPageSize(pageSize);
		bo.setPage(page);
		
		SearchFatParams params=new SearchFatParams(pageSize);
		params.setBrandId(kw.getbId().intValue());
		params.getPage().setPageNo(pageNumber);
		
		SearchResult<GoodsSolrModel> result = goodsSolrService.findSearchXiuSolr(params, false, null, null, null, false);
		if(result != null){
			List<GoodsItemBo> goodsItemBoList = CommonUtil.transformXiuItemBo(null, result.getBeanList());
			bo.setGoodsItemList(goodsItemBoList);
			bo.getPage().setRecordCount((int)result.getTotalHits());
		}
		
		return bo;
	}

	@Override
	public SeoKeywordBo findSEOKwPageResult(int pageNumber, int pageSize) {
		SeoKeywordBo bo=new SeoKeywordBo();
		Page page=new Page();
		page.setPageNo(pageNumber);
		page.setPageSize(pageSize);
		bo.setPage(page);
		
		List<XiuSeoKeywordModel> kwList = xiuSEOKeywordDAO.selectPageList(page.getFirstRow(), page.getEndRow());
		if(kwList==null){
			return null;
		}
		bo.setKwList(kwList);
		page.setRecordCount(xiuSEOKeywordDAO.selectTotCount());
		return bo;
	}
	
}
