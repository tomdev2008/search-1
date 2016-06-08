package com.xiu.search.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.search.core.bo.GoodsItemBo;
import com.xiu.search.core.bo.SkuAjaxBo;
import com.xiu.search.core.bo.SkuBo;
import com.xiu.search.core.service.GoodsSolrService;
import com.xiu.search.core.service.SkuSolrService;
import com.xiu.search.core.solr.model.XiuSKUIndexModel;
import com.xiu.search.core.util.CommonUtil;
import com.xiu.search.solrj.service.SearchResult;

@Service("skuSolrService")
public class SkuSolrServiceImpl implements SkuSolrService {

	@Autowired
	private GoodsSolrService goodsSolrService;
	
	@Override
	public Map<String, List<SkuAjaxBo>> searchSkuSizeByItemIDs(List<String> idList,int showCount) {
		
		Map<String, List<SkuAjaxBo>> skuMap = new LinkedHashMap<String, List<SkuAjaxBo>>();
		
		SearchResult<XiuSKUIndexModel> skuRst = goodsSolrService.findSearchSKUSolr(idList);
		if(skuRst!=null && skuRst.getTotalHits()>0){
			List<XiuSKUIndexModel> skuList = skuRst.getBeanList();
			if(skuList!=null){
				List<GoodsItemBo> gList = new ArrayList<GoodsItemBo>();
				GoodsItemBo bo = null;
				for(String id:idList){
					bo=new GoodsItemBo();
					bo.setId(id);
					gList.add(bo);
				}
				CommonUtil.buildGoodsShowSkuList(skuList, gList,showCount);
				List<SkuBo> tmpList=null;
				List<SkuAjaxBo> tmpList2=null;
				SkuAjaxBo aBo=null;
				for(GoodsItemBo g:gList){
					tmpList= g.getSkuList();
					tmpList2=new ArrayList<SkuAjaxBo>();
					for(SkuBo b:tmpList){
						aBo=new SkuAjaxBo();
						aBo.setS(b.getSkuSize());
						aBo.setC(b.getQty());
						tmpList2.add(aBo);
					}
					skuMap.put(g.getId(), tmpList2);
				}
			}
		}
		return skuMap;
	}

	@Override
	public Map<String, Integer> getInventoryBySkuList(List<String> skuList) {
		Map<String, Integer> result = new HashMap<String, Integer>();
		SearchResult<XiuSKUIndexModel> xiuSkusResult = goodsSolrService.findSearchSKUSolrList(skuList);
		if (xiuSkusResult != null) {
			List<XiuSKUIndexModel>  xiuSkusModels = xiuSkusResult.getBeanList();
			if (xiuSkusModels != null && !xiuSkusModels.isEmpty()) {
				for (XiuSKUIndexModel xiuSku : xiuSkusModels) {
					result.put(xiuSku.getSkuCode(), xiuSku.getQty());
				}
			}
		}
		return result;
	}

}
