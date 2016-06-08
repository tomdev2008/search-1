package com.xiu.search.core.util;

import com.xiu.search.core.service.XiuSearchConfigService;
import com.xiu.search.dao.config.XiuSearchConfig;

/**
 * 搜索配置信息bean
 * 从数据库(XIU_SEARCH_CONFIG)中读取相关配置
 * @author WuYongqi
 */
public class XiuSearchConfigBean{

    private static XiuSearchConfigBean instance = null;
    
    /**
     * 配置缓存管理
     */
    XiuSearchConfigService xiuSearchConfigService = null;
    
    private XiuSearchConfigBean(){
    }
    
    /**
     * 获取单例实例
     * @return
     */
    public static XiuSearchConfigBean newInstance() {
            synchronized (XiuSearchConfigBean.class) {
                if (null == instance) {
                    instance = new XiuSearchConfigBean();
                }
            }
        return instance;
    }
    
    /**
     * 重载配置缓存
     */
    public void reLoadConfig() {
        try {
            xiuSearchConfigService.reLoadXiuSearchConfigMap();
        } catch (Exception e) {
            e.printStackTrace();           
        }
    }
    
    /**
     * 在spring中创建bean时初始化用
     */
    public void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                reLoadConfig();
            }
        } ).start();
    }
    
    
    
    
    
    

    /**
     * 根据配置key获取配置value
     * @param key
     * @return
     */
    public String getValueByKey(String key) {
        try {
            String value = xiuSearchConfigService.getXiuSearchConfigMap().get(key);
            if (null == value) {
                return "";
            } else {
                return value;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
    /**
     * 获取是否启用新的运营分类系统
     * @return
     */
    public boolean getCatalogRemoldEnable(){
       return "true".equals(this.getValueByKey(XiuSearchConfig.CATALOG_ENABLED));
    }
    
    /**
     * 获取是否过滤掉老的运营分类
     * @return
     */
    public boolean getOldCatalogFilterEnable(){
       return "true".equals(this.getValueByKey(XiuSearchConfig.FILTER_OLD_CATALOG));
    }
    
    /**
     * 获取老运营分类ID最大位数 (默认为8位)
     * @return
     */
    public int getOldCatalogIdMaxLength(){
    	String maxLth=this.getValueByKey(XiuSearchConfig.OLD_CATALOG_LENGTH);
    	if(maxLth==null || "".equals(maxLth.trim())){
    		maxLth="8";
    	}
       return Integer.parseInt(maxLth);
    }
    
    
    /**
     * 获取百分点账号
     * @return
     */
    public String getBFDPassword() {
        return this.getValueByKey(XiuSearchConfig.BFD_CIIENT_NAME);
    }

    
    
    
    
    
    
    
    public void setXiuSearchConfigService(XiuSearchConfigService xiuSearchConfigService) {
        this.xiuSearchConfigService = xiuSearchConfigService;
    }
}
