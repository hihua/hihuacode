function getCity() {				
	requestCity(userName, owner, cityId, setCity);
	setTimeout("getCity()", 30000);
}

function getMessages() {				
	requestOtherMessages(userName, owner, 1, setMessages);											
	setTimeout("getMessages()", 40000);
}

function getItems() {
	requestOtherItems(userName, owner , setItems);				
}

function setCity(json, cityId) {
	var city = json.city;
	if (city == null)
		return null;
		
	var div = "cities";			
	var buildings = city.buildings;
	var skills = city.skills;	
	var resources = city.resources;
	var events = city.events;
	var soldiers = city.soldiers;
						
	var html = "";				
	html += "<table width=\"640\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" class=\"Table1\">";
	html += "<tr>";
	html += "<td width=\"10%\" align=\"center\" valign=\"middle\">基本信息</td>";
	html += "<td width=\"40%\">";				
	html += "<table width=\"94%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin-left:5px\">";
	html += "<tr>";
	html += "<td align=\"left\" width=\"34%\" height=\"25px\">用户名: </td>";
	html += "<td>" + city.owner + "</td>";        
	html += "</tr>";
	html += "<tr>";
	
	html += "<td align=\"left\" width=\"34%\" height=\"25px\">城市名称: </td>";
	if (city.is_capital == true)			        
		html += "<td style=\"color:#FF0000\">" + city.name + "</td>";
	else
		html += "<td>" + city.name + "</td>";
  				        
	html += "</tr>";        
	html += "<tr>";
	html += "<td align=\"left\" width=\"34%\" height=\"25px\">城市ID: </td>";
	html += "<td>" + city.id + "</td>";        
	html += "</tr>";
	html += "<tr>";
	html += "<td align=\"left\" width=\"34%\" height=\"25px\">爵位: </td>";
	html += "<td>" + city.title + "</td>";        
	html += "</tr>";
	html += "<tr>";
	html += "<td align=\"left\" width=\"34%\" height=\"25px\">等级: </td>";
	html += "<td>" + city.level + "</td>";        
	html += "</tr>";
	html += "<tr>";
	html += "<td align=\"left\" width=\"34%\" height=\"25px\">分数: </td>";
	html += "<td>" + city.score + "/" + city.max + "</td>";        
	html += "</tr>";
	html += "<tr>";
	html += "<td align=\"left\" width=\"34%\" height=\"25px\">坐标: </td>";
	html += "<td>" + city.x + "," + city.y + "</td>";        
	html += "</tr>"; 
	html += "<tr>";
	html += "<td align=\"left\" width=\"34%\" height=\"25px\">销售量: </td>";
	html += "<td>" + city.market_left_capacity + "/" + city.market_effecitve + "</td>";        
	html += "</tr>"; 
	html += "<tr>";
	html += "<td align=\"left\" width=\"34%\" height=\"25px\">登录日: </td>";
	html += "<td>" + city.login_days_count + "</td>";        
	html += "</tr>";
	html += "<tr>";
	html += "<td align=\"left\" width=\"34%\" height=\"25px\">宝石: </td>";
	html += "<td>" + city.resources.gems + "</td>";        
	html += "</tr>";
	
	if (events.protection != null) {
		$.each(events.protection, function(idx, protection) {
			var date = new Date(protection.finish_time * 1000);
			html += "<tr>";
			html += "<td align=\"left\" width=\"34%\" height=\"25px\">保护期: </td>";
			html += "<td>" + date.pattern("yyyy-MM-dd HH:mm:ss") + "</td>";
			html += "</tr>";			    				    
		});
	}
				    			    
	html += "</table>";				
	html += "</td>";
	
	html += "<td width=\"10%\" align=\"center\" valign=\"middle\">资源</td>";
	html += "<td width=\"40%\" align=\"left\">";
	
	if (resources != null) {					
		if (resources.wood != null) {
			var wood = resources.wood;
			html += "<table width=\"98%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">";
			html += "<tr>";
			html += "<td align=\"left\" width=\"25%\" height=\"25px\" rowspan=\"2\" valign=\"top\" style=\"padding-left:9px;padding-top:8px\">木头</td>";
			html += "<td align=\"left\" width=\"45%\" height=\"25px\">" + wood.count + "/" + wood.capacity + "</td>";
			html += "<td align=\"left\" width=\"30%\">" + wood.increase_per_hour + "h/" + wood.increase_max_per_hour + "h</td>";
			html += "</tr>";
			html += "<tr>";						
			html += "<td align=\"left\" width=\"45%\" height=\"25px\">" + wood.worker_count + "/" + wood.worker_capacity + "</td>";
			html += "<td align=\"left\" width=\"30%\">+" + wood.village_increase_per_hour + "/h</td>";
			html += "</tr>";
			html += "</table>";						
		}
		
		if (resources.stone != null) {
			var stone = resources.stone;
			html += "<table width=\"98%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">";
			html += "<tr>";
			html += "<td align=\"left\" width=\"25%\" height=\"25px\" rowspan=\"2\" valign=\"top\" style=\"padding-left:9px;padding-top:8px\">石料</td>";
			html += "<td align=\"left\" width=\"45%\" height=\"25px\">" + stone.count + "/" + stone.capacity + "</td>";
			html += "<td align=\"left\" width=\"30%\">" + stone.increase_per_hour + "h/" + stone.increase_max_per_hour + "h</td>";
			html += "</tr>";
			html += "<tr>";						
			html += "<td align=\"left\" width=\"45%\" height=\"25px\">" + stone.worker_count + "/" + stone.worker_capacity + "</td>";
			html += "<td align=\"left\" width=\"30%\">+" + stone.village_increase_per_hour + "/h</td>";
			html += "</tr>";
			html += "</table>";						
		}
		
		if (resources.iron != null) {
			var iron = resources.iron;
			html += "<table width=\"98%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">";
			html += "<tr>";
			html += "<td align=\"left\" width=\"25%\" height=\"25px\" rowspan=\"2\" valign=\"top\" style=\"padding-left:9px;padding-top:8px\">铁矿</td>";
			html += "<td align=\"left\" width=\"45%\" height=\"25px\">" + iron.count + "/" + iron.capacity + "</td>";
			html += "<td align=\"left\" width=\"30%\">" + iron.increase_per_hour + "h/" + iron.increase_max_per_hour + "h</td>";
			html += "</tr>";
			html += "<tr>";						
			html += "<td align=\"left\" width=\"45%\" height=\"25px\">" + iron.worker_count + "/" + iron.worker_capacity + "</td>";
			html += "<td align=\"left\" width=\"30%\">+" + iron.village_increase_per_hour + "/h</td>";
			html += "</tr>";
			html += "</table>";						
		}
		
		if (resources.food != null) {
			var food = resources.food;
			html += "<table width=\"98%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">";
			html += "<tr>";
			html += "<td align=\"left\" width=\"25%\" height=\"25px\" rowspan=\"2\" valign=\"top\" style=\"padding-left:9px;padding-top:8px\">食物</td>";
			html += "<td align=\"left\" width=\"45%\" height=\"25px\">" + food.count + "/" + food.capacity + "</td>";
			html += "<td align=\"left\" width=\"30%\">" + food.increase_per_hour + "h/" + food.increase_max_per_hour + "h</td>";
			html += "</tr>";
			html += "<tr>";						
			html += "<td align=\"left\" width=\"45%\" height=\"25px\">" + food.worker_count + "/" + food.worker_capacity + "</td>";
			html += "<td align=\"left\" width=\"30%\">+" + food.village_increase_per_hour + "/h</td>";
			html += "</tr>";
			html += "</table>";						
		}
		
		if (resources.gold != null) {
			var gold = resources.gold;
			html += "<table width=\"98%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">";
			html += "<tr>";
			html += "<td align=\"left\" width=\"25%\" height=\"25px\" rowspan=\"2\" valign=\"top\" style=\"padding-left:9px;padding-top:8px\">金币</td>";
			html += "<td align=\"left\" width=\"45%\" height=\"25px\">" + gold.count + "</td>";
			html += "<td align=\"left\" width=\"30%\">" + gold.increase_per_hour + "h/" + gold.increase_max_per_hour + "h</td>";
			html += "</tr>";
			html += "<tr>";						
			html += "<td align=\"left\" width=\"45%\" height=\"25px\">" + gold.worker_count + "/" + gold.worker_capacity + "</td>";
			html += "<td align=\"left\" width=\"30%\">&nbsp;</td>";
			html += "</tr>";
			html += "</table>";												
		}
	}
	
	html += "</td>";
	html += "</tr>";
					
	var tr = true;
	if (buildings != null) {
		$.each(buildings, function(idx, building) {
			if (building.building_type == 0) {
				if (tr)
					html += "<tr>";
				
	    		var consume = building.consume;
	    		if (building.duration > 0) {
	    			var date = new Date((building.duration + city.current_server_time) * 1000);
	    			html += "<td width=\"10%\" align=\"center\"><span style=\"color:#FF0000\" title=\"" + date.pattern("yyyy-MM-dd HH:mm:ss") + "\">城堡</span></td>";
	    		} else
	    			html += "<td width=\"10%\" align=\"center\">城堡</td>";
	    						    			
	    		html += "<td width=\"40%\">";
	    		html += "<table width=\"92%\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">";
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">等级: </td><td>" + building.level + "/" + building.max_level + "</td></tr>";
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">描述: </td>";
	    		
	    		if (building.description != null)
	    			html += "<td>" + building.description + "</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">所需木头: </td>";
	    		if (consume.wood != null)
	    			html += "<td>" + consume.wood + "</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">所需石料: </td>";
	    		if (consume.stone != null)
	    			html += "<td>" + consume.stone + "</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";			    		
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">所需时间: </td>";
	    		if (consume.duration != null)
	    			html += "<td>" + consume.duration + "s</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";			    						    		
	    			
	    		html += "</table>";
	    		html += "</td>";
	    		
	    		if (!tr) {
					html += "</tr>";
					tr = true;
				} else
					tr = false; 								
	    	}					
		});
		
		$.each(buildings, function(idx, building) {
			if (building.building_type == 4) {
				if (tr)
					html += "<tr>";
				
	    		var consume = building.consume;
	    		if (building.duration > 0) {
	    			var date = new Date((building.duration + city.current_server_time) * 1000);
	    			html += "<td width=\"10%\" align=\"center\"><span style=\"color:#FF0000\" title=\"" + date.pattern("yyyy-MM-dd HH:mm:ss") + "\">教堂</span></td>";
	    		} else
	    			html += "<td width=\"10%\" align=\"center\">教堂</td>";
	    						    			
	    		html += "<td width=\"40%\">";
	    		html += "<table width=\"92%\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">";
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">等级: </td><td>" + building.level + "/" + building.max_level + "</td></tr>";
	    		
	    		var leadership = resources.leadership;
	    		if (leadership != null)
	    			html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">信仰: </td><td>" + leadership.count + "/" + leadership.capacity + "</td></tr>";
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">描述: </td>";
	    		
	    		if (building.description != null)
	    			html += "<td>" + building.description + "</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">所需木头: </td>";
	    		if (consume.wood != null)
	    			html += "<td>" + consume.wood + "</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">所需石料: </td>";
	    		if (consume.stone != null)
	    			html += "<td>" + consume.stone + "</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";			    		
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">所需时间: </td>";
	    		if (consume.duration != null)
	    			html += "<td>" + consume.duration + "s</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";			    						    		
	    			
	    		html += "</table>";
	    		html += "</td>";
	    		
	    		if (!tr) {
					html += "</tr>";
					tr = true;
				} else
					tr = false;								
	    	}					
		});
		
		$.each(buildings, function(idx, building) {
			if (building.building_type == 11) {
	    		if (tr)
					html += "<tr>";
					
	    		var consume = building.consume;
	    		if (building.duration > 0) {
	    			var date = new Date((building.duration + city.current_server_time) * 1000);
	    			html += "<td width=\"10%\" align=\"center\"><span style=\"color:#FF0000\" title=\"" + date.pattern("yyyy-MM-dd HH:mm:ss") + "\">步兵</span></td>";
	    		} else
	    			html += "<td width=\"10%\" align=\"center\">步兵</td>";
	    			
	    		html += "<td width=\"40%\">";
	    		html += "<table width=\"92%\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">";
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">等级: </td><td>" + building.level + "/" + building.max_level + "</td></tr>";
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">描述: </td>";
	    		
	    		if (building.description != null)
	    			html += "<td>" + building.description + "</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">所需木头: </td>";
	    		if (consume.wood != null)
	    			html += "<td>" + consume.wood + "</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">所需石料: </td>";
	    		if (consume.stone != null)
	    			html += "<td>" + consume.stone + "</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";			    		
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">所需时间: </td>";
	    		if (consume.duration != null)
	    			html += "<td>" + consume.duration + "s</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">剑士: </td>";
	    		if (soldiers != null) {
	    			$.each(soldiers, function(idx, soldier) {
	    				if (soldier.soldier_type == 0)
	    					html += "<td>" + soldier.count + "</td></tr>";				    			
	    			});				    			
	    		} else
	    			html += "<td>&nbsp;</td></tr>";
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">侦察兵: </td>";
	    		if (soldiers != null) {
	    			$.each(soldiers, function(idx, soldier) {
	    				if (soldier.soldier_type == 1)
	    					html += "<td>" + soldier.count + "</td></tr>";				    			
	    			});				    			
	    		} else
	    			html += "<td>&nbsp;</td></tr>";
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">弩弓手: </td>";
	    		if (soldiers != null) {
	    			$.each(soldiers, function(idx, soldier) {
	    				if (soldier.soldier_type == 2)
	    					html += "<td>" + soldier.count + "</td></tr>";				    			
	    			});				    			
	    		} else
	    			html += "<td>&nbsp;</td></tr>";
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">侍卫: </td>";
	    		if (soldiers != null) {
	    			$.each(soldiers, function(idx, soldier) {
	    				if (soldier.soldier_type == 3)
	    					html += "<td>" + soldier.count + "</td></tr>";				    			
	    			});				    			
	    		} else
	    			html += "<td>&nbsp;</td></tr>";					    		
	    			
	    		html += "</table>";
	    		html += "</td>";
	    		
	    		if (!tr) {
					html += "</tr>";
					tr = true;
				} else
					tr = false;							
	    	}					
		});
		
		$.each(buildings, function(idx, building) {
			if (building.building_type == 12) {
	    		if (tr)
					html += "<tr>";
					
	    		var consume = building.consume;
	    		if (building.duration > 0) {
	    			var date = new Date((building.duration + city.current_server_time) * 1000);
	    			html += "<td width=\"10%\" align=\"center\"><span style=\"color:#FF0000\" title=\"" + date.pattern("yyyy-MM-dd HH:mm:ss") + "\">骑兵</span></td>";
	    		} else
	    			html += "<td width=\"10%\" align=\"center\">骑兵</td>";
	    			
	    		html += "<td width=\"40%\">";
	    		html += "<table width=\"92%\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">";
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">等级: </td><td>" + building.level + "/" + building.max_level + "</td></tr>";
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">描述: </td>";
	    		
	    		if (building.description != null)
	    			html += "<td>" + building.description + "</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">所需木头: </td>";
	    		if (consume.wood != null)
	    			html += "<td>" + consume.wood + "</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">所需石料: </td>";
	    		if (consume.stone != null)
	    			html += "<td>" + consume.stone + "</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";			    		
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">所需时间: </td>";
	    		if (consume.duration != null)
	    			html += "<td>" + consume.duration + "s</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">圣武士: </td>";
	    		if (soldiers != null) {
	    			$.each(soldiers, function(idx, soldier) {
	    				if (soldier.soldier_type == 4)
	    					html += "<td>" + soldier.count + "</td></tr>";				    			
	    			});				    			
	    		} else
	    			html += "<td>&nbsp;</td></tr>";
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">弓骑兵: </td>";
	    		if (soldiers != null) {
	    			$.each(soldiers, function(idx, soldier) {
	    				if (soldier.soldier_type == 5)
	    					html += "<td>" + soldier.count + "</td></tr>";				    			
	    			});				    			
	    		} else
	    			html += "<td>&nbsp;</td></tr>";
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">圣骑士: </td>";
	    		if (soldiers != null) {
	    			$.each(soldiers, function(idx, soldier) {
	    				if (soldier.soldier_type == 6)
	    					html += "<td>" + soldier.count + "</td></tr>";				    			
	    			});				    			
	    		} else
	    			html += "<td>&nbsp;</td></tr>";
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">皇家骑士: </td>";
	    		if (soldiers != null) {
	    			$.each(soldiers, function(idx, soldier) {
	    				if (soldier.soldier_type == 7)
	    					html += "<td>" + soldier.count + "</td></tr>";				    			
	    			});				    			
	    		} else
	    			html += "<td>&nbsp;</td></tr>";						    		
	    			
	    		html += "</table>";
	    		html += "</td>";
	    		
	    		if (!tr) {
					html += "</tr>";
					tr = true;
				} else
					tr = false;							
			}					
		});
		
		$.each(buildings, function(idx, building) {
			if (building.building_type == 6) {
	    		if (tr)
					html += "<tr>";
	    		
	    		var consume = building.consume;
	    		if (building.duration > 0) {
	    			var date = new Date((building.duration + city.current_server_time) * 1000);
	    			html += "<td width=\"10%\" align=\"center\"><span style=\"color:#FF0000\" title=\"" + date.pattern("yyyy-MM-dd HH:mm:ss") + "\">伐木工</span></td>";
	    		} else
	    			html += "<td width=\"10%\" align=\"center\">伐木工</td>";
	    			
	    		html += "<td width=\"40%\">";
	    		html += "<table width=\"92%\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">";
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">等级: </td><td>" + building.level + "/" + building.max_level + "</td></tr>";
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">描述: </td>";
	    		
	    		if (building.description != null)
	    			html += "<td>" + building.description + "</td></tr>";
	    						    						    						    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">所需木头: </td>";
	    		
	    		if (consume.wood != null)
	    			html += "<td>" + consume.wood + "</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">所需石料: </td>";
	    		if (consume.stone != null)
	    			html += "<td>" + consume.stone + "</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";			    		
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">所需时间: </td>";
	    		if (consume.duration != null)
	    			html += "<td>" + consume.duration + "s</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";			    						    		
	    			
	    		html += "</table>";
	    		html += "</td>";
	    		
	    		if (!tr) {
					html += "</tr>";
					tr = true;
				} else
					tr = false;								
	    	}					
		});
		
		$.each(buildings, function(idx, building) {
			if (building.building_type == 7) {
	    		if (tr)
					html += "<tr>";
					
	    		var consume = building.consume;
	    		if (building.duration > 0) {
	    			var date = new Date((building.duration + city.current_server_time) * 1000);
	    			html += "<td width=\"10%\" align=\"center\"><span style=\"color:#FF0000\" title=\"" + date.pattern("yyyy-MM-dd HH:mm:ss") + "\">采石工</span></td>";
	    		} else
	    			html += "<td width=\"10%\" align=\"center\">采石工</td>";
	    			
	    		html += "<td width=\"40%\">";
	    		html += "<table width=\"92%\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">";
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">等级: </td><td>" + building.level + "/" + building.max_level + "</td></tr>";
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">描述: </td>";
	    		
	    		if (building.description != null)
	    			html += "<td>" + building.description + "</td></tr>";
	    						    						    						    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">所需木头: </td>";
	    		
	    		if (consume.wood != null)
	    			html += "<td>" + consume.wood + "</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">所需石料: </td>";
	    		if (consume.stone != null)
	    			html += "<td>" + consume.stone + "</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";			    		
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">所需时间: </td>";
	    		if (consume.duration != null)
	    			html += "<td>" + consume.duration + "s</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";			    						    		
	    			
	    		html += "</table>";
	    		html += "</td>";			   
	    		
	    		if (!tr) {
					html += "</tr>";
					tr = true;
				} else
					tr = false;								
	    	}					
		});
		
		$.each(buildings, function(idx, building) {
			if (building.building_type == 8) {
	    		if (tr)
					html += "<tr>";
					
	    		var consume = building.consume;
	    		if (building.duration > 0) {
	    			var date = new Date((building.duration + city.current_server_time) * 1000);
	    			html += "<td width=\"10%\" align=\"center\"><span style=\"color:#FF0000\" title=\"" + date.pattern("yyyy-MM-dd HH:mm:ss") + "\">铁矿工</span></td>";
	    		} else
	    			html += "<td width=\"10%\" align=\"center\">铁矿工</td>";
	    			
	    		html += "<td width=\"40%\">";
	    		html += "<table width=\"92%\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">";
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">等级: </td><td>" + building.level + "/" + building.max_level + "</td></tr>";
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">描述: </td>";
	    		
	    		if (building.description != null)
	    			html += "<td>" + building.description + "</td></tr>";
	    						    						    						    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">所需木头: </td>";
	    		
	    		if (consume.wood != null)
	    			html += "<td>" + consume.wood + "</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">所需石料: </td>";
	    		if (consume.stone != null)
	    			html += "<td>" + consume.stone + "</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";			    		
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">所需时间: </td>";
	    		if (consume.duration != null)
	    			html += "<td>" + consume.duration + "s</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";			    						    		
	    			
	    		html += "</table>";
	    		html += "</td>";
	    		
	    		if (!tr) {
					html += "</tr>";
					tr = true;
				} else
					tr = false;							
	    	}					
		});
		
		$.each(buildings, function(idx, building) {
			if (building.building_type == 9) {
	    		if (tr)
					html += "<tr>";
					
	    		var consume = building.consume;
	    		if (building.duration > 0) {
	    			var date = new Date((building.duration + city.current_server_time) * 1000);
	    			html += "<td width=\"10%\" align=\"center\"><span style=\"color:#FF0000\" title=\"" + date.pattern("yyyy-MM-dd HH:mm:ss") + "\">农民工</span></td>";
	    		} else
	    			html += "<td width=\"10%\" align=\"center\">农民工</td>";
	    			
	    		html += "<td width=\"40%\">";
	    		html += "<table width=\"92%\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">";
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">等级: </td><td>" + building.level + "/" + building.max_level + "</td></tr>";
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">描述: </td>";
	    		
	    		if (building.description != null)
	    			html += "<td>" + building.description + "</td></tr>";
	    					    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">所需木头: </td>";
	    		
	    		if (consume.wood != null)
	    			html += "<td>" + consume.wood + "</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">所需石料: </td>";
	    		if (consume.stone != null)
	    			html += "<td>" + consume.stone + "</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";			    		
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">所需时间: </td>";
	    		if (consume.duration != null)
	    			html += "<td>" + consume.duration + "s</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";			    						    		
	    			
	    		html += "</table>";
	    		html += "</td>";
	    		
	    		if (!tr) {
					html += "</tr>";
					tr = true;
				} else
					tr = false;									
	    	}					
		});
		
		$.each(buildings, function(idx, building) {
			if (building.building_type == 1) {
	    		if (tr)
					html += "<tr>";
				
	    		var consume = building.consume;
	    		if (building.duration > 0) {
	    			var date = new Date((building.duration + city.current_server_time) * 1000);
	    			html += "<td width=\"10%\" align=\"center\"><span style=\"color:#FF0000\" title=\"" + date.pattern("yyyy-MM-dd HH:mm:ss") + "\">城市</span></td>";
	    		} else
	    			html += "<td width=\"10%\" align=\"center\">城市</td>";
	    			
	    		html += "<td width=\"40%\">";
	    		html += "<table width=\"92%\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">";
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">等级: </td><td>" + building.level + "/" + building.max_level + "</td></tr>";
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">描述: </td>";
	    		
	    		if (building.description != null)
	    			html += "<td>" + building.description + "</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">所需木头: </td>";
	    		if (consume.wood != null)
	    			html += "<td>" + consume.wood + "</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">所需石料: </td>";
	    		if (consume.stone != null)
	    			html += "<td>" + consume.stone + "</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";			    		
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">所需时间: </td>";
	    		if (consume.duration != null)
	    			html += "<td>" + consume.duration + "s</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";			    						    		
	    			
	    		html += "</table>";
	    		html += "</td>";
	    		
	    		if (!tr) {
					html += "</tr>";
					tr = true;
				} else
					tr = false;							
	    	}					
		});
		
		$.each(buildings, function(idx, building) {
			if (building.building_type == 10) {
	    		if (tr)
					html += "<tr>";
					
	    		var consume = building.consume;
	    		if (building.duration > 0) {
	    			var date = new Date((building.duration + city.current_server_time) * 1000);
	    			html += "<td width=\"10%\" align=\"center\"><span style=\"color:#FF0000\" title=\"" + date.pattern("yyyy-MM-dd HH:mm:ss") + "\">科研</span></td>";
	    		} else
	    			html += "<td width=\"10%\" align=\"center\">科研</td>";
	    			
	    		html += "<td width=\"40%\">";
	    		html += "<table width=\"92%\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">";
	    		
	    		$.each(skills, function(idx, skill) {
	    			if (skill.description != null) {
	    				var consume = skill.consume;
		    			html += "<tr>";
		    			html += "<td align=\"left\" width=\"55%\" height=\"25px\">" + skill.description + "</td>";
		    			html += "<td align=\"left\" height=\"25px\"><span title=\"需时: " + consume.duration + "s\r\n金币: " + consume.gold + "\r\n描述: " + consume.description + "\">" + skill.level + "</span>/" + skill.max_level + "</td>";					    			
		    			html += "</tr>";
	    			}				    							    		
	    		});
	    			
	    		html += "</table>";
	    		html += "</td>";
	    		
	    		if (!tr) {
					html += "</tr>";
					tr = true;
				} else
					tr = false;									
	    	}					
		});
		
		$.each(buildings, function(idx, building) {
			if (building.building_type == 2) {
	    		if (tr)
					html += "<tr>";
	    	
	    		var consume = building.consume;
	    		if (building.duration > 0) {
	    			var date = new Date((building.duration + city.current_server_time) * 1000);
	    			html += "<td width=\"10%\" align=\"center\"><span style=\"color:#FF0000\" title=\"" + date.pattern("yyyy-MM-dd HH:mm:ss") + "\">市场</span></td>";
	    		} else
	    			html += "<td width=\"10%\" align=\"center\">市场</td>";
	    			
	    		html += "<td width=\"40%\">";
	    		html += "<table width=\"92%\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">";
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">等级: </td><td>" + building.level + "/" + building.max_level + "</td></tr>";
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">描述: </td>";
	    		
	    		if (building.description != null)
	    			html += "<td>" + building.description + "</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">所需木头: </td>";
	    		if (consume.wood != null)
	    			html += "<td>" + consume.wood + "</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">所需石料: </td>";
	    		if (consume.stone != null)
	    			html += "<td>" + consume.stone + "</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";			    		
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">所需时间: </td>";
	    		if (consume.duration != null)
	    			html += "<td>" + consume.duration + "s</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";			    						    		
	    			
	    		html += "</table>";
	    		html += "</td>";
	    		
	    		if (!tr) {
					html += "</tr>";
					tr = true;
				} else
					tr = false;							
	    	}					
		});
		
		$.each(buildings, function(idx, building) {
			if (building.building_type == 5) {			    		
	    		if (tr)
					html += "<tr>";
					
	    		var consume = building.consume;
	    		if (building.duration > 0) {
	    			var date = new Date((building.duration + city.current_server_time) * 1000);
	    			html += "<td width=\"10%\" align=\"center\"><span style=\"color:#FF0000\" title=\"" + date.pattern("yyyy-MM-dd HH:mm:ss") + "\">仓库</span></td>";
	    		} else
	    			html += "<td width=\"10%\" align=\"center\">仓库</td>";
	    			
	    		html += "<td width=\"40%\">";
	    		html += "<table width=\"92%\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">";
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">等级: </td><td>" + building.level + "/" + building.max_level + "</td></tr>";
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">描述: </td>";
	    		
	    		if (building.description != null)
	    			html += "<td>" + building.description + "</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">所需木头: </td>";
	    		if (consume.wood != null)
	    			html += "<td>" + consume.wood + "</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">所需石料: </td>";
	    		if (consume.stone != null)
	    			html += "<td>" + consume.stone + "</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";			    		
	    			
	    		html += "<tr><td align=\"left\" width=\"34%\" height=\"25px\">所需时间: </td>";
	    		if (consume.duration != null)
	    			html += "<td>" + consume.duration + "s</td></tr>";
	    		else
	    			html += "<td>&nbsp;</td></tr>";			    						    		
	    			
	    		html += "</table>";
	    		html += "</td>";
	    		
	    		if (!tr) {
					html += "</tr>";
					tr = true;
				} else
					tr = false;							
	    	}					
		});
	}
											
	if (!tr)
		html += "<td width=\"10%\">&nbsp;</td><td width=\"40%\">&nbsp;</td></tr>";
									
	if (events != null) {
		html += "<tr><td align=\"center\" height=\"25px\">事件</td>";
		html += "<td colspan=\"3\" align=\"left\">";
		html += "<table width=\"96%\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">";
		var buildings = events.building;
		var armys = events.army_event;
		var protections = events.protection;
		var trains = events.train;
		var skills = events.skill;
		
		if (buildings != null) {		
			$.each(buildings, function(idx, building) {						
				var date = new Date((building.duration + city.current_server_time) * 1000);
				html += "<tr>";
				html += "<td width=\"35%\" height=\"25px\">" + building.desc + "</td>";
				html += "<td>" + date.pattern("yyyy-MM-dd HH:mm:ss") + "</td>";
				html += "</tr>";
			});						
		}
		
		if (armys != null) {		
			$.each(armys, function(idx, army) {						
				var date = new Date(army.arrive_time * 1000);
				html += "<tr>";
				html += "<td width=\"35%\" height=\"25px\">" + army.desc + "</td>";
				html += "<td>" + date.pattern("yyyy-MM-dd HH:mm:ss") + "</td>";
				html += "</tr>";
			});						
		}
		
		if (protections != null) {		
			$.each(protections, function(idx, protection) {						
				var date = new Date(protection.finish_time * 1000);
				html += "<tr>";
				html += "<td width=\"35%\" height=\"25px\">" + protection.desc + "</td>";
				html += "<td>" + date.pattern("yyyy-MM-dd HH:mm:ss") + "</td>";
				html += "</tr>";
			});						
		}
		
		if (skills != null) {		
			$.each(skills, function(idx, skill) {						
				var date = new Date((skill.duration + city.current_server_time) * 1000);
				html += "<tr>";
				html += "<td width=\"35%\" height=\"25px\">" + skill.desc + "</td>";
				html += "<td>" + date.pattern("yyyy-MM-dd HH:mm:ss") + "</td>";
				html += "</tr>";
			});						
		}
		
		if (trains != null) {		
			$.each(trains, function(idx, train) {						
				var date = new Date(train.finish_time * 1000);
				html += "<tr>";
				html += "<td width=\"35%\" height=\"25px\">" + train.desc + "</td>";
				html += "<td>" + date.pattern("yyyy-MM-dd HH:mm:ss") + "</td>";
				html += "</tr>";
			});						
		}
		
		html += "</table>";
		html += "</td>";	
	}							
								  
	html += "</table><br/>";			
    
    var divCity = "city_" + cityId;
    if ($("#" + div).find("#" + divCity).length == 0) {
    	html = "<div id=\"" + divCity + "\">" + html + "</div>";
    	$("#" + div).append(html);
    } else
    	$("#" + divCity).html(html);
}

