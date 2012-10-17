var WebEquipment = "servlet/WebEquipment";

function requestSetEquipment(equipmentId, fromIndex, toIndex, townId) {
	var url = WebEquipment + "?equipment_id=" + equipmentId + "&from_index=" + fromIndex + "&to_index=" + toIndex + "&town_id=" + townId; 
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
			$.each(equipments, function(idx, equipment) {
				if (equipment.equipment_id == null)
					return;
								
				html += "<tr>";
				html += "<td height=\"30\" width=\"13%\" align=\"center\">" + equipment.equipment_id + "</td>";
				html += "<td height=\"30\" width=\"35%\" align=\"center\">" + equipment.equipment_name + "</td>";
				html += "<td height=\"30\" width=\"15%\" align=\"center\">" + equipment.level + "," + equipment.need_hero_level + "," + equipment.enhance_level + "</td>";
				html += "<td height=\"30\" width=\"17%\" align=\"center\">" + equipment.attack + "," + equipment.defense + "," + equipment.intelligence + "</td>";
				html += "<td height=\"30\" width=\"20%\" align=\"center\"><input id=\"equipment_" + equipment.equipment_id + "\" type=\"text\" size=\"2\" />&nbsp;<a href=\"#\" onclick=\"putEquipment(" + equipment.equipment_id + "," + equipment.index + "," + equipment.type + ");return false;\" class=\"AdminToolsLink1\">装备</a></td>";					
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
	
	requestSetEquipment(equipmentId, fromIndex, toIndex, townId);
}