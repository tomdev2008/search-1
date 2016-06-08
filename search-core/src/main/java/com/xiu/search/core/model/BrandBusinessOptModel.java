package com.xiu.search.core.model;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * com.xiu.search.core.model.BrandBusinessOptModel.java

 * @Description: TODO 品牌下，运营业务设置类

 * @author lvshuding   

 * @date 2013-6-18 上午9:39:12

 * @version V1.0
 */
public class BrandBusinessOptModel {

	/**
	 * 品牌ID
	 */
	private Integer brandId;
	
	/**
	 * 品牌名称
	 */
	private String brandName;
	
	/**
	 * 优先显示的商品ID，按商品权重次序排
	 * k - goodsId
	 * v - weight
	 */
	private List<String> brandGoodsItemTopShow;
	
	/**
	 * 优先显示的分类ID，按分类权重次序排
	 * k - catalogId
	 * v - weight
	 */
	private LinkedHashMap<Integer, Integer> brandCatalogIdTopShow;
	
	public BrandBusinessOptModel(){}
	
	public BrandBusinessOptModel(Integer brandId){
		this.brandId=brandId;
	}
	

	
	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public List<String> getBrandGoodsItemTopShow() {
		return brandGoodsItemTopShow;
	}

	public void setBrandGoodsItemTopShow(List<String> brandGoodsItemTopShow) {
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

	//public transient static final BrandBusinessOptModel EMPTY_OBJECT = new BrandBusinessOptModel();
	
}
