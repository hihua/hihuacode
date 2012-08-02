package com.request;

import java.util.List;

import com.towns.Resources;

public class RequestEquipment extends RequestParent {
	private final String Info = "/towns/%d.json?do=hero_and_equipment_info";
	private final String Use = "/equipments/%d.json";
	private final StringBuilder m_URL = new StringBuilder();
	private final StringBuilder m_Body = new StringBuilder();
	
	public String request(String host, String clientv, String cookie, Long townId) {
		String url = String.format(Info, townId);
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(url);
		
		return requestUrl(m_URL.toString(), clientv, cookie, null);		 
	}
	
	public List<Resources> request(String host, String clientv, String cookie, Long equipmentId, Long townId, String method, String use) {
		String url = String.format(Use, equipmentId);		
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(url);
		
		m_Body.setLength(0);
		m_Body.append("_method=");
		m_Body.append(method);
		m_Body.append("&town_id=");
		m_Body.append(townId);
		m_Body.append("&do=");
		m_Body.append(use);
		
		String response = requestUrl(m_URL.toString(), clientv, cookie, m_Body.toString());
		if (response == null)
			return null;

		return Resources.parse(response);
	}
}
