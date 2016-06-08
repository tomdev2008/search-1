<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>走秀网热词排行榜</title>
<meta name="keywords" content="热词排行榜"/>
<meta name="description" content="走秀网热词排行榜,为您呈现最新搜索热词,畅销单品品牌,世界精品推荐"/>
<#include "/views/common/head_static.ftl" >
<script><#-- init info -->
$(document).ready(function(){$(".pic img").lazyload({placeholder:"http://www.xiu.com/static/img/default/default.200_200.jpg"});});
</script>
<style type="text/css">
 .itm_normal{background-color:#FFFFFF;}
 .itm_normal:hover{background-color:#F1F1F1;}
 </style>
 
 <script>
	/**
	 * 列表页面的底部搜索框form翻页提交过滤方法
	 * 用于url的格式转换
	 */
	function reWriteFormUrl(){
		
		var _box = $('#J_bottomPageBox');
		var _form = _box.find('form');
		var _button = _box.find('button').eq(0);
		_form.eq(0).bind('submit',function(){
			var _pInput = _form.find('input[name="p"]');
			if(!_pInput)return true;
			var _p = parseInt(_form.find('input[name="p"]').val()) || 1;
			_pInput.attr('disabled','disabled');
			var _action = _form.attr('action');
			var _curIdx=_action.indexOf("-.html");
			if(_curIdx<=0){
				_curIdx=_action.indexOf(".html");
			}
			if(_curIdx>0){
				var urlM=_action.substring(0,_curIdx);
				_action=urlM+"-"+_p+".html";
			}
			_form.attr('action',_action);
			return true;
		});
	}
</script>
 
</head>
<body id="index">
<#-- CMS页头 -->
<div id="J_catalogTire" class="xs-facetbox">
	<#include "/views/common/header_include.ftl" >
</div>
<script src="http://www.xiustatic.com/static/js/xiu/index201210/index.head.app.js?__=${xversion('/static/js/xiu/index201210/index.head.app.js')}" type="text/javascript"></script>
<#-- 主体部分 -->
<div id="J_catalogTire" class="xs-facetbox">
	<div id="J_facet_box"  class="facet-bd">
		<style type="text/css">
			.facet-line .line-hd{left:10px;width:60px;}
			.clearline{width:100%;clear:both;height:1px;line-height:0;font-size:0px;display:block}
			.xs-aside{overflow:hidden;}
		</style>
	</div>
</div>

<div class="xs-mainsection clearfix">

    <#-- 主体右侧部分 -->
    <div class="xs-section">
	    <div id="J_filterbox" class="xs-main">       
	        <#-- 商品列表 -->
			<div id="J_itemListBox" class="xs-itemsbox">
	    		
	    		<ul class="xs-itemsul-img clearfix">
	    		    <#if kwList??>
		        		<#list kwList as kw>
			            	<li class="item itm_normal" style="height:24px;width:150px;padding:1px 35px 2px 15px;border:1px;margin:0;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;">
			            	<#--<#if kw.curKw?length lt 11>${kw.curKw}<#else>${kw.curKw[0..10]}...</#if>-->
			                	<a target="_blank" style="font-size:14px;" href="${searchUrl}kw/${kw.id}.html" title="${kw.curKw}">${kw.curKw}</a>
			                </li>
		                </#list>
		            </#if>
	            </ul>
	        </div>
	        <#-- 商品列表结束 -->
	        <#-- 底部翻页 -->
	        <div id="J_bottomPageBox" class="xs-pagebar clearfix">
	            <@xpage currentPage="${(page.pageNo)!0}" totalPage="${(page.pageCount)!0}" repQuery="" pageParamName="p" />
	        </div>
	        <#-- 底部翻页结束 -->
	    </div>
	   	<script>
	    //商品选中效果
		$(function(){
			$(".xs-itemsul-img .item,.xs-itemsul-detail .item").hover(function(){
			$(this).addClass('curr');
			},function(){
			$(this).removeClass('curr');
			});
		});
	    </script>
	    
	    <#-- 主体左侧部分
    	<div class="xs-aside">
	        <div id="banner_PersonalSalesList" class="xs-saleitemslist hide"></div>
	        <div id="banner_PersonalVisitList" class="xs-saleitemslist hide"></div>
	    </div>
	     -->
	    <#-- 主体左侧部分结束 -->
    </div>
    <#-- 主体右侧部分结束 -->
    
    
   
</div>
<#-- 主体部分结束 -->
<#-- 分隔线开始 -->
<div id="J_catalogTire" class="xs-facetbox">
<div id="J_facet_box"  class="facet-bd">
	<style type="text/css">
		.facet-line .line-hd{left:10px;width:60px;}
		.clearline{width:100%;clear:both;height:1px;line-height:0;font-size:0px;display:block}
		.xs-aside{overflow:hidden;}
	</style>
</div>
</div>
<#-- 分隔线结束 -->

<#-- CMS页尾 -->
<#include "/resources/cms/static/web/M_footer.html" parse="false">
<#include "/views/common/bottom_static.ftl" >

<#-- 点击流埋码 -->
<script type="text/javascript">XIU.Window.send();</script>


<script>reWriteFormUrl()</script>

</body>
</html>