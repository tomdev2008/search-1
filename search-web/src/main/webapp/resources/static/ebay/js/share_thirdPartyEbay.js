shareContent_pre = "我刚在eBay秀发现个好东东:";//${goodsName},走秀价${xiuPrice}
shareContent_tail = ",挺喜欢的,大家快来看看。eBay秀是eBay和走秀网合作、中国领先的高端时尚在线购物网站。";
shareTitle =  encodeURIComponent("我刚在eBay秀发现个好东东,挺喜欢的,大家快来看看");

/*+++lyx++++++++++++++
+ 分享到腾讯微博
+ 参数说明：
+ goodsUrl: 商品详情url
+ picUrl: 图片url
+ goodsName: 商品名
+ marketPrice: 市场价
+ xiuPrice：走秀价(可能是优惠价，促销价)
++++++++++++++++++*/
function share_tx_wb(goodsUrl, picUrl, goodsName, marketPrice, xiuPrice){

  var content = shareContent_pre + goodsName + "走秀价" + xiuPrice + shareContent_tail +" @XIU-Ketang";	
  var _t = encodeURI(content);
  var _url =  encodeURI(goodsUrl);;
  var _appkey = encodeURI("appkey");//你从腾讯获得的appkey
  var _pic = encodeURI(picUrl);//（列如：var _pic='图片url1|图片url2|图片url3....）
  var _site = encodeURI(goodsUrl);//你的网站地址
  var _u = 'http://v.t.qq.com/share/share.php?title='+_t+'&url='+_url+'&appkey='+_appkey+'&site='+_site+'&pic='+_pic;
 
  window.open( _u,'转播到腾讯微博', 'width=700, height=680, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, location=yes, resizable=no, status=no' );
}

/*+++lyx++++++++++++++
+ 分享到新浪微博
+ 参数说明：
+ goodsUrl: 商品详情url
+ picUrl: 图片url
+ goodsName: 商品名
+ marketPrice: 市场价
+ xiuPrice：走秀价(可能是优惠价，促销价)
++++++++++++++++++*/
function share_vivi(goodsUrl, picUrl, goodsName, marketPrice, xiuPrice){//sina
	var tj_title = encodeURI(goodsName);
	var tj_url = encodeURI(goodsUrl);
	var tj_content = shareContent_pre + goodsName + "走秀价" + xiuPrice + shareContent_tail+" @走秀网";
	
	function openSina(s,d,e,r,l,p,t,z,c){
		var f='http://v.t.sina.com.cn/share/share.php?appkey=1311731384';
		var u=z||d.location;
		var p=['&url=',e(u),'&title=',e(t||d.title),'&source=',e(r),'&sourceUrl=',e(l),'&content=',c||'gb2312','&pic=',e(p||'')].join('');
		
		function a(){
			if(!window.open([f,p].join(''),'mb',['toolbar=0,status=0,resizable=1,width=440,height=430,left=',(s.width-440)/2,',top=',(s.height-430)/2].join('')))
				u.href=[f,p].join('');
		};
		
		if(/Firefox/.test(navigator.userAgent))
			setTimeout(a,0);
		else
			a();
	}
	openSina(screen,document,encodeURIComponent,'','','',tj_content,tj_url,'utf-8');
}

/*+++lyx++++++++++++++
+ 分享到搜狐微博
+ 参数说明：
+ goodsUrl: 商品详情url
+ picUrl: 图片url
+ goodsName: 商品名
+ marketPrice: 市场价
+ xiuPrice：走秀价(可能是优惠价，促销价)
++++++++++++++++++*/
function shareSouHu(goodsUrl, picUrl, goodsName, marketPrice, xiuPrice){
   var title =  shareContent_pre + goodsName + "走秀价" + xiuPrice + shareContent_tail;
   var rLink = encodeURI(goodsUrl);
   var pic = picUrl;
   window.open('http://t.sohu.com/third/post.jsp?url='+encodeURIComponent(rLink)+'&title='+encodeURIComponent(title)+'&content=utf-8&pic='+encodeURI(pic),'_blank','scrollbars=no,width=600,height=450,left=75,top=20,status=no,resizable=yes')
}


/*+++++++++++++++++
+ 分享到人人网
+ 参数说明：
+ goodsUrl: 商品详情url
+ picUrl: 图片url
+ goodsName: 商品名
+ marketPrice: 市场价
+ xiuPrice：走秀价(可能是优惠价，促销价)
++++++++++++++++++*/
function share_renren(goodsUrl, picUrl, goodsName, marketPrice, xiuPrice){//renren
	var tj_title = shareTitle;
	var tj_url = encodeURI(goodsUrl);
	var tj_content = shareContent_pre + goodsName + "走秀价" + xiuPrice + shareContent_tail;
	var img = encodeURI(picUrl);
	
    var url = 'http://share.renren.com/share/buttonshare/post/1004?' + "title=" + tj_title +"&content="+ tj_content + "&pic=" + img + "&url=" + tj_url + "&sid=452406";
	window.open(url,'_blank','scrollbars=no,width=600,height=450,left=75,top=20,status=no,resizable=yes'); 
	
}

/*+++++++++++++++++
+ 分享到人人网
+ 参数说明：
+ goodsUrl: 商品详情url
+ picUrl: 图片url
+ goodsName: 商品名
+ marketPrice: 市场价
+ xiuPrice：走秀价(可能是优惠价，促销价)
++++++++++++++++++*/
function shareKaiXin(goodsUrl, picUrl, goodsName, marketPrice, xiuPrice){
	if(typeof(goodsUrl)=='object')goodsUrl = goodsUrl.href;
	var kx_title = shareTitle,
	summary = shareContent_pre + goodsName + "走秀价" + xiuPrice + shareContent_tail ;	
	picUrl = encodeURI(picUrl);
	
    window.open('http://www.kaixin001.com/repaste/bshare.php?rtitle='+kx_title+'&rpic='+picUrl+'&rurl='+encodeURIComponent(goodsUrl)+'&rcontent='+encodeURIComponent(summary),'_blank','scrollbars=no,width=600,height=450,left=75,top=20,status=no,resizable=yes' )   
}
 


function shareMLS(goodsUrl, picUrl, goodsName, marketPrice, xiuPrice){
	if(typeof(goodsUrl)=='object')goodsUrl = goodsUrl.href;
	if(goodsUrl.indexOf("?") == -1){
		goodsUrl += '?from=12562970&utm_content=12562970&utm_campaign=bd&utm_source=meilishuo&utm_medium=guanggao1';
	}else{
		goodsUrl += '&from=12562970&utm_content=12562970&utm_campaign=bd&utm_source=meilishuo&utm_medium=guanggao1';
	}
	
	var goodsUrlTmp = encodeURIComponent(goodsUrl);
	
	var mls_title = shareTitle;
	summary = shareContent_pre + goodsName+ "走秀价" + xiuPrice + shareContent_tail;	
	picUrl = encodeURIComponent(picUrl);
    window.open('http://www.meilishuo.com/share/share?url='+goodsUrlTmp+'&image='+picUrl+'&content='+encodeURIComponent(summary),'_blank','scrollbars=no,width=600,height=450,left=75,top=20,status=no,resizable=yes') ;  
}
