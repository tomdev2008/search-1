//商品信息
url['productInfo']=url['wcsurl']+"GoodsDetailMainCmd?" + url['parms'] +"&jsoncallback=?";
//相关分类
url['productType']=url['wcsurl']+"GoodsDetailRelatedClsCmd?" + url['parms'];
//浏览记录json
url['productHistory']=url['wcsurl']+"GoodsDetailRecentHistoryCmd?" + url['parms'];
//加入购物车
url['addbag']=url['wcsurl']+"XOrderItemAddCmd?" + url['parms'] +"&random="+url['nowDate']+"&jsoncallback=?";
//添加到收藏夹
url['toFavo']=url['wcsurl']+"XInterestItemAddCmd?" + url['parms'];
//添加喜欢类型
url['loadLove']=url['wcsurl']+"GoodsDetailEbayUserCmd?" + url['parms'];
url['toAddLove']=url['wcsurl']+"GoodsDetailEbayInfoCmd?" + url['parms'];
//分期付款帮助
url['installmentHelp'] = '/help/201109191424.shtml';
//分享到
url['toShare']=url['wcsurl']+"shareCmd?" + url['parms'];
//到货通知
url['arrNotice'] = url['wcsurl']+"dhNoticeCmd?" + url['parms'];
var kill_param=getQueryString("kill");

if(kill_param=='1'){
	$("#sellType").val("1");
}else{
	$(".c_rtop").find("img").each(function(){
		if($(this).attr("src").indexOf("230fb728-17e2-4e01-b73c-d0fab37ef860.png") >0){
			$(this).remove();
		}
	});
}

$goodsInfo = $('#goodsInfoBox');
//设置分类ID提供给推荐引擎
var positions = $('#positionBox > a');
if (positions.length >= 4) {
	var third = positions.get(3);
	$goodsInfo.data('GoodsTypeId',third.href.substring(third.href.lastIndexOf("/") + 1, third.href.lastIndexOf(".")));
} else if (positions.length == 1) {
	var third = positions.get(0);
	$goodsInfo.data('GoodsTypeId','');
} else {
	var third = positions.last();
	$goodsInfo.data('GoodsTypeId',third.attr("href").substring(third.attr("href").lastIndexOf("/") + 1, third.attr("href").lastIndexOf(".")));
}

$('#container_ul li').click(function(){
							var index = $('#container_ul li').index(this);
							id = $('#container' + index);
							/* lazy load begin */
							var imgArr = id.find('img');
							for(var i=0;i<imgArr.length;i++){
								var addr = $(imgArr[i]).attr('src2');
								if (addr) {
									$(imgArr[i]).attr('src',addr);
									$(imgArr[i]).removeAttr('src2');
								}
							}
							/* lazy load end */
							//判断如果是商品详情，则显示所有内容
							id.show().siblings('div.container').hide();
							$(this).addClass('on').siblings('').removeClass('on'); 
							/**统计代码
								switch(index){
									case 0:
										//_gaq.push(['_trackEvent', 'tag', 'click', 'product_info']);
									break;
									case 1:
										//_gaq.push(['_trackEvent', 'tag', 'click', 'service']);
									break;
									case 2:
										//_gaq.push(['_trackEvent', 'tag', 'click', 'questions']);
									break;
									case 3:
										//_gaq.push(['_trackEvent', 'tag', 'click', 'brand story']);
									break;
									default:
									break;
								}*/

						
 });
							
$('#container_ul li a').click(function(){ 
									   $(this).parent('li').trigger('click');
									   this.style.outline = 'none';
									   return false;
									   });
$('#smallPic a').click(function(){
							  $(this).parent('dd').siblings().removeClass('dc').end().addClass('dc');
							  
							  });

if($('#sellType').val()=='4'){
	$('#sellType').val('5');
}
var goodsData={"goodsId":$('#goodsId').val(),"goodsSn":$('#goodsSn').val(),"sellType":$('#sellType').val()};
function loadProductInfo(rand){
	try{
	//加载详细信息
		$.ajax({
				type: "POST",
				url:url['productInfo']+"&rand="+(!!rand?rand:''),
				dataType:"json",
				data:goodsData,
				success: function(data){
					addInfo(data);
					
					//XJS.dprint('成功：商品详情URL：'+url['productInfo']);
					}, 
				error: function(msg){
					$('#productLoading').remove();
					$goodsInfo.html($goodsInfo.html()+ '加载商品信息失败');
					//XJS.dprint('错误：商品详情URL：'+url['productInfo']);
				},
				complete:function(){
					loadLove();//喜欢类型 
					loadShare();//分享到	
					getType();//相关类型
				}
		});
		
	
	recordGoodsIds();//最近浏览记录
	
	} catch(e) {};
}				

loadProductInfo();
				
				
//分享到
function share(type){
	
	var items = $goodsInfo.data('goodInfo');//商品信息
	var goodsName =  $("#goodsName").val(); //商品名称
	var goodsUrl = window.location.href; //商品地址
	var picUrl = skuInfo[0].pic_url + 'g1_300_300.jpg' + '?' + skuInfo[0].img_version; //商品图片
	var xiuPrice = items.XPrice; //走秀价
	var marketPrice = items.MPrice; //市场价
	var PPrice = items.PPrice; //活动价格

	var shareURL = url['toShare'] +"&goodsId="+goodsData['goodsId']+"&goodsEbayUrl="+goodsUrl+"&isShareEbay=true&selltype="+($("#sellType").val==''?'5':$("#sellType").val())+"&goodsSn="+goodsData['goodsSn']+"&random="+url['nowDate']+"&jsoncallback=?";
	
	switch(type){
		case 1:
			//1=分享到腾讯微博
			//_gaq.push(['_trackEvent', 'SNS', 'share', '腾讯微博']);
			share_tx_wb(goodsUrl, picUrl, goodsName, marketPrice, xiuPrice);
			xclkFB(3);//推荐反馈，类型分享3
		break;
		case 2:
			//2=分享到人人网
			//_gaq.push(['_trackEvent', 'SNS', 'share', '人人网']);
			share_renren(goodsUrl, picUrl, goodsName, marketPrice, xiuPrice);
			xclkFB(3);//推荐反馈，类型分享3
		break;
		case 3:
			// 3分享到新浪
			//_gaq.push(['_trackEvent', 'SNS', 'share', '新浪']);
			share_vivi(goodsUrl, picUrl, goodsName, marketPrice, xiuPrice);
			xclkFB(3);//推荐反馈，类型分享3
		break;
		case 4:
			//4=分享到开心网
			//_gaq.push(['_trackEvent', 'SNS', 'share', '开心网']);
			shareKaiXin(goodsUrl, picUrl, goodsName, marketPrice, xiuPrice);
			xclkFB(3);//推荐反馈，类型分享3
		break;
		case 5:
			//5=分享到搜狐
			//_gaq.push(['_trackEvent', 'SNS', 'share', '搜狐微博']);
			shareSouHu(goodsUrl, picUrl, goodsName, marketPrice, xiuPrice);
			xclkFB(3);//推荐反馈，类型分享3
		break;
		case 6:
			//6=分享到邮件
			//_gaq.push(['_trackEvent', 'SNS', 'share', '邮件']);
			window.open (shareURL);
			xclkFB(3);//推荐反馈，类型分享3
		break;
		case 7:
			//7=分享到美丽说
			//_gaq.push(['_trackEvent', 'SNS', 'share', '美丽说']);
			shareMLS(goodsUrl, picUrl, goodsName, marketPrice, xiuPrice);
			xclkFB(3);//推荐反馈，类型分享3
		break;
		default:
		break;
		
		
		}

}

function loadShare(){
	var str = '<span>分享到：</span><a href="javascript:;" onclick="share(1)" id="share1" title="分享到腾讯微博"></a><a href="javascript:;" onclick="share(2)" title="分享到人人网"></a><a href="javascript:;" onclick="share(3)" title="分享到新浪"></a><a href="javascript:;" onclick="share(4)" title="分享到开心网"></a><a href="javascript:;" onclick="share(5)" title="分享到搜狐"></a><a href="javascript:;" onclick="share(7)" title="分享到美丽说"></a><a href="javascript:;" onclick="share(6)" title="分享到邮件"></a>';
	$('#shareBox').html(str);
	}



//记录最近浏览商品
function recordGoodsIds(){
	var id = $('#goodsId').val();
	var sellType = $('#sellType').val();
	id=id+"@"+sellType;
	if(!$.cookie('goodIds') || $.cookie('goodIds')==""){
		 $.cookie('goodIds',id,{expires: 7, path:'/', domain:'.xiu.com'});
		 //getHistory();
		 
	}else{
		
		var goodsids = $.cookie('goodIds').split(',');
		
	    goodsids = $.map(goodsids, function(n){ return n == id ? null : n; });
		
		if(goodsids.length == 10){
			goodsids.pop();
			goodsids.unshift(id);
		}else{
			goodsids.unshift(id);
		}
		
		$.cookie('goodIds',goodsids,{expires: 7, path:'/', domain:'.xiu.com'});
		
		//getHistory();
		
		
		}}
function arrSingle(arrNum){
	var arrNewNum=[];
	var bRepeat=0;
	for(var i=0,cur;(cur=arrNum[i++])||cur===0;bRepeat=0){
	for(var j=0,curS;(curS=arrNewNum[j++])||curS===0;){
	if(cur==curS){bRepeat=1;break;}
	}
	if(!bRepeat)arrNewNum.push(cur);
	}
	return arrNewNum;
}

//取相关参数
function getParaValue(parameters,fullString){

var splitString = fullString.split(parameters), splitStringLen = splitString.length;

if(splitStringLen<2) return -1;

var lastString = splitString[splitStringLen -1],splitLastString = lastString.split('&'),callBack = splitLastString[0].replace(/=/,'');

return encodeURIComponent(callBack);

}
//abtest
function abtestRecomOrBfd(){
	
	$.ajax({
	  type: "GET",
	  url: "http://abtest.xiu.com/GetScriptServlet.js?_pid=3",
	  dataType: "script",
	  timeout:2000,
	  complete: function(x,y){
		if(typeof(obj)!='undefined'){
			  var n = obj.name.indexOf("?")>-1?(obj.name.substring(0,obj.name.indexOf("?"))):"";
			  var abparams = "|abproject_id=3|abitem_id=3|abverid=0";
			  if(n != ""){
			  	 abparams = '|abproject_id=' + getParaValue('abproject_id',obj.name) +'|abitem_id='+ getParaValue('abitem_id',obj.name)+'|abverid='+ getParaValue('abverid',obj.name);
			  }
			  if(n==='1'){bfd();}
			  else{putRecomFn();}
			  XIU.Window.ctraceClick("atype=exposuret"+abparams,"recommend_engine");
		}else{
			(url['nowDate']%2) ? 
				bfd() :									
				putRecomFn();	
				XIU.Window.ctraceClick("atype=exposuret|abproject_id=3|abitem_id=3|abverid=0","recommend_engine");						
			}
	  }
	
	});
}
//取相关分类
function getType(){
	var goodIds = $('#goodsId').val(),goodsSn = $('#goodsSn').val();
	var typeDate = [],typeDateBfd = [];
	$.ajax({
			type: "POST",
			url:url['productType'] +"&goodsId="+goodIds+"&goodsSn="+goodsSn+"&random="+url['nowDate']+"&jsoncallback=?",
			dataType:"json",
			success: function(data){
				data = jsonData(data);
				//XJS.dprint('成功：相关分类URL:'+url['productType'] +"&goodsId="+goodIds+"&goodsSn="+goodsSn+"&random="+url['nowDate']+"&jsoncallback=?");
				if(data=='') return false;
				var typeId = '';
				$.each(data,function(i,k){typeDate.push(k.id+'_'+k.name); typeId = k.id;});
				if($goodsInfo.data('GoodsTypeId') =='' || $goodsInfo.data('GoodsTypeId').indexOf('-')!=-1 ) $goodsInfo.data('GoodsTypeId',typeId);
				var arr = arrSingle(typeDate);
				for(var l = 0; l<arr.length;l++){
					var name  = arr[l].split('_')[1];
					typeDateBfd.push(name);
					}
				$goodsInfo.data('type',typeDateBfd);
				}, 
			complete:function(){
				//bfd();//百分点
				abtestRecomOrBfd();
				recomFn();//推荐引擎接口
				
			}
	});
}
//第一次加载喜欢
function loadLove(){
	$.ajax({
	type: "POST",
	url: url['loadLove']+'&goodsSn='+$('#goodsSn').val() +"&random="+url['nowDate']+"&jsoncallback=?",
	dataType:"json",
	success: function(data){
			var data = jsonData(data);
			var goodsNum = data.goodsNum;
			if(data.isLove == 'Y'){
				$('#addToLove').html("已喜欢<i>"+goodsNum+"</i>");
			}else{
				$('#addToLove').html("喜欢<i>"+goodsNum+"</i>");
			}
		}, 
	error: function(msg){
		
		}
   });
	return false;

}
//添加喜欢
function addLove(){
	var loveType='false';
	var loveText = $('#addToLove').text();
	if(loveText.indexOf('已喜欢')!=-1){
		//表示是已喜欢
		loveType='true';
	}
	$.ajax({
	type: "POST",
	url: url['toAddLove']+'&goodsSn='+$('#goodsSn').val()+'&loveType='+loveType+"&random="+url['nowDate']+"&jsoncallback=?",
	dataType:"json",
	success: function(data){
			var data = jsonData(data);
			var goodsNum = data.goodsNum;
			if(data.isLove == 'Y'){
				$('#addToLove').html("已喜欢<i>"+goodsNum+"</i>");
			}else{
				$('#addToLove').html("喜欢<i>"+goodsNum+"</i>");
			}
		}, 
	error: function(msg){
		
		}
   });
	return false;

}

