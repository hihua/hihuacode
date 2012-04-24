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
import com.queue.LinesEvent;
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
		if (json == null || json.get("town") == null)
			return null;

		Town towns = new Town();
		JSONObject town = (JSONObject) json.get("town");
		towns.setCityArmyStatus((town.get("city_army_status") != null) ? town.getString("city_army_status") : null);
		JSONArray arrays = town.getJSONArray("buildings");
		if (arrays != null) {
			for (int i = 0; i < arrays.size(); i++) {
				JSONObject array = (JSONObject) arrays.get(i);
				Long level = (array.get("level") != null) ? array.getLong("level") : null;
				Long id = (array.get("id") != null) ? array.getLong("id")	: null;
				Long buildingType = (array.get("building_type") != null) ? array.getLong("building_type") : null;
				String status = (array.get("status") != null) ? array.getString("status") : null;
				Long anchorIndex = (array.get("anchor_index") != null) ? array.getLong("anchor_index") : null;

				if (buildingType == null)
					continue;
				
				if (buildingType.equals(1L)) {
					BuildingSoldier buildingBarrack = new BuildingSoldier();
					buildingBarrack.setLevel(level);						
					buildingBarrack.setId(id);
					buildingBarrack.setBuildingType(buildingType);
					buildingBarrack.setStatus(status);
					buildingBarrack.setAnchorIndex(anchorIndex);					
					if (array.get("property") != null) {
						JSONObject property = (JSONObject) array.get("property");
						buildingBarrack.setReduceTimeRate((property.get("reduce_time_rate") != null) ? property.getLong("reduce_time_rate") : null);
					}
										
					towns.setBuildingBarrack(buildingBarrack);
				}
								
				if (buildingType.equals(2L) || buildingType.equals(5L) || buildingType.equals(6L) || buildingType.equals(7L) || buildingType.equals(8L)) {
					JSONArray lines = array.getJSONArray("lines");
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
					if (array.get("property") != null) {
						JSONObject property = (JSONObject) array.get("property");
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
					if (array.get("property") != null) {
						JSONObject property = (JSONObject) array.get("property");
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
					if (array.get("property") != null) {
						JSONObject property = (JSONObject) array.get("property");
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
					if (array.get("property") != null) {
						JSONObject property = (JSONObject) array.get("property");
						buildingWall.setDefense((property.get("defense") != null) ? property.getLong("defense") : null);						
					}
					
					if (array.get("towers") != null) {
						JSONArray towers = array.getJSONArray("towers");
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
					if (array.get("property") != null) {
						JSONObject property = (JSONObject) array.get("property");
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
					if (array.get("property") != null) {
						JSONObject property = (JSONObject) array.get("property");
						buildingCellar.setSafeCapacity((property.get("reduce_time_rate") != null) ? property.getLong("reduce_time_rate") : null);
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
			arrays = town.getJSONArray("battle_queue");
			List<BattleQueue> list = new Vector<BattleQueue>();
			for (int i = 0; i < arrays.size(); i++) {
				JSONObject array = (JSONObject) arrays.get(i);
				BattleQueue battleQueue = new BattleQueue();
				battleQueue.setToX((array.get("to_x") != null) ? array.getLong("to_x") : null);
				battleQueue.setToLevel((array.get("to_level") != null) ? array.getLong("to_level") : null);
				
				if (array.get("army") != null) {
					JSONObject army = (JSONObject) array.get("army");
					battleQueue.setFrigate((army.get("frigate") != null) ? army.getLong("frigate") : null);
					battleQueue.setMusketman((army.get("musketman") != null) ? army.getLong("musketman") : null);
					battleQueue.setCatapult((army.get("catapult") != null) ? army.getLong("catapult") : null);
					battleQueue.setInfantry((army.get("infantry") != null) ? army.getLong("infantry") : null);
					battleQueue.setDestroyer((army.get("destroyer") != null) ? army.getLong("destroyer") : null);
					battleQueue.setScout((army.get("scout") != null) ? army.getLong("scout") : null);					
				}
				
				battleQueue.setArriveTime((array.get("arrive_time") != null) ? DateTime.getTime(array.getLong("arrive_time")) : null);
				battleQueue.setTotalTime((array.get("total_time") != null) ? array.getLong("total_time") : null);
				battleQueue.setActionOwnerTownId((array.get("action_owner_town_id") != null) ? array.getLong("action_owner_town_id") : null);
				battleQueue.setToY((array.get("to_y") != null) ? array.getLong("to_y") : null);
				battleQueue.setHeroId((array.get("hero_id") != null) ? array.getLong("hero_id") : null);
				battleQueue.setFromLevel((array.get("from_level") != null) ? array.getLong("from_level") : null);
				battleQueue.setId((array.get("id") != null) ? array.getLong("id") : null);
				
				if (array.get("resources") != null) {
					JSONObject resources = (JSONObject) array.get("resources");
					battleQueue.setGold((resources.get("gold") != null) ? resources.getLong("gold") : null);
					battleQueue.setWood((resources.get("wood") != null) ? resources.getLong("wood") : null);
					battleQueue.setMarble((resources.get("marble") != null) ? resources.getLong("marble") : null);
					battleQueue.setFood((resources.get("food") != null) ? resources.getLong("food") : null);
					battleQueue.setIron((resources.get("iron") != null) ? resources.getLong("iron") : null);										
				}
				
				battleQueue.setFromX((array.get("from_x") != null) ? array.getLong("from_x") : null);
				battleQueue.setMission((array.get("mission") != null) ? array.getLong("mission") : null);
				battleQueue.setFromTownName((array.get("from_town_name") != null) ? array.getString("from_town_name") : null);
				battleQueue.setFromY((array.get("from_y") != null) ? array.getLong("from_y") : null);
				battleQueue.setType((array.get("type") != null) ? array.getString("type") : null);
				battleQueue.setReceive((array.get("receive") != null && !array.getString("receive").equals("null")) ? array.getString("receive") : null);
				battleQueue.setBattleInfoLevel((array.get("battle_info_level") != null) ? array.getLong("battle_info_level") : null);
				battleQueue.setToTownId((array.get("to_town_id") != null) ? array.getLong("to_town_id") : null);
				battleQueue.setGems((array.get("gems") != null) ? array.getLong("gems") : null);
				battleQueue.setFinished((array.get("finished") != null) ? array.getBoolean("finished") : null);
				battleQueue.setToTownName((array.get("to_town_name") != null) ? array.getString("to_town_name") : null);
				battleQueue.setFromTownId((array.get("from_town_id") != null) ? array.getLong("from_town_id") : null);				
				list.add(battleQueue);
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
			arrays = town.getJSONArray("resources");
			for (int i = 0; i < arrays.size(); i++) {
				JSONObject array = (JSONObject) arrays.get(i);				
				Resources resources = new Resources();
				resources.setMaxVolume((array.get("max_volume") != null) ? array.getLong("max_volume") : null);
				resources.setIncreasePerHour((array.get("increase_per_hour") != null) ? array.getLong("increase_per_hour") : null);
				resources.setResourceName((array.get("resource_name") != null) ? array.getString("resource_name") : null);
				resources.setResourceCount((array.get("resource_count") != null) ? array.getLong("resource_count") : null);
				
				if (resources.getResourceName() == null)
					continue;
				
				if (resources.getResourceName().equals("wood")) {
					towns.setResourcesWood(resources);
					continue;
				}
				
				if (resources.getResourceName().equals("food")) {
					towns.setResourcesFood(resources);
					continue;
				}
				
				if (resources.getResourceName().equals("iron")) {
					towns.setResourcesIron(resources);
					continue;
				}
				
				if (resources.getResourceName().equals("gold")) {
					towns.setResourcesGold(resources);
					continue;
				}
				
				if (resources.getResourceName().equals("marble")) {
					towns.setResourcesMarble(resources);
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
			arrays = town.getJSONArray("transport_queue");
			List<TransportQueue> list = new Vector<TransportQueue>();
			for (int i = 0; i < arrays.size(); i++) {
				JSONObject array = (JSONObject) arrays.get(i);
				TransportQueue transportQueue = new TransportQueue();				
				if (array.get("army") != null) {
					JSONObject army = (JSONObject) array.get("army");
					transportQueue.setFrigate((army.get("frigate") != null) ? army.getLong("frigate") : null);
					transportQueue.setMusketman((army.get("musketman") != null) ? army.getLong("musketman") : null);
					transportQueue.setCatapult((army.get("catapult") != null) ? army.getLong("catapult") : null);
					transportQueue.setInfantry((army.get("infantry") != null) ? army.getLong("infantry") : null);
					transportQueue.setDestroyer((army.get("destroyer") != null) ? army.getLong("destroyer") : null);
					transportQueue.setScout((army.get("scout") != null) ? army.getLong("scout") : null);					
				}
				
				transportQueue.setTotalTime((array.get("total_time") != null) ? array.getLong("total_time") : null);
				transportQueue.setActionOwnerTownId((array.get("action_owner_town_id") != null) ? array.getLong("action_owner_town_id") : null);
				transportQueue.setFleetTownId((array.get("fleet_town_id") != null) ? array.getLong("fleet_town_id") : null);				
				transportQueue.setHeroId((array.get("hero_id") != null && !array.getString("hero_id").equals("null")) ? array.getLong("hero_id") : null);
				transportQueue.setFleetIndex((array.get("fleet_index") != null) ? array.getLong("fleet_index") : null);
				
				if (array.get("resources") != null) {
					JSONObject resources = (JSONObject) array.get("resources");
					transportQueue.setGold((resources.get("gold") != null) ? resources.getLong("gold") : null);
					transportQueue.setWood((resources.get("wood") != null) ? resources.getLong("wood") : null);
					transportQueue.setMarble((resources.get("marble") != null) ? resources.getLong("marble") : null);
					transportQueue.setFood((resources.get("food") != null) ? resources.getLong("food") : null);
					transportQueue.setIron((resources.get("iron") != null) ? resources.getLong("iron") : null);										
				}
				
				transportQueue.setMission((array.get("mission") != null) ? array.getLong("mission") : null);
				transportQueue.setType((array.get("type") != null) ? array.getString("type") : null);
				transportQueue.setReceive((array.get("receive") != null && !array.getString("receive").equals("null")) ? array.getString("receive") : null);
				transportQueue.setFinishTime((array.get("finish_time") != null) ? DateTime.getTime(array.getLong("finish_time")) : null);
				transportQueue.setBattleInfoLevel((array.get("battle_info_level") != null) ? array.getLong("battle_info_level") : null);
				transportQueue.setToTownId((array.get("to_town_id") != null) ? array.getLong("to_town_id") : null);
				transportQueue.setGems((array.get("gems") != null) ? array.getLong("gems") : null);
				transportQueue.setFinished((array.get("finished") != null) ? array.getBoolean("finished") : null);
				transportQueue.setToTownName((array.get("to_town_name") != null) ? array.getString("to_town_name") : null);
				transportQueue.setQueueId((array.get("queue_id") != null) ? array.getLong("queue_id") : null);
				transportQueue.setFromTownId((array.get("from_town_id") != null) ? array.getLong("from_town_id") : null);				
				list.add(transportQueue);
			}	
			
			towns.setTransportQueues(list);
		}		
		
		towns.setGems((town.get("gems") != null) ? town.getLong("gems") : null);
		
		if (town.get("soldiers") != null) {
			arrays = town.getJSONArray("soldiers");
			for (int i = 0; i < arrays.size(); i++) {
				JSONObject array = (JSONObject) arrays.get(i);								
				Soldier soldier = new Soldier();
				soldier.setName((array.get("name") != null) ? array.getString("name") : null);
				soldier.setCount((array.get("count") != null) ? array.getLong("count") : null);
				
				if (soldier.getName() == null)
					continue;
				
				if (soldier.getName().equals("infantry")) {
					towns.setSoldierInfantry(soldier);
					continue;
				}
				
				if (soldier.getName().equals("musketman")) {
					towns.setSoldierMusketman(soldier);
					continue;
				}
				
				if (soldier.getName().equals("catapult")) {
					towns.setSoldierCatapult(soldier);
					continue;
				}
				
				if (soldier.getName().equals("scout")) {
					towns.setSoldierScout(soldier);
					continue;
				}
				
				if (soldier.getName().equals("frigate")) {
					towns.setSoldierFrigate(soldier);
					continue;
				}
				
				if (soldier.getName().equals("destroyer")) {
					towns.setSoldierDestroyer(soldier);
					continue;
				}
			}
		}
		
		if (town.get("building_queue") != null) {
			arrays = town.getJSONArray("building_queue");
			List<BuildingQueue> list = new Vector<BuildingQueue>();
			for (int i = 0; i < arrays.size(); i++) {
				JSONObject array = (JSONObject) arrays.get(i);
				BuildingQueue buildingQueue = new BuildingQueue();
				buildingQueue.setTotalTime((array.get("total_time") != null) ? array.getLong("total_time") : null);
				buildingQueue.setNextLevel((array.get("next_level") != null) ? array.getLong("next_level") : null);
				buildingQueue.setFinishTime((array.get("finish_time") != null) ? DateTime.getTime(array.getLong("finish_time")) : null);
				buildingQueue.setBuildingId((array.get("building_id") != null) ? array.getLong("building_id") : null);
				buildingQueue.setQueueId((array.get("queue_id") != null) ? array.getLong("queue_id") : null);
				
				if (array.get("lines_events") != null) {
					JSONArray linesEvents = array.getJSONArray("lines_events");
					List<LinesEvent> lines = new Vector<LinesEvent>();
					for (int j = 0; j < linesEvents.size(); j++) {
						JSONObject linesEvent = (JSONObject) linesEvents.get(j);
						LinesEvent event = new LinesEvent();
						event.setTotalTime((linesEvent.get("total_time") != null) ? linesEvent.getLong("total_time") : null);
						event.setFinishTime((linesEvent.get("finish_time") != null) ? DateTime.getTime(linesEvent.getLong("finish_time")) : null);
						event.setBuildingId((linesEvent.get("building_id") != null) ? linesEvent.getLong("building_id") : null);
						event.setQueueId((linesEvent.get("queue_id") != null) ? linesEvent.getLong("queue_id") : null);
						lines.add(event);
					}
					
					buildingQueue.setLinesEvent(lines);
				}
					
				list.add(buildingQueue);
			}
			
			towns.setBuildingQueues(list);
		}
		
		towns.setHaveNewMessage((town.get("have_new_message") != null) ? town.getBoolean("have_new_message") : null);
		
		if (town.get("other_towns") != null) {
			arrays = town.getJSONArray("other_towns");
			List<OtherTown> list = new Vector<OtherTown>();
			for (int i = 0; i < arrays.size(); i++) {
				JSONObject array = (JSONObject) arrays.get(i);
				OtherTown other = new OtherTown();
				other.setName((array.get("name") != null) ? array.getString("name") : null);
				other.setResourceType((array.get("resource_type") != null) ? array.getLong("resource_type") : null);				
				if (array.get("battle_queue") != null) {
					JSONArray battleQueues = array.getJSONArray("battle_queue");
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
				
				other.setLevel((array.get("level") != null) ? array.getLong("level") : null);
				other.setId((array.get("id") != null) ? array.getLong("id") : null);
				other.setIslandX((array.get("island_x") != null) ? array.getLong("island_x") : null);
				other.setIslandY((array.get("island_y") != null) ? array.getLong("island_y") : null);
				other.setIslandZ((array.get("island_z") != null) ? array.getLong("island_z") : null);
				other.setIslandNumber((array.get("island_number") != null) ? array.getLong("island_number") : null);
				other.setIsCapital((array.get("is_capital") != null) ? array.getBoolean("is_capital") : null);
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
