// JavaScript Document
(function($){
$.extend($,{
	/**
	 * cookie相关操作
	 */
	setCookie : function(setting) {
		var opt = $.extend( {
			key : '',
			value : '',
			path : '/',
			domain : '',
			// day>0正常,day<0会话,day=0删除
			expireDay : -1,
			// today是否截至00:00:00,需与expireDay配合
			today : false,
			// https安全cookie
			secure : false
		}, setting);
		if (opt.key != '') {
			var ret = new Array();
			ret.push(opt.key);
			ret.push("=");
			ret.push(encodeURIComponent(opt.value));
			ret.push("; path=");
			ret.push(opt.path);
			if (opt.domain != '') {
				ret.push("; domain=");
				ret.push(opt.domain);
			}
			if (opt.expireDay > 0) {
				var date = new Date();
				if (opt.today) {
					date.setHours(0, 0, 0, 0);
				}
				date.setTime(date.getTime() + opt.expireDay * 24 * 60 * 60
						* 1000+1000);
				ret.push("; expires=");
				ret.push(date.toGMTString());
			} else if (opt.expireDay == 0) {
				var date = new Date();
				date.setTime(date.getTime() - 1);
				ret.push("; expires=");
				ret.push(date.toGMTString());
			}
			if (opt.secure) {
				ret.push("; secure");
			}
			document.cookie = ret.join("");
		}
	},
	getCookie : function(key) {
		var value = document.cookie.match('(?:^|;)\\s*' + key.replace(
				/([.*+?^${}()|[\]\/\\])/g, '\\$1') + '=([^;]*)');
		return value ? decodeURIComponent(value[1]) : false;
	},
	delCookie : function(key) {
		$.setCookie( {
			key : key,
			expireDay : 0
		});
	},
	/**
	 * json转化为字符串
	 */
	jsonToString : function(obj){
		switch(typeof(obj)) 
        {
            case 'object':
                var ret = [];
                if (obj instanceof Array) 
                {
                    for (var i = 0, len = obj.length; i < len; i++) 
                    {
                        ret.push($.jsonToString(obj[i]));
                    }
                    return '[' + ret.join(',') + ']';
                } 
                else if (obj instanceof RegExp) 
                {
                    return obj.toString();
                } 
                else 
                {
                    for (var a in obj) 
                    {
                        ret.push('"' + a + '":' + $.jsonToString(obj[a]));
                    }
                    return '{' + ret.join(',') + '}';
                }
            case 'function':
                return 'function() {}';
            case 'number':
                return obj.toString();
            case 'string':
                return "\"" + obj.replace(/(\\|\")/g, "\\$1").replace(/\n|\r|\t/g, function(a) {return ("\n"==a)?"\\n":("\r"==a)?"\\r":("\t"==a)?"\\t":"";}) + "\"";
            case 'boolean':
                return obj.toString();
            default:
                return obj.toString();
        }
	},
	/**
	 * 获得this对象，event指js原生
	 */
	getThis : function(event){
		if($.browser.msie){
			return event.srcElement
		}else{
			return event.target
		}
	},
	/**
	 * 获得屏幕可视高度
	 */
	offHeight : function(){
		if($.browser.msie)return document.documentElement.clientHeight;
		return window.innerHeight;
	},
	/**
	 * 滚动高度
	 */
	scrollTop : function(){
		if (typeof window.pageYOffset != 'undefined') {
			return window.pageYOffset;
		} else if (typeof document.compatMode != 'undefined'
				&& document.compatMode != 'BackCompat') {
			return document.documentElement.scrollTop;
		} else if (typeof document.body != 'undefined') {
			return document.body.scrollTop;
		}
		return 0;
	},
	/**
	 * 浏览器复制操作
	 */
	copyText : function(text,callback){
		if (window.clipboardData) {
			  window.clipboardData.setData("Text", text);
		} else if (window.netscape) {
			try{
				netscape.security.PrivilegeManager.enablePrivilege('UniversalXPConnect');
			}catch(e){
				alert("您的浏览器安全限制限制您进行复制操作，请手动复制代码。");
				return;
			}
			var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
			if (!clip)
				return;
			var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
			if (!trans)
				return;
			trans.addDataFlavor('text/unicode');
			var str = new Object();
			var len = new Object();
			var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
			var copytext = text;
			str.data = copytext;
			trans.setTransferData("text/unicode", str, copytext.length * 2);
			var clipid = Components.interfaces.nsIClipboard;
			if (!clip)
				return false;
			clip.setData(trans, null, clipid.kGlobalClipboard);
		}
		alert('复制成功！');
	},
	/**
	 * 阻止浏览器默认事件
	 */
	stopDefault : function( e ) { 
	    if ( e && e.preventDefault ) 
	        e.preventDefault(); 
	    else 
	        window.event.returnValue = false; 
	    return false; 
	},
	/**
	 * 阻止冒泡事件
	 */
	stopBubble : function(e){
		if ( e && e.stopPropagation ){
			 e.stopPropagation();
		} else {
        	window.event.cancelBubble = true;
        }
	}
});
	
	
/**
 * ********************************************************************************************************
 * 搜索主要的方法
 * 此部分方法与业务逻辑有关联，页面若有变动需要修改此部分内容
 * ********************************************************************************************************
 */
if(typeof LEON == 'undefined')LEON={}
$.extend(LEON,{
	currentBrandFacetLet : 'ALL',
	brandMaxH : 295,
	brandMinH : 64,
	brandH : {},
	brandStatus : 'min',
	ie6Constant : {},
	staticUrl:'http://www.xiustatic.com/',
	wwwUrl:'http://www.xiu.com/',
	searchUrl:'http://search.xiu.com/',
	listUrl:'http://list.xiu.com/',
	brandUrl:'http://brand.xiu.com/',
	init : function(setting){
		$.extend(LEON,setting);
	},
	/**
	 * ebay TAB交互
	 */
	mktItemCountInteractive : function(params,stype,sattr){
		LEON.ajaxItemCount(params,function(count){
			if(typeof count != 'number')return;
			var _obj = $('#J_ebayTab');
			_obj.find('.itemcount').html('('+count+')');
			var _a = _obj.children('a')
			if(count == 0){
				_obj.addClass('disable');
				_a.attr('href','javascript:void(0);');
			}else{
				var _clkstat = 'atype=ebaytabclk|stype='+stype+'|sattr='+sattr+'|resnum='+count;
				_a.click(function(){
					XIU.Window.ctraceClick(_clkstat,"search");
				})
			}
			_obj.show();
		})
	},
	/**
	 * ajax获得商品数量
	 */
	ajaxItemCount:function(params,callback){
		if(typeof callback != 'function' || !params)return;
		var opt = $.extend({
		},params);
		$.ajax({
			url : LEON.searchUrl + 'ajax/mkt-item-count.htm?jsoncallback=?',
			data:opt,
			dataType : 'jsonp',
			cache:false,
			success : function(data){
				var _count = 0;
				if(!!data && !!data.itemCount)
					_count = data.itemCount;
				callback(_count);
			}
		})
	},
	/**
	 * 一定加速度滚动到顶部
	 */
	scrollViewTop : function(a,time,lastY){
		a = a || 0.1;
		time = time || 16;
		var x,y;
		if (typeof window.pageYOffset != 'undefined') {
			x = window.pageXOffset || 0;
			y = window.pageYOffset || 0;
		} else if (typeof document.compatMode != 'undefined'
				&& document.compatMode != 'BackCompat') {
			x = document.documentElement.scrollLeft || 0;
			y = document.documentElement.scrollTop || 0;
		} else if (typeof document.body != 'undefined') {
			x = document.body.scrollLeft || 0;
			y = document.body.scrollTop || 0;
		}
		if(!!lastY && y > lastY)
			return;
		var speed = 1 + a;
		window.scrollTo(Math.floor(x / speed), Math.floor(y / speed));
		if(x >1 || y >1){
			var invokeFunction = "LEON.scrollViewTop("+a+","+time+","+y+")";
			window.setTimeout(invokeFunction, time);
		}else{
			window.scrollTo(0, 0);
		}	
	},
	/**
	 * 回到顶部按钮
	 */
	initToTopBtn : function(setting){
		//回顶部取消		William.zhang	20130621
		return;
		var opt = $.extend({
			bodyWidth : 950,
			rightOffset : 10,
			topScrollShow : 150,
			backImg : "static/searchstatic/image/xtop-icon.png",
			width : 30,
			height : 80
		},setting);
		var _top = $('body').children().get(0);
		var _btn = $('<s style="display:block;cursor:pointer;position:fixed;display:none;background:url('+LEON.staticUrl+opt.backImg+') no-repeat 0 0;width:'+opt.width+'px;height:'+opt.height+'px;overflow:hidden;line-height:0;font-size:0;zoom:1"></s>').appendTo('body');
		var _ie6 = $.browser.version == 6.0 && $.browser.msie;
		var _ie7 = $.browser.version == 7.0 && $.browser.msie;
		if(!_ie6)
			_btn.css({'bottom':'20%'});
			else
			_btn.css({'position':'absolute'});
		_btn.click(function(){
			if(_ie6 || _ie7)
				_top.scrollIntoView();
				else
				LEON.scrollViewTop(0.3);
		});
		var initTop = function(scroll){
			if(!scroll){
				var _w = $('body').width();
				_w = (_w + opt.bodyWidth)/2 + opt.rightOffset;
				_btn.css({"left":_w+"px"});
			}
			if(_ie6){
				var _t = $.offHeight()*0.75 + $.scrollTop();
				_btn.css({'top':_t+'px'});
			}
		}
		initTop(false);
		var _timeout;
		var _timeoutCode = "initTop(true)";
		$(window).scroll(function(){
			var _t = $.scrollTop();
			if(_t > opt.topScrollShow){
				_btn.show();
				if(_ie6){
					//initTop(true);
					window.clearTimeout(_timeout);
					_timeout = window.setTimeout(function(){initTop(true)},300);
				}
			}else{
				_btn.hide()
			}
		});
		$(window).resize(function(){
			initTop();
		});
	},
	/**
	 * 商品图片错误图展示
	 */
	goodsImageErrorMethod : function(defaultSrc){
		$('#J_itemListBox img').one('error',function(){
			$(this).attr('src',defaultSrc);
		});
	},
	/**
	 * 品牌分组ABCD切换
	 * 此部分延续了search2.0的代码
	 */
	switchBrandBox : function(let){
		if(LEON.currentBrandFacetLet == let)return;
		$('#J_brandFacet_'+LEON.currentBrandFacetLet).hide();
		$('#J_brandFacet_'+let).show();
		$('#J_flet_'+LEON.currentBrandFacetLet).removeClass('active');
		$('#J_flet_'+let).addClass('active');
		LEON.currentBrandFacetLet = let;
		//alert(LEON.ie6Constant['J_brandFacet_'+let]);
		//if($.browser.msie && $.browser.version=='6.0'){
		if('min'==LEON.brandStatus){
			$('#J_itemboxbrand').height(LEON.brandH['J_brandFacet_'+let] > LEON.brandMinH ? LEON.brandMinH : LEON.brandH['J_brandFacet_'+let]);
			//LEON.brandH['J_brandFacet_'+let] > LEON.brandMinH ? $('#J_brand_moreopt').show() : $('#J_brand_moreopt').show().hide();
		}else{
			$('#J_itemboxbrand').height(LEON.brandH['J_brandFacet_'+let] > LEON.brandMaxH ? LEON.brandMaxH : LEON.brandH['J_brandFacet_'+let]);
			//if($.browser.msie && $.browser.version == '7.0'){
			//	$('#J_itemboxbrand').css({"overflow-x":"hidden"});
			//}
			//LEON.brandH['J_brandFacet_'+let] > LEON.brandH ? $('#J_brand_moreopt').show() : $('#J_brand_moreopt').show().hide();
		}
		LEON.brandH['J_brandFacet_'+let] > LEON.brandMinH ? $('#J_brand_moreopt').show() : $('#J_brand_moreopt').show().hide();
		//}
		return;
	},
	/**
	 * 品牌开关
	 * 此部分延续了search2.0的代码
	 */
	onOffBrandBox : function(obj){ // 品牌的更多/收起功能，单独处理
		if($('#J_itemboxbrand:animated').length>0)return;
		var _curBId = LEON.currentBrandFacetLet;
		var _box = $('#J_itemboxbrand');
		if('min' == LEON.brandStatus){
			var _h = LEON.brandH['J_brandFacet_'+_curBId] > LEON.brandMaxH ? LEON.brandMaxH : LEON.brandH['J_brandFacet_'+_curBId];
			_box.animate({"height":_h+'px'},'normal',function(){
				_box.css({"overflow-y":"auto"})
			});
			LEON.brandStatus = 'max';
			$(obj).html('收起').removeClass('down');
		}else{
			var _h = LEON.brandH['J_brandFacet_'+_curBId] > LEON.brandMinH ? LEON.brandMinH : LEON.brandH['J_brandFacet_'+_curBId];
			_box.css({"overflow-y":"hidden","overflow":"hidden"}).animate({"height":_h+'px'},'normal');
			LEON.brandStatus = 'min';
			$(obj).html('更多').addClass('down');
		}
		
		$(obj).toggleClass('down');
	},
	/**
	 * 初始化品牌切换的数据
	 * 此部分延续了search2.0的代码
	 */
	initBrandDisplayData : function(){
		$('#J_itemboxbrand').children('ul').each(function(){
			var _obj = $(this);
			var _objId = _obj.attr('id');
			//记录各个模块的高度
			//LEON.ie6Constant[_objId] = _obj.height();
			LEON.brandH[_objId] = _obj.outerHeight();
			//LEON.ie6Constant[_objId] = _obj.outerHeight() > 295 ? 295 : _obj.outerHeight();
			_obj.css({display:'none',visibility:'visible'});
		});
		$('#J_brandFacet_ALL').show();
		$('#J_itemboxbrand').css({height:(LEON.brandH.J_brandFacet_ALL > LEON.brandMinH ? LEON.brandMinH : LEON.brandH.J_brandFacet_ALL)+'px'});
		LEON.brandH.J_brandFacet_ALL > LEON.brandMinH ? $('#J_brand_moreopt').show() : $('#J_brand_moreopt').hide()
	},
	/**
	 * 其他筛选区开关
	 * 此部分延续了search2.0的代码
	 */
	onOffFacetBox : function(obj, boxId){ // facet区域更多/收起功能
		if($('#J_itembox_'+boxId+':animated').length>0)return;
		var _box = $('#J_itembox_'+boxId);
		var _obj = $(obj);
		if(!_obj.data('boxheight')){
			var _height = _box.height();
			_obj.data('boxheight',_height);
		}
		if(!!_obj.data('open')){
			_box.animate({height:30},'normal');
			_obj.html('更多').data('open',false);
		}else{
			_box.animate({height:_obj.data('boxheight')},'normal');
			_obj.html('收起').data('open',true);
		}
		$(obj).toggleClass('down');
	},
	/**
	 * 定向跳转
	 */
	href : function(_url){
		if(!_url)return;
		location.href = _url;
	},
	/**
	 * 自定义价格过滤
	 */
	exePriceRangeFilter : function(stopsubmit){
		if(!!stopsubmit)return false;
		var _minPrice = parseFloat($('#J_finput_minprice').val());
		var _maxPrice = parseFloat($('#J_finput_maxprice').val());
		var _url = $('#J_pricerangefilter_form').attr('action');
		var _qParam = _url.indexOf('?')<=0 ? '?' : '&'
		if(isNaN(_minPrice) && isNaN(_maxPrice)){
		}else if(isNaN(_minPrice)){
			_maxPrice = _maxPrice < 0 ? 0 : _maxPrice;
			_url += _qParam +("e_price="+_maxPrice);
		}else if(isNaN(_maxPrice)){
			_minPrice = _minPrice < 0 ? 0 : _minPrice;
			_url += _qParam +("s_price="+_minPrice);
		}else{
			_maxPrice = _maxPrice < 0 ? 0 : _maxPrice;
			_minPrice = _minPrice < 0 ? 0 : _minPrice;
			if(_maxPrice >= _minPrice){
				_url += _qParam + ("s_price="+_minPrice+"&e_price="+_maxPrice);
			}else{
				$('#J_finput_minprice').val(_maxPrice);
				$('#J_finput_maxprice').val(_minPrice);
				_url += _qParam + ("s_price="+_maxPrice+"&e_price="+_minPrice)
			}
		}
		//由于有锚点问题，这一行注释！	William.zhang	20130527
		//_url += "#J_filterbox";
		location.href=_url;
	},
	/**
	 * 自定义价格过滤结束
	 */
	exeCancelPriceRangeFilter : function(setting){
		$('#J_finput_minprice,#J_finput_maxprice').val('');
	},
	/**
	 * 绑定自定义价格的交互
	 */
	bindPriceRange : function(){
		var _rangePriceBox = $('#J_pricerangefilter_box');
    	var _timeout;
	    $('#J_finput_minprice,#J_finput_maxprice').focus(function(){
	    	window.clearTimeout(_timeout);
	     	_rangePriceBox.addClass('pricerange-active');
	     }).blur(function(){
			window.clearTimeout(_timeout);
	     	_timeout = window.setTimeout(function(){
	     		_rangePriceBox.removeClass('pricerange-active');
	     	},200)
	     }).keyup(function(){
	    	var _value=$(this).val();
	    	if(/[^0-9\.]/g.test(_value))
	    		$(this).val($(this).val().replace(/[^0-9\.]/g,''));
	     })
	},
	/**
	 * 搜索页面的分类树交互
	 * 性能有点烂，后面再优化。。。
	 */
	catalogSearchIntercative : function(setting){
		var opt = $.extend({
			tireId : 'J_catalogTire'
		},setting);
		var _obj = $('#'+opt.tireId);
		if(!_obj && _obj.length == 0)
			return;
		var _dtFix = 'J_catalog_dt_';
		var _ddFix = 'J_catalog_dd_';
		var selectIdArr = new Array(4);
		_obj.find('dt').each(function(){
			var _id = $(this).attr('id');//J_catalog_dt_xxxx
			var _clevel = parseInt(_id.slice(13,14));
			var _cid = _id.slice(15,_id.length)
			var _ddObj = $('#'+_ddFix+_cid);
			if($(this).children('i').hasClass('minus'))
				selectIdArr[_clevel] = _cid;
			if(!!_ddObj && _ddObj.length>0){
				$(this).children('i.minus,i.plus').click(function(){
					if($(this).hasClass('minus')){
						$(this).removeClass('minus');
						for ( var i = _clevel; i < 4; i++) {
							$('#'+_ddFix+selectIdArr[i]).hide();
							$('#'+_dtFix+i+"_"+selectIdArr[i]).children('i.minus').removeClass('minus');
							selectIdArr[i] = null;
						}
					}else{
						if(selectIdArr[_clevel] == _cid)return;
						$(this).addClass('minus');
						for ( var i = _clevel; i < 4; i++) {
							$('#'+_ddFix+selectIdArr[i]).hide();
							$('#'+_dtFix+i+"_"+selectIdArr[i]).children('i.minus').removeClass('minus');
							selectIdArr[i] = null;
						}
						selectIdArr[_clevel] = _cid;
						$('#'+_ddFix+selectIdArr[_clevel]).show();
					}
				})
			}
		})
	},
	/**
	 * 列表页面的分类树代码
	 */
	catalogListIntercative : function(setting){
		var opt = $.extend({
			tireId : 'J_catalogTire'
		},setting);
		var _obj = $('#'+opt.tireId);
		
		_obj.find('dt').each(function(){
			var _this = $(this);
			var _id = _this.attr('id');
			var _cid = _id.split('_')[2];
			_this.data('cid',_cid);
			if(!_this.hasClass('down'))
				return;
			if(_this.hasClass('up'))
				LEON.currentCatId = _cid;
			_this.click(function(){
				var sid = $(this).data('cid');
				if(!!LEON.currentCatId){
					$('#J_catgroup_'+LEON.currentCatId).removeClass('up');
					$('#J_catgroupcon_'+LEON.currentCatId).hide();
					if(LEON.currentCatId == sid){
						LEON.currentCatId = 0;
						return;
					}
				}
				LEON.currentCatId = sid;
				$('#J_catgroup_'+LEON.currentCatId).addClass('up');
				$('#J_catgroupcon_'+LEON.currentCatId).show();
			})
		})
	},
	/**
	 * 品牌，列表页面的底部搜索框form翻页提交过滤方法
	 * 用于url的格式转换
	 */
	pageFormSubmitFilter : function(setting){
		var opt = $.extend({
			pageBoxId : 'J_bottomPageBox'
		},setting);
		var _box = $('#'+opt.pageBoxId);
		var _form = _box.find('form');
		var _button = _box.find('button').eq(0);
		_form.eq(0).bind('submit',function(){
			var _pInput = _form.find('input[name="p"]');
			if(!_pInput)return true;
			var _p = parseInt(_form.find('input[name="p"]').val()) || 1;
			_pInput.attr('disabled','disabled');
			var _regexMin = /^(.*)\/([0-9]+)\.html\/?(\?(.*))?$/gi;
			var _regex = /^(.*)\/([0-9]+-.*-.*-.*-.*-.*-.*-.*-.*-.*-.*-.*-.*-.*-).*\.html\/?(\?(.*))?$/gi;
			var _action = _form.attr('action');
			if(_regexMin.test(_action)){
				_action = _action.replace(_regexMin,'$1/$2--------------'+_p+'.html');
			}else if(_regex.test(_action)){
				_action = _action.replace(_regex,'$1/$2'+_p+'.html');
			}
			_form.attr('action',_action);
			return true;
		});
	}
});


/**
 * ********************************************************************************************************
 * 1.点击流的插码代码(clkBindMethod)
 * 2.推荐数据收集(dataCollectMethod) 
 * 		siteId = 1001; 	xiu:1001
 * 		channelId=1001; xiu:1001 ebay:1002
 * ********************************************************************************************************
 */
$.extend($.fn,{
	clkBindMethod : function(){
		$(this).find('a').each(function(){
			var _obj = $(this);
			var _clkstat = _obj.attr('clkstat');
			if(!!_clkstat)
				_obj.click(function(){
					XIU.Window.ctraceClick(_clkstat,"search");
				})
		})
	},
	dataCollectMethod:function(setting){
		var opt = $.extend({siteId:1001},setting);
		$(this).find('a[dataCollect]').each(function(){
				$(this).click(function(){
					var tempArray=$(this).attr('dataCollect').split("|");
					for (var i=0;i<tempArray.length ;i++ ){   
						var strs=tempArray[i].split("=");
						if(strs[0]=="type"){
							opt.bizType=strs[1];
						}else if(strs[0]=="id"){
							opt.bizId=strs[1];
						}
					} 
					LEON.xreDataCollect(opt);
				});
		});
	}
});
/**
 * ********************************************************************************************************
 * 百分点推荐的js代码
 * 此部分因为百分点缓存了页面的js代码，导致需保留此部分代码，但 onGetGoodsInfo，onGetGoodsInfo_bh 两个方法的实现代码可以重构
 * 另外页面的DIV - ID不能改变，仍然需要 banner_PersonalSalesList、banner_PersonalVisitList、banner_BrowsingHistory
 * ********************************************************************************************************
 */
/* 回调方法，此方法被缓存
cb_recommend = function (json) {
	var result_PSL = json.recPSL;
	var code_PSL = result_PSL[0];
	if (code_PSL == 0) {
		var req_id_PSL = result_PSL[1];
		var item_info_PSL= result_PSL[2];
		if (Object.prototype.toString.apply(item_info_PSL) === '[object Array]') {
			var obj_psl = document.getElementById("banner_PersonalSalesList");
			onGetGoodsInfo(item_info_PSL, obj_psl,req_id_PSL,1);
		}
	}
	var result_PVL = json.recPVL;
	var code_PVL = result_PVL[0];
	if (code_PVL == 0) {
		var req_id_PVL = result_PVL[1];
		var item_info_PVL= result_PVL[2];
		if (Object.prototype.toString.apply(item_info_PVL) === '[object Array]') {
			var obj_pVl = document.getElementById("banner_PersonalVisitList");
			onGetGoodsInfo(item_info_PVL, obj_pVl,req_id_PVL,2);
		}
	}
	var result_BH = json.recBH;
	var code_BH = result_BH[0];
	if (code_BH == 0) {
		var req_id_BH = result_BH[1];
		var item_info_BH= result_BH[2];
		if (Object.prototype.toString.apply(item_info_BH) === '[object Array]') {
			var obj_BH = document.getElementById("banner_BrowsingHistory");
			onGetGoodsInfo_bh(item_info_BH, obj_BH,req_id_BH);
		}
	}
}*/
//此行代码确保LEON对象不为空，可注释掉
//if(typeof LEON == 'undefined')LEON = {};
//此部分代码也是由于百分点被缓存，才这么做的
//xre:xiu recommend engine
//Ctest_zouxiu   Czouxiu
$.extend(LEON,{
	bfdData : function(setting){
		var opt = $.extend({
			clientName : 'Czouxiu',//public2//bfdtest300//
			hasResult:true,
			kw:"",
			itemSaleNum : 10,
			itemVisitNum : 10,
			itemHistoryNum : 5,
			saleBoxId : 'banner_PersonalSalesList',
			visitBoxId : 'banner_PersonalVisitList',
			historyBoxId : 'banner_BrowsingHistory',
			categoryList : ['all']
		},setting);
		/*if(typeof brs == 'undefined')return;*/
		LEON.bfdData.setting=opt;
		/*
		var session_id = $.getCookie('sessionID');
		if(!session_id)session_id=0000000000;
		var user_id = $.getCookie('xiu.login.userId');
		user_id = !!user_id ? user_id : session_id;
		var client = new brs.Client(opt.clientName);
		var p = new brs.PackedRequest();
		p.recPSL = new brs.RecByPersonalSalesList(opt.categoryList, session_id, opt.itemSaleNum);
		p.recPSL.days = 30;
		p.recPSL.ratio = 0.4;
		p.recPSL.user_id = user_id;
		p.recPVL = new brs.RecByPersonalVisitList(opt.categoryList, session_id, opt.itemVisitNum);
		p.recPVL.days = 30;
		p.recPVL.ratio = 0.4;
		p.recBH = new brs.RecByBrowsingHistory(user_id,session_id,5);
		client.invoke(p, "cb_recommend");
		*/
		_BFD = window["_BFD"]||{};
		_BFD.BFD_ITEM_INFO = {
			search_result : opt.hasResult,	//搜索页，有结果此值为true,搜索无结果此值为false
			type:opt.type,	//1.search 2.list 3.brand
			keyword:opt.kw,
			catId:opt.catId,	
			brandId : opt.bId,
			client : opt.clientName   //百分点服务账号Ctest_zouxiu，测试环境下请务必使用测试账号，正式环境务必更改为正式账号。 正式账号:Czouxiu
		};
		_BFD["bfd_init_ready"] = true;
	},
	xreData:function(setting){
		var opt = $.extend({
			clientName : 'Czouxiu',//public2//bfdtest300//
			kw:'',
			hasResult:true,
			itemSaleNum : 10,
			itemVisitNum : 10,
			itemHistoryNum : 10,
			saleBoxId : 'banner_PersonalSalesList',
			visitBoxId : 'banner_PersonalVisitList',
			historyBoxId : 'banner_BrowsingHistory',
			displayTop10:true,
			brandRemmendOn:false,
			salesXREId:1003,
			catBrowseXREId:1004,
			brandBrowseXREId:1005,
			xiuRecommendXREId:1006,
			channelId:1001
		},setting);
		LEON.xreData.setting=opt;
		/*
		var session_id = $.getCookie('sessionID');
		if(!session_id)session_id=0000000000;
		var user_id = $.getCookie('xiu.login.userId');
		user_id = !!user_id ? user_id : session_id;
		*/
		//加载官网告诉百分点
		_BFD = window["_BFD"]||{};
		_BFD.BFD_ITEM_INFO = {
				search_result : opt.hasResult,
				type:opt.type,	//1.search 2.list 3.brand
				keyword:opt.kw,
				client : opt.clientName   //百分点服务账号Ctest_zouxiu，测试环境下请务必使用测试账号，正式环境务必更改为正式账号。 正式账号:Czouxiu
		};
		_BFD["bfd_init_ready"] = false;
		/*	目前只保留官方推荐的数据，其他的请求暂时取消	William.zhang	20130617
		//猜你喜欢
		var recGYL = new xres.RecommendByGuessYouLike('LEON.salesCallback');
		//recGYL.userId = user_id;
		recGYL.brandId = opt.brandId;
		recGYL.catId = opt.catId;
		recGYL.num = opt.itemSaleNum;
		recGYL.siteId=opt.siteId;
		recGYL.channelId=opt.channelId;
		xres.invoke(recGYL);
		//类目浏览
		if(!opt.brandRemmendOn){
			var recUBL = new xres.RecommendByCatBrowsingList("LEON.catBrowseCallback");
			recUBL.catIdList = opt.catId;
			recUBL.num = opt.itemSaleNum;
			recUBL.siteId=opt.siteId;
			recUBL.channelId=opt.channelId;
			xres.invoke(recUBL);
		}else{
			//品牌浏览
			var recBL = new xres.RecommendByBrandBrowsingList("LEON.brandBrowseCallback");
			recBL.brandId = opt.brandId;
			recBL.catId =opt.catId;
			recBL.num = opt.itemSaleNum;
			recBL.siteId=opt.siteId;
			recBL.channelId=opt.channelId;
			xres.invoke(recBL);
		}
		*/
//		//官方推荐
		var recOL = new xres.RecommendByOfficialList("LEON.recommendCallback");
		//recOL.userId = user_id;
		recOL.productList = opt.itemId;
		recOL.catId = opt.catId;
		recOL.brandId = opt.brandId;
		recOL.num = opt.itemSaleNum;
		recOL.siteId=opt.siteId;
		recOL.channelId=opt.channelId;
		xres.invoke(recOL);
	},
	salesCallback:function(data){
		LEON.showLeftXRE(data,1,LEON.xreData.setting.salesXREId);
	},
	catBrowseCallback:function(data){
		LEON.showLeftXRE(data,2,LEON.xreData.setting.catBrowseXREId);
	}
	,brandBrowseCallback:function(data){
		LEON.showLeftXRE(data,3,LEON.xreData.setting.brandBrowseXREId);
	},
	recommendCallback:function(data){
		LEON.bottomXRE(data,LEON.xreData.setting.xiuRecommendXREId);
	},
	showLeftXRE:function(data,flag,xresId){
		var itemInfoList = eval(data);
		var maxLen=flag=='1'?LEON.xreData.setting.itemSaleNum:LEON.xreData.setting.itemVisitNum,len=itemInfoList.length;
		if(len == 0)return;
		var title = flag=='1' ? '猜你喜欢' : '浏览排行';
		var _atype = flag=='1' ? 'buytop' : 'viewtop';
		var _stype = !!LEON.xreData.setting.stype ?  LEON.xreData.setting.stype : 'kw';
		var _sattr = !!LEON.xreData.setting.sattr ? LEON.xreData.setting.sattr : '';
		var _nodeId=flag=='1'?LEON.xreData.setting.saleBoxId:LEON.xreData.setting.visitBoxId;
		
		var _clkstat = 'atype='+_atype+'|stype='+_stype+'|sattr='+_sattr+'|itemid=';
		
		maxLen = len > maxLen ? maxLen : len;
		var i = 0;
		var _hdDoc = $('<div class="hd"><span class="tit">'+title+'</span>'+(LEON.xreData.setting.displayTop10?'<span class="tag">Top10</span></div>':''));
		var _bdDoc = $('<div/>').addClass('bd').addClass('clearfix');
		var _bdUlDoc = $('<ul/>').appendTo(_bdDoc);
		var _liDoc,_obj,_src;
		do{
			_obj=itemInfoList[i];
			_liDoc = $('<li/>');
			if(i==maxLen-1)
				_liDoc.addClass('nodot');
			_src = _obj.pictureUrl;
			if(_src.indexOf('_100_100')>0)
				_src = _src.replace('_100_100','_80_80');
			if(parseFloat(_obj.price)-parseInt(_obj.price)>0){
				_obj.price = parseFloat(_obj.price).toFixed(2);
			}else{
				_obj.price = parseInt(_obj.price);
			}
			
			_liDoc.append('<a clkstat="'+_clkstat+_obj.id+'" class="img" target="_blank" href="'+_obj.productUrl+'?xres_id='+xresId+'" title="'+_obj.name+'"><img src="'+_src+'"/></a>');
			_liDoc.append('<a clkstat="'+_clkstat+_obj.id+'" class="tit" target="_blank" href="'+_obj.productUrl+'?xres_id='+xresId+'" title="'+_obj.name+'">'+_obj.name+'</a>');
			_liDoc.append('<p class="price">'+_obj.price+'</p>');
			_bdUlDoc.append(_liDoc);
		}while(++i<maxLen);
		//_bdDoc.append('<a href="http://www.baifendian.com" class="bfd-click-link " target="_blank"></a>')
		$('#'+_nodeId).append(_hdDoc).append(_bdDoc).show();
		_bdUlDoc.clkBindMethod();
	},
	bottomXRE:function(data,xresId){
		var itemInfoList = eval(data);
		var len=itemInfoList.length;
		if(len == 0)return;
		
		var title = '猜你喜欢';
		var _atype = 'xiuroc';
		var _stype = !!LEON.xreData.setting.stype ?  LEON.xreData.setting.stype : 'kw';
		var _sattr = !!LEON.xreData.setting.sattr ? LEON.xreData.setting.sattr : '';
		var _clkstat = 'atype='+_atype+'|stype='+_stype+'|sattr='+_sattr+'|itemid=';
		var i = 0,maxLen = len > LEON.xreData.setting.itemHistoryNum ? LEON.xreData.setting.itemHistoryNum : len;
		var _hdDoc = $('<div class="hd"><p class="tit">'+title+'<span>Recommend</span></p></div>');
		var _bottomboxDoc = $('<div/>').addClass('xs-salexiurecom-bottombox').addClass('xs-xiurecombox');
		var _bdDoc = $('<div/>').addClass('bd').addClass('clearfix').appendTo(_bottomboxDoc);
		_bdDoc.css({"overflow":"hidden"});
		var _bdUlDoc = $('<ul/>').appendTo(_bdDoc);
		_bdUlDoc.css({"width":"9999px"});
		var _liDoc,_obj,_src;
		do{
			_obj = itemInfoList[i];
			if(_obj.length<=3)continue;
			_liDoc = $('<li/>');
			if(i==1)
				_liDoc.addClass('first');
			if(i==maxLen-1)
				_liDoc.addClass('last');
			if(parseFloat(_obj.price)-parseInt(_obj.price)>0){
				_obj.price = parseFloat(_obj.price).toFixed(2);
			}else{
				_obj.price = parseInt(_obj.price);
			}
			_src = _obj.pictureUrl;
			// 设置图片尺寸
			_src = _src.substring(0,_src.lastIndexOf("/")) + "/g1_180_240.jpg";
			if(_src.indexOf('images.xiustatic.com') > 0){
				_src = _src.replace('images.xiustatic.com','image.zoshow.com');
			}
			_liDoc.append('<p class="picbox"><a clkstat="'+_clkstat+_obj.id+'" target="_blank" href="'+_obj.productUrl+'?xres_id='+xresId+'" title="'+_obj.name+'"><img src="'+_src+'" /></a></p>');
			_liDoc.append('<p class="tit"><a clkstat="'+_clkstat+_obj.id+'" target="_blank" href="'+_obj.productUrl+'?xres_id='+xresId+'" title="'+_obj.name+'">'+_obj.name+'</a></p>');
			_liDoc.append('<p class="price xs-ico">'+_obj.price+'</p>');
			_bdUlDoc.append(_liDoc);
		}while(++i<maxLen);
		//_bdDoc.append('<a href="http://www.baifendian.com" class="bfd-click-link " target="_blank"></a>')
	//	_bottomboxDoc.append('<a class="arrow back" href="#"></a><a class="arrow forward" href="#"></a>');
		$('#'+LEON.xreData.setting.historyBoxId).append(_hdDoc).append(_bottomboxDoc).show();
		_bdUlDoc.clkBindMethod();
		
		//底部图片滚动效果
	    var autoscrolling = true;
	    $('.xs-salexiurecom-bottombox').infiniteCarousel().mouseover(function () {
	        autoscrolling = false;
	    }).mouseout(function () {
	        autoscrolling = true;
	    });
	    setInterval(function () {
	        if (autoscrolling) {
	            $('.xs-salexiurecom-bottombox').trigger('next');
	        }
	    }, 5000);
	},xreDataCollect:function(setting){
		var opt = $.extend({siteId:1001},setting);
		
		if(setting.bizType&&isNaN(setting.bizType)){
			if(setting.bizType=="list"){
				opt.bizType="2";
			}else if(setting.bizType=="brand"){
				opt.bizType="3";
			}
		}
		
		var user_id = $.getCookie('xiu.login.userId');
		user_id = !!user_id ? user_id : "";

		if(!!setting.kw){
			var sk = new xclk.SK();
			sk.site_id = opt.siteId;
			sk.channel_id = opt.channelId;
			sk.user_id = user_id;//用户登录id
			sk.key_words = opt.kw;//搜索关键词
			sk.result_ind = opt.result>0?1:0;//搜索结果标识 1 有结果 0 无结果
			xclk.invoke(sk);
		}else{
			var vo = new xclk.VO();
			vo.site_id = opt.siteId;
			vo.channel_id = opt.channelId;//channelId xiu:1001 ebay:1002
			vo.user_id = user_id;//用户登录id
			vo.obj_id = opt.bizId;//用户浏览的商品id、品类id、品牌id
			vo.obj_type = opt.bizType;//商品详情页为1 品类页为2 品牌页为3
			vo.x_price = "";
			vo.category_id = "";
			vo.brand_id = "";
			vo.scene_id = ""; 
			vo.ref_page_id = "";
			vo.algorithm_ind = "";
			xclk.invoke(vo);
		}
	},bfdCreateRecommend:function(data,id,req_id,bfd_id){
		var itemInfoList = eval(data);
		var len=itemInfoList.length;
		if(len == 0)return;
		
		var title = '适合您的商品Top10';
		var _atype = 'xiuroc';
		/*
		var _stype = !!LEON.xreData.setting.stype ? LEON.xreData.setting.stype : 'kw';
		var _sattr = !!LEON.xreData.setting.sattr ? LEON.xreData.setting.sattr : '';
		var _clkstat = 'atype='+_atype+'|stype='+_stype+'|sattr='+_sattr+'|itemid=';
		*/
		var i = 0,maxLen = len > 10 ? 10 : len;
		
		var _hdDoc = $('<div class="hd"><p class="tit">'+title+'<span>Recommend</span></p></div>');
		var _bottomboxDoc = $('<div/>').addClass('xs-salexiurecom-bottombox').addClass('xs-xiurecombox');
		var _bdDoc = $('<div/>').addClass('bd').addClass('clearfix').appendTo(_bottomboxDoc);
		_bdDoc.css({"overflow":"hidden"});
		var _bdUlDoc = $('<ul/>').appendTo(_bdDoc);
		_bdUlDoc.css({"width":"9999px"});
		var _liDoc,_obj,_src;
		do{
			_obj = itemInfoList[i];
			if(_obj.length<=3)continue;
			_liDoc = $('<li/>');
			if(i==1)
				_liDoc.addClass('first');
			if(i==maxLen-1)
				_liDoc.addClass('last');
			if(parseFloat(_obj.xprice)-parseInt(_obj.xprice)>0){
				_obj.xprice = parseFloat(_obj.xprice).toFixed(2);
			}else{
				_obj.xprice = parseInt(_obj.xprice);
			}
			_src = _obj.img;
			// 设置图片尺寸
			_src = _src.substring(0,_src.lastIndexOf("/")) + "/g1_180_240.jpg";
			if(_src.indexOf('images.xiustatic.com') > 0){
				_src = _src.replace('images.xiustatic.com','image.zoshow.com');
			}
			_liDoc.append('<p class="picbox"><a target="_blank" href="'+_obj.url+'?bfd_id='+bfd_id+'&req_id='+req_id+'" title="'+_obj.name+'"><img src="'+_src+'" /></a></p>');
			_liDoc.append('<p class="tit"><a target="_blank" href="'+_obj.url+'?bfd_id='+bfd_id+'&req_id='+req_id+'" title="'+_obj.name+'">'+_obj.name+'</a></p>');
			_liDoc.append('<p class="price xs-ico">'+_obj.xprice+'</p>');
			_bdUlDoc.append(_liDoc);
		}while(++i<maxLen);
	//	_bottomboxDoc.append('<a class="arrow back" href="#"></a><a class="arrow forward" href="#"></a>');
		_bottomboxDoc.append('<a href="http://www.baifendian.com" class="bfd-click-link " style="z-index:10" target="_blank"></a>');
		$('#'+id).append(_hdDoc).append(_bottomboxDoc).show();
		_bdUlDoc.clkBindMethod();
		
		//底部图片滚动效果
	    var autoscrolling = true;
	    $('.xs-salexiurecom-bottombox').infiniteCarousel().mouseover(function () {
	        autoscrolling = false;
	    }).mouseout(function () {
	        autoscrolling = true;
	    });
	    setInterval(function () {
	        if (autoscrolling) {
	            $('.xs-salexiurecom-bottombox').trigger('next');
	        }
	    }, 5000);
	}
});
})(jQuery);