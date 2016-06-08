/**
 * 底部翻页的点击流方法
 * 由于page被封装好了ftl方法，所以通过此方式进行点击流事件绑定
 */
$('#J_bottomPageBox .Pagenum a').click(function(){
	var _text = $(this).text();
	var _curPage = parseInt('${page.pageNo}');
	var _maxPat = parseInt('${page.pageCount}');
	if('上一页' == _text){
		_text = (_curPage-1)>0 ? (_curPage-1) : 1
	}else if('下一页' == _text){
		_text = (_curPage+1)>_maxPat ? _maxPat : (_curPage+1)
	}else{
		_text = parseInt(_text);
	}
	if(!_text)return;
	var _stat="atype=flip|stype=kw|sattr=${params.kw?url}|page_no=";
	XIU.Window.ctraceClick(_stat+_text,"search");
});