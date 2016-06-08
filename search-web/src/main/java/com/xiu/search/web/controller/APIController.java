package com.xiu.search.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.search.core.bo.BrandBo;
import com.xiu.search.core.bo.CatalogBo;
import com.xiu.search.core.bo.FacetFilterValueBo;
import com.xiu.search.core.bo.GoodsItemBo;
import com.xiu.search.core.bo.ListBo;
import com.xiu.search.core.bo.SearchBo;
import com.xiu.search.core.bof.BrandBof;
import com.xiu.search.core.bof.ListBof;
import com.xiu.search.core.bof.SearchBof;
import com.xiu.search.core.enumeration.RequestInletEnum;
import com.xiu.search.core.solr.enumeration.FacetPriceRangeQueryEnum;
import com.xiu.search.core.solr.enumeration.MktTypeEnum;
import com.xiu.search.core.solr.enumeration.SearchSortOrderEnum;
import com.xiu.search.core.solr.params.BrandFatParams;
import com.xiu.search.core.solr.params.ListFatParams;
import com.xiu.search.core.solr.params.SearchFatParams;
import com.xiu.search.core.util.XiuSearchStringUtils;
import com.xiu.search.dao.config.XiuSearchConfig;
import com.xiu.search.web.vo.BrandParams;
import com.xiu.search.web.vo.ListParams;
import com.xiu.search.web.vo.SearchParams;

/**
 * com.xiu.search.web.controller.APIController.java

 * @Description: TODO 对外接口Controller 包括 :search,brand,list

 * @author lvshuding   

 * @date 2013-5-22 上午10:03:38

 * @version V1.0
 */
@Controller
public class APIController extends BaseController {
	
	private Logger LOGGER = Logger.getLogger(getClass());
	
	/**
	 * 搜索最多字符数，1个汉字=2个字符<br>
	 * 禁止用户恶性的无限制输入参数
	 */
	public static final int MAX_SEARCH_LENGTH = 64;
	/**
	 * 每页展示商品数
	 */
	private static final int PAGE_SIZE = 10;
	/**
	 * 分组过滤的最多查询个数<br>
	 * 禁止用户恶性的无限制输入filter
	 */
	public static final int MAX_FILTER_SIZE = 10;
	
	/**
	 * 可选字段
	 */
	private enum OPTS{
		RW,//相关搜索词
		Cats,//分类列表
		Brands//品牌列表
	}
	
	/**
	 * 调用搜索服务的类型
	 */
	private enum S_TYPE{
		search,
		list,
		brand
	}
	
	/**
	 * 对外接口的日志头
	 */
	private static final String LOG_HEAD="【搜索AIP】";
	
	@Autowired
	private SearchBof searchBof;
	
	@Autowired
	private ListBof listBof;
	
	@Autowired
	private BrandBof brandBof;
	
	
	/*
	 * 
	 * 
	 * 使用 JSONArray.fromObject(ret)，出现中文乱码
	 * 
	 * 
	 * 
	 */
	
	private boolean checkParams(SearchParams params,Map<String, Object> ret,S_TYPE sType){
		// 若参数为空或关键字为空，直接返回到首页
		Map<String, Object> head=new HashMap<String, Object>();
		if(null == params){
			head.put("RetCode", 1001);
			head.put("Desc", "参数不能为空");
			ret.put("Head", head);
			return false;
		}
		switch (sType) {
		case search:
			if(null == params.getKw() || StringUtils.isBlank(params.getKw())){
				if(params.getTags()==null || StringUtils.isBlank(params.getTags())){
					head.put("RetCode", 1001);
					head.put("Desc", "搜索关键字不能为空");
					ret.put("Head", head);
					return false;
				}
			}else{
				try {
					params.setKw(URLDecoder.decode(params.getKw(),"UTF-8"));
				} catch (UnsupportedEncodingException e) {}
			}
			break;
		case list:
			if(null == params.getCat() || params.getCat().intValue() <= 0){
				head.put("RetCode", 1001);
				head.put("Desc", "运营分类ID不能为空");
				ret.put("Head", head);
				return false;
			}
			break;
		case brand:
			if(StringUtils.isBlank(params.getBid())){
				head.put("RetCode", 1001);
				head.put("Desc", "品牌ID不能为空");
				ret.put("Head", head);
				return false;
			}
			if(!StringUtils.isNumeric(params.getBid())){
				head.put("RetCode", 1002);
				head.put("Desc", "品牌ID无效");
				ret.put("Head", head);
				return false;
			}
			break;
		}
		if(null == params.getSrc() || StringUtils.isBlank(params.getSrc())){
			head.put("RetCode", 1001);
			head.put("Desc", "调用搜索服务的来源不能为空");
			ret.put("Head", head);
			return false;
		}
//		if(null == params.getMkt()){
//			head.put("RetCode", 1001);
//			head.put("Desc", "市场来源不能为空");
//			ret.put("Head", head);
//			return false;
//		}else if(MktTypeEnum.valueof(params.getMkt())==null){
//			head.put("RetCode", 1002);
//			head.put("Desc", "无效的市场来源");
//			ret.put("Head", head);
//			return false;
//		}
		if(params.getMkt() != null && MktTypeEnum.valueof(params.getMkt())==null){
			head.put("RetCode", 1002);
			head.put("Desc", "无效的市场来源");
			ret.put("Head", head);
			return false;
		}
		
		if(null != params.getS_price() || null != params.getE_price()){
			if(params.getE_price()!=null && params.getE_price() < 0){
				head.put("RetCode", 1002);
				head.put("Desc", "结束价格小于0，无效");
				ret.put("Head", head);
				return false;
			}
			if(params.getS_price()!=null && params.getS_price() < 0){
				head.put("RetCode", 1002);
				head.put("Desc", "开始价格小于0，无效");
				ret.put("Head", head);
				return false;
			}
			if(null != params.getS_price() && null != params.getE_price()){
				if(params.getS_price()> params.getE_price()){
					head.put("RetCode", 1002);
					head.put("Desc", "开始价格大于结束价格，无效");
					ret.put("Head", head);
					return false;
				}
			}
		}
		if(null != params.getOpts()){
			String[] opts=params.getOpts().split(";");
			String opt="";
			for(int i=0;i<opts.length;i++){
				opt=opts[i];
				if(OPTS.Cats.name().equals(opt) || OPTS.Brands.name().equals(opt) || OPTS.RW.name().equals(opt)){
					opt="";
				}else{
					break;
				}
			}
			if(opt!=""){
				head.put("RetCode", 1002);
				head.put("Desc", "可选字段名"+opt+"无效");
				ret.put("Head", head);
				return false;
			}
		}
		
		if(params.getS()==null || params.getS().intValue()==0){
			params.setS(PAGE_SIZE);
		}
		
		return true;
	}
	
