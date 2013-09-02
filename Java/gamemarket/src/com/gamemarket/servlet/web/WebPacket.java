package com.gamemarket.servlet.web;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gamemarket.model.ModelPacket;
import com.gamemarket.model.ModelTable;
import com.gamemarket.packet.Packet;
import com.gamemarket.util.Numeric;

public class WebPacket extends WebBase {
	private final String Src = "src";
	private final String Dest = "dest";
	private final String Entry = "assets\\entry.xml";
	
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
													
							final Object object = map.get("packet_status");
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
														
														if (!packet.copyFile(from, to))
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
		
		setCode(content, 1);
	}
}
