package com.xiu.search.core.solr.enumeration;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public enum SearchTimeRangeEnum {
	NOW_7DAYS(1,"7天内",null),
	NOW_1MONTH(2,"一个月",null),
	NOW_3MONTH(3,"三个月",null),
	NOW_HALF_YEAR(4,"半年",null),
	NOW_1YEAR(5,"一年",null);
	private final SimpleDateFormat DAY_FORMAT= new SimpleDateFormat("yyyy-MM-dd");
	private static final String SUB_SOLR_DATE_BEGIN = "T00:00:00Z";
	private static final String SUB_SOLR_DATE_END = "T23:59:59Z";
	private int key;
	private String desc;
	private TimeRange timeRange;
	private SearchTimeRangeEnum(int key, String desc, TimeRange timeRange){
		this.key = key;
		this.desc = desc;
		this.timeRange = timeRange;
	}
	public static SearchTimeRangeEnum getType(int key){
		switch (key) {
		case 1:
			return NOW_7DAYS;
		case 2:
			return NOW_1MONTH;
		case 3:
			return NOW_3MONTH;
		case 4:
			return NOW_HALF_YEAR;
		case 5:
			return NOW_1YEAR;
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
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public TimeRange getTimeRange() {
		Calendar calendar = null;
		switch (key) {
		case 1:
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -7);
			timeRange = new TimeRange();
			timeRange.setBegin(DAY_FORMAT.format(calendar.getTime())+SUB_SOLR_DATE_BEGIN);
			timeRange.setEnd(DAY_FORMAT.format(new Date())+SUB_SOLR_DATE_END);
			break;
		case 2:
			calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -1);
			timeRange = new TimeRange();
			timeRange.setBegin(DAY_FORMAT.format(calendar.getTime())+SUB_SOLR_DATE_BEGIN);
			timeRange.setEnd(DAY_FORMAT.format(new Date())+SUB_SOLR_DATE_END);
			break;
		case 3:
			calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -3);
			timeRange = new TimeRange();
			timeRange.setBegin(DAY_FORMAT.format(calendar.getTime())+SUB_SOLR_DATE_BEGIN);
			timeRange.setEnd(DAY_FORMAT.format(new Date())+SUB_SOLR_DATE_END);
			break;
		case 4:
			calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -6);
			timeRange = new TimeRange();
			timeRange.setBegin(DAY_FORMAT.format(calendar.getTime())+SUB_SOLR_DATE_BEGIN);
			timeRange.setEnd(DAY_FORMAT.format(new Date())+SUB_SOLR_DATE_END);
			break;
		case 5:
			calendar = Calendar.getInstance();
			calendar.add(Calendar.YEAR, -1);
			timeRange = new TimeRange();
			timeRange.setBegin(DAY_FORMAT.format(calendar.getTime())+SUB_SOLR_DATE_BEGIN);
			timeRange.setEnd(DAY_FORMAT.format(new Date())+SUB_SOLR_DATE_END);
			break;
		default:
			break;
		}
		return timeRange;
	}

	public class TimeRange{
		private String begin;
		private String end;
		public String getBegin() {
			return begin;
		}
		public void setBegin(String begin) {
			this.begin = begin;
		}
		public String getEnd() {
			return end;
		}
		public void setEnd(String end) {
			this.end = end;
		}
	}
}
