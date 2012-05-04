package com.towns;

import java.util.List;
import java.util.Vector;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.buildings.Building;
import com.buildings.BuildingPort;
import com.queue.TrainingQueue;
import com.queue.TransportQueue;
import com.soldier.Soldier;
import com.util.DateTime;

public class TransportTown {
	private List<Building> buildings;
	private String name;
	private Long resourceType;
	private Long level;
	private Long islandX;
	private Long ownerId;
	private Resources resourcesWood;
	private Resources resourcesFood;
	private Resources resourcesGold;
	private Resources resourcesIron;
	private Resources resourcesMarble;
	private Long id;
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
	private String owner;
	private Long islandNumber;

	public List<Building> getBuildings() {
		return buildings;
	}

	public String getName() {
		return name;
	}

	public Long getResourceType() {
		return resourceType;
	}

	public Long getLevel() {
		return level;
	}

	public Long getIslandX() {
		return islandX;
	}

	public Long getOwnerId() {
		return ownerId;
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

	public Long getId() {
		return id;
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

	public String getOwner() {
		return owner;
	}

	public Long getIslandNumber() {
		return islandNumber;
	}

	public void setBuildings(List<Building> buildings) {
		this.buildings = buildings;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setResourceType(Long resourceType) {
		this.resourceType = resourceType;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public void setIslandX(Long islandX) {
		this.islandX = islandX;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
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

	public void setId(Long id) {
		this.id = id;
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

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setIslandNumber(Long islandNumber) {
		this.islandNumber = islandNumber;
	}

	public static TransportTown parse(String response) {
		JSONObject json = JSONObject.fromObject(response);
		if (json == null || json.get("town") == null)
			return null;
		
		JSONObject town = (JSONObject)json.get("town");
		TransportTown transportTown = new TransportTown();
		
		JSONArray arrays = town.getJSONArray("buildings");
		if (arrays != null) {
			List<Building> buildings = new Vector<Building>();
			for (int i = 0; i < arrays.size(); i++) {
				JSONObject array = (JSONObject) arrays.get(i);
				Long level = (array.get("level") != null) ? array.getLong("level") : null;
				Long id = (array.get("id") != null) ? array.getLong("id")	: null;
				Long buildingType = (array.get("building_type") != null) ? array.getLong("building_type") : null;
				String status = (array.get("status") != null) ? array.getString("status") : null;
				Long anchorIndex = (array.get("anchor_index") != null) ? array.getLong("anchor_index") : null;
				
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
										
					buildings.add(buildingPort);
				}
			}
		}
		
		transportTown.setName(town.get("name") != null ? town.getString("name") : null);
		transportTown.setResourceType(town.get("resource_type") != null ? town.getLong("resource_type") : null);
		transportTown.setLevel(town.get("level") != null ? town.getLong("level") : null);
		transportTown.setIslandX(town.get("island_x") != null ? town.getLong("island_x") : null);
		transportTown.setOwnerId(town.get("owner_id") != null ? town.getLong("owner_id") : null);
		
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
					transportTown.setResourcesWood(resources);
					continue;
				}
				
				if (resources.getResourceName().equals("food")) {
					transportTown.setResourcesFood(resources);
					continue;
				}
				
				if (resources.getResourceName().equals("iron")) {
					transportTown.setResourcesIron(resources);
					continue;
				}
				
				if (resources.getResourceName().equals("gold")) {
					transportTown.setResourcesGold(resources);
					continue;
				}
				
				if (resources.getResourceName().equals("marble")) {
					transportTown.setResourcesMarble(resources);
					continue;
				}
			}			
		}
		
		transportTown.setId(town.get("id") != null ? town.getLong("id") : null);
		transportTown.setIslandY(town.get("island_y") != null ? town.getLong("island_y") : null);
		transportTown.setIslandZ(town.get("island_z") != null ? town.getLong("island_z") : null);
		
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
			
			transportTown.setTransportQueues(list);
		}
		
		transportTown.setGems(town.get("gems") != null ? town.getLong("gems") : null);
		
		if (town.get("soldiers") != null) {
			arrays = town.getJSONArray("soldiers");
			for (int i = 0; i < arrays.size(); i++) {
				JSONObject array = (JSONObject) arrays.get(i);								
				Soldier soldier = new Soldier();
				soldier.setName((array.get("name") != null) ? array.getString("name") : null);
				soldier.setCount((array.get("count") != null) ? array.getLong("count") : null);
				
				if (soldier.getName() == null)
					continue;
				
				if (array.get("training_queue") != null) {
					JSONObject training = (JSONObject) array.get("training_queue");
					TrainingQueue trainingQueue = new TrainingQueue();
					trainingQueue.setTotalTime((training.get("total_time") != null) ? training.getLong("total_time") : null);
					trainingQueue.setCount((training.get("count") != null) ? training.getLong("count") : null);
					trainingQueue.setFinishTime((training.get("finish_time") != null) ? DateTime.getTime(training.getLong("finish_time")) : null);
					trainingQueue.setQueueId((training.get("queue_id") != null) ? training.getLong("queue_id") : null);
					soldier.setTrainingQueue(trainingQueue);
				}				
				
				if (soldier.getName().equals("infantry")) {
					transportTown.setSoldierInfantry(soldier);
					continue;
				}
				
				if (soldier.getName().equals("musketman")) {
					transportTown.setSoldierMusketman(soldier);
					continue;
				}
				
				if (soldier.getName().equals("catapult")) {
					transportTown.setSoldierCatapult(soldier);
					continue;
				}
				
				if (soldier.getName().equals("scout")) {
					transportTown.setSoldierScout(soldier);
					continue;
				}
				
				if (soldier.getName().equals("frigate")) {
					transportTown.setSoldierFrigate(soldier);
					continue;
				}
				
				if (soldier.getName().equals("destroyer")) {
					transportTown.setSoldierDestroyer(soldier);
					continue;
				}
			}
		}
		
		transportTown.setOwner(town.get("owner") != null ? town.getString("owner") : null);
		transportTown.setIslandNumber(town.get("island_number") != null ? town.getLong("island_number") : null);
		return transportTown;
	}
}
