<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="ie=7" />
<meta name="description" content="ebay秀，全球品牌网络旗舰店，中国第一大国际时尚品牌线上零售机构。一折起销售正品国际品牌女装、男装、奢侈品、化妆品、运动、休闲、箱包、鞋、家居、配饰等商品，7天无条件退货保障。" />
<meta name="keywords" content="网上购物,网上商城,货到付款,名品打折网,奢侈品,LV,GUCCI,PRADA,CK,兰蔻,雅诗兰黛,倩碧,耐克,阿迪达斯,李宁,ZARA,百丽,ebay秀" />
<title>【${((params.kw)!'')?html}】 - 商品搜索 -eBay秀</title>
<#include "/views/common/ebay_head_static.ftl" >
</head>
<body>
<#-- CMS页头 -->
<#include "/views/common/ebay_header_include.ftl" >
<script src="http://www.xiustatic.com/static/js/xiu/index201210/index.head.app.js?__=${xversion('/static/js/xiu/index201210/index.head.app.js')}" type="text/javascript"></script>
<#-- 广告banner -->
<div class="xs-banner-topmid">
<#include "/resources/cms/static/show/search/ebay001.shtml" parse="false">
</div>
<#-- 广告banner 结束 -->
		<#-- 面包屑-->
		<#if params?? && params.kw?? && params.kw?length gt 50>
			<#assign showKw="${(params.kw?substring(50))?html}" />
		<#else>
			<#assign showKw="${((params.kw)!'')?html}" />
		</#if>
		<div class="xs-crumb clearfix">
	        <label class="hd">您的位置：</label>
	        <ul class="nav">
	            <li class="first"><span><a href="${ebayUrl}">eBay秀首页</a></span></li>
	            <li><span class="fb">"${showKw}"</span><i></i></li>
	        </ul>
	        <div class="totalcount">总共<span class="pl3 pr3 cred">0</span>件商品</div>
	        <b class="icarr "></b>
		</div>
		<#-- 面包屑结束-->
		
		<#-- 主体部分 -->
		<div class="xs-facetbox">
	        <#-- 搜索无结果内容 -->
	        <div class="xs-nrbox clearfix">
	            <i class="xs-nrico" style="top:90px"></i>
	            <dl class="sorry-box" style="padding:80px 20px 80px 200px">
	                <dt>很抱歉，没有找到符合条件的商品！！</dt>
	                <dd>建议您：</dd>
	                <dd>1.&nbsp;看看输入文字是否有误</dd>
	                <dd>2.&nbsp;适当减少筛选条件，可以获得更多结果</dd>
	                <dd>3.&nbsp;调整价格区间</dd>
	                <dd>4.&nbsp;去掉不需要的字词，如"的"，"什么等"</dd>
	                <dd>5.&nbsp;调整关键字，如"nikeaf1运动鞋"改成"nike af1 运动鞋"或"nike af1"</dd>
	            </dl>
	        </div>
	        <#-- 搜索无结果内容结束 -->
	        <#-- 百分点走秀推荐 -->
	        <div id="banner_BrowsingHistory" class="xs-nr-xiurecombox xs-xiurecombox"></div>
	        <#-- 百分点走秀推荐结束 -->


		</div>
		<#-- 主体部分结束 -->

<script type="text/javascript" src="http://xres.xiu.com/static/xclk-jsapi.js"></script>
<script type="text/javascript">
(function(){
<#--推荐引擎数据收集-->
LEON.xreDataCollect({siteId:1001,channelId:1002,kw:"${((params.kw)!'')?url}",result:0});
});
</script>
<#-- CMS页尾 -->
<#include "/resources/cms/static/web/M_footer.html" parse="false">
</body>
</html>