	/**
	 * 关键字搜索服务入口。 支持：官网、Ebay、Ebay-Tag
	 * @param params
	 * @param bindingResult
	 * @param req
	 * @param res
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@ResponseBody
	@RequestMapping(value="/search-api-action")
	public Map<String, Object> search(SearchParams params,BindingResult bindingResult,HttpServletRequest req,HttpServletResponse res,Model model) throws InterruptedException{
		long starttime=System.currentTimeMillis();
		
//		Enumeration<String> hfs =req.getHeaderNames();
//		while (hfs.hasMoreElements()) {
//			System.out.println(hfs.nextElement());
//		}
//		
//		System.out.println("req.getHeader(\"referer\")"+req.getHeader("referer"));
//		System.out.println("req.getHeader(\"Referer\")"+req.getHeader("Referer"));
		
		
		Map<String, Object> ret=new HashMap<String, Object>();
		
		if(!this.checkParams(params, ret,S_TYPE.search)){
			return ret;
		}
		
		LOGGER.info(LOG_HEAD+" 调用Search的来源为："+params.getSrc());
		
		SearchBo xiuSearchBo = null;
		
		SearchFatParams solrParams = null;
		
		if(this.retCurrentMkt(params.getMkt())==MktTypeEnum.EBAY){
			if(params.getKw() != null && StringUtils.isNotBlank(params.getKw())){
				solrParams= this.transformSearchEbayParams(params);
				solrParams.setRequestInletEnum(RequestInletEnum.EBAY_SEARCH_PAGE);
				xiuSearchBo = searchBof.findSearchEbayPageResult(solrParams);
			}else{
				solrParams= this.transformSearchEbayTagsParams(params);
				// 如果搜索关键字是数字
				if(XiuSearchStringUtils.isIntegerNumber(params.getTags())){
					solrParams.setSearchTagsId(Integer.valueOf(params.getTags()));
				}else if(StringUtils.isNotBlank(params.getTags())){// 如果搜索关键字不为空
					solrParams.setSearchTags(params.getTags());
				}
				solrParams.setRequestInletEnum(RequestInletEnum.EBAY_TAGS_SEARCH_PAGE);
				xiuSearchBo = searchBof.findSearchTagsPageResult(solrParams);
			}
		}else{
			solrParams= this.transformSearchParams(params);
			solrParams.setRequestInletEnum(RequestInletEnum.SEARCH_PAGE);
			xiuSearchBo = searchBof.findSearchXiuPageResult(solrParams);
		}
		
		// 1. 写头信息
		Map<String, Object> head=new HashMap<String, Object>();
		head.put("RetCode", 0);
		head.put("Desc", "SUCCESS");
		
		List<Map<String, Object>> Goods=new ArrayList<Map<String,Object>>();
		// 2. 封装数据		
		if(null == xiuSearchBo || null == xiuSearchBo.getGoodsItemList() || xiuSearchBo.getGoodsItemList().size()==0){
			head.put("GNum", 0);
		}else{
			//过滤掉促销的两个ID 
			APIController.filterCatalogList(xiuSearchBo);
			head.put("GNum", xiuSearchBo.getPage().getRecordCount());
			
			this.buildGoodsJson(xiuSearchBo,Goods);
		}
		ret.put("Head", head);
		ret.put("Goods", Goods);
			
		// 3.封装可选字段 搜索只有分类和相关搜索词
		if(params.getOpts()!=null){
			String[] opts=params.getOpts().split(";");
			String opt="";
			for(int i=0;i<opts.length;i++){
				opt=opts[i];
				if(OPTS.Cats.name().equals(opt)){
					if(solrParams.getCatalogId()!=null && solrParams.getCatalogId().intValue()>0){
						this.buildCats(xiuSearchBo.getSelectedCatalogTree(),ret);
					}else{
						this.buildCats2Sch(xiuSearchBo.getCatalogBoList(),ret);
					}
				}else if(OPTS.RW.name().equals(opt)){
					if(xiuSearchBo.getRelatedTerms()!=null){
						ret.put("RW", xiuSearchBo.getRelatedTerms());
					}else{
						ret.put("RW", "[]");
					}
				}
			}
		}
		LOGGER.warn(LOG_HEAD+"根据关键字查询商品列表：kw : " + params.getKw() + ", time : " + (System.currentTimeMillis() - starttime) + " ms.");
	        
		return ret;
	}
	
	/**
	 * 封装分类数据(搜索)
	 * @param cList
	 * @param ret
	 */
	private void buildCats2Sch(List<CatalogBo> cList,Map<String, Object> ret){
		if(cList==null || cList.size()==0){
			ret.put("Cats", "[]");
			return;
		}
		List<Map<String, Object>> cats=new ArrayList<Map<String,Object>>();
		Map<String, Object> cMap=null;
		for(CatalogBo cb:cList){
			cMap=new HashMap<String, Object>();
			cMap.put("id", cb.getCatalogId());
			cMap.put("name", cb.getCatalogName());
			cats.add(cMap);
		}
		ret.put("Cats", cats);
	}
	
	/**
	 * 封装分类数据(list,brand)
	 * @param tree
	 * @param ret
	 */
	private void buildCats(CatalogBo tree,Map<String, Object> ret){
		if(tree==null){
			ret.put("Cats", "[]");
			return;
		}
		if(tree.getChildCatalog()==null || tree.getChildCatalog().size()==0){
			ret.put("Cats", "[]");
			return;
		}
		List<Map<String, Object>> cats=new ArrayList<Map<String,Object>>();
		Map<String, Object> cMap=null;
		List<CatalogBo> cList=tree.getChildCatalog();
		for(CatalogBo cb:cList){
			cMap=new HashMap<String, Object>();
			cMap.put("id", cb.getCatalogId());
			cMap.put("name", cb.getCatalogName());
			cMap.put("childs",this.buildCats3(cb));
			cats.add(cMap);
		}
		cMap=new HashMap<String, Object>();
		cMap.put("id", tree.getCatalogId());
		cMap.put("name", tree.getCatalogName());
		cMap.put("childs",cats);
		ret.put("Cats", cMap);
	}
	
	private List<Map<String, Object>> buildCats3(CatalogBo b2){
		List<Map<String, Object>> b2List=new ArrayList<Map<String,Object>>();
		if(b2==null || b2.getChildCatalog()==null || b2.getChildCatalog().size()==0){
			return b2List;
		}
		Map<String, Object> cMap=null;
		List<CatalogBo> cList=b2.getChildCatalog();
		for(CatalogBo cb:cList){
			cMap=new HashMap<String, Object>();
			cMap.put("id", cb.getCatalogId());
			cMap.put("name", cb.getCatalogName());
			b2List.add(cMap);
		}
		return b2List;
	}
	
	/**
	 * 封装商品数据
	 * @param bo
	 * @param goods
	 */
	private void buildGoodsJson(SearchBo bo,List<Map<String,Object>> goods){
		List<GoodsItemBo> gList=bo.getGoodsItemList();
		Map<String, Object> curG=null;
		for(GoodsItemBo g:gList){
			curG=new HashMap<String, Object>();
			curG.put("gId",Long.valueOf(g.getId()));
			curG.put("gName", g.getNameHighlight());
			curG.put("gImg", g.getImgUrl100());
			curG.put("showP",g.getXiuPrice());
			curG.put("mktP",g.getMktPrice());
			curG.put("sOnSale",g.isBuyNow()?"1":"0");
			curG.put("disCount", String.valueOf(g.getDisCount()));
			goods.add(curG);
		}
	}
	
	/**
	 * 获取当前Mkt类型
	 * @param mkt
	 * @return
	 */
	private MktTypeEnum retCurrentMkt(Integer mkt){
		if(mkt==null){
			return null;
		}
		return MktTypeEnum.valueof(mkt);
	}
	
