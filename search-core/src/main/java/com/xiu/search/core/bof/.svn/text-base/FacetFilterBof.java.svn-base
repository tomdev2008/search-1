package com.xiu.search.core.bof;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.response.FacetField;

import com.xiu.search.core.bo.FacetFilterBo;
import com.xiu.search.core.bo.FacetFilterBo.FacetTypeEnum;
import com.xiu.search.core.model.AttrGroupJsonModel;

/**
 * 筛选区BO接口
 * @author Leon
 *
 */
public interface FacetFilterBof {

	/**
	 * 解析品牌筛选结果<br>
	 * 索引分组查询结果的解析
	 * @param facetField
	 * @return
	 */
	public FacetFilterBo parseBrandFacetFilter(FacetField facetField);
	
	/**
	 * 解析价格查询QUERY结果<br>
	 * 索引分组查询结果的解析
	 * @param priceRange
	 * @return
	 */
	public FacetFilterBo parsePriceRangeFacetFilter(Map<String, Integer> priceRange);
	
	/**
	 * 解析属性筛选结果<br>
	 * 索引分组查询结果的解析
	 * @see FacetFilterBof#parseAttrFacetFilter(FacetField, String[], Map)
	 * @param facetField
	 * @param oriAttrValIds 原始的查询参数数组
	 * @return
	 */
//	public FacetFilterBo parseAttrFacetFilter(FacetField facetField,String[] oriAttrValIds);
	
	/**
	 * 解析属性筛选结果<br>
	 * 索引分组查询结果的解析<br>
	 * 将名称通过attrIndexFieldNameMap进行初始化
	 * @param facetField
	 * @param oriAttrValIds 原始的查询参数数组
	 * @param attrIndexFieldNameMap 属性项的map，key为索引字段名，value为name
	 * @return
	 */
//	public FacetFilterBo parseAttrFacetFilter(FacetField facetField,String[] oriAttrValIds,Map<String,AttrGroupJsonModel> attrIndexFieldNameMap);
	
	
	/**
	 * 解析属性筛选结果	支持多选		William.zhang	20130508<br>
	 * 索引分组查询结果的解析<br>
	 * 将名称通过attrIndexFieldNameMap进行初始化
	 * @param facetField
	 * @param oriAttrValIds 原始的查询参数数组
	 * @param attrIndexFieldNameMap 属性项的map，key为索引字段名，value为name
	 * @return
	 */
	public FacetFilterBo parseAttrFacetFilter(FacetField facetField,List<List<String>> attrValIdList,Map<String,AttrGroupJsonModel> attrIndexFieldNameMap);
	
	
	/**
	 * 获得选中的自定义价格区间
	 * @param startPrice
	 * @param endPrice
	 * @return
	 */
	public FacetFilterBo formatSelectCustomPriceRangeFacetFilter(Double startPrice,Double endPrice);
	
	/**
	 * 获得当前分组中是否含有以选中的筛选项<br>
	 * 包含品牌，属性项<br>
	 * 此过程会生成属性项的删除filter条件
	 * 注意：后期属性值与品牌将从缓存中读取，避免不必要的facet损耗
	 * @see FacetFilterBof#formatSelectFacetFilter(FacetTypeEnum, long);
	 * @param oriFacetFilter
	 * @param valueId
	 * @return
	 */
	@Deprecated
	public FacetFilterBo formatSelectFacetFilter(FacetFilterBo oriFacetFilter,long valueId);
	
	/**
	 * 新的业务逻辑，支持多选		William.zhang	20130507
	 * 获得当前分组中是否含有以选中的筛选项<br>
	 * 包含品牌，属性项<br>
	 * 此过程会生成属性项的删除filter条件
	 * 注意：后期属性值与品牌将从缓存中读取，避免不必要的facet损耗
	 * @see FacetFilterBof#formatSelectFacetFilter(FacetTypeEnum, long);
	 * @param oriFacetFilter
	 * @param valueId
	 * @return
	 */
	@Deprecated
	public FacetFilterBo formatSelectFacetFilter(FacetFilterBo oriFacetFilter,
			List<Long> attrValIds);
	
	/**
	 * 获得已选中的筛选项<br>
	 * 注：目前仅价格过滤可适用此方法<br>
	 * @param oriFacetFilter
	 * @param valueId
	 * @return
	 */
	public FacetFilterBo formatSelectFacetFilter(FacetTypeEnum type,long valueId);
}
