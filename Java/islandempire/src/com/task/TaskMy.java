package com.task;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;
import java.util.Map.Entry;

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
import com.callback.CallBackTask;
import com.config.Config;
import com.config.ConfigTown;
import com.deals.Deal;
import com.entity.TownInfo;
import com.entity.Towns;
import com.island.IslandBuilding;
import com.island.IslandVillage;
import com.island.WorldMap;
import com.queue.BuildingQueue;
import com.queue.LinesEvent;
import com.request.RequestBuildings;
import com.request.RequestDeals;
import com.request.RequestIsland;
import com.request.RequestMessage;
import com.request.RequestRecruit;
import com.request.RequestTowns;
import com.request.RequestUpgrade;
import com.request.RequestWorldMaps;
import com.soldier.Recruit;
import com.soldier.Soldier;
import com.towns.Resources;
import com.towns.Town;
import com.util.Numeric;

public class TaskMy extends TaskBase {
	private Config m_Config;
	private final RequestTowns m_RequestTowns = new RequestTowns();
	private final RequestMessage m_RequestMessage = new RequestMessage();
	private final RequestUpgrade m_RequestUpgrade = new RequestUpgrade();
	private final RequestBuildings m_RequestBuildings = new RequestBuildings();
	private final RequestIsland m_RequestIsland = new RequestIsland();
	private final RequestWorldMaps m_RequestWorldMaps = new RequestWorldMaps();
	private final RequestDeals m_RequestDeals = new RequestDeals();
	private final RequestRecruit m_RequestRecruit = new RequestRecruit();
	private Config m_ConfigNew = null;

	public TaskMy(String taskName, Config config, CallBackTask callBack) {
		super(taskName, config.getAutoTowns(), callBack);
		m_Config = config;
	}
	
	public synchronized void setConfig(Config config) {
		if (config != null)
			m_ConfigNew = config;
		else {
			if (m_ConfigNew != null) {
				m_Config.setHost(m_ConfigNew.getHost());
				m_Config.setClientv(m_ConfigNew.getClientv());
				m_Config.setCookie(m_ConfigNew.getCookie());
				m_Config.setAutoTowns(m_ConfigNew.getAutoTowns());
				m_Config.setConfigTowns(m_ConfigNew.getConfigTowns());
			}
		}
	}

	@Override
	protected void onEntry() {
		String host = m_Config.getHost();
		String clientv = m_Config.getClientv();
		String cookie = m_Config.getCookie();
		long autoTowns = m_Config.getAutoTowns();
		List<ConfigTown> configTowns = m_Config.getConfigTowns();
		
		Towns towns = new Towns();
		List<TownInfo> townInfos = new Vector<TownInfo>();
		String username = "";
		
		for (ConfigTown configTown : configTowns) {
			Boolean autoUpgrade = configTown.getAutoUpgrade();
			Long id = configTown.getId();
			TownInfo townInfo = m_RequestTowns.request(host, clientv, cookie, id);
						
			try {					
				sleep(1000);
			} catch (InterruptedException e) {				
				
			}
			
			if (townInfo == null)
				continue;
			
			Town town = townInfo.getTown();
			if (town.getOwner() != null)
				username = town.getOwner();
			
			if (autoUpgrade && upgrade(town, m_Config, configTown)) {
				try {						
					sleep(1000);
				} catch (InterruptedException e) {
					
				}
			}
			
			sells(town, m_Config, configTown);
			buys(town, m_Config, configTown);
			townInfos.add(townInfo);
		}
		
		towns.setTownInfos(townInfos);
		
		if (username.length() > 0) {
			String message = m_RequestMessage.request(host, clientv, cookie, username, 0L);
			if (message != null) {
				towns.setMessage(message);
			}
		}
		
		m_CallBack.onTowns(towns);		
		setConfig(null);	
		setDelay(Numeric.rndNumber(autoTowns + 10, autoTowns - 10) * 1000);
	}

	@Override
	protected boolean onCheck() {
		return checkStatus(m_Config);
	}
	
	public boolean checkStatus(Config config) {
		if (config == null) {
			setCancel(true);
			return false;
		}
		
		if (config.getHost() == null) {
			setCancel(true);
			return false;
		}
		
		if (config.getClientv() == null) {
			setCancel(true);
			return false;
		}
		
		if (config.getCookie() == null) {
			setCancel(true);
			return false;
		}
		
		if (config.getAutoTowns() == null || config.getAutoTowns() == 0) {
			setCancel(true);
			return false;
		}
		
		if (config.getConfigTowns() == null || config.getConfigTowns().size() == 0) {
			setCancel(true);
			return false;
		}
		
		return true;
	}
	
