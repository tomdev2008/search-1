<#if theme?? && theme=="1">
<#-- type=1 -->
<div class="xs-minpage">
<ul>
	<li class="pagenum">${currentPage}/${totalPage}</li>
	<li class="${(currentPage==1)?string('disable','')}"><a class="l_page" href="${(currentPage==1)?string('javascript:void(0);',preUrl)}">上一页</a></li>
	<li class="${(currentPage==totalPage)?string('disable','')}"><a class="r_page" href="${(currentPage==totalPage)?string('javascript:void(0);',nextUrl)}">下一页</a></li>
</ul>
</div>
<#else>
<#-- type=0 or null -->
<div class="Pagecon">
<div class="Pagenum">

	<#if currentPage == 1>
		<a href="javascript:;">首页</a>
		<a href="javascript:;" class="pre-page">上一页</a>
	<#else>
		<a href="${firstUrl}">首页</a>
		<a href="${preUrl}" class="pre-page">上一页</a>
	</#if>
	
	<#if currentPage lte 10>
		<#list pageUrls as pageUrl>
			<#if pageUrl == "currentPage">
				<span class="curpage">${currentPage}</span>
			<#else>
				<a href="${pageUrl}">${pageIndex[pageUrl_index]}</a>
			</#if>
		</#list>
	<#else>
		<a href="${firstUrl}">1</a>
		<a href="${secondUrl}">2</a>
		<a href="${thirdUrl}">3</a>
		<#-- 当前页减2 && 小于左边页数时 -->
	    <#if (currentPage-2) &gt; leftStep>
			<span class="elli">...</span>
			<span class="elli">...</span>
		</#if>
		<#list pageUrls as pageUrl>
			<#if pageUrl == "currentPage">
				<span class="curpage">${currentPage}</span>
			<#else>
				<a href="${pageUrl}">${pageIndex[pageUrl_index]}</a>
			</#if>
		</#list>
	</#if>
	
	<#if currentPage == totalPages>
		<a href="javascript:;" class="next-page">下一页</a>
		<a href="javascript:;">尾页</a>
	<#else>
		<a href="${nextUrl}" class="next-page">下一页</a>
		<a href="${lastUrl}">尾页</a>
	</#if>
</div>
<div class="Pageopt">
    <div class="totalnum">
        共<span>${totalPages}</span>页
    </div>
</div>
</div>
</#if>