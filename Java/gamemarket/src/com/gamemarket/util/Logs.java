package com.gamemarket.util;

import org.apache.log4j.Logger;

public class Logs {
	public static Logger getDefault() {
		return Logger.getRootLogger();
	}
	
	public static Logger getLog(String name) {
		return Logger.getLogger(name);
	}
	
	public static Logger getDebug() {
		return getLog("debug");
	}
	
	public static Logger getInfo() {
		return getLog("info");
	}
	
	public static Logger getWarning() {
		return getLog("warning");
	}
	
	public static Logger getError() {
		return getLog("error");
	}
}
