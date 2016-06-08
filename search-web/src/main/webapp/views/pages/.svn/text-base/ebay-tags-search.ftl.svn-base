<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>【${xTagBo.displayTags?html}】价格,款式,购买查询-eBay秀</title>
<meta name="description" content="在ebay秀中找到了${page.recordCount}件${xTagBo.displayTags?html}相关的商品；网上购买${xTagBo.displayTags?html}就上ebay秀！" />
<meta name="keywords" content="${(xTagBo.displayTags)!''?html},eBay秀${xTagBo.displayTags?html}" />
<#include "/views/common/ebay_head_static_for_tag.ftl" >
<script>if(typeof LEON == 'undefined')LEON={};
$(document).ready(function(){$(".pic img").lazyload({placeholder:"http://www.xiu.com/static/img/default/default.200_200.jpg"});});</script>
</head>
<body id="index">
<#-- CMS页头 -->
<#include "/views/common/ebay_header_include.ftl" >
<script src="http://www.xiustatic.com/static/js/xiu/index201210/index.head.app.js?__=${xversion('/static/js/xiu/index201210/index.head.app.js')}" type="text/javascript"></script>
<script src="http://www.xiustatic.com/static/js/xiu/module/widget/imgMarkWidget.js?__=${xversion('/static/js/xiu/module/widget/imgMarkWidget.js')}" type="text/javascript"></script>
<#-- 广告banner -->
<div class="xs-banner-topmid">
<#include "/resources/cms/static/show/search/ebay001.shtml" parse="false">
</div>
<#-- 广告banner 结束 -->
<#-- 主体部分 -->
<div class="xs-mainsection clearfix">
	<#-- 主体右侧部分 -->
    <div class="xs-section">
	<div id="J_filterbox" class="xs-main">
		<#-- 面包屑 -->
        <div class="xs-crumb clearfix">
            <label class="hd">您的位置：</label>
            <ul id="J_crumbBar" class="nav">
            	<li class="first"><a href="${ebayUrl}" clkstat="atype=breadclk|stype=tags|sattr=${xTagBo.displayTags?url}|bread_cat=${'eBay秀首页'?url}|bread_lv=1">eBay秀首页</a></li>
            	<#--
            	<#if selectCatalogPlaneList??>
            		<#assign clkstat="atype=breadclk|stype=tags|sattr=${xTagBo.displayTags?url}|"/>
            		<#list selectCatalogPlaneList as catalog>
            		<li><span><a clkstat="${clkstat}bread_cat=${catalog.catalogName?url}|bread_lv=${catalog_index+1}" href="<@xurl value="/ebay-search-action.htm?tags=${xTagBo.searchTags?url}&cat=${catalog.catalogId}" />" alt="${catalog.catalogName!}"><#if (catalog.catalogName??)><#if (catalog.catalogName?length>20)>${catalog.catalogName?substring(0,20)}...<#else>${catalog.catalogName}</#if></#if></a></span><i></i></li>
            		</#list>
            	</#if>
            	 -->
            	<li><span class="fb">"<a href="<@xurl value="/ebay-search-action.htm?tags=${xTagBo.searchTags?url}&cat=${(fatParams.catalogId)!''}&s_id=${(s_id)!''}" />">${xTagBo.displayTags?html}</a>"</span><i></i></li>
            </ul>
            <div class="totalcount">总共<span class="pl3 pr3 cred">${page.recordCount}</span>件商品</div>
            <b class="icarr "></b>
        </div>
        <#-- 面包屑结束 -->
        <#-- 筛选区 -->
        <#if (selectFacetBoList?? || (letterBrandMap?? && brandFacetBo??) || (priceFacetBo.facetValueBos)?? ||  attrFacetBoList?? || selectedCatalogTree?? || catalogBoList??)>
        <#assign urlTemp="/ebay-search-action.htm?tags=${xTagBo.searchTags?url}&cat=${(fatParams.catalogId)!''}&vm=${params.vm!''}&mkt=${(params.mkt)!''}&s_id=${(s_id)!''}">
        <div class="xs-facetbox">
            <div class="facet-hd">
            	<label class="tit ">商品筛选</label>
            </div>
            <div id="J_facet_box" class="facet-bd">
        		<#-- 您已选择 -->
        		<#if selectFacetBoList?? || (selectCatalogPlaneList?? && selectCatalogPlaneList?size gt 0)>
                <div style="" class="facet-line nodot facet-select-all">
                    <label class="line-hd">您已选择：</label>
                    <div class="line-bd">
                        <div class="normal-item-box clearfix">
                            <div class="">
                            <ul class="clearfix">
                            <#if selectCatalogPlaneList?? && selectCatalogPlaneList?size gt 0>
                            <li>
	                            <a href="<@xurl value="/ebay-search-action.htm?tags=${xTagBo.searchTags?url}&vm=${params.vm!''}&mkt=${(params.mkt)!''}&bid=${(fatParams.brandId)!''}&filter=${(fatParams.filter)!''}&f_price=${(fatParams.priceRangeEnum.order)!''}&s_price=${(fatParams.startPrice)!''}&e_price=${(fatParams.endPrice)!''}" />">
	                            分类：
	                            <#list selectCatalogPlaneList as catalog>
		                            <#if catalog.catalogId == fatParams.catalogId>
		                            	${catalog.catalogName?html}
	                            	</#if>
	                            </#list>
	                            <b class="close-ico"></b>
	                            </a>
                            </li>
                            </#if>
                            <#if selectFacetBoList??>
	                            <#list selectFacetBoList as selectFacetBo>
	                            <#if selectFacetBo.facetType == 'BRAND'>
	                            <li><a href="<@xurl value="${urlTemp}&filter=${(fatParams.filter)!''}&f_price=${(fatParams.priceRangeEnum.order)!''}&s_price=${(fatParams.startPrice)!''}&e_price=${(fatParams.endPrice)!''}" />">${selectFacetBo.facetDisplay}：${selectFacetBo.facetValueBos[0].name}<b class="close-ico"></b></a></li>
	                            <#elseif selectFacetBo.facetType == 'PRICE'>
	                            <li><a href="<@xurl value="${urlTemp}&filter=${(fatParams.filter)!''}&bid=${(fatParams.brandId)!''}" />">${selectFacetBo.facetDisplay}：${selectFacetBo.facetValueBos[0].name}<b class="close-ico"></b></a></li>
	                            <#else>
	                            <li><a href="<@xurl value="${urlTemp}&bid=${(fatParams.brandId)!''}&f_price=${(fatParams.priceRangeEnum.order)!''}&filter=${(selectFacetBo.cancelFilter)!''}&s_price=${(fatParams.startPrice)!''}&e_price=${(fatParams.endPrice)!''}" />">${selectFacetBo.facetDisplay}：${selectFacetBo.facetValueBos[0].name}<b class="close-ico"></b></a></li>
	                            </#if>
	                            </#list>
                            </#if>
                            </ul>
                            </div>
                        </div>
                    </div>
                </div>
                </#if>
                <#-- 分类 -->
                <#if catalogBoList?? && !(selectedCatalogTree??)>
                <div class="facet-line ${(catalogBoList??)?string('nodot','')}">
                    <label class="line-hd">分类：</label>
                    <div class="line-bd">
                        <div class="normal-item-box clearfix">
                            <div id="J_itembox_catalog" class="facet-item-box">
                            <ul class="clearfix">
                            <#list catalogBoList as catalog >
	                            <#if catalog.childCatalog?? && catalog.childCatalog?size gt 0>
	                            	<#list catalog.childCatalog as catalog1 >
                            		<li><a href="<@xurl value="/ebay-search-action.htm?tags=${xTagBo.searchTags?url}&vm=${params.vm!''}&mkt=${(params.mkt)!''}&cat=${catalog1.catalogId}&bid=${(fatParams.brandId)!''}&f_price=${(fatParams.priceRangeEnum.order)!''}&filter=${(fatParams.filter)!''}" />">${((catalog1.catalogName)!'')?html}<span></span></a></li>
	                            	</#list>
	                            <#else>
	                            <li><a href="<@xurl value="/ebay-search-action.htm?tags=${xTagBo.searchTags?url}&vm=${params.vm!''}&mkt=${(params.mkt)!''}&cat=${catalog.catalogId}&bid=${(fatParams.brandId)!''}&f_price=${(fatParams.priceRangeEnum.order)!''}&filter=${(fatParams.filter)!''}" />">${((catalog.catalogName)!'')?html}<span></span></a></li>
	                            </#if>
                            </#list>
                            </ul>
                            </div>
                            <a class="more-opt hide" href="javascript:void(0);" onclick="LEON.onOffFacetBox(this,'catalog');">更多</a>
                        </div>
                    </div>
                </div> 
                <s style="height:0px;line-height:0;font-size:0;font-family:Arial;overflow:hidden;zoom:1;display:none;*display:block"></s>
                </#if>
                <#-- 品牌 -->
                <#if (letterBrandMap)?? && (brandFacetBo)??>
                <div class="facet-line brand-facet-box">
                    <label class="line-hd">品牌：</label>
                    <div class="line-bd">
                        <div class="brand-btn-box clearfix">
                            <ul>
                                <li><a id="J_flet_ALL" class="active" href="javascript:void(0);" onclick="LEON.switchBrandBox('ALL')">全部品牌</a></li>
                                <#if letterBrandMap?? && (letterBrandMap?size gt 1)>
	                                <#list brandFirstLetterGroups as letterGroup >
	                                <#if letterBrandMap[letterGroup]??>
	                                <li><a id="J_flet_${letterGroup}" href="javascript:void(0);" onclick="LEON.switchBrandBox('${letterGroup}')">${letterGroup}</a></li>
	                                </#if>
	                                </#list>
                                </#if>
                            </ul>
                            <a id="J_brand_moreopt" class="more-opt" href="javascript:void(0);" onclick="LEON.onOffBrandBox(this)">更多</a>
                        </div>
                        <div id="J_itemboxbrand" class="brand-item-box">
                            <ul id="J_brandFacet_ALL" style="_display:block;_visibility:hidden;">
                            <#assign clkstat="atype=smartnv|stype=kw|sattr=${xTagBo.displayTags?url}|clk_attr=${'品牌'?url}|cat_va="/>
                            	<#list brandFacetBo.facetValueBos as valueFacet >
                                <li><a clkstat="${clkstat}${valueFacet.name?url}" dataCollect="type=brand|id=${(valueFacet.id)!}" href="<@xurl value="${urlTemp}&bid=${valueFacet.id}&f_price=${(fatParams.priceRangeEnum.order)!''}&filter=${(fatParams.filter)!''}&s_price=${(fatParams.startPrice)!''}&e_price=${(fatParams.endPrice)!''}" />">${valueFacet.name?html}<span>(${(valueFacet.itemCount)!'1'})</span></a></li>
                                </#list>
                            </ul>
                            <#list brandFirstLetterGroups as letterGroup >
                            <#if letterBrandMap[letterGroup]??>
                            <ul id="J_brandFacet_${letterGroup}" style="_display:block;_visibility:hidden;">
                            	<#list letterBrandMap[letterGroup] as valueFacet >
                            		<li><a href="<@xurl value="${urlTemp}&bid=${valueFacet.id}&f_price=${(fatParams.priceRangeEnum.order)!''}&filter=${(fatParams.filter)!''}&s_price=${(fatParams.startPrice)!''}&e_price=${(fatParams.endPrice)!''}" />">${valueFacet.name?html}<span>(${(valueFacet.itemCount)!'1'})</span></a></li>
                            	</#list>
                            </ul>
                            </#if>
                            </#list>
                        </div>
                    </div>
                    <script>LEON.initBrandDisplayData();</script>
                </div>
                <s style="height:0px;line-height:0;font-size:0;font-family:Arial;overflow:hidden;zoom:1;display:none;*display:block"></s>
                </#if>
                <#-- 价格 -->
                <#if (priceFacetBo.facetValueBos)??>
                <div class="facet-line">
                    <label class="line-hd">价格：</label>
                    <div class="line-bd">
                        <div class="normal-item-box clearfix">
                            <div id="J_itembox_price" class="facet-item-box">
                            <ul class="clearfix">
                            <#assign clkstat="atype=smartnv|stype=tags|sattr=${xTagBo.searchTags?url}|clk_attr=${'价格'?url}|cat_va="/>
                            <#list priceFacetBo.facetValueBos as valueFacet >
                            <li><a clkstat="${clkstat}${valueFacet.name?url}" href="<@xurl value="${urlTemp}&bid=${(fatParams.brandId)!''}&f_price=${valueFacet.id}&filter=${(fatParams.filter)!''}" />">${valueFacet.name?html}<span>(${(valueFacet.itemCount)!'1'})</span></a></li>
                            </#list>
                            </ul>
                            </div>
                            <a class="more-opt hide" href="javascript:void(0);" onclick="LEON.onOffFacetBox(this,'price');">更多</a>
                        </div>
                    </div>
                </div> 
                <s style="height:0px;line-height:0;font-size:0;font-family:Arial;overflow:hidden;zoom:1;display:none;*display:block"></s>
                </#if>
                <#-- 其他 -->
                <#if (attrFacetBoList)??>
                <#list attrFacetBoList as attrFacet>
                <div class="facet-line">
                    <label class="line-hd">${attrFacet.facetDisplay}：</label>
                    <div class="line-bd">
                        <div class="normal-item-box clearfix">
                            <div id="J_itembox_${attrFacet.facetFieldName}" class="facet-item-box">
                            <ul class="clearfix">
                            <#assign clkstat="atype=smartnv|stype=kw|sattr=${xTagBo.searchTags?url}|clk_attr=${attrFacet.facetDisplay?url}|cat_va="/>
                            <#list attrFacet.facetValueBos as valueFacet >
                            <li><a clkstat="${clkstat}${valueFacet.name?url}" href="<@xurl value="${urlTemp}&bid=${(fatParams.brandId)!''}&f_price=${(fatParams.priceRangeEnum.order)!''}&filter=${(valueFacet.filter)!''}&s_price=${(fatParams.startPrice)!''}&e_price=${(fatParams.endPrice)!''}" />">${valueFacet.name?html}<span>(${(valueFacet.itemCount)!'1'})</span></a></li>
                            </#list>
                            </ul>
                            </div>
                            <a class="more-opt hide" href="javascript:void(0);" onclick="LEON.onOffFacetBox(this,'${attrFacet.facetFieldName}');">更多</a>
                        </div>
                    </div>
                </div> 
                <s style="height:0px;line-height:0;font-size:0;font-family:Arial;overflow:hidden;zoom:1;display:none;*display:block"></s>
                </#list>
                </#if>
            </div>
            <script><#-- @004.js处理"更多"功能 -->
            	(function(){$("#J_facet_box").find("div[id^=J_itembox_]").each(function(){var a=$(this);var c=a.next(".more-opt");var b=a.children("ul").height();c.data("boxheight",b);if(b>30){c.show()}})})();
            </script>
        </div>
        </#if>
        <#-- 筛选区结束 -->
        <#-- 排序过滤区 -->
        <div class="xs-filterbox clearfix">
        	<div class="xs-sort-bar">
                <dl id="J_sortBox" class="sort-box">
                    <dt style="display:none"><label>排序：</label></dt>
                    <#assign urlTemp="/ebay-search-action.htm?tags=${xTagBo.searchTags?url}&cat=${(fatParams.catalogId)!''}&bid=${(fatParams.brandId)!''}&vm=${(params.vm)!''}&f_price=${(fatParams.priceRangeEnum.order)!''}&s_price=${(fatParams.startPrice)!''}&e_price=${(fatParams.endPrice)!''}&filter=${(fatParams.filter)!''}&ba=${(params.ba)!''}&mkt=${(params.mkt)!''}&s_id=${(s_id)!''}">
                    <#assign clkstat="atype=sort|stype=tags|sattr=${xTagBo.searchTags?url}|sorttype=" >
                    <dd><a clkstat="${clkstat}11" href="<@xurl  value="${urlTemp}&sort=11" />" class="nonearr w2 ${(fatParams.sort=='AMOUNT_DESC')?string('active','')}">默认<b class=""></b></a></dd>
                    <dd><a clkstat="${clkstat}${(fatParams.sort=='PRICE_ASC')?string('2','1')}" href="<@xurl  value="${urlTemp}&sort=${(fatParams.sort=='PRICE_ASC')?string('2','1')}" />" class="w2 ${(fatParams.sort=='PRICE_ASC' || fatParams.sort=='PRICE_DESC')?string('active','')}">价格<b></b><i class="${(fatParams.sort=='PRICE_DESC')?string('down','')}"></i></a></dd>
                    <dd><a clkstat="${clkstat}${(fatParams.sort=='ONSALE_TIME_DESC')?string('7','8')}" href="<@xurl  value="${urlTemp}&sort=${(fatParams.sort=='ONSALE_TIME_DESC')?string('7','8')}" />" class="w4 ${(fatParams.sort=='ONSALE_TIME_DESC'|| fatParams.sort=='ONSALE_TIME_ASC')?string('active','')}">上架时间<b></b><i class="${(fatParams.sort=='ONSALE_TIME_ASC')?string('','down')}"></i></a></dd>
                </dl>
            </div>
            <div class="xs-toolbar ">
            	<div class="showmode">
            		<#assign urlTemp="/ebay-search-action.htm?tags=${xTagBo.searchTags?url}&cat=${(fatParams.catalogId)!''}&bid=${(fatParams.brandId)!''}&f_price=${(fatParams.priceRangeEnum.order)!''}&s_price=${(fatParams.startPrice)!''}&e_price=${(fatParams.endPrice)!''}&filter=${(fatParams.filter)!''}&ba=${(params.ba)!''}&sort=${fatParams.sort.sortOrder}&mkt=${(params.mkt)!''}&s_id=${(s_id)!''}">
                	<!--<a href="<@xurl value="${urlTemp}&vm=${((params.vm)??&&params.vm==1)?string('','1')}" />">${(params.vm?? && params.vm==1)?string('显示大图','显示列表')}<b></b></a> -->
                </div>
                <div id="J_pricerangefilter_box" class="pricerange">
                <#assign urlTemp="/ebay-search-action.htm?tags=${xTagBo.searchTags?url}&cat=${(fatParams.catalogId)!''}&bid=${(fatParams.brandId)!''}&filter=${(fatParams.filter)!''}&vm=${(params.vm)!''}&sort=${(fatParams.sort.sortOrder)!}&mkt=${(params.mkt)!''}&s_id=${(s_id)!''}">
                	<form id="J_pricerangefilter_form" action="<@xurl value="${urlTemp}"/>" method="get" onsubmit="return LEON.exePriceRangeFilter(true);" autocomplete="off">
                	<label>价格：</label>
                	<input type="hidden" name="tags" value="${xTagBo.searchTags?html}"/>
                	<input type="hidden" name="cat" value="${(fatParams.catalogId)!''}"/>
                	<input type="hidden" name="bid" value="${(fatParams.brandId)!''}"/>
                	<input type="hidden" name="vm" value="${(params.vm)!''}"/>
                	<input type="hidden" name="p_code" value="${(fatParams.providerCode)!''}"/>
                	<input type="hidden" name="mkt" value="${(params.mkt)!''}"/>
                	<input id="J_finput_minprice" type="text" maxlength="7" name="s_price" value="${(fatParams.startPrice)!''}" class="txt-input vm"/><span class="vm pl3 pr3">&ndash;</span><input id="J_finput_maxprice" type="text" name="e_price" class="txt-input vm" maxlength="7" value="${(fatParams.endPrice)!''}"/><button class="btn vm ml5" type="submit" onclick="LEON.exePriceRangeFilter();">筛选</button><a href="javascript:void(0);" class="cancel vm ml5" onclick="LEON.exeCancelPriceRangeFilter();">取消</a>
                	</form>
                </div>
                <script>LEON.bindPriceRange();</script>
                <@xpage currentPage="${(page.pageNo)!0}" totalPage="${(page.pageCount)!0}" repQuery="tags=${xTagBo.searchTags?url}" theme="1" anchor="J_filterbox"/>
            </div>
        </div>
        <#-- 排序过滤区结束 -->
        <#-- 商品列表 -->
        <script>if(typeof latency_metric_ti=='object')latency_metric_ti.atf=new Date().getTime();</script>
        <div id="J_itemListBox" class="xs-itemsbox">
        <#if goodsItemList?? && goodsItemList?size gt 0>
        	<#-- 列表模式和大图模式在一起 -->
    		<#--<#if (params.vm)?? && params.vm==1>
    		<ul class="xs-itemsul-detail clearfix">
    			<#list goodsItemList as goods>
    			<#assign clkstat="atype=itemclk|stype=tags|sattr=${xTagBo.searchTags?url}|clk_loc=${30*(page.pageNo-1)+(goods_index+1)}|itemid=${goods.urlId}">
	    		<li class="item">
	            	<ol>
	                	<li class="pic"><a clkstat="${clkstat}" href="${ebayUrl}product/${goods.urlId}.shtml${((s_id??)&&s_id!='')?string('?s_id='+s_id!'','')}" target="_blank"><img original="${imagesUrl}${goods.imgUrl100}" /><#if goods.soldOut><b class="saleout-ico"></b></#if></a></li>
	                    <li class="tit"><a clkstat="${clkstat}" href="${ebayUrl}product/${goods.urlId}.shtml${((s_id??)&&s_id!='')?string('?s_id='+s_id!'','')}" target="_blank">${goods.nameHighlight}</a></li>
	                    <li class="price">
	                    	<p class="mt10"><span class="showprice ">${goods.showPrice?string('#.##')}</span></p>
	                    	<#if goods.mktPrice gt goods.showPrice>
	                        <p><span class="delprice ">${goods.mktPrice?string('#.##')}</span></p>
	                        </#if>
	                    </li>
	                    <li class="btnbar">
	                    	<#if goods.buyNow>
	                    		<a clkstat="atype=immbuy|stype=tags|sattr=${xTagBo.searchTags?url}|clk_loc=${30*(page.pageNo-1)+(goods_index+1)}|itemid=${goods.urlId}" target="_blank" href="${ebayUrl}product/${goods.urlId}.shtml${((s_id??)&&s_id!='')?string('?s_id='+s_id!'','')}" class="onsale-btn">立即购买</a>
	                    	</#if>
	                    	<#if goods.arrivalNotice>
	                    		<a clkstat="${clkstat}" target="_blank" href="${ebayUrl}product/${goods.urlId}.shtml${((s_id??)&&s_id!='')?string('?s_id='+s_id!'','')}" class="offsale-btn">到货通知</a>
	                    	</#if>
	                    </li>
	                </ol>
	            </li>
	            </#list>
            </ul>
    		<#else> -->
    		<ul class="xs-itemsul-img clearfix">
        		<#list goodsItemList as goods>
        		<#assign clkstat="atype=itemclk|stype=tags|sattr=${xTagBo.searchTags?url}|clk_loc=${30*(page.pageNo-1)+(goods_index+1)}|itemid=${goods.urlId}">
            	<li class="item" <#if goods_index % 4 ==3>style=" margin-right:0;"</#if> >
                	<ol>
                    	<li class="pic"><a clkstat="${clkstat}" href="${ebayUrl}product/${goods.urlId}.shtml${((s_id??)&&s_id!='')?string('?s_id='+s_id!'','')}" target="_blank"><img original="${imagesUrl}${goods.imgUrl220}" /><#if goods.soldOut><b class="saleout-ico"></b></#if></a></li>
                        <li class="tit"><a clkstat="${clkstat}" href="${ebayUrl}product/${goods.urlId}.shtml${((s_id??)&&s_id!='')?string('?s_id='+s_id!'','')}" target="_blank">${goods.nameHighlight}</a></li>
                        <li class="price">
                        	<span class="showprice ">${goods.showPrice?string('#.##')}</span>
                        	<#if goods.mktPrice gt goods.showPrice>
                            <span class="delprice ">${goods.mktPrice?string('#.##')}</span>
                            </#if>
                        </li>
                        <#--<li class="btnbar">
                        	<#if goods.buyNow>
                        		<a clkstat="atype=immbuy|stype=tags|sattr=${xTagBo.searchTags?url}|clk_loc=${30*(page.pageNo-1)+(goods_index+1)}|itemid=${goods.urlId}" target="_blank" href="${ebayUrl}product/${goods.urlId}.shtml${((s_id??)&&s_id!='')?string('?s_id='+s_id!'','')}" class="onsale-btn">立即购买</a>
                        	</#if>
                        	<#if goods.arrivalNotice>
                        		<a clkstat="${clkstat}" target="_blank" href="${ebayUrl}product/${goods.urlId}.shtml${((s_id??)&&s_id!='')?string('?s_id='+s_id!'','')}" class="offsale-btn">到货通知</a>
                        	</#if>
                        </li> -->
                    </ol>
                </li>
                </#list>
            </ul>
    		<#--</#if> -->
        <#else>
        	<div style="padding:10px;clear:both">
        		很抱歉，未找到商品！
        	</div>
        </#if>
        </div>
        <#if (params.vm)?? && params.vm==1>
	        <script>LEON.goodsImageErrorMethod('${staticUrl}static/img/default/default.100_100.jpg');</script>
        <#else>
       		<script>LEON.goodsImageErrorMethod('${staticUrl}static/img/default/default.220_220.jpg');</script>
        </#if>
        <script>if(typeof latency_metric_ti=='object')latency_metric_ti.cf=new Date().getTime();</script>
        <#-- 商品列表结束 -->
        <#-- 底部翻页 -->
        <div id="J_bottomPageBox" class="xs-pagebar clearfix">
            <@xpage currentPage="${(page.pageNo)!0}" totalPage="${(page.pageCount)!0}" repQuery="tags=${xTagBo.searchTags?url}" pageParamName="p" anchor="J_filterbox"/>
        </div>
        <#-- 底部翻页结束 -->
	</div>
	</div>
	<#-- 主体右侧部分结束 -->
	<#-- 主体左侧部分 -->
    <div class="xs-aside">
    	<#-- 左侧标签 -->
    	<div class="xs-label">
    		<div class="hd">
            	<span class="tit">标签</span>
            </div>
            <div class="list">
            <style type="text/css">
            	.xs-label .list h2.no-tags-h2{border-bottom:0 none;padding-bottom:0}
            </style>
                <h2 class="dq ${(xTagBoList?? && xTagBoList?size gt 0)?string('','no-tags-h2')}"><span>当前标签：</span>${xTagBo.displayTags?html}</h2>
                <#if xTagBoList?? && xTagBoList?size gt 0 >
                <h2><span>相关标签：</span></h2>
                <ul>
                <#assign urlTemp="/ebay-search-action.htm?vm=${(params.vm)!''}&mkt=${(params.mkt)!''}&s_id=${(s_id)!''}">
                	<#list xTagBoList as xTag>
                	<li><a title="商品数量：${xTag.itemCount}" href="<@xurl value="${urlTemp}&tags=${xTag.searchTags}" />">${xTag.displayTags?html}</a></li>
                	</#list>
                </ul>
                </#if>
            </div>
        </div>
        <#--
    	<#if selectedCatalogTree?? || catalogBoList??>
    	<#include "/views/macro/search-page-catalog-macro.ftl" >
    	<div class="xs-catalog-search">
        	<div class="hd ">
            	<span class="tit">所有分类</span>
            </div>
            <div id="J_catalogTire" class="bd ${((selectedCatalogTree)??)?string('','all-mode')}">
            <#assign urlTemp="/ebay-search-action.htm?tags=${xTagBo.searchTags?url}&vm=${(params.vm)!''}&mkt=${(params.mkt)!''}">
            <#assign clkstat="atype=cattree|stype=tags|sattr=${xTagBo.searchTags?url}"/>
            <#if (selectedCatalogTree)??>
	        	<div class="return-all"><a href="<@xurl value="${urlTemp}"/>" title="返回所有分类"><i class="ret"></i>返回所有分类</a></div>
	            <@searchCatalogItemDisplay catalog=selectedCatalogTree displayCount=false/>
            <#else>
	            <#list catalogBoList as catalog1>
	            	<@searchCatalogItemDisplay catalog=catalog1 index=catalog1_index displayCount=false/>
	            </#list>
            </#if>
            </div>
            <script>LEON.catalogSearchIntercative();</script>
        </div>
        </#if>
         -->
        <#-- 左侧分类树结束 -->
        <#-- 左侧购买排行 -->
        <div id="banner_PersonalSalesList" class="xs-saleitemslist hide"></div>
        <div id="banner_PersonalVisitList" class="xs-saleitemslist hide"></div>
        <#-- 左侧购买排行结束 -->
    </div>
    <#-- 主体左侧部分结束 -->
