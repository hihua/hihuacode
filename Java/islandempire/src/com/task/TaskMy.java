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
import com.hero.Equipment;
import com.hero.Hero;
import com.island.IslandBuilding;
import com.island.IslandVillage;
import com.island.WorldMap;
import com.queue.BattleQueue;
import com.queue.BuildingQueue;
import com.queue.LinesEvent;
import com.queue.TransportQueue;
import com.request.RequestArmy;
import com.request.RequestBuildings;
import com.request.RequestDeals;
import com.request.RequestEquipment;
import com.request.RequestEvent;
import com.request.RequestIsland;
import com.request.RequestMessage;
import com.request.RequestRecruit;
import com.request.RequestTowns;
import com.request.RequestTransport;
import com.request.RequestUpgrade;
import com.request.RequestWorldMaps;
import com.soldier.Recruit;
import com.soldier.Soldier;
import com.towns.OtherTown;
import com.towns.Resources;
import com.towns.Town;
import com.towns.TransportTown;
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
	private final RequestArmy m_RequestArmy = new RequestArmy();
	private final RequestTransport m_RequestTransport = new RequestTransport();
	private final RequestEvent m_RequestEvent = new RequestEvent();
	private final RequestEquipment m_RequestEquipment = new RequestEquipment();
	private final List<Long> m_Village = new Vector<Long>();
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
		
		setBattles(m_Config);
						
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
			
			if (autoUpgrade)
				upgrade(town, m_Config, configTown);				
						
			sells(town, m_Config, configTown);
			buys(town, m_Config, configTown);
			recruit(town, m_Config, configTown);
			attack(town, m_Config, configTown);
			transport(town, m_Config, configTown);
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
		
		if (config.getUserId() == null) {
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
	
	private void setBattles(Config config) {
		String host = config.getHost();
		Long userId = config.getUserId();
		String clientv = config.getClientv();
		String cookie = config.getCookie();
		
		List<BattleQueue> battleQueues = m_RequestEvent.request(host, clientv, cookie, userId);
		if (battleQueues == null)
			return;
		
		List<Long> list = new Vector<Long>();
				
		for (BattleQueue battleQueue : battleQueues) {
			if (battleQueue.getMission() == null || battleQueue.getToTownId() == null) 
				continue;
			
			Long mission = battleQueue.getMission();
			if (mission != 1)
				continue;
			
			Long toTownId = battleQueue.getToTownId();			
			if (m_Village.indexOf(toTownId) == -1)
				m_Village.add(toTownId);
			
			list.add(toTownId);
		}
		
		if (list.size() == 0)
			m_Village.clear();
		else {
			List<Long> tmp = new Vector<Long>();
			for (Long toTownId : m_Village) {
				if (list.indexOf(toTownId) == -1)
					tmp.add(toTownId);
			}
			
			for (Long toTownId : tmp)
				m_Village.remove(toTownId);			
		}
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
				if (resourcesWood != null && resourcesWood.getResourceCount() != null)					
					resourcesWood.setResourceCount(resourcesWood.getResourceCount() - count);
				
				continue;
			}
			
			if (name.equals("food")) {
				if (resourcesFood != null && resourcesFood.getResourceCount() != null)				
					resourcesFood.setResourceCount(resourcesFood.getResourceCount() - count);
				
				continue;
			}
			
			if (name.equals("iron")) {
				if (resourcesIron != null && resourcesIron.getResourceCount() != null)
					resourcesIron.setResourceCount(resourcesIron.getResourceCount() - count);		
				
				continue;
			}
			
			if (name.equals("marble")) {
				if (resourcesMarble != null && resourcesMarble.getResourceCount() != null)
					resourcesMarble.setResourceCount(resourcesMarble.getResourceCount() - count);
									
				continue;
			}
			
			if (name.equals("gold")) {
				if (resourcesGold != null && resourcesGold.getResourceCount() != null)
					resourcesGold.setResourceCount(resourcesGold.getResourceCount() - count);
					
				continue;
			}
		}
	}
	
	private void decreaseSoldiers(Town town, HashMap<String, Long> soldiers) {
		Soldier soldierInfantry = town.getSoldierInfantry();
		Soldier soldierScout = town.getSoldierScout();
		Soldier soldierMusketman = town.getSoldierMusketman();
		Soldier soldierCatapult = town.getSoldierCatapult();
		Soldier soldierFrigate = town.getSoldierFrigate();
		Soldier soldierDestroyer = town.getSoldierDestroyer();
		
		for (Entry<String, Long> entry : soldiers.entrySet()) {
			String name = entry.getKey();
			Long count = entry.getValue();
			
			if (name.equals("infantry")) {
				if (soldierInfantry != null && soldierInfantry.getCount() != null)
					soldierInfantry.setCount(soldierInfantry.getCount() - count);
				
				continue;
			}
			
			if (name.equals("scout")) {
				if (soldierScout != null && soldierScout.getCount() != null)
					soldierScout.setCount(soldierScout.getCount() - count);
				
				continue;
			}
			
			if (name.equals("musketman")) {
				if (soldierMusketman != null && soldierMusketman.getCount() != null)
					soldierMusketman.setCount(soldierMusketman.getCount() - count);
				
				continue;
			}
			
			if (name.equals("catapult")) {
				if (soldierCatapult != null && soldierCatapult.getCount() != null)
					soldierCatapult.setCount(soldierCatapult.getCount() - count);
				
				continue;
			}
			
			if (name.equals("frigate")) {
				if (soldierFrigate != null && soldierFrigate.getCount() != null)
					soldierFrigate.setCount(soldierFrigate.getCount() - count);
				
				continue;
			}
			
			if (name.equals("destroyer")) {
				if (soldierDestroyer != null && soldierDestroyer.getCount() != null)
					soldierDestroyer.setCount(soldierDestroyer.getCount() - count);
				
				continue;
			}
		}
	}
		
	private void upgrade(Town town, Config config, ConfigTown configTown) {
		String host = config.getHost();
		String clientv = config.getClientv();
		String cookie = config.getCookie();
		Long level = town.getLevel();
		
		if (level == null)
			return;
		
		int total = 0;
		List<BuildingQueue> buildingQueues = town.getBuildingQueues();
		if (buildingQueues != null)
			total = buildingQueues.size();
		
		if (total > 1)
			return;
		
		if (total == 1) {
			BuildingQueue buildingQueue = buildingQueues.get(0);
			List<LinesEvent> linesEvents = buildingQueue.getLinesEvent();
			if (linesEvents != null && linesEvents.size() > 1)
				return;							
		}
		
		List<Long> prioritys = configTown.getUpgradePriority();
		if (prioritys == null)
			return;
									
		HashMap<Long, List<Long>> buildings = new HashMap<Long, List<Long>>();
		
		if (town.getBuildingBarrack() != null) {
			BuildingSoldier buildingBarrack = town.getBuildingBarrack();
			if (buildingBarrack.getBuildingType() != null && buildingBarrack.getId() != null && buildingBarrack.getLevel() != null && buildingBarrack.getLevel() < 40 && buildingBarrack.getLevel() < level && buildingBarrack.getStatus() != null && buildingBarrack.getStatus().equals("idle")) {				
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
					TreeMap<Long, List<Long>> sorts = new TreeMap<Long, List<Long>>();					
					for (BuildingLine buildingLine : buildingLines) {
						if (buildingLine.getId() != null && buildingLine.getLevel() != null && buildingLine.getLevel() < 40 && buildingLine.getLevel() < level && buildingLine.getStatus() != null && buildingLine.getStatus().equals("idle")) {
							if (sorts.containsKey(buildingLine.getLevel())) {
								List<Long> buildingIds = sorts.get(buildingLine.getLevel());
								buildingIds.add(buildingLine.getId());
							} else {
								List<Long> buildingIds = new Vector<Long>();
								buildingIds.add(buildingLine.getId());
								sorts.put(buildingLine.getLevel(), buildingIds);
							}											
						}
					}
					
					for (Entry<Long, List<Long>> entry : sorts.entrySet()) {
						List<Long> buildingIds = entry.getValue();
						buildings.put(buildingResource.getBuildingType(), buildingIds);
					}				
				}
			}
		}
		
		if (town.getBuildingFood() != null) {
			BuildingResource buildingResource = town.getBuildingFood();
			if (buildingResource.getBuildingType() != null && buildingResource.getId() != null) {
				List<BuildingLine> buildingLines = buildingResource.getBuildingLine();
				if (buildingLines != null) {
					TreeMap<Long, List<Long>> sorts = new TreeMap<Long, List<Long>>();
					for (BuildingLine buildingLine : buildingLines) {
						if (buildingLine.getId() != null && buildingLine.getLevel() != null && buildingLine.getLevel() < 40 && buildingLine.getLevel() < level && buildingLine.getStatus() != null && buildingLine.getStatus().equals("idle")) {
							if (sorts.containsKey(buildingLine.getLevel())) {
								List<Long> buildingIds = sorts.get(buildingLine.getLevel());
								buildingIds.add(buildingLine.getId());
							} else {
								List<Long> buildingIds = new Vector<Long>();
								buildingIds.add(buildingLine.getId());
								sorts.put(buildingLine.getLevel(), buildingIds);
							}
						}							
					}
					
					for (Entry<Long, List<Long>> entry : sorts.entrySet()) {
						List<Long> buildingIds = entry.getValue();
						buildings.put(buildingResource.getBuildingType(), buildingIds);
					}
				}
			}
		}
		
		if (town.getBuildingIron() != null) {
			BuildingResource buildingResource = town.getBuildingIron();
			if (buildingResource.getBuildingType() != null && buildingResource.getId() != null) {
				List<BuildingLine> buildingLines = buildingResource.getBuildingLine();
				if (buildingLines != null) {
					TreeMap<Long, List<Long>> sorts = new TreeMap<Long, List<Long>>();
					for (BuildingLine buildingLine : buildingLines) {
						if (buildingLine.getId() != null && buildingLine.getLevel() != null && buildingLine.getLevel() < 40 && buildingLine.getLevel() < level && buildingLine.getStatus() != null && buildingLine.getStatus().equals("idle")) {
							if (sorts.containsKey(buildingLine.getLevel())) {
								List<Long> buildingIds = sorts.get(buildingLine.getLevel());
								buildingIds.add(buildingLine.getId());
							} else {
								List<Long> buildingIds = new Vector<Long>();
								buildingIds.add(buildingLine.getId());
								sorts.put(buildingLine.getLevel(), buildingIds);
							}
						}							
					}
					
					for (Entry<Long, List<Long>> entry : sorts.entrySet()) {
						List<Long> buildingIds = entry.getValue();
						buildings.put(buildingResource.getBuildingType(), buildingIds);
					}
				}
			}
		}
		
		if (town.getBuildingMarble() != null) {
			BuildingResource buildingResource = town.getBuildingMarble();
			if (buildingResource.getBuildingType() != null && buildingResource.getId() != null) {
				List<BuildingLine> buildingLines = buildingResource.getBuildingLine();
				if (buildingLines != null) {
					TreeMap<Long, List<Long>> sorts = new TreeMap<Long, List<Long>>();
					for (BuildingLine buildingLine : buildingLines) {
						if (buildingLine.getId() != null && buildingLine.getLevel() != null && buildingLine.getLevel() < 40 && buildingLine.getLevel() < level && buildingLine.getStatus() != null && buildingLine.getStatus().equals("idle")) {
							if (sorts.containsKey(buildingLine.getLevel())) {
								List<Long> buildingIds = sorts.get(buildingLine.getLevel());
								buildingIds.add(buildingLine.getId());
							} else {
								List<Long> buildingIds = new Vector<Long>();
								buildingIds.add(buildingLine.getId());
								sorts.put(buildingLine.getLevel(), buildingIds);
							}
						}
					}
					
					for (Entry<Long, List<Long>> entry : sorts.entrySet()) {
						List<Long> buildingIds = entry.getValue();
						buildings.put(buildingResource.getBuildingType(), buildingIds);
					}
				}
			}
		}
		
		if (town.getBuildingGold() != null) {
			BuildingResource buildingResource = town.getBuildingGold();
			if (buildingResource.getBuildingType() != null && buildingResource.getId() != null) {
				List<BuildingLine> buildingLines = buildingResource.getBuildingLine();
				if (buildingLines != null) {
					TreeMap<Long, List<Long>> sorts = new TreeMap<Long, List<Long>>();
					for (BuildingLine buildingLine : buildingLines) {
						if (buildingLine.getId() != null && buildingLine.getLevel() != null && buildingLine.getLevel() < 40 && buildingLine.getLevel() < level && buildingLine.getStatus() != null && buildingLine.getStatus().equals("idle")) {
							if (sorts.containsKey(buildingLine.getLevel())) {
								List<Long> buildingIds = sorts.get(buildingLine.getLevel());
								buildingIds.add(buildingLine.getId());
							} else {
								List<Long> buildingIds = new Vector<Long>();
								buildingIds.add(buildingLine.getId());
								sorts.put(buildingLine.getLevel(), buildingIds);
							}
						}							
					}
					
					for (Entry<Long, List<Long>> entry : sorts.entrySet()) {
						List<Long> buildingIds = entry.getValue();
						buildings.put(buildingResource.getBuildingType(), buildingIds);
					}
				}
			}
		}
		
		if (town.getBuildingStore() != null) {
			BuildingStore buildingStore = town.getBuildingStore();
			if (buildingStore.getBuildingType() != null && buildingStore.getId() != null && buildingStore.getLevel() != null && buildingStore.getLevel() < 40 && buildingStore.getLevel() < level && buildingStore.getStatus() != null && buildingStore.getStatus().equals("idle")) {
				List<Long> buildingIds = new Vector<Long>();
				buildingIds.add(buildingStore.getId());
				buildings.put(buildingStore.getBuildingType(), buildingIds);			
			}
		}
		
		if (town.getBuildingPort() != null) {
			BuildingPort buildingPort = town.getBuildingPort();
			if (buildingPort.getBuildingType() != null && buildingPort.getId() != null && buildingPort.getLevel() != null && buildingPort.getLevel() < 40 && buildingPort.getLevel() < level && buildingPort.getStatus() != null && buildingPort.getStatus().equals("idle")) {
				List<Long> buildingIds = new Vector<Long>();
				buildingIds.add(buildingPort.getId());
				buildings.put(buildingPort.getBuildingType(), buildingIds);			
			}
		}
		
		if (town.getBuildingMarket() != null) {
			BuildingMarket buildingMarket = town.getBuildingMarket();
			if (buildingMarket.getBuildingType() != null && buildingMarket.getId() != null && buildingMarket.getLevel() != null && buildingMarket.getLevel() < 40 && buildingMarket.getLevel() < level && buildingMarket.getStatus() != null && buildingMarket.getStatus().equals("idle")) {
				List<Long> buildingIds = new Vector<Long>();
				buildingIds.add(buildingMarket.getId());
				buildings.put(buildingMarket.getBuildingType(), buildingIds);			
			}
		}
		
		if (town.getBuildingHall() != null) {
			Building buildingHall = town.getBuildingHall();
			if (buildingHall.getBuildingType() != null && buildingHall.getId() != null && buildingHall.getLevel() != null && buildingHall.getLevel() < 40 && buildingHall.getStatus() != null && buildingHall.getStatus().equals("idle")) {
				List<Long> buildingIds = new Vector<Long>();
				buildingIds.add(buildingHall.getId());
				buildings.put(buildingHall.getBuildingType(), buildingIds);			
			}
		}
		
		if (town.getBuildingWall() != null) {
			BuildingWall buildingWall = town.getBuildingWall();
			if (buildingWall.getBuildingType() != null) {
				if (buildingWall.getId() != null && buildingWall.getLevel() != null && buildingWall.getLevel() < 40 && buildingWall.getLevel() < level && buildingWall.getStatus() != null && buildingWall.getStatus().equals("idle")) {
					List<Long> buildingIds = new Vector<Long>();
					buildingIds.add(buildingWall.getId());
					buildings.put(buildingWall.getBuildingType(), buildingIds);	
				}
			
				List<BuildingTower> buildingTowers = buildingWall.getBuildingTower();
				if (buildingTowers != null) {
					TreeMap<Long, List<Long>> sorts = new TreeMap<Long, List<Long>>();
					for (BuildingTower buildingTower : buildingTowers) {
						if (buildingTower.getId() != null && buildingTower.getLevel() != null && buildingTower.getLevel() < 40 && buildingTower.getLevel() < level && buildingTower.getStatus() != null && buildingTower.getStatus().equals("idle")) {
							if (sorts.containsKey(buildingTower.getLevel())) {
								List<Long> buildingIds = sorts.get(buildingTower.getLevel());
								buildingIds.add(buildingTower.getId());
							} else {
								List<Long> buildingIds = new Vector<Long>();
								buildingIds.add(buildingTower.getId());
								sorts.put(buildingTower.getLevel(), buildingIds);
							}
						}						
					}
					
					for (Entry<Long, List<Long>> entry : sorts.entrySet()) {						
						if (buildings.containsKey(buildingWall.getBuildingType())) {
							List<Long> buildingIds = buildings.get(buildingWall.getBuildingType());
							buildingIds.addAll(entry.getValue());
						} else {
							List<Long> buildingIds = new Vector<Long>();
							buildingIds.addAll(entry.getValue());
							buildings.put(buildingWall.getBuildingType(), buildingIds);
						}					
					}
				}			
			}		
		}
		
		if (town.getBuildingYard() != null) {
			BuildingSoldier buildingYard = town.getBuildingYard();
			if (buildingYard.getBuildingType() != null && buildingYard.getId() != null && buildingYard.getLevel() != null && buildingYard.getLevel() < 40 && buildingYard.getLevel() < level && buildingYard.getStatus() != null && buildingYard.getStatus().equals("idle")) {
				List<Long> buildingIds = new Vector<Long>();
				buildingIds.add(buildingYard.getId());
				buildings.put(buildingYard.getBuildingType(), buildingIds);			
			}
		}
		
		if (town.getBuildingCellar() != null) {
			BuildingCellar buildingCellar = town.getBuildingCellar();
			if (buildingCellar.getBuildingType() != null && buildingCellar.getId() != null && buildingCellar.getLevel() != null && buildingCellar.getLevel() < 40 && buildingCellar.getLevel() < level && buildingCellar.getStatus() != null && buildingCellar.getStatus().equals("idle")) {
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
							return;
					}
				}
			}
		}
	}
	
	private Soldier getMinSoldier(Town town, Config config, long battleCount, Soldier soldier, Soldier minSoldier, List<Recruit> recruits) {
		String host = config.getHost();
		String clientv = config.getClientv();
		String cookie = config.getCookie();
		Long townId = town.getId();
		
		if (soldier != null && soldier.getName() != null && soldier.getCount() != null) {			
			String soldierName = soldier.getName();
			long soldierCount = soldier.getCount() + battleCount;
			
			Recruit recruit = m_RequestRecruit.request(host, clientv, cookie, townId, soldierName);
			if (recruit != null) {
				HashMap<String, Long> cost = recruit.getCost();				
				if (cost != null && checkResources(town, cost)) {
					if (minSoldier == null) {
						recruits.add(recruit);
						return soldier;
					} else {						
						long minSoldierCount = minSoldier.getCount();						
						if (soldierCount < minSoldierCount) {
							recruits.clear();
							recruits.add(recruit);
							return soldier;
						}
					}
				}
			}
		}
		
		return minSoldier;
	}
	
	private long getRecruitCount(Town town, Recruit recruit) {		
		Resources resourcesWood = town.getResourcesWood();
		Resources resourcesFood = town.getResourcesFood();
		Resources resourcesIron = town.getResourcesIron();
		Resources resourcesMarble = town.getResourcesMarble();
		Resources resourcesGold = town.getResourcesGold();
		long minCount = -1;
		
		HashMap<String, Long> cost = recruit.getCost();
		for (Entry<String, Long> entry : cost.entrySet()) {
			String name = entry.getKey();
			Long count = entry.getValue();
			
			if (name.equals("wood") && resourcesWood != null && resourcesWood.getResourceCount() != null) {
				long resourceCount = resourcesWood.getResourceCount();
				long total = resourceCount / count;
				if (minCount == -1 || total < minCount)
					minCount = total;
				
				continue;
			}
			
			if (name.equals("food") && resourcesFood != null && resourcesFood.getResourceCount() != null) {
				long resourceCount = resourcesFood.getResourceCount();
				long total = resourceCount / count;
				if (minCount == -1 || total < minCount)
					minCount = total;
				
				continue;
			}
			
			if (name.equals("iron") && resourcesIron != null && resourcesIron.getResourceCount() != null) {
				long resourceCount = resourcesIron.getResourceCount();
				long total = resourceCount / count;
				if (minCount == -1 || total < minCount)
					minCount = total;
				
				continue;
			}
			
			if (name.equals("marble") && resourcesMarble != null && resourcesMarble.getResourceCount() != null) {
				long resourceCount = resourcesMarble.getResourceCount();
				long total = resourceCount / count;
				if (minCount == -1 || total < minCount)
					minCount = total;
				
				continue;
			}
			
			if (name.equals("gold") && resourcesGold != null && resourcesGold.getResourceCount() != null) {
				long resourceCount = resourcesGold.getResourceCount();
				long total = resourceCount / count;
				if (minCount == -1 || total < minCount)
					minCount = total;
				
				continue;
			}
		}
		
		return minCount;
	}
	
	private void recruit(Town town, Config config, ConfigTown configTown) {
		Boolean autoRecruit = configTown.getAutoRecruit();
		if (autoRecruit == null || !autoRecruit)
			return;
		
		long total = 0;
		Soldier soldier = town.getSoldierInfantry();
		if (soldier.getTrainingQueue() != null)
			total++;
		
		soldier = town.getSoldierScout();
		if (soldier.getTrainingQueue() != null)
			total++;
		
		soldier = town.getSoldierMusketman();
		if (soldier.getTrainingQueue() != null)
			total++;
		
		soldier = town.getSoldierCatapult();
		if (soldier.getTrainingQueue() != null)
			total++;
		
		soldier = town.getSoldierFrigate();
		if (soldier.getTrainingQueue() != null)
			total++;
		
		soldier = town.getSoldierDestroyer();
		if (soldier.getTrainingQueue() != null)
			total++;
		
		if (total > 1)
			return;
		
		long infantryCount = 0;
		long musketmanCount = 0;
		long catapultCount = 0;
		long frigateCount = 0;
		long destroyerCount = 0;
		
		List<BattleQueue> battleQueues = town.getBattleQueues();
		if (battleQueues != null) {
			for (BattleQueue battleQueue : battleQueues) {
				if (battleQueue.getInfantry() != null)
					infantryCount += battleQueue.getInfantry();
				
				if (battleQueue.getMusketman() != null)
					musketmanCount += battleQueue.getMusketman();
				
				if (battleQueue.getCatapult() != null)
					catapultCount += battleQueue.getCatapult();
				
				if (battleQueue.getFrigate() != null)
					frigateCount += battleQueue.getFrigate();
				
				if (battleQueue.getDestroyer() != null)
					destroyerCount += battleQueue.getDestroyer();
			}
		}
				
		Soldier minSoldier = null;
		List<Recruit> recruits = new Vector<Recruit>(1);
		
		soldier = town.getSoldierInfantry();
		if (soldier != null && soldier.getTrainingQueue() == null)
			minSoldier = getMinSoldier(town, config, infantryCount, soldier, minSoldier, recruits);
		
		soldier = town.getSoldierMusketman();
		if (soldier != null && soldier.getTrainingQueue() == null)
			minSoldier = getMinSoldier(town, config, musketmanCount, soldier, minSoldier, recruits);
		
		soldier = town.getSoldierCatapult();
		if (soldier != null && soldier.getTrainingQueue() == null)
			minSoldier = getMinSoldier(town, config, catapultCount, soldier, minSoldier, recruits);
		
		soldier = town.getSoldierFrigate();
		if (soldier != null && soldier.getTrainingQueue() == null)
			minSoldier = getMinSoldier(town, config, frigateCount, soldier, minSoldier, recruits);
				
		soldier = town.getSoldierDestroyer();
		if (soldier != null && soldier.getTrainingQueue() == null)
			minSoldier = getMinSoldier(town, config, destroyerCount, soldier, minSoldier, recruits);
		
		if (minSoldier == null || recruits.size() == 0)
			return;
		
		String soldierName = minSoldier.getName();		
		Recruit recruit = recruits.get(0);
		long count = getRecruitCount(town, recruit);
		if (count > 0) {
			String host = config.getHost();
			String clientv = config.getClientv();
			String cookie = config.getCookie();
			Long townId = town.getId();
			List<Resources> resources = m_RequestRecruit.request(host, clientv, cookie, townId, soldierName, count);
			if (resources == null)
				return;
			
			setResources(town, resources);
		}
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
	
	private long getAttackLevel(long total) {
		if (total < 400)
			return 0;
		else if (total < 900)
			return 10;
		else if (total < 2000)
			return 20;
		else if (total < 4500)
			return 30;
		else 
			return 50;
	}
	
	private long getAttackCount(long level) {
		if (level <= 10)
			return 400;
		else if (level <= 20)
			return 900;
		else if (level <= 30)
			return 2000;
		else if (level <= 40)
			return 4500;
		else 
			return 7000;
	}
	
	private HashMap<String, Long> getAttackSoldier(Town town, long count) {		
		long left = count;
		HashMap<String, Long> soldiers = new HashMap<String, Long>();
		HashMap<String, Long> more = new HashMap<String, Long>();
						
		left -= getAttackSoldier(count, town.getSoldierDestroyer(), soldiers, more);
		left -= getAttackSoldier(count, town.getSoldierCatapult(), soldiers, more);
		left -= getAttackSoldier(count, town.getSoldierFrigate(), soldiers, more);
		left -= getAttackSoldier(count, town.getSoldierMusketman(), soldiers, more);
		left -= getAttackSoldier(count, town.getSoldierInfantry(), soldiers, more);
		
		if (left > 0 && more.size() > 0) {
			for (Entry<String, Long> entry : more.entrySet()) {
				String key = entry.getKey();
				Long value = entry.getValue();
				if (value > left)
					value = left;
				
				Long amount = soldiers.get(key);
				amount += value;
				soldiers.put(key, amount);
				left -= value;				
				if (left <= 0)
					break;
			}			
		}
				
		if (left <= 0 && soldiers.size() > 0)
			return soldiers;
		else
			return null;
	}
	
	private long getAttackSoldier(long count, Soldier soldier, HashMap<String, Long> soldiers, HashMap<String, Long> more) {
		if (soldier != null && soldier.getName() != null && soldier.getCount() != null && soldier.getCount() > 0) {
			long total = count / 5;
			if (total < soldier.getCount())
				more.put(soldier.getName(), soldier.getCount() - total);
			else
				total = soldier.getCount();
																	
			soldiers.put(soldier.getName(), total);
			return total;
		} else
			return 0;
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
			String type = islandBuilding.getType();
			if (type == null)
				continue;
			
			if (type.equals("Village")) {
				IslandVillage islandVillage = (IslandVillage)islandBuilding;				
				if (islandVillage.getId() == null || islandVillage.getLevel() == null || islandVillage.getCityStatus() == null)
					continue;
				
				if (!islandVillage.getCityStatus().equals("normal"))					
					continue;
				
				if ((attackLevelMin > 0 && attackLevelMax > 0 && (islandVillage.getLevel() < attackLevelMin || attackLevelMax < islandVillage.getLevel())) || (islandVillage.getLevel() > level))
					continue;
				
				if (m_Village.indexOf(islandVillage.getId()) > -1)
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
						String type = islandBuilding.getType();
						if (type == null)
							continue;
						
						if (type.equals("Village")) {
							IslandVillage islandVillage = (IslandVillage)islandBuilding;				
							if (islandVillage.getId() == null || islandVillage.getLevel() == null || islandVillage.getCityStatus() == null)
								continue;
							
							if (!islandVillage.getCityStatus().equals("normal"))					
								continue;
							
							if ((attackLevelMin > 0 && attackLevelMax > 0 && (islandVillage.getLevel() < attackLevelMin || attackLevelMax < islandVillage.getLevel())) || (islandVillage.getLevel() > level))
								continue;
							
							if (m_Village.indexOf(islandVillage.getId()) > -1)
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
	
	private boolean useEquipment(Town town, Config config) {
		String host = config.getHost();
		String clientv = config.getClientv();
		String cookie = config.getCookie();
		Long townId = town.getId();
		
		List<Equipment> equipments = m_RequestEquipment.request(host, clientv, cookie, townId);
		if (equipments == null)
			return false;
		
		for (Equipment equipment : equipments) {
			if (equipment.getEquipmentId() == null || equipment.getType() == null)
				continue;
			
			if (equipment.getType() == 9) {
				Long equipmentId = equipment.getEquipmentId();						
				if (m_RequestEquipment.request(host, clientv, cookie, equipmentId, townId))
					return true;
			}			
		}
		
		return false;
	}
		
	private void attack(Town town, Config config, ConfigTown configTown) {
		String host = config.getHost();
		String clientv = config.getClientv();
		String cookie = config.getCookie();
		Long townId = town.getId();
		Boolean autoAttack = configTown.getAutoAttack();
		HashMap<String, Double> sells = configTown.getSells();
		
		if (!autoAttack)
			return;
		
		int total = 0;
		Resources resourcesWood = town.getResourcesWood();
		Resources resourcesFood = town.getResourcesFood();
		Resources resourcesIron = town.getResourcesIron();
		Resources resourcesMarble = town.getResourcesMarble();
		Resources resourcesGold = town.getResourcesGold();
				
		if (resourcesWood != null && resourcesWood.getResourceName() != null && resourcesWood.getResourceCount() != null && resourcesWood.getMaxVolume() != null) {
			String resourceName = resourcesWood.getResourceName();
			Long resourceCount = resourcesWood.getResourceCount();
			Long resourceMaxVolume = resourcesWood.getMaxVolume();
			
			if (sells.containsKey(resourceName)) {
				Double rate = sells.get(resourceName);
				if ((double)resourceCount / (double)resourceMaxVolume >= rate)
					total++;				
			}			
		}	
		
		if (resourcesFood != null && resourcesFood.getResourceName() != null && resourcesFood.getResourceCount() != null && resourcesFood.getMaxVolume() != null) {
			String resourceName = resourcesFood.getResourceName();
			Long resourceCount = resourcesFood.getResourceCount();
			Long resourceMaxVolume = resourcesFood.getMaxVolume();
			
			if (sells.containsKey(resourceName)) {
				Double rate = sells.get(resourceName);
				if ((double)resourceCount / (double)resourceMaxVolume >= rate)
					total++;				
			}			
		}
		
		if (resourcesIron != null && resourcesIron.getResourceName() != null && resourcesIron.getResourceCount() != null && resourcesIron.getMaxVolume() != null) {
			String resourceName = resourcesIron.getResourceName();
			Long resourceCount = resourcesIron.getResourceCount();
			Long resourceMaxVolume = resourcesIron.getMaxVolume();
			
			if (sells.containsKey(resourceName)) {
				Double rate = sells.get(resourceName);
				if ((double)resourceCount / (double)resourceMaxVolume >= rate)
					total++;				
			}			
		}
		
		if (resourcesMarble != null && resourcesMarble.getResourceName() != null && resourcesMarble.getResourceCount() != null && resourcesMarble.getMaxVolume() != null) {
			String resourceName = resourcesMarble.getResourceName();
			Long resourceCount = resourcesMarble.getResourceCount();
			Long resourceMaxVolume = resourcesMarble.getMaxVolume();
			
			if (sells.containsKey(resourceName)) {
				Double rate = sells.get(resourceName);
				if ((double)resourceCount / (double)resourceMaxVolume >= rate)
					total++;				
			}			
		}
		
		if (resourcesGold != null && resourcesGold.getResourceName() != null && resourcesGold.getResourceCount() != null && resourcesGold.getMaxVolume() != null) {
			String resourceName = resourcesGold.getResourceName();
			Long resourceCount = resourcesGold.getResourceCount();
			Long resourceMaxVolume = resourcesGold.getMaxVolume();
			
			if (sells.containsKey(resourceName)) {
				Double rate = sells.get(resourceName);
				if ((double)resourceCount / (double)resourceMaxVolume >= rate)
					total++;				
			}			
		}
		
		if (total == 5)
			return;
		
		total = 0;
		List<BattleQueue> battleQueues = town.getBattleQueues();
		if (battleQueues != null)
			total = battleQueues.size();
				
		Hero hero = null;
		hero = town.getHero();
		if (hero != null) {
			if (hero.getId() == null || hero.getEnergy() == null)
				hero = null;
			else {
				if (hero.getEnergy() == 0 && !useEquipment(town, config))
					hero = null;
			}				
		}
		
		int error = 0;
		while (total < 2 && error < 5) {
			long level = getAttackLevel(canAttack(town));
			if (level == 0)
				return;
							
			IslandVillage islandVillage = getIsland(town, config, configTown, level);
			if (islandVillage == null)
				return;
			
			long count = getAttackCount(islandVillage.getLevel());
			HashMap<String, Long> soldiers = getAttackSoldier(town, count);
			if (soldiers == null)
				return;
									
			if (m_RequestArmy.request(host, clientv, cookie, townId, islandVillage.getId(), townId, soldiers, hero)) {
				m_Village.add(islandVillage.getId());
				decreaseSoldiers(town, soldiers);
				total++;					
			} else
				error++;						
		}		
	}
	
	private boolean sells(Town town, Config config, HashMap<String, Double> sells, Resources resources, Long leftCapacity) {
		String host = config.getHost();
		String clientv = config.getClientv();
		String cookie = config.getCookie();		
		Long townId = town.getId();		
		String name = resources.getResourceName();
		Long count = resources.getResourceCount();
		Long maxVolume = resources.getMaxVolume();
		
		if (name == null || count == null || maxVolume == null)
			return false;
		
		if (!sells.containsKey(name))
			return false;
		
		double rate = sells.get(name);
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
										
					return m_RequestDeals.request(host, clientv, cookie, townId, name, sellerPrice, sellerCount);
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
		if (town.getId() == null)
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
			
	private List<Resources> getMaxResources(Town town, HashMap<String, Double> sells) {
		if (sells == null)
			return null;
		
		Resources resourcesWood = town.getResourcesWood();
		Resources resourcesFood = town.getResourcesFood();
		Resources resourcesGold = town.getResourcesGold();
		Resources resourcesMarble = town.getResourcesMarble();
		Resources resourcesIron = town.getResourcesIron();		
						
		TreeMap<Double, List<Resources>> sorts = new TreeMap<Double, List<Resources>>();
		if (resourcesWood != null && resourcesWood.getResourceName() != null && resourcesWood.getResourceCount() != null && resourcesWood.getMaxVolume() != null) {
			String resourceName = resourcesWood.getResourceName();
			Long resourceCount = resourcesWood.getResourceCount();		
			Long resourceMaxVolume = resourcesWood.getMaxVolume();
					
			if (sells.containsKey(resourceName)) {
				double rate = sells.get(resourceName);
				double percent = (double)resourceCount / (double)resourceMaxVolume;
				if (percent >= rate)
					addMaxResources(resourcesWood, sorts, percent);				
			}
		}
		
		if (resourcesFood != null && resourcesFood.getResourceName() != null && resourcesFood.getResourceCount() != null && resourcesFood.getMaxVolume() != null) {
			String resourceName = resourcesFood.getResourceName();
			Long resourceCount = resourcesFood.getResourceCount();		
			Long resourceMaxVolume = resourcesFood.getMaxVolume();
					
			if (sells.containsKey(resourceName)) {
				double rate = sells.get(resourceName);
				double percent = (double)resourceCount / (double)resourceMaxVolume;
				if (percent >= rate)
					addMaxResources(resourcesFood, sorts, percent);				
			}
		}		
		
		if (resourcesGold != null && resourcesGold.getResourceName() != null && resourcesGold.getResourceCount() != null && resourcesGold.getMaxVolume() != null) {
			String resourceName = resourcesGold.getResourceName();
			Long resourceCount = resourcesGold.getResourceCount();		
			Long resourceMaxVolume = resourcesGold.getMaxVolume();
					
			if (sells.containsKey(resourceName)) {
				double rate = sells.get(resourceName);
				double percent = (double)resourceCount / (double)resourceMaxVolume;
				if (percent >= rate)
					addMaxResources(resourcesGold, sorts, percent);				
			}
		}			
			
	
		if (resourcesMarble != null && resourcesMarble.getResourceName() != null && resourcesMarble.getResourceCount() != null && resourcesMarble.getMaxVolume() != null) {
			String resourceName = resourcesMarble.getResourceName();
			Long resourceCount = resourcesMarble.getResourceCount();		
			Long resourceMaxVolume = resourcesMarble.getMaxVolume();
					
			if (sells.containsKey(resourceName)) {
				double rate = sells.get(resourceName);
				double percent = (double)resourceCount / (double)resourceMaxVolume;
				if (percent >= rate)
					addMaxResources(resourcesMarble, sorts, percent);				
			}
		}		
	
		if (resourcesIron != null && resourcesIron.getResourceName() != null && resourcesIron.getResourceCount() != null && resourcesIron.getMaxVolume() != null) {
			String resourceName = resourcesIron.getResourceName();
			Long resourceCount = resourcesIron.getResourceCount();		
			Long resourceMaxVolume = resourcesIron.getMaxVolume();
					
			if (sells.containsKey(resourceName)) {
				double rate = sells.get(resourceName);
				double percent = (double)resourceCount / (double)resourceMaxVolume;
				if (percent >= rate)
					addMaxResources(resourcesIron, sorts, percent);				
			}
		}		
		
		if (sorts.size() == 0)
			return null;
		
		List<Resources> list = new Vector<Resources>();
		
		for (Entry<Double, List<Resources>> entry : sorts.entrySet()) {
			List<Resources> resources = entry.getValue();
			list.addAll(resources);
		}
		
		Collections.reverse(list);
		return list;
	}
	
	private void addMaxResources(Resources resources, TreeMap<Double, List<Resources>> sorts, double percent) {		
		if (sorts.containsKey(percent)) {
			List<Resources> sort = sorts.get(percent);
			sort.add(resources);
		} else {
			List<Resources> sort = new Vector<Resources>();
			sort.add(resources);
			sorts.put(percent, sort);
		}
	}
	
	private List<Town> getOtherTown(Config config, List<OtherTown> otherTowns) {		
		String host = config.getHost();
		String clientv = config.getClientv();
		String cookie = config.getCookie();
		
		if (otherTowns == null)
			return null;
		
		List<Town> towns = new Vector<Town>();		
		for (OtherTown otherTown : otherTowns) {
			if (otherTown.getId() == null)
				continue;
			
			TownInfo townInfo = m_RequestTowns.request(host, clientv, cookie, otherTown.getId());
			if (townInfo == null)
				continue;
			
			Town town = townInfo.getTown();
			if (town.getId() == null)
				continue;
			
			towns.add(townInfo.getTown());
		}
		
		if (towns.size() == 0)
			return null;
		else
			return towns;
	}
	
	private ConfigTown getConfigTown(Long townId, Config config) {
		List<ConfigTown> configTowns = config.getConfigTowns();
		if (configTowns != null) {
			for (ConfigTown configTown : configTowns) {
				if (configTown.getId() != null && configTown.getId().equals(townId))
					return configTown;
			}
		}
		
		return null;
	}
	
	private Town getMinTown(Config config, List<Town> towns, Resources maxResources) {		
		Town minTown = null;
		double max = 0D;
		for (Town town : towns) {
			Long townId = town.getId();			
			ConfigTown configTown = getConfigTown(townId, config);
			if (configTown == null)
				continue;
			
			HashMap<String, Double> sells = configTown.getSells();
			if (sells == null)
				continue;
			
			String resourceName = maxResources.getResourceName();
			if (!sells.containsKey(resourceName))
				continue;
			
			Double rate = sells.get(resourceName);
			if (rate == 0D)
				continue;
			
			if (resourceName.equals("wood")) {
				Resources resourcesWood = town.getResourcesWood();
				if (resourcesWood == null || resourcesWood.getResourceCount() == null || resourcesWood.getMaxVolume() == null)
					continue;
				
				Long resourceCount = resourcesWood.getResourceCount();
				Long resourceMaxVolume = resourcesWood.getMaxVolume();
				
				double percent = (double)resourceCount / (double)resourceMaxVolume;
				if (percent < rate) {
					percent = rate - percent;
					if (minTown == null || percent > max) {
						minTown = town;
						max = percent;						
					}
				}
				
				continue;
			}
			
			if (resourceName.equals("food")) {
				Resources resourcesFood = town.getResourcesFood();
				if (resourcesFood == null || resourcesFood.getResourceCount() == null || resourcesFood.getMaxVolume() == null)
					continue;
				
				Long resourceCount = resourcesFood.getResourceCount();
				Long resourceMaxVolume = resourcesFood.getMaxVolume();
				
				double percent = (double)resourceCount / (double)resourceMaxVolume;
				if (percent < rate) {
					percent = rate - percent;
					if (minTown == null || percent > max) {
						minTown = town;
						max = percent;						
					}
				}
				
				continue;
			}
			
			if (resourceName.equals("gold")) {
				Resources resourcesGold = town.getResourcesGold();
				if (resourcesGold == null || resourcesGold.getResourceCount() == null || resourcesGold.getMaxVolume() == null)
					continue;
				
				Long resourceCount = resourcesGold.getResourceCount();
				Long resourceMaxVolume = resourcesGold.getMaxVolume();
				
				double percent = (double)resourceCount / (double)resourceMaxVolume;
				if (percent < rate) {
					percent = rate - percent;
					if (minTown == null || percent > max) {
						minTown = town;
						max = percent;						
					}
				}
				
				continue;
			}
			
			if (resourceName.equals("iron")) {
				Resources resourcesIron = town.getResourcesIron();
				if (resourcesIron == null || resourcesIron.getResourceCount() == null || resourcesIron.getMaxVolume() == null)
					continue;
				
				Long resourceCount = resourcesIron.getResourceCount();
				Long resourceMaxVolume = resourcesIron.getMaxVolume();
				
				double percent = (double)resourceCount / (double)resourceMaxVolume;
				if (percent < rate) {
					percent = rate - percent;
					if (minTown == null || percent > max) {
						minTown = town;
						max = percent;						
					}
				}
				
				continue;
			}
			
			if (resourceName.equals("marble")) {
				Resources resourcesMarble = town.getResourcesMarble();
				if (resourcesMarble == null || resourcesMarble.getResourceCount() == null || resourcesMarble.getMaxVolume() == null)
					continue;
				
				Long resourceCount = resourcesMarble.getResourceCount();
				Long resourceMaxVolume = resourcesMarble.getMaxVolume();
				
				double percent = (double)resourceCount / (double)resourceMaxVolume;
				if (percent < rate) {
					percent = rate - percent;
					if (minTown == null || percent > max) {
						minTown = town;
						max = percent;						
					}
				}
				
				continue;
			}
		}
		
		return minTown;
	}
	
	private void transport(Town town, Config config, ConfigTown configTown) {
		Long townId = town.getId();
		String host = config.getHost();
		String clientv = config.getClientv();
		String cookie = config.getCookie();
		BuildingPort buildingPort = town.getBuildingPort();
		if (buildingPort == null || buildingPort.getFleetQuota() == null)
			return;
				
		Long fleetQuota = buildingPort.getFleetQuota();				
		int total = 0;
		List<TransportQueue> transportQueues = town.getTransportQueues();
		if (transportQueues != null)
			total = transportQueues.size();
		
		if (total > 1)
			return;
		
		List<Town> towns = getOtherTown(config, town.getOtherTowns());
		if (towns == null)
			return;
				
		int error = 0;
		while (total < 2 && error < 3) {
			List<Resources> maxResources = getMaxResources(town, configTown.getSells());
			if (maxResources == null)
				break;
			
			for (Resources maxResource : maxResources) {
				Town minTown = getMinTown(config, towns, maxResource);
				if (minTown == null)
					continue;
				
				Long count = fleetQuota;
				if (fleetQuota > maxResource.getResourceCount())
					count = maxResource.getResourceCount();
				
				HashMap<String, Long> resources = new HashMap<String, Long>();
				resources.put(maxResource.getResourceName(), count);
				
				TransportTown transportTown = m_RequestTransport.request(host, clientv, cookie, townId, minTown.getId(), resources);
				if (transportTown == null) {
					error++;
					continue;				
				}
				
				total++;
				Resources resourcesWood = town.getResourcesWood();
				Resources resourcesFood = town.getResourcesFood();
				Resources resourcesGold = town.getResourcesGold();				
				Resources resourcesIron = town.getResourcesIron();
				Resources resourcesMarble = town.getResourcesMarble();
				
				if (resourcesWood != null)
					town.setResourcesWood(resourcesWood);
				
				if (resourcesFood != null)
					town.setResourcesFood(resourcesFood);
				
				if (resourcesGold != null)
					town.setResourcesGold(resourcesGold);
				
				if (resourcesIron != null)
					town.setResourcesIron(resourcesIron);
				
				if (resourcesMarble != null)
					town.setResourcesMarble(resourcesMarble);								
			}
		}		
	}
}
