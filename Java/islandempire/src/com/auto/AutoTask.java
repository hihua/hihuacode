package com.auto;

import java.net.URL;

import com.util.Logs;

public class AutoTask extends Thread {
	private static AutoTask Task;
	private final Logs Log = Logs.getInstance();
	private final URL m_Class = getClass().getResource("/");
	private final String m_File = "/config.xml";
	
	public synchronized static AutoTask getInstance() {				
		if (Task == null)
			Task = new AutoTask();
		
		return Task;
	}
	
	private AutoTask() {
		
	}
}
