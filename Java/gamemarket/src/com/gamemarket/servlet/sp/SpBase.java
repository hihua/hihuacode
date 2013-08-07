package com.gamemarket.servlet.sp;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import com.gamemarket.servlet.ServletBase;
import com.gamemarket.util.Logs;
import com.gamemarket.util.Numeric;

public abstract class SpBase extends ServletBase {
	protected final Logger Log = Logs.getSp();
	private final String Key = "GameMarket_UU";
	private final char End = 0x03;
	private final char Over = 0x04;
	
	@Override
	protected void writeLog(final Priority priority, final Object message, final Throwable t) {
		Log.log(priority, message, t);
	}
	
	@Override
	protected void writeLog(final Priority priority, final Object message) {
		Log.log(priority, message);
	}
	
	protected boolean checkRequest(final HttpServletRequest request) {
		final Map<String, String[]> map = request.getParameterMap();
		if (map == null)
			return false;
		
		final Map<String, String[]> tree = new TreeMap<String, String[]>(); 
		for (Entry<String, String[]> entry : map.entrySet()) {					
			String key = entry.getKey();															
			String[] value = entry.getValue();
			tree.put(key, value);				
		}		
		
		final StringBuilder sb = new StringBuilder();
		String key = "";		
		
		for (final Entry<String, String[]> entry : tree.entrySet()) {
			String keys = entry.getKey();
			final String[] values = entry.getValue();
			
			if (keys == null)
				continue;
			
			try {
				keys = new String(keys.getBytes("ISO8859-1"), Charset);
			} catch (UnsupportedEncodingException e) {
				writeLog(Level.ERROR, e.getMessage(), e);
			}
			
			if (keys.equals("key")) {
				if (values != null && values.length == 1)
					key = values[0];
				
				continue;
			}
			
			if (values != null) {
				for (String value : values) {
					sb.append(keys);
					sb.append(":");
					
					try {
						value = new String(value.getBytes("ISO8859-1"), Charset);
					} catch (UnsupportedEncodingException e) {
						writeLog(Level.ERROR, e.getMessage(), e);
					}
					
					sb.append(value);
					sb.append(End);
				}
			} else {
				sb.append(keys);
				sb.append(":");
				sb.append(End);
			}
		}
		
		if (key.length() > 0 && sb.length() > 0) {
			sb.append(Over);
			sb.append(Key);
			final String md5 = Numeric.md5(sb.toString(), Charset);
			if (md5 != null && md5.equals(key))
				return true;			
		}
		
		return false;
	}
}
