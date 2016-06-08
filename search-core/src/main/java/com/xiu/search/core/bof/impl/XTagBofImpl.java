package com.xiu.search.core.bof.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.search.core.bo.XTagBo;
import com.xiu.search.core.bof.XTagBof;
import com.xiu.search.core.service.GoodsSolrService;
import com.xiu.search.core.service.XTagService;
import com.xiu.search.core.util.XiuSearchStringUtils;
import com.xiu.search.dao.model.XTag;

@Service("xTagBof")
public class XTagBofImpl implements XTagBof{

	@Override
	public List<XTagBo> parseXTagsFromFacet(FacetField f,int maxTagCount) {
		if(null == f)
			return null;
		List<Count> counts = f.getValues();
		if(counts == null)
			return null;
		String id;
		int num;
		XTagBo xt;
		List<Integer> ids = new ArrayList<Integer>();
		List<XTagBo> ret = new ArrayList<XTagBo>();
		Count c;
		for (int i = 0,len = counts.size(); i < len; i++) {
			if((c = counts.get(i)) == null
					|| !XiuSearchStringUtils.isIntegerNumber((id = c.getName()))
					|| (num = (int)c.getCount()) == 0 )
				continue;
			xt = new XTagBo();
			xt.setId(Integer.valueOf(id));
			xt.setItemCount(num);
			ids.add(xt.getId());
			ret.add(xt);
		}
		Map<Integer,XTag> xtagsMap = xTagService.findXTagMapByIds(ids);
		Map<Integer,Integer> xtagsItemCountMap = xTagService.findXTagItemCountByIdsFromIndex(ids);
		if(xtagsMap==null)
			xtagsMap = Collections.emptyMap();
		String name;
		Integer itemCount;
		int len = ret.size();
		while (len-->0) {
			xt = ret.get(len);
			if(xtagsMap.containsKey(xt.getId())
					&& (name = xtagsMap.get(xt.getId()).getName()) != null){
				xt.setName(name);
			} else {
				ret.remove(len);
				continue;
			}
			if(xtagsItemCountMap.containsKey(xt.getId())
					&& (itemCount = xtagsItemCountMap.get(xt.getId()))!= null
					&& itemCount > 0){
				xt.setItemCount(itemCount);
			}else{
				ret.remove(len);
				continue;
			}
		}
		return (maxTagCount>0 && ret.size() > maxTagCount )? ret.subList(0, maxTagCount) :  ret;
	}
	
	@Override
	public XTagBo getCurrentXTagsBoFromFacetXTagsBoList(List<XTagBo> xTagBoList, Integer currentXTagId , boolean removeSelectedXTag) {
		if(currentXTagId == null)
			return null;
		XTagBo xt;
		if(xTagBoList!=null){
			int len = xTagBoList.size();
			while (len-- > 0) {
				xt = xTagBoList.get(len);
				if(currentXTagId.equals(xt.getId())){
					if(removeSelectedXTag)
						return xTagBoList.remove(len);
					return xTagBoList.get(len);
				}
			}
		}
		XTag xtag = xTagService.findXTagMapById(currentXTagId);
		if(null == xtag){
			return null;
		}
		xt = new XTagBo();
		xt.setId(xtag.getId());
		xt.setName(xtag.getName());
		return xt;
	}
	
	@Autowired
	private XTagService xTagService;

	@Autowired
	private GoodsSolrService goodsSolrService;


}
