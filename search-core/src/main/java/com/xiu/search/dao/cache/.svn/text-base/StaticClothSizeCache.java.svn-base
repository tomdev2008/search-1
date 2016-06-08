package com.xiu.search.dao.cache;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 尺码规则
 * @author rian.luo@xiu.com
 *
 * 2016-5-17
 */
public  class StaticClothSizeCache {
	private static StaticClothSizeCache instance;
	/*
	 * 
	 国际码：	XXXS	XXS	XS/P	XS	S	M	L	XL	XXL	XXXL	4XL																																																																																																																			
	意大利码：	IT34	IT34.5	IT35	IT35.5	IT36	IT36.5	IT37	IT37.5	IT38	IT38.5	IT39	IT39.5	IT40	IT40.5	IT41	IT41.5	IT42	IT42.5	IT44	IT46	IT48	IT50	IT52	IT54	IT56	IT58																																																																																																				
	美国码：	US0	US2	US4	US4.5	US5	US5.5	US6	US6.5	US7	US7.5	US8	US8.5	US9	US9.5	US10	US10.5	US11	US11.5	US12	US12.5	US13	US13.5	US14	US34	US36	US38	US40	US42	US44	US46																																																																																																
	英国码：	UK1.5	UK2	UK2.5	UK3	UK3.5	UK4	UK4.5	UK5	UK5.5	UK6	UK6.5	UK7	UK7.5	UK8	UK8.5	UK9	UK9.5	UK10	UK10.5	UK11	UK11.5	UK12	UK12.5	UK14	UK16	UK18	UK28	UK30	UK32	UK34	UK36	UK38	UK40	UK42	UK44	UK46																																																																																										
	欧洲码：	EU34	EU34.5	EU35	EU35.5	EU36	EU36.5	EU37	EU37.5	EU38	EU38.5	EU39	EU39.5	EU40	EU40.5	EU41	EU41.5	EU42	EU42.5	EU43	EU43.5	EU44	EU44.5	EU45	EU45.5	EU46	EU46.5	EU48	EU50	EU52	EU54	EU56	EU58																																																																																														
	法国码：	FR32	FR34	FR35	FR35.5	FR36	FR36.5	FR37	FR37.5	FR38	FR38.5	FR39	FR39.5	FR40	FR40.5	FR41	FR41.5	FR42	FR42.5	FR43	FR43.5	FR44	FR46	FR48	FR50	FR52	FR54	FR56	EU58																																																																																																		
	数字	0	1	2/3	4	5	6	7	14 1/2	15	15 1/2	15 3/4	16	16 1/2	17	17 1/2	24	25	26	27	28	29	30	31	32	33	34	35	36	37	38	39	40	41	42	43	44	65	70	75	80	85	90	95	100	105	110																																																																																
	罗马码：	0	Ⅰ	Ⅱ	Ⅲ	Ⅳ	Ⅴ	Ⅵ																																																																																																																							
	罗马码：	1	2	3	4	5	6	7																																																																																																																							
	数字英文组合	0A	0B	0C	0D	0DD	0E	1A	1B	1C	1D	1DD	1E	2A	2B	2C	2D	2DD	2E	3A	3B	3C	3D	3DD	3E	4A	4B	4C	4D	4DD	4E	5A	5B	5C	5D	5DD	5E	30A	30B	30C	30D	30DD	30E	32A	32B	32C	32D	32DD	32E	34A	34B	34C	34D	34DD	34E	36A	36B	36C	36D	36DD	36E	38A	38B	38C	38D	38DD	38E	40A	40B	40C	40D	40DD	40E	65A	65B	65C	65D	65DD	65E	70A	70B	70C	70D	70DD	70E	75A	75B	75C	75D	75DD	75E	80A	80B	80C	80D	80DD	80E	85A	85B	85C	85D	85DD	85E	90A	90B	90C	90D	90DD	90E	95A	95B	95C	95D	95DD	95E	100A	100B	100C	100D	100DD	100E	105A	105B	105C	105D	105DD	105E
	英文数字组合	BB0	BB1	BB2	BB3	BB4	BB5	BB6																																																																																																																							

	 * 	
	 */	
	/*private static final String ALL_CLOTH_SIZE = "XXXS,XXS,XS/P,XS,S,M,L,XL,XXL,XXXL,4XL," + 
			"IT26,IT27,IT30,IT31,IT32,IT34,IT34.5,IT35,IT35.5,IT36,IT36.5,IT37,IT37.5,IT38,IT38.5,IT39,IT39.5,IT40,IT40.5,IT41,IT41.5,IT42,IT42.5,IT43,IT43.5,IT44,IT44.5,IT45,IT45.5,IT46,IT46.5,IT47,IT47.5,IT48,IT50,IT52,IT54,IT55,IT56,IT58,IT60,IT62,IT64," + 
			"US0,US1,US2,US3,US4,US4.5,US5,US5.5,US6,US6.5,US7M,US7,US7.5M,US7.5,US8M,US8,US8.5M,US8.5,US9M,US9,US9.5M,US9.5,US10M,US10,US10.5M,US10.5,US11M,US11,US11.5,US12,US12.5,US13,US13.5,US14,US30,US31,US32,US33,US34,US35,US36,US38,US40,US42,US44,US46,US48,USXS,USS,USM,USL," +
			"UK1.5,UK2,UK2.5,UK3,UK3.5,UK4,UK4.5,UK5,UK5.5,UK6,UK6.5,UK7,UK7.5,UK8,UK8.5,UK9,UK9.5,UK10,UK10.5,UK11,UK11.5,UK12,UK12.5,UK14,UK16,UK18,UK28,UK30,UK32,UK34,UK36,UK38,UK40,UK42,UK44,UK46,UK48,UK50,UK52,UK54," + 
			"EU9,EU30,EU32,EU33,EU34,EU34.5,EU35,EU35.5,EU36,EU36.5,EU37,EU37.5,EU38,EU38.5,EU39,EU39.5,EU40,EU40.5,EU41,EU41.5,EU42,EU42.5,EU43,EU43.5,EU44,EU44.5,EU45,EU45.5,EU46,EU46.5,EU47,EU47.5,EU48,EU48.5,EU50,EU51,EU52,EU54,EU56,EU58,EU59,EU60,EU39H,EU40H,EU41H,EU42H," + 
			"FR30,FR32,FR34,FR35,FR35.5,FR36,FR36.5,FR37,FR37.5,FR38,FR38.5,FR39,FR39.5,FR40,FR40.5,FR41,FR41.5,FR42,FR42.5,FR43,FR43.5,FR44,FR46,FR47,FR48,FR50,FR52,FR54,FR56,FR62," + 
			"Ⅰ,Ⅱ,Ⅲ,Ⅳ,Ⅴ,Ⅵ," + 
			"0A,0B,0C,0D,0DD,0E,1A,1B,1C,1D,1DD,1E,2A,2B,2C,2D,2DD,2E,3A,3B,3C,3D,3DD,3E,4A,4B,4C,4D,4DD,4E,5A,5B,5C,5D,5DD,5E,30A,30B,30C,30D,30DD,30E,32A,32B,32C,32D,32DD,32E,34A,34B,34C,34D,34DD,34E,36A,36B,36C,36D,36DD,36E,38A,38B,38C,38D,38DD,38E,40A,40B,40C,40D,40DD,40E,65A,65B,65C,65D,65DD,65E,70A,70B,70C,70D,70DD,70E,75A,75B,75C,75D,75DD,75E,80A,80B,80C,80D,80DD,80E,85A,85B,85C,85D,85DD,85E,90A,90B,90C,90D,90DD,90E,95A,95B,95C,95D,95DD,95E,100A,100B,100C,100D,100DD,100E,105A,105B,105C,105D,105DD,105E," + 
			"BB0,BB1,BB2,BB3,BB4,BB5,BB6";*/
	
