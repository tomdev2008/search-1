package com.xiu.search.core.bof;

import java.util.List;

import org.apache.solr.client.solrj.response.FacetField;

import com.xiu.search.core.bo.BrandBo;
import com.xiu.search.core.bo.CatalogBo;
import com.xiu.search.core.bo.SearchBo;
import com.xiu.search.core.solr.enumeration.ItemShowTypeEnum;
import com.xiu.search.core.solr.enumeration.MktTypeEnum;

/**
 * 运营分类BO接口
 * @author Leon
 *
 */
public interface CatalogBof {
	
	/**
	 * 从已选中的运营分类树中得到最末端选中的叶子节点
	 * 注意：selectedCatalogTree需要是已经选中树结构
	 * @see CatalogBo#isSelected()
	 * @param catalogBoList
	 * @return
	 */
	public CatalogBo getSelectedCatalogFromSelectedCatalogTree(CatalogBo selectedCatalogTree);
	
	/**
	 * 获取水平结构的选中运营分类
	 * List<CatalogBo> catalogBoList 是已经选中
	 * @see CatalogBo#isSelected()
	 * @param catalogBoList
	 * @return
	 */
	public List<CatalogBo> parsePlaneSelectCatalogBo(List<CatalogBo> catalogBoList);
	
	/**
	 * 删除已选中分类的 其他兄弟节点
	 * @param catalogBoList
	 */
	public void filterCatalogTreeDeleteUnSelectedSiblingsItem(List<CatalogBo> catalogBoList);
	
	/**
	 * Xiu走秀官网通过运营分类ID查询分类树结构
	 * 用于分类列表页获取一个分类树的业务逻辑
	 * @param id
	 * @return
	 */
	public CatalogBo fetchCatalogBoTreeByIdForXiu(Integer id,ItemShowTypeEnum type);
	
	/**
	 * EBAY通过运营分类ID查询分类树结构
	 * 用于分类列表页获取一个分类树的业务逻辑
	 * @param id
	 * @return
	 */
	public CatalogBo fetchCatalogBoTreeById(Integer id);
	
	/**
	 * EBAY通过运营分类ID查询分类树结构
	 * 用于分类列表页获取一个分类树的业务逻辑
	 * @param id
	 * @return
	 */
	public CatalogBo fetchCatalogBoTreeByIdForEbay(Integer id);
	
	/**
	 * 官网,EBAY通过运营分类ID查询分类树结构 display=1,2
	 * 用于分类列表页获取一个分类树的业务逻辑
	 * @param id
	 * @param mktType
	 * @param showType
	 * @return
	 */
	public CatalogBo fetchCatalogBoTreeByIdFromDisylay12(Integer id,MktTypeEnum mktType,ItemShowTypeEnum showType);
	
	/**
	 * XIU通过运营分类ID集合，获得运营分类
	 * 用于 搜索和品牌页面反查 分类树逻辑
	 */
	public List<CatalogBo> fetchCatalogBoTreeListForXiu(FacetField facetField,Integer selectedCatalogId);
	
	/**
	 * XIU通过运营分类ID集合，获得运营分类兄弟节点
	 * 用于 搜索和品牌页面反查 最新分类树逻辑
	 * add time:2014-05-06 11:35
	 */
	public void fetchBrotherCatalogBoListForXiu(FacetField facetField,Integer selectedCatalogId,SearchBo brandBo);
	
	/**
	 * Ebay通过运营分类ID集合，获得运营分类兄弟节点
	 * 用于 搜索和品牌页面反查 最新分类树逻辑
	 * add time:2014-07-28 11:35
	 */
	void fetchBrotherCatalogBoListForEbay(FacetField facetField,Integer selectedCatalogId,SearchBo brandBo);
	
	
	/**
	 * Ebay通过运营分类ID集合，获得运营分类
	 * 用于 搜索和品牌页面反查 分类树逻辑
	 */
	public List<CatalogBo> fetchCatalogBoTreeListForEbay(FacetField facetField,Integer selectedCatalogId);
	
	
	/**
	 * 根据MktType加载一级分类列表
	 */
	public List<CatalogBo> returnFirstCataListByMktType(MktTypeEnum mktType);
	
	
}
