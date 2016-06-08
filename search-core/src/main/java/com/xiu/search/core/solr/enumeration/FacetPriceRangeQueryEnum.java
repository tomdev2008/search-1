package com.xiu.search.core.solr.enumeration;

import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermRangeQuery;

import com.xiu.search.core.solr.index.GoodsIndexFieldEnum;

/**
 * 价格区间筛选的枚举类
 * @author Leon
 *
 */
public enum FacetPriceRangeQueryEnum {

	/**
	 * 0-500元
	 */
	STEP_1(1,"0-500元", 0d, 500d), 
	
	/**
	 * 500-1000元
	 */
	STEP_2(2,"500-1000元", 500.01d, 1000d), 
	
	/**
	 * 1000-3000元
	 */
	STEP_3(3,"1000-3000元", 1000.01d, 3000d), 
	
	/**
	 * 3000-5000元
	 */
	STEP_4(4,"3000-5000元", 3000.01d, 5000d), 
	
	/**
	 * 5000-8000元
	 */
	STEP_5(5,"5000-8000元", 5000.01d, 8000d), 
	
	/**
	 * 8000元以上
	 */
	STEP_6(6,"8000元以上", 8000.01d, Double.MAX_VALUE)
	;

	/**
	 * 用于初始化范围与搜索query对应MAP
	 */
	private final static Map<FacetPriceRangeQueryEnum,Query> priceRangeQueryMap = new HashMap<FacetPriceRangeQueryEnum, Query>();
	
	static{
		for (FacetPriceRangeQueryEnum e : FacetPriceRangeQueryEnum.values()) {
			priceRangeQueryMap.put(e, e.getTermRangeQuery());
		}
	}
	
	private int order;
	private String display;
	private double startPrice;
	private double endPrice;

	private FacetPriceRangeQueryEnum(int order,String display, double startPrice,
			double endPrice) {
		this.order=order;
		this.display = display;
		this.startPrice = startPrice;
		this.endPrice = endPrice;
	}

	/**
	 * 根据页面传递的price order获得价格分组结果
	 * @param order
	 * @return
	 */
	public static FacetPriceRangeQueryEnum valueOf(int order){
		for (FacetPriceRangeQueryEnum e : FacetPriceRangeQueryEnum.values()) {
			if(e.getOrder() == order)
				return e;
		}
		return null;
	}
	
	
	/**
	 * 获得页面展示的顺序
	 * @return
	 */
	public int getOrder(){
		return this.order;
	}
	
	/**
	 * 获取显示内容
	 * @return
	 */
	public String getDisplay() {
		return display;
	}

	/**
	 * 获取起始价格
	 * @return
	 */
	public double getStartPrice() {
		return startPrice;
	}

	/**
	 * 获取结束价格
	 * @return
	 */
	public double getEndPrice() {
		return endPrice;
	}
	
	/**
	 * 获取范围查询query
	 * 用于文本类型的数据格式
	 * @return
	 */
	public TermRangeQuery getTermRangeQuery(){
		if(priceRangeQueryMap.containsKey(this))
			return (TermRangeQuery)priceRangeQueryMap.get(this);
		if(Double.MAX_VALUE != endPrice){
			return new TermRangeQuery(GoodsIndexFieldEnum.PRICE_FINAL.fieldName(), String.valueOf(startPrice), String.valueOf(endPrice), true, true);
		}else{
			return new TermRangeQuery(GoodsIndexFieldEnum.PRICE_FINAL.fieldName(), String.valueOf(startPrice), "*", true, true);
		}
	}
	/**
	 * 获取范围查询query
	 * 用于double tire类型的数据格式
	 * @return
	 */
	private NumericRangeQuery getNumericRangeQuery(){
		if(priceRangeQueryMap.containsKey(this))
			return (NumericRangeQuery)priceRangeQueryMap.get(this);
		return NumericRangeQuery.newDoubleRange(GoodsIndexFieldEnum.PRICE_FINAL.fieldName(), startPrice, endPrice, true, true);
	}
	
}
