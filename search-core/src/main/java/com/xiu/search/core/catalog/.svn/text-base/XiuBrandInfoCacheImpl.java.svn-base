package com.xiu.search.core.catalog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.xiu.search.core.service.GoodsSolrService;
import com.xiu.search.core.util.Constants;
import com.xiu.search.core.util.XiuSearchStringUtils;
import com.xiu.search.dao.XDataBrandDAO;
import com.xiu.search.dao.cache.CacheManage;
import com.xiu.search.dao.cache.CacheTypeEnum;
import com.xiu.search.dao.model.XDataBrand;

public class XiuBrandInfoCacheImpl extends XiuBrandInfoCache{

	private static Logger log = Logger.getLogger(XiuBrandInfoCacheImpl.class);
	
	private Timer timer;
	
	private static HashMap<Long, BrandModel> cache = new HashMap<Long, BrandModel>();
	
	@Override
	protected void init() {
		log.info("【XiuBrandInfoCacheImpl】 系统启动时，开始加载品牌缓存数据: ");
		
		reloadAllBrand();
		if(timer != null){
			timer.cancel();
		}
		timer = new Timer(true);
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				log.info("【XiuBrandInfoCacheImpl】 定时器reloadAllBrandTimer，加载品牌缓存数据 ,时间点："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				reloadAllBrand();
			}
		};
		
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, (int)(30.0d * Math.random()));
		c.set(Calendar.MILLISECOND, 0);
		c.add(Calendar.DAY_OF_MONTH, 1);
		Date date = c.getTime();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("Start loading brand data info =========== " + sf.format(date));
		
		timer.schedule(task, date, 24 * 60 * 60 * 1000L);
		
		
		/**
		 * 品牌直达相关
		 */
		this.reloadBrandToThrough();
		this.reloadBrandToThroughTimer();
		
		log.info("【XiuBrandInfoCacheImpl】 系统启动时，完成加载品牌缓存数据");
		
	}
	
	
	private void reloadAllBrand(){
		List<XDataBrand> datas = xDataBrandDAO.selectAllBrandInfo();
		if(datas == null
				|| datas.size() == 0)
			return;
		BrandModel bm;
		HashMap<Long, BrandModel> map = new HashMap<Long, BrandModel>(datas.size());
		for (XDataBrand m : datas) {
			bm = new BrandModel();
			bm.setBrandId(m.getBrandId());
			bm.setBrandName(m.getBrandName());
			bm.setEnName(m.getEnName());
			bm.setMainName(m.getMainName());
			if(StringUtils.isNotBlank(bm.getBrandName())){
				bm.setPyName(XiuSearchStringUtils.getPingYin(m.getBrandName()).toUpperCase());
				bm.setPyFirstLetter(XiuSearchStringUtils.getPingYin(m.getBrandName()).toUpperCase());
			}
			map.put(bm.getBrandId(), bm);
		}
		log.info("【XiuBrandInfoCacheImpl】 加载品牌缓存数据的记录条数为: "+map.size());
		cache = map;
	}
	
	/**
	 * @Description: 品牌直达用到的数据源
	 *      
	 * @return void
	 */
	private void reloadBrandToThrough(){
		List<XDataBrand> brandList = xDataBrandDAO.selectAllByShowFlag1AndHasGoods();
		log.info("【XiuBrandInfoCacheImpl】 加载品牌直达功能对应的品牌缓存数据的记录条数为: "+(null != brandList?brandList.size():0));
		if(null != brandList){
			CacheManage.remove(Constants.ALL_BRAND_CACHE_KEY, CacheTypeEnum.BRAND_INFO);
			CacheManage.put(Constants.ALL_BRAND_CACHE_KEY, brandList, CacheTypeEnum.BRAND_INFO);
		}
	}
	
	/**
	 * 检出有商品的品牌
	 * @param srcBrandList
	 * @return
	 */
//	private List<XDataBrand> checkBrand(List<XDataBrand> allBrandList){
//		List<XDataBrand> brandList = null;
//		if(allBrandList != null && !allBrandList.isEmpty()){
//			brandList = new ArrayList<XDataBrand>();
//			SearchFatParams params = new SearchFatParams(0);
//			for(XDataBrand b : allBrandList){
//				params.setBrandId(Integer.parseInt(b.getBrandId().toString()));
//				try{
//					SearchResult<GoodsSolrModel> result = goodsSolrService.findSearchXiuSolr(params, false, null,null);
//					if(result != null && (int) result.getTotalHits() > 0){
//						brandList.add(b);
//					}
//				}catch(Exception ex){
//					ex.printStackTrace();
//					//return null; 2014-07-22 12:07
//				}
//			}
//		}
//		return brandList;
//	}
	
	/**
	 * 定时器 品牌直达用到的
	 */
	private void reloadBrandToThroughTimer(){
		Timer t=new Timer(true);
		TimerTask tk=new TimerTask() {
			@Override
			public void run() {
				log.info("【XiuBrandInfoCacheImpl】 定时器reloadBrandToThroughTimer，加载品牌直达功能对应的品牌缓存数据 ,时间点："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				reloadBrandToThrough();
			}
		};
		//4小时之后 ，每隔4小时执行一次
		t.scheduleAtFixedRate(tk, 4*60*60*1000, 4*60*60*1000);
	}
	

	@Override
	public BrandModel getBrandById(Long brandId) {
		if(cache != null)
			return cache.get(brandId);
		return null;
	}
	
	private XDataBrandDAO xDataBrandDAO;
	
	@Autowired
	private GoodsSolrService goodsSolrService;

	public void setxDataBrandDAO(XDataBrandDAO xDataBrandDAO) {
		this.xDataBrandDAO = xDataBrandDAO;
	}

	public void setGoodsSolrService(GoodsSolrService goodsSolrService) {
		this.goodsSolrService = goodsSolrService;
	}
	
	
	
}
