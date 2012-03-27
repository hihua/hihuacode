package com.world;

import java.util.List;
import java.util.Vector;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Deal {
	private String goodsName;
	private Long amount;
	private String unitPrice;
	private Long id;
	
	public String getGoodsName() {
		return goodsName;
	}
	
	public Long getAmount() {
		return amount;
	}
	
	public String getUnitPrice() {
		return unitPrice;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public static List<Deal> parse(String response) {
		JSONObject json = JSONObject.fromObject(response);		
		JSONArray deals = json.getJSONArray("deals_inquiry");
		if (deals != null) {
			List<Deal> list = new Vector<Deal>(); 
			for (int i = 0;i < deals.size();i++) {
				try {
					JSONObject object = (JSONObject)deals.get(i);
					Deal deal = new Deal();
					deal.setGoodsName((object.get("goods_name") != null) ? object.getString("goods_name") : null);
					deal.setAmount((object.get("amount") != null) ? object.getLong("amount") : null);
					deal.setUnitPrice((object.get("unit_price") != null) ? object.getString("unit_price") : null);
					deal.setId((object.get("id") != null) ? object.getLong("id") : null);
					list.add(deal);
				} catch (Exception e) {
					
				}
			}
			
			if (list.size() > 0)
				return list;
			else
				return null;
		} else
			return null;
	}		
}
