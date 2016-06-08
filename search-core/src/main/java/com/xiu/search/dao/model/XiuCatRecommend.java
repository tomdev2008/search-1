package com.xiu.search.dao.model;

import java.io.Serializable;
import java.util.Date;

public class XiuCatRecommend implements Serializable {
	
	private Integer recommendId;
	
    private Integer catId;

    private Integer weight;

    private Integer bizType;

    private String bizId;
    
    private Date gmtCreate;

    private Date gmtModify;
    
    private Integer mktType;

    private static final long serialVersionUID = 1L;

    
	public Integer getRecommendId() {
		return recommendId;
	}

	public void setRecommendId(Integer recommendId) {
		this.recommendId = recommendId;
	}

	public Integer getCatId() {
		return catId;
	}

	public void setCatId(Integer catId) {
		this.catId = catId;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
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

	public Integer getMktType() {
		return mktType;
	}

	public void setMktType(Integer mktType) {
		this.mktType = mktType;
	}

}