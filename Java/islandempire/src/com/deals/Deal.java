package com.deals;

import java.util.List;
import java.util.Vector;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Deal {
	private String goodsName;
	private Long sellerTownId;
	private Double sellerPrice;
	private Long id;
	private Long count;
	private Long sellerId;

	public String getGoodsName() {
		return goodsName;
	}

	public Long getSellerTownId() {
		return sellerTownId;
	}

	public Double getSellerPrice() {
		return sellerPrice;
	}

	public Long getId() {
		return id;
	}

	public Long getCount() {
		return count;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public void setSellerTownId(Long sellerTownId) {
		this.sellerTownId = sellerTownId;
	}

	public void setSellerPrice(Double sellerPrice) {
		this.sellerPrice = sellerPrice;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	
	public static List<Deal> parse(String response) {
		JSONObject json = JSONObject.fromObject(response);
		if (json == null)
			return null;
		
		JSONArray arrays = json.getJSONArray("deals");
		if (arrays != null) {
			List<Deal> deals = new Vector<Deal>();
			for (int i = 0; i < arrays.size(); i++) {
				JSONObject array = (JSONObject) arrays.get(i);
				Deal deal = new Deal();
				deal.setGoodsName((array.get("goods_name") != null) ? array.getString("goods_name") : null);
				deal.setSellerTownId((array.get("seller_town_id") != null) ? array.getLong("seller_town_id") : null);
				deal.setSellerPrice((array.get("seller_price") != null) ? array.getDouble("seller_price") : null);
				deal.setId((array.get("id") != null) ? array.getLong("id") : null);
				deal.setCount((array.get("count") != null) ? array.getLong("count") : null);
				deal.setSellerId((array.get("seller_id") != null) ? array.getLong("seller_id") : null);
				deals.add(deal);
			}
			
			return deals;
		} else
			return null;
	}
}
