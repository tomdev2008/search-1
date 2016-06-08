<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>【${params.kw?html}】价格,款式,购买查询-eBay秀</title>
<meta name="keywords" content="${params.kw?html},eBay秀${params.kw?html}" />
<meta name="description" content="在ebay秀中找到了${page.recordCount}件${params.kw?html}相关的商品；网上购买${params.kw?html}就上ebay秀！" />
<#include "/views/common/ebay_head_static.ftl" >
<script>if(typeof LEON == 'undefined')LEON={};LEON.searchTerm = decodeURIComponent("${params.kw?url}");
$(document).ready(function(){$(".pic img").lazyload({placeholder:"http://www.xiu.com/static/img/default/default.200_200.jpg"});});</script>
<style type="text/css">
.xs-errortermbox{width:998px;margin-left:auto;margin-right:auto}
</style>
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
<#-- 面包屑-->
<div class="xs-crumb clearfix">
	<label class="hd">您的位置：</label>
        <ul id="J_crumbBar" class="nav">
           	<li class="first"><a href="${ebayUrl}" clkstat="atype=breadclk|stype=brand|sattr=${(brandName!)?url}|bread_cat=${'eBay秀首页'?url}|bread_lv=1">eBay秀首页</a></li>
        	<#if selectCatalogPlaneList??>
        		<#assign clkstat="atype=breadclk|stype=kw|sattr=${params.kw?url}|"/>
        		<#list selectCatalogPlaneList as catalog>
        		<li><span><a clkstat="${clkstat}bread_cat=${catalog.catalogName?url}|bread_lv=${catalog_index+1}" href="<@xurl  value="/ebay-search-action.htm?kw=${searchKW?url}&cat=${catalog.catalogId}&s_id=${(s_id)!''}" />" alt="${catalog.catalogName!}"><#if (catalog.catalogName??)><#if (catalog.catalogName?length>20)>${catalog.catalogName?substring(0,20)}...<#else>${catalog.catalogName}</#if></#if></a></span><i></i></li>
        		</#list>
        	</#if>
        	<li><span class="fb"><a href="<@xurl value="/ebay-search-action.htm?kw=${searchKW?url}&cat=${(fatParams.catalogId)!''}&s_id=${(s_id)!''}" />">${searchKW?html}</a></span><i></i></li>
        </ul>
    <div class="totalcount">总共<span class="pl3 pr3 cred">${page.recordCount}</span>件商品</div>
    <b class="icarr "></b>
</div>
<#-- 面包屑结束-->
<#-- 相关搜索和错误纠正 -->
<#if termErrorCorrect??>
<div class="xs-errortermbox">
    <p class="errmsg">
            抱歉！没有找到与“<span class="hl">${params.kw?html}</span>”相关的商品，我们为您找到了“<span class="hl">${termErrorCorrect?html}</span>”的搜索结果
    </p>
</div>
<#elseif termAutoCorrect??>
<div class="xs-errortermbox">
    <p class="errmsg">
            您要找的是不是：<a href="<@xurl value="/ebay-search-action.htm?kw=${termAutoCorrect?url}" />" class="hl">${termAutoCorrect?html}</a>
    </p>
</div>
<#elseif relatedTerms??&&(relatedTerms?size>0)>
<div class="xs-relatetermbox xs-bg">
    <label class="hd">相关搜索：</label>
    <div class="bd">
    <#assign clkstat="atype=relkw|stype=kw|sattr=${params.kw?url}|rk=" >
    <#list relatedTerms as term>
        <a class="${(term_index==0)?string('first','')}" href="${ebayUrl}search/s?kw=${term?url}" clkstat="${clkstat}${term?url}">${term?html}</a>
    </#list>
    </div>
