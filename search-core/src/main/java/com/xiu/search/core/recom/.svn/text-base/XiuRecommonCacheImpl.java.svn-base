package com.xiu.search.core.recom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.xiu.search.core.config.XiuSearchProperty;
import com.xiu.search.core.model.BrandBusinessOptModel;
import com.xiu.search.core.model.CatalogBusinessOptModel;
import com.xiu.search.core.service.BrandBusinessOptService;
import com.xiu.search.core.service.CatalogBusinessOptService;
import com.xiu.search.core.service.MemCachedService;
import com.xiu.search.core.solr.enumeration.MktTypeEnum;
import com.xiu.search.core.util.CommonUtil;
import com.xiu.search.core.util.Constants;

public class XiuRecommonCacheImpl extends XiuRecommonCache {

	private final static Logger log = Logger.getLogger(XiuRecommonCacheImpl.class);

	private boolean isRunning = false;

	/**
	 * key: brandID_mktType
	 */
	private Map<String, BrandBusinessOptModel> brandMap = null;

	/**
	 * key: catalogID_mktType
	 */
	private Map<String, CatalogBusinessOptModel> catsMap = null;

	private MemCachedService memCachedService;
	
	private BrandBusinessOptService brandBusinessOptService;
	
	private CatalogBusinessOptService catalogBusinessOptService;

	@Override
	protected void init() {

		this.reload();

		this.reloadTimer();
	}

	@Override
	public BrandBusinessOptModel loadBrandRecommenedByIdAndMkt(Long brandID,
			MktTypeEnum mktType) {
		if (brandID == null || brandID.intValue() <= 0) {
			log.warn(Constants.MCACHE_LOG_HEADER+"从本地缓存中加载品牌下推荐数据时，mktType[" + brandID + "]无效");
			return new BrandBusinessOptModel();
		}
		String keyPrefix = brandID.toString().concat("_");
		switch (mktType) {
		case XIU:
			keyPrefix = keyPrefix.concat(String.valueOf(mktType.getType()));
			break;
		case EBAY:
			keyPrefix = keyPrefix.concat(String.valueOf(mktType.getType() + 1));
			break;
		default:
			log.warn(Constants.MCACHE_LOG_HEADER+"从本地缓存中加载品牌下推荐数据时，mktType[" + mktType + "]无效");
			return new BrandBusinessOptModel();
		}
		Map<String, BrandBusinessOptModel> localBMap=brandMap;
		return localBMap.get(keyPrefix);
	}

	@Override
	public CatalogBusinessOptModel loadCatlogRecommenedByIdAndMkt(Long catID,
			MktTypeEnum mktType) {
		if (catID == null || catID.intValue() <= 0) {
			log.warn(Constants.MCACHE_LOG_HEADER+"从本地缓存中加载品牌下推荐数据时，mktType[" + catID + "]无效");
			return new CatalogBusinessOptModel();
		}
		String keyPrefix = catID.toString().concat("_");
		switch (mktType) {
		case XIU:
			keyPrefix = keyPrefix.concat(String.valueOf(mktType.getType()));
			break;
		case EBAY:
			keyPrefix = keyPrefix.concat(String.valueOf(mktType.getType() + 1));
			break;
		default:
			log.warn(Constants.MCACHE_LOG_HEADER+"从本地缓存中加载分类下推荐数据时，mktType[" + mktType + "]无效");
			return new CatalogBusinessOptModel();
		}
		Map<String, CatalogBusinessOptModel> localCMap=catsMap;
		return localCMap.get(keyPrefix);
	}

	@Override
	protected void reload() {
		log.warn(Constants.MCACHE_LOG_HEADER+"【XiuRecommonCacheImpl】 开始  读取运营推荐数据 ");
		if (isRunning) {
			log.warn(Constants.MCACHE_LOG_HEADER+"读取运营推荐数据 的操作正在运行");
			return;
		}
		isRunning = true;

		if (CommonUtil.enabledLoadFromMemcache()) {
			log.warn(Constants.MCACHE_LOG_HEADER+"从Memcache中读取数据");
			this.loadFromMemcache();
		} else {
			log.warn(Constants.MCACHE_LOG_HEADER+"从数据库中读取数据");
			this.loadFromDB();
		}
		
		log.warn(Constants.MCACHE_LOG_HEADER+"有推荐数据的品牌记录条数为："+(brandMap==null?"0":brandMap.size()));
		log.warn(Constants.MCACHE_LOG_HEADER+"有推荐数据的分类记录条数为："+(catsMap==null?"0":catsMap.size()));
		
		log.warn(Constants.MCACHE_LOG_HEADER+"【XiuRecommonCacheImpl】完成  读取运营推荐数据 ");
	}

