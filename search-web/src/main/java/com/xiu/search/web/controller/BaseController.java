package com.xiu.search.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.xiu.search.core.config.XiuSearchProperty;
import com.xiu.search.web.config.XiuWebConstant;

/**
 * 定义了几个通用的参数<br>
 * 请在开发环境中添加 -Ddevelop=1 开启开发环境域名<br>
 * 1. wwwUrl www.xiu.com<br>
 * 2. searchUrl search.xiu.com<br>
 * 3. listUrl list.xiu.com<br>
 * 4. brandUrl brand.xiu.com<br>
 * 5. staticUrl www.xiustatic.com
 * 
 * @author Leon
 */
public class BaseController {

    protected final static String ERROR = "error";
    protected final static String SUCCESS = "success";
    protected final static String LOGIN = "login";
    protected final static String INDEX = "index-search";

    /**
     * 获取首页的url
     * 
     * @param req
     * @return
     */
    @ModelAttribute("wwwUrl")
    public String getWwwUrl(HttpServletRequest req) {
        return XiuWebConstant.HTTP_SCHEMA + "://" + XiuWebConstant.WWW_URL + "/";
    }

    /**
     * 获取搜索页面的url
     * 
     * @param req
     * @return
     */
    @ModelAttribute("searchUrl")
    public String getSearchUrl(HttpServletRequest req) {
        if (XiuWebConstant.isDevelop()) {
            return XiuWebConstant.HTTP_SCHEMA + "://" + XiuWebConstant.SEARCH_URL
                    + (80 == req.getServerPort() ? "" : ":" + req.getServerPort()) + req.getContextPath() + "/";
        }
        return XiuWebConstant.HTTP_SCHEMA + "://" + XiuWebConstant.SEARCH_URL + "/";
    }

    /**
     * 获取列表页面的路径
     * 
     * @param req
     * @return
     */
    @ModelAttribute("listUrl")
    public String getListUrl(HttpServletRequest req) {
        if (XiuWebConstant.isDevelop()) {
            return XiuWebConstant.HTTP_SCHEMA + "://" + XiuWebConstant.LIST_URL
                    + (80 == req.getServerPort() ? "" : ":" + req.getServerPort()) + req.getContextPath() + "/";
        }
        return XiuWebConstant.HTTP_SCHEMA + "://" + XiuWebConstant.LIST_URL + "/";
    }
    /**
     * 获取列表页面的路径
     * 
     * @param req
     * @return
     */
    @ModelAttribute("newArrivalUrl")
    public String getNewArrivalUrl(HttpServletRequest req) {
    	if (XiuWebConstant.isDevelop()) {
    		return XiuWebConstant.HTTP_SCHEMA + "://" + XiuWebConstant.LIST_URL
    				+ (80 == req.getServerPort() ? "" : ":" + req.getServerPort()) + req.getContextPath() + "/new/";
    	}
    	return XiuWebConstant.HTTP_SCHEMA + "://" + XiuWebConstant.LIST_URL + "/new/";
    }

    
    /**
     * 获取品牌页面的路径
     * 
     * @param req
     * @return
     */
    @ModelAttribute("brandUrl")
    public String getBrandUrl(HttpServletRequest req) {
        if (XiuWebConstant.isDevelop()) {
            return XiuWebConstant.HTTP_SCHEMA + "://" + XiuWebConstant.BRAND_URL
                    + (80 == req.getServerPort() ? "" : ":" + req.getServerPort()) + req.getContextPath() + "/";
        }
        return XiuWebConstant.HTTP_SCHEMA + "://" + XiuWebConstant.BRAND_URL + "/";
    }

    /**
     * 获取详情页url
     * 
     * @param req
     * @return
     */
    @ModelAttribute("itemUrl")
    public String getItemUrl(HttpServletRequest req) {
        // if(XiuWebConstant.isDevelop()){
        // return XiuWebConstant.HTTP_SCHEMA + "://"
        // + XiuWebConstant.ITEM_URL
        // + (80 == req.getServerPort() ? "" : ":"+req.getServerPort())
        // + req.getContextPath()+"/";
        // }
        return XiuWebConstant.HTTP_SCHEMA + "://" + XiuWebConstant.ITEM_URL + "/";
    }

    /**
     * Ebay url
     * 
     * @param req
     * @return
     */
    @ModelAttribute("ebayUrl")
    public String getEbayUrl(HttpServletRequest req) {
        return XiuWebConstant.HTTP_SCHEMA + "://" + XiuWebConstant.EBAY_URL + "/";
    }

    /**
     * 获取当前请求的路径
     * 
     * @param req
     * @return
     */
    @ModelAttribute("requestUrl")
    public String getRequestUrl(HttpServletRequest req) {
        if (XiuWebConstant.isDevelop()) {
            return XiuWebConstant.HTTP_SCHEMA + "://" + req.getServerName()
                    + (80 == req.getServerPort() ? "" : ":" + req.getServerPort()) + req.getContextPath() + "/";
        }
        return XiuWebConstant.HTTP_SCHEMA + "://" + req.getServerName() + "/";
    }

    /**
     * 获取静态资源路径
     * 
     * @return
     */
    @ModelAttribute("staticUrl")
    public String getStaticUrl(HttpServletRequest req) {
        if (XiuWebConstant.isDevelop()) {
            return XiuWebConstant.HTTP_SCHEMA + "://" + XiuWebConstant.SEARCH_URL
                    + (80 == req.getServerPort() ? "" : ":" + req.getServerPort()) + req.getContextPath()
                    + "/resources/";
        }
        return XiuWebConstant.HTTP_SCHEMA + "://" + XiuWebConstant.STATIC_URL + "/";
    }

    /**
     * 获取静态资源路径
     * 
     * @return
     */
    @ModelAttribute("imagesUrl")
    public String getImagesUrl(HttpServletRequest req) {
        // if(XiuWebConstant.isDevelop()){
        // return XiuWebConstant.HTTP_SCHEMA + "://"
        // + req.getServerName()
        // + (80 == req.getServerPort() ? "" : ":"+req.getServerPort())
        // + req.getContextPath()
        // + "/resources/";
        // }
        return XiuWebConstant.HTTP_SCHEMA + "://" + XiuWebConstant.IMAGE_URL + "/";
    }

    @ModelAttribute("commentUrl")
    public String getCommentUrl(HttpServletRequest req) {
        return XiuWebConstant.HTTP_SCHEMA + "://" + XiuWebConstant.COMMENT_URL + "/";
    }

    /**
     * 菲格拉慕
     * 
     * @param req
     * @return
     */
    @ModelAttribute("ferragamoUrl")
    public String getFerragamoUrl(HttpServletRequest req) {
        return XiuWebConstant.HTTP_SCHEMA + "://" + XiuWebConstant.FERRAGAMO_URL + "/";
    }

    @ModelAttribute("developStatus")
    public boolean isDevelopStatus(HttpServletRequest req) {
        return XiuWebConstant.isDevelop();
    }

    @ModelAttribute("bfdClientName")
    public String getBfdClientName() {
        // String clientName=XiuSearchConfig.getPropertieValue(XiuSearchConfig.BFD_CIIENT_NAME);
        /** 百分点账号修改为从数据库配置中获取 20130514 */
        String clientName = XiuSearchProperty.getInstance().getBFDPassword();
        return null != clientName ? clientName : "";
    }
}
