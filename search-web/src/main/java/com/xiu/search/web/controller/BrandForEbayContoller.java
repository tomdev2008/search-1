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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xiu.search.core.bo.BrandBo;
import com.xiu.search.core.bof.BrandBof;
import com.xiu.search.core.enumeration.RequestInletEnum;
import com.xiu.search.core.solr.enumeration.FacetPriceRangeQueryEnum;
import com.xiu.search.core.solr.enumeration.MktTypeEnum;
import com.xiu.search.core.solr.enumeration.SearchSortOrderEnum;
import com.xiu.search.core.solr.params.BrandFatParams;
import com.xiu.search.core.util.XiuSearchStringUtils;
import com.xiu.search.web.config.XiuWebConstant;
import com.xiu.search.web.util.CookieUtils;
import com.xiu.search.web.util.CookieUtils.CookieExpireEnum;
import com.xiu.search.web.vo.BrandParams;

/**
 * Ebay品牌页面
 * @author Lipsion
 *
 */
@Controller
public class BrandForEbayContoller extends BaseController {
	
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
	 * 查询无结果的返回页面
	 */
	private static final String RESULT_LESS_PAGE = "error/ebay-brand-noresult";
	
	@Autowired
	private BrandBof brandBof;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/ebay-brand-action")
	public String execute(BrandParams params,BindingResult bindingResult,HttpServletRequest req,HttpServletResponse res,Model model) throws InterruptedException{
		//原始输入参数
		model.addAttribute("params",params);
		// 若参数为空或品牌为空，直接返回到首页
		if(null == params || null == params.getBid()){
			return INDEX;
		}
		BrandBo brandBo = null;
		// 若不符合条件，到错误页面		由于修改了Bid的类型，所以修改了对比方式	William.zhang 20130506
		if(StringUtils.isBlank(params.getBid()) || !StringUtils.isNumeric(params.getBid())){
			return RESULT_LESS_PAGE;
		}
		BrandFatParams solrParams = this.transformParams(params);
		// 若解析的参数为空，到无结果页面
		if(null == solrParams){
			return RESULT_LESS_PAGE;
		}
		solrParams.setRequestInletEnum(RequestInletEnum.EBAY_BRAND_PAGE);
		brandBo = brandBof.findBrandEbayPageResult(solrParams);
		if(brandBo == null || StringUtils.isEmpty(brandBo.getBrandName())){
			return "forward:/error/404.htm";
		}
		
		// 设置banner图片路径
		if(StringUtils.isNotEmpty(brandBo.getBannerImageName2())){
			String bannerImage = XiuWebConstant.BRAND_BANNER_IMG_URL + params.getBid() + "/" + brandBo.getBannerImageName2();
			if(StringUtils.isNotEmpty(bannerImage)){
				brandBo.setBannerImageName2(bannerImage);
				int f = bannerImage.indexOf(".");
				if(bannerImage.indexOf("http://") > -1){
					String newUrl = bannerImage.substring(0,f) + bannerImage.substring(f).replaceAll("//", "/");
					brandBo.setBannerImageName2(newUrl);
				}
			}
		}
		//返回参数
		model.addAllAttributes(new BeanMap(brandBo));
		//点击流插码source_id
      	model.addAttribute("s_id",req.getParameter("s_id"));
		
		if(null == brandBo || null == brandBo.getGoodsItemList() || brandBo.getGoodsItemList().size()==0)
			return RESULT_LESS_PAGE;
		
		//如果当前页数大于最大页数，则返回无结果页面		William.zhang	20130704
      	if(brandBo.getPage().getPageNo() > brandBo.getPage().getPageCount()){
      		return RESULT_LESS_PAGE;
      	}
		
      	model.addAttribute("seoInfo", this.buildSEOInfo(brandBo));
      	
		// 设置推荐参数
        if(params != null && params.getBid() != null){
        	brandBo.setRecommenBrandIds(params.getBid().replace("|",","));
        }
       
		//插码
		this.buildClickCookieValue(res, 
        		Integer.toString(brandBo.getFatParams().getBrandId()), 
        		brandBo.getPage().getRecordCount(), 
        		brandBo.getPage().getPageNo(), 
        		brandBo.getFatParams().getMktType()==null?0 : brandBo.getFatParams().getMktType().getType());
		//过滤掉促销的两个ID 
        SearchController.filterCatalogList(brandBo);
		return "ebay-brand";
	}
	
