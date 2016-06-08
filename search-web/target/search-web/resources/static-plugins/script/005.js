/**
 * 搜索主页代码
 */
$('#J_searchInput').focus(function(){
    	$('#J_welcomeText').hide();
    }).blur(function(){
    	if($(this).val().length==0)
    	$('#J_welcomeText').show();
    });
    if(typeof LEON == 'undefined')LEON = {};
LEON.lastinput="";
LEON.searchSubmit = function(){
	var ci = $.trim($('#J_searchInput').val());
	if(!ci || ""==ci)return false;
	if(LEON.lastinput !== ci){
		$('#J_catalogIdInput').val('').attr('disabled','disabled');
	}else{
		$('#J_catalogIdInput').removeAttr('disabled');
	}
	return true;
}
$('#J_searchInput').XAutoSuggest({
	url : "${searchUrl}ajax/autocomplete.htm?jsoncallback=?",
	width : 485,
	params:{"mkt":"xiu"},
	leftOff : -6,
	result : function(data){
			if(!!data.oclassId){
				LEON.lastinput = data.display;
				$('#J_catalogIdInput').val(data.oclassId).removeAttr('disabled')
			};
			$('#J_sourcesInput').val("s_"+(data._index || 0));
			$('#J_searchForm').submit();
		}
});