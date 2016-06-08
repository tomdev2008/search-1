package com.xiu.search.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xiu.search.core.bo.CatalogBo;
import com.xiu.search.core.bo.SearchBo;
import com.xiu.search.core.bof.SearchBof;
import com.xiu.search.core.enumeration.RequestInletEnum;
import com.xiu.search.core.service.DeliverTypeService;
import com.xiu.search.core.solr.enumeration.FacetPriceRangeQueryEnum;
import com.xiu.search.core.solr.enumeration.ItemShowTypeEnum;
import com.xiu.search.core.solr.enumeration.SearchSortOrderEnum;
import com.xiu.search.core.solr.params.SearchFatParams;
import com.xiu.search.core.util.XiuSearchStringUtils;
import com.xiu.search.dao.model.XPurchBaseBusinessModel;
import com.xiu.search.web.util.CookieUtils;
import com.xiu.search.web.util.CookieUtils.CookieExpireEnum;
import com.xiu.search.web.vo.SearchParams;

/**
 * 搜索页面Controller
 * @author Leon
 *
 */
@Controller
public class SearchController extends BaseController {
	
	private Logger LOGGER = Logger.getLogger(getClass());
	
	/**
	 * 搜索最多字符数，1个汉字=2个字符<br>
	 * 禁止用户恶性的无限制输入参数
	 */
	public static final int MAX_SEARCH_LENGTH = 64;
	/**
	 * 每页展示商品数
	 */
	private static final int PAGE_SIZE = 32;
	/**
	 * 分组过滤的最多查询个数<br>
	 * 禁止用户恶性的无限制输入filter
	 */
	public static final int MAX_FILTER_SIZE = 10;
	
	/**
	 * 查询无结果的返回页面
	 */
	private static final String RESULT_LESS_PAGE = "error/search-noresult";
	
	@Autowired
	private SearchBof searchBof;
	
	@Autowired
	private DeliverTypeService deliverTypeService;

