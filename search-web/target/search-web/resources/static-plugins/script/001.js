/**
 * 搜索页面的百分点代码
 */
onGetGoodsInfo = function(itemInfoList, obj, rec_id, flag){
	if(!obj)return;
	var len = itemInfoList.length,maxLen = LEON.bfdData.setting.itemVisitNum;
	if(len == 0)return;
	var title = flag == 1 ? '购买排行' : '浏览排行';
	var _atype = flag == 1 ? 'buytop' : 'viewtop';
	var _stype = !!LEON.bfdData.setting.stype ?  LEON.bfdData.setting.stype : 'kw';
	var _sattr = !!LEON.bfdData.setting.sattr ? LEON.bfdData.setting.sattr : '';
	var _clkstat = 'atype='+_atype+'|stype='+_stype+'|sattr='+_sattr+'|itemid=';
	if(flag == 1){
		title = '购买排行';
		maxLen = LEON.bfdData.setting.itemSaleNum;
	}
	var i = 0,maxLen = len > maxLen ? maxLen : len;
	var _hdDoc = $('<div class="hd"><span class="tit">'+title+'</span><span class="tag">Top10</span></div>');
	var _bdDoc = $('<div/>').addClass('bd').addClass('clearfix');
	var _bdUlDoc = $('<ul/>').appendTo(_bdDoc);
	var _liDoc,_obj,_src,_href;
	do{
		_obj = itemInfoList[i];
		if(_obj.length<=3)continue;
		_liDoc = $('<li/>');
		if(i==maxLen-1)
			_liDoc.addClass('nodot');
		_src = _obj[3];
		if(_src.indexOf('_100_100')>0)
			_src = _src.replace('_100_100','_80_80');
		_href = _obj[2];
		if(_href.indexOf('?')<0){
			_href += '?';
		}else{
			_href += '&';
		}
		_href += 'req_id='+rec_id;
		_liDoc.append('<a clkstat="'+_clkstat+_obj[0]+'" class="img" target="_blank" href="'+_href+'"><img src="'+_src+'"/></a>');
		_liDoc.append('<a clkstat="'+_clkstat+_obj[0]+'" class="tit" target="_blank" href="'+_href+'">'+_obj[1]+'</a>');
		_liDoc.append('<p class="price">'+_obj[4]+'</p>');
		_bdUlDoc.append(_liDoc);
	}while(++i<maxLen);
	_bdDoc.append('<a href="http://www.baifendian.com" class="bfd-click-link " target="_blank"></a>')
	$(obj).append(_hdDoc).append(_bdDoc).show();
	// 调用点击流绑定方法
	_bdDoc.clkBindMethod();
}
//底部官方推荐代码
onGetGoodsInfo_bh = function(itemInfoList, obj, rec_id){
	if(!obj)return;
	var len = itemInfoList.length;
	if(len == 0)return;
	var title = '官方推荐';
	var _atype = 'xiuroc';
	var _stype = !!LEON.bfdData.setting.stype ?  LEON.bfdData.setting.stype : 'kw';
	var _sattr = !!LEON.bfdData.setting.sattr ? LEON.bfdData.setting.sattr : '';
	var _clkstat = 'atype='+_atype+'|stype='+_stype+'|sattr='+_sattr+'|itemid=';
	var i = 0,maxLen = len > LEON.bfdData.setting.itemHistoryNum ? LEON.bfdData.setting.itemHistoryNum : len;
	var _hdDoc = $('<div class="hd"><p class="tit">'+title+'</p></div>');
	var _bdDoc = $('<div/>').addClass('bd').addClass('clearfix');
	var _bdUlDoc = $('<ul/>').appendTo(_bdDoc);
	var _liDoc,_obj,_href;
	do{
		_obj = itemInfoList[i];
		if(_obj.length<=3)continue;
		_liDoc = $('<li/>');
		if(i==1)
			_liDoc.addClass('first');
		if(i==maxLen-1)
			_liDoc.addClass('last');
		_href = _obj[2];
		if(_href.indexOf('?')<0){
			_href += '?';
		}else{
			_href += '&';
		}
		_href += 'req_id='+rec_id;
		_liDoc.append('<p class="picbox"><a clkstat="'+_clkstat+_obj[0]+'" target="_blank" href="'+_href+'"><img src="'+_obj[3]+'" /></a></p>');
		_liDoc.append('<p class="tit"><a clkstat="'+_clkstat+_obj[0]+'" target="_blank" href="'+_href+'">'+_obj[1]+'</a></p>');
		_liDoc.append('<p class="price xs-ico">'+_obj[4]+'</p>');
		_bdUlDoc.append(_liDoc);
	}while(++i<maxLen);
	_bdDoc.append('<a href="http://www.baifendian.com" class="bfd-click-link " target="_blank"></a>')
	$(obj).append(_hdDoc).append(_bdDoc).show();
	// 调用点击流绑定方法
	_bdDoc.clkBindMethod();
}