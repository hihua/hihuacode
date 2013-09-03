package com.gamemarket.servlet.web;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gamemarket.model.ModelPacket;
import com.gamemarket.model.ModelTable;
import com.gamemarket.model.ModelUpgrade;
import com.gamemarket.packet.Packet;
import com.gamemarket.util.DateTime;
import com.gamemarket.util.Numeric;

public class WebPacket extends WebBase {
	private final String Src = "src";
	private final String Dest = "dest";
	private final String Entry = "assets\\entry.xml";
	private final String Manifest = "AndroidManifest.xml";
	
	private String getBatPath() {
		final String path = getServletContext().getRealPath("..\\..\\packet\\packet.bat");
		final File file = new File(path);
		if (file.exists())
			return file.getAbsolutePath();
		else
			return null;
	}
	
	private String getOutPath() {
		return getServletContext().getRealPath("..\\..\\packet\\out");
	}
	
	private String getProjectPath() {
		return getServletContext().getRealPath("..\\..\\packet\\project");
	}
	
	private String getUploadPath() {
		return getServletContext().getRealPath("..\\..\\packet\\upload");
	}
	
	private String getSrcPath(final String guid) {
		final String path = getOutPath() + "\\" + guid + "\\" + Src;
		final File file = new File(path);
		if (file.mkdirs())
			return path;
		else
			return null;
	}
	
	private String getDestPath(final String guid) {
		final String path = getOutPath() + "\\" + guid + "\\" + Dest;
		final File file = new File(path);
		if (file.mkdirs())
			return path;
		else
			return null;
	}
	
	private String getPath(final String guid) {
		final String path = getOutPath() + "\\" + guid + "\\";
		final File file = new File(path);
		if (file.exists())
			return null;
		else
			return path;
	}
	
	private String getEntryPath(final String src) {
		final String path = src + "\\" + Entry;
		final File file = new File(path);
		if (file.exists())
			return path;
		else
			return null;
	}
	
	private String getManifestPath(final String src) {
		final String path = src + "\\" + Manifest;
		final File file = new File(path);
		if (file.exists())
			return path;
		else
			return null;
	}

