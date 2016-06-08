package com.xiu.search.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.xiu.search.core.solr.enumeration.MktTypeEnum;
import com.xiu.search.web.config.XiuWebConstant;

/**
 * 错误页面
 * @author Leon
 *
 */
@Controller
@RequestMapping(value = "/error")
public class ErrorController extends BaseController {

	@RequestMapping(value = "404")
    @ResponseStatus(HttpStatus.NOT_FOUND)
	public String handel404(HttpServletRequest req,Model model){
		if(XiuWebConstant.EBAY_URL.equals(req.getServerName())){
			model.addAttribute("mkt",MktTypeEnum.EBAY);
		}else{
			model.addAttribute("mkt",MktTypeEnum.XIU);
		}
		return INDEX;
	}
	@RequestMapping(value = "500")
    @ResponseStatus(HttpStatus.NOT_FOUND)
	public String handel500(HttpServletRequest req,Model model){
		if(XiuWebConstant.EBAY_URL.equals(req.getServerName())){
			return "error/ebay-server-error";
		}else{
			return "error/server-error";
		}
	}
	
}
