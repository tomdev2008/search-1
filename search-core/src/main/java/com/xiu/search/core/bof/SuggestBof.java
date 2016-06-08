package com.xiu.search.core.bof;

import com.xiu.search.core.solr.params.SuggestFatParams;

public interface SuggestBof {

	public String parseSuggestJsonStr(SuggestFatParams params);
	
}