	private boolean checkResources(Town town, HashMap<String, Long> resources) {
		Resources resourcesWood = town.getResourcesWood();
		Resources resourcesFood = town.getResourcesFood();
		Resources resourcesIron = town.getResourcesIron();
		Resources resourcesMarble = town.getResourcesMarble();
		Resources resourcesGold = town.getResourcesGold();
		
		for (Entry<String, Long> entry : resources.entrySet()) {
			String name = entry.getKey();
			Long count = entry.getValue();
			
			if (name.equals("wood")) {
				if (resourcesWood != null && resourcesWood.getResourceName() != null && resourcesWood.getResourceCount() != null) {					
					if (count > resourcesWood.getResourceCount())
						return false;
				} else
					return false;
				
				continue;
			}
			
			if (name.equals("food")) {
				if (resourcesFood != null && resourcesFood.getResourceName() != null && resourcesFood.getResourceCount() != null) {					
					if (count > resourcesFood.getResourceCount())
						return false;
				} else
					return false;
				
				continue;
			}
			
			if (name.equals("iron")) {
				if (resourcesIron != null && resourcesIron.getResourceName() != null && resourcesIron.getResourceCount() != null) {					
					if (count > resourcesIron.getResourceCount())
						return false;
				} else
					return false;
				
				continue;
			}
			
			if (name.equals("marble")) {
				if (resourcesMarble != null && resourcesMarble.getResourceName() != null && resourcesMarble.getResourceCount() != null) {					
					if (count > resourcesMarble.getResourceCount())
						return false;
				} else
					return false;
				
				continue;
			}
			
			if (name.equals("gold")) {
				if (resourcesGold != null && resourcesGold.getResourceName() != null && resourcesGold.getResourceCount() != null) {					
					if (count > resourcesGold.getResourceCount())
						return false;
				} else
					return false;
				
				continue;
			}
		}
		
		return true;
	}
	
	private void setResources(Town town, List<Resources> resources) {
		for (Resources resource : resources) {
			if (resource.getResourceName() == null || resource.getResourceCount() == null)
				continue;
			
			String resourceName = resource.getResourceName();
			
			if (resourceName.equals("wood")) {
				town.setResourcesWood(resource);
				continue;
			}
			
			if (resourceName.equals("food")) {
				town.setResourcesFood(resource);
				continue;
			}
			
			if (resourceName.equals("iron")) {
				town.setResourcesIron(resource);
				continue;
			}
			
			if (resourceName.equals("marble")) {
				town.setResourcesMarble(resource);
				continue;
			}
			
			if (resourceName.equals("gold")) {
				town.setResourcesGold(resource);
				continue;
			}
		}
	}
	
	private void decreaseResources(Town town, HashMap<String, Long> resources) {
		Resources resourcesWood = town.getResourcesWood();
		Resources resourcesFood = town.getResourcesFood();
		Resources resourcesIron = town.getResourcesIron();
		Resources resourcesMarble = town.getResourcesMarble();
		Resources resourcesGold = town.getResourcesGold();
		
		for (Entry<String, Long> entry : resources.entrySet()) {
			String name = entry.getKey();
			Long count = entry.getValue();
			
			if (name.equals("wood")) {
				if (resourcesWood != null && resourcesWood.getResourceName() != null && resourcesWood.getResourceCount() != null)					
					resourcesWood.setResourceCount(resourcesWood.getResourceCount() - count);
				
				continue;
			}
			
			if (name.equals("food")) {
				if (resourcesFood != null && resourcesFood.getResourceName() != null && resourcesFood.getResourceCount() != null)				
					resourcesFood.setResourceCount(resourcesFood.getResourceCount() - count);
				
				continue;
			}
			
			if (name.equals("iron")) {
				if (resourcesIron != null && resourcesIron.getResourceName() != null && resourcesIron.getResourceCount() != null)
					resourcesIron.setResourceCount(resourcesIron.getResourceCount() - count);		
				
				continue;
			}
			
			if (name.equals("marble")) {
				if (resourcesMarble != null && resourcesMarble.getResourceName() != null && resourcesMarble.getResourceCount() != null)
					resourcesMarble.setResourceCount(resourcesMarble.getResourceCount() - count);
									
				continue;
			}
			
			if (name.equals("gold")) {
				if (resourcesGold != null && resourcesGold.getResourceName() != null && resourcesGold.getResourceCount() != null)
					resourcesGold.setResourceCount(resourcesGold.getResourceCount() - count);
					
				continue;
			}
		}
	}
	