</div>
</#if>
<#-- 相关搜索和错误纠正结束 -->

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
				<#assign urlTemp="/ebay-search-action.htm?kw=${searchKW?url}&mkt=${(params.mkt)!''}&s_id=${(s_id)!''}&sort=${fatParams.sort.sortOrder}&vm=${params.vm!''}&channel=${(params.channel)!''}">
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
		                    <a class="more-opt hide" href="javascript:void(0);">更多</a>
		                </div>
		            </div>
		        </div>
			</#if>
		<#else>
        	<#assign urlTemp="/ebay-search-action.htm?kw=${searchKW?url}&mkt=${(params.mkt)!''}&s_id=${(s_id)!''}&sort=${fatParams.sort.sortOrder}&vm=${params.vm!''}&channel=${(params.channel)!''}">
	        <#-- 您已选择 -->
	    	<#if selectFacetBoList?? || selectedCatalogTree??>
                <div style="" class="facet-line nodot facet-select-all change_nopd">
                    <label class="line-hd">您已选择：</label>
                    <div class="line-bd">
                        <div class="normal-item-box clearfix">
                            <div class="facet-item-box">
	                            <ul class="clearfix">
	                            	<#if selectedCatalogTree??>
		                            	<li><a href="<@xurl value="${urlTemp}" />">
		                            		${selectedCatalogTree.catalogName?html}
		                            		<b class="close-ico"></b></a>
		                            	</li>
		                            </#if>
		                            <#if selectFacetBoList?? && selectFacetBoList?size gt 0>
			                            <#list selectFacetBoList as selectFacetBo>
			                            <#if selectFacetBo.facetType == 'BRAND'>
			                            <li><a title="${selectFacetBo.facetDisplay}：${selectFacetBo.facetValueBosNamesForTitle?html}" href="<@xurl value="${urlTemp}&cat=${(fatParams.catalogId)!''}&filter=${(fatParams.filter)!''}&f_price=${(fatParams.priceRangeEnum.order)!''}&s_price=${(fatParams.startPrice)!''}&e_price=${(fatParams.endPrice)!''}" />">${selectFacetBo.facetDisplay}：${selectFacetBo.facetValueBosNames?html}<b class="close-ico"></b></a></li>
			                            <#elseif selectFacetBo.facetType == 'PRICE'>
			                            <li><a href="<@xurl value="${urlTemp}&cat=${(fatParams.catalogId)!''}&filter=${(fatParams.filter)!''}&bid=${(params.bid)!''}" />">${selectFacetBo.facetDisplay}：${selectFacetBo.facetValueBos[0].name}<b class="close-ico"></b></a></li>
			                            <#else>
			                            <li><a href="<@xurl value="${urlTemp}&cat=${(fatParams.catalogId)!''}&bid=${(params.bid)!''}&f_price=${(fatParams.priceRangeEnum.order)!''}&filter=${(selectFacetBo.cancelFilter)!''}&s_price=${(fatParams.startPrice)!''}&e_price=${(fatParams.endPrice)!''}" />">${selectFacetBo.facetDisplay}：${selectFacetBo.facetValueBos[0].name}<b class="close-ico"></b></a></li>
			                            </#if>
			                            </#list>
		                            </#if>
		                            <li class="reset"><a href="<@xurl value="${urlTemp}" />">重置筛选条件</a></li>
	                            </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </#if>
            <#if selectedCatalogTree?? && selectedCatalogTree.childCatalog?? && selectedCatalogTree.childCatalog?size gt 0>
	        <#assign urlTemp="/ebay-search-action.htm?kw=${searchKW?url}&mkt=${(params.mkt)!''}&s_id=${(s_id)!''}&sort=${fatParams.sort.sortOrder}&vm=${params.vm!''}&channel=${(params.channel)!''}">
	        <#assign clkstat="atype=cattree|stype=list|sattr=${selectedCatalogTree.catalogName?url}|clk_cat=${(selectedCatalogTree.catalogName)!?url}"/>
	        <div class="facet-line brand-facet-box fl_box">
	            <label class="line-hd">分类：</label>
	            <div class="line-bd">
	                <div class="normal-item-box clearfix">
	                    <div>
	                        <ul id="J_catSecondItem" class="clearfix">
	                        	<li><a href="<@xurl value='${urlTemp}&cat=${selectedCatalogTree.catalogId}'/>" class="${(fatParams.catalogId?? && (fatParams.catalogId == selectedCatalogTree.catalogId))?string('active','')}" >
	                            	全部
	                            </a></li>
	                        	<#list selectedCatalogTree.childCatalog as catalog>
	                            	<li><a dataCollect="type=list|id=${(catalog.catalogId)!}" data-catalogid="${catalog.catalogId}" clkstat="${clkstat!},${catalog.catalogName?url}|cat_lv=2" href="<@xurl value='${urlTemp}&cat=${catalog.catalogId}' />" class="${(catalog.selected )?string('active','')}" >
	                            	${catalog.catalogName?html}
	                            	</a></li>
	                            </#list>
	                        </ul>
	                    </div>
	                    <a class="more-opt hide" href="javascript:void(0);">更多</a>
	                </div>
	            </div>
	            <div class="line-bd">
	            <#list selectedCatalogTree.childCatalog as catalog>
	            	<#if catalog.childCatalog?? && catalog.childCatalog?size gt 0>
	            	<#if catalog.selected>
	            		<input type="hidden" id="J_selectCatalogId" value="${catalog.catalogId}" />
	            	</#if>
	            	<div id="J_catchildbox_${catalog.catalogId}" class="normal-item-box ${catalog.selected?string('','hide')} clearfix">
	            		<div >
	                        <ul class="clearfix">
	                        	<li><a href="<@xurl value='${urlTemp}&cat=${catalog.catalogId}'/>" class="${(fatParams.catalogId?? && (fatParams.catalogId == catalog.catalogId))?string('active','')}" >
	                        		全部
	                        	</a></li>
		                        <#list catalog.childCatalog as catalog2>
		                            <li><a dataCollect="type=list|id=${(catalog2.catalogId)!}" href="<@xurl value='${urlTemp}&cat=${catalog2.catalogId}'/>" class="${catalog2.selected?string('active','')}">
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
	            	</#if>
	            </#list>
	            </div>
	            <script>
	            	(function(){
	            	var _timeout;
	            	var _currentShowCatId;
	            	var _selectShowId = $('#J_selectCatalogId').val();
	            	return;
	            	if(_selectShowId)
	            		_currentShowCatId = _selectShowId;
	            	$("#J_catSecondItem a").hover(function(){
	            		window.clearTimeout(_timeout);
	            		var _catid = $(this).data('catalogid');
	            //		if(!!_currentShowCatId && _currentShowCatId == _catid){
	            //			return;
	            //		}
	            		var _box;
	            		if(!!_currentShowCatId){
	            			_box = $("#J_catchildbox_"+_currentShowCatId);
	            			_box.hide();
	            		}
	            		_box = $("#J_catchildbox_"+_catid);
	            		if(!!_box && _box.length>0){
	            			_box.show();
	            			_currentShowCatId = _catid;
	            		}
	            	},function(){
	            		_timeout = window.setTimeout(function(){
	            			if(!!_currentShowCatId){
		            			_box = $("#J_catchildbox_"+_currentShowCatId);
		            			_box.hide();
		            			_currentShowCatId=_selectShowId;
		            			if(_selectShowId)
		            			$("#J_catchildbox_"+_selectShowId).show();
		            		}
	            		},100);
	            	});
	            	$('div[id^=J_catchildbox_]').hover(function(){
	            		window.clearTimeout(_timeout);
	            	},function(){
	            		_timeout = window.setTimeout(function(){
	            			if(!!_currentShowCatId){
		            			_box = $("#J_catchildbox_"+_currentShowCatId);
		            			_box.hide();
		            			_currentShowCatId=_selectShowId;
		            			if(_selectShowId)
		            			$("#J_catchildbox_"+_selectShowId).show();
		            		}
	            		},100);
	            	})
	            	})();
	            </script>
	        </div>
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
	        	.brandBoxMargin{overflow:hidden;height:74px;_height:0;zoom:1}
	        	.brandBoxMargin .noResultTipLi{width:80%;text-align:center;padding-top:30px;}
	        </style>
	        <div class="facet-line brand-facet-box brand-facet-default-status">
	            <label class="line-hd ">品牌：</label>
	            <div class="line-bd">
	                <div class="brand-item-box multiple-item-box">
	                	<h2 id="J_brandSearchBar"><label id="J_brandSearchLabel" for="J_brandSearchInput">搜索品牌名称</label><input type="text" id="J_brandSearchInput" onkeyup="LEON.brandSearchAction();return true;"></h2>
	                	<#assign urlTemp="/ebay-search-action.htm?kw=${searchKW?url}&cat=${(fatParams.catalogId)!''}&mkt=${(params.mkt)!''}&s_id=${(s_id)!''}&sort=${fatParams.sort.sortOrder}&vm=${params.vm!''}&channel=${(params.channel)!''}">
	                	<input id="J_brandUrlInput" type="hidden" value="<@xurl value="${urlTemp}&s_price=${(fatParams.startPrice)!''}&e_price=${(fatParams.endPrice)!''}&bid=xsearchplaceholderbrandids&f_price=${(fatParams.priceRangeEnum.order)!''}&filter=${(fatParams.filter)!''}" />" />
	                	<div id="J_brandBoxMargin" class="brandBoxMargin">
	                    <ul id="J_brandBoxUl">
	                    	<#list facetFilterValueBoList as facetFilterValueBo >
		                    	<li>
			                    	<a title="${facetFilterValueBo.name?html}" dataCollect="type=brand|id=${(facetFilterValueBo.id)!}" data-brandid="${facetFilterValueBo.id}" onclick="LEON.multiSelectItemAction(event,this);" href="<@xurl value="${urlTemp}&s_price=${(fatParams.startPrice)!''}&e_price=${(fatParams.endPrice)!''}&bid=${facetFilterValueBo.id}&f_price=${(fatParams.priceRangeEnum.order)!''}&filter=${(fatParams.filter)!''}" />">
			                    		<span>${facetFilterValueBo.name?html}</span><b></b>
			                    	</a>
		                    	</li>
	                    	</#list>
	                    </ul>
	                    <ul id="J_brandBoxMatchUl">
	                    </ul>
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
	        <#assign urlTemp="/ebay-search-action.htm?kw=${searchKW?url}&cat=${(fatParams.catalogId)!''}&p_code=${(fatParams.providerCode)!''}&mkt=${(params.mkt)!''}&s_id=${(s_id)!''}&f_price=${(fatParams.priceRangeEnum.order)!''}&s_price=${(fatParams.startPrice)!''}&e_price=${(fatParams.endPrice)!''}&bid=${(params.bid)!''}&sort=${fatParams.sort.sortOrder}&vm=${params.vm!''}&channel=${(params.channel)!''}">
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
		        	<#assign urlTemp1="/ebay-search-action.htm?kw=${searchKW?url}&cat=${(fatParams.catalogId)!''}&bid=${(params.bid)!''}&filter=${(fatParams.filter)!''}&mkt=${(params.mkt)!''}&s_id=${(s_id)!''}&sort=${fatParams.sort.sortOrder}&vm=${params.vm!''}&channel=${(params.channel)!''}">
		        	<div id="J_attrBox_-1" class="facet-line ${(index gt 1)?string('hide J_hideAttrCol','')}">
		            <label class="line-hd">价格：</label>
		            <div class="line-bd ">
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
	<div class="xs-filterbox clearfix">
		<div class="xs-toolbar">
            <div class="showmode">
                <#assign urlTemp="/ebay-search-action.htm?kw=${searchKW?url}&cat=${(fatParams.catalogId)!''}&bid=${(params.bid)!''}&f_price=${(fatParams.priceRangeEnum.order)!''}&s_price=${(fatParams.startPrice)!''}&e_price=${(fatParams.endPrice)!''}&filter=${(fatParams.filter)!''}&ba=${(params.ba)!''}&sort=${fatParams.sort.sortOrder}&mkt=${(params.mkt)!''}&s_id=${(s_id)!''}&channel=${(params.channel)!''}">
                <#--<a class="${( params.vm?? && params.vm==1)?string('active','')}" href="<@xurl value="${urlTemp}&vm=1" />">列表<b></b>
                </a>
                <a class="${( !(params.vm??) || params.vm==0)?string('active','')}" href="<@xurl value="${urlTemp}&vm=0" />">大图 <b class="big"></b>
                </a>-->
            </div>
            <div class="xs-sort-bar">
                <#assign urlTemp="/ebay-search-action.htm?kw=${searchKW?url}&cat=${(fatParams.catalogId)!''}&bid=${(params.bid)!''}&vm=${(params.vm)!''}&f_price=${(fatParams.priceRangeEnum.order)!''}&s_price=${(fatParams.startPrice)!''}&e_price=${(fatParams.endPrice)!''}&filter=${(fatParams.filter)!''}&ba=${(params.ba)!''}&mkt=${(params.mkt)!''}&s_id=${(s_id)!''}&channel=${(params.channel)!''}">
                <#assign clkstat="atype=sort|stype=kw|sattr=${params.kw?url}|sorttype=" >
                <dl id="J_sortBox" class="sort-box">
                  <dd><a clkstat="${clkstat}9" href="<@xurl  value="${urlTemp}&sort=9" />" class="nonearr ${(fatParams.sort=='SCORE_AMOUNT_DESC')?string('active','')}">默认</a></dd>
                  <dd><a clkstat="${clkstat}${(fatParams.sort=='PRICE_ASC')?string('2','1')}" href="<@xurl  value="${urlTemp}&sort=${(fatParams.sort=='PRICE_ASC')?string('2','1')}" />" class="${(fatParams.sort=='PRICE_ASC' || fatParams.sort=='PRICE_DESC')?string('active','')}">价格<i class="${(fatParams.sort=='PRICE_DESC')?string('down','')}"></i></a></dd>
                  <dd><a clkstat="${clkstat}${(fatParams.sort=='ONSALE_TIME_DESC')?string('7','8')}" href="<@xurl  value="${urlTemp}&sort=${(fatParams.sort=='ONSALE_TIME_DESC')?string('7','8')}" />" class="${(fatParams.sort=='ONSALE_TIME_DESC'|| fatParams.sort=='ONSALE_TIME_ASC')?string('active','')}">上架时间<i class="${(fatParams.sort=='ONSALE_TIME_ASC')?string('','down')}"></i></a></dd>
                </dl>
            </div>
            <#--
            <div class="fh-box">
            	<#assign urlTemp="/ebay-search-action.htm?kw=${searchKW?url}&cat=${(fatParams.catalogId)!''}&bid=${(params.bid)!''}&vm=${(params.vm)!''}&f_price=${(fatParams.priceRangeEnum.order)!''}&s_price=${(fatParams.startPrice)!''}&e_price=${(fatParams.endPrice)!''}&filter=${(fatParams.filter)!''}&ba=${(params.ba)!''}&mkt=${(params.mkt)!''}&s_id=${(s_id)!''}&sort=${(params.sort)!''}">
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
            <@xpage currentPage="${(page.pageNo)!0}" totalPage="${(page.pageCount)!0}" repQuery="kw=${searchKW?url}" theme="1"/>
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
			<div id="J_itemListBox" class="xs-itemsbox">
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
	                    	<li class="pic"><a clkstat="${clkstat}" target="_blank" href="${((fatParams.mktType??)&&fatParams.mktType=='EBAY')?string(ebayUrl,itemUrl)}product/${goods.urlId}.shtml${((s_id??)&&s_id!='')?string('?s_id='+s_id!'','')}"><img original="${imagesUrl}${goods.imgEbayUrl220}" /><#if goods.soldOut><b class="saleout-ico"></b></#if></a></li>
	                        <li class="tit"><a clkstat="${clkstat}" target="_blank" href="${((fatParams.mktType??)&&fatParams.mktType=='EBAY')?string(ebayUrl,itemUrl)}product/${goods.urlId}.shtml${((s_id??)&&s_id!='')?string('?s_id='+s_id!'','')}">${goods.name?html!}</a></li>
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
	        <#if (params.vm)?? && params.vm==1>
		        <script>LEON.goodsImageErrorMethod('${staticUrl}static/img/default/default.100_100.jpg');</script>
	        <#else>
	       		<script>LEON.goodsImageErrorMethod('${staticUrl}static/img/default/default.220_220.jpg');</script>
	        </#if>
	        <script>if(typeof latency_metric_ti=='object')latency_metric_ti.cf=new Date().getTime();</script>
	        <#-- 商品列表结束 -->
	        <#-- 底部翻页 -->
	        <div id="J_bottomPageBox" class="xs-pagebar clearfix">
	            <@xpage currentPage="${(page.pageNo)!0}" totalPage="${(page.pageCount)!0}" repQuery="kw=${searchKW?url}" pageParamName="p"/>
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
<#--商品展示列表结束-->
</div>
<#-- 主体部分结束 -->
<#-- 回到顶部
<script>
$(function(){LEON.initToTopBtn({backImg:'static/ebay/css/img/xtop-icon.png'});});
</script>
 -->
