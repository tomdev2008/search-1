<!DOCTYPE html>
<html>
<head>
<meta http-equiv="x-ua-compatible" content="ie=7" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>【${brandName!}】官网,价格,图片,网上专卖店-eBay秀</title>
<meta name="keywords" content="${brandName!},${brandName!}网上专卖店,${brandName!}官网,${brandName!}价格,${brandName!}图片"/>
<meta name="description" content="网上购买${brandName!}官网正品，查看${brandName!}商品图片就上ebay秀${brandName!}专卖店，${brandName!}价格特惠，100%专柜正品保证，超低折扣和7天无条件退货保障。"/>
<#include "/views/common/ebay_head_static.ftl" >
<script><#-- init info -->
LEON.init({staticUrl:'${staticUrl}'});
</script>
</head>
<body>
<!-- 公用头部 开始-->
<#include "/views/common/ebay_header_include.ftl" >
<script src="http://www.xiustatic.com/static/js/xiu/index201210/index.head.app.js?__=${xversion('/static/js/xiu/index201210/index.head.app.js')}" type="text/javascript"></script>
<!-- 公用头部 结束-->

<#-- 广告banner -->
<div class="xs-banner-topmid">
<#include "/resources/cms/static/show/search/ebay001.shtml" parse="false">
</div>
<#-- 广告banner 结束 -->

		<#-- 面包屑-->
		<div class="xs-crumb clearfix">
			<label class="hd">您的位置：</label>
            <ul id="J_crumbBar" class="nav">
            	<li class="first"><a href="${ebayUrl}" clkstat="atype=breadclk|stype=brand|sattr=${(brandName!)?url}|bread_cat=${'eBay秀首页'?url}|bread_lv=1">eBay秀首页</a></li>
            	<#if selectCatalogPlaneList??>
            		<#assign clkstat="atype=breadclk|stype=brand|sattr=${(brandName!)?url}">
            		<#list selectCatalogPlaneList as catalog>
            		<li><span><a clkstat="${clkstat}bread_cat=${catalog.catalogName?url}|bread_lv=${catalog_index+1}" href="<@xurl value="/brand-action.htm?bid=${fatParams.brandId?url}&cat=${(catalog.catalogId)!''}&p=${params.p!'1'}" />" alt="${catalog.catalogName}"><#if (catalog.catalogName??)><#if (catalog.catalogName?length>20)>${catalog.catalogName?substring(0,20)}...<#else>${catalog.catalogName}</#if></#if></a></span><i></i></li>
            		</#list>
            	</#if>
            	<li><span class="fb"><a href="<@xurl value="/ebay-brand-action.htm?bid=${(fatParams.brandId)!}&cat=${(fatParams.catalogId)!''}&p=${params.p!'1'}" />">${brandName!}</a></span><i></i></li>
            </ul>
			<div class="totalcount">总共<span class="pl3 pr3 cred">${(page.recordCount)!0}</span>件商品</div>
			<b class="icarr"></b>
		</div>
		<#-- 面包屑结束-->
		
		<#-- 主体部分 -->
		<div class="xs-facetbox">
			<div class="facet-bd">
					<style type="text/css">
						.facet-line .line-hd{left:10px;width:60px;}
						.clearline{width:100%;clear:both;height:1px;line-height:0;font-size:0px;display:block}
						.xs-aside{overflow:hidden;}
					</style>
					<#if selectedCatalogTree?? && selectedCatalogTree.childCatalog?? && selectedCatalogTree.childCatalog?size gt 0>
			        	<#assign urlTemp="/list-action.htm?vm=${(params.vm)!''}&p_code=${(fatParams.providerCode)!''}&s_id=${(s_id)!''}&itemshowtype=${(params.itemshowtype)!''}">
			        	<#assign clkstat="atype=cattree|stype=list|sattr=${selectedCatalogTree.catalogName?url}|clk_cat=${(selectedCatalogTree.catalogName)!?url}"/>
				        <div class="facet-line brand-facet-box fl_box">
				            <label class="line-hd">分类：</label>
				            <div class="line-bd">
				                <div class="normal-item-box clearfix">
				                    <div>
				                        <ul id="J_catSecondItem" class="clearfix">
				                        	<#list selectedCatalogTree.childCatalog as catalog>
				                            	<li><a data-catalogid="${catalog.catalogId}" clkstat="${clkstat!},${catalog.catalogName?url}|cat_lv=2" href="<@xurl value='${urlTemp}&cat=${catalog.catalogId}' />" class="${(catalog.selected )?string('active','')}" >
				                            	${catalog.catalogName?html}
				                            	</a></li>
				                            </#list>
				                        </ul>
				                    </div>
				                    <a class="more-opt hide" href="javascript:void(0);">更多</a>
				                </div>
				            </div>
				            <div class="line-bd">
				            <#list selectedCatalogTree.childCatalog as catalog>
				            	<#if catalog.childCatalog?? && catalog.childCatalog?size gt 0>
				            	<div id="J_catchildbox_${catalog.catalogId}" class="normal-item-box hide clearfix">
				            		<div >
				                        <ul class="clearfix">
					                        <#list catalog.childCatalog as catalog2>
					                            <li><a href="<@xurl value='${urlTemp}&cat=${catalog2.catalogId}'/>">
												<#if (catalog2.catalogName?length>11)>
				                                    ${catalog2.catalogName[0..11]?default("") + "..."}
				                                <#else>
				                                    ${catalog2.catalogName?default("")}
				                                </#if>
												</a></li>
					                        </#list>
				                        </ul>
				                    </div>
				                    </div>
				            	</#if>
				            </#list>
				            </div>
				            <script>
				            	(function(){
				            	var _timeout;
				            	var _currentShowCatId;
				            	$("#J_catSecondItem a").hover(function(){
				            		console.log('1');
				            		window.clearTimeout(_timeout);
				            		var _catid = $(this).data('catalogid');
				            		if(!!_currentShowCatId && _currentShowCatId == _catid){
				            			return;
				            		}
				            		var _box;
				            		if(!!_currentShowCatId){
				            			_box = $("#J_catchildbox_"+_currentShowCatId);
				            			_box.hide();
				            		}
				            		_box = $("#J_catchildbox_"+_catid);
				            		if(!!_box && _box.length>0){
				            			_box.show();
				            			_currentShowCatId = _catid;
				            		}
				            	},function(){
				            		_timeout = window.setTimeout(function(){
				            			if(!!_currentShowCatId){
					            			_box = $("#J_catchildbox_"+_currentShowCatId);
					            			_box.hide();
					            			_currentShowCatId=null;
					            		}
				            		},100);
				            	});
				            	$('div[id^=J_catchildbox_]').hover(function(){
				            		window.clearTimeout(_timeout);
				            	},function(){
				            		_timeout = window.setTimeout(function(){
				            			if(!!_currentShowCatId){
					            			_box = $("#J_catchildbox_"+_currentShowCatId);
					            			_box.hide();
					            			_currentShowCatId=null;
					            		}
				            		},100);
				            	})
				            	})();
				            </script>
				        </div>
					</#if>

			</div>
		</div>
		
		<div class="xs-mainsection clearfix">
	    	<div class="xs-section">
	    		<div id="J_filterbox" class="xs-main">
				     <#-- 搜索无结果内容 -->
			        <div class="xs-nrbox clearfix">
			            <i class="xs-nrico"></i>
			            <dl class="sorry-box">
			                <dd>&nbsp;</dd>
			                <dt>很抱歉，没有找到相关的商品</dt>
			            </dl>
			        </div>
			        <#-- 搜索无结果内容结束 -->
			    </div>
			    <div class="xs-aside">
			    	<#-- 左侧购买排行 -->
			        <div id="banner_PersonalSalesList" class="xs-saleitemslist hide"></div>
			        <div id="banner_PersonalVisitList" class="xs-saleitemslist hide"></div>
			        <#-- 左侧购买排行结束 -->
			    </div>
		    </div>
		</div>
		<#-- 主体部分结束 -->
