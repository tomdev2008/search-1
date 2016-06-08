package com.xiu.search.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xiu.search.core.bo.ListBo;
import com.xiu.search.core.bof.ListBof;
import com.xiu.search.core.enumeration.RequestInletEnum;
import com.xiu.search.core.solr.enumeration.FacetPriceRangeQueryEnum;
import com.xiu.search.core.solr.enumeration.MktTypeEnum;
import com.xiu.search.core.solr.enumeration.SearchSortOrderEnum;
import com.xiu.search.core.solr.params.ListFatParams;
import com.xiu.search.core.util.XiuSearchStringUtils;
import com.xiu.search.web.util.CookieUtils;
import com.xiu.search.web.util.CookieUtils.CookieExpireEnum;
import com.xiu.search.web.vo.ListParams;

/**
 * ebay列表页面
 * @author Leon
 *
 */
@Controller
public class ListForEbayController extends BaseController {

	private Logger LOGGER = Logger.getLogger(getClass());

    /**
     * 每页展示商品数
     */
    private static final int PAGE_SIZE = 32;

    /**
     * 分组过滤的最多查询个数<br>
     * 禁止用户恶性的无限制输入filter
     */
    private static final int MAX_FILTER_SIZE = 10;
    

    /**
     * 业务处理类
     */
    @Autowired
	private ListBof listBof ;
    
    
	
    /**
     * action主处理方法
     * @param params URL参数封装
     * @param bindingResult
     * @param req
     * @param res
     * @param model 传递到view层的参数
     * @return
     * @throws InterruptedException
     */
	@SuppressWarnings({ "unchecked" })
    @RequestMapping(value="/ebay-list-action")
	public String execute(ListParams params, BindingResult bindingResult, HttpServletRequest req,
	        HttpServletResponse res ,Model model) throws InterruptedException{
	    
	    long starttime = System.currentTimeMillis();
	    
		if(null == params || null == params.getCat() || params.getCat().intValue() <= 0){
		    return "forward:/error/404.htm";
		}
		
		ListBo listBo = null;
        ListFatParams fatParams = this.transformParams(params);
        // 若解析的参数为空，到无结果页面
 		if(null == fatParams){
 			return "error/ebay-list-noresult";
 		}
 		fatParams.setRequestInletEnum(RequestInletEnum.EBAY_LIST_PAGE);
        // 调用业务处理，获取bo用于页面展示
        listBo = listBof.findListEbayPageResult(fatParams);
        if(null == listBo){
        	res.setStatus(404);
            return "error/ebay-list-nocatalog";
        }
        // 设置推荐参数
        if(params != null && params.getBid() != null){
        	 listBo.setRecommenBrandIds(params.getBid().replace("|",","));
        }
        model.addAllAttributes(new BeanMap(listBo));
        model.addAttribute("params",params);
        //点击流插码source_id
      	model.addAttribute("s_id",req.getParameter("s_id"));

        if(null == listBo.getPage() || 0 == listBo.getPage().getRecordCount() || listBo.getPage().getPageNo() > listBo.getPage().getRecordCount() ){
            return "error/ebay-list-noresult";
        }
        
        model.addAttribute("seoInfo", this.buildSEOInfo(listBo));
        
        long endtime = System.currentTimeMillis();
        
        LOGGER.warn("根据运营分类查询商品列表：catid : " + fatParams.getCatalogId() + ", time : " + (endtime - starttime) + " ms.");
        
        this.buildClickCookieValue(res, 
        		Integer.toString(listBo.getFatParams().getCatalogId()), 
        		listBo.getPage().getRecordCount(), 
        		listBo.getPage().getPageNo(), 
        		listBo.getFatParams().getMktType()==null?0 : listBo.getFatParams().getMktType().getType());
        
      //过滤掉促销的两个ID 
        SearchController.filterCatalogList(listBo);
        
		return "ebay-list";
	}
	
