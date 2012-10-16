var WebEquipment = "servlet/WebEquipment";

function requestEquipment(townId, callback) {
	var url = WebEquipment + "?town_id=" + townId; 
	Ajax_CallBack(url, "", "json", "", true, function(json) {
		if (json != null)
			callback(json);		
	}, function(response, error, status) {
        return null;
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
				html += "<td height=\"30\" width=\"37%\" align=\"center\">" + equipment.equipment_name + "</td>";
				html += "<td height=\"30\" width=\"15%\" align=\"center\">" + equipment.level + "," + equipment.need_hero_level + "," + equipment.enhance_level + "</td>";
				html += "<td height=\"30\" width=\"17%\" align=\"center\">" + equipment.attack + "," + equipment.defense + "," + equipment.intelligence + "</td>";
				html += "<td height=\"30\" width=\"18%\" align=\"center\">&nbsp;</td>";					
				html += "</tr>";
			});
			
			html += "</table>";
		}		
	}
		
	return html;
}