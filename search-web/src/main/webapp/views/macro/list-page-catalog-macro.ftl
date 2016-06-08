<#macro listCatalogItemDisplay cataloglist index=0>
    <#list cataloglist as catalog>
        <dt id="J_catgroup_${catalog.catalogId}" 
            <#if catalog.childCatalog?? && catalog.childCatalog?size gt 0>
                class="down ${(catalog.selected )?string('up','')}"
            </#if>
            >
            <span>
                <a clkstat="${clkstat!},${catalog.catalogName?url}|cat_lv=2" dataCollect="type=list|id=${catalog.catalogId}"
                    href="<@xurl value='${urlTemp}&cat=${catalog.catalogId}' />"
                    title="${catalog.catalogName?html}" onclick="$.stopBubble(event);">${catalog.catalogName}</a>
            </span>
            <b class=""></b>
        </dt>
        <#if catalog.childCatalog?? && catalog.childCatalog?size gt 0>    
            <dd id="J_catgroupcon_${catalog.catalogId}" class="${(!catalog.selected )?string('hide','')}">
                <ul class="childlist">
                    <#list catalog.childCatalog as catalog2>
                        <li>
                            <a clkstat="${clkstat!},${catalog.catalogName?url},${catalog2.catalogName?url}|cat_lv=3" dataCollect="type=list|id=${catalog2.catalogId}" class="${(catalog2.catalogId == selectedCatalog.catalogId )?string('active','')}"
                                href="<@xurl value='${urlTemp}&cat=${catalog2.catalogId}' />"
                                title="${catalog2.catalogName?html}"  >
                                    <#if (catalog2.catalogName?length>11)>
                                        ${catalog2.catalogName[0..11]?default("") + "..."}
                                    <#else>
                                        ${catalog2.catalogName?default("")}
                                    </#if>     
                                </a>
                        </li>            
                    </#list>
                </ul>
            </dd>  
        </#if>
	</#list>
</#macro>