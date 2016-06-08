package com.xiu.search.web.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.xiu.search.core.service.CatalogService;
import com.xiu.search.dao.cache.CacheManage;
import com.xiu.search.dao.cache.CacheTypeEnum;
import com.xiu.search.dao.cache.CumulativeStats;
import com.xiu.search.dao.config.XiuSearchConfig;
import com.xiu.search.web.config.XiuWebConstant;
import com.xiu.search.web.util.LoginUtils;

/**
 * config控制台
 * @author Leon
 *
 */
@Controller
public class ConfigController extends BaseController{

	private static final String SERVER_PORT = "log.port";
	
	private static final String OPERATION_PARAM = "opt";
	private static final String CLEAR_CACHE_TYPE_PARAM = "cacheType";
	
	@RequestMapping(value="/config-info")
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse res ,Model model){
		if(!isSearchValidUser(req) && !super.isDevelopStatus(req)){
			return new ModelAndView("forward:/error/404.htm");
		}
		ConfigBo result = new ConfigBo();
		
		String opt = req.getParameter(OPERATION_PARAM);
		ParamEnum pe = ParamEnum.valueOfDefaultNull(opt);
		if(pe != null){
			// 操作状态
			exeOperation(result,req, res);
			exeConfigInfo(result, req, res);
			return new ModelAndView(new RedirectView("/config-info.htm", false, true, false));
		}
		exeConfigInfo(result, req, res);

		// 查询运营分类查询语句
		String cataGroupId = req.getParameter("cataGroupId");
		if (StringUtils.isNotEmpty(cataGroupId)){
	        model.addAttribute("cataGroupId", cataGroupId);
	        model.addAttribute("cataGroupQueryString", this.queryCataGroupQueryStringById(cataGroupId));
		}
		
		// 出参
		model.addAllAttributes(new BeanMap(result));
		return new ModelAndView("config-info");
	}
	private void exeOperation(ConfigBo result,HttpServletRequest req, HttpServletResponse res){
		String opt = req.getParameter(OPERATION_PARAM);
		ParamEnum pe = ParamEnum.valueOfDefaultNull(opt);
		if(null == pe)
			pe = ParamEnum.DISPLAY_STATUS;
		if(ParamEnum.DISPLAY_STATUS.equals(pe)){
			
		}else if(ParamEnum.CLEAR_CACHE.equals(pe)){
			String cacheType = req.getParameter(CLEAR_CACHE_TYPE_PARAM);
			if(StringUtils.isNotBlank(cacheType)){
				CacheTypeEnum cacheTypeEnum = null;
				try {
					cacheTypeEnum= CacheTypeEnum.valueOf(cacheType);
				} catch (Exception e) {
				}
				if(null != cacheTypeEnum){
					CacheManage.clear(cacheTypeEnum);
				}
			}
		}
	}
	
	private void exeConfigInfo(ConfigBo result,HttpServletRequest req, HttpServletResponse res){
		String serverPort;
		boolean developFlag = XiuWebConstant.isDevelop();
		result.setDevelopFlag(developFlag);
		if(developFlag){
			serverPort = "本机开发";
		} else {
			serverPort = System.getProperty(SERVER_PORT,"未知");
		}
		result.setHostPort(serverPort);
		InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
			result.setHostAddress(addr.getHostAddress().toString());
			result.setHostName(addr.getHostName().toString());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		int _size,totalSize=0;
		CumulativeStats _stats;
		Map cacheInfoItemMap,cacheInfoAllMap = new HashMap();
		for (CacheTypeEnum type : CacheTypeEnum.values()) {
			_size = CacheManage.size(type);
			_stats = CacheManage.getStats(type);
			cacheInfoItemMap = new HashMap();
			cacheInfoItemMap.put("name", type.name());
			cacheInfoItemMap.put("size", _size);
			cacheInfoItemMap.put("stats", _stats);
			cacheInfoItemMap.put("state", type.getState());
			cacheInfoAllMap.put(type.name(), cacheInfoItemMap);
			totalSize += _size;
		}
		result.setCacheInfoMap(cacheInfoAllMap);
		result.setCacheSize(totalSize);
//		result.setCacheSize(CacheManage.size());
//		result.setValidCacheSize(CacheManage.validSize());
	}
	

    /**
     * 从缓存中查询运营分类id对应的查询语句
     * @param cataGroupId 运营分类id
     * @return 查询语句
     */
	private String queryCataGroupQueryStringById(String cataGroupId) {
	    String catalogQueryStr = "";
	    return catalogQueryStr;
	}
	
    @Autowired
    private  CatalogService catalogService;
    
	
	private static boolean isSearchValidUser(HttpServletRequest request){
		String validUserId = XiuSearchConfig.getPropertieValue(XiuSearchConfig.VALID_ADMIN_USER_ID);
		if(StringUtils.isBlank(validUserId))
			return false;
		validUserId = "," + validUserId + ",";
		String userId = LoginUtils.getUserId(request);
		return StringUtils.isNotBlank(userId)
				&& validUserId.indexOf(","+userId+",")>=0
				&& LoginUtils.checkDataIntegrityOfUser(request);
	}
	
	private static enum ParamEnum{
		/**
		 * 显示服务器状态
		 */
		DISPLAY_STATUS,
		/**
		 * 清空缓存
		 */
		CLEAR_CACHE,
		;
		
		public static ParamEnum valueOfDefaultNull(String name){
			try {
				return ParamEnum.valueOf(name);
			} catch (Exception e) {
			}
			return null;
		}
	}
	
	public class ConfigBo{
		/**
		 * 是否开发状态
		 */
		private boolean developFlag;
		/**
		 * host端口号
		 */
		private String hostPort;
		/**
		 * 本地缓存总大小
		 */
		private int cacheSize;
		/**
		 * 本地有效缓存大小
		 */
		private int validCacheSize;
		/**
		 * 本地IP地址
		 */
		private String hostAddress;
		/**
		 * 本地计算机名
		 */
		private String hostName;
		
		/**
		 * 缓存类型
		 */
		private CacheTypeEnum[] cacheTypes;
		
		/**
		 * 缓存信息
		 */
		private Map cacheInfoMap; 
		

		public Map getCacheInfoMap() {
			return cacheInfoMap;
		}

		public void setCacheInfoMap(Map cacheInfoMap) {
			this.cacheInfoMap = cacheInfoMap;
		}

		public boolean isDevelopFlag() {
			return developFlag;
		}
		
		public int getValidCacheSize() {
			return validCacheSize;
		}

		public void setValidCacheSize(int validCacheSize) {
			this.validCacheSize = validCacheSize;
		}

		public void setDevelopFlag(boolean developFlag) {
			this.developFlag = developFlag;
		}
		public String getHostPort() {
			return hostPort;
		}
		public void setHostPort(String serverPort) {
			this.hostPort = serverPort;
		}
		public int getCacheSize() {
			return cacheSize;
		}
		public void setCacheSize(int cacheSize) {
			this.cacheSize = cacheSize;
		}
		public String getHostAddress() {
			return hostAddress;
		}
		public void setHostAddress(String hostAddress) {
			this.hostAddress = hostAddress;
		}
		public String getHostName() {
			return hostName;
		}
		public void setHostName(String hostName) {
			this.hostName = hostName;
		}

		public CacheTypeEnum[] getCacheTypes() {
			return CacheTypeEnum.values();
		}
		
	}
	
}
