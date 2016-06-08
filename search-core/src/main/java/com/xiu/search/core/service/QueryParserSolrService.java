package com.xiu.search.core.service;

import java.util.List;

import com.xiu.search.core.solr.model.SuggestSolrModel;
import com.xiu.search.solrj.service.GenericSolrService;

/**
 * 搜索QP接口
 * @author Leon
 *
 */
public interface QueryParserSolrService extends GenericSolrService {

	/**
	 * 获取相关搜索词列表
	 * @param keyword
	 * @param maxRows
	 * @return
	 */
	@Deprecated
	public List<String> getRelatedSearchTerms(String keyword,int maxRows);
	
	/**
	 * 获取错误纠正词
	 * @param keyword
	 * @return
	 */
	@Deprecated
	public String getErrorCorrectionTerms(String keyword);
	
	/**
	 * 自动提示功能
	 * @param keyword
	 * @param maxRows
	 * @return
	 */
	@Deprecated
	public List<SuggestSolrModel> getSuggestTerms(String keyword,int maxRows,String[] columns);
	
}