//取浏览历史
function getHistory(){

	var goodIds = $('#goodsId').val(),goodsSn = $('#goodsSn').val();
	$.ajax({
		type: "POST",
		//url['productHistory']=url['wcsurl']+"GoodsDetailRecentHistoryCmd?" + url['parms']+'&goodsIds='+$.cookie('goodIds');
		url:url['productHistory'] +"&goodIds="+$.cookie('goodIds')+"&goodsSn="+goodsSn+"&supplierCode=1528&random="+url['nowDate']+"&jsoncallback=?",
		dataType:"json",
		success: function(data){
			//XJS.dprint('成功：历史记录URL:'+url['productHistory'] +"&goodIds="+$.cookie('goodIds')+"&currentGoodIds="+goodIds+"&goodsSn="+goodsSn+"&random="+url['nowDate']+"&jsoncallback=?");
			data = jsonData(data);
			//data = jsonAllData(data);
			if(data ==''){
				 //$('#historyBox').html( '暂时没有浏览信息');
				 $('#historyBox').parent().remove();
				 return false;
				}
			var html =[],j =0;
			$.each(data,function(i,k){
				if(i<7 && k != null){
							html[j++] =' <div class="right_2_pro"> <a href="'+k.detailUrl+'" class="list_img_a"><img src="'+k.imgUrl+'" onerror="this.src=\''+$("#errImg100").val()+'\'" ></a>';
							html[j++] ='<p class="txt"><a href="'+k.detailUrl+'">'+k.goodName+'</a></p>';
							html[j++] ='<p class="jc">'+k.price+'</p>';
							html[j++] ='</div>';

				}
								 });
			html = html.join(' ');
			$('#historyBox').html(html);
			
			}, 
		error: function(msg){
			$('#historyBox').parent().remove();
			//XJS.dprint('错误：历史记录URL:'+url['productHistory'] +"&goodIds="+goodIds+"&goodsSn="+goodsSn+"&random="+url['nowDate']+"&jsoncallback=?");
		},
		complete: function (XMLHttpRequest, textStatus) {
	    //最近浏览区域点击
	    var itemid = "000000" + $('#goodsId').val();
			itemid = itemid.substring(itemid.length - 7);
			$('#historyBox').find('div > a').each(function (index, element) {
		    $(element).bind('click', function(){
		    	var clkid = this.href.substring(this.href.lastIndexOf('/') + 1, this.href.lastIndexOf('.'));
					XIU.Window.ctraceClick('dtype=recently|itemid=' + itemid + '|clkid=' + clkid,'xdpv');
				});
		  });
		  $('#historyBox').find('p > a').each(function (index, element) {
		    $(element).bind('click', function(){
		    	var clkid = this.href.substring(this.href.lastIndexOf('/') + 1, this.href.lastIndexOf('.'));
					XIU.Window.ctraceClick('dtype=recently|itemid=' + itemid + '|clkid=' + clkid,'xdpv');
				});
		  });
		}
});

}

function jumpToShoppingBag(orderId,sellType)
{
	  var paramType;
		if(sellType==null||sellType=='')
		{
		var sellType=$("#sellType").val();
        var sType=sellType.split(",");
        for(var i=0;i<sType.length;i++){
                if(sType[i]!="6"){
                        paramType=sType[i];
                        break;
        }      
      }
     }else{
     	paramType=sellType;
     	
     } 	
	$.cookie('shoppingLast',location.href,{path:'/',domain:'.xiu.com'});
	location.href =  url['wcsurl'] + "XOrderItemDisplayCmd?"+ url['parms']+"&wcsOrderId="+orderId+"&sellType=" + paramType+'&shoppingFrom=itemAdd';
}

//清除值
function clearVal(obj,con){
	if(obj.value=='电子邮件：' || obj.value=='手机号码：') obj.value = '';
	obj.className = 'input';
	if(document.getElementById(obj.id+'Tip')){
		document.getElementById(obj.id+'Tip').innerHTML ='';
		}
	
	obj.onblur = function(){
		if(obj.value.length<1){
			obj.value = con;
			obj.className = 'input tip';
			}
		}
		
	
	}
//空值判断
function valIsEmpty(val){
	
	val = val.replace(/(^\s*)|(\s*$)/g,"");
	if(val.length) return true;
	return false
	}
//Email判断
function   isEmail(s)   
{   
    var   patrn=/\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;   
    if   (!patrn.exec(s))   return   false;   
    return   true;   
}
function isChn(s){
      var patrn = /^[u4E00-u9FA5]+$/;
      if(!patrn.test(s)){
       return false;
      }
      return true;
}
//手机判断
function   isPhone(s)   
{   
	var patrn = /^1[0-9]{10}$/;
    //var   patrn=/\d{6,18}/;   
    if   (!patrn.exec(s))   return   false;   
    return   true;   
}

function countSpriceOne(endTime,nowTime){
	var eTime = Math.floor(Number(new Date(endTime.substr(0,4), endTime.substr(5,2) -1, endTime.substr(8,2), endTime.substr(11,2), endTime.substr(14,2), endTime.substr(17,2)))/1000);
    var	nTime = Math.floor(Number(new Date(nowTime.substr(0,4), nowTime.substr(5,2) -1, nowTime.substr(8,2), nowTime.substr(11,2), nowTime.substr(14,2), nowTime.substr(17,2)))/1000);
	
	if(eTime - nTime>0){
		countSprice(endTime,nowTime);
	}else{
		return false;
	}
	return true;
}

//商品详情页价格优化
function countSprice(endTime,nowTime){
	
	try{
		var eTime = Math.floor(Number(new Date(endTime.substr(0,4), endTime.substr(5,2) -1, endTime.substr(8,2), endTime.substr(11,2), endTime.substr(14,2), endTime.substr(17,2)))/1000),
			nTime = Math.floor(Number(new Date(nowTime.substr(0,4), nowTime.substr(5,2) -1, nowTime.substr(8,2), nowTime.substr(11,2), nowTime.substr(14,2), nowTime.substr(17,2)))/1000),
			diffT = $('#onlyTimeSprice').data('diffTime') ? $('#onlyTimeSprice').data('diffTime') :(eTime - nTime);
		  
			$('#onlyTimeSprice').data('diffTime',diffT-1);
			diffT = $('#onlyTimeSprice').data('diffTime');
		
			if(diffT<=0){
				$('#onlySecondSprice').text('00');
				$('#prd_price_div').remove();
				$('#prd_attr_div').remove();
				$goodsInfo = $('#goodsInfoBox');
				$('#productLoading').show();
				loadProductInfo(new Date());
				return false;
			}
			//alert('endTime= '+endTime);	alert('nowTime= '+nowTime);	alert('diffT= '+diffT);
		
		var day = Math.floor(diffT/86400);
		diffT %= 86400;
		var hour   = Math.floor(diffT/3600);
		diffT %= 3600;
		
		var minute = Math.floor(diffT/60);
		diffT %= 60;
		var second = diffT;
		if (hour <= 9) hour = '0' + hour;
		if (minute <= 9) minute = '0' + minute;
		if (second <= 9) second = '0' + second;
		$('#onlyDaySprice').text(day);
		$('#onlyHourSprice').text(hour);
		$('#onlyMinuteSprice').text(minute);
		$('#onlySecondSprice').text(second);
		}catch(e){
		}
		window.setTimeout(function(){countSprice(endTime,nowTime)},1000);
		
	
	}

//秒杀倒计时
function countdown(endTime,nowTime){

	var eTime = Math.floor(Number(new Date(endTime.substr(0,4), endTime.substr(5,2) -1, endTime.substr(8,2), endTime.substr(11,2), endTime.substr(14,2), endTime.substr(17,2)))/1000),
		nTime = Math.floor(Number(new Date(nowTime.substr(0,4), nowTime.substr(5,2) -1, nowTime.substr(8,2), nowTime.substr(11,2), nowTime.substr(14,2), nowTime.substr(17,2)))/1000),
		diffT = $('#onlyTime').data('diffTime') ? $('#onlyTime').data('diffTime') :(eTime - nTime);
      
		$('#onlyTime').data('diffTime',diffT-1);
		diffT = $('#onlyTime').data('diffTime');
	//alert(endTime);	alert(nowTime);	alert(diffT );
	if(diffT<=0){
		//var $qu = $('#inputQuantity');
		$goodsInfo.data('kill',false);
		$('#onlyTime').text('剩余时间：秒杀已过期');
		//$qu.removeAttr('disabled');
		
		//if($qu.attr('limValue')=='0') $('#limitMunSpan').remove();
		//else $('#limitMunSpan').text('【每笔订单限购'+$qu.attr('limValue')+'件】');
		
/*		$('#user_buy_btn')
		.removeClass()
		.addClass('user_buy1')
		.attr('title','放入购物袋')
		.unbind('click')
		.bind('click',function(){
							addShopingBag(this);
							return false;
							});*/
		return false;
	}
	var day = Math.floor(diffT/86400);
	diffT %= 86400;
	var hour   = Math.floor(diffT/3600);
	diffT %= 3600;
	
    var minute = Math.floor(diffT/60);
	diffT %= 60;
    var second = diffT;
    if (hour <= 9) hour = '0' + hour;
    if (minute <= 9) minute = '0' + minute;
    if (second <= 9) second = '0' + second;
    $('#onlyDay').text(day);
	$('#onlyHour').text(hour);
	$('#onlyMinute').text(minute);
	$('#onlySecond').text(second);
	
	window.setTimeout(function(){countdown(endTime,nowTime)},1000);
	
	}
