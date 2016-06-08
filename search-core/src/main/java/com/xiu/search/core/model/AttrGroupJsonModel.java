package com.xiu.search.core.model;

public class AttrGroupJsonModel {

	public enum AttrGroupTypeEnum{
		/**
		 * 扩展属性
		 */
		ATTR(0),
		/**
		 * 品牌
		 */
		BRAND(1);
		private int type;
		private AttrGroupTypeEnum(int type){
			this.type = type;
		}
		
		public int type(){
			return this.type;
		}
		
		public static AttrGroupTypeEnum valueof(int type){
			for (AttrGroupTypeEnum e : AttrGroupTypeEnum.values()) {
				if(e.type==type)
					return e;
			}
			return null;
		}
		
		public static AttrGroupTypeEnum valueof(String name){
			try {
				return AttrGroupTypeEnum.valueOf(name);
			} catch (Exception e) {
				// TODO: ignore this exception
			}
			return null;
		}
		
	}
	
	
	/**
	 * 属性项ID
	 */
	private String id;
	/**
	 * 是否展示
	 */
	private boolean displayFlag;
	/**
	 * 类型
	 */
	private int type;
	/**
	 * 属性项名称
	 */
	private String name;
	/**
	 * 需要展示的属性组ID
	 */
	private String[] attrGroupValueIds;
	/**
	 * 排序顺序
	 */
	private int order;
//---------------新版本新增逻辑项	William.zhang	20130507-------------
	/**
	 * 属性项别名，无别名为空
	 */
	private String aliasName;
	/**
	 * //属性项是否全显示，表示这个属性项下属性值组是否全部在前端参与筛选,1或者空
	 */
	private boolean isAll;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String[] getAttrGroupValueIds() {
		return attrGroupValueIds;
	}
	public void setAttrGroupValueIds(String[] attrGroupValueIds) {
		this.attrGroupValueIds = attrGroupValueIds;
	}
	public boolean isDisplayFlag() {
		return displayFlag;
	}
	public void setDisplayFlag(boolean displayFlag) {
		this.displayFlag = displayFlag;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getAliasName() {
		return aliasName;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	public boolean isAll() {
		return isAll;
	}
	public void setAll(boolean isAll) {
		this.isAll = isAll;
	}
}
