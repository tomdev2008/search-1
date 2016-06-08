package com.xiu.search.core.util;

import java.util.List;

public class Page implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int pageSize = 10; // 每页显示记录数
	private int recordCount = 0; // 共多少条记录
	private int pageCount = 1; // 共几页
	private int pageNo = 1; // 当前第几页
	private int firstRow=0;//当前页起始条数
	private int endRow=0;
	
	@Deprecated
	private List dataList; // 数据

	public Page() {
	}

	public Page(int pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * 获得每页记录数
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 每页显示记录数
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 获取总记录数
	 * @return
	 */
	public int getRecordCount() {
		return recordCount;
	}

	/**
	 * 共多少条记录数
	 * @param recordCount
	 */
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	/** 
	 * 获取总页数
	 * 最小值为1
	 * @return
	 */
	public int getPageCount() {
		/** 根据总页数，获取总页数 */
		if (this.recordCount > 0) {
			if (recordCount % pageSize == 0)
				this.pageCount = recordCount / pageSize;
			else
				this.pageCount = recordCount / pageSize + 1;
		} else
			this.pageCount = 1;
		return this.pageCount;
	}

	/**
	 * 共几页
	 * @param pageCount
	 */
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	/**
	 * 获得当前页数<br>
	 * 注意：有可能当前页数大于总页数
	 * @return
	 */
	public int getPageNo() {
		if (pageNo <= 0)
			pageNo = 1;
		return pageNo;
	}

	/**
	 * 设置当前第几页
	 * @param pageNo
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	/**
	 * 当前页面第一行
	 * @return
	 */
	public int getFirstRow() {
		return (this.getPageNo()-1)*this.getPageSize();
	}

	/**
	 * 下一页第一行
	 * @return
	 */
	public int getEndRow() {
		return this.getPageNo()*this.getPageSize()+1;
	}
	@Deprecated
	public List getDataList() {
		return dataList;
	}
	@Deprecated
	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

}