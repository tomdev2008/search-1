package com.xiu.search.core.bof.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.response.FacetField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.search.core.bo.BrandBo;
import com.xiu.search.core.bo.CatalogBo;
import com.xiu.search.core.bo.FacetFilterBo;
import com.xiu.search.core.bo.FacetFilterBo.FacetTypeEnum;
import com.xiu.search.core.bo.FacetFilterValueBo;
import com.xiu.search.core.bo.GoodsItemBo;
import com.xiu.search.core.bof.BrandBof;
import com.xiu.search.core.bof.CatalogBof;
import com.xiu.search.core.bof.FacetFilterBof;
import com.xiu.search.core.bof.ListBof;
import com.xiu.search.core.config.XiuSearchProperty;
import com.xiu.search.core.model.AttrGroupJsonModel;
import com.xiu.search.core.model.BrandBusinessOptModel;
import com.xiu.search.core.model.CatalogBusinessOptModel;
import com.xiu.search.core.recom.XiuRecommonCache;
import com.xiu.search.core.service.AttrGroupService;
import com.xiu.search.core.service.BrandService;
import com.xiu.search.core.service.GoodsSolrService;
import com.xiu.search.core.solr.enumeration.MktTypeEnum;
import com.xiu.search.core.solr.index.GoodsIndexFieldEnum;
import com.xiu.search.core.solr.model.GoodsSolrModel;
import com.xiu.search.core.solr.model.XiuSKUIndexModel;
import com.xiu.search.core.solr.params.BrandFatParams;
import com.xiu.search.core.solr.params.SearchFatParams;
import com.xiu.search.core.util.CommonUtil;
import com.xiu.search.core.util.Constants;
import com.xiu.search.core.util.XiuSearchStringUtils;
import com.xiu.search.dao.cache.CacheManage;
import com.xiu.search.dao.cache.CacheTypeEnum;
import com.xiu.search.dao.model.XDataBrand;
import com.xiu.search.solrj.service.SearchResult;

@Service("brandBof")
public class BrandBofImpl implements BrandBof {
	private static final Logger LOGGER = LoggerFactory.getLogger(BrandBofImpl.class);

