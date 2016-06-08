package com.xiu.search.core.solr.enumeration;

/**
 * @author yuechu.chen@xiu.com
 * @Date 2014-11-12
 * @description 
 */
public enum DeliverTypeEnum {
	/**
	 * 国内发货
	 */
	GNFH(2,new String[]{
			DeliverType.GNZF.code,
			DeliverType.GNC.code,
			DeliverType.GNAB.code
			}),
	/**
	 * 海外发货
	 */
	HWFH(3,new String[]{
			DeliverType.GMARKET.code,
			DeliverType.HGZF.code,
			DeliverType.RBZF.code,
			DeliverType.XGZF.code,
			DeliverType.EBAYMG.code,
			DeliverType.EBAYYG.code,
			DeliverType.HGC.code,
			DeliverType.MGHUB.code,
			DeliverType.MGHK.code,
			DeliverType.XGC.code,
			DeliverType.OZ.code,
			DeliverType.YDL.code,
			DeliverType.YG.code
	}),
	
	/**
	 * 香港和欧洲发货
	 */
	HK_UN(4,new String[]{
			DeliverType.XGZF.code,
			DeliverType.XGC.code,
			DeliverType.OZ.code,
			DeliverType.YDL.code,
			DeliverType.YG.code
	});
	private int key;
	private String[] deliverPlaceCodes;
	private DeliverTypeEnum(int key,String[] deliverPlaceCodes){
		this.key = key;
		this.deliverPlaceCodes = deliverPlaceCodes;
	}
	public static DeliverTypeEnum getDeliverType(int key){
		switch (key) {
		case 2:
			
			return GNFH;
		case 3:
			
			return HWFH;
		case 4:
			return HK_UN;
		default:
			return null;
		}
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public String[] getDeliverPlaceCodes() {
		return deliverPlaceCodes;
	}
	public void setDeliverPlaceCodes(String[] deliverPlaceCodes) {
		this.deliverPlaceCodes = deliverPlaceCodes;
	}
	enum DeliverType{
		//------------国内-------
		GNZF("101","国内直发"),
		GNC("102","国内C"),
		GNAB("103","国内AB"),
		//-----------国外---------
		GMARKET("203","Gmarket"),
		HGZF("202","韩风直发"),
		RBZF("601","日本直发"),
		XGZF("401","香港直发"),
		EBAYMG("303","EBAY-美国"),
		EBAYYG("502","EBAY-英国"),
		HGC("201","韩国C"),
		MGHUB("302","美国(HUB发运)"),
		MGHK("301","美国(直发HK)"),
		XGC("402","香港C"),
		OZ("702","欧洲"),
		YDL("701","意大利"),
		YG("501","英国");
		public String code;
		public String desc;
		private DeliverType(String code, String desc){
			this.code = code;
			this.desc = desc;
		}
	}
}
