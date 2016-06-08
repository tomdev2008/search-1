package com.xiu.search.dao.cache;

/**
 * 缓存类型枚举类
 * 设置缓存时间
 * @author Leon
 *
 */
public enum CacheTypeEnum {
	/**
	 * 运营分类查询条件
	 */
//	CATAGROUP_QUERY(CacheStateEnum.STATIC,CacheExpireEnum.TEN_MINUTE),
	
	/**
	 * 运营分类对应的筛选项
	 */
	CATAGROUP_ATTR(CacheStateEnum.STATIC,CacheExpireEnum.THIRTY_MINUTE),
	
	/**
	 * 索引中的运营分类树 【运营分类改造时将时间由10分钟改为5分钟】
	 */
//	CATAGROUP_OCLASSSOLRMODELLIST(CacheStateEnum.STATIC,CacheExpireEnum.FIVE_MINUTE),//TEN_MINUTE
	
	/**
	 * 品牌信息
	 */
	BRAND_INFO(CacheStateEnum.STATIC,CacheExpireEnum.TODAY),
	
	/**
	 * 走秀商品，美国直发 TAB的商品数量信息
	 */
	MKT_TAB_ITEM_COUNT(CacheStateEnum.LRU,CacheExpireEnum.FIVE_MINUTE),
	
	/**
	 * 运营分类绑定的商品优先显示列表
	 */
	CATAGROUP_BUSINESS_OPT(CacheStateEnum.STATIC,CacheExpireEnum.FIVE_MINUTE),
	
	/**
	 * 品牌绑定的的商品优先级
	 */
	BRANDID_BUSINESS_OPT(CacheStateEnum.LRU,CacheExpireEnum.FIVE_MINUTE),
	
	/**
	 * 品牌绑定的的商品、分类的优先级显示列表   addTime:2013-06-18 10:20
	 */
	BRANDID_BUSINESS_OPT2(CacheStateEnum.STATIC,CacheExpireEnum.FIVE_MINUTE),
	
	/**
	 * 标签
	 */
	X_TAGS_INFO(CacheStateEnum.LRU,CacheExpireEnum.THIRTY_MINUTE),
	
	/**
	 * 标签
	 */
	X_TAGS_ITEM_COUNT(CacheStateEnum.LRU,CacheExpireEnum.TEN_MINUTE),
	
	/**
	 * 发货地址
	 */
	ITEM_SHOW_TYPE(CacheStateEnum.LRU,CacheExpireEnum.FIVE_MINUTE),
	
	/**
	 * 过滤的运营分类树
	 */
	CATALOG_BO(CacheStateEnum.LRU,CacheExpireEnum.TEN_MINUTE),
	
	/**
	 * 相似商品
	 */
	SIMILAR_ITEMS(CacheStateEnum.STATIC,CacheExpireEnum.TWO_HOUR);
	

	
	

	
	private CacheExpireEnum expire;
	private CacheStateEnum state;
	
	CacheTypeEnum(CacheStateEnum state,CacheExpireEnum expire){
		this.state = state;
		this.expire = expire;
	}
	
	public CacheExpireEnum getExpire(){
		return this.expire;
	}
	
	public CacheStateEnum getState(){
		return this.state;
	}
	
	public static void main(String[] args) {
		for(CacheTypeEnum enum1 :CacheTypeEnum.values()){
			System.out.println(enum1.getState());
		}
	}
}
