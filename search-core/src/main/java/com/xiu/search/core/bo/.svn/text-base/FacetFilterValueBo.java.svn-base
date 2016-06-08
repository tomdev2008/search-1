package com.xiu.search.core.bo;

import java.io.Serializable;

import com.xiu.search.core.solr.enumeration.FacetPriceRangeQueryEnum;

/**
 * 筛选属性值
 * @author Leon
 *
 */
public class FacetFilterValueBo implements Cloneable,Serializable{

	/**
	 * 属性值ID或品牌ID或价格筛选序号
	 */
	private Long id;
	/**
	 * 属性值名称或品牌名称或价格的展示名称
	 */
	private String name;
	/**
	 * 属性值名称或品牌名称或价格的拼音名称
	 */
	private String pyName;
	
	/**
	 * 品牌名称拼音首字母（用于排序使用，addTime:2014-10-06 10:01）
	 */
	private String firstLetter;
	
	/**
	 * 属性值包含商品数量
	 */
	private long itemCount;
	/**
	 * 属性值页面的查询参数，例如filter=12523|12312|2132;64734;274553;56332
	 */
	private String filter;
	/**
	 * 商品价格范围
	 */
	private FacetPriceRangeQueryEnum priceRange;
	
	/**
	 * 前台展示顺序
	 */
	private int showOrder;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getItemCount() {
		return itemCount;
	}
	public void setItemCount(long itemCount) {
		this.itemCount = itemCount;
	}
	public FacetPriceRangeQueryEnum getPriceRange() {
		return priceRange;
	}
	public void setPriceRange(FacetPriceRangeQueryEnum priceRange) {
		this.priceRange = priceRange;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	
	
	public String getPyName() {
		return pyName;
	}
	public void setPyName(String pyName) {
		this.pyName = pyName;
	}
	/**
	 * 复杂数据请深度clone
	 */
	@Override
	public FacetFilterValueBo clone(){
		try {
			FacetFilterValueBo ret = (FacetFilterValueBo)super.clone();
			return ret;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public int getShowOrder() {
		return showOrder;
	}
	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}
	/**
	 * @return the firstLetter
	 */
	public String getFirstLetter() {
		return firstLetter;
	}
	/**
	 * @param firstLetter the firstLetter to set
	 */
	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter;
	}
	
	
	
}
