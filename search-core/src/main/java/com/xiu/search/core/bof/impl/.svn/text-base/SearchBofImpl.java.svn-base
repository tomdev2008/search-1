package com.xiu.search.core.bof.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.response.FacetField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.search.core.bo.CatalogBo;
import com.xiu.search.core.bo.FacetFilterBo;
import com.xiu.search.core.bo.FacetFilterBo.FacetTypeEnum;
import com.xiu.search.core.bo.FacetFilterValueBo;
import com.xiu.search.core.bo.GoodsItemBo;
import com.xiu.search.core.bo.SearchBo;
import com.xiu.search.core.bo.XTagBo;
import com.xiu.search.core.bof.CatalogBof;
import com.xiu.search.core.bof.FacetFilterBof;
import com.xiu.search.core.bof.SearchBof;
import com.xiu.search.core.bof.XTagBof;
import com.xiu.search.core.catalog.BrandModel;
import com.xiu.search.core.catalog.XiuBrandInfoCache;
import com.xiu.search.core.config.XiuSearchProperty;
import com.xiu.search.core.model.AttrGroupJsonModel;
import com.xiu.search.core.model.CatalogBusinessOptModel;
import com.xiu.search.core.service.AttrGroupService;
import com.xiu.search.core.service.CatalogBusinessOptService;
import com.xiu.search.core.service.CatalogService;
import com.xiu.search.core.service.GoodsSolrService;
import com.xiu.search.core.service.XTagService;
import com.xiu.search.core.solr.enumeration.MktTypeEnum;
import com.xiu.search.core.solr.index.GoodsIndexFieldEnum;
import com.xiu.search.core.solr.model.GoodsSolrModel;
import com.xiu.search.core.solr.model.XiuSKUIndexModel;
import com.xiu.search.core.solr.params.SearchFatParams;
import com.xiu.search.core.util.CommonUtil;
import com.xiu.search.core.util.Constants;
import com.xiu.search.core.util.XiuSearchStringUtils;
import com.xiu.search.dao.model.XTag;
import com.xiu.search.solrj.service.SearchResult;
import com.xiu.solr.lexicon.client.model.QueryParseModel;
import com.xiu.solr.lexicon.client.model.QueryParseModel.UnitEnum;
import com.xiu.solr.lexicon.client.model.QueryParseUnit;
import com.xiu.solr.lexicon.client.service.QueryParseService;

