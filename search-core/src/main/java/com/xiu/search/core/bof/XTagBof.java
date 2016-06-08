package com.xiu.search.core.bof;

import java.util.List;

import org.apache.solr.client.solrj.response.FacetField;

import com.xiu.search.core.bo.XTagBo;

public interface XTagBof {

	/**
	 * 从筛选结果解析出 搜索标签
	 * @param f
	 * @return
	 */
	public List<XTagBo> parseXTagsFromFacet(FacetField f,int maxTagCount);
	
	/**
	 * 从解析出来的标签列表中获得当前选中的 xTagsBo
	 * @param xTagBoList
	 * @param currentXTagId
	 * @param removeSelectedXTag
	 * @return
	 */
	public XTagBo getCurrentXTagsBoFromFacetXTagsBoList(List<XTagBo> xTagBoList, Integer currentXTagId, boolean removeSelectedXTag);
	
}
