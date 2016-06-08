package com.xiu.search.web.vo;



public class SearchParams {
	private String kw;//搜索关键字
	private String tags;//标签搜索字段
	private Integer cat;// 运营分类
	private String bid;// 品牌ID
	private Integer f_price;// 价格过滤
	private Integer sort;// 排序
	private Integer ba;// 可购买
	private Integer p;// 页数
	private Float s_price;// 输入开始价格
	private Float e_price;// 输入结束价格
	private String filter;// 过滤条件
	private Integer vm;//浏览模式，大图，列表
	private Integer mkt;// 市场来源 0-走秀 1-ebay
	private String channel;//新增商品类别，用于页面多选按钮	William.zhang
	private String itemshowtype; //商品类别，1 奢侈品，2 国内，3 国外。属于多个类别则使用,连接
	/**
	 * 搜索来源
	 * 判断从Input来或者其他
	 */
	private String src;
	
	/**
	 * 可选字段列表，多个之间用;分隔
	 */
	private String opts;
	private Integer s;//每页返回的记录数
	
//	private String kwEncode;// encode，url使用
	
	public String getKw() {
		return kw;
	}
	
	public Integer getVm() {
		return vm;
	}
	public void setVm(Integer vm) {
		this.vm = vm;
	}
	public void setKw(String kw) {
		this.kw = kw;
	}
	public Integer getCat() {
		return cat;
	}
	public void setCat(Integer cat) {
		this.cat = cat;
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public Integer getF_price() {
		return f_price;
	}
	public void setF_price(Integer f_price) {
		this.f_price = f_price;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getBa() {
		return ba;
	}
	public void setBa(Integer stock) {
		this.ba = stock;
	}
	public Integer getP() {
		return p;
	}
	public void setP(Integer p) {
		this.p = p;
	}
	public Float getS_price() {
		return s_price;
	}
	public void setS_price(Float s_price) {
		this.s_price = s_price;
	}
	public Float getE_price() {
		return e_price;
	}
	public void setE_price(Float e_price) {
		this.e_price = e_price;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	public Integer getMkt() {
		return mkt;
	}
	public void setMkt(Integer mkt) {
		this.mkt = mkt;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

    public String getItemshowtype() {
        return itemshowtype;
    }
    //业务要求，页面传入的itemshowtype取消		William.zhang	20130722
    public void setItemshowtype(String itemshowtype) {
        this.itemshowtype = itemshowtype;
    }

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getOpts() {
		return opts;
	}

	public void setOpts(String opts) {
		this.opts = opts;
	}

	public Integer getS() {
		return s;
	}

	public void setS(Integer s) {
		this.s = s;
	}

	@Override
	public String toString() {
		return "SearchParams [kw=" + kw + ", tags=" + tags + ", cat=" + cat
				+ ", bid=" + bid + ", f_price=" + f_price + ", sort=" + sort
				+ ", ba=" + ba + ", p=" + p + ", s_price=" + s_price
				+ ", e_price=" + e_price + ", filter=" + filter + ", vm=" + vm
				+ ", mkt=" + mkt + ", channel=" + channel + ", itemshowtype="
				+ itemshowtype + ", src=" + src + ", opts=" + opts + ", s=" + s
				+ "]";
	}
	
//	public String getKwEncode() {
//		try {
//			return URLEncoder.encode(this.kw, "UTF-8");
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return this.kw;
//	}
	
}