	@Override
	protected void onPost(final HttpServletRequest request, final HttpServletResponse response, final Map<String, Object> content) throws ServletException, IOException {
		final String command = getParameter(request, "command");
		if (command == null) {
			setCode(content, 1);
			return;
		}
				
		if (command.equals("0")) {
			final String tableId = getParameter(request, "table_id");
			if (checkString(tableId)) {
				final Map<String, Object> admin = getAdmin(content, request);
				if (admin != null) {
					final ModelTable modelTable = new ModelTable();
					Map<String, Object> map = modelTable.tableSelectAdmin(Integer.parseInt(tableId));
					modelTable.destroy();
					
					if (map != null) {
						if (checkAuthority(admin, map)) {												
							final ModelPacket modelPacket = new ModelPacket();
							map = modelPacket.packetSelect();
							modelPacket.destroy();
																				
							Object object = map.get("packet_status");
							if (Numeric.toInteger(object) == 0) {			
								String guid = UUID.randomUUID().toString();
								String path = null;
								while ((path = getPath(guid)) == null)
									guid = UUID.randomUUID().toString();
								
								final String srcpath = getSrcPath(guid);
								final String destpath = getDestPath(guid);																		
								if (srcpath != null && destpath != null) {
									final String project = getProjectPath();
									final Packet packet = new Packet();
									if (packet.newPacket(project, srcpath)) {
										final String entry = getEntryPath(srcpath);
										if (entry != null) {
											if (packet.setEntry(entry, Integer.parseInt(tableId))) {
												final String bat = getBatPath();													
												final String outfile = "gamemarket_" + tableId + ".apk";
												
												if (packet.execute(bat, destpath, outfile, srcpath)) {
													final File from = new File(destpath, outfile);
													if (from.exists()) {
														final String dir = getServletContext().getRealPath(Upgrade);
														final File to = new File(dir, outfile);
														if (to.exists())
															to.delete();
														
														if (packet.copyFile(from, to)) {
															object = map.get("version_code");
															final int versionCode = Numeric.toInteger(object);
															object = map.get("version_name");
															final String versionName = String.valueOf(object);
															final int now = getNow();
															
															final ModelUpgrade modelUpgrade = new ModelUpgrade();
															if (!modelUpgrade.upgradeInsert(Integer.parseInt(tableId), versionCode, versionName, now, outfile, 0))
																setCode(content, 12);
															
															modelUpgrade.destroy();												
														} else
															setCode(content, 11);
													} else
														setCode(content, 10);
												} else
													setCode(content, 9);
											} else
												setCode(content, 8);
										} else
											setCode(content, 7);
									} else
										setCode(content, 6);
									
									final File dir = new File(path);
									packet.deleteDirectory(dir);
								} else
									setCode(content, 5);
							} else
								setCode(content, 4);														
						} else
							setCode(content, 3);
					} else
						setCode(content, 2);										
				}
				
				return;
			}
		}
		
		if (command.equals("1")) {
			final Map<String, Object> admin = getAdmin(content, request);
			if (admin != null) {
				if (isAdmin(admin)) {
					final ModelPacket modelPacket = new ModelPacket();
					final Map<String, Object> map = modelPacket.packetSelect();
					modelPacket.destroy();
					
					if (map != null) {
						final Object object = map.get("packet_filename");
						if (object != null) {
							final String filename = String.valueOf(object);
							final String dir = getServletContext().getRealPath(Upgrade);
							final File file = new File(dir, filename);
							if (file.exists()) {
								final long lastModified = file.lastModified();
								final Date date = new Date(lastModified);
								final long timestamp = DateTime.getTimeStamp(date);
								map.put("packet_lasttime", timestamp);
							}
						}
						
						setContent(content, map);
					} else
						setCode(content, 3);
				} else
					setCode(content, 2);				
			}
			
			return;
		}
		
		if (command.equals("2")) {
			final String versionCode = getParameter(request, "version_code");
			String versionName = getParameter(request, "version_name");
			
			if (Numeric.isNumber(versionCode) && Numeric.isInteger(versionCode) && checkString(versionName)) {
				final Map<String, Object> admin = getAdmin(content, request);
				if (admin != null) {
					if (isAdmin(admin)) {
						final ModelPacket modelPacket = new ModelPacket();
						final Map<String, Object> map = modelPacket.packetSelect();
												
						if (map != null) {
							Object object = map.get("packet_status");						
							final int packetStatus = Numeric.toInteger(object);
							if (packetStatus != 3) {
								String guid = UUID.randomUUID().toString();
								String path = null;
								while ((path = getPath(guid)) == null)
									guid = UUID.randomUUID().toString();
								
								final String destpath = getDestPath(guid);
								if (destpath != null) {								
									object = map.get("packet_filename");
									if (object != null) {
										final String filename = String.valueOf(object);
										final String dir = getServletContext().getRealPath(Upgrade);
										final File file = new File(dir, filename);
										if (file.exists())
											file.delete();																		
									}
									
									final Packet packet = new Packet();
									versionName = cutString(versionName, 50);
									if (modelPacket.packetUpdate(Integer.parseInt(versionCode), versionName, 1, null)) {
										final String project = getProjectPath();
										final String upload = getUploadPath();										
										final File directory = new File(project);										
										if (packet.deleteDirectory(directory) && directory.mkdirs()) {
											if (packet.newPacket(upload, project)) {
												final String entry = getEntryPath(project);
												if (entry != null) {
													if (packet.setEntry(entry, 0)) {
														final String manifest = getManifestPath(project);
														if (manifest != null) {
															if (packet.setVersion(manifest, Integer.parseInt(versionCode), versionName)) {
																final String date = DateTime.getDateTime(new Date(), "yyyyMMddHHmmss");
																final String bat = getBatPath();													
																final String outfile = "gamemarket_" + date + ".apk";
																
																if (packet.execute(bat, destpath, outfile, project)) {
																	final File from = new File(destpath, outfile);
																	if (from.exists()) {
																		final String dir = getServletContext().getRealPath(Upgrade);
																		final File to = new File(dir, outfile);
																		if (to.exists())
																			to.delete();
																		
																		if (packet.copyFile(from, to)) {
																			if (!modelPacket.packetUpdate(Integer.parseInt(versionCode), versionName, 2, outfile))
																				setCode(content, 16);	
																		} else
																			setCode(content, 15);
																	} else
																		setCode(content, 14);
																} else
																	setCode(content, 13);
															} else
																setCode(content, 12);
														} else
															setCode(content, 11);
													} else
														setCode(content, 10);
												} else
													setCode(content, 9);
											} else
												setCode(content, 8);
										} else
											setCode(content, 7);
									} else
										setCode(content, 6);
									
									final File dir = new File(path);									
									packet.deleteDirectory(dir);
								} else
									setCode(content, 5);
							} else
								setCode(content, 4);
						} else
							setCode(content, 3);
						
						modelPacket.destroy();
					} else
						setCode(content, 2);
				}
				
				return;	
			}					
		}
		
		if (command.equals("3")) {
			final String upgradeTables = getParameter(request, "upgrade_tables");
			if (checkString(upgradeTables)) {
				String[] upgradeTable = null;
				final int p = upgradeTables.indexOf(",");				
				if (p > -1)
					upgradeTable = upgradeTables.split(",");
				else {
					upgradeTable = new String[1];
					upgradeTable[0] = upgradeTables;
				}

				if (upgradeTable != null && upgradeTable.length > 0) {
					for (final String table : upgradeTable) {
						if (!Numeric.isNumber(table) || !Numeric.isInteger(table)) {
							setCode(content, 1);
							return;
						}
					}
				} else {
					setCode(content, 1);
					return;
				}			
				
				final Map<String, Object> admin = getAdmin(content, request);
				if (admin != null) {
					if (isAdmin(admin)) {
						final ModelPacket modelPacket = new ModelPacket();
						Map<String, Object> map = modelPacket.packetSelect();
																								
						Object object = map.get("packet_status");						
						final int packetStatus = Numeric.toInteger(object);
						if (packetStatus == 2 || packetStatus == 3) {
							final Packet packet = new Packet();
							object = map.get("version_code");
							final int versionCode = Numeric.toInteger(object);
							object = map.get("version_name");
							final String versionName = String.valueOf(object);							
							object = map.get("packet_filename");
							String packetFilename = null; 
							if (object != null)
								packetFilename = String.valueOf(object);																										
																					
							String guid = UUID.randomUUID().toString();
							String path = null;
							while ((path = getPath(guid)) == null)
								guid = UUID.randomUUID().toString();
							
							final String srcpath = getSrcPath(guid);
							final String destpath = getDestPath(guid);
							if (srcpath != null && destpath != null) {																
								final String project = getProjectPath();								
								if (packet.newPacket(project, srcpath)) {
									if (modelPacket.packetUpdate(versionCode, versionName, 3, packetFilename)) {
										final ModelUpgrade modelUpgrade = new ModelUpgrade();										
										for (final String table : upgradeTable) {
											map = modelUpgrade.upgradeSelect(Integer.parseInt(table));								
											if (map != null) {
												final String entry = getEntryPath(srcpath);
												if (entry != null) {
													if (packet.setEntry(entry, Integer.parseInt(table))) {
														final String manifest = getManifestPath(srcpath);
														if (manifest != null) {
															if (packet.setVersion(manifest, versionCode, versionName)) {														
																final String bat = getBatPath();													
																final String outfile = "gamemarket_" + table + ".apk";
																
																if (packet.execute(bat, destpath, outfile, srcpath)) {																
																	final File from = new File(destpath, outfile);
																	if (from.exists()) {
																		final String dir = getServletContext().getRealPath(Upgrade);
																		final File to = new File(dir, outfile);
																		if (to.exists())
																			to.delete();
																		
																		final int now = getNow();
																		if (packet.copyFile(from, to))
																			modelUpgrade.upgradeInsert(Integer.parseInt(table), versionCode, versionName, now, outfile, 0);																	
																	}
																}																												
															}
														}
													}												
												}
											}
											
											final File directory = new File(destpath);	
											packet.deleteDirectory(directory);
											directory.mkdirs();
										}
										
										if (packetFilename != null) {
											final String dir = getServletContext().getRealPath(Upgrade);
											final File file = new File(dir, packetFilename);
											if (file.exists())
												file.delete();								
										}
										
										modelPacket.packetUpdate(versionCode, versionName, 0, null);
										modelUpgrade.destroy();
									} else
										setCode(content, 6);
								} else
									setCode(content, 5);
							} else
								setCode(content, 4);
							
							final File dir = new File(path);									
							packet.deleteDirectory(dir);
						} else
							setCode(content, 3);
						
						modelPacket.destroy();
					} else
						setCode(content, 2);
				}
			}
						
			return;
		}
		
		setCode(content, 1);
	}		
}
