package com.buildings;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.queue.BattleQueue;
import com.queue.BuildingQueue;
import com.queue.TransportQueue;

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
	private BuildingCellar buildingCellar;
	private String name;
	private Alliance alliance;
	private Boolean showWelcome;
	private Long resourceType;
	private BattleQueue battleQueue;
	private Double cityArmyPower;
	private Long level;
	private Long presenceDefenders;
	private Date currentServerTime;
	private Long accountType;
	private Resources resources;
	private Boolean newReward;
	private Long ownerId;
	private Long islandX;
	private Long id;
	private Long presenceScout;
	private String cityStatus;
	private Long islandY;
	private Long islandZ;
	private TransportQueue transportQueue;
	private Long gems;
	private List<Soldier> soldiers;
	private List<BuildingQueue> buildingQueues;
	private Boolean haveNewMessage;
	private List<OtherTown> otherTowns;
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

	public BattleQueue getBattleQueue() {
		return battleQueue;
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

	public Resources getResources() {
		return resources;
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

	public TransportQueue getTransportQueue() {
		return transportQueue;
	}

	public Long getGems() {
		return gems;
	}

	public List<Soldier> getSoldiers() {
		return soldiers;
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

	public void setBattleQueue(BattleQueue battleQueue) {
		this.battleQueue = battleQueue;
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

	public void setResources(Resources resources) {
		this.resources = resources;
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

	public void setTransportQueue(TransportQueue transportQueue) {
		this.transportQueue = transportQueue;
	}

	public void setGems(Long gems) {
		this.gems = gems;
	}

	public void setSoldiers(List<Soldier> soldiers) {
		this.soldiers = soldiers;
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

				if (2 <= buildingType && buildingType >= 6) {
					JSONArray lines = building.getJSONArray("lines");
					if (lines != null) {
						List<BuildingLine> buildingLines = new Vector<BuildingLine>();
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
				}
			}
		}

		return towns;
	}
}
