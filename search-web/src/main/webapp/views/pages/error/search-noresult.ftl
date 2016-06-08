<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="ie=7" />
<meta name="description" content="走秀网，全球品牌网络旗舰店，中国第一大国际时尚品牌线上零售机构。一折起销售正品国际品牌女装、男装、奢侈品、化妆品、运动、休闲、箱包、鞋、家居、配饰等商品，支持货到付款，7天无条件退换货保障。" />
<meta name="keywords" content="网上购物,网上商城,货到付款,名品打折网,奢侈品,LV,GUCCI,PRADA,CK,兰蔻,雅诗兰黛,倩碧,耐克,阿迪达斯,李宁,ZARA,百丽,走秀网,走秀网官网" />
<title>${params.kw?html} - 商品搜索 - 走秀网</title>
<#include "/views/common/head_static.ftl" >
</head>
<body>
<#-- CMS页头 -->
<#include "/views/common/header_include.ftl" >
<script src="http://www.xiustatic.com/static/js/xiu/index201210/index.head.app.js?__=${xversion('/static/js/xiu/index201210/index.head.app.js')}" type="text/javascript"></script>
<#-- 广告banner -->
<div class="xs-banner-topmid">
<#include "/resources/cms/static/show/search/001.shtml" parse="false">
</div>
<#-- 广告banner 结束 -->
		<#-- 面包屑-->
		<#if params.kw?length gt 50>
			<#assign showKw="${(params.kw?substring(50))?html}" />
		<#else>
			<#assign showKw="${params.kw?html}" />
		</#if>
		<div class="xs-crumb clearfix">
	        <label class="hd">您的位置：</label>
	        <ul class="nav">
	            <li class="first"><span><a href="${wwwUrl}">走秀首页</a></span></li>
	            <li><span class="fb">"${showKw}"</span><i></i></li>
	        </ul>
	        <div class="totalcount">总共<span class="pl3 pr3 cred">0</span>件商品</div>
	        <b class="icarr "></b>
		</div>
		<#-- 面包屑结束-->
		
		<#-- 主体部分 -->
		<#if params?? && params.channel??>
			<div class="xs-mainsection clearfix">
				<#if fatParams??>
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
			             		<dd><a href="avascript:void(0)" class="nonearr ${(fatParams.sort=='SCORE_AMOUNT_DESC')?string('active','')}">默认</a></dd>
			                    <dd><a href="avascript:void(0)" class="${(fatParams.sort=='PRICE_ASC' || fatParams.sort=='PRICE_DESC')?string('active','')}">价格<i class="${(fatParams.sort=='PRICE_DESC')?string('down','')}"></i></a></dd>
			                    <dd><a href="avascript:void(0)" class="${(fatParams.sort=='DISCOUNT_DESC' || fatParams.sort=='DISCOUNT_ASC')?string('active','')}">折扣<i class="${(fatParams.sort=='DISCOUNT_ASC')?string('','down')}"></i></a></dd>
			                    <dd><a href="avascript:void(0)" class="${(fatParams.sort=='ONSALE_TIME_DESC'|| fatParams.sort=='ONSALE_TIME_ASC')?string('active','')}">上架时间<i class="${(fatParams.sort=='ONSALE_TIME_ASC')?string('','down')}"></i></a></dd>
			                </dl>
			            </div>
			            <#if ((params.itemshowtype)!'')?trim!='2' && ((params.itemshowtype)!'')?trim!='3'>
						<div class="fh-box">
			            	<#assign urlTemp="/search-action.htm?kw=${searchKW?url}&cat=${(fatParams.catalogId)!''}&bid=${(params.bid)!''}&vm=${(params.vm)!''}&f_price=${(fatParams.priceRangeEnum.order)!''}&s_price=${(fatParams.startPrice)!''}&e_price=${(fatParams.endPrice)!''}&filter=${(fatParams.filter)!''}&ba=${(params.ba)!''}&mkt=${(fatParams.mktType.type)!''}&s_id=${(s_id)!''}&itemshowtype=${(params.itemshowtype)!''}&sort=${(params.sort)!''}">
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
		    	<div class="xs-section">
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
			    </div>
			</div>
			<#-- 主体部分结束 -->
		<#else>		
			<div class="xs-facetbox">
				<#-- 搜索无结果内容 -->
		        <div class="xs-nrbox clearfix">
		            <i class="xs-nrico"></i>
		            <dl class="sorry-box" style="padding-right:20px">
		                <dt>很抱歉，没有找到符合条件的商品！！</dt>
		                <dd>建议您：</dd>
		                <dd>1.&nbsp;看看输入文字是否有误</dd>
		                <dd>2.&nbsp;适当减少筛选条件，可以获得更多结果</dd>
		                <dd>3.&nbsp;调整价格区间</dd>
		                <dd>4.&nbsp;去掉不需要的字词，如"的"，"什么等"</dd>
		                <dd>5.&nbsp;调整关键字，如"nikeaf1运动鞋"改成"nike af1 运动鞋"或"nike af1"</dd>
		            </dl>
		            <dl class="ulike-box">
		                <dt>您是不是想找：</dt>
		                <dd><a href="${searchUrl}s?kw=${'连衣裙'?url}">连衣裙</a></dd>
		                <dd><a href="${searchUrl}s?kw=${'gucci'?url}">gucci</a></dd>
		                <dd><a href="${searchUrl}s?kw=${'外套'?url}">外套</a></dd>
		                <dd><a href="${searchUrl}s?kw=${'lv'?url}">lv</a></dd>
		                <dd><a href="${searchUrl}s?kw=${'衬衫'?url}">衬衫</a></dd>
		                <dd><a href="${searchUrl}s?kw=${'ck'?url}">ck</a></dd>
		                <dd><a href="${searchUrl}s?kw=${'包包'?url}">包包</a></dd>
		                <dd><a href="${searchUrl}s?kw=${'手表'?url}">手表</a></dd>
		            </dl>
		        </div>
		        <#-- 搜索无结果内容结束 -->
		        <#-- 百分点走秀推荐 -->
		        <div id="banner_BrowsingHistory" class="tj_box"></div>
		        <#-- 百分点走秀推荐结束 -->
			</div>
		</#if>
		<#-- 主体部分结束 -->
