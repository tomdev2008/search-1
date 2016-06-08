package com.xiu.search.core.bof.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.search.core.bo.CatalogBo;
import com.xiu.search.core.bo.SearchBo;
import com.xiu.search.core.bof.CatalogBof;
import com.xiu.search.core.catalog.CatalogModel;
import com.xiu.search.core.catalog.XiuCatalogTreeCache;
import com.xiu.search.core.service.CatalogService;
import com.xiu.search.core.solr.enumeration.ItemShowTypeEnum;
import com.xiu.search.core.solr.enumeration.MktTypeEnum;
import com.xiu.search.core.util.Constants;

@Service("catalogBof")
public class CatalogBofImpl implements CatalogBof {

	@Autowired
	private  CatalogService catalogService;
	
	@Override
	public CatalogBo getSelectedCatalogFromSelectedCatalogTree(
			CatalogBo selectedCatalogTree) {
		if(selectedCatalogTree == null)
			return null;
		CatalogBo ret = selectedCatalogTree;
		CatalogBo retTemp;
		if(selectedCatalogTree.isSelected() && selectedCatalogTree.getChildCatalog()!=null && selectedCatalogTree.getChildCatalog().size()>0){
			for (CatalogBo bo : selectedCatalogTree.getChildCatalog()) {
				if(bo.isSelected()){
					ret = bo;
					retTemp = getSelectedCatalogFromSelectedCatalogTree(bo);
					if(null != retTemp) return retTemp;
				}
			}
		}
		return ret;
	}
	
	public List<CatalogBo> parsePlaneSelectCatalogBo(List<CatalogBo> catalogBoList){
		List<CatalogBo> list = new ArrayList<CatalogBo>();
		if(catalogBoList != null)
		for (CatalogBo cat:catalogBoList) {
			if(cat.isSelected()){
				list.add(cat);
				list.addAll(parsePlaneSelectCatalogBo(cat.getChildCatalog()));
				break;
			}
		}
		return list;
	}
	

	@Override
	public void filterCatalogTreeDeleteUnSelectedSiblingsItem(List<CatalogBo> catalogBoList) {
		if(CollectionUtils.isEmpty(catalogBoList))
			return;
		int len = catalogBoList.size();
		CatalogBo bo;
		while(len-->0){
			bo = catalogBoList.get(len);
			if(bo.isSelected()){
				catalogBoList.clear();
				catalogBoList.add(bo);
				if(CollectionUtils.isNotEmpty(bo.getChildCatalog()))
					this.filterCatalogTreeDeleteUnSelectedSiblingsItem(bo.getChildCatalog());
				return;
			}
			
		}
		
	}
	
	@Override
	public CatalogBo fetchCatalogBoTreeById(Integer catId){
		return this.fetchCatalogBoByIdAndMktTypeAndShowType(catId, null, null);
	}
	
	@Override
	public CatalogBo fetchCatalogBoTreeByIdForXiu(Integer catId,ItemShowTypeEnum type) {
		return this.fetchCatalogBoByIdAndMktTypeAndShowType(catId, MktTypeEnum.XIU, type);
	}
	
	@Override
	public CatalogBo fetchCatalogBoTreeByIdForEbay(Integer catId) {
		return this.fetchCatalogBoByIdAndMktTypeAndShowType(catId, MktTypeEnum.EBAY, null);
	}
	
	public CatalogBo fetchCatalogBoTreeByIdFromDisylay12(Integer catId,MktTypeEnum mktType,ItemShowTypeEnum showType) {
		return this.fetchCatalogBoByIdAndMktTypeAndShowType(catId, mktType, showType);
	}
	

	@Override
	public List<CatalogBo> fetchCatalogBoTreeListForEbay(FacetField facetField, Integer catId) {
		// TODO Auto-generated method stub
		return this.fetchCatalogBoListByCatalogFacetFieldAndCatalogId(facetField, catId, MktTypeEnum.EBAY);
	}