function setMessages(json, userName, to, page) {				
	var messages = json.messages;				
	if (messages == null)
		return;
				
	var html = "";				
	$.each(messages, function(idx, message) {
		var date = new Date(message.created_at * 1000);
		
		var body = message.body;
		if (message.msg_type == 3 || message.msg_type == 4) {
			var start = body.indexOf("<body>");					
			if (start == -1)
				return;
			
			start += 6;
			var end = body.indexOf("</body>", start);
			if (end == -1)
				return;
				
			body = body.substring(start, end);					
		}					
											
		html += "<table width=\"500\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" class=\"Table1\">";
		html += "<tr>";
		html += "<td width=\"60%\" height=\"30px\" style=\"padding-left:5px;padding-right:5px\"><a href=\"#\" onclick=\"bodyShow(" + message.id + ");return false;\" class=\"AdminToolsLink1\">" + message.subject + "</a></td>";
		html += "<td rowspan=\"2\" align=\"center\">";
		html += "<table width=\"180\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">";
		html += "<tr>";
		html += "<td align=\"center\" height=\"25px\">" + message.from + " -> " + message.to + "</td>";
		html += "</tr>";
        html += "<tr>";
        html += "<td align=\"center\" height=\"25px\">" + date.pattern("yyyy-MM-dd HH:mm:ss") + "</td>";
        html += "</tr>";
        html += "</table>";
        html += "</td>";
        html += "</tr>";
        html += "<tr>";		            
        html += "<td height=\"25px\" style=\"padding-left:5px;padding-right:5px;\">" + owner + "</td>";
        html += "</tr>";
        
        var found = false;
        for (var i = 0;i < otherMessages.length;i++) {
        	if (message.id == otherMessages[i]) {
        		found = true;
        		break;		          
        	}  
        }
        
        if (!found)
        	html += "<tr id=\"body_" + message.id + "\" style=\"display:none\">";
        else
        	html += "<tr id=\"body_" + message.id + "\">";
        	
        html += "<td height=\"25px\" style=\"padding-left:5px;padding-right:5px;padding-top:5px;padding-bottom:5px;\" colspan=\"2\">" + body + "</td>";
        html += "</tr>";
        html += "</table>";
        html += "<br />";
	});
	
	html += "<table width=\"500\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">";
	html += "<tr>";
	html += "<td align=\"right\" style=\"padding-right:5px\"><a href=\"messages.html?username=" + userName + "&to=" + owner + "&page=1\" target=\"_blank\" class=\"AdminToolsLink2\">[更多]</a></td>";
	html += "</tr>";
	html += "</table>";		
	$("#messages").html(html);
}

