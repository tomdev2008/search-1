package com.xiu.search.core.solr.index;

public enum GoodsIndexFieldEnum {

	/**
	 * 默认搜索字段
	 */
	DEFAULT_SEARCH("searchable"),
	/**
	 * lucene得分
	 */
	SCORE("score"),
	
	ITEM_ID("itemId"),
	PART_NUMBER("partNumber"),
//	CLASS_IDS("classIds"),
//	CLASS_NAMES("classNames"),
	OCLASS_PATH("oclassPath"),
	OCLASS_PATH_EBAY("oclassPathEBay"),
	OCLASS_IDS("oclassIds"),
//	OCLASS_NAMES("oclassNames"),
//	MCLASS("mclass"),
	
	/**
	 * 商品综合评分，仅Ebay适用
	 */
	ITEM_PIQ("itemPIQ"),
	
	/**
	 * 综合排序得分--分类
	 */
	CATALOG_SCORE("comCatScore_"),
	
	/**
	 * 综合排序得分--品牌
	 */
	BRAND_SCORE("comBrandScore"),
	
	
	/**
	 * 品牌ID
	 */
	BRAND_ID("brandId"),
	/**
	 * 品牌ID和NAME组合字段
	 */
	BRAND_ID_NAME("brandNameDisplay"),
	/**
	 * 品牌中文
	 */
	BRAND_NAME("brandName"),
	/**
	 * 品牌英文
	 */
	BRAND_NAME_EN("brandNameEn"),
	ITEM_NAME("itemName"),
//	ITEM_NAME_PRE("itemNamePre"),
//	ITEM_NAME_POST("itemNamePost"),
//	DESC_SHORT("descShort"),
//	PRICE_XIU("priceXiu"),
//	PRICE_MKT("priceMkt"),
	/**
	 * 最终价格
	 */
	PRICE_FINAL("priceFinal"),
	/**
	 * 属性值ID
	 */
	ATTR_IDS("attrs"),
	/**
	 * 500个属性项对应的属性值
	 */
	ATTRS_PREFIX("attr_"),
	/**
	 * 品牌分类权重
	 */
	CBRAND_WEIGHT_PREFIX("brandWeight_"),
	/**
	 * 折扣
	 */
	DISCOUNT("discount"),
//	FLAG_INSTMT("flagInstmt"),
//	FLAG_GLOABL("flagGloabl"),
	/**
	 * 总销量
	 */
	SALES_VOLUME("salesVolume"),
	/**
	 * 总销售额
	 */
	SALES_AMOUNT("salesAmount"),
	
	ITEMSCORE("itemScore"),
	/**
	 * 商品标签
	 */
	ITEM_TAGS("itemTags"),
	/**
	 * 标签搜索的标签ID
	 */
	ITEM_LABELS("itemLabels"),
	/**
	 * 标签搜索的标签ID
	 */
	ITEM_LABEL_SORT("itemLabelSort_"),
	/**
	 * 是否上线标志
	 */
	ONSALE_FLAG("stateOnsale"),
//	STATE_OFFSHOW("stateOffshow"),
	/**
	 * 上架时间
	 */
	ONSALE_TIME("onsaleTime"),
	/**
	 * 店中店标识
	 */
	PROVIDER_CODE("providerCode"),
	/**
	 * 商品市场标记<br>
	 * 0 - 走秀<br>
	 * 1 - ebay<br>
	 */
	MKT_TYPE("mktType"),
	ITEM_SHOW_TYPE("itemShowType"),
//	ITEM_UPDATE_TIME("itemUpdateTime"),
	IMG_URL("imgUrl"),
//	AUTHOR("author"),
//	ALLFIELDS("allfields"),
//	ATTRS("attrs"),
//	TIMESTAMP("timestamp"),
	ITEM_COLOR("itemColor"),
	ITEM_SIZE("itemSize"),
//	PRODUCTAREA("productArea"),
//	ITEM_LIMIT("itemLimit"),
//	OFFSALETIME("offsaleTime"),
//	SALEPRESTART("salePreStart"),
//	SALEPREEND("salePreEnd"),
//	SALEPRERECEIVE("salePreReceive"),
//	SALELIMITSTART("saleLimitStart"),
//	SALELIMITEND("saleLimitEnd"),
	SIMILAR_GROUPID("similarGroupId"),
	SIMILAR_SCORE("similarScore"),
	/**
	 * 	moreLink相似商品排前
	 *  1 - 排头
	 *	0 - 非排头
	 */
	SIMILAR_LEVEL("similarLevel"),
	/**
	 * 发货方式
	 */
	SPACE_FLAG("spaceFlag");
	
	private String fieldName;
	
	GoodsIndexFieldEnum(String fieldName){
		this.fieldName = fieldName;
	}
	
	public String fieldName() {
		return fieldName;
	}
	
}
