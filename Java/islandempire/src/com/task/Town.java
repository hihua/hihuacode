package com.task;

import java.util.Date;
import java.util.List;

import com.buildings.Building;
import com.buildings.BuildingCellar;
import com.buildings.BuildingCommand;
import com.buildings.BuildingMarket;
import com.buildings.BuildingPort;
import com.buildings.BuildingResource;
import com.buildings.BuildingSoldier;
import com.buildings.BuildingStore;
import com.buildings.BuildingWall;
import com.hero.Hero;
import com.queue.BattleQueue;
import com.queue.BuildingQueue;
import com.queue.TransportQueue;
import com.soldier.Soldier;
import com.towns.Alliance;
import com.towns.OtherTown;
import com.towns.Resources;

public class Town {
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
	private BuildingCommand buildingCommand;
	private String name;
	private Alliance alliance;
	private Boolean showWelcome;
	private Long resourceType;
	private List<BattleQueue> battleQueues;
	private boolean haveNewMedal;
	private Long medal;
	private Long population;
	private Long populationMax;
	private Long sands;
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
	private Long newReward;
	private Long ownerId;
	private Long islandX;
	private Long id;
	private Long presenceScout;
	private String cityStatus;
	private Long islandY;
	private Long islandZ;
	private String mdStatus;
	private String moveDestroyStatus;
	private List<TransportQueue> transportQueues;
	private Long gems;
	private Soldier soldierInfantry;
	private Soldier soldierScout;
	private Soldier soldierMusketman;
	private Soldier soldierCatapult;
	private Soldier soldierFrigate;
	private Soldier soldierDestroyer;
	private Soldier soldierPegasus;
	private Soldier soldierBerserker;
	private Soldier soldierIronclad;
	private List<BuildingQueue> buildingQueues;
	private Boolean haveNewMessage;
	private List<OtherTown> otherTowns;
	private Long score;
	private Hero hero;
	private String owner;
	private Boolean isCapital;
	private Long islandNumber;

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

	public BuildingCommand getBuildingCommand() {
		return buildingCommand;
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

	public boolean isHaveNewMedal() {
		return haveNewMedal;
	}

	public Long getMedal() {
		return medal;
	}

	public Long getPopulation() {
		return population;
	}

	public Long getPopulationMax() {
		return populationMax;
	}

	public Long getSands() {
		return sands;
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

	public Long getNewReward() {
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

	public String getMdStatus() {
		return mdStatus;
	}

	public String getMoveDestroyStatus() {
		return moveDestroyStatus;
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

	public Soldier getSoldierPegasus() {
		return soldierPegasus;
	}

	public Soldier getSoldierBerserker() {
		return soldierBerserker;
	}

	public Soldier getSoldierIronclad() {
		return soldierIronclad;
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

	public Long getScore() {
		return score;
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

	public void setBuildingCommand(BuildingCommand buildingCommand) {
		this.buildingCommand = buildingCommand;
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

	public void setHaveNewMedal(boolean haveNewMedal) {
		this.haveNewMedal = haveNewMedal;
	}

	public void setMedal(Long medal) {
		this.medal = medal;
	}

	public void setPopulation(Long population) {
		this.population = population;
	}

	public void setPopulationMax(Long populationMax) {
		this.populationMax = populationMax;
	}

	public void setSands(Long sands) {
		this.sands = sands;
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

	public void setNewReward(Long newReward) {
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

	public void setMdStatus(String mdStatus) {
		this.mdStatus = mdStatus;
	}

	public void setMoveDestroyStatus(String moveDestroyStatus) {
		this.moveDestroyStatus = moveDestroyStatus;
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

	public void setSoldierPegasus(Soldier soldierPegasus) {
		this.soldierPegasus = soldierPegasus;
	}

	public void setSoldierBerserker(Soldier soldierBerserker) {
		this.soldierBerserker = soldierBerserker;
	}

	public void setSoldierIronclad(Soldier soldierIronclad) {
		this.soldierIronclad = soldierIronclad;
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

	public void setScore(Long score) {
		this.score = score;
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
}