	private boolean upgrade(Town town, Config config, ConfigTown configTown) {
		String host = config.getHost();
		String clientv = config.getClientv();
		String cookie = config.getCookie();
		
		int total = 0;
		List<BuildingQueue> buildingQueues = town.getBuildingQueues();
		if (buildingQueues != null)
			total = buildingQueues.size();
		
		if (total > 1)
			return false;
		
		if (total == 1) {
			BuildingQueue buildingQueue = buildingQueues.get(0);
			List<LinesEvent> linesEvents = buildingQueue.getLinesEvent();
			if (linesEvents != null && linesEvents.size() > 1)
				return false;							
		}
		
		List<Long> prioritys = configTown.getUpgradePriority();
		if (prioritys == null)
			return false;
									
		HashMap<Long, List<Long>> buildings = new HashMap<Long, List<Long>>();
		
		if (town.getBuildingBarrack() != null) {
			BuildingSoldier buildingBarrack = town.getBuildingBarrack();
			if (buildingBarrack.getBuildingType() != null && buildingBarrack.getId() != null && buildingBarrack.getLevel() < 40) {
				List<Long> buildingIds = new Vector<Long>();
				buildingIds.add(buildingBarrack.getId());
				buildings.put(buildingBarrack.getBuildingType(), buildingIds);			
			}
		}
		
		if (town.getBuildingWood() != null) {
			BuildingResource buildingResource = town.getBuildingWood();
			if (buildingResource.getBuildingType() != null && buildingResource.getId() != null) {
				List<BuildingLine> buildingLines = buildingResource.getBuildingLine();
				if (buildingLines != null) {
					TreeMap<Long, Long> sorts = new TreeMap<Long, Long>();
					List<Long> buildingIds = new Vector<Long>();
					for (BuildingLine buildingLine : buildingLines) {
						if (buildingLine.getId() != null && buildingLine.getLevel() != null && buildingLine.getLevel() < 40 && buildingLine.getStatus() != null && buildingLine.getStatus().equals("idle"))
							sorts.put(buildingLine.getLevel(), buildingLine.getId());						
					}
					
					for (Entry<Double, List<WorldMap>> entry : sorts.entrySet()) {
						
					}
					
					Collections.sort(buildingIds);
					buildings.put(buildingResource.getBuildingType(), buildingIds);
				}
			}
		}
		
		if (town.getBuildingFood() != null) {
			BuildingResource buildingResource = town.getBuildingFood();
			if (buildingResource.getBuildingType() != null && buildingResource.getId() != null) {
				List<BuildingLine> buildingLines = buildingResource.getBuildingLine();
				if (buildingLines != null) {
					List<Long> buildingIds = new Vector<Long>();
					for (BuildingLine buildingLine : buildingLines) {
						if (buildingLine.getId() != null && buildingLine.getLevel() != null && buildingLine.getLevel() < 40 && buildingLine.getStatus() != null && buildingLine.getStatus().equals("idle"))
							buildingIds.add(buildingLine.getId());
					}
					
					Collections.sort(buildingIds);
					buildings.put(buildingResource.getBuildingType(), buildingIds);
				}
			}
		}
		
		if (town.getBuildingIron() != null) {
			BuildingResource buildingResource = town.getBuildingIron();
			if (buildingResource.getBuildingType() != null && buildingResource.getId() != null) {
				List<BuildingLine> buildingLines = buildingResource.getBuildingLine();
				if (buildingLines != null) {
					List<Long> buildingIds = new Vector<Long>();
					for (BuildingLine buildingLine : buildingLines) {
						if (buildingLine.getId() != null && buildingLine.getLevel() != null && buildingLine.getLevel() < 40 && buildingLine.getStatus() != null && buildingLine.getStatus().equals("idle"))
							buildingIds.add(buildingLine.getId());
					}
					
					Collections.sort(buildingIds);
					buildings.put(buildingResource.getBuildingType(), buildingIds);
				}
			}
		}
		
		if (town.getBuildingMarble() != null) {
			BuildingResource buildingResource = town.getBuildingMarble();
			if (buildingResource.getBuildingType() != null && buildingResource.getId() != null) {
				List<BuildingLine> buildingLines = buildingResource.getBuildingLine();
				if (buildingLines != null) {
					List<Long> buildingIds = new Vector<Long>();
					for (BuildingLine buildingLine : buildingLines) {
						if (buildingLine.getId() != null && buildingLine.getLevel() != null && buildingLine.getLevel() < 40 && buildingLine.getStatus() != null && buildingLine.getStatus().equals("idle"))
							buildingIds.add(buildingLine.getId());
					}
					
					Collections.sort(buildingIds);
					buildings.put(buildingResource.getBuildingType(), buildingIds);
				}
			}
		}
		
		if (town.getBuildingGold() != null) {
			BuildingResource buildingResource = town.getBuildingGold();
			if (buildingResource.getBuildingType() != null && buildingResource.getId() != null) {
				List<BuildingLine> buildingLines = buildingResource.getBuildingLine();
				if (buildingLines != null) {
					List<Long> buildingIds = new Vector<Long>();
					for (BuildingLine buildingLine : buildingLines) {
						if (buildingLine.getId() != null && buildingLine.getLevel() != null && buildingLine.getLevel() < 40 && buildingLine.getStatus() != null && buildingLine.getStatus().equals("idle"))
							buildingIds.add(buildingLine.getId());
					}
					
					Collections.sort(buildingIds);
					buildings.put(buildingResource.getBuildingType(), buildingIds);
				}
			}
		}
		
		if (town.getBuildingStore() != null) {
			BuildingStore buildingStore = town.getBuildingStore();
			if (buildingStore.getBuildingType() != null && buildingStore.getId() != null && buildingStore.getLevel() < 40) {
				List<Long> buildingIds = new Vector<Long>();
				buildingIds.add(buildingStore.getId());
				buildings.put(buildingStore.getBuildingType(), buildingIds);			
			}
		}
		
		if (town.getBuildingPort() != null) {
			BuildingPort buildingPort = town.getBuildingPort();
			if (buildingPort.getBuildingType() != null && buildingPort.getId() != null && buildingPort.getLevel() < 40) {
				List<Long> buildingIds = new Vector<Long>();
				buildingIds.add(buildingPort.getId());
				buildings.put(buildingPort.getBuildingType(), buildingIds);			
			}
		}
		
		if (town.getBuildingMarket() != null) {
			BuildingMarket buildingMarket = town.getBuildingMarket();
			if (buildingMarket.getBuildingType() != null && buildingMarket.getId() != null && buildingMarket.getLevel() < 40) {
				List<Long> buildingIds = new Vector<Long>();
				buildingIds.add(buildingMarket.getId());
				buildings.put(buildingMarket.getBuildingType(), buildingIds);			
			}
		}
		
		if (town.getBuildingHall() != null) {
			Building buildingHall = town.getBuildingHall();
			if (buildingHall.getBuildingType() != null && buildingHall.getId() != null && buildingHall.getLevel() < 40) {
				List<Long> buildingIds = new Vector<Long>();
				buildingIds.add(buildingHall.getId());
				buildings.put(buildingHall.getBuildingType(), buildingIds);			
			}
		}
		
		if (town.getBuildingWall() != null) {
			BuildingWall buildingWall = town.getBuildingWall();
			if (buildingWall.getBuildingType() != null && buildingWall.getId() != null) {
				List<BuildingTower> buildingTowers = buildingWall.getBuildingTower();
				if (buildingTowers != null) {
					List<Long> buildingIds = new Vector<Long>();
					for (BuildingTower buildingTower : buildingTowers) {
						if (buildingTower.getId() != null && buildingTower.getLevel() != null && buildingTower.getLevel() < 40 && buildingTower.getStatus() != null && buildingTower.getStatus().equals("idle"))
							buildingIds.add(buildingTower.getId());
					}
					
					buildings.put(buildingWall.getBuildingType(), buildingIds);
				}
			}
		}
		
		if (town.getBuildingYard() != null) {
			BuildingSoldier buildingYard = town.getBuildingYard();
			if (buildingYard.getBuildingType() != null && buildingYard.getId() != null && buildingYard.getLevel() < 40) {
				List<Long> buildingIds = new Vector<Long>();
				buildingIds.add(buildingYard.getId());
				buildings.put(buildingYard.getBuildingType(), buildingIds);			
			}
		}
		
		if (town.getBuildingCellar() != null) {
			BuildingCellar buildingCellar = town.getBuildingCellar();
			if (buildingCellar.getBuildingType() != null && buildingCellar.getId() != null && buildingCellar.getLevel() < 40) {
				List<Long> buildingIds = new Vector<Long>();
				buildingIds.add(buildingCellar.getId());
				buildings.put(buildingCellar.getBuildingType(), buildingIds);			
			}
		}
		
		if (buildings.size() > 0) {
			for (Long priority : prioritys) {
				if (!buildings.containsKey(priority))
					continue;
				
				List<Long> buildingIds = buildings.get(priority);				
				for (Long buildingId : buildingIds) {
					HashMap<String, Long> resources = m_RequestUpgrade.request(host, clientv, cookie, buildingId);
					if (resources != null && checkResources(town, resources) && m_RequestBuildings.request(host, clientv, cookie, buildingId)) {
						decreaseResources(town, resources);
						if (++total > 1)
							return true;
					}
				}
			}
		}		
				
		return false;
	}
	
