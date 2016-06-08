package com.xiu.search.core.model;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 运营分类下，运营业务设置类
 * @author Leon
 *
 */
public class CatalogBusinessOptModel {

	/**
	 * 运营分类ID
	 */
	private Integer catalogId;
	
	private Integer brandId;
	
	/**
	 * 运营分类名称
	 */
	private String catalogName;
	
	/**
	 * 优先显示的商品partNumber，按显示次序排
	 */
	private List<String> goodsItemTopShow;  
	
	/**
	 * 优先显示的品牌ID，按品牌权重次序排
	 * k - brandid
	 * v - weight
	 */
	private LinkedHashMap<Integer, Integer> brandGoodsItemTopShow;
	
	/**
	 * 优先显示的分类ID，按分类权重次序排
	 * k - catalogId
	 * v - weight
	 */
	private LinkedHashMap<Integer, Integer> brandCatalogIdTopShow;
	
	public CatalogBusinessOptModel(){}

	public CatalogBusinessOptModel(Integer catalogId){
		this.catalogId=catalogId;
	}
	
	public Integer getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(Integer catalogId) {
		this.catalogId = catalogId;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public List<String> getGoodsItemTopShow() {
		return goodsItemTopShow;
	}

	public void setGoodsItemTopShow(List<String> goodsItemTopShow) {
		this.goodsItemTopShow = goodsItemTopShow;
	}

	public LinkedHashMap<Integer, Integer> getBrandGoodsItemTopShow() {
		return brandGoodsItemTopShow;
	}

	public void setBrandGoodsItemTopShow(
			LinkedHashMap<Integer, Integer> brandGoodsItemTopShow) {
		this.brandGoodsItemTopShow = brandGoodsItemTopShow;
	}
	
	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public LinkedHashMap<Integer, Integer> getBrandCatalogIdTopShow() {
		return brandCatalogIdTopShow;
	}

	public void setBrandCatalogIdTopShow(
			LinkedHashMap<Integer, Integer> brandCatalogIdTopShow) {
		this.brandCatalogIdTopShow = brandCatalogIdTopShow;
	}
	
	//public transient static final CatalogBusinessOptModel EMPTY_OBJECT = new CatalogBusinessOptModel();
	
}
