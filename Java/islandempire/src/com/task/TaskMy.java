package com.task;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;
import java.util.Map.Entry;

import com.buildings.Building;
import com.buildings.BuildingCellar;
import com.buildings.BuildingCommand;
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
import com.hero.Enhance;
import com.hero.Equipment;
import com.hero.FreeSafeResources;
import com.hero.Hero;
import com.hero.NeedResources;
import com.island.IslandBuilding;
import com.island.IslandTown;
import com.island.IslandVillage;
import com.island.WorldMap;
import com.queue.BattleQueue;
import com.queue.BuildingQueue;
import com.queue.LinesEvent;
import com.queue.TransportQueue;
import com.request.RequestArmy;
import com.request.RequestBuildings;
import com.request.RequestCallBack;
import com.request.RequestDeals;
import com.request.RequestEquipment;
import com.request.RequestEvent;
import com.request.RequestIsland;
import com.request.RequestMessage;
import com.request.RequestRanks;
import com.request.RequestRecruit;
import com.request.RequestRewards;
import com.request.RequestShipBoards;
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
import com.util.Logs;
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
	private final RequestRanks m_RequestRanks = new RequestRanks();
	private final RequestRewards m_RequestRewards = new RequestRewards();
	private final RequestCallBack m_RequestCallBack = new RequestCallBack();
	private final RequestShipBoards m_RequestShipBoards = new RequestShipBoards();
	private final List<Long> m_Village = new Vector<Long>();
	private Config m_ConfigNew = null;
	private Date m_Rewards = null;

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
				m_Config.setEquipmentMax(m_ConfigNew.getEquipmentMax());
				m_Config.setEquipmentTowns(m_ConfigNew.getEquipmentTowns());
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
		List<Long> townIds = new Vector<Long>();
		String username = "";
		
		setRewards(m_Config);
		setBattles(m_Config, configTowns);
		String ranks = getRanks(m_Config);
						
		for (ConfigTown configTown : configTowns) {
			Boolean autoUpgrade = configTown.getAutoUpgrade();
			Long townId = configTown.getTownId();
			townIds.add(townId);
			TownInfo townInfo = m_RequestTowns.request(host, clientv, cookie, townId);
						
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
			equipment(townInfo, m_Config, townId);
			townInfos.add(townInfo);
		}
		
		towns.setTownInfos(townInfos);
		towns.setRanks(ranks);
		
		if (username.length() > 0) {
			String message = m_RequestMessage.request(host, clientv, cookie, username, 0L);
			if (message != null) {
				towns.setMessage(message);
			}
		}
		
		if (townIds.size() > 0) {
			Collections.shuffle(townIds);
			Long townId = townIds.get(0);
			setEquipment(towns, m_Config, townId);
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
	
	private void setRewards(Config config) {		
		if (m_Rewards != null) {
			Date now = new Date();
			if (now.getTime() - m_Rewards.getTime() < 24 * 3600 * 1000)
				return;			
		}
		
		String host = config.getHost();
		Long userId = config.getUserId();
		String clientv = config.getClientv();
		String cookie = config.getCookie();
				
		m_RequestRewards.request(host, clientv, cookie, userId);
		m_Rewards = new Date();
	}
	
	private String getRanks(Config config) {
		String host = config.getHost();
		Long userId = config.getUserId();
		String clientv = config.getClientv();
		String cookie = config.getCookie();
		
		return m_RequestRanks.request(host, clientv, cookie, "top_users", userId);
	}
	
	private String getBattles(Config config, Long x, Long y, Long fromTownId) {
		String host = config.getHost();
		Long userId = config.getUserId();
		String clientv = config.getClientv();
		String cookie = config.getCookie();
		
		String yy = String.valueOf(y);
		if (yy.length() == 1)
			yy = "00" + yy;
		
		if (yy.length() == 2)
			yy = "0" + yy;
			
		String xy = String.valueOf(x) + yy;
		
		try {
			Long postion = Long.parseLong(xy);
			List<IslandBuilding> buildings = m_RequestIsland.request(host, clientv, cookie, postion, userId);
			if (buildings != null) {
				for (IslandBuilding building : buildings) {
					if (building.getId() != null && building.getType() != null) {
						Long townId = building.getId();
						String type = building.getType();
						if (type.equals("Town")) {
							IslandTown town = (IslandTown)building;
							if (townId.equals(fromTownId))
								return town.getOwnerName();							
						}
					}						
				}
			}
			
			return null;
		} catch (Exception e) {
			return null;
		}		
	}
	
	private void setBattles(Config config, List<ConfigTown> configTowns) {
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
			
			if (battleQueue.getId() != null && battleQueue.getFromTownId() != null && battleQueue.getFromX() != null && battleQueue.getFromY() != null) {
				Long queueId = battleQueue.getId();
				Long fromTownId = battleQueue.getFromTownId();
				Long x = battleQueue.getFromX();
				Long y = battleQueue.getFromY();
				for (ConfigTown configTown : configTowns) {
					Long townId = configTown.getTownId();
					if (toTownId.equals(townId)) {
						String username = getBattles(config, x, y, fromTownId);
						if (username != null) {
							Logs logs = Logs.getInstance();
							StringBuilder sb = new StringBuilder();
							sb.append("callback: ");
							sb.append("x=");
							sb.append(x);
							sb.append(" ");
							sb.append("y=");
							sb.append(y);
							sb.append(" ");
							sb.append(queueId);
							sb.append(" ");
							sb.append(fromTownId);
							sb.append(" ");
							sb.append(username);
							sb.append(" ");
							sb.append(toTownId);
							if (m_RequestCallBack.request(host, clientv, cookie, username, queueId))
								sb.append(" success");
							else
								sb.append(" failed");
							
							logs.writeLogs(sb.toString());
						}
						
						break;
					}
				}
			}
						
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
			
			return false;
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
		Soldier soldierIronclad = town.getSoldierIronclad();
		Soldier soldierPegasus = town.getSoldierPegasus();
		Soldier soldierBerserker = town.getSoldierBerserker();
		
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
			
			if (name.equals("ironclad")) {
				if (soldierIronclad != null && soldierIronclad.getCount() != null)
					soldierIronclad.setCount(soldierIronclad.getCount() - count);
				
				continue;
			}
			
			if (name.equals("pegasus")) {
				if (soldierPegasus != null && soldierPegasus.getCount() != null)
					soldierPegasus.setCount(soldierPegasus.getCount() - count);
				
				continue;
			}
			
			if (name.equals("berserker")) {
				if (soldierBerserker != null && soldierBerserker.getCount() != null)
					soldierBerserker.setCount(soldierBerserker.getCount() - count);
				
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
									
		boolean citywall = false;
		HashMap<Long, List<Long>> buildings = new HashMap<Long, List<Long>>();
		
		if (town.getBuildingBarrack() != null) {
			BuildingSoldier buildingBarrack = town.getBuildingBarrack();
			
			if (buildingBarrack.getBuildingType() != null && buildingBarrack.getId() != null && buildingBarrack.getLevel() != null && buildingBarrack.getMaxLevel() != null && buildingBarrack.getLevel() < buildingBarrack.getMaxLevel() && buildingBarrack.getLevel() < level && buildingBarrack.getStatus() != null && !buildingBarrack.getStatus().equals("upgrading")) {				
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
						if (buildingLine.getId() != null && buildingLine.getLevel() != null && buildingResource.getMaxLevel() != null && buildingLine.getLevel() < buildingResource.getMaxLevel() && buildingLine.getLevel() < level && buildingLine.getStatus() != null && !buildingLine.getStatus().equals("upgrading")) {
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
					
					if (sorts.size() > 0) {
						List<Long> buildingIds = new Vector<Long>();
						for (Entry<Long, List<Long>> entry : sorts.entrySet())
							buildingIds.addAll(entry.getValue());
						
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
						if (buildingLine.getId() != null && buildingLine.getLevel() != null && buildingResource.getMaxLevel() != null && buildingLine.getLevel() < buildingResource.getMaxLevel() && buildingLine.getLevel() < level && buildingLine.getStatus() != null && !buildingLine.getStatus().equals("upgrading")) {
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
					
					if (sorts.size() > 0) {
						List<Long> buildingIds = new Vector<Long>();
						for (Entry<Long, List<Long>> entry : sorts.entrySet())
							buildingIds.addAll(entry.getValue());
						
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
						if (buildingLine.getId() != null && buildingLine.getLevel() != null && buildingResource.getMaxLevel() != null && buildingLine.getLevel() < buildingResource.getMaxLevel() && buildingLine.getLevel() < level && buildingLine.getStatus() != null && !buildingLine.getStatus().equals("upgrading")) {
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
					
					if (sorts.size() > 0) {
						List<Long> buildingIds = new Vector<Long>();
						for (Entry<Long, List<Long>> entry : sorts.entrySet())
							buildingIds.addAll(entry.getValue());
						
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
						if (buildingLine.getId() != null && buildingLine.getLevel() != null && buildingResource.getMaxLevel() != null && buildingLine.getLevel() < buildingResource.getMaxLevel() && buildingLine.getLevel() < level && buildingLine.getStatus() != null && !buildingLine.getStatus().equals("upgrading")) {
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
					
					if (sorts.size() > 0) {
						List<Long> buildingIds = new Vector<Long>();
						for (Entry<Long, List<Long>> entry : sorts.entrySet())
							buildingIds.addAll(entry.getValue());
						
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
						if (buildingLine.getId() != null && buildingLine.getLevel() != null && buildingResource.getMaxLevel() != null && buildingLine.getLevel() < buildingResource.getMaxLevel() && buildingLine.getLevel() < level && buildingLine.getStatus() != null && !buildingLine.getStatus().equals("upgrading")) {
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
					
					if (sorts.size() > 0) {
						List<Long> buildingIds = new Vector<Long>();
						for (Entry<Long, List<Long>> entry : sorts.entrySet())
							buildingIds.addAll(entry.getValue());
						
						buildings.put(buildingResource.getBuildingType(), buildingIds);						
					}
				}
			}
		}
		
		if (town.getBuildingStore() != null) {
			BuildingStore buildingStore = town.getBuildingStore();
			if (buildingStore.getBuildingType() != null && buildingStore.getId() != null && buildingStore.getLevel() != null && buildingStore.getMaxLevel() != null && buildingStore.getLevel() < buildingStore.getMaxLevel() && buildingStore.getLevel() < level && buildingStore.getStatus() != null && !buildingStore.getStatus().equals("upgrading")) {
				List<Long> buildingIds = new Vector<Long>();
				buildingIds.add(buildingStore.getId());
				buildings.put(buildingStore.getBuildingType(), buildingIds);			
			}
		}
		
		if (town.getBuildingPort() != null) {
			BuildingPort buildingPort = town.getBuildingPort();
			if (buildingPort.getBuildingType() != null && buildingPort.getId() != null && buildingPort.getLevel() != null && buildingPort.getMaxLevel() != null && buildingPort.getLevel() < buildingPort.getMaxLevel() && buildingPort.getLevel() < level && buildingPort.getStatus() != null && !buildingPort.getStatus().equals("upgrading")) {
				List<Long> buildingIds = new Vector<Long>();
				buildingIds.add(buildingPort.getId());
				buildings.put(buildingPort.getBuildingType(), buildingIds);			
			}
		}
		
		if (town.getBuildingMarket() != null) {
			BuildingMarket buildingMarket = town.getBuildingMarket();
			if (buildingMarket.getBuildingType() != null && buildingMarket.getId() != null && buildingMarket.getLevel() != null && buildingMarket.getMaxLevel() != null && buildingMarket.getLevel() < buildingMarket.getMaxLevel() && buildingMarket.getLevel() < level && buildingMarket.getStatus() != null && !buildingMarket.getStatus().equals("upgrading")) {
				List<Long> buildingIds = new Vector<Long>();
				buildingIds.add(buildingMarket.getId());
				buildings.put(buildingMarket.getBuildingType(), buildingIds);			
			}
		}
		
		if (town.getBuildingHall() != null) {
			Building buildingHall = town.getBuildingHall();
			if (buildingHall.getBuildingType() != null && buildingHall.getId() != null && buildingHall.getLevel() != null && buildingHall.getMaxLevel() != null && buildingHall.getLevel() < buildingHall.getMaxLevel() && buildingHall.getStatus() != null && !buildingHall.getStatus().equals("upgrading")) {
				List<Long> buildingIds = new Vector<Long>();
				buildingIds.add(buildingHall.getId());
				buildings.put(buildingHall.getBuildingType(), buildingIds);			
			}
		}
		
		if (town.getBuildingWall() != null) {
			BuildingWall buildingWall = town.getBuildingWall();			
			if (buildingWall.getBuildingType() != null) {				
				if (buildingWall.getId() != null && buildingWall.getLevel() != null && buildingWall.getMaxLevel() != null && buildingWall.getLevel() < buildingWall.getMaxLevel() && buildingWall.getLevel() < level && buildingWall.getStatus() != null) {
					if (!buildingWall.getStatus().equals("upgrading")) {
						List<Long> buildingIds = new Vector<Long>();
						buildingIds.add(buildingWall.getId());					
						buildings.put(buildingWall.getBuildingType(), buildingIds);
					} else
						citywall = true;					
				}
			
				List<BuildingTower> buildingTowers = buildingWall.getBuildingTower();
				if (buildingTowers != null) {
					TreeMap<Long, HashMap<Long, Long>> sorts = new TreeMap<Long, HashMap<Long, Long>>();
					for (BuildingTower buildingTower : buildingTowers) {
						if (buildingTower.getId() != null && buildingTower.getLevel() != null && buildingTower.getType() != null && buildingWall.getMaxLevel() != null && buildingTower.getLevel() < buildingWall.getMaxLevel() && buildingTower.getLevel() < level && buildingTower.getStatus() != null && !buildingTower.getStatus().equals("upgrading")) {
							if (sorts.containsKey(buildingTower.getLevel())) {
								HashMap<Long, Long> buildingIds = sorts.get(buildingTower.getLevel());
								buildingIds.put(buildingTower.getId(), buildingTower.getType());
							} else {
								HashMap<Long, Long> buildingIds = new HashMap<Long, Long>();
								buildingIds.put(buildingTower.getId(), buildingTower.getType());
								sorts.put(buildingTower.getLevel(), buildingIds);
							}
						}						
					}
					
					if (sorts.size() > 0) {						
						for (Entry<Long, HashMap<Long, Long>> entry : sorts.entrySet()) {
							HashMap<Long, Long> map = entry.getValue();
							for (Entry<Long, Long> values : map.entrySet()) {
								Long key = values.getKey();
								Long value = values.getValue();
								
								if (buildings.containsKey(value)) {
									List<Long> list = buildings.get(value);
									list.add(key);
								} else {
									List<Long> list = new Vector<Long>();
									list.add(key);
									buildings.put(value, list);
								}
							}							
						}											
					}
				}								
			}		
		}
		
		if (town.getBuildingYard() != null) {
			BuildingSoldier buildingYard = town.getBuildingYard();
			if (buildingYard.getBuildingType() != null && buildingYard.getId() != null && buildingYard.getLevel() != null && buildingYard.getMaxLevel() != null && buildingYard.getLevel() < buildingYard.getMaxLevel() && buildingYard.getLevel() < level && buildingYard.getStatus() != null && !buildingYard.getStatus().equals("upgrading")) {
				List<Long> buildingIds = new Vector<Long>();
				buildingIds.add(buildingYard.getId());
				buildings.put(buildingYard.getBuildingType(), buildingIds);			
			}
		}
		
		if (town.getBuildingCellar() != null) {
			BuildingCellar buildingCellar = town.getBuildingCellar();
			if (buildingCellar.getBuildingType() != null && buildingCellar.getId() != null && buildingCellar.getLevel() != null && buildingCellar.getMaxLevel() != null && buildingCellar.getLevel() < buildingCellar.getMaxLevel() && buildingCellar.getLevel() < level && buildingCellar.getStatus() != null && !buildingCellar.getStatus().equals("upgrading")) {
				List<Long> buildingIds = new Vector<Long>();
				buildingIds.add(buildingCellar.getId());
				buildings.put(buildingCellar.getBuildingType(), buildingIds);			
			}
		}
		
		if (town.getBuildingCommand() != null) {
			BuildingCommand buildingCommand = town.getBuildingCommand();
			if (buildingCommand.getBuildingType() != null && buildingCommand.getId() != null && buildingCommand.getLevel() != null && buildingCommand.getMaxLevel() != null && buildingCommand.getLevel() < buildingCommand.getMaxLevel() && buildingCommand.getLevel() < level && buildingCommand.getStatus() != null && !buildingCommand.getStatus().equals("upgrading")) {
				List<Long> buildingIds = new Vector<Long>();
				buildingIds.add(buildingCommand.getId());
				buildings.put(buildingCommand.getBuildingType(), buildingIds);			
			}
		}
		
		if (buildings.size() > 0) {			
			for (Long priority : prioritys) {
				if (!buildings.containsKey(priority))
					continue;
				
				if (citywall && (priority == 16 || priority == 17))
					continue;
				
				List<Long> buildingIds = buildings.get(priority);				
				for (Long buildingId : buildingIds) {					
					HashMap<String, Long> resources = m_RequestUpgrade.request(host, clientv, cookie, buildingId);
					if (resources != null && checkResources(town, resources) && m_RequestBuildings.request(host, clientv, cookie, buildingId)) {
						decreaseResources(town, resources);
						if (++total > 1)
							return;
						
						if (priority == 12)
							citywall = true;
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
		Long userId = config.getUserId();
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
		
		soldier = town.getSoldierIronclad();
		if (soldier.getTrainingQueue() != null)
			total++;
		
		soldier = town.getSoldierPegasus();
		if (soldier.getTrainingQueue() != null)
			total++;
		
		soldier = town.getSoldierBerserker();
		if (soldier.getTrainingQueue() != null)
			total++;
		
		if (total > 1)
			return;
		
		long infantryCount = 0;
		long musketmanCount = 0;
		long catapultCount = 0;
		long frigateCount = 0;
		long destroyerCount = 0;
		long ironcladCount = 0;
		long pegasusCount = 0;
		long berserkerCount = 0;
		
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
				
				if (battleQueue.getIronclad() != null)
					ironcladCount += battleQueue.getIronclad();
				
				if (battleQueue.getPegasus() != null)
					pegasusCount += battleQueue.getPegasus();
				
				if (battleQueue.getBerserker() != null)
					berserkerCount += battleQueue.getBerserker();
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
		
		soldier = town.getSoldierIronclad();
		if (soldier != null && soldier.getTrainingQueue() == null)
			minSoldier = getMinSoldier(town, config, ironcladCount, soldier, minSoldier, recruits);
		
		soldier = town.getSoldierPegasus();
		if (soldier != null && soldier.getTrainingQueue() == null)
			minSoldier = getMinSoldier(town, config, pegasusCount, soldier, minSoldier, recruits);
		
		soldier = town.getSoldierBerserker();
		if (soldier != null && soldier.getTrainingQueue() == null)
			minSoldier = getMinSoldier(town, config, berserkerCount, soldier, minSoldier, recruits);
		
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
			List<Resources> resources = m_RequestRecruit.request(host, clientv, cookie, userId, townId, soldierName, count);
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
		Soldier ironclad = town.getSoldierIronclad();
		Soldier pegasus = town.getSoldierPegasus();
		Soldier berserker = town.getSoldierBerserker();
		
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
		
		if (ironclad != null && ironclad.getCount() != null)
			total += ironclad.getCount();
		
		if (pegasus != null && pegasus.getCount() != null)
			total += pegasus.getCount();
		
		if (berserker != null && berserker.getCount() != null)
			total += berserker.getCount();
		
		return total;
	}
	
	private long getAttackLevel(long total) {
		if (total < 400)
			return 0;
		else if (total < 1500)
			return 10;
		else if (total < 4000)
			return 20;
		else if (total < 7000)
			return 30;
		else 
			return 50;
	}
	
	private long getAttackCount(long level) {
		if (level <= 10)
			return 400;
		else if (level <= 20)
			return 1500;
		else if (level <= 30)
			return 4000;
		else if (level <= 40)
			return 7000;
		else 
			return 9000;
	}
	
	private HashMap<String, Long> getAttackSoldier(Town town, long count) {		
		long left = count;
		HashMap<String, Long> soldiers = new HashMap<String, Long>();
		HashMap<String, Long> more = new HashMap<String, Long>();
		
		left -= getAttackSoldier(count, town.getSoldierBerserker(), soldiers, more);
		left -= getAttackSoldier(count, town.getSoldierPegasus(), soldiers, more);
		left -= getAttackSoldier(count, town.getSoldierIronclad(), soldiers, more);
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
		
		String response = m_RequestEquipment.request(host, clientv, cookie, townId);
		if (response == null)
			return false;
		
		List<Equipment> equipments = Equipment.parse(response);
		if (equipments == null)
			return false;
		
		for (Equipment equipment : equipments) {
			if (equipment.getEquipmentId() == null || equipment.getType() == null)
				continue;
			
			if (equipment.getType() == 9) {
				Long equipmentId = equipment.getEquipmentId();						
				if (m_RequestEquipment.request(host, clientv, cookie, equipmentId, "use", "delete", townId))
					return true;
			}			
		}
		
		return false;
	}
		
	private void attack(Town town, Config config, ConfigTown configTown) {
		String host = config.getHost();
		String clientv = config.getClientv();
		String cookie = config.getCookie();
		Long userId = config.getUserId();
		Long townId = town.getId();
		Boolean autoAttack = configTown.getAutoAttack();
				
		if (!autoAttack)
			return;
		
		if (town.getIslandNumber() == null)
			return;
		
		Long islandNumber = town.getIslandNumber();
		int total = 0;
						
		List<BattleQueue> battleQueues = town.getBattleQueues();
		if (battleQueues != null)
			total = battleQueues.size();
				
		Hero hero = null;
		hero = town.getHero();
		if (hero != null) {
			if (hero.getId() == null || hero.getEnergy() == null || hero.getLevel() == null)
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
			
			Hero h = null;
			if (hero != null && islandVillage.getLevel() >= hero.getLevel() - 6)
				h = hero;
						
			List<IslandBuilding> islandBuildings = m_RequestIsland.request(host, clientv, cookie, islandNumber, userId);
			if (islandBuildings != null) {
				boolean found = false;
				for (IslandBuilding islandBuilding : islandBuildings) {
					Long id = islandBuilding.getId();
					if (id == null)
						continue;
					
					if (id.equals(townId)) {
						found = true;
						break;
					}
				}
				
				if (!found || !m_RequestShipBoards.request(host, clientv, cookie, userId, townId, soldiers)) {
					error++;
					continue;
				}
			}
															
			if (m_RequestArmy.request(host, clientv, cookie, townId, islandVillage.getId(), townId, soldiers, h)) {
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
										
					List<Resources> list = m_RequestDeals.request(host, clientv, cookie, townId, name, sellerPrice, sellerCount);
					if (list == null)
						return false;
					else {
						setResources(town, list);
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	private void sells(Town town, Config config, ConfigTown configTown) {
		if (!configTown.getSell() || configTown.getSells() == null)
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
		
		Resources resourcesIron = town.getResourcesIron();
		if (resourcesIron != null && sells(town, config, configTown.getSells(), resourcesIron, leftCapacity))
			return;
		
		Resources resourcesMarble = town.getResourcesMarble();
		if (resourcesMarble != null && sells(town, config, configTown.getSells(), resourcesMarble, leftCapacity))
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
		
		HashMap<String, Double> buys = configTown.getBuys();
		if (!configTown.getBuy() || buys == null)
			return;	
		
		Resources resourcesGold = town.getResourcesGold();
		if (resourcesGold == null || resourcesGold.getResourceCount() == null || resourcesGold.getMaxVolume() == null)
			return;
		
		Long goldCount = resourcesGold.getResourceCount();		
		Long goldMaxVolume = resourcesGold.getMaxVolume();
		
		HashMap<String, Double> sells = configTown.getSells();
		if (!configTown.getSell() || sells == null)
			return;
		
		if (!sells.containsKey("gold"))
			return;
		
		double goldRate = sells.get("gold");
		if (goldRate <= 0D)
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
				if (configTown.getTownId() != null && configTown.getTownId().equals(townId))
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
			if (!configTown.getSell() || sells == null)
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
				
		if (!configTown.getSell())
			return;
		
		List<Resources> maxResources = getMaxResources(town, configTown.getSells());
		if (maxResources == null)
			return;
		
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
			if (transportTown == null)				
				continue;				
			
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
			
			total++;
			if (total > 1)
				break;
		}	
	}
	
	private void equipment(TownInfo townInfo, Config config, Long townId) {
		String host = config.getHost();
		String clientv = config.getClientv();
		String cookie = config.getCookie();
				
		String response = m_RequestEquipment.request(host, clientv, cookie, townId);
		if (response == null)
			return;
		else
			townInfo.setHeroEquipment(response);
	}
		
	private void setEquipment(Towns towns, Config config, Long townId) {
		String host = config.getHost();
		String clientv = config.getClientv();
		String cookie = config.getCookie();
		Long userId = config.getUserId();
		
		if (config.getEquipmentMax() == null)
			return;
								
		String response = m_RequestEquipment.request(host, clientv, cookie, townId);
		if (response == null)
			return;
				
		List<Equipment> equipments = Equipment.parse(response);
		if (equipments == null)
			return;
				
		List<Long> equipmentTowns = config.getEquipmentTowns();
		if (equipmentTowns == null || equipmentTowns.size() == 0)
			return;
				
		Long equipmentMax = config.getEquipmentMax();
		if (equipmentMax <= 0)
			return;
		
		List<Equipment> eqs = new Vector<Equipment>();
		List<Equipment> mins = new Vector<Equipment>();
		Equipment eq = null;
		Long total = 0L, lv = -1L;
						
		for (Equipment equipment : equipments) {
			if (equipment.getEquipmentId() == null || equipment.getType() == null || equipment.getLevel() == null || equipment.getEnhanceLevel() == null)
				continue;
									
			total++;
			Long type = equipment.getType();
			if (type > -1 && type < 8) {
				if (lv < 0) {
					mins.add(equipment);
					lv = equipment.getLevel();
				} else {
					if (lv > equipment.getLevel()) {
						mins.clear();
						mins.add(equipment);
						lv = equipment.getLevel();
					} else {
						if (lv.equals(equipment.getLevel()))
							mins.add(equipment);
					}
				}
			}
			
			Enhance enhance = equipment.getEnhance();
			if (enhance == null)
				continue;
			
			NeedResources needResources = enhance.getNeedResources();			
			if (needResources == null || needResources.getAll() == null)
				continue;
			
			FreeSafeResources freeSafeResources = enhance.getFreeSafeResources();
			if (freeSafeResources == null || freeSafeResources.getAll() == null)
				continue;
			
			Long level = equipment.getLevel();
			if (level < equipmentMax)
				continue;
			
			Long enhanceLevel = equipment.getEnhanceLevel();
			if (enhanceLevel >= 15)
				continue;						
									
			if (type > -1 && type < 8)
				eqs.add(equipment);		
		}
						
		for (Equipment equipment : eqs) {
			Long enhanceLevel = equipment.getEnhanceLevel();
			if (eq == null || enhanceLevel < eq.getEnhanceLevel())
				eq = equipment;
		}
		
		if (eq == null)
			return;
		
		Long all = 0L;
		Long safe = 0L;
		Enhance enhance = eq.getEnhance();
		
		if (eq.getEnhanceLevel() > 4) {						
			FreeSafeResources freeSafeResources = enhance.getFreeSafeResources();
			all = freeSafeResources.getAll();
			safe = 2L;
		} else {
			NeedResources needResources = enhance.getNeedResources();
			all = needResources.getAll();
		}
		
		List<TownInfo> townInfos = towns.getTownInfos();
		if (townInfos == null)
			return;
		
		TownInfo tinfo = null, minfo = null;
		Long max = 0L;
		
		for (TownInfo townInfo : townInfos) {
			Long id = townInfo.getTownId();
			if (id == null)
				continue;
			
			Town town = townInfo.getTown();
			if (town == null)
				continue;
			
			Resources resourcesWood = town.getResourcesWood();
			Resources resourcesFood = town.getResourcesFood();
			Resources resourcesIron = town.getResourcesIron();
			Resources resourcesMarble = town.getResourcesMarble();
			Resources resourcesGold = town.getResourcesGold();
			if (resourcesGold != null && resourcesGold.getResourceCount() != null) {
				if (minfo == null || minfo.getTown().getResourcesGold().getResourceCount() > resourcesGold.getResourceCount())
					minfo = townInfo;
			}
			
			if (equipmentTowns.indexOf(id) == -1)
				continue;			
			
			if (resourcesWood == null || resourcesFood == null || resourcesMarble == null || resourcesIron == null || resourcesGold == null)
				continue;
			
			Long woodCount = resourcesWood.getResourceCount();
			Long foodCount = resourcesFood.getResourceCount();
			Long marbleCount = resourcesMarble.getResourceCount();
			Long ironCount = resourcesIron.getResourceCount();
			Long goldCount = resourcesGold.getResourceCount();
			
			if (woodCount == null || foodCount == null || marbleCount == null || ironCount == null || goldCount == null)
				continue;
			
			if (woodCount < all || foodCount < all || marbleCount < all || ironCount < all || goldCount < all)
				continue;
			
			Long count = woodCount + foodCount + marbleCount + ironCount + goldCount;
			if (tinfo == null || max < count) {
				tinfo = townInfo;
				max = count;
			}
		}
		
		if (tinfo != null) {
			List<Resources> resources = m_RequestEquipment.request(host, clientv, cookie, eq.getEquipmentId(), safe, "enhance", 0L, userId, "put", tinfo.getTownId());
			if (resources != null) {
				Town town = tinfo.getTown();
				setResources(town, resources);
			}				
		}
		
		if (total > 50 && minfo != null) {
			Equipment min = null;			
			for (Equipment equipment : mins) {				
				if (min == null || min.getEnhanceLevel() > equipment.getEnhanceLevel())
					min = equipment;
			}
			
			if (min != null)
				m_RequestEquipment.request(host, clientv, cookie, min.getEquipmentId(), "sold_to_npc", "delete", minfo.getTownId());
		}
	}
}
