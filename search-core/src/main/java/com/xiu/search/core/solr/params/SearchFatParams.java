package com.xiu.search.core.solr.params;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.xiu.search.core.enumeration.RequestInletEnum;
import com.xiu.search.core.solr.enumeration.FacetPriceRangeQueryEnum;
import com.xiu.search.core.solr.enumeration.ItemShowTypeEnum;
import com.xiu.search.core.solr.enumeration.MktTypeEnum;
import com.xiu.search.core.solr.enumeration.SearchSortOrderEnum;
import com.xiu.search.core.solr.enumeration.SearchTimeRangeEnum;
import com.xiu.search.core.util.Page;

/**
 * 搜索页面的业务条件参数
 * @author Leon
 *
 */
public class SearchFatParams implements Cloneable,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1083405018339203120L;
	 
	/**
	 * 输入关键字
	 */
	private String keyword;
	
	/**
	 * 标签搜索字段
	 */
	private String searchTags;
	
	/**
	 * 标签搜索的ID
	 */
	private Integer searchTagsId;
	
	/**
	 * 分类ID
	 */
	private Integer catalogId;
	/**
	 * 运营分类对应的查询条件
	 */
	@Deprecated
	private String catalogQueryStr;
	/**
	 * 品牌ID
	 */
	private Integer brandId;
	
	/**
	 * 品牌ID,用于多选查询	William.zhang 20130506
	 */
	private List<Long> brandIds;
	/**
	 * 价格区间
	 */
	private FacetPriceRangeQueryEnum priceRangeEnum;
	/**
	 * 上架时间
	 */
	private SearchTimeRangeEnum timeRangeEnum;
	/**
	 * 排序
	 */
	private SearchSortOrderEnum sort;
	/**
	 * 起始价格
	 */
	private Double startPrice;
	/**
	 * 结束价格
	 */
	private Double endPrice;
	/**
	 * 是否可购买
	 */
	private Boolean buyableFlag;
	/**
	 * 属性值的ID
	 */
	private String[] attrValIds;
	
	/**
	 * 添加的额外facet字段
	 */
	private String[] extFacetFields = null;
	
	/**
	 * 属性值的ID,支持多选	William.zhang	20130507
	 */
	private List<List<String>> attrValIdList;
	
	/**
	 * 属性值查询的条件
	 */
	private String filter;
	
	/**
	 * 市场来源类型 <br>
	 * 0为走秀商品，1为ebay商品
	 */
	private MktTypeEnum mktType;
	
	/**
	 * 翻页
	 */
	private final Page page;
	
	/**
	 * 是否是第一次搜索
	 * 第一次请求的逻辑会有所不同
	 */
	private boolean isFirstRequest;
	
	/**
	 * 是否根据商品数量进行MKT tab的自动切换
	 */
	private boolean autoMktSwitchFlag;
	
	/**
	 * 店中店标识
	 */
	private String providerCode;
	
	private RequestInletEnum requestInletEnum;
	
