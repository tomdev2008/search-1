package com.xiu.search.core.solr.params;


/**
 * 列表页面的业务条件参数
 * @author 吴勇其
 *
 */
public class ListFatParams extends SearchFatParams{

	private static final long serialVersionUID = 3683405648860588604L;
	
	public ListFatParams() {
		super(30);
	}
	
	public ListFatParams(int pageSize){
		super(pageSize);
	}
	
}