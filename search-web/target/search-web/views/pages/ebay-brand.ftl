<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#if seoInfo??>
<title>${seoInfo.title?html}</title>
<meta name="keywords" content="${seoInfo.keywords?html }"/>
<meta name="description" content="${seoInfo.description?html}"/>
</#if>
<#include "/views/common/ebay_head_static.ftl" >
<script><#-- init info -->
LEON.init({staticUrl:'${staticUrl}'});
$(document).ready(function(){$(".pic img").lazyload({placeholder:"http://www.xiu.com/static/img/default/default.234_312.jpg"});});
<#--009.js-->
//$(document).ready(function(){var b=$("#J_brandBox");var f=$("#J_brandInfoText"),c=f.height(),d=f.html(),e=$("#showMoreTxt");var a=$.browser.msie&&$.browser.version=="6.0";if(c>110){f.css({height:"110px",overflow:"hidden"}).data("nowHeight",110);if(a){b.css({height:"110px"})}$('<a href="#" class="down" ></a>').click(function(){if(f.data("nowHeight")!=110){if(f.data("jscroll")){f.removeData("jscroll").html(d)}f.css({height:"110px",overflow:"hidden"}).data("nowHeight",110);if(a){b.css({height:"110px"})}}else{if(c>176){f.css({height:"176px"}).data("nowHeight",176).jscroll({W:"7px",Bg:"right 0 repeat-y",Btn:{btn:false},Fn:function(){}});if(a){b.css({height:"176px"})}}else{f.css({height:c+"px",overflow:"hidden"}).data("nowHeight",c);if(a){b.css({height:c+"px"})}}}$(this).toggleClass("up");return false}).appendTo(e)}else{f.css({height:"110px",overflow:"hidden"}).data("nowHeight",110);if(a){b.css({height:"110px"})}}});

//品牌介绍
$(document).ready(function(){
var $txtBox = $('.info_text:first'),txtHeight = $txtBox.height(),txtHtml = $txtBox.html(), $txtBtnBox = $('#showMoreTxt');
if(txtHeight > 88){
	 $txtBox.css({height:'88px',overflow:'hidden'}).data('nowHeight',88);
	 $('<a href="#" class="down" ></a>').click(function(){
			if($txtBox.data('nowHeight')!=88){
				if($txtBox.data('jscroll')) $txtBox.removeData('jscroll').html(txtHtml);
				$txtBox.css({height:'88px',overflow:'hidden'}).data('nowHeight',88);
			}else{	
				txtHeight > 176 ? 
				$txtBox.css({height:'176px'}).data('nowHeight',176).jscroll({ W:"7px"
					,Bg:"right 0 repeat-y"
					,Btn:{btn:false}
					,Fn:function(){}
				})
				:
				$txtBox.css({height:txtHeight+'px',overflow:'hidden'}).data('nowHeight',txtHeight)
				;
			}
			$(this).toggleClass('up');
			return false;
	})
	 .appendTo($txtBtnBox);
	}	
});

//下拉框
$(function () {
     $(".tabs>li").on("mouseenter",function(){
		if($(".dropdown",this).length !=0){
			$(".on",this).addClass("select");
			$(".dropdown",this).css('visibility', 'visible');
		}
     }).on('mouseleave', function(event) {
          $(".on",this).removeClass("select");
       $(".dropdown",this).css('visibility', 'hidden');
     });
});
</script>
</head>
<body id="index">
<#-- 公用头部 开始-->
<#include "/views/common/ebay_header_include.ftl" >
<#-- 公用头部 结束-->
<script src="http://www.xiustatic.com/static/build/js/xiu/module/widget/head.events-1.0.min.js?__=${xversion('/static/build/js/xiu/module/widget/head.events-1.0.min.js')}" type="text/javascript"></script>
<script src="http://www.xiustatic.com/static/build/js/xiu/module/widget/imgMarkWidget.min.js?__=${xversion('/static/build/js/xiu/module/widget/imgMarkWidget.min.js')}" type="text/javascript"></script>
<#-- 广告banner -->
<div class="xs-banner-topmid">
<#include "/resources/cms/static/show/search/ebay001.shtml" parse="false">
</div>
<#-- 广告banner 结束 -->
<#if (bannerImageName2??)&&(""!=bannerImageName2)>
	<div id="brand_pic_div" class="brand_banner" style="margin:10px auto; width:1000px;">
		<img id="brand_pic" src="${bannerImageName2!}" alt="${(brandName!)}"/>
	</div>