	private Soldier getMinSoldier(Town town, Config config, Soldier soldier, Soldier minSoldier) {
		String host = m_Config.getHost();
		String clientv = m_Config.getClientv();
		String cookie = m_Config.getCookie();
		Long townId = town.getId();
		
		if (soldier != null && soldier.getName() != null && soldier.getCount() != null) {			
			String soldierName = soldier.getName();
			long soldierCount = soldier.getCount();
			
			Recruit recruit = m_RequestRecruit.request(host, clientv, cookie, townId, soldierName);
			if (recruit != null) {
				HashMap<String, Long> cost = recruit.getCost();
				if (cost != null && checkResources(town, cost)) {
					if (minSoldier == null)
						return soldier;
					else {						
						long minSoldierCount = minSoldier.getCount();						
						if (soldierCount < minSoldierCount)
							return soldier;
					}
				}
			}
		}
		
		return minSoldier;
	}
	
	private void recruit(Town town, Config config, ConfigTown configTown) {
				
		Soldier minSoldier = null;		
		minSoldier = getMinSoldier(town, config, town.getSoldierMusketman(), minSoldier);
		minSoldier = getMinSoldier(town, config, town.getSoldierCatapult(), minSoldier);
		minSoldier = getMinSoldier(town, config, town.getSoldierFrigate(), minSoldier);
		minSoldier = getMinSoldier(town, config, town.getSoldierDestroyer(), minSoldier);		
		if (minSoldier == null)
			return;
		
	}
	
