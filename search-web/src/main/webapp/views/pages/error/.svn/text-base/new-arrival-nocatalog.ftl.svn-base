<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>商品分类未找到-走秀网</title>
<meta name="description" content="走秀网是全球时尚在线百货商城零售机构，提供奢侈品特卖，品牌折扣，名品打折；服装、奢侈品、化妆品、手表、珠宝配饰等品牌商品首页，网上购物正品保障，货到付款的网站，7天无条件退换货。" />
<meta name="keywords" content="网上购物,名品打折网,品牌折扣网,奢侈品,LV,巴宝莉,古驰,蔻驰,阿玛尼,路易威登,爱马仕,欧米茄,D&G,施华洛世奇,香奈儿,耐克,阿迪达斯,李宁,ZARA,百丽,gucci,百货商城，货到付款,走秀网,走秀网官网" />
<#include "/views/common/head_static.ftl" >
<script><#-- init info -->
LEON.init({staticUrl:'${staticUrl}'})
</script>
</head>
<body id="index">
<#-- CMS页头 -->
<#include "/views/common/header_include.ftl" >
<script src="http://www.xiustatic.com/static/js/xiu/index201210/index.head.app.js?__=${xversion('/static/js/xiu/index201210/index.head.app.js')}" type="text/javascript"></script>
<#-- 广告banner -->
<div class="xs-banner-topmid">
<#include "/resources/cms/static/show/search/001.shtml" parse="false">
</div>
<#-- 广告banner 结束 -->
		<#-- 面包屑-->
		<div class="xs-crumb clearfix">
	        <label class="hd">您的位置：</label>
            <ul id="J_crumbBar" class="nav">
                <li class="first"><a href="${wwwUrl}" >走秀首页</a></li>
            </ul>
            <div class="totalcount">总共<span class="pl3 pr3 cred">0</span>件商品</div>
	        <b class="icarr "></b>
		</div>
		<#-- 面包屑结束-->
		
		<#-- 主体部分 -->
		<div class="xs-mainsection clearfix">
	    	<div class="xs-section">
	    		<div id="J_filterbox" class="xs-main">
				     <#-- 搜索无结果内容 -->
			        <div class="xs-nrbox clearfix">
			            <i class="xs-nrico"></i>
			             <dl class="sorry-box">
		                    <dd>&nbsp;</dd>
		                    <dt>很抱歉，该分类不存在或已被取消！</dt>
		                    <dd>建议您：</dd>
		                    <dd>1.&nbsp;查看<a class="hl" href="http://www.xiu.com/about/classifymap.shtml">全部分类</a></dd>
		                    <dd>2.&nbsp;点击这里<a class="hl" href="http://www.xiu.com/">回到首页</a></dd>
		                </dl>
			        </div>
			        <#-- 搜索无结果内容结束 -->
			    </div>
			    <div class="xs-aside">
			    	<#-- 左侧购买排行 
			        <div id="banner_PersonalSalesList" class="xs-saleitemslist hide"></div>
			        <div id="banner_PersonalVisitList" class="xs-saleitemslist hide"></div>
			        -->
			        <#-- 左侧购买排行结束 -->
			    </div>
		    </div>
		</div>
		<#-- 主体部分结束 -->
<#-- 底部搜索 -->
<div class="xs-btm-search">
<form id="J_searchFormBottom" action="${searchUrl}s" method="get" autocomplete="off">
    <div class="searchbox">
        <div class="inputbg"><input id="J_searchInputBottom" name="kw" class="txt-input" type="text" value="" /></div>
        <button class="sbtn" type="submit"></button>
    </div>
</form>
</div>

<script>
<#-- @006.js -->
if(typeof LEON=="undefined"){LEON={}}LEON.lastinputBottom=$("#J_searchInputBottom").val();$("#J_searchInputBottom").XAutoSuggest({url:"${searchUrl}ajax/autocomplete.htm?jsoncallback=?",params:{type:"min",mkt:"xiu"},width:480,leftOff:0,result:function(a){if(!!a.oclassId){LEON.lastinputBottom=a.display;$("#J_catalogIdInput").val(a.oclassId).removeAttr("disabled")}$("#J_searchForm").submit()}});
<#-- 回到顶部 -->
$(function(){LEON.initToTopBtn();});
</script>
<#-- 底部搜索结束 -->