</#if>
<#-- 面包屑-->
<div class="xs-crumb clearfix">
	<label class="hd">您的位置：</label>
        <ul id="J_crumbBar" class="tabs">
        	<li class="no_dropdown first"><a href="${ebayUrl}" clkstat="atype=breadclk|stype=brand|sattr=${(brandName!)?url}|bread_cat=${'eBay秀首页'?url}|bread_lv=1">eBay秀首页</a><i></i></li>
        	<#if selectCatalogPlaneList??>
			    <#assign clkstat="atype=breadclk|stype=brand|sattr=${(brandName!)?url}">
			    <#assign catUrlTemp="/ebay-brand-action.htm?bid=${(params.bid)!}&mkt=${(fatParams.mktType.type)!''}&s_id=${(s_id)!''}&itemshowtype=${(params.itemshowtype)!''}&sort=${fatParams.sort.sortOrder}&vm=${params.vm!''}&channel=${(params.channel)!''}">
		        	    	
		        <#list selectCatalogPlaneList as catalog>
        			<li><a class="on" clkstat="${clkstat}|bread_cat=${catalog.catalogName?url}|bread_lv=${catalog_index+1}" href="<@xurl value="${catUrlTemp}&cat=${catalog.catalogId}" />">${catalog.catalogName}<em></em></a><i></i>
	        		<#if catalog_index == 0><!-- 1级分类兄弟 -->
	        			<#if firstCataList?? && firstCataList?size gt 0>
	        				<ul class="dropdown">
		        	    		<#list firstCataList as fc>
		        	    			<li>
			        	    			<#if fc.selected>
			        	    				<a clkstat="${clkstat}|bread_cat=${fc.catalogName?url}|bread_lv=1" href="javascript:void();" class="selected">${fc.catalogName}</a>
			        	    			<#else>
			        	    				<a clkstat="${clkstat}|bread_cat=${fc.catalogName?url}|bread_lv=1" href="<@xurl value="${catUrlTemp}&cat=${fc.catalogId}" />">${fc.catalogName}</a>
			        	    			</#if>
			        	    			<#if fc_has_next>|</#if>
		        	    		    </li>
		        	    		</#list>
	        	    		</ul>
	        	    	</#if>
	        	    <#elseif catalog_index == 1><!-- 2级分类 兄弟-->
	        	    	<#if secondCataList?? && secondCataList?size gt 0>
	                        <ul class="dropdown">
			        	    	<#list secondCataList as sc>
		                        	<li>
			        	    			<#if sc.selected>
			        	    				<a clkstat="${clkstat}|bread_cat=${sc.catalogName?url}|bread_lv=2" href="javascript:void();" class="selected">${sc.catalogName}</a>
			        	    			<#else>
			        	    				<a clkstat="${clkstat}|bread_cat=${sc.catalogName?url}|bread_lv=2" href="<@xurl value="${catUrlTemp}&cat=${sc.catalogId}" />">${sc.catalogName}</a>
			        	    			</#if>
			        	    			<#if sc_has_next>|</#if>
		        	    		    </li>
		                        </#list>
	                        </ul>
                        </#if>
	        	    <#else><!-- 3级分类 兄弟-->
		            	<#if thirdCataList?? && thirdCataList?size gt 0>
	                        <ul class="dropdown">
	                        <#list thirdCataList as catalog2>
	                        	<li>
		                       		<#if catalog2.selected>
		        	    				<a clkstat="${clkstat}|bread_cat=${catalog2.catalogName?url}|bread_lv=3" href="javascript:void();" class="selected">${catalog2.catalogName}</a>
		        	    			<#else>
		        	    				<a clkstat="${clkstat}|bread_cat=${catalog2.catalogName?url}|bread_lv=3" href="<@xurl value='${catUrlTemp}&cat=${catalog2.catalogId}' />">${catalog2.catalogName}</a>
		        	    			</#if>
		        	    			<#if catalog2_has_next>|</#if>
		        	    		</li>
	                        </#list>
	                        </ul>
		            	</#if>
		        	</#if>
		        	</li>
		        </#list>
		    </#if>
        	<li class="no_dropdown"><span class="no_active_label">${brandName!}</span></li>
        </ul>
	<div class="totalcount">总共<span class="pl3 pr3 cred">${(page.recordCount)!0}</span>件商品</div>
	<b class="icarr "></b>
</div>
<#-- 面包屑结束-->

<#-- 品牌故事开始 -->
<#if ((!bannerImageName2??)||("" == bannerImageName2)) && ((story??)&&story?length gt 0)>

<div id="J_brandBox" class="pp_info">
	<div class="text_nr">
		<#if (storeImgUrl??)&&(""!=storeImgUrl)>
        <a href="${commentUrl}commentlist/brand/${(params.bid)!}.html" target="_blank"><img src="${imagesUrl}UploadFiles/xiu/brand${storeImgUrl!}" alt="" /></a>
        <#else>
        <a href="${commentUrl}commentlist/brand/${(params.bid)!}.html" target="_blank"><img src="${ebayUrl}static/ebay/images/brand-logo.png" alt="" /></a>
        </#if>
        <div id="J_brandInfoText" class="info_text">${story!}
        </div>
	</div>
	<div class="arrow_ico" id="showMoreTxt"></div>
</div>

</#if>
<#-- 品牌故事结束 -->


