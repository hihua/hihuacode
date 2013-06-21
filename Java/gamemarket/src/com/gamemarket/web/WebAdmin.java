package com.gamemarket.web;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.gamemarket.util.Numeric;

public class WebAdmin extends WebBase {

	@Override
	protected void onPost(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws ServletException, IOException {				
		final String command = request.getParameter("command");
		if (command == null) {
			setCode(1);
			return;
		}
		
		if (command.equals("0")) {
			final HttpSession session = request.getSession();
			if (session != null) {
				final Object object = session.getAttribute("admin");
				if (object != null)
					setResult(object);
				
				return;
			}
		}
		
		if (command.equals("1")) {
			final String username = getParameter(request, "username");
			final String password = getParameter(request, "password");
			final String code = getParameter(request, "code");
			if (!checkString(username) || !checkString(password) || !checkString(code)) {
				setCode(2);
				return;
			}
			
			final HttpSession session = request.getSession();
			if (session == null) {
				setCode(3);
				return;
			}
			
			final Object object = session.getAttribute("admin_code");
			if (object == null) {
				setCode(3);
				return;
			}
			
			final String adminCode = String.valueOf(object);
			if (!adminCode.equals(code)) {
				session.removeAttribute("admin_code");
				setCode(3);
				return;
			}
			
			session.removeAttribute("admin_code");									
			final ModelAdmin admin = new ModelAdmin();
			final Map<String, Object> map = admin.adminSelect(username, password);
			if (map != null) {
				if (session != null)
					session.setAttribute("admin", map);					
			} else
				setCode(1);				
						
			admin.destroy();
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
				Object object = session.getAttribute("admin");
				if (object != null) {
					Map<String, Object> map = (Map<String, Object>) object;
					if (map.containsKey("admin_parent")) {
						object = map.get("admin_parent");
						if (object != null) {
							final int admin_parent = Numeric.toInteger(object);
							if (admin_parent == 0) {
								final ModelAdmin admin = new ModelAdmin();
								final List<Map<String, Object>> list = admin.adminAllSelect();
								if (list != null) {
									final List<Map<String, Object>> lists = new Vector<Map<String,Object>>();
									final Map<Integer, Map<String, Object>> parents = new TreeMap<Integer, Map<String,Object>>();
									final Map<Integer, List<Map<String, Object>>> childs = new HashMap<Integer, List<Map<String,Object>>>();
									for (Map<String, Object> m : list) {
										if (!m.containsKey("admin_id"))
											continue;
										
										if (!m.containsKey("admin_parent"))
											continue;
										
										object = m.get("admin_id");
										final Integer adminId = Numeric.toInteger(object);
										
										object = m.get("admin_parent");
										final Integer adminParent = Numeric.toInteger(object);
										if (adminParent == 1) {
											parents.put(adminId, m);
										} else {
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
									
									setResult(lists);
								}
																
								admin.destroy();
								return;
							}
						}						
					}
				}
			}
		}
		
		if (command.equals("4")) {
			final String adminId = getParameter(request, "admin_id");
			if (Numeric.isNumber(adminId) && Numeric.isInteger(adminId) && Integer.parseInt(adminId) > 1) {
				final HttpSession session = request.getSession();
				if (session != null) {
					Object object = session.getAttribute("admin");
					if (object != null) {
						Map<String, Object> map = (Map<String, Object>) object;
						if (map.containsKey("admin_parent")) {
							object = map.get("admin_parent");
							final int adminParent = Numeric.toInteger(object);
							if (adminParent == 0 || adminParent == 1) {
								final ModelAdmin admin = new ModelAdmin();
								if (adminParent == 1) {
									object = session.getAttribute("admin");
									map = (Map<String, Object>) object;
									if (!map.containsKey("admin_id")) {
										setCode(1);
										admin.destroy();
										return;
									}
									
									object = map.get("admin_id");
									final int id = Numeric.toInteger(object);									
									map = admin.adminIdSelect(Integer.parseInt(adminId));
									if (map.containsKey("admin_parent")) {
										object = map.get("admin_parent");										
										if (id != Numeric.toInteger(object)) {
											setCode(1);
											admin.destroy();
											return;
										}
									}
								}
								
								if (!admin.adminDelete(Integer.parseInt(adminId)))
									setCode(1);
								
								admin.destroy();
								return;
							}
						}
					}
				}				
			}
		}
		
		if (command.equals("5")) {
			final String adminId = getParameter(request, "admin_id");
			final String adminDesc = getParameter(request, "admin_desc");
			
			if (Numeric.isNumber(adminId) && Numeric.isInteger(adminId)) {
				final HttpSession session = request.getSession();
				if (session != null) {
					Object object = session.getAttribute("admin");
					if (object != null) {
						Map<String, Object> map = (Map<String, Object>) object;
						if (map.containsKey("admin_id")) {
							object = map.get("admin_id");
							if (Integer.parseInt(adminId) == Numeric.toInteger(object)) {
								final ModelAdmin admin = new ModelAdmin();
								if (admin.adminDescUpdate(Integer.parseInt(adminId), adminDesc))
									map.put("admin_desc", adminDesc);
								else
									setCode(1);
																
								admin.destroy();
								return;
							}														
						}
						
						if (map.containsKey("admin_parent")) {
							object = map.get("admin_parent");
							final int adminParent = Numeric.toInteger(object);
							if (adminParent == 0 || adminParent == 1) {
								final ModelAdmin admin = new ModelAdmin();
								if (adminParent == 1) {
									object = session.getAttribute("admin");
									map = (Map<String, Object>) object;
									if (!map.containsKey("admin_id")) {
										setCode(1);
										admin.destroy();
										return;
									}
										
									object = map.get("admin_id");
									final int id = Numeric.toInteger(object);									
									map = admin.adminIdSelect(Integer.parseInt(adminId));
									if (map != null && map.containsKey("admin_parent")) {								
										object = map.get("admin_parent");
										if (id != Numeric.toInteger(object)) {
											setCode(1);
											admin.destroy();
											return;
										}									
									} else {
										setCode(1);
										admin.destroy();
										return;
									}
								}
								
								if (admin.adminDescUpdate(Integer.parseInt(adminId), adminDesc))
									map.put("admin_desc", adminDesc);
								else
									setCode(1);
																
								admin.destroy();
								return;								
							}
						}
					}
				}
			}
		}
		
		if (command.equals("6")) {
			final String adminUsername = getParameter(request, "admin_username");
			final String adminPassword = getParameter(request, "admin_password");
			final String adminDesc = getParameter(request, "admin_desc");
			
			if (checkString(adminUsername) && checkString(adminPassword)) {
				final HttpSession session = request.getSession();
				if (session != null) {
					Object object = session.getAttribute("admin");
					if (object != null) {
						Map<String, Object> map = (Map<String, Object>) object;
						if (map.containsKey("admin_parent")) {
							object = map.get("admin_parent");
							final int adminParent = Numeric.toInteger(object);
							if (adminParent == 0 || adminParent == 1) {
								final ModelAdmin admin = new ModelAdmin();
								final int count = admin.adminExists(adminUsername);
								if (count > 0)
									setCode(2);									
								else {
									object = map.get("admin_id");
									final int adminId = Numeric.toInteger(object);
									int now = getNow();
									if (!admin.adminInsert(adminUsername, adminPassword, adminId, adminDesc, now))
										setCode(1);
								}
								
								admin.destroy();
								return;
							}
						}
					}
				}					
			}
		}
		
		if (command.equals("7")) {
			final HttpSession session = request.getSession();
			if (session != null) {
				Object object = session.getAttribute("admin");
				if (object != null) {
					Map<String, Object> map = (Map<String, Object>) object;
					if (map.containsKey("admin_parent")) {
						object = map.get("admin_parent");
						final int adminParent = Numeric.toInteger(object);						
						if (adminParent == 1) {
							final ModelAdmin admin = new ModelAdmin();
							object = map.get("admin_id");
							final int adminId = Numeric.toInteger(object);
							final List<Map<String, Object>> list = admin.adminParentSelect(adminId);
							if (list != null)
								setResult(list);
															
							admin.destroy();
							return;
						}
					}
				}
			}
		}
		
		if (command.equals("8")) {
			final String adminId = getParameter(request, "admin_id");
			final String adminOldPassword = getParameter(request, "admin_oldpassword");
			final String adminPassword = getParameter(request, "admin_password");
						
			if (Numeric.isNumber(adminId) && Numeric.isInteger(adminId) && checkString(adminPassword)) {
				final HttpSession session = request.getSession();
				if (session != null) {
					Object object = session.getAttribute("admin");
					if (object != null) {
						Map<String, Object> map = (Map<String, Object>) object;
						if (map.containsKey("admin_id")) {
							object = map.get("admin_id");
							if (Integer.parseInt(adminId) == Numeric.toInteger(object)) {
								if (!checkString(adminOldPassword)) {
									setCode(2);
									return;
								}
								
								object = map.get("admin_username");
								if (object != null) {
									final String adminUsername = String.valueOf(object);
									final ModelAdmin admin = new ModelAdmin();								
									if (admin.adminSelect(adminUsername, adminOldPassword) != null) {
										if (!admin.adminPasswordUpdate(Integer.parseInt(adminId), adminPassword))
											setCode(1);										
									} else
										setCode(3);
																										
									admin.destroy();
									return;									
								} else {
									setCode(1);
									return;
								}
							}
						}
						
						if (map.containsKey("admin_parent")) {
							object = map.get("admin_parent");
							final int adminParent = Numeric.toInteger(object);
							if (adminParent == 0 || adminParent == 1) {
								final ModelAdmin admin = new ModelAdmin();
								if (adminParent == 1) {
									object = session.getAttribute("admin");
									map = (Map<String, Object>) object;
									if (!map.containsKey("admin_id")) {
										setCode(1);
										admin.destroy();
										return;
									}										
									
									object = map.get("admin_id");
									final int id = Numeric.toInteger(object);									
									map = admin.adminIdSelect(Integer.parseInt(adminId));
									if (map != null && map.containsKey("admin_parent")) {								
										object = map.get("admin_parent");
										if (id != Numeric.toInteger(object)) {
											setCode(4);
											admin.destroy();
											return;
										}									
									} else {
										setCode(1);
										admin.destroy();
										return;
									}
								}
								
								if (!admin.adminPasswordUpdate(Integer.parseInt(adminId), adminPassword))
									setCode(1);
																
								admin.destroy();
								return;								
							}
						}
					}
				}
			}			
		}
		
		setCode(1);		
	}
}
