package com.xiu.search.core.bof.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
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
import com.xiu.search.core.bof.NewArrivalListBof;
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
@Service("newArrivalListBof")
public class NewArrivalListBofImpl implements NewArrivalListBof {


	/**
	 * 走秀官网
	 * 
	 * @param params
	 * @return
	 */
	public ListBo findNewArrivalListXiuPageResult(ListFatParams params) {
		ListBo listBo = new ListBo();
		
		// 品牌街，奢侈品，海外直发
		listBo.setShowType(params.getShowType());
		
		// solr结果项
		SearchResult<GoodsSolrModel> result = null;
		// 搜索商品项
		List<GoodsSolrModel> xiuSolrModelList = null;
		// 用户属性项筛选的map
		// key - 维度字段，value - 维度名称
		Map<String, AttrGroupJsonModel> attrFacetFieldLinkMap = null;
		// 设置分组条件
		String[] attrFacetFields = null;
		FacetFilterBo facetFilterBo = null;
		
		CatalogBo selectCatalogTree = this.getCatalogBoList2(params, MktTypeEnum.XIU); // 选中的整棵完整的树
		if (null == selectCatalogTree) {
			return null;
		}
		List<CatalogBo> planCatalogList = this.getCatalogPlaneList(selectCatalogTree); // 获得面包屑list
		
		listBo.setSelectCatalogPlaneList(planCatalogList);
		
		if (planCatalogList.size() > 0) {
			listBo.setSelectedCatalog(planCatalogList.get(planCatalogList.size() - 1)); // 获得选中的运营分类节点
		}
		// 行的运营分类规则

		// 查找运营设置的业务相关排序
		CatalogBusinessOptModel businessModel = XiuRecommonCache.getInstance().loadCatlogRecommenedByIdAndMkt(new Long(params.getCatalogId()), MktTypeEnum.XIU);
				//catalogBusinessOptService.getGoodsItemsTopShow(params.getCatalogId(), null,MktTypeEnum.XIU.getType());

		// 查询索引
		result = goodsSolrService.findSearchXiuSolr(params, true, attrFacetFields, params.getExtFacetFields(), businessModel, false);
		if (result == null) {
			return listBo;
		}

		params.getPage().setRecordCount((int) result.getTotalHits());
		if (params.getPage().getPageNo() > params.getPage().getPageCount()) {
			// 若当前页数大于总页数，则属于用户输入参数错误，需重新搜索
//			params.getPage().setPageNo(1);
//			return this.findListXiuPageResult(params);
			// 用户参数错误时，则返回到无结果页面		William.zhang	20130704
			return listBo;
		}

		xiuSolrModelList = result.getBeanList();
		List<GoodsItemBo> goodsItemBoList = CommonUtil.transformXiuItemBo(xiuSolrModelList);
		
		listBo.setGoodsItemList(goodsItemBoList);
		
		listBo.setSelectedCatalogTree(selectCatalogTree); // 选中的整棵完整的树

		//进行分类，品牌，价格及属性项的筛选			William.zhang
		this.getFacetFilterBo(result, listBo, attrFacetFieldLinkMap, params, MktTypeEnum.XIU);
		
		//开始取得可用的发货方式		William.zhang	目前放在页面解决
//		attrFacetFields = new String[]{GoodsIndexFieldEnum.ITEMR_SHOW_TYPE.fieldName()};
//		catalogService.getItemShowType(listBo, params, businessModel, goodsSolrService, attrFacetFields);
		
		
		/*
		 * 添加日期：2014-03-31 18：16
		 */
		//装载运营分类树		William.zhang
		//listBo.setSelectedCatalogTree(this.getCatalogBoList(params, MktTypeEnum.XIU)); // 选中的整棵完整的树
		
		
		/*
		 * 注释日期： 2014-05-06 16:50 
		 * 注释原因：官网绑定的三级分类链接，由于其父节点设置的是display=2,在此去除后，三级的兄弟节点下拉列表没有内容
		 */
		//listBo.setSelectedCatalogTree(CommonUtil.deleteDisplay2(selectCatalogTree)); // 选中的整棵完整的树
		
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

		listBo.setSelectedCatalogTree(CommonUtil.deleteDisplay2(selectCatalogTree)); // 选中的整棵完整的树
		//进行分类，品牌，价格及属性项的筛选			William.zhang
		this.getFacetFilterBo(result, listBo, attrFacetFieldLinkMap, params, MktTypeEnum.EBAY);
		
		
		/*
		 * 添加日期：2014-03-31 18：16
		 */
		//装载运营分类树		William.zhang
		//listBo.setSelectedCatalogTree(this.getCatalogBoList(params, MktTypeEnum.EBAY));// 选中的整棵完整的树
		
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
				}else if (StringUtils.equals(f.getName(), GoodsIndexFieldEnum.OCLASS_IDS.fieldName())){
					if(params != null && params.getExtFacetFields() != null) {
						String[] extFacetFields = params.getExtFacetFields();
						if (ArrayUtils.contains(extFacetFields, GoodsIndexFieldEnum.OCLASS_IDS.fieldName()) && params.getTimeRangeEnum() != null) {
							//设置当前分类下级分类的商品数量
							setChildrenCatalogItemCount(listBo.getSelectedCatalogTree(), params.getCatalogId(), f);
						}
					}
				}
			}
		}
	}
	private CatalogBo setChildrenCatalogItemCount(CatalogBo catalogBo, Integer catalogId, FacetField facetField){
		if (catalogBo.getCatalogId() == catalogId) {
			List<CatalogBo> childrenBos = catalogBo.getChildCatalog();
			if (childrenBos != null && !childrenBos.isEmpty()) {
				List<Count> counts = facetField.getValues();
				if (counts != null) {
					for (int i = 0; i < childrenBos.size(); i++) {
						CatalogBo tempBo = childrenBos.get(i);
						for (int j = 0; j < counts.size(); j++) {
							Count count = counts.get(j);
							if (StringUtils.equals(tempBo.getCatalogId()+"", count.getName())) {
								tempBo.setItemCount(count.getCount());
								break;
							}
						}
					}
				}
			}
			return catalogBo;
		}
		if (!catalogBo.isSelected()) {
			return null;
		}
		List<CatalogBo> children = catalogBo.getChildCatalog();
		if (children != null && !children.isEmpty()) {
			for (int i = 0; i < children.size(); i++) {
				CatalogBo tempBo = setChildrenCatalogItemCount(children.get(i), catalogId, facetField);
				if (tempBo != null) {
					return tempBo;
				}
			}
		}
		return null;
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