</div>
<script type="text/javascript" src="http://xres.xiu.com/static/xclk-jsapi.js"></script>
<script type="text/javascript">
$(function(){
<#--百分点异步加载@012.js-->
_BFD=window._BFD||{};_BFD.script=document.createElement("script");_BFD.script.setAttribute("src","http://www.xiustatic.com/static/js/thirdparty/bfd/zx_list_search_new.min.js");_BFD.script.setAttribute("type","text/javascript");_BFD.script.setAttribute("charset","utf-8");document.getElementsByTagName("head")[0].appendChild(_BFD.script);
<#--推荐引擎数据收集-->
LEON.xreDataCollect({siteId:1001,channelId:${(!(params.mkt??) || (params.mkt == 0))?string('1001','1002')},kw:"${params.kw?url}",result:0});
<#-- @abtest判断使用推荐引擎015.js -->
var abtest_ret=$.cookie("abtestRet");var cur_sid=$.cookie("JSESSIONID_WCS");if(abtest_ret&&cur_sid){var abtest_id=abtest_ret.split("|")[0];var ret_id=abtest_ret.split("|")[1];if(abtest_id===cur_sid){if(ret_id==="1"){var ab_id=18;LEON.bfdData({stype:"kw",sattr:"${params.kw?url}",categoryList:["${(selectedCatalog.catalogName)!'all'}"],type:"search",kw:"${params.kw?url}",catId:"<#if ((fatParams??)&&(fatParams.catalogId??)&&(fatParams.catalogId>0))>${fatParams.catalogId!}<#else>${recommendCatIds!}</#if>",bId:"${(fatParams.brandId)!}",hasResult:false,clientName:"${bfdClientName!}"})}else{var ab_id=19;LEON.xreData({hasResult:false,kw:"${params.kw?url}",clientName:"${bfdClientName!}",type:"search",brandId:"${(recommenBrandIds)!}",catId:"${recommendCatIds!''}",itemId:"${recommendItemIds!}",stype:"kw",sattr:"${params.kw?url}",siteId:"1001",channelId:"1001"})}XIU.Window.ctraceClick("atype=exposuret|abproject_id=8|abitem_id=8|abverid="+ab_id,"recommend_engine")}else{recommendItem()}}else{recommendItem()}function recommendItem(){var a=18;$.ajax({url:"http://abtest.xiu.com/GetScriptJsonpServlet.js?_pid=8",dataType:"jsonp",jsonp:"callbackparam",success:function(e){var d=[];var f=$.cookie("JSESSIONID_WCS");d.push(f);d.push(e.name.substring(0,1));$.cookie("abtestRet",d.join("|"),{expires:30*3,path:"/",domain:".xiu.com"});try{if(e.name.substring(0,1)=="1"){LEON.bfdData({stype:"kw",sattr:"${params.kw?url}",categoryList:["${(selectedCatalog.catalogName)!'all'}"],type:"search",kw:"${params.kw?url}",catId:"<#if ((fatParams??)&&(fatParams.catalogId??)&&(fatParams.catalogId>0))>${fatParams.catalogId!}<#else>${recommendCatIds!}</#if>",bId:"${(fatParams.brandId)!}",hasResult:false,clientName:"${bfdClientName!}"})}else{a=19;LEON.xreData({hasResult:false,kw:"${params.kw?url}",clientName:"${bfdClientName!}",type:"search",brandId:"${(recommenBrandIds)!}",catId:"${recommendCatIds!''}",itemId:"${recommendItemIds!}",stype:"kw",sattr:"${params.kw?url}",siteId:"1001",channelId:"1001"})}}catch(c){LEON.bfdData({stype:"kw",sattr:"${params.kw?url}",categoryList:["${(selectedCatalog.catalogName)!'all'}"],type:"search",kw:"${params.kw?url}",catId:"<#if ((fatParams??)&&(fatParams.catalogId??)&&(fatParams.catalogId>0))>${(fatParams.catalogId)!}<#else>${recommendCatIds!}</#if>",bId:"${(fatParams.brandId)!}",hasResult:false,clientName:"${bfdClientName!}"})}},error:function(){LEON.bfdData({stype:"kw",sattr:"${params.kw?url}",categoryList:["${(selectedCatalog.catalogName)!'all'}"],type:"search",kw:"${params.kw?url}",catId:"<#if ((fatParams??)&&(fatParams.catalogId??)&&(fatParams.catalogId>0))>${fatParams.catalogId!}<#else>${recommendCatIds!}</#if>",bId:"${(fatParams.brandId)!}",hasResult:false,clientName:"${bfdClientName!}"})}});XIU.Window.ctraceClick("atype=exposuret|abproject_id=8|abitem_id=8|abverid="+a,"recommend_engine")};
});
</script>
 <#--底部搜索 -->