	/**
	 * 对输入参数进行校验，并赋予初始值<br>
	 * 关键字：不允许为空，最多不超过maxSearchLength个字符<br>
	 * @param params
	 * @param params
	 * @param solrParams
	 * @return
	 */
	private BrandFatParams transformParams(BrandParams params){
		BrandFatParams fatParams = new BrandFatParams(PAGE_SIZE);
//		List<Integer> brandIds = new ArrayList<Integer>();
//		//查询纠错
//		fatParams.setCorrect(false);
//		//来源
//		fatParams.setFromTypeEnum(FromTypeEnum.EBAY);
		// 验证运营分类
		if(null != params.getCat() && params.getCat().intValue()>0){
			fatParams.setCatalogId(params.getCat());
		}

		// 验证品牌ID 新品牌组装规则，前台传入String 这里转换为INT		William.zhang 20130506
//        if(params.getBid() != null && params.getBid().length() > 0){
//        	if(params.getBid().indexOf('|') >= 0){
//        		String[] bids = StringUtils.split(params.getBid(), "|");
//        		for(String str : bids){
//        			if(StringUtils.isNumeric(str) && params.getBid().length() > 0){
//        				brandIds.add(Integer.parseInt(str));
//        			}
//        		}
//        		fatParams.setBrandIds(brandIds);
//        	}else{
//        		if(StringUtils.isNumeric(params.getBid()) && params.getBid().length() > 0){
//        			brandIds.add(Integer.parseInt(params.getBid()));
//        		}
//        		fatParams.setBrandIds(brandIds);
//        	}
//        }
		
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
		//店中店编码
		//if(StringUtils.isNotBlank(params.getP_code())
		//		&& !params.getP_code().equals(XiuSearchConfig.getPropertieValue(XiuSearchConfig.EBAY_PROVIDER_CODE)))
		//	fatParams.setProviderCode(params.getP_code());
		
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
//		 //新的业务逻辑，在页面增加itemShowType用户输入项	William.zhang	20130509
//		if(null != params.getChannel()){
//  			String[] channel = params.getChannel().split("|");
//  			for(String str : channel){
//  				
//  				if(StringUtils.isNotBlank(str) && StringUtils.isNumeric(str)){
//  		    		if(fatParams.getItemShowType() != null){
//  			    		fatParams.getItemShowType().add(Integer.parseInt(str));
//  			    	}else{
//  			    		 List<Integer> itemShowType = new ArrayList<Integer>(1);
//  			    		 itemShowType.add(Integer.parseInt(str));
//  			    		 fatParams.setItemShowType(itemShowType);
//  			    	}
//  				}
//  			}	    	
//  		}
		return fatParams;
	}
	
	private HashMap<String,String> buildSEOInfo(BrandBo brandBo){
		HashMap<String,String> seoMap = new HashMap<String,String>(3);
		StringBuilder sb = new StringBuilder();
		// title
		if(brandBo.getBrandNameCN()!=null)
			sb.append(brandBo.getBrandNameCN());
		else if(brandBo.getBrandNameEN() != null)
			sb.append(brandBo.getBrandNameEN().toLowerCase());
		sb.append("_");
		if(brandBo.getBrandName()!=null)
			sb.append(brandBo.getBrandName().toLowerCase());
		sb.append("官网_");
		if(brandBo.getBrandName()!=null)
			sb.append(brandBo.getBrandName().toLowerCase());
		sb.append("品牌大全_网购");
		if(brandBo.getBrandNameEN()!=null)
			sb.append(brandBo.getBrandNameEN().toLowerCase());
		else
			sb.append(brandBo.getBrandNameCN());
		sb.append("选走秀网");
		seoMap.put("title", sb.toString());
		
		// description
		sb = new StringBuilder();
		sb.append("网购");
		if(brandBo.getBrandNameCN()!=null)
			sb.append(brandBo.getBrandNameCN());
		else if(brandBo.getBrandNameEN()!=null)
			sb.append(brandBo.getBrandNameEN().toLowerCase());
		sb.append(",正品");
		if(brandBo.getBrandNameEN()!=null)
			sb.append(brandBo.getBrandNameEN().toLowerCase());
		else
			sb.append(brandBo.getBrandNameCN());
		sb.append(",就上走秀网,热销");
		if(brandBo.getBrandNameCN()!=null)
			sb.append(brandBo.getBrandNameCN());
		else if(brandBo.getBrandNameEN()!=null)
			sb.append(brandBo.getBrandNameEN().toLowerCase());
		sb.append("品牌大全,");
		if(brandBo.getBrandNameEN()!=null)
			sb.append(brandBo.getBrandNameEN().toLowerCase());
		else
			sb.append(brandBo.getBrandNameCN());
		sb.append("女装,");
		if(brandBo.getBrandNameCN()!=null)
			sb.append(brandBo.getBrandNameCN());
		else if(brandBo.getBrandNameEN()!=null)
			sb.append(brandBo.getBrandNameEN().toLowerCase());
		sb.append("包包,");
		if(brandBo.getBrandNameCN()!=null)
			sb.append(brandBo.getBrandNameCN());
		else if(brandBo.getBrandNameEN()!=null)
			sb.append(brandBo.getBrandNameEN().toLowerCase());
		sb.append("手表,");
		if(brandBo.getBrandNameCN()!=null)
			sb.append(brandBo.getBrandNameCN());
		else if(brandBo.getBrandNameEN()!=null)
			sb.append(brandBo.getBrandNameEN().toLowerCase());
		sb.append("鞋,正品保证,支持货到付款。");
		seoMap.put("description", sb.toString());
		
		// keywords
		sb = new StringBuilder();
		if(brandBo.getBrandNameCN()!=null)
			sb.append(brandBo.getBrandNameCN());
		else if(brandBo.getBrandNameEN()!=null)
			sb.append(brandBo.getBrandNameEN().toLowerCase());
		sb.append(",");
		if(brandBo.getBrandNameEN()!=null)
			sb.append(brandBo.getBrandNameEN().toLowerCase());
		else
			sb.append(brandBo.getBrandNameCN());
		sb.append(",");
		if(brandBo.getBrandNameCN()!=null)
			sb.append(brandBo.getBrandNameCN());
		else if(brandBo.getBrandNameEN()!=null)
			sb.append(brandBo.getBrandNameEN().toLowerCase());
		sb.append("官网,");
		if(brandBo.getBrandNameEN()!=null)
			sb.append(brandBo.getBrandNameEN().toLowerCase());
		else
			sb.append(brandBo.getBrandNameCN());
		sb.append("官网");
		seoMap.put("keywords", sb.toString());
		return seoMap;
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
    private void buildClickCookieValue(HttpServletResponse response,String brandId,int count,int page,int mkt){
    	if(brandId == null || response == null)
    		return;
        StringBuffer sb = new StringBuffer();
        sb.append("brand=").append(brandId).append("|count=").append(count).append("|page=").append(page).append("|mkt=").append(mkt);
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
