package com.xiu.search.core.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.xiu.search.core.catalog.CatalogModel;

/**
 * 运营分类树
 * 
 * @author Leon
 *
 */
public class CatalogBo implements Serializable,Cloneable {

	private CatalogModel catalogModel;
//	// 分类ID
//	private int catalogId;
//	// 分类名称
//	private String catalogName;
//	// 层级
//	private int level;
//	// 商品数量
	private long itemCount;
//	// 父分类ID
//	private int parentCatalogId;
	// 完整路径
//	private String path;
//	// 分类对应的查询索引query
//	private String indexQueryStr;
	// 子分类
	private List<CatalogBo> childCatalog;
	// 排序顺序
//	private int sortNum;
	// 是否选中
	private boolean selected;
	//父节点
//	private CatalogBo parentCatalog;
	
//	private int mktType;
	
	/**
	 * 增加子分类
	 * @param catalogBo
	 */
	public void addChildCatalog(CatalogBo catalogBo){
		if(null == this.childCatalog)
			this.childCatalog = new ArrayList<CatalogBo>();
		this.childCatalog.add(catalogBo);
	}
	
	/**
	 * 清空所有的子分类
	 */
	public void cleanChildCatalog(){
		this.childCatalog = null;
	}
	
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public int getCatalogId() {
		return catalogModel.getCatalogId();
	}
	public String getCatalogName() {
		return catalogModel.getCatalogName();
	}	
	
	public String getDisplay(){
		return catalogModel.getDisplay();
	}

	public List<CatalogBo> getChildCatalog() {
		return childCatalog;
	}
	public void setChildCatalog(List<CatalogBo> childCatalog) {
		this.childCatalog = childCatalog;
	}
	public int getMktType() {
		return catalogModel.getMktType();
	}
	public CatalogModel getCatalogModel() {
		return catalogModel;
	}

	public void setCatalogModel(CatalogModel catalogModel) {
		this.catalogModel = catalogModel;
	}
	
	public long getItemCount() {
		return itemCount;
	}

	public void setItemCount(long itemCount) {
		this.itemCount = itemCount;
	}

	@Override
	public CatalogBo clone(){
		CatalogBo cloneObj = null;
		 try {
			cloneObj =(CatalogBo) super.clone();
		} catch (CloneNotSupportedException e1) {
			e1.printStackTrace();
			return this;
		}
		return cloneObj;
	}
	
}
