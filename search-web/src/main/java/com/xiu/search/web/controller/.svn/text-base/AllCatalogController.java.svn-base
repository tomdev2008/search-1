package com.xiu.search.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xiu.search.core.bo.CatalogBo;
import com.xiu.search.core.catalog.CatalogModel;
import com.xiu.search.core.catalog.XiuCatalogTreeCache;
import com.xiu.search.core.util.Constants;

/**
 * 全部运营分类页面
 * 从运营分类缓存中获取官网运营分类列表
 * 按sortnum值排序
 * 生成List<CatalogBo>用于页面展示
 */
@Controller
public class AllCatalogController extends BaseController {

    private Logger LOGGER = Logger.getLogger(getClass());
    
    /**
     * action主处理方法
     * @return
     * @throws InterruptedException
     */
    @RequestMapping(value="/allcatalog-action")
    public String execute(HttpServletRequest req, HttpServletResponse res ,Model model) 
            throws InterruptedException{
         
        long time = System.currentTimeMillis();
        
        try {
            List<CatalogModel> catalogList = XiuCatalogTreeCache.getInstance().getTree();
            
            List<CatalogModel> xiuCatalogList = removeEbayCatalog(catalogList);
            
            this.sortBySortNum(xiuCatalogList);

            List<CatalogBo> catalogboList = this.createCatalogBoList(xiuCatalogList);
            
            model.addAttribute("params",catalogboList);
            
            LOGGER.warn("AllCatalogController - 获取全部官网运营分类, 耗时: " + (System.currentTimeMillis() - time)/1000 + " ms." );
        } catch (Exception e) {
            LOGGER.error("AllCatalogController - 获取全部官网运营分类 失败, 耗时: " + (System.currentTimeMillis() - time)/1000 + " ms.  " + e );
        }
        
        return "all-catalog";
    }
    
    
    
    
    /**
     * 将CatalogModel封装成CatalogBo，用于页面展示
     * @param list
     * @return
     */
    private List<CatalogBo> createCatalogBoList(List<CatalogModel> list) {

        if(null == list || list.size() <= 0) {
            return null;
        }
        
        Map<Integer, CatalogBo> map = new HashMap<Integer, CatalogBo>(list.size());

        CatalogBo tmp = null;
        for (CatalogModel e : list) {
            tmp = new CatalogBo();
            tmp.setCatalogModel(e);
            map.put(e.getCatalogId(), tmp);
        }
        
        List<CatalogBo> catalogBoList = new ArrayList<CatalogBo>(); 
        
        for (CatalogModel e : list) {
            if (e.getParentCatalogId() == 0) {
                catalogBoList.add(map.get(e.getCatalogId()));
            } else {
                tmp = map.get(e.getParentCatalogId());
                if (null != tmp) {
                    tmp.addChildCatalog(map.get(e.getCatalogId()));
                }
            }
        }
        tmp = null;
        map = null;
        return catalogBoList;
    }
    
    
    
    
    /**
     * 按sortnum排序
     * 使用了选择排序法
     * @param list
     */
    private void sortBySortNum(List<CatalogModel> list) {
        
        if(null == list || list.size() <= 0) {
            return;
        }
        
        int minIndex = 0;
        CatalogModel temp = null;
        for(int i=0; i<list.size(); i++){
            minIndex = i;                        //无序区的最小数据数组下标
            
            for(int j=i+1; j<list.size(); j++){    //在无序区中找到最小数据并保存其数组下标
                if (list.get(j).getSortNum() < list.get(minIndex).getSortNum()) {
                    minIndex = j;
                }
            }
            if(minIndex != i){                    //如果不是无序区的最小值位置不是默认的第一个数据，则交换之。
                temp = list.get(i);
                list.set(i, list.get(minIndex));
                list.set(minIndex, temp);
            }
        }
    }
    
    
    
    /**
     * 移除list中的ebay分类,只保留官网
     * @param list
     * @return
     */
    private List<CatalogModel> removeEbayCatalog(List<CatalogModel> list) {
     
        List<CatalogModel> xiuCatalogModel = new ArrayList<CatalogModel>(list.size());
        
        for (CatalogModel e : list) {
            if (Constants.XIUINDEX_MKTTYPE_XIU == e.getMktType()) {
                xiuCatalogModel.add(e);
            }
        }
        
        return xiuCatalogModel;
    }
    
    
//    /**
//     * 复制一个新的CatalogModelList
//     * 使排序等操作不影响原来的list
//     * @param list
//     * @return
//     */
//    private List<CatalogModel> copyCatalogModelList(List<CatalogModel> list) { 
//
//        if(null == list || list.size() <= 0) {
//            return null;
//        }
//        
//        List<CatalogModel> copyList = new ArrayList<CatalogModel>(list.size());
//        CatalogModel tmp = null;
//        for (CatalogModel e : list) {
//            tmp = new CatalogModel(e.getCatalogId(), e.getCatalogName(), e.getParentCatalogId(), e.getSortNum(), e.getMktType());
//            if (null != e.getChildIdList() && e.getChildIdList().size() > 0) {
//                for (Integer cid : e.getChildIdList()) {
//                    tmp.addChildId(cid);
//                }
//            }
//            copyList.add(tmp);
//        }
//        return copyList;
//    }
    
}
