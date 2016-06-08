package com.xiu.search.core.catalog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.params.SolrParams;

import com.xiu.search.core.config.XiuSearchProperty;
import com.xiu.search.core.solr.enumeration.ItemShowTypeEnum;
import com.xiu.search.core.solr.model.GoodsSolrModel;
import com.xiu.search.core.util.Constants;
import com.xiu.search.dao.XiuCatalogDAO;
import com.xiu.search.dao.model.XiuCatalogDBModel;
import com.xiu.search.solrj.service.SolrHttpService;

/**
 * 
 * com.xiu.search.core.catalog.XiuCatalogTreeCacheImpl.java

 * @Description: TODO SearchWeb 对应的运营分类树相关操作  具体实现类

 * @author lvshuding   

 * @date 2013-6-27 下午12:16:14

 * @version V1.0
 */
public class XiuCatalogTreeCacheImpl extends XiuCatalogTreeCache {
	
	private final static Logger log = Logger.getLogger(XiuCatalogTreeCacheImpl.class);

	private volatile boolean isRunning;
	
	//官网,display=1
	private volatile Map<String, CatalogModel> treeMap = null;
	
	//官网,display=1,2
	private volatile Map<String, CatalogModel> treeMap_0 = null;
	
	//官网所有一级分类 ，display=1
	private volatile List<CatalogModel> firstCataList=null;
	
	//奢侈品
	private volatile Map<String, CatalogModel> treeMap1 = null;
	
	//品牌街
	private volatile Map<String, CatalogModel> treeMap2 = null;
	
	//海外直发
	private volatile Map<String, CatalogModel> treeMap3 = null;
	
	private XiuCatalogDAO xiuCatalogDAO;
	
	@Override
	protected void init() {
		this.reload();
		this.reloadTimer();
	}

	@Override
	protected void reload() {
		if(isRunning){
			return;
		}
		log.info("【XiuCatalogTreeCacheImpl】 开始从数据库中读取运营分类数据: ");
		
		// 1. 查询数据库中运营分类数据
		List<XiuCatalogDBModel> cList = this.getCatalogFromDB();
		if(cList==null || cList.size()==0){
			isRunning = false;
			return;
		}
		
		// 2.1 构建官网树
		Map<String, CatalogModel> cMap=this.getCatalogBosByDb(cList,null);
		if(cMap==null || cMap.size()==0){
			log.info("【XiuCatalogTreeCacheImpl】官网树  中不包含display=1的运营分类");
			isRunning = false;
			return;
		}else{
			treeMap = cMap;
		}
		// 2.2 构建官网树 display=1,2
		cMap=this.getCatalogBosByDb(cList,null,true);
		if(cMap==null || cMap.size()==0){
			log.info("【XiuCatalogTreeCacheImpl】官网树  中不包含display=1,2的运营分类");
			isRunning = false;
			return;
		}else{
			treeMap_0 = cMap;
		}
		
		// 3. 构造奢侈品树
		cMap=this.getCatalogBosByDb(cList,String.valueOf(ItemShowTypeEnum.SCP.getType()));
		if(cMap==null || cMap.size()==0){
			log.info("【XiuCatalogTreeCacheImpl】侈品树  中不包含运营分类 ");
		}else{
			treeMap1 = cMap;
		}
		
		// 4. 构造品牌街树
		cMap=this.getCatalogBosByDb(cList,String.valueOf(ItemShowTypeEnum.PPJ.getType()));
		if(cMap==null || cMap.size()==0){
			log.info("【XiuCatalogTreeCacheImpl】 品牌街树 中不包含运营分类");
		}else{
			treeMap2 = cMap;
		}
		
		// 5. 构海外直发树
		cMap=this.getCatalogBosByDb(cList,String.valueOf(ItemShowTypeEnum.HWZF.getType()));
		if(cMap==null || cMap.size()==0){
			log.info("【XiuCatalogTreeCacheImpl】外直发树  中不包含运营分类 ");
		}else{
			treeMap3 = cMap;
		}
		
		// 6.构造所有一级分类列表
		List<CatalogModel> tmpList=this.buildFirstCataList(cList);
		if(tmpList==null || tmpList.size()==0){
			log.info("【XiuCatalogTreeCacheImpl】官网树  中不包含有效的一级运营分类");
			isRunning = false;
		}else{
			firstCataList = tmpList;
		}
		
		log.info("【XiuCatalogTreeCacheImpl】 从数据库中读取运营分类数据完成，共有["+cList.size()+"]条数据");
		// 清空临时变量
		cList.clear();
		cList=null;
		cMap=null;
		isRunning =false;
	}

	
	//返回到外部的对象，用克隆的，要不都是内存中同一个，具体克隆方法是用序列化还是用创建新对象或是clone()，明天比对效率之后再决定。
	@Override
	public List<CatalogModel> getTree() {
		Map<String, CatalogModel> map = treeMap;
		if(map==null || map.size()==0){
			return null;
		}
		List<CatalogModel> retList=new ArrayList<CatalogModel>(map.size());
		Iterator<CatalogModel> itr=map.values().iterator();
		while (itr.hasNext()) {
			retList.add(itr.next());
		}
		itr=null;
		map = null;
		return retList;
	}
	
