package com.xiu.search.core.solr.params;

import com.xiu.search.core.util.Page;




/**
 * 品牌页面的业务条件参数
 * @author Lipsion
 *
 */
public class BrandFatParams extends SearchFatParams{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3683405648860588604L;
	
	private Page page;
	
	public BrandFatParams(int pageSize){
		super(pageSize);
		this.page = new Page(pageSize);
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
}