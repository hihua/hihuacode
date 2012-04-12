package com.world;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.buildings.BuildingCastle;
import com.buildings.BuildingConsume;
import com.buildings.BuildingChurch;
import com.buildings.BuildingLeader;
import com.buildings.BuildingStore;
import com.buildings.BuildingMarket;
import com.buildings.BuildingResources;
import com.buildings.BuildingSkill;
import com.buildings.BuildingSoldiers;
import com.buildings.BuildingWorkers;
import com.events.EventArmy;
import com.events.EventBuilding;
import com.events.EventProtection;
import com.events.EventSkill;
import com.events.EventTrain;
import com.resources.ResourcesBase;
import com.resources.ResourcesWorkers;
import com.skills.SkillBase;
import com.skills.SkillChurch;
import com.skills.SkillConsume;
import com.skills.SkillResources;
import com.skills.SkillSoldiers;
import com.soldiers.SoldierBase;
import com.soldiers.SoldierConsume;
import com.util.DateTime;

/*building_type
 *  0 	城堡
 *  1 	城市 
 *  2 	市场
 *  3 	使馆
 *  4 	信仰
 *  5 	仓库
 *  6 	木场		0
 *  7 	石场		1
 *  8 	铁场		2
 *  9 	农场		3
 * 10 	科技
 * 11 	步兵
 * 12 	骑兵
 */

public class City {	
	private BuildingCastle buildingCastle;
	private BuildingChurch buildingChurch;
	private BuildingSkill buildingSkill;		
	private BuildingStore buildingStore;
	private BuildingMarket buildingMarket;
	private BuildingLeader buildingLeader;
	private BuildingSoldiers buildingCavalry;
	private BuildingSoldiers buildingInfantry;
	private BuildingWorkers buildingWorkers;
	private BuildingResources buildingWood;
	private BuildingResources buildingFood;
	private BuildingResources buildingStrone;
	private BuildingResources buildingIron;
	private ResourcesWorkers resourcesWorkers;
	private ResourcesBase resourcesGold;
	private ResourcesBase resourcesWood;
	private ResourcesBase resourcesFood;
	private ResourcesBase resourcesStone;
	private ResourcesBase resourcesIron;
	private ResourcesBase resourcesLeader;
	private SkillResources skillResources;
	private SkillSoldiers skillSoldiers;
	private SkillChurch skillChurch;
	private List<EventBuilding> eventBuilding;
	private List<EventArmy> eventArmy;
	private List<EventSkill> eventSkill;
	private List<EventTrain> eventTrain;	
	private EventProtection eventProtection;
	private SoldierBase infantrySwords;
	private SoldierBase infantryScout;
	private SoldierBase infantryCrossbow;
	private SoldierBase infantrySquire;
	private SoldierBase cavalryTemplar;
	private SoldierBase cavalryArcher;
	private SoldierBase cavalryPaladin;
	private SoldierBase cavalryRoyal;	
	private String wishingVersion;
	private String countryName;
	private String name;
	private Long loginDaysCount;
	private Long leftWishingCount;
	private String title;
	private Long x;
	private Long y;
	private Long friendApplication;
	private Long max;
	private Long countryId;
	private Long level;
	private Long ownerId;
	private Date currentServerTime;
	private Long citiesCount;
	private Long gems;
	private Boolean newReward;
	private Long id;
	private Long accountLevel;
	private Long min;
	private Long newMessage;
	private Long marketLeftCapacity;
	private Long marketEffecitve;
	private Boolean announcement;
	private Long score;
	private Long maxUnitPrice;
	private Long maxCitiesCount;
	private Boolean isCapital;
	private Long noviceStep;
	private String owner;
		
	public BuildingCastle getBuildingCastle() {
		return buildingCastle;
	}

	public BuildingChurch getBuildingChurch() {
		return buildingChurch;
	}

	public BuildingSkill getBuildingSkill() {
		return buildingSkill;
	}
	
	public BuildingStore getBuildingStore() {
		return buildingStore;
	}

	public BuildingMarket getBuildingMarket() {
		return buildingMarket;
	}
	
	public BuildingLeader getBuildingLeader() {
		return buildingLeader;
	}

	public BuildingSoldiers getBuildingCavalry() {
		return buildingCavalry;
	}

	public BuildingSoldiers getBuildingInfantry() {
		return buildingInfantry;
	}

	public BuildingWorkers getBuildingWorkers() {
		return buildingWorkers;
	}

	public BuildingResources getBuildingWood() {
		return buildingWood;
	}

	public BuildingResources getBuildingFood() {
		return buildingFood;
	}

	public BuildingResources getBuildingStrone() {
		return buildingStrone;
	}

	public BuildingResources getBuildingIron() {
		return buildingIron;
	}

	public ResourcesWorkers getResourcesWorkers() {
		return resourcesWorkers;
	}

	public ResourcesBase getResourcesGold() {
		return resourcesGold;
	}

	public ResourcesBase getResourcesWood() {
		return resourcesWood;
	}

	public ResourcesBase getResourcesFood() {
		return resourcesFood;
	}

	public ResourcesBase getResourcesStone() {
		return resourcesStone;
	}

	public ResourcesBase getResourcesIron() {
		return resourcesIron;
	}
	
	public ResourcesBase getResourcesLeader() {
		return resourcesLeader;
	}

	public SkillResources getSkillResources() {
		return skillResources;
	}

	public SkillSoldiers getSkillSoldiers() {
		return skillSoldiers;
	}
	
	public SkillChurch getSkillChurch() {
		return skillChurch;
	}

	public List<EventBuilding> getEventBuilding() {
		return eventBuilding;
	}
	
	public List<EventArmy> getEventArmy() {
		return eventArmy;
	}
	
	public List<EventSkill> getEventSkill() {
		return eventSkill;
	}

	public List<EventTrain> getEventTrain() {
		return eventTrain;
	}

	public EventProtection getEventProtection() {
		return eventProtection;
	}

	public SoldierBase getInfantrySwords() {
		return infantrySwords;
	}

	public SoldierBase getInfantryScout() {
		return infantryScout;
	}

	public SoldierBase getInfantryCrossbow() {
		return infantryCrossbow;
	}

	public SoldierBase getInfantrySquire() {
		return infantrySquire;
	}

	public SoldierBase getCavalryTemplar() {
		return cavalryTemplar;
	}

	public SoldierBase getCavalryArcher() {
		return cavalryArcher;
	}

	public SoldierBase getCavalryPaladin() {
		return cavalryPaladin;
	}

	public SoldierBase getCavalryRoyal() {
		return cavalryRoyal;
	}

	public String getWishingVersion() {
		return wishingVersion;
	}

	public String getCountryName() {
		return countryName;
	}

	public String getName() {
		return name;
	}

	public Long getLoginDaysCount() {
		return loginDaysCount;
	}

	public Long getLeftWishingCount() {
		return leftWishingCount;
	}

	public String getTitle() {
		return title;
	}

	public Long getX() {
		return x;
	}

	public Long getY() {
		return y;
	}

	public Long getFriendApplication() {
		return friendApplication;
	}

	public Long getMax() {
		return max;
	}

	public Long getCountryId() {
		return countryId;
	}