	/**
	 * 品牌 ——官网、Ebay
	 * @param params
	 * @param req
	 * @param res
	 * @return
	 * @throws InterruptedException
	 */
	@ResponseBody
	@RequestMapping(value="/brand-api-action")
	public Map<String, Object> brand(BrandParams params,HttpServletRequest req,HttpServletResponse res) throws InterruptedException{
		long starttime=System.currentTimeMillis();
		Map<String, Object> ret=new HashMap<String, Object>();
		
		if(!this.checkParams(params, ret,S_TYPE.brand)){
			return ret;
		}
		LOGGER.info(LOG_HEAD+" 调用Brand的来源为："+params.getSrc());
		
		BrandFatParams fatParams = null;
		
		
		BrandBo brandBo=null;
		// 调用业务处理，
		if(MktTypeEnum.EBAY==this.retCurrentMkt(params.getMkt())){
			fatParams = this.transformBrandEbayParams(params);
			fatParams.setRequestInletEnum(RequestInletEnum.EBAY_BRAND_PAGE);
        	brandBo = brandBof.findBrandEbayPageResult(fatParams);
		}else{
			fatParams = this.transformBrandParams(params);
			fatParams.setRequestInletEnum(RequestInletEnum.BRAND_PAGE);
			brandBo = brandBof.findBrandXiuPageResult(fatParams);
		}
		
        // 1. 写头信息
 		Map<String, Object> head=new HashMap<String, Object>();
 		
 		 if(StringUtils.isEmpty(brandBo.getBrandName())){
 			head.put("RetCode", 2002);
 			head.put("Desc", "不存在该品牌");
 			ret.put("Head", head);
 			return ret;
 		}else{
 			head.put("RetCode", 0);
 			head.put("Desc", "SUCCESS");
 		}
 		List<Map<String, Object>> Goods=new ArrayList<Map<String,Object>>();
 		
 		// 2. 封装数据		
 		if(brandBo.getGoodsItemList()==null || brandBo.getPage().getRecordCount()==0){
 			head.put("GNum", 0);
 		}else{
 			//过滤掉促销的两个ID 
 			APIController.filterCatalogList(brandBo);
 	        
 			head.put("GNum", brandBo.getPage().getRecordCount());
 			
 			this.buildGoodsJson(brandBo,Goods);
 		}
 		ret.put("Head", head);
 		ret.put("Goods", Goods);
 			
 		// 3.封装可选字段 搜索只有分类和相关搜索词
 		if(params.getOpts()!=null){
 			String[] opts=params.getOpts().split(";");
 			String opt="";
 			for(int i=0;i<opts.length;i++){
 				opt=opts[i];
 				if(OPTS.Cats.name().equals(opt)){
 					if(params.getCat()==null || params.getCat().intValue()==0){
 						this.buildCats2Sch(brandBo.getCatalogBoList(),ret);
 					}else{
 						this.buildCats(brandBo.getSelectedCatalogTree(),ret);
 					}
 					break;
 				}
 			}
 		}
         
        LOGGER.warn(LOG_HEAD+"根据品牌查询商品列表：brandId : " + fatParams.getBrandId() + ", time : " + (System.currentTimeMillis() - starttime) + " ms.");
         
 		return ret;
	}
	
	/**
	 * 商品分类搜索服务入口。支持：官网 、Ebay
	 * @param params
	 * @param bindingResult
	 * @param req
	 * @param res
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@ResponseBody
	@RequestMapping(value="/list-api-action")
	public Map<String, Object> list(ListParams params,HttpServletRequest req,HttpServletResponse res,Model model) throws InterruptedException{
		
		long starttime = System.currentTimeMillis();
		 
		Map<String, Object> ret=new HashMap<String, Object>();
		if(!this.checkParams(params, ret,S_TYPE.list)){
			return ret;
		}
		LOGGER.info(LOG_HEAD+" 调用List的来源为："+params.getSrc());
		
		ListBo listBo = null;
        ListFatParams fatParams = null;
        
        // 调用业务处理，获取bo用于页面展示
        if(this.retCurrentMkt(params.getMkt())==MktTypeEnum.EBAY){
        	fatParams = this.transformListEbayParams(params);
        	fatParams.setRequestInletEnum(RequestInletEnum.EBAY_LIST_PAGE);
        	listBo = listBof.findListEbayPageResult(fatParams);
		}else{
			fatParams = this.transformListParams(params);
			fatParams.setRequestInletEnum(RequestInletEnum.LIST_PAGE);
			listBo = listBof.findListXiuPageResult(fatParams);
		}
		
		// 1. 写头信息
		Map<String, Object> head=new HashMap<String, Object>();
		
		if(null == listBo){
			head.put("RetCode", 2001);
			head.put("Desc", "不存在该分类");
			ret.put("Head", head);
			return ret;
		}else{
			head.put("RetCode", 0);
			head.put("Desc", "SUCCESS");
		}
		List<Map<String, Object>> Goods=new ArrayList<Map<String,Object>>();
		
		// 2. 封装数据		
		if(listBo.getGoodsItemList()==null || listBo.getPage().getRecordCount()==0){
			head.put("GNum", 0);
		}else{
			//过滤掉促销的两个ID 
			APIController.filterCatalogList(listBo);
	        
			head.put("GNum", listBo.getPage().getRecordCount());
			
			this.buildGoodsJson(listBo,Goods);
		}
		ret.put("Head", head);
		ret.put("Goods", Goods);
			
		// 3.封装可选字段 搜索只有分类和相关搜索词
		if(params.getOpts()!=null){
			String[] opts=params.getOpts().split(";");
			String opt="";
			for(int i=0;i<opts.length;i++){
				opt=opts[i];
				if(OPTS.Cats.name().equals(opt)){
					this.buildCats(listBo.getSelectedCatalogTree(),ret);
				}else if(OPTS.Brands.name().equals(opt)){
					this.buildBrands(listBo.getFacetFilterValueBoList(),ret);
				}
			}
		}
        
        LOGGER.warn(LOG_HEAD+"根据运营分类查询商品列表：catid : " + fatParams.getCatalogId() + ", time : " + (System.currentTimeMillis() - starttime) + " ms.");
        
		return ret;
	}
	
	/**
	 * 封装品牌列表
	 * @param bList
	 * @param ret
	 */
	private void buildBrands(List<FacetFilterValueBo> bList,Map<String, Object> ret){
		if(bList==null || bList.size()==0){
			ret.put("Brands", "[]");
			return;
		}
		List<Map<String,Object>> objList = new ArrayList<Map<String,Object>>();
		Map<String,Object> map =null;
    	for(FacetFilterValueBo b : bList){
    		map = new HashMap<String,Object>();
        	map.put("name", b.getName());
        	map.put("pyName", b.getPyName());
        	map.put("id", b.getId());
        	objList.add(map);
    	}
    	ret.put("Brands", objList);
	}
	