//预售倒计时
function countdownPreSale(endTime,nowTime){
	
	var eTime = Math.floor(Number(new Date(endTime.substr(0,4), endTime.substr(5,2) -1, endTime.substr(8,2), endTime.substr(11,2), endTime.substr(14,2), endTime.substr(17,2)))/1000),
		nTime = Math.floor(Number(new Date(nowTime.substr(0,4), nowTime.substr(5,2) - 1, nowTime.substr(8,2), nowTime.substr(11,2), nowTime.substr(14,2), nowTime.substr(17,2)))/1000),
		diffT = $('#onlyTime').data('diffTime') ? $('#onlyTime').data('diffTime') :(eTime - nTime),
		hour   = Math.floor(diffT/3600),
		day = Math.floor(hour/24);
		$('#preSaleDay').text(day);
	
	}
	
//添加到收藏夹
function addToFavo(e){
	//_gaq.push(['_trackEvent', 'bookmarks', 'click', 'add_bookmarks']);
	var offset = $(e).offset();
	$.ajax({
	type: "POST",
	url: url['toFavo']+'&catentryId='+$("#goodsId").val() +"&random="+url['nowDate']+"&sellType="+$("#sellType").val()+"&jsoncallback=?",
	dataType:"json",
	success: function(data){
			//XJS.dprint('成功：收藏URL:'+url['toFavo']+'&catentryId='+$("#goodsId").val() +"&random="+url['nowDate']+"&jsoncallback=?");
			var data = jsonData(data);
			var msg = '加入到收藏夹成功';
			if(data.seccessfull == 'N'){
				msg = data.message;
			}
			xiuTips(msg,offset.left,offset.top-46,2);
		}, 
	error: function(msg){
		
		//XJS.dprint('失败：收藏URL:'+url['toFavo']+'&catentryId='+$("#goodsId").val() +"&random="+url['nowDate']+"&jsoncallback=?");
		xiuTips('加入收藏夹失败',offset.left,offset.top-46,2);
		
		}
   });
   xclkFB(1);//推荐反馈，类型收藏1
	return false;

}

//添加到购物袋
function addShopingBag(e,method){
		
		switch(method){
			case 1: //秒杀
			sendUserBuyBtnClickData('miaosha');
			break;
			case 20 : //到货通知
			sendUserBuyBtnClickData('daohuotongzhi');
			break;
			default :
			sendUserBuyBtnClickData('gouwudai');
			break;
		}

		if(method!=null&(method==6||method==1)){
			paramType=method;
		}else{
			var sellType=$("#sellType").val();
			var sType=sellType.split(",");
			var paramType;
			for(var i=0;i<sType.length;i++){
				if(sType[i]!="6"){
					paramType=sType[i];
					break;		
				}	
			}
		}
		
		
		var offset = $(e).offset(), 
		
		colorId=$goodsInfo.data('cid') ? $goodsInfo.data('cid') : false,
		sizeId=$goodsInfo.data('sid') ? $goodsInfo.data('sid') :false;
		
		if(!colorId || colorId==-1){
			xiuTips('请先选择 <u>'+$goodsInfo.data('colorsName')+'</u>',offset.left,offset.top-43,2);
			return ;
		}
		if(!sizeId || sizeId==-1){
			xiuTips('请先选择 <u>'+$goodsInfo.data('sizesName')+'</u>',offset.left,offset.top-43,2);
			return ;
		}
		
		
		if(!checkBuyMumber($('#inputQuantity'))) return false;
		var inputQuantity=$("#inputQuantity").val();			
		if(inputQuantity>1&&paramType==6){
			 showTipDiv('分期付款只能购买一件商品',offset.left,offset.top-44,2);
			 return false;
		}
		
		var colorValue = $('#'+colorId).text(), sizeValue =  $('#'+sizeId).text(),catentryId = $goodsInfo.data('catentryId'),part_number = $goodsInfo.data('part_number');
		var postData={"sellType":paramType,"part_number":part_number,"method":"new","catentryId":catentryId,"inputQuantity":inputQuantity,"goods_sn":$("#goodsSn").val()};
		
		switch(method){
			case 1: //秒杀	 未完成		
				kill(e);	
			break;
			case 6:
				//分期
				appInstallment(e,postData);
				xclkFB(2);//推荐反馈，类型加入购物袋2
			break;
			
	/*		case 3:
				//预售
				alert('预售')
			break;*/
			
			case 20 : //到货通知
				xclkFB(4);//推荐反馈，类型到货通知4
				var con = '<label><input type="text" id="noticeEmail" class="input tip" value = "电子邮件：" onfocus = "clearVal(this,\'电子邮件：\')"/><span id="noticeEmailTip"></span></label>'+
				          '<label><input type="text" id="noticePhone" class="input tip" value = "手机号码：" onfocus = "clearVal(this,\'手机号码：\')"/><span id="noticePhoneTip"></span></label>';
				$.xiupop({
						   title:'到货通知',
						   content:con,
						   pageX:offset.left-6, 
						   pageY:offset.top-3, 
						   button:{
							  '确定': function(t) {
								  
								 var email =  $("#noticeEmail").val();
								 var phone =  $("#noticePhone").val();
								  
								 //判断是只需要输入种联系方式可以了
								  if((email=='电子邮件：' ||email.length<1)　&& (phone =='手机号码：' ||phone.length<1)){
									  if(email=='电子邮件：' ||email.length<1　){
										  $('#noticeEmailTip').html('<small class="error">　邮件不能为空</small>'); 
										  return false;
									  }
									  if(phone =='手机号码：' ||phone.length<1){
										  $('#noticePhoneTip').html('<small class="error">　手机不能为空</small>');
										  return false;
									  }
								  }
								  //验证输入了电子邮箱
								  if(!(email=='电子邮件：' ||email.length<1)){
								　	if(!isEmail(email)){
									   $('#noticeEmailTip').html('<small class="error">　邮件格式不正确</small>'); 
									  return false;
									}  
								  }
								  //验证输入了手机号码
								  if(!(phone =='手机号码：' || phone.length<1)){
								   if(!isPhone(phone)){
									   $('#noticePhoneTip').html('<small class="error">　手机格式不正确</small>'); 
									  return false;
									}	
								  }
								  var chose = '';
								  if(email !='电子邮件：' && email.length) chose = '邮件';
								  if(phone !='手机号码：' && phone.length) chose = '短信';
								  if(email !='电子邮件：' && email.length && phone !='手机号码：' && phone.length) chose ='邮件和短信';
								  email=email.replace("电子邮件：","");
								  phone=phone.replace("手机号码：","");	
									$.ajax({
											type: "POST",
											url: url['arrNotice'] +"&skuId="+catentryId+"&email="+email+"&phone="+phone+"&random="+url['nowDate']+"&jsoncallback=?",
											dataType:"json",
											data:postData,
											timeout:60000,
											success: function(result){
												$.closepop();
												$.xiupop({
													title:'到货通知',
													 content:'谢谢您的关注，到货会以'+chose+'的方式通知到您',
													 pageX:offset.left-6, //要调
													 pageY:offset.top-3, //要调
													 button:{
														 '关闭':function(){
															 $.closepop();
															 }
														 }
													 
														 });
												//xiuTips('到货通知成功',offset.left,offset.top-43,2);
												//XJS.dprint('成功：到货通知：URL:'+url['arrNotice'] +"&skuId="+catentryId+"&email="+email+"&phone="+phone+"&random="+url['nowDate']+"&jsoncallback=?");
												
												}, 
											error: function(msg){
												$.closepop();
												xiuTips('出错',offset.left,offset.top-43,2);
												//XJS.dprint('错误：到货通知：URL:'+url['arrNotice'] +"&skuId="+catentryId+"&email="+email+"&phone="+phone+"&random="+url['nowDate']+"&jsoncallback=?");
											}
						  			 });

								  },
								'取消':function(){
										$.closepop();
									}
						  
						   }
						
							  
								 });
			
			
			break;
			default :
			    xclkFB(2);//推荐反馈，类型加入购物袋2
				$.ajax({
					type: "POST",
					url:url['addbag'],
					dataType:"json",
					data:postData,
					timeout:60000,
					async:false,
					success: function(result){
						var data; 
						if(result&&result.length>0){
							data=result[0];
							if(data.successful != null){
								$._xiu_login.showCardNum();
								
								jumpToShoppingBag(data.orderId);
								
							/*	$.xiupop({
									 title:data.successful,
									 content:'购物袋<em>'+data.allNotPayItems+'</em>件商品，合计<b id="wndCartPrice">'+data.totalPay+'</b>',
									 pageX:offset.left-6,
									 pageY: offset.top-3,
									 button:{
										'查看购物袋': function(t) {
											jumpToShoppingBag(data.orderId);
											},
										'继续购物': function(t) {
											$.closepop();
											}
									
									 }
							
								  
									 });*/
								
							}else{
							xiuTips(data.failure,offset.left,offset.top-43,2);
							}
						}
					//XJS.dprint('成功：放入购物袋URL:'+url['addbag']+'&'+$.param(postData));
					}, 
					error: function(msg){
						$.xiupop({
									// title:'系统故障',
									 content:'放入购物袋异常，请检查网络',
									 width:'280px',
									 pageX:offset.left-6,
									 pageY:offset.top-3,
									 button:{
										'关闭': function(t) {
											$.closepop();
											}
									
									 }
						});
						//XJS.dprint('错误：放入购物袋URL:'+url['addbag']+'&'+$.param(postData));
								
	
					}
				});
			
			break;
			
			}
			
	
	return false;
}

//添加已售完
function addSaleoutPic(){
	//if(!document.getElementById('noStock'))
	//{$('#bigPicZone').append('<span class="layer_rl" id="noStock"><img  src="/static/img/seleout.gif"/></span>');
	//}
	}
	
//选择商品
function selectGoods(colorId,sizeId,arr,type){
	
	var cid = colorId,sid = sizeId,useId = []; 
	$goodsInfo.data('sid',sid).data('cid',cid);
	
	//alert('cid:'+colorId+'\n'+'sid:'+sizeId)
	
	if(type=='c'){
		
		 $('#sizesArea a').data('stock',false).addClass('noStock').attr('title','没有库存');
		 //$('#colorsArea a').data('stock',true).removeClass('noStock').removeAttr('title');
		$.each(arr,function(c,b){
		  		if(b.stock==1 && b.colorId == cid) useId.push(b.sizeId);
		});
	}
	if(type=='s'){
		
		// $('#sizesArea a').data('stock',true).removeClass('noStock').removeAttr('title');
		 $('#colorsArea a').data('stock',false).addClass('noStock').attr('title','没有库存');	
		 
		 $.each(arr,function(c,b){
		  		if(b.stock==1 && b.sizeId == sid) useId.push(b.colorId);
		});
		 
	}
		
	for(var i = 0;i<useId.length; i++){
		
		$('#'+useId[i]).data('stock',true).removeClass('noStock').removeAttr('title');
		
				}
	$goodsInfo.data('catentryId',-1).data('part_number',-1);

	if(cid==-1 || sid ==-1) return false;
	
	if(!$goodsInfo.data('preSale')&&!$goodsInfo.data('onSale')){
		
		if($('#'+sid).data('stock') && $('#'+cid).data('stock')){
		//	alert('有库存')
			$goodsInfo.data('stock',true);
			
			if(!$goodsInfo.data('kill')){
				
				$('#user_buy_btn')
				.removeClass()
				.addClass('user_buy1')
				.attr('title','放入购物袋')
				.unbind('click')
				.bind('click',function(){
								addShopingBag(this);
								return false;
								});
				
			}else{
				
				 $('#user_buy_btn')
				 .removeClass()
				 .addClass('user_buy4')
				 .attr('title','立即秒杀')
				 .unbind('click')
				 .bind('click',function(){
								 addShopingBag(this,1);
								 return false;
								 });
				}
		} 
		else{
		//alert('没有库存')
			$goodsInfo.data('stock',false);			
			$('#user_buy_btn')
			.removeClass()
			.addClass('user_buy2')
			.attr('title','到货通知')
			.unbind('click')
			.bind('click',function(){
							addShopingBag(this,20);
							return false;
							});
	
	
		}
		
		}
		//已售完标签
		if($goodsInfo.data('stock')){$('#noStock').remove();}
		else addSaleoutPic();
		//取sku
		var colorValue = $('#'+cid).text(), sizeValue =  $('#'+sid).text(),skuInfoData = jsonGet(skuInfo);
		if(skuInfoData=="") return false;
		var p = skuInfoData.length -1;
	
		while(p>=0){
			if(skuInfo[p].colorId==colorValue&&skuInfo[p].sizeId==sizeValue){
					//保存sku
					$goodsInfo
					.data('catentryId',skuInfo[p].skuId)
					.data('part_number',skuInfo[p].skuNum);
					//更换图片路径
					if(skuInfo[p].pic_url!=$goodsInfo.data('picUrl') && skuInfo[p].pics != null && skuInfo[p].pics.length >0) changeZoom(skuInfo[p]);
					break;
				}
			p--;
		}
	
	
	
	
	}
