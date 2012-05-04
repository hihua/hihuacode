package com.island;

import java.util.List;
import java.util.Vector;

import com.util.DateTime;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Island {
	private Long x;
	private Long resourceType;
	private Long y;
	private Long townCount;
	private Long id;
	private String islandNumber;

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

	public String getIslandNumber() {
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

	public void setIslandNumber(String islandNumber) {
		this.islandNumber = islandNumber;
	}
	
	public static List<IslandBuilding> parse(String response) {
		JSONObject json = JSONObject.fromObject(response);
		if (json == null || json.get("island") == null)
			return null;
		
		JSONObject island = (JSONObject)json.get("island");
		if (island.get("towns") == null)
			return null;
		
		JSONArray arrays = island.getJSONArray("towns");
		if (arrays != null) {
			List<IslandBuilding> list = new Vector<IslandBuilding>();
			for (int i = 0; i < arrays.size(); i++) {
				JSONObject array = (JSONObject) arrays.get(i);
				String name = array.get("name") != null ? array.getString("name") : null;
				Long level = array.get("level") != null ? array.getLong("level") : null;
				Long id = array.get("id") != null ? array.getLong("id") : null;
				Long islandId = array.get("island_id") != null ? array.getLong("island_id") : null;
				String type = array.get("type") != null ? array.getString("type") : null;
				String cityStatus = array.get("city_status") != null ? array.getString("city_status") : null;
				Long islandZ = array.get("island_z") != null ? array.getLong("island_z") : null;
				
				if (type == null)
					continue;
				
				if (type.equals("Village")) {
					IslandVillage islandVillage = new IslandVillage();
					islandVillage.setName(name);
					islandVillage.setLevel(level);
					islandVillage.setId(id);
					islandVillage.setIslandId(islandId);
					islandVillage.setType(type);
					islandVillage.setCityStatus(cityStatus);
					islandVillage.setIslandZ(islandZ);					
					islandVillage.setTotalTime(array.get("total_time") != null ? array.getLong("total_time") : null);
					islandVillage.setFinishTime(array.get("finish_time") != null ? DateTime.getTime(array.getLong("finish_time")) : null);
					list.add(islandVillage);
				}
				
				if (type.equals("Town")) {
					IslandTown islandTown = new IslandTown();
					islandTown.setName(name);
					islandTown.setLevel(level);
					islandTown.setId(id);
					islandTown.setIslandId(islandId);
					islandTown.setType(type);
					islandTown.setCityStatus(cityStatus);
					islandTown.setIslandZ(islandZ);
					islandTown.setCityAttackLevel(array.get("city_attack_level") != null ? array.getLong("city_attack_level") : null);
					islandTown.setOwnerName(array.get("owner_name") != null ? array.getString("owner_name") : null);
					islandTown.setOwnerId(array.get("owner_id") != null ? array.getLong("owner_id") : null);
					list.add(islandTown);
				}
			}
			
			return list;
		} else
			return null;
	}
}
