/*+++++++++++++++++
+ FileName :ebay.login.js
+ XIU.com 页头公共js 包括登录、购物袋功能  
+ Anson.chen@xiu.com
+ v0.7
+ 2012-9-18
+ 
++++++++++++++++++*/
//判断用户是否登录

(function($){
	$._xiu_login = function(){}
	$._xiu_login.error ='<div style="padding-top:5px;padding-left: 8px;padding-bottom: 8px; color:#FFFFFF"><span>购物袋暂时没有商品!</span></div>';
	$._xiu_login.logoutHref = 'http://ebay.xiu.com';
	
	$.extend(
			 $._xiu_login,
			 {
		
		isLogin:function(){
			if($.cookie('xiu.login.userName')!=null && $.cookie('xiu.login.tokenId')!=null ) {
				return true;
			}else{
				return false;
			}	
		},
		loginInit:function(){
		    var login = $('#logindiv');
		    login.html('<li><a href="'+url['regHTML']+'"  rel="nofollow" title="注册" class="toolitem">&nbsp;[注册]</a></li><li><a href="'+url['loginHTML']+'"  rel="nofollow" title="登录" class="toolitem">[登录]</a></li><li><p class="toolitem">欢迎来eBay秀&nbsp;&nbsp;</p></li>');
			
		},
		logined:function(){
		    var login = $('#logindiv');
			if( $.cookie('xiu.login.userName') == null || $.cookie('xiu.login.tokenId') ==null){
				return $._xiu_login.loginInit();
			}
		    var loginHtml = '<li><a class="toolitem rel="nofollow" href="javascript:$._xiu_login.logout();">[退出]</a></li><li><p class="toolitem">您好，' +  $.cookie('xiu.login.userName') + '，欢迎来eBay秀&nbsp;&nbsp;</p></li>';
		    login.html(loginHtml);
		},
		loginUnionCheck:function(){
			$.ajax({
				type:"POST",
				url : url['checkloginurl'],
				data:{"isTest":"1"},
				async:false,
				dataType:"JSON",
				complete:function(){
						//XJS.dprint('登录验测：URL:'+url['checkloginurl']);
				}
			})
		},
		loginCMS:function(){
			//模拟session
			if($.cookie('sessionID')== null ){
				 $.cookie('sessionID',url['nowDate']+''+url['random'],{expires: 30, path:'/', domain:'.xiu.com'});
			}
			$._xiu_login.loginInit();
			var bagHtml = $._xiu_login.error;
			$("#baglist").html(bagHtml);
			$(".shopNum").html("0");
	        //判断是否已登录
	        $.getJSON(url['checkloginControl'],function(data) {  
	        	if(data != null && data[0] !=null && data[0].isAlreadyLogin){
					$._xiu_login.logined();
				}else{
					//$._xiu_login.loginInit();
					$._xiu_login.loginUnionXiuTuan();
				}
				$._xiu_login.showCardNum();
				
				var myxiu = $('#my_xiu');
			    var myxiuHtml='';
			    $.each(url['myXiu'].list,function(n,j){
					myxiuHtml = myxiuHtml+ '<a rel="nofollow" href="'+j.url+'" >'+j.name+'</a>';
				}) 
				myxiu.html(myxiuHtml);
				var xiuurl = $('#xiuurl');
				xiuurl.attr("href",url['myXiu'].url);
    		}); 
			
		},
		logout:function(){
		    //var login = $('#logindiv');
			//login.html('<img src="'+url['cmsurl']+'/static/img/floader.gif" alt="" />');
			//setTimeout(function(){location.href=url['logoutHTML'];},600);
			$.getJSON(url['logoutHTML'],function(data) {
				if(data != null){
					if(data.isLogout){
						//$._xiu_login.loginInit();
						//$._xiu_login.showCardNum();
					    location.href=$._xiu_login.logoutHref;
					}
				}
			});
		},
		showCard:function(){
			$.getJSON(url['shoppingCart'],function(data) {  
				$._xiu_login.dataProcess(data); 
    		});   
		},
		showCardNum:function(){
			$.getJSON(url['shoppingCartNum'],function(data) {  
				if(data != null){
					$(".shopNum").html(data.totalQuantity);
				}
    		}); 
		},
        loginUnionXiuTuan:function(){
        	//var x_cookies ={"tockenId":"","userId":"","channelId":"12","nickName":null};
        	$.getScript("http://www.xiutuan.com/redirect.php",function(){
        		if(typeof x_cookies != 'undefined' && typeof x_cookies.tockenId != 'undefined' ){
					var tockenId = x_cookies.tockenId;
					var notValid=/(^\s)|(\s$)/; 
					while(notValid.test(tockenId)){ 
						tockenId=tockenId.replace(notValid,"");
					}
					var nickName = x_cookies.nickName;
					if(tockenId != '' && nickName != null && nickName !=''){
						var param = '&rtid='+x_cookies.tockenId+'&userId='+x_cookies.userId+'&channelId='+x_cookies.channelId+'&nickName='+x_cookies.nickName;
						$.getJSON(url['checkloginControl']+param);
						var login = $('#logindiv');
						var loginHtml = '您好，<b>' +  x_cookies.nickName + '</b> [<a class="logout" rel="nofollow" href="javascript:$._xiu_login.logout();">退出</a>]';
						login.html(loginHtml);  
					}
        		}
        	});
        	 
        },
		removeCart:function(e,id){
			$.ajax({
            	type: "POST",
            	url: url['cartDelete']+'&skuId='+ id+'&jsoncallback=?',
				dataType:"json",
				timeout: 5000,
				cache:false,
            	success: function(data) {
					$._xiu_login.dataProcess(data);
				},
	            error: function(msg) {
	                alert("error");
					//XJS.dprint('失败：删除购物袋URL:'+url['cartDelete']+'&catentryId=' + id);
	            }
        	});
		},
		dataProcess:function(data){
				if(data[0].cart=='' || data[0].cart== "null" || data[0].cart.length<1){
				    var bagHtml = $._xiu_login.error;
					$("#baglist").html(bagHtml);
					$(".shopNum").html("0");
				}else{
					var bagHtml = '<ul>';
					$.each(data[0].cart,function(i,j){
						
						var id = j.catentryId;
						var goodSn = j.goodsSn;
						var sellType = j.lineitemtype;
						id = id+"";
						while(id.length!=7) id = '0'+id;
						if(i<5){
						bagHtml += '<li>';							
						bagHtml += '<a class="bag-item1 left" href="'+url['cartJson'] + '&goodsId=' + id + '&goodsSn='+ goodSn +'&sellType='+sellType+'" target="_blank"><img width="50" height="50" src="'+j.imageUrl+'" onerror="this.src=\'/static/img/default/default.50_50.jpg\'"></a>';
						bagHtml += '<div class="bag-item2 left"><a  rel="nofollow" style="height:32px;overflow:hidden;word-break:break-all;" href="'+url['cartJson'] + '&goodsId=' + id + '&goodsSn='+ goodSn +'&sellType='+sellType+'" target="_blank" title="'+j.catentryName+' '+j.attr+'" >'+j.catentryName+' '+j.attr+'</a><span>'+j.discountPrice+' X '+j.quantity+'</span></div>';
						bagHtml += '<a class="bag-item3 left" href="#" rel="nofollow" onclick="$._xiu_login.removeCart(this,\''+j.skuId+'\');return false;">删除</a>';
                        bagHtml += '</li>';
						}
					});
					
					bagHtml += '</ul><div class="clearfloat"></div>';
					bagHtml += '<div id="total">购物袋中共有<span class="red" class="shopNum"> '+data[0].totalQuantity+' </span>件商品</br>金额总计：<span class="red">&yen;'+data[0].totalAmount+'</span></div>';
                    bagHtml += '<a rel="nofollow" href="'+url['cartHTML']+'"  id="baglistmore">查看购物袋</a>';  
					$("#baglist").html(bagHtml);
					$(".shopNum").html(data[0].totalQuantity);
				} 
		}
	});
})(jQuery);


