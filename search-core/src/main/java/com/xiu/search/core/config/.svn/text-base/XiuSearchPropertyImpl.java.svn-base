package com.xiu.search.core.config;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.xiu.search.dao.XiuSearchConfigDAO;
import com.xiu.search.dao.config.XiuSearchConfig;
import com.xiu.search.dao.model.XiuSearchConfigModel;

public class XiuSearchPropertyImpl extends XiuSearchProperty {

	private final static Logger log = Logger
			.getLogger(XiuSearchPropertyImpl.class);

	private Map<String, String> configMap = new ConcurrentHashMap<String, String>();

	private XiuSearchConfigDAO xiuSearchConfigDAO;

	@Override
	protected void init() {
		this.reloadConfigData();
		this.reloadTimer();
	}
	
	@Override
	public void reloadConfigData() {
		List<XiuSearchConfigModel> xiuSearchConfigList = null;
		log.info("【XiuSearchConfigImpl】 开始从数据库中读取Search配置: ");
		System.out.println("【XiuSearchConfigImpl】 开始从数据库中读取Search配置: ");
		try {
			xiuSearchConfigList = xiuSearchConfigDAO.selectAllXiuSearchConfig();
		} catch (SQLException e) {
			log.error("【XiuSearchConfigImpl】 从数据库中查询搜索配置列表出错: " + e);
			e.printStackTrace();
			return;
		}
		log.info("【XiuSearchConfigImpl】读取完成，共从数据库读取到["+(xiuSearchConfigList == null ? 0 : xiuSearchConfigList.size())+"]条配置信息! ");
		System.out.println("【XiuSearchConfigImpl】读取完成，共从数据库读取到["+(xiuSearchConfigList == null ? 0 : xiuSearchConfigList.size())+"]条配置信息! ");
		if (xiuSearchConfigList == null)
			return;
		Map<String, String> confMap = new HashMap<String, String>();
		if (null != xiuSearchConfigList && xiuSearchConfigList.size() > 0) {
			for (XiuSearchConfigModel e : xiuSearchConfigList) {
				if ("1".equals(e.getConfigStatus())) {
					confMap.put(e.getConfigKey(), e.getConfigValue().trim());
				}
			}
		}
		configMap.clear();
		configMap.putAll(confMap);
	}

	/**
	 * 定时器
	 */
	private void reloadTimer(){
		Timer t=new Timer(true);
		TimerTask tk=new TimerTask() {
			@Override
			public void run() {
				reloadConfigData();
			}
		};
		//N分钟之后 ，每隔N分钟执行一次,在此设置为20分钟(30分钟后 每隔20分钟)
		t.scheduleAtFixedRate(tk, 30*60*1000, 20*60*1000);
	}
	
	
	@Override
	public String getValueByKey(String key) {
		if (configMap.containsKey(key))
			return configMap.get(key);
		return null;
	}

	@Override
	public boolean getCatalogRemoldEnable() {
		return "true".equalsIgnoreCase(this
				.getValueByKey(XiuSearchConfig.CATALOG_ENABLED));
	}

	@Override
	public boolean getOldCatalogFilterEnable() {
		return "true".equalsIgnoreCase(this
				.getValueByKey(XiuSearchConfig.FILTER_OLD_CATALOG));
	}
	
	@Override
	public boolean enabledCatalogByDisplayIS0() {
		return "true".equalsIgnoreCase(this
				.getValueByKey(XiuSearchConfig.ENABLE_SHOW_DISPLAY0_CATALOG));
	}

	@Override
	public int getOldCatalogIdMaxLength() {
		String maxLth = this.getValueByKey(XiuSearchConfig.OLD_CATALOG_LENGTH);
		if (StringUtils.isBlank(maxLth)
				|| !StringUtils.isNumeric(maxLth)) {
			return 8;
		}
		return Integer.parseInt(maxLth);
	}
	
	@Override
	public boolean enableBuildSkuSize() {
		return "true".equalsIgnoreCase(this.getValueByKey(XiuSearchConfig.SKU_SIZE_SHOW_BUILD));
	}

	@Override
	public int showSkuSizeCount() {
		String maxLth = this.getValueByKey(XiuSearchConfig.SKU_SIZE_SHOW_COUNT);
		if (StringUtils.isBlank(maxLth) || !StringUtils.isNumeric(maxLth)) {
			return 8;
		}
		return Integer.parseInt(maxLth);
	}
	
	@Override
	public boolean enableSkuSizeSortByQty() {
		return "true".equalsIgnoreCase(this.getValueByKey(XiuSearchConfig.SKU_SIZE_SHOW_QTYSORT));
	}

	@Override
	public String getBFDPassword() {
		return this.getValueByKey(XiuSearchConfig.BFD_CIIENT_NAME);
	}
	
	@Override
	public String hugoKeywords() {
		return this.getValueByKey(XiuSearchConfig.HUGO_FILTER_WORDS);
	}

	@Override
	public String hugoBrandIds() {
		return this.getValueByKey(XiuSearchConfig.HUGO_FILTER_BRAND_IDS);
	}

	@Override
	public long getCatalogRefreshfrequency() {
		String freq = this.getValueByKey(XiuSearchConfig.CATALOG_REFRESH_FREQUENCY);
		if (StringUtils.isBlank(freq)
				|| !StringUtils.isNumeric(freq)) {
			return 10*60*1000;
		}
		return Long.parseLong(freq);
	}
	
	@Override
	public long getAttrRefreshfrequency() {
		String freq = this.getValueByKey(XiuSearchConfig.ATTR_REFRESH_FREQUENCY);
		if (StringUtils.isBlank(freq)
				|| !StringUtils.isNumeric(freq)) {
			return 11*60*1000;
		}
		return Long.parseLong(freq);
	}
	
	@Override
	public boolean enabledLoadFromMemcache() {
		return "true".equalsIgnoreCase(this.getValueByKey(XiuSearchConfig.ENABLED_LOAD_FROM_MEMCACHE));
	}

	public void setXiuSearchConfigDAO(XiuSearchConfigDAO xiuSearchConfigDAO) {
        this.xiuSearchConfigDAO = xiuSearchConfigDAO;
    }

	/*@Override
	public String yangMiKeyWords() {
		return this.getValueByKey(XiuSearchConfig.YANGMI_FILTER_WORDS);
	}*/
	
}
