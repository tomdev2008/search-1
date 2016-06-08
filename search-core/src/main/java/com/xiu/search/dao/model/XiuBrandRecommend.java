package com.xiu.search.dao.model;

import java.io.Serializable;
import java.util.Date;

public class XiuBrandRecommend implements Serializable {
	
	private Integer recommendId;
	
    private Integer brandId;

    private Integer weight;

    private Boolean isDisplay;
    
    private Integer bizType;
    
    private Integer mktType;

    private String bizId;
    
    private Date gmtCreate;

    private Date gmtModify;

    private static final long serialVersionUID = 1L;

    
	public Integer getRecommendId() {
		return recommendId;
	}

	public void setRecommendId(Integer recommendId) {
		this.recommendId = recommendId;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Boolean getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(Boolean isDisplay) {
		this.isDisplay = isDisplay;
	}

	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	/**
	 * @return the mktType
	 */
	public Integer getMktType() {
		return mktType;
	}

	/**
	 * @param mktType the mktType to set
	 */
	public void setMktType(Integer mktType) {
		this.mktType = mktType;
	}

}