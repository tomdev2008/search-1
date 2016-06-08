package com.xiu.search.core.solr.enumeration;

public enum SearchSortOrderEnum {

	/**
	 * 价格升序
	 */
	PRICE_ASC(1),
	/**
	 * 价格升序
	 */
	PRICE_DESC(2),
	/**
	 * 折扣升序
	 */
	DISCOUNT_ASC(3),
	/**
	 * 折扣降序
	 */
	DISCOUNT_DESC(4),
	/**
	 * 总销量升序
	 */
	VOLUME_ASC(5),
	/**
	 * 总销量降序
	 */
	VOLUME_DESC(6),
	/**
	 * 上架时间升序 
	 */
	ONSALE_TIME_ASC(7),
	/**
	 * 上架时间降序
	 */
	ONSALE_TIME_DESC(8),
	/**
	 * 得分降序/销售额降序
	 */
	SCORE_AMOUNT_DESC(9),
	/**
	 * 总销售额升序
	 */
	AMOUNT_ASC(10),
	/**
	 * 总销售额降序 
	 */
	AMOUNT_DESC(11),
	/**
	 * 相似商品得分升序
	 */
	SIMILAR_ASC(12),
	/**
	 * 相似商品得分降序
	 */
	SIMILAR_DESC(13)
	;
	
	private int sortOrder;
	
	SearchSortOrderEnum(int sortOrder){
		this.sortOrder = sortOrder;
	}
	
	/**
	 * 获得页面参数的排序数字
	 * @return
	 */
	public int getSortOrder(){
		return this.sortOrder;
	}
	
	/**
	 * 根据排序参数获得值
	 * @param sortOrder
	 * @return
	 */
	public static SearchSortOrderEnum valueOfSortOrder(int sortOrder){
		for (SearchSortOrderEnum e : SearchSortOrderEnum.values()) {
			if(e.getSortOrder() == sortOrder)return e;
		}
		return null;
	}
	
	
	
}
