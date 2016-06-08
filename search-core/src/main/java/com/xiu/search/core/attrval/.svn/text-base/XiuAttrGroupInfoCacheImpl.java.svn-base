package com.xiu.search.core.attrval;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.xiu.search.dao.XDataAttrDescDAO;

/** 
 * @Title: XiuAttrGroupInfoCacheImpl.java 
 *
 * @Package com.xiu.search.core.attrval 
 *
 * @Description:  属性项名称缓存 
 *
 * @author lvshuding 
 *
 * @date 2014年7月21日 上午11:19:33 
 *
 * @version V1.0 
 */
public class XiuAttrGroupInfoCacheImpl extends XiuAttrGroupInfoCache {

	private final static Logger log = Logger.getLogger(XiuAttrGroupInfoCacheImpl.class);
	
	private static Map<String, String> cache = new HashMap<String, String>();
	
	@Override
	protected void init() {
		log.info("【XiuAttrGroupInfoCacheImpl】 系统启动时，开始加载属性项名称缓存数据: ");
		this.reloadAllAttrGroupName();
		this.reloadAttrGrouNameTimer();
		log.info("【XiuAttrGroupInfoCacheImpl】 系统启动时，完成加载属性项名称缓存数据");
	}

	@Override
	protected String getAttrGrouNameByID(String id) {
		Map<String, String> tmpMap=cache;
		return tmpMap.get(id);
	}
	
	@Override
	protected int getCacheSize() {
		Map<String, String> tmpMap=cache;
		return tmpMap.size();
	}

	/**
	 * @Description: 从数据库中加载所有属性项名称缓存   
	 * @return void
	 */
	private void reloadAllAttrGroupName(){
		Map<String, String> tmpMap=xDataAttrDescDAO.getAllAttrGroupName();
		if(tmpMap==null){
			log.error("=========================reloadAttrGrouName return NULL========================"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		}else {
			log.info("=========================reloadAttrGrouName return size:"+tmpMap.size()+"========================"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		}
		cache = tmpMap;
	}
	
	/**
	 * 定时器 
	 */
	private void reloadAttrGrouNameTimer(){
		Timer t=new Timer(true);
		TimerTask tk=new TimerTask() {
			@Override
			public void run() {
				reloadAllAttrGroupName();
				log.info("=========================reloadAttrGrouNameTimer========================"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			}
		};
		//4小时之后 ，每隔4小时执行一次
		t.scheduleAtFixedRate(tk, 4*60*60*1000, 4*60*60*1000);
	}

	private XDataAttrDescDAO xDataAttrDescDAO;

	/**
	 * @param xDataAttrDescDAO the xDataAttrDescDAO to set
	 */
	public void setxDataAttrDescDAO(XDataAttrDescDAO xDataAttrDescDAO) {
		this.xDataAttrDescDAO = xDataAttrDescDAO;
	}
	
	
}
