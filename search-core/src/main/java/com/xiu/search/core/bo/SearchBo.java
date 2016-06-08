package com.xiu.search.core.bo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xiu.search.core.solr.enumeration.ItemShowTypeEnum;
import com.xiu.search.core.solr.enumeration.MktTypeEnum;
import com.xiu.search.core.solr.params.SearchFatParams;
import com.xiu.search.core.util.Page;

/**
 * 搜索结果页面
 * @author Leon
 *
 */
public class SearchBo {
	
	/**
	 * 品牌区域英文首字母分组的定义
	 */
	public final static String[] BRAND_FIRST_LETTER_GROUPS = new String[]{"ABCD","EFG","HIJK","LMN","OPQ","RST","UVW","XYZ"};
	
	/**
	 * 品牌区域英文品牌集合	William.zhang	20130506
	 */
	private List<FacetFilterValueBo> facetFilterValueBoList;
	/**
	 * 传到前台做搜索的JOSN对象数据	William.zhang	20130506
	 */
	private String facetFilterValueBoJSON;
	/**
	 * 传到前台做搜索的JOSN对象数据，用来生成发货方式	William.zhang	20130603
	 */
	private String itemShowTypeJSON;
	/**
	 * 以选中的树结构，用来在页面判断是否高亮	William.zhang	20130603
	 */
	private Set<Integer> selectedCatalogIdSet = new HashSet<Integer>();
	
	/**
	 * 品牌区域英文首字母分组包含的品牌集合
	 */
	private Map<String, List<FacetFilterValueBo>> letterBrandMap;
	/**
	 * 左侧运营分类树
	 */
	private List<CatalogBo> catalogBoList;
	/**
	 * 当前选中的运营分类树，含完整的一棵树
	 */
	private CatalogBo selectedCatalogTree;
	/**
	 * 当前选中的运营分类
	 */
	private CatalogBo selectedCatalog;
	/**
	 * 格式化后的参数
	 */
	private SearchFatParams fatParams;
	/**
	 * 水平结构的选中运营分类
	 */
	private List<CatalogBo> selectCatalogPlaneList;
	/**
	 * 品牌筛选条件
	 */
	private FacetFilterBo brandFacetBo;
	/**
	 * 价格区间筛选条件
	 */
	private FacetFilterBo priceFacetBo;
	/**
	 * 属性过滤条件
	 */
	private List<FacetFilterBo> attrFacetBoList;
	/**
	 * 当前选中的筛选区
	 */
	private List<FacetFilterBo> selectFacetBoList;
	/**
	 * 搜索商品结果
	 */
	private List<GoodsItemBo> goodsItemList;
	/**
	 * 相关搜索词
	 */
	private List<String> relatedTerms;
	/**
	 * 真正搜索的关键字<br>
	 * 错误纠正使用
	 */
	private String searchKW;
	/**
	 * 错误词纠正<br>
	 * 用于无结果重新搜索
	 */
	private String termErrorCorrect;
	/**
	 * 智能纠正<br>
	 * 用于有搜索结果情况
	 */
	private String termAutoCorrect;
	/**
	 * 不同市场来源的商品数量
	 */
	private Map<String,Integer> mktItemCountMap;
	/**
	 * 页码信息
	 */
	private Page page;
	
	/**
	 * 标签
	 */
	private XTagBo xTagBo;
	
	/**
	 * 相关标签
	 */
	private List<XTagBo> xTagBoList;
	
	/**
	 * 官网推荐所需分类(前3个商品对应最后一级分类)
	 */
	private String recommendCatIds;
	
	/**
	 * 官网商品编号(前3个商品对应最后一级分类)
	 */
	private String recommendItemIds;
	
	/**
	 * 官网品牌编号(前3个商品对应最后一级分类)
	 */
	private String recommenBrandIds;
	
	private ItemShowTypeEnum showType;
	
	/**
	 * 展示时一级的兄弟节点
	 */
	private List<CatalogBo> firstCataList;
	
	/**
	 * 展示时二级的兄弟节点
	 */
	private List<CatalogBo> secondCataList;
	
	/**
	 * 展示时三级的兄弟节点
	 */
	private List<CatalogBo> thirdCataList;
	
	
	/**
	 * @return the firstCataList
	 */
	public List<CatalogBo> getFirstCataList() {
		return firstCataList;
	}

	/**
	 * @param firstCataList the firstCataList to set
	 */
	public void setFirstCataList(List<CatalogBo> firstCataList) {
		this.firstCataList = firstCataList;
	}
	
