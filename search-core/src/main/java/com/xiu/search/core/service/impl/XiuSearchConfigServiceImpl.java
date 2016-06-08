package com.xiu.search.core.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.search.core.service.XiuSearchConfigService;
import com.xiu.search.dao.XiuSearchConfigDAO;
import com.xiu.search.dao.model.XiuSearchConfigModel;

/**
 * 搜索配置service及缓存 
 * @author WuYongqi
 */
@Service("xiuSearchConfigService")
public class XiuSearchConfigServiceImpl implements XiuSearchConfigService{

    private final static Logger log = Logger.getLogger(XiuSearchConfigServiceImpl.class);
    
    /**
     * 搜索配置缓存
     * key : String 
     * value : String 
     */
    
    private Map<String, String> XiuSearchConfigMap = null;
    
    @Autowired
    XiuSearchConfigDAO xiuSearchConfigDAO = null;
    
    /**
     * 从数据库中查询搜索配置列表，表：XIU_SEARCH_CONFIG
     * @return List<XiuSearchConfig>
     */
    private List<XiuSearchConfigModel> getXiuSearchConfigList() {

        List<XiuSearchConfigModel> list = null;
        
        try {            
            list = xiuSearchConfigDAO.selectAllXiuSearchConfig();
        } catch (SQLException e) {
            list = null;
            log.error("【XiuSearchConfigService】 从数据库中查询搜索配置列表出错: " + e);
            e.printStackTrace();
        }
        
        return list;
    }
    
    
    /**
     * 获取搜索配置MAP
     * @return
     */
    public Map<String, String> getXiuSearchConfigMap() {
        if (null == XiuSearchConfigMap) {
            synchronized (XiuSearchConfigService.class) {
                if (null == XiuSearchConfigMap) {
                    reLoadXiuSearchConfigMap();
                }
            }
        }
        return XiuSearchConfigMap;
    }
    
    
    

    /**
     * 重载
     * @return
     */
    public void reLoadXiuSearchConfigMap() {
        synchronized (XiuSearchConfigService.class) {
            Map<String, String> map = loadXiuSearchConfigMap();
            XiuSearchConfigMap = map;
        }
    }
    
    
    
    
    /**
     * 加载搜索配置列表，存入对应Map缓存
     * @return Map<String, String>
     */
    private Map<String, String> loadXiuSearchConfigMap() {
        
        long time = System.currentTimeMillis();
        
        List<XiuSearchConfigModel> xiuSearchConfigList = getXiuSearchConfigList();
        Map<String, String> confMap = new HashMap<String, String>();
        if (null != xiuSearchConfigList && xiuSearchConfigList.size() > 0) {
            for (XiuSearchConfigModel e : xiuSearchConfigList) {
                if ("1".equals(e.getConfigStatus())) {
                    confMap.put(e.getConfigKey(), e.getConfigValue().trim());
                }
            }
        }
        
        log.warn("【XiuSearchConfigService】 获取搜索配置 ，共获取到 " + confMap.size() + " 条有效的搜索配置，耗时: " + (System.currentTimeMillis() - time)/1000 + " s.");
        
        return confMap;
    }


    public void setXiuSearchConfigDAO(XiuSearchConfigDAO xiuSearchConfigDAO) {
        this.xiuSearchConfigDAO = xiuSearchConfigDAO;
    }
    


}
