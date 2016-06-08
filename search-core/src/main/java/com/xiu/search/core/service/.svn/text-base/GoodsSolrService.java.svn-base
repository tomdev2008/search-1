package com.xiu.search.core.service;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;

import com.xiu.search.core.model.CatalogBusinessOptModel;
import com.xiu.search.core.solr.enumeration.MktTypeEnum;
import com.xiu.search.core.solr.model.GoodsSolrModel;
import com.xiu.search.core.solr.model.XiuSKUIndexModel;
import com.xiu.search.core.solr.params.SearchFatParams;
import com.xiu.search.solrj.service.GenericSolrService;
import com.xiu.search.solrj.service.SearchResult;

/**
 * 搜索页面查询接口
 * @author Leon
 *
 */
public interface GoodsSolrService extends GenericSolrService{

	public SearchResult<GoodsSolrModel> findSearchXiuSolr(SearchFatParams solrParams,boolean needFilterFacetFlag,String[] attrFacetFields,String[] extFacetFields,CatalogBusinessOptModel businessModel,boolean needMoreLinkSort);
	
	/**
	 * 搜索页面带筛选区查询搜索结果方法<br>
	 * 同步进行与搜索结构匹配的分组查询(由业务逻辑制约)
	 * @param solrParams 搜索参数对象
	 * @param needFilterFacetFlag [筛选区]是否进行筛选区的分组筛选
	 * @param attrFacetFields [筛选区]属性项分组条件
	 * @param extFacetFields 扩展分组条件
	 * @param needMoreLinkSort MoreLink商品排前 
	 * @return
	 * @throws SolrServerException
	 */
	public SearchResult<GoodsSolrModel> findSearchXiuSolr(SearchFatParams solrParams,boolean needFilterFacetFlag,String[] attrFacetFields,String[] extFacetFields,boolean needMoreLinkSort);
	
	/**
	 * 搜索页面带筛选区查询搜索结果方法<br>
	 * 同步进行与搜索结构匹配的分组查询(由业务逻辑制约)
	 * @param solrParams 搜索参数对象
	 * @param needFilterFacetFlag [筛选区]是否进行筛选区的分组筛选
	 * @param attrFacetFields [筛选区]属性项分组条件
	 * @param extFacetFields 扩展分组条件
	 * @return
	 * @throws SolrServerException
	 */
	public SearchResult<GoodsSolrModel> findSearchXiuSolr(SearchFatParams solrParams,boolean needFilterFacetFlag,String[] attrFacetFields,String[] extFacetFields);
	
	/**
	 * 搜索页面带筛选区查询结果方法
	 * @param solrParams 搜索参数对象
	 * @param needFilterFacetFlag [筛选区]是否进行筛选区的分组筛选
	 * @param attrFacetFields [筛选区]属性项分组条件
	 * @return
	 */
	public SearchResult<GoodsSolrModel> findSearchXiuSolr(SearchFatParams solrParams,boolean needFilterFacetFlag,String[] attrFacetFields);
	
	/**
	 * 异步进行分组结果的查询方法<br>
	 * 不返回商品结果，只返回需要的分组结果<br>
	 * 注意：limit参数请根据实际需要设定
	 * @param solrParams 搜索参数对象
	 * @param facetFields 分组字段
	 * @return
	 * @throws SolrServerException 
	 */
	public SearchResult<GoodsSolrModel> findSearchFacetFieldXiuSolr(SearchFatParams solrParams,String[] facetFields);
	
	/**
	 * 获得商品数量
	 * @param solrParams
	 * @param withCache 是否从缓存中查询
	 * @return
	 */
	public int findGoodsCount(SearchFatParams solrParams,boolean withCache);
	
	/**
	 * 获得所有mkttype的商品数量，通过facet方式获取
	 * @param solrParams
	 * @param withCache
	 * @return
	 */
	public Map<MktTypeEnum,Integer> findAllMktGoodsCount(SearchFatParams solrParams,boolean withCache);
	
	/**
	 * 根据商品列表ID获得所有SKU数据
	 * @param idList 商品列表ID
	 * @return
	 */
	public SearchResult<XiuSKUIndexModel> findSearchSKUSolr(List<String> idList);

	public SearchResult<XiuSKUIndexModel> findSearchSKUSolrList(List<String> skuList);
	
	public SearchResult<GoodsSolrModel> findCurrentCatalogOtherAttrCount(SearchFatParams params, List<Long> attrId);
}
