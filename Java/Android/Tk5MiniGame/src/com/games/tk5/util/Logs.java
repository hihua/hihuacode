package com.games.tk5.util;

import android.util.Log;

public class Logs {
	public static final String LogName = "Tk5MiniGame";
	
	public static void LogsError(String msg) {
		Log.e(LogName, msg);
	}
	
	public static void LogsError(Exception e) {
		Log.e(LogName, e.getMessage(), e.getCause());
	}
	
	public static void LogsInfo(String format, Object... args) {
		Log.i(LogName, String.format(format, args));		
	}
	
	public static void LogsInfo(String msg) {
		Log.i(LogName, msg);
	}
			
	public static void LogsInfo(int value) {
		Log.i(LogName, String.valueOf(value));
	}
	
	public static void LogsInfo(long value) {
		Log.i(LogName, String.valueOf(value));
	}
	
	public static void LogsInfo(float value) {
		Log.i(LogName, String.valueOf(value));
	}
	
	public static void LogsInfo(double value) {
		Log.i(LogName, String.valueOf(value));
	}
	
	public static void LogsInfo(Exception e) {
		Log.i(LogName, e.getMessage(), e.getCause());
	}		
}