@Service("searchBof")
public class SearchBofImpl implements SearchBof {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchBofImpl.class);
	// TODO xiu	findSearchXiuPageResult	William.zhang
	@Override
	public SearchBo findSearchXiuPageResult(SearchFatParams params) {
		SearchBo searchBo = new SearchBo();
		
		// 品牌街，奢侈品，海外直发
		searchBo.setShowType(params.getShowType());
		
		SearchResult<GoodsSolrModel> result;
		List<GoodsSolrModel> xiuSolrModelList;
		
		//确认来源渠道对应的运营分类,默认为官网
		GoodsIndexFieldEnum oClassPath=GoodsIndexFieldEnum.OCLASS_IDS;
		String[] searchFacetFieldArr = new String[]{GoodsIndexFieldEnum.OCLASS_IDS.fieldName()};
		
		// 用户属性项筛选的map
		// key - 维度字段，value - 维度名称
		Map<String, AttrGroupJsonModel> attrFacetFieldLinkMap = null;
		if(null != params.getCatalogId()){
			String[] attrFacetFields = null;
			// 此方法返回参数为LinkHashMap，在此方法内保证有序
			attrFacetFieldLinkMap = attrGroupService.getAttrGroupIdNameListWithInherit(params.getCatalogId().longValue());
			if(null != attrFacetFieldLinkMap){
				attrFacetFields = attrFacetFieldLinkMap.keySet().toArray(new String[attrFacetFieldLinkMap.size()]);
			}
			result = goodsSolrService.findSearchXiuSolr(params, true, attrFacetFields,searchFacetFieldArr);
			if(result == null){
				return searchBo;
			}
			
			params.getPage().setRecordCount((int)result.getTotalHits());
			if(params.getPage().getPageNo() > params.getPage().getPageCount()){
				// 若当前页数大于总页数，则属于用户输入参数错误，需重新搜索
//				params.getPage().setPageNo(1);
//				return this.findSearchXiuPageResult(params);
				// 用户参数错误时，则返回到无结果页面		William.zhang	20130704
				return searchBo;
			}
			xiuSolrModelList = result.getBeanList();
			List<GoodsItemBo> goodsItemBoList = CommonUtil.transformXiuItemBo(params.getKeyword(), xiuSolrModelList);
			
			/*
			 * 尺码展示
			 * 添加日期：2014-03-21 11：20
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
			
			searchBo.setGoodsItemList(goodsItemBoList);
		}else{
			// QP
			QueryParseModel queryParseModel = queryParseService.findQueryParseModel(params.getKeyword(), 10);
			// 获取商业规则
			CatalogBusinessOptModel businessModel = catalogBusinessOptService.getBusinessByQueryParseModel(queryParseModel);

			// 查找商品
			result = goodsSolrService.findSearchXiuSolr(params, false, null, searchFacetFieldArr,businessModel,false);
			if(result == null){
				return searchBo;
			}

			params.getPage().setRecordCount((int)result.getTotalHits());
			if(params.getPage().getPageNo() > params.getPage().getPageCount()){
				// 若当前页数大于总页数，则属于用户输入参数错误，需重新搜索(走淘宝搜索逻辑)
//				params.getPage().setPageNo(1);
//				return this.findSearchXiuPageResult(params);
				// 用户参数错误时，则返回到无结果页面		William.zhang	20130704
				return searchBo;
			}
			xiuSolrModelList = result.getBeanList();
			if(null != xiuSolrModelList && xiuSolrModelList.size()>0){
				// 如果有商品则转换商品
				List<GoodsItemBo> goodsItemBoList = CommonUtil.transformXiuItemBo(params.getKeyword(), xiuSolrModelList);
				
				/*
				 * 尺码展示
				 * 添加日期：2014-03-21 11：20
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
				searchBo.setGoodsItemList(goodsItemBoList);
				String errorTerm = transformErrorTerms(queryParseModel);
				if(needErrorCorrection(params) && StringUtils.isNotBlank(errorTerm)){
					// 错误纠正
					searchBo.setTermAutoCorrect(errorTerm);
				}else
					// 查找相关搜索
				if(needRelateTerms(params)){
					searchBo.setRelatedTerms(this.transformRelatedTerms(queryParseModel));
				}
			} else {
				// 错误词纠正
				if(needErrorCorrection(params)){
					String errorTerm = transformErrorTerms(queryParseModel);
					if(StringUtils.isNotBlank(errorTerm)){
						searchBo.setTermErrorCorrect(errorTerm);
						params.getPage().setPageNo(1);
						SearchFatParams paramsError = this.initNameErrorParams(params, errorTerm);
						result = goodsSolrService.findSearchXiuSolr(paramsError, false, null, new String[]{oClassPath.fieldName()});
						if(null == result)
							return searchBo;
						searchBo.putMktItemCount(MktTypeEnum.XIU,(int)result.getTotalHits());
						params.getPage().setRecordCount((int)result.getTotalHits());
						xiuSolrModelList = result.getBeanList();
						List<GoodsItemBo> goodsItemBoList = CommonUtil.transformXiuItemBo(params.getKeyword(), xiuSolrModelList);
						
						/*
						 * 尺码展示
						 * 添加日期：2014-03-21 11：20
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
						
						searchBo.setGoodsItemList(goodsItemBoList);
					}
				}
			}
			
		}
		// 进行分类，品牌，价格及属性项的筛选
		FacetFilterBo facetFilterBo = null;
		if(null != result.getFacetFields()){
			for (FacetField f: result.getFacetFields()) {
				if(GoodsIndexFieldEnum.BRAND_ID_NAME.fieldName().equals(f.getName())){
					// 如果是品牌筛选
					facetFilterBo = facetFilterBof.parseBrandFacetFilter(f);
					if(null != facetFilterBo){
						searchBo.setBrandFacetBo(facetFilterBo);
						searchBo.setLetterBrandMap(CommonUtil.parseBrandLetterGroupMap(facetFilterBo));
						
						//新增业务逻辑需要存储每个品牌的拼音名称		William.zhang  20130506
	                    searchBo.setFacetFilterValueBoList(facetFilterBo.getFacetValueBos());
						searchBo.setFacetFilterValueBoJSON(this.facetFilterValueBoToJSON(facetFilterBo.getFacetValueBos()));
					}
				}else if(oClassPath.fieldName().equals(f.getName())){
					// 如果是运营分类
					List<CatalogBo> catalogBoList = null;
					
					//新旧运营分类切换 2013-05-09 16：09
					catalogBoList = catalogBof.fetchCatalogBoTreeListForXiu(f, params.getCatalogId());

					if(null != catalogBoList){
						searchBo.setCatalogBoList(catalogBoList);
					}
					
					
					/*
					 * 设置所有一级分类  lvshuding 2014-04-18 15:50
					 */
					searchBo.setFirstCataList(catalogBof.fetchCatalogBoTreeListForXiu(f, null));
					
					
					
					
				}else if(f.getName().indexOf(GoodsIndexFieldEnum.ATTRS_PREFIX.fieldName())==0){
					// 如果是属性项维度的筛选
				//	facetFilterBo = facetFilterBof.parseAttrFacetFilter(f,params.getAttrValIds(),attrFacetFieldLinkMap);
					
					//新的业务逻辑	支持多选	William.zhang	20130508
                	facetFilterBo = facetFilterBof.parseAttrFacetFilter(f,params.getAttrValIdList(),attrFacetFieldLinkMap);
                	
					if(null != facetFilterBo)
						searchBo.addAttrFacetBoList(facetFilterBo);
				}
			}
		}

		// 生成价格范围查询的筛选
		facetFilterBo = facetFilterBof.parsePriceRangeFacetFilter(result.getFacetQuery());
		if(null != facetFilterBo)
			searchBo.setPriceFacetBo(facetFilterBo);
		// 生成已选择的运营分类平面结构
		if(searchBo.getCatalogBoList()!=null){
			List<CatalogBo> selectCatalogPlaneList = catalogBof.parsePlaneSelectCatalogBo(searchBo.getCatalogBoList());
			searchBo.setSelectCatalogPlaneList(selectCatalogPlaneList);
			
			/*
			 * 对各级兄弟节点赋值   2014-05-06 15:05
			 */
			if(selectCatalogPlaneList.size()>0){
				SearchFatParams bfp=new SearchFatParams(1);
				bfp.setKeyword(params.getKeyword());
				
				SearchResult<GoodsSolrModel> tmpRst = goodsSolrService.findSearchXiuSolr(bfp, false, null, new String[]{oClassPath.fieldName()},null,false);
				
				if(null!=tmpRst){
					if(null != tmpRst.getFacetFields()){
						for (FacetField f: tmpRst.getFacetFields()) {
							if(oClassPath.fieldName().equals(f.getName())){
								// 如果是运营分类
								catalogBof.fetchBrotherCatalogBoListForXiu(f, params.getCatalogId(),searchBo);
								break;
							}
						}
					}
				}
			}else{
			
				/*
				 * 选中的一级分类设置 isSelected,并删除隐藏的分类   2014-04-18 15:50
				 */
				List<CatalogBo> fcList=searchBo.getFirstCataList();
				if(fcList!=null && fcList.size()>0){
					if(searchBo.getSelectCatalogPlaneList()!=null && searchBo.getSelectCatalogPlaneList().size()>0){
						int selectedId = searchBo.getSelectCatalogPlaneList().get(0).getCatalogId();
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
		//通过各筛选结果，来判断是否硬性添加”其他“筛选项
		/*try {
			this.mergeOtherAttr(result, searchBo, params, attrFacetFieldLinkMap);
		} catch (Exception e) {
			LOGGER.error("通过各筛选结果，来判断是否硬性添加”其他“筛选项出现异常------------>>",e);
		}*/
		// TODO 生成已选择filter条件，后期请将必要数据放入缓存，降低索引facet开销
		// 生成已选择的品牌条件
		if(null != searchBo.getBrandFacetBo() && params.getBrandIds() != null){
			//新增业务逻辑，用于支持多选时展示品牌名称		William.zhang	20130507
			facetFilterBo = facetFilterBof.formatSelectFacetFilter(searchBo.getBrandFacetBo(), params.getBrandIds());
			if(facetFilterBo != null){
				searchBo.addSelectFacetBoList(facetFilterBo);
				searchBo.setBrandFacetBo(null);
				searchBo.setLetterBrandMap(null);
            }

		}
		// 生成已选择的价格条件
		if(null != params.getStartPrice() || null != params.getEndPrice()){
			facetFilterBo = facetFilterBof.formatSelectCustomPriceRangeFacetFilter(params.getStartPrice(), params.getEndPrice());
			if(null != facetFilterBo){
				searchBo.addSelectFacetBoList(facetFilterBo);
				searchBo.setPriceFacetBo(null);
			}
		}else if(null != params.getPriceRangeEnum()){
			facetFilterBo = facetFilterBof.formatSelectFacetFilter(FacetTypeEnum.PRICE, params.getPriceRangeEnum().getOrder());
			if(null != facetFilterBo){
				searchBo.addSelectFacetBoList(facetFilterBo);
				searchBo.setPriceFacetBo(null);
			}
		}
		// 生成已选择的属性条件
		if(CollectionUtils.isNotEmpty(params.getAttrValIdList())  && null != searchBo.getAttrFacetBoList() && searchBo.getAttrFacetBoList().size()>0){
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
        		for (int i=0;i< searchBo.getAttrFacetBoList().size();i++) {
                	_ffBo = searchBo.getAttrFacetBoList().get(i);
                    facetFilterBo = facetFilterBof.formatSelectFacetFilter(_ffBo, attrValIds);
                    if(null != facetFilterBo){
                    	searchBo.addSelectFacetBoList(facetFilterBo);
                    	searchBo.removeAttrFacetBoList(facetFilterBo.getFacetFieldName());
                    }
                }
			}
		}
		
		//价格整合到筛选项list中，并排到最后
		if(null != searchBo.getPriceFacetBo()){
			searchBo.addAttrFacetBoList(searchBo.getPriceFacetBo());
		}
		//删除只有一个属性值的属性项
		this.removeOnlyOneValAttr(searchBo);
		// 生成已选择的运营分类树
		if(null != searchBo.getCatalogBoList()){
			
			// 过滤分类树，未选中的兄弟节点需要删除,注释日期：2014-04-18 16：15
			//catalogBof.filterCatalogTreeDeleteUnSelectedSiblingsItem(searchBo.getCatalogBoList());
			
			
			
			for(CatalogBo bo : searchBo.getCatalogBoList()){
				if(bo.isSelected()){
					searchBo.setSelectedCatalogTree(bo);
					break;
				}
			}
			searchBo.setSelectedCatalog(catalogBof.getSelectedCatalogFromSelectedCatalogTree(searchBo.getSelectedCatalogTree()));
			
			/*
			 * 清除隐藏的运营分类树节点  2014-04-11 11:56
			 */
			searchBo.setSelectedCatalogTree(CommonUtil.deleteDisplay2(searchBo.getSelectedCatalogTree()));
		}
		
		//官网推荐插码
		if(null!=result&&null!=result.getBeanList()){
			//获取选中品牌编号
			String brandIds="";
			if(null!=searchBo.getSelectFacetBoList()&&searchBo.getSelectFacetBoList().size()>0){
				for (FacetFilterBo facetFilter : searchBo.getSelectFacetBoList()) {
					if(FacetTypeEnum.BRAND==facetFilter.getFacetType()){
						for (FacetFilterValueBo valueBo : facetFilter.getFacetValueBos()) {
							brandIds=brandIds.length()>0?brandIds+","+valueBo.getId():valueBo.getId()+"";
						}
					}
				}
			}
			
			String[] recommendInfo=CommonUtil.matchRecommendInfo(MktTypeEnum.XIU,params.getCatalogId(), result.getBeanList());
			if(null!=recommendInfo){
				searchBo.setRecommendItemIds(recommendInfo[0]);
				searchBo.setRecommendCatIds(recommendInfo[1]);
				searchBo.setRecommenBrandIds(brandIds);
			}
		}
		
		/*
		 * 清除隐藏的运营分类树节点  2014-04-10 15:56
		 */
		if(searchBo.getCatalogBoList()!=null){
			List<CatalogBo> tmpLst=new ArrayList<CatalogBo>();
			CatalogBo tmpCb=null;
			for(CatalogBo cb:searchBo.getCatalogBoList()){
				tmpCb=CommonUtil.deleteDisplay2(cb);
				if(tmpCb.getCatalogModel().getParentCatalogId()==0 && (tmpCb.getChildCatalog()==null || tmpCb.getChildCatalog().size()==0)){
					continue;
				}
				tmpLst.add(tmpCb);
			}
			searchBo.setCatalogBoList(tmpLst);
		}
		
		// 生成其他信息
		searchBo.setPage(params.getPage());
		searchBo.setFatParams(params);
		return searchBo;
	}
	/**
	 * 过滤掉只有一个属性值的属性项
	 * @param listBo
	 */
	private void removeOnlyOneValAttr(SearchBo searchBo) {
		List<FacetFilterBo> facetFilterBos = searchBo.getAttrFacetBoList();
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
	private void mergeOtherAttr(SearchResult<GoodsSolrModel> result, SearchBo searchBo, SearchFatParams params, Map<String, AttrGroupJsonModel> attrIdNameMap) throws Exception{
		List<FacetFilterBo> facetFilterBoList = searchBo.getAttrFacetBoList();
		List<List<String>> selectedAttrValIds = params.getAttrValIdList();
		//已选择的过滤值
		StringBuilder filterStringBuilder = null;
		//属性项map
		Map<String, FacetFilterBo> facetFilterBoMap = null;
		if (selectedAttrValIds != null && !selectedAttrValIds.isEmpty()) {
			filterStringBuilder = new StringBuilder();
			for (int i = 0; i < selectedAttrValIds.size(); i++) {
				if (selectedAttrValIds.get(i) == null || selectedAttrValIds.get(i).isEmpty()) {
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
							searchBo.addAttrFacetBoList(tempBo);
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
						searchBo.addAttrFacetBoList(tempBo);
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



	@Override
	public SearchBo findSearchEbayPageResult(SearchFatParams params){
		SearchBo searchBo = new SearchBo();
		SearchResult<GoodsSolrModel> result;
		List<GoodsSolrModel> xiuSolrModelList;
		
		/*
		 * 新旧运营分类切换  2013-05-09 16:03
		 */
		GoodsIndexFieldEnum oClassPath=GoodsIndexFieldEnum.OCLASS_IDS;;
		
		// 用户属性项筛选的map
		// key - 维度字段，value - 维度名称
		Map<String, AttrGroupJsonModel> attrFacetFieldLinkMap = null;
		if(null != params.getCatalogId()){
			String[] attrFacetFields = null;
			// 此方法返回参数为LinkHashMap，在此方法内保证有序
			attrFacetFieldLinkMap = attrGroupService.getAttrGroupIdNameListWithInherit(params.getCatalogId().longValue());
			if(null != attrFacetFieldLinkMap)
				attrFacetFields = attrFacetFieldLinkMap.keySet().toArray(new String[attrFacetFieldLinkMap.size()]);
			result = goodsSolrService.findSearchXiuSolr(params, true, attrFacetFields,new String[]{oClassPath.fieldName()});
			if(result == null){
				return searchBo;
			}
			params.getPage().setRecordCount((int)result.getTotalHits());
			if(params.getPage().getPageNo() > params.getPage().getPageCount()){
//				// 若当前页数大于总页数，则属于用户输入参数错误，需重新搜索
//				params.getPage().setPageNo(1);
//				//return this.findSearchPageResult(params);
//				//新的业务逻辑	支持多选	William.zhang	20130508
//				return this.findSearchEbayPageResult(params);
				// 用户参数错误时，则返回到无结果页面		William.zhang	20130704
				return searchBo;
			}
			xiuSolrModelList = result.getBeanList();
			List<GoodsItemBo> goodsItemBoList = CommonUtil.transformXiuItemBo(params.getKeyword(), xiuSolrModelList);
			searchBo.setGoodsItemList(goodsItemBoList);
		}else{
			// QP
			QueryParseModel queryParseModel = queryParseService.findQueryParseModel(params.getKeyword(), 10, com.xiu.solr.lexicon.client.common.MktTypeEnum.EBAY);
			// 获取商业规则
			CatalogBusinessOptModel businessModel = catalogBusinessOptService.getBusinessByQueryParseModel(queryParseModel);
			// 查找商品
			result = goodsSolrService.findSearchXiuSolr(params, false, null, new String[]{oClassPath.fieldName()},businessModel,false);
			if(result == null){
				return searchBo;
			}
			params.getPage().setRecordCount((int)result.getTotalHits());
			if(params.getPage().getPageNo() > params.getPage().getPageCount()){
				// 若当前页数大于总页数，则属于用户输入参数错误，需重新搜索(走淘宝搜索逻辑)
//				params.getPage().setPageNo(1);
////				return this.findSearchPageResult(params);
//				//新的业务逻辑	支持多选	William.zhang	20130508
//				return this.findSearchEbayPageResult(params);
				
				// 用户参数错误时，则返回到无结果页面		William.zhang	20130704
				return searchBo;
			}
			xiuSolrModelList = result.getBeanList();
			if(null != xiuSolrModelList && xiuSolrModelList.size()>0){
				// 如果有商品则转换商品
				List<GoodsItemBo> goodsItemBoList = CommonUtil.transformXiuItemBo(params.getKeyword(), xiuSolrModelList);
				searchBo.setGoodsItemList(goodsItemBoList);
				String errorTerm = transformErrorTerms(queryParseModel);
				if(needErrorCorrection(params) && StringUtils.isNotBlank(errorTerm)){
					// 错误纠正
					searchBo.setTermAutoCorrect(errorTerm);
				}else
					// 查找相关搜索
				if(needRelateTerms(params)){
					searchBo.setRelatedTerms(this.transformRelatedTerms(queryParseModel));
				}
			} else {
				// 错误词纠正
				if(needErrorCorrection(params)){
					String errorTerm = transformErrorTerms(queryParseModel);
					if(StringUtils.isNotBlank(errorTerm)){
						searchBo.setTermErrorCorrect(errorTerm);
						params.getPage().setPageNo(1);
						SearchFatParams paramsError = this.initNameErrorParams(params, errorTerm);
						result = goodsSolrService.findSearchXiuSolr(paramsError, false, null, new String[]{oClassPath.fieldName()});
						if(null == result)
							return searchBo;
//						searchBo.putMktItemCount(MktTypeEnum.XIU,(int)result.getTotalHits());
						params.getPage().setRecordCount((int)result.getTotalHits());
						xiuSolrModelList = result.getBeanList();
						List<GoodsItemBo> goodsItemBoList = CommonUtil.transformXiuItemBo(params.getKeyword(), xiuSolrModelList);
						searchBo.setGoodsItemList(goodsItemBoList);
					}
				}
			}
			
		}
		// 进行分类，品牌，价格及属性项的筛选
		FacetFilterBo facetFilterBo = null;
		if(null != result.getFacetFields()){
			for (FacetField f: result.getFacetFields()) {
				if(GoodsIndexFieldEnum.BRAND_ID_NAME.fieldName().equals(f.getName())){
					// 如果是品牌筛选
					facetFilterBo = facetFilterBof.parseBrandFacetFilter(f);
					if(null != facetFilterBo){
						searchBo.setBrandFacetBo(facetFilterBo);
						searchBo.setLetterBrandMap(CommonUtil.parseBrandLetterGroupMap(facetFilterBo));
						//新增业务逻辑需要存储每个品牌的拼音名称		William.zhang  20130506
						searchBo.setFacetFilterValueBoList(facetFilterBo.getFacetValueBos());
						searchBo.setFacetFilterValueBoJSON(this.facetFilterValueBoToJSON(facetFilterBo.getFacetValueBos()));
					}
				}else if(oClassPath.fieldName().equals(f.getName())){
					// 如果是运营分类
					List<CatalogBo> catalogBoList = null;
					catalogBoList = catalogBof.fetchCatalogBoTreeListForEbay(f, params.getCatalogId());
					searchBo.setCatalogBoList(catalogBoList);
				}else if(f.getName().indexOf(GoodsIndexFieldEnum.ATTRS_PREFIX.fieldName())==0){
					// 如果是属性项维度的筛选
					//facetFilterBo = facetFilterBof.parseAttrFacetFilter(f,params.getAttrValIds(),attrFacetFieldLinkMap);
					//新的业务逻辑	支持多选	William.zhang	20130508
                	facetFilterBo = facetFilterBof.parseAttrFacetFilter(f,params.getAttrValIdList(),attrFacetFieldLinkMap);
					if(null != facetFilterBo)
						searchBo.addAttrFacetBoList(facetFilterBo);
				}
			}
		}
		
		// 生成价格范围查询的筛选
		facetFilterBo = facetFilterBof.parsePriceRangeFacetFilter(result.getFacetQuery());
		if(null != facetFilterBo)
			searchBo.setPriceFacetBo(facetFilterBo);
		// 生成已选择的运营分类平面结构
		if(searchBo.getCatalogBoList()!=null){
			List<CatalogBo> selectCatalogPlaneList = catalogBof.parsePlaneSelectCatalogBo(searchBo.getCatalogBoList());
			searchBo.setSelectCatalogPlaneList(selectCatalogPlaneList);
		}
		
		// TODO 生成已选择filter条件，后期请将必要数据放入缓存，降低索引facet开销
		// 生成已选择的品牌条件
		if(null != searchBo.getBrandFacetBo() && params.getBrandIds() != null){
			facetFilterBo = facetFilterBof.formatSelectFacetFilter(searchBo.getBrandFacetBo(), params.getBrandIds());
			searchBo.addSelectFacetBoList(facetFilterBo);
			searchBo.setBrandFacetBo(null);
			searchBo.setLetterBrandMap(null);
		}
		// 生成已选择的价格条件
		if(null != params.getStartPrice() || null != params.getEndPrice()){
			facetFilterBo = facetFilterBof.formatSelectCustomPriceRangeFacetFilter(params.getStartPrice(), params.getEndPrice());
			if(null != facetFilterBo){
				searchBo.addSelectFacetBoList(facetFilterBo);
				searchBo.setPriceFacetBo(null);
			}
		}else if(null != params.getPriceRangeEnum()){
			facetFilterBo = facetFilterBof.formatSelectFacetFilter(FacetTypeEnum.PRICE, params.getPriceRangeEnum().getOrder());
			if(null != facetFilterBo){
				searchBo.addSelectFacetBoList(facetFilterBo);
				searchBo.setPriceFacetBo(null);
			}
		}
		// 生成已选择的属性条件
		if(CollectionUtils.isNotEmpty(params.getAttrValIdList()) && null != searchBo.getAttrFacetBoList() && searchBo.getAttrFacetBoList().size()>0){
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
        		for (int i=0;i< searchBo.getAttrFacetBoList().size();i++) {
                	_ffBo = searchBo.getAttrFacetBoList().get(i);
                    facetFilterBo = facetFilterBof.formatSelectFacetFilter(_ffBo, attrValIds);
                    if(null != facetFilterBo){
                    	searchBo.addSelectFacetBoList(facetFilterBo);
                    	searchBo.removeAttrFacetBoList(facetFilterBo.getFacetFieldName());
                    }
                }
			}
		}
		
		//价格整合到筛选项list中，并排到最后
		if(null != searchBo.getPriceFacetBo()){
			searchBo.addAttrFacetBoList(searchBo.getPriceFacetBo());
		}
		
		// 生成已选择的运营分类树
		if(null != searchBo.getCatalogBoList()){
			// 过滤分类树，未选中的兄弟节点需要删除
			catalogBof.filterCatalogTreeDeleteUnSelectedSiblingsItem(searchBo.getCatalogBoList());
			for (CatalogBo bo : searchBo.getCatalogBoList()) {
				if(bo.isSelected()){
					searchBo.setSelectedCatalogTree(bo);
					break;
				}
			}
			searchBo.setSelectedCatalog(catalogBof.getSelectedCatalogFromSelectedCatalogTree(searchBo.getSelectedCatalogTree()));
			
			/*
			 * 清除隐藏的运营分类树节点  2014-04-11 11:56
			 */
			searchBo.setSelectedCatalogTree(CommonUtil.deleteDisplay2(searchBo.getSelectedCatalogTree()));
		}
		
		
		/*
		 * 清除隐藏的运营分类树节点  2014-04-10 15:56
		 */
		if(searchBo.getCatalogBoList()!=null){
			List<CatalogBo> tmpLst=new ArrayList<CatalogBo>();
			CatalogBo tmpCb=null;
			for(CatalogBo cb:searchBo.getCatalogBoList()){
				tmpCb=CommonUtil.deleteDisplay2(cb);
				if(tmpCb.getCatalogModel().getParentCatalogId()==0 && (tmpCb.getChildCatalog()==null || tmpCb.getChildCatalog().size()==0)){
					continue;
				}
				tmpLst.add(tmpCb);
			}
			searchBo.setCatalogBoList(tmpLst);
		}
		
		//ebay推荐插码
		if(null!=result&&null!=result.getBeanList()){
			//获取选中品牌编号
			String brandIds="";
			if(null!=searchBo.getSelectFacetBoList()&&searchBo.getSelectFacetBoList().size()>0){
				for (FacetFilterBo facetFilter : searchBo.getSelectFacetBoList()) {
					if(FacetTypeEnum.BRAND==facetFilter.getFacetType()){
						for (FacetFilterValueBo valueBo : facetFilter.getFacetValueBos()) {
							brandIds=brandIds.length()>0?brandIds+","+valueBo.getId():valueBo.getId()+"";
						}
					}
				}
			}
			
			String[] recommendInfo=CommonUtil.matchRecommendInfo(MktTypeEnum.EBAY,params.getCatalogId(), result.getBeanList());
			if(null!=recommendInfo){
				searchBo.setRecommendItemIds(recommendInfo[0]);
				searchBo.setRecommendCatIds(recommendInfo[1]);
				searchBo.setRecommenBrandIds(brandIds);
			}
		}
		
		// 生成其他信息
		searchBo.setPage(params.getPage());
		searchBo.setFatParams(params);
		return searchBo;
	}
	// TODO ebay findSearchTagsPageResult	William.zhang
	public SearchBo findSearchTagsPageResult(SearchFatParams params){
		SearchBo searchBo = new SearchBo();
		SearchResult<GoodsSolrModel> result;
		List<GoodsSolrModel> xiuSolrModelList;
		XTagBo xTagBo = new XTagBo();
		//获取一个标签的ID和标签的名称
		XTag xTags = null;
		if(null != params.getSearchTags()){
			xTags = xTagService.findXTagMapByName(params.getSearchTags());
		}
		if(null != xTags && null != xTags.getId()){
			params.setSearchTagsId(xTags.getId());
			xTagBo.setId(xTags.getId());
			xTagBo.setName(xTags.getName());
		} else {
			xTagBo.setId(params.getSearchTagsId());
			xTagBo.setName(params.getSearchTags());
		}
		// 生成Page等信息
		searchBo.setPage(params.getPage());
		searchBo.setFatParams(params);
		searchBo.setxTagBo(xTagBo);
		if(null == params.getSearchTagsId())
			return searchBo;
		//确认来源渠道对应的运营分类,默认为官网
		GoodsIndexFieldEnum oClassPath=GoodsIndexFieldEnum.OCLASS_IDS;;
		
		// 用户属性项筛选的map
		// key - 维度字段，value - 维度名称
		Map<String, AttrGroupJsonModel> attrFacetFieldLinkMap = null;
		if(null != params.getCatalogId()){
			String[] attrFacetFields = null;
			// 此方法返回参数为LinkHashMap，在此方法内保证有序
			attrFacetFieldLinkMap = attrGroupService.getAttrGroupIdNameListWithInherit(params.getCatalogId().longValue());
			if(null != attrFacetFieldLinkMap)
				attrFacetFields = attrFacetFieldLinkMap.keySet().toArray(new String[attrFacetFieldLinkMap.size()]);
			result = goodsSolrService.findSearchXiuSolr(params, true, attrFacetFields,new String[]{oClassPath.fieldName(),GoodsIndexFieldEnum.ITEM_LABELS.fieldName()});
			if(result == null){
				return searchBo;
			}
			params.getPage().setRecordCount((int)result.getTotalHits());
			if(params.getPage().getPageNo() > params.getPage().getPageCount()){
				// 若当前页数大于总页数，则属于用户输入参数错误，需重新搜索
//				params.getPage().setPageNo(1);
//				return this.findSearchTagsPageResult(params);
				
				// 用户参数错误时，则返回到无结果页面		William.zhang	20130704
				return searchBo;
			}
			xiuSolrModelList = result.getBeanList();
			List<GoodsItemBo> goodsItemBoList = CommonUtil.transformXiuItemBo(params.getKeyword(), xiuSolrModelList);
			searchBo.setGoodsItemList(goodsItemBoList);
		}else{
			// 查找商品
			result = goodsSolrService.findSearchXiuSolr(params, true, null, new String[]{oClassPath.fieldName(),GoodsIndexFieldEnum.ITEM_LABELS.fieldName()});
			if(result == null){
				return searchBo;
			}
			params.getPage().setRecordCount((int)result.getTotalHits());
			if(params.getPage().getPageNo() > params.getPage().getPageCount()){
				// 若当前页数大于总页数，则属于用户输入参数错误，需重新搜索(走淘宝搜索逻辑)
//				params.getPage().setPageNo(1);
//				return this.findSearchTagsPageResult(params);
				
				// 用户参数错误时，则返回到无结果页面		William.zhang	20130704
				return searchBo;
			}
			xiuSolrModelList = result.getBeanList();
			if(null != xiuSolrModelList && xiuSolrModelList.size()>0){
				// 如果有商品则转换商品
				List<GoodsItemBo> goodsItemBoList = CommonUtil.transformXiuItemBo(params.getKeyword(), xiuSolrModelList);
				searchBo.setGoodsItemList(goodsItemBoList);
			}
			
		}
		// 进行分类，品牌，价格及属性项的筛选
		FacetFilterBo facetFilterBo = null;
		if(null != result.getFacetFields()){
			for (FacetField f: result.getFacetFields()) {
				if(GoodsIndexFieldEnum.BRAND_ID_NAME.fieldName().equals(f.getName())){
					// 如果是品牌筛选
					facetFilterBo = facetFilterBof.parseBrandFacetFilter(f);
					if(null != facetFilterBo){
						searchBo.setBrandFacetBo(facetFilterBo);
						searchBo.setLetterBrandMap(CommonUtil.parseBrandLetterGroupMap(facetFilterBo));
						//新增业务逻辑需要存储每个品牌的拼音名称		William.zhang  20130506
						searchBo.setFacetFilterValueBoList(facetFilterBo.getFacetValueBos());
						searchBo.setFacetFilterValueBoJSON(this.facetFilterValueBoToJSON(facetFilterBo.getFacetValueBos()));
					}
				}else if(oClassPath.fieldName().equals(f.getName())){
					// 如果是运营分类
					List<CatalogBo> catalogBoList = null;
					
					catalogBoList = catalogBof.fetchCatalogBoTreeListForEbay(f, params.getCatalogId());
					if(null != catalogBoList){
						searchBo.setCatalogBoList(catalogBoList);
					}
				}else if(f.getName().indexOf(GoodsIndexFieldEnum.ATTRS_PREFIX.fieldName())==0){
					// 如果是属性项维度的筛选
				//	facetFilterBo = facetFilterBof.parseAttrFacetFilter(f,params.getAttrValIds(),attrFacetFieldLinkMap);
					
					//新的业务逻辑	支持多选	William.zhang	20130508
                	facetFilterBo = facetFilterBof.parseAttrFacetFilter(f,params.getAttrValIdList(),attrFacetFieldLinkMap);
					
					if(null != facetFilterBo)
						searchBo.addAttrFacetBoList(facetFilterBo);
				}else if(GoodsIndexFieldEnum.ITEM_LABELS.fieldName().equals(f.getName())){
					// 如果是ITEM_LABELS
					List<XTagBo> xTagsBoList = xTagBof.parseXTagsFromFacet(f,30);
					if(null != xTagsBoList && xTagsBoList.size()>0){
						searchBo.setxTagBoList(xTagsBoList);
					}
				}
			}
		}
		
		// 生成XTagsBo
		XTagBo xtagsTemp = xTagBof.getCurrentXTagsBoFromFacetXTagsBoList(searchBo.getxTagBoList(), xTagBo.getId(), true);
		if(xtagsTemp != null && null != xtagsTemp.getId()){
			xTagBo.setName(xtagsTemp.getName());
			xTagBo.setItemCount(xtagsTemp.getItemCount());
		}
		searchBo.setxTagBo(xTagBo);
		// 生成价格范围查询的筛选
		facetFilterBo = facetFilterBof.parsePriceRangeFacetFilter(result.getFacetQuery());
		if(null != facetFilterBo)
			searchBo.setPriceFacetBo(facetFilterBo);
		// 生成已选择的运营分类平面结构
		if(searchBo.getCatalogBoList()!=null){
			List<CatalogBo> selectCatalogPlaneList = catalogBof.parsePlaneSelectCatalogBo(searchBo.getCatalogBoList());
			searchBo.setSelectCatalogPlaneList(selectCatalogPlaneList);
		}
		
		// TODO 生成已选择filter条件，后期请将必要数据放入缓存，降低索引facet开销
		// 生成已选择的品牌条件
		if(null != searchBo.getBrandFacetBo() && params.getBrandId() != null){
			facetFilterBo = facetFilterBof.formatSelectFacetFilter(searchBo.getBrandFacetBo(), params.getBrandId());
			searchBo.addSelectFacetBoList(facetFilterBo);
			searchBo.setBrandFacetBo(null);
			searchBo.setLetterBrandMap(null);
		}
		// 生成已选择的价格条件
		if(null != params.getStartPrice() || null != params.getEndPrice()){
			facetFilterBo = facetFilterBof.formatSelectCustomPriceRangeFacetFilter(params.getStartPrice(), params.getEndPrice());
			if(null != facetFilterBo){
				searchBo.addSelectFacetBoList(facetFilterBo);
				searchBo.setPriceFacetBo(null);
			}
		}else if(null != params.getPriceRangeEnum()){
			facetFilterBo = facetFilterBof.formatSelectFacetFilter(FacetTypeEnum.PRICE, params.getPriceRangeEnum().getOrder());
			if(null != facetFilterBo){
				searchBo.addSelectFacetBoList(facetFilterBo);
				searchBo.setPriceFacetBo(null);
			}
		}
		// 生成已选择的属性条件
		if(CollectionUtils.isNotEmpty(params.getAttrValIdList()) && null != searchBo.getAttrFacetBoList() && searchBo.getAttrFacetBoList().size()>0){
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
        		for (int i=0;i< searchBo.getAttrFacetBoList().size();i++) {
                	_ffBo = searchBo.getAttrFacetBoList().get(i);
                    facetFilterBo = facetFilterBof.formatSelectFacetFilter(_ffBo, attrValIds);
                    if(null != facetFilterBo){
                    	searchBo.addSelectFacetBoList(facetFilterBo);
                    	searchBo.removeAttrFacetBoList(facetFilterBo.getFacetFieldName());
                    }
                }
			}
		}
		// 生成已选择的运营分类树
		if(null != searchBo.getCatalogBoList()){
			// 过滤分类树，未选中的兄弟节点需要删除
			catalogBof.filterCatalogTreeDeleteUnSelectedSiblingsItem(searchBo.getCatalogBoList());
			for (CatalogBo bo : searchBo.getCatalogBoList()) {
				if(bo.isSelected()){
					searchBo.setSelectedCatalogTree(bo);
					break;
				}
			}
			searchBo.setSelectedCatalog(catalogBof.getSelectedCatalogFromSelectedCatalogTree(searchBo.getSelectedCatalogTree()));
			
			/*
			 * 清除隐藏的运营分类树节点  2014-04-11 11:56
			 */
			searchBo.setSelectedCatalogTree(CommonUtil.deleteDisplay2(searchBo.getSelectedCatalogTree()));
		}
		
		
		/*
		 * 清除隐藏的运营分类树节点  2014-04-11 12:05
		 */
		if(searchBo.getCatalogBoList()!=null){
			List<CatalogBo> tmpLst=new ArrayList<CatalogBo>();
			CatalogBo tmpCb=null;
			for(CatalogBo cb:searchBo.getCatalogBoList()){
				tmpCb=CommonUtil.deleteDisplay2(cb);
				if(tmpCb.getCatalogModel().getParentCatalogId()==0 && (tmpCb.getChildCatalog()==null || tmpCb.getChildCatalog().size()==0)){
					continue;
				}
				tmpLst.add(tmpCb);
			}
			searchBo.setCatalogBoList(tmpLst);
		}
		
		
		//ebay推荐插码
		if(null!=result&&null!=result.getBeanList()){
			//获取选中品牌编号
			String brandIds="";
			if(null!=searchBo.getSelectFacetBoList()&&searchBo.getSelectFacetBoList().size()>0){
				for (FacetFilterBo facetFilter : searchBo.getSelectFacetBoList()) {
					if(FacetTypeEnum.BRAND==facetFilter.getFacetType()){
						for (FacetFilterValueBo valueBo : facetFilter.getFacetValueBos()) {
							brandIds=brandIds.length()>0?brandIds+","+valueBo.getId():valueBo.getId()+"";
						}
					}
				}
			}
			
			String[] recommendInfo=CommonUtil.matchRecommendInfo(MktTypeEnum.EBAY,params.getCatalogId(), result.getBeanList());
			if(null!=recommendInfo){
				searchBo.setRecommendItemIds(recommendInfo[0]);
				searchBo.setRecommendCatIds(recommendInfo[1]);
				searchBo.setRecommenBrandIds(brandIds);
			}
		}
		return searchBo;
	}
	
	
	@Override
	public String findSimilarItemsResult(SearchFatParams params) {
		
		return null;
	}


	/**
	 * 将当前选中的分类解析成水平结构
	 * @param selectList
	 * @param catalogBoList
	 */
//	private void parsePlaneSelectCatalogBo(List<CatalogBo> selectList,List<CatalogBo> catalogBoList){
//		if(null == catalogBoList)
//			return;
//		for (CatalogBo bo : catalogBoList) {
//			if(bo.isSelected()){
//				selectList.add(bo);
//				this.parsePlaneSelectCatalogBo(selectList, bo.getChildCatalog());
//				break;
//			}
//		}
//	}
	
	/**
	 * 是否需要相关搜索
	 * @param params
	 * @return
	 */
	private boolean needRelateTerms(SearchFatParams params){
		return null == params.getCatalogId()
				&& null == params.getBrandId()
				&& null == params.getStartPrice()
				&& null == params.getEndPrice()
				&& null == params.getPriceRangeEnum()
				&& null == params.getAttrValIds()
				//&& (null == params.getP() || params.getP().intValue()==1)
				//&& null == params.getStock()
				//&& null == params.getSort()
				;
	}
	/**
	 * 是否需要错误纠正
	 * @param params
	 * @return
	 */
	private boolean needErrorCorrection(SearchFatParams params){
		return params.isFirstRequest();
	}
	
	/**
	 * 构造用于查询数量的MKT params
	 * @param oriParams
	 * @param mktType
	 * @return
	 */
	private SearchFatParams buildMktParams(SearchFatParams oriParams,MktTypeEnum mktType){
		SearchFatParams params = new SearchFatParams(0);
		params.setKeyword(oriParams.getKeyword());
		params.setAttrValIds(oriParams.getAttrValIds());
		params.setBrandId(oriParams.getBrandId());
		params.setCatalogId(oriParams.getCatalogId());
		params.setCatalogQueryStr(oriParams.getCatalogQueryStr());
		params.setEndPrice(oriParams.getEndPrice());
		params.setMktType(mktType);
		params.setPriceRangeEnum(oriParams.getPriceRangeEnum());
		params.setStartPrice(oriParams.getStartPrice());
		return params;
	}
	
	private SearchFatParams initNameErrorParams(SearchFatParams oriParams,String errorTerm){
		SearchFatParams ret = new SearchFatParams();
		ret.setKeyword(errorTerm);
		ret.setSort(oriParams.getSort());
		ret.getPage().setPageSize(oriParams.getPage().getPageSize());
		ret.setMktType(oriParams.getMktType());
		return ret;
	}

	/**
	 * 生成相关搜索词
	 * @return
	 */
	private List<String> transformRelatedTerms(QueryParseModel queryParseModel){
		if(null == queryParseModel)
			return null;
		List<QueryParseUnit> units = queryParseModel.getAll(UnitEnum.RELATED_TOKEN);
		if(null == units || units.size()==0)
			return null;
		List<String> ret = new ArrayList<String>();
		for (QueryParseUnit u : units) {
			ret.add(u.getName());
		}
		return ret;
	}

	/**
	 * 生成错误词纠正
	 * @param queryParseModel
	 * @return
	 */
	private String transformErrorTerms(QueryParseModel queryParseModel){
		if(null == queryParseModel){
		    return null;
		}
		QueryParseUnit unit = queryParseModel.get(UnitEnum.CORRECTION);
		if(null == unit) {
			return null;
		}
		// 解决错误词纠正异常问题（如迷你唐卡），这里处理不解决根本问题，还是在lexicon索引的词库里添加比较好。
//		if (null != queryParseModel.getQ() && queryParseModel.getQ().equals( unit.getName())) {
//		    return null;
//		}
		return unit.getName();
	}
	
    /**
     * 将品牌数据转化成JOSN格式	William.zhang 20130506
     * @param facetFilterValueBoList
     */
    private String facetFilterValueBoToJSON(List<FacetFilterValueBo> ffvbos){
    	if(ffvbos==null)
			return null;
		List<Map<String, String>> objList = new ArrayList<Map<String, String>>();
		BrandModel brand;
		for (FacetFilterValueBo facetFilterValueBo : ffvbos) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", facetFilterValueBo.getName());
			map.put("id", facetFilterValueBo.getId() + "");
			brand = XiuBrandInfoCache.getInstance().getBrandById(facetFilterValueBo.getId());
			if(brand != null){
				if(StringUtils.isNotBlank(brand.getMainName()))
					map.put("name", brand.getMainName());
				if(StringUtils.isNotBlank(brand.getBrandName()))
					map.put("cnName", brand.getBrandName());
				if(StringUtils.isNotBlank(brand.getEnName()))
					map.put("enName", brand.getEnName().toUpperCase());
				if(StringUtils.isNotBlank(brand.getPyName()))
					map.put("pyName", brand.getPyName().toUpperCase());
			}
			objList.add(map);
		}

		return JSONArray.fromObject(objList).toString();

    }

	@Autowired
	private GoodsSolrService goodsSolrService;
	@Autowired
	private QueryParseService queryParseService;
	@Autowired
	private CatalogBof catalogBof;
	@Autowired
	private CatalogService catalogService;
	@Autowired
	private FacetFilterBof facetFilterBof;
	@Autowired
	private AttrGroupService attrGroupService;
	@Autowired
	private XTagService xTagService;
	@Autowired
	private XTagBof  xTagBof;
	@Autowired
    private CatalogBusinessOptService catalogBusinessOptService;
}
