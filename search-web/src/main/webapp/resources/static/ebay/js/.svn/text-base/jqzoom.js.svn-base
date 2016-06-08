//-------------------------------------------------------------------------------------
//Name:jqzoom 1.0
//Author:By josh.y 
//Date:2012-03-13
(function($){
	$.xiu_jqzoom=function(options){
		var defaults ={
			animate_time: 600,	        //动画时间
			small_pic: "smallPic",	        //列表图片的ID
			img_pic_div: "imgPicDiv",	  //商品图片DIV的ID
			img_pic: "imgPic",            //商品图片的ID
			img_pic_mask: "imgPicMask",   //商品图遮罩层的ID
			img_show: "showimg",	//商品图片选取范围的ID
			big_pic_div: "bigPicDiv",	  //放大图DIV的ID
			big_pic: "bigPic"		//放大图的ID
		};
		options = $.extend(defaults,options);
		$.xiu_jqzoom.init(options);
	};
	$.extend($.xiu_jqzoom,{
		//初始化
		init: function (options){
			$.xiu_jqzoom.picListInit(options);
			$.xiu_jqzoom.picBigInit(options);
		},
		picListInit: function (options){
			var bigPic = document.getElementById(options.big_pic);
			var imgPicMask = document.getElementById(options.img_pic_mask);
			var imgPic = document.getElementById(options.img_pic);
			$('#smallPic').delegate("a","mouseover", function(e) {
				e.preventDefault();   
				e.stopPropagation();  
				$(this).parent('dd').siblings().removeClass('dc').end().addClass('dc');
				var currSrc = $(this).find("img").attr("src");
				var imgSrc = currSrc.replace("50_50", "400_400");
				var bigSrc = currSrc.replace("50_50", "600_600");
				bigPic.src = bigSrc;

				imgPicMask.src = imgSrc;
				$('#'+options.img_pic_mask).fadeIn(options.animate_time,function(){
					imgPic.src = imgSrc;
					$('#'+options.img_pic_mask).fadeOut(options.animate_time);
				});
				

			});
		},
		picBigInit: function (options){
			var show_half = 0;
			var maxWidth =  0;
			var maxHeight = 0;
			var showimg = document.getElementById(options.img_show);
			var bigPicDiv = document.getElementById(options.big_pic_div);
			var bigPic = document.getElementById(options.big_pic);
			$('#'+options.img_pic_div).bind("mouseover",function(){ 
				showimg.style.display="block";
				bigPicDiv.style.display="block";
				show_half = showimg.offsetHeight/2;
				maxWidth = this.clientWidth-showimg.clientWidth-2;
				maxHeight = this.clientHeight-showimg.clientHeight-2;
				
			});

			$("#"+options.img_pic_div).bind("mousemove",function(e){
				e = e || window.event; //兼容多个浏览器的event参数模式
				var mousePost = $.xiu_jqzoom.mousePosition(e);
				//var num=bigimg.clientWidth/showimg.clientWidth;

				var offsetLeft = $.xiu_jqzoom.pageX(this);
				var offsetTop = $.xiu_jqzoom.pageY(this);

				var top = mousePost.y - offsetTop - show_half;
				var left = mousePost.x - offsetLeft - show_half;

				top = top<0?0:top>maxHeight?maxHeight:top;
				left = left<0?0:left>maxWidth?maxWidth:left;

				showimg.style.top = top + "px";
				showimg.style.left = left + "px";

				var newTop = top>0?top+2:0;
				var newLeft = left>0?left+2:0;

				bigPic.style.top = "-"+newTop+"px";
				bigPic.style.left = "-"+newLeft+"px";
			});

			$("#"+options.img_pic_div).bind("mouseout",function(e){
				showimg.style.display="none";
				bigPicDiv.style.display="none";
			});
		},
		mousePosition: function(ev) {
			return {x:ev.clientX + (document.body.scrollLeft || document.documentElement.scrollLeft),y:ev.clientY + (document.body.scrollTop || document.documentElement.scrollTop)};
		},
		pageX: function(elem){  
			return elem.offsetParent ?elem.offsetLeft + $.xiu_jqzoom.pageX( elem.offsetParent ) : elem.offsetLeft;  
		},
		pageY: function(elem){  	  
			return elem.offsetParent?elem.offsetTop + $.xiu_jqzoom.pageY(elem.offsetParent):elem.offsetTop;  
		}
	});
})(jQuery);


$.xiu_jqzoom();