onGetGoodsInfo=function(h,k,f,p){if(!k){return}var r=h.length,b=LEON.bfdData.setting.itemVisitNum;if(r==0){return}var t=p==1?"\u8d2d\u4e70\u6392\u884c":"\u6d4f\u89c8\u6392\u884c";var l=p==1?"buytop":"viewtop";var o=!!LEON.bfdData.setting.stype?LEON.bfdData.setting.stype:"kw";var d=!!LEON.bfdData.setting.sattr?LEON.bfdData.setting.sattr:"";var m="atype="+l+"|stype="+o+"|sattr="+d+"|itemid=";if(p==1){t="\u8d2d\u4e70\u6392\u884c";b=LEON.bfdData.setting.itemSaleNum}var q=0,b=r>b?b:r;var s=$('<div class="hd"><span class="tit">'+t+'</span><span class="tag">Top10</span></div>');var e=$("<div/>").addClass("bd").addClass("clearfix");var c=$("<ul/>").appendTo(e);var a,n,j,g;do{n=h[q];if(n.length<=3){continue}a=$("<li/>");if(q==b-1){a.addClass("nodot")}j=n[3];if(j.indexOf("_100_100")>0){j=j.replace("_100_100","_80_80")}g=n[2];if(g.indexOf("?")<0){g+="?"}else{g+="&"}g+="req_id="+f;a.append('<a clkstat="'+m+n[0]+'" class="img" target="_blank" href="'+g+'"><img src="'+j+'"/></a>');a.append('<a clkstat="'+m+n[0]+'" class="tit" target="_blank" href="'+g+'">'+n[1]+"</a>");a.append('<p class="price">'+n[4]+"</p>");c.append(a)}while(++q<b);e.append('<a href="http://www.baifendian.com" class="bfd-click-link " target="_blank"></a>');$(k).append(s).append(e).show();e.clkBindMethod()};onGetGoodsInfo_bh=function(g,h,a){if(!h){return}var j=g.length;if(j==0){return}var l="\u5b98\u65b9\u63a8\u8350";var f="xiuroc";var o=!!LEON.bfdData.setting.stype?LEON.bfdData.setting.stype:"kw";var m=!!LEON.bfdData.setting.sattr?LEON.bfdData.setting.sattr:"";var q="atype="+f+"|stype="+o+"|sattr="+m+"|itemid=";var e=0,c=j>LEON.bfdData.setting.itemHistoryNum?LEON.bfdData.setting.itemHistoryNum:j;var p=$('<div class="hd"><p class="tit">'+l+"</p></div>");var d=$("<div/>").addClass("bd").addClass("clearfix");var r=$("<ul/>").appendTo(d);var b,k,n;do{k=g[e];if(k.length<=3){continue}b=$("<li/>");if(e==1){b.addClass("first")}if(e==c-1){b.addClass("last")}n=k[2];if(n.indexOf("?")<0){n+="?"}else{n+="&"}n+="req_id="+a;b.append('<p class="picbox"><a clkstat="'+q+k[0]+'" target="_blank" href="'+n+'"><img src="'+k[3]+'" /></a></p>');b.append('<p class="tit"><a clkstat="'+q+k[0]+'" target="_blank" href="'+n+'">'+k[1]+"</a></p>");b.append('<p class="price xs-ico">'+k[4]+"</p>");r.append(b)}while(++e<c);d.append('<a href="http://www.baifendian.com" class="bfd-click-link " target="_blank"></a>');$(h).append(p).append(d).show()};