	@Override
	public BrandBo findBrandXiuPageResult(BrandFatParams params) {
		BrandBo brandBo=new BrandBo();
		
		// 品牌街，奢侈品，海外直发
		brandBo.setShowType(params.getShowType());
		
		brandBo.setFatParams(params);
		SearchResult<GoodsSolrModel> result;
		List<GoodsSolrModel> xiuSolrModelList;
		//获得品牌信息
		setBrandInfo(new Long(params.getBrandId()), brandBo);
		if(StringUtils.isEmpty(brandBo.getBrandName()))
			return brandBo;
		// key - 维度字段，value - 维度名称
		Map<String, AttrGroupJsonModel> attrFacetFieldMap = null;
		//确认来源渠道对应的运营分类,默认为官网
		GoodsIndexFieldEnum oClassPath = GoodsIndexFieldEnum.OCLASS_IDS;
		/*
		 * 品牌页推荐商品 lvshd
		 * add time: 2013-06-18 09:30
		 */
		// 查找运营设置的业务相关排序
		BrandBusinessOptModel businessModel = XiuRecommonCache.getInstance().loadBrandRecommenedByIdAndMkt(new Long(params.getBrandId()), MktTypeEnum.XIU);
				//brandBusinessOptService.getGoodsItemsTopShow(new Integer(params.getBrandId()), MktTypeEnum.XIU.getType());
		CatalogBusinessOptModel cOptModel= null;
		if(businessModel!=null){
			cOptModel=new CatalogBusinessOptModel();
			cOptModel.setBrandId(businessModel.getBrandId());
			cOptModel.setGoodsItemTopShow(businessModel.getBrandGoodsItemTopShow());
			cOptModel.setBrandCatalogIdTopShow(businessModel.getBrandCatalogIdTopShow());
		}
//		if(businessModel!=null && businessModel.getBrandGoodsItemTopShow()!=null){
//			List<String> idsList=new ArrayList<String>();
//			Iterator<Map.Entry<String, Integer>> xbrItr=businessModel.getBrandGoodsItemTopShow().entrySet().iterator();
//			while (xbrItr.hasNext()) {
//				idsList.add(xbrItr.next().getKey());
//			}
//			cOptModel=new CatalogBusinessOptModel();
//			cOptModel.setGoodsItemTopShow(idsList);
//		}
		
		
//		Map<MktTypeEnum,Integer> mktGoodsCountMap = null;
		//若运营分类不为空，就根据运营分类与品牌编号进行查询
		if(null!=params.getCatalogId()){
			
			// 获取运营分类的筛选属性项
			// attrFacetFieldMap-->LinkedHashMap为有序
			attrFacetFieldMap=attrGroupService.getAttrGroupIdNameListWithInherit(new Long(params.getCatalogId()));
			String[] attrFacetFields = null;
			if(attrFacetFieldMap !=null){
				attrFacetFields = attrFacetFieldMap.keySet().toArray(new String[attrFacetFieldMap.size()]);
			}
			
//			/*
//			 *  品牌页推荐分类 lvshd
//			 * add Time 2013-06-18 10：50
//			 */
//			businessModel=brandBusinessOptService.getCatItemsTopShow(new Integer(params.getBrandId()), MktTypeEnum.XIU.getType());
//			if(businessModel!=null && businessModel.getBrandCatalogIdTopShow()!=null){
//				if(cOptModel==null){
//					cOptModel=new CatalogBusinessOptModel();
//				}
//				cOptModel.setBrandCatalogIdTopShow(businessModel.getBrandCatalogIdTopShow());
//			}	
			
			/*
			 * 商品推荐 lvshd
			 * 日期：2013-06-18 10：50
			 */
			result = goodsSolrService.findSearchXiuSolr(params, true, attrFacetFields, new String[]{oClassPath.fieldName()}, cOptModel, false);
					
			if(result != null){
				params.getPage().setRecordCount((int)result.getTotalHits());
				if(params.getPage().getPageNo() > params.getPage().getPageCount()){
					// 若当前页数大于总页数，则属于用户输入参数错误，需重新搜索
//					params.getPage().setPageNo(1);
//					return this.findBrandXiuPageResult(params);
					// 用户参数错误时，则返回到无结果页面		William.zhang	20130704
					return brandBo;
				}
				xiuSolrModelList = result.getBeanList();
				
				List<GoodsItemBo> goodsItemBoList = CommonUtil.transformXiuItemBo(null, xiuSolrModelList);
				
				
				this.buildSkuSize(goodsItemBoList);
				
				brandBo.setGoodsItemList(goodsItemBoList);
			}
		}else{
			// 查找商品
			/*
			 * 商品推荐 lvshd
			 * 修改日期：2013-06-18 10：50
			 */
			result = goodsSolrService.findSearchXiuSolr(params, false, null, new String[]{oClassPath.fieldName()},cOptModel,false);
			
			
			if(result != null){
				List<GoodsItemBo> goodsItemBoList = CommonUtil.transformXiuItemBo(null, result.getBeanList());
				
				
				this.buildSkuSize(goodsItemBoList);
				
				brandBo.setGoodsItemList(goodsItemBoList);
				params.getPage().setRecordCount((int)result.getTotalHits());
				if(params.getPage().getPageNo() > params.getPage().getPageCount()){
					// 若当前页数大于总页数，则属于用户输入参数错误，需重新搜索
//					params.getPage().setPageNo(1);
//					return this.findBrandXiuPageResult(params);
					// 用户参数错误时，则返回到无结果页面		William.zhang	20130704
					return brandBo;
				}
			}
		}
		
		FacetFilterBo facetFilterBo = null;
		if(null!=result){
			if(null != result.getFacetFields()){
				for (FacetField f: result.getFacetFields()) {
					if(oClassPath.fieldName().equals(f.getName())){
						// 如果是运营分类
						List<CatalogBo> catalogBoList = catalogBof.fetchCatalogBoTreeListForXiu(f, params.getCatalogId());
						if(null != catalogBoList){
							brandBo.setCatalogBoList(catalogBoList);
						}
						
						/*
						 * 设置所有一级分类  lvshuding 2014-04-18 14:50   最后注释日期：2014-05-06 11：25
						 */
						brandBo.setFirstCataList(catalogBof.fetchCatalogBoTreeListForXiu(f, null));
						
					}else if(f.getName().indexOf(GoodsIndexFieldEnum.ATTRS_PREFIX.fieldName())==0){
						
						//新的业务逻辑	支持多选	William.zhang	20130508
	                	facetFilterBo = facetFilterBof.parseAttrFacetFilter(f,params.getAttrValIdList(),attrFacetFieldMap);
	                	
						if(null != facetFilterBo)
							brandBo.addAttrFacetBoList(facetFilterBo);
					}
				}
			}
			
			// 生成价格范围查询的筛选
			facetFilterBo = facetFilterBof.parsePriceRangeFacetFilter(result.getFacetQuery());
			if(null != facetFilterBo){
				brandBo.setPriceFacetBo(facetFilterBo);
			}
			
			
			// 生成已选择的运营分类平面结构
			if(brandBo.getCatalogBoList()!=null){
				List<CatalogBo> selectCatalogPlaneList = new ArrayList<CatalogBo>();
				this.parsePlaneSelectCatalogBo(selectCatalogPlaneList, brandBo.getCatalogBoList());
				brandBo.setSelectCatalogPlaneList(selectCatalogPlaneList);
				
				
				/*
				 * 对各级兄弟节点赋值   2014-05-06 15:05
				 */
				if(selectCatalogPlaneList.size()>0){
					BrandFatParams bfp=new BrandFatParams(1);
					bfp.setBrandId(params.getBrandId());
					
					SearchResult<GoodsSolrModel> tmpRst = goodsSolrService.findSearchXiuSolr(bfp, false, null, new String[]{oClassPath.fieldName()},null,false);
					
					if(null!=tmpRst){
						if(null != tmpRst.getFacetFields()){
							for (FacetField f: tmpRst.getFacetFields()) {
								if(oClassPath.fieldName().equals(f.getName())){
									// 如果是运营分类
									catalogBof.fetchBrotherCatalogBoListForXiu(f, params.getCatalogId(),brandBo);
									break;
								}
							}
						}
					}
				}else{
					/*
					 * 选中的一级分类设置 isSelected,并删除隐藏的分类   2014-04-18 12:05
					 */
					List<CatalogBo> fcList=brandBo.getFirstCataList();
					if(fcList!=null && fcList.size()>0){
						if(brandBo.getSelectCatalogPlaneList()!=null && brandBo.getSelectCatalogPlaneList().size()>0){
							int selectedId = brandBo.getSelectCatalogPlaneList().get(0).getCatalogId();
							for(int i=0;i<fcList.size();i++){
								if(Constants.CATALOG_DISPLAY_HIDDEN.equals(fcList.get(i).getDisplay())){
									fcList.remove(i);
									--i;
								}
							}
							for(int i=0;i<fcList.size();i++){
								if(fcList.get(i).getCatalogId()==selectedId){
									fcList.get(i).setSelected(true);
									break;
								}
							}
						}
					}
				}
			}
		}else{
			//reQuery(params, brandBo);
			if(null!=params.getCatalogId()){
//				CatalogBo catalogBo=listBof.getCatalogBoList(params,MktTypeEnum.XIU);
//				if(null!=catalogBo){
//					brandBo.setSelectedCatalogTree(catalogBo);
//				}
				
				/*
				 * 清除隐藏的运营分类树节点  2014-04-10 14:56
				 */
				CatalogBo catalogBo=listBof.getCatalogBoList2(params,MktTypeEnum.XIU);
				if(null!=catalogBo){
					brandBo.setSelectedCatalogTree(CommonUtil.deleteDisplay2(catalogBo));
				}
			}
			return brandBo;
		}
		//通过各筛选结果，来判断是否硬性添加”其他“筛选项
		/*try {
			this.mergeOtherAttr(result, brandBo, params, attrFacetFieldMap);
		} catch (Exception e) {
			LOGGER.error("通过各筛选结果，来判断是否硬性添加”其他“筛选项出现异常------------>>",e);
		}*/
		
		// 生成已选择的价格条件
		if(null != params.getStartPrice() || null != params.getEndPrice()){
			facetFilterBo = facetFilterBof.formatSelectCustomPriceRangeFacetFilter(params.getStartPrice(), params.getEndPrice());
			if(null != facetFilterBo){
				brandBo.addSelectFacetBoList(facetFilterBo);
				brandBo.setPriceFacetBo(null);
			}
		}else if(null != params.getPriceRangeEnum()){
			facetFilterBo = facetFilterBof.formatSelectFacetFilter(FacetTypeEnum.PRICE, params.getPriceRangeEnum().getOrder());
			if(null != facetFilterBo){
				brandBo.addSelectFacetBoList(facetFilterBo);
				brandBo.setPriceFacetBo(null);
			}
		}
		// 生成已选择的属性条件
		if(CollectionUtils.isNotEmpty(params.getAttrValIdList()) && null != brandBo.getAttrFacetBoList() && brandBo.getAttrFacetBoList().size()>0){
        	FacetFilterBo _ffBo;
        	List<Long> attrValIds;
        	// 多值属性值选择，过滤已经选择的属性项。	William.zhang	20130515
        	for (List<String> list : params.getAttrValIdList()) {
        		if(null == list)
        			continue;
        		attrValIds = new ArrayList<Long>(list.size());
        		for (String attrValIdStr : list) {
					if(XiuSearchStringUtils.isIntegerNumber(attrValIdStr))
						attrValIds.add(Long.valueOf(attrValIdStr));
				}
        		for (int i=0;i< brandBo.getAttrFacetBoList().size();i++) {
                	_ffBo = brandBo.getAttrFacetBoList().get(i);
                    facetFilterBo = facetFilterBof.formatSelectFacetFilter(_ffBo, attrValIds);
                    if(null != facetFilterBo){
                    	brandBo.addSelectFacetBoList(facetFilterBo);
                    	brandBo.removeAttrFacetBoList(facetFilterBo.getFacetFieldName());
                    }
                }
			}
			
		}
		
		//价格整合到筛选项list中，并排到最后
		if(null != brandBo.getPriceFacetBo()){
			brandBo.addAttrFacetBoList(brandBo.getPriceFacetBo());
		}
	
		// 生成已选择的运营分类树
		if(null != brandBo.getCatalogBoList() && params.getCatalogId() != null){
			
			// 过滤分类树，未选中的兄弟节点需要删除  注释日期：2014-04-18 14:55
			//catalogBof.filterCatalogTreeDeleteUnSelectedSiblingsItem(brandBo.getCatalogBoList());
			
			for(CatalogBo bo : brandBo.getCatalogBoList()){
				if(bo.isSelected()){
					brandBo.setSelectedCatalogTree(bo);
					break;
				}
			}
			brandBo.setSelectedCatalog(catalogBof.getSelectedCatalogFromSelectedCatalogTree(brandBo.getSelectedCatalogTree()));
			
			/*
			 * 清除隐藏的运营分类树节点  2014-04-11 11:56
			 */
			brandBo.setSelectedCatalogTree(CommonUtil.deleteDisplay2(brandBo.getSelectedCatalogTree()));
		}
		//删除只有一个属性值的属性项
		this.removeOnlyOneValAttr(brandBo);
		//官网推荐插码
  		if(null!=result&&null!=result.getBeanList()){
  			String[] recommendInfo=CommonUtil.matchRecommendInfo(MktTypeEnum.XIU, params.getCatalogId(), result.getBeanList());
  			if(null!=recommendInfo){
  				brandBo.setRecommendItemIds(recommendInfo[0]);
  				brandBo.setRecommendCatIds(recommendInfo[1]);
  				brandBo.setRecommenBrandIds(params.getBrandId()+"");
  			}
  		}
  		
  		/*
		 * 清除隐藏的运营分类树节点  2014-04-10 14:56
		 */
		if(brandBo.getCatalogBoList()!=null){
			List<CatalogBo> tmpLst=new ArrayList<CatalogBo>();
			CatalogBo tmpCb=null;
			for(CatalogBo cb:brandBo.getCatalogBoList()){
				tmpCb=CommonUtil.deleteDisplay2(cb);
				if(tmpCb.getCatalogModel().getParentCatalogId()==0 && (tmpCb.getChildCatalog()==null || tmpCb.getChildCatalog().size()==0)){
					continue;
				}
				tmpLst.add(tmpCb);
			}
			brandBo.setCatalogBoList(tmpLst);
		}
		
  		
		// 生成其他信息
		brandBo.setPage(params.getPage());
		return brandBo;
	}
	/**
	 * 过滤掉只有一个属性值的属性项
	 * @param listBo
	 */
	private void removeOnlyOneValAttr(BrandBo brandBo) {
		List<FacetFilterBo> facetFilterBos = brandBo.getAttrFacetBoList();
		if (facetFilterBos != null && facetFilterBos.size()>0) {
			for (Iterator<FacetFilterBo> boIter = facetFilterBos.iterator();boIter.hasNext();) {
				FacetFilterBo bo = boIter.next();
				List<FacetFilterValueBo> valueBos = bo.getFacetValueBos();
				if (FacetTypeEnum.ATTR.equals(bo.getFacetType()) && (valueBos == null || valueBos.size() <= 1)) {
					boIter.remove();
				}
			}
		}
	}
	/**
	 * 梳理各个属性项的“其他”属性值
	 * @param result
	 * @param listBo
	 * @param params
	 * @param attrIdNameMap
	 * @throws Exception
	 */
	private void mergeOtherAttr(SearchResult<GoodsSolrModel> result, BrandBo brandBo, BrandFatParams params, Map<String, AttrGroupJsonModel> attrIdNameMap) throws Exception{
		List<FacetFilterBo> facetFilterBoList = brandBo.getAttrFacetBoList();
		List<List<String>> selectedAttrValIds = params.getAttrValIdList();
		//已选择的过滤值
		StringBuilder filterStringBuilder = null;
		//属性项map
		Map<String, FacetFilterBo> facetFilterBoMap = null;
		if (selectedAttrValIds != null && !selectedAttrValIds.isEmpty()) {
			filterStringBuilder = new StringBuilder();
			for (int i = 0; i < selectedAttrValIds.size(); i++) {
				if (selectedAttrValIds.get(i) == null || selectedAttrValIds.isEmpty()) {
					continue;
				}
				for (int j = 0; j < selectedAttrValIds.get(i).size(); j++) {
					filterStringBuilder.append(selectedAttrValIds.get(i).get(j)).append("|");
				}
				filterStringBuilder.setCharAt(filterStringBuilder.length()-1, ';');
			}
		}
		//检查并设置各属性项的“其他”筛选值（当前属性项未做筛选）
		if (facetFilterBoList != null) {
			facetFilterBoMap = new HashMap<String, FacetFilterBo>();
			List<Long> attrIds = new ArrayList<Long>();
			for (Iterator<FacetFilterBo> boIter = facetFilterBoList.iterator();boIter.hasNext();) {
				FacetFilterBo tempBo = boIter.next();
				attrIds.add(tempBo.getFacetId());
			}
			Map<String, Integer> attrId_CountMap = new HashMap<String, Integer>();
			SearchResult<GoodsSolrModel> otherAttrCountResult = goodsSolrService.findCurrentCatalogOtherAttrCount(params, attrIds);
			if (otherAttrCountResult != null) {
				Map<String, Integer> tempMap = otherAttrCountResult.getFacetQuery();
				if (tempMap != null) {
					for (Iterator<Map.Entry<String, Integer>> iterator = tempMap.entrySet().iterator(); iterator.hasNext();) {
						Map.Entry<String, Integer> entry = iterator.next();
						if (entry.getKey()!=null && entry.getKey().startsWith(GoodsIndexFieldEnum.ATTR_IDS.fieldName())) {
							attrId_CountMap.put(entry.getKey().replaceFirst("attrs:", "").trim(), entry.getValue());
						}
					}
				}
			}
			for (Iterator<FacetFilterBo> boIter = facetFilterBoList.iterator();boIter.hasNext(); ) {
				int maxOrder = 0;
				FacetFilterBo facetFilterBo = boIter.next();
				//boolean useOldOther = true;
				//boolean hasOldOther = false;
				facetFilterBoMap.put(facetFilterBo.getFacetId()+"", facetFilterBo);
				//存放分类下此属性项是否有被标记为“其他”的商品
				Integer num = attrId_CountMap.get(params.getCatalogId()+""+facetFilterBo.getFacetId())==null?0:attrId_CountMap.get(params.getCatalogId()+""+facetFilterBo.getFacetId());
				if (num < 1) {
					//该分类筛选项下没有后期设置的“其他”值的商品，不需要强行设置“其他”筛选值
					continue;
				}
				List<FacetFilterValueBo> filterValueBos = facetFilterBo.getFacetValueBos();
				if(filterValueBos != null && filterValueBos.size()>0){
					for (Iterator<FacetFilterValueBo> filterValueBoIter = filterValueBos.iterator();filterValueBoIter.hasNext();) {
						FacetFilterValueBo filterValueBo = filterValueBoIter.next();
						//同时要删除已有的"其他"属性值，统一使用硬性添加的
						if ("其他".equals(filterValueBo.getName()) || "其它".equals(filterValueBo.getName())) {
							//hasOldOther = true;
							//filterValueBo.setShowOrder(Integer.MAX_VALUE);
							//容错
							//if (num >0 && num >= filterValueBo.getItemCount()) {
								filterValueBoIter.remove();
							//	useOldOther = false;
							//}
							continue;
						}
						maxOrder = Math.max(maxOrder, filterValueBo.getShowOrder());
					}
					//如果这个分类下没有其他属性，然后有后期标注为”其他“的商品，或者后期设置的“其他”没有原来固有的“其他”的商品多
					//if(!hasOldOther || !useOldOther) {
						FacetFilterValueBo otherValBo = new FacetFilterValueBo();
						otherValBo.setFilter((filterStringBuilder==null?"":filterStringBuilder.toString())+facetFilterBo.getFacetId()+"");
						otherValBo.setFirstLetter("Q");
						otherValBo.setId(facetFilterBo.getFacetId());
						otherValBo.setItemCount(num);
						otherValBo.setName("其他");
						filterValueBos.add(otherValBo);
					//}
				}
			}
		}
		//检查是否筛选了“其他”属性值
		if (selectedAttrValIds != null && !selectedAttrValIds.isEmpty()) {
			List<String> singleAttrVals = null;
			String attrId = null;
			for (int i = 0; i < selectedAttrValIds.size(); i++) {
				String tempAttrVal = null;
				singleAttrVals = selectedAttrValIds.get(i);
				for (int j = 0; j < singleAttrVals.size(); j++) {
					tempAttrVal = singleAttrVals.get(j);
					//以7开头的19位字符串值，每个属性项只可能有一个“其他”
					if (tempAttrVal != null && tempAttrVal.startsWith("7") && tempAttrVal.trim().length()>=19) {
						attrId = tempAttrVal;
						break;
					}
				}
				//有进行“其他”筛选
				if (attrId != null) {
					//查找是否在facetAttrBo中存在
					if (facetFilterBoMap != null && !facetFilterBoMap.isEmpty()) {
						FacetFilterBo tempBo = facetFilterBoMap.get(attrId);
						//没在facetAttrList里面出现，就直接新增一个
						if (tempBo == null) {
							tempBo = new FacetFilterBo();
							tempBo.setFacetFieldName(GoodsIndexFieldEnum.ATTRS_PREFIX.fieldName()+attrId);
							String display = attrIdNameMap.get(GoodsIndexFieldEnum.ATTRS_PREFIX.fieldName()+attrId).getName();
							if (display != null && display.trim().length()>4) {
								display = display.substring(0,4);
							}
							tempBo.setFacetDisplay(display);
							tempBo.setFacetId(Long.valueOf(attrId));
							tempBo.setFacetType(FacetTypeEnum.ATTR);
							tempBo.setShowOrder(1);
							brandBo.addAttrFacetBoList(tempBo);
						}
						FacetFilterValueBo tempValueBo = new FacetFilterValueBo();
						tempValueBo.setFilter(filterStringBuilder==null?"":filterStringBuilder.toString());
						tempValueBo.setFirstLetter("Q");
						tempValueBo.setId(Long.valueOf(attrId));
						tempValueBo.setName("其他");
						tempValueBo.setShowOrder(Integer.MAX_VALUE);
						tempValueBo.setPyName("qita");
						List<FacetFilterValueBo> tempValueBoList = tempBo.getFacetValueBos();
						if(tempValueBoList == null){
							tempBo.setFacetValueBos(new ArrayList<FacetFilterValueBo>());
							tempBo.getFacetValueBos().add(tempValueBo);
						}else {
							//多选下，加入“其他”
							boolean flag = false;
							for (int j = 0; j < tempValueBoList.size(); j++) {
								if (tempValueBoList.get(j) != null && tempValueBoList.get(j).getId().equals(Long.valueOf(attrId))) {
									flag = true;
									break;
								}
							}
							if (!flag) {
								tempValueBoList.add(tempValueBo);
							}
						}
					}else {
						FacetFilterBo tempBo = new FacetFilterBo();
						tempBo.setFacetFieldName(GoodsIndexFieldEnum.ATTRS_PREFIX.fieldName()+attrId);
						String display = attrIdNameMap.get(GoodsIndexFieldEnum.ATTRS_PREFIX.fieldName()+attrId).getName();
						if (display != null && display.trim().length()>4) {
							display = display.substring(0,4);
						}
						tempBo.setFacetDisplay(display);
						tempBo.setFacetId(Long.valueOf(attrId));
						tempBo.setFacetType(FacetTypeEnum.ATTR);
						tempBo.setShowOrder(1);
						brandBo.addAttrFacetBoList(tempBo);
						FacetFilterValueBo tempValueBo = new FacetFilterValueBo();
						tempValueBo.setFilter(filterStringBuilder==null?"":filterStringBuilder.toString());
						tempValueBo.setFirstLetter("Q");
						tempValueBo.setId(Long.valueOf(attrId));
						tempValueBo.setName("其他");
						tempValueBo.setShowOrder(Integer.MAX_VALUE);
						tempValueBo.setPyName("qita");
						tempBo.setFacetValueBos(new ArrayList<FacetFilterValueBo>());
						tempBo.getFacetValueBos().add(tempValueBo);
					}
				}
			}
		}
	}

