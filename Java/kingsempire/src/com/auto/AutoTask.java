package com.auto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Hashtable;
import java.util.List;

import com.callback.CallBackTask;
import com.config.Config;
import com.entity.Cities;
import com.entity.CityInfo;
import com.entity.ItemInfo;
import com.request.RequestBuildings;
import com.request.RequestCities;
import com.request.RequestItems;
import com.request.RequestMessages;
import com.request.RequestOthers;
import com.request.RequestRecruit;
import com.request.RequestSkills;
import com.request.RequestWorldMaps;
import com.task.TaskMy;
import com.util.FileManager;
import com.util.Logs;
import com.world.City;

public class AutoTask extends Thread implements CallBackTask {	
	private static AutoTask Task;
	private final Logs Log = Logs.getInstance();
	private final URL m_Class = getClass().getResource("/");
	private final String m_File = "/config.xml";
	private final Hashtable<String, TaskMy> m_Task = new Hashtable<String, TaskMy>();
	private final Hashtable<String, Cities> m_Cities = new Hashtable<String, Cities>();	
	private final RequestItems m_RequestItems = new RequestItems();
	private final RequestOthers m_RequestOthers = new RequestOthers();
	private final RequestWorldMaps m_RequestWorldMaps = new RequestWorldMaps();
	private final RequestMessages m_RequestMessages = new RequestMessages();
	private final RequestCities m_RequestCities = new RequestCities();
	private final RequestBuildings m_RequestBuildings = new RequestBuildings();
	private final RequestSkills m_RequestSkills = new RequestSkills();
	private final RequestRecruit m_RequestRecruit = new RequestRecruit();
	private List<Config> m_Config = null;
		
	public synchronized static AutoTask getInstance() {				
		if (Task == null)
			Task = new AutoTask();
		
		return Task;
	}
	
	private AutoTask() {
		
	}
		
	public Hashtable<Long, String> getCities(String userName) {
		if (m_Cities.containsKey(userName)) {
			Cities cities = m_Cities.get(userName);			
			List<CityInfo> cityInfos = cities.getCityInfos();
			if (cityInfos != null) {
				Hashtable<Long, String> table = new Hashtable<Long, String>();
				for (CityInfo cityInfo : cityInfos) {
					City city = cityInfo.getCity();					
					if (city == null || city.getId() == null || city.getName() == null)
						continue;
					
					table.put(city.getId(), city.getName());
				}
				
				return table;
			} else
				return null;
		} else
			return null;
	}
	
	public String getCityInfo(String userName, long cityId) {
		if (m_Cities.containsKey(userName)) {
			Cities cities = m_Cities.get(userName);			
			List<CityInfo> cityInfos = cities.getCityInfos();
			if (cityInfos != null) {				
				for (CityInfo cityInfo : cityInfos) {
					City city = cityInfo.getCity();
					if (city == null || city.getId() == null)
						continue;
					
					if (cityId == city.getId())
						return cityInfo.getPacket();
				}
				
				return null;
			} else
				return null;
		} else
			return null;
	}
	
	public String getUpdateTime(String userName, long cityId) {
		if (m_Cities.containsKey(userName)) {
			Cities cities = m_Cities.get(userName);			
			List<CityInfo> cityInfos = cities.getCityInfos();
			if (cityInfos != null) {				
				for (CityInfo cityInfo : cityInfos) {
					City city = cityInfo.getCity();
					if (city == null || city.getId() == null)
						continue;
					
					if (cityId == city.getId())
						return cityInfo.getUpdateTime();
				}
				
				return null;
			} else
				return null;
		} else
			return null;
	}
	