	/**
	 * 过滤掉促销的两个ID 
	 * @param searchBo
	 */
	public static void filterCatalogList(SearchBo searchBo){
		if(searchBo== null 
				|| CollectionUtils.isEmpty(searchBo.getCatalogBoList())){
			return;
		}
		List<CatalogBo> catalogBos = searchBo.getCatalogBoList();
		int len = catalogBos.size();
		CatalogBo cat;
		while (len -- > 0){
			cat = catalogBos.get(len);
			if(cat.getCatalogId() == 215269
					|| cat.getCatalogId() == 100000220){
				catalogBos.remove(len);
			}
		}
	}
	
	
	/**
	 * 列表——官网
	 * @param params
	 * @return
	 */
	private ListFatParams transformListParams(ListParams params){
	    
	    ListFatParams fatParams = new ListFatParams(params.getS());
	    List<Long> brandIds = new ArrayList<Long>();

        // 运营分类
        fatParams.setCatalogId(params.getCat());
        
        // 验证品牌ID 新品牌组装规则，前台传入String 这里转换为INT		William.zhang 20130506
        if(params.getBid() != null && params.getBid().length() > 0){
        	if(params.getBid().indexOf('|') >= 0){
        		String[] bids = StringUtils.split(params.getBid(), "|");
        		for(String str : bids){
        			if(StringUtils.isNumeric(str) && params.getBid().length() > 0){
        				brandIds.add(Long.parseLong(str));
        			}
        		}
        		fatParams.setBrandIds(brandIds);
        	}else{
        		if(StringUtils.isNumeric(params.getBid()) && params.getBid().length() > 0){
        			brandIds.add(Long.parseLong(params.getBid()));
        		}
        		fatParams.setBrandIds(brandIds);
        	}
        }
        
        // 验证排序
        SearchSortOrderEnum sort = null;
        if(null != params.getSort()){
            sort = SearchSortOrderEnum.valueOfSortOrder(params.getSort());
        }
        if(null == sort 
                || SearchSortOrderEnum.VOLUME_ASC.equals(sort) 
                || SearchSortOrderEnum.VOLUME_DESC.equals(sort)
                || SearchSortOrderEnum.SCORE_AMOUNT_DESC.equals(sort) ){
            sort = SearchSortOrderEnum.AMOUNT_DESC;
        }
        fatParams.setSort(sort);
        
        // 验证是否有库存
        if(null != params.getBa()){
            fatParams.setBuyableFlag(1 == params.getBa().intValue());
        }
        
        //店中店编码
  		if(StringUtils.isNotBlank(params.getP_code())
  				&& !params.getP_code().equals(XiuSearchConfig.getPropertieValue(XiuSearchConfig.EBAY_PROVIDER_CODE)))
  			fatParams.setProviderCode(params.getP_code());
  		
        // 验证翻页
        if(null != params.getP() && params.getP()>0){
            fatParams.getPage().setPageNo(params.getP());
        }
        
        // 验证价格区间
        if(null != params.getS_price() || null != params.getE_price()){
        	if(null != params.getE_price()){
				fatParams.setEndPrice(Double.valueOf(params.getE_price()));
			}
        	if(null != params.getS_price()){
				fatParams.setStartPrice(Double.valueOf(params.getS_price()));
        	}
        }else if(null != params.getF_price()){
            // 如果没有用户自定义价格，则进行价格过滤
            // 验证价格过滤
            fatParams.setPriceRangeEnum(FacetPriceRangeQueryEnum.valueOf(params.getF_price().intValue()));
        }
        
        // 是否第一次请求
        fatParams.setFirstRequest(null == params.getKw()
                && null == params.getBid()
                && null == params.getE_price()
                && null == params.getF_price()
                && null == params.getFilter()
                && (null == params.getP() || params.getP().intValue() == 1)
                && null == params.getS_price()
                && null == params.getSort()
                && null == params.getBa());
        // 是否根据商品数量自动切换 mkt tab
        fatParams.setAutoMktSwitchFlag(fatParams.isFirstRequest());
        if(null != params.getFilter()){
        	// TODO 此处限制用户恶性输入过滤条件个数
            String[] filters = StringUtils.split(params.getFilter(), ";",MAX_FILTER_SIZE*2);
            
          //属性查询新的业务逻辑，支持多选	William.zhang	20130507
            List<List<String>> attrValIdList = new ArrayList<List<String>>(filters.length);
            for(String vals : filters){
            	String[] items = StringUtils.split(vals,"|");
            	List<String> list = new ArrayList<String>(vals.length());
            	for(String item : items){
            		String fStrTrim = item.trim();
            		if(XiuSearchStringUtils.isIntegerNumber(fStrTrim) 
                            && !list.contains(fStrTrim)){
            			list.add(fStrTrim);
                    }
            	}
            	attrValIdList.add(list);
            }
            if(attrValIdList.size()>0){
            	fatParams.setAttrValIdList(attrValIdList);
            }
        }
        // 判断页面传入的商品类别，拆分为List，验证是否为数字
	    if ( StringUtils.isNotEmpty(params.getItemshowtype()) ) {
	        String[] showTypes = params.getItemshowtype().split("_");
	        if (null != showTypes && showTypes.length > 0) {
	            List<Integer> itemShowType = new ArrayList<Integer>(showTypes.length);
	            for (String s : showTypes) {
	                try {
	                    itemShowType.add(Integer.valueOf(s));
                    } catch (java.lang.NumberFormatException e) { }
                }
	            if (itemShowType.size() > 0) {
	                fatParams.setItemShowType(itemShowType);
	            }
	        }
	    }
        
	    //新的业务逻辑，在页面增加itemShowType用户输入项	William.zhang	20130509
	    if(null != params.getChannel()){
	    	if( StringUtils.isNotBlank(params.getChannel()) && StringUtils.isNumeric( params.getChannel())){
	    		if(fatParams.getItemShowType() != null){
		    		fatParams.getItemShowType().add(Integer.parseInt(params.getChannel()));
		    	}else{
		    		 List<Integer> itemShowType = new ArrayList<Integer>(1);
		    		 itemShowType.add(Integer.parseInt(params.getChannel()));
		    		 fatParams.setItemShowType(itemShowType);
		    	}
	    		
	    	}
	    }
	    
	    return fatParams;
	}
	