	private long canAttack(Town town) { 
		Soldier infantry = town.getSoldierInfantry();
		Soldier musketman = town.getSoldierMusketman();
		Soldier catapult = town.getSoldierCatapult();
		Soldier frigate = town.getSoldierFrigate();
		Soldier destroyer = town.getSoldierDestroyer();		
		long total = 0;
		
		if (infantry != null && infantry.getCount() != null)
			total += infantry.getCount();
		
		if (musketman != null && musketman.getCount() != null)
			total += musketman.getCount();
		
		if (catapult != null && catapult.getCount() != null)
			total += catapult.getCount();
		
		if (frigate != null && frigate.getCount() != null)
			total += frigate.getCount();
		
		if (destroyer != null && destroyer.getCount() != null)
			total += destroyer.getCount();
		
		return total;
	}
	
	private long getLevel(long total) {
		if (total < 300)
			return 0;
		else if (total < 700)
			return 10;
		else if (total < 1500)
			return 20;
		else if (total < 3200)
			return 30;
		else 
			return 50;
	}
	
	private IslandVillage getIsland(Town town, Config config, ConfigTown configTown, long level) {
		String host = config.getHost();
		String clientv = config.getClientv();
		String cookie = config.getCookie();
		long attackLevelMin = configTown.getAttackLevelMin();
		long attackLevelMax = configTown.getAttackLevelMax();
		Long islandNumber = town.getIslandNumber();
		Long islandX = town.getIslandX();
		Long islandY = town.getIslandY();					
				
		if (islandNumber == null)
			return null;
		
		if (islandX == null || islandY == null)
			return null;
		
		Long ownerId = town.getOwnerId();
		if (ownerId == null)
			return null;
		
		List<IslandBuilding> islandBuildings = m_RequestIsland.request(host, clientv, cookie, islandNumber, ownerId);
		if (islandBuildings == null)
			return null;
		
		List<IslandVillage> list = new Vector<IslandVillage>();
		for (IslandBuilding islandBuilding : islandBuildings) {
			if (islandBuilding.equals("Village")) {
				IslandVillage islandVillage = (IslandVillage)islandBuilding;				
				if (islandVillage.getId() == null || islandVillage.getLevel() == null || islandVillage.getCityStatus() == null)
					continue;
				
				if (!islandVillage.getCityStatus().equals("normal"))					
					continue;
				
				if ((attackLevelMin > 0 && attackLevelMax > 0 && (islandVillage.getLevel() < attackLevelMin || attackLevelMax < islandVillage.getLevel())) || (islandVillage.getLevel() > level))
					continue;
				
				list.add(islandVillage);
			}
		}
		
		if (list.size() == 0) {
			List<WorldMap> worldMaps = m_RequestWorldMaps.request(host, clientv, cookie, islandNumber);
			if (worldMaps == null)
				return null;
			
			TreeMap<Double, List<WorldMap>> sorts = new TreeMap<Double, List<WorldMap>>();
												
			for (WorldMap worldMap : worldMaps) {
				if (worldMap.getIslandNumber() == null)
					continue;
				
				if (worldMap.getIslandNumber().equals(islandNumber))
					continue;
				
				Long x = worldMap.getX();
				Long y = worldMap.getY();
				
				if (x == null || y == null)
					continue;
								
				double distance = Math.sqrt((Math.pow(Math.abs(islandX - x), 2) + Math.pow(Math.abs(islandY - y), 2)));
				if (sorts.containsKey(distance)) {
					List<WorldMap> sort = sorts.get(distance);
					sort.add(worldMap);
				} else {
					List<WorldMap> sort = new Vector<WorldMap>();
					sort.add(worldMap);
					sorts.put(distance, sort);
				}
			}
			
			if (sorts.size() == 0)
				return null;
			
			for (Entry<Double, List<WorldMap>> entry : sorts.entrySet()) {
				List<WorldMap> sort = entry.getValue();
				for (WorldMap worldMap : sort) {										
					islandBuildings = m_RequestIsland.request(host, clientv, cookie, worldMap.getIslandNumber(), ownerId);
					if (islandBuildings == null)
						continue;
					
					for (IslandBuilding islandBuilding : islandBuildings) {
						if (islandBuilding.equals("Village")) {
							IslandVillage islandVillage = (IslandVillage)islandBuilding;				
							if (islandVillage.getId() == null || islandVillage.getLevel() == null || islandVillage.getCityStatus() == null)
								continue;
							
							if (!islandVillage.getCityStatus().equals("normal"))					
								continue;
							
							if ((attackLevelMin > 0 && attackLevelMax > 0 && (islandVillage.getLevel() < attackLevelMin || attackLevelMax < islandVillage.getLevel())) || (islandVillage.getLevel() > level))
								continue;
							
							list.add(islandVillage);
						}
					}
					
					if (list.size() > 0)
						break;
				}
			}
						
			if (list.size() == 0)
				return null;				
		}
		
		Long max = 0L;
		IslandVillage village = null;
		
		for (IslandVillage islandVillage : list) {
			if (village == null) {
				village = islandVillage;
				max = islandVillage.getLevel();
				continue;
			}
			
			if (islandVillage.getLevel() > max) {
				village = islandVillage;
				max = islandVillage.getLevel();
			}				
		}
		
		return village;
	}
	
