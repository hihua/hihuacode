package com.island;

import java.util.List;
import java.util.Vector;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WorldMap {
	private Long x;
	private Long resourceType;
	private Long y;
	private Long townCount;
	private Long id;
	private Long islandNumber;

	public Long getX() {
		return x;
	}

	public Long getResourceType() {
		return resourceType;
	}

	public Long getY() {
		return y;
	}

	public Long getTownCount() {
		return townCount;
	}

	public Long getId() {
		return id;
	}

	public Long getIslandNumber() {
		return islandNumber;
	}

	public void setX(Long x) {
		this.x = x;
	}

	public void setResourceType(Long resourceType) {
		this.resourceType = resourceType;
	}

	public void setY(Long y) {
		this.y = y;
	}

	public void setTownCount(Long townCount) {
		this.townCount = townCount;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIslandNumber(Long islandNumber) {
		this.islandNumber = islandNumber;
	}
	
	public static List<WorldMap> parse(String response) {
		JSONArray arrays = JSONArray.fromObject("islands");
		if (arrays != null) {
			List<WorldMap> list = new Vector<WorldMap>();
			for (int i = 0; i < arrays.size(); i++) {
				JSONObject array = (JSONObject) arrays.get(i);
				WorldMap worldMap = new WorldMap();
				worldMap.setX((array.get("x") != null) ? array.getLong("x") : null);
				worldMap.setResourceType((array.get("resource") != null) ? array.getLong("resource") : null);
				worldMap.setY((array.get("y") != null) ? array.getLong("y") : null);
				worldMap.setTownCount((array.get("town_count") != null) ? array.getLong("town_count") : null);
				worldMap.setId((array.get("id") != null) ? array.getLong("id") : null);
				worldMap.setIslandNumber((array.get("island_number") != null) ? array.getLong("island_number") : null);
				list.add(worldMap);
			}
			
			return list;
		} else
			return null;
	}
}
