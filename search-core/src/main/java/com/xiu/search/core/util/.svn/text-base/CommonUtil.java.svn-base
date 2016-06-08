package com.xiu.search.core.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.xiu.search.core.bo.CatalogBo;
import com.xiu.search.core.bo.FacetFilterBo;
import com.xiu.search.core.bo.FacetFilterValueBo;
import com.xiu.search.core.bo.GoodsItemBo;
import com.xiu.search.core.bo.SearchBo;
import com.xiu.search.core.bo.SkuBo;
import com.xiu.search.core.config.XiuSearchProperty;
import com.xiu.search.core.solr.enumeration.MktTypeEnum;
import com.xiu.search.core.solr.model.GoodsSolrModel;
import com.xiu.search.core.solr.model.XiuSKUIndexModel;
import com.xiu.search.dao.config.XiuSearchConfig;

/**
 * 公共方法定义
 */
public class CommonUtil {
    
	private static Logger LOGGER = Logger.getLogger(CommonUtil.class);
    /**
     * 判断是否是立即购买商品
     * @param onsale
     * @param offshow
     * @return
     */
    public static boolean isBuyNow(int onsale,int offshow){
        return 1 == onsale;
    }
    
    /**
     * 判断是否是售罄商品
     * @param onsale
     * @param offshow
     * @return
     */
    public static  boolean isSoldOut(int onsale,int offshow) {
        return 0==onsale && 2==offshow;
    }

    /**
     * 判断是否是到货通知
     * @param onsale
     * @param offshow
     * @return
     */
    public static  boolean isArrivalNotice(int onsale,int offshow) {
        return 0 == onsale && 2 != offshow;
    }
    
    /**
     * 生成图片的请求路径
     * @param imgUrl
     * @param width
     * @param height
     * @param version
     * @return
     */
    public static String createImgUrl(String imgUrl,int width,int height,String version){
        StringBuffer sb = new StringBuffer(8);
        sb.append("upload").append(imgUrl).append("/g1_"+width+"_"+height+".jpg");
        if(StringUtils.isNotBlank(version)){
//            sb.append("?").append("version").append("=").append(version);
            sb.append("?").append(version);
        }
        return sb.toString();
    }    
    
