package com.xiu.search.core.bof.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.search.core.bo.SuggestBo;
import com.xiu.search.core.bof.SuggestBof;
import com.xiu.search.core.service.QueryParserSolrService;
import com.xiu.search.core.solr.model.SuggestSolrModel;
import com.xiu.search.core.solr.params.SuggestFatParams;
import com.xiu.solr.lexicon.client.model.SuggestModel;
import com.xiu.solr.lexicon.client.service.SuggestService;

@Service("suggestBof")
public class SuggestBofImpl implements SuggestBof{

	
	public String parseSuggestJsonStr(SuggestFatParams params){
		List<SuggestModel> result = null;
		if("min".equals(params.getType())){
			result = suggestService.findSuggest(params.getKeyword(), params.getMaxRows(), 0, false, params.getMktType());
		}else{
			result = suggestService.findSuggest(params.getKeyword(), params.getMaxRows(), params.getMktType());
		}
		if(null == result || result.size()==0)
			return null;
		String jsonStr = null;
		try {
			List<Map<String,Object>> objList = new ArrayList<Map<String,Object>>();
			for (SuggestModel sm : result) {
				Map<String,Object> m = new HashMap<String, Object>();
				m.put("display", sm.getDisplay());
				m.put("matchValue", sm.getMatchValue());
				if(StringUtils.isNotBlank(sm.getCatalogId())){
					m.put("oclassId", sm.getCatalogId());
					m.put("oclassName", sm.getCatalogName());
				}
				m.put("type", sm.getType());
				objList.add(m);
			}
			// TODO 添加后台自定义功能
//			if(!"min".equals(params.getType()))
//				objList = this.newParseJsonObj(objList);
			jsonStr = JSONArray.fromObject(objList).toString();
//			PrintWriter out = getPrintWriter();
			//jsonStr = jsonStr.replaceAll("\\\"", "\\\\\"");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr;
	}
	
	
	
	public String parseSuggestJsonStr1(SuggestFatParams params){
		List<SuggestSolrModel> result = null;
		
		if("min".equals(params.getType())){
			result = queryParserSolrService.getSuggestTerms(params.getKeyword(), params.getMaxRows(),new String[]{"name"});
		}else{
			result = queryParserSolrService.getSuggestTerms(params.getKeyword(), params.getMaxRows(),new String[]{"name","oclass"});
		}
		if(null == result || result.size()==0){
			return null;
		}
		List<SuggestBo> smList = new ArrayList<SuggestBo>();
		boolean isFirst = true;
		String marthInput =	params.getKeyword().trim().replaceAll("[\\\\\\|!\\(\\)\\{\\}\\[\\]\\^\\$~\\?:\\s]+", "\\ ");
		int len = 0;
		loop1:
		for (SuggestSolrModel o : result) {
			len = o.getNameList().size();
			SuggestBo sm = null;
			for (int i = 0; i < len; i++) {
				if(o.getNameList().get(i).startsWith(marthInput)){
					sm = new SuggestBo();
					sm.setDisplay(o.getNameList().get(i));
//						sm.setCount(o.getCount());
					sm.setMatchValue(o.getNameList().get(i).startsWith(marthInput) ? o.getNameList().get(i) : o.getPyList().get(i).startsWith(marthInput) ? o.getPyList().get(i) : o.getsPyList().get(i));
					sm.setType(0);
					if(isFirst && !"min".equals(params.getType())){
						createPathSuggest(smList, sm, o.getOclassList());
						isFirst = false;
					}else{
						smList.add(sm);
					}
//						String oclasspath = this.getOclassShowPath(o.get);
					continue loop1;
				}
			}
			for (int i = 0; i < len; i++) {
				if(o.getPyList().get(i).startsWith(marthInput)){
					sm = new SuggestBo();
					sm.setDisplay(o.getNameList().get(i));
//						sm.setCount(o.getCount());
					sm.setMatchValue(o.getNameList().get(i).startsWith(marthInput) ? o.getNameList().get(i) : o.getPyList().get(i).startsWith(marthInput) ? o.getPyList().get(i) : o.getsPyList().get(i));
					sm.setType(0);
					if(isFirst && !"min".equals(params.getType())){
						createPathSuggest(smList, sm, o.getOclassList());
						isFirst = false;
					}else{
						smList.add(sm);
					}
					continue loop1;
				}
			}
			for (int i = 0; i < len; i++) {
				if(o.getsPyList().get(i).startsWith(marthInput)){
					sm = new SuggestBo();
					sm.setDisplay(o.getNameList().get(i));
//						sm.setCount(o.getCount());
					sm.setMatchValue(o.getNameList().get(i).startsWith(marthInput) ? o.getNameList().get(i) : o.getPyList().get(i).startsWith(marthInput) ? o.getPyList().get(i) : o.getsPyList().get(i));
					sm.setType(0);
					if(isFirst && !"min".equals(params.getType())){
						createPathSuggest(smList, sm, o.getOclassList());
						isFirst = false;
					}else{
						smList.add(sm);
					}
					continue loop1;
				}
			}
		}
		String jsonStr = null;
		try {
			List<Map<String,Object>> objList = new ArrayList<Map<String,Object>>();
			for (SuggestBo sm : smList) {
				Map<String,Object> m = new HashMap<String, Object>();
				m.put("display", sm.getDisplay());
				m.put("matchValue", sm.getMatchValue());
				if(StringUtils.isNotBlank(sm.getCatalogId())){
					m.put("oclassId", sm.getCatalogId());
					m.put("oclassName", sm.getCatalogName());
				}
				m.put("type", sm.getType());
				objList.add(m);
			}
			// TODO 添加后台自定义功能
//			if(!"min".equals(params.getType()))
//				objList = this.newParseJsonObj(objList);
			jsonStr = JSONArray.fromObject(objList).toString();
//			PrintWriter out = getPrintWriter();
			//jsonStr = jsonStr.replaceAll("\\\"", "\\\\\"");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr;
	}
	
	/**
	 * 显示在xx分类下搜索
	 * @param smList
	 * @param sm
	 * @param pathList
	 * @throws Exception
	 */
	private void createPathSuggest (List<SuggestBo> smList,SuggestBo sm,List<String> pathList){
		boolean hasFlag = false;
		if(null!=pathList){
			for (String str : pathList) {
				String oid =this.getOclassId(str);
				String sp = this.getOclassShowPath(str);
				if(StringUtils.isNotBlank(oid) && StringUtils.isNotBlank(sp)){
					hasFlag = true;
					SuggestBo smt = new SuggestBo();
					BeanUtils.copyProperties(sm, smt);
					smt.setCatalogId(oid);
					smt.setCatalogName(sp);
					smt.setType(1);
					smList.add(smt);
				}
			}
		}
		smList.add(sm);
	}
	
	/**
	 * 输入oclasspath路径得到显示ID
	 * @return
	 */
	private String getOclassId(String path){
		try {
			String[] arr = StringUtils.split(path, "|");
			String sp = arr[arr.length-1];
			String[] spArr = StringUtils.split(sp, ":");
			return spArr[0];
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	/**
	 * 输入oclasspath路径得到显示路径
	 * @return
	 */
	private String getOclassShowPath(String path){
		try {
			String[] arr = StringUtils.split(path, "|");
			StringBuffer sb = new StringBuffer(arr.length*2);
			for (int i = 0; i < arr.length; i++) {
				if(i > 0)sb.append("/");
				sb.append(StringUtils.split(arr[i], ":")[1]);
			}
			return sb.toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	@Autowired
	private QueryParserSolrService queryParserSolrService;
	@Autowired
	private SuggestService suggestService;
}
