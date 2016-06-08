package com.xiu.search.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xiu.search.core.config.XiuSearchProperty;
import com.xiu.search.core.service.BrandService;
import com.xiu.search.dao.model.XDataBrand;
import com.xiu.search.web.config.XiuWebConstant;

/**
 * 菲拉格慕需求URL跳转
 * @author Leon
 *
 */
public class FGReadyRedirectFilter implements Filter{

	private final static String REDIRECT_TO_URL="http://ferragamo.xiu.com/";
	
	private final static String[] FG_KWS = new String[]{"菲拉格慕","ferragamo"};
	private final static String FG_BID = "1276";
	
	private final static String PARAM_KW = "kw";
	
	private final static String LIST_PATH_LONG_REGEX = "^/([0-9]*)-(.*)-(.*)-(.*)-(.*)-(.*)-(.*)-(.*)-(.*)-(.*)-(.*)-(.*)-(.*)-(.*)-(.*)\\.html$";
	private final static String BRAND_PATH_REGEX = "^/([0-9]*)\\.html$";
	private final static String BRAND_PATH_LONG_REGEX = "^/([0-9]*)-(.*)-(.*)-(.*)-(.*)-(.*)-(.*)-(.*)-(.*)-(.*)-(.*)-(.*)-(.*)-(.*)-(.*)\\.html$";
	
	
	private final static String HUGO_TO_URL="http://hugoboss.xiu.com/";
	
	private final static String YANGMI_TO_URL="http://yangmi.xiu.com/";
	
	private List<String> YANGMI_KWS = Collections.synchronizedList(new ArrayList<String>());//{"杨幂包"};
	
	private List<String> HUGO_KWS = Collections.synchronizedList(new ArrayList<String>());//{"bossorange","hugoboss","boss","hugo","雨果博斯"};
	
	private List<String> HUGO_BIDS = Collections.synchronizedList(new ArrayList<String>());// "5684470";
	