function picError(e){
	//e.parentNode.href=$('#errImg600').val();
	//e.parentNode.rev=$('#errImg300').val();
	e.src = $('#errImg50').val();
}	
function minpicLoad(e){
	if(parseInt(e.width)>50) picError(e);	
	}	
//改变图片
function changeZoom(sku){

	var path = sku.pic_url;
	var pics = sku.pics;
	
	var img_version ='';
	if(typeof(sku.img_version)!="undefined" && sku.img_version!=null && sku.img_version!='null'){
		 img_version = '?'+ sku.img_version;
  }
  
	//var $goodInfo = $goodsInfo,$Zoomer = $('#Zoomer'),$smallPic = $('#smallPic');
	var $goodInfo = $goodsInfo,$Zoomer = $('#imgPicDiv'),$smallPic = $('#smallPic');
	
	if($goodInfo.data('selectRun')){ clearTimeout($goodInfo.data('selectRun'))};
	
	$goodInfo
	.data('picUrl',path)
	.data('selectRun',setTimeout( function(){

	
		//默认大图url
		//$Zoomer[0].href= path + 'g1_600_600.jpg';
		//alert(path + 'g1_600_600.jpg');
		$Zoomer.find('#bigPic').attr('src',path + 'g1_600_600.jpg' + img_version);
		//中图
		$Zoomer.find('#imgPicMask').attr('src',path + 'g1_400_400.jpg' + img_version);
		$('img:first',$Zoomer)[0].src =path + 'g1_400_400.jpg' + img_version;
		
		//清空图片
		$smallPic.empty();
		$.each(pics,function(i,g){
			var dd = $("<dd>");
			var a = $("<a>");
			var img = $("<img>");
			
			a.attr('href','javascript:;');
			
			img.attr('src',path + g+'_50_50.jpg' + img_version);
			img.attr('onerror','picError(this);');
			img.attr('onload','minpicLoad(this);');
			img.attr('alt',$('#goodsName').val());
			
			a.append(img);
			dd.append(a);
			
			$smallPic.append(dd);
		});
		
		/***
		//小图列表
		//$('a',$smallPic).each(function(i){
		$('img',$smallPic).each(function(i){
						var mum = i +1,self = this,img=self.firstChild;
						//img.src = path + 'g'+mum+'_50_50.jpg';
						//img.onerror = function (evt) {picError(this);}
						//img.onload = function (evt) {minpicLoad(this);}
						//self.href= path + 'g'+mum+'_600_600.jpg';
						//self.rev= path + 'g'+mum+'_300_300.jpg';
						if(i < pics.length){
							$(this).attr('src',path + pics[i]+'_50_50.jpg');
							//$(this).attr('src',path + 'g'+mum+'_50_50.jpg');
							$(this).attr('onerror','picError(this);');
							$(this).attr('onload','minpicLoad(this);');
							$(this).parent().parent().show();
						}else{
						//	$(this).attr('onerror','picError(this);');
						//	$(this).attr('onload','minpicLoad(this);');
						//	$(this).attr('src',path + 'g'+mum+'_50_50.jpg');
							$(this).parent().parent().hide();
						}
   		});***/
													
	$('dd',$smallPic)
	.removeClass('dc')
	//.show('fast')
	.eq(0).addClass('dc');
	//XMagic.refresh();
	},10))
	
	}
	
//申请分期付款
function appInstallment(e,data){

		var offset = $(e).offset();
		var postData=data;
		
		$.ajax({
				type: "POST",
				url:url['addbag'],
				dataType:"json",
				data:postData,
				timeout:60000,
				success: function(result){
					var data; //--
					
					if(result&&result.length>0){//--
						data=result[0];//--
						if(data.successful != null){//--
							jumpToShoppingBag(data.orderId,6);
						}else{//--
							xiuTips(data.failure,offset.left,offset.top-43,2);
							//alert(data.failure);//--
							
						}//--
					}
			//XJS.dprint('成功：分期付款URL:'+url['addbag']+'$'+$.param(postData));
				}, 
				error: function(msg){
					$.xiupop({
								// title:'系统故障',
								 content:'放入购物袋异常，请检查网络',
								 width:'280px',
								 pageX:offset.left-6,
								 pageY:offset.top-3,
								 button:{
									 
									'关闭': function(t) {
										$.closepop();
										}
								
								 }
						
							  
								 });
					//XJS.dprint('错误：分期付款URL:'+url['addbag']+'$'+$.param(postData));

				}
									  
		});

	}

function sendUserBuyBtnClickData(btnType){
	var goodIds = "000000" + $('#goodsId').val();
	goodIds = goodIds.substring(goodIds.length - 7);
	var itemnum = $("#inputQuantity").val();
	var param = "dtype=additem|itemid=" + goodIds + "|addcart=" + btnType + "|itemnum=" + itemnum;
	XIU.Window.ctraceClick(param,"xdpv");
}

