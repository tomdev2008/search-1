package com.xiu.search.core.solr.enumeration;

public enum MktTypeEnum {

	/**
	 * 全部商品
	 */
	ALL(100),
	/**
	 * 走秀商品
	 */
	XIU(0),
	/**
	 * ebay商品
	 */
	EBAY(1)
	;
	/**
	 * 数字类型
	 */
	private int type;
	
	private MktTypeEnum(int order) {
		this.type = order;
	}
	
	public int getType(){
		return this.type;
	}
	
	public static MktTypeEnum valueof(String name){
		try {
			return MktTypeEnum.valueOf(name);
		} catch (Exception e) {
			// TODO: ignore this exception
			return null;
		}
	}
	
	public static MktTypeEnum valueof(int type){
		MktTypeEnum[] array = MktTypeEnum.values();
		for (int i = 0,len=array.length; i < len; i++) {
			if(array[i].type == type)
				return array[i];
		}
		return null;
	}
}