    /**
     * 将XiuSolrModel转化为XiuItemBo
     * @param highLigheKeyword 高亮关键字
     * @param items
     * @return
     */
    public static  List<GoodsItemBo> transformXiuItemBo(String highLigheKeyword,List<GoodsSolrModel> items){
        List<GoodsItemBo> ret = new ArrayList<GoodsItemBo>();
        if(null == items)
            return null;
        GoodsItemBo itemBo = null;
        String name = "";
        String highlightRegex = null;
        if(StringUtils.isNotBlank(highLigheKeyword)) {
            highlightRegex = CommonUtil.createHighlightRegex(highLigheKeyword);
        }
        for (GoodsSolrModel item : items) {
            itemBo = new GoodsItemBo();
            name = "";
           
            /*if(StringUtils.isNotEmpty(item.getItemNamePre())){
                name += item.getItemNamePre();
            }*/
            if(StringUtils.isNotEmpty(item.getItemName())){
                name += item.getItemName();
            }
            /*if(StringUtils.isNotEmpty(item.getItemNamePost())){
                name += item.getItemNamePost();
            }*/
            itemBo.setId(item.getItemId());
            itemBo.setName(name);
            //品名简化
            if(StringUtils.isNotEmpty(item.getBrandName())){
            	itemBo.setBrandNameCn(item.getBrandName());
            }
            if(StringUtils.isNotEmpty(item.getBrandNameEn())){
            	itemBo.setBrandNameEn(item.getBrandNameEn());
            }
            if(StringUtils.isEmpty(item.getBrandName()) && StringUtils.isEmpty(item.getBrandNameEn())){
            	LOGGER.warn("==================中英文品名都为空走秀码为:"+item.getPartNumber());
            }
            if(null != highlightRegex){
                itemBo.setNameHighlight(CommonUtil.highlightItemName(highlightRegex, name));
            }else{
                itemBo.setNameHighlight(name);
            }
            itemBo.setBuyNow(CommonUtil.isBuyNow(item.getOnsale(), item.getOffshow()));
            itemBo.setArrivalNotice(CommonUtil.isArrivalNotice(item.getOnsale(), item.getOffshow()));
            itemBo.setSoldOut(CommonUtil.isSoldOut(item.getOnsale(), item.getOffshow()));
            itemBo.setImgUrl100(CommonUtil.createImgUrl(item.getImgUrl(), 100, 100, item.getImgVersion()));
//            itemBo.setImgUrl220(CommonUtil.createImgUrl(item.getImgUrl(), 220, 220, item.getImgVersion()));
            // eBay改版图片采用4:3格式 modifyTime  20140711
            itemBo.setImgEbayUrl220(CommonUtil.createImgUrl(item.getImgUrl(), 234, 312, item.getImgVersion()));
            itemBo.setImgUrl220(CommonUtil.createImgUrl(item.getImgUrl(), 234, 312, item.getImgVersion()));
            itemBo.setXiuPrice(item.getPriceXiu() != null ? item.getPriceXiu() : 0);
            itemBo.setMktPrice(item.getPriceMkt());
            itemBo.setShowPrice(item.getPriceFinal()!=null && item.getPriceFinal()>=0 ? item.getPriceFinal() : 
            	(item.getPriceXiu() !=null && item.getPriceXiu() >0) ? item.getPriceXiu() : 
            		item.getPriceMkt());
            itemBo.setProviderCode(item.getProviderCode());
            itemBo.setDisCount(item.getDiscount());
            //走秀码 
            itemBo.setXiuSn(item.getPartNumber());
            ret.add(itemBo);
        }
        return ret;
    }
    
    /**
     * 匹配推荐引擎信息
     * @param mktType
     * @param brandOn
     * @param catalogId
     * @param goodsList
     * @return
     */
    public static String[] matchRecommendInfo(MktTypeEnum mktType,Integer catalogId,List<GoodsSolrModel> goodsList){
    	//前面商品数大于等于6,就取前6个
    	int length=goodsList.size()>5?6:goodsList.size();
    	//记录商品的最后一级
    	Set<String> lastLevel=new LinkedHashSet<String>();
    	
    	//排序用,第3级排在最前面,插入到lastLevel中
    	Set<String> level3Result=new HashSet<String>();
		Set<String> level2Result=new HashSet<String>();
		Set<String> level1Result=new HashSet<String>();
		
		//记录商品
		String itemIds=null;
		
		//遍历每一个商品的最后一级
    	for (int i = 0; i <length; i++) {
    		Set<String> level3=new HashSet<String>();
    		Set<String> level2=new HashSet<String>();
    		Set<String> level1=new HashSet<String>();
    		
    		GoodsSolrModel goods=goodsList.get(i);
    		
    		//商品编号
    		itemIds=itemIds!=null?itemIds+","+goods.getItemId():goods.getItemId();

    		String[] classPath=null;
    		if(mktType==MktTypeEnum.EBAY){
    			classPath=goods.getOclassPathEBay();
        	}else if(mktType==MktTypeEnum.XIU){
        		classPath=goods.getOclassPath();
        	}
    		
    		if(null==classPath||classPath.length<1){
    			continue;
    		}
    		
    		for (String oclass : classPath) {
    			String[] oclassLevel=StringUtils.split(oclass,'|');
    			if(oclassLevel.length==3){
    				level3.add(oclassLevel[2].substring(0,oclassLevel[2].indexOf(":")));
    			}else if(oclassLevel.length==2){
    				level2.add(oclassLevel[1].substring(0,oclassLevel[1].indexOf(":")));
    			}else if(oclassLevel.length==1){
    				level1.add(oclassLevel[0].substring(0,oclassLevel[0].indexOf(":")));
    			}
    			
    			//如果存在当前运营分类,直接返回清空缓存返回当前分类下的最后一级分类
    			if(oclass.indexOf("|"+catalogId+":")>0){
    				if(oclassLevel.length==3){
    					level3.clear();
    					level3.add(oclassLevel[2].substring(0,oclassLevel[2].indexOf(":")));
    				}else if(oclassLevel.length==2){
    					level2.clear();
    					level2.add(oclassLevel[1].substring(0,oclassLevel[1].indexOf(":")));
    				}else if(oclassLevel.length==1){
    					level1.clear();
        				level1.add(oclassLevel[0].substring(0,oclassLevel[0].indexOf(":")));
        			}
    				break;	
    			}
    		}
    		
        	if(level3.size()>0){
        		level3Result.addAll(level3);
        	}else if(level2.size()>0){
        		level2Result.addAll(level2);
        	}else if(level1.size()>0){
        		level1Result.addAll(level1);
        	}
    	}
    	
    	lastLevel.addAll(level3Result);
    	lastLevel.addAll(level2Result);
    	lastLevel.addAll(level1Result);
    	
    	if(null!=lastLevel&&lastLevel.size()>0){
    		
    		String catalogString="";
    		for (String catId : lastLevel) {
    			catalogString=catalogString.length()>0?catalogString+","+catId:catId;
			}
    		String[] info={itemIds,catalogString};
    		return info;
    	}else{
    		return null;
    	}
    }
    
    
    /**
     * 将XiuSolrModel转化为XiuItemBo
     * @param items
     * @return
     */
    public static  List<GoodsItemBo> transformXiuItemBo(List<GoodsSolrModel> items) {
        return transformXiuItemBo(null, items);
    }
    
