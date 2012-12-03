package com.util;

import java.io.File;
import java.net.URL;

public class Logs {
	private static Logs Log = null;
	private final URL m_Class = getClass().getResource("/");
	private final String m_Path = m_Class.getPath() + "../../logs/";
	private final String m_File = "logs.txt";
	private final StringBuilder m_Logs = new StringBuilder();
	
	public synchronized static Logs getInstance() {				
		if (Log == null)
			Log = new Logs();
		
		return Log;
	}
	
	private Logs() {
				
	}
		
	public synchronized void writeLogs(String content) {		
		File dir = new File(m_Path);
		if (!dir.exists() && !dir.mkdirs())
			return;
						
		String path = m_Path + m_File;
		File file = new File(path);
		if (file.exists() && file.length() > 1024 * 5000 && !file.delete())
			return;
		
		m_Logs.setLength(0);
		m_Logs.append(DateTime.getNow());
		m_Logs.append("\t");
		m_Logs.append(content);
		m_Logs.append("\r\n");
				
		FileManager.writeFile(path, true, "UTF-8", m_Logs.toString());
	}
}
