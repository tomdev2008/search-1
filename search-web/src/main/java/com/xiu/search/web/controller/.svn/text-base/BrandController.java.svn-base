package com.xiu.search.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xiu.search.core.bo.BrandBo;
import com.xiu.search.core.bo.FacetFilterBo;
import com.xiu.search.core.bof.BrandBof;
import com.xiu.search.core.enumeration.RequestInletEnum;
import com.xiu.search.core.solr.enumeration.FacetPriceRangeQueryEnum;
import com.xiu.search.core.solr.enumeration.ItemShowTypeEnum;
import com.xiu.search.core.solr.enumeration.SearchSortOrderEnum;
import com.xiu.search.core.solr.params.BrandFatParams;
import com.xiu.search.core.util.XiuSearchStringUtils;
import com.xiu.search.dao.config.XiuSearchConfig;
import com.xiu.search.web.config.XiuWebConstant;
import com.xiu.search.web.util.CookieUtils;
import com.xiu.search.web.util.CookieUtils.CookieExpireEnum;
import com.xiu.search.web.vo.BrandParams;

/**
 * 品牌页面
 * @author Leon
 *
 */
@Controller
public class BrandController extends BaseController {

	private Logger LOGGER = Logger.getLogger(getClass());
	
	/**
	 * 搜索最多字符数，1个汉字=2个字符<br>
	 * 禁止用户恶性的无限制输入参数
	 */
	private static final int MAX_SEARCH_LENGTH = 64;
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
	private static final String RESULT_LESS_PAGE = "error/brand-noresult";
	
	@Autowired
	private BrandBof brandBof;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/brand-action")
	public String execute(BrandParams params,@RequestParam(value="bo", required=false,defaultValue="0")int brandOnly, BindingResult bindingResult,HttpServletRequest req,HttpServletResponse res,Model model) throws InterruptedException{
		//原始输入参数
		model.addAttribute("params",params);
		req.removeAttribute("bo");
		// 若参数为空或品牌为空，直接返回到首页
		if(null == params || null == params.getBid()){
			return INDEX;
		}
		BrandBo brandBo = null;
		// 若不符合条件，到错误页面	由于修改了Bid的类型，所以修改了对比方式	William.zhang 20130506
		if(StringUtils.isBlank(params.getBid()) || !StringUtils.isNumeric(params.getBid())){
			return RESULT_LESS_PAGE;
		}
		BrandFatParams solrParams = this.transformParams(params);
		// 若解析的参数为空，到无结果页面
		if(null == solrParams){
			return RESULT_LESS_PAGE;
		}
		if(null != solrParams)
			solrParams.setRequestInletEnum(RequestInletEnum.BRAND_PAGE);
		brandBo = brandBof.findBrandXiuPageResult(solrParams);
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
		
      	/**
      	 * SEO优化处理 by dingchenghong 20140409
      	 */
      	//model.addAttribute("seoInfo", this.buildSEOInfo(brandBo));
      	try{
      		model.addAttribute("seoInfo", this.buildSEOInfo(brandBo,brandBo.getPage().getPageNo(),brandOnly));
      	}catch(Exception ex){
      		LOGGER.error("品牌页搜索SEO TDK优化出现异常", ex);
      		model.addAttribute("seoInfo", this.buildSEOInfo(brandBo));
      	}
      	
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
		return "brand";
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
				&& null == params.getE_price()
				&& null == params.getF_price()
				&& null == params.getFilter()
				&& (null == params.getP() || params.getP().intValue()==1)
				&& null == params.getS_price()
				&& null == params.getSort()
				&& null == params.getBa());
		