	/**
	 * 从DB中加载推荐数据
	 */
	private void loadFromDB() {
		long startTime=System.nanoTime();
		//加载品牌推荐
		brandMap = brandBusinessOptService.getAllTopShow();
		
		//加载分类推荐
		catsMap = catalogBusinessOptService.getAllTopShow();
		log.warn(Constants.MCACHE_LOG_HEADER+"【XiuRecommonCacheImpl.loadFromDB()】共耗时： "+((System.nanoTime()-startTime)/1000000.0f)+"毫秒");
	}

	/**
	 * 从Memcache中加载推荐数据
	 */
	private void loadFromMemcache() {
		
		long startTime=System.nanoTime();
		
		Map<String, BrandBusinessOptModel> brandMap1 = new HashMap<String, BrandBusinessOptModel>();

		Map<String, CatalogBusinessOptModel> catsMap1 = new HashMap<String, CatalogBusinessOptModel>();

		Map<String, Map<String, String>> slabs = memCachedService.getStatsItems();
		if (slabs == null) {
			isRunning = false;
			return;
		}
		
		Set<String> bSet=new HashSet<String>();
		Set<String> cSet=new HashSet<String>();
		
		Iterator<String> itemsItr = slabs.keySet().iterator();
		String[] itemAtt = null;
		String serverInfo = null, itemName = null, key = null;
		Map<String, String> itemNames = null, items = null;
		Iterator<String> itemNameItr = null, itr = null, keyItr = null;
		Map<String, Map<String, String>> chcheDump = null;
		while (itemsItr.hasNext()) {
			serverInfo = itemsItr.next();
			itemNames = slabs.get(serverInfo);
			itemNameItr = itemNames.keySet().iterator();
			while (itemNameItr.hasNext()) {
				itemName = itemNameItr.next();
				itemAtt = itemName.split(":");
				if (itemAtt[2].startsWith("number")) {
					chcheDump = memCachedService.getStatsCacheDump(Integer.parseInt(itemAtt[1]), 0);
					itr = chcheDump.keySet().iterator();
					while (itr.hasNext()) {
						serverInfo = itr.next().toString();
						items = chcheDump.get(serverInfo);
						keyItr = items.keySet().iterator();
						while (keyItr.hasNext()) {
							key = keyItr.next();
							if (key.startsWith(Constants.MEMCACHE_PRE_BRAND_REC_GOODS)) {// 品牌下推荐商品
								bSet.add(key);
								//this.putBrandsValue(key, brandMap1);
							} else if (key.startsWith(Constants.MEMCACHE_PRE_BRAND_REC_CAT)) {// 品牌下推荐分类
								bSet.add(key);
								//this.putBrandsValue(key, brandMap1);
							} else if (key.startsWith(Constants.MEMCACHE_PRE_CAT_REC_GOODS)) {// 分类下推荐商品
								cSet.add(key);
								//this.putCatsValue(key, catsMap1);
							} else if (key.startsWith(Constants.MEMCACHE_PRE_CAT_REC_BRAND)) {// 分类下推荐品牌
								cSet.add(key);
								//this.putCatsValue(key, catsMap1);
							}
						}
					}
				}
			}
		}
		
		this.putBrandsBySet(bSet,brandMap1);
		brandMap = brandMap1;
		
		this.putCatsBySet(cSet,catsMap1);
		catsMap = catsMap1;
		
		log.warn(Constants.MCACHE_LOG_HEADER+"【XiuRecommonCacheImpl.loadFromMemcache()】共耗时： "+((System.nanoTime()-startTime)/1000000.0f)+"毫秒");
		
		isRunning = false;

		itemsItr = null;
		itemAtt = null;
		itemNames = null;
		items = null;
		itemNameItr = null;
		itr = null;
		keyItr = null;
		chcheDump = null;
		bSet.clear();
		bSet=null;
		cSet.clear();
		cSet=null;
		catsMap1=null;
		brandMap1=null;
	}

