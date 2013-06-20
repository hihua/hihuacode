package com.apps.game.market.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {		
	public static String getTimeStamp(long timeStamp, String format){
		try {			 
			final SimpleDateFormat simple = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance();			
			final Date da = new Date(timeStamp);
			simple.applyPattern(format);
			return simple.format(da);			
		} catch (Exception e) {			
			return "";
		}
	}
}
