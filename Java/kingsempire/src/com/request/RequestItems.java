package com.request;

import java.util.List;

import com.entity.ItemInfo;
import com.util.DateTime;
import com.world.Item;

public class RequestItems extends RequestBase {
	private final String URL = "/items";
	private final StringBuilder m_URL = new StringBuilder();
	private final StringBuilder m_Body = new StringBuilder();
	
	public ItemInfo request(String host, String cookie, String authorization) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		
		String response = super.request(m_URL.toString(), null, cookie, authorization);
		if (response == null)
			return null;
		
		List<Item> items = Item.parse(response);
		if (items == null)
			return null;
		
		ItemInfo itemInfo = new ItemInfo();
		itemInfo.setItems(items);
		itemInfo.setPacket(response);
		itemInfo.setUpdateTime(DateTime.getNow());
		return itemInfo;
	}
	
	public boolean request(String host, String cookie, String authorization, Long cityId, Long itemType, Long number) {
		m_URL.setLength(0);
		m_URL.append(host);
		m_URL.append(URL);
		m_URL.append("/");
		m_URL.append(cityId);
		
		m_Body.setLength(0);
		m_Body.append("do=use_items");
		m_Body.append("&add_resource=0");
		m_Body.append("&_method=put");
		m_Body.append("&item_type=");
		m_Body.append(itemType);
		m_Body.append("&number=");
		m_Body.append(number);
		
		String response = super.request(m_URL.toString(), m_Body.toString(), cookie, authorization);
		if (response == null)
			return false;
		else
			return true;
	}
}
