<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>
<#if selectedCatalog??>
【${selectedCatalog.catalogName?html}】
</#if>
价格,款式,购买查询-eBay秀</title>
<meta name="description" content="ebay秀是全球时尚在线百货商城零售机构，提供奢侈品特卖，品牌折扣，名品打折；服装、奢侈品、化妆品、手表、珠宝配饰等品牌商品首页，网上购物正品保障，7天无条件退货。" />
<meta name="keywords" content="ebay秀" />
<#include "/views/common/ebay_head_static.ftl" >
<script><#-- init info -->
LEON.init({staticUrl:'${staticUrl}'})
</script>
</head>
<body id="index">
<#-- CMS页头 -->
<#include "/views/common/ebay_header_include.ftl" >
<script src="http://www.xiustatic.com/static/js/xiu/index201210/index.head.app.js?__=${xversion('/static/js/xiu/index201210/index.head.app.js')}" type="text/javascript"></script>
<#-- 广告banner -->
<div class="xs-banner-topmid">
<#include "/resources/cms/static/show/search/ebay001.shtml" parse="false">
</div>
<#-- 广告banner 结束 -->
	<#-- 面包屑-->
	<div class="xs-crumb clearfix">
		<label class="hd">您的位置：</label>
        <ul id="J_crumbBar" class="nav">
        	<#if selectedCatalog??>
        	<li class="first"><a href="${ebayUrl}" clkstat="atype=breadclk|stype=list|sattr=${selectedCatalog.catalogName?url}|bread_cat=${'eBay秀首页'?url}|bread_lv=1">eBay秀首页</a></li>
        	<#else>
        	<li class="first"><a href="${ebayUrl}" clkstat="atype=breadclk|stype=list|sattr=|bread_cat=${'eBay秀首页'?url}|bread_lv=1">eBay秀首页</a></li>
        	</#if>
            
            <#if selectCatalogPlaneList??>
            	<#assign clkstat="atype=breadclk|stype=list|sattr=${((selectedCatalog.catalogName)!'')?url}"/>
                <#list selectCatalogPlaneList as catalog>
                <li><span><a clkstat="${clkstat}bread_cat=${catalog.catalogName?url}|bread_lv=${catalog_index+1}" href="<@xurl  value="/ebay-list-action.htm?cat=${catalog.catalogId}"/>" alt="${catalog.catalogName!}"><#if (catalog.catalogName??)><#if (catalog.catalogName?length>20)>${catalog.catalogName?substring(0,20)}...<#else>${catalog.catalogName}</#if></#if></a></span><i></i></li>
                </#list>
            </#if>
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
<#-- 百分点走秀推荐 -->
<div id="banner_BrowsingHistory" class="xs-salexiurecom-bottombox xs-xiurecombox hide"></div>
<#--
<script type="text/javascript" src="http://www.baifendian.com/api/js/bfd-jsapi-3.0.min.js"></script>
<script type="text/javascript">
 @001.js
onGetGoodsInfo=function(g,h,a,l){if(!h){return}var j=g.length,c=LEON.bfdData.setting.itemVisitNum;if(j==0){return}var n=l==1?"\u8d2d\u4e70\u6392\u884c":"\u6d4f\u89c8\u6392\u884c";var f=l==1?"buytop":"viewtop";var o=!!LEON.bfdData.setting.stype?LEON.bfdData.setting.stype:"kw";var m=!!LEON.bfdData.setting.sattr?LEON.bfdData.setting.sattr:"";var q="atype="+f+"|stype="+o+"|sattr="+m+"|itemid=";if(l==1){n="\u8d2d\u4e70\u6392\u884c";c=LEON.bfdData.setting.itemSaleNum}var e=0,c=j>c?c:j;var p=$('<div class="hd"><span class="tit">'+n+'</span><span class="tag">Top10</span></div>');var d=$("<div/>").addClass("bd").addClass("clearfix");var r=$("<ul/>").appendTo(d);var b,k;do{k=g[e];if(k.length<=3){continue}b=$("<li/>");if(e==c-1){b.addClass("nodot")}b.append('<a clkstat="'+q+k[0]+'" class="img" href="'+k[2]+'"><img src="'+k[3]+'"/></a>');b.append('<a clkstat="'+q+k[0]+'" class="tit" href="'+k[2]+'">'+k[1]+"</a>");b.append('<p class="price">'+k[4]+"</p>");r.append(b)}while(++e<c);d.append('<a href="http://www.baifendian.com" class="bfd-click-link " target="_blank"></a>');$(h).append(p).append(d).show();d.clkBindMethod()};onGetGoodsInfo_bh=function(g,h,a){if(!h){return}var j=g.length;if(j==0){return}var l="\u5b98\u65b9\u63a8\u8350";var f="xiuroc";var n=!!LEON.bfdData.setting.stype?LEON.bfdData.setting.stype:"kw";var m=!!LEON.bfdData.setting.sattr?LEON.bfdData.setting.sattr:"";var p="atype="+f+"|stype="+n+"|sattr="+m+"|itemid=";var e=0,c=j>LEON.bfdData.setting.itemHistoryNum?LEON.bfdData.setting.itemHistoryNum:j;var o=$('<div class="hd"><p class="tit">'+l+"</p></div>");var d=$("<div/>").addClass("bd").addClass("clearfix");var q=$("<ul/>").appendTo(d);var b,k;do{k=g[e];if(k.length<=3){continue}b=$("<li/>");if(e==1){b.addClass("first")}if(e==c-1){b.addClass("last")}b.append('<p class="picbox"><a clkstat="'+p+k[0]+'" href="'+k[2]+'"><img src="'+k[3]+'" /></a></p>');b.append('<p class="tit"><a clkstat="'+p+k[0]+'" href="'+k[2]+'">'+k[1]+"</a></p>");b.append('<p class="price xs-ico">'+k[4]+"</p>");q.append(b)}while(++e<c);d.append('<a href="http://www.baifendian.com" class="bfd-click-link " target="_blank"></a>');$(h).append(o).append(d).show()};
LEON.bfdData({stype:'list',sattr:'${selectedCatalog.catalogName?url}',categoryList:["${(selectedCatalog.catalogName)!'all'}"]});
</script>
 -->
<#-- 百分点走秀推荐结束 -->
<#-- CMS页尾 -->
<#include "/resources/cms/static/web/M_footer.html" parse="false">
</body>
</html>