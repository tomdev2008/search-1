package com.xiu.search.core.service;

import java.util.Map;

/**
 * 搜索配置service及缓存 
 * @author WuYongqi
 */
public interface XiuSearchConfigService {
    
    public Map<String, String> getXiuSearchConfigMap();
    
    public void reLoadXiuSearchConfigMap();
}