	public String getCity(String userName, String owner, long cityId) {
		if (m_Config == null)
			return null;
		
		for (Config config : m_Config) {
			if (config.getUserName() == null)
				continue;
			
			if (!config.getUserName().equals(userName))
				continue;
			
			String host = config.getHost();
			String cookie = config.getCookie();
			String s = owner + ":12345678";
			
			try {
				s = new sun.misc.BASE64Encoder().encode(s.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {				
				return null;				
			}
			
			String authorization = "Basic " + s;
			CityInfo cityInfo = m_RequestCities.request(host, cookie, authorization, cityId);
			if (cityInfo == null)
				return null;
			else
				return cityInfo.getPacket();
		}
		
		return null;
	}
	
	public boolean configureWorkers(String userName, Long cityId, String type, Long number) {
		if (m_Config == null)
			return false;
		
		for (Config config : m_Config) {
			if (config.getUserName() == null)
				continue;
			
			if (!config.getUserName().equals(userName))
				continue;
			
			String host = config.getHost();						
			String authorization = config.getAuthorization();
			String cookie = config.getCookie();
			
			return m_RequestCities.request(host, cookie, authorization, cityId, type, number);
		}
		
		return false;
	}
	
	public String getMessages(String userName) {
		if (m_Cities.containsKey(userName)) {
			Cities cities = m_Cities.get(userName);
			if (cities != null)
				return cities.getMessage();
			else
				return null;
		} else
			return null;
	}
	
	public String getMessages(String userName, String to, Long page) {
		if (m_Config == null)
			return null;
		
		for (Config config : m_Config) {
			if (!config.getUserName().equals(userName))
				continue;
			
			String host = config.getHost();
			String cookie = config.getCookie();
			String s = to + ":12345678";
			
			try {
				s = new sun.misc.BASE64Encoder().encode(s.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {				
				return null;				
			}
			
			String authorization = "Basic " + s;						
			return m_RequestMessages.request(host, cookie, authorization, to, page);
		}
				
		return null;
	}
	
	public String getItems(String userName) {
		if (m_Cities.containsKey(userName)) {
			Cities cities = m_Cities.get(userName);
			if (cities != null && cities.getItemInfos() != null) {
				ItemInfo itemInfo = cities.getItemInfos();
				return itemInfo.getPacket();
			} else
				return null;
		} else
			return null;
	}
	
	public String getItems(String userName, String owner) {
		if (m_Config == null)
			return null;
		
		for (Config config : m_Config) {
			if (config.getUserName() == null)
				continue;
			
			if (!config.getUserName().equals(userName))
				continue;
			
			String host = config.getHost();
			String s = owner + ":12345678";
			
			try {
				s = new sun.misc.BASE64Encoder().encode(s.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {				
				return null;				
			}
			
			String cookie = config.getCookie();
			String authorization = "Basic " + s;
			ItemInfo itemInfo = m_RequestItems.request(host, cookie, authorization);
			if (itemInfo == null)
				return null;
			else
				return itemInfo.getPacket();
		}
		
		return null;
	}
	
	public boolean useItems(String userName, Long cityId, Long itemType, Long number) {
		if (m_Config == null)
			return false;
		
		for (Config config : m_Config) {
			if (!config.getUserName().equals(userName))
				continue;
			
			String host = config.getHost();
			String cookie = config.getCookie();
			String authorization = config.getAuthorization();
			return m_RequestItems.request(host, cookie, authorization, cityId, itemType, number);
		}
		
		return false;
	}
	
	public String getConfig() {
		String path = m_Class.getPath() + m_File;
		return Config.getConfig(path);
	}
	
	public String getWorldMaps(String userName, Long x, Long y, Long width) {		
		if (m_Config == null)
			return null;
		
		for (Config config : m_Config) {
			if (!config.getUserName().equals(userName))
				continue;
			
			String host = config.getHost();
			String cookie = config.getCookie();
			String authorization = config.getAuthorization();
						
			return m_RequestWorldMaps.request(host, cookie, authorization, x, y, width);
		}
		
		return null;
	}
	
	public boolean upgradeBuildings(String userName, Long buildingId) {
		if (m_Config == null)
			return false;
		
		for (Config config : m_Config) {
			if (!config.getUserName().equals(userName))
				continue;
			
			String host = config.getHost();
			String cookie = config.getCookie();
			String authorization = config.getAuthorization();
			
			return m_RequestBuildings.request(host, cookie, authorization, buildingId);
		}
		
		return false;
	}
	
	public boolean upgradeSkills(String userName, Long skillId) {
		if (m_Config == null)
			return false;
		
		for (Config config : m_Config) {
			if (!config.getUserName().equals(userName))
				continue;
			
			String host = config.getHost();
			String cookie = config.getCookie();
			String authorization = config.getAuthorization();
			
			return m_RequestSkills.request(host, cookie, authorization, skillId);
		}
		
		return false;
	}
	
	public boolean recruit(String userName, Long cityId, String soldierName, Long count) {
		if (m_Config == null)
			return false;
		
		for (Config config : m_Config) {
			if (!config.getUserName().equals(userName))
				continue;
			
			String host = config.getHost();
			String cookie = config.getCookie();
			String authorization = config.getAuthorization();
			
			return m_RequestRecruit.request(host, cookie, authorization, cityId, soldierName, count);
		}
		
		return false;
	}
	
	public boolean requestOthers(String userName, String url, InputStream in) {
		if (m_Config == null)
			return false;
		
		for (Config config : m_Config) {
			if (!config.getUserName().equals(userName))
				continue;
			
			String host = config.getHost();
			String cookie = config.getCookie();
			String authorization = config.getAuthorization();
			
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
			
			try {
				String body = outputStream.toString("UTF-8");
				closeOutputStream(outputStream);
				return m_RequestOthers.request(host, cookie, authorization, url, body);
			} catch (UnsupportedEncodingException e) {
				closeOutputStream(outputStream);
				return false;
			}			
		}
		
		return false;
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
			List<Config> configs = Config.getConfigs(path);
			if (configs == null)
				continue;
			
			m_Config = configs;							
			for (Config config : configs) {
				String userName = config.getUserName();
				if (!m_Task.containsKey(userName)) {
					TaskMy task = new TaskMy(userName, config, this);
					task.start();
					m_Task.put(userName, task);
				}
			}
			
			break;
		}		
	}		
	
	public boolean setConfig(InputStream in) {
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
		
		List<Config> configs = Config.getConfigs(inputStream);
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
		
		for (Config config : configs) {
			String userName = config.getUserName();
			if (m_Task.containsKey(userName)) {
				TaskMy task = m_Task.get(userName);
				task.setConfig(config);
			} else {
				TaskMy task = new TaskMy(userName, config, this);
				if (task.checkStatus(config)) {
					task.start();
					m_Task.put(userName, task);
					Log.writeLogs(userName + " start");
				}
			}
		}
		
		return true;
	}

	@Override
	public void onCancel(String taskName) {
		if (m_Task.containsKey(taskName)) {
			m_Task.remove(taskName);
			Log.writeLogs(taskName + " cancel");
		}
	}

	@Override
	public void onCities(String userName, Cities cities) {
		m_Cities.put(userName, cities);
	}
}
