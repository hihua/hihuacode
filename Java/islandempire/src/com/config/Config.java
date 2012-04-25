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
	private String cookie;
	private Long autoTowns;
	private List<ConfigTown> configTowns;

	public String getHost() {
		return host;
	}

	public String getClientv() {
		return clientv;
	}

	public String getCookie() {
		return cookie;
	}

	public Long getAutoTowns() {
		return autoTowns;
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

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public void setAutoTowns(Long autoTowns) {
		this.autoTowns = autoTowns;
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
					
					configTown.setId(Long.parseLong(tmp));
					
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
						if (Numeric.isNumber(tmp)) {
							long attackTotal = Long.parseLong(tmp);
							if (attackTotal > 0) {																							
								Attribute level = element.attribute("level");
								if (level != null && level.getText() != null) {
									tmp = level.getText();
									if (tmp.indexOf("-") > -1) {
										String[] array = tmp.split("-");
										if (array != null && array.length == 2 && Numeric.isNumber(array[0]) && Numeric.isNumber(array[1])) {
											configTown.setAutoAttack(true);
											configTown.setAttackTotal(attackTotal);
											configTown.setAttackLevelMin(Long.parseLong(array[0]));
											configTown.setAttackLevelMax(Long.parseLong(array[1]));
										} else {
											configTown.setAutoAttack(false);
											configTown.setAttackTotal(0L);
										}
									} else {
										configTown.setAutoAttack(false);
										configTown.setAttackTotal(0L);
									}
								} else {
									configTown.setAutoAttack(false);
									configTown.setAttackTotal(0L);
								}
							} else {
								configTown.setAutoAttack(false);
								configTown.setAttackTotal(0L);
							}
						} else {
							configTown.setAutoAttack(false);
							configTown.setAttackTotal(0L);
						}
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
}
