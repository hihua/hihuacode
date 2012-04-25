package com.request;

import java.util.HashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class RequestUpgrade extends RequestParent {
	private final String URL = "/buildings/%d.json?do=upgrade_info";
	private final StringBuilder m_URL = new StringBuilder();
	
	public HashMap<String, Long> request(String host, String clientv, String cookie, Long buildingId) {
		String url = String.format(URL, buildingId);
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(url);
		
		String response = requestUrl(m_URL.toString(), clientv, cookie, null);
		if (response == null)
			return null;
		else			
			return parse(response);		
	}
	
	private static HashMap<String, Long> parse(String response) {
		JSONObject json = JSONObject.fromObject(response);
		if (json == null)
			return null;
		
		JSONArray arrays = json.getJSONArray("resources");
		if (arrays != null) {
			HashMap<String, Long> resources = new HashMap<String, Long>();
			for (int i = 0; i < arrays.size(); i++) {
				JSONObject array = (JSONObject) arrays.get(i);
				if (array.get("name") != null && array.get("count") != null) {
					String name = array.getString("name");
					Long count = array.getLong("count");
					resources.put(name, count);
				}
			}
			
			return resources;
		} else
			return null;
	}
}
