package com.xiu.search.core.service;

import java.util.List;
import java.util.Map;

import com.xiu.search.core.bo.SkuAjaxBo;

/**
 * 
 * com.xiu.search.core.service.SkuSolrService.java

 * @Description: TODO SKU操作索引相关

 * @author lvshuding   

 * @date 2014-3-6 下午5:39:18

 * @version V1.0
 */
public interface SkuSolrService {

	/**
	 * 根据商品ID列表，返回对应在页面展示的尺码列表
	 * 
	 * @param idList  	商品ID列表
	 * 
	 * @param showCount 每个商品对应尺码展示的个数
	 * 
	 * @return	Map<String, Map<String,Integer>> 
	 */
	//public Map<String, List<Map<String,Object>>> searchSkuSizeByItemIDs(List<String> idList,int showCount);
	
	public Map<String, List<SkuAjaxBo>> searchSkuSizeByItemIDs(List<String> idList,int showCount);

	public Map<String, Integer> getInventoryBySkuList(List<String> sku);
	
	
}