function setItems(json, userName) {
	if (json.items == null)
		return;
										
	var html = "";
	html += "<table width=\"500\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" class=\"Table1\">";
	html += "<tr>";
	html += "<td height=\"30px\" style=\"padding-left:5px;\">" + userName + "道具</td>";
	html += "</tr>";
	
	$.each(json.items, function(idx, item) {								
		html += "<tr>";
		html += "<td>";
		html += "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"Table1\">";
		html += "<tr>";
		html += "<td height=\"30px\" align=\"left\" style=\"padding-left:5px;\">" + item.name + "</td>";
		html += "<td height=\"30px\" align=\"right\" style=\"padding-right:5px;\">" + item.count + "</td>";
		html += "</tr>";
		html += "<tr>";
		html += "<td height=\"30px\" colspan=\"2\" style=\"padding-left:5px;\">" + item.desc + "</td>";
		html += "</tr>";					
		html += "</table>";
		html += "</td>";
		html += "</tr>";	
	});
	
	html += "</table>";						
	$("#items").html(html);
}

function bodyShow(id) {				
	if ($("#body_" + id).css("display") == "none") {												
		$("#body_" + id).show();					
		otherMessages.push(id);					
	} else {
		$("#body_" + id).hide();
		var index = -1;
        for (var i = 0;i < otherMessages.length;i++) {
        	if (id == otherMessages[i]) {
        		index = i;
        		break;		          
        	}  
        }
        
        if (index != -1)
        	otherMessages.splice(index, 1);            			            
	}				
}