	@Override
	public List<CatalogBo> fetchCatalogBoTreeListForXiu(FacetField facetField, Integer catId) {
		return this.fetchCatalogBoListByCatalogFacetFieldAndCatalogId(facetField, catId, MktTypeEnum.XIU);
	}
	
	
	@Override
	public void fetchBrotherCatalogBoListForXiu(FacetField facetField, Integer catId,SearchBo brandBo) {
		this.fetchBrotherCatalogBoByCatalogFacetFieldAndCatalogId(facetField, catId, MktTypeEnum.XIU,brandBo);
	}
	
	@Override
	public void fetchBrotherCatalogBoListForEbay(FacetField facetField, Integer catId,SearchBo brandBo) {
		this.fetchBrotherCatalogBoByCatalogFacetFieldAndCatalogId(facetField, catId, MktTypeEnum.EBAY,brandBo);
	}
	
	@Override
	public List<CatalogBo> returnFirstCataListByMktType(MktTypeEnum mktType) {
		List<CatalogModel> fcmList = XiuCatalogTreeCache.getInstance().getAllFirstCataList();
		if(fcmList==null || fcmList.size()==0){
			return null;
		}
		
		CatalogBo cb =null;
		List<CatalogBo> cbList = new ArrayList<CatalogBo>();
		for(CatalogModel cm:fcmList){
			if(mktType != MktTypeEnum.valueof(cm.getMktType())){
				continue;
			}
			cb=new CatalogBo();
			cb.setCatalogModel(cm);
			cbList.add(cb);
		}
		
		return cbList;
	}
	
	
	/**
	 * 
	 * @return
	 */
	private void fetchBrotherCatalogBoByCatalogFacetFieldAndCatalogId(FacetField facetField, Integer catId, MktTypeEnum mktType,SearchBo bo){
		List<Count> cList = facetField.getValues();
		Map<Integer,CatalogBo> map = new HashMap<Integer,CatalogBo>();		//用于保存遍历过的节点
		if(cList == null || cList.size()<=0){
			return;
		}
		
		/*
		 * 0.取出面包屑中各CatalogId放入List
		 * 
		 * 1.遍历所有Count，放入Map中, if display=2 delete
		 * 
		 * 2.封装一级
		 *   找到所有一级节点，再判断List中否存在，存在的放入brandBo.firstList
		 * 
		 * 3.封装二级
		 *   找到父节点的所有子节点，再判断List中否存在，存在的放入brandBo.secondList
		 *   
		 * 4.判断是否是三级
		 * if(三级){
		 * 		找到父节点的所有子节点，再判断List中否存在，存在的放入brandBo.thirdList
		 * }
		 */
		
		//0.
		List<Integer> selectIds = new ArrayList<Integer>(3);
		for(CatalogBo cb:bo.getSelectCatalogPlaneList()){
			selectIds.add(cb.getCatalogId());
		}
		
		//1.
		String countName;
		CatalogModel cm1;
		CatalogBo boTemp1;
		Integer i = null;
		for(Count count : cList){
			countName = count.getName();
			if(!StringUtils.isNumeric(countName)){
				continue;
			}
			i = Integer.parseInt(count.getName());
			if( i == null || i.intValue() <=0){
				continue;
			}
			// 如果map 包含当前节点,则不作任何操作，继续下一个。
			if(map.containsKey(i)){
				continue;
			}
			cm1 = this.fetchCatalogModelNoteById(i,mktType ,ItemShowTypeEnum.DSP12);
			if(cm1 == null || Constants.CATALOG_DISPLAY_HIDDEN.equals(cm1.getDisplay())){//跳过display=2
				continue;
			}
			
			boTemp1 = new CatalogBo();
			boTemp1.setSelected(selectIds.contains(i));
			boTemp1.setCatalogModel(cm1);
			// 当前map不包含当前节点
			map.put(boTemp1.getCatalogId(), boTemp1);
		}
		
		//2. 3. 4.
		List<CatalogBo> list1=new ArrayList<CatalogBo>();
		List<CatalogBo> list2=new ArrayList<CatalogBo>();
		List<CatalogBo> list3=new ArrayList<CatalogBo>();
		Integer firstId = selectIds.get(0);
		Integer secondId = null;
		if(selectIds.size()>1){
			secondId = selectIds.get(1);
		}
		
		for(Integer id:map.keySet()){
			if(map.get(id).getCatalogModel().getParentCatalogId()==0){//1级
				list1.add(map.get(id));
			}else if(map.get(id).getCatalogModel().getParentCatalogId()==firstId){//2级
				list2.add(map.get(id));
			}else if(secondId!=null && map.get(id).getCatalogModel().getParentCatalogId()==secondId){//3级
				list3.add(map.get(id));
			}
		}
		bo.setFirstCataList(list1);
		bo.setSecondCataList(list2);
		if(list3.size()>0){
			bo.setThirdCataList(list3);
		}
	}