	/**
	 * List——Ebay
	 * @param params
	 * @return
	 */
	private ListFatParams transformListEbayParams(ListParams params){
	    
	    ListFatParams fatParams = new ListFatParams(params.getS());
	    List<Long> brandIds = new ArrayList<Long>();
        // 运营分类
        fatParams.setCatalogId(params.getCat());
        // 验证品牌ID 新品牌组装规则，前台传入String 这里转换为INT		William.zhang 20130506
        if(params.getBid() != null && params.getBid().length() > 0){
        	if(params.getBid().indexOf('|') >= 0){
        		String[] bids = StringUtils.split(params.getBid(), "|");
        		for(String str : bids){
        			if(StringUtils.isNumeric(str) && params.getBid().length() > 0){
        				brandIds.add(Long.parseLong(str));
        			}
        		}
        		fatParams.setBrandIds(brandIds);
        	}else{
        		if(StringUtils.isNumeric(params.getBid()) && params.getBid().length() > 0){
        			brandIds.add(Long.parseLong(params.getBid()));
        		}
        		fatParams.setBrandIds(brandIds);
        	}
        }
        // 验证排序
        SearchSortOrderEnum sort = null;
        if(null != params.getSort()){
            sort = SearchSortOrderEnum.valueOfSortOrder(params.getSort());
        }
        if(null == sort 
                || SearchSortOrderEnum.VOLUME_ASC.equals(sort) 
                || SearchSortOrderEnum.VOLUME_DESC.equals(sort)
                || SearchSortOrderEnum.SCORE_AMOUNT_DESC.equals(sort) ){
            sort = SearchSortOrderEnum.AMOUNT_DESC;
        }
        fatParams.setSort(sort);
        
        // 验证是否有库存
        if(null != params.getBa()){
            fatParams.setBuyableFlag(1 == params.getBa().intValue());
        }
  		fatParams.setMktType(MktTypeEnum.EBAY);
        // 验证翻页
        if(null != params.getP() && params.getP()>0){
            fatParams.getPage().setPageNo(params.getP());
        }
        // 验证价格区间
 		if(null != params.getS_price() || null != params.getE_price()){
 			if(null != params.getE_price()){
 				fatParams.setEndPrice(Double.valueOf(params.getE_price()));
 			}
 			if(null != params.getS_price()){
 				fatParams.setStartPrice(Double.valueOf(params.getS_price()));
 			}
 		}else if(null != params.getF_price()){
 			// 如果没有用户自定义价格，则进行价格过滤
 			// 验证价格过滤
 			fatParams.setPriceRangeEnum(FacetPriceRangeQueryEnum.valueOf(params.getF_price().intValue()));
 		}
        
        //是否第一次请求
        fatParams.setFirstRequest(null == params.getCat()
                && null == params.getBid()
                && null == params.getE_price()
                && null == params.getF_price()
                && null == params.getFilter()
                && (null == params.getP() || params.getP().intValue() == 1)
                && null == params.getS_price()
                && null == params.getSort()
                && null == params.getBa());
        
        if(null != params.getFilter()){
            String[] filters = StringUtils.split(params.getFilter(), ";",MAX_FILTER_SIZE*2);
            
			//属性查询新的业务逻辑，支持多选	William.zhang	20130513
			List<List<String>> attrValIdList = new ArrayList<List<String>>(filters.length);
            for(String vals : filters){
            	String[] items = StringUtils.split(vals,"|");
            	List<String> list = new ArrayList<String>(vals.length());
            	for(String item : items){
            		String fStrTrim = item.trim();
            		if(XiuSearchStringUtils.isIntegerNumber(fStrTrim) 
                            && !list.contains(fStrTrim)){
            			list.add(fStrTrim);
                    }
            	}
            	attrValIdList.add(list);
            }
            if(attrValIdList.size()>0){
            	fatParams.setAttrValIdList(attrValIdList);
            }
        }
        //新的业务逻辑，在页面增加itemShowType用户输入项	William.zhang	20130509
	    if(null != params.getChannel()){
	    	if( StringUtils.isNotBlank(params.getChannel()) && StringUtils.isNumeric( params.getChannel())){
	    		if(fatParams.getItemShowType() != null){
		    		fatParams.getItemShowType().add(Integer.parseInt(params.getChannel()));
		    	}else{
		    		 List<Integer> itemShowType = new ArrayList<Integer>(1);
		    		 itemShowType.add(Integer.parseInt(params.getChannel()));
		    		 fatParams.setItemShowType(itemShowType);
		    	}
	    		
	    	}
	    }
	    return fatParams;
	}

	/**
	 * 搜索-Ebay
	 * @param params
	 * @return
	 */
	private SearchFatParams transformSearchEbayParams(SearchParams params){
		SearchFatParams fatParams = new SearchFatParams(params.getS());
		List<Long> brandIds = new ArrayList<Long>();
		// 验证关键字
		if(StringUtils.isNotBlank(params.getKw())){
			fatParams.setKeyword(XiuSearchStringUtils.getValidLengthTerm(params.getKw(), MAX_SEARCH_LENGTH));
		}else{
			// 如果搜索关键字是数字
			if(XiuSearchStringUtils.isIntegerNumber(params.getTags())){
				fatParams.setSearchTagsId(Integer.valueOf(params.getTags()));
			}else if(StringUtils.isNotBlank(params.getTags())){// 如果搜索关键字不为空
				fatParams.setSearchTags(params.getTags());
			}
		}
		// 验证运营分类
		if(null != params.getCat() && params.getCat().intValue()>0){
			fatParams.setCatalogId(params.getCat());
		}
		// 验证品牌ID 新品牌组装规则，前台传入String 这里转换为INT		William.zhang 20130506
        if(params.getBid() != null && params.getBid().length() > 0){
        	if(params.getBid().indexOf('|') >= 0){
        		String[] bids = StringUtils.split(params.getBid(), "|");
        		for(String str : bids){
        			if(StringUtils.isNumeric(str) && params.getBid().length() > 0){
        				brandIds.add(Long.parseLong(str));
        			}
        		}
        		fatParams.setBrandIds(brandIds);
        	}else{
        		if(StringUtils.isNumeric(params.getBid()) && params.getBid().length() > 0){
        			brandIds.add(Long.parseLong(params.getBid()));
        		}
        		fatParams.setBrandIds(brandIds);
        	}
        }
		// 验证排序
		SearchSortOrderEnum sort = null;
		if(null != params.getSort()){
			sort = SearchSortOrderEnum.valueOfSortOrder(params.getSort());
		}
		if(null == sort 
				|| SearchSortOrderEnum.VOLUME_ASC.equals(sort) 
				|| SearchSortOrderEnum.VOLUME_DESC.equals(sort)
				|| SearchSortOrderEnum.AMOUNT_ASC.equals(sort)
				|| SearchSortOrderEnum.AMOUNT_DESC.equals(sort)){
			sort = SearchSortOrderEnum.SCORE_AMOUNT_DESC;
		}
		fatParams.setSort(sort);
		// 是ebay商品
		fatParams.setMktType(MktTypeEnum.EBAY);
		// 验证是否有库存
		if(null != params.getBa()){
			fatParams.setBuyableFlag(1 == params.getBa().intValue());
		}
		// 验证翻页
		if(null != params.getP() && params.getP()>0){
			fatParams.getPage().setPageNo(params.getP());
		}
		// 验证价格区间
		if(null != params.getS_price() || null != params.getE_price()){
			if(null != params.getE_price()){
				fatParams.setEndPrice(Double.valueOf(params.getE_price()));
			}
			if(null != params.getS_price()){
				fatParams.setStartPrice(Double.valueOf(params.getS_price()));
			}
		}else if(null != params.getF_price()){
			// 如果没有用户自定义价格，则进行价格过滤
			// 验证价格过滤
			fatParams.setPriceRangeEnum(FacetPriceRangeQueryEnum.valueOf(params.getF_price().intValue()));
		}
		//是否第一次请求
		fatParams.setFirstRequest(null == params.getCat()
				&& null == params.getBid()
				&& null == params.getE_price()
				&& null == params.getF_price()
				&& null == params.getFilter()
				&& null == params.getP()
				&& null == params.getS_price()
				&& null == params.getSort()
				&& null == params.getBa());
		if(null != params.getFilter()){
			// TODO 此处限制用户恶性输入过滤条件个数
			String[] filters = StringUtils.split(params.getFilter(), ";",MAX_FILTER_SIZE*2);
			
			//属性查询新的业务逻辑，支持多选	William.zhang	20130507
			List<List<String>> attrValIdList = new ArrayList<List<String>>(filters.length);
            for(String vals : filters){
            	String[] items = StringUtils.split(vals,"|");
            	List<String> list = new ArrayList<String>(vals.length());
            	for(String item : items){
            		String fStrTrim = item.trim();
            		if(XiuSearchStringUtils.isIntegerNumber(fStrTrim) 
                            && !list.contains(fStrTrim)){
            			list.add(fStrTrim);
                    }
            	}
            	attrValIdList.add(list);
            }
            if(attrValIdList.size()>0){
            	fatParams.setAttrValIdList(attrValIdList);
            }
		}
        //新的业务逻辑，在页面增加itemShowType用户输入项	William.zhang	20130509
	    if(null != params.getChannel()){
	    	if( StringUtils.isNotBlank(params.getChannel()) && StringUtils.isNumeric( params.getChannel())){
	    		if(fatParams.getItemShowType() != null){
		    		fatParams.getItemShowType().add(Integer.parseInt(params.getChannel()));
		    	}else{
		    		 List<Integer> itemShowType = new ArrayList<Integer>(1);
		    		 itemShowType.add(Integer.parseInt(params.getChannel()));
		    		 fatParams.setItemShowType(itemShowType);
		    	}
	    		
	    	}
	    }
		return fatParams;
	}
	
