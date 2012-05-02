package com.task;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import com.buildings.BuildingBase;
import com.buildings.BuildingCastle;
import com.buildings.BuildingChurch;
import com.buildings.BuildingMarket;
import com.buildings.BuildingResources;
import com.buildings.BuildingSoldiers;
import com.buildings.BuildingStore;
import com.buildings.BuildingWorkers;
import com.callback.CallBackTask;
import com.config.Config;
import com.entity.AttackInfo;
import com.entity.CityInfo;
import com.entity.Cities;
import com.entity.ItemInfo;
import com.events.EventArmy;
import com.events.EventBuilding;
import com.events.EventSkill;
import com.events.EventTrain;
import com.request.RequestArmy;
import com.request.RequestBuildings;
import com.request.RequestCities;
import com.request.RequestDeals;
import com.request.RequestItems;
import com.request.RequestMessages;
import com.request.RequestRecruit;
import com.request.RequestRewards;
import com.request.RequestSkills;
import com.request.RequestWorldMaps;
import com.resources.ResourcesBase;
import com.skills.SkillBase;
import com.skills.SkillChurch;
import com.skills.SkillResources;
import com.skills.SkillSoldiers;
import com.soldiers.SoldierBase;
import com.soldiers.SoldierConsume;
import com.util.Numeric;
import com.world.City;
import com.world.Deal;
import com.worldmap.Tile;
import com.worldmap.WorldMap;

public class TaskMy extends TaskBase {
	private Config m_Config;	
	private final Cities m_Cities = new Cities();
	private final RequestArmy m_RequestArmy = new RequestArmy();
	private final RequestCities m_RequestCities = new RequestCities();
	private final RequestMessages m_RequestMessages = new RequestMessages();
	private final RequestRecruit m_RequestRecruit = new RequestRecruit();
	private final RequestBuildings m_RequestBuildings = new RequestBuildings();
	private final RequestWorldMaps m_RequestWorldMaps = new RequestWorldMaps();
	private final RequestItems m_RequestItems = new RequestItems();
	private final RequestSkills m_RequestSkills = new RequestSkills();
	private final RequestDeals m_RequestDeals = new RequestDeals();
	private final RequestRewards m_RequestRewards = new RequestRewards();
	private final Hashtable<Long, AttackInfo> m_AttackInfo = new Hashtable<Long, AttackInfo>();
	private Config m_ConfigNew = null;
	
	public TaskMy(String taskName, Config config, CallBackTask callBack) {
		super(taskName, config.getCityDelay() * 1000, callBack);
		m_Config = config;
	}
	
	public synchronized void setConfig(Config config) {
		if (config != null)
			m_ConfigNew = config;
		else {
			if (m_ConfigNew != null) {
				m_Config.setHost(m_ConfigNew.getHost());
				m_Config.setPassword(m_ConfigNew.getPassword());
				m_Config.setCookie(m_ConfigNew.getCookie());
				m_Config.setAuthorization(m_ConfigNew.getAuthorization());
				m_Config.setCities(m_ConfigNew.getCities());
				m_Config.setAutoCity(m_ConfigNew.getAutoCity());
				m_Config.setCityDelay(m_ConfigNew.getCityDelay());
				m_Config.setAutoUpgrade(m_ConfigNew.getAutoUpgrade());
				m_Config.setUpgradePriority(m_ConfigNew.getUpgradePriority());
				m_Config.setAutoAttack(m_ConfigNew.getAutoAttack());
				m_Config.setAttackTotal(m_ConfigNew.getAttackTotal());
				m_Config.setAttackLevelMin(m_ConfigNew.getAttackLevelMin());
				m_Config.setAttackLevelMax(m_ConfigNew.getAttackLevelMax());
				m_Config.setAutoRecruit(m_ConfigNew.getAutoRecruit());
				m_Config.setInfantrySwords(m_ConfigNew.getInfantrySwords());
				m_Config.setInfantryScout(m_ConfigNew.getInfantryScout());
				m_Config.setInfantryCrossbow(m_ConfigNew.getInfantryCrossbow());
				m_Config.setInfantrySquire(m_ConfigNew.getInfantrySquire());
				m_Config.setCavalryTemplar(m_ConfigNew.getCavalryTemplar());
				m_Config.setCavalryArcher(m_ConfigNew.getCavalryArcher());
				m_Config.setCavalryPaladin(m_ConfigNew.getCavalryPaladin());
				m_Config.setCavalryRoyal(m_ConfigNew.getCavalryRoyal());				
				m_Config.setMarketRate(m_ConfigNew.getMarketRate());
				m_ConfigNew = null;
			}
		}
	}

