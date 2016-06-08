package com.xiu.search.dao;

import java.util.List;

import com.xiu.search.dao.model.XDataBrand;

public interface XDataBrandDAO {
	
	XDataBrand selectByPrimaryKey(Long brandId);
	
	String selectByPrimaryKeyForMainName(Long brandId);
	
	List<XDataBrand> selectAllBrandInfo();
	
	/**
	 * @Description: 加载所有品牌数据（show_flag=1 and 品牌下挂的有商品）
	 * @return    
	 * @return List<XDataBrand>
	 */
	List<XDataBrand> selectAllByShowFlag1AndHasGoods();
}