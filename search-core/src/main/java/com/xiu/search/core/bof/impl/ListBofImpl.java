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
import com.xiu.search.core.bo.ListBo;
import com.xiu.search.core.bof.CatalogBof;
import com.xiu.search.core.bof.FacetFilterBof;
import com.xiu.search.core.bof.ListBof;
import com.xiu.search.core.catalog.BrandModel;
import com.xiu.search.core.catalog.XiuBrandInfoCache;
import com.xiu.search.core.config.XiuSearchProperty;
import com.xiu.search.core.model.AttrGroupJsonModel;
import com.xiu.search.core.model.CatalogBusinessOptModel;
import com.xiu.search.core.recom.XiuRecommonCache;
import com.xiu.search.core.service.AttrGroupService;
import com.xiu.search.core.service.CatalogBusinessOptService;
import com.xiu.search.core.service.CatalogService;
import com.xiu.search.core.service.GoodsSolrService;
import com.xiu.search.core.solr.enumeration.ItemShowTypeEnum;
import com.xiu.search.core.solr.enumeration.MktTypeEnum;
import com.xiu.search.core.solr.index.GoodsIndexFieldEnum;
import com.xiu.search.core.solr.model.GoodsSolrModel;
import com.xiu.search.core.solr.model.XiuSKUIndexModel;
import com.xiu.search.core.solr.params.ListFatParams;
import com.xiu.search.core.solr.params.SearchFatParams;
import com.xiu.search.core.util.CommonUtil;
import com.xiu.search.core.util.XiuSearchStringUtils;
import com.xiu.search.solrj.service.SearchResult;

/**
 * 列表页业务处理类
 * 
 * @author wuyongqi
 * 
 */
@Service("listBof")
public class ListBofImpl implements ListBof {
	private static final Logger LOGGER = LoggerFactory.getLogger(ListBofImpl.class);