	@Override
	protected void onEntry() {		
		String host = m_Config.getHost();
		String userName = m_Config.getUserName();
		String cookie = m_Config.getCookie();
		String authorization = m_Config.getAuthorization();			
		long cityDelay = m_Config.getCityDelay();
		boolean autoUpgrade = m_Config.getAutoUpgrade();
		boolean autoAttack = m_Config.getAutoAttack();
		long attackTotal = m_Config.getAttackTotal();
		long attackCount = m_Config.getAttackCount();
		boolean autoRecruit = m_Config.getAutoRecruit();
		double marketRate = m_Config.getMarketRate();
		List<Long> cities = m_Config.getCities();
		
		List<CityInfo> cityInfos = new Vector<CityInfo>();				
		for (Long cityId : cities) {
			try {					
				sleep(1000);
			} catch (InterruptedException e) {				
				
			}
						
			CityInfo cityInfo = m_RequestCities.request(host, cookie, authorization, cityId);			
			if (cityInfo == null)
				continue;
			
			cityInfos.add(cityInfo);
											
			City myCity = cityInfo.getCity();
			if (autoUpgrade && upgrade(myCity, m_Config)) {
				try {						
					sleep(1000);
				} catch (InterruptedException e) {				
					
				}
			}
			
			setEventArmy(myCity);
													
			if (autoRecruit && recruit(myCity, m_Config)) {
				try {						
					sleep(1000);
				} catch (InterruptedException e) {				
					
				}
			}				
															
			if (autoAttack) {
				long level = canAttack(myCity);
				if (level > 0) {
					attackCount++;
					if (attackCount >= attackTotal) {
						List<Tile> npcs = getWorldMap(myCity, m_Config, level);
						if (npcs != null) {
							int i = 0;
							for (Tile npc : npcs) {
								Hashtable<String, Long> soldiers = setSoldiers(myCity, npc.getLevel());						
								if (soldiers != null) {
									City city = m_RequestArmy.request(host, cookie, authorization, myCity.getId(), soldiers, npc.getX(), npc.getY());
									if (city != null) {									
										decreaseSoldiers(myCity, soldiers);
										EventArmy eventArmy = getEventArmy(city);
										if (eventArmy != null) {
											Long eventId = eventArmy.getId();
											AttackInfo attackInfo = new AttackInfo();
											attackInfo.setNpc(npc);
											attackInfo.setX(myCity.getX());
											attackInfo.setY(myCity.getY());
											attackInfo.setEventArmy(eventArmy);
											attackInfo.setSoldiers(soldiers);
											m_AttackInfo.put(eventId, attackInfo);
										}
										
										if (i == 0)
											useSkills(myCity, m_Config);
										
										i++;
									}											
								}
							}
							
							attackCount = 0;
						}						
					}
					
					m_Config.setAttackCount(attackCount);
				}
			}
			
			if (marketRate > 0D)
				sells(myCity, m_Config);
			
			if (myCity.getLeftWishingCount() != null && myCity.getLeftWishingCount() > 0)
				m_RequestRewards.request(host, cookie, authorization, 0L, cityId);			
		}
		
		ItemInfo itemInfo = m_RequestItems.request(host, cookie, authorization);
		String message = m_RequestMessages.request(host, cookie, authorization, userName, 1L);
					
		m_Cities.setCityInfos(cityInfos);
		m_Cities.setItemInfo(itemInfo);
		m_Cities.setMessage(message);
		m_CallBack.onCities(getTaskName(), m_Cities);
		
		setConfig(null);	
		setDelay(Numeric.rndNumber(cityDelay + 10, cityDelay - 10) * 1000);			
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
		
		if (config.getCookie() == null) {
			setCancel(true);
			return false;
		}
		
		if (config.getAuthorization() == null) {
			setCancel(true);
			return false;
		}
		
		if (config.getAutoCity() == null || !config.getAutoCity()) {
			setCancel(true);
			return false;
		}
		
		if (config.getCityDelay().equals(0L)) {
			setCancel(true);
			return false;
		}
		
		if (config.getCities() == null) {
			setCancel(true);
			return false;
		}
		
		if (config.getCities().size() == 0) {
			setCancel(true);
			return false;
		}
		
		return true;
	}
	
	private EventArmy getEventArmy(City city) {
		List<EventArmy> eventArmys = city.getEventArmy();
		if (eventArmys == null)
			return null;
		
		Long max = null;
		EventArmy event = null;
				
		for (EventArmy eventArmy : eventArmys) {
			if (eventArmy.getId() == null)
				continue;
			
			if (max == null) {
				max = eventArmy.getId();
				event = eventArmy;
			} else {
				if (max < eventArmy.getId()) {
					max = eventArmy.getId();
					event = eventArmy;
				}
			}
		}
		
		return event;
	}
	
	private void setEventArmy(City city) {
		if (m_AttackInfo.size() == 0)
			return;
		
		List<EventArmy> eventArmys = city.getEventArmy();
		if (eventArmys == null)
			return;
				
		List<Long> list = new Vector<Long>();
		
		Enumeration<Long> enu = m_AttackInfo.keys();
		while (enu.hasMoreElements()) {
			Long eventId = enu.nextElement();
						
			boolean found = false;
			for (EventArmy eventArmy : eventArmys) {
				if (eventArmy.getId() == null)
					continue;
				
				if (eventArmy.getId().equals(eventId)) {
					found = true;
					break;
				}
			}
			
			if (!found)
				list.add(eventId);
		}
		
		for (Long eventId : list)
			m_AttackInfo.remove(eventId);		
	}
	
