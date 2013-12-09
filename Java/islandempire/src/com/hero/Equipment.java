package com.hero;

import java.util.List;
import java.util.Vector;

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
	private Long count;
	private Long skillSid;
	private Long enhanceLevel;
	private Long npcPrice;
	private String iconUrl;
	private Long heroId;
	private Long subType;
	private Long level;
	private Long total;
	private Long equipmentId;
	private Long starLevel;
	private Enhance enhance;
	private Long userId;
	private String equipmentDesc;
	private Long type;
	private Long index;
	private String iconName;
	private Long needHeroLevel;
	private Long status;
	private Long intelligence;
	private Long attack;
	private String equipmentName;
	private Long refreshGems;
	private Long bindType;

	public Long getDefense() {
		return defense;
	}

	public Long getCount() {
		return count;
	}

	public Long getSkillSid() {
		return skillSid;
	}

	public Long getEnhanceLevel() {
		return enhanceLevel;
	}

	public Long getNpcPrice() {
		return npcPrice;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public Long getHeroId() {
		return heroId;
	}

	public Long getSubType() {
		return subType;
	}

	public Long getLevel() {
		return level;
	}

	public Long getTotal() {
		return total;
	}

	public Long getEquipmentId() {
		return equipmentId;
	}

	public Long getStarLevel() {
		return starLevel;
	}

	public Enhance getEnhance() {
		return enhance;
	}

	public Long getUserId() {
		return userId;
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

	public String getIconName() {
		return iconName;
	}

	public Long getNeedHeroLevel() {
		return needHeroLevel;
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

	public Long getRefreshGems() {
		return refreshGems;
	}

	public Long getBindType() {
		return bindType;
	}

	public void setDefense(Long defense) {
		this.defense = defense;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public void setSkillSid(Long skillSid) {
		this.skillSid = skillSid;
	}

	public void setEnhanceLevel(Long enhanceLevel) {
		this.enhanceLevel = enhanceLevel;
	}

	public void setNpcPrice(Long npcPrice) {
		this.npcPrice = npcPrice;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public void setHeroId(Long heroId) {
		this.heroId = heroId;
	}

	public void setSubType(Long subType) {
		this.subType = subType;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}

	public void setStarLevel(Long starLevel) {
		this.starLevel = starLevel;
	}

	public void setEnhance(Enhance enhance) {
		this.enhance = enhance;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public void setNeedHeroLevel(Long needHeroLevel) {
		this.needHeroLevel = needHeroLevel;
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

	public void setRefreshGems(Long refreshGems) {
		this.refreshGems = refreshGems;
	}

	public void setBindType(Long bindType) {
		this.bindType = bindType;
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
			equipment.setCount(array.get("count") != null ? array.getLong("count") : null);
			equipment.setSkillSid(array.get("skill_sid") != null ? array.getLong("skill_sid") : null);
			equipment.setEnhanceLevel(array.get("enhance_level") != null ? array.getLong("enhance_level") : null);
			equipment.setNpcPrice(array.get("npc_price") != null ? array.getLong("npc_price") : null);
			equipment.setIconUrl(array.get("icon_url") != null ? array.getString("icon_url") : null);
			equipment.setHeroId(array.get("hero_id") != null ? array.getLong("hero_id") : null);
			equipment.setSubType(array.get("sub_type") != null ? array.getLong("sub_type") : null);
			equipment.setLevel(array.get("level") != null ? array.getLong("level") : null);
			equipment.setTotal(array.get("total") != null ? array.getLong("total") : null);
			equipment.setEquipmentId(array.get("equipment_id") != null ? array.getLong("equipment_id") : null);
			equipment.setStarLevel(array.get("star_level") != null ? array.getLong("star_level") : null);
			
			if (array.get("enhance") != null) {
				JSONObject enhance = array.getJSONObject("enhance");
				Enhance enhances = new Enhance();
				enhances.setRate(enhance.get("rate") != null ? enhance.getDouble("rate") : null);
				enhances.setSafeGems(enhance.get("safe_gems") != null ? enhance.getLong("safe_gems") : null);
				enhances.setProfit(enhance.get("profit") != null ? enhance.getLong("profit") : null);
								
				JSONArray luckGems = enhance.getJSONArray("luck_gems");
				if (luckGems != null) {
					List<Long> list = new Vector<Long>();
					for (int j = 0; j < luckGems.size(); j++) {
						Long luckGem = luckGems.getLong(j);
						list.add(luckGem);
					}
					
					enhances.setLuckGems(list);
				}
				
				JSONArray luckValues = enhance.getJSONArray("luck_value");
				if (luckValues != null) {
					List<Long> list = new Vector<Long>();
					for (int j = 0; j < luckValues.size(); j++) {
						Long luckValue = luckValues.getLong(j);
						list.add(luckValue);
					}
					
					enhances.setLuckValue(list);
				}
								
				if (enhance.get("need_resources") != null) {
					JSONObject needResource = enhance.getJSONObject("need_resources");
					NeedResources needResources = new NeedResources();
					needResources.setAll(needResource.get("all") != null ? needResource.getLong("all") : null);
					enhances.setNeedResources(needResources);
				}
				
				if (enhance.get("free_safe_resources") != null) {
					JSONObject freeSafeResource = enhance.getJSONObject("free_safe_resources");
					FreeSafeResources freeSafeResources = new FreeSafeResources();
					freeSafeResources.setAll(freeSafeResource.get("all") != null ? freeSafeResource.getLong("all") : null);
					enhances.setFreeSafeResources(freeSafeResources);
				}
				
				equipment.setEnhance(enhances);
			}
			
			equipment.setUserId(array.get("user_id") != null ? array.getLong("user_id") : null);
			equipment.setEquipmentDesc(array.get("equipment_desc") != null ? array.getString("equipment_desc") : null);
			equipment.setType(array.get("type") != null ? array.getLong("type") : null);
			equipment.setIndex(array.get("index") != null ? array.getLong("index") : null);
			equipment.setIconName(array.get("icon_name") != null ? array.getString("icon_name") : null);
			equipment.setNeedHeroLevel(array.get("need_hero_level") != null ? array.getLong("need_hero_level") : null);			
			equipment.setStatus(array.get("status") != null ? array.getLong("status") : null);
			equipment.setIntelligence(array.get("intelligence") != null ? array.getLong("intelligence") : null);
			equipment.setAttack(array.get("attack") != null ? array.getLong("attack") : null);
			equipment.setEquipmentName(array.get("equipment_name") != null ? array.getString("equipment_name") : null);
			equipment.setRefreshGems(array.get("refresh_gems") != null ? array.getLong("refresh_gems") : null);
			equipment.setBindType(array.get("bind_type") != null ? array.getLong("bind_type") : null);
			equipments.add(equipment);
		}
		
		return equipments;
	}
}
