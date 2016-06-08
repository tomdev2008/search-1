package com.xiu.search.core.bo;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索页面展示商品信息
 * @author Leon
 *
 */
public class GoodsItemBo {

	/**
	 * ID
	 */
	private String id;
	/**
	 * 7位，不足则高位补零
	 */
	private String urlId;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 中文品牌名
	 */
	private String brandNameCn;
	/**
	 * 英文品牌名
	 */
	private String brandNameEn;
	/**
	 * 高亮名称
	 */
	private String nameHighlight;
	/**
	 * 220图片url
	 */
	private String imgUrl220;
	
	/**
	 * 220图片url
	 */
	private String imgEbayUrl220;
	
	/**
	 * 100图片url
	 */
	private String imgUrl100;
	/**
	 * 售罄
	 */
	private boolean soldOut;
	/**
	 * 立即购买
	 */
	private boolean buyNow;
	/**
	 * 到货通知
	 */
	private boolean arrivalNotice;
	/**
	 * 走秀价
	 */
	private double xiuPrice;
	/**
	 * 市场价
	 */
	private double mktPrice;
	/**
	 * 最终价
	 */
	private double finalPrice;
	
	/**
	 * 展示价格
	 */
	private double showPrice;
	
	/**
	 * 商品List页跳转url
	 */
	private String itemUrl;
	
	/**
	 * 供应商标识 1528:ebay ferragamo:菲拉格慕
	 */
	private String providerCode;
	
	private double disCount;//折扣
	
	/**
	 * 展示的尺码信息
	 */
	private List<SkuBo> skuList=new ArrayList<SkuBo>();
	
	/**
	 * zouxiuma
	 */
	private String xiuSn;
	
	
	public String getXiuSn() {
		return xiuSn;
	}
	public void setXiuSn(String xiuSn) {
		this.xiuSn = xiuSn;
	}
	public String getImgEbayUrl220() {
		return imgEbayUrl220;
	}
	public void setImgEbayUrl220(String imgEbayUrl220) {
		this.imgEbayUrl220 = imgEbayUrl220;
	}
	public double getDisCount() {
		return disCount;
	}
	public void setDisCount(double disCount) {
		this.disCount = disCount;
	}
	public double getShowPrice() {
		return this.showPrice;
	}
	public void setShowPrice(double showPrice){
		this.showPrice = showPrice;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
		if(id == null){
			this.urlId = null;
		}else{
			StringBuffer sb = new StringBuffer();
			for (int i = 0,len = 7-id.length(); i < len; i++) {
				sb.append("0");
			}
			this.urlId = sb.append(id).toString();
		}
	}
	public String getUrlId(){
		return this.urlId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImgUrl220() {
		return imgUrl220;
	}
	public void setImgUrl220(String imgUrl220) {
		this.imgUrl220 = imgUrl220;
	}
	public String getImgUrl100() {
		return imgUrl100;
	}
	public void setImgUrl100(String imgUrl100) {
		this.imgUrl100 = imgUrl100;
	}
	public boolean isSoldOut() {
		return soldOut;
	}
	public void setSoldOut(boolean soldOut) {
		this.soldOut = soldOut;
	}
	public boolean isBuyNow() {
		return buyNow;
	}
	public void setBuyNow(boolean buyNow) {
		this.buyNow = buyNow;
	}
	public boolean isArrivalNotice() {
		return arrivalNotice;
	}
	public void setArrivalNotice(boolean arrivalNotice) {
		this.arrivalNotice = arrivalNotice;
	}
	public double getXiuPrice() {
		return xiuPrice;
	}
	public void setXiuPrice(double xiuPrice) {
		this.xiuPrice = xiuPrice;
	}
	public double getMktPrice() {
		return mktPrice;
	}
	public void setMktPrice(double mktPrice) {
		this.mktPrice = mktPrice;
	}
	public double getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(double finalPrice) {
		this.finalPrice = finalPrice;
	}
	public String getNameHighlight() {
		return nameHighlight;
	}
	public void setNameHighlight(String nameHighlight) {
		this.nameHighlight = nameHighlight;
	}
	public String getProviderCode() {
		return providerCode;
	}
	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}
	public String getItemUrl() {
		return itemUrl;
	}
	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}
	/**
	 * @return the skuList
	 */
	public List<SkuBo> getSkuList() {
		return skuList;
	}
	/**
	 * @param skuList the skuList to set
	 */
	public void setSkuList(List<SkuBo> skuList) {
		this.skuList = skuList;
	}
	public String getBrandNameCn() {
		return brandNameCn;
	}
	public void setBrandNameCn(String brandNameCn) {
		this.brandNameCn = brandNameCn;
	}
	public String getBrandNameEn() {
		return brandNameEn;
	}
	public void setBrandNameEn(String brandNameEn) {
		this.brandNameEn = brandNameEn;
	}
	
	
	
}
