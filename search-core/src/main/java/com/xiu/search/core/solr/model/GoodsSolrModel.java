package com.xiu.search.core.solr.model;

import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;

public class GoodsSolrModel {

	@Field("itemId")
	private String itemId;
	@Field("itemName")
	private String itemName;
	@Field("brandName")
	private String brandName;
	@Field("brandNameEn")
	private String brandNameEn;
	@Field("priceFinal")
	private Double priceFinal;
	@Field("priceXiu")
	private Double priceXiu;
	@Field("priceMkt")
	private double priceMkt;
	@Field("discount")
	private double discount;
	@Field("stateOffshow")
	private int offshow;
	@Field("partNumber")
	private String partNumber;
	@Field("stateOnsale")
	private int onsale;
	@Field("itemNamePre")
	private String itemNamePre;
	@Field("itemNamePost")
	private String itemNamePost;
	@Field("imgUrl")
	private String imgUrl;
	@Field("imgVersion")
	private String imgVersion;
	@Field("salesVolume")
	private int salesVolume;
	@Field("score")
	private double score;
	@Field("brandId")
	private String brandId;
	@Field("onsaletime")
	private Date onsaleTime;
	@Field("oclassPath")
	private String[] oclassPath;
	@Field("oclassPathEBay")
	private String[] oclassPathEBay;
	@Field("providerCode")
	private String providerCode;
	@Field("itemColor")
	private String[] itemColor;
	@Field("itemSize")
	private String[] itemSize;
	
	public String getBrandNameEn() {
		return brandNameEn;
	}
	public void setBrandNameEn(String brandNameEn) {
		this.brandNameEn = brandNameEn;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	
	public Double getPriceFinal() {
		return priceFinal;
	}
	public void setPriceFinal(Double priceFinal) {
		this.priceFinal = priceFinal;
	}
	//	public double getPrice() {
//		return price;
//	}
//	public void setPrice(double price) {
//		this.price = price;
//	}
	public Double getPriceXiu() {
		return priceXiu;
	}
	public void setPriceXiu(Double priceXiu) {
		this.priceXiu = priceXiu;
	}
	public double getPriceMkt() {
		return priceMkt;
	}
	public void setPriceMkt(double priceMkt) {
		this.priceMkt = priceMkt;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public int getOffshow() {
		return offshow;
	}
	public void setOffshow(int offshow) {
		this.offshow = offshow;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public int getOnsale() {
		return onsale;
	}
	public void setOnsale(int onsale) {
		this.onsale = onsale;
	}
	public String getItemNamePre() {
		return itemNamePre;
	}
	public void setItemNamePre(String itemNamePre) {
		this.itemNamePre = itemNamePre;
	}
	public String getItemNamePost() {
		return itemNamePost;
	}
	public void setItemNamePost(String itemNamePost) {
		this.itemNamePost = itemNamePost;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getImgVersion() {
		return imgVersion;
	}
	public void setImgVersion(String imgVersion) {
		this.imgVersion = imgVersion;
	}
	public int getSalesVolume() {
		return salesVolume;
	}
	public void setSalesVolume(int salesVolume) {
		this.salesVolume = salesVolume;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public Date getOnsaleTime() {
		return onsaleTime;
	}
	public void setOnsaleTime(Date onsaleTime) {
		this.onsaleTime = onsaleTime;
	}
	public String[] getOclassPath() {
		return oclassPath;
	}
	public void setOclassPath(String[] oclassPath) {
		this.oclassPath = oclassPath;
	}
	public String[] getOclassPathEBay() {
		return oclassPathEBay;
	}
	public void setOclassPathEBay(String[] oclassPathEBay) {
		this.oclassPathEBay = oclassPathEBay;
	}
	public String getProviderCode() {
		return providerCode;
	}
	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}
	public String[] getItemColor() {
		return itemColor;
	}
	public void setItemColor(String[] itemColor) {
		this.itemColor = itemColor;
	}
	public String[] getItemSize() {
		return itemSize;
	}
	public void setItemSize(String[] itemSize) {
		this.itemSize = itemSize;
	}
}
