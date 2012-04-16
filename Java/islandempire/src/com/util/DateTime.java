package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {

	public static long getTime(Date date) {
		try {			
			return (date.getTime() - new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1970-01-01 08:00:00").getTime()) / 1000;
		} catch (ParseException e) {			
			return 0;
		}
	}
	
	public static Date getTime(Long second) {
		try {				
			return new Date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1970-01-01 08:00:00").getTime() + second * 1000);
		} catch (ParseException e) {			
			return null;
		}
	}
	
	public static String getNow() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(new Date());
	}
}
