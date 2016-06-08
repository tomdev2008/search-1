package com.xiu.search.web.controller;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.search.core.bo.MktItemInfoBo;
import com.xiu.search.core.bof.MktItemInfoBof;
import com.xiu.search.core.solr.enumeration.FacetPriceRangeQueryEnum;
import com.xiu.search.core.solr.enumeration.MktTypeEnum;
import com.xiu.search.core.solr.params.MktItemInfoFatParams;
import com.xiu.search.core.util.XiuSearchStringUtils;
import com.xiu.search.web.vo.MktInfoAjaxParams;

/**
 * 三方市场商品信息Controller<br>
 * 此类均为Ajax方法
 * @author Leon
 *
 */
@Controller
@RequestMapping(value="/ajax")
public class MktItemInfoController {
	
	/**
	 * 获得商品数量
	 * @return
	 */
//	@RequestMapping(value="/mkt-item-count", produces="text/plain;charset=UTF-8")
//	public @ResponseBody String mktItemCount(MktInfoAjaxParams params,BindingResult bindingResult,Model model){
//		MktItemInfoFatParams fatParams = this.transformMktItemInfoParamsParams(params);
//		MktItemInfoBo result;
//		if(fatParams != null){
//			result = mktItemInfoBof.findItemInfo(fatParams);
//		}else{
//			result = new MktItemInfoBo();
//		}
//		String json = JSONObject.fromObject(result).toString();
//		return this.formatJsonResult(params.getJsoncallback(), json);
//	}
	
	
	
	private MktItemInfoFatParams transformMktItemInfoParamsParams(MktInfoAjaxParams params){
		MktItemInfoFatParams fatParams = new MktItemInfoFatParams();
		boolean valide = false;
		// 验证关键字
		if(StringUtils.isNotBlank(params.getKw())){
			int len = params.getKw().length();
			int clen = 0;
			StringBuffer sb = new StringBuffer(len > SearchController.MAX_SEARCH_LENGTH ? SearchController.MAX_SEARCH_LENGTH : len);
			char kwChar = '0';
			for (int i = 0; i < len; i++) {
				kwChar = params.getKw().charAt(i);
				if(XiuSearchStringUtils.isCJK(kwChar)){
					clen+=2;
				}else{
					clen++;
				}
				if(clen <= SearchController.MAX_SEARCH_LENGTH){
					sb.append(kwChar);
				}else{
					break;
				}
			}
			fatParams.setKeyword(sb.toString());
			valide = true;
		}
		// 验证运营分类
		if(null != params.getCat() && params.getCat().intValue()>0){
			fatParams.setCatalogId(params.getCat());
			valide = true;
		}
		// 是ebay商品或走秀商品
		if(null != params.getMkt()){
			fatParams.setMktType(MktTypeEnum.valueof(params.getMkt()));
		}else{
			fatParams.setMktType(MktTypeEnum.XIU);
		}
		// 验证品牌ID
//		if(null != params.getBid() &&  params.getBid().intValue()>0){
//			fatParams.setBrandId(params.getBid());
//			valide = true;
//		}
		
        // 验证品牌ID		由于修改了Bid的类型，所以修改了对比方式	William.zhang 20130506
        if(StringUtils.isNumeric(params.getBid()) &&  params.getBid().length() > 0){
        	int bid = Integer.parseInt(params.getBid());
	        if(null != params.getBid() &&  bid>0){
	            fatParams.setBrandId(bid);
	            valide = true;
	        }
        }
		
		
		
		// 验证价格区间
		if(null != params.getS_price() || null != params.getE_price()){
			if(null == params.getS_price()){
				 if(params.getE_price() < 0)
					 return null;
				fatParams.setEndPrice(Double.valueOf(params.getE_price()));
			}else if(null == params.getE_price()){
				if(params.getS_price() < 0)
					return null;
				fatParams.setStartPrice(Double.valueOf(params.getS_price()));
			}else if(params.getS_price()> params.getE_price() 
					|| params.getS_price() < 0 
					|| params.getE_price() < 0){
				// 如果价格不符合条件，直接跳转到查询无结果页面。
				return null;
			}else{
				fatParams.setStartPrice(Double.valueOf(params.getS_price()));
				fatParams.setEndPrice(Double.valueOf(params.getE_price()));
			}
			valide = true;
		}else if(null != params.getF_price()){
			// 如果没有用户自定义价格，则进行价格过滤
			// 验证价格过滤
			fatParams.setPriceRangeEnum(FacetPriceRangeQueryEnum.valueOf(params.getF_price().intValue()));
			valide = true;
		}
		if(null != params.getFilter()){
			// TODO 此处限制用户恶性输入过滤条件个数
			String[] filters = StringUtils.split(params.getFilter(), ";",SearchController.MAX_FILTER_SIZE*2);
			List<String> validFilterStrList = new ArrayList<String>(filters.length);
			String fStrTrim;
			for (int i = 0,len=filters.length,vi=0; i<len && vi<SearchController.MAX_FILTER_SIZE; i++) {
				fStrTrim = filters[i].trim();
				if(StringUtils.isNumeric(fStrTrim) 
						&& !validFilterStrList.contains(fStrTrim)){
					validFilterStrList.add(fStrTrim);
					vi++;
				}
			}
			if(validFilterStrList.size()>0)
				fatParams.setAttrValIds(validFilterStrList.toArray(new String[0]));
		}
		if(valide)
			return fatParams;
		return null;
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
	private MktItemInfoBof mktItemInfoBof;
	
}
