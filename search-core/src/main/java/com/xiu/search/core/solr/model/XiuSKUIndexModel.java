package com.xiu.search.core.solr.model;

import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;

/**
 * 
 * com.xiu.search.core.solr.model.XiuSKUIndexModel.java

 * @Description: TODO Xiu SKU索引Model

 * @author lvshuding   

 * @date 2014-3-6 下午12:23:14

 * @version V1.0
 */
public class XiuSKUIndexModel{

	@Field("itemId")
	private String itemId;//产品ID

	@Field("itemCode")
	private String itemCode;//走秀码
	
	@Field("skuCode")
	private String skuCode;//SKU

	@Field("skuColor")
	private String skuColor;//标准颜色
	
	@Field("skuSize")
	private String skuSize;//标准尺码
	
	@Field("skuQty")
	private Integer qty;//库存量
	
	@Field("orderVal")
	private Integer orderBy;//排序值
	
	@Field("skusUpdateTime")
	private Date updateTime;//修改时间

	@Field("indexVersion")
	private Date indexVersion;//索引最后更新时间

	/**
	 * @return the itemId
	 */
	public String getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * @param itemCode the itemCode to set
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * @return the skuCode
	 */
	public String getSkuCode() {
		return skuCode;
	}

	/**
	 * @param skuCode the skuCode to set
	 */
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	/**
	 * @return the skuColor
	 */
	public String getSkuColor() {
		return skuColor;
	}

	/**
	 * @param skuColor the skuColor to set
	 */
	public void setSkuColor(String skuColor) {
		this.skuColor = skuColor;
	}

	/**
	 * @return the skuSize
	 */
	public String getSkuSize() {
		return skuSize;
	}

	/**
	 * @param skuSize the skuSize to set
	 */
	public void setSkuSize(String skuSize) {
		this.skuSize = skuSize;
	}


	/**
	 * @return the orderBy
	 */
	public Integer getOrderBy() {
		return orderBy;
	}

	/**
	 * @param orderBy the orderBy to set
	 */
	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the qty
	 */
	public Integer getQty() {
		return qty;
	}

	/**
	 * @param qty the qty to set
	 */
	public void setQty(Integer qty) {
		this.qty = qty;
	}

	/**
	 * @return the indexVersion
	 */
	public Date getIndexVersion() {
		return indexVersion;
	}

	/**
	 * @param indexVersion the indexVersion to set
	 */
	public void setIndexVersion(Date indexVersion) {
		this.indexVersion = indexVersion;
	}
	
}