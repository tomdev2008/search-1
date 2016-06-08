<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="description" content="走秀网是全球时尚在线百货商城零售机构，提供奢侈品特卖，品牌折扣，名品打折；服装、奢侈品、化妆品、手表、珠宝配饰等品牌商品首页，网上购物正品保障，货到付款的网站，7天无条件退换货。" />
<meta name="keywords" content="网上购物,名品打折网,品牌折扣网,奢侈品,LV,巴宝莉,古驰,蔻驰,阿玛尼,路易威登,爱马仕,欧米茄,D&G,施华洛世奇,香奈儿,耐克,阿迪达斯,李宁,ZARA,百丽,gucci,百货商城，货到付款,走秀网,走秀网官网" />
<title>【${brandName!}】价格,款式,购买查询-走秀网</title>
<#include "/views/common/head_static.ftl" >
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
		<#-- 面包屑-->
		<div class="xs-crumb clearfix">
			<label class="hd">您的位置：</label>
			<ul id="J_crumbBar" class="nav">
            	<li class="first"><a href="${wwwUrl}" clkstat="atype=breadclk|stype=brand|sattr=${(brandName!)?url}|bread_cat=${'走秀首页'?url}|bread_lv=1">走秀首页</a></li>
            	<#if selectCatalogPlaneList??>
            		<#assign clkstat="atype=breadclk|stype=brand|sattr=${(brandName!)?url}">
            		<#list selectCatalogPlaneList as catalog>
            		<li><span><a clkstat="${clkstat}bread_cat=${catalog.catalogName?url}|bread_lv=${catalog_index+1}" href="<@xurl value="/brand-action.htm?bid=${fatParams.brandId?url}&cat=${(catalog.catalogId)!''}&p=${params.p!'1'}&itemshowtype=${(params.itemshowtype)!''}&s_id=${(s_id)!''}" />">${catalog.catalogName}</a></span><i></i></li>
            		</#list>
            	</#if>
            	<li><span class="fb"><a href="<@xurl value="/brand-action.htm?bid=${(fatParams.brandId)!}&cat=${(fatParams.catalogId)!''}&p=${params.p!'1'}&itemshowtype=${(params.itemshowtype)!''}&s_id=${(s_id)!''}" />">${brandName!}</a></span><i></i></li>
            </ul>
			<div class="totalcount">总共<span class="pl3 pr3 cred">${(page.recordCount)!0}</span>件商品</div>
            <b class="icarr "></b>
		</div>
		<#-- 面包屑结束-->
		
		<#-- 主体部分 -->
		<div class="xs-facetbox">
			<div class="facet-bd">
					<style type="text/css">
						.facet-line .line-hd{left:10px;width:60px;}
						.clearline{width:100%;clear:both;height:1px;line-height:0;font-size:0px;display:block}
						.xs-aside{overflow:hidden;}
					</style>
					<#if selectedCatalogTree?? && selectedCatalogTree.childCatalog?? && selectedCatalogTree.childCatalog?size gt 0>
			        	<#assign urlTemp="/list-action.htm?vm=${(params.vm)!''}&p_code=${(fatParams.providerCode)!''}&s_id=${(s_id)!''}&itemshowtype=${(params.itemshowtype)!''}">
			        	<#assign clkstat="atype=cattree|stype=list|sattr=${selectedCatalogTree.catalogName?url}|clk_cat=${(selectedCatalogTree.catalogName)!?url}"/>
				        <div class="facet-line brand-facet-box fl_box">
				            <label class="line-hd">分类：</label>
				            <div class="line-bd">
				                <div class="normal-item-box clearfix">
				                    <div>
				                        <ul id="J_catSecondItem" class="clearfix">
				                        	<#list selectedCatalogTree.childCatalog as catalog>
				                            	<li><a data-catalogid="${catalog.catalogId}" clkstat="${clkstat!},${catalog.catalogName?url}|cat_lv=2" href="<@xurl value='${urlTemp}&cat=${catalog.catalogId}' />" class="${(catalog.selected )?string('active','')}" >
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
				            	<div id="J_catchildbox_${catalog.catalogId}" class="normal-item-box hide clearfix">
				            		<div >
				                        <ul class="clearfix">
					                        <#list catalog.childCatalog as catalog2>
					                            <li><a href="<@xurl value='${urlTemp}&cat=${catalog2.catalogId}'/>">
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
				            	$("#J_catSecondItem a").hover(function(){
				            		console.log('1');
				            		window.clearTimeout(_timeout);
				            		var _catid = $(this).data('catalogid');
				            		if(!!_currentShowCatId && _currentShowCatId == _catid){
				            			return;
				            		}
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
					            			_currentShowCatId=null;
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
					            			_currentShowCatId=null;
					            		}
				            		},100);
				            	})
				            	})();
				            </script>
				        </div>
					</#if>

			</div>
		</div>
		
		<div class="xs-mainsection clearfix">
	    	<div class="xs-section">
	    		<#if fatParams?? && params??>
		    	<div class="xs-filterbox clearfix">
					<div class="xs-toolbar">
						<div class="showmode">
			                <a class="${( params.vm?? && params.vm==1)?string('active','')}" href="javascript:void(0)">列表<b></b>
			                </a>
			                <a class="${( !(params.vm??) || params.vm==0)?string('active','')}" href="javascript:void(0)">大图 <b class="big"></b>
			                </a>
			            </div>
			            <div class="xs-sort-bar">
			                <dl id="J_sortBox" class="sort-box">
		
			                    <dd><a href="javascript:void(0)" class="nonearr ${(fatParams.sort=='AMOUNT_DESC')?string('active','')}">默认</a></dd>
			                    <dd><a href="javascript:void(0)" class="${(fatParams.sort=='PRICE_ASC' || fatParams.sort=='PRICE_DESC')?string('active','')}">价格<i class="${(fatParams.sort=='PRICE_DESC')?string('down','')}"></i></a></dd>
			                    <dd><a href="javascript:void(0)" class="${(fatParams.sort=='DISCOUNT_DESC' || fatParams.sort=='DISCOUNT_ASC')?string('active','')}">折扣<i class="${(fatParams.sort=='DISCOUNT_ASC')?string('','down')}"></i></a></dd>
			                    <dd><a  href="javascript:void(0)" class="${(fatParams.sort=='ONSALE_TIME_ASC' || fatParams.sort=='ONSALE_TIME_DESC')?string('active','')}">上架时间<i class="${(fatParams.sort=='ONSALE_TIME_ASC')?string('','down')}"></i></a></dd>
			                	
			                </dl>
			            </div>
			            <#if ((params.itemshowtype)!'')?trim!='2' && ((params.itemshowtype)!'')?trim!='3'>
						<div class="fh-box">
			            	<#assign urlTemp="/brand-action.htm?bid=${(params.bid)!}&cat=${(fatParams.catalogId)!''}&f_price=${(fatParams.priceRangeEnum.order)!''}&s_price=${(fatParams.startPrice)!''}&e_price=${(fatParams.endPrice)!''}&vm=${params.vm!''}&ba=${(params.ba)!0}&mkt=${(fatParams.mktType.type)!''}&s_id=${(s_id)!''}&itemshowtype=${(params.itemshowtype)!''}&sort=${(params.sort)!''}">
			            	<label>发货方式：</label>
			            	<input id="J_ItemShowType" type="hidden" value="<@xurl value="${urlTemp}&channel=aaaaaaaa" />" />
			                <ul>
			                	<li><label for="J_All"><input id="J_All" name="Itemshowtype" type="radio" onClick="shouItemshowtype('')" checked = "checked">全部</label></li>
			                	<li><label for="J_Foreign"><input id="J_Foreign" name="Itemshowtype" type="radio" value="3" onClick="shouItemshowtype('3')" >海外发货</label></li>
			                	<li><label for="J_Domestic"><input id="J_Domestic" name="Itemshowtype" type="radio" value="2" onClick="shouItemshowtype('2')" >国内发货</label></li>
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
			            <#--
			            <@xpage currentPage="${(page.pageNo)!0}" totalPage="${(page.pageCount)!0}" repQuery="mkt=${(fatParams.mktType.type)!''}" theme="1" />
			            -->
			        </div>
		        </div>
		        </#if>
	    		<div id="J_filterbox" class="xs-main">
				     <#-- 搜索无结果内容 -->
			        <div class="xs-nrbox clearfix">
			            <i class="xs-nrico"></i>
			            <dl class="sorry-box">
			                <dd>&nbsp;</dd>
			                <dt>很抱歉，没有找到相关的商品</dt>
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
		<#-- 底部搜索 -->
		<div class="xs-btm-search">
		<form id="J_searchFormBottom" action="${searchUrl}s" method="get" autocomplete="off">
		    <div class="searchbox">
		        <div class="inputbg"><input id="J_searchInputBottom" name="kw" class="txt-input" type="text" value="${((params.kw)!)?html}" /></div>
		        <button class="sbtn" type="submit"></button>
		    </div>
		</form>
		</div>
