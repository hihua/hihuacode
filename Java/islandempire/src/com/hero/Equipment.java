package com.hero;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.util.DateTime;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//type
//0			主武器
//1			副武器
//2			头盔
//3			护甲
//4			靴子
//5			护腿
//6			戒指
//7			项链
//8			技能书
//9			回复药

public class Equipment {
	private Long defense;
	private String iconUrl;
	private Long npcPrice;
	private Long subType;
	private Long level;
	private Date gainTime;
	private Long equipmentId;
	private Enhance enhance;
	private String equipmentDesc;
	private Long type;
	private Long index;
	private Long needHeroLevel;
	private String iconName;
	private Long status;
	private Long intelligence;
	private Long attack;
	private String equipmentName;

	public Long getDefense() {
		return defense;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public Long getNpcPrice() {
		return npcPrice;
	}

	public Long getSubType() {
		return subType;
	}

	public Long getLevel() {
		return level;
	}

	public Date getGainTime() {
		return gainTime;
	}

	public Long getEquipmentId() {
		return equipmentId;
	}

	public Enhance getEnhance() {
		return enhance;
	}

	public String getEquipmentDesc() {
		return equipmentDesc;
	}

	public Long getType() {
		return type;
	}

	public Long getIndex() {
		return index;
	}

	public Long getNeedHeroLevel() {
		return needHeroLevel;
	}

	public String getIconName() {
		return iconName;
	}

	public Long getStatus() {
		return status;
	}

	public Long getIntelligence() {
		return intelligence;
	}

	public Long getAttack() {
		return attack;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setDefense(Long defense) {
		this.defense = defense;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public void setNpcPrice(Long npcPrice) {
		this.npcPrice = npcPrice;
	}

	public void setSubType(Long subType) {
		this.subType = subType;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public void setGainTime(Date gainTime) {
		this.gainTime = gainTime;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}

	public void setEnhance(Enhance enhance) {
		this.enhance = enhance;
	}

	public void setEquipmentDesc(String equipmentDesc) {
		this.equipmentDesc = equipmentDesc;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public void setIndex(Long index) {
		this.index = index;
	}

	public void setNeedHeroLevel(Long needHeroLevel) {
		this.needHeroLevel = needHeroLevel;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public void setIntelligence(Long intelligence) {
		this.intelligence = intelligence;
	}

	public void setAttack(Long attack) {
		this.attack = attack;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	
	public static List<Equipment> parse(String response) {
		JSONObject json = JSONObject.fromObject(response);
		if (json == null || json.get("town") == null)
			return null;
		
		JSONObject town = (JSONObject) json.get("town");
		if (town.get("equipments") == null)
			return null;
		
		List<Equipment> equipments = new Vector<Equipment>();		
		JSONArray arrays = town.getJSONArray("equipments");
		for (int i = 0; i < arrays.size(); i++) {
			JSONObject array = (JSONObject) arrays.get(i);
			Equipment equipment = new Equipment();
			equipment.setDefense(array.get("defense") != null ? array.getLong("defense") : null);
			equipment.setIconUrl(array.get("icon_url") != null ? array.getString("icon_url") : null);
			equipment.setNpcPrice(array.get("npc_price") != null ? array.getLong("npc_price") : null);
			equipment.setSubType(array.get("sub_type") != null ? array.getLong("sub_type") : null);
			equipment.setLevel(array.get("level") != null ? array.getLong("level") : null);
			equipment.setGainTime(array.get("gain_time") != null ? DateTime.getTime(array.getLong("gain_time")) : null);
			equipment.setEquipmentId(array.get("equipment_id") != null ? array.getLong("equipment_id") : null);
			
			if (array.get("enhance") != null) {
				JSONObject enhance = array.getJSONObject("enhance");
				Enhance enhances = new Enhance();
				enhances.setDefense(enhance.get("defense") != null ? enhance.getLong("defense") : null);
				enhances.setNpcPrice(enhance.get("npc_price") != null ? enhance.getLong("npc_price") : null);
				enhances.setRate(enhance.get("rate") != null ? enhance.getDouble("rate") : null);
				
				if (enhance.get("need_resources") != null) {
					JSONObject needResource = enhance.getJSONObject("need_resources");
					NeedResources needResources = new NeedResources();
					needResources.setMarble(needResource.get("marble") != null ? needResource.getLong("marble") : null);
					needResources.setIron(needResource.get("iron") != null ? needResource.getLong("iron") : null);
					enhances.setNeedResources(needResources);
				}
				
				enhances.setIntelligence(enhance.get("intelligence") != null ? enhance.getLong("intelligence") : null);
				enhances.setAttack(enhance.get("attack") != null ? enhance.getLong("attack") : null);
				equipment.setEnhance(enhances);
			}
			
			equipment.setEquipmentDesc(array.get("equipment_desc") != null ? array.getString("equipment_desc") : null);
			equipment.setType(array.get("type") != null ? array.getLong("type") : null);
			equipment.setIndex(array.get("index") != null ? array.getLong("index") : null);
			equipment.setNeedHeroLevel(array.get("need_hero_level") != null ? array.getLong("need_hero_level") : null);
			equipment.setIconName(array.get("icon_name") != null ? array.getString("icon_name") : null);
			equipment.setStatus(array.get("status") != null ? array.getLong("status") : null);
			equipment.setIntelligence(array.get("intelligence") != null ? array.getLong("intelligence") : null);
			equipment.setAttack(array.get("attack") != null ? array.getLong("attack") : null);
			equipment.setEquipmentName(array.get("equipment_name") != null ? array.getString("equipment_name") : null);
			equipments.add(equipment);
		}
		
		return equipments;
	}
}
