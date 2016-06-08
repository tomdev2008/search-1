package com.xiu.search.web.config;

import java.util.HashMap;
import java.util.Map;

public class XiuWebConstant {

	// 开发状态
	private static final String DEVELOP_PRO = "develop";
	
	
	public static final String HTTP_SCHEMA = "http";
	public static final String HTTPS_SCHEMA = "https";
	
	public static final String SCHEMA_DIV = "://";
	
	public static final String WWW_URL = "www.xiu.com";
	public static final String SEARCH_URL = "search.xiu.com";
	public static final String LIST_URL = "list.xiu.com";
	public static final String BRAND_URL = "brand.xiu.com";
	public static final String ITEM_URL = "item.xiu.com";
	public static final String STATIC_URL = "www.xiustatic.com";
	public static final String IMAGE_URL = "images.xiustatic.com";
	public static final String EBAY_URL="ebay.xiu.com";
	public static final String FERRAGAMO_URL="ferragamo.xiu.com";
	public static final String BRAND_BANNER_IMG_URL = "http://images.xiustatic.com/UploadFiles/xiu/brand/";
	/**
	 * 显示库存警告提示的库存阙值
	 */
	public static final int WARNING_INVENTERY_THRESHOLD = 2;
	
    public static final String COMMENT_URL="comm.xiu.com";
    
    /**
     * zoshow域名映射
     */
    public static final Map<Integer, String> IMAGE_ZOSHOW_URL = new HashMap<Integer, String>(){
		private static final long serialVersionUID = 3725395382706332404L;
		{
    		put(0,"http://image.zoshow.com");
    		put(1,"http://image1.zoshow.com");
    		put(2,"http://image2.zoshow.com");
    		put(3,"http://image3.zoshow.com");
    		put(4,"http://image4.zoshow.com");
    		put(5,"http://image5.zoshow.com");
    	}
    };
    
	/**
	 * 是否是开发状态
	 * @return
	 */
	public static boolean isDevelop(){
		return "1".equalsIgnoreCase(System.getProperty(DEVELOP_PRO));
	}
	
}
