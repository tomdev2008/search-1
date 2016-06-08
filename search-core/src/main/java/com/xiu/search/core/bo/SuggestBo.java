package com.xiu.search.core.bo;

public class SuggestBo {

	// 展示名称
	private String display;
	// 匹配的regex
	private String matchValue;
	// 运营分类名称
	private String catalogName;
	// 运营分类ID
	private String catalogId;
	// 运营分类下商品数量
	private String oclassCount;
	// 提示类型
	private int type;
	// 商品数量
	private int count;
	
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public String getMatchValue() {
		return matchValue;
	}
	public void setMatchValue(String matchValue) {
		this.matchValue = matchValue;
	}
	public String getCatalogName() {
		return catalogName;
	}
	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public String getOclassCount() {
		return oclassCount;
	}
	public void setOclassCount(String oclassCount) {
		this.oclassCount = oclassCount;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