	/**
	 * 搜索-Ebay——Tag
	 * @param params
	 * @return
	 */
	private SearchFatParams transformSearchEbayTagsParams(SearchParams params){
		SearchFatParams fatParams = new SearchFatParams(params.getS());
		List<Long> brandIds = new ArrayList<Long>();
		// 验证关键字
		if(StringUtils.isNotBlank(params.getKw())){
			fatParams.setKeyword(XiuSearchStringUtils.getValidLengthTerm(params.getKw(), MAX_SEARCH_LENGTH));
		}else{
			// 如果搜索关键字是数字
			if(XiuSearchStringUtils.isIntegerNumber(params.getTags())){
				fatParams.setSearchTagsId(Integer.valueOf(params.getTags()));
			}else if(StringUtils.isNotBlank(params.getTags())){// 如果搜索关键字不为空
				fatParams.setSearchTags(params.getTags());
			}
		}
		// 验证运营分类
		if(null != params.getCat() && params.getCat().intValue()>0){
			fatParams.setCatalogId(params.getCat());
		}
		// 验证品牌ID 新品牌组装规则，前台传入String 这里转换为INT		William.zhang 20130506
        if(params.getBid() != null && params.getBid().length() > 0){
        	if(params.getBid().indexOf('|') >= 0){
        		String[] bids = StringUtils.split(params.getBid(), "|");
        		for(String str : bids){
        			if(StringUtils.isNumeric(str) && params.getBid().length() > 0){
        				brandIds.add(Long.parseLong(str));
        			}
        		}
        		fatParams.setBrandIds(brandIds);
        	}else{
        		if(StringUtils.isNumeric(params.getBid()) && params.getBid().length() > 0){
        			brandIds.add(Long.parseLong(params.getBid()));
        		}
        		fatParams.setBrandIds(brandIds);
        	}
        }
		
		// 验证排序
		SearchSortOrderEnum sort = null;
		if(null != params.getSort()){
			sort = SearchSortOrderEnum.valueOfSortOrder(params.getSort());
		}
		if(null == sort 
				|| SearchSortOrderEnum.VOLUME_ASC.equals(sort) 
				|| SearchSortOrderEnum.VOLUME_DESC.equals(sort)
				|| SearchSortOrderEnum.AMOUNT_ASC.equals(sort)){
			sort = SearchSortOrderEnum.AMOUNT_DESC;
		}
		fatParams.setSort(sort);
		// 是ebay商品
		fatParams.setMktType(MktTypeEnum.EBAY);
		// 验证是否有库存
		if(null != params.getBa()){
			fatParams.setBuyableFlag(1 == params.getBa().intValue());
		}
		// 验证翻页
		if(null != params.getP() && params.getP()>0){
			fatParams.getPage().setPageNo(params.getP());
		}
		// 验证价格区间
		if(null != params.getS_price() || null != params.getE_price()){
			if(null != params.getE_price()){
				fatParams.setEndPrice(Double.valueOf(params.getE_price()));
			}
			if(null != params.getS_price()){
				fatParams.setStartPrice(Double.valueOf(params.getS_price()));
			}
		}else if(null != params.getF_price()){
			// 如果没有用户自定义价格，则进行价格过滤
			// 验证价格过滤
			fatParams.setPriceRangeEnum(FacetPriceRangeQueryEnum.valueOf(params.getF_price().intValue()));
		}
		//是否第一次请求
		fatParams.setFirstRequest(null == params.getCat()
				&& null == params.getBid()
				&& null == params.getE_price()
				&& null == params.getF_price()
				&& null == params.getFilter()
				&& null == params.getP()
				&& null == params.getS_price()
				&& null == params.getSort()
				&& null == params.getBa());
		if(null != params.getFilter()){
			// TODO 此处限制用户恶性输入过滤条件个数
			String[] filters = StringUtils.split(params.getFilter(), ";",MAX_FILTER_SIZE*2);
			
			
			//属性查询新的业务逻辑，支持多选	William.zhang	20130507
			List<List<String>> attrValIdList = new ArrayList<List<String>>(filters.length);
            for(String vals : filters){
            	String[] items = StringUtils.split(vals,"|");
            	List<String> list = new ArrayList<String>(vals.length());
            	for(String item : items){
            		String fStrTrim = item.trim();
            		if(XiuSearchStringUtils.isIntegerNumber(fStrTrim) 
                            && !list.contains(fStrTrim)){
            			list.add(fStrTrim);
                    }
            	}
            	attrValIdList.add(list);
            }
            if(attrValIdList.size()>0){
            	fatParams.setAttrValIdList(attrValIdList);
            }
            
            //新的业务逻辑，在页面增加itemShowType用户输入项	William.zhang	20130509
    	    if(null != params.getChannel()){
    	    	if( StringUtils.isNotBlank(params.getChannel()) && StringUtils.isNumeric( params.getChannel())){
    	    		if(fatParams.getItemShowType() != null){
    		    		fatParams.getItemShowType().add(Integer.parseInt(params.getChannel()));
    		    	}else{
    		    		 List<Integer> itemShowType = new ArrayList<Integer>(1);
    		    		 itemShowType.add(Integer.parseInt(params.getChannel()));
    		    		 fatParams.setItemShowType(itemShowType);
    		    	}
    	    		
    	    	}
    	    }
		}
		return fatParams;
	}
	