	/**
	 * 
	 * @return
	 */
	private List<CatalogBo> fetchCatalogBoListByCatalogFacetFieldAndCatalogId(FacetField facetField, Integer catId, MktTypeEnum mktType){
		List<Count> cList = facetField.getValues();
		Map<Integer,CatalogBo> map = new HashMap<Integer,CatalogBo>();		//用于保存遍历过的节点
		List<CatalogBo> catalogBolist = new ArrayList<CatalogBo>();
		if(cList == null || cList.size()<=0){
			return null;
		}
		List<Integer> selectIds = null;
		if(catId != null){
			//取得当前被选中的运营分类
			CatalogModel cm = this.fetchCatalogModelNoteById(catId,mktType ,ItemShowTypeEnum.DSP12);//this.fetchCatalogModelNoteById(catId,mktType ,null);
			if(cm != null){
				selectIds = new ArrayList<Integer>(3);
				selectIds.add(cm.getCatalogId());
				// 获得顶级分类Model，以及选中的分类树的ID
				while(cm.getParentCatalogId()>0){
					cm = this.fetchCatalogModelNoteById(cm.getParentCatalogId(),mktType ,ItemShowTypeEnum.DSP12);//this.fetchCatalogModelNoteById(cm.getParentCatalogId(),mktType ,null);
					if(cm == null){
						selectIds = null;
						break;
					}
					selectIds.add(cm.getCatalogId());
				}
				cm = null;
			}
		}
		String countName;
		for(Count count : cList){
			countName = count.getName();
			Integer i = null;
			if(StringUtils.isNumeric(countName)){
				i = Integer.parseInt(count.getName());
			}
			if( i == null || i.intValue() <=0){
				continue;
			}
			// 如果map 包含当前节点,则不作任何操作，继续下一个。
			if(map.containsKey(i)){
				continue;
			}
			CatalogModel cm1 = this.fetchCatalogModelNoteById(i,mktType ,ItemShowTypeEnum.DSP12);//this.fetchCatalogModelNoteById(i,mktType ,null);
			if(cm1 == null){
				continue;
			}
			CatalogBo boTemp1 = new CatalogBo();
			if(selectIds!=null){
				boTemp1.setSelected(selectIds.contains(i));
			}
			boTemp1.setCatalogModel(cm1);
			// 当前map不包含当前节点
			map.put(boTemp1.getCatalogId(), boTemp1);
			
			//如果该节点有父类节点，则继续取得父类
			if(cm1.getParentCatalogId() != 0){
				//如果当前的MAP中包含该集合的父类，则直接将该类加载在其父类下
				if(map.containsKey(cm1.getParentCatalogId())){
					map.get(cm1.getParentCatalogId()).addChildCatalog(boTemp1);
					continue;
				}
				CatalogModel cm2 = this.fetchCatalogModelNoteById(cm1.getParentCatalogId(),mktType ,ItemShowTypeEnum.DSP12);//this.fetchCatalogModelNoteById(cm1.getParentCatalogId(),mktType ,null);
				if(null == cm2)
					continue;
				CatalogBo boTemp2 = new CatalogBo();
				if(selectIds!=null){
					boTemp2.setSelected(selectIds.contains(i));
				}
				boTemp2.addChildCatalog(boTemp1);
				boTemp2.setCatalogModel(cm2);
				map.put(cm2.getCatalogId(), boTemp2);
				if(cm2.getParentCatalogId() != 0){
					if(map.containsKey(cm2.getParentCatalogId())){
						map.get(cm2.getParentCatalogId()).addChildCatalog(boTemp2);
						continue;
					}
					CatalogModel cm3 = this.fetchCatalogModelNoteById(cm2.getParentCatalogId(),mktType ,ItemShowTypeEnum.DSP12);//this.fetchCatalogModelNoteById(cm2.getParentCatalogId(),mktType ,null);
					if(null == cm3)
						continue;
					CatalogBo boTemp3 = new CatalogBo();
					if(selectIds!=null){
						boTemp3.setSelected(selectIds.contains(i));
					}
					boTemp3.setCatalogModel(cm3);
					boTemp3.addChildCatalog(boTemp2);
					map.put(cm3.getCatalogId(), boTemp3);
					catalogBolist.add(boTemp3);
				}else{
					catalogBolist.add(boTemp2);
				}
			}else{
				catalogBolist.add(boTemp1);
			}
		}
		if(map != null){
			map.clear();
			map = null;
		}
		return catalogBolist;
		
	}
	