	@RequestMapping(value="/search-action")
	public String execute(SearchParams params,BindingResult bindingResult,HttpServletRequest req,HttpServletResponse res,Model model) throws InterruptedException{
		//原始输入参数
		model.addAttribute("params",params);
		// 若参数为空或关键字为空，直接返回到首页
		if(null == params || null == params.getKw()){
			return "forward:/error/404.htm";
		}
		SearchBo xiuSearchBo = null;
		// 若不符合条件，到错误页面
		if(StringUtils.isBlank(params.getKw())){
			return RESULT_LESS_PAGE;
		}
		SearchFatParams solrParams = this.transformParams(params);
		// 若解析的参数为空，到无结果页面
		if(null == solrParams){
			return RESULT_LESS_PAGE;
		}
		solrParams.setRequestInletEnum(RequestInletEnum.SEARCH_PAGE);
		xiuSearchBo = searchBof.findSearchXiuPageResult(solrParams);
		//返回参数
		model.addAllAttributes(new BeanMap(xiuSearchBo));
		
		if(null == xiuSearchBo || null == xiuSearchBo.getGoodsItemList() || xiuSearchBo.getGoodsItemList().size()==0)
			return RESULT_LESS_PAGE;
		
		
		// 设置推荐参数
        if(params != null && params.getBid() != null){
        	xiuSearchBo.setRecommenBrandIds(params.getBid().replace("|",","));
        }
        //如果当前页数大于最大页数，则返回无结果页面		William.zhang	20130704
        if(xiuSearchBo.getPage().getPageNo() > xiuSearchBo.getPage().getPageCount()){
        	return RESULT_LESS_PAGE;
        }
        
		//点击流插码source_id
		model.addAttribute("s_id",req.getParameter("s_id"));
		//插码
		this.buildClickCookieValue(res, 
        		xiuSearchBo.getFatParams().getKeyword(), 
        		xiuSearchBo.getPage().getRecordCount(), 
        		xiuSearchBo.getPage().getPageNo(), 
        		xiuSearchBo.getFatParams().getMktType()==null?0 : xiuSearchBo.getFatParams().getMktType().getType());
		//过滤掉促销的两个ID 
		SearchController.filterCatalogList(xiuSearchBo);
		return "search";
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
	 * 对输入参数进行校验，并赋予初始值<br>
	 * 关键字：不允许为空，最多不超过maxSearchLength个字符<br>
	 * @param params
	 * @param params
	 * @param solrParams
	 * @return
	 */
	private SearchFatParams transformParams(SearchParams params){
		SearchFatParams fatParams = new SearchFatParams(PAGE_SIZE);
		List<Long> brandIds = new ArrayList<Long>();
		// 验证关键字
		if(StringUtils.isNotBlank(params.getKw())){
			fatParams.setKeyword(XiuSearchStringUtils.getValidLengthTerm(params.getKw(), MAX_SEARCH_LENGTH));
		}
		// 验证运营分类
		if(null != params.getCat() && params.getCat().intValue()>0){
			fatParams.setCatalogId(params.getCat());
		}
		// 验证品牌ID
//		if(null != params.getBid() &&  params.getBid().intValue()>0){
//			fatParams.setBrandId(params.getBid());
//		}
		
        
		// 验证品牌ID 新品牌组装规则，前台传入String 这里转换为INT		William.zhang 20130506
        if(params.getBid() != null && params.getBid().length() > 0){
        	if(params.getBid().indexOf('|') >= 0){
        		String[] bids = StringUtils.split(params.getBid(), "|");
        		for(String str : bids){
        			if(StringUtils.isNumeric(str) && params.getBid().length() > 0){
        				brandIds.add(Long.valueOf(str));
        			}
        		}
        		fatParams.setBrandIds(brandIds);
        	}else{
        		if(StringUtils.isNumeric(params.getBid()) && params.getBid().length() > 0){
        			brandIds.add(Long.valueOf(params.getBid()));
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
		// TAB 是ebay商品还是xiu商品
//		MktTypeEnum mktType = MktTypeEnum.ALL;
//		if(null != params.getMkt()){
//			mktType = MktTypeEnum.valueof(params.getMkt());
//		}
//		fatParams.setMktType(MktTypeEnum.ALL);
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
//			if(null == params.getS_price()){
//				 if(params.getE_price() < 0)
//					 return null;
//				fatParams.setEndPrice(Double.valueOf(params.getE_price()));
//			}else if(null == params.getE_price()){
//				if(params.getS_price() < 0)
//					return null;
//				fatParams.setStartPrice(Double.valueOf(params.getS_price()));
//			}else if(params.getS_price()> params.getE_price() 
//					|| params.getS_price() < 0 
//					|| params.getE_price() < 0){
//				// 如果价格不符合条件，直接跳转到查询无结果页面。
//				return null;
//			}else{
//				fatParams.setStartPrice(Double.valueOf(params.getS_price()));
//				fatParams.setEndPrice(Double.valueOf(params.getE_price()));
//			}
			try{
        		if(params.getS_price() < 0){
        			params.setS_price(0f);
        		}
        		fatParams.setStartPrice(Double.valueOf(params.getS_price()));
        	}catch(Exception e){
        	}
        	try{
        		if(params.getE_price()<0){
        			params.setE_price(0f);
        		}
        		fatParams.setEndPrice(Double.valueOf(params.getE_price()));
        	}catch(Exception e){
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
		// 是否根据商品数量自动切换mkt tab
//		fatParams.setAutoMktSwitchFlag(null == params.getBid()
//				&& null == params.getE_price()
//				&& null == params.getF_price()
//				&& null == params.getFilter()
//				&& null == params.getP()
//				&& null == params.getS_price()
//				&& null == params.getSort()
//				&& null == params.getBa() //--------
//				&& null == params.getMkt()
//				&& null != params.getSrc()
//				&& (params.getSrc().indexOf("s_")==0 || "ori".equals(params.getSrc())));
		fatParams.setAutoMktSwitchFlag(null == params.getMkt());
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
			
			
//			List<String> validFilterStrList = new ArrayList<String>(filters.length);
//			String fStrTrim;
//			for (int i = 0,len=filters.length,vi=0; i<len && vi<MAX_FILTER_SIZE; i++) {
//				fStrTrim = filters[i].trim();
//				if(XiuSearchStringUtils.isIntegerNumber(fStrTrim) 
//						&& !validFilterStrList.contains(fStrTrim)){
//					validFilterStrList.add(fStrTrim);
//					vi++;
//				}
//			}
//			if(validFilterStrList.size()>0)
//				fatParams.setAttrValIds(validFilterStrList.toArray(new String[0]));
		}
		

        // 判断页面传入的商品类别，拆分为List，验证是否为数字
        if ( StringUtils.isNotEmpty(params.getItemshowtype()) ) {
            String[] showTypes = params.getItemshowtype().split("_");
            if (null != showTypes && showTypes.length > 0) {
                List<Integer> itemShowType = new ArrayList<Integer>(showTypes.length);
                for (String s : showTypes) {
                    try {
                        itemShowType.add(Integer.valueOf(s.trim()));
                    } catch (java.lang.NumberFormatException e) { }
                }
                if (itemShowType.size() > 0) {
                    fatParams.setItemShowType(itemShowType);
                    fatParams.setShowType(ItemShowTypeEnum.valueof(itemShowType.get(0)));
                }
            }
        }
        
	    //新的业务逻辑，在页面增加"发货方式Channel"用户输入项	William.zhang	20130509
	    if(null != params.getChannel()){
	    	if( StringUtils.isNotBlank(params.getChannel()) && StringUtils.isNumeric( params.getChannel())){
	    		fatParams.setChannel(Integer.parseInt( params.getChannel()));
	    	}
	    }
		
		return fatParams;
	}
	
	/**
     * 生成点击流需要的cookie<br>
     * 注意：临时测试方法，需确认是否需要flow_cookie.jar包。
     * @param kw
     * @param count
     * @param page
     * @return
     */
    @Deprecated
    private void buildClickCookieValue(HttpServletResponse response,String kw,int count,int page,int mkt){
    	if(kw == null || response == null)
    		return;
        StringBuffer sb = new StringBuffer();
        sb.append("kw=").append(kw).append("|count=").append(count).append("|page=").append(page).append("|mkt=").append(mkt);
        String cv = null;
        try {
        	cv = URLEncoder.encode(sb.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
        	cv = sb.toString();
        	LOGGER.error("点击流cookie value encode出错："+cv, e);
        }
        CookieUtils.putCookie(response, CookieUtils.TEMP_PARAM, cv,".xiu.com", CookieExpireEnum.CURRENT);
    }
    
    @RequestMapping(value="/search-clean-cache",method = RequestMethod.GET)
    public String clearCache(@RequestParam(value="flag", required=false)String flag,HttpServletResponse resp) throws Exception{
    	 resp.setContentType("text/html; charset=UTF-8");
    	resp.getWriter().write("...Before clean clearCache China ...<br>");
    	 List<XPurchBaseBusinessModel>  modelList =  deliverTypeService.getDeliverType( 2 );
    	 for ( int i = 0; i < modelList.size(); i ++  ){
    	    	resp.getWriter().write("China " + modelList.get(i).getSpaceFlag() + " ; " + modelList.get(i).getSpaceFlagName() +  "<br>");
    	 }
    	
    	resp.getWriter().write("...Before clean clearCache oversea ...<br>");
    	modelList =  deliverTypeService.getDeliverType( 3 );
	   	 for ( int i = 0; i < modelList.size(); i ++  ){
		    	resp.getWriter().write("OverSea " + modelList.get(i).getSpaceFlag() + " ; " + modelList.get(i).getSpaceFlagName() +  "<br>");
		 }
    	try{
    		deliverTypeService.reload();
    	}catch( Exception e ){
   	    	resp.getWriter().write("Exception e =  " + e.getMessage() +  "<br>");
    	}
    	
	   	 modelList =  deliverTypeService.getDeliverType( 2 );
	   	 for ( int i = 0; i < modelList.size(); i ++  ){
	   	    	resp.getWriter().write("After Reload China " + modelList.get(i).getSpaceFlag() + " ; " + modelList.get(i).getSpaceFlagName() +  "<br>");
	   	 }
	   	
	   	resp.getWriter().write("...After clean clearCache oversea ...<br>");
	   	modelList =  deliverTypeService.getDeliverType( 3 );
		   	 for ( int i = 0; i < modelList.size(); i ++  ){
			    	resp.getWriter().write("After Reload OverSea " + modelList.get(i).getSpaceFlag() + " ; " + modelList.get(i).getSpaceFlagName() +  "<br>");
			 }
    	resp.getWriter().write("...clean clearCache success...");
//    	boolean result = brandBof.cleanCacheOfBrandInfo(brandId, f);
//    	if(result){
//    		resp.getWriter().write("...clean cache success...");
//    	}else{
//    		resp.getWriter().write("...clean cache failed...");
//    	}
    	return null;
    }
	
}