<#-- 百分点走秀推荐 -->
<div id="banner_BrowsingHistory" class="tj_box"></div>
<script type="text/javascript" src="http://xres.xiu.com/static/xclk-jsapi.js"></script>
<script type="text/javascript">
<#-- @001.js 
onGetGoodsInfo=function(g,h,a,l){if(!h){return}var j=g.length,c=LEON.bfdData.setting.itemVisitNum;if(j==0){return}var n=l==1?"\u8d2d\u4e70\u6392\u884c":"\u6d4f\u89c8\u6392\u884c";var f=l==1?"buytop":"viewtop";var o=!!LEON.bfdData.setting.stype?LEON.bfdData.setting.stype:"kw";var m=!!LEON.bfdData.setting.sattr?LEON.bfdData.setting.sattr:"";var q="atype="+f+"|stype="+o+"|sattr="+m+"|itemid=";if(l==1){n="\u8d2d\u4e70\u6392\u884c";c=LEON.bfdData.setting.itemSaleNum}var e=0,c=j>c?c:j;var p=$('<div class="hd"><span class="tit">'+n+'</span><span class="tag">Top10</span></div>');var d=$("<div/>").addClass("bd").addClass("clearfix");var r=$("<ul/>").appendTo(d);var b,k;do{k=g[e];if(k.length<=3){continue}b=$("<li/>");if(e==c-1){b.addClass("nodot")}b.append('<a clkstat="'+q+k[0]+'" class="img" href="'+k[2]+'"><img src="'+k[3]+'"/></a>');b.append('<a clkstat="'+q+k[0]+'" class="tit" href="'+k[2]+'">'+k[1]+"</a>");b.append('<p class="price">'+k[4]+"</p>");r.append(b)}while(++e<c);d.append('<a href="http://www.baifendian.com" class="bfd-click-link " target="_blank"></a>');$(h).append(p).append(d).show();d.clkBindMethod()};onGetGoodsInfo_bh=function(g,h,a){if(!h){return}var j=g.length;if(j==0){return}var l="\u5b98\u65b9\u63a8\u8350";var f="xiuroc";var n=!!LEON.bfdData.setting.stype?LEON.bfdData.setting.stype:"kw";var m=!!LEON.bfdData.setting.sattr?LEON.bfdData.setting.sattr:"";var p="atype="+f+"|stype="+n+"|sattr="+m+"|itemid=";var e=0,c=j>LEON.bfdData.setting.itemHistoryNum?LEON.bfdData.setting.itemHistoryNum:j;var o=$('<div class="hd"><p class="tit">'+l+"</p></div>");var d=$("<div/>").addClass("bd").addClass("clearfix");var q=$("<ul/>").appendTo(d);var b,k;do{k=g[e];if(k.length<=3){continue}b=$("<li/>");if(e==1){b.addClass("first")}if(e==c-1){b.addClass("last")}b.append('<p class="picbox"><a clkstat="'+p+k[0]+'" href="'+k[2]+'"><img src="'+k[3]+'" /></a></p>');b.append('<p class="tit"><a clkstat="'+p+k[0]+'" href="'+k[2]+'">'+k[1]+"</a></p>");b.append('<p class="price xs-ico">'+k[4]+"</p>");q.append(b)}while(++e<c);d.append('<a href="http://www.baifendian.com" class="bfd-click-link " target="_blank"></a>');$(h).append(o).append(d).show()};
-->
$(function(){
<#--百分点异步加载@012.js-->
_BFD=window._BFD||{};_BFD.script=document.createElement("script");_BFD.script.setAttribute("src","http://www.xiustatic.com/static/js/thirdparty/bfd/zx_list_search_new.min.js");_BFD.script.setAttribute("type","text/javascript");_BFD.script.setAttribute("charset","utf-8");document.getElementsByTagName("head")[0].appendChild(_BFD.script);

<#--百分点推荐回调函数 -->
function showRecommend_vh(datas,req_id,bfd_id){
	LEON.bfdCreateRecommend(datas,"banner_BrowsingHistory",req_id,bfd_id);		
}
<#-- @abtest判断使用推荐引擎013.js -->
var abtest_ret=$.cookie("abtestRet");var cur_sid=$.cookie("JSESSIONID_WCS");if(abtest_ret&&cur_sid){var abtest_id=abtest_ret.split("|")[0];var ret_id=abtest_ret.split("|")[1];if(abtest_id===cur_sid){if(ret_id==="1"){var ab_id=18;LEON.bfdData({stype:"list",sattr:"${((selectedCatalog.catalogName)!'')?url}",categoryList:["${(selectedCatalog.catalogName)!'all'}"],type:"list",catId:"<#if ((fatParams??)&&(fatParams.catalogId??)&&(fatParams.catalogId>0))>${fatParams.catalogId!}<#else>${recommendCatIds!}</#if>",bId:"${(fatParams.brandId)!}",hasResult:false,clientName:"${bfdClientName!}"})}else{var ab_id=19;LEON.xreData({hasResult:false,clientName:"${bfdClientName!}",type:"list",brandId:"${(recommenBrandIds)!}",catId:"${recommendCatIds!}",itemId:"${recommendItemIds!}",stype:"list",sattr:"${((selectedCatalog.catalogName)!'')?url}",siteId:"1001",channelId:"1001"})}XIU.Window.ctraceClick("atype=exposuret|abproject_id=8|abitem_id=8|abverid="+ab_id,"recommend_engine")}else{recommendItem()}}else{recommendItem()}function recommendItem(){var a=18;$.ajax({url:"http://abtest.xiu.com/GetScriptJsonpServlet.js?_pid=8",dataType:"jsonp",jsonp:"callbackparam",success:function(e){var d=[];var f=$.cookie("JSESSIONID_WCS");d.push(f);d.push(e.name.substring(0,1));$.cookie("abtestRet",d.join("|"),{expires:30*3,path:"/",domain:".xiu.com"});try{if(e.name.substring(0,1)=="1"){LEON.bfdData({stype:"list",sattr:"${((selectedCatalog.catalogName)!'')?url}",categoryList:["${(selectedCatalog.catalogName)!'all'}"],type:"list",catId:"<#if ((fatParams??)&&(fatParams.catalogId??)&&(fatParams.catalogId>0))>${fatParams.catalogId!}<#else>${recommendCatIds!}</#if>",bId:"${(fatParams.brandId)!}",hasResult:false,clientName:"${bfdClientName!}"})}else{a=19;LEON.xreData({hasResult:false,clientName:"${bfdClientName!}",type:"list",brandId:"${(recommenBrandIds)!}",catId:"${recommendCatIds!}",itemId:"${recommendItemIds!}",stype:"list",sattr:"${((selectedCatalog.catalogName)!'')?url}",siteId:"1001",channelId:"1001"})}}catch(c){LEON.bfdData({stype:"list",sattr:"${((selectedCatalog.catalogName)!'')?url}",categoryList:["${(selectedCatalog.catalogName)!'all'}"],type:"list",catId:"<#if ((fatParams??)&&(fatParams.catalogId??)&&(fatParams.catalogId>0))>${fatParams.catalogId!}<#else>${recommendCatIds!}</#if>",bId:"${(fatParams.brandId)!}",hasResult:false,clientName:"${bfdClientName!}"})}},error:function(){LEON.bfdData({stype:"list",sattr:"${(selectedCatalog.catalogName)!?url}",categoryList:["${(selectedCatalog.catalogName)!'all'}"],type:"list",catId:"<#if ((fatParams??)&&(fatParams.catalogId??)&&(fatParams.catalogId>0))>${fatParams.catalogId!}<#else>${recommendCatIds!}</#if>",bId:"${(fatParams.brandId)!}",hasResult:false,clientName:"${bfdClientName!}"})}});XIU.Window.ctraceClick("atype=exposuret|abproject_id=8|abitem_id=8|abverid="+a,"recommend_engine")};
});
</script>
<#-- 百分点走秀推荐结束 -->
<#-- CMS页尾 -->
<#include "/resources/cms/static/web/M_footer.html" parse="false">
<#include "/views/common/bottom_static.ftl" >
</body>
</html>