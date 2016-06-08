<script>
if(typeof GXIU == 'undefined')GXIU={};
<#-- CMS页头 -->
<#if !(showType??)>
<#elseif showType=='SCP'>
GXIU.showType="luxury";
</#if>
</script>
<#include "/resources/cms/xiu_head_1000.shtml" parse="false">
<div class="fix_hg2">
	<div id="navbox" class="navbox">
		<#include "/resources/cms/index_category_page_first_level.shtml" parse="false"/>
		<script type="text/javascript">
			jQuery(function(){
					$.ajax({
					  url: "/resources/cms/index_category_page_120106.shtml",
					  type: "get",
					  dataType: "html",
					  success:function(data){
					  	 jQuery('#navbox').html(data);
						 dropMenu(".drop-menu-effect");
					  }
					});
		        });
		</script>
	</div>
</div>