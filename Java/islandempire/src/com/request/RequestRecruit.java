package com.request;

import java.util.List;

import com.soldier.Recruit;
import com.towns.Resources;

public class RequestRecruit extends RequestParent {
	private final String URL = "/recruit_events";
	private final StringBuilder m_URL = new StringBuilder();
	private final StringBuilder m_Body = new StringBuilder();
	
	public Recruit request(String host, String clientv, String cookie, Long townId, String soldierName) {		
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		m_URL.append("/");
		m_URL.append(townId);
		m_URL.append(".json");
		m_URL.append("?");
		m_URL.append("soldier_name=");
		m_URL.append(soldierName);
		
		String response = requestUrl(m_URL.toString(), clientv, cookie, null);
		if (response == null)
			return null;
		else
			return Recruit.parse(response);
	}
	
	public List<Resources> request(String host, String clientv, String cookie, Long townId, String soldierName, Long count) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		m_URL.append(".json");
		
		m_Body.setLength(0);
		m_Body.append("town_id=");
		m_Body.append(townId);
		m_Body.append("&soldier_name=");
		m_Body.append(soldierName);
		m_Body.append("&count=");
		m_Body.append(count);
		
		String response = requestUrl(m_URL.toString(), clientv, cookie, null);
		if (response == null)
			return null;
		else
			return Resources.parse(response);
	}
}