	private boolean checkRecruit(City city, SoldierConsume consume, long count) {		
		ResourcesBase resourcesWood = city.getResourcesWood();
		ResourcesBase resourcesStone = city.getResourcesStone();
		ResourcesBase resourcesIron = city.getResourcesIron();
		ResourcesBase resourcesFood = city.getResourcesFood();
		ResourcesBase resourcesGold = city.getResourcesGold();
						
		String message = consume.getMessage();
		if (message != null)
			return false;
		
		if (consume.getWood() != null && consume.getWood() > 0 && resourcesWood != null && resourcesWood.getCount() != null) {
			if (consume.getWood() * count > resourcesWood.getCount())
				return false;
		}
		
		if (consume.getStone() != null && consume.getStone() > 0 && resourcesStone != null && resourcesStone.getCount() != null) {
			if (consume.getStone() * count > resourcesStone.getCount())
				return false;
		}
		
		if (consume.getIron() != null && consume.getIron() > 0 && resourcesIron != null && resourcesIron.getCount() != null) {
			if (consume.getIron() * count > resourcesIron.getCount())
				return false;
		}
		
		if (consume.getFood() != null && consume.getFood() > 0 && resourcesFood != null && resourcesFood.getCount() != null) {
			if (consume.getFood() * count > resourcesFood.getCount())
				return false;
		}
		
		if (consume.getGold() != null && consume.getGold() > 0 && resourcesGold != null && resourcesGold.getCount() != null) {
			if (consume.getGold() * count > resourcesGold.getCount())
				return false;
		}
		
		return true;
	}	
		
	private void decreaseResources(City city, SoldierConsume consume, Long count) {
		ResourcesBase resourcesWood = city.getResourcesWood();
		ResourcesBase resourcesStone = city.getResourcesStone();
		ResourcesBase resourcesIron = city.getResourcesIron();
		ResourcesBase resourcesFood = city.getResourcesFood();
		ResourcesBase resourcesGold = city.getResourcesGold();
		
		if (consume.getWood() != null && consume.getWood() > 0 && resourcesWood != null && resourcesWood.getCount() != null && (consume.getWood() * count) <= resourcesWood.getCount())
			resourcesWood.setCount(resourcesWood.getCount() - consume.getWood() * count);
		
		if (consume.getStone() != null && consume.getStone() > 0 && resourcesStone != null && resourcesStone.getCount() != null && (consume.getStone() * count) <= resourcesStone.getCount())
			resourcesStone.setCount(resourcesStone.getCount() - consume.getStone() * count);
		
		if (consume.getIron() != null && consume.getIron() > 0 && resourcesStone != null && resourcesIron.getCount() != null && (consume.getIron() * count) <= resourcesIron.getCount())
			resourcesIron.setCount(resourcesIron.getCount() - consume.getIron() * count);
		
		if (consume.getFood() != null && consume.getFood() > 0 && resourcesFood != null && resourcesFood.getCount() != null && (consume.getFood() * count) <= resourcesFood.getCount())
			resourcesFood.setCount(resourcesFood.getCount() - consume.getFood() * count);
		
		if (consume.getGold() != null && consume.getGold() > 0 && resourcesGold != null && resourcesGold.getCount() != null && (consume.getGold() * count) <= resourcesGold.getCount())
			resourcesGold.setCount(resourcesGold.getCount() - consume.getGold() * count);
	}
	
	private boolean upgradeBuildings(City city, Config config) {
		String host = config.getHost();
		String authorization = config.getAuthorization();
		String cookie = config.getCookie();
		
		int total = 0;
		List<EventBuilding> eventBuildings = city.getEventBuilding();
		if (eventBuildings != null)
			total = eventBuildings.size();
		
		if (total > 1)
			return false;
		
		List<Long> prioritys = config.getUpgradePriority();
		if (prioritys == null)
			return false;
		
		Hashtable<Long, BuildingBase> buildings = new Hashtable<Long, BuildingBase>();
		
		if (city.getBuildingCastle() != null) {
			BuildingCastle buildingCastle = city.getBuildingCastle();
			if (buildingCastle.getId() != null && buildingCastle.getBuildingType() != null && buildingCastle.checkUpgrade(city))
				buildings.put(buildingCastle.getBuildingType(), buildingCastle);
		}
		
		if (city.getBuildingChurch() != null) {
			BuildingChurch buildingChurch = city.getBuildingChurch();
			if (buildingChurch.getId() != null && buildingChurch.getBuildingType() != null && buildingChurch.checkUpgrade(city))
				buildings.put(buildingChurch.getBuildingType(), buildingChurch);
		}
		
		if (city.getBuildingStore() != null) {
			BuildingStore buildingStore = city.getBuildingStore();
			if (buildingStore.getId() != null && buildingStore.getBuildingType() != null && buildingStore.checkUpgrade(city))
				buildings.put(buildingStore.getBuildingType(), buildingStore);
		}
		
		if (city.getBuildingWorkers() != null) {
			BuildingWorkers buildingWorkers = city.getBuildingWorkers();
			if (buildingWorkers.getId() != null && buildingWorkers.getBuildingType() != null && buildingWorkers.checkUpgrade(city))
				buildings.put(buildingWorkers.getBuildingType(), buildingWorkers);
		}
		
		if (city.getBuildingCavalry() != null) {
			BuildingSoldiers buildingCavalry = city.getBuildingCavalry();
			if (buildingCavalry.getId() != null && buildingCavalry.getBuildingType() != null && buildingCavalry.checkUpgrade(city))
				buildings.put(buildingCavalry.getBuildingType(), buildingCavalry);
		}
		
		if (city.getBuildingInfantry() != null) {
			BuildingSoldiers buildingInfantry = city.getBuildingInfantry();
			if (buildingInfantry.getId() != null && buildingInfantry.getBuildingType() != null && buildingInfantry.checkUpgrade(city))
				buildings.put(buildingInfantry.getBuildingType(), buildingInfantry);
		}
		
		if (city.getBuildingWood() != null) {
			BuildingResources buildingWood = city.getBuildingWood();
			if (buildingWood.getId() != null && buildingWood.getBuildingType() != null && buildingWood.checkUpgrade(city))
				buildings.put(buildingWood.getBuildingType(), buildingWood);
		}
		
		if (city.getBuildingStrone() != null) {
			BuildingResources buildingStrone = city.getBuildingStrone();
			if (buildingStrone.getId() != null && buildingStrone.getBuildingType() != null && buildingStrone.checkUpgrade(city))
				buildings.put(buildingStrone.getBuildingType(), buildingStrone);
		}
		
		if (city.getBuildingIron() != null) {
			BuildingResources buildingIron = city.getBuildingIron();
			if (buildingIron.getId() != null && buildingIron.getBuildingType() != null && buildingIron.checkUpgrade(city))
				buildings.put(buildingIron.getBuildingType(), buildingIron);
		}
		
		if (city.getBuildingFood() != null) {
			BuildingResources buildingFood = city.getBuildingFood();
			if (buildingFood.getId() != null && buildingFood.getBuildingType() != null && buildingFood.checkUpgrade(city))
				buildings.put(buildingFood.getBuildingType(), buildingFood);
		}
		
		if (city.getBuildingMarket() != null) {
			BuildingMarket buildingMarket = city.getBuildingMarket();
			if (buildingMarket.getId() != null && buildingMarket.getBuildingType() != null && buildingMarket.checkUpgrade(city))
				buildings.put(buildingMarket.getBuildingType(), buildingMarket);
		}
		
		for (Long priority : prioritys) {
			if (buildings.containsKey(priority)) {
				BuildingBase building = buildings.get(priority);
				if (m_RequestBuildings.request(host, cookie, authorization, building.getId())) {
					building.decreaseResources(city);
					if (++total > 1)
						return true;
				}
			}
		}
				
		return false;
	}
	