	/**
	 * @return the secondCataList
	 */
	public List<CatalogBo> getSecondCataList() {
		return secondCataList;
	}

	/**
	 * @param secondCataList the secondCataList to set
	 */
	public void setSecondCataList(List<CatalogBo> secondCataList) {
		this.secondCataList = secondCataList;
	}

	/**
	 * @return the thirdCataList
	 */
	public List<CatalogBo> getThirdCataList() {
		return thirdCataList;
	}

	/**
	 * @param thirdCataList the thirdCataList to set
	 */
	public void setThirdCataList(List<CatalogBo> thirdCataList) {
		this.thirdCataList = thirdCataList;
	}

	/**
	 * 获取用于搜索的关键字
	 * @return
	 */
	public String getSearchKW() {
		return null != termErrorCorrect ? termErrorCorrect : null!=fatParams?fatParams.getKeyword():"";
	}

	/**
	 * 增加属性筛选
	 * @param facetFilterBo
	 */
	public void addAttrFacetBoList(FacetFilterBo facetFilterBo) {
		if(attrFacetBoList == null)
			attrFacetBoList = new ArrayList<FacetFilterBo>();
		attrFacetBoList.add(facetFilterBo);
	}
	
	public FacetFilterBo removeAttrFacetBoList(long facetId){
		if(attrFacetBoList == null)
			return null;
		for (int i = 0,len=attrFacetBoList.size(); i < len; i++) {
			if(facetId == attrFacetBoList.get(i).getFacetId()){
				return attrFacetBoList.remove(i);
			}
		}
		return null;
	}
	
	public FacetFilterBo removeAttrFacetBoList(String facetName){
		if(attrFacetBoList == null)
			return null;
		for (int i = 0,len=attrFacetBoList.size(); i < len; i++) {
			if(facetName.equals(attrFacetBoList.get(i).getFacetFieldName())){
				return attrFacetBoList.remove(i);
			}
		}
		return null;
	}

	/**
	 * 增加已选择的筛选项或品牌或价格
	 * @param facetFilterBo
	 */
	public void addSelectFacetBoList(FacetFilterBo facetFilterBo) {
		if(this.selectFacetBoList == null)
			this.selectFacetBoList = new ArrayList<FacetFilterBo>();
		this.selectFacetBoList.add(facetFilterBo);
	}
	
	public List<CatalogBo> getSelectCatalogPlaneList() {
		return selectCatalogPlaneList;
	}

	public void setSelectCatalogPlaneList(List<CatalogBo> selectCatalogPlaneList) {
		this.selectCatalogPlaneList = selectCatalogPlaneList;
	}

	public void putMktItemCount(MktTypeEnum mktType,Integer count){
		if(null == this.mktItemCountMap)
			this.mktItemCountMap = new HashMap<String, Integer>();
		this.mktItemCountMap.put(mktType.name(), count);
	}

	public Map<String, Integer> getMktItemCountMap() {
		return mktItemCountMap;
	}

	public Map<String, List<FacetFilterValueBo>> getLetterBrandMap() {
		return letterBrandMap;
	}

	public void setLetterBrandMap(
			Map<String, List<FacetFilterValueBo>> letterBrandMap) {
		this.letterBrandMap = letterBrandMap;
	}

	/**
	 * 获取品牌分组的ABCD/EFG项
	 * @return
	 */
	public String[] getBrandFirstLetterGroups() {
		return BRAND_FIRST_LETTER_GROUPS;
	}

	/*
	 * 添加排序功能： lvshd 2014-07-23 12:03
	 */
	public List<FacetFilterBo> getAttrFacetBoList() {
		if(attrFacetBoList!=null && attrFacetBoList.size()>1){
			//排序
			Collections.sort(attrFacetBoList,new Comparator<FacetFilterBo>() {
				@Override
				public int compare(FacetFilterBo o1, FacetFilterBo o2) {
					return o2.getShowOrder() - o1.getShowOrder();
				}
			});
		}
		return attrFacetBoList;
	}
	public void setAttrFacetBoList(List<FacetFilterBo> attrFacetBoList) {
		this.attrFacetBoList = attrFacetBoList;
	}

	public List<CatalogBo> getCatalogBoList() {
		return catalogBoList;
	}
	public void setCatalogBoList(List<CatalogBo> catalogBoList) {
		this.catalogBoList = catalogBoList;
	}
	
