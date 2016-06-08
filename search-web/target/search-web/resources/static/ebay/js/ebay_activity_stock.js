function readStock(index) {
var actids=$("input[name=actids]");  

if(actids == null || actids.length <= 0){
	return;
}
var id = actids.eq(index).val();

var SNs = '';
var goodsSN1s=$("input[name=goodsSNs]"); 
if(goodsSN1s != null && goodsSN1s.length > 0){
	SNs = goodsSN1s.eq(0).val();
}

var stockUrl =url['wcsurl']+"SecondInventoryCmd?" + url['parms'] + '&activityId=' + id + '&goodsSns=' + SNs + '&timeStamp=' +new Date() +'&jsoncallback=?';
	$.ajax({
        type: "POST",
        url: stockUrl,
        dataType: "json",
        success: function(data) {
            data = jsonData(data);
            list = jsonGet(data.stock);
            $.each(list,
            function(i, j) {
				var name = id +'_'+ j.goodsId;
				var obj = $('#stock_'+name);
				var inventory = j.inventory;
				if(inventory < 0 ){
                	inventory = 0;
                }
                //
                 if(j.onSale == 0){
                	inventory = 0;
                }
				obj.text(inventory);
				if(inventory < 5){
					obj.parent().show();
				}else{
					obj.parent().hide();
				}
                if(inventory == 0){
					var flag = $('#flag_stock_'+name);
					if(flag != null ) {
					 //
					 $('#flag_stock_'+name).append("<b></b>");
					}
				}
            })

        },
        error: function(msg) {
            
        },
		complete: function(){
			index ++;
			if(index < actids.length){
				readStock(index);
			}
		}

    });

}

$(document).ready(function(){
	readStock(0);
});