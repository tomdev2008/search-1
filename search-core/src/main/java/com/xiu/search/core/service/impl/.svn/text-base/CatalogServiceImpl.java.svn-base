package com.xiu.search.core.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.common.params.CommonParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.search.core.bo.CatalogBo;
import com.xiu.search.core.bo.ListBo;
import com.xiu.search.core.catalog.CatalogModel;
import com.xiu.search.core.catalog.XiuCatalogTreeCache;
import com.xiu.search.core.model.CatalogBusinessOptModel;
import com.xiu.search.core.service.CatalogService;
import com.xiu.search.core.service.GoodsSolrService;
import com.xiu.search.core.solr.enumeration.ItemShowTypeEnum;
import com.xiu.search.core.solr.enumeration.MktTypeEnum;
import com.xiu.search.core.solr.index.GoodsIndexFieldEnum;
import com.xiu.search.core.solr.index.OclassIndexFieldEnum;
import com.xiu.search.core.solr.model.GoodsSolrModel;
import com.xiu.search.core.solr.model.OclassSolrModel;
import com.xiu.search.core.solr.params.ListFatParams;
import com.xiu.search.dao.XDataCatalogDAO;
import com.xiu.search.dao.cache.CacheManage;
import com.xiu.search.dao.cache.CacheTypeEnum;
import com.xiu.search.dao.model.XDataCatalog;
import com.xiu.search.open.lucenesrc.queryParser.ParseException;
import com.xiu.search.open.utils.SolrExpressionFilter;
import com.xiu.search.solrj.service.GenericSolrServiceImpl;
import com.xiu.search.solrj.service.SearchResult;

/**
 * 运营分类业务处理类
 * 
 * @author wuyongqi
 */
@Service("catalogService")
public class CatalogServiceImpl extends GenericSolrServiceImpl implements CatalogService {

	private Logger LOGGER = Logger.getLogger(getClass());

	@Autowired
	private XDataCatalogDAO xDataCatalogDAO;

