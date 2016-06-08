function showPLInfo(event){
    var flag = event.data.flag;
    var index = event.data.index;
    if(flag=="b"){
        $('#plinfo'+index).css('display', 'block');
    }else{
        $('#plinfo'+index).css('display', 'none');
    }    	
 }
(function(){   
var itemList = $("#J_itemListBox").find('li[class=item]'); 
var item = '';
var itemid = '';
var itemDivMap = {};
var itemidList = [];
for (var i=0; i<itemList.length; i++) { 
    item = $(itemList[i]);  
    itemid = item.data('itemid');
    itemDivMap[itemid] = item;
    itemidList.push(itemid);  
}
// 使用jsonp调用评论系统，获取itemidList评论信息            
try {
    // plresult = eval({'27886':"["22","0.5","1223"]",'1362390':["11","5.5","3465"],'27749':["121","3.5","3465"]});
    // 评论接口URL
    var url = "http://comm.xiu.com/commentlist/comment_totalnum_score?prodIds=" + itemidList.join(",") + "&jsoncallback=?";  
    jQuery.getJSON(url, function(data){  
        // 回写评论信息   
        var plresult = eval(data);
        if (typeof(plresult)=='undefined' || null == plresult ||plresult=="") {
            return;
        }   
        var innerhtml = '<p class="ajia"><a target="_blank" href="http://comm.xiu.com/commentlist/brand/{brandid}.html">{commentcount}条品牌评论</a></p><span class="icon_star"><b style="width:{star}%;"></b></span>';
        var commentcount = '';
        var star = '';
        var brandid = '';
        for(var i in plresult) {
            if (plresult[i].length != 3) {
                continue;
            } 
            if (typeof(itemDivMap[i]) == 'undefined') {
                continue;
            }
            commentcount = plresult[i][0];
            star = plresult[i][1];
            brandid = plresult[i][2]; 
            if (commentcount<=0) {
            	star=5;
            }
            if (star<0 || star > 5) {
            	star=5;
            }
            if (brandid == '') {
                continue;
            }
            item = $(itemDivMap[i]);
            star = star / 5.0 * 100;
            item.find('div[name=plinfo]').html(innerhtml.replace('{star}', star).replace('{commentcount}', commentcount).replace('{brandid}', brandid));
            itemid = item.attr("data-itemid"); 
            item.bind('mouseover', {flag:'b', index:itemid}, showPLInfo);
            item.bind('mouseout', {flag:'n', index:itemid}, showPLInfo);
        }
    }); 
} catch(e) {} 
})();