//加载商品详细信息
function addInfo(data){
	var info = [];
	data = jsonData(data);
	info['stock'] = jsonGet(data.stock);//库存
	info['nowTime'] = jsonGet(data.nowTime);//当前时间
	info['preferential'] = jsonGet(data.preferential);//优惠
	info['installmentInfo'] = jsonGet(data.installmentInfo);//分期
	info['sizes'] = jsonGet(data.sizes);//尺码
	info['colors'] = jsonGet(data.colors);//颜色
	info['sizeColorStock'] = jsonGet(data.sizeColorStock);//库存
	info['combinationInfo'] = jsonGet(data.combinationInfo);//联盟
	info['goods'] = jsonGet(data.goods);//商品信息
	info['saleType'] = jsonGet(data.saleType);//销售类型
	info['activity'] = jsonGet(data.activity);//活动
	info['limitGoods'] = jsonGet(data.limitGoods);//限购
	info['colorsName'] = jsonGet(data.colorsName);
	info['sizesName'] = jsonGet(data.sizesName);
	info['preSaleGoods']=jsonGet(data.preSaleGoods);//预售
	info['goods'].globalFlag=jsonGet(info['goods'].globalFlag);//全球速递的标志 0：默认，1：直发，2：全球速递，3：香港速递
	info['goods'].spaceFlag=jsonGet(info['goods'].spaceFlag);//全球速递的发货仓库的标志 0：国内、1：香港、2：美国、3：海外一类（非ebay）、4：海外二类(ebay)、5韩风
	info['onSale']=jsonGet(data.onSale); //上架状态：0:下架,1:上架
	info['offSaleShow']=jsonGet(data.offSaleShow);//0：不显示 1：显示 2：显示为[售罄] 3: 显示为[到货通知]
	info['onSaleType']=jsonGet(data.onSaleType); //1：自动，0：手动
	if(info['saleType']!='2')
	{
		$("#sellType").val(info['saleType']);
	}
	
	info['goods'].invType=jsonGet(info['goods'].invType);//判断是AB类还是C类：-分为“先采后销”和“先销后采” 0:先采后销 1:先销后采
	
 var WenLongGoodsInfo ={'00':'<p class="col1 layer12">配送信息：预计<font style="font-weight:bold;color:#d11700">1天内</font>从国内仓库发货，<font style="font-weight:bold;color:#d11700">2-5天</font>送达</p>',
						'01':'<p class="col1 layer12">配送信息：预计<font style="font-weight:bold;color:#d11700">3-5天</font>从国内仓库发货，<font style="font-weight:bold;color:#d11700">5-8天</font>送达</p>',
						'40':'<p class="col1 layer12">配送信息：预计<font style="font-weight:bold;color:#d11700">5天</font>从<font style="font-weight:bold;color:#d11700">美国仓库</font>发货，<font style="font-weight:bold;color:#d11700">12-18天</font>送达</p>',
						'41':'<p class="col1 layer12">配送信息：预计<font style="font-weight:bold;color:#d11700">5天</font>从<font style="font-weight:bold;color:#d11700">美国仓库</font>发货，<font style="font-weight:bold;color:#d11700">12-18天</font>送达</p>',
						'30':'<p class="col1 layer12">配送信息：预计<font style="font-weight:bold;color:#d11700">3天</font>从<font style="font-weight:bold;color:#d11700">美国仓库</font>发货，<font style="font-weight:bold;color:#d11700">12-15天</font>送达</p>',
						'31':'<p class="col1 layer12">配送信息：预计<font style="font-weight:bold;color:#d11700">3天</font>从<font style="font-weight:bold;color:#d11700">美国仓库</font>发货，<font style="font-weight:bold;color:#d11700">12-15天</font>送达</p>',
						'10':'<p class="col1 layer12">配送信息：预计<font style="font-weight:bold;color:#d11700">1天内</font>从<font style="font-weight:bold;color:#d11700">香港仓库</font>发货，<font style="font-weight:bold;color:#d11700">6-9天</font>送达</p>',
						'11':'<p class="col1 layer12">配送信息：预计<font style="font-weight:bold;color:#d11700">2天</font>从<font style="font-weight:bold;color:#d11700">香港仓库</font>发货，<font style="font-weight:bold;color:#d11700">8-11天</font>送达</p>',
						'50':'<p class="col1 layer12">配送信息：预计<font style="font-weight:bold;color:#d11700">1天内</font>从国内仓库发货，<font style="font-weight:bold;color:#d11700">2-5天</font>送达</p>',
						'51':'<p class="col1 layer12">配送信息：预计<font style="font-weight:bold;color:#d11700">5-7天</font>从国内仓库发货，<font style="font-weight:bold;color:#d11700">6-10天</font>送达</p>'};

	var sdcq='';
	// 如果商品有库存、并且商品没有下架，则显示配送时间
	if(info['stock']!=0 && info['onSale']!=0 && info['goods'].spaceFlag !='' && info['goods'].spaceFlag !='null' && info['goods'].spaceFlag != null){
		sdcq = WenLongGoodsInfo[info['goods'].spaceFlag+info['goods'].invType];
	}
	if(typeof sdcq =='undefined'||!sdcq){
		sdcq='';
	}
	/*var sdcq='';//全球速递仓库的嵌入html代码
	if(info['goods'].globalFlag=='2'||info['goods'].globalFlag=='3'){
		var spaceFlag = '';
		if(info['goods'].spaceFlag=='1'){
			spaceFlag='香港仓库';
			sdcq='<p class="col1 layer12">此商品为全球速递商品，由走秀<font style="font-weight:bold;color:#d11700">'+spaceFlag+'</font>发出，预计<font style="font-weight:bold;color:#d11700">3~5个工作日</font>送达</p>';
		}else if(info['goods'].spaceFlag=='2'){
			spaceFlag='美国仓库';
			sdcq='<p class="col1 layer12">此商品为全球速递商品，由走秀<font style="font-weight:bold;color:#d11700">'+spaceFlag+'</font>发出，预计<font style="font-weight:bold;color:#d11700">5~10个工作日</font>送达</p>';
		}
		
		
	}*/
	
	//$('.p_title',$goodsInfo).text($('.p_title',$goodsInfo).text()+''+$('#brandName').val());
	
	$goodsInfo
	.data('colorsName',info['colorsName'])
	.data('sizesName',info['sizesName'])
	.data('goodInfo',info['goods']);

	if(info['goods']=='' ||  jsonGet(data.goods.goodsId)=='' ){alert('此商品可能已被删除');return false};
	
	var html=[],d=0,button ='<a href="#" class="user_buy1" id="user_buy_btn" title="放入购物袋"></a>';
	
	info['saleType'] = info['saleType'].split(',');

	//价格-----------
	info['goods'].mPrice = jsonGet(info['goods'].MPrice);//市场价
	info['goods'].xPrice = jsonGet(info['goods'].XPrice);//走秀价
	info['goods'].sPrice = jsonGet(info['goods'].SPrice);//促销价
	info['goods'].pPrice = jsonGet(info['goods'].PPrice);//活动价
	info['goods'].pScore = jsonGet(info['goods'].PScore);//活动积分
	info['goods'].xScore = info['goods'].pScore ;//走秀积分

	//结束时间
	info['endTime']=jsonGet(data.endTime);
	//商品销售价格类型 秀团价= 3
	info['priceType']=jsonGet(data.priceType);
	
	//mp市场价,xiup走秀价,xp活动价,jf积分,fq分期,tp优惠活动  
	var mp = info['goods'].mPrice==''?'':'　<span>市 场 价：￥'+info['goods'].mPrice+'</span>',
		xiup = xp =fq =jf='',isKill= false;
		var tp = '';
		//如果为明品特卖页进入

		/*tp = (info['preferential'].detail ==''||info['preferential']=='')?''
		:'<p class="col1"><a href="javascript:void(0);" id="t_u"></a> 限 时 抢 购 ：<span class="style32">'+info['goods'].sPrice+'</span> ( 享受优惠，共节省'+info[		        'preferential'].discountAmount+'元 )'+时间+'</p>';*/
		
		tp = (info['preferential'].detail ==''||info['preferential']=='')?''
		:'<p class="col1 xs"><span class="xs_ico">限时抢购</span><span class="style32">'+info['goods'].sPrice+'</span></p>';
			   
		
		//tp=info['preferential'] !=''?'':tp;
		var activityTp='';//info['preferential'].detail !=''?'<a href="javascript:void(0);" id="t_u"></a>':'';
	var isShowSaleCount = true; //显示销售情况
	  for(i=0;i<info['saleType'].length;i++){
		  
		   switch(info['saleType'][i]){
			   case '-1': 
			   	   $goodsInfo.data('mp') ?  amp='' : amp = mp;
				   xp = info['goods'].pPrice==''?'': '<p class="col1">促 销 价：<span class="style3">'+info['goods'].pPrice+'</span>'+amp+'</p>';
				   /*jf = '<p class="col1 layer12">购买此商品可获得<b>'+info['goods'].pScore+'</b>积分</p>';*/
				   $goodsInfo.data('mp',true);
			   break;
			   /**
			   case '5':
				  //alert('普通购物')
				  xiup = info['goods'].xPrice==''?'':'<p class="col1">走 秀 价：<span class="style3">'+info['goods'].xPrice+'</span>'+mp+'</p>';
				  jf = '<p class="col1 layer12">购买此商品可获得<b>'+info['goods'].xScore+'</b>积分</p>';
				  $goodsInfo.data('mp',true);
			   break;**/
			   case '1':
				  $goodsInfo.data('mp') ?  amp='' : amp = mp;
				  xp = info['goods'].pPrice ==''?'':'<p class="col1">秒 杀 价：<span class="style3">'+info['goods'].pPrice+'</span>'+amp+'</p>';
				  button = '<a  href="#" class="user_buy4" id="user_buy_btn" title="立即秒杀"></a>';
				  $goodsInfo.data('kill',true).data('mp',true);
			   break;
			   case '3':
				  //alert('预售')
				  xiup = info['goods'].xPrice==''?'':'<p class="col1">走 秀 价：<span class="style3">'+info['goods'].xPrice+'</span>'+mp+'</p>';
				  /*jf = '<p class="col1 layer12">购买此商品可获得<b>'+info['goods'].xScore+'</b>积分</p>';*/
				  button = '<a  href="#" class="user_buy5" id="user_buy_btn" title="我要预订"></a>';
				  $goodsInfo.data('preSale',true).data('mp',true);
			   break;
			   case '6':
				  //alert('分期付款')
				  fq = 1;
			   break;
			   
			   case '2':
				   //alert('（活动）名品特卖')
				  $goodsInfo.data('mp') ?  amp='' : amp = mp;
				  tp='';
				  if(parseFloat(info['goods'].sPrice) < parseFloat(info['goods'].xPrice))
				  {
					   xp = info['goods'].sPrice==''?'': '<p class="col1 xs"><span class="xs_ico">限时特卖</span><span class="style32">'+info['goods'].sPrice+'</span></p><p class="col1">走 秀 价：￥<span>'+info['goods'].xPrice+'</span>'+amp+'</p>';
					  /*jf = '<p class="col1 layer12">购买此商品可获得<b>'+info['goods'].pScore+'</b>积分  '+activityTp+'</p>';*/
					  $goodsInfo.data('active',true).data('mp',true);
					  isShowSaleCount = false;
				  }else{
					   xp = info['goods'].xPrice==''?'': '<p class="col1 xs"><span class="xs_ico">限时特卖</span><span class="style32">'+info['goods'].xPrice+'</span></p>'+(info['goods'].mPrice==''?'':('<p class="col1 xs">市 场 价：￥'+info['goods'].mPrice+'</p>'));
					  /*jf = '<p class="col1 layer12">购买此商品可获得<b>'+info['goods'].pScore+'</b>积分  '+activityTp+'</p>';*/
					  $goodsInfo.data('active',true).data('mp',true);
					  isShowSaleCount = false;
				  }
				  
			   break;
			   case '4':
				  //alert('（活动）奢华会')
				  $goodsInfo.data('mp') ?  amp='' : amp = mp;
				  xp = info['goods'].xPrice==''?'' : '<p class="col1">活 动 价：<span class="style3">'+info['goods'].xPrice+'</span>'+amp+'</p>';
				  /*jf = '<p class="col1 layer12">购买此商品可获得<b>'+info['goods'].pScore+'</b>积分  '+activityTp+'</p>';*/
				  $goodsInfo.data('active',true).data('mp',true);
				  isShowSaleCount = false;
			   break;
			   
			   default:
			   
					if(info['priceType'] == '1'){
						 
						 xiup = '<p class="col1 xs"><span class="xs_ico">限时特卖</span><span class="style32">'+info['goods'].sPrice+'</span></p>';
						 
					 var openXiup ='<p class="col1 sec_time" id="onlyTimeSprice">剩余<span style="color:#CF1700; font-weight:bold" class="h" id="onlyDaySprice">00</span>天<span style="color:#CF1700; font-weight:bold" class="m" id="onlyHourSprice">00</span>小时<span style="color:#CF1700; font-weight:bold" class="s" id="onlyMinuteSprice">00</span>分<span style="color:#CF1700; font-weight:bold" class="hs" id="onlySecondSprice">00</span>秒</p>';	
		
		                 if(!countSpriceOne(info['endTime'],info['nowTime'])){
							 openXiup = '';
						 }
						 
						 xiup += openXiup + (info['goods'].xPrice==''?'':'<p class="col1">走 秀 价：￥<span>'+info['goods'].xPrice+'</span>'+mp+'</p>');	
						 
					}else if(info['priceType'] == '3'){
						 xiup = '<p class="col1 xs"><span class="xs_ico">限时团购</span><span class="style32">'+info['goods'].sPrice+'</span></p>';
						 
					  var openXiup ='<p class="col1 sec_time" id="onlyTimeSprice">剩余<span style="color:#CF1700; font-weight:bold" class="h" id="onlyDaySprice">00</span>天<span style="color:#CF1700; font-weight:bold" class="m" id="onlyHourSprice">00</span>小时<span style="color:#CF1700; font-weight:bold" class="s" id="onlyMinuteSprice">00</span>分<span style="color:#CF1700; font-weight:bold" class="hs" id="onlySecondSprice">00</span>秒</p>';	
		
		                 if(!countSpriceOne(info['endTime'],info['nowTime'])){
							 openXiup = '';
						 }
						 
						 xiup += openXiup + (info['goods'].xPrice==''?'':'<p class="col1">走 秀 价：￥<span>'+info['goods'].xPrice+'</span>'+mp+'</p>');	
						 
					}else{
						
						 if(info['preferential']!='' && info['preferential'].detail !=''){
							 
				var openXiup ='<p class="col1 sec_time" id="onlyTimeSprice">剩余<span style="color:#CF1700; font-weight:bold" class="h" id="onlyDaySprice">00</span>天<span style="color:#CF1700; font-weight:bold" class="m" id="onlyHourSprice">00</span>小时<span style="color:#CF1700; font-weight:bold" class="s" id="onlyMinuteSprice">00</span>分<span style="color:#CF1700; font-weight:bold" class="hs" id="onlySecondSprice">00</span>秒</p>';	
				
					if(!countSpriceOne(info['endTime'],info['nowTime'])){
							 openXiup = '';
						 }
				
							xiup += openXiup + (info['goods'].xPrice==''?'':'<p class="col1">走 秀 价：￥<span>'+info['goods'].xPrice+'</span>'+mp+'</p>');		 
						 }else{
							xiup = info['goods'].xPrice==''?'':'<p class="col1">走 秀 价：<span class="style3">'+info['goods'].xPrice+'</span>'+mp+'</p>';
						 }
					     
						 
					}

				  /*jf = '<p class="col1 layer12">购买此商品可获得<b>'+info['goods'].xScore+'</b>积分</p>';*/
				  $goodsInfo.data('mp',true);
			   break;
			   }
										  
		  }
		  
	  html[d++] = '<div id="prd_price_div" class="xxjx">'; 
	  //加入商品的发货创库的代码显示
	  html[d++] = tp + xiup + xp + jf+sdcq;
	  
	  if(info['activity']!=''&&info['activity'].left_count>=0&&info['saleType']==1){
		 //名品特卖和奢华会不显示销售情况
		 if(isShowSaleCount){
	  		//销售情况			
	    	html[d++] ='<p class="col1">销售情况：售出<span class="layer10">'+info['activity'].sales_count+'</span>件；还剩<span class="layer10">'+info['activity'].left_count+'</span>件 </p>';
	     }
	   }
	   
	  //倒计时
	  info['activity'].end_dt = jsonGet(info['activity'].end_dt);
	  //秒杀剩余时间
	  if(info['activity'].end_dt!='' && ($goodsInfo.data('kill') || $goodsInfo.data('active'))) html[d++] ='<p class="col1 layer11" id="onlyTime">剩余时间：<span id="onlyDay">00</span>天<span id="onlyHour">00</span>时<span id="onlyMinute">00</span>分<span id="onlySecond">00</span>秒 </p>';
	   
	  
	   
	   html[d++] ='</div>';
			  
	  //frame		
	  html[d++] ='<div id="prd_attr_div" class="cm">';	
	  html[d++] ='<div class="modleDiv">';
	  
	  
	  //colors-----------
	  if(info['colors']!=''){
		  
		  html[d++] ='<div class="cm_hz" id="colorsArea">'+
			  '<div class="xsxm">'+info['colorsName']+'：</div>'+
			  '<div class="cmxs noLimit">'+
				'<ul>';
	  $.each(info['colors'],function(a,b){
									 
				  
					  html[d++] ='<li><a href="#" rel="color" id='+b.attrId+'>'+b.attrValue+'</a></li>';
					  
				   });
	   html[d++] ='</ul>'+
			  '</div>'+
			'</div>';
		  
		  
		   }
		   
	 //size-----------
	  if(info['sizes']!=''){
		  
	  html[d++] ='<div class="cm_hz" id="sizesArea">'+
			  '<div class="xsxm">'+info['sizesName']+'：</div>'+
			  '<div class="cmxs noLimit">'+
				'<ul>';
	  var sizeSort = '';
	  $.each(info['sizes'],function(a,b){
						sizeSort += ',' + b.attrValue;

					  html[d++] ='<li><a href="#" rel="size" id='+b.attrId+'>'+b.attrValue+'</a></li>';

				   });
	   html[d++] ='</ul>'+
			  '</div>'+
			'</div>';
		  
		  
		  }
		  
		 //提交-------------	
		//上架
	  var changeAmount = 'up';
	  if(info['onSale']==1){
		    //上架，库存为0
		   	if(info['stock']==0){
				button ='<a  href="#" class="user_buy2" id="user_buy_btn" title="到货通知"></a>';
				changeAmount='up dis';
				$goodsInfo.data('onSale',true);
				}
	 }
	 //下架
	  if(info['activity']!=''&&info['saleType']=='2'&&!info['activity'].isActGoods){
	  	//名品特卖商品详细下活动结束后，将“商品下架”按钮改成“活动已结束”
		 button ='<a class="user_buy8" id="user_buy_btn_onsale" title="活动已结束"></a>';
	  }else if(info['onSale']==0){
		/*	
		
		if(info['offSaleShow']==0) button ='<a class="user_buy6" id="user_buy_btn_onsale" title="商品已下架"></a>';
			 // 显示已售完+灰掉加入购物车 
			 if(info['offSaleShow']==2){
				 addSaleoutPic();
				 button ='<a href="javascript:void(0)" class="user_buy1 btnDisable" title="不能放入购物袋"></a>';
				 }
			 
			//显示已售完+到货通知 
			 if(info['offSaleShow']==3){
				 addSaleoutPic();
				 button ='<a  href="#" class="user_buy2" id="user_buy_btn" title="到货通知"></a>';
				 }*/
				 //下架就显示到货通知
				 //addSaleoutPic();
				// button ='<a  href="#" class="user_buy2" id="user_buy_btn" title="到货通知"></a>';
				// $goodsInfo.data('onSale',true);
				//吕律需求：下架显示状态，到货通知：显示到货通知按钮；不显示&售罄：将加入购物车按钮灰掉，
				if(info['offSaleShow']==3){
					 button ='<a  href="#" class="user_buy2" id="user_buy_btn" title="到货通知"></a>';
					 changeAmount='up dis';
					 $goodsInfo.data('onSale',true);
				 }else{
				 	 button ='<a  class="user_buy7" id="user_buy_btn_onsale" title="不能放入购物袋"></a>';
					 changeAmount='up dis';
					 $goodsInfo.data('onSale',true);
				 }
				 
		}
		  
	   	var ln= jsonGet(info['limitGoods'].limitNumber),
			ln = (ln!='') ? ln : 10,
			//limStr = (ln !=10) ? '<span id="limitMunSpan">(每用户限购'+ln+'件)</span>':'';	
			limStr ='';	
	  //buy Input----------
	   html[d++] ='<div class="cm_hz"><div class="xsxm">我要买：</div>'+
			  '<div class="cmxs">'+
			  '<span class="down dis" id="minusAmount"></span><input type="text" id="inputQuantity" name="inputQuantity" value="1"  limValue="'+ln+'"/><span class="'+changeAmount+'" id="plusAmount"></span>'+
			  '件 '+limStr+'</div>'+
		   ' </div>';

	  // 运费----------
	  //html[d++] ='<div class="cm_hz"><img src="/static/css/default/img/free.gif" alt="" title=""/></div>';
	  
	  //frame
	  html[d++] ='<div class="buy_it rela" >';
  
	 
	 
	  
	  html[d++] =button;
   
	 if(info['installmentInfo']!='' && fq ==1 && !$goodsInfo.data('preSale') &&  !$goodsInfo.data('kill') ) html[d++] ='<a id="stageLink" href="#">分期付款</a> ';
	   html[d++] ='<div class="sg_btn"><a class="sc_love" href="#" id="addScLove"><span class="lbg"></span><span class="mbg"><span class="love_ico"></span><span id="addToLove">喜欢<i></i></span></span><span class="rbg"></span></a>';
	  
	  html[d++] ='<a href="#" class="sc_t" id="addToFavoBtn">加入收藏</a>';
	  
	   html[d++] ='</div>';
	  
	  html[d++] ='</div>';//end cm
	  
	  //frame
	  html[d++] ='</div></div>';//end cm 	modleDiv  
	 if($goodsInfo.data('preSale')) html[d++] ='<div class="xxjx"><p class="col1 layer11" id="preSaleTime">预计到货时间：<span id="preSaleDay">00</span> 天 </p></div>';
	 if(info['preferential']!='' && info['preferential'].detail !=''){
		  var detailInfo ='';
		  for(var i=0;i<info['preferential'].detail.length;i++){
			  if(i==0){
				detailInfo +='<li>商品已参加"'+info['preferential'].detail[i].activityName+'"活动</li>';
			  }else{
				detailInfo +='<li>'+info['preferential'].detail[i].activityName+'</li>';  
			  }
		  }
		   html[d++] ='<div class="yh_tips"><h2>优惠提示</h2><ul>'+detailInfo+'</ul></div>';
	   } 	 
	 html = html.join(' ');	
	  
	 $('#productLoading').hide();//loading remove
	 
	 $goodsInfo.html($goodsInfo.html()+ html);//add html
	 /**
	 //尺码排序
	 sizeSort = sizeSort.replace(",", "").split(',').sort();
	 
	//if (isNaN(sizeSort[0])) {
		sizeSort.sort(function(a, b) {return a.localeCompare(b)})
	//} else {
	//	sizeSort.sort(function(a, b) {return a - b})
	//}
	
	sizeSort = '' + sizeSort + '';
	sizeSort = sizeSort.split(",");
	for (i = 0; i < sizeSort.length; i++) {
		$('#sizesArea li').each(function() {
			if ($(this).text() == sizeSort[i]) {$(this).appendTo($('#sizesArea ul'))}
		})
	}
	**/
	 //处理倒计时---------
	 if(info['activity']!=''&& info['activity'].end_dt!='' && info['nowTime']!='' && ($goodsInfo.data('kill') || $goodsInfo.data('active'))) countdown(info['activity'].end_dt,info['nowTime']);
	 //预售天
	 if($goodsInfo.data('preSale')) countdownPreSale(info['preSaleGoods'].preSaleArrivalTime,info['nowTime']);
	 
	   //显示分期付款-------------
	 $('#stageLink').click(function(e){
									var offset = $(this).offset(),html =[],d=0;

									colorId=$goodsInfo.data('cid') ? $goodsInfo.data('cid') : false,
									sizeId=$goodsInfo.data('sid') ? $goodsInfo.data('sid') :false;
									
									if(!colorId || colorId==-1){
										xiuTips('请先选择 <u>'+$goodsInfo.data('colorsName')+'</u>',offset.left,offset.top-43,2);
										return false;
									}
									if(!sizeId || sizeId==-1){
										xiuTips('请先选择 <u>'+$goodsInfo.data('sizesName')+'</u>',offset.left,offset.top-43,2);
										return false;
									}
									var inputQuantity=$("#inputQuantity").val();			
									if(inputQuantity>1){
										 showTipDiv('分期付款只能购买一件商品',offset.left,offset.top-44,2);
										 return false;
									}
									var mt = mc = info['installmentInfo'].installmentCol,sbankName='';
									 html[d++] ='<table width="100%" class="payfor" cellpadding="0" cellspacing="0">'+
													  '<tr><th>银行</th><th>'+
													  mc.join('期</th><th>')+
													  '期</th></tr>';
									$.each(info['installmentInfo'].detail,function(a,b){
												  
			  html[d++] ='<tr title="'+b.description+'"><td><img src="'+b.bankLogoUrl+'" title="'+b.bankName+'"></td>';
			 				 // sbankName+=b.bankName+',';
							  for(var i=0;i<mt.length;i++){
								  
								  if(inArray(mt[i],b.times)){
									  
									  var j = getArrayPos(mt[i],b.times);
									  html[d++] ='<td><span style="color:#f60">'+b.ways[j]+'</span>元 × '+b.times[j]+'期</td>';
									  }
									  
								  else{
									  html[d++] ='<td>-</td>';
									  
									  }
								  
								  }
							  html[d++] ='</tr>';
			  
			   
			  });
									
			html[d++] ='</table>'+
												//'<p><small>'+sbankName.substring(0,sbankName.length-1)+'信用卡在线分期，实时自动审核，支付更快捷更省心！</small></p>'+
						'<p><small>信用卡在线分期，实时自动审核，支付更快捷更省心！</small></p>'+
						'<a href="javascript:void(0);" class="appInstallment" onclick="addShopingBag(this,6)"><img src="/static/css/default/img/sqfq.gif" width="83" height="25"></a><small><a href="'+url['installmentHelp']+'" target="_blank">查看分期付款帮助</a></small>';
									html = html.join(' ');

									 $.xiupop({
										 title:'分期付款',
										 content:html,
										 width:'500px',
										 pageX:offset.left-202,
										 pageY:offset.top-112
										 });

									return false;
									});
	 //加入收藏
	 $('#addToFavoBtn').click(function(){
									   
						checkLogin(this,addToFavo);
						return false;
						});
	 //添加喜欢类型
	 $('#addScLove').click(function(){
									   
						checkLogin(this,addLove);
						return false;
						});
	 //优惠活动----------
	  $('#t_u').click(function(){
							   
							   var offset = $(this).offset(),html =[],d=0;
							   
							    html[d++] = '<ul>';
							   $.each(info['preferential'].detail,function(i,j){
									html[d++] = '<li>'+j.activityName+'</li>'
						 		 });
							    html[d++] = '</ul>';
							    //html[d++] = '<p>折　扣：<em>'+info['preferential'].discountAmount+'</em></p>';
					  			html[d++] = '<p>共节省：<em>'+info['preferential'].discountAmount+'</em>元</p>';
								html = html.join(' ');
								
							   $.xiupop({
								 title:'享受的优惠活动',
								 content:html,
								 //skin:'white',
								 width:'300px',
								 pageX:offset.left-232,
								 pageY:offset.top+22
								 });
							   return false;
							   });

  
	//选择尺码,颜色
	$('.cmxs').click(function(e){
	 var a = $(e.target);
	 if(a[0].tagName.toUpperCase()=='A'){
		
		 a.parent('li').siblings('li').removeClass().end().addClass('selected');
		 
		 if(a[0].rel=='color'){
			 
			 sid = $goodsInfo.data('sid') ? $goodsInfo.data('sid') : -1; 
			 
			 selectGoods(a[0].id,sid,info['sizeColorStock'],'c');
		 }
		 if(a[0].rel=='size'){
			 
			 cid = $goodsInfo.data('cid') ? $goodsInfo.data('cid') : -1;
			 selectGoods(cid,a[0].id,info['sizeColorStock'],'s');
		 }
		e.stopPropagation();
		  
		 return false;
		 }
	 });
	
	//默认选中
	setTimeout(function(){
		
		//if($('a[rel=color]').length>0){$('a[rel=color]:first').trigger('click');}
		//if($('a[rel=size]').length>0) {$('a[rel=size]:first').trigger('click');}
		//默认选择中主sku尺码
		if($('a[rel=size]').length>0) {
			$.each(info['sizes'],function(a,b){
				   if(skuInfo[0].sizeId == b.attrValue){
					   $('#'+b.attrId).trigger('click');
					   return false;
					   }
				   });
		}
		//默认选择中主sku颜色
		if($('a[rel=color]').length>0){
			$.each(info['colors'],function(a,b){
				   if(skuInfo[0].colorId == b.attrValue){
					   $('#'+b.attrId).trigger('click');
					   return false;
					   }
				   });
		} 
		//上架处理	
		if(info['onSale']==1){
			$.each(info['sizeColorStock'],function(a,b){
												   if(b.stock==1){
													   $('#'+b.colorId).trigger('click');
													   $('#'+b.sizeId).trigger('click');
													   return false;
													   }
												   })
		}
		var itemid = "000000" + goodsData['goodsId'];
		itemid = itemid.substring(itemid.length - 7);
		var addcart;
		var cln = $("a[class^='user_buy']").attr('class');
		switch(cln){
			case 'user_buy1':
				addcart = 'gouwudai';
			break;
			case 'user_buy2':
				addcart = 'daohuotongzhi';
			break;
			case 'user_buy3':
				addcart = 'jijiangkaishi';
			break;
			case 'user_buy4':
				addcart = 'miaosha';
			break;
			case 'user_buy5':
				addcart = 'woyaoyuding';
			break;
			case 'user_buy6':
				addcart = 'yixiajia';
			break;
			case 'user_buy7':
				addcart = 'yishouwan';
			break;
			case 'user_buy8':
				addcart = 'yijieshu';
			break;
			default:
				addcart = 'gouwudai';
		}
		var attr = $('#colorsArea a').length + $('#sizesArea a').length;
		var optattr = $('#colorsArea a:not(.noStock)').length + $('#sizesArea a:not(.noStock)').length;
		XIU.Window.ctraceClick('dtype=areaload|itemid=' + itemid + '|addcart=' + addcart + '|attr=' + attr + '|optattr=' + optattr,'xdpv');
	},300);
	
	
	//购买数量
	$('#inputQuantity').blur(function(e){checkBuyMumber($(this));})
	.change(function(e){checkBuyMumber($(this));});
	//点击修改数量
	$('#minusAmount').click(function(e){
									 if($(this).hasClass('dis')) return false;
									 input = $('#inputQuantity');
									 val = parseInt(input.val());
									 input.val(val-1);
									 checkBuyMumber(input);
									 return false;
	})
	//点击修改数量
	$('#plusAmount').click(function(e){
									if($(this).hasClass('dis')) return false;
									input = $('#inputQuantity');		
									val = parseInt(input.val());
									input.val(val+1);
									checkBuyMumber(input);
									return false;	
							 							 
 	})
	//放入购物袋
	$('#user_buy_btn').bind('click',function(e){
							var cln = this.className;
								switch(cln){
									case 'user_buy2':
										addShopingBag(this,20);
									break;
									case 'user_buy4':
										addShopingBag(this,1);
									break;
									
									case 'user_buy5':
										addShopingBag(this,3);
									break;
									
									default:
										addShopingBag(this);
									break;
									
									
									
									}	  
									  return false;
									  });

	//bfdload();//加载百分点
	}
	
