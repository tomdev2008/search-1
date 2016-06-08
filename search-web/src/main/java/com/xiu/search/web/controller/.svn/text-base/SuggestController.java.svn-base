package com.xiu.search.web.controller;

import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.xiu.search.core.bof.SuggestBof;
import com.xiu.search.core.solr.params.SuggestFatParams;
import com.xiu.search.web.vo.SuggestParams;
import com.xiu.solr.lexicon.client.common.MktTypeEnum;

/**
 * 自动联想Controller
 * @author Leon
 *
 */
@Controller
@RequestMapping(value="/ajax")
public class SuggestController {

	@RequestMapping(value="/autocomplete", produces="text/plain;charset=UTF-8")
	public @ResponseBody String execute(SuggestParams params,BindingResult bindingResult,Model model){
		if(StringUtils.isBlank(params.getQ())){
			return this.formatJsonResult(params.getJsoncallback(), "null");
		}
		SuggestFatParams solrParams = this.transformParams(params);
		String jsonRes = suggestBof.parseSuggestJsonStr(solrParams);
		return formatJsonResult(params.getJsoncallback(), jsonRes);
	}
	
	private SuggestFatParams transformParams(SuggestParams params){
		SuggestFatParams ret = new SuggestFatParams();
		try {
			ret.setKeyword(URLDecoder.decode(params.getQ(), "UTF-8"));
		} catch (Exception e) {
			ret.setKeyword("");
		}
		if(params.getLimit()>0)
			ret.setMaxRows(params.getLimit());
		ret.setType(params.getType());
		if(null != params.getMkt()){
			try {
				ret.setMktType(MktTypeEnum.valueOf(params.getMkt().toUpperCase()));
			} catch (Exception e) {
				// TODO: Ignore this exception
			}
		}
		return ret;
	}
	
	/**
	 * 格式化返回的jsonp
	 * @param jsonCallback
	 * @param content
	 * @return
	 */
	private String formatJsonResult(String jsonCallback,String content){
		if(StringUtils.isBlank(jsonCallback))
			return content;
		return jsonCallback+"(" + content +")";
	}
	
	
	@Autowired
	private SuggestBof suggestBof;
}