    /**
     * 创建高亮正则表达式
     * @param kw
     * @return
     */
    public static String createHighlightRegex(String kw){
        char[] incharArr = kw.toLowerCase().toCharArray();
        StringBuffer tokSb = new StringBuffer(incharArr.length);
        int charType = 0;//0-其他，1-字母，2-数字
        char c;
        for (int i = 0,len = incharArr.length; i < len; i++) {
            c = incharArr[i];
            if(c>='a' && c<='z'){
                if(charType!= 1){
                    tokSb.append('|');
                }
                tokSb.append('[').append(c).append(Character.toUpperCase(c)).append(']');
                charType = 1;
            }else if(c>='0' && c<='9'){
                if(charType!= 2){
                    tokSb.append('|');
                }
                tokSb.append(c+"");
                charType = 2;
            }else if(Character.isWhitespace(c)){
                charType = 0;
            }else{
                tokSb.append('|').append(XiuSearchStringUtils.escapeRegexMetacharactor(c+""));
                charType = 0;
            }
            
        }
        return tokSb.substring(1,tokSb.length());
    }
    
    
    /**
     * 高亮商品名称
     * @param itemName
     * @return
     */
    public static String highlightItemName(String reg,String name){
        Pattern p = Pattern.compile("(" + reg + ")");
        Matcher m = p.matcher(name);
        StringBuffer sb = new StringBuffer();
        String replacement = XiuSearchConfig.getPropertieValue(XiuSearchConfig.SEARCH_HIGHLIGHT_PREFIX) 
                + "$1" 
                + XiuSearchConfig.getPropertieValue(XiuSearchConfig.SEARCH_HIGHLIGHT_SUFFIX);
        while (m.find()) {
            m.appendReplacement(sb, replacement);
        }
        m.appendTail(sb);
        return sb.toString();
    }
    
    
    /**
     * 解析用于品牌ABCD/EFG分组切换的MAP
     * @return
     */
    public static Map<String, List<FacetFilterValueBo>> parseBrandLetterGroupMap(FacetFilterBo brandFacetBo){
        if(null == brandFacetBo || brandFacetBo.getFacetValueBos() == null || brandFacetBo.getFacetValueBos().size()==0)
            return null;
        Map<String, List<FacetFilterValueBo>> ret = new HashMap<String, List<FacetFilterValueBo>>();
        String firstLetter;
        for (FacetFilterValueBo valueBo : brandFacetBo.getFacetValueBos()) {
            firstLetter = XiuSearchStringUtils.getFirstLetter(valueBo.getName()).toUpperCase();
            if(firstLetter!=null && firstLetter.length()>0){
                firstLetter = firstLetter.substring(0,1);
            }else{
                continue;
            }
            for (String fl : SearchBo.BRAND_FIRST_LETTER_GROUPS) {
                if(fl.indexOf(firstLetter)>=0){
                    if(!ret.containsKey(fl))
                        ret.put(fl, new ArrayList<FacetFilterValueBo>());
                    ret.get(fl).add(valueBo);
                    break;
                }
            }
        }
        return ret;
    }
    