//秒杀
function kill(e) {
	checkLogin(e,doKill);	 
}
function doKill(e){
 		var offset = $(e).offset();
		var colorId=$goodsInfo.data('cid') ? $goodsInfo.data('cid') : false;	
		var sizeId=$goodsInfo.data('sid') ? $goodsInfo.data('sid') :false;

		if(!colorId){
			
			xiuTips('请先选择 <u>'+$goodsInfo.data('colorsName')+'</u>',offset.left,offset.top-43,2);
			return ;
		}
		if(!sizeId){

		
			xiuTips('请先选择 <u>'+$goodsInfo.data('sizesName')+'</u>',offset.left,offset.top-43,2);
			return ;
		}
	 if(!checkBuyMumber($('#inputQuantity'))) return false;
	var catentryId = $goodsInfo.data('catentryId'),part_number = $goodsInfo.data('part_number');
	var skuId= catentryId;
	var skuCode =part_number;
	var goodsSn =$("#goodsSn").val();
	var inputQuantity=$('#inputQuantity').val();
	addOrderItems(skuId,inputQuantity,url['parm'].storeId,url['parm'].catalogId, skuCode, goodsSn, "1", e);
}
	
//检查购买数量	
function checkBuyMumber(obj){
	
	if(!obj) return false;
	offset = obj.offset();
	val = obj.val();
	down = $('#minusAmount');
	up = $('#plusAmount');
	down.removeClass('dis');
	up.removeClass('dis');
	maxVal = parseInt(obj.attr('limValue'));
	
	 if(isNaN(val)){
		 showTipDiv('必须为数字',offset.left-20,offset.top-44,2);
 		 obj.val(1);
		 down.addClass('dis');
		 return false;
		 }
	if(val==1){
		down.addClass('dis');
	}
	if(val<1){
		//showTipDiv('至少购买一件',offset.left-20,offset.top-44,2);
		obj.val(1);
		down.addClass('dis');
		return false;
		}	 
	if(maxVal!=0){
			if(val==maxVal){
				up.addClass('dis');
			}
			 if(val>maxVal){
				 showTipDiv('您最多只能购买 '+maxVal+' 件',offset.left-20,offset.top-44,2);
				 obj.val(maxVal);
				 up.addClass('dis');
				 return false;
				 }
		}
	return true;
	//在此处重新订单计算价格
} 
function addOrderItems(catentryId, inputQuantity, storeId, catelogId, skuCode,goods_sn, type, e){
	var offset = $(e).offset();
	$.ajax({
		
		type : "POST",
		url : url['wcsurl']+ "XOrderItemAddCmd?storeId=" + storeId + "&catalogId=" + catelogId + "&sellType=" + type + "&part_number=" + skuCode + "&method=new&catentryId="+catentryId+"&inputQuantity="+inputQuantity+"&goods_sn=" + goods_sn + "&random" + Math.random()+"&jsoncallback=?",
		dataType:"json",
		timeout:60000,
		success : function(result) {
				var data;
				if(result&&result.length>0){
				data=result[0];
				} 
				
				//商品已成功添加至购物袋
				if(data.successful != null){
					if(type == "1") {
						window.location.href= url['wcsurl'] + "ConfirmOrder?storeId=" + storeId + "&catalogId=" + catelogId + "&wcsOrderId=" + data.orderId;
					} else {
						
						$._xiu_login.showCardNum();
						jumpToShoppingBag(data.orderId,storeInfo['storeId'],storeInfo['catalogId']);
						
						/*$.xiupop({
						   title:data.successful,
						   content:'购物袋<em>'+data.allNotPayItems+'</em>件商品，合计<b id="wndCartPrice">'+data.totalPay+'</b>',
						   pageX:offset.left-6,
						   pageY:offset.top-3,
						   button:{
						  '查看购物袋': function(t) {
							  jumpToShoppingBag(data.orderId,storeInfo['storeId'],storeInfo['catalogId']);
							  },
						  '继续购物': function(t) {
							  $.closepop();
							  }
						  }
						});*/
					}
				}else{
					xiuTips(data.failure,offset.left,offset.top-43,2);
				}
		},
		error : function(result) {
			xiuTips(result,offset.left,offset.top-43,2);
		}
	});
}


