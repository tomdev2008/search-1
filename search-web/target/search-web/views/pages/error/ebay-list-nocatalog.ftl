<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>商品分类未找到,请点击导航进入其他分类-eBay秀</title>
<meta name="description" content="商品分类未找到,请点击导航进入其他分类或返回首页,eBay秀 奢侈品特卖,正品保障" />
<meta name="keywords" content="eBay秀" />
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
                <li class="first"><a href="${ebayUrl}" >eBay秀首页</a></li>
            </ul>
            <div class="totalcount">总共<span class="pl3 pr3 cred">0</span>件商品</div>
            <b class="icarr "></b>
		</div>
		<#-- 面包屑结束-->
		
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
		                    <dd>1.&nbsp;点击这里<a class="hl" href="${ebayUrl}">回到eBay秀首页</a></dd>
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
LEON.bfdData({stype:'list',sattr:'',categoryList:["all"]});
</script>
-->
<#-- 百分点走秀推荐结束 -->
<#-- CMS页尾 -->
<#include "/resources/cms/ebay/footer.html" parse="false">
<#include "/views/common/ebay_bottom_static.ftl" >
</body>
</html>