<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>${kw.curKw}--走秀网精彩呈现</title>
<meta name="keywords" content="${kw.curKw}，正品保证，低价"/>
<meta name="description" content="${kw.curKw}，时尚百货首选走秀网,与专柜同步,100%确保正品,更多关键词相关信息欢迎登录走秀网"/>

<#include "/views/common/head_static.ftl" >
<script><#-- init info -->
$(document).ready(function(){$(".pic img").lazyload({placeholder:"http://www.xiu.com/static/img/default/default.234_312.jpg"});});
</script>


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
<div class="xs-banner-topmid">
	<#include "/views/common/header_include.ftl" >
</div>
<script src="http://www.xiustatic.com/static/build/js/xiu/module/widget/head.events-1.0.min.js?__=${xversion('/static/build/js/xiu/module/widget/head.events-1.0.min.js')}" type="text/javascript"></script>
<#-- 面包屑-->
<div class="xs-crumb clearfix">
	     <ul id="J_crumbBar" class="nav">
        	<li class="first">根据您搜索的<b>${kw.curKw}</b>为您推荐：</li>
        </ul>
		<div class="totalcount">总共<span class="pl3 pr3 cred">${(page.recordCount)!0}</span>件商品</div>
        <b class="icarr "></b>
</div>
<#-- 面包屑结束-->
<#-- 主体部分 -->


<#--商品展示列表-->
<div class="xs-mainsection clearfix">
    <#-- 主体部分 -->
    <div class="xs-section">
	    <div id="J_filterbox" class="xs-main">       
	        <#-- 商品列表 -->
	        <script>if(typeof latency_metric_ti=='object')latency_metric_ti.atf=new Date().getTime();</script>
			<div id="J_itemListBox" class="xs-itemsbox" style="padding-bottom:20px;">
	    		<#-- 列表模式和大图模式在一起 -->
	    		<#--
	    		<ul class="xs-itemsul-detail clearfix">
	    		-->
	    		<ul class="xs-itemsul-img clearfix">
    				<#list goodsItemList as goods>
    				<#assign clkstat="atype=itemclk|stype=brand|sattr=${(brandName!)?url}|clk_loc=${30*(page.pageNo-1)+(goods_index+1)}|itemid=${goods.urlId}">
		            
		            <li class="item" <#if goods_index % 4 ==3>style=" margin-right:0;"</#if>>
	                	<ol>
	                    	<li class="pic"><a clkstat="${clkstat}" target="_blank" href="${itemUrl}product/${goods.urlId}.shtml${((s_id??)&&s_id!='')?string('?s_id='+s_id!'','')}"><img original="${imagesUrl}${goods.imgUrl220}" /><#if goods.soldOut><b class="saleout-ico"></b></#if></a></li>
	                        <li class="tit">
	                          <#--品牌简化-->
	                          <span>
		                          <#if goods.brandNameEn ?exists>
		                             ${goods.brandNameEn}
		                           <#else>
		                             ${goods.brandNameCn ? default('')}
		                          </#if>
	                          </span>
	                        <a clkstat="${clkstat}" target="_blank" href="${itemUrl}product/${goods.urlId}.shtml${((s_id??)&&s_id!='')?string('?s_id='+s_id!'','')}">${goods.name?html!}</a></li>
	                        <li class="price">
	                        	<span class="showprice ">${goods.showPrice?string('#.##')}</span>
	                        	<#if goods.mktPrice gt goods.showPrice>
	                            <span class="delprice ">${goods.mktPrice?string('#.##')}</span>
	                            </#if>
	                        </li>
	                        <!--<li class="btnbar">
	                        	<#if goods.buyNow>
	                        		<a clkstat="atype=immbuy|stype=brand|sattr=${(brandName!)?url}|clk_loc=${30*(page.pageNo-1)+(goods_index+1)}|itemid=${goods.urlId}" target="_blank" href="${itemUrl}product/${goods.urlId}.shtml${((s_id??)&&s_id!='')?string('?s_id='+s_id!'','')}" class="onsale-btn">立即购买</a>
	                        	</#if>
	                        	<#if goods.arrivalNotice>
	                        		<a clkstat="${clkstat}" target="_blank" href="${itemUrl}product/${goods.urlId}.shtml${((s_id??)&&s_id!='')?string('?s_id='+s_id!'','')}" class="offsale-btn">到货通知</a>
	                        	</#if>
	                        </li>-->
	                    </ol>
	                </li>
		            </#list>
	            </ul>
	        </div>
	        
		    <script>LEON.goodsImageErrorMethod('${staticUrl}static/img/default/default.234_312.jpg');</script>
		    
	        <script>if(typeof latency_metric_ti=='object')latency_metric_ti.cf=new Date().getTime();</script>
	        <#-- 商品列表结束 -->
	        <#-- 底部翻页 -->
	        <div id="J_bottomPageBox" class="xs-pagebar clearfix">
	            <@xpage currentPage="${(page.pageNo)!0}" totalPage="${(page.pageCount)!0}" repQuery="kwId=${kw.id}" pageParamName="p"/>
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
	    
    </div>
    <#-- 主体部分结束 -->
</div>
<#--商品展示列表结束-->
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