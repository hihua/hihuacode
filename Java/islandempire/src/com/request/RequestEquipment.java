package com.request;

import java.util.List;

import com.towns.Resources;

public class RequestEquipment extends RequestParent {
	private final String Info = "/towns/%d.json?do=hero_and_equipment_info";
	private final String Equipments = "/equipments/%d.json";
	private final String Actions = "/auctions.json";
	private final StringBuilder m_URL = new StringBuilder();
	private final StringBuilder m_Body = new StringBuilder();
	
	public String request(String host, String clientv, String cookie, Long townId) {
		String url = String.format(Info, townId);
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(url);
		
		return requestUrl(m_URL.toString(), clientv, cookie, null);		 
	}
	
	public boolean request(String host, String clientv, String cookie, Long equipmentId, Long fromIndex, Long toIndex, Long townId) {
		String url = String.format(Equipments, equipmentId);		
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(url);
		
		m_Body.setLength(0);
		m_Body.append("from_index=");
		m_Body.append(fromIndex);
		m_Body.append("&to_index=");
		m_Body.append(toIndex);
		m_Body.append("&from=equipment");		
		m_Body.append("&do=change_position");		
		m_Body.append("&_method=put");
		m_Body.append("&to=hero");
		m_Body.append("&town_id=");
		m_Body.append(townId);
		
		String response = requestUrl(m_URL.toString(), clientv, cookie, m_Body.toString());
		if (response == null)
			return false;
		else
			return true;
	}
	
	public List<Resources> request(String host, String clientv, String cookie, Long equipmentId, Long safe, String d, Long luck, Long userId, String method, Long townId) {
		String url = String.format(Equipments, equipmentId);		
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(url);
		
		m_Body.setLength(0);
		m_Body.append("safe=");
		m_Body.append(safe);
		m_Body.append("&do=");
		m_Body.append(d);
		m_Body.append("&luck=");
		m_Body.append(luck);
		m_Body.append("&user_id=");
		m_Body.append(userId);
		m_Body.append("&_method=");
		m_Body.append(method);
		m_Body.append("&town_id=");
		m_Body.append(townId);
				
		String response = requestUrl(m_URL.toString(), clientv, cookie, m_Body.toString());
		if (response == null)
			return null;

		return Resources.parse(response);
	}
	
	public boolean request(String host, String clientv, String cookie, Long equipmentId, String d, String method, Long townId) {
		String url = String.format(Equipments, equipmentId);		
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(url);
		
		m_Body.setLength(0);		
		m_Body.append("do=");
		m_Body.append(d);
		m_Body.append("&_method=");
		m_Body.append(method);
		m_Body.append("&confirm=0");
		m_Body.append("&town_id=");
		m_Body.append(townId);
		
		String response = requestUrl(m_URL.toString(), clientv, cookie, m_Body.toString());
		if (response == null)
			return false;
		else
			return true;
	}
	
	public boolean request(String host, String clientv, String cookie, Long equipmentId, Long iniPrice, Long totalTimeInhours, String method, Long townId) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(Actions);
		
		m_Body.setLength(0);
		m_Body.append("equipment_id=");
		m_Body.append(equipmentId);
		m_Body.append("&ini_price=");
		m_Body.append(iniPrice);
		m_Body.append("&total_time_inhours=");
		m_Body.append(totalTimeInhours);
		m_Body.append("&_method=");
		m_Body.append(method);
		m_Body.append("&town_id=");
		m_Body.append(townId);

		String response = requestUrl(m_URL.toString(), clientv, cookie, m_Body.toString());
		if (response == null)
			return false;
		else
			return true;
	}
}
