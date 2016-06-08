package com.xiu.search.core.util;

/**
 * 
 * com.xiu.search.core.util.Constants.java

 * @Description: TODO 常量类 【目前只有运营分类改造使用】

 * @author lvshuding   

 * @date 2013-4-17 上午11:20:38

 * @version V1.0
 */
public class Constants {

	private Constants(){}
	
	// 索引表中存储字符串的分隔符
	public static final String SPLIT_STR_TWO = "|";
	public static final String SPLIT_STR_THIRD = ":";
	
	// xiu 索引mktType字段值，0：官网，1：ebay
    public static final  int XIUINDEX_MKTTYPE_XIU = 0;
    public static final  int XIUINDEX_MKTTYPE_EBAY = 1;
    
    // ebay标识
    public static final  String PROVIDER_CODE_EBAY = "1528";
    
    /**
     * 品牌下推荐商品的业务类型
     */
    public static final int BRAND_RECOMMEND_GOODS=2;
    
    /**
     * 品牌下推荐分类的业务类型
     */
    public static final int BRAND_RECOMMEND_CATS=1;
    
    /**
     * 分类下推荐商品的业务类型
     */
    public static final int CATALOG_RECOMMEND_GOODS=1;
    
    /**
     * 分类下推荐品牌的业务类型
     */
    public static final int CATALOG_RECOMMEND_BRAND=2;
    
    /**
	 * 缓存操作相关的日志头
	 */
	public static final String MCACHE_LOG_HEADER="【缓存操作】";
    
    /** memcached key前缀 品牌下推荐商品 */
	public static final String MEMCACHE_PRE_BRAND_REC_GOODS = "BRAND_REC_GOODS_";
	
	/** memcached key前缀 品牌下推荐分类 */
	public static final String MEMCACHE_PRE_BRAND_REC_CAT = "BRAND_REC_CAT_";
	
	/** memcached key前缀 运营分类下推荐商品 */
	public static final String MEMCACHE_PRE_CAT_REC_GOODS = "CAT_REC_GOODS_";
	
	/** memcached key前缀 运营分类下推荐品牌 */
	public static final String MEMCACHE_PRE_CAT_REC_BRAND = "CAT_REC_BRAND_";
	
	/**
	 * 运营分类隐藏的标识值
	 */
	public static final String CATALOG_DISPLAY_HIDDEN="2";
	
	/**
	 * 所有品牌信息缓存key
	 */
	public static final String ALL_BRAND_CACHE_KEY = "ALL_BRAND_CACHE_KEY";
	
}