//推荐接口
function getParaValue(parameters,fullString){
	var splitString = fullString.split(parameters), splitStringLen = splitString.length;
	if(splitStringLen<2) return'';
	var lastString = splitString[splitStringLen -1],splitLastString = lastString.split('&'),callBack = splitLastString[0].replace(/=/,'');
	return callBack;
}

function recomFn(){
    var items = $goodsInfo.data('goodInfo');
    var recom = {
        userId: $.cookie('xiu.login.userId')!= null ? $.cookie('xiu.login.userId') : null,
        objId:items.goodsId,
        xPrice:items.xPrice,
        categoryId:$goodsInfo.data('GoodsTypeId'),//最后一级分类id
        brandId:$('#brandId').val(),//品牌id//要改模板增加
        sceneId:'',
        pageId:'',
        algorithmInd:''
    };

    var parm = getParaValue('xclk_ind',location.href);    
    if(parm!=''){
        recom.sceneId = parm.split('_')[0];
        recom.pageId =  (typeof(parm.split('_')[1])=='undefined') ?'': parm.split('_')[1];
        recom.algorithmInd= (typeof(parm.split('_')[2])=='undefined') ?'': parm.split('_')[2];
    }
    //推荐引擎
    try{
        xclk.onLoad = function(){
            var vo = new xclk.VO();
            vo.user_id =  recom.userId;    //用户登录id
            vo.obj_id = recom.objId;     //用户浏览的商品id、品类id、品牌id
            vo.obj_type = 1;        //商品详情页为1 品类首页为2 品牌首页为3
            vo.x_price = recom.xPrice;    //如果是浏览商品详情页，则赋值为该商品的走秀价
            vo.category_id = recom.categoryId;   //如果是浏览商品详情页，则赋值为该商品的三级分类id
            vo.brand_id = recom.brandId;      //如果是浏览商品详情页，则赋值为该商品的品牌id
            vo.scene_id = recom.sceneId;    //解析当前url的querystring，取到key为xclk_ind的值，以下划线split，取第一段，取不到赋值为’’
            vo.ref_page_id = recom.pageId;  //解析当前url的querystring，取到key为xclk_ind的值，以下划线split，取第二段
            vo.algorithm_ind = recom.algorithmInd;//解析当前url的querystring，取到key为xclk_ind的值，以下划线split，取第三段
            xclk.invoke(vo);
        };
    }catch(e){}
}

function xclkFB(t) {
    try{
        var items = $goodsInfo.data('goodInfo');
        var fb = new xclk.FB();
        fb.user_id = $.cookie('xiu.login.userId')!= null ? $.cookie('xiu.login.userId') :'';    //用户登录id
        fb.fb_type = t;          //加入收藏夹为1 加入购物车为2 分享为3 到货通知为4
        
        var fbd0 = new xclk.FbDetail();
        fbd0.item_id =items.goodsId;      //商品id
        fbd0.category_id =$goodsInfo.data('GoodsTypeId');    //三级分类id
        fbd0.brand_id = $('#brandId').val();     //商品品牌id
        fbd0.x_price = items.xPrice;    //商品走秀价

        fb.fb_detail = [fbd0];
        
        xclk.invoke(fb);
        
    }catch(e){}
}

//代替百分点
function putRecomFn(){
    var items = $goodsInfo.data('goodInfo');
    var recom = {
        userId: $.cookie('xiu.login.userId')!= null ? $.cookie('xiu.login.userId') : null,
        objId:items.goodsId,
        xPrice:items.xPrice,
        categoryId:$goodsInfo.data('GoodsTypeId'),//最后一级分类id
        brandId:$('#brandId').val(),//品牌id//要改模板增加
        sceneId:'',
        pageId:'',
        algorithmInd:''
    };

    var parm = getParaValue('xclk_ind',location.href);    
    if(parm!=''){
        recom.sceneId = parm.split('_')[0];
        recom.pageId =  (typeof(parm.split('_')[1])=='undefined') ?'': parm.split('_')[1];
        recom.algorithmInd= (typeof(parm.split('_')[2])=='undefined') ?'': parm.split('_')[2];
    }
    //推荐引擎
    try{
       // xclk.onLoad = function(){
			var recVav = new xres.RecommendVAV("recomCallback");
				recVav.productId = recom.objId;
				recVav.catId = recom.categoryId;
				recVav.num = 10;
				xres.invoke(recVav);
    //   };
    }catch(e){}
}
function recomCallback(data) {
		var listP = eval(data);
		var box = document.getElementById("bfd_Box");
			box.innerHTML='';
		var divTitle = document.createElement("div");
			divTitle.className = "right_2_t";
		var text = document.createTextNode('你可能对以下商品感兴趣');
		divTitle.appendChild(text);
		box.appendChild(divTitle);
		var div = document.createElement("div");
			div.className = "right_2_con";
		box.appendChild(div);
		for ( var i = 0; i <listP.length; i++) {
			recomTemplate(listP[i],div);
		}
	}
