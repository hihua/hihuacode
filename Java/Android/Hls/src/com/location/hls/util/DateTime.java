package com.location.hls.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {
	public static String getTimeStamp(final long timeStamp, final String format){
		try {			 
			final SimpleDateFormat simple = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance();			
			final Date date = new Date(timeStamp * 1000);
			simple.applyPattern(format);
			return simple.format(date);			
		} catch (Exception e) {			
			return "";
		}
	}
	
	public static long getNow() {
		try {
			final Date date = new Date();
			final SimpleDateFormat simple = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance();
			simple.applyPattern("yyyy-MM-dd HH:mm:ss");
			final Date da = simple.parse("1970-01-01 08:00:00");			
			return (date.getTime() - da.getTime()) / 1000;
		} catch (ParseException e) {			
			return 0;
		}
	}
}
