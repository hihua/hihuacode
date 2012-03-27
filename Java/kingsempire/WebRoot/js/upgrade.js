var WebUpgrade = "servlet/WebUpgrade";

function requestUpgradeBuilding(userName, buildingId, callback) {
	var url = WebUpgrade + "?command=0&username=" + userName + "&buildingid=" + buildingId; 
	Ajax_CallBack(url, "", "json", "", true, function(json) {
		if (json != null && json.ret == 0)
			callback(true);
		else
			callback(false);		
	}, function(response, error, status) {
		callback(false);
    });
}

function requestUpgradeSkills(userName, skillId, callback) {
	var url = WebUpgrade + "?command=1&username=" + userName + "&skillid=" + skillId; 
	Ajax_CallBack(url, "", "json", "", true, function(json) {
		if (json != null && json.ret == 0)
			callback(true);
		else
			callback(false);		
	}, function(response, error, status) {
		callback(false);
    });
}