//	/**
//	 * 是否查询纠错
//	 */
//	private boolean isCorrect;
//	
//	/**
//	 * 来源渠道
//	 */
//	private FromTypeEnum fromTypeEnum;
	/**
	 * 相似商品分组编号
	 */
	private String similarGroupId;
	

	/**
	 * 商品类别
	 */
	@Deprecated
    private List<Integer> itemShowType;
    
	private ItemShowTypeEnum showType;
	
    /**
     * 用户选择的商品
     */
    private Integer channel;
	
	public SearchFatParams(){
		this.page = new Page(30);
	}
	
	/**
	 * @param rows 返回的记录总数
	 */
	public SearchFatParams(int pageSize){
		this.page = new Page(pageSize);
	}
	
	public String[] getAttrValIds() {
		return attrValIds;
	}

	public void setAttrValIds(String[] attrValIds) {
		this.attrValIds = attrValIds;
		this.filter = StringUtils.join(attrValIds, ";");
	}

	public MktTypeEnum getMktType() {
		return mktType;
	}

	public void setMktType(MktTypeEnum mktType) {
		this.mktType = mktType;
	}

	public boolean isFirstRequest() {
		return isFirstRequest;
	}

	public void setFirstRequest(boolean isFirstRequest) {
		this.isFirstRequest = isFirstRequest;
	}

	@Deprecated
	public String getCatalogQueryStr() {
		return catalogQueryStr;
	}
	@Deprecated
	public void setCatalogQueryStr(String catalogQueryStr) {
		this.catalogQueryStr = catalogQueryStr;
	}
	
	public String getSearchTags() {
		return searchTags;
	}

	public void setSearchTags(String searchTags) {
		this.searchTags = searchTags;
	}

	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Integer getCatalogId() {
		return catalogId;
	}
	public void setCatalogId(Integer catalogId) {
		this.catalogId = catalogId;
	}
	public Integer getBrandId() {
		return brandId;
	}
	
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	public FacetPriceRangeQueryEnum getPriceRangeEnum() {
		return priceRangeEnum;
	}
	public void setPriceRangeEnum(FacetPriceRangeQueryEnum priceRangeEnum) {
		this.priceRangeEnum = priceRangeEnum;
	}
	public SearchSortOrderEnum getSort() {
		return sort;
	}
	public void setSort(SearchSortOrderEnum sort) {
		this.sort = sort;
	}
	public Boolean getBuyableFlag() {
		return buyableFlag;
	}
	public void setBuyableFlag(Boolean buyableFlag) {
		this.buyableFlag = buyableFlag;
	}
	
	public Page getPage() {
		return page;
	}

	public Double getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(Double startPrice) {
		this.startPrice = startPrice;
	}

	public Double getEndPrice() {
		return endPrice;
	}

	public void setEndPrice(Double endPrice) {
		this.endPrice = endPrice;
	}
	
	private static Logger logger = Logger.getLogger(SearchFatParams.class);
	/**
	 * 注意！若有复杂类型，或需修改对象，请重写为深clone<br>
	 * 偷懒了 =.=||
	 */
	@Override
	public SearchFatParams clone(){
		try {
			return (SearchFatParams) super.clone();
		} catch (CloneNotSupportedException e) {
			logger.error("XiuSolrSearchParams clone 失败",e);
		}
		return null;
	}

	public String getFilter() {
		return filter;
	}

	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}
	
	public String cacheKey(){
		StringBuilder sb = new StringBuilder();
		sb.append("keyword:").append(this.keyword)
		.append('|').append("catalogId:").append(this.catalogId)
		.append('|').append("endPrice:").append(this.endPrice)
		.append('|').append("startPrice:").append(this.startPrice)
		.append('|').append("mktType:").append(this.mktType)
		.append('|').append("priceRangeEnum:").append(this.priceRangeEnum)
		.append('|').append("catalogQueryStr").append(this.catalogQueryStr);
		if(attrValIdList != null && attrValIdList.size()>0){
			sb.append('|').append("attrValIds:");
			List<String> list = new ArrayList<String>();
			for(List<String> i : this.attrValIdList){
				list.addAll(i);
			}
			Collections.sort(list);
			for(String s : list){
				sb.append(s);
			}
		}
		if(brandIds != null && brandIds.size()>0){
			sb.append('|').append("brandIds:");
			Collections.sort(this.brandIds);
			for(Long i : brandIds){
				sb.append(i);
			}
		}
		if(itemShowType != null && itemShowType.size() > 0){
			sb.append('|').append("itemShowType:");
			Collections.sort(this.itemShowType);
			for(int i : this.itemShowType){
				sb.append(i);
			}
		}
		return sb.toString();
	}
	/**
	 * 是否需要根据商品数量自动切换tab
	 * @return
	 */
	public boolean isAutoMktSwitchFlag() {
		return autoMktSwitchFlag;
	}

	public void setAutoMktSwitchFlag(boolean autoMktSwitchFlag) {
		this.autoMktSwitchFlag = autoMktSwitchFlag;
	}

	public RequestInletEnum getRequestInletEnum() {
		return requestInletEnum;
	}

	public void setRequestInletEnum(RequestInletEnum requestInletEnum) {
		this.requestInletEnum = requestInletEnum;
	}

	public Integer getSearchTagsId() {
		return searchTagsId;
	}

	public void setSearchTagsId(Integer searchTagsId) {
		this.searchTagsId = searchTagsId;
	}

	public String getSimilarGroupId() {
		return similarGroupId;
	}

	public void setSimilarGroupId(String similarGroupId) {
		this.similarGroupId = similarGroupId;
	}
	@Deprecated
    public List<Integer> getItemShowType() {
        return itemShowType;
    }
    @Deprecated
    public void setItemShowType(List<Integer> itemShowType) {
        this.itemShowType = itemShowType;
    }

	public List<Long> getBrandIds() {
		return brandIds;
	}

	public void setBrandIds(List<Long> brandIds) {
		this.brandIds = brandIds;
	}

	public List<List<String>> getAttrValIdList() {
		return attrValIdList;
	}
	
	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	public ItemShowTypeEnum getShowType() {
		return showType;
	}

	public void setShowType(ItemShowTypeEnum showType) {
		this.showType = showType;
	}

	public String[] getExtFacetFields() {
		return extFacetFields;
	}

	public void setExtFacetFields(String[] extFacetFields) {
		this.extFacetFields = extFacetFields;
	}

	public void setAttrValIdList(List<List<String>> attrValIdList) {
		this.attrValIdList = attrValIdList;
		//还原前台输入数组	William.zhang	20130507
		String oriFilterStr = "";
		
		//新的拼装原始字符串逻辑	William.zhang	20130508
		if(attrValIdList != null && attrValIdList.size() > 0){
			for(List<String> list : attrValIdList){
				if(list != null && list.size() > 0){
		    	  	for(String str : list){
		    	  		oriFilterStr += str + "|";
		    	  	}
		    	  	oriFilterStr = oriFilterStr.substring(0,oriFilterStr.length()-1);
				}
	    	  	oriFilterStr += ";";
			}
			oriFilterStr = oriFilterStr.substring(0,oriFilterStr.length()-1);
		}
		
		this.filter = oriFilterStr;
	}

	/**
	 * 上架时间范围
	 * @return
	 */
	public SearchTimeRangeEnum getTimeRangeEnum() {
		return timeRangeEnum;
	}

	public void setTimeRangeEnum(SearchTimeRangeEnum timeRangeEnum) {
		this.timeRangeEnum = timeRangeEnum;
	}
	
	
}