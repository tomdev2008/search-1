<#function getItemUrl providerCode>
		<#if (providerCode='1528')>
			<#return itemUrl+'product/'>
		<#elseif (providerCode='ferragamo')>
			<#return ferragamoUrl>
		<#else>
			<#return itemUrl+'product/'>
		</#if>
</#function>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#if seoInfo??>
<title>${seoInfo.title?html}</title>
<meta name="keywords" content="${seoInfo.keywords?html }"/>
<meta name="description" content="${seoInfo.description?html}"/>
</#if>
<#include "/views/common/head_static.ftl" >

<script><#-- init info -->
$(document).ready(function(){$(".pic img").lazyload({placeholder:"http://www.xiu.com/static/img/default/default.234_312.jpg"});});

//下拉框您的位置该栏
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
<#-- CMS页头 start-->
<#include "/views/common/header_include.ftl" >
<#-- CMS页头  end-->
<script src="http://www.xiustatic.com/static/js/xiu/index201210/index.head.app.js?__=${xversion('/static/js/xiu/index201210/index.head.app.js')}" type="text/javascript"></script> 
<script src="http://www.xiustatic.com/static/build/js/xiu/module/widget/imgMarkWidget.min.js?__=${xversion('/static/build/js/xiu/module/widget/imgMarkWidget.min.js')}" type="text/javascript"></script>
<#-- 广告 start -->
<div class="xs-banner-topmid">
<#include "/resources/cms/static/show/search/001.shtml" parse="false">
</div>
<#-- 广告 end-->
<#-- 面包屑 -->
<div class="xs-crumb clearfix">
	<label class="hd">您的位置：</label>
	<ul id="J_crumbBar" class="tabs">
	    <li class="no_dropdown first"><a href="${wwwUrl}" clkstat="atype=breadclk|stype=list|sattr=${((selectedCatalog.catalogName)!'')?url}|bread_cat=${'走秀首页'?url}|bread_lv=1">走秀首页</a><i></i></li>
	    <#if selectCatalogPlaneList??>
	    <#assign clkstat="atype=breadclk|stype=list|sattr=${((selectedCatalog.catalogName)!'')?url}"/>
	    
	    <#assign catUrlTemp="/list-action.htm?vm=${(params.vm)!''}&p_code=${(fatParams.providerCode)!''}&s_id=${(s_id)!''}&itemshowtype=${(params.itemshowtype)!''}">
	        	    	
	        <#list selectCatalogPlaneList as catalog>
        		<li><a class="on" clkstat="${clkstat}|bread_cat=${catalog.catalogName?url}|bread_lv=${catalog_index+1}" href="<@xurl value="${catUrlTemp}&cat=${catalog.catalogId}" />">${catalog.catalogName}<em></em></a><#if catalog_has_next><i></i></#if>
        		<#if catalog_index == 0><!-- 1级分类兄弟 -->
        			<#if firstCataList??>
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
        	    	<#if selectedCatalogTree.childCatalog??>
                        <ul class="dropdown">
		        	    	<#list selectedCatalogTree.childCatalog as sc>
		        	    		<#if sc.display=='1'>
			        	    		<li>
		        	    			 	<#if sc.selected>
			        	    				<a clkstat="${clkstat}|bread_cat=${sc.catalogName?url}|bread_lv=2" href="javascript:void();" class="selected">${sc.catalogName}</a>
			        	    			<#else>
			        	    				<a clkstat="${clkstat}|bread_cat=${sc.catalogName?url}|bread_lv=2" href="<@xurl value="${catUrlTemp}&cat=${sc.catalogId}" />">${sc.catalogName}</a>
			        	    			</#if>
			        	    			<#if sc_has_next>|</#if>
		        	    			 </li>
		        	    		</#if>
	                        </#list>
                        </ul>
                    </#if>
        	    <#else><!-- 3级分类 兄弟-->
	        		<#list selectedCatalogTree.childCatalog as catalog>
		            	<#if catalog.selected && catalog.childCatalog?? && catalog.childCatalog?size gt 0>
	                        <ul class="dropdown">
	                        <#list catalog.childCatalog as catalog2>
	                        	<#if catalog2.display=='1'>
		                        	<li>
		        	    			 	<#if catalog2.selected>
			        	    				<a clkstat="${clkstat}|bread_cat=${catalog2.catalogName?url}|bread_lv=3" href="javascript:void();" class="selected">${catalog2.catalogName}</a>
			        	    			<#else>
			        	    				<a clkstat="${clkstat}|bread_cat=${catalog2.catalogName?url}|bread_lv=3" href="<@xurl value="${catUrlTemp}&cat=${catalog2.catalogId}" />">${catalog2.catalogName}</a>
			        	    			</#if>
			        	    			<#if catalog2_has_next>|</#if>
		        	    			 </li>
		        	    		</#if>
	                        </#list>
	                        </ul>
		            	</#if>
		            </#list>
	        	</#if>
	        	</li>
	        </#list>
	    </#if>
	</ul>
	<div class="totalcount">总共<span class="pl3 pr3 cred">${page.recordCount}</span>件商品</div>
	<b class="icarr"></b>
</div>
<#-- 面包屑结束-->     