	/**
	 * 搜索-官网
	 * @param params
	 * @return
	 */
	private SearchFatParams transformSearchParams(SearchParams params){
		SearchFatParams fatParams = new SearchFatParams(params.getS());
		List<Long> brandIds = new ArrayList<Long>();
		// 验证关键字
		if(StringUtils.isNotBlank(params.getKw())){
			fatParams.setKeyword(XiuSearchStringUtils.getValidLengthTerm(params.getKw(), MAX_SEARCH_LENGTH));
		}
		// 验证运营分类
		if(null != params.getCat() && params.getCat().intValue()>0){
			fatParams.setCatalogId(params.getCat());
		}
        
		// 验证品牌ID 新品牌组装规则，前台传入String 这里转换为INT		William.zhang 20130506
        if(params.getBid() != null && params.getBid().length() > 0){
        	if(params.getBid().indexOf('|') >= 0){
        		String[] bids = StringUtils.split(params.getBid(), "|");
        		for(String str : bids){
        			if(StringUtils.isNumeric(str) && params.getBid().length() > 0){
        				brandIds.add(Long.parseLong(str));
        			}
        		}
        		fatParams.setBrandIds(brandIds);
        	}else{
        		if(StringUtils.isNumeric(params.getBid()) && params.getBid().length() > 0){
        			brandIds.add(Long.parseLong(params.getBid()));
        		}
        		fatParams.setBrandIds(brandIds);
        	}
        }
		
		// 验证排序
		SearchSortOrderEnum sort = null;
		if(null != params.getSort()){
			sort = SearchSortOrderEnum.valueOfSortOrder(params.getSort());
		}
		if(null == sort 
				|| SearchSortOrderEnum.VOLUME_ASC.equals(sort) 
				|| SearchSortOrderEnum.VOLUME_DESC.equals(sort)
				|| SearchSortOrderEnum.AMOUNT_ASC.equals(sort)
				|| SearchSortOrderEnum.AMOUNT_DESC.equals(sort)){
			sort = SearchSortOrderEnum.SCORE_AMOUNT_DESC;
		}
		fatParams.setSort(sort);
		// 验证是否有库存
		if(null != params.getBa()){
			fatParams.setBuyableFlag(1 == params.getBa().intValue());
		}
		// 验证翻页
		if(null != params.getP() && params.getP()>0){
			fatParams.getPage().setPageNo(params.getP());
		}
		// 验证价格区间
		if(null != params.getS_price() || null != params.getE_price()){
			if(null != params.getE_price()){
				fatParams.setEndPrice(Double.valueOf(params.getE_price()));
			}
			if(null != params.getS_price()){
				fatParams.setStartPrice(Double.valueOf(params.getS_price()));
			}
		}else if(null != params.getF_price()){
			// 如果没有用户自定义价格，则进行价格过滤
			// 验证价格过滤
			fatParams.setPriceRangeEnum(FacetPriceRangeQueryEnum.valueOf(params.getF_price().intValue()));
		}
		//是否第一次请求
		fatParams.setFirstRequest(null == params.getCat()
				&& null == params.getBid()
				&& null == params.getE_price()
				&& null == params.getF_price()
				&& null == params.getFilter()
				&& null == params.getP()
				&& null == params.getS_price()
				&& null == params.getSort()
				&& null == params.getBa());
		fatParams.setAutoMktSwitchFlag(true);
//		fatParams.setMktType(MktTypeEnum.XIU);
		if(null != params.getFilter()){
			// TODO 此处限制用户恶性输入过滤条件个数
			String[] filters = StringUtils.split(params.getFilter(), ";",MAX_FILTER_SIZE*2);
			
			//属性查询新的业务逻辑，支持多选	William.zhang	20130507
			List<List<String>> attrValIdList = new ArrayList<List<String>>(filters.length);
            for(String vals : filters){
            	String[] items = StringUtils.split(vals,"|");
            	List<String> list = new ArrayList<String>(vals.length());
            	for(String item : items){
            		String fStrTrim = item.trim();
            		if(XiuSearchStringUtils.isIntegerNumber(fStrTrim) 
                            && !list.contains(fStrTrim)){
            			list.add(fStrTrim);
                    }
            	}
            	attrValIdList.add(list);
            }
            if(attrValIdList.size()>0){
            	fatParams.setAttrValIdList(attrValIdList);
            }
		}
		
        // 判断页面传入的商品类别，拆分为List，验证是否为数字
        if ( StringUtils.isNotEmpty(params.getItemshowtype()) ) {
            String[] showTypes = params.getItemshowtype().split("_");
            if (null != showTypes && showTypes.length > 0) {
                List<Integer> itemShowType = new ArrayList<Integer>(showTypes.length);
                for (String s : showTypes) {
                    try {
                        itemShowType.add(Integer.valueOf(s));
                    } catch (java.lang.NumberFormatException e) { }
                }
                if (itemShowType.size() > 0) {
                    fatParams.setItemShowType(itemShowType);
                }
            }
        }
        
        //新的业务逻辑，在页面增加itemShowType用户输入项	William.zhang	20130509
	    if(null != params.getChannel()){
	    	if( StringUtils.isNotBlank(params.getChannel()) && StringUtils.isNumeric( params.getChannel())){
	    		if(fatParams.getItemShowType() != null){
		    		fatParams.getItemShowType().add(Integer.parseInt(params.getChannel()));
		    	}else{
		    		 List<Integer> itemShowType = new ArrayList<Integer>(1);
		    		 itemShowType.add(Integer.parseInt(params.getChannel()));
		    		 fatParams.setItemShowType(itemShowType);
		    	}
	    		
	    	}
	    }
		
		return fatParams;
	}
	
