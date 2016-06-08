package com.xiu.search.web.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.xiu.search.core.service.SimilarGoodsSolrService;

@Controller
public class AjaxController {
	
	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	private SimilarGoodsSolrService similarGoodsSolrService;
	/**
	 * cms查询相似度商品排前
	 * @param partNumber
	 * @param res
	 */
	@RequestMapping(value="/similarItems-action")
	public void querySimilarItems(@RequestParam("partNumber") String partNumber,HttpServletResponse res){
		res.setContentType("text/html; charset=UTF-8");
		res.setCharacterEncoding("UTF-8");
		StringBuilder sb=new StringBuilder();
		sb.append("callback(");
		List<Map<String, String>> similarItemsList=similarGoodsSolrService.findSimilarGoodsByPartNumber(partNumber);
		if(null!=similarItemsList){
			try {
				String similarItemJson=JSONArray.fromObject(similarItemsList).toString();
				sb.append(similarItemJson);
			} catch (Exception e) {
				logger.error("query similar goods list for json fail",e);
			}
		}
		sb.append(")");
		try {
			res.getWriter().write(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("query similar goods list for generator json fail ",e);
		}
	}
}
