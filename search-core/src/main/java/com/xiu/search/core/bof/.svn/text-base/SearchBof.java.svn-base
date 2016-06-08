package com.xiu.search.core.bof;

import com.xiu.search.core.bo.SearchBo;
import com.xiu.search.core.solr.params.SearchFatParams;

/**
 * 搜索页面的BO接口
 * @author Leon
 *
 */
public interface SearchBof {

	/**
	 * 官网标签合并查询逻辑
	 * @param params
	 * @return
	 */
	public SearchBo findSearchXiuPageResult(SearchFatParams params);
	
	/**
	 * Ebay查询逻辑
	 * @param params
	 * @return
	 */
	public SearchBo findSearchEbayPageResult(SearchFatParams params);
	
	/**
	 * 标签搜索接口
	 * @param params
	 * @return
	 */
	public SearchBo findSearchTagsPageResult(SearchFatParams params);
	
	/**
	 * cms相似商品接口
	 * @param params
	 * @return
	 */
	public String findSimilarItemsResult(SearchFatParams params);
}