	/**
	 * 用于查询某个分类树的Bo；
	 * showType只作用于MKTType = Xiu的情况
	 * @param catId
	 * @param mktType
	 * @param showType
	 * @return
	 */
	private CatalogBo fetchCatalogBoByIdAndMktTypeAndShowType(Integer catId,MktTypeEnum mktType,ItemShowTypeEnum showType){
		CatalogModel cm = this.fetchCatalogModelNoteById(catId, mktType,showType);
		if(cm == null)
			return null;
		List<Integer> selectIds = new ArrayList<Integer>(3);
		selectIds.add(cm.getCatalogId());
		// 获得顶级分类Model，以及选中的分类树的ID
		while(cm.getParentCatalogId()>0){
			cm = this.fetchCatalogModelNoteById(cm.getParentCatalogId(), mktType,showType);
			if(cm == null)
				return null;
			selectIds.add(cm.getCatalogId());
		}
		CatalogBo catalogBo = new CatalogBo();// 一级
		catalogBo.setCatalogModel(cm);
		catalogBo.setSelected(true);
		if(CollectionUtils.isNotEmpty(cm.getChildIdList())){
			//遍历二级分类
			CatalogBo boTemp2,boTemp3;
			CatalogModel cmTemp2,cmTemp3;
			for(Integer cId2 : cm.getChildIdList()){
				cmTemp2 = this.fetchCatalogModelNoteById(cId2, mktType,showType);
				if(cmTemp2 == null)
					continue;
				boTemp2 = new CatalogBo();
				boTemp2.setCatalogModel(cmTemp2);
				boTemp2.setSelected(selectIds.contains(cId2));
				catalogBo.addChildCatalog(boTemp2);
				if(CollectionUtils.isNotEmpty(cmTemp2.getChildIdList())){
					// 遍历三级分类
					for(Integer cId3 : cmTemp2.getChildIdList()){
						cmTemp3 = this.fetchCatalogModelNoteById(cId3, mktType,showType);
						if(cmTemp3 == null)
							continue;
						boTemp3 = new CatalogBo();
						boTemp3.setCatalogModel(cmTemp3);
						boTemp3.setSelected(selectIds.contains(cId3));
						boTemp2.addChildCatalog(boTemp3);
					}
				}
			}
			boTemp2 = null;
			boTemp3 = null;
			cmTemp2 = null;
			cmTemp3 = null;
		}
		return catalogBo;
	}
	
	private CatalogModel fetchCatalogModelNoteById(Integer catId,MktTypeEnum mktType,ItemShowTypeEnum showType) {
		if(catId == null)
			return null;
		CatalogModel cm = null;
		// 从缓存中 获取 category model
		if(showType == null)
			cm = XiuCatalogTreeCache.getInstance().getTreeNodeById(catId.toString());
		else
			cm = XiuCatalogTreeCache.getInstance().getTreeNodeById(catId.toString(),showType);
		if(cm == null){
			return null;
		}
		// 判断MKT 类型
		if(mktType!=null && cm.getMktType() != mktType.getType())
			return null;
		return cm;
	}

}