	private boolean sells(Town town, Config config, HashMap<String, Double> marketRate, Resources resources, Long leftCapacity) {
		String host = config.getHost();
		String clientv = config.getClientv();
		String cookie = config.getCookie();		
		Long id = town.getId();		
		String name = resources.getResourceName();
		Long count = resources.getResourceCount();
		Long maxVolume = resources.getMaxVolume();
		
		if (name == null || count == null || maxVolume == null)
			return false;
		
		if (!marketRate.containsKey(name))
			return false;
		
		double rate = marketRate.get(name);
		if (rate <= 0D)
			return false;
		
		if ((double)count / (double)maxVolume >= rate) {			
			List<Deal> deals = m_RequestDeals.request(host, clientv, cookie, name, 0L);
			if (deals != null && deals.size() > 0) {
				Deal deal = deals.get(0);
				Double sellerPrice = deal.getSellerPrice();
				if (sellerPrice != null) {
					Long sellerCount = leftCapacity;
					if (leftCapacity > count)
						sellerCount = count;
										
					return m_RequestDeals.request(host, clientv, cookie, id, name, sellerPrice, sellerCount);
				}
			}
		}
		
		return false;
	}
	
	private void sells(Town town, Config config, ConfigTown configTown) {
		if (configTown.getSells() == null)
			return;
		
		BuildingMarket buildingMarket = town.getBuildingMarket();
		if (buildingMarket == null)
			return;
		
		Long leftCapacity = buildingMarket.getLeftCapacity();
		if (leftCapacity == null || leftCapacity <= 0)
			return;
				
		Resources resourcesWood = town.getResourcesWood();
		if (resourcesWood != null && sells(town, config, configTown.getSells(), resourcesWood, leftCapacity))
			return;
		
		Resources resourcesFood = town.getResourcesFood();
		if (resourcesFood != null && sells(town, config, configTown.getSells(), resourcesFood, leftCapacity))
			return;
	}
	
