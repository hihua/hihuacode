package com.request;

import com.entity.CityInfo;
import com.util.DateTime;
import com.world.City;

public class RequestCities extends RequestBase {	
	private final String URL = "/cities/";	
	private final StringBuilder m_URL = new StringBuilder();
	private final StringBuilder m_Body = new StringBuilder();
	
	public CityInfo request(String host, String cookie, String authorization, Long cityId) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		m_URL.append(cityId);
		
		String response = super.request(m_URL.toString(), null, cookie, authorization);
		if (response == null)
			return null;
				
		City city = City.parse(response);
		if (city == null)
			return null;
					
		CityInfo cityInfo = new CityInfo();
		cityInfo.setCity(city);
		cityInfo.setPacket(response);
		cityInfo.setUpdateTime(DateTime.getNow());
		return cityInfo;
	}
	
	public boolean request(String host, String cookie, String authorization, Long cityId, String type, Long number) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		m_URL.append(cityId);
		m_URL.append(".json");
		
		m_Body.setLength(0);
		m_Body.append("do=configure_workers");
		m_Body.append("&_method=put");
		m_Body.append("&type=");
		m_Body.append(type);
		m_Body.append("&number=");
		m_Body.append(number);
		
		String response = super.request(m_URL.toString(), m_Body.toString(), cookie, authorization);
		if (response == null)
			return false;
		else
			return true;
	}
}