<script>
<#-- @006.js -->
if(typeof LEON=="undefined"){LEON={}}LEON.lastinputBottom=$("#J_searchInputBottom").val();$("#J_searchInputBottom").XAutoSuggest({url:"${searchUrl}ajax/autocomplete.htm?jsoncallback=?",params:{type:"min",mkt:"xiu"},width:480,leftOff:0,result:function(a){if(!!a.oclassId){LEON.lastinputBottom=a.display;$("#J_catalogIdInput").val(a.oclassId).removeAttr("disabled")}$("#J_searchForm").submit()}});
</script>
<#-- 底部搜索结束 -->
<#-- 百分点走秀推荐 -->
<div id="banner_BrowsingHistory" class="tj_box"></div>
<script type="text/javascript" src="http://xres.xiu.com/static/xclk-jsapi.js"></script>
<script type="text/javascript">
$(function(){
<#--百分点异步加载@012.js-->
_BFD=window._BFD||{};_BFD.script=document.createElement("script");_BFD.script.setAttribute("src","http://www.xiustatic.com/static/js/thirdparty/bfd/zx_list_search_new.min.js");_BFD.script.setAttribute("type","text/javascript");_BFD.script.setAttribute("charset","utf-8");document.getElementsByTagName("head")[0].appendChild(_BFD.script);
<#--百分点推荐回调函数 -->
function showRecommend_noresult(datas,req_id,bfd_id){
	LEON.bfdCreateRecommend(datas,"banner_BrowsingHistory",req_id,bfd_id);		
}
<#-- @abtest判断使用推荐引擎014.js -->
var abtest_ret=$.cookie("abtestRet");var cur_sid=$.cookie("JSESSIONID_WCS");if(abtest_ret&&cur_sid){var abtest_id=abtest_ret.split("|")[0];var ret_id=abtest_ret.split("|")[1];if(abtest_id===cur_sid){if(ret_id==="1"){var ab_id=18;LEON.bfdData({stype:"brand",sattr:"${(brandName!)?url}",categoryList:[decodeURIComponent("${(brandName!)?url}")],type:"brand",catId:"<#if ((fatParams??)&&(fatParams.catalogId??)&&(fatParams.catalogId>0))>${fatParams.catalogId!}<#else>${recommendCatIds!}</#if>",bId:"${(fatParams.brandId)!}",hasResult:false,clientName:"${bfdClientName!}"})}else{var ab_id=19;LEON.xreData({hasResult:false,clientName:"${bfdClientName!}",type:"brand",brandId:"${(recommenBrandIds)!}",catId:"${recommendCatIds!}",itemId:"${recommendItemIds!}",brandRemmendOn:true,stype:"brand",sattr:"${(brandName!)?url}",siteId:"1001",channelId:"1001"})}XIU.Window.ctraceClick("atype=exposuret|abproject_id=8|abitem_id=8|abverid="+ab_id,"recommend_engine")}else{recommendItem()}}else{recommendItem()}function recommendItem(){var a=18;$.ajax({url:"http://abtest.xiu.com/GetScriptJsonpServlet.js?_pid=8",dataType:"jsonp",jsonp:"callbackparam",success:function(e){var d=[];var f=$.cookie("JSESSIONID_WCS");d.push(f);d.push(e.name.substring(0,1));$.cookie("abtestRet",d.join("|"),{expires:30*3,path:"/",domain:".xiu.com"});try{if(e.name.substring(0,1)=="1"){LEON.bfdData({stype:"brand",sattr:"${(brandName!)?url}",categoryList:[decodeURIComponent("${(brandName!)?url}")],type:"brand",catId:"<#if ((fatParams??)&&(fatParams.catalogId??)&&(fatParams.catalogId>0))>${fatParams.catalogId!}<#else>${recommendCatIds!}</#if>",bId:"${(fatParams.brandId)!}",hasResult:false,clientName:"${bfdClientName!}"})}else{a=19;LEON.xreData({hasResult:false,clientName:"${bfdClientName!}",type:"brand",brandId:"${(recommenBrandIds)!}",catId:"${recommendCatIds!}",itemId:"${recommendItemIds!}",brandRemmendOn:true,stype:"brand",sattr:"${(brandName!)?url}",siteId:"1001",channelId:"1001"})}}catch(c){LEON.bfdData({stype:"brand",sattr:"${(brandName!)?url}",categoryList:[decodeURIComponent("${(brandName!)?url}")],type:"brand",catId:"<#if ((fatParams??)&&(fatParams.catalogId??)&&(fatParams.catalogId>0))>${fatParams.catalogId!}<#else>${recommendCatIds!}</#if>",bId:"${(fatParams.brandId)!}",hasResult:false,clientName:"${bfdClientName!}"})}},error:function(){LEON.bfdData({stype:"brand",sattr:"${(brandName!)?url}",categoryList:[decodeURIComponent("${(brandName!)?url}")],type:"brand",catId:"<#if ((fatParams??)&&(fatParams.catalogId??)&&(fatParams.catalogId>0))>${fatParams.catalogId!}<#else>${recommendCatIds!}</#if>",bId:"${(fatParams.brandId)!}",hasResult:false,clientName:"${bfdClientName!}"})}});XIU.Window.ctraceClick("atype=exposuret|abproject_id=8|abitem_id=8|abverid="+a,"recommend_engine")};
});
</script>
<#-- 百分点走秀推荐结束 -->
<#-- CMS页尾 -->
<#include "/resources/cms/static/web/M_footer.html" parse="false">
<#include "/views/common/bottom_static.ftl" >
</body>
</html>