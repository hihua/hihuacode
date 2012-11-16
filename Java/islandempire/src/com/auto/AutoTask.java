package com.auto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import com.callback.CallBackTask;
import com.config.Config;
import com.entity.TownInfo;
import com.entity.Towns;
import com.request.RequestBattle;
import com.request.RequestBuildings;
import com.request.RequestEquipment;
import com.request.RequestIsland;
import com.request.RequestMessage;
import com.request.RequestRanks;
import com.request.RequestSessions;
import com.request.RequestTowns;
import com.request.RequestTransport;
import com.request.RequestWorldMaps;
import com.task.TaskBase;
import com.task.TaskMy;
import com.towns.OtherTown;
import com.towns.Resources;
import com.towns.Town;
import com.util.FileManager;
import com.util.Logs;
import com.util.Numeric;

public class AutoTask extends Thread implements CallBackTask {
	private static AutoTask Task;
	private final Logs Log = Logs.getInstance();
	private final URL m_Class = getClass().getResource("/");
	private final String m_File = "/config.xml";	
	private Config m_Config = null;
	private Towns m_MyTowns = null;	
	private TaskMy m_MyTask = null;
	
	public synchronized static AutoTask getInstance() {				
		if (Task == null)
			Task = new AutoTask();
		
		return Task;
	}
	
	private AutoTask() {
		
	}
	
	public synchronized String resetCookie(String username, String password) {
		if (m_Config == null)
			return null;
		
		String host = m_Config.getHost();
		String clientv = m_Config.getClientv();		
		String path = m_Class.getPath() + m_File;
		
		RequestSessions requestSessions = new RequestSessions();
		if (requestSessions.request(host, clientv, m_Config, username, password)) {
			String xml = Config.setConfig(m_Config);
			boolean success = FileManager.writeFile(path, false, "UTF-8", xml);
			if (success)
				return m_Config.getCookie();
			else
				return null;
		} else
			return null;
	}
	
	public synchronized boolean setCookie(String cookie) {
		if (m_Config == null)
			return false;
		
		String host = m_Config.getHost();
		String clientv = m_Config.getClientv();		
		String path = m_Class.getPath() + m_File;
		
		RequestSessions requestSessions = new RequestSessions();
		if (requestSessions.request(host, clientv, cookie, m_Config)) {
			String xml = Config.setConfig(m_Config);
			return FileManager.writeFile(path, false, "UTF-8", xml);			
		} else
			return false;
	}
	
	public String getMyTown(Long townId) {
		if (m_MyTowns == null)
			return null;
		
		List<TownInfo> towns = m_MyTowns.getTownInfos();
		if (towns == null)
			return null;
		
		for (TownInfo townInfo : towns) {
			if (townInfo.getTownId() == null)
				continue;
			
			if (townInfo.getTownId().equals(townId))
				return townInfo.getPacket();
		}
		
		return null;
	}
	