	private boolean upgradeSkills(City city, Config config) {
		String host = config.getHost();
		String authorization = config.getAuthorization();
		String cookie = config.getCookie();
		
		int total = 0;		
		List<EventSkill> eventSkills = city.getEventSkill();
		if (eventSkills != null) {
			for (EventSkill eventSkill : eventSkills) {
				if (eventSkill.getType() != null) {
					if (eventSkill.getType().equals(1L) || eventSkill.getType().equals(2L) || eventSkill.getType().equals(3L) || eventSkill.getType().equals(4L)) {
						total++;
						continue;
					}
					
					if (eventSkill.getType().equals(7L) || eventSkill.getType().equals(8L) || eventSkill.getType().equals(9L) || eventSkill.getType().equals(10L)) {
						total++;
						continue;
					}
					
					if (eventSkill.getType().equals(17L)) {
						total++;
						continue;
					}
				}
			}			
		}
		
		if (total > 1)
			return false;
		
		ResourcesBase gold = city.getResourcesGold();
		if (gold == null || gold.getCount() == null)
			return false;
		
		SkillResources skillResources = city.getSkillResources();
		SkillSoldiers skillSoldiers = city.getSkillSoldiers();
		boolean success = false;
		
		if (skillSoldiers != null) {
			SkillBase infantryAttack = skillSoldiers.getInfantryAttack();
			SkillBase cavalryAttack = skillSoldiers.getCavalryAttack();
			SkillBase infantryDefense = skillSoldiers.getInfantryDefense();
			SkillBase cavalryDefense = skillSoldiers.getCavalryDefense();
						
			if (cavalryAttack != null && cavalryAttack.checkUpgrade(gold.getCount())) {
				if (m_RequestSkills.request(host, cookie, authorization, cavalryAttack.getId())) {
					cavalryAttack.decreaseResources(gold);
					if (++total > 1)
						return true;
					else
						success = true;
				}
			}
			
			if (infantryAttack != null && infantryAttack.checkUpgrade(gold.getCount())) {
				if (m_RequestSkills.request(host, cookie, authorization, infantryAttack.getId())) {
					infantryAttack.decreaseResources(gold);
					if (++total > 1)
						return true;
					else
						success = true;
				}
			}
			
			if (cavalryDefense != null && cavalryDefense.checkUpgrade(gold.getCount())) {
				if (m_RequestSkills.request(host, cookie, authorization, cavalryDefense.getId())) {
					cavalryDefense.decreaseResources(gold);
					if (++total > 1)
						return true;
					else
						success = true;
				}
			}
			
			if (infantryDefense != null && infantryDefense.checkUpgrade(gold.getCount())) {
				if (m_RequestSkills.request(host, cookie, authorization, infantryDefense.getId())) {
					infantryDefense.decreaseResources(gold);
					if (++total > 1)
						return true;
					else
						success = true;
				}
			}
		}
		
		if (skillResources != null) {
			SkillBase skillWood = skillResources.getSkillWood();
			SkillBase skillStone = skillResources.getSkillStone();
			SkillBase skillIron = skillResources.getSkillIron();
			SkillBase skillFood = skillResources.getSkillFood();
			SkillBase skillStore = skillResources.getSkillStore();
						
			List<SkillBase> list = new Vector<SkillBase>();
												
			if (skillWood != null && skillWood.checkUpgrade(gold.getCount()))
				list.add(skillWood);
									
			if (skillStone != null && skillStone.checkUpgrade(gold.getCount()))
				list.add(skillStone);
						
			if (skillIron != null && skillIron.checkUpgrade(gold.getCount()))
				list.add(skillIron);
						
			if (skillFood != null && skillFood.checkUpgrade(gold.getCount()))
				list.add(skillFood);
						
			if (skillStore != null && skillStore.checkUpgrade(gold.getCount()))
				list.add(skillStore);
			
			if (list.size() == 0)
				return success;
						
			int max = 2 - total;
			int i = 0;
						
			while (i < max) {				
				SkillBase skillBase = null;				
				for (SkillBase skill : list) {
					if (!skill.checkUpgrade(gold.getCount()))
						continue;
					
					if (skillBase == null)
						skillBase = skill;						
					else {
						if (skillBase.getLevel() > skill.getLevel())
							skillBase = skill;
					}				
				}
				
				if (skillBase == null)
					break;
				
				if (m_RequestSkills.request(host, cookie, authorization, skillBase.getId())) {
					skillBase.decreaseResources(gold);
					if (!success)
						success = true;								
				}
								
				if (list.remove(skillBase) && list.size() == 0)
					break;
				
				skillBase = null;
				i++;
			}
		}
		
		return success;
	}
	
