package com.request;

import java.util.List;

import com.deals.Deal;

public class RequestDeals extends RequestParent {
	private final String URL = "/deals.json";
	private final StringBuilder m_URL = new StringBuilder();
	private final StringBuilder m_Body = new StringBuilder();
	
	public List<Deal> request(String host, String clientv, String cookie, String goodsName, Long page) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		m_URL.append("?do=public");
		m_URL.append("&goods_name=");
		m_URL.append(goodsName);
		m_URL.append("&page=");
		m_URL.append(page);
		
		String response = super.request(m_URL.toString(), clientv, cookie, null);
		if (response == null)
			return null;
		else
			return Deal.parse(response);
	}
	
	public boolean request(String host, String clientv, String cookie, Long sellerTownId, String goodsName, Double price, Long count) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		
		m_Body.setLength(0);
		m_Body.append("seller_town_id=");
		m_Body.append(sellerTownId);
		m_Body.append("&goods_name=");
		m_Body.append(goodsName);
		m_Body.append("&price=");
		m_Body.append(price);
		m_Body.append("&count=");
		m_Body.append(count);
		
		String response = super.request(m_URL.toString(), clientv, cookie, m_Body.toString());
		if (response == null)
			return false;
		else
			return true;
	}
}
