package com.xiu.search.core.solr.index;

public enum OclassIndexFieldEnum {

    ID("id"), 
    
    PATH("path"), 
    
    NAME("name"), 
    
    QUERY("query"), 
    
    ITEMID("itemid"), 
    
    PARENTID("parentid"), 
    
    LEVEL("level"), 
    
    ITEMCOUNT("itemcount"), 
    
    MKTTYPE("mktType"), 
    
    LASTUPDATEDATE("lastupdatedate"), 
    
    SORTNUM("sortnum"),
    
    
	/**
	 * 默认搜索字段
	 */
	DEFAULT_SEARCH("searchable"),
	
	/**
	 * lucene得分
	 */
	SCORE("score");
	
	
	private String fieldName;
	
	OclassIndexFieldEnum(String fieldName){
		this.fieldName = fieldName;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	
}