	private static final String ALL_CLOTH_SIZE2 = "XXS及以下,XS及以下,XS,S,M,L,XL,XXL,XXL及以上,XXXL及以上,0-3M,3-6M,6-9M,9-12M,12-18M,18-24M,2,3,4,5,6,7,8,9-10,11-12,13-14,15-16,17-18,15,16,17,18,19,20,21,22,23,24,24及以下,25,26,27,28及以下,28,29,30,31,31及以上,32,33,34及以下,34,35,36,37,38及以下,38,39,40,40及以上,41,42,43,44,45及以上,65,70,75,80,85,90,95,100,105,105及以上,110,115,30A,30B,30C,30D及以上,32A,32B,32C,32D及以上,34A,34B,34C,34D及以上,36A,36B,36C,36D及以上,38A,38B,38C,38D及以上,40A,40B,40C,40D及以上,均码,其他";
	
	private static final Map<String, Integer> MAP_CLOTH_SIZE = new HashMap<String, Integer>();
	
	public static StaticClothSizeCache getInstance(){
		if(instance == null)
			instance = new StaticClothSizeCache();
		return instance;
	}
	
	StaticClothSizeCache(){
		String[] sizeArr = ALL_CLOTH_SIZE2.split("\\,");
		for ( int i = 0; i < sizeArr.length; i ++ ){
			MAP_CLOTH_SIZE.put(sizeArr[i].trim(), i);
		}
	}