</div>
<#-- 主体部分结束 -->
<#-- 百分点走秀推荐 	由于样式问题，先注释		William.zhang
<div id="banner_BrowsingHistory" class="tj_box"></div>
-->

<!-- go to top botton -->



<#--
<script type="text/javascript" src="http://www.baifendian.com/api/js/bfd-jsapi-3.0.min.js"></script>
-->
<script type="text/javascript" src="http://xres.xiu.com/static/xclk-jsapi.js"></script>
<script type="text/javascript">
<#-- @百分点推荐001.js 
onGetGoodsInfo=function(g,j,f,o){if(!j){return}var q=g.length,b=LEON.bfdData.setting.itemVisitNum;if(q==0){return}var s=o==1?"\u8d2d\u4e70\u6392\u884c":"\u6d4f\u89c8\u6392\u884c";var k=o==1?"buytop":"viewtop";var n=!!LEON.bfdData.setting.stype?LEON.bfdData.setting.stype:"kw";var d=!!LEON.bfdData.setting.sattr?LEON.bfdData.setting.sattr:"";var l="atype="+k+"|stype="+n+"|sattr="+d+"|itemid=";if(o==1){s="\u8d2d\u4e70\u6392\u884c";b=LEON.bfdData.setting.itemSaleNum}var p=0,b=q>b?b:q;var r=$('<div class="hd"><span class="tit">'+s+'</span><span class="tag">Top10</span></div>');var e=$("<div/>").addClass("bd").addClass("clearfix");var c=$("<ul/>").appendTo(e);var a,m,h;do{m=g[p];if(m.length<=3){continue}a=$("<li/>");if(p==b-1){a.addClass("nodot")}h=m[3];if(h.indexOf("_100_100")>0){h=h.replace("_100_100","_80_80")}a.append('<a clkstat="'+l+m[0]+'" class="img" target="_blank" href="'+m[2]+'"><img src="'+h+'"/></a>');a.append('<a clkstat="'+l+m[0]+'" class="tit" target="_blank" href="'+m[2]+'">'+m[1]+"</a>");a.append('<p class="price">'+m[4]+"</p>");c.append(a)}while(++p<b);e.append('<a href="http://www.baifendian.com" class="bfd-click-link " target="_blank"></a>');$(j).append(r).append(e).show();e.clkBindMethod()};onGetGoodsInfo_bh=function(g,h,a){if(!h){return}var j=g.length;if(j==0){return}var l="\u5b98\u65b9\u63a8\u8350";var f="xiuroc";var n=!!LEON.bfdData.setting.stype?LEON.bfdData.setting.stype:"kw";var m=!!LEON.bfdData.setting.sattr?LEON.bfdData.setting.sattr:"";var p="atype="+f+"|stype="+n+"|sattr="+m+"|itemid=";var e=0,c=j>LEON.bfdData.setting.itemHistoryNum?LEON.bfdData.setting.itemHistoryNum:j;var o=$('<div class="hd"><p class="tit">'+l+"</p></div>");var d=$("<div/>").addClass("bd").addClass("clearfix");var q=$("<ul/>").appendTo(d);var b,k;do{k=g[e];if(k.length<=3){continue}b=$("<li/>");if(e==1){b.addClass("first")}if(e==c-1){b.addClass("last")}b.append('<p class="picbox"><a clkstat="'+p+k[0]+'" target="_blank" href="'+k[2]+'"><img src="'+k[3]+'" /></a></p>');b.append('<p class="tit"><a clkstat="'+p+k[0]+'" target="_blank" href="'+k[2]+'">'+k[1]+"</a></p>");b.append('<p class="price xs-ico">'+k[4]+"</p>");q.append(b)}while(++e<c);d.append('<a href="http://www.baifendian.com" class="bfd-click-link " target="_blank"></a>');$(h).append(o).append(d).show()};
$(function(){
LEON.bfdData({stype:'kw',sattr:"${xTagBo.searchTags?url}",categoryList:["${(selectedCatalog.catalogName)!'all'}"]});
});
-->
$(function(){
<#-- 点击流底部翻页@007 -->
$("#J_bottomPageBox,.Pagenum a,.xs-minpage a").click(function(){var c=$(this).text();var b=parseInt("${page.pageNo!}");var d=parseInt("${page.pageCount!}");if("\u4e0a\u4e00\u9875"==c){c=(b-1)>0?(b-1):1}else{if("\u4e0b\u4e00\u9875"==c){c=(b+1)>d?d:(b+1)}else{c=parseInt(c)}}if(!c){return}var a="atype=flip|stype=kw|sattr=${params.tags?url}|page_no=";XIU.Window.ctraceClick(a+c,"search")});
<#--推荐引擎数据收集-->
$('#J_catalogTire,#J_itemboxbrand').dataCollectMethod({siteId:1001,channelId:1002});
LEON.xreDataCollect({siteId:1001,channelId:1002,kw:"${params.tags?html}",result:${page.recordCount!0}});
<#--推荐引擎数据-->
LEON.xreData({brandId : "${(recommenBrandIds)!}",catId : "${recommendCatIds!}",itemId : "${recommendItemIds!}",stype : "kw",sattr : "${params.tags?url}",siteId : "1001",channelId : "1002",displayTop10:false});
<#-- 点击流事件绑定 -->
$('#J_itemListBox,#J_sortBox,#J_crumbBar,#J_catalogTire,#J_mktTypeBox,#J_facet_box').clkBindMethod();
});
</script>
<#-- 百分点走秀推荐结束 -->
<#-- CMS页尾 -->
<#include "/resources/cms/static/web/M_footer.html" parse="false">
<#include "/views/common/ebay_bottom_static.ftl" parse="false">
</body>
</html>