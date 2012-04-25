package com.request;

import com.entity.TownInfo;
import com.towns.Town;
import com.util.DateTime;

public class RequestTowns extends RequestParent {	
	private final String URL = "/towns/%d.json";
	private final StringBuilder m_URL = new StringBuilder();
	
	public TownInfo request(String host, String clientv, String cookie, Long townId) {
		String url = String.format(URL, townId);
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(url);
		
		String response = requestUrl(m_URL.toString(), clientv, cookie, null);
		if (response == null)
			return null;
		else {
			Town town = Town.parse(response);
			if (town == null)
				return null;
			else {
				TownInfo townInfo = new TownInfo();
				townInfo.setTownId(townId);
				townInfo.setTown(town);
				townInfo.setPacket(response);
				townInfo.setUpdateTime(DateTime.getNow());
				return townInfo;
			}
		}
	}
	
	public TownInfo request(String host, String clientv, String cookie, String username, Long townId) {
		String url = String.format(URL, townId);
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(url);
		
		String response = requestUrl(m_URL.toString(), clientv, cookie, username, null);
		if (response == null)
			return null;
		else {
			Town town = Town.parse(response);
			if (town == null)
				return null;
			else {
				TownInfo townInfo = new TownInfo();
				townInfo.setTownId(townId);
				townInfo.setTown(town);
				townInfo.setPacket(response);
				townInfo.setUpdateTime(DateTime.getNow());
				return townInfo;
			}
		}
	}
}
