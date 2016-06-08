package com.xiu.search.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.search.core.model.CatalogBusinessOptModel;
import com.xiu.search.core.service.CatalogBusinessOptService;
import com.xiu.search.dao.XiuBrandRecommendDAO;
import com.xiu.search.dao.XiuCatRecommendDAO;
import com.xiu.search.dao.cache.CacheManage;
import com.xiu.search.dao.cache.CacheTypeEnum;
import com.xiu.search.dao.model.XiuBrandRecommend;
import com.xiu.search.dao.model.XiuCatRecommend;
import com.xiu.solr.lexicon.client.model.QueryParseModel;
import com.xiu.solr.lexicon.client.model.QueryParseModel.UnitEnum;
import com.xiu.solr.lexicon.client.model.QueryParseUnit;

@Service("catalogBusinessOptService")
public class CatalogBusinessOptServiceImpl implements CatalogBusinessOptService{

	@Override
	public CatalogBusinessOptModel getGoodsItemsTopShow(Integer catalogId,Integer[] types,Integer mktType) {
		if(null == catalogId
				|| catalogId.intValue() <= 0)
			return null;
		CatalogBusinessOptModel ret;
//		if((ret = (CatalogBusinessOptModel) CacheManage.get(catalogId.toString(), CacheTypeEnum.CATAGROUP_BUSINESS_OPT)) != null)
//			return ret;
		List<XiuCatRecommend> catItems = xiuCatRecommendDAO.selectByPrimaryKeyForBusiness(catalogId,null,mktType);
		// 如果有值，则进行解析
		if(catItems != null && catItems.size()>0){
			ret = new CatalogBusinessOptModel();
			ret.setCatalogId(catalogId);
			Map<Integer,List<XiuCatRecommend>> recMap = new HashMap<Integer, List<XiuCatRecommend>>(2);
			for (XiuCatRecommend o : catItems) {
				if(o!=null 
						&& o.getCatId()!=null 
						&& o.getBizId()!=null 
						&& o.getBizType()!=null){
					if(!recMap.containsKey(o.getBizType()))
						recMap.put(o.getBizType(), new ArrayList<XiuCatRecommend>());
					recMap.get(o.getBizType()).add(o);
				}
			}
			
			Integer type = Integer.valueOf(1);
			// 商品推荐
			if((catItems = recMap.get(type)) != null){
				List<String> obj = new ArrayList<String>(catItems.size());
				for (XiuCatRecommend o : catItems) {
					if(null != o.getBizId()
							&& o.getBizId().length()>0)
						obj.add(o.getBizId());
				}
				if(obj.size()>0)
					ret.setGoodsItemTopShow(obj);
			}
			
			// 分类下的品牌排序绑定
			type = Integer.valueOf(2);
			if((catItems = recMap.get(type)) != null){
				LinkedHashMap<Integer, Integer> obj = new LinkedHashMap<Integer, Integer>(catItems.size());
				for (XiuCatRecommend o : catItems) {
					if(null != o.getBizId()
							&& o.getBizId().length()>0
							&& StringUtils.isNumeric(o.getBizId()));
					obj.put(Integer.valueOf(o.getBizId()), null == o.getWeight() ? 1 : o.getWeight());
				}
				if(obj.size()>0)
					ret.setBrandGoodsItemTopShow(obj);
			}
			
			
		}else{
			ret = new CatalogBusinessOptModel();
		}
//		CacheManage.put(catalogId.toString(), ret, CacheTypeEnum.CATAGROUP_BUSINESS_OPT);
		return ret;
	}
	
	@Autowired
    private XiuCatRecommendDAO xiuCatRecommendDAO;
	@Autowired
	private XiuBrandRecommendDAO xiuBrandRecommendDAO;
	