	private boolean upgrade(City city, Config config) {
		boolean success = upgradeBuildings(city, config);
		success = upgradeSkills(city, config);
		return success;
	}
	
	private boolean recruit(City city, Config config, SoldierBase soldier, Long total) {
		Long cityId = city.getId();
		String host = config.getHost();
		String authorization = config.getAuthorization();
		String cookie = config.getCookie();
		SoldierConsume consume = soldier.getConsume();
		String soldierName = soldier.getName();
		
		long amount = 0;
		if (m_AttackInfo.size() > 0) {
			Enumeration<AttackInfo> enu = m_AttackInfo.elements();
			while (enu.hasMoreElements()) {
				AttackInfo attackInfo = enu.nextElement();
				Hashtable<String, Long> soldiers = attackInfo.getSoldiers();
				if (soldiers.containsKey(soldierName)) {
					long p = soldiers.get(soldierName);
					amount += p;
				}					
			}
		}
		
		if (soldier.getCount() + amount < total) {			
			Long count = total - (soldier.getCount() + amount);			
			if (checkRecruit(city, consume, count) && m_RequestRecruit.request(host, cookie, authorization, cityId, soldierName, count)) {
				decreaseResources(city, consume, count);
				return true;
			}
		}
		
		return false;
	}
	
	private boolean recruit(City city, Config config) {		
		Long cityId = city.getId();
		if (cityId == null)
			return false;
		
		List<EventTrain> eventTrains = city.getEventTrain();
		if (eventTrains != null && eventTrains.size() > 1)
			return false;
						
		long type = -1;
		if (eventTrains != null && eventTrains.size() > 0) {
			EventTrain eventTrain = eventTrains.get(0);
			if (eventTrain != null && eventTrain.getType() != null)
				type = eventTrain.getType();
		}
		
		boolean recruit = false;
		int trains = eventTrains.size();
		
		SoldierBase infantrySwords = city.getInfantrySwords();		
		if (infantrySwords != null && infantrySwords.getSoldierType() != null && infantrySwords.getCount() != null && infantrySwords.getConsume() != null && infantrySwords.getName() != null && config.getInfantrySwords() != null && config.getInfantrySwords() > 0) {
			if (type != infantrySwords.getSoldierType() && recruit(city, config, infantrySwords, config.getInfantrySwords())) {
				recruit = true;
				if (++trains > 1)
					return recruit;
			}							
		}
		
		SoldierBase infantryScout = city.getInfantryScout();
		if (infantryScout != null && infantryScout.getSoldierType() != null && infantryScout.getCount() != null && infantryScout.getConsume() != null && infantryScout.getName() != null && config.getInfantryScout() != null && config.getInfantryScout() > 0) {
			if (type != infantryScout.getSoldierType() && recruit(city, config, infantryScout, config.getInfantryScout())) {
				recruit = true;
				if (++trains > 1)
					return recruit;				
			}				
		}
		
		SoldierBase infantryCrossbow = city.getInfantryCrossbow();
		if (infantryCrossbow != null && infantryCrossbow.getSoldierType() != null && infantryCrossbow.getCount() != null && infantryCrossbow.getConsume() != null && infantryCrossbow.getName() != null && config.getInfantryCrossbow() != null && config.getInfantryCrossbow() > 0) {
			if (type != infantryCrossbow.getSoldierType() && recruit(city, config, infantryCrossbow, config.getInfantryCrossbow())) {
				recruit = true;
				if (++trains > 1)
					return recruit;
			}				
		}
		
		SoldierBase infantrySquire = city.getInfantrySquire();
		if (infantrySquire != null && infantrySquire.getSoldierType() != null && infantrySquire.getCount() != null && infantrySquire.getConsume() != null && infantrySquire.getName() != null && config.getInfantrySquire() != null && config.getInfantrySquire() > 0) {
			if (type != infantrySquire.getSoldierType() && recruit(city, config, infantrySquire, config.getInfantrySquire())) {
				recruit = true;
				if (++trains > 1)
					return recruit;
			}			
		}
		
		SoldierBase cavalryTemplar = city.getCavalryTemplar();
		if (cavalryTemplar != null && cavalryTemplar.getSoldierType() != null && cavalryTemplar.getCount() != null && cavalryTemplar.getConsume() != null && cavalryTemplar.getName() != null && config.getCavalryTemplar() != null && config.getCavalryTemplar() > 0) {
			if (type != cavalryTemplar.getSoldierType() && recruit(city, config, cavalryTemplar, config.getCavalryTemplar())) {
				recruit = true;
				if (++trains > 1)
					return recruit;
			}				
		}
		
		SoldierBase cavalryArcher = city.getCavalryArcher();
		if (cavalryArcher != null && cavalryArcher.getSoldierType() != null && cavalryArcher.getCount() != null && cavalryArcher.getConsume() != null && cavalryArcher.getName() != null && config.getCavalryArcher() != null && config.getCavalryArcher() > 0) {
			if (type != cavalryArcher.getSoldierType() && recruit(city, config, cavalryArcher, config.getCavalryArcher())) {
				recruit = true;
				if (++trains > 1)
					return recruit;
			}				
		}
		
		SoldierBase cavalryPaladin = city.getCavalryPaladin();
		if (cavalryPaladin != null && cavalryPaladin.getSoldierType() != null && cavalryPaladin.getCount() != null && cavalryPaladin.getConsume() != null && cavalryPaladin.getName() != null && config.getCavalryPaladin() != null && config.getCavalryPaladin() > 0) {
			if (type != cavalryPaladin.getSoldierType() && recruit(city, config, cavalryPaladin, config.getCavalryPaladin())) {
				recruit = true;
				if (++trains > 1)
					return recruit;
			}				
		}
		
		SoldierBase cavalryRoyal = city.getCavalryRoyal();
		if (cavalryRoyal != null && cavalryRoyal.getSoldierType() != null && cavalryRoyal.getCount() != null && cavalryRoyal.getConsume() != null && cavalryRoyal.getName() != null && config.getCavalryRoyal() != null && config.getCavalryRoyal() > 0) {
			if (type != cavalryRoyal.getSoldierType() && recruit(city, config, cavalryRoyal, config.getCavalryRoyal())) {
				recruit = true;
				if (++trains > 1)
					return recruit;
			}				
		}
		
		return recruit;
	}
	
