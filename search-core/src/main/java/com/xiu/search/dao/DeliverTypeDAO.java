package com.xiu.search.dao;

import java.util.List;
import java.util.Map;

import com.xiu.search.dao.model.XPurchBaseBusinessModel;

public interface DeliverTypeDAO {
	/**
	 * @Description: 国内发货
	 * @return    
	 * @return List<XPurchBaseBusinessModel>
	 */
	public List<XPurchBaseBusinessModel> getDeliverTypeFromChina( );

	/**
	 * @Description: 海外发货
	 * @return    
	 * @return List<XPurchBaseBusinessModel>
	 */
	public List<XPurchBaseBusinessModel> getDeliverTypeFromOverSea( );
}