	/**
	 * 走秀官网
	 * 
	 * @param params
	 * @return
	 */
	public ListBo findListXiuPageResult(ListFatParams params) {
		ListBo listBo = new ListBo();
		// 品牌街，奢侈品，海外直发
		listBo.setShowType(params.getShowType());
		// solr结果项
		SearchResult<GoodsSolrModel> result = null;
		// 搜索商品项
		List<GoodsSolrModel> xiuSolrModelList = null;
		// 用户属性项筛选的map
		// key - 属性项id，value - 维度名称
		Map<String, AttrGroupJsonModel> attrFacetFieldLinkMap = null;
		// 设置分组条件 属性项id集合
		String[] attrFacetFields = null;
		FacetFilterBo facetFilterBo = null;
		//该分类下的
		CatalogBo selectCatalogTree = this.getCatalogBoList2(params, MktTypeEnum.XIU); // 选中的整棵完整的树
		if (null == selectCatalogTree) {
			return null;
		}
		List<CatalogBo> planCatalogList = this.getCatalogPlaneList(selectCatalogTree); // 获得面包屑list
		
		listBo.setSelectCatalogPlaneList(planCatalogList);
		
		if (planCatalogList.size() > 0) {
			listBo.setSelectedCatalog(planCatalogList.get(planCatalogList.size() - 1)); // 获得选中的运营分类节点
		}
		// 此方法返回参数为LinkHashMap，在此方法内保证有序，获取属性过滤规则
		attrFacetFieldLinkMap = attrGroupService.getAttrGroupIdNameListWithInherit(params.getCatalogId().longValue());
		if (null != attrFacetFieldLinkMap) {
			//属性项id
			attrFacetFields = attrFacetFieldLinkMap.keySet().toArray(new String[attrFacetFieldLinkMap.size()]);
		}
		// 查找运营设置的业务相关排序
		CatalogBusinessOptModel businessModel = XiuRecommonCache.getInstance().loadCatlogRecommenedByIdAndMkt(new Long(params.getCatalogId()), MktTypeEnum.XIU);
		// 查询索引
		result = goodsSolrService.findSearchXiuSolr(params, true, attrFacetFields, null, businessModel, false);
		if (result == null) {
			return listBo;
		}
		params.getPage().setRecordCount((int) result.getTotalHits());
		if (params.getPage().getPageNo() > params.getPage().getPageCount()) {
			return listBo;
		}
		xiuSolrModelList = result.getBeanList();
		List<GoodsItemBo> goodsItemBoList = CommonUtil.transformXiuItemBo(xiuSolrModelList);
		/*
		 * 尺码展示
		 * 添加日期：2014-03-21 11：20
		 * 列表页每个商品上需要展示的尺码信息
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
		listBo.setGoodsItemList(goodsItemBoList);
		//进行分类，品牌，价格及属性项的筛选			William.zhang
		//尺码排序
		this.getFacetFilterBo(result, listBo, attrFacetFieldLinkMap, params, MktTypeEnum.XIU);
		//通过各筛选结果，来判断是否硬性添加”其他“筛选项
		/*try {
			this.mergeOtherAttr(result, listBo, params, attrFacetFieldLinkMap);
		} catch (Exception e) {
			LOGGER.error("通过各筛选结果，来判断是否硬性添加”其他“筛选项出现异常------------>>",e);
		}*/
		listBo.setSelectedCatalogTree(selectCatalogTree); // 选中的整棵完整的树
		/*
		 * 设置所有一级分类  lvshuding 2014-04-17 11:15
		 */
		List<CatalogBo> fcList=catalogBof.returnFirstCataListByMktType(MktTypeEnum.XIU);
		listBo.setFirstCataList(fcList);
		//选中的一级分类设置 isSelected 
		if(fcList!=null && fcList.size()>0){
			if(listBo.getSelectCatalogPlaneList()!=null && listBo.getSelectCatalogPlaneList().size()>0){
				int selectedId = listBo.getSelectCatalogPlaneList().get(0).getCatalogId();
				for(int i=0;i<fcList.size();i++){
					if(fcList.get(i).getCatalogId()==selectedId){
						fcList.get(i).setSelected(true);
						break;
					}
				}
			}
		}
		// 生成价格范围查询的筛选
		facetFilterBo = facetFilterBof.parsePriceRangeFacetFilter(result.getFacetQuery());
		if (null != facetFilterBo)
			listBo.setPriceFacetBo(facetFilterBo);
		//生成已选择的属性过滤条件
		this.creatFacetFilterBo(listBo, params);
		
		//删除只有一个属性值的属性项
		this.removeOnlyOneValAttr(listBo);
		//价格整合到筛选项list中，并排到最后
		if(null != facetFilterBo){
			listBo.addAttrFacetBoList(facetFilterBo);
		}

		// 官网推荐插码
		if (null != result && null != result.getBeanList()) {
			// 获取选中品牌编号
			String brandIds = "";
			if (null != listBo.getSelectFacetBoList() && listBo.getSelectFacetBoList().size() > 0) {
				for (FacetFilterBo facetFilter : listBo.getSelectFacetBoList()) {
					if (FacetTypeEnum.BRAND == facetFilter.getFacetType()) {
						for (FacetFilterValueBo valueBo : facetFilter.getFacetValueBos()) {
							brandIds = brandIds.length() > 0 ? brandIds + "," + valueBo.getId() : valueBo.getId() + "";
						}
					}
				}
			}
			String[] recommendInfo = CommonUtil.matchRecommendInfo(MktTypeEnum.XIU, params.getCatalogId(), result.getBeanList());
			if (null != recommendInfo) {
				listBo.setRecommendItemIds(recommendInfo[0]);
				listBo.setRecommendCatIds(recommendInfo[1]);
				listBo.setRecommenBrandIds(brandIds);
			}
		}

		// 生成其他信息
		listBo.setPage(params.getPage());
		listBo.setFatParams(params);
		return listBo;
	}
	/**
	 * 过滤掉只有一个属性值的属性项
	 * @param listBo
	 */
	private void removeOnlyOneValAttr(ListBo listBo) {
		List<FacetFilterBo> facetFilterBos = listBo.getAttrFacetBoList();
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

	public ListBo findListEbayPageResult(ListFatParams params) {

		ListBo listBo = new ListBo();
		SearchResult<GoodsSolrModel> result;
		List<GoodsSolrModel> xiuSolrModelList;
		FacetFilterBo facetFilterBo = null;
		// 用户属性项筛选的map
		// key - 维度字段，value - 维度名称
		Map<String, AttrGroupJsonModel> attrFacetFieldLinkMap = null;
		String[] attrFacetFields = null;

		CatalogBo selectCatalogTree = this.getCatalogBoList2(params, MktTypeEnum.EBAY); // 选中的整棵完整的树
		if (null == selectCatalogTree) {
			return null;
		}

		List<CatalogBo> planCatalogList = this.getCatalogPlaneList(selectCatalogTree); // 获得面包屑list
		listBo.setSelectCatalogPlaneList(planCatalogList);
		
		if (planCatalogList.size() > 0) {
			listBo.setSelectedCatalog(planCatalogList.get(planCatalogList.size() - 1)); // 获得选中的运营分类节点
		}

		// 此方法返回参数为LinkHashMap，在此方法内保证有序，获取属性过滤规则
		attrFacetFieldLinkMap = attrGroupService.getAttrGroupIdNameListWithInherit(params.getCatalogId().longValue());
		if (null != attrFacetFieldLinkMap) {
			attrFacetFields = attrFacetFieldLinkMap.keySet().toArray(new String[attrFacetFieldLinkMap.size()]);
		}
		// 查找运营设置的业务相关排序
		CatalogBusinessOptModel businessModel = XiuRecommonCache.getInstance().loadCatlogRecommenedByIdAndMkt(new Long(params.getCatalogId()), MktTypeEnum.EBAY);
					//catalogBusinessOptService.getGoodsItemsTopShow(params.getCatalogId(), null,MktTypeEnum.EBAY.getType()+1);
		result = goodsSolrService.findSearchXiuSolr(params, true, attrFacetFields, null, businessModel, true);
		if (result == null) {
			return listBo;
		}
		params.getPage().setRecordCount((int) result.getTotalHits());
		if (params.getPage().getPageNo() > params.getPage().getPageCount()) {
			// 若当前页数大于总页数，则属于用户输入参数错误，需重新搜索
//			params.getPage().setPageNo(1);
//			return this.findListEbayPageResult(params);
			// 用户参数错误时，则返回到无结果页面		William.zhang	20130704
			return listBo;
		}
		xiuSolrModelList = result.getBeanList();
		List<GoodsItemBo> goodsItemBoList = CommonUtil.transformXiuItemBo(xiuSolrModelList);
		listBo.setGoodsItemList(goodsItemBoList);

		//进行分类，品牌，价格及属性项的筛选			William.zhang
		this.getFacetFilterBo(result, listBo, attrFacetFieldLinkMap, params, MktTypeEnum.EBAY);
		
		
		/*
		 * 添加日期：2014-03-31 18：16
		 */
		//装载运营分类树		William.zhang
		//listBo.setSelectedCatalogTree(this.getCatalogBoList(params, MktTypeEnum.EBAY));// 选中的整棵完整的树
		
		listBo.setSelectedCatalogTree(CommonUtil.deleteDisplay2(selectCatalogTree)); // 选中的整棵完整的树
		
		// 生成价格范围查询的筛选
		facetFilterBo = facetFilterBof.parsePriceRangeFacetFilter(result.getFacetQuery());
		if (null != facetFilterBo)
			listBo.setPriceFacetBo(facetFilterBo);

		// TODO 生成已选择filter条件，后期请将必要数据放入缓存，降低索引facet开销
		//生成已选择的属性过滤条件
		this.creatFacetFilterBo(listBo, params);
		
		//价格整合到筛选项list中，并排到最后
		if(null != facetFilterBo){
			listBo.addAttrFacetBoList(facetFilterBo);
		}
		
		// ebay推荐插码
		if (null != result && null != result.getBeanList()) {
			// 获取选中品牌编号
			String brandIds = "";
			if (null != listBo.getSelectFacetBoList() && listBo.getSelectFacetBoList().size() > 0) {
				for (FacetFilterBo facetFilter : listBo.getSelectFacetBoList()) {
					if (FacetTypeEnum.BRAND == facetFilter.getFacetType()) {
						for (FacetFilterValueBo valueBo : facetFilter.getFacetValueBos()) {
							brandIds = brandIds.length() > 0 ? brandIds + "," + valueBo.getId() : valueBo.getId() + "";
						}
					}
				}
			}

			String[] recommendInfo = CommonUtil.matchRecommendInfo(MktTypeEnum.EBAY, params.getCatalogId(), result.getBeanList());
			if (null != recommendInfo) {
				listBo.setRecommendItemIds(recommendInfo[0]);
				listBo.setRecommendCatIds(recommendInfo[1]);
				listBo.setRecommenBrandIds(brandIds);
			}
		}

		// 生成其他信息
		listBo.setPage(params.getPage());
		listBo.setFatParams(params);
		return listBo;
	}

	/**
	 * 构造用于查询数量的MKT params
	 * 
	 * @param oriParams
	 * @param mktType
	 * @return
	 */
	private ListFatParams buildMktParams(ListFatParams oriParams, MktTypeEnum mktType) {
		ListFatParams params = new ListFatParams(0);
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

	/**
	 * 获取选中的整棵完整的树
	 * 
	 * @param selectedId
	 *            运营分类ID
	 * @return
	 */
	public CatalogBo getCatalogBoList(SearchFatParams params, MktTypeEnum fromType) {
		CatalogBo selectCatalogTree = null;
		ItemShowTypeEnum typeEnum = null;
		if(fromType.getType() == MktTypeEnum.XIU.getType()){
			if(params.getItemShowType() != null && params.getItemShowType().size() > 0){
				typeEnum = ItemShowTypeEnum.valueof(params.getItemShowType().get(0));
			}
			selectCatalogTree = catalogBof.fetchCatalogBoTreeByIdForXiu(params.getCatalogId(), typeEnum); // 获取当前选中的运营分类树（从1级节点开始）
		}else if(fromType.getType() == MktTypeEnum.EBAY.getType()){
			selectCatalogTree = catalogBof.fetchCatalogBoTreeByIdForEbay(params.getCatalogId()); // 获取当前选中的运营分类树（从1级节点开始）
		}

		return selectCatalogTree;
	}
	
	/**
	 * 获取选中的整棵完整的树 包含Display=1，2的
	 * 
	 * @param selectedId
	 *            运营分类ID
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public CatalogBo getCatalogBoList2(SearchFatParams params, MktTypeEnum fromType) {
		CatalogBo selectCatalogTree = null;
		ItemShowTypeEnum typeEnum = ItemShowTypeEnum.DSP12;
		if(fromType.getType() == MktTypeEnum.XIU.getType()){
			if(params.getItemShowType() != null && params.getItemShowType().size() > 0){
				typeEnum = ItemShowTypeEnum.valueof(params.getItemShowType().get(0));
			}
		}
		selectCatalogTree = catalogBof.fetchCatalogBoTreeByIdFromDisylay12(params.getCatalogId(),fromType, typeEnum); // 获取当前选中的运营分类树（从1级节点开始）
		return selectCatalogTree;
	}

	/**
	 * 获得面包屑list 所有selected为true的分类节点按顺序排列
	 * 
	 * @param selectCatalog
	 *            运营分类树
	 * @return List<CatalogBo>
	 */
	private List<CatalogBo> getCatalogPlaneList(CatalogBo selectCatalog) {

		List<CatalogBo> planCatalogList = new ArrayList<CatalogBo>();

		List<CatalogBo> tmpCatalogBoList = new ArrayList<CatalogBo>();
		tmpCatalogBoList.add(selectCatalog);

		for (int i = 0; i < 4; i++) {
			if (null != tmpCatalogBoList && tmpCatalogBoList.size() > 0) {
				int N = tmpCatalogBoList.size();
				for (int j = 0; j < N; j++) {
					if (tmpCatalogBoList.get(j).isSelected()) {
						planCatalogList.add(tmpCatalogBoList.get(j));
						tmpCatalogBoList = tmpCatalogBoList.get(j).getChildCatalog();
						break;
					}
					if (j == N - 1) {
						tmpCatalogBoList = null;
					}
				}
			} else {
				break;
			}
		}
		return planCatalogList;
	}

	/**
	 * 将品牌数据转化成JOSN格式 William.zhang 20130506
	 * 
	 * @param facetFilterValueBoList
	 */
	private String facetFilterValueBoToJSON(List<FacetFilterValueBo> ffvbos) {
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

	/**
	 * 进行分类，品牌，价格及属性项的筛选
	 **/
	private void getFacetFilterBo(SearchResult<GoodsSolrModel> result, ListBo listBo, Map<String, AttrGroupJsonModel> attrFacetFieldLinkMap, ListFatParams params, MktTypeEnum mte) {
		FacetFilterBo facetFilterBo = null;
		if (null != result.getFacetFields()) {
			for (FacetField f : result.getFacetFields()) {
				if (GoodsIndexFieldEnum.BRAND_ID_NAME.fieldName().equals(f.getName())) {
					// 如果是品牌筛选
					facetFilterBo = facetFilterBof.parseBrandFacetFilter(f);
					if (null != facetFilterBo) {
						listBo.setBrandFacetBo(facetFilterBo);
						listBo.setLetterBrandMap(CommonUtil.parseBrandLetterGroupMap(facetFilterBo));
						// 新增业务逻辑需要存储每个品牌的拼音名称 William.zhang 20130506
						listBo.setFacetFilterValueBoList(facetFilterBo.getFacetValueBos());
						listBo.setFacetFilterValueBoJSON(this.facetFilterValueBoToJSON(facetFilterBo.getFacetValueBos()));
					}
				}else if (f.getName().indexOf(GoodsIndexFieldEnum.ATTRS_PREFIX.fieldName()) == 0) {
					// 新的业务逻辑 支持多选 William.zhang 20130508
					facetFilterBo = facetFilterBof.parseAttrFacetFilter(f, params.getAttrValIdList(), attrFacetFieldLinkMap);
					if (null != facetFilterBo)
						listBo.addAttrFacetBoList(facetFilterBo);
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
	private void mergeOtherAttr(SearchResult<GoodsSolrModel> result, ListBo listBo, ListFatParams params, Map<String, AttrGroupJsonModel> attrIdNameMap) throws Exception{
		List<FacetFilterBo> facetFilterBoList = listBo.getAttrFacetBoList();
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
								//useOldOther = false;
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
							listBo.addAttrFacetBoList(tempBo);
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
						listBo.addAttrFacetBoList(tempBo);
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
	 *  生成已选择的属性过滤项
	 */
	private void creatFacetFilterBo(ListBo listBo,ListFatParams params){
		//生成已选则的品牌条件
		FacetFilterBo facetFilterBo = null;
		if (null != listBo.getBrandFacetBo() && params.getBrandIds() != null) {
			// 新增业务逻辑，用于支持多选时展示品牌名称 William.zhang 20130507
			facetFilterBo = facetFilterBof.formatSelectFacetFilter(listBo.getBrandFacetBo(), params.getBrandIds());
			if (facetFilterBo != null) {
				listBo.addSelectFacetBoList(facetFilterBo);
				listBo.setBrandFacetBo(null);
				listBo.setLetterBrandMap(null);
			}
		}
		//生成已选则的价格条件
		if (null != params.getStartPrice() || null != params.getEndPrice()) {
			facetFilterBo = facetFilterBof.formatSelectCustomPriceRangeFacetFilter(params.getStartPrice(), params.getEndPrice());
			if (null != facetFilterBo) {
				listBo.addSelectFacetBoList(facetFilterBo);
				listBo.setPriceFacetBo(null);
			}
		} else if (null != params.getPriceRangeEnum()) {
			facetFilterBo = facetFilterBof.formatSelectFacetFilter(FacetTypeEnum.PRICE, params.getPriceRangeEnum().getOrder());
			if (null != facetFilterBo) {
				listBo.addSelectFacetBoList(facetFilterBo);
				listBo.setPriceFacetBo(null);
			}
		}
		
		// 生成已选择的属性条件
		if (CollectionUtils.isNotEmpty(params.getAttrValIdList()) && null != listBo.getAttrFacetBoList() && listBo.getAttrFacetBoList().size() > 0) {
			FacetFilterBo _ffBo;
			List<Long> attrValIds;
			// 多值属性值选择，过滤已经选择的属性项。 William.zhang 20130515
			for (List<String> list : params.getAttrValIdList()) {
				if (null == list)
					continue;
				attrValIds = new ArrayList<Long>(list.size());
				for (String attrValIdStr : list) {
					if (XiuSearchStringUtils.isIntegerNumber(attrValIdStr))
						attrValIds.add(Long.valueOf(attrValIdStr));
				}
				if(attrValIds.size()>0){
					for (int i = 0; i < listBo.getAttrFacetBoList().size(); i++) {
						_ffBo = listBo.getAttrFacetBoList().get(i);
						facetFilterBo = facetFilterBof.formatSelectFacetFilter(_ffBo, attrValIds);
						if (null != facetFilterBo) {
							listBo.addSelectFacetBoList(facetFilterBo);
							listBo.removeAttrFacetBoList(facetFilterBo.getFacetFieldName());
						}
					}
				}
			}
		}
	}

	@Autowired
	private CatalogService catalogService;

	@Autowired
	private CatalogBof catalogBof;

	@Autowired
	private AttrGroupService attrGroupService;

	@Autowired
	private GoodsSolrService goodsSolrService;

	@Autowired
	private FacetFilterBof facetFilterBof;

	@Autowired
	private CatalogBusinessOptService catalogBusinessOptService;

	public void setCatalogService(CatalogService catalogService) {
		this.catalogService = catalogService;
	}

}