<div class="xs-btm-search">
<form id="J_searchFormBottom" action="${searchUrl}s" method="get" autocomplete="off">
    <div class="searchbox">
        <div class="inputbg"><input id="J_searchInputBottom" name="kw" class="txt-input" type="text" value="${params.kw?html}" /></div>
        <button class="sbtn" type="submit"></button>
    </div>
</form>
</div>
<script>
<#--百分点推荐回调函数 -->
function showRecommend_noresult(datas,req_id,bfd_id){
	LEON.bfdCreateRecommend(datas,"banner_BrowsingHistory",req_id,bfd_id);		
}
<#-- @006.js -->
if(typeof LEON=="undefined"){LEON={}}LEON.lastinputBottom=$("#J_searchInputBottom").val();$("#J_searchInputBottom").XAutoSuggest({url:"${searchUrl}ajax/autocomplete.htm?jsoncallback=?",params:{type:"min",mkt:"xiu"},width:480,leftOff:0,result:function(a){if(!!a.oclassId){LEON.lastinputBottom=a.display;$("#J_catalogIdInput").val(a.oclassId).removeAttr("disabled")}$("#J_searchForm").submit()}});
</script>
<#-- 底部搜索结束 -->
<#-- CMS页尾 -->
<#include "/resources/cms/static/web/M_footer.html" parse="false">
</body>
</html>
