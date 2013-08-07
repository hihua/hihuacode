package com.gamemarket.servlet.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gamemarket.model.ModelAdmin;
import com.gamemarket.model.ModelEntry;
import com.gamemarket.model.ModelRecord;
import com.gamemarket.model.ModelTable;
import com.gamemarket.model.ModelUpgrade;
import com.gamemarket.util.Numeric;

public class WebAdmin extends WebBase {

	@Override
	protected void onPost(final HttpServletRequest request, final HttpServletResponse response, final Map<String, Object> content) throws ServletException, IOException {				
		final String command = request.getParameter("command");
		if (command == null) {
			setCode(content, 1);
			return;
		}
		
		if (command.equals("0")) {
			final Map<String, Object> map = getAdmin(content, request);
			if (map != null)
				setContent(content, map);
			
			return;
		}
		
		if (command.equals("1")) {
			final String username = getParameter(request, "username");
			final String password = getParameter(request, "password");
			final String code = getParameter(request, "code");
			if (!checkString(username) || !checkString(password) || !checkString(code)) {
				setCode(content, 2);
				return;
			}
			
			final HttpSession session = request.getSession();
			if (session == null) {
				setCode(content, 3);
				return;
			}
			
			final Object object = session.getAttribute("admin_code");
			if (object == null) {
				setCode(content, 3);
				return;
			}
			
			final String adminCode = String.valueOf(object);
			if (!adminCode.equals(code)) {
				session.removeAttribute("admin_code");
				setCode(content, 3);
				return;
			}
			
			session.removeAttribute("admin_code");									
			final ModelAdmin modelAdmin = new ModelAdmin();
			final Map<String, Object> map = modelAdmin.adminSelect(username, password);
			modelAdmin.destroy();
			
			if (map != null) {
				if (session != null)
					session.setAttribute("admin", map);					
			} else
				setCode(content, 1);				
									
			return;			
		}
		
		if (command.equals("2")) {						
			final HttpSession session = request.getSession();
			if (session != null)
				session.removeAttribute("admin");
			
			return;
		}
		
		if (command.equals("3")) {
			final HttpSession session = request.getSession();
			if (session != null) {
				final Map<String, Object> admin = getAdmin(content, request);
				if (admin != null) {
					if (isAdmin(admin)) {
						final ModelAdmin modelAdmin = new ModelAdmin();
						final List<Map<String, Object>> list = modelAdmin.adminSelectAll();
						modelAdmin.destroy();
						
						if (list != null) {
							final List<Map<String, Object>> lists = new Vector<Map<String,Object>>();
							final Map<Integer, Map<String, Object>> parents = new TreeMap<Integer, Map<String,Object>>();
							final Map<Integer, List<Map<String, Object>>> childs = new HashMap<Integer, List<Map<String,Object>>>();
							for (Map<String, Object> m : list) {
								Object object = m.get("admin_id");
								final Integer adminId = Numeric.toInteger(object);
								
								object = m.get("admin_parent");
								final Integer adminParent = Numeric.toInteger(object);
								
								if (isManager(m))
									parents.put(adminId, m);
								else {
									if (childs.containsKey(adminParent)) {
										final List<Map<String, Object>> ls = childs.get(adminParent);
										ls.add(m);											
									} else {
										final List<Map<String, Object>> ls = new Vector<Map<String,Object>>();
										ls.add(m);
										childs.put(adminParent, ls);
									}
								}											
							}
							
							for (Entry<Integer, Map<String, Object>> entry : parents.entrySet()) {
								final Integer key = entry.getKey();
								final Map<String, Object> value = entry.getValue();
								lists.add(value);
								if (childs.containsKey(key)) {
									final List<Map<String, Object>> ls = childs.get(key);
									lists.addAll(ls);
								}
							}
							
							setContent(content, lists);
						}
					} else
						setCode(content, 2);
				}
				
				return;
			}
		}
		
		if (command.equals("4")) {
			final String adminId = getParameter(request, "admin_id");
			if (Numeric.isInteger(adminId) && Integer.parseInt(adminId) > 1) {
				final Map<String, Object> admin = getAdmin(content, request);
				if (admin != null) {
					if (isAdmin(admin) || isManager(admin)) {
						final ModelAdmin modelAdmin = new ModelAdmin();
						final Map<String, Object> map = modelAdmin.adminSelectId(Integer.parseInt(adminId));
						if (map != null) {
							if (isManager(admin)) {							
								if (!isUser(map) || !checkAuthority(admin, map))								
									setCode(content, 2);								
							}
							
							if (!delAll(modelAdmin, map))
								setCode(content, 1);
						} else
							setCode(content, 3);
						
						modelAdmin.destroy();
					} else
						setCode(content, 2);
				}
				
				return;
			}
		}
		
		if (command.equals("5")) {
			final String adminId = getParameter(request, "admin_id");
			final String adminDesc = getParameter(request, "admin_desc");
			
			if (Numeric.isInteger(adminId) && checkString(adminDesc)) {
				final Map<String, Object> admin = getAdmin(content, request);
				if (admin != null) {
					final ModelAdmin modelAdmin = new ModelAdmin();
					final Map<String, Object> map = modelAdmin.adminSelectId(Integer.parseInt(adminId));
										
					if (map != null) {
						if (checkAuthority(admin, map)) {							
							if (modelAdmin.adminUpdateDesc(Integer.parseInt(adminId), adminDesc)) {
								final int aId = getAdminId(admin);
								final int mId = getAdminId(map);
								if (aId == mId)									
									admin.put("admin_desc", adminDesc);
							} else
								setCode(content, 1);														
						} else
							setCode(content, 2);
					} else
						setCode(content, 3);
					
					modelAdmin.destroy();
				}
				
				return;
			}
		}
		
		if (command.equals("6")) {
			final String adminUsername = getParameter(request, "admin_username");
			final String adminPassword = getParameter(request, "admin_password");
			final String adminDesc = getParameter(request, "admin_desc");
			
			if (checkString(adminUsername) && checkString(adminPassword)) {
				final Map<String, Object> admin = getAdmin(content, request);
				if (admin != null) {
					if (isAdmin(admin) || isManager(admin)) {						
						final ModelAdmin modelAdmin = new ModelAdmin();
						final int count = modelAdmin.adminExists(adminUsername);
						if (count == 0) {
							final int adminId = getAdminId(admin);
							final int now = getNow();
							if (!modelAdmin.adminInsert(adminUsername, adminPassword, adminId, adminDesc, now))
								setCode(content, 1);
						} else
							setCode(content, 3);
						
						modelAdmin.destroy();
					} else
						setCode(content, 2);
				}
				
				return;
			}
		}
		
		if (command.equals("7")) {
			final Map<String, Object> admin = getAdmin(content, request);
			if (admin != null) {
				if (isManager(admin)) {									
					final int adminId = getAdminId(admin);
					final ModelAdmin modelAdmin = new ModelAdmin();
					final List<Map<String, Object>> list = modelAdmin.adminSelectParent(adminId);
					modelAdmin.destroy();
					
					if (list != null)
						setContent(content, list);																		
				} else
					setCode(content, 2);
			}
			
			return;
		}
		
		if (command.equals("8")) {
			final String adminId = getParameter(request, "admin_id");
			final String adminOldPassword = getParameter(request, "admin_oldpassword");
			final String adminPassword = getParameter(request, "admin_password");
						
			if (Numeric.isInteger(adminId) && checkString(adminPassword)) {
				final Map<String, Object> admin = getAdmin(content, request);
				if (admin != null) {
					final ModelAdmin modelAdmin = new ModelAdmin();
					final Map<String, Object> map = modelAdmin.adminSelectId(Integer.parseInt(adminId));
					if (map != null) {
						if (checkAuthority(admin, map)) {
							final int aId = getAdminId(admin);
							final int mId = getAdminId(map);							
							if (aId == mId) {
								if (checkString(adminOldPassword)) {
									final String adminUsername = getAdminUsername(map);
									if (modelAdmin.adminSelect(adminUsername, adminOldPassword) != null) {
										if (!modelAdmin.adminUpdatePassword(Integer.parseInt(adminId), adminPassword))
											setCode(content, 1);
									} else
										setCode(content, 3);
								} else
									setCode(content, 4);														
							} else {
								if (isAdmin(admin) || isManager(admin)) {
									if (!modelAdmin.adminUpdatePassword(Integer.parseInt(adminId), adminPassword))
										setCode(content, 1);
								} else
									setCode(content, 2);
							}																							
						} else
							setCode(content, 2);						
					} else
						setCode(content, 5);
										
					modelAdmin.destroy();
				}
				
				return;
			}			
		}
		
		setCode(content, 1);		
	}
	
