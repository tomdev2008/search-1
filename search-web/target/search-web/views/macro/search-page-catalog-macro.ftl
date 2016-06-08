<#macro searchCatalogItemDisplay catalog index=0 displayCount=true>
<dl class="ct-dl">
<dt id="J_catalog_dt_0_${catalog.catalogId}" class="${((fatParams.catalogId)?? && catalog.catalogId==fatParams.catalogId)?string('fb fbo','fbo')}">
<#if catalog.childCatalog??>
	<#if catalog.selected>
		<#if catalog.catalogId==fatParams.catalogId>
		<i class="cur"></i>
		<#else>
		<i class="ret"></i>
		</#if>
	<#elseif !selectedCatalog?? && index==0>
		<i class="plus minus"></i>
	<#else>
		<i class="plus"></i>
	</#if>
</#if>
	<a clkstat="${clkstat!}${catalog.catalogName?url}|cat_lv=1" dataCollect="type=list|id=${catalog.catalogId}" href="<@xurl value="${urlTemp}&cat=${catalog.catalogId}"/>" title="${catalog.catalogName?html}<#if displayCount>(${catalog.itemCount})</#if>">${catalog.catalogName?html}<#if (catalog.itemCount gt 0)&&(displayCount)><b>(${catalog.itemCount})</b></#if></a>
</dt>
<#if catalog.childCatalog??>
<dd id="J_catalog_dd_${catalog.catalogId}" class="${(catalog.selected || (!(fatParams.catalogId)?? && index==0))?string('','hide')}">
	<#list catalog.childCatalog as catalog2>
	<dl>
		<dt id="J_catalog_dt_1_${catalog2.catalogId}" class="${((fatParams.catalogId)?? && catalog2.catalogId==fatParams.catalogId)?string('fb fbo','fbo')}">
		<#if catalog2.childCatalog??>
			<#if catalog2.selected>
        		<#if catalog2.catalogId==fatParams.catalogId>
        		<i class="cur"></i>
        		<#else>
        		<i class="ret"></i>
        		</#if>
        	<#else>
        	<i class="plus"></i>
        	</#if>
    	</#if>
			<a clkstat="${clkstat!}${catalog.catalogName?url},${catalog2.catalogName?url}|cat_lv=2" dataCollect="type=list|id=${catalog2.catalogId}" href="<@xurl value="${urlTemp}&cat=${catalog2.catalogId}"/>" title="${catalog2.catalogName?html}<#if displayCount>(${catalog2.itemCount})</#if>">${catalog2.catalogName?html}<#if (catalog2.itemCount gt 0)&&(displayCount)><b>(${catalog2.itemCount})</b></#if></a>
		</dt>
		<#if catalog2.childCatalog??>
		<dd id="J_catalog_dd_${catalog2.catalogId}" class="${(catalog2.selected)?string('','hide')}">
			<#list catalog2.childCatalog as catalog3>
			<dl class="child-ct">
				<dt id="J_catalog_dt_2_${catalog3.catalogId}" class="${((fatParams.catalogId)?? && catalog3.catalogId==fatParams.catalogId)?string('fb fbo','fbo')}">
    				<a clkstat="${clkstat!}${catalog.catalogName?url},${catalog2.catalogName?url},${catalog3.catalogName?url}|cat_lv=3" dataCollect="type=list|id=${catalog3.catalogId}" href="<@xurl value="${urlTemp}&cat=${catalog3.catalogId}"/>" title="${catalog3.catalogName?html}<#if displayCount>(${catalog3.itemCount})</#if>">${catalog3.catalogName?html}<#if (catalog3.itemCount gt 0)&&(displayCount)><b>(${catalog3.itemCount})</b></#if></a>
    			</dt>
			</dl>
			</#list>
		</dd>
		</#if>
	</dl>
	</#list>
</dd>
</#if>
</dl>
</#macro>