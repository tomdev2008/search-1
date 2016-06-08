package com.xiu.search.core.bof;

import com.xiu.search.core.bo.BrandBo;
import com.xiu.search.core.solr.params.BrandFatParams;

/**
 * 品牌页面的BO接口
 * @author Lipsion
 *
 */
public interface BrandBof {

	/**
	 * 官网标签合并查询逻辑
	 * @param params
	 * @return
	 */
	public BrandBo findBrandXiuPageResult(BrandFatParams params);
	
	/**
	 * Ebay品牌页面的查询逻辑
	 * @param params
	 * @return
	 */
	public BrandBo findBrandEbayPageResult(BrandFatParams params);
	
	/**
	 * 清除品牌信息的缓存
	 * @param brandId 品牌id
	 * @param flag 标记   1:全部品牌的缓存    0:某个品牌的缓存
	 * @return
	 */
	public boolean cleanCacheOfBrandInfo(Long brandId,int flag);
	
}
