package com.xiu.search.core.catalog;

import com.xiu.search.dao.model.XDataBrand;

public class BrandModel extends XDataBrand{

	private String pyName;
	
	private String pyFirstLetter;

	public String getPyName() {
		return pyName;
	}

	public void setPyName(String pyName) {
		this.pyName = pyName;
	}

	public String getPyFirstLetter() {
		return pyFirstLetter;
	}

	public void setPyFirstLetter(String pyFirstLetter) {
		this.pyFirstLetter = pyFirstLetter;
	}
	
	
	
}
