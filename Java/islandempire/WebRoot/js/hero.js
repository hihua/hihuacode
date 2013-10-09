function setHero(json) {
	var html = "";
	var town = json.town;	
	if (town != null) {	
		var hero = town.hero;
		if (hero != null) {
			var date = new Date(hero.restore_energy_at * 1000);
			var restoreEnergyAt = date.pattern("yyyy-MM-dd HH:mm:ss");
			
			date = new Date(hero.recovery_at * 1000);
			var recoveryAt = date.pattern("yyyy-MM-dd HH:mm:ss");			
						
			html += "<table width=\"97%\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">";
			html += "<tr><td width=\"50%\" valign=\"top\">";
			html += "<table width=\"100%\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">";
			html += "<tr><td align=\"left\" height=\"25\" width=\"25%\">名称: </td><td>" + hero.name + "</td><td align=\"left\">" + hero.id + "</td></tr>";
			html += "<tr><td align=\"left\" height=\"25\" width=\"25%\">技能: </td><td colspan=\"2\">" + hero.specialty + "</td></tr>";
			html += "<tr><td align=\"left\" height=\"25\" width=\"25%\">经验: </td><td>" + hero.experience + "/" + hero.next_level_exp + "</td><td align=\"left\">Lv: " + hero.level + "</td></tr>";
			html += "<tr><td align=\"left\" height=\"25\" width=\"25%\">能量: </td><td colspan=\"2\">" + hero.energy + "/" + hero.max_energy + "</td></tr>";
			html += "<tr><td align=\"left\" height=\"25\" width=\"25%\">攻击: </td><td>" + hero.attack + "/" + hero.total_attack + "</td><td align=\"left\">" + hero.init_attack + "</td></tr>";
			html += "<tr><td align=\"left\" height=\"25\" width=\"25%\">防御: </td><td>" + hero.defense + "/" + hero.total_defense + "</td><td align=\"left\">" + hero.init_defense + "</td></tr>";
			html += "<tr><td align=\"left\" height=\"25\" width=\"25%\">敏捷: </td><td>" + hero.intelligence + "/" + hero.total_intelligence + "</td><td align=\"left\">" + hero.init_intelligence + "</td></tr>";
			html += "<tr><td align=\"left\" height=\"25\" width=\"25%\">智力: </td><td>" + hero.genius + "</td><td>&nbsp;</td></tr>";
			html += "<tr><td align=\"left\" height=\"25\" width=\"25%\">能力点: </td><td>" + hero.ability_point + "</td><td>&nbsp;</td></tr>";
			html += "<tr><td align=\"left\" height=\"25\" width=\"25%\">状态: </td><td colspan=\"2\">" + hero.status + "</td></tr>";	
			html += "<tr><td align=\"left\" height=\"25\" width=\"25%\">活动: </td><td colspan=\"2\">" + hero.hero_city_effect + "</td></tr>";
			html += "<tr><td align=\"left\" height=\"25\" width=\"25%\">创建时间: </td><td colspan=\"2\">" + recoveryAt + "</td></tr>";
			html += "<tr><td align=\"left\" height=\"25\" width=\"25%\">恢复时间: </td><td colspan=\"2\">" + restoreEnergyAt + "</td></tr>";			    					    			    			    			
			html += "</table></td>";
			html += "<td width=\"50%\" valign=\"top\">";
			html += "<table width=\"100%\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">";			
			var equipments = hero.equipments;
			if (equipments != null) {
				$.each(equipments, function(idx, equipment) {
					if (equipment.equipment_id == null)
						return;
						
					html += "<tr><td align=\"left\" height=\"25\" title=\"" + equipment.equipment_id + "\">" + equipment.equipment_name + "</td>";					
					html += "<td>" + equipment.level + "," + equipment.need_hero_level + "," + equipment.enhance_level + "</td>";
					html += "<td>" + equipment.attack + "," + equipment.defense + "," + equipment.intelligence + "</td></tr>";					
				});				
			}
			
			html += "</table></td>";
			html += "</tr></table><br/>";
			html += "<table width=\"97%\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">";			
			var skills = hero.skills;
			if (skills != null) {
				$.each(skills, function(idx, skill) {
					html += "<tr><td align=\"left\" height=\"25\">" + skill.name + "</td><td>" + skill.desc + "</td></tr>";					
				});				
			}
			
			html += "</table>";
			
			var divHero = "hero_" + hero.town_id;
			if ($("body").find("#" + divHero).length == 0)
				$("body").append("<div id=\"" + divHero + "\" style=\"display:none\">" + html + "</div>");			
			else
				$("#" + divHero).html(html);
		}					
	}	
}