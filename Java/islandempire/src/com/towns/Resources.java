package com.towns;

import java.util.List;
import java.util.Vector;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Resources {
	private Long maxVolume;
	private Long increasePerHour;
	private String resourceName;
	private Long resourceCount;

	public Long getMaxVolume() {
		return maxVolume;
	}

	public Long getIncreasePerHour() {
		return increasePerHour;
	}

	public String getResourceName() {
		return resourceName;
	}

	public Long getResourceCount() {
		return resourceCount;
	}

	public void setMaxVolume(Long maxVolume) {
		this.maxVolume = maxVolume;
	}

	public void setIncreasePerHour(Long increasePerHour) {
		this.increasePerHour = increasePerHour;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public void setResourceCount(Long resourceCount) {
		this.resourceCount = resourceCount;
	}
	
	public static List<Resources> parse(String response) {
		JSONObject json = JSONObject.fromObject(response);
		if (json == null || json.get("town") == null)
			return null;
		
		JSONArray arrays = json.getJSONArray("resources");
		if (arrays != null) {
			List<Resources> resources = new Vector<Resources>();
			for (int i = 0;i < arrays.size();i++) {
				JSONObject array = (JSONObject) arrays.get(i);				
				Resources resource = new Resources();
				resource.setMaxVolume((array.get("max_volume") != null) ? array.getLong("max_volume") : null);
				resource.setIncreasePerHour((array.get("increase_per_hour") != null) ? array.getLong("increase_per_hour") : null);
				resource.setResourceName((array.get("resource_name") != null) ? array.getString("resource_name") : null);
				resource.setResourceCount((array.get("resource_count") != null) ? array.getLong("resource_count") : null);
				resources.add(resource);
			}
			
			return resources;
		} else
			return null;
	}
}
