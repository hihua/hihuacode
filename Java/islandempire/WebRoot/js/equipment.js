var WebEquipment = "servlet/WebEquipment";

function requestPutEquipment(equipmentId, fromIndex, toIndex, townId) {
	var url = WebEquipment + "?command=0&equipment_id=" + equipmentId + "&from_index=" + fromIndex + "&to_index=" + toIndex + "&town_id=" + townId; 
	Ajax_CallBack(url, "", "json", "", true, function(json) {
		if (json == null)
			alert("装备失败");
		else {
			var ret = json.ret;
			if (ret != null && ret == 0)
				alert("装备成功");
			else
				alert("装备失败");
		}		
	}, function(response, error, status) {
		alert("装备失败");
    });
}

function requestSellEquipment(equipmentId, townId) {
	var url = WebEquipment + "?command=1&equipment_id=" + equipmentId + "&town_id=" + townId; 
	Ajax_CallBack(url, "", "json", "", true, function(json) {
		if (json == null)
			alert("卖掉失败");
		else {
			var ret = json.ret;
			if (ret != null && ret == 0)
				alert("卖掉成功");
			else
				alert("卖掉失败");
		}		
	}, function(response, error, status) {
		alert("卖掉失败");
    });
}

function requestUpgradeEquipment(equipmentId, townId, safe) {
	var url = WebEquipment + "?command=2&equipment_id=" + equipmentId + "&town_id=" + townId + "&safe=" + safe; 
	Ajax_CallBack(url, "", "json", "", true, function(json) {
		if (json == null)
			alert("升级失败");
		else {
			var ret = json.ret;
			if (ret != null && ret == 0)
				alert("升级成功");
			else
				alert("升级失败");
		}		
	}, function(response, error, status) {
		alert("升级失败");
    });
}

function sortEquipment(key1, key2) {
	return function (a, b) {
		var v1 = a[key1];
		var v2 = b[key1];
		var p1 = a[key2];
		var p2 = b[key2];
		if (v1 == null || v2 == null)
			return 1;
				
		if (v1 > v2)
			return 1;
					
		if (v1 < v2)			
			return -1;
				
		if (p1 < p2)
			return 1;
		
		if (p1 > p2)
			return -1;
		
		return 0;
	};
}

function setEquipment(json) {
	var html = "";
	var town = json.town;		
	if (town != null) {
		var equipments = town.equipments;		
		if (equipments != null) {
			html += "<table width=\"500\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">";
			html += "<tr><td>装备</td></tr>";
			html += "</table>";
			html += "<table width=\"500\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" class=\"Table1\">";
			
			var arrays = [];
			while (equipments.length > 0) {
				var equipment = equipments.pop();
				if (equipment.equipment_id != null)
					arrays.push(equipment);
			}
			
			arrays.sort(sortEquipment("type", "enhance_level"));		
		
			$.each(arrays, function(idx, equipment) {
				var rate = "";
				var needResources = "Need Resources: ";
				var safeResources = "Safe Resources: ";
				if (equipment.enhance != null && equipment.enhance.rate != null)
					rate = equipment.enhance.rate + "%";
				
				if (equipment.enhance != null && equipment.enhance.need_resources != null && equipment.enhance.need_resources.all != null)
					needResources += equipment.enhance.need_resources.all;
				
				if (equipment.enhance != null && equipment.enhance.free_safe_resources != null && equipment.enhance.free_safe_resources.all != null)
					safeResources += equipment.enhance.free_safe_resources.all;
				
				var title = rate + "\r\n" + needResources + "\r\n" + safeResources;
				
				html += "<tr>";
				html += "<td height=\"30\" width=\"11%\" align=\"center\">" + equipment.equipment_id + "</td>";
				html += "<td height=\"30\" width=\"29%\" align=\"center\" title=\"" + equipment.npc_price + "\">" + equipment.equipment_name + "</td>";
				html += "<td height=\"30\" width=\"14%\" align=\"center\">" + equipment.level + "," + equipment.need_hero_level + "," + equipment.enhance_level + "</td>";
				html += "<td height=\"30\" width=\"17%\" align=\"center\">" + equipment.attack + "," + equipment.defense + "," + equipment.intelligence + "</td>";
				html += "<td height=\"30\" width=\"29%\" align=\"center\">";
				html += "<input id=\"equipment_" + equipment.equipment_id + "\" type=\"text\" size=\"2\" />&nbsp;";
				html += "<a href=\"#\" onclick=\"putEquipment(" + equipment.equipment_id + "," + equipment.index + "," + equipment.type + ");return false;\" class=\"AdminToolsLink1\">装备</a>&nbsp;";
				html += "<a href=\"#\" onclick=\"sellEquipment(" + equipment.equipment_id + ");return false;\" class=\"AdminToolsLink1\">卖掉</a>&nbsp;";
				html += "<a href=\"#\" onclick=\"upgradeEquipment(" + equipment.equipment_id + "," + equipment.enhance_level + ");return false;\" class=\"AdminToolsLink1\" title=\"" + title + "\">升级</a>";
				html += "</td>";					
				html += "</tr>";
			});		
			
			html += "</table>";
		}		
	}
		
	return html;
}

function putEquipment(equipmentId, fromIndex, toIndex) {
	var townId = $("#equipment_" + equipmentId).val();
	if (townId == "")
		return;
	
	requestPutEquipment(equipmentId, fromIndex, toIndex, townId);
}

function sellEquipment(equipmentId) {
	var townId = $("#equipment_" + equipmentId).val();
	if (townId == "")
		return;
	
	requestSellEquipment(equipmentId, townId);
}

function upgradeEquipment(equipmentId, enhanceLevel) {
	var townId = $("#equipment_" + equipmentId).val();
	if (townId == "")
		return;
	
	var safe = 0;
	if (enhanceLevel > 4)
		safe = 2;
	
	requestUpgradeEquipment(equipmentId, townId, safe);
}