	@SuppressWarnings("unchecked")
	private void putBrandsBySet(Set<String> kSet,Map<String, BrandBusinessOptModel> bMap){
		if(kSet==null || kSet.size()==0){
			return;
		}
		Map<String, Object> valMap=(Map<String, Object>)memCachedService.getMulti(new ArrayList<String>(kSet));
		if(valMap==null || valMap.size()==0){
			return;
		}
		Iterator<Entry<String, Object>> itr=valMap.entrySet().iterator();
		Entry<String, Object> entry=null;
		while (itr.hasNext()) {
			entry = itr.next();
			this.putBrandsValue(entry.getKey(), entry.getValue(), bMap);
		}
		itr=null;
		valMap=null;
	}
	
	@SuppressWarnings("unchecked")
	private void putBrandsValue(String key,Object value,Map<String, BrandBusinessOptModel> bMap) {
		if (value == null) {
			return;
		}
		String localKey="";
		String[] pts=key.split("_");//前缀_mktType_brandID
		if(pts==null || pts.length!=5){
			log.warn(Constants.MCACHE_LOG_HEADER+"从Memcache缓存中刷新数据时，key["+key+"]无效");
			return;
		}

		localKey=pts[4].concat("_").concat(pts[3]);
		
		if (!bMap.containsKey(localKey)) {
			bMap.put(localKey, new BrandBusinessOptModel(Integer.parseInt(pts[4])));
		}
		if (value instanceof List) {
			bMap.get(localKey).setBrandGoodsItemTopShow((List<String>) value);
		} else if (value instanceof LinkedHashMap) {
			bMap.get(localKey).setBrandCatalogIdTopShow((LinkedHashMap<Integer, Integer>) value);
		}
	}

	@SuppressWarnings("unchecked")
	private void putCatsBySet(Set<String> kSet,Map<String, CatalogBusinessOptModel> cMap){
		if(kSet==null || kSet.size()==0){
			return;
		}
		Map<String, Object> valMap=(Map<String, Object>)memCachedService.getMulti(new ArrayList<String>(kSet));
		if(valMap==null || valMap.size()==0){
			return;
		}
		Iterator<Entry<String, Object>> itr=valMap.entrySet().iterator();
		Entry<String, Object> entry=null;
		while (itr.hasNext()) {
			entry = itr.next();
			this.putCatsValue(entry.getKey(), entry.getValue(), cMap);
		}
		itr=null;
		valMap=null;
	}
	
	@SuppressWarnings("unchecked")
	private void putCatsValue(String key,Object value,Map<String, CatalogBusinessOptModel> cMap) {
		if (value == null) {
			return;
		}
		String localKey="";
		String[] pts=key.split("_");//前缀_mktType_catID
		if(pts==null || pts.length!=5){
			log.warn(Constants.MCACHE_LOG_HEADER+"从Memcache缓存中刷新数据时，key["+key+"]无效");
			return;
		}
//		if("19380".equals(pts[4])){
//			System.out.println("===========19380=========");
//		}
		localKey=pts[4].concat("_").concat(pts[3]);
		if (!cMap.containsKey(localKey)) {
			cMap.put(localKey, new CatalogBusinessOptModel(Integer.parseInt(pts[4])));
		}
		if (value instanceof List) {
			cMap.get(localKey).setGoodsItemTopShow((List<String>) value);
		} else if (value instanceof LinkedHashMap) {
			cMap.get(localKey).setBrandGoodsItemTopShow((LinkedHashMap<Integer, Integer>) value);
		}
	}

	/**
	 * 定时器
	 */
	private void reloadTimer() {
		Timer t = new Timer(true);
		TimerTask tk = new TimerTask() {
			@Override
			public void run() {
				reload();
			}
		};
		// N分钟之后 ，每隔N分钟执行一次
		t.scheduleAtFixedRate(tk, XiuSearchProperty.getInstance()
				.getCatalogRefreshfrequency(), XiuSearchProperty.getInstance()
				.getCatalogRefreshfrequency());
	}

	public void setMemCachedService(MemCachedService memCachedService) {
		this.memCachedService = memCachedService;
	}

	public void setBrandBusinessOptService(
			BrandBusinessOptService brandBusinessOptService) {
		this.brandBusinessOptService = brandBusinessOptService;
	}

	public void setCatalogBusinessOptService(
			CatalogBusinessOptService catalogBusinessOptService) {
		this.catalogBusinessOptService = catalogBusinessOptService;
	}
	
}
