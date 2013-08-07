package com.gamemarket.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTime {
	public static long getNow(String from) {
		try {
			Date date = new Date();			
			Date da = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(from);
			return (date.getTime() - da.getTime()) / 1000;
		} catch (ParseException e) {
			return 0;
		}
	}
	
	public static long getNow() {
		try {
			Date date = new Date();			
			Date da = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1970-01-01 08:00:00");
			return (date.getTime() - da.getTime()) / 1000;
		} catch (ParseException e) {			
			return 0;
		}
	}
	
	public static long getDate() {
		try {
			String s = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(s);			
			return getTimeStamp(date);
		} catch (ParseException e) {			
			return 0;
		}
	}
	
	public static long getTimeStamp(Date date){
		try {
			Date da = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1970-01-01 08:00:00");
			return (date.getTime() - da.getTime()) / 1000;
		} catch (ParseException e) {			
			return 0;
		}
	}
	
	public static Date getTimeStamp(long second) {
		try {
			Date da = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1970-01-01 08:00:00");			
			return new Date(da.getTime() + second * 1000);
		} catch (ParseException e) {			
			return null;
		}
	}
	
	public static Date getDateTime(String s, String format) {
		SimpleDateFormat da = new SimpleDateFormat(format);		
		try {
			Date dateTime = da.parse(s);
			return dateTime;
		} catch (ParseException e) {			
			return null;
		}
	}
	
	public static String getDateTime(Date date, String format) {
		SimpleDateFormat da = new SimpleDateFormat(format);
		return da.format(date);
	}
	
	public static Date getDay(Date date, int day) {
		Calendar calendar = Calendar.getInstance();   
		calendar.setTime(date);   
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + day);
		return calendar.getTime();
	}
}