	/**
	 * @param goodsItemBoList
	 */
	private void buildSkuSize(List<GoodsItemBo> goodsItemBoList) {
		/*
		 * 尺码展示
		 * 添加日期：2014-03-06 14：20
		 */
		if(XiuSearchProperty.getInstance().enableBuildSkuSize()){
			if(goodsItemBoList!=null && goodsItemBoList.size()>0){
				List<String> idList=new ArrayList<String>();
				for(GoodsItemBo b:goodsItemBoList){
					idList.add(b.getId());
				}
				SearchResult<XiuSKUIndexModel> skuRst = goodsSolrService.findSearchSKUSolr(idList);
				if(skuRst!=null && skuRst.getTotalHits()>0){
					List<XiuSKUIndexModel> skuList = skuRst.getBeanList();
					if(skuList!=null){
						CommonUtil.buildGoodsShowSkuList(skuList, goodsItemBoList,XiuSearchProperty.getInstance().showSkuSizeCount());
					}
				}
			}
		}
	}

	
	@Override
	public BrandBo findBrandEbayPageResult(BrandFatParams params) {
		BrandBo brandBo=new BrandBo();
		brandBo.setFatParams(params);
		SearchResult<GoodsSolrModel> result;
		List<GoodsSolrModel> xiuSolrModelList;
		// key - 维度字段，value - 维度名称
		Map<String, AttrGroupJsonModel> attrFacetFieldMap = null;
		//获得品牌信息
		setBrandInfo(new Long(params.getBrandId()), brandBo);
		if(StringUtils.isEmpty(brandBo.getBrandName()))
			return brandBo;
		
		//确认来源渠道对应的运营分类,默认为官网
		
		GoodsIndexFieldEnum oClassPath=GoodsIndexFieldEnum.OCLASS_IDS;
		
		// 查找运营设置的业务相关排序
		BrandBusinessOptModel businessModel = XiuRecommonCache.getInstance().loadBrandRecommenedByIdAndMkt(new Long(params.getBrandId()), MktTypeEnum.EBAY);
				//brandBusinessOptService.getGoodsItemsTopShow(new Integer(params.getBrandId()), MktTypeEnum.EBAY.getType()+1);
		CatalogBusinessOptModel cOptModel= null;
		if(businessModel!=null){
			cOptModel=new CatalogBusinessOptModel();
			cOptModel.setBrandId(businessModel.getBrandId());
			cOptModel.setGoodsItemTopShow(businessModel.getBrandGoodsItemTopShow());
			cOptModel.setBrandCatalogIdTopShow(businessModel.getBrandCatalogIdTopShow());
		}
//		if(businessModel!=null && businessModel.getBrandGoodsItemTopShow()!=null){
//			List<String> idsList=new ArrayList<String>();
//			Iterator<Map.Entry<String, Integer>> xbrItr=businessModel.getBrandGoodsItemTopShow().entrySet().iterator();
//			while (xbrItr.hasNext()) {
//				idsList.add(xbrItr.next().getKey());
//			}
//			cOptModel=new CatalogBusinessOptModel();
//			cOptModel.setGoodsItemTopShow(idsList);
//		}
				
		//若运营分类不为空，就根据运营分类与品牌编号进行查询
		if(null!=params.getCatalogId()){
			
			// 获取运营分类的筛选属性项
			// attrFacetFieldMap-->LinkedHashMap为有序
			attrFacetFieldMap=attrGroupService.getAttrGroupIdNameListWithInherit(new Long(params.getCatalogId()));
			String[] attrFacetFields = null;
			if(attrFacetFieldMap !=null){
				attrFacetFields = attrFacetFieldMap.keySet().toArray(new String[attrFacetFieldMap.size()]);
			}
			
//			/*
//			 * 商品推荐 lvshd
//			 * add Time 2013-06-18 10：50
//			 */
//			businessModel=brandBusinessOptService.getCatItemsTopShow(new Integer(params.getBrandId()), MktTypeEnum.EBAY.getType()+1);
//			if(businessModel!=null && businessModel.getBrandCatalogIdTopShow()!=null){
//				if(cOptModel==null){
//					cOptModel=new CatalogBusinessOptModel();
//				}
//				cOptModel.setBrandCatalogIdTopShow(businessModel.getBrandCatalogIdTopShow());
//			}	
			
			/*
			 * 商品推荐 lvshd
			 * 日期：2013-06-18 10：50
			 */
			result = goodsSolrService.findSearchXiuSolr(params, true, attrFacetFields,new String[]{oClassPath.fieldName()},cOptModel,true);
			
			if(result != null){
				params.getPage().setRecordCount((int)result.getTotalHits());
				if(params.getPage().getPageNo() > params.getPage().getPageCount()){
					// 若当前页数大于总页数，则属于用户输入参数错误，需重新搜索
//					params.getPage().setPageNo(1);
//					return this.findBrandEbayPageResult(params);
					
					// 用户参数错误时，则返回到无结果页面		William.zhang	20130704
					return brandBo;
				}
				xiuSolrModelList = result.getBeanList();
				List<GoodsItemBo> goodsItemBoList = CommonUtil.transformXiuItemBo(null, xiuSolrModelList);
				this.buildSkuSize(goodsItemBoList);
				brandBo.setGoodsItemList(goodsItemBoList);
			}
		}else{
			// 查找商品
			/*
			 * 商品推荐 lvshd
			 * 日期：2013-06-18 14：50
			 */
			result = goodsSolrService.findSearchXiuSolr(params, false, null, new String[]{oClassPath.fieldName()},cOptModel,true);
			
			if(result != null){
				List<GoodsItemBo> goodsItemBoList = CommonUtil.transformXiuItemBo(null, result.getBeanList());
				this.buildSkuSize(goodsItemBoList);
				brandBo.setGoodsItemList(goodsItemBoList);
				params.getPage().setRecordCount((int)result.getTotalHits());
				if(params.getPage().getPageNo() > params.getPage().getPageCount()){
					// 若当前页数大于总页数，则属于用户输入参数错误，需重新搜索
//					params.getPage().setPageNo(1);
//					return this.findBrandXiuPageResult(params);
					// 用户参数错误时，则返回到无结果页面		William.zhang	20130704
					return brandBo;
				}
			}
		}
		
		SearchFatParams paramsTemp = params.clone();// 为了使用官网的运营分类
		paramsTemp.setMktType(null);
		SearchResult<GoodsSolrModel> resultTemp = goodsSolrService.findSearchXiuSolr(paramsTemp, false, null,
				new String[] { oClassPath.fieldName() }, cOptModel, true);
		FacetFilterBo facetFilterBo = null;
		if(null!=resultTemp){
			if(null != resultTemp.getFacetFields()){
				for (FacetField f: resultTemp.getFacetFields()) {
					if(oClassPath.fieldName().equals(f.getName())){
						// 如果是运营分类
						List<CatalogBo> catalogBoList = catalogBof.fetchCatalogBoTreeListForXiu(f, params.getCatalogId());
						if(null != catalogBoList){
							brandBo.setCatalogBoList(catalogBoList);
						}
						
						/*
						 * 设置所有一级分类  yanfei 2014-07-24 16:00
						 */
						brandBo.setFirstCataList(catalogBof.fetchCatalogBoTreeListForXiu(f, null));
						
					}else if(f.getName().indexOf(GoodsIndexFieldEnum.ATTRS_PREFIX.fieldName())==0){
						//新的业务逻辑	支持多选	William.zhang	20130508
	                	facetFilterBo = facetFilterBof.parseAttrFacetFilter(f,params.getAttrValIdList(),attrFacetFieldMap);
						if(null != facetFilterBo)
							brandBo.addAttrFacetBoList(facetFilterBo);
					}
				}
			}
			
			// 生成价格范围查询的筛选
			if (result != null) {
				facetFilterBo = facetFilterBof.parsePriceRangeFacetFilter(result.getFacetQuery());
			}
			if(null != facetFilterBo)
				brandBo.setPriceFacetBo(facetFilterBo);
			// 生成已选择的运营分类平面结构
			if(brandBo.getCatalogBoList()!=null){
				List<CatalogBo> selectCatalogPlaneList = new ArrayList<CatalogBo>();
				this.parsePlaneSelectCatalogBo(selectCatalogPlaneList, brandBo.getCatalogBoList());
				brandBo.setSelectCatalogPlaneList(selectCatalogPlaneList);
				
				/*
				 * 对各级兄弟节点赋值   2014-07-24 16:15
				 */
				if(selectCatalogPlaneList.size()>0){
					BrandFatParams bfp=new BrandFatParams(1);
					bfp.setBrandId(params.getBrandId());
					
					SearchResult<GoodsSolrModel> tmpRst = goodsSolrService.findSearchXiuSolr(bfp, false, null, new String[]{oClassPath.fieldName()},null,false);
					
					if(null!=tmpRst){
						if(null != tmpRst.getFacetFields()){
							for (FacetField f: tmpRst.getFacetFields()) {
								if(oClassPath.fieldName().equals(f.getName())){
									// 如果是运营分类
									catalogBof.fetchBrotherCatalogBoListForXiu(f, params.getCatalogId(),brandBo);
									break;
								}
							}
						}
					}
				}else{
					/*
					 * 选中的一级分类设置 isSelected,并删除隐藏的分类   2014-07-24 16:15
					 */
					List<CatalogBo> fcList=brandBo.getFirstCataList();
					if(fcList!=null && fcList.size()>0){
						if(brandBo.getSelectCatalogPlaneList()!=null && brandBo.getSelectCatalogPlaneList().size()>0){
							int selectedId = brandBo.getSelectCatalogPlaneList().get(0).getCatalogId();
							for(int i=0;i<fcList.size();i++){
								if(Constants.CATALOG_DISPLAY_HIDDEN.equals(fcList.get(i).getDisplay())){
									fcList.remove(i);
									--i;
								}
							}
							for(int i=0;i<fcList.size();i++){
								if(fcList.get(i).getCatalogId()==selectedId){
									fcList.get(i).setSelected(true);
									break;
								}
							}
						}
					}
				}
				
			}
		}else{
			//重新查询
			//reQuery(params, brandBo);
			if(null!=params.getCatalogId()){
//				CatalogBo catalogBo=listBof.getCatalogBoList(params,MktTypeEnum.EBAY);
//				if(null!=catalogBo){
//					brandBo.setSelectedCatalogTree(catalogBo);
//				}
				
				/*
				 * 清除隐藏的运营分类树节点  2014-04-10 14:56
				 */
				CatalogBo catalogBo=listBof.getCatalogBoList2(params,MktTypeEnum.XIU);
				if(null!=catalogBo){
					brandBo.setSelectedCatalogTree(CommonUtil.deleteDisplay2(catalogBo));
				}
			}
			return brandBo;
		}
		
		
		// 生成已选择的价格条件
		if(null != params.getStartPrice() || null != params.getEndPrice()){
			facetFilterBo = facetFilterBof.formatSelectCustomPriceRangeFacetFilter(params.getStartPrice(), params.getEndPrice());
			if(null != facetFilterBo){
				brandBo.addSelectFacetBoList(facetFilterBo);
				brandBo.setPriceFacetBo(null);
			}
		}else if(null != params.getPriceRangeEnum()){
			facetFilterBo = facetFilterBof.formatSelectFacetFilter(FacetTypeEnum.PRICE, params.getPriceRangeEnum().getOrder());
			if(null != facetFilterBo){
				brandBo.addSelectFacetBoList(facetFilterBo);
				brandBo.setPriceFacetBo(null);
			}
		}
		
		// 生成已选择的属性条件
		if(CollectionUtils.isNotEmpty(params.getAttrValIdList()) && null != brandBo.getAttrFacetBoList() && brandBo.getAttrFacetBoList().size()>0){
        	FacetFilterBo _ffBo;
        	List<Long> attrValIds;
        	// 多值属性值选择，过滤已经选择的属性项。	William.zhang	20130515
        	for (List<String> list : params.getAttrValIdList()) {
        		if(null == list)
        			continue;
        		attrValIds = new ArrayList<Long>(list.size());
        		for (String attrValIdStr : list) {
					if(XiuSearchStringUtils.isIntegerNumber(attrValIdStr))
						attrValIds.add(Long.valueOf(attrValIdStr));
				}
        		for (int i=0;i< brandBo.getAttrFacetBoList().size();i++) {
                	_ffBo = brandBo.getAttrFacetBoList().get(i);
                    facetFilterBo = facetFilterBof.formatSelectFacetFilter(_ffBo, attrValIds);
                    if(null != facetFilterBo){
                    	brandBo.addSelectFacetBoList(facetFilterBo);
                    	brandBo.removeAttrFacetBoList(facetFilterBo.getFacetFieldName());
                    }
                }
			}
		}
		
		//价格整合到筛选项list中，并排到最后
		if(null != brandBo.getPriceFacetBo()){
			brandBo.addAttrFacetBoList(brandBo.getPriceFacetBo());
		}
		
		// 生成已选择的运营分类树
		if(null != brandBo.getCatalogBoList()){
			// 过滤分类树，未选中的兄弟节点需要删除 注释日期 2014-07-24 16:18
			// catalogBof.filterCatalogTreeDeleteUnSelectedSiblingsItem(brandBo.getCatalogBoList());
			for (CatalogBo bo : brandBo.getCatalogBoList()) {
				if(bo.isSelected()){
					brandBo.setSelectedCatalogTree(bo);
					break;
				}
			}
			brandBo.setSelectedCatalog(catalogBof.getSelectedCatalogFromSelectedCatalogTree(brandBo.getSelectedCatalogTree()));
			
			/*
			 * 清除隐藏的运营分类树节点  2014-04-11 11:56
			 */
			brandBo.setSelectedCatalogTree(CommonUtil.deleteDisplay2(brandBo.getSelectedCatalogTree()));
		}
		

		/*
		 * 清除隐藏的运营分类树节点  2014-04-10 14:56
		 */
		if(brandBo.getCatalogBoList()!=null){
			List<CatalogBo> tmpLst=new ArrayList<CatalogBo>();
			CatalogBo tmpCb=null;
			for(CatalogBo cb:brandBo.getCatalogBoList()){
				tmpCb=CommonUtil.deleteDisplay2(cb);
				if(tmpCb.getCatalogModel().getParentCatalogId()==0 && (tmpCb.getChildCatalog()==null || tmpCb.getChildCatalog().size()==0)){
					continue;
				}
				tmpLst.add(tmpCb);
			}
			brandBo.setCatalogBoList(tmpLst);
		}
		
		
		//ebay推荐插码
  		if(null!=result&&null!=result.getBeanList()){
  			brandBo.setRecommenBrandIds(params.getBrandId()+"");
  			String[] recommendInfo=CommonUtil.matchRecommendInfo(MktTypeEnum.EBAY, params.getCatalogId(), result.getBeanList());
  			if(null!=recommendInfo){
  				brandBo.setRecommendItemIds(recommendInfo[0]);
  				brandBo.setRecommendCatIds(recommendInfo[1]);
  			}
  		}
  		
		// 生成其他信息
		brandBo.setPage(params.getPage());
		//brandBo.setFatParams(params);
		return brandBo;
	}
	