	private static boolean isNumeric(String str){ 
		   Pattern pattern = Pattern.compile("[0-9/.]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
	}
	
	public static void main(String[] args) {
		StaticClothSizeCache scscCache = new StaticClothSizeCache();
		System.out.println("----------------"+MAP_CLOTH_SIZE.size()+"-----------------");
		for (Iterator<String> iterator = MAP_CLOTH_SIZE.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next();
			System.out.println(key+"-----"+MAP_CLOTH_SIZE.get(key));
		}
		
		//sizeSortTest();
	}
	
	private static double parseNumber(String strNumber){
		
		double doublePos = 0;
		double intPart = 0;
		double deciPart = 0;
		if (isNumeric(strNumber.trim())){
			if (strNumber.trim().indexOf(" ")>= 0){
				String[] strArr = strNumber.split(" ");
				if (strArr.length > 0){
					intPart = Double.parseDouble(strArr[0].trim());
				}
				if (strArr.length >= 2){
					String[] leavArr = strArr[1].split("/");
					if (leavArr.length >= 2){
						System.out.println("leavArr[0].trim() " + leavArr[0].trim() + " ; " + leavArr[1].trim()); 
						deciPart = Double.parseDouble(leavArr[0].trim())/Double.parseDouble(leavArr[1].trim()) ;
					}
				}
				doublePos = intPart + deciPart;
				
			}else{
				if ( strNumber.indexOf("/") >= 0 ){
					String[] leavArr = strNumber.split("/");
					if (leavArr.length >= 2){
						deciPart = Double.parseDouble(leavArr[0].trim())/Double.parseDouble(leavArr[1].trim()) ;
						doublePos = intPart + deciPart;
					}
				}else {
					doublePos = Double.parseDouble(strNumber.trim());
				}
			}
			BigDecimal b = new BigDecimal(doublePos);  
			double f1 = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  
			return f1;
		}
		else
			return 0;
	}
	
	
	public static double getSize(String key){
		key = key.trim();
		if (MAP_CLOTH_SIZE.containsKey(key)){
			return MAP_CLOTH_SIZE.get(key);
		}else if ("均码".equalsIgnoreCase(key)){
			return Double.MAX_VALUE;
		}else if("F".equalsIgnoreCase(key)){
			return Double.MAX_VALUE+1;
		}else if("选配".equalsIgnoreCase(key)){
			return Double.MAX_VALUE+2;
		}else if("标配".equalsIgnoreCase(key)){
			return Double.MAX_VALUE+3;
		}else if(isNumeric(key)){
			return parseNumber(key) + 1000;
		}else{
			return parseNumber(key) + 5000;
		}
	}
	
	private class Size{
		private String size;

		public String getSize() {
			return size;
		}

		public void setSize(String size) {
			this.size = size;
		}
	}
	
	
	public static List<String> sizeSortTest(){
		List<String> sizeList = new ArrayList<String>();
		sizeList.add("XS");
		sizeList.add("S");
		sizeList.add("M");
		sizeList.add("L");
		sizeList.add("XL");
		sizeList.add("28");
		sizeList.add("XXL及以上");
		sizeList.add("XXS及以下");
		sizeList.add("其他");
		sizeList.add("均码");
		Collections.sort(sizeList, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				double d1 = getSize(o1);
				double d2 = getSize(o2);
				int rsult =0;
				if(d1>d2){
					rsult=1;
				}else if(d1<d2) {
					rsult=-1;
				}
				return rsult;
			}
			
		});
		for (int i = 0; i < sizeList.size(); i++) {
			System.out.println(sizeList.get(i));
		}
		
		return null;
		
	}
	
}