    /**
     * 将商品名称从中文翻译成拼音,转化为大写,方便页面查询	William.zhang 20130506
     * @return
     */
    public static List<FacetFilterValueBo> parseChineseToPinyin(List<FacetFilterValueBo> facetFilterValueBoList){
    	for(FacetFilterValueBo facetFilterValueBo : facetFilterValueBoList){
    		String name = XiuSearchStringUtils.getPingYin(facetFilterValueBo.getName());
    		facetFilterValueBo.setPyName(name.toUpperCase());
    	}
    	return facetFilterValueBoList;
    }
    
    /**
     * 判断数据的位数是否小于给定的位数
     * @param src 数据
     * @param digit 位数
     * @return
     */
    public static boolean checkNumGtDigit(Integer src,int digit){
    	if(src==null){
    		return true;
    	}
    	return src.intValue()<Math.pow(10,digit);
    }
    
    /**
     * 是否启用运营分类改造
     * @return
     */
    public static boolean enabledCatalogRemold(){
    	return XiuSearchProperty.getInstance().getCatalogRemoldEnable();
//    	return Boolean.valueOf(XiuSearchConfig.getPropertieValue(XiuSearchConfig.CATALOG_ENABLED));
    }
    
    /**
     * 是否启用过滤老的运营分类
     * @return
     */
    public static boolean enabledFilterCatalog(){
    	return XiuSearchProperty.getInstance().getOldCatalogFilterEnable();
//    	return Boolean.valueOf(XiuSearchConfig.getPropertieValue(XiuSearchConfig.FILTER_OLD_CATALOG));
    }
    
    /**
     * 老的运营分类ID最大位数
     * @return
     */
    public static int oldCatalogIdMaxLength(){
    	return XiuSearchProperty.getInstance().getOldCatalogIdMaxLength();
//    	return Integer.valueOf(XiuSearchConfig.getPropertieValue(XiuSearchConfig.OLD_CATALOG_LENGTH));
    }
    
    /**
     * 前端是否展示display为0的运营分类
     * @return
     */
    public static boolean enabledCatalogByDisplayIS0(){
    	return XiuSearchProperty.getInstance().enabledCatalogByDisplayIS0();
//    	return Boolean.valueOf(XiuSearchConfig.getPropertieValue(XiuSearchConfig.ENABLE_SHOW_DISPLAY0_CATALOG));
    }
    
    /**
     * 推荐数据是否允许从Memcache中获取
     * @return
     */
    public static boolean enabledLoadFromMemcache(){
    	return XiuSearchProperty.getInstance().enabledLoadFromMemcache();
    }
    
