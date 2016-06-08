package com.xiu.search.core.bo;

import org.apache.commons.lang.StringUtils;

import com.xiu.search.dao.model.XTag;

public class XTagBo extends XTag{

	public XTagBo(){
		super.setName(StringUtils.EMPTY);
	}
	
	/**
	 * 商品数量
	 */
	private int itemCount;

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
	
	/**
	 * 获取前台搜索用的
	 * 优先ID
	 * @return
	 */
	public String getSearchTags(){
		return null != super.getId() ? String.valueOf(super.getId()) : super.getName();
	}
	
	/**
	 * 获取前台展示用的
	 * 优先ID
	 * @return
	 */
	public String getDisplayTags(){
		return null != super.getName() ? super.getName() : String.valueOf(super.getId());
	}
	
}
