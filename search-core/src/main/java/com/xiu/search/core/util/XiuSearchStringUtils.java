package com.xiu.search.core.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.lucene.queryParser.QueryParser;



public class XiuSearchStringUtils {
	
	/**
	 * 正数，负数<br>
	 * 但非浮点数
	 * @param input
	 * @return
	 */
	public static boolean isIntegerNumber(String input){
		int len;
		if(null == input 
				|| (len= input.length())==0)
			return false;
		char c;
		for (int i = 0; i < len; i++) {
			c = input.charAt(i);
			if(!Character.isDigit(c)
					&& !(i==0 && c == '-'))
				return false;
		}
		return true;
	}
	
	/**
	 * 获取有效长度的词
	 * 1个中文=2个英文
	 * @param input 原始输入term
	 * @param maxLength 最大允许的长度
	 * @return
	 */
	public static String getValidLengthTerm(String input,int maxLength){
    	int len = input.length();
    	if(len == 0)
    		return input;
		int clen = 0,start=0;
		do {
			if(XiuSearchStringUtils.isCJK(input.charAt(start))){
				clen+=2;
			}else{
				clen++;
			}
		} while (clen < maxLength && ++start < len);
		if(clen == maxLength)
			start++;
		return start < len ? input.substring(0,start):input;
    }
	
	/**
	 * 获取一个字符串的拼音码
	 * 
	 * @param oriStr
	 * @return
	 */
	public static String getFirstLetter(String oriStr) {
		char[] t1 = oriStr.toCharArray();
		StringBuffer sb = new StringBuffer(t1.length);
		String[] t2 = new String[t1.length];
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);

		int len = t1.length;
		try {
			for (int i = 0; i < len; i++) {
				// 判断是否为汉字字符
				if (java.lang.Character.toString(t1[i]).matches(
						"[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
					sb.append(t2[0].substring(0, 1));
				} else {
					sb.append(t1[i]);
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 获取一个字符串的拼音
	 * 
	 * @param src
	 * @return
	 */
	public static String getPingYin(String src) {

		char[] t1 = null;
		t1 = src.toCharArray();
		String[] t2 = new String[t1.length];
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		String t4 = "";
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// 判断是否为汉字字符
				if (java.lang.Character.toString(t1[i]).matches(
						"[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
					t4 += t2[0];
				} else
					t4 += java.lang.Character.toString(t1[i]);
			}
			// System.out.println(t4);
			return t4;
		} catch (BadHanyuPinyinOutputFormatCombination e1) {
			e1.printStackTrace();
		}
		return t4;
	}
	
	/**
	 * 判断是否是中文，日文，韩文
	 * @param c
	 * @return
	 */
	public static boolean isCJK(char c){
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if(ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                // 韩文
                || ub == Character.UnicodeBlock.HANGUL_SYLLABLES
                || ub == Character.UnicodeBlock.HANGUL_JAMO
                || ub == Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO
                //日文
                || ub == Character.UnicodeBlock.HIRAGANA
                || ub == Character.UnicodeBlock.KATAKANA
                || ub == Character.UnicodeBlock.KATAKANA_PHONETIC_EXTENSIONS)
			return true;
		return false;
	}
	
	/**
	 * 转义正则保留关键字符
	 * @param s
	 * @return
	 */
	public static String escapeRegexMetacharactor(String s){
		StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < s.length(); i++) {
	      char c = s.charAt(i);
	      // These characters are part of the query syntax and must be escaped
	      if (c == '\\' || c == '+' || c == '-' || c == '!' || c == '(' || c == ')' || c == '.'
	        || c == '^' || c == '[' || c == ']' || c == '{' || c == '}' 
	        || c == '*' || c == '?' || c == '|' || c == '&' || c == '$') {
	        sb.append('\\');
	      }
	      sb.append(c);
	    }
	    return sb.toString();
	}
	
	/**
	 * 转移所有的solr保留关键字
	 * @param s
	 * @return
	 */
	public static String escapeSolrMetacharactor(String s){
		return QueryParser.escape(s);
	}
	
	/**
	 * Replace all "    " to " ";
	 * @param input
	 * @return
	 */
	public static String replaceMultiBlankToSingle(String input){
		return input.replaceAll("\\s+", "\\ ");
	}
	
	/**
	 * Use blank charactor replaceof special charactor 
	 * @param input
	 * @return
	 */
	public static String replaceBlankSpecialCharactor(String input){
		String regex = "[^\u4e00-\u9fa5\\w\\.]+";
		return input.replaceAll(regex, " ");
	}
	
	/**
	 * Replace all " " to "\ ";
	 * @param input
	 * @return
	 */
	public static String escapeBlank(String input){
		return input.replaceAll("\\s", "\\\\ ");
	}
}
