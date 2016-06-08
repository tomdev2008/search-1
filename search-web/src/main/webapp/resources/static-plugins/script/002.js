/**
 * 搜索无结果页面的百分点代码
 */
//左侧推荐代码
onGetGoodsInfo = function(){
	return false;
}
//底部官方推荐代码
onGetGoodsInfo_bh = function(itemInfoList, obj, rec_id){
	if(!obj)return;
	var len = itemInfoList.length;
	if(len == 0)return;
	var title = '官方推荐';
	var i = 0,maxLen = len > LEON.bfdData.setting.itemHistoryNum ? LEON.bfdData.setting.itemHistoryNum : len;
	var _hdDoc = $('<div class="hd"><p class="tit">'+title+'</p></div>');
	var _bdDoc = $('<div/>').addClass('bd').addClass('clearfix');
	var _bdUlDoc = $('<ul/>').appendTo(_bdDoc);
	var _liDoc,_obj,_href;
	do{
		_obj = itemInfoList[i];
		if(_obj.length<=3)continue;
		_liDoc = $('<li/>');
//		if(i==1)
//			_liDoc.addClass('first');
//		if(i==maxLen-1)
//			_liDoc.addClass('last');
		_href = _obj[2];
		if(_href.indexOf('?')<0){
			_href += '?';
		}else{
			_href += '&';
		}
		_href += 'req_id='+rec_id;
		_liDoc.append('<p class="picbox"><a href="'+_href+'"><img src="'+_obj[3]+'" /></a></p>');
		_liDoc.append('<p class="tit"><a href="'+_href+'">'+_obj[1]+'</a></p>');
		_liDoc.append('<p class="price xs-ico">'+_obj[4]+'</p>');
		_bdUlDoc.append(_liDoc);
	}while(++i<maxLen);
	_bdDoc.append('<a href="http://www.baifendian.com" class="bfd-click-link " target="_blank"></a>')
	$(obj).append(_hdDoc).append(_bdDoc).show();
}