function recomTemplate(data_one, div){
		var rec_id = "1001";
		var div_sn = document.createElement("div");
		div_sn.className="right_2_pro";
		html='';
		html += '<a class="list_img_a" href="'+data_one.productUrl+"?xres_id="+rec_id+'"><img title="'+data_one.name+'" src="'+data_one.pictureUrl+'" /></a>';
		html += '<p class="txt"><a href="'+data_one.productUrl+"?xres_id="+rec_id+'" title="'+data_one.name+'">'+data_one.name+'</a></p>';
		html += '<p class="jc">'+data_one.price+'</p>';
		div_sn.innerHTML = html;
		div.appendChild(div_sn);
	}
	
//百分点接口----------------------
function bfdStart(){
	try{
	    var client = new brs.Client("Czouxiu"), // 电商账号。
		p = new brs.PackedRequest(),
		items = $goodsInfo.data('goodInfo'),//商品信息
		picUrl = skuInfo[0].pic_url + 'g1_100_100.jpg' + '?' + skuInfo[0].img_version,
		item_id_url = item_id = items.goodsId,// "575",
		item_name =  $('#goodsName').val(),
		//item_link = location.host+'/product/'+item_id+'.shtml',
		item_link = location.href;
		image_link = picUrl;
		item_price = items.SPrice || items.XPrice;;
	  	
		var goodsInfoTypes = $goodsInfo.data('type'); 
		item_brand = $('#brandName').val();
		var item_category = new Array();
		item_category.push('all');
		item_category.push(item_brand);
		if(!goodsInfoTypes) {}else{
			for(i=0;i<goodsInfoTypes.length;i++){
				var typename = goodsInfoTypes[i];
				item_category.push(typename);
			}
		}
		
		item_description=item_name;
		
		user_id = $.cookie('xiu.login.userId')!= null ? $.cookie('xiu.login.userId') :''; //用户ID
		session_id = $.cookie('sessionID');
		//while(item_id_url.length!=7) item_id_url = '0'+item_id_url;
		//item_link = location.host+'/product/'+item_id_url+'.shtml';
		if (typeof(user_id) == 'undefined' || user_id == '' ) user_id = session_id;
		
		
		// 先判断当前商品是否已经下架，如果单品页实在无法判断下架与否，请联系我们
		if ($goodsInfo.data('onSale')) {
			p.r = new brs.RemoveItem(item_id);
		} else {
			var url = self.location.href;	
			if (isFromRecommend(url)) { // 该商品页面是通过点击推荐栏中商品打开		
				var req_id = getReqId(url);
				// 上传用户的点击推荐行为 
				p.cr = new brs.ClickRecItem(user_id, item_id, session_id, req_id);
			} else { // 该商品页面是通过自然浏览行为打开
				// 上传商品信息
				p.a = new brs.AddItem(item_id, item_name, item_link);
				p.a.image_link = image_link;
				p.a.price = item_price;
				p.a.category = item_category;
				p.a.description=item_description;
				
				// 上传用户的浏览行为
				p.v = new brs.VisitItem(user_id, item_id, session_id);
			}
		}
		// 向百分点请求推荐，比如请求 “看过还看过”、“买过还买过”
		p.recVAV = new brs.RecByViewAlsoView(new Array(item_id), user_id, 10);	
		//p.recBH = new brs.RecByBrowsingHistory(user_id, session_id, 10);	
		client.invoke(p, "bfdCallback");

	}catch(e){
		//XJS.dprint('百分点接口有误');
	}
}
																																																																																																																																																																																						function bfd(){
																																																																																																																																																																																							
	var counter = 0;
	var intervalId = setInterval(function() {
		counter++;
		if (typeof(window.bfd_onload) == "function") {
			window.bfd_onload = bfdStart;
			window.bfd_onload();
			clearInterval(intervalId)
		}
		if (counter >= 50) {
			try {
				clearInterval(intervalId)
			} catch(e) {}
		}
	},
	100);
}
if(window.addEventListener) {
	window.addEventListener("load", getHistory, false);
} else if(window.attachEvent) {
	window.attachEvent("onload", getHistory);
}
/*function bfdload(){

	if(!document.getElementById('bfd_Box')) return false;
	try{
		
		XJS.execScript("http://www.baifendian.com/api/js/bfd-jsapi-3.0.min.js", bfd);
	}catch(e){
			//XJS.dprint('百分点接口有误');
	}
}*/

function getReqId(url)
{
	var str = "req_id=";
	idx = url.indexOf(str);
	var reqid = url.slice(idx + str.length); 
	return reqid;
}

function isFromRecommend(url)
{
	if (url.indexOf("req_id=") == -1){
		return false;
	} else {
		return true;
	}
}

// 回调函数：用于处理推荐请求的返回结果。即,将推荐结果展示在推荐栏中，您可以根据需要修改
function bfdCallback(json) {
	//XJS.dprint('百分点回调数据->>' + jsonToString(json).substring(0, 64));
	var result_BH = json.recVAV;
		var code_BH = result_BH[0];
		if (code_BH == 0) {
			var req_id_BH = result_BH[1];
			var item_info_BH = result_BH[2];
			if (Object.prototype.toString.apply(item_info_BH) === '[object Array]') {
				var obj_bh = document.getElementById("bfd_Box");
				onGetGoodsInfo(item_info_BH, obj_bh,req_id_BH);
			}
		}
	//推荐区域点击
	var itemid = "000000" + $('#goodsId').val();
	itemid = itemid.substring(itemid.length - 7);
	$('#bfd_Box').find('div > a').each(function (index, element) {
    $(element).bind('click', function(){
    	var clkid = this.href.substring(this.href.lastIndexOf('/') + 1, this.href.lastIndexOf('.'));
			XIU.Window.ctraceClick('dtype=recommend|itemid=' + itemid + '|clkid=' + clkid,'xdpv');
		});
  });
  $('#bfd_Box').find('p > a').each(function (index, element) {
    $(element).bind('click', function(){
    	var clkid = this.href.substring(this.href.lastIndexOf('/') + 1, this.href.lastIndexOf('.'));
			XIU.Window.ctraceClick('dtype=recommend|itemid=' + itemid + '|clkid=' + clkid,'xdpv');
		});
  });
}

function onGetGoodsInfo(data_all, obj,rec_id){
	var html = '<div class="right_2_t">你可能对以下商品感兴趣</div>';
	var div = document.createElement("div");
	div.className = "right_2_con";
	obj.innerHTML=html;
	obj.appendChild(div);
	var show_num=(data_all.length>10)?10:data_all.length;
	for(i=0;i<show_num;i++){
		if(i<7)	template(data_all[i],div,rec_id);
		}
	var bfd_logo=document.createElement("a");
	bfd_logo.href="http://www.baifendian.com";
	bfd_logo.target="_blank";
	bfd_logo.className="bfd_img_logo";
	bfd_logo.rel="nofollow";
	obj.appendChild(bfd_logo);

}
function template(data_one, div,rec_id){
	var div_sn = document.createElement("div");
	div_sn.className="right_2_pro";
	html='';
	html += '<a class="list_img_a" href="'+data_one[2]+"?req_id="+rec_id+'"><img src="'+data_one[3]+'" onerror="this.src=\''+$("#errImg100").val()+'\'" /></a>';
	html += '<p class="txt"><a href="'+data_one[2]+"?req_id="+rec_id+'">'+data_one[1]+'</a></p>';
	html += '<p class="jc">'+data_one[4]+'</p>';
	div_sn.innerHTML = html;
	div.appendChild(div_sn);
}
/**
*取url中kill=的参数值
*/
function getQueryString(name)
{
    // 如果链接没有参数，或者链接中不存在我们要获取的参数，直接返回空
    if(location.href.indexOf("?")==-1)
    {
        return '';
    }
 
    // 获取链接中参数部分
    var queryString = location.href.substring(location.href.indexOf("?")+1);
 
    // 分离参数对 ?key=value&key2=value2
    var parameters = queryString.split("&");
 
    var pos, paraName, paraValue;
    for(var i=0; i<parameters.length; i++)
    {
        // 获取等号位置
        pos = parameters[i].indexOf('=');
        if(pos == -1) { continue; }
 
        // 获取name 和 value
        paraName = parameters[i].substring(0, pos);
        paraValue = parameters[i].substring(pos + 1);
 
        // 如果查询的name等于当前name，就返回当前值，同时，将链接中的+号还原成空格
        if(paraName == name)
        {
            return unescape(paraValue.replace(/\+/g, " "));
        }
    }
    return '';
};
//点击流
function clickStream() {
	var itemid = "000000" + goodsData['goodsId'];
	itemid = itemid.substring(itemid.length - 7);
	//面包屑点击
	$('#positionBox').find('a').each(function (index, element) {
    $(element).bind('click', function(){
    	var i = index + 1;
			XIU.Window.ctraceClick('dtype=bread|itemid=' + itemid + '|bread_lev=' + i,'xdpv');
		});
  });
	//加入收藏点击
	$('#addToFavoBtn').bind('click', function(){
		XIU.Window.ctraceClick('dtype=collect|itemid=' + itemid,'xdpv');
	});
	//分享点击
	var channel = ['wb','renren','sina','kaixin','souhu','mls','email'];
	$('#shareBox').find('a').each(function (index, element) {
    $(element).bind('click', function(){
			XIU.Window.ctraceClick('dtype=share|itemid=' + itemid + '|chunnel=' + channel[index],'xdpv');
		});
  });
  //品牌区点击
  $('#goodsInfoBox').delegate('div.p_title a', 'click', function(){
			XIU.Window.ctraceClick('dtype=brandarea|itemid=' + itemid,'xdpv');
  });
  //售后服务点击
  $('#container_ul li').eq(1).bind('click', function(){
		XIU.Window.ctraceClick('dtype=aftersales|itemid=' + itemid,'xdpv');
	});
	//常见问题点击
  $('#container_ul li').eq(2).bind('click', function(){
		XIU.Window.ctraceClick('dtype=faq|itemid=' + itemid,'xdpv');
	});
	//品牌故事点击
  $('#container_ul li').eq(3).bind('click', function(){
		XIU.Window.ctraceClick('dtype=brandstory|itemid=' + itemid,'xdpv');
	});
}

function lazyloadBug(option) {
	var settings = {
		defObj : null,
		defHeight : 0
	};
	settings = $.extend(settings, option || {});
	var defObj = (typeof settings.defObj == "object")? settings.defObj.find("img"): $(settings.defObj).find("img");
	var pageTop = function() {
		var d = document, y = (navigator.userAgent.toLowerCase().match(/iPad/i) == "ipad")? window.pageYOffset: Math.max(d.documentElement.scrollTop, d.body.scrollTop);
		return d.documentElement.clientHeight + y - settings.defHeight;
	};
	var imgLoad = function() {
		defObj.each(function() {
			if ($(this).offset().top <= pageTop()) {
				var src3 = $(this).attr("src3");
				if (src3) {
					$(this).attr("src", src3).removeAttr("src3");
				}
			}
		});
	};
	imgLoad();
	$(window).bind("scroll", function() {
		imgLoad();
	});
}


$(document).ready(function(){
	lazyloadBug({defObj : "#index"});
	//图片缩放
	$('#container0 .conlist img').load(function(){
												
		var image=new Image();
			image.src=this.src;
		
		if(image.width>0 && image.height>0){
	
			if(image.width>700){  
				this.width=700;
				this.height=(image.height*700)/image.width;
			}else{
				this.width=image.width;  
				this.height=image.height;
			}
				this.alt=image.width+"×"+image.height;
				
	   }
	})
	.error(function(){
	$(this).hide();				
	});
	
	window.setTimeout(function(){clickStream()},1000);//点击流
})