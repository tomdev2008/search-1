/*
 * Written by Leon
 * 2013-05-22 10:36
 * */

(function($){
	if(typeof LEON == "undefined")LEON={};
	LEON.LinkSrcTrack={};
	var _anchor = window.location.hash;
	if(!_anchor || _anchor.indexOf("#s_id=") != 0)
		return;
	LEON.LinkSrcTrack.anchor = _anchor
	$(function(){
		$("a").live("click",function(){
			var _this = $(this);
			var _href = _this.attr("href") || "";
			var _hrefMath = $.trim(_href).toLowerCase();
			if(_hrefMath.indexOf("http://")==0
					|| _hrefMath.indexOf("https://")==0){
				var _index;
				if((_index = _hrefMath.indexOf("#")) > 0){
					_href = _href.substring(0,_index) + LEON.LinkSrcTrack.anchor;
				}else{
					_href += LEON.LinkSrcTrack.anchor;
				}
				_this.attr("href",_href);
			}
		});
	});
})(jQuery);