    /**
     * 封装每个商品要展示的尺码 
     * @param skuList
     * @param goodsItemBoList
     * @param showCount 
     * @return
     */
    public static boolean buildGoodsShowSkuList(List<XiuSKUIndexModel> skuList,List<GoodsItemBo> goodsItemBoList,int showCount){ 
    	
    	Map<String, Map<String,SkuBo>> skuMap = buildSkus(skuList);
    	if(skuMap==null || skuMap.size()==0){
    		return true;
    	}
    	Map<String,SkuBo> sizeMap = null;
    	List<SkuBo> tmpList=null;
    	
    	/*
    	 * 添加此自定义比较器的原因：
    	 *    如果查询出来的尺码合并后有20个，页面展示6个，但按尺码排序后前8个的库存量都为0，这样返回后页面中尺码全是不可用的，可能会造成用户误解不会点进商品详情查看。
    	 *    
    	 *    2014-07-01 11:35 目前只根据orderVal字段排序
    	 */
    	Comparator<SkuBo> skuComparator = new SkuSizeCompare();
    	
    	
    	for(GoodsItemBo g:goodsItemBoList){
    		if(!skuMap.containsKey(g.getId())){
    			continue;
    		}
    		sizeMap=skuMap.get(g.getId());
    		if(sizeMap== null || sizeMap.size()==0){
    			continue;
    		}
    		if(sizeMap.size()<=showCount){
    			g.setSkuList(new ArrayList<SkuBo>(sizeMap.values()));
    			Collections.sort(g.getSkuList(),skuComparator);
    		}else{
    			tmpList = new ArrayList<SkuBo>(sizeMap.values());
    			Collections.sort(tmpList,skuComparator);
    			for(int i=0;i<showCount-1;i++){
    				g.getSkuList().add(tmpList.get(i));
    			}
    			g.getSkuList().add(buildMoreSKUBo(g.getId()));
    		}
    	}
    	
    	return true;
    }
    
    
    /**
     * 删除display=2的节点
     * @param tmp
     * @return
     */
    public static CatalogBo deleteDisplay2(CatalogBo tmp){
    	if(tmp==null){
    		return null;
    	}
		if(Constants.CATALOG_DISPLAY_HIDDEN.equals(tmp.getDisplay()) && tmp.getCatalogModel().getParentCatalogId()==0){
			CatalogBo tmp1 = tmp.clone();
			tmp1.setChildCatalog(new ArrayList<CatalogBo>());
			return tmp1;
		}
		CatalogBo selectCatalogTree = tmp.clone();
		List<CatalogBo> tmp1List=selectCatalogTree.getChildCatalog();
		if(tmp1List!=null && tmp1List.size()>0){
			List<CatalogBo> tmp2List=null;
			for(int i=0;i<tmp1List.size();i++){
				if(Constants.CATALOG_DISPLAY_HIDDEN.equals(tmp1List.get(i).getDisplay())){
					selectCatalogTree.getChildCatalog().remove(i);
					//return;
					--i;
					continue;
				}
				tmp2List=tmp1List.get(i).getChildCatalog();
				if(tmp2List!=null && tmp2List.size()>0){
					for(int j=0;j<tmp2List.size();j++){
						if(Constants.CATALOG_DISPLAY_HIDDEN.equals(tmp2List.get(j).getDisplay())){
							selectCatalogTree.getChildCatalog().get(i).getChildCatalog().remove(j);
							//return;
							--j;
							continue;
						}
					}
				}
			}
		}
		return selectCatalogTree;
	}
    
    /**
     * 
     * com.xiu.search.core.util.SkuSizeCompare.java
    
     * @Description: TODO  自定义比较器，比较两个尺码的库存，按库存降序
     *    
     *    
     *                     【注意】现在最新修改的是按尺码升序排序 ，添加日期：2014-03-13 11：30
    
     * @author lvshuding   
    
     * @date 2014-3-12 下午5:05:15
    
     * @version V1.0
     */
    private static class SkuSizeCompare implements Comparator<SkuBo>{

