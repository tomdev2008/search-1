package com.xiu.search.dao.model;

import java.io.Serializable;

/**
 * 
 * com.xiu.search.dao.model.XiuSeoKeywordModel.java

 * @Description: TODO  SEO 关键词

 * @author lvshuding   

 * @date 2013-10-28 下午2:47:40

 * @version V1.0
 */
public class XiuSeoKeywordModel implements Serializable {

	private static final long serialVersionUID = -1647552011097415995L;

	private int id;
	
	private String curKw;
	
	private Long bId;
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the curKw
	 */
	public String getCurKw() {
		return curKw;
	}

	/**
	 * @param curKw the curKw to set
	 */
	public void setCurKw(String curKw) {
		this.curKw = curKw;
	}

	/**
	 * @return the bId
	 */
	public Long getbId() {
		return bId;
	}

	/**
	 * @param bId the bId to set
	 */
	public void setbId(Long bId) {
		this.bId = bId;
	}
	
	
}
