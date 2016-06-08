package com.xiu.search.core.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Service;

import com.xiu.search.core.service.QueryParserSolrService;
import com.xiu.search.core.solr.model.SuggestSolrModel;
import com.xiu.search.core.util.XiuSearchStringUtils;
import com.xiu.search.dao.config.XiuSearchConfig;
import com.xiu.search.solrj.query.QueryFieldCondition;
import com.xiu.search.solrj.query.QueryFieldSortCondition;
import com.xiu.search.solrj.query.SolrQueryBuilder;
import com.xiu.search.solrj.service.GenericSolrServiceImpl;
import com.xiu.search.solrj.service.SearchResult;

@Service("queryParserSolrService")
public class QueryParserSolrServiceImpl extends GenericSolrServiceImpl implements QueryParserSolrService {

	private Logger logger = Logger.getLogger(getClass());
	
	public List<String> getRelatedSearchTerms(String keyword,int maxRows){
		if(StringUtils.isBlank(keyword)){
			return null;
		}
		long begin=System.currentTimeMillis();
		String escapeInput = XiuSearchStringUtils.escapeSolrMetacharactor(keyword.trim().toLowerCase());
		List<String> relatedList = new ArrayList<String>();
		SolrQueryBuilder builder = new SolrQueryBuilder();
		BooleanQuery bq = new BooleanQuery();
		bq.add(new TermQuery(new Term("name_err",escapeInput)),Occur.MUST);
		BooleanQuery q = new BooleanQuery();
		q.add(new TermQuery(new Term("type","0")),Occur.SHOULD);
		q.add(new TermQuery(new Term("type","2")),Occur.SHOULD);
		q.add(new TermQuery(new Term("type","3")),Occur.SHOULD);
		bq.add(q,Occur.MUST);
		bq.add(new TermRangeQuery("count",1+"","*",true,true),Occur.MUST);
		builder.addMainQueryClauses(new QueryFieldCondition(bq));
		builder.addSortQueryClauses(new QueryFieldSortCondition("count", ORDER.desc));
		builder.setRows(10);
		builder.addFields("name");
		List<SuggestSolrModel> rets = null;
		SolrQuery solrQuery = builder.solrQueryBuilder();
		try {
			SearchResult<SuggestSolrModel> results = super.findAll(SuggestSolrModel.class, solrQuery);
			rets = results.getBeanList();
		} catch (SolrServerException e) {
			logger.error("查询suggest索引出错!\tQUERY"+solrQuery.toString(),e);
		}
		if(null != rets && rets.size()>0){
			loop1:
			for (SuggestSolrModel ts : rets) {
				for (String nt : ts.getNameList()) {
					if(nt.equals(escapeInput))continue loop1;
				}
				relatedList.add(ts.getNameList().get(0));
			}
		}
		long end=System.currentTimeMillis();
		logger.info("query solr for suggest info execute time:"+(end-begin));
		if(Boolean.valueOf(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_SWITCH))&&
				Integer.parseInt(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_OVERTIME))<(end-begin)){
			logger.warn("query solr for suggest info execute overtime:"+(end-begin));
		}
		return relatedList;
	}
	
	
	public String getErrorCorrectionTerms(String keyword){
		if(StringUtils.isBlank(keyword))
			return null;
		long begin=System.currentTimeMillis();
		String escapeInput = XiuSearchStringUtils.escapeSolrMetacharactor(keyword).trim();
		escapeInput = XiuSearchStringUtils.escapeBlank(XiuSearchStringUtils.replaceMultiBlankToSingle(escapeInput));
		BooleanQuery mainBQ = new BooleanQuery();
		TermQuery tq = null;
		tq = new TermQuery(new Term("name","\""+escapeInput+"\""));
		tq.setBoost(10f);
		mainBQ.add(tq,Occur.SHOULD);
		BooleanQuery cq = new BooleanQuery();
		BooleanQuery cq1 = new BooleanQuery();
		boolean hasCJK = escapeInput.matches(".*[\\u4E00-\\u9FA5]+.*");
		if(hasCJK){
			try {
				tq = new TermQuery(new Term("py","\""+XiuSearchStringUtils.getPingYin(escapeInput) + "\""));
				tq.setBoost(2f);
				cq1.add(tq,Occur.SHOULD);
			} catch (Exception e) {
			}
		}else{
			tq = new TermQuery(new Term("py","\""+escapeInput+"\""));
			tq.setBoost(1.5f);
			cq1.add(tq,Occur.SHOULD);
		}
//		cq.add(new TermRangeQuery("count",1+"","*",true,true),Occur.MUST);
		cq1.add(new TermQuery(new Term("name_err",escapeInput)),Occur.SHOULD);
		if(!hasCJK){
			Query qFuzz = this.letterFuzzyBuilder(keyword);
			if(null != qFuzz)
				cq1.add(qFuzz,Occur.SHOULD);
		}
		cq.add(cq1,Occur.MUST);
		cq.add(new TermRangeQuery("count",1+"","*",true,true),Occur.MUST);
		mainBQ.add(cq,Occur.SHOULD);

		SolrQuery query = new SolrQuery();
		query.setQuery(mainBQ.toString());
		query.setRows(2);
		query.addField("name");
		query.addField("count");
		query.add("defType", "extlucene");
		List<SuggestSolrModel> rets = null;
		try {
			SearchResult<SuggestSolrModel> results = super.findAll(SuggestSolrModel.class, query);
			rets = results.getBeanList();
		} catch (SolrServerException e) {
			logger.error("查询suggest索引出错!\tQUERY:"+query.toString(),e);
		}
		
		long end=System.currentTimeMillis();
		logger.info("query solr for suggest info execute getErrorCorrectionTerms method time:"+(end-begin));
		if(Boolean.valueOf(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_SWITCH))&&
				Integer.parseInt(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_OVERTIME))<(end-begin)){
			logger.warn("query solr for suggest info execute getErrorCorrectionTerms method overtime:"+(end-begin));
		}
		
		if(null == rets || rets.size()==0){
			return null;
		}
		SuggestSolrModel b = rets.get(0);
		
		for (String nt : b.getNameList()) {
			if(escapeInput.equalsIgnoreCase(nt))return null;
		}
		String reg = null;
		String pyReg = null;
		String pyInput = XiuSearchStringUtils.getPingYin(escapeInput);
		String oSearchTermErrRec = null;
		if(b.getCount()>0){
			oSearchTermErrRec = b.getNameList().get(0);
			for (String sname : b.getNameList()) {
				reg = "^.*("+sname.replaceAll("\\s ", ")+(")+")+.*$";
				pyReg = "^.*("+XiuSearchStringUtils.getPingYin(sname).replaceAll("\\s ", ")+(")+")+.*$";
				if(escapeInput.matches(reg) || pyInput.matches(pyReg)){
					oSearchTermErrRec = sname;
				}
			}
		}else if(rets.size()==2 && rets.get(1).getCount()>0){
			b = rets.get(1);
			oSearchTermErrRec = b.getNameList().get(0);
			for (String sname : b.getNameList()) {
				reg = "^.*("+sname.replaceAll("\\s ", ")+(")+")+.*$";
				pyReg = "^.*("+XiuSearchStringUtils.getPingYin(sname).replaceAll("\\s ", ")+(")+")+.*$";
				if(escapeInput.matches(reg) || pyInput.matches(pyReg)){
					oSearchTermErrRec = sname;
				}
			}
		}
		return oSearchTermErrRec;
	}
	
	@Override
	public List<SuggestSolrModel> getSuggestTerms(String keyword, int maxRows,String[] columns) {
		long begin=System.currentTimeMillis();
		
		//SolrQueryBuilder builder = new SolrQueryBuilder();
		keyword = keyword.trim();
		String input = keyword.replaceAll("[\\\\\\|!\\(\\)\\{\\}\\[\\]\\^\\$~\\?:\\s]+", "\\\\ ");
		String sinput = input.replaceAll("\\-", "\\\\-").replaceAll("\\&", "\\\\&").replaceAll("\\*","\\\\*");
		BooleanQuery mbq = new BooleanQuery();
		Query tq = null;
		BooleanQuery bq = new BooleanQuery();
		//name=input 并且 type=1
		BooleanQuery bqT1 = new BooleanQuery();
		tq = new TermQuery(new Term("name",sinput));
		bqT1.add(tq, Occur.MUST);
		tq = new TermQuery(new Term("type",1+""));
		bqT1.add(tq, Occur.MUST_NOT);
		bqT1.setBoost(10);
		bq.add(bqT1,Occur.SHOULD);
		//拼音首字母匹配并且  单个 品牌中文
		bqT1 = new BooleanQuery();
		tq = new PrefixQuery(new Term("s_py",sinput));
		bqT1.add(tq, Occur.MUST);
		tq = new TermQuery(new Term("type",4+""));
		bqT1.add(tq, Occur.MUST);
		bqT1.setBoost(0.6f);
		bq.add(bqT1,Occur.SHOULD);
		//拼音等于input的拼音并且 type=1
		bqT1 = new BooleanQuery();
		tq = new TermQuery(new Term("py",XiuSearchStringUtils.getPingYin(sinput)));
		bqT1.add(tq, Occur.MUST);
		tq = new TermQuery(new Term("type",1+""));
		bqT1.add(tq, Occur.MUST_NOT);//AND OR NOT
		bqT1.setBoost(1);
		bq.add(bqT1,Occur.SHOULD);
		tq = new PrefixQuery(new Term("name",sinput));
		tq.setBoost(6);
		bq.add(tq, Occur.SHOULD);
		tq = new PrefixQuery(new Term("py",sinput));
		tq.setBoost(5);
		bq.add(tq, Occur.SHOULD);
		mbq.add(bq,Occur.MUST);
		
		tq = new TermQuery(new Term("type", 0+""));
		tq.setBoost(20);
		mbq.add(tq,Occur.SHOULD);
		
		bq = new BooleanQuery();
		tq = new TermQuery(new Term("type", 1+""));
		bq.add(tq,Occur.MUST);
		tq = new TermRangeQuery("count", 5+"", "*", true, true);
		bq.add(tq,Occur.MUST);
		bq.setBoost(2);
		mbq.add(bq,Occur.SHOULD);
		
		tq = new TermQuery(new Term("type", 2+""));
		tq.setBoost(3);
		mbq.add(tq,Occur.SHOULD);
		
		tq = new TermQuery(new Term("type", 4+""));
		tq.setBoost(3);
		mbq.add(tq,Occur.SHOULD);
		
		tq = new TermQuery(new Term("type", 3+""));
		tq.setBoost(0.5f);
		mbq.add(tq,Occur.SHOULD);
		
		tq = new TermQuery(new Term("type", 5+""));
		tq.setBoost(0.5f);
		mbq.add(tq,Occur.SHOULD);
		
		tq = new TermRangeQuery("count",1+"","*",true,true);
		mbq.add(tq,Occur.MUST);
		SolrQuery solrQuery = new SolrQuery(mbq.toString());
		solrQuery.setRows(10);
		solrQuery.addSortField("score", ORDER.desc);
		solrQuery.addSortField("common_score", ORDER.desc);
		solrQuery.addSortField("count", ORDER.desc);
		List<SuggestSolrModel> ret = null;
		try {
			SearchResult<SuggestSolrModel> result = super.findAll(SuggestSolrModel.class, solrQuery);
			if(null != result)
				ret = result.getBeanList();
		} catch (SolrServerException e) {
			logger.error("查询suggest索引出错!\tQUERY:"+solrQuery.toString(),e);
		}
		
		long end=System.currentTimeMillis();
		logger.info("query solr for suggest info execute getSuggestTerms method time:"+(end-begin));
		if(Boolean.valueOf(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_SWITCH))&&
				Integer.parseInt(XiuSearchConfig.getPropertieValue(XiuSearchConfig.LOG_WARN_OVERTIME))<(end-begin)){
			logger.warn("query solr for suggest info execute getSuggestTerms method overtime:"+(end-begin));
		}
		return ret;
	}
	
	private Query letterFuzzyBuilder(String input){
		input = XiuSearchStringUtils.replaceBlankSpecialCharactor(input).trim();
		input = XiuSearchStringUtils.replaceMultiBlankToSingle(input);
		if(null == input || input.length()==0)return null;
		Set<String> tSet = new HashSet<String>(); 
		char[] inputChars = input.toCharArray();
		//letter reversal
		//this.letterReversal(tSet, inputChars);
		//letter lack
		this.letterLack(tSet, inputChars);
		//letter wildcard
		this.letterWildcard(tSet, inputChars,input);
		//letter omit
		this.letterOmit(tSet, inputChars);
		
		BooleanQuery mainBQ = new BooleanQuery();
		TermQuery tq = null;
		for (String o : tSet) {
			tq = new TermQuery(new Term("name", XiuSearchStringUtils.escapeBlank(o)));
			mainBQ.add(tq,Occur.SHOULD);
		}
		return mainBQ;
	}
	
	
	/**
	 * 
	 * @param tokenSet
	 * @param inputChars
	 */
	private void letterReversal(Set<String> tokenSet,char[] inputChars){
		int len = inputChars.length;
		if(len <= 1)
			tokenSet.add(String.valueOf(inputChars));
		char char1 = '\0';
		char char2 = '\0';
		for (int i = 0,j=1; i < len && j<len; i++,j++) {
			char1 = inputChars[i];
			char2 = inputChars[j];
			inputChars[i] = char2;
			inputChars[j] = char1;
			tokenSet.add(String.valueOf(inputChars));
			inputChars[i] = char1;
			inputChars[j] = char2;
		}
	}
	
	/**
	 * 
	 * @param tokenSet
	 * @param inputChars
	 */
	private void letterLack(Set<String> tokenSet,char[] inputChars){
		int len = inputChars.length;
		if(len <= 1)
			tokenSet.add(String.valueOf(inputChars));
		char[] tChars = new char[len-1];
		for (int i = 0; i < len; i++) {
			if(i>0){
				System.arraycopy(inputChars, 0, tChars, 0, i);
			}
			if(i<len){
				System.arraycopy(inputChars, i+1, tChars, i, len-i-1);
			}
//			System.out.println(String.valueOf(tChars));
			tokenSet.add(String.valueOf(tChars));
		}
	}
	
	private void letterWildcard(Set<String> tokenSet,char[] inputChars,String oriInput){
		int len = inputChars.length;
		if(len <= 1)
			tokenSet.add(String.valueOf(inputChars));
		char char1;
		for (int i = 1; i < len; i++) {
			char1 = inputChars[i];
			inputChars[i]=SINGLE_WILDCARD_CHAR;
//			System.out.println(String.valueOf(inputChars));
			tokenSet.add(String.valueOf(inputChars));
			inputChars[i]=char1;
		}
		len = WILDCARD_CHARS.length;
		for (int i = 0; i < len; i++) {
			tokenSet.add(WILDCARD_CHARS[i]+oriInput);
		}
	}
	
	private void letterOmit(Set<String> tokenSet,char[] inputChars){
		int len = inputChars.length;
		if(len <= 1)
			tokenSet.add(String.valueOf(inputChars));
		char[] tChars = new char[len+1];
		for (int i = 1; i < len+1; i++) {
			System.arraycopy(inputChars, 0, tChars, 0, i);
			tChars[i] = SINGLE_WILDCARD_CHAR;
			System.arraycopy(inputChars, i, tChars, i+1, len-i);
//			System.out.println(String.valueOf(tChars));
			tokenSet.add(String.valueOf(tChars));
		}
	}
	
	private static final char SINGLE_WILDCARD_CHAR = '?';
	private static final char[] WILDCARD_CHARS = new char[26];
	
	static{
		int i = 0;
		for (char c = 'a'; c <= 'z'; c++,i++) {
			WILDCARD_CHARS[i] = c;
		}
	}

	
}