	private BrandService brandService;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		reloadHugo();
		//reloadYangM();
		reloadTimer();
		
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
		brandService = (BrandService)ctx.getBean("brandService");
	}
	
	private void reloadHugo(){
		String[] tmp=null;
		//搜索关键词列表
		String tmpStr = XiuSearchProperty.getInstance().hugoKeywords();
		if(tmpStr!=null && !"".equals(tmpStr.trim())){
			tmp=tmpStr.toLowerCase().replaceAll("[\\s]", "").split(",");
			HUGO_KWS.clear();
			HUGO_KWS.addAll(Arrays.asList(tmp));
		}
		//品牌ID列表
		tmpStr = XiuSearchProperty.getInstance().hugoBrandIds();
		if(tmpStr!=null && !"".equals(tmpStr.trim())){
			tmp=tmpStr.toLowerCase().replaceAll("[\\s]", "").split(",");
			HUGO_BIDS.clear();
			HUGO_BIDS.addAll(Arrays.asList(tmp));
		}
	}
	
	//杨幂包
	/*private void reloadYangM(){
		String[] tmp=null;
		//搜索关键词列表
		String tmpStr = XiuSearchProperty.getInstance().yangMiKeyWords();
		if(tmpStr!=null && !"".equals(tmpStr.trim())){
			tmp=tmpStr.toLowerCase().replaceAll("[\\s]", "").split(",");
			YANGMI_KWS.clear();
			YANGMI_KWS.addAll(Arrays.asList(tmp));
		}
	}*/
	
	/**
	 * 定时器
	 */
	private void reloadTimer(){
		Timer t=new Timer(true);
		TimerTask tk=new TimerTask() {
			@Override
			public void run() {
				reloadHugo();
				//reloadYangM();
			}
		};
		//N分钟之后 ，每隔N分钟执行一次,在此设置为10分钟
		t.scheduleAtFixedRate(tk, 20*60*1000, 10*60*1000);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		String path = req.getServletPath();
		if(XiuWebConstant.SEARCH_URL.equalsIgnoreCase(req.getServerName())){
			Map<String,String[]> params = req.getParameterMap();
			if(params.containsKey(PARAM_KW)){
				String kw = params.get(PARAM_KW)[0];
				//String deCodeKw=URLDecoder.decode(kw, "utf-8");
				//System.out.println(deCodeKw);
				if(isFGKw(kw)){
					res.sendRedirect(REDIRECT_TO_URL);
					return;
				}
				
				if(isHugoKw(kw)){
					res.sendRedirect(HUGO_TO_URL);
					return;
				}
				//杨幂包
				if(isYmbKw(kw)){
					res.sendRedirect(YANGMI_TO_URL);
					return;
				}
				
				// 是否品牌直达
				String url = directGo(kw);
				if(null != url){
					res.sendRedirect(url);
					return;
				}
			}
		}else if(XiuWebConstant.LIST_URL.equalsIgnoreCase(req.getServerName())){
			Pattern p = Pattern.compile(LIST_PATH_LONG_REGEX);
			Matcher matcher = p.matcher(path);
			if(matcher.find()){
				String bid = matcher.group(2);
				if(FG_BID.equals(bid)){
					res.sendRedirect(REDIRECT_TO_URL);
					return;
				}
			}
		}else if(XiuWebConstant.BRAND_URL.equalsIgnoreCase(req.getServerName())){
			Pattern p = Pattern.compile(BRAND_PATH_REGEX);
			Matcher matcher = p.matcher(path);
			if(matcher.find()){
				String bid = matcher.group(1);
				if(FG_BID.equals(bid)){
					res.sendRedirect(REDIRECT_TO_URL);
					return;
				}
				
				if(isHugoId(bid)){
					res.sendRedirect(HUGO_TO_URL);
					return;
				}
			}else{
				p = Pattern.compile(BRAND_PATH_LONG_REGEX);
				matcher = p.matcher(path);
				if(matcher.find()){
					String bid = matcher.group(1);
					if(FG_BID.equals(bid)){
						res.sendRedirect(REDIRECT_TO_URL);
						return;
					}
					
					if(isHugoId(bid)){
						res.sendRedirect(HUGO_TO_URL);
						return;
					}
				}
			}
		}
		chain.doFilter(request, response);
	}

	private boolean isFGKw(String kw){
		if(kw != null){
			kw = kw.toLowerCase();
			for (int i = 0,len=FG_KWS.length; i < len; i++) {
				if(kw.indexOf(FG_KWS[i])>=0)
					return true;
			}
		}
		return false;
	}
	
	private boolean isHugoKw(String kw){
		if(HUGO_KWS.size()==0 || kw==null || "".equals(kw)){
			return false;
		}
		kw = kw.toLowerCase().replaceAll("[\\s]", "");
		if(HUGO_KWS.contains(kw)){
			return true;
		}
		return false;
	}
	
	
	private boolean isHugoId(String curId){
		if(HUGO_BIDS.size()==0 || curId==null || "".equals(curId)){
			return false;
		}
		if(HUGO_BIDS.contains(curId)){
			return true;
		}
		return false;
	}
	
	//杨幂包
	private boolean isYmbKw(String kw){
		if(YANGMI_KWS.size()==0 || kw==null || "".equals(kw)){
			return false;
		}
		kw = kw.toLowerCase().replaceAll("[\\s]", "");
		if(YANGMI_KWS.contains(kw)){
			return true;
		}
		return false;
	}
	/**
	 * 是否品牌直达
	 * @param kw
	 * @return
	 */
	private String directGo(String kw){
		String url = "http://brand.xiu.com/";
		List<XDataBrand> brandList = brandService.getAllBrandInfo();
		if(null != brandList){
			for(XDataBrand brand : brandList){
				if(kw.equalsIgnoreCase(brand.getBrandName())
						|| kw.equalsIgnoreCase(brand.getEnName())
						|| kw.equalsIgnoreCase(brand.getMainName())){
					url += brand.getBrandId() + ".html";
					return url;
				}
				if(brand.getBrandName()!=null  && kw.equalsIgnoreCase(brand.getBrandName().replace("·", " "))){
					url += brand.getBrandId() + ".html";
					return url;
				}
			}
		}
		return null;
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
