package com.xiu.search.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.xiu.search.dao.model.XPurchBaseBusinessModel;
import com.xiu.search.dao.DeliverTypeDAO;

public class DeliverTypeDAOImpl  extends SqlSessionDaoSupport implements DeliverTypeDAO{

	@Override
	public List<XPurchBaseBusinessModel> getDeliverTypeFromChina( ){
		List<XPurchBaseBusinessModel> xPurchBaseList=  getSqlSession().selectList( "X_PURCH_BASE_BUSINESS_MODEL.deliverTypeFromChina" );
		return xPurchBaseList;
	}
	
	@Override
	public List<XPurchBaseBusinessModel> getDeliverTypeFromOverSea( ){
		List<XPurchBaseBusinessModel> xPurchBaseList= getSqlSession().selectList("X_PURCH_BASE_BUSINESS_MODEL.deliverTypeFromOverSea" );
		return xPurchBaseList ;
	}
	
}
