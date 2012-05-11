package com.request;

import java.util.List;

import com.hero.Equipment;

public class RequestEquipment extends RequestParent {
	private final String Info = "/towns/%d.json?do=hero_and_equipment_info";
	private final String Use = "/equipments/%d.json";
	private final StringBuilder m_URL = new StringBuilder();
	private final StringBuilder m_Body = new StringBuilder();
	
	public List<Equipment> request(String host, String clientv, String cookie, Long townId) {
		String url = String.format(Info, townId);
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(url);
		
		String response = requestUrl(m_URL.toString(), clientv, cookie, null);
		if (response == null)
			return null;
		else
			return Equipment.parse(response);
	}
	
	public boolean request(String host, String clientv, String cookie, Long equipmentId, Long townId) {
		String url = String.format(Use, equipmentId);		
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(url);
		
		m_Body.setLength(0);
		m_Body.append("_method=delete");
		m_Body.append("&town_id=");
		m_Body.append(townId);
		m_Body.append("&do=use");
		
		String response = requestUrl(m_URL.toString(), clientv, cookie, m_Body.toString());
		if (response == null)
			return false;
		else
			return true;
	}
}
