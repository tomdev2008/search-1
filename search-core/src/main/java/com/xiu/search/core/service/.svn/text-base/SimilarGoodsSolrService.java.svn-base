package com.xiu.search.core.service;

import java.util.List;
import java.util.Map;

/**
 * 相似商品查询(cms详细页查询用)
 * @author Lipsion
 *
 */
public interface SimilarGoodsSolrService {

	/**
	 * 根据走秀码查询相似商品
	 * @param partNumber
	 * @return
	 */
	public List<Map<String,String>> findSimilarGoodsByPartNumber(String partNumber);
	
	/**
     * 拨测功能使用，返回总记录数
     * @return
     */
    long selectCountForId();
}
