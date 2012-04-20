package com.towns;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.buildings.Building;
import com.buildings.BuildingCellar;
import com.buildings.BuildingLine;
import com.buildings.BuildingMarket;
import com.buildings.BuildingPort;
import com.buildings.BuildingResource;
import com.buildings.BuildingSoldier;
import com.buildings.BuildingStore;
import com.buildings.BuildingTower;
import com.buildings.BuildingWall;
import com.hero.Hero;
import com.queue.BattleQueue;
import com.queue.BuildingQueue;
import com.queue.TransportQueue;
import com.util.DateTime;

/*	1	兵营
 * 	2	木头
 * 	3	仓库
 * 	4	港口
 * 	5	食物
 * 	6	大理石
 * 	7	黑铁
 * 	8	黄金
 * 	9	市场
 * 10	大厅
 * 11	城墙
 * 13	造船厂
 * 14	地窖
 */

public class Town {
	private String cityArmyStatus;
	private Building buildingHall;
	private BuildingResource buildingWood;
	private BuildingResource buildingFood;
	private BuildingResource buildingGold;
	private BuildingResource buildingIron;
	private BuildingResource buildingMarble;
	private BuildingStore buildingStore;
	private BuildingSoldier buildingBarrack;
	private BuildingSoldier buildingYard;
	private BuildingPort buildingPort;
	private BuildingWall buildingWall;
	private BuildingMarket buildingMarket;
	private BuildingCellar buildingCellar;
	private String name;
	private Alliance alliance;
	private Boolean showWelcome;
	private Long resourceType;
	private List<BattleQueue> battleQueues;
	private Double cityArmyPower;
	private Long level;
	private Long presenceDefenders;
	private Date currentServerTime;
	private Long accountType;
	private Resources resourcesWood;
	private Resources resourcesFood;
	private Resources resourcesGold;
	private Resources resourcesIron;
	private Resources resourcesMarble;
	private Boolean newReward;
	private Long ownerId;
	private Long islandX;
	private Long id;
	private Long presenceScout;
	private String cityStatus;
	private Long islandY;
	private Long islandZ;
	private List<TransportQueue> transportQueues;
	private Long gems;
	private Soldier soldierInfantry;
	private Soldier soldierScout;
	private Soldier soldierMusketman;
	private Soldier soldierCatapult;
	private Soldier soldierFrigate;
	private Soldier soldierDestroyer;
	private List<BuildingQueue> buildingQueues;
	private Boolean haveNewMessage;
	private List<OtherTown> otherTowns;
	private Hero hero;
	private String owner;
	private Boolean isCapital;
	private Long islandNumber;
	private Long totalFoodCost;

	public String getCityArmyStatus() {
		return cityArmyStatus;
	}

	public Building getBuildingHall() {
		return buildingHall;
	}

	public BuildingResource getBuildingWood() {
		return buildingWood;
	}

	public BuildingResource getBuildingFood() {
		return buildingFood;
	}

	public BuildingResource getBuildingGold() {
		return buildingGold;
	}

	public BuildingResource getBuildingIron() {
		return buildingIron;
	}

	public BuildingResource getBuildingMarble() {
		return buildingMarble;
	}

	public BuildingStore getBuildingStore() {
		return buildingStore;
	}

	public BuildingSoldier getBuildingBarrack() {
		return buildingBarrack;
	}

	public BuildingSoldier getBuildingYard() {
		return buildingYard;
	}

	public BuildingPort getBuildingPort() {
		return buildingPort;
	}

	public BuildingWall getBuildingWall() {
		return buildingWall;
	}

	public BuildingMarket getBuildingMarket() {
		return buildingMarket;
	}

	public BuildingCellar getBuildingCellar() {
		return buildingCellar;
	}

	public String getName() {
		return name;
	}

	public Alliance getAlliance() {
		return alliance;
	}

	public Boolean getShowWelcome() {
		return showWelcome;
	}

	public Long getResourceType() {
		return resourceType;
	}

	public List<BattleQueue> getBattleQueues() {
		return battleQueues;
	}

	public Double getCityArmyPower() {
		return cityArmyPower;
	}

	public Long getLevel() {
		return level;
	}

	public Long getPresenceDefenders() {
		return presenceDefenders;
	}

	public Date getCurrentServerTime() {
		return currentServerTime;
	}

	public Long getAccountType() {
		return accountType;
	}

	public Resources getResourcesWood() {
		return resourcesWood;
	}

	public Resources getResourcesFood() {
		return resourcesFood;
	}

	public Resources getResourcesGold() {
		return resourcesGold;
	}

	public Resources getResourcesIron() {
		return resourcesIron;
	}

	public Resources getResourcesMarble() {
		return resourcesMarble;
	}

