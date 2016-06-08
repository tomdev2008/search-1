package com.xiu.search.web.ext.freemarker;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.RequestContext;

import com.xiu.search.web.config.XiuWebConstant;

import freemarker.core.Environment;
import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelAdapter;
import freemarker.template.TemplateModelException;

/**
 * 翻页模板
 * 需定义search-page.ftl路径
 * @author Leon
 *
 */
public class PageFTLMethod implements TemplateDirectiveModel{
	
	private static Logger log = LoggerFactory.getLogger(PageFTLMethod.class);
	
	private static final String PAGE_FILE_PATH = "/views/templates/search-page.ftl";
	
	private static final String ENCODE="UTF-8";
	
	// 定义Configuration单例
	private volatile Configuration cfg;
	/**
	 * 当前页码
	 */
	private static final String CURRENT_PAGE_PARAM = "currentPage";
	/**
	 * 总页码
	 */
	private static final String TOTAL_PAGE_PARAM = "totalPage";
	/**
	 * 样式
	 */
	private static final String THEME_PARAM = "theme";
	/**
	 * 翻页参数名
	 */
	private static final String PAGE_PARAM_NAME_PARAM = "pageParamName";
	/**
	 * 命名空间
	 */
	private static final String NAME_SPACE_PARAM = "namespace";
	/**
	 * 请求名
	 */
	private static final String ACTION_PARAM = "action";
	/**
	 * 锚点
	 */
	private static final String ANCHOR_PARAM = "anchor";
	/**
	 * 额外的参数
	 */
	private static final String REPLACE_PARAMS_PARAM = "repQuery";
	
	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		if(params.isEmpty()){
			throw new TemplateModelException("The params must be not empty.");
		}
		if(!params.containsKey(CURRENT_PAGE_PARAM) ){
			throw new TemplateModelException("The param \""+CURRENT_PAGE_PARAM+"\" is required.");
		}
		if(!params.containsKey(TOTAL_PAGE_PARAM)){
			throw new TemplateModelException("The param \""+TOTAL_PAGE_PARAM+"\" is required.");
		}
		if(loopVars.length>0){
			throw new TemplateModelException("This directive doesn't allow loop variables.");
		}
		if(body != null){
			throw new TemplateModelException("This directive doesn't need body.");
		}
		HttpServletRequest req = null;
		HttpServletResponse res = null;
		TemplateModelAdapter  hrp = (TemplateModelAdapter)env.__getitem__("Request");
		if(null != hrp){
			HttpRequestHashModel tm = (HttpRequestHashModel)hrp.getTemplateModel();
			if(null != tm){
				req = tm.getRequest();
				res = tm.getResponse();
			}
		}
		if(null == res || null == req){
			throw new TemplateModelException("Servlet request and response is null.");
		}
		RequestContext rc = (RequestContext)env.__getitem__("springMacroRequestContext");
		if(null == rc){
			throw new TemplateModelException("Request context is null.");
		}
		// init params;
		PageParamsModel pm = this.initParams(params,req,res);
//		Configuration cfg = env.getConfiguration();
		Template template = this.getTemplateFromConfiguration(rc.getWebApplicationContext().getServletContext(), PAGE_FILE_PATH);
		template.setEncoding(ENCODE);
		template.setSetting("url_escaping_charset", ENCODE);
		template.setSetting("number_format", "0.######");
		Object rootMap = null;
		if("1".equals(pm.getTheme())){
			rootMap = getMinRootMap(pm,req,res);
		}else{
			// default 0 or null
			rootMap = getFullRootMap(pm,req,res);
		}
		template.process(rootMap, env.getOut());
	}
	
	/**
	 * 获取简单模式的分页root
	 * @param req
	 * @param res
	 * @return
	 */
	private Object getMinRootMap(PageParamsModel pm,HttpServletRequest req,HttpServletResponse res){
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("currentPage", pm.getCurrentPage());
		context.put("totalPage", pm.getTotalPage());
		context.put("theme", pm.getTheme());
		context.put("preUrl", this.buildPagerUrl(pm,req, res, Integer.toString(pm.getCurrentPage() > 1 ? pm.getCurrentPage() - 1 : 1),pm.getAnchor()));
		context.put("nextUrl", this.buildPagerUrl(pm,req, res,Integer.toString(pm.getCurrentPage() < pm.getTotalPage() ? pm.getCurrentPage()+1 : pm.getTotalPage()),pm.getAnchor()));
		context.put("pageParamName", pm.getPageParamName());
		return context;
	}
	
	/**
	 * 获取全模式的分页root
	 * @return
	 */
	private Object getFullRootMap(PageParamsModel pm,HttpServletRequest req,HttpServletResponse res){
		int currentPage = pm.getCurrentPage() > 0 ? pm.getCurrentPage(): 1;
		Map<String, Object> context = new HashMap<String, Object>();
		if(currentPage>pm.getTotalPage()){
			currentPage=1;
		}
		
		context.put("firstUrl", this.buildPagerUrl(pm,req, res, "1",pm.getAnchor()));
		if(pm.getTotalPage() > 1){
			context.put("secondUrl", this.buildPagerUrl(pm,req, res, "2",pm.getAnchor()));
		}
		if (currentPage > 1) {
			context.put("preUrl", this
					.buildPagerUrl(pm,req, res,Integer.toString(currentPage - 1),pm.getAnchor()));
		}
		if (currentPage < pm.getTotalPage()) {
			context.put("nextUrl", this.buildPagerUrl(pm,req, res,Integer.toString(currentPage + 1),pm.getAnchor()));
		}
		context.put("currentPage", currentPage);
		context.put("totalPages", pm.getTotalPage());
		context.put("leftStep", pm.getLeftStep());
		context.put("rightStep", pm.getRightStep());
		context.put("lastUrl", this.buildPagerUrl(pm,req, res, Integer.toString(pm.getTotalPage()),pm.getAnchor()));
		context.put("theme", pm.getTheme());
		
		if(currentPage <= 10){
			
			/**
			 * 当前页码<=10时的显示
			 * [首页][上一页]1 2 3 4 5 6 [7] 8 9 10 [下一页][尾页] 共39页
			 */
			int beginPage = 1;
			int endPage = pm.getTotalPage() > 10 ? 10 : pm.getTotalPage();
			List<String> pageUrls = new ArrayList<String>(endPage-beginPage);
			List<Integer> pageIndex = new ArrayList<Integer>(endPage-beginPage);
			for(int i = beginPage; i <= endPage; i++){
				pageIndex.add(i);
				if(i != currentPage){
					pageUrls.add(this.buildPagerUrl(pm,req, res, Integer.toString(i),pm.getAnchor()));
				}else{
					pageUrls.add("currentPage");
				}
			}
			context.put("pageUrls", pageUrls);
			context.put("pageIndex", pageIndex);
		}else if(currentPage > 10){
			/**
			 * 当前页码>10时的显示（当前页左右显示三个页码的情况）
			 * [首页][上一页]1 2 3... ... 20 21 22 [23] 24 25 26 [下一页][尾页] 共39页
			 */
			int beginPage = currentPage - pm.getLeftStep();
			if(beginPage < 2){
				beginPage = 2;
			}
			int endPage = currentPage + pm.getRightStep()+1;
			if(endPage > pm.getTotalPage()){
				endPage = pm.getTotalPage() > beginPage ? pm.getTotalPage() : beginPage;
			}
			
			/**
			 * 当前页码接近总页数时，右边不再显示[下一页][尾页]
			 * （当前页左右显示三个页码的情况）
			 * [首页][上一页]1 2 3... ...34 35 36 [37] 38 39  共39页
			 */
			if(pm.getTotalPage() - currentPage <= pm.getRightStep()){
				endPage = pm.getTotalPage() + 1;
			}
			List<String> pageUrls = new ArrayList<String>(endPage-beginPage);
			List<Integer> pageIndex = new ArrayList<Integer>(endPage-beginPage);
			if(endPage>beginPage){
				for (int i = beginPage; i < endPage; i++) {
					pageIndex.add(i);
					if(i != currentPage){
						pageUrls.add(this.buildPagerUrl(pm,req, res, Integer.toString(i),pm.getAnchor()));
					}else{
						pageUrls.add("currentPage");
					}
				}
			}
			context.put("thirdUrl", this.buildPagerUrl(pm,req, res, "3",pm.getAnchor()));
			context.put("pageUrls", pageUrls);
			context.put("pageIndex", pageIndex);
		}
		String nopageUrl = this.buildPagerUrl(pm,req, res, null,null);
		context.put("nopageUrl", nopageUrl);
		context.put("inputParamMap", buildPagerInputMap(pm,req, nopageUrl));
		context.put("pageParamName", pm.getPageParamName());
		return context;
	}
	
	/**
	 * 获取input输入的param
	 * @param req
	 * @param url
	 * @return
	 */
	private Map<String,List<String>> buildPagerInputMap(PageParamsModel pm,HttpServletRequest req,String url){
//		Map<String, String[]> allParams = req.getParameterMap();
		//解析出来参数
		int questIndex = url.indexOf('?');
		if(questIndex == -1){
			return null;
		}
		String queryStr = url.substring(questIndex+1,url.length());
		Map<String,List<String>> matchValueMap = new HashMap<String, List<String>>();
		int ampersandIndex,lastAmpersandIndex = 0,pvIndex;
		String subStr,param,value;
		do{
			ampersandIndex = queryStr.indexOf('&', lastAmpersandIndex)+1;
			if(ampersandIndex>0){
				subStr = queryStr.substring(lastAmpersandIndex, ampersandIndex-1);
				lastAmpersandIndex = ampersandIndex;
			}else{
				subStr = queryStr.substring(lastAmpersandIndex);
			}
			pvIndex = subStr.indexOf("=");
			param = pvIndex>=0 ? subStr.substring(0,pvIndex) : subStr;
			value = pvIndex>=0 ? subStr.substring(pvIndex+1,subStr.length()): "";
			if(!pm.getPageParamName().equals(param) && null != param && !"".equals(param) && null != value && !"".equals(value)){
				if(!matchValueMap.containsKey(param))
					matchValueMap.put(param, new ArrayList<String>());
				try {
					value = URLDecoder.decode(value, ENCODE);
				} catch (Exception e) {
					log.error("无法DECODE参数："+value,e);
				}
				matchValueMap.get(param).add(value);
			}
		}while(ampersandIndex>0);
		return matchValueMap;
	}
	
	/**
	 * 生成page的url
	 * @param req
	 * @param res
	 * @param pageIndex 页码
	 * @param anchor 锚点
	 * @return
	 */
	private String buildPagerUrl(PageParamsModel pm,HttpServletRequest req,HttpServletResponse res,String pageIndex,String anchor){
		Map<String, String[]> allParams = this.getFormatParamMap(pm);
		if(null != pageIndex){
			allParams.put(pm.getPageParamName(), new String[] { pageIndex });
		}else{
			allParams.remove(pm.getPageParamName());
		}
		String paramStr = this.paramMapToString(allParams, ENCODE, false);
		StringBuffer url = new StringBuffer();
		url
//		.append(req.getContextPath())
		.append(pm.getNamespace())
		.append(pm.getAction()).append("?").append(paramStr);
//		res.encodeURL(arg0);
		if(null != anchor){
			return this.buildFullUrl(req, res.encodeURL(url.toString())+"#"+anchor);
		}
		return this.buildFullUrl(req, res.encodeURL(url.toString()));
	}
	
	/**
	 * 将参数转化为query串
	 * @param paramMap
	 * @param encode
	 * @param ignoreNull 是否忽略空值
	 * @return
	 */
	private String paramMapToString(Map<String,String[]> paramMap, String encode, boolean ignoreNull){
		StringBuffer ret = new StringBuffer();
		boolean first = true;
		String value = null;
		String[] orgValue = null;
		for (Entry<String,String[]> param :  paramMap.entrySet()){
			orgValue = param.getValue();
			if(orgValue != null){
				if (orgValue instanceof String[]) {
					String[] orgTempValue = orgValue;
					for (int i = 0,len=orgTempValue.length; i < len; i++) {
						this.paramKeyValueBuild(ret, first, param.getKey().toString(), orgTempValue[i], encode, ignoreNull);
						first = false;
					}
					continue;
				} else {
					value = "";
					//value = orgValue.toString();
				}
				this.paramKeyValueBuild(ret, first, param.getKey().toString(), value, encode, ignoreNull);
				first = false;
			}
		}
		return ret.toString();
	}
	
	private void paramKeyValueBuild(StringBuffer sb,boolean first,String key,String value,String encode, boolean ignoreNull){
		if (ignoreNull && null == value) {
			return;
		}
		if(!first){
			sb.append("&");
		}
		sb.append(key+"="+value);
	}
	
	/**
	 * 
	 * @param params
	 */
	private PageParamsModel initParams(Map params,HttpServletRequest req,HttpServletResponse res){
		PageParamsModel pm = new PageParamsModel();
		pm.setCurrentPage(Integer.valueOf(String.valueOf(params.get(CURRENT_PAGE_PARAM))));
		pm.setTotalPage(Integer.valueOf(String.valueOf(params.get(TOTAL_PAGE_PARAM))));
		pm.setAnchor(params.containsKey(ANCHOR_PARAM) ? params.get(ANCHOR_PARAM).toString() : null);
		pm.setNamespace(params.containsKey(NAME_SPACE_PARAM) ? params.get(NAME_SPACE_PARAM).toString() : "");
		pm.setRepQuery(params.containsKey(REPLACE_PARAMS_PARAM) ? params.get(REPLACE_PARAMS_PARAM).toString() : null);
		pm.setFormatParamMap(parseParamsFromQuery(req,pm.getRepQuery(),pm.getExtQuery()));
		String action = params.containsKey(ACTION_PARAM) ? params.get(ACTION_PARAM).toString() : req.getRequestURI();
		if(action.indexOf("/") != 0){
			action = "/" + action;
		}
		pm.setAction(action);
		pm.setTheme(params.containsKey(THEME_PARAM) ? params.get(THEME_PARAM).toString() : null);
		
		pm.setPageParamName(params.containsKey(PAGE_PARAM_NAME_PARAM) ? params.get(PAGE_PARAM_NAME_PARAM).toString() : pm.getPageParamName());
		return pm;
	}
	
	/**
	 * 从query中获得参数
	 * @param query
	 * @return
	 */
	private Map<String,String[]> parseParamsFromQuery(HttpServletRequest req,String repQuery,String extQuery){
		Map<String,String[]> formatParamMap = req.getParameterMap();
		//解析query
		if(repQuery != null && !"".equals(repQuery)){
			Map<String,List<String>> replaceParamMap = new HashMap<String,List<String>>();
			int ampersandIndex,lastAmpersandIndex = 0,pvIndex;
			String subStr,param,value;
			do{
				ampersandIndex = repQuery.indexOf('&', lastAmpersandIndex)+1;
				if(ampersandIndex>0){
					subStr = repQuery.substring(lastAmpersandIndex, ampersandIndex-1);
					lastAmpersandIndex = ampersandIndex;
				}else{
					subStr = repQuery.substring(lastAmpersandIndex);
				}
				pvIndex = subStr.indexOf("=");
				param = pvIndex>=0 ? subStr.substring(0,pvIndex) : subStr;
				value = pvIndex>=0 ? subStr.substring(pvIndex+1,subStr.length()): "";
				if(!replaceParamMap.containsKey(param))
					replaceParamMap.put(param, new ArrayList<String>());
				replaceParamMap.get(param).add(value);
			}while(ampersandIndex>0);
			if(replaceParamMap.size()>0){
				for (Entry<String,List<String>> obj : replaceParamMap.entrySet()) {
					formatParamMap.put(obj.getKey(), obj.getValue().toArray(new String[0]));
				}
			}
		}
		if(extQuery != null || !"".equals(extQuery)){
			
		}
		return formatParamMap;
	}
	private Map<String,String[]> getFormatParamMap(PageParamsModel pm){
		Map<String,String[]> ret = new HashMap<String, String[]>();
		ret.putAll(pm.getFormatParamMap());
		return ret;
	}
	
	/**
	 * 构建全格式的url
	 * @param req
	 * @param pathUrl
	 * @return
	 */
	private String buildFullUrl(HttpServletRequest req,String pathUrl){
		if(req==null)
			return pathUrl;
		StringBuffer sb = new StringBuffer();
		if(XiuWebConstant.isDevelop()){
			sb.append(req.getScheme()==null ? "http" : req.getScheme())
			.append(XiuWebConstant.SCHEMA_DIV)
			.append(req.getServerName())
			.append(80 == req.getLocalPort() ? "" : ":"+req.getLocalPort())
			.append(pathUrl);
		}else{
			sb.append(XiuWebConstant.HTTP_SCHEMA).append(XiuWebConstant.SCHEMA_DIV)
			.append(req.getServerName())
			.append(pathUrl);
		}
		return sb.toString();
	}
	
	/**
	 * 获取模板
	 * @param hsRequest
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	private Template getTemplateFromConfiguration(ServletContext sc,String fileName) throws IOException{
		if(cfg == null){
			synchronized (this) {
				cfg = new Configuration();
				String dir = sc.getRealPath("/");
				log.info("page ftl configuration set template root dir: \""+dir+"\"");
				cfg.setDefaultEncoding(ENCODE);
				cfg.setDirectoryForTemplateLoading(new File(dir));
			}
		}
		return cfg.getTemplate(fileName);
	}
	
	private final class PageParamsModel{
		private int currentPage;
		private int totalPage;
		private String anchor;
		private String namespace;
		private String action;
		/**
		 * 注，此参数将覆盖原始参数，而不会作为数组传递
		 */
		private String repQuery;
		private Map<String,String[]> formatParamMap;
		private String extQuery;
		private int leftStep = 3;
		private int rightStep = 3;
		private String theme;
		private String pageParamName = "p";
		public int getCurrentPage() {
			return currentPage;
		}
		public void setCurrentPage(int currentPage) {
			this.currentPage = currentPage;
		}
		public int getTotalPage() {
			return totalPage;
		}
		public void setTotalPage(int totalPage) {
			this.totalPage = totalPage;
		}
		public String getAnchor() {
			return anchor;
		}
		public void setAnchor(String anchor) {
			this.anchor = anchor;
		}
		public String getNamespace() {
			return namespace;
		}
		public void setNamespace(String namespace) {
			this.namespace = namespace;
		}
		public String getAction() {
			return action;
		}
		public void setAction(String action) {
			this.action = action;
		}
		public String getRepQuery() {
			return repQuery;
		}
		public void setRepQuery(String repQuery) {
			this.repQuery = repQuery;
		}
		public Map<String, String[]> getFormatParamMap() {
			return formatParamMap;
		}
		public void setFormatParamMap(Map<String, String[]> formatParamMap) {
			this.formatParamMap = formatParamMap;
		}
		public String getExtQuery() {
			return extQuery;
		}
		public void setExtQuery(String extQuery) {
			this.extQuery = extQuery;
		}
		public int getLeftStep() {
			return leftStep;
		}
		public void setLeftStep(int leftStep) {
			this.leftStep = leftStep;
		}
		public int getRightStep() {
			return rightStep;
		}
		public void setRightStep(int rightStep) {
			this.rightStep = rightStep;
		}
		public String getTheme() {
			return theme;
		}
		public void setTheme(String theme) {
			this.theme = theme;
		}
		public String getPageParamName() {
			return pageParamName;
		}
		public void setPageParamName(String pageParamName) {
			this.pageParamName = pageParamName;
		}
	}
}