//购物袋	
	$("#bag").hover(function () {
		if (this.pointer) {	clearTimeout(this.pointer);}
		this.pointer = setTimeout(function(){
			$("#bag").children("a").addClass("sel");
			$._xiu_login.showCard();
			$("#baglist").show();
			
		},200);
		
	}, function () {
		if (this.pointer) {	clearTimeout(this.pointer);}
		$("#bag").children("a").removeClass("sel");
		$("#baglist").hide();
		
	});

//搜索
		//处理搜索条text默认值
	var $search = $("#search");
	$search
	.focus(function() {
		if($(this).val() == "请输入商品名称或品牌名称") {
			$(this).val("");
		}
	})
	.blur(function() {
		if($(this).val() == "") {
			$(this).val("请输入商品名称或品牌名称");
		}
	});
	
	if(typeof LEON == 'undefined')LEON = {};
	if(!!LEON.searchTerm){
		$search.val(LEON.searchTerm);
	}else{
		$search.val('请输入商品名称或品牌名称');
	}
	//if(!$('#search').val())$('#searchTip').show();
	LEON.lastinput="";
	LEON.searchSubmit = function(){
		var ci = $.trim($search.val());
		if(!ci || ""==ci || ci==='请输入商品名称或品牌名称')return false;
		if(LEON.lastinput !== ci){
			$('#J_st_oclass_id').val('').attr('disabled','disabled');
		}else{
			$('#J_st_oclass_id').removeAttr('disabled');
		}
		return true;
		
		}
	$search.XAutoSuggest({
		url : 'http://search.xiu.com/ajax/autocomplete.htm?jsoncallback=?',
		params : {"mkt":"ebay"},
		width : 296,
		result : function(data){
			if(!!data.oclassId){LEON.lastinput = data.display;$('#J_st_oclass_id').val(data.oclassId).removeAttr('disabled')};$('#searchform').submit();
			}
	});
	
	$(document).ready(function(){
	//登录
		$._xiu_login.loginCMS();
		$._xiu_login.showCard();
		$('#navbagNum').hover(function(){
							 $._xiu_login.showCard();
							 },function(){
								 
								 })
	
	/*处理随屏滚动菜单条*/
	$(window).scroll( function() { 
		if ($(document).scrollTop() > 103) {$("#navbox").addClass("fixed-top");$("#navbarright").show()}
		if ($(document).scrollTop() < 103) {$("#navbox").removeClass("fixed-top");$("#navbarright").hide()}
		if ($(document).scrollTop() > 500) {$(".gotop").show()}
		if ($(document).scrollTop() < 500) {$(".gotop").hide()}
	}); 	   
		
	/*处理页面上所有二级下拉菜单  需设置菜单项li为class=hassubmenu,下拉菜单层为class=submenu*/
	$(".hassubmenu").hover(function(){
		$(this).children(".submenu").show();	
		$(this).children("a").addClass("sel");
	},function(){
		$(this).children(".submenu").hide();
		$(this).children("a").removeClass("sel");
	});
	
	var pagewidth = $("#header").width();
	$("#fashionlist").width(pagewidth+"px");  //根据page宽度动态设置时尚直搜的宽度(为了1200px & 950px 两种宽度的自适应)
	$("#fashionlist dl").width(pagewidth / 5 - 28 +"px");
	$("#allclass").width(pagewidth+"px");  //根据page宽度动态设置全部类目的宽度(为了1200px & 950px 两种宽度的自适应)
	$("#allclass ul li").width(pagewidth / 8 - 40 +"px");
	
	})