<#-- 主体部分 -->
<div id="J_catalogTire" class="xs-facetbox">
<div id="J_facet_box"  class="facet-bd">
	<style type="text/css">
		.facet-line .line-hd{left:10px;width:60px;}
		.clearline{width:100%;clear:both;height:1px;line-height:0;font-size:0px;display:block}
		.xs-aside{overflow:hidden;}
	</style>
	<#if (fatParams.catalogId)??>
    	<#assign urlTemp="/list-action.htm?cat=${(fatParams.catalogId)!''}&p_code=${(fatParams.providerCode)!''}&mkt=${(fatParams.mktType.type)!''}&s_id=${(s_id)!''}&itemshowtype=${(params.itemshowtype)!''}&sort=${fatParams.sort.sortOrder}&vm=${params.vm!''}&channel=${(params.channel)!''}">
        <#-- 您已选择 -->
    	<#if selectFacetBoList??>
        <div class="facet-line nodot facet-select-all change_nopd">
            <label class="line-hd">您已选择：</label>
            <div class="line-bd">
                <div class="normal-item-box clearfix">
                    <div class="facet-item-box">
                        <ul class="clearfix">
                            <#assign urlTemp="/list-action.htm?cat=${(fatParams.catalogId)!''}&vm=${(params.vm)!''}&p_code=${(fatParams.providerCode)!''}&mkt=${(fatParams.mktType.type)!''}&s_id=${(s_id)!''}&itemshowtype=${(params.itemshowtype)!''}&sort=${fatParams.sort.sortOrder}&vm=${params.vm!''}&channel=${(params.channel)!''}">
                            <#list selectFacetBoList as selectFacetBo>
                            <#if selectFacetBo.facetType == 'BRAND'>
                            <li><a title="${selectFacetBo.facetDisplay}：${selectFacetBo.facetValueBosNamesForTitle?html}" href="<@xurl value="${urlTemp}&filter=${(fatParams.filter)!''}&f_price=${(fatParams.priceRangeEnum.order)!''}&s_price=${(fatParams.startPrice)!''}&e_price=${(fatParams.endPrice)!''}" />">${selectFacetBo.facetDisplay}：${selectFacetBo.facetValueBosNames?html}<b class="close-ico"></b></a></li>
                            <#elseif selectFacetBo.facetType == 'PRICE'>
                            <li><a href="<@xurl value="${urlTemp}&filter=${(fatParams.filter)!''}&bid=${(params.bid)!''}" />">${selectFacetBo.facetDisplay}：${selectFacetBo.facetValueBos[0].name?html}<b class="close-ico"></b></a></li>
                            <#else>
	                            <#if selectFacetBo?? && (selectFacetBo.facetValueBos)?? && (selectFacetBo.facetValueBos?size gt 0)>
	                            <li><a href="<@xurl value="${urlTemp}&bid=${(params.bid)!''}&f_price=${(fatParams.priceRangeEnum.order)!''}&filter=${(selectFacetBo.cancelFilter)!''}&s_price=${(fatParams.startPrice)!''}&e_price=${(fatParams.endPrice)!''}" />">${selectFacetBo.facetDisplay}：${selectFacetBo.facetValueBos[0].name?html}<b class="close-ico"></b></a></li>
	                            </#if>
                            </#if>
                            </#list>
                            <li class="reset"><a href="<@xurl value="${urlTemp}" />">重置筛选条件</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        </#if>
        
        <#if selectedCatalogTree?? && selectedCatalogTree.childCatalog?? && selectedCatalogTree.childCatalog?size gt 0 && selectCatalogPlaneList?? && selectCatalogPlaneList?size lt 3>
	        <#assign urlTemp="/list-action.htm?vm=${(params.vm)!''}&p_code=${(fatParams.providerCode)!''}&s_id=${(s_id)!''}&itemshowtype=${(params.itemshowtype)!''}">
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
		                            	<#if catalog.display=='1'>
			                            	<li><a data-catalogid="${catalog.catalogId}" dataCollect="type=list|id=${(catalog.catalogId)!}" clkstat="${clkstat!},${catalog.catalogName?url}|cat_lv=2" href="<@xurl value='${urlTemp}&cat=${catalog.catalogId}' />" class="${catalog.selected?string('active','')}" >
			                            	${catalog.catalogName?html}
			                            	</a></li>
			                            </#if>
		                            </#list>
		                        </ul>
		                    </div>
		                </div>
		            </div>
		        </div>
		    <#else>
		    <!-- 显示3级分类 -->
		        <#list selectedCatalogTree.childCatalog as catalog>
		        	<#if catalog.selected && catalog.display=='1'>
		        		<div class="facet-line brand-facet-box fl_box">
				            <label class="line-hd">分类：</label>
				            <div class="line-bd">
				                <div class="normal-item-box clearfix">
				                    <div>
				                        <ul id="J_catSecondItem" class="clearfix">
				                        	<input type="hidden" id="J_selectCatalogId" value="${catalog.catalogId}" />
							                <#list catalog.childCatalog as catalog2>
							                	<#if catalog2.display=='1'>
								                    <li><a dataCollect="type=list|id=${(catalog2.catalogId)!}" href="<@xurl value='${urlTemp}&cat=${catalog2.catalogId}' />" class="${(catalog2.selected)?string('active','')}">
													<#if (catalog2.catalogName?length>11)>
								                        ${catalog2.catalogName[0..11]?default("") + "..."}
								                    <#else>
								                        ${catalog2.catalogName?default("")}
								                    </#if>
													</a></li>
												</#if>
							                </#list>
				                        </ul>
				                    </div>
				                </div>
				            </div>
				        </div>
		        	</#if>
		        </#list>
	        </#if>
        </#if>
        
        <!-- 品牌 -->        
        <#if (facetFilterValueBoList)?? && (brandFacetBo)??>
        <script>
        	//页面品牌搜索 William.zhang 20130506
			var brandData = ${facetFilterValueBoJSON};
			if(typeof LEON == 'undefined')LEON={};
			var _unfoldFlag = false;
			var _multiSelect = false;
			var _hasSelectBrands = {};
			$.extend(LEON,{
	    		brandFoldAction : function(unfload){
	    			var __unfload = (typeof unfload != 'undefined') ? !!unfload : _unfoldFlag;
	    			if(!__unfload){
	    				$('#J_brandBoxMargin').css({"height":"170px","overflow-y":"auto","overflow-x":"hidden"});
	    				$('#J_brandSearchBar').show();
	    				$('#J_brandMoreBtn').addClass('down');
	    				$('#J_brandMoreBtn').empty();
	    				$('#J_brandMoreBtn').html("收起");
	    				_unfoldFlag = true;
	    				_multiSelect = false;
					}else{
						$('#J_brandBoxMargin').css({"height":"74px","overflow":"hidden","overflow-y":"hidden"});
						$('#J_brandSearchBar').hide();
						$('#J_brandMoreBtn').removeClass('down');
						$('#J_brandMoreBtn').empty();
	    				$('#J_brandMoreBtn').html("展开");
						_unfoldFlag = false;
						_multiSelect = false;
					}
					$("#J_brandBoxUl").show();
					$("#J_brandBoxMatchUl").hide();
	    		},
	    		multiFoldAction : function(){
	    			$('#J_brandBtnBox').hide();
	    			$('#J_brandMultiOKCancelBtnBox').show();
	    			LEON.brandFoldAction(false);
	    			_multiSelect = true;
	    		},
	    		multiSelectItemAction : function(e,obj){
	    			if(_multiSelect){
	    				$.stopDefault(e);
	    			}else{
	    				return;
	    			}
	    			var _this = $(obj);
	    			var brandid = _this.data('brandid');
	    			if(_hasSelectBrands[brandid]){
	    				_this.removeClass('active');
	    				delete _hasSelectBrands[brandid];
	    			}else{
	    				_this.addClass('active');
	    				_hasSelectBrands[brandid]=true;
	    			}
	    			var size = 0;
	    			for (key in _hasSelectBrands) {
				        if (_hasSelectBrands.hasOwnProperty(key)) size++;
				    }
	    			if(size > 0){
	    				$('#J_brandMultiOKBtn').removeClass('no_change');
	    			}else{
	    				$('#J_brandMultiOKBtn').addClass('no_change');
	    			}
	    		},
	    		multiSubmitAction : function(){
	    			var _keys = [];
	    			for (key in _hasSelectBrands) {
				        if (_hasSelectBrands.hasOwnProperty(key)) _keys.push(key);
				    }
	    			if(_keys.length == 0){
	    				return;
	    			}
	    			var _brandIds = [];
	    			for(var k in _keys){
	    				_brandIds.push(_keys[k]);
	    			}
	    			var _bIdStr = _brandIds.join('|');
	    			var _url = $('#J_brandUrlInput').val();
	    			_url = _url.replace(/xsearchplaceholderbrandids/ig,_bIdStr);
	    		//	XIU.Window.ctraceClick($('#J_clkstatForBrand').val()+_bIdStr,"search");
	    			location.href = _url;
	    		},
	    		multiCancelAction : function(){
	    			$('#J_brandMultiOKCancelBtnBox').hide();
	    			$('#J_brandBtnBox').show();
	    			LEON.brandFoldAction();
	    			_multiSelect = false;
	    			$('#J_brandBoxUl a').each(function(){
	    				var _this = $(this);
	    				if(_this.hasClass('active')){
		    				_this.removeClass('active');
		    			}
	    			});
	    			_hasSelectBrands={};
	    			$('#J_brandMultiOKBtn').addClass('no_change');
	    		},
	    		brandSearchAction : function(){
	    			_hasSelectBrands = {};
	    			$('#J_brandMultiOKBtn').addClass('no_change');
	    			var _input = $("#J_brandSearchInput").val().toUpperCase();
					if(_input.length == 0){
						$("#J_brandBoxUl").show();
						$("#J_brandBoxMatchUl").hide();
						return;
					}
					$("#J_brandBoxUl").hide();
					$("#J_brandBoxMatchUl").show();
					var _oriUrl = $('#J_brandUrlInput').val();
					var _url;
					var str;
					var list = '';
					var matchResult = [];
					var _brand;
					for(var n in brandData){
						_brand = brandData[n];
						if((str = _brand.name)
							&& str.indexOf(_input) >=0){
								matchResult.push(_brand);
						}else if((str = _brand.pyName)
							&& str.indexOf(_input) >=0){
							matchResult.push(_brand);
						}else if((str = _brand.enName)
							&& str.indexOf(_input) >=0){
							matchResult.push(_brand);
						}else if((str = _brand.cnName)
							&& str.indexOf(_input) >=0){
							matchResult.push(_brand);
						}
					}
					if(matchResult.length > 0){
						for(var k in matchResult){
							if($("#multipleSelect").css("display") != 'none'){
								_url = _oriUrl.replace(/xsearchplaceholderbrandids/ig,matchResult[k].id);
								list += '<li class="li1"><a data-brandid="'+matchResult[k].id+'" onclick="LEON.multiSelectItemAction(event,this);" href="'+_url+'"><span>'+matchResult[k].name+'</span><b></b></a></li>';
							}else{
								list += "<li><a><span>"+matchResult[k].name+"</span><b></b></a></li>";
							}
						}
					}else{
						list += "<li class=\"noResultTipLi\">未找到该品牌</li>"
					}
					$("#J_brandBoxMatchUl").empty();
					$("#J_brandBoxMatchUl").html(list);
	    		}
	    	});
        </script>
        <style type="text/css">
        	.brand-facet-default-status{}
        	.brand-facet-default-status h2{display:none;position:relative;}
        	.brand-facet-default-status h2 label{display:block;position:absolute;top:8px;left:35px;width:120px;height:20px;font-size:12px;font-weight:normal;z-index:1}
        	.brand-facet-default-status .item-btns{display:none}
        	.brand-item-box ul{overflow:hidden;height:auto}
        	.brandBoxMargin{overflow:hidden;height:74px;zoom:1}
        	.brandBoxMargin .noResultTipLi{width:80%;text-align:center;padding-top:30px;}
        </style>
        <#-- 品牌 start -->
        <div class="facet-line brand-facet-box brand-facet-default-status">
            <label class="line-hd ">品牌：</label>
            <div class="line-bd">
                <div id="J_itemboxbrand" class="brand-item-box multiple-item-box">
                	<h2 id="J_brandSearchBar"><label id="J_brandSearchLabel" for="J_brandSearchInput">搜索品牌名称</label><input type="text" id="J_brandSearchInput" onkeyup="LEON.brandSearchAction();return true;"></h2>
                	<#assign urlTemp="/list-action.htm?cat=${(fatParams.catalogId)!''}&vm=${(params.vm)!''}&s_price=${(fatParams.startPrice)!''}&e_price=${(fatParams.endPrice)!''}&f_price=${(fatParams.priceRangeEnum.order)!''}&filter=${(fatParams.filter)!''}&p_code=${(fatParams.providerCode)!''}&mkt=${(fatParams.mktType.type)!''}&s_id=${(s_id)!''}&itemshowtype=${(params.itemshowtype)!''}&sort=${fatParams.sort.sortOrder}&vm=${params.vm!''}&channel=${(params.channel)!''}">
                	<#assign clkstat="atype=smartnv|stype=list|sattr=${((selectedCatalog.catalogName)!'')?url}|clk_attr=${'品牌'?url}|cat_va="/>
                	<input id="J_clkstatForBrand" type="hidden" value="${clkstat}" />
                	<input id="J_brandUrlInput" type="hidden" value="<@xurl value="${urlTemp}&bid=xsearchplaceholderbrandids" />" />
                	<div id="J_brandBoxMargin" class="brandBoxMargin">
	                    <ul id="J_brandBoxUl">
	                    	<#list facetFilterValueBoList as facetFilterValueBo >
		                    	<li>
			                    	<a clkstat="${clkstat}${facetFilterValueBo.name?url}" dataCollect="type=brand|id=${(facetFilterValueBo.id)!}" title="${facetFilterValueBo.name?html}" data-brandid="${facetFilterValueBo.id}" onclick="LEON.multiSelectItemAction(event,this);" href="<@xurl value="${urlTemp}&bid=${facetFilterValueBo.id}" />">
			                    		<span>${facetFilterValueBo.name?html}</span><b></b>
			                    	</a>
		                    	</li>
	                    	</#list>
	                    </ul>
	                    <ul id="J_brandBoxMatchUl"></ul>
                   </div>
               </div>
                <#--
                <a id="J_multSelectBtn" href="javascript:void(0);" class="avo-multiple">多选</a>
                -->
                <#if facetFilterValueBoList?? &&facetFilterValueBoList?size gt 1>
                <div id="J_brandBtnBox" >
                	<a id="J_multiSelectBrandBtn" href="javascript:void(0);" onclick="LEON.multiFoldAction();" class="avo-multiple">多选</a>
                	<#if facetFilterValueBoList?? &&facetFilterValueBoList?size gt 10>
                	<a id="J_brandMoreBtn" href="javascript:void(0);" onclick="LEON.brandFoldAction();" class="more-opt up">展开</a>
                	</#if>
                </div>
                </#if>
                <div id="J_brandMultiOKCancelBtnBox" class="item-btns" style="display:none">
                	<a id="J_brandMultiOKBtn" href="javascript:void(0);" onclick="LEON.multiSubmitAction();" class="sure no_change">确定</a>
                    <a href="javascript:void(0);" onclick="LEON.multiCancelAction();" class="cancel">取消</a>
                </div>
            </div>
            <script>
            	(function(){
            		var _ulH = $("#J_brandBoxUl").height();
            		if($.browser.msie && $.browser.version == 6.0){
            			if(_ulH >= 70){
            				$("#J_brandBoxMargin").height(74);
            			}
            		}
            		if(_ulH >= 70){
        				$("#J_brandBtnBox").show();
        			}
            		$("#J_brandSearchInput").focus(function(){
            			$("#J_brandSearchLabel").hide();
            		}).blur(function(){
            			if($(this).val().length == 0)
            			$("#J_brandSearchLabel").show();
            		})
            	})();
            </script>
        </div>
        <#-- 品牌end -->
        </#if>
        <div class="clearline"></div>
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
	    			var attrvalid = _this.attr('data-attrvalid');
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
        	.attrBoxMargin{overflow:hidden;height:24px;zoom:1}
        	.attrBoxMarginAutoHeight{height:auto !important}
        </style>
        <#assign index=0 />
        <#assign moreBtnTittle="" />
        <#if (attrFacetBoList)??>
        <#assign urlTemp="/list-action.htm?cat=${(fatParams.catalogId)!''}&s_price=${(fatParams.startPrice)!''}&e_price=${(fatParams.endPrice)!''}&f_price=${(fatParams.priceRangeEnum.order)!''}&bid=${(params.bid)!''}&vm=${(params.vm)!''}&p_code=${(fatParams.providerCode)!''}&s_id=${(s_id)!''}&itemshowtype=${(params.itemshowtype)!''}&sort=${fatParams.sort.sortOrder}&vm=${params.vm!''}&channel=${(params.channel)!''}">
        <#list attrFacetBoList as attrFacet>
        	<#assign index=index+1 />
        	<#if index gt 1 && index lt 4>
        		<#if moreBtnTittle!="">
        			<#assign moreBtnTittle="${moreBtnTittle}、" />
        		</#if>
        		<#assign moreBtnTittle="${moreBtnTittle}${attrFacet.facetDisplay}" />
        	</#if>
	        <#if attrFacet.facetType == 'PRICE'>
        	<!-- 价格 start-->
		        <#assign urlTemp1="/list-action.htm?cat=${(fatParams.catalogId)!''}&bid=${(params.bid)!''}&filter=${(fatParams.filter)!''}&vm=${(params.vm)!''}&p_code=${(fatParams.providerCode)!''}&s_id=${(s_id)!''}&itemshowtype=${(params.itemshowtype)!''}&sort=${fatParams.sort.sortOrder}&vm=${params.vm!''}&channel=${(params.channel)!''}">
		        <div id="J_attrBox_-1" class="facet-line ${(index gt 1)?string('hide J_hideAttrCol','')}">
		            <label class="line-hd">价格：</label>
		            <div class="line-bd">
		                <div class="normal-item-box ${(index % 2==1)?string('','multiple-item2-box')} clearfix">
		                    <div class="facet-item-box">
		                        <ul class="clearfix">
		                        	<#assign clkstat="atype=smartnv|stype=list|sattr=${((selectedCatalog.catalogName)!'')?url}|clk_attr=${'价格'?url}|cat_va="/>
		                            <#list priceFacetBo.facetValueBos as valueFacet >
		                            <li><a clkstat="${clkstat}${valueFacet.name?url}" href="<@xurl value="${urlTemp1}&f_price=${valueFacet.id}" />">
		                            ${valueFacet.name?html}</a></li>
		                            </#list>	                           
		                        </ul>
		                    </div>
		                    <div id="J_pricerangefilter_box" class="pricerange">
								<form id="J_pricerangefilter_form" action="<@xurl value="${urlTemp1}"/>" method="get" onsubmit="return LEON.exePriceRangeFilter(true);" autocomplete="off">
									<label>自定义：</label>
									<input type="hidden" name="kw" value="${searchKW?html}"/>
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
				<!-- 价格以下 刷选项（页面默认隐藏）-->
				<div id="J_attrBox_${attrFacet.facetId}" class="facet-line ${(index gt 1)?string('hide J_hideAttrCol','')}">
					<#assign clkstat="atype=smartnv|stype=list|sattr=${((selectedCatalog.catalogName)!'')?url}|clk_attr=${attrFacet.facetDisplay?url}|cat_va="/>
					<input id="J_clkstatForItem" type="hidden" value="${clkstat}"/>
					 <!--筛选项 尺码 颜色。。-->
					<label class="line-hd">${attrFacet.facetDisplay}：</label> 
					<div class="line-bd">
					    <div class="normal-item-box ${(index % 2==1)?string('','multiple-item2-box')} clearfix">
					        <div id="J_attrBoxMargin_${attrFacet.facetId}" class="attrBoxMargin">
					            <ul id="J_attrBoxUl_${attrFacet.facetId}" class="clearfix" data-attrid="${attrFacet.facetId}">
			                     
			                      <#list attrFacet.facetValueBos as valueFacet >
			                        <li>
				                        <a data-attrvalid="${valueFacet.id}" onclick="LEON.multiAttrSelectItemAction(event,this,'${attrFacet.facetId}','${valueFacet.id}')" clkstat="${clkstat}${valueFacet.name?url}" href="<@xurl value="${urlTemp}&filter=${(valueFacet.filter)!''}" />">
				                        <span>${valueFacet.name?html}</span><#-- <span>(${(valueFacet.itemCount)!'1'})</span>-->
				                        <b></b>
				                        </a>
			                        </li>
			                     </#list>
			                    </ul>
					        </div>
					        <#if attrFacet.facetValueBos?? && attrFacet.facetValueBos?size gt 1>
					        <div id="J_attrBtnBox_${attrFacet.facetId}" class="">
						        <a onclick="LEON.multiAttrFoldAction('${attrFacet.facetId}')" href="javascript:void(0);" class="avo-multiple">多选</a>
						        <a onclick="LEON.attrFoldAction('${attrFacet.facetId}');" id="J_attrMoreBtn_${attrFacet.facetId}" class="more-opt hide" href="javascript:void(0);">展开</a>
					        </div>
					        </#if>
					        <#-- 
					        <div id="J_attrBtnBox_${attrFacet.facetId}">
						        <a onclick="LEON.multiAttrFoldAction('${attrFacet.facetId}')" href="javascript:void(0);" class="avo-multiple">多选</a>
					        </div>
					        -->
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
		<#-- 其他筛选项(尺码，颜色。。。。等等)结束 -->
	    </div>
		<#-- 筛选项底部按钮 （展开 缩小）--> 
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
			<a href="javascript:void(0);" onclick="LEON.moreOrMinAttrBoxOpt();" style="text-align:center;padding-right: 20px;" ><b class="min-opt">精简选项</b><b class="more-opt">更多选项（${moreBtnTittle!''}等）</b><i id="J_moreStyle" class="down"></i><span class="l_bg"></span><span class="r_bg"></span></a>
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