	/**
	 * 将当前选中的分类解析成水平结构
	 * @param selectList
	 * @param catalogBoList
	 */
	private void parsePlaneSelectCatalogBo(List<CatalogBo> selectList,List<CatalogBo> catalogBoList){
		if(null == catalogBoList)
			return;
		for (CatalogBo bo : catalogBoList) {
			if(bo.isSelected()){
				selectList.add(bo);
				this.parsePlaneSelectCatalogBo(selectList, bo.getChildCatalog());
				break;
			}
		}
	}
	
	private BrandBo setBrandInfo(Long brandId,BrandBo brandBo){
		XDataBrand xDataBrand=brandService.getBrandInfo(brandId);
		if(null!=xDataBrand){
			String brandName=null;
			if(null != xDataBrand){
				if(null != xDataBrand.getMainName()){
					brandName = xDataBrand.getMainName();
				}else if(null != xDataBrand.getBrandName()){
					brandName = xDataBrand.getBrandName();
				}else if(null != xDataBrand.getEnName()){
					brandName = xDataBrand.getEnName();
				}else{
					brandName = StringUtils.EMPTY;
				}
				if(null != xDataBrand.getBrandName())
					brandBo.setBrandNameCN(xDataBrand.getBrandName());
				if(null != xDataBrand.getEnName())
					brandBo.setBrandNameEN(xDataBrand.getEnName());
			}
//			if (null != xDataBrand && null != xDataBrand.getMainName()) {
//				brandName = xDataBrand.getMainName();
//			} else if (null != xDataBrand && null != xDataBrand.getBrandName()) {
//				brandName = xDataBrand.getBrandName();
//			} else if (null != xDataBrand && null != xDataBrand.getEnName()) {
//				brandName = xDataBrand.getEnName();
//			} else {
//				brandName = "";
//			}
			brandBo.setBrandName(brandName);
			brandBo.setStory(null==xDataBrand.getStory()?"":xDataBrand.getStory());
			//先使用授权图片,不存在则使用非授权图片 
			if(StringUtils.isNotEmpty(xDataBrand.getAuthimgurl())){
				brandBo.setStoreImgUrl(xDataBrand.getAuthimgurl());
			}else{
				brandBo.setStoreImgUrl(null==xDataBrand.getStoreimgurl()?"":xDataBrand.getStoreimgurl());
			}
			
			// 设置第二张图片
			if(StringUtils.isNotEmpty(xDataBrand.getBannerimgname2())){
				brandBo.setBannerImageName2(xDataBrand.getBannerimgname2());
			}else{
				brandBo.setBannerImageName2(null == xDataBrand.getBannerimgname2() ? "" : xDataBrand.getBannerimgname2());
			}
		}else{
			brandBo.setBrandName("");
			brandBo.setStory("");
			brandBo.setStoreImgUrl("");
			brandBo.setBannerImageName2("");
		}
		return brandBo;
	}
	
	@Override
	public boolean cleanCacheOfBrandInfo(Long brandId, int flag) {
		boolean result = false;
		try{
			if(1 == flag){
				CacheManage.clear(CacheTypeEnum.BRAND_INFO);
				result = true;
			}else{
				if(null != brandId){
					CacheManage.remove(String.valueOf(brandId), CacheTypeEnum.BRAND_INFO);
					result = true;
				}
			}
		}catch(Exception ex){
			System.out.println("BrandBofImpl.cleanCacheOfBrandInfo删除品牌缓存异常 | message=" + ex.getMessage());
			ex.printStackTrace();
		}
		return result;
	}



	@Autowired
	private GoodsSolrService goodsSolrService;
	@Autowired
	private CatalogBof catalogBof;
	@Autowired
	private FacetFilterBof facetFilterBof;
	@Autowired
	private BrandService brandService;
	@Autowired
	private AttrGroupService attrGroupService;
//	@Autowired
//	private CatalogService catalogService;
	@Autowired
	private ListBof listBof;
//	@Autowired
//	private BrandBusinessOptService brandBusinessOptService;
	
}