	public CatalogBo getSelectedCatalogTree() {
		return selectedCatalogTree;
	}

	public void setSelectedCatalogTree(CatalogBo selectedCatalog) {
		this.selectedCatalogTree = selectedCatalog;
	}

	public List<FacetFilterBo> getSelectFacetBoList() {
		return selectFacetBoList;
	}
	public void setSelectFacetBoList(List<FacetFilterBo> selectFacetBoList) {
		this.selectFacetBoList = selectFacetBoList;
	}
	public List<GoodsItemBo> getGoodsItemList() {
		return goodsItemList;
	}
	public void setGoodsItemList(List<GoodsItemBo> goodsItemList) {
		this.goodsItemList = goodsItemList;
	}
	public List<String> getRelatedTerms() {
		return relatedTerms;
	}
	public void setRelatedTerms(List<String> relatedTerms) {
		this.relatedTerms = relatedTerms;
	}
	public String getTermErrorCorrect() {
		return termErrorCorrect;
	}
	public void setTermErrorCorrect(String termError) {
		this.termErrorCorrect = termError;
	}

	public String getTermAutoCorrect() {
		return termAutoCorrect;
	}

	public void setTermAutoCorrect(String termAutoCorrect) {
		this.termAutoCorrect = termAutoCorrect;
	}

	public FacetFilterBo getBrandFacetBo() {
		return brandFacetBo;
	}

	public void setBrandFacetBo(FacetFilterBo brandFacetBo) {
		this.brandFacetBo = brandFacetBo;
	}

	public FacetFilterBo getPriceFacetBo() {
		return priceFacetBo;
	}

	public void setPriceFacetBo(FacetFilterBo priceFacetBo) {
		this.priceFacetBo = priceFacetBo;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public SearchFatParams getFatParams() {
		return fatParams;
	}

	public void setFatParams(SearchFatParams fatParams) {
		this.fatParams = fatParams;
	}

	public CatalogBo getSelectedCatalog() {
		return selectedCatalog;
	}

	public void setSelectedCatalog(CatalogBo selectedCatalog) {
		this.selectedCatalog = selectedCatalog;
	}

	public String getRecommendCatIds() {
		return recommendCatIds;
	}

	public void setRecommendCatIds(String recommendCatIds) {
		this.recommendCatIds = recommendCatIds;
	}

	public String getRecommendItemIds() {
		return recommendItemIds;
	}

	public void setRecommendItemIds(String recommendItemIds) {
		this.recommendItemIds = recommendItemIds;
	}

	public String getRecommenBrandIds() {
		return recommenBrandIds;
	}

	public void setRecommenBrandIds(String recommenBrandIds) {
		this.recommenBrandIds = recommenBrandIds;
	}

	public XTagBo getxTagBo() {
		return xTagBo;
	}

	public void setxTagBo(XTagBo xTagsBo) {
		this.xTagBo = xTagsBo;
	}

	public List<XTagBo> getxTagBoList() {
		return xTagBoList;
	}

	public void setxTagBoList(List<XTagBo> xTagsBoList) {
		this.xTagBoList = xTagsBoList;
	}

	public List<FacetFilterValueBo> getFacetFilterValueBoList() {
		return facetFilterValueBoList;
	}

	public void setFacetFilterValueBoList(
			List<FacetFilterValueBo> facetFilterValueBoList) {
		this.facetFilterValueBoList = facetFilterValueBoList;
	}

	public String getFacetFilterValueBoJSON() {
		return facetFilterValueBoJSON;
	}

	public void setFacetFilterValueBoJSON(String facetFilterValueBoJSON) {
		this.facetFilterValueBoJSON = facetFilterValueBoJSON;
	}

	public String getItemShowTypeJSON() {
		return itemShowTypeJSON;
	}

	public void setItemShowTypeJSON(String itemShowTypeJSON) {
		this.itemShowTypeJSON = itemShowTypeJSON;
	}

	public ItemShowTypeEnum getShowType() {
		return showType;
	}

	public void setShowType(ItemShowTypeEnum showType) {
		this.showType = showType;
	}

//	public Set<Integer> getSelectedCatalogIdSet() {
//		return selectedCatalogIdSet;
//	}
//
//	public void setSelectedCatalogIdSet(Set<Integer> selectedCatalogIdSet) {
//		this.selectedCatalogIdSet = selectedCatalogIdSet;
//	}
	
	
	
}
