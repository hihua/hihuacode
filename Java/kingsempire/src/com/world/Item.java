package com.world;

import java.util.List;
import java.util.Vector;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Item {
	private String name;
	private String imageUrl;
	private Boolean usable;
	private Long count;
	private Boolean canUseMore;
	private String note;
	private Long effectiveTime;
	private String desc;
	private Long itemType;
	
	public String getName() {
		return name;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public Boolean getUsable() {
		return usable;
	}
	
	public Long getCount() {
		return count;
	}
	
	public Boolean getCanUseMore() {
		return canUseMore;
	}
	
	public String getNote() {
		return note;
	}
	
	public Long getEffectiveTime() {
		return effectiveTime;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public Long getItemType() {
		return itemType;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public void setUsable(Boolean usable) {
		this.usable = usable;
	}
	
	public void setCount(Long count) {
		this.count = count;
	}
	
	public void setCanUseMore(Boolean canUseMore) {
		this.canUseMore = canUseMore;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public void setEffectiveTime(Long effectiveTime) {
		this.effectiveTime = effectiveTime;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public void setItemType(Long itemType) {
		this.itemType = itemType;
	}
	
	public static List<Item> parse(String response) {
		JSONObject json = JSONObject.fromObject(response);		
		JSONArray items = json.getJSONArray("items");
		if (items != null) {
			List<Item> list = new Vector<Item>(); 
			for (int i = 0;i < items.size();i++) {
				try {
					JSONObject object = (JSONObject)items.get(i);
					Item item = new Item();
					item.setName((object.get("name") != null) ? object.getString("name") : null);
					item.setImageUrl((object.get("image_url") != null) ? object.getString("image_url") : null);
					item.setUsable((object.get("usable") != null) ? object.getBoolean("usable") : null);
					item.setCount((object.get("count") != null) ? object.getLong("count") : null);
					item.setCanUseMore((object.get("can_use_more") != null) ? object.getBoolean("can_use_more") : null);
					item.setNote((object.get("note") != null) ? object.getString("note") : null);
					item.setEffectiveTime((object.get("effective_time") != null) ? object.getLong("effective_time") : null);
					item.setDesc((object.get("desc") != null) ? object.getString("desc") : null);
					item.setItemType((object.get("item_type") != null) ? object.getLong("item_type") : null);
					list.add(item);
				} catch (Exception e) {
					
				}
			}
			
			if (list.size() > 0)
				return list;
			else
				return null;
		}  else
			return null;
	}
}
