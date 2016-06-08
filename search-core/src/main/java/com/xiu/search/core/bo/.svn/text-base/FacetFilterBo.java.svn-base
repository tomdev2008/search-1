package com.xiu.search.core.bo;

import java.util.ArrayList;
import java.util.List;

/**
 * 筛选区BO
 * @author Leon
 *
 */
public class FacetFilterBo {

	public enum FacetTypeEnum{
		/**
		 * 品牌
		 */
		BRAND,
		/**
		 * 价格
		 */
		PRICE,
		/**
		 * 属性
		 */
		ATTR
	}
	
	/**
	 * 当前筛选的类别，品牌，价格，属性
	 */
	private FacetTypeEnum facetType;
	/**
	 * 维度ID，价格和商品不存在ID
	 */
	private long facetId;
	/**
	 * 维度索引字段名称
	 */
	private String facetFieldName;
	/**
	 * 用于展示的名称
	 */
	private String facetDisplay;
	/**
	 * 仅当选中属性值有效。取消当前选中属性项的过滤值<br>
	 * 例如当前属性值为101，选中filter=100;101;102<br>
	 * 则当前值为  100;102
	 */
	private String cancelFilter;
	/**
	 * 包含的属性值
	 */
	private List<FacetFilterValueBo> facetValueBos;
	
	private int showOrder;
	
	/**
	 * 用于已经选择的条件 展示用名称,只展示三个，多出使用省略号,用于支持多选	William.zhang	20130507
	 */
	private String facetValueBosNames;
	
	/**
	 * 用于已经选择的条件 展示用名称，展示全名,用于支持多选	William.zhang	20130507
	 */
	private String facetValueBosNamesForTitle;
	
	/**
	 * 添加当前属性项下面的属性值<br>
	 * 品牌，价格，属性项通用
	 * @param facetValueBo
	 */
	public void addFacetValueBo(FacetFilterValueBo facetValueBo){
		if(null == facetValueBos)
			facetValueBos = new ArrayList<FacetFilterValueBo>();
		facetValueBos.add(facetValueBo);
	}
	
	public FacetTypeEnum getFacetType() {
		return facetType;
	}
	public void setFacetType(FacetTypeEnum facetType) {
		this.facetType = facetType;
	}
	public long getFacetId() {
		return facetId;
	}
	public void setFacetId(long facetId) {
		this.facetId = facetId;
	}
	public String getFacetFieldName() {
		return facetFieldName;
	}
	public void setFacetFieldName(String facetName) {
		this.facetFieldName = facetName;
	}
	public List<FacetFilterValueBo> getFacetValueBos() {
		return facetValueBos;
	}
	public void setFacetValueBos(List<FacetFilterValueBo> facetValueBos) {
		this.facetValueBos = facetValueBos;
	}
	
	public String getFacetDisplay() {
		return facetDisplay;
	}
	public void setFacetDisplay(String facetDisplay) {
		this.facetDisplay = facetDisplay;
	}

	public String getCancelFilter() {
		return cancelFilter;
	}

	public void setCancelFilter(String cancelFilter) {
		this.cancelFilter = cancelFilter;
	}

	public String getFacetValueBosNames() {
		return facetValueBosNames;
	}

	public void setFacetValueBosNames(String facetValueBosNames) {
		this.facetValueBosNames = facetValueBosNames;
	}

	public String getFacetValueBosNamesForTitle() {
		return facetValueBosNamesForTitle;
	}

	public void setFacetValueBosNamesForTitle(String facetValueBosNamesForTitle) {
		this.facetValueBosNamesForTitle = facetValueBosNamesForTitle;
	}

	public int getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}

}
