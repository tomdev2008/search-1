package com.xiu.search.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xiu.search.core.bo.SearchBo;
import com.xiu.search.core.bof.SearchBof;
import com.xiu.search.core.enumeration.RequestInletEnum;
import com.xiu.search.core.solr.enumeration.FacetPriceRangeQueryEnum;
import com.xiu.search.core.solr.enumeration.MktTypeEnum;
import com.xiu.search.core.solr.enumeration.SearchSortOrderEnum;
import com.xiu.search.core.solr.params.SearchFatParams;
import com.xiu.search.core.util.XiuSearchStringUtils;
import com.xiu.search.web.util.CookieUtils;
import com.xiu.search.web.util.CookieUtils.CookieExpireEnum;
import com.xiu.search.web.vo.SearchParams;

/**
 * 搜索页面Controller
 * @author Leon
 *
 */
@Controller
public class SearchForEbayController extends BaseController {

	
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
	 * TAGS页每页展示商品数
	 */
	private static final int TAGS_PAGE_SIZE = 30;
	/**
	 * 分组过滤的最多查询个数<br>
	 * 禁止用户恶性的无限制输入filter
	 */
	public static final int MAX_FILTER_SIZE = 10;
	
	/**
	 * 查询无结果的返回页面
	 */
	private static final String RESULT_LESS_PAGE = "error/ebay-search-noresult";
	
	private static final String EBAY_TAGS_SEARCH_PAGE = "ebay-tags-search";
	
	@Autowired
	private SearchBof searchBof;
	
	/**
	 * 关键字搜索和标签搜索在一个action中判断，标签搜索优先
	 * @param params
	 * @param bindingResult
	 * @param req
	 * @param res
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value="/ebay-search-action")
	public String execute(SearchParams params,BindingResult bindingResult,HttpServletRequest req,HttpServletResponse res,Model model) throws InterruptedException{
		//原始输入参数
		model.addAttribute("params",params);
		//点击流插码source_id
		model.addAttribute("s_id",req.getParameter("s_id"));
//		System.out.println("--------------");
		// 若参数为空或关键字为空，直接返回到首页
		if(null == params 
				|| (null == params.getKw() && null == params.getTags())){
			return "forward:/error/404.htm";
		}
		if(null != params.getKw())
			return this.exeSearch(params, req, res, model);
		return this.exeTagsSearch(params, req, res, model);
	}
	
	/**
	 * 关键字搜索方法
	 * @param params
	 * @param req
	 * @param res
	 * @param model
	 * @return
	 */
	private String exeSearch(SearchParams params,HttpServletRequest req,HttpServletResponse res,Model model){
		SearchBo xiuSearchBo = null;
		// 若不符合条件，到错误页面
		if(params == null || (StringUtils.isBlank(params.getKw()) && StringUtils.isBlank(params.getTags()))){
			return RESULT_LESS_PAGE;
		}
		SearchFatParams solrParams = this.transformParams(params);
		// 若解析的参数为空，到无结果页面
		if(null == solrParams){
			return RESULT_LESS_PAGE;
		}
		if(null != solrParams)
			solrParams.setRequestInletEnum(RequestInletEnum.EBAY_SEARCH_PAGE);
		xiuSearchBo = searchBof.findSearchEbayPageResult(solrParams);
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
        
		//返回参数
		model.addAllAttributes(new BeanMap(xiuSearchBo));
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
		return "ebay-search";
	}
	
