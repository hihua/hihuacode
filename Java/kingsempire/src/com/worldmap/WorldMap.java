package com.worldmap;

import java.util.List;
import java.util.Vector;

import com.util.DateTime;
import com.util.Numeric;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WorldMap {
	private Region region;
	private List<Tile> tile;
		
	public Region getRegion() {
		return region;
	}

	public List<Tile> getTile() {
		return tile;
	}
	
	public void setRegion(Region region) {
		this.region = region;
	}
	
	public void setTile(List<Tile> tile) {
		this.tile = tile;
	}

	public static WorldMap parse(String response) {
		JSONObject json = JSONObject.fromObject(response);
		if (json == null)
			return null;
		
		WorldMap worldMap = new WorldMap();
		if (json.get("region_status") != null) {
			JSONObject array = (JSONObject)json.get("region_status");			
			Region region = new Region();
			region.setDuration((array.get("duration") != null) ? array.getLong("duration") : null);
			region.setDescription((array.get("description") != null) ? array.getString("description") : null);
			worldMap.setRegion(region);
		}					
		
		JSONArray arrays = json.getJSONArray("tiles");
		if (arrays != null) {
			List<Tile> tiles = new Vector<Tile>();
			for (int i = 0;i < arrays.size();i++) {
				try {
					JSONObject array = (JSONObject)arrays.get(i);					
					Tile tile = new Tile();
					tile.setMapType((array.get("map_type") != null) ? array.getLong("map_type") : null);
					tile.setName((array.get("name") != null) ? array.getString("name") : null);
					tile.setX((array.get("x") != null) ? array.getLong("x") : null);
					tile.setY((array.get("y") != null) ? array.getLong("y") : null);
					tile.setOccupy((array.get("occupy") != null) ? array.getLong("occupy") : null);
					tile.setLevel((array.get("level") != null) ? array.getLong("level") : null);
					tile.setRecoveryTime((array.get("recovery_time") != null && !array.getString("recovery_time").equals("0") && Numeric.isNumber(array.getString("recovery_time"))) ? DateTime.getTime(array.getLong("recovery_time")) : null);
					tile.setProtectionFinishAt((array.get("protection_finish_at") != null && Numeric.isNumber(array.getString("protection_finish_at"))) ? DateTime.getTime(array.getLong("protection_finish_at")) : null);
					tile.setId((array.get("id") != null) ? array.getLong("id") : null);
					tile.setMajorCity((array.get("major_city") != null) ? array.getLong("major_city") : null);
					tile.setInProtection((array.get("in_protection") != null) ? array.getBoolean("in_protection") : null);
					tile.setOwnerCityName((array.get("owner_city_name") != null) ? array.getString("owner_city_name") : null);
					tile.setOwnerUserName((array.get("owner_user_name") != null) ? array.getString("owner_user_name") : null);
					tiles.add(tile);
				} catch (Exception e) {
					
				}				
			}
			
			worldMap.setTile(tiles);
			return worldMap;
		} else
			return null;
	}
}
