$("#J_bottomPageBox .Pagenum a").click(function(){var c=$(this).text();var b=parseInt("${page.pageNo}");var d=parseInt("${page.pageCount}");if("\u4e0a\u4e00\u9875"==c){c=(b-1)>0?(b-1):1}else{if("\u4e0b\u4e00\u9875"==c){c=(b+1)>d?d:(b+1)}else{c=parseInt(c)}}if(!c){return}var a="atype=flip|stype=kw|sattr=${params.kw?url}|page_no=";XIU.Window.ctraceClick(a+c,"search")});