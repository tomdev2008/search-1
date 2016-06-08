package com.xiu.search.core.solr.enumeration;

public enum ItemShowTypeEnum {
	
	/**
	 * 海外直发
	 */
	HWZF(3),
	
	/**
	 * 奢侈品
	 */
	SCP(1),
	
	/**
	 * 品牌街
	 */
	PPJ(2),
	
	/**
	 * display=1,2
	 */
	DSP12(12);
	
	private int type;
	
	private ItemShowTypeEnum(int order) {
		this.type = order;
	}
	
	public static ItemShowTypeEnum valueof(int type){
		ItemShowTypeEnum[] array = ItemShowTypeEnum.values();
		for (int i = 0,len=array.length; i < len; i++) {
			if(array[i].type == type)
				return array[i];
		}
		return null;
	}
	
	public int getType(){
		return this.type;
	}
}
