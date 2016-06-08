package com.xiu.search.web.ext.freemarker;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.RequestContext;

import com.xiu.search.web.config.XiuWebConstant;

import freemarker.core.Environment;
import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelAdapter;
import freemarker.template.TemplateModelException;

public class RewriteURLMethod implements TemplateDirectiveModel{

	private static Logger log = LoggerFactory.getLogger(RewriteURLMethod.class);
	
	private static final String VALUE_PARAM = "value";
	private static final String AUTHOR_PARAM="author";
	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		if(params.isEmpty() || !params.containsKey(VALUE_PARAM)){
			throw new TemplateModelException("The param \"value\" is required.");
		}
		if(loopVars.length>0){
			throw new TemplateModelException("This directive doesn't allow loop variables.");
		}
		if(body != null){
			throw new TemplateModelException("This directive doesn't need body.");
		}
		RequestContext rc = (RequestContext)env.__getitem__("springMacroRequestContext");
		String _value = String.valueOf(params.get(VALUE_PARAM));
		String _author = params.containsKey(AUTHOR_PARAM) ? String.valueOf(params.get(AUTHOR_PARAM)) : null;
		if(null == rc){
			_value = null == _author ? _value : _value+"#"+_author;
		}else{
			_value = null == _author ? rc.getContextUrl(_value) : rc.getContextUrl(_value) + "#" + _author;
		}
		HttpServletRequest req = null;
//		HttpServletResponse res = null;
		TemplateModelAdapter  hrp = (TemplateModelAdapter)env.__getitem__("Request");
		if(null != hrp){
			HttpRequestHashModel tm = (HttpRequestHashModel)hrp.getTemplateModel();
			if(null != tm){
				req = tm.getRequest();
//				res = tm.getResponse();
				if(null != req){
					_value = buildFullUrl(req, _value);
				}
			}
		}
		env.getOut().write(_value);
	}
	
	
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

	
}