<#--
<script>
 回到顶部
$(function(){LEON.initToTopBtn({backImg:'static/ebay/css/img/xtop-icon.png'});});
</script>
 -->
<#-- 百分点走秀推荐 -->
<div id="banner_BrowsingHistory" class="xs-salexiurecom-bottombox xs-xiurecombox"></div>
<#--
<script type="text/javascript" src="http://www.baifendian.com/api/js/bfd-jsapi-3.0.min.js"></script>
<script type="text/javascript">
 @001.js
onGetGoodsInfo=function(g,j,f,o){if(!j){return}var q=g.length,b=LEON.bfdData.setting.itemVisitNum;if(q==0){return}var s=o==1?"\u8d2d\u4e70\u6392\u884c":"\u6d4f\u89c8\u6392\u884c";var k=o==1?"buytop":"viewtop";var n=!!LEON.bfdData.setting.stype?LEON.bfdData.setting.stype:"kw";var d=!!LEON.bfdData.setting.sattr?LEON.bfdData.setting.sattr:"";var l="atype="+k+"|stype="+n+"|sattr="+d+"|itemid=";if(o==1){s="\u8d2d\u4e70\u6392\u884c";b=LEON.bfdData.setting.itemSaleNum}var p=0,b=q>b?b:q;var r=$('<div class="hd"><span class="tit">'+s+'</span><span class="tag">Top10</span></div>');var e=$("<div/>").addClass("bd").addClass("clearfix");var c=$("<ul/>").appendTo(e);var a,m,h;do{m=g[p];if(m.length<=3){continue}a=$("<li/>");if(p==b-1){a.addClass("nodot")}h=m[3];if(h.indexOf("_100_100")>0){h=h.replace("_100_100","_80_80")}a.append('<a clkstat="'+l+m[0]+'" class="img" target="_blank" href="'+m[2]+'"><img src="'+h+'"/></a>');a.append('<a clkstat="'+l+m[0]+'" class="tit" target="_blank" href="'+m[2]+'">'+m[1]+"</a>");a.append('<p class="price">'+m[4]+"</p>");c.append(a)}while(++p<b);e.append('<a href="http://www.baifendian.com" class="bfd-click-link " target="_blank"></a>');$(j).append(r).append(e).show();e.clkBindMethod()};onGetGoodsInfo_bh=function(g,h,a){if(!h){return}var j=g.length;if(j==0){return}var l="\u5b98\u65b9\u63a8\u8350";var f="xiuroc";var n=!!LEON.bfdData.setting.stype?LEON.bfdData.setting.stype:"kw";var m=!!LEON.bfdData.setting.sattr?LEON.bfdData.setting.sattr:"";var p="atype="+f+"|stype="+n+"|sattr="+m+"|itemid=";var e=0,c=j>LEON.bfdData.setting.itemHistoryNum?LEON.bfdData.setting.itemHistoryNum:j;var o=$('<div class="hd"><p class="tit">'+l+"</p></div>");var d=$("<div/>").addClass("bd").addClass("clearfix");var q=$("<ul/>").appendTo(d);var b,k;do{k=g[e];if(k.length<=3){continue}b=$("<li/>");if(e==1){b.addClass("first")}if(e==c-1){b.addClass("last")}b.append('<p class="picbox"><a clkstat="'+p+k[0]+'" target="_blank" href="'+k[2]+'"><img src="'+k[3]+'" /></a></p>');b.append('<p class="tit"><a clkstat="'+p+k[0]+'" target="_blank" href="'+k[2]+'">'+k[1]+"</a></p>");b.append('<p class="price xs-ico">'+k[4]+"</p>");q.append(b)}while(++e<c);d.append('<a href="http://www.baifendian.com" class="bfd-click-link " target="_blank"></a>');$(h).append(o).append(d).show()};
LEON.bfdData({stype:'brand',sattr:"${(brandName!)?url}",categoryList:[decodeURIComponent("${(brandName!)?url}")]});
</script>
 -->
<#-- 百分点走秀推荐结束 -->
<#-- CMS页尾 -->
<#include "/resources/cms/static/web/M_footer.html" parse="false">
</body>
</html>