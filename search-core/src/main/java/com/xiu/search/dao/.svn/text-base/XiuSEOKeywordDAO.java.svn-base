package com.xiu.search.dao;

import java.util.List;

import com.xiu.search.dao.model.XiuSeoKeywordModel;

/**
 * 
 * com.xiu.search.dao.XiuSEOKeywordDAO.java

 * @Description: TODO SEO关键字的DAO接口

 * @author lvshuding   

 * @date 2013-10-28 下午2:51:01

 * @version V1.0
 */
public interface XiuSEOKeywordDAO {
	
	/**
	 * 根据主键加载对象
	 * @param id
	 * @return
	 */
	public XiuSeoKeywordModel selectById(int id);
	
	/**
	 * 分页查询
	 * @param firstRow
	 * @param endRow
	 * @return
	 */
	public List<XiuSeoKeywordModel> selectPageList(int firstRow,int endRow);
	
	/**
	 * 返回总记录数
	 * @return
	 */
	public int selectTotCount();
	
}