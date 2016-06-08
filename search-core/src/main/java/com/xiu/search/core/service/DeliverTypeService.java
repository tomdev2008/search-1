package com.xiu.search.core.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.xiu.search.core.catalog.BrandModel;
import com.xiu.search.core.catalog.XiuBrandInfoCache;
import com.xiu.search.core.catalog.XiuBrandInfoCacheImpl;
import com.xiu.search.dao.model.XPurchBaseBusinessModel;
import com.xiu.search.core.service.impl.DeliverTypeServiceImpl;

@Service("deliverTypeService")
public abstract class DeliverTypeService {
	private static DeliverTypeService instance;
	
	public static DeliverTypeService getInstance(){
		if(instance == null)
			instance = new DeliverTypeServiceImpl();
		return instance;
	}
	
	public static void reload(){
		instance.init();
	}
	
	
	protected abstract void init();
	
	public abstract List<XPurchBaseBusinessModel> getDeliverType( Integer channel );

}