	private long getLevel(long level) {		
		if (level == 1)
			return 800;
		else if (level == 2)
			return 1000;
		else if (level == 3)
			return 1500;
		else if (level == 4)
			return 2500;
		else if (level == 5)
			return 5000;
		else if (level == 6)
			return 8000;
		else if (level == 7)
			return 13000;
		else if (level == 8)
			return 20000;
		else if (level == 9)
			return 40000;
		else
			return 80000;					
	}
	
	private long setLevel(long total) {
		if (total < 800)
			return 0;
		else if (total < 1000)
			return 1;
		else if (total < 1500)
			return 2;
		else if (total < 2500)
			return 3;
		else if (total < 5000)
			return 4;
		else if (total < 8000)
			return 5;
		else if (total < 13000)
			return 6;
		else if (total < 20000)
			return 7;
		else if (total < 40000)
			return 8;
		else if (total < 80000)
			return 9;
		else 
			return 10;
	}
	
	private long getSoldiers(long amount, long total, long count) {
		return (total + count >= amount) ? (amount - total) : count;
	}
	
	private Hashtable<String, Long> setSoldiers(City city, long level) {
		SoldierBase infantrySwords = city.getInfantrySwords();		
		SoldierBase infantryCrossbow = city.getInfantryCrossbow();
		SoldierBase infantrySquire = city.getInfantrySquire();
		SoldierBase cavalryTemplar = city.getCavalryTemplar();
		SoldierBase cavalryArcher = city.getCavalryArcher();
		SoldierBase cavalryPaladin = city.getCavalryPaladin();
		SoldierBase cavalryRoyal = city.getCavalryRoyal();
		
		long total = 0;
		List<SoldierBase> list = new Vector<SoldierBase>();
		if (infantrySquire != null && infantrySquire.getName() != null && infantrySquire.getCount() != null && infantrySquire.getCount() > 0)
			list.add(infantrySquire);
		
		if (cavalryTemplar != null && cavalryTemplar.getName() != null && cavalryTemplar.getCount() != null && cavalryTemplar.getCount() > 0)
			list.add(cavalryTemplar);
		
		if (infantrySwords != null && infantrySwords.getName() != null && infantrySwords.getCount() != null && infantrySwords.getCount() > 0)
			list.add(infantrySwords);
		
		if (infantryCrossbow != null && infantryCrossbow.getName() != null && infantryCrossbow.getCount() != null && infantryCrossbow.getCount() > 0)
			list.add(infantryCrossbow);
		
		if (cavalryArcher != null && cavalryArcher.getName() != null && cavalryArcher.getCount() != null && cavalryArcher.getCount() > 0)
			list.add(cavalryArcher);
		
		if (cavalryPaladin != null && cavalryPaladin.getName() != null && cavalryPaladin.getCount() != null && cavalryPaladin.getCount() > 0)
			list.add(cavalryPaladin);
		
		if (cavalryRoyal != null && cavalryRoyal.getName() != null && cavalryRoyal.getCount() != null && cavalryRoyal.getCount() > 0)
			list.add(cavalryRoyal);
		
		if (list.size() == 0)
			return null;
		
		long amount = getLevel(level);
		Hashtable<String, Long> table = new Hashtable<String, Long>();
		
		for (SoldierBase soldier : list) {
			String name = soldier.getName();
			long count = soldier.getCount();
			
			long number = getSoldiers(amount, total, count);
			if (number > 0) {
				table.put(name, number);
				total += number;
			} else
				break;
		}
		
		if (total >= amount)
			return table;
		else
			return null;
	}
	
