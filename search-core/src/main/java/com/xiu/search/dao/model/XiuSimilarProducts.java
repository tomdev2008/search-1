package com.xiu.search.dao.model;

import java.util.Date;

public class XiuSimilarProducts{
	
	private String similarItemGroupId;
	
	private String sameItemGroupId;
	
	private String xiuCode;
	
	private Date insertTime;
	
	private Long score;

	public String getSimilarItemGroupId() {
		return similarItemGroupId;
	}

	public void setSimilarItemGroupId(String similarItemGroupId) {
		this.similarItemGroupId = similarItemGroupId;
	}

	public String getSameItemGroupId() {
		return sameItemGroupId;
	}

	public void setSameItemGroupId(String sameItemGroupId) {
		this.sameItemGroupId = sameItemGroupId;
	}

	public String getXiuCode() {
		return xiuCode;
	}

	public void setXiuCode(String xiuCode) {
		this.xiuCode = xiuCode;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}
}