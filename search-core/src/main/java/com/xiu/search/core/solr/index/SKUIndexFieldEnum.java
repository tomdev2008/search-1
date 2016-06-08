package com.xiu.search.core.solr.index;

/**
 * 
 * com.xiu.search.core.solr.index.SKUIndexFieldEnum.java

 * @Description: TODO SKU索引对应的字段列表类

 * @author lvshuding   

 * @date 2014-3-6 下午2:38:40

 * @version V1.0
 */
public enum SKUIndexFieldEnum {

	/**
	 * 默认搜索字段
	 */
	DEFAULT_SEARCH("searchable"),
	
	/**
	 * 商品ID
	 */
	ITEM_ID("itemId"),
	
	/**
	 * 走秀码
	 */
	ITEM_CODE("itemCode"),
	
	/**
	 * SKU
	 */
	SKU_CODE("skuCode"),
	
	/**
	 * 颜色
	 */
	SKU_COLOR("skuColor"),
	
	/**
	 * 尺码
	 */
	SKU_SIZE("skuSize"),
	
	/**
	 * 排序
	 */
	ORDER_BY("orderVal"),
	
	/**
	 * 库存量
	 */
	SKU_QTY("skuQty");
	
	
	
	private String fieldName;
	
	SKUIndexFieldEnum(String fieldName){
		this.fieldName = fieldName;
	}
	
	public String fieldName() {
		return fieldName;
	}
	
}