<div class="xs-mainsection clearfix">
<#-- 排序过滤区 -->
	<div class="xs-filterbox clearfix">
		<div class="xs-toolbar">
            <div class="showmode">
                <#assign urlTemp="/list-action.htm?cat=${(fatParams.catalogId)!''}&bid=${(params.bid)!''}&sort=${fatParams.sort.sortOrder}&f_price=${(fatParams.priceRangeEnum.order)!''}&s_price=${(fatParams.startPrice)!''}&e_price=${(fatParams.endPrice)!''}&filter=${(fatParams.filter)!''}&ba=${(params.ba)!''}&p_code=${(fatParams.providerCode)!''}&mkt=${(fatParams.mktType.type)!''}&s_id=${(s_id)!''}&itemshowtype=${(params.itemshowtype)!''}&channel=${(params.channel)!''}">
            </div>
            <div class="xs-sort-bar">
                <#assign urlTemp="/list-action.htm?cat=${(fatParams.catalogId)!''}&bid=${(params.bid)!''}&vm=${(params.vm)!''}&f_price=${(fatParams.priceRangeEnum.order)!''}&s_price=${(fatParams.startPrice)!''}&e_price=${(fatParams.endPrice)!''}&filter=${(fatParams.filter)!''}&ba=${(params.ba)!''}&p_code=${(fatParams.providerCode)!''}&mkt=${(fatParams.mktType.type)!''}&s_id=${(s_id)!''}&itemshowtype=${(params.itemshowtype)!''}&channel=${(params.channel)!''}">
                <#assign clkstat="atype=sort|stype=list|sattr=${((selectedCatalog.catalogName)!'')?url}|sorttype=" >
                <dl id="J_sortBox" class="sort-box">
                    <#if params.bid?? || fatParams.filter??>
                    	<dd><a rel="nofollow" clkstat="${clkstat}11" href="<@xurl value="${urlTemp}&sort=11" />" class="nonearr ${(fatParams.sort=='AMOUNT_DESC')?string('active','')}">默认</a></dd>
                    <#else>
                    	<dd><a rel="nofollow" clkstat="${clkstat}11" href="<@xurl value="${urlTemp}" />" class="nonearr ${(fatParams.sort=='AMOUNT_DESC')?string('active','')}">默认</a></dd>
                    </#if>
                    <dd><a rel="nofollow" clkstat="${clkstat}${(fatParams.sort=='PRICE_ASC')?string('2','1')}" href="<@xurl  value="${urlTemp}&sort=${(fatParams.sort=='PRICE_ASC')?string('2','1')}" />" class="${(fatParams.sort=='PRICE_ASC' || fatParams.sort=='PRICE_DESC')?string('active','')}">价格<i class="${(fatParams.sort=='PRICE_DESC')?string('down','')}"></i></a></dd>
                    <dd><a rel="nofollow" clkstat="${clkstat}${(fatParams.sort=='DISCOUNT_DESC')?string('3','4')}" href="<@xurl  value="${urlTemp}&sort=${(fatParams.sort=='DISCOUNT_DESC')?string('3','4')}" />" class="${(fatParams.sort=='DISCOUNT_DESC' || fatParams.sort=='DISCOUNT_ASC')?string('active','')}">折扣<i class="${(fatParams.sort=='DISCOUNT_ASC')?string('','down')}"></i></a></dd>
                    <dd><a rel="nofollow" clkstat="${clkstat}${(fatParams.sort=='ONSALE_TIME_DESC')?string('7','8')}" href="<@xurl  value="${urlTemp}&sort=${(fatParams.sort=='ONSALE_TIME_DESC')?string('7','8')}" />" class="${(fatParams.sort=='ONSALE_TIME_ASC' || fatParams.sort=='ONSALE_TIME_DESC')?string('active','')}">上架时间<i class="${(fatParams.sort=='ONSALE_TIME_ASC')?string('','down')}"></i></a></dd>
                </dl>
            </div>
            <#if ((params.itemshowtype)!'')?trim!='2' && ((params.itemshowtype)!'')?trim!='3'>
           	<div class="fh-box">
            	<#assign urlTemp="/list-action.htm?cat=${(fatParams.catalogId)!''}&bid=${(params.bid)!''}&vm=${(params.vm)!''}&f_price=${(fatParams.priceRangeEnum.order)!''}&s_price=${(fatParams.startPrice)!''}&e_price=${(fatParams.endPrice)!''}&filter=${(fatParams.filter)!''}&ba=${(params.ba)!''}&p_code=${(fatParams.providerCode)!''}&mkt=${(fatParams.mktType.type)!''}&s_id=${(s_id)!''}&itemshowtype=${(params.itemshowtype)!''}&sort=${(params.sort)!''}">
            	<label>发货方式：</label>
            	<input id="J_ItemShowType" type="hidden" value="<@xurl value="${urlTemp}&channel=aaaaaaaa" />" />
                <ul>
                	<li><label for="J_All"><input id="J_All" name="Itemshowtype" type="radio" onClick="shouItemshowtype('')" checked = "checked">全部 </label></li>
                	<li><label for="J_Foreign"><input id="J_Foreign" name="Itemshowtype" type="radio" value="3" onClick="shouItemshowtype('3')">海外发货</label></li>
                	<li><label for="J_Domestic"><input id="J_Domestic" name="Itemshowtype" type="radio" value="2" onClick="shouItemshowtype('2')">国内发货</label></li>
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
            </#if>
            	<@xpage currentPage="${(page.pageNo)!0}" totalPage="${(page.pageCount)!0}" repQuery="mkt=${(fatParams.mktType.type)!''}" theme="1" />
        </div>
	</div>
	<#-- 排序 发货方式过滤区结束 -->
    <#-- 主体右侧部分 -->
    <div class="xs-section">
	    <div id="J_filterbox" class="xs-main">       
	        <#-- 商品列表 -->
	        <script>if(typeof latency_metric_ti=='object')latency_metric_ti.atf=new Date().getTime();</script>
			<div id="J_itemListBox" class="xs-itemsbox" style="padding-bottom:20px;">
	    		<ul class="xs-itemsul-img clearfix">
	        		<#list goodsItemList as goods>
	        		<#assign clkstat="atype=itemclk|stype=list|sattr=${((selectedCatalog.catalogName)!'')?url}|clk_loc=${30*(page.pageNo-1)+(goods_index+1)}|itemid=${goods.urlId}">
	            	<li class="item" data-itemid="${goods.urlId}" <#if goods_index % 4 ==3>style=" margin-right:0;"</#if> >
	                	<ol>
	                    	<li class="pic"><a clkstat="${clkstat}" goods_sn="${goods.xiuSn}" href="${getItemUrl(goods.providerCode!)}${goods.urlId}.shtml${((s_id??)&&s_id!='')?string('?s_id='+s_id!'','')}" target="_blank"><img alt="${goods.name}" original="${zsurl()}${goods.imgUrl220}" /><#if goods.soldOut><b class="saleout-ico"></b></#if>
	                    	</a>
	                    	<#if goods.skuList?size gt 0>
		                    	<div class="sold_size">
		                    		<p class="sold_size_tit">可售尺码：</p>
	                                <ul class="sold_size_list clearfix J_sizeArea">
	                                	<#list goods.skuList as sku>
											<#if sku.qty == 0> <!--库存为0的尺码-->
												<li class="ssl_item box"><span class="line-through">${sku.skuSize}</span></li>
											<#else>
												<li class="ssl_item"><span>${sku.skuSize}</span></li>
											</#if>
										</#list>
	                                </ul>
	                            </div>
                            </#if>
                            </li>
	                        <li class="tit">
	                          <#--品牌简化-->
	                          <span>
		                          <#if goods.brandNameEn ?exists>
		                             ${goods.brandNameEn}
		                           <#else>
		                             ${goods.brandNameCn ? default('')}
		                          </#if>
	                          </span>
	                          <a clkstat="${clkstat}" href="${getItemUrl(goods.providerCode!)}${goods.urlId}.shtml${((s_id??)&&s_id!='')?string('?s_id='+s_id!'','')}" target="_blank">${goods.nameHighlight}</a>
	                        </li>
	                        <li class="price">
	                        	<span class="showprice ">${goods.showPrice?string('#.##')}</span>
	                        	<#if goods.mktPrice gt goods.showPrice>
	                            <span class="delprice ">${goods.mktPrice?string('#.##')}</span>
	                            </#if>
	                        </li>
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
	            <@xpage currentPage="${(page.pageNo)!0}" totalPage="${(page.pageCount)!0}" repQuery="mkt=${(fatParams.mktType.type)!''}" pageParamName="p" />
	        </div>
	        <script>LEON.pageFormSubmitFilter();</script>
	        <#-- 底部翻页结束 -->
	    </div>
	   	<script>
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
(function(){
<#--百分点异步加载@012.js-->	
_BFD=window._BFD||{};_BFD.script=document.createElement("script");_BFD.script.setAttribute("src","http://www.xiustatic.com/static/js/thirdparty/bfd/zx_list_search_new.min.js");_BFD.script.setAttribute("type","text/javascript");_BFD.script.setAttribute("charset","utf-8");document.getElementsByTagName("head")[0].appendChild(_BFD.script);
<#--推荐引擎数据收集-->
$('#J_catalogTire,#J_itemboxbrand').dataCollectMethod({siteId:1001,channelId:${(!(fatParams.mktType??) || (fatParams.mktType == 'XIU'))?string('1001','1002')}});
<#-- 点击流底部翻页@007 -->
$("#J_bottomPageBox,.Pagenum a,.xs-minpage a").click(function(){var c=$(this).text();var b=parseInt("${page.pageNo}");var d=parseInt("${page.pageCount}");if("\u4e0a\u4e00\u9875"==c){c=(b-1)>0?(b-1):1}else{if("\u4e0b\u4e00\u9875"==c){c=(b+1)>d?d:(b+1)}else{c=parseInt(c)}}if(!c){return}var a="atype=flip|stype=list|sattr=${((selectedCatalog.catalogName)!'')?url}|page_no=";XIU.Window.ctraceClick(a+c,"search")});
<#-- @001.js
onGetGoodsInfo=function(h,k,f,p){if(!k){return}var r=h.length,b=LEON.bfdData.setting.itemVisitNum;if(r==0){return}var t=p==1?"\u8d2d\u4e70\u6392\u884c":"\u6d4f\u89c8\u6392\u884c";var l=p==1?"buytop":"viewtop";var o=!!LEON.bfdData.setting.stype?LEON.bfdData.setting.stype:"kw";var d=!!LEON.bfdData.setting.sattr?LEON.bfdData.setting.sattr:"";var m="atype="+l+"|stype="+o+"|sattr="+d+"|itemid=";if(p==1){t="\u8d2d\u4e70\u6392\u884c";b=LEON.bfdData.setting.itemSaleNum}var q=0,b=r>b?b:r;var s=$('<div class="hd"><span class="tit">'+t+'</span><span class="tag">Top10</span></div>');var e=$("<div/>").addClass("bd").addClass("clearfix");var c=$("<ul/>").appendTo(e);var a,n,j,g;do{n=h[q];if(n.length<=3){continue}a=$("<li/>");if(q==b-1){a.addClass("nodot")}j=n[3];if(j.indexOf("_100_100")>0){j=j.replace("_100_100","_80_80")}g=n[2];if(g.indexOf("?")<0){g+="?"}else{g+="&"}g+="req_id="+f;a.append('<a clkstat="'+m+n[0]+'" class="img" target="_blank" href="'+g+'"><img src="'+j+'"/></a>');a.append('<a clkstat="'+m+n[0]+'" class="tit" target="_blank" href="'+g+'">'+n[1]+"</a>");a.append('<p class="price">'+n[4]+"</p>");c.append(a)}while(++q<b);e.append('<a href="http://www.baifendian.com" class="bfd-click-link " target="_blank"></a>');$(k).append(s).append(e).show();e.clkBindMethod()};onGetGoodsInfo_bh=function(g,h,a){if(!h){return}var j=g.length;if(j==0){return}var l="\u5b98\u65b9\u63a8\u8350";var f="xiuroc";var o=!!LEON.bfdData.setting.stype?LEON.bfdData.setting.stype:"kw";var m=!!LEON.bfdData.setting.sattr?LEON.bfdData.setting.sattr:"";var q="atype="+f+"|stype="+o+"|sattr="+m+"|itemid=";var e=0,c=j>LEON.bfdData.setting.itemHistoryNum?LEON.bfdData.setting.itemHistoryNum:j;var p=$('<div class="hd"><p class="tit">'+l+"</p></div>");var d=$("<div/>").addClass("bd").addClass("clearfix");var r=$("<ul/>").appendTo(d);var b,k,n;do{k=g[e];if(k.length<=3){continue}b=$("<li/>");if(e==1){b.addClass("first")}if(e==c-1){b.addClass("last")}n=k[2];if(n.indexOf("?")<0){n+="?"}else{n+="&"}n+="req_id="+a;b.append('<p class="picbox"><a clkstat="'+q+k[0]+'" target="_blank" href="'+n+'"><img src="'+k[3]+'" /></a></p>');b.append('<p class="tit"><a clkstat="'+q+k[0]+'" target="_blank" href="'+n+'">'+k[1]+"</a></p>");b.append('<p class="price xs-ico">'+k[4]+"</p>");r.append(b)}while(++e<c);d.append('<a href="http://www.baifendian.com" class="bfd-click-link " target="_blank"></a>');$(h).append(p).append(d).show()};
-->
<#-- 点击流事件绑定 -->
$('#J_itemListBox,#J_sortBox,#J_crumbBar,#J_catalogTire,#J_facet_box').clkBindMethod();
})();
<#--百分点推荐回调函数 -->
function showRecommend_vh(datas,req_id,bfd_id){
	LEON.bfdCreateRecommend(datas,"banner_BrowsingHistory",req_id,bfd_id);		
}
<#--@abtest判断使用推荐引擎010.js-->
$(window).load(function() { 
    var abtest_ret = $.cookie("abtestRet");
    var cur_sid = $.cookie("JSESSIONID_WCS");
    if (abtest_ret && cur_sid) {
        var abtest_id = abtest_ret.split("|")[0];
        var ret_id = abtest_ret.split("|")[1];
        if (abtest_id === cur_sid) {
            var ab_id = 19;
            LEON.xreData({
                clientName: "${bfdClientName!}",
                type: "list",
                brandId: "${(recommenBrandIds)!}",
                catId: "${recommendCatIds!}",
                itemId: "${recommendItemIds!}",
                stype: "list",
                sattr: "${((selectedCatalog.catalogName)!'')?url}",
                siteId: "1001",
                channelId: "1001",
                sceneId: "1006"
            });
            XIU.Window.ctraceClick("atype=exposuret|abproject_id=8|abitem_id=8|abverid=" + ab_id, "recommend_engine");
        } else {
            recommendItem()
        }
    } else {
        recommendItem()
    }

    function recommendItem() {
        var a = 19;
        $.ajax({
            url: "http://abtest.xiu.com/GetScriptJsonpServlet.js?_pid=8",
            dataType: "jsonp",
            jsonp: "callbackparam",
            success: function(e) {
                var d = [];
                var f = $.cookie("JSESSIONID_WCS");
                d.push(f);
                d.push(e.name.substring(0, 1));
                $.cookie("abtestRet", d.join("|"), {
                    expires: 30 * 3,
                    path: "/",
                    domain: ".xiu.com"
                });
                LEON.xreData({
                    clientName: "${bfdClientName!}",
                    type: "list",
                    brandId: "${(recommenBrandIds)!}",
                    catId: "${recommendCatIds!}",
                    itemId: "${recommendItemIds!}",
                    stype: "list",
                    sattr: "${((selectedCatalog.catalogName)!'')?url}",
                    siteId: "1001",
                    channelId: "1001",
                    sceneId: "1006"
                });
            },
            error: function() {
                LEON.xreData({
                    clientName: "${bfdClientName!}",
                    type: "list",
                    brandId: "${(recommenBrandIds)!}",
                    catId: "${recommendCatIds!}",
                    itemId: "${recommendItemIds!}",
                    stype: "list",
                    sattr: "${((selectedCatalog.catalogName)!'')?url}",
                    siteId: "1001",
                    channelId: "1001",
                    sceneId: "1006"
                });
            }
        });
        XIU.Window.ctraceClick("atype=exposuret|abproject_id=8|abitem_id=8|abverid=" + a, "recommend_engine");
    };
});
</script>

<#-- 百分点走秀推荐结束 -->
<#-- CMS页尾 -->
<#include "/resources/cms/static/web/M_footer.html" parse="false">
<#include "/views/common/bottom_static.ftl" >
</body>
</html>