		// 是否根据商品数量自动切换mkt tab
//		fatParams.setAutoMktSwitchFlag(fatParams.isFirstRequest() 
//				&& null == params.getMkt());
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
	 * 生成SEO信息
	 * @param brandBo
	 * @param pageNo 当前页码
	 * @return
	 */
	private HashMap<String,String> buildSEOInfo(BrandBo brandBo,int pageNo,int brandOnly) throws Exception{
		HashMap<String,String> seoMap = new HashMap<String,String>(3);
		
		// seo优化 --- title by dingchenghong 20140409
		//进入到的是品牌主页
		if (brandOnly == 1) {
			StringBuilder title = new StringBuilder();
			StringBuilder desc = new StringBuilder();
			StringBuilder key = new StringBuilder();
			String brandCN = brandBo.getBrandNameCN()==null?"":brandBo.getBrandNameCN();
			String brandEN = brandBo.getBrandNameEN()==null?"":brandBo.getBrandNameEN();
			String brandName = brandBo.getBrandName()==null?"":brandBo.getBrandName();
			if (!"".equals(brandCN) || !"".equals(brandEN)){
				String tempName = brandCN;
				if ("".equals(brandCN)) {
					tempName = brandEN;
				}
				title.append("【").append(brandEN).append(brandCN).append("】").append(tempName).append("价格-正品")
					 .append(tempName).append("推荐-走秀网官网");
				if (pageNo >1) {
					title.append("-第"+pageNo+"页");
				}
				key.append(brandEN).append(brandCN).append("，").append(tempName).append("价格,正品").append(tempName).append("推荐");
				desc.append("走秀网时尚国际品牌商城，为您推荐正品").append(brandEN).append(brandCN).append("商品，查看")
				   .append(tempName).append("价格，网购").append(tempName).append("商品，上XIU.COM").append(tempName)
				   .append("频道，正品保证，货到付款，国际品牌全球同价！");
			}else {
				title.append("【").append(brandName).append("】").append(brandName).append("价格-正品")
				 .append(brandName).append("推荐-走秀网官网");
				if (pageNo >1) {
					title.append("-第"+pageNo+"页");
				}
				key.append(brandName).append("，").append(brandName).append("价格,正品").append(brandName).append("推荐");
				desc.append("走秀网时尚国际品牌商城，为您推荐正品").append(brandName).append("商品，查看")
				   .append(brandName).append("价格，网购").append(brandName).append("商品，上XIU.COM").append(brandName)
				   .append("频道，正品保证，货到付款，国际品牌全球同价！");
				}
			seoMap.put("title", title.toString());
			seoMap.put("description", desc.toString());
			seoMap.put("keywords", key.toString());
		}else {
			StringBuffer sf = new StringBuffer();
			// 筛选选择的catalogName
			String selectedCatalogName = "";
			// 一级/二级/三级类别名
			String lastCatalogName = "";
			// 一级品类名称
			String firstCatalogName = "";
			// 筛选属性
			String selectFilter = "";
			// 品牌名称
			String brandName = "";
			
			if(brandBo.getBrandNameCN() != null){
				brandName = brandBo.getBrandNameCN();
			}else if(brandBo.getBrandNameEN() != null){
				brandName = brandBo.getBrandNameEN();
			}else{
				brandName = brandBo.getBrandName();
			}
			
			// 取得用户的筛选条件
			List<String> sfList = new ArrayList<String>();
			if(brandBo.getSelectFacetBoList() != null && !brandBo.getSelectFacetBoList().isEmpty()){
				for(FacetFilterBo fb : brandBo.getSelectFacetBoList()){
					sfList.add(fb.getFacetValueBos().get(0).getName());
				}
			}
			
			// 对用户筛选条件排序
			if(!sfList.isEmpty()){
				Collections.sort(sfList);
				StringBuffer sf1 = new StringBuffer();
				for(String s : sfList){
					sf1.append(s);
					sf1.append(" ");
				}
				selectFilter = sf1.toString();
			}
			
			// 取得最后一级和第一级品类名称
			if(brandBo.getSelectCatalogPlaneList() != null && !brandBo.getSelectCatalogPlaneList().isEmpty()){
				lastCatalogName = brandBo.getSelectCatalogPlaneList().get(brandBo.getSelectCatalogPlaneList().size() - 1).getCatalogName();
				firstCatalogName = brandBo.getSelectCatalogPlaneList().get(0).getCatalogName();
			}
			
			// 取得用户的筛选条件中的CatalogTree名称
			if(null != brandBo.getSelectedCatalogTree()){
				selectedCatalogName = brandBo.getSelectedCatalogTree().getCatalogName();
			}
			
			// 组织title
			/** 去掉selectedCatalogName by dingchenghong 20140428
			if(!"".equals(selectedCatalogName)){
				sf.append(selectedCatalogName);
			}**/
			if(!"".equals(selectFilter)){
				sf.append(selectFilter);
			}
			if(null != brandName){
				sf.append(brandName);
			}
			if(!"".equals(lastCatalogName)){
				sf.append(lastCatalogName);
			}
			sf.append(" - 走秀网");
			if(null != brandName){
				sf.append(brandName);
			}
			if(!"".equals(firstCatalogName)){
				sf.append(firstCatalogName);
			}
			sf.append("频道");
			if(pageNo > 1){
				sf.append(" ");
				sf.append("_第");
				sf.append(pageNo);
				sf.append("页");
			}
			seoMap.put("title", sf.toString());
			
			// 组织description
			sf = new StringBuffer();
			sf.append("走秀网国内最专业的时尚商城，提供");
			/** 去掉selectedCatalogName by dingchenghong 20140428
			if(!"".equals(selectedCatalogName)){
				sf.append(selectedCatalogName);
			}**/
			if(!"".equals(selectFilter)){
				sf.append(selectFilter);
			}
			if(!"".equals(brandName)){
				sf.append(brandName);
			}
			if(!"".equals(lastCatalogName)){
				sf.append(lastCatalogName);
			}
			sf.append("商品，查看");
			if(!"".equals(brandName)){
				sf.append(brandName);
			}
			if(!"".equals(lastCatalogName)){
				sf.append(lastCatalogName);
			}
			sf.append("，网购");
			if(!"".equals(brandName)){
				sf.append(brandName);
			}
			if(!"".equals(lastCatalogName)){
				sf.append(lastCatalogName);
			}
			sf.append("商品，上XIU.COM");
			if(!"".equals(brandName)){
				sf.append(brandName);
			}
			if(!"".equals(firstCatalogName)){
				sf.append(firstCatalogName);
			}
			sf.append("频道，正品保证，货到付款");
			seoMap.put("description", sf.toString());
			
			// 组织keywords
			sf = new StringBuffer();
			/** 去掉selectedCatalogName by dingchenghong 20140428
			if(!"".equals(selectedCatalogName)){
				sf.append(selectedCatalogName);
			}**/
			if(!"".equals(selectFilter)){
				sf.append(selectFilter);
			}
			if(!"".equals(brandName)){
				sf.append(brandName);
			}
			if(!"".equals(lastCatalogName)){
				sf.append(lastCatalogName);
			}
			seoMap.put("keywords", sf.toString());
		}
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
        	cv = sb.toString();
        	LOGGER.error("点击流cookie value encode出错："+cv, e);
        }
        CookieUtils.putCookie(response, CookieUtils.TEMP_PARAM, cv,".xiu.com", CookieExpireEnum.CURRENT);
    }
    
    /**
     * 清除品牌的缓存
     * @param brandId 品牌id
     * @param flag 标记   1:全部品牌的缓存  0:某个品牌的缓存
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/brand-clean-cache",method = RequestMethod.GET)
    public String clearCache(@RequestParam(value="brandId", required=false)Long brandId,
    		@RequestParam(value="flag", required=false)String flag,HttpServletResponse resp) throws Exception{
    	int f = 0;
    	if(null != flag){
    		try{
    			f = Integer.valueOf(flag);
    		}catch(Exception ex){
    			LOGGER.error("清除品牌缓存flag转换出错：" + flag, ex);
    		}
    	}
    	boolean result = brandBof.cleanCacheOfBrandInfo(brandId, f);
    	if(result){
    		resp.getWriter().write("...clean cache success...");
    	}else{
    		resp.getWriter().write("...clean cache failed...");
    	}
    	return null;
    }
}
