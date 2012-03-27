package com.world;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.util.DateTime;
import com.util.Numeric;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WorldMap {
	private Long mapType;
	private String name;
	private Long x;
	private Long y;
	private Long occupy;
	private Long level;
	private Date recoveryTime;
	private Date protectionFinishAt;
	private Long id;
	private Long majorCity;
	private Boolean inProtection;
	private String ownerCityName;
	private String ownerUserName;
	
	public Long getMapType() {
		return mapType;
	}
	
	public String getName() {
		return name;
	}
	
	public Long getX() {
		return x;
	}
	
	public Long getY() {
		return y;
	}
	
	public Long getOccupy() {
		return occupy;
	}
	
	public Long getLevel() {
		return level;
	}
	
	public Date getRecoveryTime() {
		return recoveryTime;
	}
	
	public Date getProtectionFinishAt() {
		return protectionFinishAt;
	}
	
	public Long getId() {
		return id;
	}
	
	public Long getMajorCity() {
		return majorCity;
	}
	
	public Boolean getInProtection() {
		return inProtection;
	}
	
	public String getOwnerCityName() {
		return ownerCityName;
	}
	
	public String getOwnerUserName() {
		return ownerUserName;
	}
	
	public void setMapType(Long mapType) {
		this.mapType = mapType;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setX(Long x) {
		this.x = x;
	}
	
	public void setY(Long y) {
		this.y = y;
	}
	
	public void setOccupy(Long occupy) {
		this.occupy = occupy;
	}
	
	public void setLevel(Long level) {
		this.level = level;
	}
	
	public void setRecoveryTime(Date recoveryTime) {
		this.recoveryTime = recoveryTime;
	}
	
	public void setProtectionFinishAt(Date protectionFinishAt) {
		this.protectionFinishAt = protectionFinishAt;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setMajorCity(Long majorCity) {
		this.majorCity = majorCity;
	}
	
	public void setInProtection(Boolean inProtection) {
		this.inProtection = inProtection;
	}
	
	public void setOwnerCityName(String ownerCityName) {
		this.ownerCityName = ownerCityName;
	}
	
	public void setOwnerUserName(String ownerUserName) {
		this.ownerUserName = ownerUserName;
	}
	
	public static List<WorldMap> parse(String response) {
		JSONObject json = JSONObject.fromObject(response);		
		JSONArray tiles = json.getJSONArray("tiles");
		if (tiles != null) {
			List<WorldMap> worldMaps = new Vector<WorldMap>(); 
			for (int i = 0;i < tiles.size();i++) {
				try {
					JSONObject tile = (JSONObject)tiles.get(i);
					WorldMap worldMap = new WorldMap();
					worldMap.setMapType((tile.get("map_type") != null) ? tile.getLong("map_type") : null);
					worldMap.setName((tile.get("name") != null) ? tile.getString("name") : null);
					worldMap.setX((tile.get("x") != null) ? tile.getLong("x") : null);
					worldMap.setY((tile.get("y") != null) ? tile.getLong("y") : null);
					worldMap.setOccupy((tile.get("occupy") != null) ? tile.getLong("occupy") : null);
					worldMap.setLevel((tile.get("level") != null) ? tile.getLong("level") : null);
					worldMap.setRecoveryTime((tile.get("recovery_time") != null && !tile.getString("recovery_time").equals("0") && Numeric.isNumber(tile.getString("recovery_time"))) ? DateTime.getTime(tile.getLong("recovery_time")) : null);
					worldMap.setProtectionFinishAt((tile.get("protection_finish_at") != null && Numeric.isNumber(tile.getString("protection_finish_at"))) ? DateTime.getTime(tile.getLong("protection_finish_at")) : null);
					worldMap.setId((tile.get("id") != null) ? tile.getLong("id") : null);
					worldMap.setMajorCity((tile.get("major_city") != null) ? tile.getLong("major_city") : null);
					worldMap.setInProtection((tile.get("in_protection") != null) ? tile.getBoolean("in_protection") : null);
					worldMap.setOwnerCityName((tile.get("owner_city_name") != null) ? tile.getString("owner_city_name") : null);
					worldMap.setOwnerUserName((tile.get("owner_user_name") != null) ? tile.getString("owner_user_name") : null);
					worldMaps.add(worldMap);
				} catch (Exception e) {
					
				}				
			}
			
			return worldMaps;
		} else
			return null;
	}
}
