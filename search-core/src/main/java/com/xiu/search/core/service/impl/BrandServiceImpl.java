package com.xiu.search.core.service.impl;

import java.util.ArrayList;
import java.util.List;



import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.search.core.service.BrandService;
import com.xiu.search.core.service.GoodsSolrService;
import com.xiu.search.core.solr.model.GoodsSolrModel;
import com.xiu.search.core.solr.params.SearchFatParams;
import com.xiu.search.core.util.Constants;
import com.xiu.search.dao.XDataBrandDAO;
import com.xiu.search.dao.cache.CacheManage;
import com.xiu.search.dao.cache.CacheTypeEnum;
import com.xiu.search.dao.model.XDataBrand;
import com.xiu.search.solrj.service.SearchResult;

@Service("brandService")
public class BrandServiceImpl implements BrandService {

	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	private XDataBrandDAO xDataBrandDAO;
	@Autowired
	private GoodsSolrService goodsSolrService;

	@Override
	public String getBrandName(Long brandId) {
		XDataBrand brandObject = this.getBrandInfo(brandId);

		String brandName = null;
		if (null != brandObject && null != brandObject.getMainName()) {
			brandName = brandObject.getMainName();
		} else if (null != brandObject && null != brandObject.getBrandName()) {
			brandName = brandObject.getBrandName();
		} else if (null != brandObject && null != brandObject.getEnName()) {
			brandName = brandObject.getEnName();
		} else {
			brandName = "";
		}
		return brandName;
	}

	public XDataBrand getBrandInfo(Long brandId) {
		XDataBrand brandObject = null;
		try {
//			String bidStr = Long.toString(brandId);
//			if (CacheManage.containsKey(bidStr, CacheTypeEnum.BRAND_INFO)) {
			brandObject = (XDataBrand) CacheManage.get(Long.toString(brandId),CacheTypeEnum.BRAND_INFO);
//			}
			if (null == brandObject) {
				brandObject = xDataBrandDAO.selectByPrimaryKey(brandId);
				if(null == brandObject)
					brandObject = XDataBrand.EMPTY_OBJECT;
				CacheManage.put(Long.toString(brandId), brandObject, CacheTypeEnum.BRAND_INFO);
			}
			if(null != brandObject 
					&& null != brandObject.getBrandId()){
				return brandObject;
			}
		} catch (Exception e) {
			logger.error("查询品牌信息出错,品牌ID:", e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<XDataBrand> getAllBrandInfo() {
		List<XDataBrand> brandList = null;
		try {
			brandList = (List<XDataBrand>) CacheManage.get(Constants.ALL_BRAND_CACHE_KEY,CacheTypeEnum.BRAND_INFO);
			
			/*
			 * 2014-05-22 10:48    注释原因：客户端负责用数据，不负责数据提取
			 */
//			if (null == brandList) {
//				List<XDataBrand> allBrand = xDataBrandDAO.selectAllBrandInfo();
//				brandList = checkBrand(allBrand);
//				if(null != brandList)
//					CacheManage.put(Constants.ALL_BRAND_CACHE_KEY, brandList, CacheTypeEnum.BRAND_INFO);
//			}
			
			
			if(null != brandList){
				return brandList;
			}
		} catch (Exception e) {
			logger.error("查询所有品牌信息出错", e);
		}
		return null;
	}
	
	/**
	 * 检出有商品的品牌
	 * @param srcBrandList
	 * @return
	 */
	private List<XDataBrand> checkBrand(List<XDataBrand> allBrandList){
		List<XDataBrand> brandList = null;
		if(allBrandList != null && !allBrandList.isEmpty()){
			brandList = new ArrayList<XDataBrand>();
			SearchFatParams params = new SearchFatParams(0);
			for(XDataBrand b : allBrandList){
				params.setBrandId(Integer.parseInt(b.getBrandId().toString()));
				try{
					SearchResult<GoodsSolrModel> result = goodsSolrService.findSearchXiuSolr(params, false, null,null);
					if(result != null && (int) result.getTotalHits() > 0){
						brandList.add(b);
					}
				}catch(Exception ex){
					logger.error("goodsSolrService.findSearchXiuSolr检出有商品的品牌出现异常", ex);
					return null;
				}
			}
		}
		return brandList;
	}

}
