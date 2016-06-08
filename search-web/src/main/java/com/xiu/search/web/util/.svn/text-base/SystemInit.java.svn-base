package com.xiu.search.web.util;

import java.util.Map;

import javax.servlet.ServletException;

/**
 * 初始化参数参数
 * @author Lipsion
 *
 */
public class SystemInit{

	private Map<String,String> systemInitParam;
	
	public Map<String, String> getSystemInitParam() {
		return systemInitParam;
	}

	public void setSystemInitParam(Map<String, String> systemInitParam) {
		this.systemInitParam = systemInitParam;
	}

	/**
	 * Initialization System Param
	 * Log4j中变量初始化用
	 */
	public void init() throws ServletException {
		for (String key : systemInitParam.keySet()) {
			System.setProperty(key, systemInitParam.get(key));
		}
	}
}
