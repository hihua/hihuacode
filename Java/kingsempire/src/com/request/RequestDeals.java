package com.request;

import java.util.List;

import com.world.Deal;

public class RequestDeals extends RequestBase {
	private final String URL = "/deals";
	private final StringBuilder m_URL = new StringBuilder();
	private final StringBuilder m_Body = new StringBuilder();
	
	public List<Deal> request(String host, String authorization, String goodsName, Long page) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		m_URL.append("?do=public");
		m_URL.append("&goods_name=");
		m_URL.append(goodsName);
		m_URL.append("&page=");
		m_URL.append(page);
		
		String response = super.request(m_URL.toString(), null, authorization);
		if (response == null)
			return null;
		else
			return Deal.parse(response);
	}
	
	public boolean request(String host, String authorization, Long sellerCityId, String goodsName, String unitPrice, Long amount) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		
		m_Body.setLength(0);
		m_Body.append("seller_city_id=");
		m_Body.append(sellerCityId);
		m_Body.append("&goods_name=");
		m_Body.append(goodsName);
		m_Body.append("&unit_price=");
		m_Body.append(unitPrice);
		m_Body.append("&amount=");
		m_Body.append(amount);
		
		String response = super.request(m_URL.toString(), m_Body.toString(), authorization);
		if (response == null)
			return false;
		else
			return true;
	}
}