	/**
	 * 构建SEO title，description & keywords
	 * @param listBo
	 * @return
	 */
	private HashMap<String,String> buildSEOInfo(ListBo listBo){
		HashMap<String,String> seoMap = new HashMap<String,String>(3);
		StringBuilder sb = new StringBuilder();
		// title 
		if(listBo.getSelectedCatalog()!=null)
			sb.append(listBo.getSelectedCatalog().getCatalogName());
		sb.append('_');
		if(listBo.getSelectedCatalogTree()!=null)
			sb.append(listBo.getSelectedCatalogTree().getCatalogName());
		sb.append('_');
		if(listBo.getSelectedCatalog()!=null)
			sb.append(listBo.getSelectedCatalog().getCatalogName());
		sb.append("品牌_网购");
		if(listBo.getSelectedCatalogTree()!=null)
			sb.append(listBo.getSelectedCatalogTree().getCatalogName());
		sb.append("选走秀网");
		seoMap.put("title", sb.toString());
		
		sb = new StringBuilder();
		// description
		sb.append("网购");
		if(listBo.getSelectedCatalog()!=null)
			sb.append(listBo.getSelectedCatalog().getCatalogName());
		sb.append(",正品");
		if(listBo.getSelectedCatalog()!=null)
			sb.append(listBo.getSelectedCatalog().getCatalogName());
		sb.append(",就上走秀网,热销");
		if(listBo.getSelectCatalogPlaneList()!=null 
				&& listBo.getSelectCatalogPlaneList().size()>1)
			sb.append(listBo.getSelectCatalogPlaneList().get(1).getCatalogName());
		else if(listBo.getSelectedCatalogTree()!=null)
			sb.append(listBo.getSelectedCatalogTree().getCatalogName());
		sb.append("品牌,正品保证,名品");
		if(listBo.getSelectedCatalogTree()!=null)
			sb.append(listBo.getSelectedCatalogTree().getCatalogName());
		sb.append("特卖,支持货到付款。");
		seoMap.put("description", sb.toString());
		
		sb = new StringBuilder();
		sb.append("网购");
		// keywords
		if(listBo.getSelectedCatalog()!=null)
			sb.append(listBo.getSelectedCatalog().getCatalogName());
		sb.append(",网购");
		if(listBo.getSelectCatalogPlaneList()!=null 
				&& listBo.getSelectCatalogPlaneList().size()>1)
			sb.append(listBo.getSelectCatalogPlaneList().get(1).getCatalogName());
		else if(listBo.getSelectedCatalogTree()!=null)
			sb.append(listBo.getSelectedCatalogTree().getCatalogName());
		sb.append(",");
		if(listBo.getSelectedCatalogTree()!=null)
			sb.append(listBo.getSelectedCatalogTree().getCatalogName());
		sb.append("品牌,走秀网");
		if(listBo.getSelectedCatalog()!=null)
			sb.append(listBo.getSelectedCatalog().getCatalogName());
		seoMap.put("keywords", sb.toString());
		return seoMap;
	}
	
	
	/**
	 * 验证页面传入的参数并再次封装
	 * @param params
	 * @return
	 */
	private ListFatParams transformParams(ListParams params){
	    
	    ListFatParams fatParams = new ListFatParams(PAGE_SIZE);
	    List<Long> brandIds = new ArrayList<Long>();
//	    //查询纠错
//	  	fatParams.setCorrect(true);
//	  	//来源
//	  	fatParams.setFromTypeEnum(FromTypeEnum.EBAY);

        // 运营分类
        fatParams.setCatalogId(params.getCat());
        
        
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
                || SearchSortOrderEnum.SCORE_AMOUNT_DESC.equals(sort) ){
            sort = SearchSortOrderEnum.AMOUNT_DESC;
        }
        fatParams.setSort(sort);
        
        // 验证是否有库存
        if(null != params.getBa()){
            fatParams.setBuyableFlag(1 == params.getBa().intValue());
        }
        
        //店中店编码
  		//if(StringUtils.isNotBlank(params.getP_code())
  		//		&& !params.getP_code().equals(XiuSearchConfig.getPropertieValue(XiuSearchConfig.EBAY_PROVIDER_CODE)))
  		//	fatParams.setProviderCode(params.getP_code());
        
		//
  		fatParams.setMktType(MktTypeEnum.EBAY);
  		
        // 验证翻页
        if(null != params.getP() && params.getP()>0){
            fatParams.getPage().setPageNo(params.getP());
        }
        
        // 验证价格区间
 		if(null != params.getS_price() || null != params.getE_price()){
// 			if(null == params.getS_price()){
// 				 if(params.getE_price() < 0)
// 					 return null;
// 				fatParams.setEndPrice(Double.valueOf(params.getE_price()));
// 			}else if(null == params.getE_price()){
// 				if(params.getS_price() < 0)
// 					return null;
// 				fatParams.setStartPrice(Double.valueOf(params.getS_price()));
// 			}else if(params.getS_price()> params.getE_price() 
// 					|| params.getS_price() < 0 
// 					|| params.getE_price() < 0){
// 				// 如果价格不符合条件，直接跳转到查询无结果页面。
// 				return null;
// 			}else{
// 				fatParams.setStartPrice(Double.valueOf(params.getS_price()));
// 				fatParams.setEndPrice(Double.valueOf(params.getE_price()));
// 			}
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
            
            
//            List<String> validFilterStrList = new ArrayList<String>(filters.length);
//            String fStrTrim;
//            for (int i = 0,len=filters.length,vi=0; i<len && vi<MAX_FILTER_SIZE; i++) {
//                fStrTrim = filters[i].trim();
//                if(XiuSearchStringUtils.isIntegerNumber(fStrTrim) 
//                        && !validFilterStrList.contains(fStrTrim)){
//                    validFilterStrList.add(fStrTrim);
//                    vi++;
//                }
//            }
//            if(validFilterStrList.size()>0)
//                fatParams.setAttrValIds(validFilterStrList.toArray(new String[0]));
            
            
        }
//        //新的业务逻辑，在页面增加itemShowType用户输入项	William.zhang	20130509
//	    if(null != params.getChannel()){
//	    	if( StringUtils.isNotBlank(params.getChannel()) && StringUtils.isNumeric( params.getChannel())){
//	    		if(fatParams.getItemShowType() != null){
//		    		fatParams.getItemShowType().add(Integer.parseInt(params.getChannel()));
//		    	}else{
//		    		 List<Integer> itemShowType = new ArrayList<Integer>(1);
//		    		 itemShowType.add(Integer.parseInt(params.getChannel()));
//		    		 fatParams.setItemShowType(itemShowType);
//		    	}
//	    		
//	    	}
//	    }
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
    private void buildClickCookieValue(HttpServletResponse response,String catId,int count,int page,int mkt){
    	if(catId == null || response == null)
    		return;
        StringBuffer sb = new StringBuffer();
        sb.append("cat=").append(catId).append("|count=").append(count).append("|page=").append(page).append("|mkt=").append(mkt);
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
	
//	
//	
//    public ListBof getListBof() {
//        return listBof;
//    }
//
//    public void setListBof(ListBof listBof) {
//        this.listBof = listBof;
//    }
	 
}
