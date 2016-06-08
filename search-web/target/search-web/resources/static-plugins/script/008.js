/**
 * abtest判断最后确认使用百分点推荐还是用官网推荐
 */
var abtest_ret = $.cookie("abtestRet");//cookie("abtestRet");
var cur_sid = $.cookie("JSESSIONID_WCS");
if(abtest_ret && cur_sid){
	var abtest_id = abtest_ret.split("|")[0];
	var ret_id = abtest_ret.split("|")[1];
	if(abtest_id === cur_sid){  //如果是同一个session
		if(ret_id ==='1'){
			var ab_id = 18;
			//调用百分点
			LEON.bfdData({
				stype : "kw",
				sattr : "${params.kw?url}",
				categoryList : [ "${(selectedCatalog.catalogName)!'all'}" ],
				type:"search",
				kw: "${params.kw?url}",
				catId:"<#if ((fatParams??)&&(fatParams.catalogId??)&&(fatParams.catalogId>0))>${fatParams.catalogId!}<#else>${recommendCatIds!}</#if>",
				bId:"${(fatParams.brandId)!}",
				clientName:"${bfdClientName!}"
			});
		}
		else{
			//调用走秀
			var ab_id = 19;
			LEON.xreData({
				kw : "${params.kw?url}",
				clientName:"${bfdClientName!}",
				type:"search",
				brandId : "${(recommenBrandIds)!}",
				catId : "${recommendCatIds!}",
				itemId : "${recommendItemIds!}",
				stype : "kw",
				sattr : "${params.kw?url}",
				siteId : "1001",
				channelId : "1001"
			});
		}
		XIU.Window.ctraceClick("atype=exposuret|abproject_id=8|abitem_id=8|abverid="+ab_id,"recommend_engine");
	}else{
		recommendItem();
	}
}else{
	recommendItem();
}

function recommendItem(){
	var ab_id=18;
	$.ajax({
		url : "http://abtest.xiu.com/GetScriptJsonpServlet.js?_pid=8",
		dataType : "jsonp",
		jsonp : "callbackparam",
		success : function(a) {
			var abtestRetValue = [];
			var cur_sid = $.cookie("JSESSIONID_WCS");
			abtestRetValue.push(cur_sid);
			abtestRetValue.push(a.name.substring(0, 1));
			$.cookie('abtestRet',abtestRetValue.join("|"),{expires: 30*3, path:'/', domain:'.xiu.com'});
			
			try {
				if (a.name.substring(0, 1) == "1") {
					LEON.bfdData({
						stype : "kw",
						sattr : "${params.kw?url}",
						categoryList : [ "${(selectedCatalog.catalogName)!'all'}" ],
						type:"search",
						kw: "${params.kw?url}",
						catId:"<#if ((fatParams??)&&(fatParams.catalogId??)&&(fatParams.catalogId>0))>${fatParams.catalogId!}<#else>${recommendCatIds!}</#if>",
						bId:"${(fatParams.brandId)!}",
						clientName:"${bfdClientName!}"
					});
				} else {
					// search-page:stype : "kw",sattr : "${params.kw?url}"
					// list-page:stype:"list",sattr:"${selectedCatalog.catalogName?url}"
					// brand-page:stype:"brand",sattr:"${(brandName!)?url}"
					//channelId 官网:1001  美国直发、ebay:1002
					ab_id=19;
					LEON.xreData({
						kw : "${params.kw?url}",
						clientName:"${bfdClientName!}",
						type:"search",
						brandId : "${(recommenBrandIds)!}",
						catId : "${recommendCatIds!}",
						itemId : "${recommendItemIds!}",
						stype : "kw",
						sattr : "${params.kw?url}",
						siteId : "1001",
						channelId : "1001"
					});
				}
			} catch (b) {
				LEON.bfdData({
					stype : "kw",
					sattr : "${params.kw?url}",
					categoryList : [ "${(selectedCatalog.catalogName)!'all'}" ],
					type:"search",
					kw: "${params.kw?url}",
					catId:"<#if ((fatParams??)&&(fatParams.catalogId??)&&(fatParams.catalogId>0))>${fatParams.catalogId!}<#else>${recommendCatIds!}</#if>",
					bId:"${(fatParams.brandId)!}",
					clientName:"${bfdClientName!}"
				});
			}
		},
		error : function() {
			LEON.bfdData({
				stype : "kw",
				sattr : "${params.kw?url}",
				categoryList : [ "${(selectedCatalog.catalogName)!'all'}" ],
				type:"search",
				kw: "${params.kw?url}",
				catId:"<#if ((fatParams??)&&(fatParams.catalogId??)&&(fatParams.catalogId>0))>${fatParams.catalogId!}<#else>${recommendCatIds!}</#if>",
				bId:"${(fatParams.brandId)!}",
				clientName:"${bfdClientName!}"
			});
		}
	});
	XIU.Window.ctraceClick("atype=exposuret|abproject_id=8|abitem_id=8|abverid="+ab_id,"recommend_engine");
}
	