	@Override
	public List<CatalogModel> getTree12() {
		Map<String, CatalogModel> map = treeMap_0;
		if(map==null || map.size()==0){
			return null;
		}
		List<CatalogModel> retList=new ArrayList<CatalogModel>(map.size());
		Iterator<CatalogModel> itr=map.values().iterator();
		while (itr.hasNext()) {
			retList.add(itr.next());
		}
		itr=null;
		map = null;
		return retList;
	}
	
	
	
	@Override
	public List<CatalogModel> getTree(ItemShowTypeEnum itemShoTypeEnum) {
		if(itemShoTypeEnum==null){
			return this.getTree();
		}
		List<CatalogModel> cbList=null;
		Map<String, CatalogModel> map = null;
		switch (itemShoTypeEnum) {
		case SCP:
			map = treeMap1;
			break;
		case PPJ:
			map = treeMap2;
			break;
		case HWZF:
			map = treeMap3;
			break;
		}
		cbList=this.retListByMap(map);
		map = null;
		return cbList;
	}

	@Override
	public CatalogModel getTreeNodeById(String catalogID) {
		Map<String, CatalogModel> map = treeMap;
		if(map==null || map.size()==0){
			return null;
		}
		CatalogModel cm=map.get(catalogID);
		map = null;
		return cm;
	}
	
	@Override
	public CatalogModel getTreeNodeById12(String catalogID) {
		Map<String, CatalogModel> map = treeMap_0;
		if(map==null || map.size()==0){
			return null;
		}
		CatalogModel cm=map.get(catalogID);
		map = null;
		return cm;
	}
	
	@Override
	public CatalogModel getTreeNodeByIdFromTreeMap0(String catalogID) {
		Map<String, CatalogModel> map = treeMap_0;
		if(map==null || map.size()==0){
			return null;
		}
		CatalogModel cm=map.get(catalogID);
		map = null;
		return cm;
	}
	
	
	@Override
	public CatalogModel getTreeNodeById(String catalogId,
			ItemShowTypeEnum itemShoTypeEnum) {
		if(itemShoTypeEnum==null){
			return this.getTreeNodeById(catalogId);
		}
		CatalogModel cb=null;
		Map<String, CatalogModel> map = null;
		switch (itemShoTypeEnum) {
		case SCP:
			map = treeMap1;
			break;
		case PPJ:
			map = treeMap2;
			break;
		case HWZF:
			map = treeMap3;
			break;
		case DSP12:
			map = treeMap_0;
			break;	
		}
		cb = this.retBoByMap(catalogId, map);
		map = null;
		return cb;
	}
	
	
	@Override
	public List<CatalogModel> getAllFirstCataList() {
		return firstCataList;
	}

	/**
	 * 从指定的Map中获取对象
	 * @param catalogId
	 * @param tMap
	 * @return
	 */
	private CatalogModel retBoByMap(String catalogId,Map<String, CatalogModel> tMap){
		if(tMap==null || tMap.size()==0){
			return null;
		}
		CatalogModel cm=tMap.get(catalogId);
		tMap = null;
		return cm;
	}
	
	/**
	 * 从指定的Map中获取对象列表
	 * @param catalogId
	 * @param tMap
	 * @return
	 */
	private List<CatalogModel> retListByMap(Map<String, CatalogModel> tMap){
		if(tMap==null || tMap.size()==0){
			return null;
		}
		List<CatalogModel> retList=new ArrayList<CatalogModel>(tMap.size());
		Iterator<CatalogModel> itr = tMap.values().iterator();
		while (itr.hasNext()) {
			retList.add(itr.next());
		}
		itr=null;
		tMap = null;
		return retList;
	}


