package com.task;

import java.util.HashMap;
import java.util.List;
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
import com.entity.TownInfo;
import com.entity.Towns;
import com.queue.BuildingQueue;
import com.request.RequestBuildings;
import com.request.RequestMessage;
import com.request.RequestTowns;
import com.request.RequestUpgrade;
import com.towns.Resources;
import com.towns.Town;
import com.util.Numeric;

public class TaskMy extends TaskBase {
	private Config m_Config;
	private final RequestTowns m_RequestTowns = new RequestTowns();
	private final RequestMessage m_RequestMessage = new RequestMessage();
	private final RequestUpgrade m_RequestUpgrade = new RequestUpgrade();
	private final RequestBuildings m_RequestBuildings = new RequestBuildings();
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
		}
		
		towns.setTownInfos(townInfos);
		
		if (username.length() > 0) {
			String message = m_RequestMessage.request(host, clientv, cookie, username, 0L);
			if (message != null) {
				towns.setMessage(message);
			}
		}
		
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
		String host = m_Config.getHost();
		String clientv = m_Config.getClientv();
		String cookie = m_Config.getCookie();
		
		int total = 0;
		List<BuildingQueue> buildingQueues = town.getBuildingQueues();
		if (buildingQueues != null)
			total = buildingQueues.size();
		
		if (total > 1)
			return false;
		
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
					List<Long> buildingIds = new Vector<Long>();
					for (BuildingLine buildingLine : buildingLines) {
						if (buildingLine.getId() != null && buildingLine.getLevel() != null && buildingLine.getLevel() < 40 && buildingLine.getStatus() != null && buildingLine.getStatus().equals("idle"))
							buildingIds.add(buildingLine.getId());
					}
					
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
}
