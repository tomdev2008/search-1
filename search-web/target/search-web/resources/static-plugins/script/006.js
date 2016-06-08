/**
 * 搜索下方的搜索框代码
 */
if(typeof LEON == "undefined")LEON = {};
LEON.lastinputBottom=$("#J_searchInputBottom").val();
//LEON.searchSubmitBottom = function(){
//	var ci = $.trim($('#J_searchInputBottom').val());
//	if(!ci || ""==ci)return false;
//	return true;
//}
$('#J_searchInputBottom').XAutoSuggest({
	url : "${searchUrl}ajax/autocomplete.htm?jsoncallback=?",
	params : {"type":"min","mkt":"xiu"},
	width : 480,
	leftOff : 0,
	result : function(data){
		if(!!data.oclassId){LEON.lastinputBottom = data.display;$('#J_catalogIdInput').val(data.oclassId).removeAttr('disabled')};$('#J_searchForm').submit();
		}
});