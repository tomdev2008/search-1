package com.xiu.search.web.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tuckey.web.filters.urlrewrite.UrlRewriteWrappedResponse;
import org.tuckey.web.filters.urlrewrite.UrlRewriter;

import com.xiu.search.web.config.XiuWebConstant;

/**
 * 老的url请求301到新url过滤器
 * 
 * @author Leon
 * 
 */
public class OrigUrlRedirectFilter extends XUrlRewriteFilter implements Filter {

	private final static String NEW_PARAM_KW = "kw";
	private final static String NEW_PARAM_CAT = "cat";
	private final static String NEW_PARAM_SORT = "sort";
	private final static String NEW_PARAM_BID = "bid";
	private final static String NEW_PARAM_F_PRICE = "f_price";
	private final static String NEW_PARAM_FILTER = "filter";
	private final static String NEW_PARAM_P = "p";
	private final static String NEW_PARAM_S_PRICE = "s_price";
	private final static String NEW_PARAM_E_PRICE = "e_price";
	private final static String NEW_PARAM_VM = "vm";
	private final static String NEW_PARAM_BA = "ba";
	private final static String NEW_PARAM_P_CODE = "p_code";
	private final static String NEW_PARAM_MKT = "mkt";

	private final static String OLD_PARAM_KW = "kw";
	private final static String OLD_PARAM_ORDER_BY = "orderBy";
	private final static String OLD_PARAM_PAGE_VIEW = "pageView";
	private final static String OLD_PARAM_BUY_ABLE = "buyAble";
	private final static String OLD_PARAM_BRAND_ID = "brandid";
	private final static String OLD_PARAM_SEARCH_TERM = "searchTerm";
	private final static String OLD_PARAM_FACET = "facet";
	private final static String OLD_PARAM_P_CODE = "p_code";
	private final static String OLD_PARAM_MKT = "mkt";
	private final static String OLD_PARAM_S_PRICE = "minPrice";
	private final static String OLD_PARAM_E_PRICE = "maxPrice";

	private final static char EQ_SIGN = '=';
	private final static char QUESTION_SIGN = '?';
	private final static char AND_SIGN = '&';

	private final static String LIST_PATH_REGEX = "^/[0-9]+\\.html$";
	private final static String BRAND_PATH_REGEX = "^/[0-9]+\\.html$";
	private final static String SEARCH_ACTION = "/search-action.htm";
	private final static String SEARCH_HOST = "http://search.xiu.com/s";
	private final static String LIST_ACTION = "/list-action.htm";
	private final static String BRAND_ACTION = "/brand-action.htm";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		UrlRewriter urlRewriter = getUrlRewriter(request, response, chain);

		final HttpServletRequest hsRequest = (HttpServletRequest) request;
		final HttpServletResponse hsResponse = (HttpServletResponse) response;
		UrlRewriteWrappedResponse urlRewriteWrappedResponse = new XUrlRewriteWrappedResponse(
				hsResponse, hsRequest, urlRewriter);