	private void decreaseSoldiers(City city, Hashtable<String, Long> soldiers) {
		SoldierBase infantrySwords = city.getInfantrySwords();		
		SoldierBase infantryCrossbow = city.getInfantryCrossbow();
		SoldierBase infantrySquire = city.getInfantrySquire();
		SoldierBase cavalryTemplar = city.getCavalryTemplar();
		SoldierBase cavalryArcher = city.getCavalryArcher();
		SoldierBase cavalryPaladin = city.getCavalryPaladin();
		SoldierBase cavalryRoyal = city.getCavalryRoyal();
		
		List<SoldierBase> list = new Vector<SoldierBase>();
		if (infantrySquire != null && infantrySquire.getName() != null && infantrySquire.getCount() != null)
			list.add(infantrySquire);
		
		if (cavalryTemplar != null && cavalryTemplar.getName() != null && cavalryTemplar.getCount() != null)
			list.add(cavalryTemplar);
		
		if (infantrySwords != null && infantrySwords.getName() != null && infantrySwords.getCount() != null)
			list.add(infantrySwords);
		
		if (infantryCrossbow != null && infantryCrossbow.getName() != null && infantryCrossbow.getCount() != null)
			list.add(infantryCrossbow);
		
		if (cavalryArcher != null && cavalryArcher.getName() != null && cavalryArcher.getCount() != null)
			list.add(cavalryArcher);
		
		if (cavalryPaladin != null && cavalryPaladin.getName() != null && cavalryPaladin.getCount() != null)
			list.add(cavalryPaladin);
		
		if (cavalryRoyal != null && cavalryRoyal.getName() != null && cavalryRoyal.getCount() != null)
			list.add(cavalryRoyal);
		
		for (SoldierBase soldier : list) {
			Enumeration<String> enu = soldiers.keys();
			while (enu.hasMoreElements()) {
				String name = enu.nextElement();
				Long count = soldiers.get(name);
				
				if (soldier.getName().equals(name) && soldier.getCount() >= count) {
					soldier.setCount(soldier.getCount() - count);
					break;
				}
			}
		}
	}
			
	private List<Tile> getWorldMap(City city, Config config, long level) {		
		long attackLevelMin = config.getAttackLevelMin();
		long attackLevelMax = config.getAttackLevelMax();
		Long x = city.getX();
		Long y = city.getY();
		
		if (x == null || y == null)
			return null;
		
		String host = config.getHost();
		String authorization = config.getAuthorization();
		String cookie = config.getCookie();
		
		WorldMap npcs = m_RequestWorldMaps.request(host, cookie, authorization, x, y, 14L, 15L);
		if (npcs == null || npcs.getTile() == null)
			return null;
								
		List<Tile> list = new Vector<Tile>();
		List<Tile> tiles = npcs.getTile();
		
		for (Tile tile : tiles) {
			Long mapType = tile.getMapType();			
			if (tile.getX() == null || tile.getY() == null || mapType == null || tile.getLevel() == null)
				continue;
			
			if (tile.getRecoveryTime() != null)
				continue;						
			
			if ((attackLevelMin > 0 && attackLevelMax > 0 && (tile.getLevel() < attackLevelMin || attackLevelMax < tile.getLevel())) || (tile.getLevel() > level))
				continue;
			
			if (m_AttackInfo.size() > 0) {
				boolean found = false;
				Enumeration<AttackInfo> enu = m_AttackInfo.elements();
				while (enu.hasMoreElements()) {
					AttackInfo attackInfo = enu.nextElement();
					Tile npc = attackInfo.getNpc();
					if (npc.getX().equals(tile.getX()) && npc.getY().equals(tile.getY())) {
						found = true;
						break;
					}
				}
				
				if (found)
					continue;
			}			
			
			if (mapType.equals(3L) || mapType.equals(5L) || mapType.equals(6L)) {
				list.add(tile);
				continue;
			}
		}
		
		if (list.size() == 0)
			return null;
		
		int max = 2;
		if (city.getEventArmy() != null)
			max -= city.getEventArmy().size();
			
		Hashtable<Tile, Double> min = new Hashtable<Tile, Double>(max);
				
		for (Tile tile : list) {
			double distance = Math.sqrt((Math.pow(Math.abs(tile.getX() - x), 2) + Math.pow(Math.abs(tile.getY() - y), 2)));
			if (min.size() < max) {
				min.put(tile, distance);
				continue;
			} else {
				Enumeration<Tile> enu = min.keys();
				while (enu.hasMoreElements()) {
					Tile npc = enu.nextElement();
					double p = min.get(npc);
					
					if (distance < p) {
						min.remove(npc);
						min.put(tile, distance);
						break;
					}
				}
			}			
		}
		
		list.clear();		
		Enumeration<Tile> enu = min.keys();
		while (enu.hasMoreElements()) {
			Tile npc = enu.nextElement();
			list.add(npc);
		}
		
		return list;
	}
					
