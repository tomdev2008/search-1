<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="ie=7" />
<title>系统检测  - SEARCH - 走秀网</title>
<script src="${staticUrl}static/js/library/jquery-1.6.2.min.js" type="text/javascript"></script>
<style type="text/css">
/* CSS Document */
body, dl, dt, dd, ol, ul, h1, h2, h3, h4, h5, form, fieldset, input, textarea, buttom, blockquote, pre, p, td, th { margin:0; padding:0; }
body, input, option, buttom, select, textarea, table { font:12px/1.5 tahoma, arial, 宋体, sans-serif }
ul,ol{ list-style:none outside none}
/*a{color:#0063DC; text-decoration:none;outline:none}
a:hover{color:#f60; text-decoration:underline}*/
/*.h{color:#ff5500 !important;}
.loading-16{}
.loading-32{}*/
.hl{color:#ff5500 !important;}
.clearfix:after{clear:both;content:".";display:block;height:0;visibility:hidden;_height:1%;}
.fl{ float:left;}
.fr{ float:right}
.hide{ display:none;}
.cb{ clear:both}
.cl{ clear:left}
.cr{ clear:right}
.tl{text-align: left;}
.tr {text-align: right;}
.tc {text-align: center;}
.vm {vertical-align: middle;}
.pr {position: relative;}
.pa {position: absolute;}
.fn {font-weight: normal;}
.fb {font-weight: bold;}
.fnum{font-size:0.9em}
.tlt{ text-decoration:line-through}
.zoom {zoom:1}

/******颜色******/
.cred{ color:#F00}
.cgreen{ color:#090}
/******间距******/
.p5{ padding:5px}
.pt5{ padding-top:5px}
.pl5{ padding-left:5px}
.pr5{ padding-right:5px}
.pb5{ padding-bottom:5px;}
.p10{ padding:10px}
.pt10{ padding-top:10px;}
.pl10{ padding-left:10px;}
.pr10{ padding-right:10px}
.pb10{ padding-bottom:10px;}
.mt5{ margin-top:5px;}
.mr5{ margin-right:5px;}
.mb5{ margin-bottom:5px;}
.ml5{ margin-left:5px;}
.mt10{ margin-top:10px;}
.mr10{ margin-right:10px;}
.mb10{ margin-bottom:10px;}
.ml10{ margin-left:10px;}
.pl3{ padding-left:3px}
.pr3{ padding-right:3px}

.wp100{ width:100%}

body{ text-align:center}
.config-box{padding:10px; overflow:hidden}
.config-table{width:100%; text-align:left;}
.config-table th{font-size:12px;border:1px solid #BBB;background-color:#e5e5e5; line-height:1.5em;padding:0.6em; }
.config-table th div{display:block;padding:0.6em;}
.config-table th a{padding:0.6em;}
.config-table th a:hover{text-decoration: none;}
.config-table th.selected{background-color:#c5c5c5;  }
.config-table th.selected a{color:#000;font-weight:bold;padding-right:18px;position: relative;}
.config-table th.selected .ic-desc,.config-table th.selected .ic-asc{background:url(${staticUrl}styles/img/sort_desc_asc.png) no-repeat 0 0;display:block;width:14px;height:14px;position: absolute;top:50%;margin-top:-7px;right:0px;}
.config-table th.selected .ic-asc{background-position:-20px 2px;display:inline-block;*display:inline;*zoom:1;}
.config-table td{border:1px solid #DDD;padding:0.4em}
.config-table td.selected{background-color:#f0f0f0;font-weight:bold}
.config-table .even td{background-color:#f9f9f9}
.config-table .even td.selected{background-color:#e5e5e5}

.cache-opt-box{display:none;border:1px solid #ccc;}
.cache-opt-box li{padding:5px 10px; border-bottom:1px solid #ccc;}
.cache-opt-box li label{float:left;text-align:right;width:130px;padding-right:5px;}
</style>
</head>
<body>
<div class="config-box">
<table class="config-table" cellpadding="0" cellspacing="0">
	<tr>
		<th>Navigation</th>
	</tr>
    <tr>
		<td>
        <a href="#a1">Server information</a>
        <span class="pl5 pr5">|</span>
        <a href="#a2">Local cache information</a>
        <span class="pl5 pr5">|</span>
        <a href="#a3">catagroup query string search</a>
        </td>
	</tr>
</table>
<table id="a1" class="config-table" cellpadding="0" cellspacing="0">
	<tr>
		<th colspan="2">Server information</th>
	</tr>
	<#if developFlag>
	<tr>
		<td>Model</td>
		<td>Develop status</td>
	</tr>
	</#if>
	<tr>
		<td width="120">Server name</td>
		<td>${hostName}</td>
	</tr>
	<tr>
		<td>Server address</td>
		<td>${hostAddress}</td>
	</tr>
	<tr>
		<td>Server port</td>
		<td>${hostPort}</td>
	</tr>
</table>
<table id="a2" class="config-table" cellpadding="0" cellspacing="0">
	<tr>
		<th colspan="2">Information of local cache</th>
	</tr>
	<tr>
		<td width="120">Total cache size</td>
		<td>${cacheSize}</td>
	</tr>
	<#if cacheInfoMap?? >
	<tr>
		<td colspan="2">
		<script>
		if(typeof LEON =='undefined')LEON = {};
		LEON.stopDefault=function( e ) { 
		    if ( e && e.preventDefault ) 
		        e.preventDefault(); 
		    else 
		        window.event.returnValue = false; 
		    return false; 
		}
		LEON.warnInfo = function(e){
			if(confirm('Are you sure?')){
				return true;
			}
			LEON.stopDefault(e);
			return false;
		}
		</script>
		<#list cacheInfoMap?keys as itemKey>
			<#assign itemInfo = (cacheInfoMap[itemKey])! />
			<#assign stats = itemInfo["stats"] />
			<table class="config-table" cellpadding="0" cellspacing="0">
				<tr>
					<th colspan="14">${itemKey}
					<a class="ml10 fn" href="${searchUrl}config-info.htm?opt=CLEAR_CACHE&cacheType=${itemKey}" onclick="LEON.warnInfo(event);">[clear]</a>
					</th>
				</tr>
				<tr>
					<td class="fb" width="70">Hits ratio:</td>
					<#if (stats.lookups)?? && stats.lookups gt 0>
					<td class="hl fb" width="50">${(stats.hits/stats.lookups*100)?string("0.0")}%</td>
					<#else>
					<td class="hl fb" width="50">-</td>
					</#if>
					<td class="fb" width="60">State:</td>
					<td class="" width="60">${(itemInfo["state"])!'unknown'}</td>
					<td class="fb" width="60">Size:</td>
					<td class="fnum" width="60">${(itemInfo["size"])!'unknown'}</td>
					<td class="fb" width="60">Lookups:</td>
					<td class="fnum" width="60">${(stats.lookups)!'unknown'}</td>
					<td class="fb" width="60">Hits:</td>
					<td class="fnum" width="60">${(stats.hits)!'unknown'}</td>
					<td class="fb" width="60">Inserts:</td>
					<td class="fnum" width="60">${(stats.inserts)!'unknown'}</td>
					<td class="fb" width="60">Evictions:</td>
					<td class="fnum" width="auto">${(stats.evictions)!'unknown'}</td>
				</tr>
			</table>
		</#list>
		</td>
	</tr>
	</#if>
</table>
<table id="a3" class="config-table" cellpadding="0" cellspacing="0">
	<tr>
		<th>catagroup query string search</th>
	</tr>
    <tr>
		<td>
            <form action="/config-info.htm" method="post">
                cataGroupId: <input type="text" size="8" id="cataGroupId" name="cataGroupId" value="${cataGroupId!''}"/> &nbsp; <input type="submit" value="search"/>
            </form>
        </td>
	</tr>
    <tr>
		<td>
            <div>${cataGroupQueryString!''}</div>
        </td>
	</tr>
</table>
</div>
</body>
</html>