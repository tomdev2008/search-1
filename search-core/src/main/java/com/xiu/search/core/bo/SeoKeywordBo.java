package com.xiu.search.core.bo;

import java.util.List;

import com.xiu.search.core.util.Page;
import com.xiu.search.dao.model.XiuSeoKeywordModel;

public class SeoKeywordBo{
    
	/**
	 * 页码信息
	 */
	private Page page;
	
	/**
	 * 关键词
	 */
	private List<XiuSeoKeywordModel> kwList;
	
	/**
	 * 搜索商品结果
	 */
	private List<GoodsItemBo> goodsItemList;

	/**
	 * @return the page
	 */
	public Page getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(Page page) {
		this.page = page;
	}

	/**
	 * @return the kwList
	 */
	public List<XiuSeoKeywordModel> getKwList() {
		return kwList;
	}

	/**
	 * @param kwList the kwList to set
	 */
	public void setKwList(List<XiuSeoKeywordModel> kwList) {
		this.kwList = kwList;
	}

	/**
	 * @return the goodsItemList
	 */
	public List<GoodsItemBo> getGoodsItemList() {
		return goodsItemList;
	}

	/**
	 * @param goodsItemList the goodsItemList to set
	 */
	public void setGoodsItemList(List<GoodsItemBo> goodsItemList) {
		this.goodsItemList = goodsItemList;
	}
	
}