	@Override
	public CatalogBusinessOptModel getBrandHotBusinessInfo(Integer brandId) {
		if(null == brandId
				|| brandId.intValue() <= 0)
			return null;
		CatalogBusinessOptModel ret;
		if((ret = (CatalogBusinessOptModel) CacheManage.get(brandId.toString(), CacheTypeEnum.BRANDID_BUSINESS_OPT)) != null)
			return ret;
		List<XiuBrandRecommend> brandItems = xiuBrandRecommendDAO.selectByPrimaryKeyForBusiness(brandId, null);
		// 如果有值，则进行解析
		if(brandItems != null && brandItems.size()>0){
			ret = new CatalogBusinessOptModel();
			Map<Integer,List<XiuBrandRecommend>> recMap = new HashMap<Integer, List<XiuBrandRecommend>>(2);
			for (XiuBrandRecommend o : brandItems) {
				if(o!=null 
						&& o.getBrandId()!=null 
						&& o.getBizId()!=null 
						&& o.getBizType()!=null){
					if(!recMap.containsKey(o.getBizType()))
						recMap.put(o.getBizType(), new ArrayList<XiuBrandRecommend>());
					recMap.get(o.getBizType()).add(o);
				}
			}
			
			Integer type = null;
			
			// 品牌下的分类绑定排序
			type = Integer.valueOf(1);//1:品牌分类推荐，2:商品推荐
			if((brandItems = recMap.get(type)) != null){
				LinkedHashMap<Integer, Integer> obj = new LinkedHashMap<Integer, Integer>(brandItems.size());
				for (XiuBrandRecommend o : brandItems) {
					if(null != o.getBizId()
							&& o.getBizId().length()>0
							&& StringUtils.isNumeric(o.getBizId()));
					obj.put(Integer.valueOf(o.getBizId()), null == o.getWeight() ? 1 : o.getWeight());
				}
				if(obj.size()>0)
					ret.setBrandCatalogIdTopShow(obj);
			}
			
		}else{
			ret = new CatalogBusinessOptModel();
		}
		CacheManage.put(brandId.toString(), ret, CacheTypeEnum.BRANDID_BUSINESS_OPT);
		return ret;
	}

	@Override
	public CatalogBusinessOptModel getBusinessByQueryParseModel(
			QueryParseModel queryParseModel) {
		QueryParseUnit unit;
		QueryParseUnit mainUnit = queryParseModel.get(UnitEnum.MAIN);
		if(mainUnit == null){
			if((unit = queryParseModel.get(UnitEnum.BRAND_CN)) != null
					|| (unit = queryParseModel.get(UnitEnum.BRAND_EN))!= null){
				Integer brandId = unit.getBrandId();
				return getBrandHotBusinessInfo(brandId);
			}
		}
		return null;
	}

	@Override
	public Map<String, CatalogBusinessOptModel> getAllTopShow() {
		
		// 1. 加载所有分类信息
		List<XiuCatRecommend> crList= xiuCatRecommendDAO.selectAllCatRecommend();
		if(crList==null || crList.size()==0){
			 return null;
		 }
		
		Map<String, CatalogBusinessOptModel> cMap=new HashMap<String, CatalogBusinessOptModel>();
		
		// 2. 数据归类  HashSet<String>  ID_mktType
		Set<String> cSet=this.catalogClass(crList);
		if(cSet==null || cSet.size()==0){
			return null;
		}
		
		// 3. 封装数据
		 String[] kpts=null;
		 CatalogBusinessOptModel cModel=null;
		 for(String k:cSet){
			 kpts=k.split("_");
			 if(kpts==null || kpts.length!=2){
				 continue;
			 }
			 cModel = this.getGoodsItemsTopShow(Integer.parseInt(kpts[0]), null, Integer.parseInt(kpts[1]));
			 if(cModel==null){
				 continue;
			 }
			 cMap.put(k, cModel);
		 }
		
		return cMap;
	}
	
	private Set<String> catalogClass(List<XiuCatRecommend> crList){
		Set<String> bSet=new HashSet<String>();
		for(XiuCatRecommend c:crList){
			if(c.getMktType()==null){
				continue;
			}
			bSet.add(c.getCatId().toString().concat("_").concat(c.getMktType().toString()));
		}
		return bSet;
	}
	
	

}
