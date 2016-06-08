package com.xiu.search.core.bof;

import com.xiu.search.core.bo.CatalogBo;
import com.xiu.search.core.bo.ListBo;
import com.xiu.search.core.solr.enumeration.MktTypeEnum;
import com.xiu.search.core.solr.params.ListFatParams;
import com.xiu.search.core.solr.params.SearchFatParams;

/**
 * 列表页业务处理接口
 * @author wuyongqi
 *
 */
public interface ListBof {
    
    /**
	 * 官网标签合并查询逻辑
	 * @param params
	 * @return
	 */
	public ListBo findListXiuPageResult(ListFatParams params);
	
    /**
     * Ebay查询逻辑
     * @param params 相关参数封装
     * @return ListBo
     */
    public ListBo findListEbayPageResult(ListFatParams params);
    
    /**
     * 查询当前运营分类
     * @param selectedId
     * @param fromType
     * @return
     */
    public CatalogBo getCatalogBoList(SearchFatParams params,MktTypeEnum fromType);
    
    /**
     * 获取选中的整棵完整的树 包含Display=1，2的
     * @param params
     * @param fromType
     * @return
     */
    public CatalogBo getCatalogBoList2(SearchFatParams params, MktTypeEnum fromType);
}
