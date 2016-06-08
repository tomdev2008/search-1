package com.xiu.search.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xiu.search.core.service.DeliverTypeService;
import com.xiu.search.dao.DeliverTypeDAO;
import com.xiu.search.dao.model.XPurchBaseBusinessModel;

public class DeliverTypeServiceImpl extends DeliverTypeService {
	
	private static Map<Integer, List<XPurchBaseBusinessModel>> DELIVER_TYPES = new HashMap();
	/**
	 * 国内发货
	 */
	private final static Integer GNFH = 2;
	/**
	 * 海外发货
	 */
	private final static Integer HWFH = 3;
	
	private DeliverTypeDAO deliverTypeDAO;
	public void setDeliverTypeDAO(DeliverTypeDAO deliverTypeDAO) {
		this.deliverTypeDAO = deliverTypeDAO;
	}

	
	@Override
	protected void init() {
		reloadAllDeliverTypes();
	}
	
	private void reloadAllDeliverTypes(){
		List<XPurchBaseBusinessModel> spaceList = deliverTypeDAO.getDeliverTypeFromChina();
		DELIVER_TYPES.put(GNFH, spaceList);
		spaceList.clear();
		spaceList = deliverTypeDAO.getDeliverTypeFromOverSea();
		DELIVER_TYPES.put(HWFH, spaceList);
		
	}

	@Override
	public  List<XPurchBaseBusinessModel> getDeliverType( Integer channel ){
		List<XPurchBaseBusinessModel> spaceList =  DELIVER_TYPES.get( channel );
		if ( null == spaceList || spaceList.isEmpty() ){
			if ( GNFH.intValue() == channel.intValue() ){
				spaceList = deliverTypeDAO.getDeliverTypeFromChina();
				DELIVER_TYPES.put(GNFH, spaceList);
			}else if ( HWFH.intValue() == channel.intValue() ){
				spaceList = deliverTypeDAO.getDeliverTypeFromOverSea();
				DELIVER_TYPES.put(HWFH, spaceList);
			} 
		}
		return spaceList;
	}

}