<#-- 百分点走秀推荐 -->
<div id="banner_BrowsingHistory" class="tj_box"></div>

<script type="text/javascript" src="http://xres.xiu.com/static/xclk-jsapi.js"></script>
<script type="text/javascript">
(function(){
<#--推荐引擎数据收集-->
$('#J_catalogTire,#J_itemboxbrand').dataCollectMethod({siteId:1001,channelId:1002});
LEON.xreDataCollect({siteId:1001,channelId:1002,kw:"${params.kw?url}",result:${page.recordCount!0}});
<#-- 点击流底部翻页@007 -->
$("#J_bottomPageBox,.Pagenum a,.xs-minpage a").click(function(){var c=$(this).text();var b=parseInt("${page.pageNo}");var d=parseInt("${page.pageCount}");if("\u4e0a\u4e00\u9875"==c){c=(b-1)>0?(b-1):1}else{if("\u4e0b\u4e00\u9875"==c){c=(b+1)>d?d:(b+1)}else{c=parseInt(c)}}if(!c){return}var a="atype=flip|stype=kw|sattr=${params.kw?url}|page_no=";XIU.Window.ctraceClick(a+c,"search")});
<#-- 推荐引擎 -->
LEON.xreData({brandId : "${(recommenBrandIds)!}",catId : "${recommendCatIds!}",itemId : "${recommendItemIds!}",stype : "kw",sattr : "${params.kw?url}",siteId : "1001",channelId : "1002",displayTop10:false});
<#-- 点击流事件绑定 -->
$('#J_itemListBox,#J_sortBox,#J_crumbBar,#J_catalogTire,#J_facet_box,#J_filterbox').clkBindMethod();
})();
</script>


<#-- 百分点走秀推荐结束 -->
<#-- CMS页尾 -->
<#include "/resources/cms/static/web/M_footer.html" parse="false">
<#include "/views/common/ebay_bottom_static.ftl" parse="false">
</body>
</html>