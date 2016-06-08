package com.xiu.search.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.search.core.model.BrandBusinessOptModel;
import com.xiu.search.core.service.BrandBusinessOptService;
import com.xiu.search.core.util.Constants;
import com.xiu.search.dao.XiuBrandRecommendDAO;
import com.xiu.search.dao.model.XiuBrandRecommend;

@Service("brandBusinessOptService")
public class BrandBusinessOptServiceImpl implements BrandBusinessOptService {

	@Autowired
	private XiuBrandRecommendDAO xiuBrandRecommendDAO;
	
	@Override
	public BrandBusinessOptModel getGoodsItemsTopShow(Integer brandId,Integer mktType) {
		return this.loadByAttr(brandId, Constants.BRAND_RECOMMEND_GOODS, mktType);//2
	}

	@Override
	public BrandBusinessOptModel getCatItemsTopShow(Integer brandId,
			Integer mktType) {
		return this.loadByAttr(brandId, Constants.BRAND_RECOMMEND_CATS, mktType);//1
	}
	
	@Override
	public Map<String, BrandBusinessOptModel> getAllTopShow() {

		 // 1.加载所有品牌信息，包含：品牌ID，Mkttype，biztype
		 List<XiuBrandRecommend> brList=xiuBrandRecommendDAO.selectAllBrandRecommend();
		 if(brList==null || brList.size()==0){
			 return null;
		 }
		 
		 Map<String, BrandBusinessOptModel> bMap=new HashMap<String, BrandBusinessOptModel>();
		 
		 // 2.数据归类  HashSet<String>  ID_bizType_mktType
		 Set<String> bSet=this.brandClass(brList);
		 if(bSet.size()==0){
			 return null;
		 }
		 
		 // 3.封装数据
		 boolean isGoods=false;
		 String curKey="";
		 String[] kpts=null;
		 BrandBusinessOptModel bModel=null;
		 for(String k:bSet){
			 kpts=k.split("_");
			 if(kpts==null || kpts.length!=3){
				 continue;
			 }
			 isGoods=false;
			 if(Integer.parseInt(kpts[1])==Constants.BRAND_RECOMMEND_GOODS){//推荐商品
				 isGoods=true;
				 bModel=this.getGoodsItemsTopShow(Integer.parseInt(kpts[0]),Integer.parseInt(kpts[2]));
			 }else{//推荐分类
				 bModel=this.getCatItemsTopShow(Integer.parseInt(kpts[0]),Integer.parseInt(kpts[2]));
			 }
			 if(bModel==null){
				 continue;
			 }
			 curKey=kpts[0].concat("_").concat(kpts[2]);
			 if(bMap.containsKey(curKey)){
				 if(isGoods){
					 bMap.get(curKey).setBrandGoodsItemTopShow(bModel.getBrandGoodsItemTopShow());
				 }else{
					 bMap.get(curKey).setBrandCatalogIdTopShow(bModel.getBrandCatalogIdTopShow());
				 }
			 }else{
				 bMap.put(curKey, bModel);
			 }
		 }
		 
		 return bMap;
	}
	
	private Set<String> brandClass(List<XiuBrandRecommend> brList){
		Set<String> bSet=new HashSet<String>();
		for(XiuBrandRecommend b:brList){
			if(b.getBizType()==null || b.getMktType()==null){
				continue;
			}
			bSet.add(b.getBrandId().toString().concat("_").concat(b.getBizType().toString()).concat("_").concat(b.getMktType().toString()));
		}
		return bSet;
	}

	/**
	 * 
	 * @param brandId
	 * @param types 	业务类型，1：品牌下分类；2：品牌下商品
	 * @param mktType 	业务范围 0：官网，2：Ebay
	 * @return
	 */
	private BrandBusinessOptModel loadByAttr(Integer brandId,Integer types,Integer mktType){
		if(null == brandId || brandId.intValue() <= 0 || mktType==null || types==null){
			return null;
		}
		BrandBusinessOptModel ret=null;
//		if((ret = (BrandBusinessOptModel) CacheManage.get(brandId.toString()+"_"+types.toString()+"_"+mktType.toString(), CacheTypeEnum.BRANDID_BUSINESS_OPT2)) != null){
//			return ret;
//		}
		List<XiuBrandRecommend> bItems = xiuBrandRecommendDAO.selectByPKAndTypesAndMktTypeForBusiness(brandId,new Integer[]{types},mktType);
		// 如果有值，则进行解析
		if(bItems != null && bItems.size()>0){
			ret = new BrandBusinessOptModel();
			ret.setBrandId(brandId);
			if(types==1){//品牌下分类
				LinkedHashMap<Integer, Integer> obj = new LinkedHashMap<Integer, Integer>(bItems.size());
				for (XiuBrandRecommend o : bItems) {
					if(o!=null && o.getBrandId()!=null  && o.getBizId()!=null  && o.getBizType()!=null){
						obj.put(Integer.valueOf(o.getBizId()), null == o.getWeight() ? 1 : o.getWeight());
					}
				}
				if(obj.size()>0){
					ret.setBrandCatalogIdTopShow(obj);
				}
			}else if(types==2){//品牌下商品
//				LinkedHashMap<String, Integer> obj = new LinkedHashMap<String, Integer>(bItems.size());
//				for (XiuBrandRecommend o : bItems) {
//					if(o!=null && o.getBrandId()!=null  && o.getBizId()!=null  && o.getBizType()!=null){
//						obj.put(o.getBizId(), null == o.getWeight() ? 1 : o.getWeight());
//					}
//				}
//				if(obj.size()>0){
//					ret.setBrandGoodsItemTopShow(obj);
//				}
				List<String> obj = new ArrayList<String>(bItems.size());
				for (XiuBrandRecommend o : bItems) {
					if(o!=null && o.getBrandId()!=null  && o.getBizId()!=null  && o.getBizType()!=null){
						obj.add(o.getBizId());
					}
				}
				if(obj.size()>0){
					ret.setBrandGoodsItemTopShow(obj);
				}
			}
			
		}else{
			ret = new BrandBusinessOptModel();
		}
//		CacheManage.put(brandId.toString()+"_"+types.toString()+"_"+mktType.toString(), ret, CacheTypeEnum.BRANDID_BUSINESS_OPT2);
		return ret;
	}

}