	/**
	 * 全部运营分类列表在缓存中的key
	 */
//	private String oclassModelListCacheKey = "AllOclassSolrModelListByIndex";
//
//	/**
//	 * 根据catalogId调用DAO获取运营分类树中的所有query
//	 * 
//	 * @param catalogId
//	 *            运营分类ID
//	 * @return Map<Long, StringBuffer> key：运营分类ID，value：经过转换的query
//	 * @throws ParseException
//	 */
//	private Map<Long, StringBuffer> selectCatalogQueryByCatalogId(Long catalogId) throws ParseException {
//
//		long starttime = System.currentTimeMillis();
//		List<XDataCatalog> catalogList = xDataCatalogDAO.selectCatalogQueryByCatalogId(catalogId); // 调用DAO获取运营分类列表
//		long dbtime = System.currentTimeMillis();
//
//		if (null == catalogList) {
//			return null;
//		}
//
//		Map<Long, StringBuffer> queryMap = new HashMap<Long, StringBuffer>();
//		XDataCatalog xDataCatalog = null;
//		String newquery = "";
//
//		// //// 通过正反两次遍历，生成每个运营分类最终需要的query：即运营分类的query包含所有子分类的query //////
//		try {
//
//			for (int i = 0; i < catalogList.size(); i++) {
//				xDataCatalog = catalogList.get(i);
//
//				if (StringUtils.isNotEmpty(xDataCatalog.getQuery())) {
//					newquery = SolrExpressionFilter.getInstance().filter(xDataCatalog.getQuery());
//				} else {
//					newquery = "";
//				}
//
//				newquery = (newquery.length() <= 0) ? "" : "(" + newquery + ")";
//
//				queryMap.put(xDataCatalog.getCatalogId(), new StringBuffer(newquery));
//			}
//		} catch (ParseException e) {
//			LOGGER.error("运营分类query转换出错, catalogId : " + catalogId + ", query : " + xDataCatalog.getQuery());
//			e.printStackTrace();
//			throw e;
//		}
//
//		StringBuffer curQueryBuf = null;
//		StringBuffer parentBuf = null;
//
//		for (int i = catalogList.size() - 1; i >= 0; i--) {
//			xDataCatalog = catalogList.get(i);
//
//			if (null != xDataCatalog.getParentCatalogId()) {
//
//				curQueryBuf = queryMap.get(xDataCatalog.getCatalogId());
//				if (curQueryBuf.length() > 0) {
//
//					parentBuf = queryMap.get(xDataCatalog.getParentCatalogId());
//					if (parentBuf.length() <= 0) {
//						parentBuf.append(curQueryBuf);
//					} else {
//						parentBuf.append(" OR ").append(curQueryBuf);
//					}
//				}
//			}
//		}
//
//		long endtime = System.currentTimeMillis();
//		if (endtime - starttime > 250) {
//			LOGGER.warn("从数据库查询运营分类条件耗时 : " + (dbtime - starttime) + " ms, 构造query耗时: " + (endtime - dbtime) + ", 总耗时 : " + (endtime - starttime) + " ms, catalogId : " + catalogId + ", queryMap.size : " + queryMap.size() + ". ");
//		}
//
//		return queryMap;
//	}
//
//	/**
//	 * 将query写入缓存
//	 * 
//	 * @param queryMap
//	 */
//	private void updateCatalogQueryCache(Map<Long, StringBuffer> queryMap) {
//
//		long time = System.currentTimeMillis();
//		if (null == queryMap || queryMap.size() <= 0) {
//			return;
//		}
//
//		for (Entry<Long, StringBuffer> entry : queryMap.entrySet()) {
//			CacheManage.put(String.valueOf(entry.getKey()), entry.getValue(), CacheTypeEnum.CATAGROUP_QUERY);
//		}
//
//		long end = (System.currentTimeMillis() - time);
//
//		if (end > 8) {
//			LOGGER.warn("将运营分类查询更新入缓存耗时 : " + end + " ms, queryMap.size : " + queryMap.size() + ". ");
//		}
//
//	}
//
//	/**
//	 * 将运营分类树写入缓存
//	 * 
//	 * @param queryMap
//	 */
//	private void updateOclassSolrModelListCache(List<OclassSolrModel> oclassList) {
//
//		long time = System.currentTimeMillis();
//		if (null == oclassList || oclassList.size() <= 0) {
//			return;
//		}
//
//		CacheManage.put(oclassModelListCacheKey, oclassList, CacheTypeEnum.CATAGROUP_OCLASSSOLRMODELLIST);
//
//		long end = (System.currentTimeMillis() - time);
//
//		if (end > 5) {
//			LOGGER.warn("将运营分类树写入缓存耗时 : " + end + " ms. ");
//		}
//
//	}
//
//	/**
//	 * 根据catalogId获取对应的query 通过缓存获取，如果没有则更新缓存
//	 * 
//	 * @param catalogId
//	 *            运营分类ID
//	 */
//	public String getCatalogQueryByCache(Long catalogId) {
//
//		if (null == catalogId || catalogId.intValue() <= 0) {
//			return "";
//		}
//
//		// 如果缓存中没有记录，则查询数据库，并更新入缓存
//		// if (!CacheManage.containsKey(String.valueOf(catalogId),
//		// CacheTypeEnum.CATAGROUP_QUERY)) {
//		// try {
//		// Map<Long, StringBuffer> queryMap =
//		// this.selectCatalogQueryByCatalogId(catalogId);
//		// this.updateCatalogQueryCache(queryMap);
//		// } catch (ParseException e) {
//		// LOGGER.error("运营分类query转换出错, catalogId : " + catalogId);
//		// e.printStackTrace();
//		// return "";
//		// }
//		// }
//
//		Object obj = CacheManage.get(String.valueOf(catalogId), CacheTypeEnum.CATAGROUP_QUERY);
//		if (null == obj) {
//			try {
//				Map<Long, StringBuffer> queryMap = this.selectCatalogQueryByCatalogId(catalogId);
//				if (null == queryMap) {
//					queryMap = new HashMap<Long, StringBuffer>();
//				}
//				if (!queryMap.containsKey(catalogId))
//					queryMap.put(catalogId, new StringBuffer(0));
//				this.updateCatalogQueryCache(queryMap);
//				obj = queryMap.get(catalogId).toString();
//			} catch (ParseException e) {
//				LOGGER.error("运营分类query转换出错, catalogId : " + catalogId);
//				e.printStackTrace();
//				return "";
//			}
//		}
//		if (null == obj) {
//			return "";
//		} else {
//			return obj.toString();
//		}
//	}
//
//
//	/**
//	 * 从oclass中获取所有运营分类
//	 * 
//	 * @return List<OclassSolrModel>
//	 */
//	private List<OclassSolrModel> getOclassModelListByIndex() {
//
//		long starttime = System.currentTimeMillis();
//
//		SolrQuery solrQuery = new SolrQuery();
//		solrQuery.add(CommonParams.Q, "*:*");
//		solrQuery.addField(OclassIndexFieldEnum.ID.getFieldName());
//		solrQuery.addField(OclassIndexFieldEnum.NAME.getFieldName());
//		solrQuery.addField(OclassIndexFieldEnum.PARENTID.getFieldName());
//		solrQuery.addField(OclassIndexFieldEnum.LEVEL.getFieldName());
//		solrQuery.addField(OclassIndexFieldEnum.ITEMCOUNT.getFieldName());
//		solrQuery.addField(OclassIndexFieldEnum.MKTTYPE.getFieldName());
//		solrQuery.addSortField(OclassIndexFieldEnum.LEVEL.getFieldName(), ORDER.asc);
//		solrQuery.addSortField(OclassIndexFieldEnum.SORTNUM.getFieldName(), ORDER.asc);
//		solrQuery.setStart(0);
//		solrQuery.setRows(100000);
//
//		SearchResult<OclassSolrModel> ret = null;
//		try {
//			ret = super.findAll(OclassSolrModel.class, solrQuery);
//		} catch (SolrServerException e) {
//			LOGGER.error("查询索引失败\tSolrQuery : " + solrQuery.toString() + "\tclass : " + OclassSolrModel.class, e);
//			return null;
//		}
//
//		long end = (System.currentTimeMillis() - starttime);
//
//		if (end > 50) {
//			LOGGER.warn("从索引查询运营分类树耗时 : " + end + " ms. ");
//		} else {
//			LOGGER.info("从索引查询运营分类树耗时 : " + end + " ms. ");
//		}
//
//		if (null != ret) {
//			return ret.getBeanList();
//		} else {
//			return null;
//		}
//	}
//
//	@Override
//	public List<CatalogBo> getCatalogBoByCache() {
//		List<CatalogModel> CatalogModel = XiuCatalogTreeCache.getInstance().getTree();
//		List<CatalogBo> list = this.getCatalogBoForList(CatalogModel);
//		return list;
//	}
//	
//	@Override
//	public List<CatalogBo> getCatalogBoByCache(ItemShowTypeEnum itemShowType) {
//		List<CatalogModel> CatalogModels = XiuCatalogTreeCache.getInstance().getTree(itemShowType);
//		List<CatalogBo> list = this.getCatalogBoForList(CatalogModels);
//		return list;
//	}
//
//	@Override
//	public CatalogBo getCatalogFromCacheByID(String catalogID) {
//		CatalogModel CatalogModel = XiuCatalogTreeCache.getInstance().getTreeNodeById(catalogID);
//		return null;
//	}
//	
//	@Override
//	public CatalogBo getCatalogFromCacheByID(String catalogID,Integer itemShowType) {
//		CatalogModel CatalogModel = XiuCatalogTreeCache.getInstance().getTreeNodeById(catalogID,ItemShowTypeEnum.valueof(itemShowType));
//		
//		return null;
//	}
	
//	/**
//	 *  取得可用的运营分类树	William.zhang	20130701
//	 * @param CatalogModels
//	 * @return
//	 */
//	public List<CatalogBo> getCatalogBoForList (List<CatalogModel> CatalogModels){
//		List<CatalogBo> list = new ArrayList<CatalogBo>();
//		if(CatalogModels != null && CatalogModels.size() > 0){
//			for(CatalogModel c : CatalogModels){
//				CatalogBo catalogBo = new CatalogBo();
//				list.add(catalogBo);
//				catalogBo.setCatalogModel(c);
//				if(c.getChildIdList() != null && c.getChildIdList().size() > 0){
//					//二级分类
//					List<CatalogBo> childCatalogTwo = new ArrayList<CatalogBo>();
//					catalogBo.setChildCatalog(childCatalogTwo);
//					for(Integer i : c.getChildIdList()){
//						CatalogBo catalogBoTwo = new CatalogBo();
//						childCatalogTwo.add(catalogBoTwo);
//						CatalogModel c1 = XiuCatalogTreeCache.getInstance().getTreeNodeById(Integer.toString(i));
//						catalogBoTwo.setCatalogModel(c1);
//						
//						if(c1.getChildIdList() != null && c1.getChildIdList().size() > 0){
//							//三级分类
//							List<CatalogBo> childCatalogThree = new ArrayList<CatalogBo>();
//							catalogBoTwo.setChildCatalog(childCatalogThree);
//							for(Integer j : c1.getChildIdList()){
//								CatalogBo catalogBoThree = new CatalogBo();
//								childCatalogThree.add(catalogBoThree);
//								CatalogModel c2 = XiuCatalogTreeCache.getInstance().getTreeNodeById(j+"");
//								catalogBoThree.setCatalogModel(c2);
//								
//							}
//						}
//						
//					}
//				}
//				
//			}
//		}
//		return list;
//	}
//
//	/**
//	 * 从缓存中获取所有运营分类
//	 * 
//	 * @return List<OclassSolrModel>
//	 */
//	@SuppressWarnings("unchecked")
//	public List<OclassSolrModel> getOclassModelListByCache() {
//
//		// if (!CacheManage.containsKey(oclassModelListCacheKey,
//		// CacheTypeEnum.CATAGROUP_OCLASSSOLRMODELLIST)) {
//		// List<OclassSolrModel> oclassList = this.getOclassModelListByIndex();
//		// this.updateOclassSolrModelListCache(oclassList);
//		// }
//		List<OclassSolrModel> list = (List<OclassSolrModel>) CacheManage.get(oclassModelListCacheKey, CacheTypeEnum.CATAGROUP_OCLASSSOLRMODELLIST);
//		if (null == list) {
//			list = this.getOclassModelListByIndex();
//			if (null == list)
//				list = Collections.EMPTY_LIST;
//			this.updateOclassSolrModelListCache(list);
//		}
//		return list;
//	}
//
//	public XDataCatalogDAO getxDataCatalogDAO() {
//		return xDataCatalogDAO;
//	}
//
//	public void setxDataCatalogDAO(XDataCatalogDAO xDataCatalogDAO) {
//		this.xDataCatalogDAO = xDataCatalogDAO;
//	}
//
//	public static void main(String[] args) throws ParseException {
//		// System.out.println(checkNumGtDigit(99999,5));
//		// System.out.println(SolrExpressionFilter.getInstance().filter("( ( parentCatgroup_id_search:(10001_14831) AND attr_415:羊皮 AND attr_415:仿皮（PU皮 皮革） AND attr_415:牛皮 AND attr_415:水洗皮（PU皮 皮革） AND attr_415:麂皮) )"));
//	}
//
//
//	/**
//	 * 取得当前可用的发货方式			 William.zhang
//	 */
//	public void getItemShowType(ListBo listBo, ListFatParams params, CatalogBusinessOptModel businessModel, GoodsSolrService goodsSolrService, String[] attrFacetFields) {
//		try {
//			List<Integer> itemShowTypeList = null;
//			// 首先从缓存中去查询,如果存在，则返回该条数据
//			String itemShowTypeJSON = (String) CacheManage.get(params.cacheKey()+"|ItemShowType", CacheTypeEnum.ITEM_SHOW_TYPE);
//			if (itemShowTypeJSON != null && !"".equals(itemShowTypeJSON)) {
//				listBo.setItemShowTypeJSON(itemShowTypeJSON);
//				return;
//			}
//			// 首先保存老的属性项选择
//			if (params.getItemShowType() != null) {
//				itemShowTypeList = new ArrayList<Integer>(params.getItemShowType().size());
//				for (Integer i : params.getItemShowType()) {
//					itemShowTypeList.add(i);
//				}
//			}
//			// 将属性项选择设置为空，不作为查询条件！
//			params.setItemShowType(null);
//			attrFacetFields = new String[] { GoodsIndexFieldEnum.ITEM_SHOW_TYPE.fieldName() };
//			// 开始查询分类项
//			SearchResult<GoodsSolrModel> result = goodsSolrService.findSearchXiuSolr(params, true, attrFacetFields, null, businessModel, false);
//			params.setItemShowType(itemShowTypeList);
//			List<Map<String, String>> json = new ArrayList<Map<String, String>>();
//			if (null != result.getFacetFields()) {
//				for (FacetField f : result.getFacetFields()) {
//					if (GoodsIndexFieldEnum.ITEM_SHOW_TYPE.fieldName().equals(f.getName())) {
//						if(f.getValues() != null && f.getValues().size() > 0){
//							for (Count count : f.getValues()) {
//								Map<String, String> map = new HashMap<String, String>();
//								map.put("itemName", count.getName());
//								map.put("itemCount", count.getCount() + "");
//								json.add(map);
//							}
//						}
//					}
//				}
//			}
//			String item = JSONArray.fromObject(json).toString();
//			CacheManage.put(params.cacheKey()+"|ItemShowType", item, CacheTypeEnum.ITEM_SHOW_TYPE);
//			listBo.setItemShowTypeJSON(item);
//		} catch (Exception e) {
//			LOGGER.error("取得当前可用的发货方式失败.", e);
//		}
//	}
}
