package com.xiu.search.dao.cache;

class CacheHolder implements java.io.Serializable{
	private final long timeout;
	private final Object value;
	private volatile boolean expiredFlag;
	public CacheHolder(Object value, long timeout){
		this.value = value;
		this.timeout = timeout;
	}
	public Object getValue() {
		return value;
	}
	/**
	 * 是否过期失效
	 * @return
	 */
	public boolean isExpired(){
		if(!expiredFlag)
			expiredFlag = System.currentTimeMillis()>this.timeout;
		return expiredFlag;
	}
	
	@Override
	public boolean equals(Object o){
		if(o == null || !(o instanceof CacheHolder))
			return false;
		CacheHolder _o = (CacheHolder)o;
		if(null == this.value || this.isExpired())
			return null == _o.getValue() || _o.isExpired();
		return this.value.equals(_o.getValue());
	}
}
