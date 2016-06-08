package com.xiu.search.core.config;

public abstract class XiuSearchProperty {

	private static XiuSearchProperty instance;
	
	public static XiuSearchProperty getInstance(){
		if(instance == null){
			instance = new XiuSearchPropertyImpl();
		}
		return instance;
	}
	
	protected abstract void init();
	
	public abstract void reloadConfigData();
	
	/**
     * 根据配置key获取配置value
     * @param key
     * @return
     */
    public abstract String getValueByKey(String key);
    
    /**
     * 获取是否启用新的运营分类系统
     * @return
     */
    public abstract boolean getCatalogRemoldEnable();
    
    /**
     * 获取是否过滤掉老的运营分类
     * @return
     */
    public abstract boolean getOldCatalogFilterEnable();
    
    /**
     * 前端是否展示display为0的运营分类
     * @return
     */
    public abstract boolean enabledCatalogByDisplayIS0();
    
    /**
     * 获取老运营分类ID最大位数 (默认为8位)
     * @return
     */
    public abstract int getOldCatalogIdMaxLength();
    
    
    /**
     * 获取百分点账号
     * @return
     */
    public abstract String getBFDPassword();
    
    /**
     * 运营分类缓存刷新频率  单位为:毫秒 (默认为10分钟)
     * @return
     */
    public abstract long getCatalogRefreshfrequency();
    /**
     * 运营分类缓存刷新频率  单位为:毫秒 (默认为11分钟)
     * @return
     */
    public abstract long getAttrRefreshfrequency();
    
    /**
     * 是否允许从Memcache中加载数据（如果不允许从DB中加载数据）
     * @return
     */
    public abstract boolean enabledLoadFromMemcache();
    
    /**
     * 索系统构造商品数据时是否构造尺码数据
     * @return
     */
    public abstract boolean enableBuildSkuSize();
    
    /**
     * 搜索系统每个商品构造的尺码数据个数,默认值为8
     * @return
     */
    public abstract int showSkuSizeCount();
    
    /**
     * 搜索系统每个商品构造的尺码数据时，尺码列表是否按库存排序
     * @return
     */
    public abstract boolean enableSkuSizeSortByQty();
    
    /**
     * Filter过滤时，跳转到HUGO店中店的关键词
     * @return
     */
    public abstract String hugoKeywords();
    
    /**
     * Filter过滤时，跳转到HUGO店中店的品牌ID
     * @return
     */
    public abstract String hugoBrandIds();
    
    /**
     * 杨幂包
     * @return
     */
    /*public abstract String yangMiKeyWords();*/
    
}