	/**
	 * 从数据库中加载所有分类信息
	 * @return
	 */
	private List<XiuCatalogDBModel> getCatalogFromDB(){
		List<XiuCatalogDBModel> xcList = null;
		try{
			xcList = xiuCatalogDAO.selectAllLevel3CatalogListByDB();
			return xcList;
		}catch (Exception e) {
			log.error("【XiuCatalogTreeCacheImpl】 从数据库中读取运营分类数据出错: ",e);
			return null;
		}
	}
	
	/**
	 * 生成Tree，只包含display=1
	 */
	private Map<String, CatalogModel> getCatalogBosByDb(List<XiuCatalogDBModel> xcList,String itemShowType) {
		return this.getCatalogBosByDb(xcList,itemShowType,false);
	}
	/**
	 * 生成Tree
	 */
	private Map<String, CatalogModel> getCatalogBosByDb(List<XiuCatalogDBModel> xcList,String itemShowType,boolean include2) {
		
		Map<String, CatalogModel> classMap = new HashMap<String, CatalogModel>();
		CatalogModel o = null, op1 = null, op2 = null;
         //对应分类id 的数量
		Map<Integer, Long> retMap = this.queryCounts(itemShowType);
		
		int mktType = 0;
		for (XiuCatalogDBModel xcb : xcList) {
			if(!include2 && Constants.CATALOG_DISPLAY_HIDDEN.equals(xcb.getDisplay1())){
				continue;
			}
			if (Constants.PROVIDER_CODE_EBAY.equals(xcb.getCatProviderCode())) {
				mktType = Constants.XIUINDEX_MKTTYPE_EBAY;
			} else {
				mktType = Constants.XIUINDEX_MKTTYPE_XIU;
			}
			// 一级分类
			op1 = classMap.get(String.valueOf(xcb.getParCatId1()));
			if (op1 == null) {
				op1 = this.buildModel(xcb.getParCatId1(), xcb.getParCatName1(),0, xcb.getParCatRank1(), mktType, this.getItemCount(retMap, xcb.getParCatId1()),xcb.getDisplay1());
				if (op1 == null) {
					continue;
				}
				classMap.put(String.valueOf(xcb.getParCatId1()), op1);//100000065 CatalogModel1
			}
			// 二级分类
			if(!include2 && Constants.CATALOG_DISPLAY_HIDDEN.equals(xcb.getDisplay2())){
				continue;
			}
			op2 = classMap.get(String.valueOf(xcb.getParCatId2()));
			if (op2 == null) {
				op2 = this.buildModel(xcb.getParCatId2(), xcb.getParCatName2(),xcb.getParCatId1(), xcb.getParCatRank2(), mktType, this.getItemCount(retMap, xcb.getParCatId2()),xcb.getDisplay2());
				if (op2 == null) {
					continue;
				}
				classMap.put(String.valueOf(xcb.getParCatId2()), op2);//100003453 CatalogModel2
			}
			// 把二级分类加到一级分类节点的childList中
			this.addChild(op2.getCatalogId(), op1);//CatalogModel1 addChild

			// 三级分类
			if(!include2 && Constants.CATALOG_DISPLAY_HIDDEN.equals(xcb.getDisplay3())){
				continue;
			}
			o = this.buildModel(xcb.getCatId(), xcb.getCatName(),xcb.getParCatId2(), xcb.getCatRank(), mktType, this.getItemCount(retMap, xcb.getCatId()),xcb.getDisplay3());
			if (o == null) {
				continue;
			}
			classMap.put(String.valueOf(o.getCatalogId()), o);//100003454 CatalogModel3
			op2.addChildId(o.getCatalogId());//CatalogModel2 
		}
		retMap.clear();
		retMap=null;
		
		return classMap;
	}
	
	private long getItemCount(Map<Integer, Long> iMap, Integer key) {
		Long ic = iMap.get(key);
		if (ic == null) {
			return 0;
		}
		return ic.longValue();
	}
	