		HttpServletResponse res = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;
		String serverName = request.getServerName();
		Map<String, String[]> params = req.getParameterMap();
		String path = req.getServletPath();
		String redirectUrl;
		if (XiuWebConstant.SEARCH_URL.equals(serverName)
				&& "/s".equalsIgnoreCase(path)) {
			if (!params.containsKey(NEW_PARAM_KW)) {
				chain.doFilter(request, response);
				return;
			}
			if (params.containsKey(OLD_PARAM_BUY_ABLE)
					|| params.containsKey(OLD_PARAM_ORDER_BY)
					|| params.containsKey(OLD_PARAM_PAGE_VIEW)) {
				redirectUrl = buildNewSearchUrl(params.get(NEW_PARAM_KW)[0],
						null, null, req, res);
				this.redirect(res, redirectUrl);
				return;
			}
		} else if (XiuWebConstant.LIST_URL.equals(serverName)
				&& path.matches(LIST_PATH_REGEX)) {
			if (params.containsKey(OLD_PARAM_SEARCH_TERM)
					&& !"".equals(params.get(OLD_PARAM_SEARCH_TERM)[0])) {
				redirectUrl = buildNewSearchUrl(
						params.get(OLD_PARAM_SEARCH_TERM)[0],
						getListOrBrandId(path), null, req, res);
				this.redirect(res, redirectUrl);
				return;
			}
			if (params.containsKey(OLD_PARAM_BRAND_ID)
					|| params.containsKey(OLD_PARAM_BUY_ABLE)
					|| params.containsKey(OLD_PARAM_ORDER_BY)
					|| params.containsKey(OLD_PARAM_PAGE_VIEW)) {
				redirectUrl = buildListUrl(getListOrBrandId(path), req, res);
				redirectUrl = urlRewriteWrappedResponse.encodeURL(redirectUrl);
				this.redirect(res, redirectUrl);
				return;
			}
		} else if (XiuWebConstant.BRAND_URL.equals(serverName)
				&& path.matches(BRAND_PATH_REGEX)) {
			if (params.containsKey(OLD_PARAM_SEARCH_TERM)
					&& !"".equals(params.get(OLD_PARAM_SEARCH_TERM)[0])) {
				redirectUrl = buildNewSearchUrl(
						params.get(OLD_PARAM_SEARCH_TERM)[0], null,
						getListOrBrandId(path), req, res);
				this.redirect(res, redirectUrl);
				return;
			}
			if (params.containsKey(OLD_PARAM_BUY_ABLE)
					|| params.containsKey(OLD_PARAM_ORDER_BY)
					|| params.containsKey(OLD_PARAM_PAGE_VIEW)) {
				redirectUrl = buildBrandUrl(getListOrBrandId(path), req, res);
				redirectUrl = urlRewriteWrappedResponse.encodeURL(redirectUrl);
				this.redirect(res, redirectUrl);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	private String buildBrandUrl(String brandId, HttpServletRequest req,
			HttpServletResponse res) {
		StringBuffer sb = new StringBuffer();
		Map<String, String[]> params = req.getParameterMap();
		sb.append(BRAND_ACTION).append(QUESTION_SIGN).append(NEW_PARAM_BID)
				.append(EQ_SIGN).append(brandId);
		if (params.containsKey(OLD_PARAM_BUY_ABLE)
				&& !"".equals(params.get(OLD_PARAM_BUY_ABLE)[0]))
			sb.append(AND_SIGN).append(NEW_PARAM_BA).append(EQ_SIGN)
					.append(params.get(OLD_PARAM_BUY_ABLE)[0]);
		if (params.containsKey(OLD_PARAM_ORDER_BY)
				&& !"".equals(params.get(OLD_PARAM_ORDER_BY)[0]))
			sb.append(AND_SIGN).append(NEW_PARAM_SORT).append(EQ_SIGN)
					.append(params.get(OLD_PARAM_ORDER_BY)[0]);
		if (params.containsKey(OLD_PARAM_PAGE_VIEW)
				&& !"".equals(params.get(OLD_PARAM_PAGE_VIEW)[0]))
			sb.append(AND_SIGN)
					.append(NEW_PARAM_VM)
					.append(EQ_SIGN)
					.append(this.parseViewModel(params.get(OLD_PARAM_PAGE_VIEW)[0]));
		if (params.containsKey(OLD_PARAM_FACET))
			sb.append(AND_SIGN)
					.append(NEW_PARAM_F_PRICE)
					.append(EQ_SIGN)
					.append(this.parsePriceRangeFilter(params
							.get(OLD_PARAM_FACET)[0]));
		if (params.containsKey(OLD_PARAM_P_CODE)
				&& !"".equals(params.get(OLD_PARAM_P_CODE)[0]))
			sb.append(AND_SIGN).append(NEW_PARAM_P_CODE).append(EQ_SIGN)
					.append(params.get(NEW_PARAM_P_CODE)[0]);
		if (params.containsKey(OLD_PARAM_MKT)
				&& !"".equals(params.get(OLD_PARAM_MKT)[0]))
			sb.append(AND_SIGN).append(NEW_PARAM_MKT).append(EQ_SIGN)
					.append(params.get(NEW_PARAM_MKT)[0]);
		if (params.containsKey(OLD_PARAM_S_PRICE)
				&& !"".equals(params.get(OLD_PARAM_S_PRICE)[0]))
			sb.append(AND_SIGN).append(NEW_PARAM_S_PRICE).append(EQ_SIGN)
					.append(params.get(OLD_PARAM_S_PRICE)[0]);
		if (params.containsKey(OLD_PARAM_E_PRICE)
				&& !"".equals(params.get(OLD_PARAM_E_PRICE)[0]))
			sb.append(AND_SIGN).append(NEW_PARAM_E_PRICE).append(EQ_SIGN)
					.append(params.get(OLD_PARAM_E_PRICE)[0]);

		sb.append(AND_SIGN).append(NEW_PARAM_P).append(EQ_SIGN).append(1);
		return sb.toString();
	}

	private String buildListUrl(String catalogId, HttpServletRequest req,
			HttpServletResponse res) {
		StringBuffer sb = new StringBuffer();
		Map<String, String[]> params = req.getParameterMap();
		sb.append(LIST_ACTION).append(QUESTION_SIGN).append(NEW_PARAM_CAT)
				.append(EQ_SIGN).append(catalogId);
		if (params.containsKey(OLD_PARAM_BRAND_ID)
				&& !"".equals(params.get(OLD_PARAM_BRAND_ID)[0]))
			sb.append(AND_SIGN).append(NEW_PARAM_BID).append(EQ_SIGN)
					.append(params.get(OLD_PARAM_BRAND_ID)[0]);
		if (params.containsKey(OLD_PARAM_BUY_ABLE)
				&& !"".equals(params.get(OLD_PARAM_BUY_ABLE)[0]))
			sb.append(AND_SIGN).append(NEW_PARAM_BA).append(EQ_SIGN)
					.append(params.get(OLD_PARAM_BUY_ABLE)[0]);
		if (params.containsKey(OLD_PARAM_ORDER_BY)
				&& !"".equals(params.get(OLD_PARAM_ORDER_BY)[0]))
			sb.append(AND_SIGN).append(NEW_PARAM_SORT).append(EQ_SIGN)
					.append(params.get(OLD_PARAM_ORDER_BY)[0]);
		if (params.containsKey(OLD_PARAM_PAGE_VIEW)
				&& !"".equals(params.get(OLD_PARAM_PAGE_VIEW)[0]))
			sb.append(AND_SIGN)
					.append(NEW_PARAM_VM)
					.append(EQ_SIGN)
					.append(this.parseViewModel(params.get(OLD_PARAM_PAGE_VIEW)[0]));
		if (params.containsKey(OLD_PARAM_FACET))
			sb.append(AND_SIGN)
					.append(NEW_PARAM_F_PRICE)
					.append(EQ_SIGN)
					.append(this.parsePriceRangeFilter(params
							.get(OLD_PARAM_FACET)[0]));
		if (params.containsKey(OLD_PARAM_P_CODE)
				&& !"".equals(params.get(OLD_PARAM_P_CODE)[0]))
			sb.append(AND_SIGN).append(NEW_PARAM_P_CODE).append(EQ_SIGN)
					.append(params.get(NEW_PARAM_P_CODE)[0]);
		if (params.containsKey(OLD_PARAM_MKT)
				&& !"".equals(params.get(OLD_PARAM_MKT)[0]))
			sb.append(AND_SIGN).append(NEW_PARAM_MKT).append(EQ_SIGN)
					.append(params.get(NEW_PARAM_MKT)[0]);
		if (params.containsKey(OLD_PARAM_S_PRICE)
				&& !"".equals(params.get(OLD_PARAM_S_PRICE)[0]))
			sb.append(AND_SIGN).append(NEW_PARAM_S_PRICE).append(EQ_SIGN)
					.append(params.get(OLD_PARAM_S_PRICE)[0]);
		if (params.containsKey(OLD_PARAM_E_PRICE)
				&& !"".equals(params.get(OLD_PARAM_E_PRICE)[0]))
			sb.append(AND_SIGN).append(NEW_PARAM_E_PRICE).append(EQ_SIGN)
					.append(params.get(OLD_PARAM_E_PRICE)[0]);

		sb.append(AND_SIGN).append(NEW_PARAM_P).append(EQ_SIGN).append(1);
		return sb.toString();
	}

	private String getListOrBrandId(String path) {
		return path.substring(1, path.length() - 5);
	}

	/**
	 * 构建新的query
	 * 
	 * @param kw
	 * @param req
	 * @param res
	 * @return
	 */
	private String buildNewSearchUrl(String kw, String catId, String brandId,
			HttpServletRequest req, HttpServletResponse res) {
		StringBuffer sb = new StringBuffer();
		Map<String, String[]> params = req.getParameterMap();
		sb.append(SEARCH_HOST).append(QUESTION_SIGN);
		try {
			sb.append(NEW_PARAM_KW).append(EQ_SIGN)
					.append(URLEncoder.encode(kw, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (null != catId)
			sb.append(AND_SIGN).append(NEW_PARAM_CAT).append(EQ_SIGN)
					.append(catId);
		if (null != brandId)
			sb.append(AND_SIGN).append(NEW_PARAM_BID).append(EQ_SIGN)
					.append(brandId);
		if (params.containsKey(OLD_PARAM_BUY_ABLE)
				&& !"".equals(params.get(OLD_PARAM_BUY_ABLE)[0]))
			sb.append(AND_SIGN).append(NEW_PARAM_BA).append(EQ_SIGN)
					.append(params.get(OLD_PARAM_BUY_ABLE)[0]);
		if (params.containsKey(OLD_PARAM_ORDER_BY)
				&& !"".equals(params.get(OLD_PARAM_ORDER_BY)[0]))
			sb.append(AND_SIGN).append(NEW_PARAM_SORT).append(EQ_SIGN)
					.append(params.get(OLD_PARAM_ORDER_BY)[0]);
		if (params.containsKey(OLD_PARAM_PAGE_VIEW)
				&& !"".equals(params.get(OLD_PARAM_PAGE_VIEW)[0]))
			sb.append(AND_SIGN)
					.append(NEW_PARAM_VM)
					.append(EQ_SIGN)
					.append(this.parseViewModel(params.get(OLD_PARAM_PAGE_VIEW)[0]));
		if (params.containsKey(OLD_PARAM_MKT)
				&& !"".equals(params.get(OLD_PARAM_MKT)[0]))
			sb.append(AND_SIGN).append(NEW_PARAM_MKT).append(EQ_SIGN)
					.append(params.get(OLD_PARAM_MKT)[0]);
		if (params.containsKey(OLD_PARAM_P_CODE)
				&& !"".equals(params.get(OLD_PARAM_P_CODE)[0]))
			sb.append(AND_SIGN).append(NEW_PARAM_P_CODE).append(EQ_SIGN)
					.append(params.get(OLD_PARAM_P_CODE)[0]);

		if (params.containsKey(OLD_PARAM_S_PRICE)
				&& !"".equals(params.get(OLD_PARAM_S_PRICE)[0]))
			sb.append(AND_SIGN).append(NEW_PARAM_S_PRICE).append(EQ_SIGN)
					.append(params.get(OLD_PARAM_S_PRICE)[0]);
		if (params.containsKey(OLD_PARAM_E_PRICE)
				&& !"".equals(params.get(OLD_PARAM_E_PRICE)[0]))
			sb.append(AND_SIGN).append(NEW_PARAM_E_PRICE).append(EQ_SIGN)
					.append(params.get(OLD_PARAM_E_PRICE)[0]);

		if (params.containsKey(OLD_PARAM_BRAND_ID)
				&& !"".equals(params.get(OLD_PARAM_BRAND_ID)[0]))
			sb.append(AND_SIGN).append(NEW_PARAM_BID).append(EQ_SIGN)
					.append(params.get(OLD_PARAM_BRAND_ID)[0]);

		return sb.toString();
	}

	private String parsePriceRangeFilter(String facetQuery) {
		if (null == facetQuery || "".equals(facetQuery))
			return "";
		if (facetQuery.indexOf("priceFinal:{0 TO 500}") >= 0)
			return "1";
		if (facetQuery.indexOf("priceFinal:{500 TO 1000}") >= 0)
			return "2";
		if (facetQuery.indexOf("priceFinal:{1000 TO 3000}") >= 0)
			return "3";
		if (facetQuery.indexOf("priceFinal:{3000 TO 5000}") >= 0)
			return "4";
		if (facetQuery.indexOf("priceFinal:{5000 TO 8000}") >= 0)
			return "5";
		if (facetQuery.indexOf("priceFinal:{8000 TO *}") >= 0)
			return "6";
		return "";
	}

	private String parseViewModel(String vm) {
		if ("detail".equalsIgnoreCase(vm))
			return "1";
		return "";
	}

	private void redirect(HttpServletResponse res, String redirectUrl) {
		res.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		res.setHeader("Location", redirectUrl);
		res.setHeader("Connection", "close");
	}

	/*public static void main(HttpServletRequest req) {
		System.out.println(req.getRequestURI());
		System.out.println(req.getRequestURL().toString());
		System.out.println(req.getContextPath());
		System.out.println(req.getLocalAddr());
		System.out.println(req.getLocalName());
		System.out.println(req.getLocalPort());
		System.out.println(req.getMethod());
		System.out.println(req.getPathInfo());
		System.out.println(req.getServletPath());
		System.out.println(req.getAuthType());
		System.out.println(req.getCharacterEncoding());
		System.out.println(req.getContentLength());
		System.out.println(req.getContentType());
		System.out.println(req.getServerPort());
		System.out.println(req.getContentType());
	}*/
}
