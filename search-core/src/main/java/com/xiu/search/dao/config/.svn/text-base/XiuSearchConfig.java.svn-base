package com.xiu.search.dao.config;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XiuSearchConfig {

	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(XiuSearchConfig.class);

	@SuppressWarnings("static-access")
	public XiuSearchConfig(Properties propertiesHolder) {
		this.propertiesHolder = propertiesHolder;
	}

	/**
	 * 高亮前缀
	 */
	public static final String SEARCH_HIGHLIGHT_PREFIX = "search.highlight.prefix";

	/**
	 * 高亮后缀
	 */
	public static final String SEARCH_HIGHLIGHT_SUFFIX = "search.highlight.suffix";

	public static final String ENCRYPTION_AUTH_MD5CODE = "encryption.auth_md5code";

	public static final String VALID_ADMIN_USER_ID = "search.admin_id";
	/**
	 * 日志Warn的开关
	 */
	public static final String LOG_WARN_SWITCH = "search.log.warn.switch";
	/**
	 * 日志Warn的超时时间
	 */
	public static final String LOG_WARN_OVERTIME = "search.log.warn.overtime";
	/**
	 * 百分点连接客户端名称
	 */
	public static final String BFD_CIIENT_NAME = "search.bfd.client_name";

	/**
	 * 是否启用综合排序
	 */
	public static final String COMP_SORT_ENABLED = "search.comp.sort.enabled";

	/**
	 * 是否启用运营分类改造
	 */
	public static final String CATALOG_ENABLED = "search.catalog.remold.enable";

	/**
	 * 是否过滤掉老的运营分类
	 */
	public static final String FILTER_OLD_CATALOG = "search.oldcatalog.filter.enable";

	/**
	 * 老的运营分类ID的最大位数
	 */
	public static final String OLD_CATALOG_LENGTH = "search.oldcatalog.id.maxlength";

	/**
	 * 前端是否展示display为0的运营分类
	 */
	public static final String ENABLE_SHOW_DISPLAY0_CATALOG = "search.catalog.get.displayis0";

	/**
	 * SearchWeb工程运营分类缓存刷新频率
	 */
	public static final String CATALOG_REFRESH_FREQUENCY = "scatalog.cache.refresh.frequency";
	/**
	 * SearchWeb属性项缓存刷新频率
	 */
	public static final String ATTR_REFRESH_FREQUENCY = "scatalog.cache.attr.group";
	
	/**
	 * 推荐数据是否允许从Memcache中读取
	 */
	public static final String ENABLED_LOAD_FROM_MEMCACHE="search.recommend.load.from.memcache";
	
	/**
	 * 搜索系统构造商品数据时是否构造尺码数据
	 */
	public static final String SKU_SIZE_SHOW_BUILD="search.sku.size.show.build";
	
	/**
	 * 搜索系统每个商品构造的尺码数据个数
	 */
	public static final String SKU_SIZE_SHOW_COUNT="search.sku.size.show.count";
	
	/**
	 * 搜索系统每个商品构造的尺码数据时，尺码列表是否按库存排序
	 */
	public static final String SKU_SIZE_SHOW_QTYSORT="search.sku.size.show.qtysort";
	
	
	/**
	 * Filter过滤时，跳转到HUGO店中店的关键词
	 */
	public static final String HUGO_FILTER_WORDS="hugo.filter.search.keywords";
	
	//杨幂包
	public static final String YANGMI_FILTER_WORDS="yangmi.filter.search.keywords";
	
	/**
	 * Filter过滤时，跳转到HUGO店中店的品牌ID列表
	 */
	public static final String HUGO_FILTER_BRAND_IDS="hugo.filter.search.brand.ids";
	
	

	/**
	 * Ebay对接的 provider code 值
	 */
	public static final String EBAY_PROVIDER_CODE = "search.ebay.pcode";

	private volatile static Properties propertiesHolder;

	public static String getPropertieValue(String key) {
		return propertiesHolder.getProperty(key);
	}
}
