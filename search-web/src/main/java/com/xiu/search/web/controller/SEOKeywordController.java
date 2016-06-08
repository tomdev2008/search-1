package com.xiu.search.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xiu.search.core.bo.SeoKeywordBo;
import com.xiu.search.core.bof.SEOKeywordBof;
import com.xiu.search.dao.model.XiuSeoKeywordModel;

/**
 * 
 * com.xiu.search.web.controller.SEOKeywordController.java

 * @Description: TODO  SEO 关键词

 * @author lvshuding   

 * @date 2013-10-28 上午11:48:52

 * @version V1.0
 */
@Controller
public class SEOKeywordController extends BaseController {
	
	private Logger LOGGER = Logger.getLogger(getClass());
	
	private static final String LOG_HEAD="【SEO关键词】 ";
	
	/**
	 * 每页展示关键词数
	 */
	private static final int PAGE_SIZE = 1000;
	
	/**
	 * 每页展示商品数
	 */
	private static final int G_PAGE_SIZE = 32;
	
	
	@Autowired
	private SEOKeywordBof seoKeywordBof;
	
	
	
	/**
	 * 关键词列表入口
	 * @param req
	 * @param res
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/seokw-action")
	public String toKwList(HttpServletRequest req,HttpServletResponse res,Model model) throws InterruptedException{
		
		String pNum = req.getParameter("p");
		int pageNumber=1;
		if(pNum!=null && !"".equals(pNum)){
			pageNumber=Integer.parseInt(pNum);
		}
		pageNumber=pageNumber==0?1:pageNumber;	
		
		SeoKeywordBo bo = seoKeywordBof.findSEOKwPageResult(pageNumber, PAGE_SIZE);
		/**
		 * 此处的处理逻辑没有定，暂定继续跳转到列表页,记录日志
		 */
		if(bo==null){
			bo=new SeoKeywordBo();
			LOGGER.error(LOG_HEAD.concat("查询列表页出错"));
		}
		
		model.addAllAttributes(new BeanMap(bo));
		
		return "seo-list";
	}
	
	/**
	 * 关键词对应商品列表入口
	 * @param req
	 * @param res
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/seokw-goods-action")
	public String kwToGoods(HttpServletRequest req,HttpServletResponse res,Model model) throws InterruptedException{
		
		String id=req.getParameter("kwId");
		if(id==null || "".equals(id)){
			LOGGER.error(LOG_HEAD.concat("关键词对应的主键不能为空"));
			return "forward:/error/404.htm";
		}
		
		String pNum = req.getParameter("p");
		int pageNumber=1;
		if(pNum!=null && !"".equals(pNum)){
			pageNumber=Integer.parseInt(pNum);
		}
		pageNumber=pageNumber==0?1:pageNumber;
		
		SeoKeywordBo bo = seoKeywordBof.findSEOKwById(Integer.parseInt(id),pageNumber,G_PAGE_SIZE);
		/**
		 * 此处的处理逻辑没有定，暂定继续跳转到列表页,记录日志
		 */
		if(bo==null){
			LOGGER.error(LOG_HEAD.concat("关键词对应的主键["+id+"]在数据库中不存在"));
			return "forward:/error/404.htm";
		}
		
		XiuSeoKeywordModel kw = bo.getKwList().get(0);
		model.addAttribute("kw",kw);
		
		System.out.println("---------------kw.brandId---------"+kw.getbId());
		
//		Page page=new Page();
//		page.setPageNo(pageNumber);
//		page.setPageSize(PAGE_SIZE);
//		bo.setPage(page);
		
		bo.getPage().setPageSize(G_PAGE_SIZE);		
		
		/**
		 * 查询Solr
		 */
		
		
		model.addAllAttributes(new BeanMap(bo));
	        
		return "seo-goods";
	}
	
	
}
