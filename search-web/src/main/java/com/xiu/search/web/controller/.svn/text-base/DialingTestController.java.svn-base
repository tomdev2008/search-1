package com.xiu.search.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.params.SolrParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.search.core.service.SimilarGoodsSolrService;
import com.xiu.search.core.solr.model.GoodsSolrModel;
import com.xiu.search.solrj.service.SolrHttpService;

/**
 * 
 * com.xiu.search.web.controller.DialingTestController.java

 * @Description: TODO 拨测功能

 * @author lvshuding   

 * @date 2013-4-25 上午10:44:10

 * @version V1.0
 */
@Controller
public class DialingTestController extends BaseController{

	@Autowired
	private SimilarGoodsSolrService similarGoodsSolrService;
	
	@ResponseBody
	@RequestMapping(value="/dialing-test")
	public String execute(Model model){
		
		boolean dbSuccess=false,schSuccess=false;
		
		long itemCount=similarGoodsSolrService.selectCountForId();
		dbSuccess=(itemCount==-1?false:true);
		try {
			CommonsHttpSolrServer solrServer = SolrHttpService.getInstance().getSolrServer(GoodsSolrModel.class);
			Map<String, String[]> prms=new HashMap<String, String[]>();
	    	prms.put("q", new String[]{"*:*"});
	    	prms.put("facet", new String[]{"true"});
	    	prms.put("facet.field", new String[]{"oclassIds"});
	    	prms.put("rows", new String[]{"0"});
	    	prms.put("facet.limit", new String[]{"1"});
	    	SolrParams sp=new ModifiableSolrParams(prms);
			QueryResponse response = solrServer.query(sp);
			List<FacetField> fList = response.getFacetFields();
			schSuccess=(fList==null?false:true);
			fList=null;
		} catch (Exception e) {
			e.printStackTrace();
			schSuccess=false;
		}
		if(dbSuccess && schSuccess){
			return "state:0";
		}else if(!dbSuccess){
			return "state:-1";
		}else if(!schSuccess){
			return "state:-2";
		}else{
			return "state:-3";
		}
	}
	
}
