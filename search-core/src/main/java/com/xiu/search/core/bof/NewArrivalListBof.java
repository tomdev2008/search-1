package com.xiu.search.core.bof;

import com.xiu.search.core.bo.ListBo;
import com.xiu.search.core.solr.params.ListFatParams;

/**
 * 列表页业务处理接口
 * @author wuyongqi
 *
 */
public interface NewArrivalListBof {
    
    /**
	 * 获取新品列表信息
	 * @param params
	 * @return
	 */
	public ListBo findNewArrivalListXiuPageResult(ListFatParams params);
}
