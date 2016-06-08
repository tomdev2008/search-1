package com.xiu.search.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.search.core.service.GoodsSolrService;
import com.xiu.search.core.service.MemCachedService;
import com.xiu.search.core.service.SimilarGoodsSolrService;
import com.xiu.search.core.solr.enumeration.SearchSortOrderEnum;
import com.xiu.search.core.solr.index.GoodsIndexFieldEnum;
import com.xiu.search.core.solr.model.GoodsSolrModel;
import com.xiu.search.core.solr.params.SearchFatParams;
import com.xiu.search.dao.XiuItemInfoDAO;
import com.xiu.search.dao.cache.CacheManage;
import com.xiu.search.dao.cache.CacheTypeEnum;
import com.xiu.search.solrj.service.SearchResult;

@Service("similarGoodsSolrService")
public class SimilarGoodsSolrServiceImpl implements SimilarGoodsSolrService {

	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	private GoodsSolrService goodsSolrService;
	@Autowired
	private XiuItemInfoDAO xiuItemInfoDAO;
	@Autowired
	private MemCachedService memCachedService;

	private final String MEMCACHE_KEY = "search";

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, String>> findSimilarGoodsByPartNumber(
			String partNumber) {

		Object obj = CacheManage.get(partNumber, CacheTypeEnum.SIMILAR_ITEMS);
		if (null != obj) {
			List<Map<String, String>> goodsList = (List<Map<String, String>>) obj;
			List<String> keys = new ArrayList<String>();
			for (Map<String, String> item : goodsList) {
				String partNum = item.get(GoodsIndexFieldEnum.PART_NUMBER
						.fieldName());
				keys.add(MEMCACHE_KEY + partNum);
			}
			// 查询memcache商品价格
			queryMemcacheForItemPrice(goodsList, keys);
			return goodsList;
		}

		// TODO 查询相似商品组ID select * from xiu_similar_products
		String similarGroupId = null;
		try {
			similarGroupId = xiuItemInfoDAO
					.selectByPartNumberForSimilarGroupId(partNumber);
			if (null == similarGroupId)
				return null;
		} catch (Exception e) {
			logger.error("query similar goods for similar group id fail", e);
			return null;
		}

		SearchFatParams solrParams = new SearchFatParams();
		solrParams.setSimilarGroupId(similarGroupId);
		solrParams.setBuyableFlag(true);
		solrParams.setSort(SearchSortOrderEnum.SIMILAR_DESC);

		SearchResult<GoodsSolrModel> goodsResult = this.goodsSolrService
				.findSearchXiuSolr(solrParams, false, null);
		if (null != goodsResult && goodsResult.getBeanList().size() > 0) {
			List<Map<String, String>> goodsList = new ArrayList<Map<String, String>>();

			// 缓存key,从memcache取价格
			List<String> keys = new ArrayList<String>();
			for (GoodsSolrModel goods : goodsResult.getBeanList()) {
				Map<String, String> goodsMap = new HashMap<String, String>();
				goodsMap.put(GoodsIndexFieldEnum.ITEM_ID.fieldName(),
						goods.getItemId());
				goodsMap.put(GoodsIndexFieldEnum.ITEM_NAME.fieldName(),
						goods.getItemName());
				goodsMap.put(GoodsIndexFieldEnum.PART_NUMBER.fieldName(),
						goods.getPartNumber());
				goodsMap.put(GoodsIndexFieldEnum.IMG_URL.fieldName(),
						goods.getImgUrl());
				goodsMap.put(GoodsIndexFieldEnum.PRICE_FINAL.fieldName(),
						goods.getPriceFinal() + "");
				String itemColor = "";
				if (null != goods.getItemColor()) {
					Set<String> colorSet = new HashSet<String>();
					// 去重
					for (String color : goods.getItemColor()) {
						colorSet.add(color);
					}
					for (String color : colorSet) {
						itemColor += itemColor.length() > 0 ? color + "|"
								: color;
					}
				}
				String itemSize = "";
				if (null != goods.getItemSize()) {
					Set<String> sizeSet = new TreeSet<String>();
					// 去重加排序
					for (String size : goods.getItemSize()) {
						sizeSet.add(size);
					}
					for (String size : sizeSet) {
						itemSize += itemSize.length() > 0 ? size + "|" : size;
					}
				}
				goodsMap.put(GoodsIndexFieldEnum.ITEM_COLOR.fieldName(),
						itemColor);
				goodsMap.put(GoodsIndexFieldEnum.ITEM_SIZE.fieldName(),
						itemSize);

				keys.add(MEMCACHE_KEY + goods.getPartNumber());
				goodsList.add(goodsMap);
			}
			// 查询memcache商品价格
			queryMemcacheForItemPrice(goodsList, keys);
			//是否存在此走秀码
			boolean flag=false;
			for (String key : keys) {
				if(key.equals(MEMCACHE_KEY+partNumber)){
					flag=true;
					break;
				}
			}
			//如果不存在添加剩余的商品
			if(!flag){
				CacheManage.put(partNumber, goodsList,CacheTypeEnum.SIMILAR_ITEMS);
			}

			// 克隆放入缓存
			for (int i = 0; i < goodsList.size(); i++) {
				ArrayList<Map<String, String>> similarGoodsList = cloneGoodsList(goodsList);
				String partNum = similarGoodsList.get(i).get(
						GoodsIndexFieldEnum.PART_NUMBER.fieldName());
				similarGoodsList.remove(i);
				CacheManage.put(partNum, similarGoodsList,
						CacheTypeEnum.SIMILAR_ITEMS);
			}

			obj = CacheManage.get(partNumber, CacheTypeEnum.SIMILAR_ITEMS);
			if (null != obj)
				return (List<Map<String, String>>) obj;
		}
		return null;
	}

	private void queryMemcacheForItemPrice(List<Map<String, String>> goodsList,
			List<String> keys) {
		// 查询memcache缓存的价格
		try {
			Map<String, Object> itemsMap = memCachedService.getMulti(keys);
			if (null != itemsMap && itemsMap.size() > 0) {
				for (Map<String, String> itemMap : goodsList) {
					String partNum = itemMap
							.get(GoodsIndexFieldEnum.PART_NUMBER.fieldName());
					Object price = itemsMap.get(MEMCACHE_KEY + partNum);
					if (null == price || StringUtils.isEmpty(price.toString())) {
						continue;
					}

					BigDecimal priceDecimal = new BigDecimal(price.toString())
							.divide(new BigDecimal(100));
					itemMap.put(GoodsIndexFieldEnum.PRICE_FINAL.fieldName(),
							priceDecimal.toString());
				}
			}
		} catch (Exception e) {
			logger.error("query memcache similar goods for goods price fail", e);
			e.printStackTrace();
		}
	}

	private ArrayList<Map<String, String>> cloneGoodsList(
			List<Map<String, String>> goodsList) {
		ArrayList<Map<String, String>> goodsListClone = new ArrayList<Map<String, String>>();
		for (Map<String, String> goodsMap : goodsList) {
			try {
				goodsListClone.add(goodsMap);
			} catch (Exception e) {
				logger.error("query similar goods for clone goods list fail", e);
				e.printStackTrace();
			}
		}
		return goodsListClone;
	}

	@Override
	public long selectCountForId() {
		try{
			return xiuItemInfoDAO.selectCountForId();
		}catch (Exception e) {
			logger.error("operated selectCountForId() fail:", e);
			e.printStackTrace();
			return -1l;
		}
	}
	
}