	/**
	 * 构造所有一级分类
	 */
	private List<CatalogModel> buildFirstCataList(List<XiuCatalogDBModel> xcList) {
		
		Map<String, CatalogModel> classMap = new HashMap<String, CatalogModel>();
		CatalogModel op1 = null;

		Map<Integer, Long> retMap = this.queryCounts(null);
		
		int mktType = 0;

		for (XiuCatalogDBModel xcb : xcList) {
			if(Constants.CATALOG_DISPLAY_HIDDEN.equals(xcb.getDisplay1())){
				continue;
			}
			if (Constants.PROVIDER_CODE_EBAY.equals(xcb.getCatProviderCode())) {
				mktType = Constants.XIUINDEX_MKTTYPE_EBAY;
			} else {
				mktType = Constants.XIUINDEX_MKTTYPE_XIU;
			}

			// 一级分类
			op1 = classMap.get(String.valueOf(xcb.getParCatId1()));
			if (op1 == null) {
				op1 = this.buildModel(xcb.getParCatId1(), xcb.getParCatName1(),0, xcb.getParCatRank1(), mktType, this.getItemCount(retMap, xcb.getParCatId1()),xcb.getDisplay1());
				if (op1 == null) {
					continue;
				}
				classMap.put(String.valueOf(xcb.getParCatId1()), op1);
			}
		}
		return new ArrayList<CatalogModel>(classMap.values());
	}

	/**
	 * 搜索各运营分类对应的商品数量
	 * itemShowTypeValue: 商品类别，1 奢侈品，2 国内，3 国外。属于多个类别则使用,连接
	 * @return
	 */
	private Map<Integer, Long> queryCounts(String itemShowTypeValue) {
		Map<Integer, Long> retMap = new HashMap<Integer, Long>();

		// String solrUrl = "http://10.0.0.161:3736/solr2/xiu";
		CommonsHttpSolrServer solrServer = null;
		try {
			solrServer = SolrHttpService.getInstance().getSolrServer(GoodsSolrModel.class);
			Map<String, String[]> prms = new HashMap<String, String[]>();
			if(itemShowTypeValue==null){
				prms.put("q", new String[] { "*:*" });
			}else{
				prms.put("q", new String[] { "itemShowType:"+itemShowTypeValue });
			}
			
			prms.put("facet", new String[] { "true" });
			prms.put("facet.field", new String[] { "oclassIds" });
			prms.put("rows", new String[] { "0" });
			prms.put("facet.limit", new String[] { "1000000" });

			SolrParams sp = new ModifiableSolrParams(prms);
			QueryResponse response = solrServer.query(sp);

			List<FacetField> fList = response.getFacetFields();
			if (fList == null) {
				return retMap;
			}
			List<Count> cList = null;
			for (FacetField ff : fList) {
				cList = ff.getValues();
				if (cList == null || cList.size() == 0) {
					continue;
				}
				for (Count c : cList) {
					retMap.put(Integer.parseInt(c.getName()), c.getCount());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return retMap;
		}finally{
			solrServer=null;
		}
		return retMap;
	}

	/**
	 * 创建节点
	 * 
	 * @param id
	 * @param name
	 * @param parentId
	 * @param rank
	 * @param mktType
	 * @param itemCount
	 * @param display
	 * @return
	 */
	private CatalogModel buildModel(int id, String name,int parentId, Integer rank, int mktType, long itemCount,String display) {

		if(itemCount<1){
			return null;
		}
		CatalogModel os = new CatalogModel(id,name,parentId,rank!=null?rank.intValue():0,mktType,display);
		return os;
	}

	/**
	 * 把子节点加入父节点
	 * 
	 * @param sonId
	 * @param parent
	 */
	private void addChild(Integer sonId, CatalogModel parent) {
		boolean contains = false;
		List<Integer> sList = parent.getChildIdList();
		if (sList == null || sList.size() == 0) {
			parent.addChildId(sonId);
		} else {
			for (Integer os : sList) {
				if (os!=null && os.intValue() == sonId.intValue()) {
					contains = true;
					break;
				}
			}
			if (!contains) {
				parent.addChildId(sonId);
			}
		}
	}
	
	/**
	 * 定时器
	 */
	private void reloadTimer(){
		Timer t=new Timer(true);
		TimerTask tk=new TimerTask() {
			@Override
			public void run() {
				//以防止意外终止timer
				try {
					reload();
				} catch (Exception e) {
					log.error("reload the catalog tree ocurrs error ----->",e);
				}
			}
		};
		//N分钟之后 ，每隔N分钟执行一次10分钟
		t.scheduleAtFixedRate(tk, XiuSearchProperty.getInstance().getCatalogRefreshfrequency(), XiuSearchProperty.getInstance().getCatalogRefreshfrequency());
	}
	
	public void setXiuCatalogDAO(XiuCatalogDAO xiuCatalogDAO) {
		this.xiuCatalogDAO = xiuCatalogDAO;
	}
	
}
