<script>if(typeof latency_metric_ti=='undefined')latency_metric_ti={};latency_metric_ti.fb=new Date().getTime();latency_metric_ti.pt='search';</script>
<link rel="shortcut icon" type="image/x-icon" href="${staticUrl}favicon.ico?__=${xversion('/favicon.ico')}"/>
<#--老版页头，目前不用了！William.zhang	20130528
<#include "/resources/cms/PublicHeadStatic.shtml" >
-->
<#include "/resources/cms/ebay/ebay-publicStatic.html" >
<#-- search2.5自己的js和css
<link href="${staticUrl}static/ebay/css/xsearch20120615${developStatus?string('','-min')}.css?__=${xversion('${staticUrl}static/ebay/css/xsearch20120615.css')}" rel="stylesheet" type="text/css" />
<link href="${staticUrl}static/ebay/css/style${developStatus?string('','-min')}.css?__=${xversion('${staticUrl}static/ebay/css/style.css')}" type="text/css" rel="stylesheet" />
<link href="${staticUrl}static/ebay/css/slides${developStatus?string('','-min')}.css?__=${xversion('${staticUrl}static/ebay/css/slides.css')}" type="text/css" rel="stylesheet" />
 -->
 <#--
<link href="${staticUrl}static/ebay/css/xsearch20120615.css?__=${xversion('${staticUrl}static/ebay/css/xsearch20120615.css')}" rel="stylesheet" type="text/css" />
-->
<#--	老版本,现在取消
<link href="${staticUrl}static/ebay/css/style.css?__=${xversion('${staticUrl}static/ebay/css/style.css')}" type="text/css" rel="stylesheet" />
<link href="${staticUrl}static/ebay/css/slides.css?__=${xversion('${staticUrl}static/ebay/css/slides.css')}" type="text/css" rel="stylesheet" />
-->
<script src="${staticUrl}static/searchstatic/script/xs-monitor${developStatus?string('','-min')}.js?__=${xversion('/static/searchstatic/script/xs-monitor.js')}" type="text/javascript"></script>
<#--	老版本,现在取消
<script src="${staticUrl}static/ebay/js/jquery/slides.min.jquery.js" type="text/javascript"></script>
<script src="${staticUrl}static/ebay/js/global.js?__=${xversion('${staticUrl}static/ebay/js/global.js')}" type="text/javascript"></script>
<script src="${staticUrl}static/ebay/js/jscroll.js?__=${xversion('${staticUrl}static/ebay/js/jscroll.js')}" type="text/javascript"></script>
-->
<script>LEON.init({staticUrl:'${staticUrl}',searchUrl:'${searchUrl}',listUrl:'${listUrl}',brandUrl:'${brandUrl}'});</script>

<#-- 新版本用到的css William.zhang
<link href="${staticUrl}static/css/default/index_201304.css" rel="stylesheet" type="text/css" />
<link href="${staticUrl}static/css/default/xsearch20120615.css" rel="stylesheet" type="text/css" />
-->
<#--老版本,现在取消
<link type="text/css" rel="stylesheet" href="http://www.xiustatic.com/static/css/default/index_201304.css">
<link type="text/css" rel="stylesheet" href="http://192.168.90.100/static/ebay/css/xsearch20120615.css">
<script src="http://192.168.90.100/static/ebay/js/jscroll.js" type="text/javascript"></script>
<script type="text/javascript" src="http://192.168.90.100/static/ebay/js/ScrollImg.js"></script>
-->

<link href="${staticUrl}static/ebay/css/xsearch20120615.css?__=${xversion('static/ebay/css/xsearch20120615.css')}" rel="stylesheet" type="text/css" />
<script src="${staticUrl}static/ebay/js/jscroll.js?__=${xversion('static/ebay/js/jscroll.js')}" type="text/javascript"></script>
<#--没有调用这个js--
<script type="text/javascript" src="${staticUrl}static/ebay/js/ScrollImg.js?__=${xversion('static/ebay/js/ScrollImg.js')}"></script>
-->

<#--
<#if !developStatus>
<script> @003.js 
var _gaq=_gaq||[];_gaq.push(["_setAccount","UA-9540643-5"]);_gaq.push(["_addOrganic","soso","w"]);_gaq.push(["_addOrganic","youdao","q"]);_gaq.push(["_addOrganic","baidu","word"]);_gaq.push(["_addOrganic","sogou","query"]);_gaq.push(["_setDomainName",".xiu.com"]);_gaq.push(["_setAllowLinker",true]);_gaq.push(["_setAllowHash",false]);_gaq.push(["_trackPageview"]);_gaq.push(["_trackPageLoadTime"]);(function(){var b=document.createElement("script");b.type="text/javascript";b.async=true;b.src=("https:"==document.location.protocol?"https://ssl":"http://www")+".google-analytics.com/ga.js";var a=document.getElementsByTagName("script")[0];a.parentNode.insertBefore(b,a)})();
</script>
</#if>
-->