		@Override
		public int compare(SkuBo o1, SkuBo o2) {
			
			return o1.getOrderBy().intValue()-o2.getOrderBy().intValue();
			
			//只按尺码排序
//			if(!XiuSearchProperty.getInstance().enableSkuSizeSortByQty()){
//				return o1.getSkuSize().trim().compareToIgnoreCase(o2.getSkuSize().trim());
//			}
			
//			if(o1.getQty()==555555 && "更多".equals(o1.getSkuSize())){
//				return 1;
//			}
//			if(o2.getQty()==555555 && "更多".equals(o2.getSkuSize())){
//				return -1;
//			}
//			if(o1.getQty()==o2.getQty()){
//				return o1.getSkuSize().trim().compareToIgnoreCase(o2.getSkuSize().trim());
//				//return 0;
//			}
//			if(o1.getQty()>o2.getQty()){
//				return -1;
//			}
//			return 1;
		}
    	
    }
    
   
    
    /**
     * 把每个商品下的所有SKU数据归类
     * @param skuList
     * @return
     */
    private static Map<String, Map<String,SkuBo>> buildSkus(List<XiuSKUIndexModel> skuList){
    	Map<String, Map<String,SkuBo>> skuMap = new HashMap<String, Map<String,SkuBo>>();
    	Map<String,SkuBo> mMap=null;// key:尺码  	value:SKU对象
    	SkuBo tmp=null;
    	for(XiuSKUIndexModel m:skuList){
    		mMap=skuMap.get(m.getItemId());
    		if(mMap==null){
    			mMap = new HashMap<String, SkuBo>();
    			mMap.put(m.getSkuSize().toLowerCase().trim(), buildSKUBo(m));
    			skuMap.put(m.getItemId(), mMap);
    			continue;
    		}
    		tmp = mMap.get(m.getSkuSize().toLowerCase().trim());
    		if(tmp==null){
    			mMap.put(m.getSkuSize().toLowerCase().trim(), buildSKUBo(m));
    			continue;
    		}
    		if(m.getQty()==null){
    			m.setQty(0);
    		}
    		if(tmp.getQty()==null){
    			tmp.setQty(0);
    		}
    		tmp.setQty(m.getQty()+tmp.getQty());
    		
    		/*
    		 *  2014-07-01 11:49 同一个尺码不同颜色的SKU合并时，排序值取最小的 
    		 */
    		tmp.setOrderBy(Math.min(m.getOrderBy().intValue(), tmp.getOrderBy().intValue()));
    	}
    	
    	return skuMap;
    }
    
    /**
     * 把Model转换成Bo
     * @param m
     * @return
     */
    private static SkuBo buildSKUBo(XiuSKUIndexModel m){
    	SkuBo bo=new SkuBo();
		bo.setItemCode(m.getItemCode());
		bo.setItemId(m.getItemId());
		bo.setSkuCode(m.getSkuCode());
		bo.setSkuColor(m.getSkuColor());
		bo.setSkuSize(m.getSkuSize());
		bo.setQty(m.getQty());
		bo.setOrderBy(m.getOrderBy());
		
		return bo;
    }
    
    /**
     * 创建Bo-More
     * @param id 	商品ID
     * @return
     */
    private static SkuBo buildMoreSKUBo(String id){
    	SkuBo bo=new SkuBo();
		bo.setItemId(id);
		bo.setSkuSize("更多");
		bo.setQty(555555);
		bo.setOrderBy(57570);
		return bo;
    }
    
    
//    /**
//     * 生成点击流需要的cookie<br>
//     * 注意：临时测试方法，需确认是否需要flow_cookie.jar包。
//     * @param kw
//     * @param count
//     * @param page
//     * @return
//     */
//    @Deprecated
//    public static String buildClickCookieValue(String kw,int count,int page){
//        StringBuffer sb = new StringBuffer();
//        try {
//            sb.append("kw=").append(kw).append("|count=").append(count).append("|page=").append(page);
//            return URLEncoder.encode(sb.toString(), "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return sb.toString();
//    }
}
