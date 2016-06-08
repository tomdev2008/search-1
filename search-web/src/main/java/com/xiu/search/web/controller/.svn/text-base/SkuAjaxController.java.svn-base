package com.xiu.search.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.search.core.bo.SkuAjaxBo;
import com.xiu.search.core.config.XiuSearchProperty;
import com.xiu.search.core.service.SkuSolrService;
import com.xiu.search.web.config.XiuWebConstant;

/**
 * com.xiu.search.web.controller.SkuAjaxController.java

 * @Description: TODO 获取尺码的Controller

 * @author lvshuding   

 * @date 2014-3-6 下午5:28:56

 * @version V1.0
 */
@Controller
public class SkuAjaxController{

	private Logger LOG = Logger.getLogger(getClass());
	
	/**
	 * 每个商品对应尺码默认显示的个数,默认从数据库中读取
	 */
	private static int DEFAULT_SHOW_COUNT = XiuSearchProperty.getInstance().showSkuSizeCount();
	
	/**
	 * 每个商品对应尺码默认显示的最大个数，防止恶意攻击
	 * 
	 * 【注意】最新需求是有多少尺码展示多少尺码，故此处值放大（原定值为12），修改日期：2014-03-13 11：35
	 */
	private static final int MAX_SHOW_COUNT=1200; 
	
	/**
	 * 每个商品对应尺码显示个数的字段名
	 */
	private static final String SHOW_COUNT = "sc";
	
	/**
	 * 商品列表字段名,多个字段名用,分隔
	 */
	private static final String ITEM_IDS = "ids";
	
	/**
	 * 跨域请求时的参数
	 */
	private static final String JSONP = "callback";
	
	
	@Autowired
	private SkuSolrService skuSolrService;
	
	
	@RequestMapping(value="/sku-list")
	public void execute(HttpServletRequest req,HttpServletResponse res) throws InterruptedException{
		
		res.setCharacterEncoding("UTF-8");
        res.setHeader("Cache-Control","no-cache");
		res.setContentType("text/html; charset=UTF-8");
		//res.setContentType("applicaton/json; charset=UTF-8");
		
		String ids=req.getParameter(ITEM_IDS);
		if(ids==null || "".equals(ids.trim())){
			this.writeRespInfo("", res);
			LOG.error("商品ID列表无效");
			return;
		}
		
		/*
		 * 尺码展示
		 * 添加日期：2014-03-24 14:50
		 */
		if(!XiuSearchProperty.getInstance().enableBuildSkuSize()){
			this.writeRespInfo("", res);
			LOG.warn("系统配置不允许启动搜索SKU索引");
			return;
		}
		
		String[] idsArr=ids.split(",");
		
		int showCount = DEFAULT_SHOW_COUNT;
		
		ids=req.getParameter(SHOW_COUNT);
		if(ids!=null && !"".equals(ids.trim())){
			try{
				showCount=Integer.parseInt(ids);
				if(showCount>MAX_SHOW_COUNT){
					showCount=MAX_SHOW_COUNT;
				}
			}catch (NumberFormatException e) {
				showCount = DEFAULT_SHOW_COUNT;
				LOG.error("指定的尺码个数不是数值 【"+ids+"】：",e);
			}
		}
		
		String callback=req.getParameter(JSONP);
		callback=StringUtils.isEmpty(callback)?"":callback;
		
		StringBuilder sb=new StringBuilder();
		Map<String,List<SkuAjaxBo>> skuList=skuSolrService.searchSkuSizeByItemIDs(Arrays.asList(idsArr),showCount);
		if(null!=skuList){
			try {
				String similarItemJson=JSONArray.fromObject(skuList).toString();
				sb.append(similarItemJson);
			} catch (Exception e) {
				LOG.error("query sku list for json fail",e);
			}
		}
		
		String src=sb.toString();
		if (StringUtils.isNotBlank(callback)) { // jsonp协议
			src=callback.concat("(").concat(sb.toString()).concat(")");
		}
		this.writeRespInfo(src, res);
	}
	private void writeRespInfo(String src,HttpServletResponse res){
		try {
			PrintWriter out  = res.getWriter();
			out.write(src);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("SKU Json数据回写失败： ",e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/inventory/sku",method=RequestMethod.GET,params="format=jsonp",produces="text/html;charset=utf-8")
	public String skuInventory(@RequestParam List<String> sku,@RequestParam(value="callback",required=false) String jsonCallback){
		try {
		Map<String, Integer> inventoryMap = skuSolrService.getInventoryBySkuList(sku);
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		if (inventoryMap !=null) {
			
			for (Iterator<Map.Entry<String, Integer>> iter = inventoryMap.entrySet().iterator();iter.hasNext();) {
				Map.Entry<String, Integer> entry = iter.next();
				Map<String, Object> tempMap = new HashMap<String, Object>();
				if (entry.getValue()>0 && entry.getValue() <= XiuWebConstant.WARNING_INVENTERY_THRESHOLD) {
					tempMap.put("sku", entry.getKey());
					tempMap.put("tips", Boolean.TRUE);
					tempMap.put("num", entry.getValue());
				}else {
					tempMap.put("sku", entry.getKey());
					tempMap.put("tips", Boolean.FALSE);
				}
				resultList.add(tempMap);
			}
		}
		ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(resultList);
			if (StringUtils.isNotEmpty(jsonCallback)) {
				return jsonCallback+"("+json+")";
			}
			return json;
		} catch (JsonGenerationException e) {
			LOG.error("通过sku【"+sku+"】获取库存出现异常", e);
			e.printStackTrace();
		} catch (JsonMappingException e) {
			LOG.error("通过sku【"+sku+"】获取库存出现异常", e);
			e.printStackTrace();
		} catch (IOException e) {
			LOG.error("通过sku【"+sku+"】获取库存出现异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			LOG.error("通过sku【"+sku+"】获取库存出现异常", e);
			e.printStackTrace();
		}
		return "";
	}
}