	public Long getLevel() {
		return level;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public Date getCurrentServerTime() {
		return currentServerTime;
	}

	public Long getCitiesCount() {
		return citiesCount;
	}

	public Long getGems() {
		return gems;
	}

	public Boolean getNewReward() {
		return newReward;
	}

	public Long getId() {
		return id;
	}

	public Long getAccountLevel() {
		return accountLevel;
	}

	public Long getMin() {
		return min;
	}

	public Long getNewMessage() {
		return newMessage;
	}

	public Long getMarketLeftCapacity() {
		return marketLeftCapacity;
	}

	public Long getMarketEffecitve() {
		return marketEffecitve;
	}

	public Boolean getAnnouncement() {
		return announcement;
	}

	public Long getScore() {
		return score;
	}

	public Long getMaxUnitPrice() {
		return maxUnitPrice;
	}

	public Long getMaxCitiesCount() {
		return maxCitiesCount;
	}

	public Boolean getIsCapital() {
		return isCapital;
	}

	public Long getNoviceStep() {
		return noviceStep;
	}

	public String getOwner() {
		return owner;
	}

	public void setBuildingCastle(BuildingCastle buildingCastle) {
		this.buildingCastle = buildingCastle;
	}

	public void setBuildingChurch(BuildingChurch buildingChurch) {
		this.buildingChurch = buildingChurch;
	}

	public void setBuildingSkill(BuildingSkill buildingSkill) {
		this.buildingSkill = buildingSkill;
	}

	public void setBuildingStore(BuildingStore buildingStore) {
		this.buildingStore = buildingStore;
	}

	public void setBuildingMarket(BuildingMarket buildingMarket) {
		this.buildingMarket = buildingMarket;
	}
	
	public void setBuildingLeader(BuildingLeader buildingLeader) {
		this.buildingLeader = buildingLeader;
	}

	public void setBuildingCavalry(BuildingSoldiers buildingCavalry) {
		this.buildingCavalry = buildingCavalry;
	}

	public void setBuildingInfantry(BuildingSoldiers buildingInfantry) {
		this.buildingInfantry = buildingInfantry;
	}

	public void setBuildingWorkers(BuildingWorkers buildingWorkers) {
		this.buildingWorkers = buildingWorkers;
	}

	public void setBuildingWood(BuildingResources buildingWood) {
		this.buildingWood = buildingWood;
	}

	public void setBuildingFood(BuildingResources buildingFood) {
		this.buildingFood = buildingFood;
	}

	public void setBuildingStrone(BuildingResources buildingStrone) {
		this.buildingStrone = buildingStrone;
	}

	public void setBuildingIron(BuildingResources buildingIron) {
		this.buildingIron = buildingIron;
	}

	public void setResourcesWorkers(ResourcesWorkers resourcesWorkers) {
		this.resourcesWorkers = resourcesWorkers;
	}

	public void setResourcesGold(ResourcesBase resourcesGold) {
		this.resourcesGold = resourcesGold;
	}

	public void setResourcesWood(ResourcesBase resourcesWood) {
		this.resourcesWood = resourcesWood;
	}

	public void setResourcesFood(ResourcesBase resourcesFood) {
		this.resourcesFood = resourcesFood;
	}

	public void setResourcesStone(ResourcesBase resourcesStone) {
		this.resourcesStone = resourcesStone;
	}

	public void setResourcesIron(ResourcesBase resourcesIron) {
		this.resourcesIron = resourcesIron;
	}
	
	public void setResourcesLeader(ResourcesBase resourcesLeader) {
		this.resourcesLeader = resourcesLeader;
	}

	public void setSkillResources(SkillResources skillResources) {
		this.skillResources = skillResources;
	}

	public void setSkillSoldiers(SkillSoldiers skillSoldiers) {
		this.skillSoldiers = skillSoldiers;
	}
	
	public void setSkillChurch(SkillChurch skillChurch) {
		this.skillChurch = skillChurch;
	}

	public void setEventBuilding(List<EventBuilding> eventBuilding) {
		this.eventBuilding = eventBuilding;
	}
	
	public void setEventArmy(List<EventArmy> eventArmy) {
		this.eventArmy = eventArmy;
	}
	
	public void setEventSkill(List<EventSkill> eventSkill) {
		this.eventSkill = eventSkill;
	}

	public void setEventTrain(List<EventTrain> eventTrain) {
		this.eventTrain = eventTrain;
	}

	public void setEventProtection(EventProtection eventProtection) {
		this.eventProtection = eventProtection;
	}

	public void setInfantrySwords(SoldierBase infantrySwords) {
		this.infantrySwords = infantrySwords;
	}

	public void setInfantryScout(SoldierBase infantryScout) {
		this.infantryScout = infantryScout;
	}

	public void setInfantryCrossbow(SoldierBase infantryCrossbow) {
		this.infantryCrossbow = infantryCrossbow;
	}

	public void setInfantrySquire(SoldierBase infantrySquire) {
		this.infantrySquire = infantrySquire;
	}

	public void setCavalryTemplar(SoldierBase cavalryTemplar) {
		this.cavalryTemplar = cavalryTemplar;
	}

	public void setCavalryArcher(SoldierBase cavalryArcher) {
		this.cavalryArcher = cavalryArcher;
	}

	public void setCavalryPaladin(SoldierBase cavalryPaladin) {
		this.cavalryPaladin = cavalryPaladin;
	}

	public void setCavalryRoyal(SoldierBase cavalryRoyal) {
		this.cavalryRoyal = cavalryRoyal;
	}

	public void setWishingVersion(String wishingVersion) {
		this.wishingVersion = wishingVersion;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLoginDaysCount(Long loginDaysCount) {
		this.loginDaysCount = loginDaysCount;
	}

	public void setLeftWishingCount(Long leftWishingCount) {
		this.leftWishingCount = leftWishingCount;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setX(Long x) {
		this.x = x;
	}

	public void setY(Long y) {
		this.y = y;
	}

	public void setFriendApplication(Long friendApplication) {
		this.friendApplication = friendApplication;
	}

	public void setMax(Long max) {
		this.max = max;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public void setCurrentServerTime(Date currentServerTime) {
		this.currentServerTime = currentServerTime;
	}

	public void setCitiesCount(Long citiesCount) {
		this.citiesCount = citiesCount;
	}

	public void setGems(Long gems) {
		this.gems = gems;
	}

	public void setNewReward(Boolean newReward) {
		this.newReward = newReward;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAccountLevel(Long accountLevel) {
		this.accountLevel = accountLevel;
	}

	public void setMin(Long min) {
		this.min = min;
	}

	public void setNewMessage(Long newMessage) {
		this.newMessage = newMessage;
	}

	public void setMarketLeftCapacity(Long marketLeftCapacity) {
		this.marketLeftCapacity = marketLeftCapacity;
	}

	public void setMarketEffecitve(Long marketEffecitve) {
		this.marketEffecitve = marketEffecitve;
	}

	public void setAnnouncement(Boolean announcement) {
		this.announcement = announcement;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public void setMaxUnitPrice(Long maxUnitPrice) {
		this.maxUnitPrice = maxUnitPrice;
	}

	public void setMaxCitiesCount(Long maxCitiesCount) {
		this.maxCitiesCount = maxCitiesCount;
	}

	public void setIsCapital(Boolean isCapital) {
		this.isCapital = isCapital;
	}

	public void setNoviceStep(Long noviceStep) {
		this.noviceStep = noviceStep;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public static City parse(String response) {
		JSONObject json = JSONObject.fromObject(response);	
		if (json.get("city") == null)
			return null;
		
		City cities = new City();
		JSONObject city = (JSONObject)json.get("city");
		JSONArray buildings = city.getJSONArray("buildings");
		if (buildings != null) {
			for (int i = 0;i < buildings.size();i++) {
				JSONObject building = (JSONObject)buildings.get(i);				
				Long duration = (building.get("duration") != null) ? building.getLong("duration") : null;
				Long maxLevel = (building.get("max_level") != null) ? building.getLong("max_level") : null;
				Long property = (building.get("property") != null) ? building.getLong("property") : null;
				Long level = (building.get("level") != null) ? building.getLong("level") : null;
				Long id = (building.get("id") != null) ? building.getLong("id") : null;
				String description = (building.get("description") != null) ? building.getString("description") : null;
				Long buildingType = (building.get("building_type") != null) ? building.getLong("building_type") : null;
				
				if (buildingType == null)
					continue;
								
				BuildingConsume buildingConsume = null;
				
				if (building.get("consume") != null) {
					JSONObject consume = (JSONObject)building.get("consume");
					buildingConsume = new BuildingConsume();
					buildingConsume.setDuration((consume.get("duration") != null) ? consume.getLong("duration") : null);
					buildingConsume.setWood((consume.get("wood") != null) ? consume.getLong("wood") : null);
					buildingConsume.setDescription((consume.get("description") != null) ? consume.getString("description") : null);
					buildingConsume.setStone((consume.get("stone") != null) ? consume.getLong("stone") : null);
				}
				
				if (buildingType.equals(0L)) {
					BuildingCastle buildingCastle = new BuildingCastle();
					buildingCastle.setDuration(duration);
					buildingCastle.setMaxLevel(maxLevel);
					buildingCastle.setProperty(property);
					buildingCastle.setLevel(level);
					buildingCastle.setId(id);
					buildingCastle.setConsume(buildingConsume);
					buildingCastle.setDescription(description);
					buildingCastle.setBuildingType(buildingType);
					cities.setBuildingCastle(buildingCastle);
					continue;
				}
				
				if (buildingType.equals(1L)) {
					BuildingWorkers buildingWorkers = new BuildingWorkers();
					buildingWorkers.setDuration(duration);
					buildingWorkers.setMaxLevel(maxLevel);
					buildingWorkers.setProperty(property);
					buildingWorkers.setLevel(level);
					buildingWorkers.setId(id);
					buildingWorkers.setConsume(buildingConsume);
					buildingWorkers.setDescription(description);
					buildingWorkers.setBuildingType(buildingType);
					cities.setBuildingWorkers(buildingWorkers);
					continue;
				}
				
				if (buildingType.equals(2L)) {
					BuildingMarket buildingMarket = new BuildingMarket();
					buildingMarket.setDuration(duration);
					buildingMarket.setMaxLevel(maxLevel);
					buildingMarket.setProperty(property);
					buildingMarket.setLevel(level);
					buildingMarket.setId(id);
					buildingMarket.setConsume(buildingConsume);
					buildingMarket.setDescription(description);
					buildingMarket.setBuildingType(buildingType);
					cities.setBuildingMarket(buildingMarket);
					continue;
				}
				
				if (buildingType.equals(3L)) {
					BuildingLeader buildingLeader = new BuildingLeader();
					buildingLeader.setDuration(duration);
					buildingLeader.setMaxLevel(maxLevel);
					buildingLeader.setProperty(property);
					buildingLeader.setLevel(level);
					buildingLeader.setId(id);
					buildingLeader.setConsume(buildingConsume);
					buildingLeader.setDescription(description);
					buildingLeader.setBuildingType(buildingType);
					cities.setBuildingLeader(buildingLeader);
					continue;
				}
				
				if (buildingType.equals(4L)) {
					BuildingChurch buildingChurch = new BuildingChurch();
					buildingChurch.setDuration(duration);
					buildingChurch.setMaxLevel(maxLevel);
					buildingChurch.setProperty(property);
					buildingChurch.setLevel(level);
					buildingChurch.setId(id);
					buildingChurch.setConsume(buildingConsume);
					buildingChurch.setDescription(description);
					buildingChurch.setBuildingType(buildingType);
					cities.setBuildingChurch(buildingChurch);
					continue;
				}
				
				if (buildingType.equals(5L)) {
					BuildingStore buildingStore = new BuildingStore();
					buildingStore.setDuration(duration);
					buildingStore.setMaxLevel(maxLevel);
					buildingStore.setProperty(property);
					buildingStore.setLevel(level);
					buildingStore.setId(id);
					buildingStore.setConsume(buildingConsume);
					buildingStore.setDescription(description);
					buildingStore.setBuildingType(buildingType);
					cities.setBuildingStore(buildingStore);
					continue;
				}
				
				if (buildingType.equals(6L)) {
					BuildingResources buildingResources = new BuildingResources();
					buildingResources.setDuration(duration);
					buildingResources.setMaxLevel(maxLevel);
					buildingResources.setProperty(property);
					buildingResources.setLevel(level);
					buildingResources.setId(id);
					buildingResources.setConsume(buildingConsume);
					buildingResources.setDescription(description);
					buildingResources.setBuildingType(buildingType);					
					JSONArray resources = building.getJSONArray("resources");
					if (resources != null && resources.size() > 0)
						buildingResources.setResources(resources.getLong(0));
									
					cities.setBuildingWood(buildingResources);
					continue;
				}
				
				if (buildingType.equals(7L)) {
					BuildingResources buildingResources = new BuildingResources();
					buildingResources.setDuration(duration);
					buildingResources.setMaxLevel(maxLevel);
					buildingResources.setProperty(property);
					buildingResources.setLevel(level);
					buildingResources.setId(id);
					buildingResources.setConsume(buildingConsume);
					buildingResources.setDescription(description);
					buildingResources.setBuildingType(buildingType);					
					JSONArray resources = building.getJSONArray("resources");
					if (resources != null && resources.size() > 0)
						buildingResources.setResources(resources.getLong(0));
									
					cities.setBuildingStrone(buildingResources);
					continue;
				}
				
				if (buildingType.equals(8L)) {
					BuildingResources buildingResources = new BuildingResources();
					buildingResources.setDuration(duration);
					buildingResources.setMaxLevel(maxLevel);
					buildingResources.setProperty(property);
					buildingResources.setLevel(level);
					buildingResources.setId(id);
					buildingResources.setConsume(buildingConsume);
					buildingResources.setDescription(description);
					buildingResources.setBuildingType(buildingType);					
					JSONArray resources = building.getJSONArray("resources");
					if (resources != null && resources.size() > 0)
						buildingResources.setResources(resources.getLong(0));
									
					cities.setBuildingIron(buildingResources);
					continue;
				}
				
				if (buildingType.equals(9L)) {
					BuildingResources buildingResources = new BuildingResources();
					buildingResources.setDuration(duration);
					buildingResources.setMaxLevel(maxLevel);
					buildingResources.setProperty(property);
					buildingResources.setLevel(level);
					buildingResources.setId(id);
					buildingResources.setConsume(buildingConsume);
					buildingResources.setDescription(description);
					buildingResources.setBuildingType(buildingType);					
					JSONArray resources = building.getJSONArray("resources");
					if (resources != null && resources.size() > 0)
						buildingResources.setResources(resources.getLong(0));
									
					cities.setBuildingFood(buildingResources);
					continue;
				}
				
				if (buildingType.equals(10L)) {
					BuildingSkill buildingSkill = new BuildingSkill();
					buildingSkill.setDuration(duration);
					buildingSkill.setMaxLevel(maxLevel);
					buildingSkill.setProperty(property);
					buildingSkill.setLevel(level);
					buildingSkill.setId(id);
					buildingSkill.setConsume(buildingConsume);
					buildingSkill.setDescription(description);
					buildingSkill.setBuildingType(buildingType);																			
					cities.setBuildingSkill(buildingSkill);
					continue;
				}
				
				if (buildingType.equals(11L)) {
					BuildingSoldiers buildingSoldiers = new BuildingSoldiers();
					buildingSoldiers.setDuration(duration);
					buildingSoldiers.setMaxLevel(maxLevel);
					buildingSoldiers.setProperty(property);
					buildingSoldiers.setLevel(level);
					buildingSoldiers.setId(id);
					buildingSoldiers.setConsume(buildingConsume);
					buildingSoldiers.setDescription(description);
					buildingSoldiers.setBuildingType(buildingType);																			
					cities.setBuildingInfantry(buildingSoldiers);
					continue;
				}
				
				if (buildingType.equals(12L)) {
					BuildingSoldiers buildingSoldiers = new BuildingSoldiers();
					buildingSoldiers.setDuration(duration);
					buildingSoldiers.setMaxLevel(maxLevel);
					buildingSoldiers.setProperty(property);
					buildingSoldiers.setLevel(level);
					buildingSoldiers.setId(id);
					buildingSoldiers.setConsume(buildingConsume);
					buildingSoldiers.setDescription(description);
					buildingSoldiers.setBuildingType(buildingType);																			
					cities.setBuildingCavalry(buildingSoldiers);
					continue;
				}
			}
		}
		
		JSONArray skills = city.getJSONArray("skills");
		if (skills != null) {
			SkillResources skillResources = new SkillResources();
			SkillSoldiers skillSoldiers = new SkillSoldiers();
			SkillChurch skillChurch = new SkillChurch();
			
			for (int i = 0;i < skills.size();i++) {
				JSONObject skill = (JSONObject)skills.get(i);
				Long duration = (skill.get("duration") != null) ? skill.getLong("duration") : null;
				Long maxLevel = (skill.get("max_level") != null) ? skill.getLong("max_level") : null;
				Long level = (skill.get("level") != null) ? skill.getLong("level") : null;
				Long id = (skill.get("id") != null) ? skill.getLong("id") : null;
				Long leaderShip = (skill.get("leadership") != null) ? skill.getLong("leadership") : null;
				Long skillType = (skill.get("skill_type") != null) ? skill.getLong("skill_type") : null;
				
				if (skillType == null)
					continue;
								
				SkillConsume skillConsume = null;
				
				if (skill.get("consume") != null) {
					JSONObject consume = (JSONObject)skill.get("consume");
					skillConsume = new SkillConsume();
					skillConsume.setDuration((consume.get("duration") != null) ? consume.getLong("duration") : null);
					skillConsume.setGold((consume.get("gold") != null) ? consume.getLong("gold") : null);					
					skillConsume.setDescription((consume.get("description") != null) ? consume.getString("description") : null);
					skillConsume.setMessage((consume.get("message") != null) ? consume.getString("message") : null);
				}
				
				if (skillType.equals(1L)) {
					SkillBase skillBase = new SkillBase();
					skillBase.setDuration(duration);
					skillBase.setMaxLevel(maxLevel);
					skillBase.setLevel(level);
					skillBase.setId(id);
					skillBase.setLeaderShip(leaderShip);
					skillBase.setSkillType(skillType);
					skillBase.setConsume(skillConsume);										
					skillResources.setSkillWood(skillBase);
					continue;
				}
				
				if (skillType.equals(2L)) {
					SkillBase skillBase = new SkillBase();
					skillBase.setDuration(duration);
					skillBase.setMaxLevel(maxLevel);
					skillBase.setLevel(level);
					skillBase.setId(id);
					skillBase.setLeaderShip(leaderShip);
					skillBase.setSkillType(skillType);
					skillBase.setConsume(skillConsume);										
					skillResources.setSkillStone(skillBase);
					continue;
				}
				
				if (skillType.equals(3L)) {
					SkillBase skillBase = new SkillBase();
					skillBase.setDuration(duration);
					skillBase.setMaxLevel(maxLevel);
					skillBase.setLevel(level);
					skillBase.setId(id);
					skillBase.setLeaderShip(leaderShip);
					skillBase.setSkillType(skillType);
					skillBase.setConsume(skillConsume);										
					skillResources.setSkillIron(skillBase);
					continue;
				}
				
				if (skillType.equals(4L)) {
					SkillBase skillBase = new SkillBase();
					skillBase.setDuration(duration);
					skillBase.setMaxLevel(maxLevel);
					skillBase.setLevel(level);
					skillBase.setId(id);
					skillBase.setLeaderShip(leaderShip);
					skillBase.setSkillType(skillType);
					skillBase.setConsume(skillConsume);										
					skillResources.setSkillFood(skillBase);
					continue;
				}
				
				if (skillType.equals(7L)) {
					SkillBase skillBase = new SkillBase();
					skillBase.setDuration(duration);
					skillBase.setMaxLevel(maxLevel);
					skillBase.setLevel(level);
					skillBase.setId(id);
					skillBase.setLeaderShip(leaderShip);
					skillBase.setSkillType(skillType);
					skillBase.setConsume(skillConsume);										
					skillSoldiers.setInfantryAttack(skillBase);
					continue;
				}
				
				if (skillType.equals(8L)) {
					SkillBase skillBase = new SkillBase();
					skillBase.setDuration(duration);
					skillBase.setMaxLevel(maxLevel);
					skillBase.setLevel(level);
					skillBase.setId(id);
					skillBase.setLeaderShip(leaderShip);
					skillBase.setSkillType(skillType);
					skillBase.setConsume(skillConsume);										
					skillSoldiers.setCavalryAttack(skillBase);
					continue;
				}
				
				if (skillType.equals(9L)) {
					SkillBase skillBase = new SkillBase();
					skillBase.setDuration(duration);
					skillBase.setMaxLevel(maxLevel);
					skillBase.setLevel(level);
					skillBase.setId(id);
					skillBase.setLeaderShip(leaderShip);
					skillBase.setSkillType(skillType);
					skillBase.setConsume(skillConsume);										
					skillSoldiers.setInfantryDefense(skillBase);
					continue;
				}
				
				if (skillType.equals(10L)) {
					SkillBase skillBase = new SkillBase();
					skillBase.setDuration(duration);
					skillBase.setMaxLevel(maxLevel);
					skillBase.setLevel(level);
					skillBase.setId(id);
					skillBase.setLeaderShip(leaderShip);
					skillBase.setSkillType(skillType);
					skillBase.setConsume(skillConsume);										
					skillSoldiers.setCavalryDefense(skillBase);
					continue;
				}
				
				if (skillType.equals(13L)) {
					SkillBase skillBase = new SkillBase();
					skillBase.setDuration(duration);
					skillBase.setMaxLevel(maxLevel);
					skillBase.setLevel(level);
					skillBase.setId(id);
					skillBase.setLeaderShip(leaderShip);
					skillBase.setSkillType(skillType);
					skillBase.setConsume(skillConsume);										
					skillChurch.setChurchGift(skillBase);
					continue;
				}
				
				if (skillType.equals(14L)) {
					SkillBase skillBase = new SkillBase();
					skillBase.setDuration(duration);
					skillBase.setMaxLevel(maxLevel);
					skillBase.setLevel(level);
					skillBase.setId(id);
					skillBase.setLeaderShip(leaderShip);
					skillBase.setSkillType(skillType);
					skillBase.setConsume(skillConsume);										
					skillChurch.setChurchLucky(skillBase);
					continue;
				}
				
				if (skillType.equals(15L)) {
					SkillBase skillBase = new SkillBase();
					skillBase.setDuration(duration);
					skillBase.setMaxLevel(maxLevel);
					skillBase.setLevel(level);
					skillBase.setId(id);
					skillBase.setLeaderShip(leaderShip);
					skillBase.setSkillType(skillType);
					skillBase.setConsume(skillConsume);										
					skillChurch.setChurchRecall(skillBase);
					continue;
				}
				
				if (skillType.equals(16L)) {
					SkillBase skillBase = new SkillBase();
					skillBase.setDuration(duration);
					skillBase.setMaxLevel(maxLevel);
					skillBase.setLevel(level);
					skillBase.setId(id);
					skillBase.setLeaderShip(leaderShip);
					skillBase.setSkillType(skillType);
					skillBase.setConsume(skillConsume);										
					skillChurch.setChurchAngel(skillBase);
					continue;
				}
				
				if (skillType.equals(17L)) {
					SkillBase skillBase = new SkillBase();
					skillBase.setDuration(duration);
					skillBase.setMaxLevel(maxLevel);
					skillBase.setLevel(level);
					skillBase.setId(id);
					skillBase.setLeaderShip(leaderShip);
					skillBase.setSkillType(skillType);
					skillBase.setConsume(skillConsume);										
					skillResources.setSkillStore(skillBase);
					continue;
				}
			}
			
			cities.setSkillResources(skillResources);
			cities.setSkillSoldiers(skillSoldiers);
			cities.setSkillChurch(skillChurch);
		}
				
		if (city.get("resources") != null) {
			JSONObject resources = (JSONObject)city.get("resources");
			if (resources.get("gold") != null) {
				JSONObject gold = (JSONObject)resources.get("gold");
				ResourcesBase resourcesBase = new ResourcesBase();
				resourcesBase.setName((gold.get("name") != null) ? gold.getString("name") : null);
				resourcesBase.setIncreaseMaxPerHour((gold.get("increase_max_per_hour") != null) ? gold.getLong("increase_max_per_hour") : null);
				resourcesBase.setWorkerCount((gold.get("worker_count") != null) ? gold.getLong("worker_count") : null);
				resourcesBase.setIncreasePerHour((gold.get("increase_per_hour") != null) ? gold.getLong("increase_per_hour") : null);
				resourcesBase.setCount((gold.get("count") != null) ? gold.getLong("count") : null);
				resourcesBase.setWorkerCapacity((gold.get("worker_capacity") != null) ? gold.getLong("worker_capacity") : null);
				resourcesBase.setDecreasePerHour((gold.get("decrease_per_hour") != null) ? gold.getLong("decrease_per_hour") : null);
				resourcesBase.setVillageIncreasePerHour((gold.get("village_increase_per_hour") != null) ? gold.getLong("village_increase_per_hour") : null);
				resourcesBase.setCapacity((gold.get("capacity") != null) ? gold.getLong("capacity") : null);
				cities.setResourcesGold(resourcesBase);
			}
			
			if (resources.get("wood") != null) {
				JSONObject wood = (JSONObject)resources.get("wood");
				ResourcesBase resourcesBase = new ResourcesBase();
				resourcesBase.setName((wood.get("name") != null) ? wood.getString("name") : null);
				resourcesBase.setIncreaseMaxPerHour((wood.get("increase_max_per_hour") != null) ? wood.getLong("increase_max_per_hour") : null);
				resourcesBase.setWorkerCount((wood.get("worker_count") != null) ? wood.getLong("worker_count") : null);
				resourcesBase.setIncreasePerHour((wood.get("increase_per_hour") != null) ? wood.getLong("increase_per_hour") : null);
				resourcesBase.setCount((wood.get("count") != null) ? wood.getLong("count") : null);
				resourcesBase.setWorkerCapacity((wood.get("worker_capacity") != null) ? wood.getLong("worker_capacity") : null);
				resourcesBase.setDecreasePerHour((wood.get("decrease_per_hour") != null) ? wood.getLong("decrease_per_hour") : null);
				resourcesBase.setVillageIncreasePerHour((wood.get("village_increase_per_hour") != null) ? wood.getLong("village_increase_per_hour") : null);
				resourcesBase.setCapacity((wood.get("capacity") != null) ? wood.getLong("capacity") : null);
				cities.setResourcesWood(resourcesBase);
			}
			
			if (resources.get("stone") != null) {
				JSONObject stone = (JSONObject)resources.get("stone");
				ResourcesBase resourcesBase = new ResourcesBase();
				resourcesBase.setName((stone.get("name") != null) ? stone.getString("name") : null);
				resourcesBase.setIncreaseMaxPerHour((stone.get("increase_max_per_hour") != null) ? stone.getLong("increase_max_per_hour") : null);
				resourcesBase.setWorkerCount((stone.get("worker_count") != null) ? stone.getLong("worker_count") : null);
				resourcesBase.setIncreasePerHour((stone.get("increase_per_hour") != null) ? stone.getLong("increase_per_hour") : null);
				resourcesBase.setCount((stone.get("count") != null) ? stone.getLong("count") : null);
				resourcesBase.setWorkerCapacity((stone.get("worker_capacity") != null) ? stone.getLong("worker_capacity") : null);
				resourcesBase.setDecreasePerHour((stone.get("decrease_per_hour") != null) ? stone.getLong("decrease_per_hour") : null);
				resourcesBase.setVillageIncreasePerHour((stone.get("village_increase_per_hour") != null) ? stone.getLong("village_increase_per_hour") : null);
				resourcesBase.setCapacity((stone.get("capacity") != null) ? stone.getLong("capacity") : null);
				cities.setResourcesStone(resourcesBase);
			}
			
			if (resources.get("iron") != null) {
				JSONObject iron = (JSONObject)resources.get("iron");
				ResourcesBase resourcesBase = new ResourcesBase();
				resourcesBase.setName((iron.get("name") != null) ? iron.getString("name") : null);
				resourcesBase.setIncreaseMaxPerHour((iron.get("increase_max_per_hour") != null) ? iron.getLong("increase_max_per_hour") : null);
				resourcesBase.setWorkerCount((iron.get("worker_count") != null) ? iron.getLong("worker_count") : null);
				resourcesBase.setIncreasePerHour((iron.get("increase_per_hour") != null) ? iron.getLong("increase_per_hour") : null);
				resourcesBase.setCount((iron.get("count") != null) ? iron.getLong("count") : null);
				resourcesBase.setWorkerCapacity((iron.get("worker_capacity") != null) ? iron.getLong("worker_capacity") : null);
				resourcesBase.setDecreasePerHour((iron.get("decrease_per_hour") != null) ? iron.getLong("decrease_per_hour") : null);
				resourcesBase.setVillageIncreasePerHour((iron.get("village_increase_per_hour") != null) ? iron.getLong("village_increase_per_hour") : null);
				resourcesBase.setCapacity((iron.get("capacity") != null) ? iron.getLong("capacity") : null);
				cities.setResourcesIron(resourcesBase);
			}
			
			if (resources.get("food") != null) {
				JSONObject food = (JSONObject)resources.get("food");
				ResourcesBase resourcesBase = new ResourcesBase();
				resourcesBase.setName((food.get("name") != null) ? food.getString("name") : null);
				resourcesBase.setIncreaseMaxPerHour((food.get("increase_max_per_hour") != null) ? food.getLong("increase_max_per_hour") : null);
				resourcesBase.setWorkerCount((food.get("worker_count") != null) ? food.getLong("worker_count") : null);
				resourcesBase.setIncreasePerHour((food.get("increase_per_hour") != null) ? food.getLong("increase_per_hour") : null);
				resourcesBase.setCount((food.get("count") != null) ? food.getLong("count") : null);
				resourcesBase.setWorkerCapacity((food.get("worker_capacity") != null) ? food.getLong("worker_capacity") : null);
				resourcesBase.setDecreasePerHour((food.get("decrease_per_hour") != null) ? food.getLong("decrease_per_hour") : null);
				resourcesBase.setVillageIncreasePerHour((food.get("village_increase_per_hour") != null) ? food.getLong("village_increase_per_hour") : null);
				resourcesBase.setCapacity((food.get("capacity") != null) ? food.getLong("capacity") : null);
				cities.setResourcesFood(resourcesBase);
			}
			
			if (resources.get("leadership") != null) {
				JSONObject leader = (JSONObject)resources.get("leadership");
				ResourcesBase resourcesBase = new ResourcesBase();
				resourcesBase.setName((leader.get("name") != null) ? leader.getString("name") : null);
				resourcesBase.setIncreaseMaxPerHour((leader.get("increase_max_per_hour") != null) ? leader.getLong("increase_max_per_hour") : null);
				resourcesBase.setWorkerCount((leader.get("worker_count") != null) ? leader.getLong("worker_count") : null);
				resourcesBase.setIncreasePerHour((leader.get("increase_per_hour") != null) ? leader.getLong("increase_per_hour") : null);
				resourcesBase.setCount((leader.get("count") != null) ? leader.getLong("count") : null);
				resourcesBase.setWorkerCapacity((leader.get("worker_capacity") != null) ? leader.getLong("worker_capacity") : null);
				resourcesBase.setDecreasePerHour((leader.get("decrease_per_hour") != null) ? leader.getLong("decrease_per_hour") : null);
				resourcesBase.setVillageIncreasePerHour((leader.get("village_increase_per_hour") != null) ? leader.getLong("village_increase_per_hour") : null);
				resourcesBase.setCapacity((leader.get("capacity") != null) ? leader.getLong("capacity") : null);
				cities.setResourcesLeader(resourcesBase);
			}
			
			if (resources.get("workers") != null) {
				JSONObject worker = (JSONObject)resources.get("workers");
				ResourcesWorkers resourcesWorkers = new ResourcesWorkers();
				resourcesWorkers.setStoneCount((worker.get("stone_count") != null) ? worker.getLong("stone_count") : null);
				resourcesWorkers.setIronCount((worker.get("iron_count") != null) ? worker.getLong("iron_count") : null);
				resourcesWorkers.setFoodCount((worker.get("food_count") != null) ? worker.getLong("food_count") : null);
				resourcesWorkers.setWoodCount((worker.get("wood_count") != null) ? worker.getLong("wood_count") : null);
				resourcesWorkers.setFreeCount((worker.get("free_count") != null) ? worker.getLong("free_count") : null);
				resourcesWorkers.setBuildingCount((worker.get("building_count") != null) ? worker.getLong("building_count") : null);
				resourcesWorkers.setTotalCount((worker.get("total_count") != null) ? worker.getLong("total_count") : null);
				cities.setResourcesWorkers(resourcesWorkers);
			}
			
			cities.setGems((resources.get("gems") != null) ? resources.getLong("gems") : null);			
		}
		
		if (city.get("events") != null) {
			JSONObject events = (JSONObject)city.get("events");
			buildings = events.getJSONArray("building");
			if (buildings != null) {
				List<EventBuilding> eventBuildings = new Vector<EventBuilding>();
				for (int i = 0;i < buildings.size();i++) {
					JSONObject building = (JSONObject)buildings.get(i);
					Long duration = (building.get("duration") != null) ? building.getLong("duration") : null;
					Boolean canCancel = (building.get("can_cancel") != null) ? building.getBoolean("can_cancel") : null;
					String desc = (building.get("desc") != null) ? building.getString("desc") : null;
					Boolean emergency = (building.get("emergency") != null) ? building.getBoolean("emergency") : null;
					
					EventBuilding eventBuilding = new EventBuilding();
					eventBuilding.setDuration(duration);
					eventBuilding.setCanCancel(canCancel);
					eventBuilding.setDesc(desc);
					eventBuilding.setEmergency(emergency);					
					eventBuilding.setFinishDesc((building.get("finish_desc") != null) ? building.getString("finish_desc") : null);
					eventBuilding.setCanSpeed((building.get("can_speed") != null) ? building.getBoolean("can_speed") : null);
					eventBuilding.setType((building.get("type") != null) ? building.getLong("type") : null);					
					eventBuildings.add(eventBuilding);
				}
				
				cities.setEventBuilding(eventBuildings);
			}
			
			JSONArray armys = events.getJSONArray("army_event");
			if (armys != null) {
				List<EventArmy> eventArmys = new Vector<EventArmy>();
				for (int i = 0;i < armys.size();i++) {
					JSONObject army = (JSONObject)armys.get(i);
					Long duration = (army.get("duration") != null) ? army.getLong("duration") : null;
					Boolean canCancel = (army.get("can_cancel") != null) ? army.getBoolean("can_cancel") : null;
					String desc = (army.get("desc") != null) ? army.getString("desc") : null;
					Boolean emergency = (army.get("emergency") != null) ? army.getBoolean("emergency") : null;
					
					EventArmy eventArmy = new EventArmy();
					eventArmy.setDuration(duration);
					eventArmy.setCanCancel(canCancel);
					eventArmy.setDesc(desc);
					eventArmy.setEmergency(emergency);					
					eventArmy.setFromCityName((army.get("from_city_name") != null) ? army.getString("from_city_name") : null);
					eventArmy.setTotalTime((army.get("total_time") != null) ? army.getLong("total_time") : null);
					eventArmy.setArriveTime((army.get("arrive_time") != null) ? DateTime.getTime(army.getLong("arrive_time")) : null);
					eventArmy.setMission((army.get("mission") != null) ? army.getLong("mission") : null);
					eventArmy.setId((army.get("id") != null) ? army.getLong("id") : null);
					eventArmy.setToCityName((army.get("to_city_name") != null) ? army.getString("to_city_name") : null);
					eventArmys.add(eventArmy);
				}
				
				cities.setEventArmy(eventArmys);
			}
			
			JSONArray protections = events.getJSONArray("protection");
			if (protections != null) {
				for (int i = 0;i < protections.size();i++) {
					JSONObject protection = (JSONObject)protections.get(i);
					Long duration = (protection.get("duration") != null) ? protection.getLong("duration") : null;
					Boolean canCancel = (protection.get("can_cancel") != null) ? protection.getBoolean("can_cancel") : null;
					String desc = (protection.get("desc") != null) ? protection.getString("desc") : null;
					Boolean emergency = (protection.get("emergency") != null) ? protection.getBoolean("emergency") : null;
					
					EventProtection eventProtection = new EventProtection();
					eventProtection.setDuration(duration);
					eventProtection.setCanCancel(canCancel);
					eventProtection.setDesc(desc);
					eventProtection.setEmergency(emergency);				
					eventProtection.setFinishTime((protection.get("finish_time") != null) ? DateTime.getTime(protection.getLong("finish_time")) : null);
					cities.setEventProtection(eventProtection);
				}
			}
			
			skills = events.getJSONArray("skill");
			if (skills != null) {
				List<EventSkill> eventSkills = new Vector<EventSkill>();
				for (int i = 0;i < skills.size();i++) {
					JSONObject skill = (JSONObject)skills.get(i);
					Long duration = (skill.get("duration") != null) ? skill.getLong("duration") : null;
					Boolean canCancel = (skill.get("can_cancel") != null) ? skill.getBoolean("can_cancel") : null;
					String desc = (skill.get("desc") != null) ? skill.getString("desc") : null;
					Boolean emergency = (skill.get("emergency") != null) ? skill.getBoolean("emergency") : null;
					
					EventSkill eventSkill = new EventSkill();
					eventSkill.setDuration(duration);
					eventSkill.setCanCancel(canCancel);
					eventSkill.setDesc(desc);
					eventSkill.setEmergency(emergency);					
					eventSkill.setFinishDesc((skill.get("finish_desc") != null) ? skill.getString("finish_desc") : null);
					eventSkill.setCanSpeed((skill.get("can_speed") != null) ? skill.getBoolean("can_speed") : null);
					eventSkill.setType((skill.get("type") != null) ? skill.getLong("type") : null);
					eventSkills.add(eventSkill);
				}
				
				cities.setEventSkill(eventSkills);
			}
			
			JSONArray trains = events.getJSONArray("train");
			if (trains != null) {
				List<EventTrain> eventTrains = new Vector<EventTrain>();
				for (int i = 0;i < trains.size();i++) {
					JSONObject train = (JSONObject)trains.get(i);
					Long duration = (train.get("duration") != null) ? train.getLong("duration") : null;
					Boolean canCancel = (train.get("can_cancel") != null) ? train.getBoolean("can_cancel") : null;
					String desc = (train.get("desc") != null) ? train.getString("desc") : null;
					Boolean emergency = (train.get("emergency") != null) ? train.getBoolean("emergency") : null;
					
					EventTrain eventTrain = new EventTrain();
					eventTrain.setDuration(duration);
					eventTrain.setCanCancel(canCancel);
					eventTrain.setDesc(desc);
					eventTrain.setEmergency(emergency);
					eventTrain.setFinishDesc((train.get("finish_desc") != null) ? train.getString("finish_desc") : null);
					eventTrain.setCanSpeed((train.get("can_speed") != null) ? train.getBoolean("can_speed") : null);
					eventTrain.setType((train.get("type") != null) ? train.getLong("type") : null);
					eventTrain.setId((train.get("id") != null) ? train.getLong("id") : null);					
					eventTrain.setCount((train.get("count") != null) ? train.getLong("count") : null);
					eventTrain.setFinishTime((train.get("finish_time") != null) ? DateTime.getTime(train.getLong("finish_time")) : null);
					eventTrains.add(eventTrain);
				}
				
				cities.setEventTrain(eventTrains);
			}
		}
		
		if (city.get("soldiers") != null) {
			JSONArray soldiers = city.getJSONArray("soldiers");
			for (int i = 0;i < soldiers.size();i++) {
				JSONObject soldier = (JSONObject)soldiers.get(i);
				String name = (soldier.get("name") != null) ? soldier.getString("name") : null;
				Long consumeFood = (soldier.get("consume_food") != null) ? soldier.getLong("consume_food") : null;
				Double addDefMagic = (soldier.get("add_def_magic") != null) ? soldier.getDouble("add_def_magic") : null;
				Long soldierType = (soldier.get("soldier_type") != null) ? soldier.getLong("soldier_type") : null;
				Long canMove = (soldier.get("can_move") != null) ? soldier.getLong("can_move") : null;
				Double addDefInfantry = (soldier.get("add_def_infantry") != null) ? soldier.getDouble("add_def_infantry") : null;
				Long defArtillery = (soldier.get("def_artillery") != null) ? soldier.getLong("def_artillery") : null;
				Long defCavalry = (soldier.get("def_cavalry") != null) ? soldier.getLong("def_cavalry") : null;
				Double addDefArtillery = (soldier.get("add_def_artillery") != null) ? soldier.getDouble("add_def_artillery") : null;
				Long count = (soldier.get("count") != null) ? soldier.getLong("count") : null;
				Long speed = (soldier.get("speed") != null) ? soldier.getLong("speed") : null;
				Double addDefCavalry = (soldier.get("add_def_cavalry") != null) ? soldier.getDouble("add_def_cavalry") : null;
				Double addAttack = (soldier.get("add_attack") != null) ? soldier.getDouble("add_attack") : null;
				Long defMagic = (soldier.get("def_magic") != null) ? soldier.getLong("def_magic") : null;
				Long attackType = (soldier.get("attack_type") != null) ? soldier.getLong("attack_type") : null;
				Long defInfantry = (soldier.get("def_infantry") != null) ? soldier.getLong("def_infantry") : null;
				Long attack = (soldier.get("attack") != null) ? soldier.getLong("attack") : null;
				
				if (soldierType == null)
					continue;
								
				SoldierConsume soldierConsume = null;
				
				if (soldier.get("consume") != null) {
					JSONObject consume = (JSONObject)soldier.get("consume");
					soldierConsume = new SoldierConsume();
					soldierConsume.setDuration((consume.get("duration") != null) ? consume.getDouble("duration") : null);
					soldierConsume.setGold((consume.get("gold") != null) ? consume.getLong("gold") : null);
					soldierConsume.setWood((consume.get("wood") != null) ? consume.getLong("wood") : null);
					soldierConsume.setStone((consume.get("stone") != null) ? consume.getLong("stone") : null);
					soldierConsume.setFood((consume.get("food") != null) ? consume.getLong("food") : null);
					soldierConsume.setIron((consume.get("iron") != null) ? consume.getLong("iron") : null);
					soldierConsume.setMessage((consume.get("message") != null) ? consume.getString("message") : null);
				}
				
				if (soldierType.equals(0L)) {
					SoldierBase soldierBase = new SoldierBase();
					soldierBase.setName(name);
					soldierBase.setAddDefMagic(addDefMagic);
					soldierBase.setConsumeFood(consumeFood);
					soldierBase.setSoldierType(soldierType);
					soldierBase.setCanMove(canMove);
					soldierBase.setAddDefInfantry(addDefInfantry);
					soldierBase.setDefArtillery(defArtillery);
					soldierBase.setDefCavalry(defCavalry);
					soldierBase.setAddDefArtillery(addDefArtillery);
					soldierBase.setCount(count);
					soldierBase.setSpeed(speed);
					soldierBase.setAddDefCavalry(addDefCavalry);
					soldierBase.setAddAttack(addAttack);
					soldierBase.setDefMagic(defMagic);
					soldierBase.setAttackType(attackType);
					soldierBase.setDefInfantry(defInfantry);
					soldierBase.setAttack(attack);
					soldierBase.setConsume(soldierConsume);
					cities.setInfantrySwords(soldierBase);
					continue;
				}
				
				if (soldierType.equals(1L)) {
					SoldierBase soldierBase = new SoldierBase();
					soldierBase.setName(name);
					soldierBase.setAddDefMagic(addDefMagic);
					soldierBase.setConsumeFood(consumeFood);
					soldierBase.setSoldierType(soldierType);
					soldierBase.setCanMove(canMove);
					soldierBase.setAddDefInfantry(addDefInfantry);
					soldierBase.setDefArtillery(defArtillery);
					soldierBase.setDefCavalry(defCavalry);
					soldierBase.setAddDefArtillery(addDefArtillery);
					soldierBase.setCount(count);
					soldierBase.setSpeed(speed);
					soldierBase.setAddDefCavalry(addDefCavalry);
					soldierBase.setAddAttack(addAttack);
					soldierBase.setDefMagic(defMagic);
					soldierBase.setAttackType(attackType);
					soldierBase.setDefInfantry(defInfantry);
					soldierBase.setAttack(attack);
					soldierBase.setConsume(soldierConsume);
					cities.setInfantryScout(soldierBase);
					continue;
				}
				
				if (soldierType.equals(2L)) {
					SoldierBase soldierBase = new SoldierBase();
					soldierBase.setName(name);
					soldierBase.setAddDefMagic(addDefMagic);
					soldierBase.setConsumeFood(consumeFood);
					soldierBase.setSoldierType(soldierType);
					soldierBase.setCanMove(canMove);
					soldierBase.setAddDefInfantry(addDefInfantry);
					soldierBase.setDefArtillery(defArtillery);
					soldierBase.setDefCavalry(defCavalry);
					soldierBase.setAddDefArtillery(addDefArtillery);
					soldierBase.setCount(count);
					soldierBase.setSpeed(speed);
					soldierBase.setAddDefCavalry(addDefCavalry);
					soldierBase.setAddAttack(addAttack);
					soldierBase.setDefMagic(defMagic);
					soldierBase.setAttackType(attackType);
					soldierBase.setDefInfantry(defInfantry);
					soldierBase.setAttack(attack);
					soldierBase.setConsume(soldierConsume);
					cities.setInfantryCrossbow(soldierBase);
					continue;
				}
				
				if (soldierType.equals(3L)) {
					SoldierBase soldierBase = new SoldierBase();
					soldierBase.setName(name);
					soldierBase.setAddDefMagic(addDefMagic);
					soldierBase.setConsumeFood(consumeFood);
					soldierBase.setSoldierType(soldierType);
					soldierBase.setCanMove(canMove);
					soldierBase.setAddDefInfantry(addDefInfantry);
					soldierBase.setDefArtillery(defArtillery);
					soldierBase.setDefCavalry(defCavalry);
					soldierBase.setAddDefArtillery(addDefArtillery);
					soldierBase.setCount(count);
					soldierBase.setSpeed(speed);
					soldierBase.setAddDefCavalry(addDefCavalry);
					soldierBase.setAddAttack(addAttack);
					soldierBase.setDefMagic(defMagic);
					soldierBase.setAttackType(attackType);
					soldierBase.setDefInfantry(defInfantry);
					soldierBase.setAttack(attack);
					soldierBase.setConsume(soldierConsume);
					cities.setInfantrySquire(soldierBase);
					continue;
				}
				
				if (soldierType.equals(4L)) {
					SoldierBase soldierBase = new SoldierBase();
					soldierBase.setName(name);
					soldierBase.setAddDefMagic(addDefMagic);
					soldierBase.setConsumeFood(consumeFood);
					soldierBase.setSoldierType(soldierType);
					soldierBase.setCanMove(canMove);
					soldierBase.setAddDefInfantry(addDefInfantry);
					soldierBase.setDefArtillery(defArtillery);
					soldierBase.setDefCavalry(defCavalry);
					soldierBase.setAddDefArtillery(addDefArtillery);
					soldierBase.setCount(count);
					soldierBase.setSpeed(speed);
					soldierBase.setAddDefCavalry(addDefCavalry);
					soldierBase.setAddAttack(addAttack);
					soldierBase.setDefMagic(defMagic);
					soldierBase.setAttackType(attackType);
					soldierBase.setDefInfantry(defInfantry);
					soldierBase.setAttack(attack);
					soldierBase.setConsume(soldierConsume);
					cities.setCavalryTemplar(soldierBase);
					continue;
				}
				
				if (soldierType.equals(5L)) {
					SoldierBase soldierBase = new SoldierBase();
					soldierBase.setName(name);
					soldierBase.setAddDefMagic(addDefMagic);
					soldierBase.setConsumeFood(consumeFood);
					soldierBase.setSoldierType(soldierType);
					soldierBase.setCanMove(canMove);
					soldierBase.setAddDefInfantry(addDefInfantry);
					soldierBase.setDefArtillery(defArtillery);
					soldierBase.setDefCavalry(defCavalry);
					soldierBase.setAddDefArtillery(addDefArtillery);
					soldierBase.setCount(count);
					soldierBase.setSpeed(speed);
					soldierBase.setAddDefCavalry(addDefCavalry);
					soldierBase.setAddAttack(addAttack);
					soldierBase.setDefMagic(defMagic);
					soldierBase.setAttackType(attackType);
					soldierBase.setDefInfantry(defInfantry);
					soldierBase.setAttack(attack);
					soldierBase.setConsume(soldierConsume);
					cities.setCavalryArcher(soldierBase);
					continue;
				}
				
				if (soldierType.equals(6L)) {
					SoldierBase soldierBase = new SoldierBase();
					soldierBase.setName(name);
					soldierBase.setAddDefMagic(addDefMagic);
					soldierBase.setConsumeFood(consumeFood);
					soldierBase.setSoldierType(soldierType);
					soldierBase.setCanMove(canMove);
					soldierBase.setAddDefInfantry(addDefInfantry);
					soldierBase.setDefArtillery(defArtillery);
					soldierBase.setDefCavalry(defCavalry);
					soldierBase.setAddDefArtillery(addDefArtillery);
					soldierBase.setCount(count);
					soldierBase.setSpeed(speed);
					soldierBase.setAddDefCavalry(addDefCavalry);
					soldierBase.setAddAttack(addAttack);
					soldierBase.setDefMagic(defMagic);
					soldierBase.setAttackType(attackType);
					soldierBase.setDefInfantry(defInfantry);
					soldierBase.setAttack(attack);
					soldierBase.setConsume(soldierConsume);
					cities.setCavalryPaladin(soldierBase);
					continue;
				}
				
				if (soldierType.equals(7L)) {
					SoldierBase soldierBase = new SoldierBase();
					soldierBase.setName(name);
					soldierBase.setAddDefMagic(addDefMagic);
					soldierBase.setConsumeFood(consumeFood);
					soldierBase.setSoldierType(soldierType);
					soldierBase.setCanMove(canMove);
					soldierBase.setAddDefInfantry(addDefInfantry);
					soldierBase.setDefArtillery(defArtillery);
					soldierBase.setDefCavalry(defCavalry);
					soldierBase.setAddDefArtillery(addDefArtillery);
					soldierBase.setCount(count);
					soldierBase.setSpeed(speed);
					soldierBase.setAddDefCavalry(addDefCavalry);
					soldierBase.setAddAttack(addAttack);
					soldierBase.setDefMagic(defMagic);
					soldierBase.setAttackType(attackType);
					soldierBase.setDefInfantry(defInfantry);
					soldierBase.setAttack(attack);
					soldierBase.setConsume(soldierConsume);
					cities.setCavalryRoyal(soldierBase);
					continue;
				}
			}
		}
		
		cities.setWishingVersion((city.get("wishing_version") != null) ? city.getString("wishing_version") : null);
		cities.setCountryName((city.get("country_name") != null) ? city.getString("country_name") : null);
		cities.setName((city.get("name") != null) ? city.getString("name") : null);
		cities.setLoginDaysCount((city.get("login_days_count") != null) ? city.getLong("login_days_count") : null);
		cities.setLeftWishingCount((city.get("left_wishing_count") != null) ? city.getLong("left_wishing_count") : null);
		cities.setTitle((city.get("title") != null) ? city.getString("title") : null);
		cities.setX((city.get("x") != null) ? city.getLong("x") : null);
		cities.setY((city.get("y") != null) ? city.getLong("y") : null);
		cities.setFriendApplication((city.get("friend_application") != null) ? city.getLong("friend_application") : null);
		cities.setMax((city.get("max") != null) ? city.getLong("max") : null);
		cities.setCountryId((city.get("country_id") != null) ? city.getLong("country_id") : null);
		cities.setLevel((city.get("level") != null) ? city.getLong("level") : null);
		cities.setOwnerId((city.get("owner_id") != null) ? city.getLong("owner_id") : null);
		cities.setCurrentServerTime((city.get("current_server_time") != null) ? DateTime.getTime(city.getLong("current_server_time")) : null);
		cities.setOwnerId((city.get("owner_id") != null) ? city.getLong("owner_id") : null);
		cities.setCitiesCount((city.get("cities_count") != null) ? city.getLong("cities_count") : null);
		cities.setNewReward((city.get("new_reward") != null) ? city.getBoolean("new_reward") : null);
		cities.setId((city.get("id") != null) ? city.getLong("id") : null);
		cities.setAccountLevel((city.get("account_level") != null) ? city.getLong("account_level") : null);
		cities.setMin((city.get("min") != null) ? city.getLong("min") : null);
		cities.setNewMessage((city.get("new_message") != null) ? city.getLong("new_message") : null);
		cities.setMarketLeftCapacity((city.get("market_left_capacity") != null) ? city.getLong("market_left_capacity") : null);
		cities.setMarketEffecitve((city.get("market_effecitve") != null) ? city.getLong("market_effecitve") : null);
		cities.setAnnouncement((city.get("announcement") != null) ? city.getBoolean("announcement") : null);
		cities.setScore((city.get("score") != null) ? city.getLong("score") : null);
		cities.setMaxUnitPrice((city.get("max_unit_price") != null) ? city.getLong("max_unit_price") : null);
		cities.setMaxCitiesCount((city.get("max_cities_count") != null) ? city.getLong("max_cities_count") : null);
		cities.setIsCapital((city.get("is_capital") != null) ? city.getBoolean("is_capital") : null);
		cities.setNoviceStep((city.get("novice_step") != null) ? city.getLong("novice_step") : null);
		cities.setOwner((city.get("owner") != null) ? city.getString("owner") : null);
		return cities;
	}
}
