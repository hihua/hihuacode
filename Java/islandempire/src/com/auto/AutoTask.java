package com.auto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;
import java.util.Vector;

import com.callback.CallBackTask;
import com.config.Config;
import com.entity.TownInfo;
import com.entity.Towns;
import com.request.RequestMessage;
import com.request.RequestTowns;
import com.task.TaskBase;
import com.task.TaskMy;
import com.towns.OtherTown;
import com.towns.Town;
import com.util.FileManager;
import com.util.Logs;

public class AutoTask extends Thread implements CallBackTask {
	private static AutoTask Task;
	private final Logs Log = Logs.getInstance();
	private final URL m_Class = getClass().getResource("/");
	private final String m_File = "/config.xml";	
	private Config m_Config = null;
	private Towns m_MyTowns = null;	
	private TaskMy m_MyTask = null;
	private final RequestTowns m_RequestTowns = new RequestTowns();
	private final RequestMessage m_RequestMessage = new RequestMessage();
	
	public synchronized static AutoTask getInstance() {				
		if (Task == null)
			Task = new AutoTask();
		
		return Task;
	}
	
	private AutoTask() {
		
	}
	
	public String getMyTown(Long id) {
		if (m_MyTowns == null)
			return null;
		
		List<TownInfo> towns = m_MyTowns.getTownInfos();
		if (towns == null)
			return null;
		
		for (TownInfo townInfo : towns) {
			if (townInfo.getId() == null)
				continue;
			
			if (townInfo.getId().equals(id))
				return townInfo.getPacket();
		}
		
		return null;
	}
	
	public List<Long> getOtherTowns(String username, Long id) {
		if (m_Config == null)
			return null;
		
		String host = m_Config.getHost();
		String clientv = m_Config.getClientv();
		String cookie = m_Config.getCookie();
		
		TownInfo townInfo = m_RequestTowns.request(host, clientv, cookie, username, id);
		if (townInfo == null)
			return null;
		
		List<Long> list = new Vector<Long>();
		list.add(id);
		
		Town town = townInfo.getTown();
		List<OtherTown> otherTowns = town.getOtherTowns();
		if (otherTowns != null) {
			for (OtherTown otherTown : otherTowns) {
				if (otherTown.getId() != null)
					list.add(otherTown.getId());
			}
		}
		
		return list;
	}
	
	public String getOtherTown(String username, Long id) {
		if (m_Config == null)
			return null;
		
		String host = m_Config.getHost();
		String clientv = m_Config.getClientv();
		String cookie = m_Config.getCookie();
		
		TownInfo townInfo = m_RequestTowns.request(host, clientv, cookie, username, id);
		if (townInfo == null)
			return null;
		else
			return townInfo.getPacket();
	}
	
	public String getMyMessages() {
		if (m_MyTowns == null)
			return null;
		
		return m_MyTowns.getMessage();
	}
	
	public String getOtherMessages(String username, Long page) {
		if (m_Config == null)
			return null;
		
		String host = m_Config.getHost();
		String clientv = m_Config.getClientv();
		String cookie = m_Config.getCookie();
		
		return m_RequestMessage.request(host, clientv, cookie, username, username, page);
	}
	
	public String getConfig() {
		String path = m_Class.getPath() + m_File;
		return Config.getConfig(path);
	}
	
	private void closeInputStream(InputStream in) {
		if (in == null)
			return;
		
		try {
			in.close();
		} catch (IOException e) {
			
		}
	}
	
	private void closeOutputStream(OutputStream out) {
		if (out == null)
			return;
		
		try {
			out.close();
		} catch (IOException e) {
			
		}
	}
	
	public void run() {					
		newTask();
    }
	
	private void newTask() {
		while (true) {
			try {
				sleep(1000 * 60);
			} catch (InterruptedException e) {
				
			}
			
			String path = m_Class.getPath() + m_File;						
			Config configs = Config.getConfigs(path);
			if (configs == null)
				continue;
			
			m_Config = configs;
			m_MyTask = new TaskMy("mytask", configs, this);
			m_MyTask.start();
			break;
		}
	}
	
	public synchronized boolean setConfig(InputStream in) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int len = 0;
		
		try {
			while ((len = in.read(buffer)) > 0)
				outputStream.write(buffer, 0, len);
		} catch (IOException e) {			
			closeOutputStream(outputStream);
			return false;
		}
		
		if (outputStream.size() == 0) {
			closeOutputStream(outputStream);
			return false;
		}
		
		ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
		
		Config configs = Config.getConfigs(inputStream);
		if (configs == null) {
			closeInputStream(inputStream);
			closeOutputStream(outputStream);
			return false;
		}
		
		String path = m_Class.getPath() + m_File;
		
		try {
			String s = outputStream.toString("UTF-8");
			boolean success = FileManager.writeFile(path, false, "UTF-8", s);
			closeInputStream(inputStream);
			closeOutputStream(outputStream);
			
			if (!success)
				return false;
		} catch (UnsupportedEncodingException e) {
			closeInputStream(inputStream);
			closeOutputStream(outputStream);
			return false;
		}
						
		m_Config = configs;
		
		if (m_MyTask != null)
			m_MyTask.setConfig(configs);
		else {
			m_MyTask = new TaskMy("mytask", configs, this);
			if (m_MyTask.checkStatus(configs))
				m_MyTask.start();
		}
		
		return true;
	}

	@Override
	public void onCancel(String taskName, TaskBase taskBase) {
		if (taskBase == m_MyTask)
			m_MyTask = null;
		
		Log.writeLogs(taskName + " cancel");
	}

	@Override
	public void onTowns(Towns towns) {
		m_MyTowns = towns;
	}
}