<#-- 主体部分 -->
<div id="J_catalogTire" class="xs-facetbox">
	<div id="J_facet_box" class="facet-bd">
    	<style type="text/css">
			.facet-line .line-hd{left:10px;width:60px;}
			.clearline{width:100%;clear:both;height:1px;line-height:0;font-size:0px;display:block}
			.xs-aside{overflow:hidden;}
		</style>
		<#if !((fatParams.catalogId)??)>
			<#if catalogBoList?? && catalogBoList?size gt 0>
				<#assign urlTemp="/ebay-brand-action.htm?bid=${(params.bid)!}&mkt=${(params.mkt)!''}&s_id=${(s_id)!''}&sort=${fatParams.sort.sortOrder}&vm=${params.vm!''}&channel=${(params.channel)!''}">
		        <#assign clkstat="atype=cattree|stype=list|clk_cat=${(selectedCatalogTree.catalogName)!?url}"/>
		        <div class="facet-line brand-facet-box fl_box">
		            <label class="line-hd">分类：</label>
	            	<div class="line-bd">
		                <div class="normal-item-box clearfix">
		                    <div>
		                        <ul id="J_catSecondItem" class="clearfix">
		                        	<#list catalogBoList as catalog>
		                            	<li><a dataCollect="type=list|id=${(catalog.catalogId)!}" data-catalogid="${catalog.catalogId}" clkstat="${clkstat!},${catalog.catalogName?url}|cat_lv=2" href="<@xurl value='${urlTemp}&cat=${catalog.catalogId}' />" class="${(catalog.selected )?string('active','')}" >
		                            	${catalog.catalogName?html}
		                            	</a></li>
		                            </#list>
		                        </ul>
		                    </div>
		                </div>
		            </div>
		        </div>
			</#if>
		<#else>
        	<#assign urlTemp="/ebay-brand-action.htm?bid=${(params.bid)!}&mkt=${(params.mkt)!''}&s_id=${(s_id)!''}&sort=${fatParams.sort.sortOrder}&vm=${params.vm!''}&channel=${(params.channel)!''}">
	    	<#if selectFacetBoList??>
            	<div class="facet-line nodot facet-select-all change_nopd">
	                <label class="line-hd">您已选择：</label>
	                <div class="line-bd">
	                    <div class="normal-item-box clearfix">
	                        <div class="facet-item-box">
	                            <ul class="clearfix">
	                            <#--
	                            	<#if selectedCatalogTree??>
		                            	<li><a href="<@xurl value="${urlTemp}" />">
		                            		${selectedCatalogTree.catalogName?html}
		                            		<b class="close-ico"></b></a>
		                            	</li>
		                            </#if>
		                        -->    
		                            <#if selectFacetBoList?? && selectFacetBoList?size gt 0>
			                            <#list selectFacetBoList as selectFacetBo>
			                            <#if selectFacetBo.facetType == 'BRAND'>
			                            <li><a href="<@xurl value="${urlTemp}&cat=${(fatParams.catalogId)!''}&filter=${(fatParams.filter)!''}&f_price=${(fatParams.priceRangeEnum.order)!''}&s_price=${(fatParams.startPrice)!''}&e_price=${(fatParams.endPrice)!''}" />">${selectFacetBo.facetDisplay}：${selectFacetBo.facetValueBos[0].name}<b class="close-ico"></b></a></li>
			                            <#elseif selectFacetBo.facetType == 'PRICE'>
			                            <li><a href="<@xurl value="${urlTemp}&cat=${(fatParams.catalogId)!''}&filter=${(fatParams.filter)!''}&bid=${(params.bid)!''}" />">${selectFacetBo.facetDisplay}：${selectFacetBo.facetValueBos[0].name}<b class="close-ico"></b></a></li>
			                            <#else>
			                            <li><a href="<@xurl value="${urlTemp}&cat=${(fatParams.catalogId)!''}&bid=${(params.bid)!''}&f_price=${(fatParams.priceRangeEnum.order)!''}&filter=${(selectFacetBo.cancelFilter)!''}&s_price=${(fatParams.startPrice)!''}&e_price=${(fatParams.endPrice)!''}" />">${selectFacetBo.facetDisplay}：${selectFacetBo.facetValueBos[0].name}<b class="close-ico"></b></a></li>
			                            </#if>
			                            </#list>
		                            </#if>
		                            <#--<li class="reset"><a href="<@xurl value="${urlTemp}" />">重置筛选条件</a></li>-->
		                            <li class="reset">
		                             <#list selectCatalogPlaneList as catalog>
								        <#if !catalog_has_next>
								        	<a href="<@xurl value="${urlTemp}&cat=${(catalog.catalogId)!''}" />">重置筛选条件</a>
								        </#if>
					        	     </#list>
		                            </li>
	                            </ul>
	                        </div>
	                    </div>
	                </div>
	            </div>
	        </#if>
            <#if selectedCatalogTree?? && selectedCatalogTree.childCatalog?? && selectedCatalogTree.childCatalog?size gt 0 && selectCatalogPlaneList?? && selectCatalogPlaneList?size lt 3>
	        <#assign urlTemp="/ebay-brand-action.htm?bid=${params.bid!}&mkt=${(params.mkt)!''}&s_id=${(s_id)!''}&itemshowtype=${(params.itemshowtype)!''}&sort=${fatParams.sort.sortOrder}&vm=${params.vm!''}&channel=${(params.channel)!''}">
	        <#assign clkstat="atype=cattree|stype=list|sattr=${selectedCatalogTree.catalogName?url}|clk_cat=${(selectedCatalogTree.catalogName)!?url}"/>
            	<!-- 显示2级分类-->
            	<#if selectCatalogPlaneList?size ==1>
            		<div class="facet-line brand-facet-box fl_box">
			            <label class="line-hd">分类：</label>
			            <div class="line-bd">
			                <div class="normal-item-box clearfix">
			                    <div>
			                        <ul id="J_catSecondItem" class="clearfix">
			                        	<#list selectedCatalogTree.childCatalog as catalog>
			                            	<li><a data-catalogid="${catalog.catalogId}" dataCollect="type=list|id=${(catalog.catalogId)!}" clkstat="${clkstat!},${catalog.catalogName?url}|cat_lv=2" href="<@xurl value='${urlTemp}&cat=${catalog.catalogId}' />" class="${(catalog.selected )?string('active','')}" >
			                            	${catalog.catalogName?html}
			                            	</a></li>
			                            </#list>
			                        </ul>
			                    </div>
			                </div>
			            </div>
			        </div>
            	<#else>
            	    <!-- 显示3级分类 -->
            	    <#list selectedCatalogTree.childCatalog as catalog>
		        	<#if catalog.childCatalog?? && catalog.childCatalog?size gt 0>
		            	<#if catalog.selected && catalog.childCatalog?? && catalog.childCatalog?size gt 0>
		            		<div class="facet-line brand-facet-box fl_box">
					            <label class="line-hd">分类：</label>
					            <div class="line-bd">
					                <div class="normal-item-box clearfix">
					                    <div>
					                        <ul id="J_catSecondItem" class="clearfix">
							            		<input type="hidden" id="J_selectCatalogId" value="${catalog.catalogId}" />
						                        <#list catalog.childCatalog as catalog2>
						                            <li><a dataCollect="type=list|id=${(catalog2.catalogId)!}" href="<@xurl value='${urlTemp}&cat=${catalog2.catalogId}' />" class="${catalog2.selected?string('active','')}">
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
					            </div>
					        </div>
		                </#if>
		        	</#if>
		        </#list>
            	</#if>
	        </#if>
			<#--
			尺码:<a href="<@xurl value='/list-action.htm?cat=19380&s_price=&e_price=&bid=&f_price=&filter=;10|20|30;;'/>">尺码测试</a>
			-->
	        <!-- 其他(尺码，材料。。。。等等) -->
	        <script>
	        	//页面搜索 William.zhang 20130506
				if(typeof LEON == 'undefined')LEON={};
				var _attrUnfoldStatMap = {};
				var _multiAttrSelectMap = {};
				var _hasSelectAttr = {};
				var _moreAttrBoxOpt = false;
				$.extend(LEON,{
		    		attrFoldAction : function(attrId,unfload){
		    			var __unfload = (typeof unfload != 'undefined') ? unfload : !!_attrUnfoldStatMap[attrId];
		    			if(!__unfload){
		    				$('#J_attrBoxMargin_'+attrId).css({"height":"auto"});
		    				$('#J_attrMoreBtn_'+attrId).addClass('down');
		    				$('#J_attrMoreBtn_'+attrId).empty();
			    			$('#J_attrMoreBtn_'+attrId).html("收起");
		    				_attrUnfoldStatMap[attrId] = true;
						}else{
							$('#J_attrBoxMargin_'+attrId).css({"height":"24px"});
							$('#J_attrMoreBtn_'+attrId).removeClass('down');
							$('#J_attrMoreBtn_'+attrId).empty();
		    				$('#J_attrMoreBtn_'+attrId).html("展开");
							_attrUnfoldStatMap[attrId] = false;
						}
		    		},
		    		multiAttrFoldAction : function(attrId){
		    			$('#J_attrBtnBox_'+attrId).hide();
		    			$('#J_attrMultiOKCancelBtnBox_'+attrId).show();
		    			LEON.attrFoldAction(attrId,false);
		    			_multiAttrSelectMap[attrId] = true;
		    		},
		    		multiAttrSelectItemAction : function(e,obj,attrId){
		    			if(_multiAttrSelectMap[attrId]){
		    				$.stopDefault(e);
		    			}else{
		    				return;
		    			}
		    			if(!_hasSelectAttr[attrId])
		    				_hasSelectAttr[attrId] = {};
		    			var _this = $(obj);
		    			var attrvalid = _this.data('attrvalid');
		    			if(_hasSelectAttr[attrId][attrvalid]){
		    				_this.removeClass('active');
		    				delete _hasSelectAttr[attrId][attrvalid];
		    			}else{
		    				_this.addClass('active');
		    				_hasSelectAttr[attrId][attrvalid]=true;
		    			}
		    			var size = 0;
		    			for (key in _hasSelectAttr[attrId]) {
					        if (_hasSelectAttr[attrId].hasOwnProperty(key)) size++;
					    }
		    			if(size > 0){
		    				$('#J_attrMultiOKBtn_'+attrId).removeClass('no_change');
		    			}else{
		    				$('#J_attrMultiOKBtn_'+attrId).addClass('no_change');
		    			}
		    		},
		    		multiAttrSubmitAction : function(attrId){
		    			var _keys = [];
		    			for (key in _hasSelectAttr[attrId]) {
					        if (_hasSelectAttr[attrId].hasOwnProperty(key)) _keys.push(key);
					    }
		    			if(_keys.length == 0){
		    				return;
		    			}
		    			var _attrIds = [];
		    			for(var k in _keys){
		    				_attrIds.push(_keys[k]);
		    			}
		    			var _idStr = _attrIds.join('|');
		    			var _url = $('#J_attrUrlInput_'+attrId).val();
		    			var _filter = $('#J_attrFilterParamInput_'+attrId).val();
		    			if(_filter.length > 0){
		    				_filter += ";"
		    			}
		    			_filter += _idStr;
		    			if(_url.indexOf('?')>0){
		    				_url += '&filter=' + _filter;
		    			}else{
		    				_url += '?filter=' + _filter;
		    			}
		    			//alert(_url);
		    			location.href = _url;
		    		},
		    		multiAttrCancelAction : function(attrId){
		    			$('#J_attrMultiOKCancelBtnBox_'+attrId).hide();
		    			$('#J_attrBtnBox_'+attrId).show();
		    			LEON.attrFoldAction(attrId);
		    			_multiAttrSelectMap[attrId] = false;
		    			$('#J_attrBoxUl_'+attrId+' a').each(function(){
		    				var _this = $(this);
		    				if(_this.hasClass('active')){
			    				_this.removeClass('active');
			    			}
		    			});
		    			_hasSelectAttr[attrId]={};
		    			$('#J_attrMultiOKBtn_'+attrId).addClass('no_change');
		    		},
		    		moreOrMinAttrBoxOpt : function(){
		    			if(_moreAttrBoxOpt){
		    				$('div[id^=J_attrBox_]').each(function(){
		    					if($(this).hasClass('J_hideAttrCol')){
		    						$(this).hide();
		    					}
		    				});
		    				$('#J_moreStyle').removeClass();
							$('#J_moreStyle').addClass("down");
		    				_moreAttrBoxOpt = false;
		    				$('#J_moreItemBox').removeClass('more-open-item-box');
		    			}else{
		    				$('div[id^=J_attrBox_]').each(function(){
		    					if($(this).hasClass('J_hideAttrCol')){
		    						$(this).show();
		    					}
		    				});
		    				$('#J_moreStyle').removeClass();
							$('#J_moreStyle').addClass("down up");
		    				_moreAttrBoxOpt = true;
		    				$('#J_moreItemBox').addClass('more-open-item-box');
		    				LEON.initMoreBtnStat();
		    			}
		    		},
		    		initMoreBtnStat : function(){
		    			$('ul[id^=J_attrBoxUl_]').each(function(){
		        			var idStr = $(this).attr('id');
		        			var indexof = idStr.split('_');
		        			var _attrid = indexof[2];
		        			var _ulH = $("#J_attrBoxUl_"+_attrid).height();
			        		if(_ulH >= 50){
			    				$("#J_attrBtnBox_"+_attrid+" .more-opt").show();
			    			}
		        		});
		    		}
		    	});
	        </script>
	        <style type="text/css">
	        	.brand-facet-default-status{}
	        	.brand-facet-default-status h2{display:none;position:relative;}
	        	.brand-facet-default-status h2 label{display:block;position:absolute;top:8px;left:35px;width:120px;height:20px;font-size:12px;font-weight:normal;z-index:1}
	        	.brand-facet-default-status .item-btns{display:none}
	        	.brand-item-box ul{overflow:hidden;height:auto}
	        	.brandBoxMargin .noResultTipLi{width:80%;text-align:center;padding-top:30px;}
	        	.multiple-item2-box{padding-right:110px}
	        	.attrBoxMargin{overflow:hidden;height:24px;_height:0;zoom:1}
	        	.attrBoxMarginAutoHeight{height:auto !important}
	        </style>
	        <#assign index=0 />
	        <#assign moreBtnTittle="" />
	        <#if (attrFacetBoList)??>
	        <#assign urlTemp="/ebay-brand-action.htm?cat=${(fatParams.catalogId)!''}&p_code=${(fatParams.providerCode)!''}&mkt=${(params.mkt)!''}&s_id=${(s_id)!''}&f_price=${(fatParams.priceRangeEnum.order)!''}&s_price=${(fatParams.startPrice)!''}&e_price=${(fatParams.endPrice)!''}&bid=${(params.bid)!''}&sort=${fatParams.sort.sortOrder}&vm=${params.vm!''}&channel=${(params.channel)!''}">
	        <#list attrFacetBoList as attrFacet>
	        	<#assign index=index+1 />
	        	<#if index gt 1 && index lt 4>
	        		<#if moreBtnTittle!="">
	        			<#assign moreBtnTittle="${moreBtnTittle}、" />
	        		</#if>
	        		<#assign moreBtnTittle="${moreBtnTittle}${attrFacet.facetDisplay}" />
	        	</#if>
	        	
	        	<!-- 价格 -->
		        <#if attrFacet.facetType == 'PRICE'>
		        <#assign urlTemp1="/ebay-brand-action.htm?bid=${(params.bid)!''}&cat=${(fatParams.catalogId)!''}&filter=${(fatParams.filter)!''}&ba=${(params.ba)!0}&mkt=${(params.mkt)!''}&s_id=${(s_id)!''}&sort=${fatParams.sort.sortOrder}&vm=${params.vm!''}&channel=${(params.channel)!''}">
		        <div id="J_attrBox_-1" class="facet-line ${(index gt 1)?string('hide J_hideAttrCol','')}">
		            <label class="line-hd">价格：</label>
		            <div class="line-bd">
		                <div class="normal-item-box ${(index % 2==1)?string('','multiple-item2-box')} clearfix">
		                    <div class="facet-item-box">
		                        <ul class="clearfix">
		                        	<#assign clkstat="atype=smartnv|stype=list|sattr=${((selectedCatalog.catalogName)!'')?url}|clk_attr=${'价格'?url}|cat_va="/>
		                            <#list priceFacetBo.facetValueBos as valueFacet >
		                            <li><a clkstat="${clkstat}${valueFacet.name?url}" href="<@xurl value="${urlTemp1}&bid=${(params.bid)!''}&f_price=${valueFacet.id}&filter=${(fatParams.filter)!''}" />">
		                            ${valueFacet.name?html}</a></li>
		                            </#list>
		                        </ul>
		                    </div>
		                    
		                    <div id="J_pricerangefilter_box" class="pricerange">
		                    	
								<form id="J_pricerangefilter_form" action="<@xurl value="${urlTemp1}"/>" method="get" onsubmit="return LEON.exePriceRangeFilter(true);" autocomplete="off">
									<label>自定义：</label>
									<input type="hidden" name="cat" value="${(fatParams.catalogId)!''}"/>
									<input type="hidden" name="bid" value="${(params.bid)!''}"/>
									<input type="hidden" name="vm" value="${(params.vm)!''}"/>
									<input type="hidden" name="p_code" value="${(fatParams.providerCode)!''}"/>
									<input type="hidden" name="mkt" value="${(fatParams.mktType.type)!''}"/>
									<input id="J_finput_minprice" type="text" maxlength="7" name="s_price" value="${(fatParams.startPrice)!''}" class="txt-input vm"/>
									<span class="vm pl3 pr3">&ndash;</span>
									<input id="J_finput_maxprice" type="text" name="e_price" class="txt-input vm" maxlength="7" value="${(fatParams.endPrice)!''}"/>
									<button class="btn vm ml5" type="submit" onclick="LEON.exePriceRangeFilter();">确定</button>
								</form>
								<script>LEON.bindPriceRange();</script>
		                    </div>
		                    <#--	需求不明确，暂时注释不使用		William.zhang 20130507
		                    <a class="avo-multiple" href="#">多选</a>
		                    <a href="#" class="more-opt">更多</a>
		                    -->
		                </div>
		            </div>
		        </div>
				<!-- 价格结束 -->
	        	<#else>
	        	
	        	<input type="hidden" id="J_attrUrlInput_${attrFacet.facetId}" value="<@xurl value="${urlTemp}" />"/>
	        	<input type="hidden" id="J_attrFilterParamInput_${attrFacet.facetId}" value="${(fatParams.filter)!''}"/>
				<div id="J_attrBox_${attrFacet.facetId}" class="facet-line ${(index gt 1)?string('hide J_hideAttrCol','')}">
					<label class="line-hd">${attrFacet.facetDisplay}：</label>
					<div class="line-bd">
					    <div class="normal-item-box ${(index % 2==1)?string('','multiple-item2-box')} clearfix">
					        <div id="J_attrBoxMargin_${attrFacet.facetId}" class="attrBoxMargin">
					            <ul id="J_attrBoxUl_${attrFacet.facetId}" class="clearfix" data-attrid="${attrFacet.facetId}">
					            <#assign clkstat="atype=smartnv|stype=list|sattr=${((selectedCatalog.catalogName)!'')?url}|clk_attr=${attrFacet.facetDisplay?url}|cat_va="/>
			                    <#list attrFacet.facetValueBos as valueFacet >
			                    <li><a data-attrvalid="${valueFacet.id}" onclick="LEON.multiAttrSelectItemAction(event,this,'${attrFacet.facetId}','${valueFacet.id}')" clkstat="${clkstat}${valueFacet.name?url}" href="<@xurl value="${urlTemp}&filter=${(valueFacet.filter)!''}" />">
			                    <span>${valueFacet.name?html}</span><#-- <span>(${(valueFacet.itemCount)!'1'})</span>-->
			                    <b></b>
			                    </a></li>
			                    </#list>
			                    </ul>
					        </div>
					        <#if attrFacet.facetValueBos?? && attrFacet.facetValueBos?size gt 1>
					        <div id="J_attrBtnBox_${attrFacet.facetId}" class="">
						        <a onclick="LEON.multiAttrFoldAction('${attrFacet.facetId}')" href="javascript:void(0);" class="avo-multiple">多选</a>
						        <a onclick="LEON.attrFoldAction('${attrFacet.facetId}');" id="J_attrMoreBtn_${attrFacet.facetId}" class="more-opt hide" href="javascript:void(0);">展开</a>
					        </div>
					        </#if>
					    </div>
					</div>
					<div id="J_attrMultiOKCancelBtnBox_${attrFacet.facetId}" class="item-btns hide">
					    <a id="J_attrMultiOKBtn_${attrFacet.facetId}" onclick="LEON.multiAttrSubmitAction('${attrFacet.facetId}');" href="javascript:void(0);" class="sure no_change">确定</a>
					    <a onclick="LEON.multiAttrCancelAction('${attrFacet.facetId}');" href="javascript:void(0);" class="cancel">取消</a>
					</div>
				</div>
				</#if>
			</#list>
	        </#if>
			<#-- 其他(尺码，材料。。。。等等)结束 -->
		    </div>
			<#-- 底部按钮 --> 
			<style type="text/css">
				.more-item-box b{font-weight:normal}
				.more-item-box .min-opt{display:none;}
			--	.more-item-box i.up{display:none;}
				.more-open-item-box .min-opt{display:block}
			--	.more-open-item-box i.up{display:block}
				.more-open-item-box .more-opt{display:none}
			--	.more-open-item-box i.down{display:none}
			</style>
			<#if moreBtnTittle?? && moreBtnTittle!="">
			<div id="J_moreItemBox" class="more-item-box">
				<p></p>
				<a href="javascript:void(0);" onclick="LEON.moreOrMinAttrBoxOpt();" style="text-align:center;padding-right: 20px;"><b class="min-opt">精简选项</b><b class="more-opt">更多选项（${moreBtnTittle!''}等）</b><i id="J_moreStyle" class="down"></i><span class="l_bg"></span><span class="r_bg"></span></a>
			</div>
			</#if>
			<script>
				(function(){
	        		LEON.initMoreBtnStat();
	        	})();
	        </script>
			
			</div>
	        
	        
		</#if>
	</div>
