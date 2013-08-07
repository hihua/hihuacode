package com.gamemarket.servlet.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gamemarket.model.ModelTable;
import com.gamemarket.model.ModelUpgrade;
import com.gamemarket.util.Numeric;

public class WebUpgrade extends WebBase {	
	private final String mTemp = "temp";
	private final String mFileName = "gamemarket_%s.apk";

	@Override
	protected void onPost(final HttpServletRequest request, final HttpServletResponse response, final Map<String, Object> content) throws ServletException, IOException {
		final String command = request.getParameter("command");
		if (command == null) {
			setCode(content, 1);
			return;
		}
		
		if (command.equals("0")) {
			final Map<String, Object> admin = getAdmin(content, request);			
			if (admin != null) {				
				final Map<String, String> param = new HashMap<String, String>();
				param.put("upgrade_table", null);
				param.put("upgrade_force", null);			
				final String temp = getServletContext().getRealPath(mTemp);
				final String dir = getServletContext().getRealPath(Upgrade);
				final String rnd = Numeric.rndNumber(10);			
				if (!upload(request, param, temp, rnd)) {
					setCode(content, 2);
					return;
				}
				
				final File file = new File(temp + "\\" + rnd);
				if (!file.exists()) {
					setCode(content, 2);
					return;
				}
				
				if (file.length() == 0) {
					setCode(content, 2);
					return;					
				}
											
				final String upgradeTable = param.get("upgrade_table");
				if (!Numeric.isInteger(upgradeTable)) {
					file.delete();
					setCode(content, 3);
					return;
				}
				
				int upgradeForce = 0;
				if (param.get("upgrade_force") != null)
					upgradeForce = 1;
								
				final ModelTable table = new ModelTable();
				Map<String, Object> map = table.tableSelectAdmin(Integer.parseInt(upgradeTable));
				table.destroy();
				
				if (map != null) {
					if (checkAuthority(admin, map)) {
						final ModelUpgrade upgrade = new ModelUpgrade();
						map = upgrade.upgradeSelect(0);
						if (map != null) {
							final String filename = String.format(mFileName, upgradeTable);
							final File dest = new File(dir + "\\" + filename);
							dest.delete();							
							file.renameTo(dest);
							Object object = map.get("upgrade_version_code");
							final int upgradeVersionCode = Numeric.toInteger(object);
							object = map.get("upgrade_version_name");
							final String  upgradeVersionName = String.valueOf(object);
							final int now = getNow();
							if (!upgrade.upgradeInsert(Integer.parseInt(upgradeTable), upgradeVersionCode, upgradeVersionName, now, dest.getName(), upgradeForce))
								setCode(content, 1);
						} else
							setCode(content, 1);
												
						upgrade.destroy();
					} else {
						file.delete();
						setCode(content, 5);						
					}
				} else {
					file.delete();
					setCode(content, 4);					
				}					
			}			
				
			return;
		}
		
		if (command.equals("1")) {
			final String upgradeTable = getParameter(request, "upgrade_table");
			if (Numeric.isInteger(upgradeTable)) {
				final Map<String, Object> admin = getAdmin(content, request);			
				if (admin != null) {
					final ModelTable table = new ModelTable();
					Map<String, Object> map = table.tableSelectAdmin(Integer.parseInt(upgradeTable));
					table.destroy();
					
					if (map != null) {
						if (checkAuthority(admin, map)) {
							final ModelUpgrade upgrade = new ModelUpgrade();
							map = upgrade.upgradeSelect(Integer.parseInt(upgradeTable));
							if (map != null) {
								final Object object = map.get("upgrade_filename");
								final String filename = String.valueOf(object);
								final String dir = getServletContext().getRealPath(Upgrade);
								final File dest = new File(dir + "\\" + filename);
								dest.delete();
								
								if (!upgrade.upgradeDeleteTable(Integer.parseInt(upgradeTable)))
									setCode(content, 1);
							} else
								setCode(content, 2);
							
							upgrade.destroy();
						} else
							setCode(content, 3);
					} else
						setCode(content, 1);									
				}
				
				return;
			}			
		}
		
		if (command.equals("2")) {
			final Map<String, Object> admin = getAdmin(content, request);			
			if (admin != null) {
				Object object = admin.get("admin_id");
				final int adminId = Numeric.toInteger(object);
				object = admin.get("admin_parent");
				final int adminParent = Numeric.toInteger(object);
				
				final ModelUpgrade upgrade = new ModelUpgrade();
				final List<Map<String, Object>> list = upgrade.upgradeSelectAll(adminId, adminParent);				
				upgrade.destroy();
												
				if (list != null) {
					for (final Map<String, Object> map : list) {
						for (final Entry<String, Object> entry : map.entrySet()) {
							final String key = entry.getKey();
							if (key.equals("upgrade_filename")) {
								object = entry.getValue();
								final String upgradeFilename = String.valueOf(object);
								final String dir = getServletContext().getRealPath(Upgrade);
								final File file = new File(dir + "\\" + upgradeFilename);
								final long upgradeFileSize = file.length();
								map.put("upgrade_filesize", upgradeFileSize);
								break;
							}						
						}						
					}
					
					setContent(content, list);
				}
			}
			
			return;
		}
		
		if (command.equals("3")) {
			final Map<String, Object> admin = getAdmin(content, request);			
			if (admin != null) {
				Object object = admin.get("admin_id");
				final int adminId = Numeric.toInteger(object);
				object = admin.get("admin_parent");
				final int adminParent = Numeric.toInteger(object);
				
				final ModelUpgrade upgrade = new ModelUpgrade();
				final List<Map<String, Object>> list = upgrade.upgradeSelectTable(adminId, adminParent);
				upgrade.destroy();
				
				if (list != null)
					setContent(content, list);								
			}
			
			return;
		}
		
		if (command.equals("4")) {
			final String upgradeTable = getParameter(request, "upgrade_table");
			final String upgradeForce = getParameter(request, "upgrade_force");
			
			if (Numeric.isInteger(upgradeTable) && upgradeForce != null && (upgradeForce.equals("0") || upgradeForce.equals("1"))) {
				final Map<String, Object> admin = getAdmin(content, request);			
				if (admin != null) {
					final ModelTable table = new ModelTable();
					Map<String, Object> map = table.tableSelectAdmin(Integer.parseInt(upgradeTable));
					table.destroy();
					
					if (map != null) {
						if (checkAuthority(admin, map)) {
							final ModelUpgrade upgrade = new ModelUpgrade();
							if (!upgrade.upgradeUpdateForce(Integer.parseInt(upgradeTable), Integer.parseInt(upgradeForce)))
								setCode(content, 1);
							
							upgrade.destroy();
						} else
							setCode(content, 2);						
					} else
						setCode(content, 3);							
				} 
			}
			
			return;
		}
		
		setCode(content, 1);
	}	
}