package com.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.util.FileManager;
import com.util.Numeric;

public class Config {
	private String host;
	private String userName;
	private String password;
	private String cookie;
	private String authorization;
	private List<Long> cities;	
	private Boolean autoCity = false;
	private Long cityDelay;	
	private Boolean autoUpgrade = false;
	private List<Long> upgradePriority = null;
	private Boolean autoAttack = false;
	private Long attackTotal = 0L;
	private Long attackCount = 0L;
	private Long attackLevelMin = 0L;
	private Long attackLevelMax = 0L;
	private Boolean autoRecruit = false;	
	private Long infantrySwords = 0L;
	private Long infantryScout = 0L;
	private Long infantryCrossbow = 0L;
	private Long infantrySquire = 0L;
	private Long cavalryTemplar = 0L;
	private Long cavalryArcher = 0L;
	private Long cavalryPaladin = 0L;
	private Long cavalryRoyal = 0L;
	private Double marketRate = 0D;
			
	public String getHost() {
		return host;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getAuthorization() {
		return authorization;
	}
	
	public String getCookie() {
		return cookie;
	}

	public List<Long> getCities() {
		return cities;
	}

	public Boolean getAutoCity() {
		return autoCity;
	}

	public Long getCityDelay() {
		return cityDelay;
	}

	public Boolean getAutoUpgrade() {
		return autoUpgrade;
	}
	
	public List<Long> getUpgradePriority() {
		return upgradePriority;
	}

	public Boolean getAutoAttack() {
		return autoAttack;
	}

	public Long getAttackTotal() {
		return attackTotal;
	}

	public Long getAttackCount() {
		return attackCount;
	}

	public Long getAttackLevelMin() {
		return attackLevelMin;
	}

	public Long getAttackLevelMax() {
		return attackLevelMax;
	}

	public Boolean getAutoRecruit() {
		return autoRecruit;
	}
	
	public Long getInfantrySwords() {
		return infantrySwords;
	}

	public Long getInfantryScout() {
		return infantryScout;
	}

	public Long getInfantryCrossbow() {
		return infantryCrossbow;
	}

	public Long getInfantrySquire() {
		return infantrySquire;
	}

	public Long getCavalryTemplar() {
		return cavalryTemplar;
	}

	public Long getCavalryArcher() {
		return cavalryArcher;
	}

	public Long getCavalryPaladin() {
		return cavalryPaladin;
	}

	public Long getCavalryRoyal() {
		return cavalryRoyal;
	}
	
	public Double getMarketRate() {
		return marketRate;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

	public void setCities(List<Long> cities) {
		this.cities = cities;
	}

	public void setAutoCity(Boolean autoCity) {
		this.autoCity = autoCity;
	}

	public void setCityDelay(Long cityDelay) {
		this.cityDelay = cityDelay;
	}

	public void setAutoUpgrade(Boolean autoUpgrade) {
		this.autoUpgrade = autoUpgrade;
	}
	
	public void setUpgradePriority(List<Long> upgradePriority) {
		this.upgradePriority = upgradePriority;
	}

	public void setAutoAttack(Boolean autoAttack) {
		this.autoAttack = autoAttack;
	}

	public void setAttackTotal(Long attackTotal) {
		this.attackTotal = attackTotal;
	}

	public void setAttackCount(Long attackCount) {
		this.attackCount = attackCount;
	}

	public void setAttackLevelMin(Long attackLevelMin) {
		this.attackLevelMin = attackLevelMin;
	}

	public void setAttackLevelMax(Long attackLevelMax) {
		this.attackLevelMax = attackLevelMax;
	}

	public void setAutoRecruit(Boolean autoRecruit) {
		this.autoRecruit = autoRecruit;
	}
	
	public void setInfantrySwords(Long infantrySwords) {
		this.infantrySwords = infantrySwords;
	}

	public void setInfantryScout(Long infantryScout) {
		this.infantryScout = infantryScout;
	}

	public void setInfantryCrossbow(Long infantryCrossbow) {
		this.infantryCrossbow = infantryCrossbow;
	}

	public void setInfantrySquire(Long infantrySquire) {
		this.infantrySquire = infantrySquire;
	}

	public void setCavalryTemplar(Long cavalryTemplar) {
		this.cavalryTemplar = cavalryTemplar;
	}

	public void setCavalryArcher(Long cavalryArcher) {
		this.cavalryArcher = cavalryArcher;
	}

	public void setCavalryPaladin(Long cavalryPaladin) {
		this.cavalryPaladin = cavalryPaladin;
	}

	public void setCavalryRoyal(Long cavalryRoyal) {
		this.cavalryRoyal = cavalryRoyal;
	}
	
	public void setMarketRate(Double marketRate) {
		this.marketRate = marketRate;
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
	
	public static List<Config> getConfigs(InputStream in) {	
		Document document = loadXML(in);						
		if (document == null)			
			return null;
				
		Element elementRoot = document.getRootElement();
		Iterator<Element> iterator = elementRoot.elementIterator("configs");
		if (iterator != null) {
			List<Config> configs = new Vector<Config>();
			while (iterator.hasNext()) {
				try {
					Element element = iterator.next();
					Element host = element.element("host");
					Element userName = element.element("username");
					Element password = element.element("password");
					Element cookie = element.element("cookie");
					Element cities = element.element("cities");
					Element autoCities = element.element("autocities");
					Element autoUpgrade = element.element("autoupgrade");
					Element autoAttack = element.element("autoattack");					
					Element autoRecruit = element.element("autorecruit");					
					Element marketRate = element.element("marketrate");
					
					Config config = new Config();
					
					if (host == null || host.getText() == null)
						continue;
					else {
						String s = host.getText();
						if (s.length() == 0)
							continue;
						else
							config.setHost(s);					
					}
					
					if (userName == null || userName.getText() == null || password == null || password.getText() == null)
						continue;
					else {
						String u = userName.getText();
						String p = password.getText();
						if (u.length() == 0 || p.length() == 0)
							continue;
						else {
							config.setUserName(u);
							config.setPassword(p);
							String s = u + ":" + p;							
							s = new sun.misc.BASE64Encoder().encode(s.getBytes("UTF-8"));
							String authorization = "Basic " + s;
							config.setAuthorization(authorization);
						}
					}
					
					if (cookie == null)
						continue;
					else {
						String s = cookie.getText();
						if (s == null || s.length() == 0)
							continue;
						
						config.setCookie(s);
					}						
										
					if (cities == null || cities.getText() == null)
						continue;
					else {
						List<Long> list = new Vector<Long>();
						String s = cities.getText();
						if (s.indexOf(",") > -1) {
							String[] array = s.split(",");
							if (array == null)
								continue;
							else {								
								for (String city : array) {
									if (!Numeric.isNumber(city))
										continue;
									
									list.add(Long.parseLong(city));
								}																
							}
						} else {
							if (!Numeric.isNumber(s))
								continue;
							
							list.add(Long.parseLong(s));
						}
						
						if (list.size() == 0)
							continue;
						
						config.setCities(list);
					}
					
					if (autoCities == null || autoCities.getText() == null)
						continue;
					else {
						String s = autoCities.getText();
						if (!Numeric.isNumber(s))
							continue;
						
						long cityDelay = Long.parseLong(s);
						if (cityDelay > 0) {
							config.setAutoCity(true);
							config.setCityDelay(cityDelay);
						} else {
							config.setAutoCity(false);
							config.setCityDelay(0L);
						}
					}
					
					if (autoUpgrade != null && autoUpgrade.getText() != null) {
						String s = autoUpgrade.getText();
						if (s.equals("true")) {
							Attribute priority = autoUpgrade.attribute("priority");
							if (priority != null && priority.getText() != null) {
								s = priority.getText();
								if (s.indexOf(",") > -1) {
									String[] arrays = s.split(",");
									if (arrays != null) {
										List<Long> prioritys = new Vector<Long>();
										for (String array : arrays) {
											if (Numeric.isNumber(array))
												prioritys.add(Long.parseLong(array));
										}
										
										if (prioritys.size() > 0) {
											config.setUpgradePriority(prioritys);
											config.setAutoUpgrade(true);											
										} else
											config.setAutoUpgrade(false);
									} else
										config.setAutoUpgrade(false);
								} else {
									if (Numeric.isNumber(s)) {
										List<Long> prioritys = new Vector<Long>();
										prioritys.add(Long.parseLong(s));
										config.setUpgradePriority(prioritys);
										config.setAutoUpgrade(true);										
									} else
										config.setAutoUpgrade(false);																	
								}									
							} else
								config.setAutoUpgrade(false);														
						} else
							config.setAutoUpgrade(false);
					} else
						config.setAutoUpgrade(false);
					
					if (autoAttack != null && autoAttack.getText() != null) {
						String s = autoAttack.getText();
						if (Numeric.isNumber(s)) {
							long attackTotal = Long.parseLong(s);
							if (attackTotal > 0) {																							
								Attribute level = autoAttack.attribute("level");
								if (level != null && level.getText() != null) {
									s = level.getText();
									if (s.indexOf("-") > -1) {
										String[] array = s.split("-");
										if (array != null && array.length == 2 && Numeric.isNumber(array[0]) && Numeric.isNumber(array[1])) {
											config.setAutoAttack(true);
											config.setAttackTotal(attackTotal);
											config.setAttackLevelMin(Long.parseLong(array[0]));
											config.setAttackLevelMax(Long.parseLong(array[1]));
										} else {
											config.setAutoAttack(false);
											config.setAttackTotal(0L);
										}
									} else {
										config.setAutoAttack(false);
										config.setAttackTotal(0L);
									}
								} else {
									config.setAutoAttack(false);
									config.setAttackTotal(0L);
								}
							} else {
								config.setAutoAttack(false);
								config.setAttackTotal(0L);
							}
						} else {
							config.setAutoAttack(false);
							config.setAttackTotal(0L);
						}
					}
					
					if (autoRecruit != null && autoRecruit.getText() != null) {
						String s = autoRecruit.getText();
						Attribute infantrySwords = autoRecruit.attribute("infantryswords");
						Attribute infantryScout = autoRecruit.attribute("infantryscout");
						Attribute infantryCrossbow = autoRecruit.attribute("infantrycrossbow");
						Attribute infantrySquire = autoRecruit.attribute("infantrysquire");
						Attribute cavalryTemplar = autoRecruit.attribute("cavalrytemplar");
						Attribute cavalryArcher = autoRecruit.attribute("cavalryarcher");
						Attribute cavalryPaladin = autoRecruit.attribute("cavalrypaladin");
						Attribute cavalryRoyal = autoRecruit.attribute("cavalryroyal");
												
						if (s.equals("true"))
							config.setAutoRecruit(true);
						else
							config.setAutoRecruit(false);
												
						if (infantrySwords != null && Numeric.isNumber(infantrySwords.getText()) && Long.parseLong(infantrySwords.getText()) > 0)
							config.setInfantrySwords(Long.parseLong(infantrySwords.getText()));
						else
							config.setInfantrySwords(0L);
						
						if (infantryScout != null && Numeric.isNumber(infantryScout.getText()) && Long.parseLong(infantryScout.getText()) > 0)
							config.setInfantryScout(Long.parseLong(infantryScout.getText()));
						else
							config.setInfantryScout(0L);
						
						if (infantryCrossbow != null && Numeric.isNumber(infantryCrossbow.getText()) && Long.parseLong(infantryCrossbow.getText()) > 0)
							config.setInfantryCrossbow(Long.parseLong(infantryCrossbow.getText()));
						else
							config.setInfantryCrossbow(0L);
						
						if (infantrySquire != null && Numeric.isNumber(infantrySquire.getText()) && Long.parseLong(infantrySquire.getText()) > 0)
							config.setInfantrySquire(Long.parseLong(infantrySquire.getText()));
						else
							config.setInfantrySquire(0L);
						
						if (cavalryTemplar != null && Numeric.isNumber(cavalryTemplar.getText()) && Long.parseLong(cavalryTemplar.getText()) > 0)
							config.setCavalryTemplar(Long.parseLong(cavalryTemplar.getText()));
						else
							config.setCavalryTemplar(0L);
						
						if (cavalryArcher != null && Numeric.isNumber(cavalryArcher.getText()) && Long.parseLong(cavalryArcher.getText()) > 0)
							config.setCavalryArcher(Long.parseLong(cavalryArcher.getText()));
						else
							config.setCavalryArcher(0L);
						
						if (cavalryPaladin != null && Numeric.isNumber(cavalryPaladin.getText()) && Long.parseLong(cavalryPaladin.getText()) > 0)
							config.setCavalryPaladin(Long.parseLong(cavalryPaladin.getText()));
						else
							config.setCavalryPaladin(0L);
						
						if (cavalryRoyal != null && Numeric.isNumber(cavalryRoyal.getText()) && Long.parseLong(cavalryRoyal.getText()) > 0)
							config.setCavalryRoyal(Long.parseLong(cavalryRoyal.getText()));
						else
							config.setCavalryRoyal(0L);
					} else
						config.setAutoRecruit(false);
															
					if (marketRate != null && marketRate.getText() != null) {
						String s = marketRate.getText();
						if (Numeric.isNumber(s)) {
							Double rate = Double.parseDouble(s) / 100D;
							config.setMarketRate(rate);
						} else
							config.setMarketRate(0D);
					} else
						config.setMarketRate(0D);
											
					configs.add(config);
				} catch (UnsupportedEncodingException e) {
					
				} catch (NumberFormatException e) {
					
				} catch (NullPointerException e) {
					
				}
			}
			
			if (configs.size() > 0) {			
				return configs;
			} else 
				return null;
		} else
			return null;
	}
	
	public static List<Config> getConfigs(String path) {
		InputStream in = null;
		
		try {
			in = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			return null;
		}
		
		List<Config> configs = getConfigs(in);
		
		try {
			in.close();
		} catch (IOException e) {
			return null;
		}
		
		return configs;
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
	
	public static String setConfig(List<Config> configs) {
		StringBuilder xml = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n\r\n<config>\r\n");
		
		for (Config config : configs) {
			xml.append("\t<configs>\r\n");
			xml.append("\t\t<host>");
			xml.append(config.getHost() != null ? config.getHost() : "");
			xml.append("</host>\r\n");
			xml.append("\t\t<username>");
			xml.append(config.getUserName() != null ? config.getUserName() : "");
			xml.append("</username>\r\n");			
			xml.append("\t\t<password>");
			xml.append(config.getPassword() != null ? config.getPassword() : "");
			xml.append("</password>\r\n");
			xml.append("\t\t<cookie>");
			xml.append(config.getCookie() != null ? config.getCookie() : "");
			xml.append("</cookie>\r\n");
			
			StringBuilder cities = new StringBuilder();
			if (config.getCities() != null) {
				for (Long city : config.getCities()) {
					cities.append(",");
					cities.append(city);
				}
			}
			
			if (cities.length() > 0)
				cities.deleteCharAt(0);
			
			xml.append("\t\t<cities>");
			xml.append(cities);
			xml.append("</cities>\r\n");
			xml.append("\t\t<autocities>");
			xml.append(config.getCityDelay() != null ? config.getCityDelay() : "");
			xml.append("</autocities>\r\n");
			
			StringBuilder upgradePriority = new StringBuilder();
			if (config.getUpgradePriority() != null) {
				for (Long priority : config.getUpgradePriority()) {
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
			if (config.getAttackLevelMin() != null && config.getAttackLevelMax() != null) {
				attackLevel.append(config.getAttackLevelMin());
				attackLevel.append("-");
				attackLevel.append(config.getAttackLevelMax());
			}
									
			xml.append("\t\t<autoattack level=\"");
			xml.append(attackLevel);
			xml.append("\">");
			xml.append(config.getAutoAttack() ? "true" : "false");
			xml.append("</autoattack>\r\n");
			
			xml.append("\t\t<autorecruit infantryswords=\"");
			xml.append(config.getInfantrySwords() != null ? config.getInfantrySwords() : "");
			xml.append("\" infantryscout=\"");
			xml.append(config.getInfantryScout() != null ? config.getInfantryScout() : "");
			xml.append("\" infantrycrossbow=\"");
			xml.append(config.getInfantryCrossbow() != null ? config.getInfantryCrossbow() : "");
			xml.append("\" infantrysquire=\"");
			xml.append(config.getInfantrySquire() != null ? config.getInfantrySquire() : "");
			xml.append("\" cavalrytemplar=\"");
			xml.append(config.getCavalryTemplar() != null ? config.getCavalryTemplar() : "");
			xml.append("\" cavalryarcher=\"");
			xml.append(config.getCavalryArcher() != null ? config.getCavalryArcher() : "");
			xml.append("\" cavalrypaladin=\"");
			xml.append(config.getCavalryPaladin() != null ? config.getCavalryPaladin() : "");
			xml.append("\" cavalryroyal=\"");
			xml.append(config.getCavalryRoyal() != null ? config.getCavalryRoyal() : "");
			xml.append("\">");
			xml.append(config.getAutoRecruit() ? "true" : "false");
			xml.append("</autorecruit>\r\n");
			
			String marketRate = "";
			if (config.getMarketRate() != null) {
				marketRate = String.valueOf(config.getMarketRate() * 100);
				if (marketRate.length() > 2)
					marketRate = marketRate.substring(0, 2);				
			}
			
			xml.append("\t\t<marketrate>");
			xml.append(marketRate);
			xml.append("</marketrate>\r\n");
			xml.append("\t</configs>\r\n");
		}
		
		xml.append("</config>");
		return xml.toString();
	}
}