	private long canAttack(City city) {
		List<EventArmy> eventArmys = city.getEventArmy();
		if (eventArmys != null && eventArmys.size() > 1)
			return 0;
										
		long total = 0;
		if (city.getInfantrySwords() != null && city.getInfantrySwords().getCount() != null)
			total += city.getInfantrySwords().getCount();
		
		if (city.getInfantryCrossbow() != null && city.getInfantryCrossbow().getCount() != null)
			total += city.getInfantryCrossbow().getCount();
		
		if (city.getInfantrySquire() != null && city.getInfantrySquire().getCount() != null)
			total += city.getInfantrySquire().getCount();
		
		if (city.getCavalryTemplar() != null && city.getCavalryTemplar().getCount() != null)
			total += city.getCavalryTemplar().getCount();
		
		if (city.getCavalryArcher() != null && city.getCavalryArcher().getCount() != null)
			total += city.getCavalryArcher().getCount();
		
		if (city.getCavalryPaladin() != null && city.getCavalryPaladin().getCount() != null)
			total += city.getCavalryPaladin().getCount();
		
		if (city.getCavalryRoyal() != null && city.getCavalryRoyal().getCount() != null)
			total += city.getCavalryRoyal().getCount();
		
		return setLevel(total);
	}		
	
	private void useSkills(City city, Config config) {
		String host = config.getHost();
		String authorization = config.getAuthorization();
		String cookie = config.getCookie();
		
		BuildingCastle buildingCastle = city.getBuildingCastle();
		if (buildingCastle == null || buildingCastle.getLevel() == null || buildingCastle.getLevel() < 20)
			return;
		
		BuildingChurch buildingChurch = city.getBuildingChurch();
		if (buildingChurch == null || buildingChurch.getLevel() == null || buildingChurch.getLevel() == 0)
			return;
		
		SkillChurch skillChurch = city.getSkillChurch();
		if (skillChurch == null)
			return;
		
		ResourcesBase resourcesLeader = city.getResourcesLeader();
		if (resourcesLeader == null || resourcesLeader.getCount() == null || resourcesLeader.getCount() < 120)
			return;				
		
		SkillBase churchGift = skillChurch.getChurchGift();
		if (churchGift != null && churchGift.getSkillType() != null && churchGift.getLeaderShip() != null && churchGift.getId() != null) {			
			boolean found = false;			
			List<EventSkill> eventSkills = city.getEventSkill();
			if (eventSkills != null) {
				for (EventSkill eventSkill : eventSkills) {
					if (eventSkill.getType() == null)
						continue;
					
					if (eventSkill.getType().equals(churchGift.getSkillType())) {
						found = true;
						break;
					}
				}
			}
			
			if (!found && m_RequestSkills.request(host, cookie, authorization, churchGift.getId()))
				resourcesLeader.setCount(resourcesLeader.getCount() - churchGift.getLeaderShip());					
		}
		
		SkillBase churchLucky = skillChurch.getChurchLucky();
		if (churchLucky != null && churchLucky.getSkillType() != null && churchLucky.getLeaderShip() != null && churchLucky.getId() != null && buildingChurch.getLevel() >= 10) {
			boolean found = false;			
			List<EventSkill> eventSkills = city.getEventSkill();
			if (eventSkills != null) {
				for (EventSkill eventSkill : eventSkills) {
					if (eventSkill.getType() == null)
						continue;
					
					if (eventSkill.getType().equals(churchLucky.getSkillType())) {
						found = true;
						break;
					}
				}
			}
			
			if (!found && m_RequestSkills.request(host, cookie, authorization, churchLucky.getId()))
				resourcesLeader.setCount(resourcesLeader.getCount() - churchLucky.getLeaderShip());						
		}
	}
	
	private boolean sells(City city, ResourcesBase resources, Config config) {
		String host = config.getHost();
		String authorization = config.getAuthorization();
		String cookie = config.getCookie();
		
		if (resources.getCount() != null && resources.getCapacity() != null && resources.getName() != null) {
			long count = resources.getCount();
			long capacity = resources.getCapacity();
			String name = resources.getName();
			
			if ((double)count / (double)capacity >= config.getMarketRate()) {
				List<Deal> deals = m_RequestDeals.request(host, cookie, authorization, name, 1L);
				if (deals != null && deals.size() > 0) {
					Deal deal = deals.get(0);
					String unitPrice = deal.getUnitPrice();
					if (unitPrice != null)
						return m_RequestDeals.request(host, cookie, authorization, city.getId(), name, unitPrice, city.getMarketLeftCapacity());
				}
			}				
		}
		
		return false;
	}
	
	private void sells(City city, Config config) {
		if (city.getMarketLeftCapacity() == null || city.getMarketLeftCapacity() <= 0)
			return;
		
		ResourcesBase resourcesWood = city.getResourcesWood();
		ResourcesBase resourcesStone = city.getResourcesStone();
		ResourcesBase resourcesIron = city.getResourcesIron();
		ResourcesBase resourcesFood = city.getResourcesFood();
		
		if (resourcesWood != null && sells(city, resourcesWood, config))
			return;
		
		if (resourcesStone != null && sells(city, resourcesStone, config))
			return;	
		
		if (resourcesIron != null && sells(city, resourcesIron, config))
			return;
		
		if (resourcesFood != null && sells(city, resourcesFood, config))
			return;
	}
}