	public Boolean getNewReward() {
		return newReward;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public Long getIslandX() {
		return islandX;
	}

	public Long getId() {
		return id;
	}

	public Long getPresenceScout() {
		return presenceScout;
	}

	public String getCityStatus() {
		return cityStatus;
	}

	public Long getIslandY() {
		return islandY;
	}

	public Long getIslandZ() {
		return islandZ;
	}

	public List<TransportQueue> getTransportQueues() {
		return transportQueues;
	}

	public Long getGems() {
		return gems;
	}

	public Soldier getSoldierInfantry() {
		return soldierInfantry;
	}

	public Soldier getSoldierScout() {
		return soldierScout;
	}

	public Soldier getSoldierMusketman() {
		return soldierMusketman;
	}

	public Soldier getSoldierCatapult() {
		return soldierCatapult;
	}

	public Soldier getSoldierFrigate() {
		return soldierFrigate;
	}

	public Soldier getSoldierDestroyer() {
		return soldierDestroyer;
	}

	public List<BuildingQueue> getBuildingQueues() {
		return buildingQueues;
	}

	public Boolean getHaveNewMessage() {
		return haveNewMessage;
	}

	public List<OtherTown> getOtherTowns() {
		return otherTowns;
	}

	public Hero getHero() {
		return hero;
	}

	public String getOwner() {
		return owner;
	}

	public Boolean getIsCapital() {
		return isCapital;
	}

	public Long getIslandNumber() {
		return islandNumber;
	}

	public Long getTotalFoodCost() {
		return totalFoodCost;
	}

	public void setCityArmyStatus(String cityArmyStatus) {
		this.cityArmyStatus = cityArmyStatus;
	}

	public void setBuildingHall(Building buildingHall) {
		this.buildingHall = buildingHall;
	}

	public void setBuildingWood(BuildingResource buildingWood) {
		this.buildingWood = buildingWood;
	}

	public void setBuildingFood(BuildingResource buildingFood) {
		this.buildingFood = buildingFood;
	}

	public void setBuildingGold(BuildingResource buildingGold) {
		this.buildingGold = buildingGold;
	}

	public void setBuildingIron(BuildingResource buildingIron) {
		this.buildingIron = buildingIron;
	}

	public void setBuildingMarble(BuildingResource buildingMarble) {
		this.buildingMarble = buildingMarble;
	}

	public void setBuildingStore(BuildingStore buildingStore) {
		this.buildingStore = buildingStore;
	}

	public void setBuildingBarrack(BuildingSoldier buildingBarrack) {
		this.buildingBarrack = buildingBarrack;
	}

	public void setBuildingYard(BuildingSoldier buildingYard) {
		this.buildingYard = buildingYard;
	}

	public void setBuildingPort(BuildingPort buildingPort) {
		this.buildingPort = buildingPort;
	}

	public void setBuildingWall(BuildingWall buildingWall) {
		this.buildingWall = buildingWall;
	}

	public void setBuildingMarket(BuildingMarket buildingMarket) {
		this.buildingMarket = buildingMarket;
	}

	public void setBuildingCellar(BuildingCellar buildingCellar) {
		this.buildingCellar = buildingCellar;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAlliance(Alliance alliance) {
		this.alliance = alliance;
	}

	public void setShowWelcome(Boolean showWelcome) {
		this.showWelcome = showWelcome;
	}

	public void setResourceType(Long resourceType) {
		this.resourceType = resourceType;
	}

	public void setBattleQueues(List<BattleQueue> battleQueues) {
		this.battleQueues = battleQueues;
	}

	public void setCityArmyPower(Double cityArmyPower) {
		this.cityArmyPower = cityArmyPower;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public void setPresenceDefenders(Long presenceDefenders) {
		this.presenceDefenders = presenceDefenders;
	}

	public void setCurrentServerTime(Date currentServerTime) {
		this.currentServerTime = currentServerTime;
	}

	public void setAccountType(Long accountType) {
		this.accountType = accountType;
	}

	public void setResourcesWood(Resources resourcesWood) {
		this.resourcesWood = resourcesWood;
	}

	public void setResourcesFood(Resources resourcesFood) {
		this.resourcesFood = resourcesFood;
	}

	public void setResourcesGold(Resources resourcesGold) {
		this.resourcesGold = resourcesGold;
	}

	public void setResourcesIron(Resources resourcesIron) {
		this.resourcesIron = resourcesIron;
	}

	public void setResourcesMarble(Resources resourcesMarble) {
		this.resourcesMarble = resourcesMarble;
	}

	public void setNewReward(Boolean newReward) {
		this.newReward = newReward;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public void setIslandX(Long islandX) {
		this.islandX = islandX;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPresenceScout(Long presenceScout) {
		this.presenceScout = presenceScout;
	}

	public void setCityStatus(String cityStatus) {
		this.cityStatus = cityStatus;
	}

	public void setIslandY(Long islandY) {
		this.islandY = islandY;
	}

	public void setIslandZ(Long islandZ) {
		this.islandZ = islandZ;
	}

	public void setTransportQueues(List<TransportQueue> transportQueues) {
		this.transportQueues = transportQueues;
	}

	public void setGems(Long gems) {
		this.gems = gems;
	}

	public void setSoldierInfantry(Soldier soldierInfantry) {
		this.soldierInfantry = soldierInfantry;
	}

	public void setSoldierScout(Soldier soldierScout) {
		this.soldierScout = soldierScout;
	}

	public void setSoldierMusketman(Soldier soldierMusketman) {
		this.soldierMusketman = soldierMusketman;
	}

	public void setSoldierCatapult(Soldier soldierCatapult) {
		this.soldierCatapult = soldierCatapult;
	}

	public void setSoldierFrigate(Soldier soldierFrigate) {
		this.soldierFrigate = soldierFrigate;
	}

	public void setSoldierDestroyer(Soldier soldierDestroyer) {
		this.soldierDestroyer = soldierDestroyer;
	}

	public void setBuildingQueues(List<BuildingQueue> buildingQueues) {
		this.buildingQueues = buildingQueues;
	}

	public void setHaveNewMessage(Boolean haveNewMessage) {
		this.haveNewMessage = haveNewMessage;
	}

	public void setOtherTowns(List<OtherTown> otherTowns) {
		this.otherTowns = otherTowns;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setIsCapital(Boolean isCapital) {
		this.isCapital = isCapital;
	}

	public void setIslandNumber(Long islandNumber) {
		this.islandNumber = islandNumber;
	}

	public void setTotalFoodCost(Long totalFoodCost) {
		this.totalFoodCost = totalFoodCost;
	}

	public static Town parse(String response) {
		JSONObject json = JSONObject.fromObject(response);
		if (json.get("town") == null)
			return null;

		Town towns = new Town();
		JSONObject town = (JSONObject) json.get("town");
		towns.setCityArmyStatus((town.get("city_army_status") != null) ? town.getString("city_army_status") : null);
		JSONArray buildings = town.getJSONArray("buildings");
		if (buildings != null) {
			for (int i = 0; i < buildings.size(); i++) {
				JSONObject building = (JSONObject) buildings.get(i);
				Long level = (building.get("level") != null) ? building.getLong("level") : null;
				Long id = (building.get("id") != null) ? building.getLong("id")	: null;
				Long buildingType = (building.get("building_type") != null) ? building.getLong("building_type") : null;
				String status = (building.get("status") != null) ? building.getString("status") : null;
				Long anchorIndex = (building.get("anchor_index") != null) ? building.getLong("anchor_index") : null;

				if (buildingType == null)
					continue;
				
				if (buildingType.equals(1L)) {
					BuildingSoldier buildingBarrack = new BuildingSoldier();
					buildingBarrack.setLevel(level);						
					buildingBarrack.setId(id);
					buildingBarrack.setBuildingType(buildingType);
					buildingBarrack.setStatus(status);
					buildingBarrack.setAnchorIndex(anchorIndex);					
					if (building.get("property") != null) {
						JSONObject property = (JSONObject) building.get("property");
						buildingBarrack.setReduceTimeRate((property.get("reduce_time_rate") != null) ? property.getLong("reduce_time_rate") : null);
					}
										
					towns.setBuildingBarrack(buildingBarrack);
				}
								
				if (buildingType.equals(2L) || buildingType.equals(5L) || buildingType.equals(6L) || buildingType.equals(7L) || buildingType.equals(8L)) {
					JSONArray lines = building.getJSONArray("lines");
					List<BuildingLine> buildingLines = new Vector<BuildingLine>();
					if (lines != null) {						
						for (int j = 0; j < lines.size(); j++) {
							JSONObject line = (JSONObject) lines.get(j);		
							BuildingLine buildingLine = new BuildingLine();
							buildingLine.setLevel((line.get("level") != null) ? line.getLong("level") : null);
							buildingLine.setId((line.get("id") != null) ? line.getLong("id") : null);
							buildingLine.setCurrentOutput((line.get("current_output") != null) ? line.getLong("current_output") : null);
							buildingLine.setStatus((line.get("status") != null) ? line.getString("status") : null);
							buildingLines.add(buildingLine);
						}
					}
					
					BuildingResource buildingResource = new BuildingResource();
					buildingResource.setLevel(level);						
					buildingResource.setId(id);
					buildingResource.setBuildingType(buildingType);
					buildingResource.setStatus(status);
					buildingResource.setAnchorIndex(anchorIndex);
					buildingResource.setLine(buildingLines);
										
					if (buildingType.equals(2L)) {
						towns.setBuildingWood(buildingResource);
						continue;
					}
					
					if (buildingType.equals(5L)) {
						towns.setBuildingFood(buildingResource);
						continue;
					}
					
					if (buildingType.equals(6L)) {
						towns.setBuildingMarble(buildingResource);
						continue;
					}
					
					if (buildingType.equals(7L)) {
						towns.setBuildingIron(buildingResource);
						continue;
					}
					
					if (buildingType.equals(8L)) {
						towns.setBuildingGold(buildingResource);
						continue;
					}
				}
				
				if (buildingType.equals(3L)) {
					BuildingStore buildingStore = new BuildingStore();
					buildingStore.setLevel(level);						
					buildingStore.setId(id);
					buildingStore.setBuildingType(buildingType);
					buildingStore.setStatus(status);
					buildingStore.setAnchorIndex(anchorIndex);					
					if (building.get("property") != null) {
						JSONObject property = (JSONObject) building.get("property");
						buildingStore.setCapacity((property.get("capacity") != null) ? property.getLong("capacity") : null);
					}					
										
					towns.setBuildingStore(buildingStore);
				}
				
				if (buildingType.equals(4L)) {
					BuildingPort buildingPort = new BuildingPort();
					buildingPort.setLevel(level);						
					buildingPort.setId(id);
					buildingPort.setBuildingType(buildingType);
					buildingPort.setStatus(status);
					buildingPort.setAnchorIndex(anchorIndex);					
					if (building.get("property") != null) {
						JSONObject property = (JSONObject) building.get("property");
						buildingPort.setAvailableFleet((property.get("available_fleet") != null) ? property.getLong("available_fleet") : null);
						buildingPort.setFleetQuota((property.get("fleet_quota") != null) ? property.getLong("fleet_quota") : null);
					}					
										
					towns.setBuildingPort(buildingPort);
				}
				
				if (buildingType.equals(9L)) {
					BuildingMarket buildingMarket = new BuildingMarket();
					buildingMarket.setLevel(level);						
					buildingMarket.setId(id);
					buildingMarket.setBuildingType(buildingType);
					buildingMarket.setStatus(status);
					buildingMarket.setAnchorIndex(anchorIndex);
					if (building.get("property") != null) {
						JSONObject property = (JSONObject) building.get("property");
						buildingMarket.setLeftCapacity((property.get("left_capacity") != null) ? property.getLong("left_capacity") : null);
						buildingMarket.setCapacity((property.get("capacity") != null) ? property.getLong("capacity") : null);
					}
					
					towns.setBuildingMarket(buildingMarket);
				}
				
				if (buildingType.equals(10L)) {
					Building buildingHall = new Building();
					buildingHall.setLevel(level);						
					buildingHall.setId(id);
					buildingHall.setBuildingType(buildingType);
					buildingHall.setStatus(status);
					buildingHall.setAnchorIndex(anchorIndex);
					towns.setBuildingHall(buildingHall);
				}
				
				if (buildingType.equals(11L)) {
					BuildingWall buildingWall = new BuildingWall();
					buildingWall.setLevel(level);						
					buildingWall.setId(id);
					buildingWall.setBuildingType(buildingType);
					buildingWall.setStatus(status);
					buildingWall.setAnchorIndex(anchorIndex);
					if (building.get("property") != null) {
						JSONObject property = (JSONObject) building.get("property");
						buildingWall.setDefense((property.get("defense") != null) ? property.getLong("defense") : null);						
					}
					
					if (building.get("towers") != null) {
						JSONArray towers = building.getJSONArray("towers");
						List<BuildingTower> buildingTowers = new Vector<BuildingTower>();						
						for (int j = 0; j < towers.size(); j++) {
							JSONObject tower = (JSONObject) towers.get(j);
							BuildingTower buildingTower = new BuildingTower();
							buildingTower.setDefense((tower.get("defense") != null) ? tower.getLong("defense") : null);
							buildingTower.setLevel((tower.get("level") != null) ? tower.getLong("level") : null);
							buildingTower.setId((tower.get("id") != null) ? tower.getLong("id") : null);
							buildingTower.setType((tower.get("type") != null) ? tower.getLong("type") : null);
							buildingTower.setStatus((tower.get("status") != null) ? tower.getString("status") : null);
							buildingTower.setAttack((tower.get("attack") != null) ? tower.getLong("attack") : null);
							buildingTowers.add(buildingTower);
						}
						
						buildingWall.setBuildingTower(buildingTowers);
					}
					
					towns.setBuildingWall(buildingWall);
				}
				
				if (buildingType.equals(13L)) {
					BuildingSoldier buildingYard = new BuildingSoldier();
					buildingYard.setLevel(level);						
					buildingYard.setId(id);
					buildingYard.setBuildingType(buildingType);
					buildingYard.setStatus(status);
					buildingYard.setAnchorIndex(anchorIndex);					
					if (building.get("property") != null) {
						JSONObject property = (JSONObject) building.get("property");
						buildingYard.setReduceTimeRate((property.get("reduce_time_rate") != null) ? property.getLong("reduce_time_rate") : null);
					}
										
					towns.setBuildingYard(buildingYard);
				}
				
				if (buildingType.equals(14L)) {
					BuildingCellar buildingCellar = new BuildingCellar();
					buildingCellar.setLevel(level);						
					buildingCellar.setId(id);
					buildingCellar.setBuildingType(buildingType);
					buildingCellar.setStatus(status);
					buildingCellar.setAnchorIndex(anchorIndex);					
					if (building.get("property") != null) {
						JSONObject property = (JSONObject) building.get("property");
						buildingCellar.setSafeCapacity((property.get("safe_capacity") != null) ? property.getLong("reduce_time_rate") : null);
					}
										
					towns.setBuildingCellar(buildingCellar);
				}
			}
		}
		
		towns.setName((town.get("name") != null) ? town.getString("name") : null);
		if (town.get("alliance") != null) {
			JSONObject obj = (JSONObject) town.get("alliance");
			Alliance alliance = new Alliance();
			alliance.setAllianceId((obj.get("alliance_id") != null) ? obj.getLong("alliance_id") : null);
			alliance.setLevel((obj.get("level") != null) ? obj.getLong("level") : null);
			alliance.setMembershipId((obj.get("membership_id") != null) ? obj.getLong("membership_id") : null);
			alliance.setAllianceName((obj.get("alliance_name") != null) ? obj.getString("alliance_name") : null);
			towns.setAlliance(alliance);
		}
		
		towns.setResourceType((town.get("resource_type") != null) ? town.getLong("resource_type") : null);		
		if (town.get("battle_queue") != null) {
			JSONArray battleQueues = town.getJSONArray("battle_queue");
			List<BattleQueue> list = new Vector<BattleQueue>();
			for (int i = 0; i < battleQueues.size(); i++) {
				JSONObject battleQueue = (JSONObject) battleQueues.get(i);
				BattleQueue queue = new BattleQueue();
				queue.setToX((battleQueue.get("to_x") != null) ? battleQueue.getLong("to_x") : null);
				queue.setToLevel((battleQueue.get("to_level") != null) ? battleQueue.getLong("to_level") : null);
				
				if (battleQueue.get("army") != null) {
					JSONObject army = (JSONObject) battleQueue.get("army");
					queue.setFrigate((army.get("frigate") != null) ? army.getLong("frigate") : null);
					queue.setMusketman((army.get("musketman") != null) ? army.getLong("musketman") : null);
					queue.setCatapult((army.get("catapult") != null) ? army.getLong("catapult") : null);
					queue.setInfantry((army.get("infantry") != null) ? army.getLong("infantry") : null);
					queue.setDestroyer((army.get("destroyer") != null) ? army.getLong("destroyer") : null);
					queue.setScout((army.get("scout") != null) ? army.getLong("scout") : null);					
				}
				
				queue.setArriveTime((battleQueue.get("arrive_time") != null) ? DateTime.getTime(battleQueue.getLong("arrive_time")) : null);
				queue.setTotalTime((battleQueue.get("total_time") != null) ? battleQueue.getLong("total_time") : null);
				queue.setActionOwnerTownId((battleQueue.get("action_owner_town_id") != null) ? battleQueue.getLong("action_owner_town_id") : null);
				queue.setToY((battleQueue.get("to_y") != null) ? battleQueue.getLong("to_y") : null);
				queue.setHeroId((battleQueue.get("hero_id") != null) ? battleQueue.getLong("hero_id") : null);
				queue.setFromLevel((battleQueue.get("from_level") != null) ? battleQueue.getLong("from_level") : null);
				queue.setId((battleQueue.get("id") != null) ? battleQueue.getLong("id") : null);
				
				if (battleQueue.get("resources") != null) {
					JSONObject resources = (JSONObject) battleQueue.get("resources");
					queue.setGold((resources.get("gold") != null) ? resources.getLong("gold") : null);
					queue.setWood((resources.get("wood") != null) ? resources.getLong("wood") : null);
					queue.setMarble((resources.get("marble") != null) ? resources.getLong("marble") : null);
					queue.setFood((resources.get("food") != null) ? resources.getLong("food") : null);
					queue.setIron((resources.get("iron") != null) ? resources.getLong("iron") : null);										
				}
				
				queue.setFromX((battleQueue.get("from_x") != null) ? battleQueue.getLong("from_x") : null);
				queue.setMission((battleQueue.get("mission") != null) ? battleQueue.getLong("mission") : null);
				queue.setFromTownName((battleQueue.get("from_town_name") != null) ? battleQueue.getString("from_town_name") : null);
				queue.setFromY((battleQueue.get("from_y") != null) ? battleQueue.getLong("from_y") : null);
				queue.setType((battleQueue.get("type") != null) ? battleQueue.getString("type") : null);
				queue.setReceive((battleQueue.get("receive") != null && !battleQueue.getString("receive").equals("null")) ? battleQueue.getString("receive") : null);
				queue.setBattleInfoLevel((battleQueue.get("battle_info_level") != null) ? battleQueue.getLong("battle_info_level") : null);
				queue.setToTownId((battleQueue.get("to_town_id") != null) ? battleQueue.getLong("to_town_id") : null);
				queue.setGems((battleQueue.get("gems") != null) ? battleQueue.getLong("gems") : null);
				queue.setFinished((battleQueue.get("finished") != null) ? battleQueue.getBoolean("finished") : null);
				queue.setToTownName((battleQueue.get("to_town_name") != null) ? battleQueue.getString("to_town_name") : null);
				queue.setFromTownId((battleQueue.get("from_town_id") != null) ? battleQueue.getLong("from_town_id") : null);				
				list.add(queue);
			}	
			
			towns.setBattleQueues(list);
		}
		
		towns.setShowWelcome((town.get("show_welcome") != null) ? town.getBoolean("show_welcome") : null);
		towns.setCityArmyPower((town.get("city_army_power") != null) ? town.getDouble("city_army_power") : null);
		towns.setLevel((town.get("level") != null) ? town.getLong("level") : null);
		towns.setPresenceDefenders((town.get("presence_defenders") != null) ? town.getLong("presence_defenders") : null);
		towns.setAccountType((town.get("account_type") != null) ? town.getLong("account_type") : null);
		towns.setCurrentServerTime((town.get("current_server_time") != null) ? DateTime.getTime(town.getLong("current_server_time")) : null);
		
		if (town.get("resources") != null) {
			JSONArray resources = town.getJSONArray("resources");
			for (int i = 0; i < resources.size(); i++) {
				JSONObject resource = (JSONObject) resources.get(i);				
				Resources res = new Resources();
				res.setMaxVolume((resource.get("max_volume") != null) ? resource.getLong("max_volume") : null);
				res.setIncreasePerHour((resource.get("increase_per_hour") != null) ? resource.getLong("increase_per_hour") : null);
				res.setResourceName((resource.get("resource_name") != null) ? resource.getString("resource_name") : null);
				res.setResourceCount((resource.get("resource_count") != null) ? resource.getLong("resource_count") : null);
				
				if (res.getResourceName() == null)
					continue;
				
				if (res.getResourceName().equals("wood")) {
					towns.setResourcesWood(res);
					continue;
				}
				
				if (res.getResourceName().equals("food")) {
					towns.setResourcesFood(res);
					continue;
				}
				
				if (res.getResourceName().equals("iron")) {
					towns.setResourcesIron(res);
					continue;
				}
				
				if (res.getResourceName().equals("gold")) {
					towns.setResourcesGold(res);
					continue;
				}
				
				if (res.getResourceName().equals("marble")) {
					towns.setResourcesMarble(res);
					continue;
				}
			}			
		}
		
		towns.setOwnerId((town.get("owner_id") != null) ? town.getLong("owner_id") : null);
		towns.setIslandX((town.get("island_x") != null) ? town.getLong("island_x") : null);
		towns.setId((town.get("id") != null) ? town.getLong("id") : null);
		towns.setNewReward((town.get("new_reward") != null) ? town.getBoolean("new_reward") : null);
		towns.setPresenceScout((town.get("presence_scout") != null) ? town.getLong("presence_scout") : null);
		towns.setCityStatus((town.get("city_status") != null) ? town.getString("city_status") : null);
		towns.setIslandY((town.get("island_y") != null) ? town.getLong("island_y") : null);
		towns.setIslandZ((town.get("island_z") != null) ? town.getLong("island_z") : null);
		
		if (town.get("transport_queue") != null) {
			JSONArray transportQueues = town.getJSONArray("transport_queue");
			List<TransportQueue> list = new Vector<TransportQueue>();
			for (int i = 0; i < transportQueues.size(); i++) {
				JSONObject transportQueue = (JSONObject) transportQueues.get(i);
				TransportQueue queue = new TransportQueue();				
				if (transportQueue.get("army") != null) {
					JSONObject army = (JSONObject) transportQueue.get("army");
					queue.setFrigate((army.get("frigate") != null) ? army.getLong("frigate") : null);
					queue.setMusketman((army.get("musketman") != null) ? army.getLong("musketman") : null);
					queue.setCatapult((army.get("catapult") != null) ? army.getLong("catapult") : null);
					queue.setInfantry((army.get("infantry") != null) ? army.getLong("infantry") : null);
					queue.setDestroyer((army.get("destroyer") != null) ? army.getLong("destroyer") : null);
					queue.setScout((army.get("scout") != null) ? army.getLong("scout") : null);					
				}
				
				queue.setTotalTime((transportQueue.get("total_time") != null) ? transportQueue.getLong("total_time") : null);
				queue.setActionOwnerTownId((transportQueue.get("action_owner_town_id") != null) ? transportQueue.getLong("action_owner_town_id") : null);
				queue.setFleetTownId((transportQueue.get("fleet_town_id") != null) ? transportQueue.getLong("fleet_town_id") : null);				
				queue.setHeroId((transportQueue.get("hero_id") != null && !transportQueue.getString("hero_id").equals("null")) ? transportQueue.getLong("hero_id") : null);
				queue.setFleetIndex((transportQueue.get("fleet_index") != null) ? transportQueue.getLong("fleet_index") : null);
				
				if (transportQueue.get("resources") != null) {
					JSONObject resources = (JSONObject) transportQueue.get("resources");
					queue.setGold((resources.get("gold") != null) ? resources.getLong("gold") : null);
					queue.setWood((resources.get("wood") != null) ? resources.getLong("wood") : null);
					queue.setMarble((resources.get("marble") != null) ? resources.getLong("marble") : null);
					queue.setFood((resources.get("food") != null) ? resources.getLong("food") : null);
					queue.setIron((resources.get("iron") != null) ? resources.getLong("iron") : null);										
				}
				
				queue.setMission((transportQueue.get("mission") != null) ? transportQueue.getLong("mission") : null);
				queue.setType((transportQueue.get("type") != null) ? transportQueue.getString("type") : null);
				queue.setReceive((transportQueue.get("receive") != null && !transportQueue.getString("receive").equals("null")) ? transportQueue.getString("receive") : null);
				queue.setFinishTime((transportQueue.get("finish_time") != null) ? DateTime.getTime(transportQueue.getLong("finish_time")) : null);
				queue.setBattleInfoLevel((transportQueue.get("battle_info_level") != null) ? transportQueue.getLong("battle_info_level") : null);
				queue.setToTownId((transportQueue.get("to_town_id") != null) ? transportQueue.getLong("to_town_id") : null);
				queue.setGems((transportQueue.get("gems") != null) ? transportQueue.getLong("gems") : null);
				queue.setFinished((transportQueue.get("finished") != null) ? transportQueue.getBoolean("finished") : null);
				queue.setToTownName((transportQueue.get("to_town_name") != null) ? transportQueue.getString("to_town_name") : null);
				queue.setQueueId((transportQueue.get("queue_id") != null) ? transportQueue.getLong("queue_id") : null);
				queue.setFromTownId((transportQueue.get("from_town_id") != null) ? transportQueue.getLong("from_town_id") : null);				
				list.add(queue);
			}	
			
			towns.setTransportQueues(list);
		}		
		
		towns.setGems((town.get("gems") != null) ? town.getLong("gems") : null);
		
		if (town.get("soldiers") != null) {
			JSONArray soldiers = town.getJSONArray("soldiers");
			for (int i = 0; i < soldiers.size(); i++) {
				JSONObject soldier = (JSONObject) soldiers.get(i);								
				Soldier sold = new Soldier();
				sold.setName((soldier.get("name") != null) ? soldier.getString("name") : null);
				sold.setCount((soldier.get("count") != null) ? soldier.getLong("count") : null);
				
				if (sold.getName() == null)
					continue;
				
				if (sold.getName().equals("infantry")) {
					towns.setSoldierInfantry(sold);
					continue;
				}
				
				if (sold.getName().equals("musketman")) {
					towns.setSoldierMusketman(sold);
					continue;
				}
				
				if (sold.getName().equals("catapult")) {
					towns.setSoldierCatapult(sold);
					continue;
				}
				
				if (sold.getName().equals("scout")) {
					towns.setSoldierScout(sold);
					continue;
				}
				
				if (sold.getName().equals("frigate")) {
					towns.setSoldierFrigate(sold);
					continue;
				}
				
				if (sold.getName().equals("destroyer")) {
					towns.setSoldierDestroyer(sold);
					continue;
				}
			}
		}
		
		if (town.get("building_queue") != null) {
			JSONArray buildingQueues = town.getJSONArray("building_queue");
			List<BuildingQueue> list = new Vector<BuildingQueue>();
			for (int i = 0; i < buildingQueues.size(); i++) {
				JSONObject buildingQueue = (JSONObject) buildingQueues.get(i);
				BuildingQueue queue = new BuildingQueue();
				queue.setTotalTime((buildingQueue.get("total_time") != null) ? buildingQueue.getLong("total_time") : null);
				queue.setFinishTime((buildingQueue.get("finish_time") != null) ? DateTime.getTime(buildingQueue.getLong("finish_time")) : null);
				queue.setBuildingId((buildingQueue.get("building_id") != null) ? buildingQueue.getLong("building_id") : null);
				queue.setQueueId((buildingQueue.get("queue_id") != null) ? buildingQueue.getLong("queue_id") : null);
				list.add(queue);
			}
			
			towns.setBuildingQueues(list);
		}
		
		towns.setHaveNewMessage((town.get("have_new_message") != null) ? town.getBoolean("have_new_message") : null);
		
		if (town.get("other_towns") != null) {
			JSONArray otherTowns = town.getJSONArray("other_towns");
			List<OtherTown> list = new Vector<OtherTown>();
			for (int i = 0; i < otherTowns.size(); i++) {
				JSONObject otherTown = (JSONObject) otherTowns.get(i);
				OtherTown other = new OtherTown();
				other.setName((otherTown.get("name") != null) ? otherTown.getString("name") : null);
				other.setResourceType((otherTown.get("resource_type") != null) ? otherTown.getLong("resource_type") : null);				
				if (otherTown.get("battle_queue") != null) {
					JSONArray battleQueues = otherTown.getJSONArray("battle_queue");
					List<BattleQueue> queues = new Vector<BattleQueue>();
					for (int j = 0; j < battleQueues.size(); j++) {
						JSONObject battleQueue = (JSONObject) battleQueues.get(j);
						BattleQueue queue = new BattleQueue();
						queue.setToX((battleQueue.get("to_x") != null) ? battleQueue.getLong("to_x") : null);
						queue.setToLevel((battleQueue.get("to_level") != null) ? battleQueue.getLong("to_level") : null);
						
						if (battleQueue.get("army") != null) {
							JSONObject army = (JSONObject) battleQueue.get("army");
							queue.setFrigate((army.get("frigate") != null) ? army.getLong("frigate") : null);
							queue.setMusketman((army.get("musketman") != null) ? army.getLong("musketman") : null);
							queue.setCatapult((army.get("catapult") != null) ? army.getLong("catapult") : null);
							queue.setInfantry((army.get("infantry") != null) ? army.getLong("infantry") : null);
							queue.setDestroyer((army.get("destroyer") != null) ? army.getLong("destroyer") : null);
							queue.setScout((army.get("scout") != null) ? army.getLong("scout") : null);					
						}
						
						queue.setArriveTime((battleQueue.get("arrive_time") != null) ? DateTime.getTime(battleQueue.getLong("arrive_time")) : null);
						queue.setTotalTime((battleQueue.get("total_time") != null) ? battleQueue.getLong("total_time") : null);
						queue.setActionOwnerTownId((battleQueue.get("action_owner_town_id") != null) ? battleQueue.getLong("action_owner_town_id") : null);
						queue.setToY((battleQueue.get("to_y") != null) ? battleQueue.getLong("to_y") : null);
						queue.setHeroId((battleQueue.get("hero_id") != null) ? battleQueue.getLong("hero_id") : null);
						queue.setFromLevel((battleQueue.get("from_level") != null) ? battleQueue.getLong("from_level") : null);
						queue.setId((battleQueue.get("id") != null) ? battleQueue.getLong("id") : null);
						
						if (battleQueue.get("resources") != null) {
							JSONObject resources = (JSONObject) battleQueue.get("resources");
							queue.setGold((resources.get("gold") != null) ? resources.getLong("gold") : null);
							queue.setWood((resources.get("wood") != null) ? resources.getLong("wood") : null);
							queue.setMarble((resources.get("marble") != null) ? resources.getLong("marble") : null);
							queue.setFood((resources.get("food") != null) ? resources.getLong("food") : null);
							queue.setIron((resources.get("iron") != null) ? resources.getLong("iron") : null);										
						}
						
						queue.setFromX((battleQueue.get("from_x") != null) ? battleQueue.getLong("from_x") : null);
						queue.setMission((battleQueue.get("mission") != null) ? battleQueue.getLong("mission") : null);
						queue.setFromTownName((battleQueue.get("from_town_name") != null) ? battleQueue.getString("from_town_name") : null);
						queue.setFromY((battleQueue.get("from_y") != null) ? battleQueue.getLong("from_y") : null);
						queue.setType((battleQueue.get("type") != null) ? battleQueue.getString("type") : null);
						queue.setReceive((battleQueue.get("receive") != null && !battleQueue.getString("receive").equals("null")) ? battleQueue.getString("receive") : null);
						queue.setBattleInfoLevel((battleQueue.get("battle_info_level") != null) ? battleQueue.getLong("battle_info_level") : null);
						queue.setToTownId((battleQueue.get("to_town_id") != null) ? battleQueue.getLong("to_town_id") : null);
						queue.setGems((battleQueue.get("gems") != null) ? battleQueue.getLong("gems") : null);
						queue.setFinished((battleQueue.get("finished") != null) ? battleQueue.getBoolean("finished") : null);
						queue.setToTownName((battleQueue.get("to_town_name") != null) ? battleQueue.getString("to_town_name") : null);
						queue.setFromTownId((battleQueue.get("from_town_id") != null) ? battleQueue.getLong("from_town_id") : null);				
						queues.add(queue);
					}	
					
					other.setBattleQueues(queues);
				}
				
				other.setLevel((otherTown.get("level") != null) ? otherTown.getLong("level") : null);
				other.setId((otherTown.get("id") != null) ? otherTown.getLong("id") : null);
				other.setIslandX((otherTown.get("island_x") != null) ? otherTown.getLong("island_x") : null);
				other.setIslandY((otherTown.get("island_y") != null) ? otherTown.getLong("island_y") : null);
				other.setIslandZ((otherTown.get("island_z") != null) ? otherTown.getLong("island_z") : null);
				other.setIslandNumber((otherTown.get("island_number") != null) ? otherTown.getLong("island_number") : null);
				other.setIsCapital((otherTown.get("is_capital") != null) ? otherTown.getBoolean("is_capital") : null);
				list.add(other);
			}
			
			towns.setOtherTowns(list);
		}
		
		towns.setOwner((town.get("owner") != null) ? town.getString("owner") : null);
		towns.setIsCapital((town.get("is_capital") != null) ? town.getBoolean("is_capital") : null);
		towns.setIslandNumber((town.get("island_number") != null) ? town.getLong("island_number") : null);
		towns.setTotalFoodCost((town.get("total_food_cost") != null) ? town.getLong("total_food_cost") : null);		
		return towns;
	}
}
