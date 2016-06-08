(function() {
	// 处理"更多/收起"按钮是否显示
	$('#J_facet_box').find('div[id^=J_itembox_]').each(function() {
		var _box = $(this);
		var _optBtn = _box.next('.more-opt');
		var _h = _box.children('ul').height();
		_optBtn.data('boxheight',_h);
		if (_h > 30) {
			_optBtn.show();
		}
	})
})();