	private boolean buys(Town town, Config config, Resources resources) {
		Long townId = town.getId();
		String host = config.getHost();
		String clientv = config.getClientv();
		String cookie = config.getCookie();
		
		List<Deal> deals = m_RequestDeals.request(host, clientv, cookie, resources.getResourceName(), 0L);
		if (deals == null || deals.size() == 0)
			return false;
		
		Deal deal = deals.get(0);
		Long dealId = deal.getId();
		if (dealId == null)
			return false;
		
		List<Resources> list = m_RequestDeals.request(host, clientv, cookie, dealId, townId);
		if (list == null)
			return false;
		
		setResources(town, list);
		return true;
	}
	
	private void buys(Town town, Config config, ConfigTown configTown) {
		Long id = town.getId();
		if (id == null)
			return;
		
		Resources resourcesGold = town.getResourcesGold();
		if (resourcesGold == null || resourcesGold.getResourceCount() == null || resourcesGold.getMaxVolume() == null)
			return;
		
		Long goldCount = resourcesGold.getResourceCount();		
		Long goldMaxVolume = resourcesGold.getMaxVolume();
		
		HashMap<String, Double> sells = configTown.getSells();
		if (sells == null)
			return;
		
		if (!sells.containsKey("gold"))
			return;
		
		double goldRate = sells.get("gold");
		if (goldRate <= 0D)
			return;
		
		HashMap<String, Double> buys = configTown.getBuys();
		if (buys == null)
			return;					
		
		Resources minResources = null;
						
		while ((double)goldCount / (double)goldMaxVolume >= goldRate) {			
			minResources = getMinResources(town.getResourcesWood(), minResources, buys);			
			minResources = getMinResources(town.getResourcesFood(), minResources, buys);
			minResources = getMinResources(town.getResourcesMarble(), minResources, buys);
			minResources = getMinResources(town.getResourcesIron(), minResources, buys);
			
			if (minResources != null) {
				if (buys(town, config, minResources)) {					
					resourcesGold = town.getResourcesGold();
					if (resourcesGold == null)
						break;
					
					goldCount = resourcesGold.getResourceCount();		
					goldMaxVolume = resourcesGold.getMaxVolume();
				} else
					break;
			} else
				break;
		}		
	}
	
	private Resources getMinResources(Resources resources, Resources minResources, HashMap<String, Double> buys) {		
		if (resources != null && resources.getResourceName() != null && resources.getResourceCount() != null && resources.getMaxVolume() != null) {
			String resourceName = resources.getResourceName();			
			if (buys.containsKey(resourceName)) {				
				double buysRate = buys.get(resourceName);
				if (buysRate <= 0D)
					return minResources;
				
				long resourcesCount = resources.getResourceCount();
				long resourcesMaxVolume = resources.getMaxVolume();
				if ((double)resourcesCount / (double)resourcesMaxVolume < buysRate) {
					if (minResources == null)
						return resources;
					else {
						long minResourceCount = minResources.getResourceCount();
						if (resourcesCount < minResourceCount)
							return resources;						
					}
				}
			}
		}
		
		return minResources;
	}
}