	public List<Long> getOtherTowns(String username, Long townId) {
		if (m_Config == null)
			return null;
		
		String host = m_Config.getHost();
		String clientv = m_Config.getClientv();
		String cookie = m_Config.getCookie();
		
		RequestTowns requestTowns = new RequestTowns();
		TownInfo townInfo = requestTowns.request(host, clientv, cookie, username, townId);
		if (townInfo == null)
			return null;
		
		List<Long> list = new Vector<Long>();
		list.add(townId);
		
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
	
	public String getOtherTown(String username, Long townId) {
		if (m_Config == null)
			return null;
		
		String host = m_Config.getHost();
		String clientv = m_Config.getClientv();
		String cookie = m_Config.getCookie();
		
		RequestTowns requestTowns = new RequestTowns();
		TownInfo townInfo = requestTowns.request(host, clientv, cookie, username, townId);
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
	
	public boolean postMyMessages(String to, String from, String subject, String body) {
		if (m_Config == null)
			return false;
		
		if (to == null || from == null || subject == null || body == null)
			return false;
		
		if (to.length() == 0 || from.length() == 0 || subject.length() == 0 || body.length() == 0)
			return false;
		
		String host = m_Config.getHost();
		String clientv = m_Config.getClientv();
		String cookie = m_Config.getCookie();
		
		RequestMessage requestMessage = new RequestMessage();
		String response = requestMessage.request(host, clientv, cookie, to, from, subject, body);
		if (response == null)
			return false;
		else
			return true;
	}
	
	public String getOtherMessages(String username, Long page) {
		if (m_Config == null)
			return null;
		
		String host = m_Config.getHost();
		String clientv = m_Config.getClientv();
		String cookie = m_Config.getCookie();
		
		RequestMessage requestMessage = new RequestMessage();
		return requestMessage.request(host, clientv, cookie, username, username, page);
	}
	
	public String getMyRanks(String d) {
		if (m_MyTowns == null || d == null)
			return null;
		
		if (d.equals("top_heroes"))
			return m_MyTowns.getHeroes();
		
		if (d.equals("top_users"))
			return m_MyTowns.getRanks();
		
		return null;
	}
	
	public String getOtherRanks(String username, Long userId) {
		if (m_Config == null)
			return null;
		
		String host = m_Config.getHost();
		String clientv = m_Config.getClientv();
		String cookie = m_Config.getCookie();
		
		RequestRanks requestRanks = new RequestRanks();
		return requestRanks.request(host, clientv, cookie, "my_rank", username, userId);
	}
	
	public String getIsland(String x, String y) {
		if (m_Config == null)
			return null;
		
		if (!Numeric.isNumber(x) || !Numeric.isNumber(y))
			return null;
		
		String host = m_Config.getHost();
		String clientv = m_Config.getClientv();
		String cookie = m_Config.getCookie();
		
		RequestIsland requestIsland = new RequestIsland();
		return requestIsland.request(host, clientv, cookie, x, y, m_Config.getUserId());
	}
	
	public String getWorldMaps(String x, String y) {
		if (m_Config == null)
			return null;
		
		if (!Numeric.isNumber(x) || !Numeric.isNumber(y))
			return null;
		
		String host = m_Config.getHost();
		String clientv = m_Config.getClientv();
		String cookie = m_Config.getCookie();
		
		RequestWorldMaps requestWorldMaps = new RequestWorldMaps();
		return requestWorldMaps.request(host, clientv, cookie, x, y);
	}
	
	public String getMyEquipment(Long townId) {
		if (m_MyTowns == null)
			return null;
		
		List<TownInfo> townInfos = m_MyTowns.getTownInfos();
		if (townInfos != null) {
			for (TownInfo townInfo : townInfos) {
				Long id = townInfo.getTownId();
				if (id.equals(townId))
					return townInfo.getHeroEquipment();
			}
		}
		
		return null;
	}
	
	public boolean putEquipment(Long equipmentId, Long fromIndex, Long toIndex, Long townId) {
		String host = m_Config.getHost();
		String clientv = m_Config.getClientv();
		String cookie = m_Config.getCookie();
		
		RequestEquipment requestEquipment = new RequestEquipment();
		return requestEquipment.request(host, clientv, cookie, equipmentId, fromIndex, toIndex, townId);
	}
	
	public boolean sellEquipment(Long equipmentId, Long townId) {
		String host = m_Config.getHost();
		String clientv = m_Config.getClientv();
		String cookie = m_Config.getCookie();
		
		RequestEquipment requestEquipment = new RequestEquipment();
		return requestEquipment.request(host, clientv, cookie, equipmentId, "sold_to_npc", "delete", townId);
	}
	
	public boolean upgradeEquipment(Long equipmentId, Long townId, Long safe) {
		String host = m_Config.getHost();
		String clientv = m_Config.getClientv();
		String cookie = m_Config.getCookie();
		Long userId = m_Config.getUserId();
		
		RequestEquipment requestEquipment = new RequestEquipment();
		List<Resources> resources = requestEquipment.request(host, clientv, cookie, equipmentId, safe, "enhance", 0L, userId, "put", townId);
		if (resources != null)
			return true;
		else
			return false;
	}
	
	public boolean actionsEquipment(Long equipmentId, Long iniPrice, Long totalTimeInhours, Long townId) {
		String host = m_Config.getHost();
		String clientv = m_Config.getClientv();
		String cookie = m_Config.getCookie();
		
		RequestEquipment requestEquipment = new RequestEquipment();
		return requestEquipment.request(host, clientv, cookie, equipmentId, iniPrice, totalTimeInhours, "post", townId);
	}
	
	public boolean requestBuildings(Long buildingId) {
		if (m_Config == null)
			return false;
		
		String host = m_Config.getHost();
		String clientv = m_Config.getClientv();
		String cookie = m_Config.getCookie();
		
		RequestBuildings requestBuildings = new RequestBuildings();
		return requestBuildings.request(host, clientv, cookie, buildingId);		
	}
	
	public String requestBattle(String mailId) {
		if (m_Config == null)
			return null;
		
		String host = m_Config.getHost();
		String clientv = m_Config.getClientv();
		String cookie = m_Config.getCookie();
		Long userId = m_Config.getUserId();
		
		RequestBattle requestBattle = new RequestBattle();
		return requestBattle.request(host, clientv, cookie, userId, mailId);		
	}
	
	public boolean requestTransport(Long fromTownId, Long toTownId, HashMap<String, Long> resources) {
		if (m_Config == null)
			return false;
		
		String host = m_Config.getHost();
		String clientv = m_Config.getClientv();
		String cookie = m_Config.getCookie();
		
		RequestTransport transportTown = new RequestTransport();
		if (transportTown.request(host, clientv, cookie, fromTownId, toTownId, resources) == null)
			return false;
		else
			return true;
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