	private boolean delAll(final ModelAdmin modelAdmin, final Map<String, Object> admin) {
		final int adminId = getAdminId(admin);
		final int adminParent = getAdminParent(admin);		
				
		final ModelTable table = new ModelTable();
		final ModelEntry entry = new ModelEntry();
		final ModelRecord record = new ModelRecord();
		final ModelUpgrade upgrade = new ModelUpgrade();
				
		final List<Map<String, Object>> list = table.tableSelect(adminId, adminParent);
		if (list != null) {
			for (final Map<String, Object> map : list) {
				Object object = map.get("table_id");
				final int tableId = Numeric.toInteger(object);
				
				final Map<String, Object> m = upgrade.upgradeSelect(tableId);
				if (m != null) {
					object = m.get("upgrade_filename");
					final String upgradeFilename = String.valueOf(object);
					final String dir = getServletContext().getRealPath(Upgrade);
					final File dest = new File(dir + "\\" + upgradeFilename);
					dest.delete();
				}
				
				entry.entryDeleteTable(tableId);
				record.recordDeleteTable(tableId);
				upgrade.upgradeDeleteTable(tableId);
				table.tableDelete(tableId);
			}
		}
		
		if (isManager(admin))
			modelAdmin.adminDeleteParent(adminId);
		
		return modelAdmin.adminDelete(adminId);
	}		
}
