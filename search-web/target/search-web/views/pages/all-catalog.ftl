<!doctype html>
<html>
<head>
    <title>走秀网-时尚百货网上商城网购首选！</title>
    <meta name="description" content="走秀网全球时尚正品百货网上商城,名品特卖,限时抢购,奢侈品特卖,女装,男装,手表,包包,化妆品,珠宝配饰等数万个国际品牌千万优质商品，诚信优质服务。"/>
    <meta name="keywords" content=""/>
    
    <link href="${staticUrl}static/css/default/about.css" rel="stylesheet" type="text/css" />
    <#include "/views/common/head_static.ftl" >    
   
    <script><#-- init info -->
    $(document).ready(function(){$(".pic img").lazyload({placeholder:"http://www.xiu.com/static/img/default/default.200_200.jpg"});});
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

<div id="bd">
  <div class="xbox" style="background:#fff;margin-top:0;">
    <div class="content clearfix">
      <div class="maincol sitemap" style="width:950px;">
        <h2></h2>				
<#-- 遍历一级分类 -->
<#if (params)?? >    
<#list params as level1>
    <h3 class="color1"><a href="<@xurl value="/list-action.htm?cat=${level1.catalogId}" />" target="_blank">${level1.catalogName}</a></h3>
    <#-- 遍历二级分类 -->
    <#if (level1.childCatalog)?? >
    <#list level1.childCatalog as level2> 
         <dl><dt><a href="<@xurl value="/list-action.htm?cat=${level2.catalogId}" />" class="title_1" target="_blank">${level2.catalogName}</a></dt>
        <#-- 遍历三级分类 -->
        <#if (level2.childCatalog)?? >
        <dd>
        <#list level2.childCatalog as level3>  
            <#if level3_index != 0 ><small>|</small></#if><a href="<@xurl value="/list-action.htm?cat=${level3.catalogId}" />" target="_blank">${level3.catalogName}</a>
        </#list>
        </dd> 		    		    	
        </#if>
        </dl> 
    </#list>
    </#if>
</#list>
</#if>        		
      </div>
    </div>
  </div>
</div>





<#-- CMS页尾 -->
<#include "/resources/cms/static/web/M_footer.html" parse="false">
<#include "/views/common/bottom_static.ftl" >
</body>
</html>