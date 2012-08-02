package com.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.util.Numeric;

public class Config {
	private String host;
	private String clientv;
	private Long userId;
	private String cookie;
	private Long autoTowns;
	private Long equipmentMax;
	private List<Long> equipmentTowns;
	private List<ConfigTown> configTowns;
	
	public String getHost() {
		return host;
	}

	public String getClientv() {
		return clientv;
	}

	public Long getUserId() {
		return userId;
	}

	public String getCookie() {
		return cookie;
	}

	public Long getAutoTowns() {
		return autoTowns;
	}

	public Long getEquipmentMax() {
		return equipmentMax;
	}

	public List<Long> getEquipmentTowns() {
		return equipmentTowns;
	}

	public List<ConfigTown> getConfigTowns() {
		return configTowns;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setClientv(String clientv) {
		this.clientv = clientv;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public void setAutoTowns(Long autoTowns) {
		this.autoTowns = autoTowns;
	}

	public void setEquipmentMax(Long equipmentMax) {
		this.equipmentMax = equipmentMax;
	}

	public void setEquipmentTowns(List<Long> equipmentTowns) {
		this.equipmentTowns = equipmentTowns;
	}

	public void setConfigTowns(List<ConfigTown> configTowns) {
		this.configTowns = configTowns;
	}

	private static Document loadXML(InputStream in) {
		Document document = null;
		
		try {
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(in);			
		} catch (DocumentException e) {			
			return null;
		}
		
		return document;
	}
	
	public static Config getConfigs(InputStream in) {	
		Document document = loadXML(in);						
		if (document == null)			
			return null;
				
		Element elementRoot = document.getRootElement();
		Config config = new Config();
		
		Element elementParent = elementRoot.element("host");
		if (elementParent == null)
			return null;
		
		String tmp = elementParent.getText();
		if (tmp == null || tmp.length() == 0)
			return null;
		
		config.setHost(tmp);
		
		elementParent = elementRoot.element("clientv");
		if (elementParent == null)
			return null;
		
		tmp = elementParent.getText();
		if (tmp == null || tmp.length() == 0)
			return null;
		
		config.setClientv(tmp);
		
		elementParent = elementRoot.element("userid");
		if (elementParent == null)
			return null;
		
		tmp = elementParent.getText();
		if (!Numeric.isNumber(tmp))
			return null;
		
		try {
			config.setUserId(Long.parseLong(tmp));
		} catch (Exception e) {
			return null;
		}
		
		elementParent = elementRoot.element("cookie");
		if (elementParent == null)
			return null;
		
		tmp = elementParent.getText();
		if (tmp == null || tmp.length() == 0)
			return null;
		
		config.setCookie(tmp);
		
		elementParent = elementRoot.element("autotowns");
		if (elementParent == null)
			return null;
		
		tmp = elementParent.getText();
		if (!Numeric.isNumber(tmp))
			return null;
		
		try {
			config.setAutoTowns(Long.parseLong(tmp));
		} catch (NumberFormatException e) {
			return null;
		}
		
		elementParent = elementRoot.element("equipment");
		if (elementParent != null) {
			Element equipmentMax = elementParent.element("max");
			Element equipmentTowns = elementParent.element("towns");
			if (equipmentMax != null && equipmentTowns != null) {
				tmp = equipmentMax.getText();
				if (Numeric.isNumber(tmp)) {
					try {
						Long max = Long.parseLong(tmp);
						tmp = equipmentTowns.getText();
						if (max > 0 && tmp != null) {
							List<Long> towns = new Vector<Long>();
							if (tmp.indexOf(",") > -1) {
								String[] arrays = tmp.split(",");
								for (String array : arrays) {
									if (Numeric.isNumber(array))
										towns.add(Long.parseLong(array));
								}
							} else {
								if (Numeric.isNumber(tmp))
									towns.add(Long.parseLong(tmp));
							}
							
							if (towns.size() > 0) {
								config.setEquipmentMax(max);
								config.setEquipmentTowns(towns);
							}
						}												
					}  catch (NumberFormatException e) {
						
					}
				}					
			}
		}
						
		Iterator<Element> elements = elementRoot.elementIterator("towns");
		if (elements != null) {
			List<ConfigTown> configTowns = new Vector<ConfigTown>();
			while (elements.hasNext()) {
				try {			
					elementParent = elements.next();
					ConfigTown configTown = new ConfigTown();
					
					Attribute attribute = elementParent.attribute("id");
					if (attribute == null)
						continue;
					
					tmp = attribute.getText();
					if (!Numeric.isNumber(tmp))
						continue;
					
					configTown.setTownId(Long.parseLong(tmp));
					
					Element element = elementParent.element("autoupgrade");
					if (element != null && element.getText() != null) {
						tmp = element.getText();
						if (tmp.equals("true")) {
							Attribute priority = element.attribute("priority");
							if (priority != null && priority.getText() != null) {
								tmp = priority.getText();
								if (tmp.indexOf(",") > -1) {
									String[] arrays = tmp.split(",");
									if (arrays != null) {
										List<Long> prioritys = new Vector<Long>();
										for (String array : arrays) {
											if (Numeric.isNumber(array))
												prioritys.add(Long.parseLong(array));
										}
										
										if (prioritys.size() > 0) {
											configTown.setUpgradePriority(prioritys);
											configTown.setAutoUpgrade(true);											
										} else
											configTown.setAutoUpgrade(false);
									} else
										configTown.setAutoUpgrade(false);
								} else {
									if (Numeric.isNumber(tmp)) {
										List<Long> prioritys = new Vector<Long>();
										prioritys.add(Long.parseLong(tmp));
										configTown.setUpgradePriority(prioritys);
										configTown.setAutoUpgrade(true);										
									} else
										configTown.setAutoUpgrade(false);																	
								}									
							} else
								configTown.setAutoUpgrade(false);										
						} else
							configTown.setAutoUpgrade(false);
					} else
						configTown.setAutoUpgrade(false);
					
					element = elementParent.element("autoattack");
					if (element != null && element.getText() != null) {
						tmp = element.getText();
						if (tmp != null && tmp.equals("true")) {																									
							Attribute level = element.attribute("level");
							if (level != null && level.getText() != null) {
								tmp = level.getText();
								if (tmp.indexOf("-") > -1) {
									String[] array = tmp.split("-");
									if (array != null && array.length == 2 && Numeric.isNumber(array[0]) && Numeric.isNumber(array[1])) {
										configTown.setAutoAttack(true);											
										configTown.setAttackLevelMin(Long.parseLong(array[0]));
										configTown.setAttackLevelMax(Long.parseLong(array[1]));
									} else
										configTown.setAutoAttack(false);									
								} else
									configTown.setAutoAttack(false);																			
							} else
								configTown.setAutoAttack(false);
						} else
							configTown.setAutoAttack(false);							
					}
					
					element = elementParent.element("autorecruit");
					if (element != null && element.getText() != null) {
						tmp = element.getText();
						if (tmp.equals("true"))
							configTown.setAutoRecruit(true);
						else
							configTown.setAutoRecruit(false);
					} else
						configTown.setAutoRecruit(false);
					
					element = elementParent.element("sells");
					if (element != null && element.getText() != null) {
						tmp = element.getText();
						if (tmp.equals("true")) {
							Iterator<Attribute> attributes = element.attributeIterator();
							if (attributes != null) {
								HashMap<String, Double> sells = new HashMap<String, Double>();
								while (attributes.hasNext()) {
									attribute = attributes.next();
									String name = attribute.getName();
									String value = attribute.getText();
									if (!Numeric.isNumber(value))
										continue;
									
									Double rate = Double.parseDouble(value) / 100D;
									sells.put(name, rate);
								}
								
								if (sells.size() > 0)
									configTown.setSells(sells);
								else
									configTown.setSells(null);
							} else
								configTown.setSells(null);
						} else 
							configTown.setSells(null);
					} else 
						configTown.setSells(null);
					
					element = elementParent.element("buys");
					if (element != null && element.getText() != null) {
						tmp = element.getText();
						if (tmp.equals("true")) {
							Iterator<Attribute> attributes = element.attributeIterator();
							if (attributes != null) {
								HashMap<String, Double> buys = new HashMap<String, Double>();
								while (attributes.hasNext()) {
									attribute = attributes.next();
									String name = attribute.getName();
									String value = attribute.getText();
									if (!Numeric.isNumber(value))
										continue;
									
									Double rate = Double.parseDouble(value) / 100D;
									buys.put(name, rate);
								}
								
								if (buys.size() > 0)
									configTown.setBuys(buys);
								else
									configTown.setBuys(null);
							} else
								configTown.setBuys(null);
						} else 
							configTown.setBuys(null);
					} else 
						configTown.setBuys(null);
						
					configTowns.add(configTown);
				} catch (NumberFormatException e) {
					
				}				
			}
			
			config.setConfigTowns(configTowns);
		}
		
		return config;
	}
	
	public static Config getConfigs(String path) {
		InputStream in = null;
		
		try {
			in = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			return null;
		}
		
		Config config = getConfigs(in);
		
		try {
			in.close();
		} catch (IOException e) {
			return null;
		}
		
		return config;
	}
	
	public static String getConfig(String path) {
		InputStream in = null;
		
		try {
			in = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			return null;
		}
		
		Document document = loadXML(in);
		if (document == null) {
			try {
				in.close();
				return null;
			} catch (IOException e) {
				return null;
			}
		} else {
			String xml = document.asXML();
			
			try {
				in.close();
			} catch (IOException e) {
				return null;
			}
			
			return xml;
		}	
	}
	
	public static String setConfig(Config config) {
		StringBuilder xml = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n\r\n");
		xml.append("<config>\r\n");
		
		xml.append("\t<host>");
		xml.append(config.getHost() != null ? config.getHost() : "");
		xml.append("</host>\r\n");
		
		xml.append("\t<clientv>");
		xml.append(config.getClientv() != null ? config.getClientv() : "");
		xml.append("</clientv>\r\n");
		
		xml.append("\t<userid>");
		xml.append(config.getUserId() != null ? config.getUserId() : "");
		xml.append("</userid>\r\n");
		
		xml.append("\t<cookie>");
		xml.append(config.getCookie() != null ? config.getCookie() : "");
		xml.append("</cookie>\r\n");
		
		xml.append("\t<autotowns>");
		xml.append(config.getAutoTowns() != null ? config.getAutoTowns() : "");
		xml.append("</autotowns>\r\n");
		
		xml.append("\t<equipment>");
		xml.append("\t\t<max>");
		xml.append(config.getEquipmentMax() != null ? config.getEquipmentMax() : "");
		xml.append("</max>");
		xml.append("\t\t<towns>");
		if (config.getEquipmentTowns() != null) {
			StringBuilder sb = new StringBuilder();
			List<Long> towns = config.getEquipmentTowns();
			for (Long town : towns) {
				sb.append(",");
				sb.append(town);
			}
			
			if (sb.length() > 0)
				sb.deleteCharAt(0);
			
			xml.append(sb.toString());
		}
		
		xml.append("</towns>");
		xml.append("\t</equipment>\r\n");
		
		List<ConfigTown> configTowns = config.getConfigTowns();
		if (configTowns != null) {
			for (ConfigTown configTown : configTowns) {
				xml.append("\t<towns id=\"");
				xml.append(configTown.getTownId() != null ? configTown.getTownId() : "");
				xml.append("\">\r\n");
				
				StringBuilder upgradePriority = new StringBuilder();
				if (configTown.getUpgradePriority() != null) {
					for (Long priority : configTown.getUpgradePriority()) {
						upgradePriority.append(",");
						upgradePriority.append(priority);
					}
				}
				
				if (upgradePriority.length() > 0)
					upgradePriority.deleteCharAt(0);
				
				xml.append("\t\t<autoupgrade priority=\"");
				xml.append(upgradePriority);
				xml.append("\">");
				xml.append(upgradePriority.length() > 0 ? "true" : "false");
				xml.append("</autoupgrade>\r\n");
				
				StringBuilder attackLevel = new StringBuilder();
				if (configTown.getAttackLevelMin() != null && configTown.getAttackLevelMax() != null) {
					attackLevel.append(configTown.getAttackLevelMin());
					attackLevel.append("-");
					attackLevel.append(configTown.getAttackLevelMax());
				}						
								
				xml.append("\t\t<autoattack level=\"");
				xml.append(attackLevel);
				xml.append("\">");
				xml.append(configTown.getAutoAttack() ? "true" : "false");
				xml.append("</autoattack>\r\n");
				
				xml.append("\t\t<autorecruit>");
				xml.append(configTown.getAutoRecruit() ? "true" : "false");
				xml.append("</autorecruit>\r\n");
				
				xml.append("\t\t<sells");
				HashMap<String, Double> sells = configTown.getSells();
				if (sells != null && sells.containsKey("wood")) {
					String rate = "";
					double value = sells.get("wood");
					if (value == 0D)
						rate = "0";
					else {
						rate = String.valueOf(value * 100);
						if (rate.length() > 2)
							rate = rate.substring(0, 2);					
					}
					
					xml.append(" wood=\"");
					xml.append(rate);
					xml.append("\"");
				}
				
				if (sells != null && sells.containsKey("food")) {
					String rate = "";
					double value = sells.get("food");
					if (value == 0D)
						rate = "0";
					else {
						rate = String.valueOf(value * 100);
						if (rate.length() > 2)
							rate = rate.substring(0, 2);					
					}
					
					xml.append(" food=\"");
					xml.append(rate);
					xml.append("\"");
				}
				
				if (sells != null && sells.containsKey("iron")) {
					String rate = "";
					double value = sells.get("iron");
					if (value == 0D)
						rate = "0";
					else {
						rate = String.valueOf(value * 100);
						if (rate.length() > 2)
							rate = rate.substring(0, 2);					
					}
					
					xml.append(" iron=\"");
					xml.append(rate);
					xml.append("\"");
				}
				
				if (sells != null && sells.containsKey("marble")) {
					String rate = "";
					double value = sells.get("marble");
					if (value == 0D)
						rate = "0";
					else {
						rate = String.valueOf(value * 100);
						if (rate.length() > 2)
							rate = rate.substring(0, 2);					
					}
					
					xml.append(" marble=\"");
					xml.append(rate);
					xml.append("\"");
				}
				
				if (sells != null && sells.containsKey("gold")) {
					String rate = "";
					double value = sells.get("gold");
					if (value == 0D)
						rate = "0";
					else {
						rate = String.valueOf(value * 100);
						if (rate.length() > 2)
							rate = rate.substring(0, 2);					
					}
					
					xml.append(" gold=\"");
					xml.append(rate);
					xml.append("\"");
				}
				
				xml.append(">");
				xml.append(sells != null ? "true" : "false");
				xml.append("</sells>\r\n");
				
				xml.append("\t\t<buys");
				HashMap<String, Double> buys = configTown.getBuys();
				if (buys != null && buys.containsKey("wood")) {
					String rate = "";
					double value = buys.get("wood");
					if (value == 0D)
						rate = "0";
					else {
						rate = String.valueOf(value * 100);
						if (rate.length() > 2)
							rate = rate.substring(0, 2);					
					}
					
					xml.append(" wood=\"");
					xml.append(rate);
					xml.append("\"");
				}
				
				if (buys != null && buys.containsKey("food")) {
					String rate = "";
					double value = buys.get("food");
					if (value == 0D)
						rate = "0";
					else {
						rate = String.valueOf(value * 100);
						if (rate.length() > 2)
							rate = rate.substring(0, 2);					
					}
					
					xml.append(" food=\"");
					xml.append(rate);
					xml.append("\"");
				}
				
				if (buys != null && buys.containsKey("iron")) {
					String rate = "";
					double value = buys.get("iron");
					if (value == 0D)
						rate = "0";
					else {
						rate = String.valueOf(value * 100);
						if (rate.length() > 2)
							rate = rate.substring(0, 2);					
					}
					
					xml.append(" iron=\"");
					xml.append(rate);
					xml.append("\"");
				}
				
				if (buys != null && buys.containsKey("marble")) {
					String rate = "";
					double value = buys.get("marble");
					if (value == 0D)
						rate = "0";
					else {
						rate = String.valueOf(value * 100);
						if (rate.length() > 2)
							rate = rate.substring(0, 2);					
					}
					
					xml.append(" marble=\"");
					xml.append(rate);
					xml.append("\"");
				}
				
				xml.append(">");
				xml.append(buys != null ? "true" : "false");
				xml.append("</buys>\r\n");				
				xml.append("\t</towns>\r\n");
			}
		}
		
		xml.append("</config>\r\n");		
		return xml.toString();
	}
}