</div>

<#--商品展示列表-->
<div class="xs-mainsection clearfix">
	<#-- 排序过滤区 -->
	<div class="xs-filterbox clearfix" style="width:1000px; margin:10px auto;">
		<div class="xs-toolbar">
            <div class="showmode">
                <#assign urlTemp="/ebay-brand-action.htm?bid=${(params.bid)!''}&cat=${(fatParams.catalogId)!''}&sort=${fatParams.sort.sortOrder}&f_price=${(fatParams.priceRangeEnum.order)!''}&s_price=${(fatParams.startPrice)!''}&e_price=${(fatParams.endPrice)!''}&filter=${(fatParams.filter)!''}&ba=${(params.ba)!''}&mkt=${(params.mkt)!''}&s_id=${(s_id)!''}&channel=${(params.channel)!''}">
                <#--
                <a class="${( params.vm?? && params.vm==1)?string('active','')}" href="<@xurl value="${urlTemp}&vm=1" />">列表<b></b>
                </a>
                <a class="${( !(params.vm??) || params.vm==0)?string('active','')}" href="<@xurl value="${urlTemp}&vm=0" />">大图 <b class="big"></b>
                </a>
                -->
            </div>
            <div class="xs-sort-bar">
                <#assign urlTemp="/ebay-brand-action.htm?bid=${(params.bid)!}&cat=${(fatParams.catalogId)!''}&f_price=${(fatParams.priceRangeEnum.order)!''}&s_price=${(fatParams.startPrice)!''}&e_price=${(fatParams.endPrice)!''}&vm=${params.vm!''}&ba=${(params.ba)!0}&mkt=${(params.mkt)!''}&s_id=${(s_id)!''}&channel=${(params.channel)!''}">
                <#assign clkstat="atype=sort|stype=brand|sattr=${(brandName!)?url}|sorttype=" >
                <dl id="J_sortBox" class="sort-box">
					<dd><a clkstat="${clkstat}11" href="<@xurl  value="${urlTemp!}&sort=11" />" class="nonearr ${(fatParams.sort=='AMOUNT_DESC')?string('active','')}">默认</a></dd>
	                <dd><a clkstat="${clkstat}${(fatParams.sort=='PRICE_ASC')?string('2','1')}" href="<@xurl  value="${urlTemp}&sort=${(fatParams.sort=='PRICE_ASC')?string('2','1')}" />" class="${(fatParams.sort=='PRICE_ASC' || fatParams.sort=='PRICE_DESC')?string('active','')}">价格<i class="${(fatParams.sort=='PRICE_DESC')?string('down','')}"></i></a></dd>
	                <dd><a clkstat="${clkstat}${(fatParams.sort=='ONSALE_TIME_DESC')?string('7','8')}" href="<@xurl  value="${urlTemp}&sort=${(fatParams.sort=='ONSALE_TIME_DESC')?string('7','8')}" />" class="${(fatParams.sort=='ONSALE_TIME_DESC'|| fatParams.sort=='ONSALE_TIME_ASC')?string('active','')}">上架时间<i class="${(fatParams.sort=='ONSALE_TIME_ASC')?string('','down')}"></i></a></dd>
                </dl>
            </div>
            <#--
            <div class="fh-box">
            	<#assign urlTemp="/ebay-brand-action.htm?bid=${(params.bid)!}&cat=${(fatParams.catalogId)!''}&f_price=${(fatParams.priceRangeEnum.order)!''}&s_price=${(fatParams.startPrice)!''}&e_price=${(fatParams.endPrice)!''}&vm=${params.vm!''}&ba=${(params.ba)!0}&mkt=${(params.mkt)!''}&s_id=${(s_id)!''}&sort=${(params.sort)!''}">
            	<label>发货方式：</label>
            	<input id="J_ItemShowType" type="hidden" value="<@xurl value="${urlTemp}&channel=aaaaaaaa" />" />
                <ul>
                	<li><input id="J_All" name="Itemshowtype" type="radio" value="2|3" onClick="shouItemshowtype('2|3')" checked = "checked">全部</li>
                	<li><input id="J_Foreign" name="Itemshowtype" type="radio" value="3" onClick="shouItemshowtype('3')">海外发货</li>
                	<li><input id="J_Domestic" name="Itemshowtype" type="radio" value="2" onClick="shouItemshowtype('2')">国内发货</li>
                </ul>
                
                <script>
                
                	var ItemShowTypeState = "${(params.channel)!''}";
                	
            		if(ItemShowTypeState == $("#J_Foreign").val()){
            			$("#J_Foreign").attr("checked","checked");
            		}else if(ItemShowTypeState == $("#J_Domestic").val()){
            			$("#J_Domestic").attr("checked","checked");
            		}else {
            			$("#J_All").attr("checked","checked");
            		}
            		
                	function shouItemshowtype(date){
                		var url = $("#J_ItemShowType").val();
                		url = url.replace(/aaaaaaaa/ig,date);
                		location.href = url;
                	}
                </script>
            </div>
            -->
        	<#if (page)??>
            <@xpage currentPage="${(page.pageNo)!0}" totalPage="${(page.pageCount)!0}" repQuery="bid=${params.bid!}" theme="1" />
            </#if> 
                <!--
                 <ul>
                    <li class="pagenum">1/100</li>
                    <li class="disable"><a href="javascript:void(0);" class="l_page ">上一页</a></li>
                    <li class=""><a href="#" class="r_page ">下一页</a></li>
                </ul>
                -->
    	</div>
	</div>
	<#-- 排序过滤区结束 -->
    <#-- 主体右侧部分 -->
    <div class="xs-section">
	    <div id="J_filterbox" class="xs-main">       
	        <#-- 商品列表 -->
	        <script>if(typeof latency_metric_ti=='object')latency_metric_ti.atf=new Date().getTime();</script>
			<div id="J_itemListBox" class="xs-itemsbox" style="padding-bottom:20px;">
	    		<#-- 列表模式和大图模式在一起 -->
	    		<#--<#if (params.vm)?? && params.vm==1>
	    		<ul class="xs-itemsul-detail clearfix">
	    			<#list goodsItemList as goods>
	    			<#assign clkstat="atype=itemclk|stype=brand|sattr=${(brandName!)?url}|clk_loc=${30*(page.pageNo-1)+(goods_index+1)}|itemid=${goods.urlId}">
		    		<li class="item" data-itemid="${goods.urlId}" >
		            	<ol>
		                	<li class="pic"><a  clkstat="${clkstat}" href="${((fatParams.mktType??)&&fatParams.mktType=='EBAY')?string(ebayUrl,itemUrl)}product/${goods.urlId}.shtml${((s_id??)&&s_id!='')?string('?s_id='+s_id!'','')}" target="_blank"><img original="${imagesUrl}${goods.imgUrl100}" /><#if goods.soldOut><b class="saleout-ico"></b></#if></a></li>
		                    <li class="tit"><a  clkstat="${clkstat}" href="${((fatParams.mktType??)&&fatParams.mktType=='EBAY')?string(ebayUrl,itemUrl)}product/${goods.urlId}.shtml${((s_id??)&&s_id!='')?string('?s_id='+s_id!'','')}" target="_blank">${goods.name?html!}</a></li>
		                    <li class="price">
		                    	<p class="mt10"><span class="showprice ">${goods.showPrice?string('#.##')}</span></p>
		                    	<#if goods.mktPrice gt goods.showPrice>
		                        <p><span class="delprice ">${goods.mktPrice?string('#.##')}</span></p>
		                        </#if>
		                    </li>
		                    <li class="btnbar">
		                    	<#if goods.buyNow>
		                    		<a clkstat="atype=immbuy|stype=brand|sattr=${((brandname!)?url)}|clk_loc=${30*(page.pageNo-1)+(goods_index+1)}|itemid=${goods.urlId}" target="_blank" href="${((fatParams.mktType??)&&fatParams.mktType=='EBAY')?string(ebayUrl,itemUrl)}product/${goods.urlId}.shtml${((s_id??)&&s_id!='')?string('?s_id='+s_id!'','')}" class="onsale-btn">立即购买</a>
		                    	</#if>
		                    	<#if goods.arrivalNotice>
		                    		<a  clkstat="${clkstat}" target="_blank" href="${((fatParams.mktType??)&&fatParams.mktType=='EBAY')?string(ebayUrl,itemUrl)}product/${goods.urlId}.shtml${((s_id??)&&s_id!='')?string('?s_id='+s_id!'','')}" class="offsale-btn">到货通知</a>
		                    	</#if>
                                <div class="ceshow" name="plinfo" id="plinfo${goods.urlId}" style="display:none;"></div>  
		                    </li>
		                </ol>
		            </li>
		            </#list>
	            </ul>
	    		<#else> -->
	    		<ul class="xs-itemsul-img clearfix">
        			<#list goodsItemList as goods>
	        		<#assign clkstat="atype=itemclk|stype=brand|sattr=${(brandName!)?url}|clk_loc=${30*(page.pageNo-1)+(goods_index+1)}|itemid=${goods.urlId}">
	            	<li class="item" data-itemid="${goods.urlId}" <#if goods_index % 4 ==3>style=" margin-right:0;"</#if> >
	                	<ol>
	                		<#if goods.soldOut>
	                			<img class="sq_icon" src="http://www.xiu.com/static/img/ebay/sq_icon.png">
	                		</#if>
	                    	<li class="pic"><a clkstat="${clkstat}" target="_blank" href="${((fatParams.mktType??)&&fatParams.mktType=='EBAY')?string(itemUrl,ebayUrl)}product/${goods.urlId}.shtml${((s_id??)&&s_id!='')?string('?s_id='+s_id!'','')}"><img original="${zsurl()}${goods.imgEbayUrl220}" /></a>
	                    	<#if goods.skuList?size gt 0>
		                    	<div class="sold_size">
		                    	<p class="sold_size_tit">可售尺码：</p>
	                                <ul class="sold_size_list clearfix J_sizeArea">
	                                	<#list goods.skuList as sku>
											<#if sku.qty == 0>
												<li class="ssl_item box"><span class="line-through">${sku.skuSize}</span></li>
											<#else>
												<li class="ssl_item"><span>${sku.skuSize}</span></li>
											</#if>
										</#list>
	                                </ul>
	                            </div>
                            </#if>
	                    	</li>
	                        <li class="tit"><a clkstat="${clkstat}" target="_blank" href="${((fatParams.mktType??)&&fatParams.mktType=='EBAY')?string(itemUrl,ebayUrl)}product/${goods.urlId}.shtml${((s_id??)&&s_id!='')?string('?s_id='+s_id!'','')}">${goods.name?html!}</a></li>
	                        <li class="price">
	                        	<span class="showprice ">${goods.showPrice?string('#.##')}</span>
	                        	<#if goods.mktPrice gt goods.showPrice>
	                            <span class="delprice ">${goods.mktPrice?string('#.##')}</span>
	                            </#if>
	                        </li>
	                        <!--<li class="btnbar">
	                        	<#if goods.buyNow>
	                        		<a clkstat="atype=immbuy|stype=brand|sattr=${(brandName!)?url}|clk_loc=${30*(page.pageNo-1)+(goods_index+1)}|itemid=${goods.urlId}" target="_blank" href="${((fatParams.mktType??)&&fatParams.mktType=='EBAY')?string(ebayUrl,itemUrl)}product/${goods.urlId}.shtml${((s_id??)&&s_id!='')?string('?s_id='+s_id!'','')}" class="onsale-btn">立即购买</a>
	                        	</#if>
	                        	<#if goods.arrivalNotice>
	                        		<a clkstat="${clkstat}" target="_blank" href="${((fatParams.mktType??)&&fatParams.mktType=='EBAY')?string(ebayUrl,itemUrl)}product/${goods.urlId}.shtml${((s_id??)&&s_id!='')?string('?s_id='+s_id!'','')}" class="offsale-btn">到货通知</a>
	                        	</#if>
	                        </li>-->
	                    </ol>
                        <div class="cl"></div>                  
                        
	                </li>
	                </#list>
	            </ul>
	    		<#--</#if> -->
	        </div>
	       	<script>LEON.goodsImageErrorMethod('${staticUrl}static/img/default/default.234_312.jpg');</script>
	        <script>if(typeof latency_metric_ti=='object')latency_metric_ti.cf=new Date().getTime();</script>
	        <#-- 商品列表结束 -->
	        <#-- 底部翻页 -->
	        <div id="J_bottomPageBox" class="xs-pagebar clearfix">
	            <@xpage currentPage="${(page.pageNo)!0}" totalPage="${(page.pageCount)!0}" repQuery="bid=${params.bid!}" pageParamName="p" />
	        </div>
	        <script>LEON.pageFormSubmitFilter();</script>
	        <#-- 底部翻页结束 -->
	    </div>
	    <script>
	    //商品选中效果
		jQuery(function(){
            var eventDom = jQuery(".xs-itemsul-img .item");
            var op = {'eventBoxClass':'sold_size','listClass':'ssl_item'};
            xiu.module.widget.imgMarkWidget.Instance(eventDom,op);
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
<#--商品展示列表结束-->
</div>
<#-- 主体部分结束 -->
<#-- 底部搜索 -->
<div class="xs-btm-search">
<form id="J_searchFormBottom" action="${searchUrl}s" method="get" autocomplete="off">
    <div class="searchbox">
        <div class="inputbg"><input id="J_searchInputBottom" name="kw" class="txt-input" type="text" value="${((params.kw)!)?html}" /></div>
        <button class="sbtn" type="submit"></button>
    </div>
</form>
</div>
<#--
<script>
 回到顶部
$(function(){LEON.initToTopBtn({backImg:'static/ebay/css/img/xtop-icon.png'});});
</script>
 -->
<#-- 百分点走秀推荐 -->
<div id="banner_BrowsingHistory" class="tj_box"></div>

<script type="text/javascript" src="http://xres.xiu.com/static/xclk-jsapi.js"></script>
<script type="text/javascript">
(function(){
<#--百分点异步加载@012.js-->
_BFD=window._BFD||{};_BFD.script=document.createElement("script");_BFD.script.setAttribute("src","http://www.xiustatic.com/static/js/thirdparty/bfd/zx_list_search_new.min.js");_BFD.script.setAttribute("type","text/javascript");_BFD.script.setAttribute("charset","utf-8");document.getElementsByTagName("head")[0].appendChild(_BFD.script);
<#--推荐引擎数据收集-->
$('#J_catalogTire').dataCollectMethod({siteId:1001,channelId:${(!(fatParams.mktType??) || (fatParams.mktType == 'XIU'))?string('1001','1002')}});
<#-- 点击流底部翻页@007 -->
$("#J_bottomPageBox,.Pagenum a,.xs-minpage a").click(function(){var c=$(this).text();var b=parseInt("${page.pageNo}");var d=parseInt("${page.pageCount}");if("\u4e0a\u4e00\u9875"==c){c=(b-1)>0?(b-1):1}else{if("\u4e0b\u4e00\u9875"==c){c=(b+1)>d?d:(b+1)}else{c=parseInt(c)}}if(!c){return}var a="atype=flip|stype=brand|sattr=${(brandName!)?url}|page_no=";XIU.Window.ctraceClick(a+c,"search")});
<#-- 点击流事件绑定 -->
$('#J_itemListBox,#J_sortBox,#J_crumbBar,#J_catalogTire,#J_facet_box').clkBindMethod();
})();

<#--百分点推荐回调函数-->
function showRecommend_hb(datas,req_id,bfd_id){
	LEON.bfdCreateRecommend(datas,"banner_BrowsingHistory",req_id,bfd_id);		
}

$(window).load(function(){
<#-- @abtest判断使用推荐引擎011.js -->
var abtest_ret=$.cookie("abtestRet");var cur_sid=$.cookie("JSESSIONID_WCS");if(abtest_ret&&cur_sid){var abtest_id=abtest_ret.split("|")[0];var ret_id=abtest_ret.split("|")[1];if(abtest_id===cur_sid){if(ret_id==="1"){var ab_id=18;LEON.bfdData({stype:"brand",sattr:"${(brandName!)?url}",categoryList:[decodeURIComponent("${(brandName!)?url}")],type:"brand",catId:"<#if ((fatParams??)&&(fatParams.catalogId??)&&(fatParams.catalogId>0))>${fatParams.catalogId!}<#else>${recommendCatIds!}</#if>",bId:"${(params.bid)!}",clientName:"${bfdClientName!}"})}else{var ab_id=19;LEON.xreData({clientName:"${bfdClientName!}",type:"brand",brandId:"${(params.bid)!}",catId:"${recommendCatIds!}",itemId:"${recommendItemIds!}",brandRemmendOn:true,stype:"brand",sattr:"${(brandName!)?url}",siteId:"1001",channelId:"1001"})}XIU.Window.ctraceClick("atype=exposuret|abproject_id=8|abitem_id=8|abverid="+ab_id,"recommend_engine")}else{recommendItem()}}else{recommendItem()}function recommendItem(){var a=18;$.ajax({url:"http://abtest.xiu.com/GetScriptJsonpServlet.js?_pid=8",dataType:"jsonp",jsonp:"callbackparam",success:function(e){var d=[];var f=$.cookie("JSESSIONID_WCS");d.push(f);d.push(e.name.substring(0,1));$.cookie("abtestRet",d.join("|"),{expires:30*3,path:"/",domain:".xiu.com"});try{if(e.name.substring(0,1)=="1"){LEON.bfdData({stype:"brand",sattr:"${(brandName!)?url}",categoryList:[decodeURIComponent("${(brandName!)?url}")],type:"brand",catId:"<#if ((fatParams??)&&(fatParams.catalogId??)&&(fatParams.catalogId>0))>${fatParams.catalogId!}<#else>${recommendCatIds!}</#if>",bId:"${(params.bid)!}",clientName:"${bfdClientName!}"})}else{a=19;LEON.xreData({clientName:"${bfdClientName!}",type:"brand",brandId:"${(recommenBrandIds)!}",catId:"${recommendCatIds!}",itemId:"${recommendItemIds!}",brandRemmendOn:true,stype:"brand",sattr:"${(brandName!)?url}",siteId:"1001",channelId:"1001"})}}catch(c){LEON.bfdData({stype:"brand",sattr:"${(brandName!)?url}",categoryList:[decodeURIComponent("${(brandName!)?url}")],type:"brand",catId:"<#if ((fatParams??)&&(fatParams.catalogId??)&&(fatParams.catalogId>0))>${fatParams.catalogId!}<#else>${recommendCatIds!}</#if>",bId:"${(params.bid)!}",clientName:"${bfdClientName!}"})}},error:function(){LEON.bfdData({stype:"brand",sattr:"${(brandName!)?url}",categoryList:[decodeURIComponent("${(brandName!)?url}")],type:"brand",catId:"<#if ((fatParams??)&&(fatParams.catalogId??)&&(fatParams.catalogId>0))>${fatParams.catalogId!}<#else>${recommendCatIds!}</#if>",bId:"${(params.bid)!}",clientName:"${bfdClientName!}"})}});XIU.Window.ctraceClick("atype=exposuret|abproject_id=8|abitem_id=8|abverid="+a,"recommend_engine")};
});
</script>


<#-- 百分点走秀推荐结束 -->
<#-- CMS页尾 -->
<#include "/resources/cms/static/web/M_footer.html" parse="false">
<#include "/views/common/ebay_bottom_static.ftl" parse="false">
</body>
</html>