	/**
	 * 标签搜索方法
	 * @param params
	 * @param req
	 * @param res
	 * @param model
	 * @return
	 */
	private String exeTagsSearch(SearchParams params,HttpServletRequest req,HttpServletResponse res,Model model){
		SearchBo xiuSearchBo = null;
		SearchFatParams solrParams = this.transformTagsParams(params);
		if(StringUtils.isNotBlank(params.getTags())
				&& null != solrParams){
				solrParams.setRequestInletEnum(RequestInletEnum.EBAY_TAGS_SEARCH_PAGE);
				xiuSearchBo = searchBof.findSearchTagsPageResult(solrParams);
		}
		//返回参数
		model.addAllAttributes(new BeanMap(xiuSearchBo));
		//点击流插码source_id
		model.addAttribute("s_id",req.getParameter("s_id"));
		
        //如果当前页数大于最大页数，则返回无结果页面		William.zhang	20130704
        if(xiuSearchBo != null && xiuSearchBo.getPage().getPageNo() > xiuSearchBo.getPage().getPageCount()){
        	return RESULT_LESS_PAGE;
        }
		
		//插码
        if (xiuSearchBo != null) {
        	this.buildTagsSearchClickCookieValue(res, 
        			null == xiuSearchBo.getFatParams() ? null : xiuSearchBo.getFatParams().getSearchTags(), 
        					null == xiuSearchBo.getPage() ? 0 :
        						xiuSearchBo.getPage().getRecordCount(),
        						null == xiuSearchBo.getPage() ? 0 :
        							xiuSearchBo.getPage().getPageNo(),
        							1);
		}
		return EBAY_TAGS_SEARCH_PAGE;
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
		// 验证品牌ID
//		if(null != params.getBid() &&  params.getBid().intValue()>0){
//			fatParams.setBrandId(params.getBid());
//		}
		
//		 // 验证品牌ID		由于修改了Bid的类型，所以修改了对比方式	William.zhang 20130506
//        if(StringUtils.isNumeric(params.getBid()) &&  params.getBid().length() > 0){
//        	int bid = Integer.parseInt(params.getBid());
//	        if(null != params.getBid() &&  bid>0){
//	            fatParams.setBrandId(bid);
//	        }
//        }
        
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
	
	private SearchFatParams transformTagsParams(SearchParams params){
		SearchFatParams fatParams = new SearchFatParams(TAGS_PAGE_SIZE);
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
            
//            //新的业务逻辑，在页面增加itemShowType用户输入项	William.zhang	20130509
//    	    if(null != params.getChannel()){
//    	    	if( StringUtils.isNotBlank(params.getChannel()) && StringUtils.isNumeric( params.getChannel())){
//    	    		if(fatParams.getItemShowType() != null){
//    		    		fatParams.getItemShowType().add(Integer.parseInt(params.getChannel()));
//    		    	}else{
//    		    		 List<Integer> itemShowType = new ArrayList<Integer>(1);
//    		    		 itemShowType.add(Integer.parseInt(params.getChannel()));
//    		    		 fatParams.setItemShowType(itemShowType);
//    		    	}
//    	    		
//    	    	}
//    	    }
			
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
    private void buildClickCookieValue(HttpServletResponse response,String kw,int count,int page,int mkt){
    	if(kw == null || response == null)
    		return;
        StringBuffer sb = new StringBuffer();
        sb.append("kw=").append(kw).append("|count=").append(count).append("|page=").append(page).append("|mkt=").append(mkt);
        String cv = null;
        try {
        	cv = URLEncoder.encode(sb.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            cv = sb.toString();
        }
        CookieUtils.putCookie(response, CookieUtils.TEMP_PARAM, cv,".xiu.com", CookieExpireEnum.CURRENT);
    }
    /**
     * 标签搜索的点击流cookie
     * @param response
     * @param kw
     * @param count
     * @param page
     * @param mkt
     */
    private void buildTagsSearchClickCookieValue(HttpServletResponse response,String tags,int count,int page,int mkt){
    	if(tags == null || response == null)
    		return;
        StringBuffer sb = new StringBuffer();
        sb.append("tags=").append(tags).append("|count=").append(count).append("|page=").append(page).append("|mkt=").append(mkt);
        String cv = null;
        try {
        	cv = URLEncoder.encode(sb.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            cv = sb.toString();
        }
        CookieUtils.putCookie(response, CookieUtils.TEMP_PARAM, cv,".xiu.com", CookieExpireEnum.CURRENT);
    }
}