	/**
	 * 品牌-官网
	 * @param params
	 * @return
	 */
	private BrandFatParams transformBrandParams(BrandParams params){
		BrandFatParams fatParams = new BrandFatParams(params.getS());
		// 验证关键字
		if(StringUtils.isNotBlank(params.getKw())){
			int len = params.getKw().length();
			int clen = 0;
			StringBuffer sb = new StringBuffer(len > MAX_SEARCH_LENGTH ? MAX_SEARCH_LENGTH : len);
			char kwChar = '0';
			for (int i = 0; i < len; i++) {
				kwChar = params.getKw().charAt(i);
				if(XiuSearchStringUtils.isCJK(kwChar)){
					clen+=2;
				}else{
					clen++;
				}
				if(clen <= MAX_SEARCH_LENGTH){
					sb.append(kwChar);
				}else{
					break;
				}
			}
			fatParams.setKeyword(sb.toString());
		}
		// 验证运营分类
		if(null != params.getCat() && params.getCat().intValue()>0){
			fatParams.setCatalogId(params.getCat());
		}
		// 验证品牌ID
		if(null != params.getBid() &&  StringUtils.isNotBlank(params.getBid())){
			fatParams.setBrandId(Integer.valueOf(params.getBid()));
		}
		
		// 验证排序
		SearchSortOrderEnum sort = null;
		if(null != params.getSort()){
			sort = SearchSortOrderEnum.valueOfSortOrder(params.getSort());
		}
        if(null == sort 
                || SearchSortOrderEnum.VOLUME_ASC.equals(sort) 
                || SearchSortOrderEnum.VOLUME_DESC.equals(sort)
                || SearchSortOrderEnum.SCORE_AMOUNT_DESC.equals(sort) ){
            sort = SearchSortOrderEnum.AMOUNT_DESC;
        }
		fatParams.setSort(sort);
		//店中店编码
		if(StringUtils.isNotBlank(params.getP_code())
				&& !params.getP_code().equals(XiuSearchConfig.getPropertieValue(XiuSearchConfig.EBAY_PROVIDER_CODE)))
			fatParams.setProviderCode(params.getP_code());
		
		// 验证是否有库存
		if(null != params.getBa()){
			fatParams.setBuyableFlag(1 == params.getBa().intValue());
		}
		// 验证翻页
		if(null != params.getP() && params.getP()>0){
			fatParams.getPage().setPageNo(params.getP());
		}
		// 验证价格区间
		if(null != params.getS_price() || null != params.getE_price()){
			if(null != params.getE_price()){
				fatParams.setEndPrice(Double.valueOf(params.getE_price()));
			}
			if(null != params.getS_price()){
				fatParams.setStartPrice(Double.valueOf(params.getS_price()));
			}
		}else if(null != params.getF_price()){
			// 如果没有用户自定义价格，则进行价格过滤
			// 验证价格过滤
			fatParams.setPriceRangeEnum(FacetPriceRangeQueryEnum.valueOf(params.getF_price().intValue()));
		}
		//是否第一次请求
		fatParams.setFirstRequest(null == params.getCat()
				&& null == params.getE_price()
				&& null == params.getF_price()
				&& null == params.getFilter()
				&& (null == params.getP() || params.getP().intValue()==1)
				&& null == params.getS_price()
				&& null == params.getSort()
				&& null == params.getBa());
		
		// 是否根据商品数量自动切换mkt tab
		fatParams.setAutoMktSwitchFlag(true);
		if(null != params.getFilter()){
			// TODO 此处限制用户恶性输入过滤条件个数
			String[] filters = StringUtils.split(params.getFilter(), ";",MAX_FILTER_SIZE*2);
			
			//属性查询新的业务逻辑，支持多选	William.zhang	20130507
			List<List<String>> attrValIdList = new ArrayList<List<String>>(filters.length);
            for(String vals : filters){
            	String[] items = StringUtils.split(vals,"|");
            	List<String> list = new ArrayList<String>(vals.length());
            	for(String item : items){
            		String fStrTrim = item.trim();
            		if(XiuSearchStringUtils.isIntegerNumber(fStrTrim) 
                            && !list.contains(fStrTrim)){
            			list.add(fStrTrim);
                    }
            	}
            	attrValIdList.add(list);
            }
            if(attrValIdList.size()>0){
            	fatParams.setAttrValIdList(attrValIdList);
            }
		}
		
        // 判断页面传入的商品类别，拆分为List，验证是否为数字
        if ( StringUtils.isNotEmpty(params.getItemshowtype()) ) {
            String[] showTypes = params.getItemshowtype().split("_");
            if (null != showTypes && showTypes.length > 0) {
                List<Integer> itemShowType = new ArrayList<Integer>(showTypes.length);
                for (String s : showTypes) {
                    try {
                        itemShowType.add(Integer.valueOf(s));
                    } catch (java.lang.NumberFormatException e) { }
                }
                if (itemShowType.size() > 0) {
                    fatParams.setItemShowType(itemShowType);
                }
            }
        }
        
        //新的业务逻辑，在页面增加itemShowType用户输入项	William.zhang	20130509
  		if(null != params.getChannel()){
  			String[] channel = params.getChannel().split("|");
  			for(String str : channel){
  				
  				if(StringUtils.isNotBlank(str) && StringUtils.isNumeric(str)){
  		    		if(fatParams.getItemShowType() != null){
  			    		fatParams.getItemShowType().add(Integer.parseInt(str));
  			    	}else{
  			    		 List<Integer> itemShowType = new ArrayList<Integer>(1);
  			    		 itemShowType.add(Integer.parseInt(str));
  			    		 fatParams.setItemShowType(itemShowType);
  			    	}
  				}
  			}	    	
  		}
        
		return fatParams;
	}
	
	/**
	 * 品牌-Ebay
	 * @param params
	 * @return
	 */
	private BrandFatParams transformBrandEbayParams(BrandParams params){
		BrandFatParams fatParams = new BrandFatParams(params.getS());
		// 验证运营分类
		if(null != params.getCat() && params.getCat().intValue()>0){
			fatParams.setCatalogId(params.getCat());
		}
		// 验证品牌ID
		if(null != params.getBid() &&  StringUtils.isNotBlank(params.getBid())){
			fatParams.setBrandId(Integer.valueOf(params.getBid()));
		}
        
		// 验证排序
		SearchSortOrderEnum sort = null;
		if(null != params.getSort()){
			sort = SearchSortOrderEnum.valueOfSortOrder(params.getSort());
		}
        if(null == sort 
                || SearchSortOrderEnum.VOLUME_ASC.equals(sort) 
                || SearchSortOrderEnum.VOLUME_DESC.equals(sort)
                || SearchSortOrderEnum.AMOUNT_ASC.equals(sort)
                || SearchSortOrderEnum.SCORE_AMOUNT_DESC.equals(sort) ){
            sort = SearchSortOrderEnum.AMOUNT_DESC;
        }
		fatParams.setSort(sort);
		fatParams.setMktType(MktTypeEnum.EBAY);
		// 验证是否有库存
		if(null != params.getBa()){
			fatParams.setBuyableFlag(1 == params.getBa().intValue());
		}
		// 验证翻页
		if(null != params.getP() && params.getP()>0){
			fatParams.getPage().setPageNo(params.getP());
		}
		// 验证价格区间
		if(null != params.getS_price() || null != params.getE_price()){
			if(null != params.getE_price()){
				fatParams.setEndPrice(Double.valueOf(params.getE_price()));
			}
			if(null != params.getS_price()){
				fatParams.setStartPrice(Double.valueOf(params.getS_price()));
			}
		}else if(null != params.getF_price()){
			// 如果没有用户自定义价格，则进行价格过滤
			// 验证价格过滤
			fatParams.setPriceRangeEnum(FacetPriceRangeQueryEnum.valueOf(params.getF_price().intValue()));
		}
		//是否第一次请求
		fatParams.setFirstRequest(null == params.getCat()
				&& null == params.getBid()
				&& null == params.getE_price()
				&& null == params.getF_price()
				&& null == params.getFilter()
				&& (null == params.getP() || params.getP().intValue()==1)
				&& null == params.getS_price()
				&& null == params.getSort()
				&& null == params.getBa());
		if(null != params.getFilter()){
			// TODO 此处限制用户恶性输入过滤条件个数
			String[] filters = StringUtils.split(params.getFilter(), ";",MAX_FILTER_SIZE*2);
			
			//属性查询新的业务逻辑，支持多选	William.zhang	20130507
			List<List<String>> attrValIdList = new ArrayList<List<String>>(filters.length);
            for(String vals : filters){
            	String[] items = StringUtils.split(vals,"|");
            	List<String> list = new ArrayList<String>(vals.length());
            	for(String item : items){
            		String fStrTrim = item.trim();
            		if(XiuSearchStringUtils.isIntegerNumber(fStrTrim) 
                            && !list.contains(fStrTrim)){
            			list.add(fStrTrim);
                    }
            	}
            	attrValIdList.add(list);
            }
            if(attrValIdList.size()>0){
            	fatParams.setAttrValIdList(attrValIdList);
            }
		}
		 //新的业务逻辑，在页面增加itemShowType用户输入项	William.zhang	20130509
		if(null != params.getChannel()){
  			String[] channel = params.getChannel().split("|");
  			for(String str : channel){
  				
  				if(StringUtils.isNotBlank(str) && StringUtils.isNumeric(str)){
  		    		if(fatParams.getItemShowType() != null){
  			    		fatParams.getItemShowType().add(Integer.parseInt(str));
  			    	}else{
  			    		 List<Integer> itemShowType = new ArrayList<Integer>(1);
  			    		 itemShowType.add(Integer.parseInt(str));
  			    		 fatParams.setItemShowType(itemShowType);
  			    	}
  				}
  			}	    	
  		}
		return fatParams;
	}
	
}
