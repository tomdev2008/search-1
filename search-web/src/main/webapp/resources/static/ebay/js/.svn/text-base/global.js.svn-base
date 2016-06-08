$(function(){ 
	
	//ebay path
	var ebayPath = '/static/ebay/';	
	
	/*处理日期值*/
	var tempdate;
	$("#lmd-head .date2 a").hover(function() {
		tempdate = $(this).html();
		$(this).html("预告");
	},function(){
		$(this).html(tempdate);	
	});
	

	
	//ebay love tooltip效果
	var conleft,contop,conbottom;
	var $con = $("#popupMember");
	$("#loves div > div[info]").hover(function(){
			conbottom = 0;
			//鼠标mouseonover后图片做略微放大效果
			var $img =  $(this).children().children();
			if( $(this).hasClass("doubleheight") ) {
				$img.css({width:"125px",height:"250px",margin:0});
				$("#popupMember .info img").css({width:"125px",height:"250px",margin:0});
			} else {
				$img.css({width:"125px",height:"125px",margin:0});
				$("#popupMember .info img").css({width:"125px",height:"125px",margin:0});
			}
			//初始化tooltip层内容
			var data = $(this).attr("info").split("|");
			var url = $(this).find('a:first').attr('href');
			$("#popupMember .info a").attr('href',url);
			$("#popupMember .info img").attr("src", $(this).children().children().attr("src"));
			
			$("#goodsname a").html(data[0]).attr('href',url);
			$("#popupMember .datainfo .i1").html(data[1]);
			$("#popupMember .datainfo .i3").html(data[2]);
			$("#popupMember .datainfo .i4").html(data[3]);
			
			
			var newsleft = $("#loves").offset().left;
			var thisobjleft = 	$(this).offset().left - newsleft; 
			if ( $(this).hasClass("cleft1") ) {  //tooltip处在上面或下面
					conleft = thisobjleft; 
			}
			if ( $(this).hasClass("cleft4") ) { //tooltip处在左边，处理中间行最右边格子
					conleft = thisobjleft - 165;
			}
			if ( $(this).hasClass("cleft3") ) {	//tooltip处在右边，处理中间行格子
					conleft = thisobjleft + 125;
			}
			if ( $(this).hasClass("cleft2") ) { //tooltip处在上面个或下面，处理上下两行最右边的格子
					conleft = thisobjleft - 40;
			}
			if ( $(this).hasClass("ctop1") ) { //处理第一行格子
					contop = 83 + 125;
			}
			if ( $(this).hasClass("ctop2") ) {	//处理最后一行格子
					conbottom = 125 - 8;
			}
			if ( $(this).hasClass("ctop3") ) {//处理中间行格子
					contop = 83 + 125 ;
			}
			if ( $(this).hasClass("ctop4") ) { //处理两倍高的格子
					contop = 83 ;
			}
			
			
			if ( $(this).hasClass("cleft1") && $(this).hasClass("ctop1") ) {
					$("#popupMember img.arrow").css({left:"57px",top:"-8px",bottom:"auto"});
					$("#popupMember img.arrow").attr("src",ebayPath + "images/arrow-top.gif");
			}
			if ( $(this).hasClass("cleft2") && $(this).hasClass("ctop1") ) {
					$("#popupMember img.arrow").css({left:"99px",top:"-8px",bottom:"auto"});
					$("#popupMember img.arrow").attr("src",ebayPath + "images/arrow-top.gif");
			}
			if ( $(this).hasClass("cleft1") && $(this).hasClass("ctop2") ) {
					$("#popupMember img.arrow").css({left:"57px",bottom:"-8px",top:"auto"});
					$("#popupMember img.arrow").attr("src",ebayPath + "images/arrow-bottom.gif");
			}
			if ( $(this).hasClass("cleft2") && $(this).hasClass("ctop2") ) {
					$("#popupMember img.arrow").css({left:"99px",bottom:"-8px",top:"auto"});
					$("#popupMember img.arrow").attr("src",ebayPath + "images/arrow-bottom.gif");
			}
			if ( $(this).hasClass("cleft3") && $(this).hasClass("ctop3") ) {
					$("#popupMember img.arrow").css({left:"-8px",top:"62px",bottom:"auto"});
					$("#popupMember img.arrow").attr("src",ebayPath + "images/arrow-left.gif");
			}
			if ( $(this).hasClass("cleft4") && $(this).hasClass("ctop3") ) {
					$("#popupMember img.arrow").css({left:"164px",top:"62px",bottom:"auto"});
					$("#popupMember img.arrow").attr("src",ebayPath + "images/arrow-right.gif");
			}
			if ( $(this).hasClass("cleft3") && $(this).hasClass("ctop4") ) {
					$("#popupMember img.arrow").css({left:"-8px",top:"62px",bottom:"auto"});
					$("#popupMember img.arrow").attr("src",ebayPath + "images/arrow-left.gif");
			}
			
			if (conbottom == 117) { //conbottom不为0，说明是处理第三行格子的tooltip
				$con.css({
					'bottom' : conbottom,
					'left' : conleft,
					'top':'auto'
				});
			} else {
				$con.css({
					'top' : contop,
					'left' : conleft,
					'bottom':'auto'
				});
			}
			$con.show();	
	},function(){
		//鼠标out之后图片还原大小
		if( $(this).hasClass("doubleheight") ) {  //对高度为双倍格子得图片特殊处理
			$(this).children().children().css({width:"121px",height:"246px",margin:"2px"});
		} else {
			$(this).children().children().css({width:"121px",height:"121px",margin:"2px"});
		}
		$con.hide();
	});
	$("#popupMember").hover(function(){
		$(this).show();	
	},function(){
		$(this).hide();	
	});
	
	//处理ebay love 名人stylemouseover出头像的效果
	var yuantu;
	$("#loves div > div[manpic]").hover(function(){
		yuantu = $(this).children().children().attr("src");
		$(this).children().children().attr("src", $(this).attr("manpic"));
		$(this).children().children().css({width:"95px",height:"95px",margin:"15px"});
	},function(){
		$(this).children().children().attr("src", yuantu);
		$(this).children().children().css({width:"121px",height:"121px",margin:"2px"});
	});
	
	/* 全球新品 tab*/
	$(".newpicslide").eq(0).show();
	var Interval_control = setInterval(tabauto,5000); 
	var j = 1;
	$("#newclass li").each(function(i){ //处理全球新品tab效果
		$("#newclass li").eq(i).hover(
			function(){
				$("#newclass li a.current").removeClass("current");
				$("#colordot li").removeClass("sel");
				$(this).children().addClass("current");
				$("#colordot li").eq(i).addClass("sel");
				$(".newpicslide").hide();
				$(".newpicslide").eq(i).fadeIn();
				j = i +1; 
				clearInterval(Interval_control);
			},
			function() {
				Interval_control = setInterval(tabauto,5000);
			}
		  )
		}) 
	$("#colordot li").each(function(i){ //处理全球新品处彩色圈圈的tab效果
		$("#colordot li").eq(i).hover(
			function(){
				$("#newclass li a.current").removeClass("current");
				$("#colordot li.sel").removeClass("sel");
				$(this).addClass("sel");
				$("#newclass a").eq(i).addClass("current");
				$(".newpicslide").hide();
				$(".newpicslide").eq(i).fadeIn();
				j = i + 1;
				clearInterval(Interval_control);
			},
			function() {
				Interval_control = setInterval(tabauto,5000);
			}
		  )
		}) 
		function tabauto() {
			if ( j >4 ) { j = 0}
			$("#colordot li.sel").removeClass("sel");
			$("#colordot li").eq(j).addClass("sel");
			$("#newclass li a.current").removeClass("current");
			$("#newclass a").eq(j).addClass("current");
			$(".newpicslide").hide();
			$(".newpicslide").eq(j).fadeIn();
			j++;
			if ( j == 5) { j = 0}
		}
		$(".newpicslide").hover(
			function(){
				clearInterval(Interval_control);
			},
			function() {
				Interval_control = setInterval(tabauto,5000);
			}
		  )
		
	
	/* limited date 效果*/
	$("#lmd-head a").each(function(i){ //
		$("#lmd-head a").eq(i).hover(
			function(){
				$("#lmd-head a.sel").removeClass("sel");
				$(this).addClass("sel");
				$("#lmd-body div").hide();
				$("#lmd-body div").eq(i).show();
			}
		  )
		}) 
	
	
	/*全球同步效果*/
	
	$("#fashioncity a").hover(function(){
		$(this).children().css({height:"113px",width:"93px",margin:0});	
	},function(){
		$(this).children().css({height:"111px",width:"91px",margin:"1px"});	
	});
	
	
	/*kv slides 效果*/
	$('#kvshow').slides({
		preload: true,
		preloadImage: 'images/loading.gif',
		effect: 'slide',
		play: 4000,
		pause: 1500,
		generateNextPrev: false,
		generatePagination: false,
		hoverPause: true
		
	}); 
	
	
});