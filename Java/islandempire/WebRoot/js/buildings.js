var WebBuildings = "servlet/WebBuildings";

function requestBuildings(buildingId) {
	var url = WebBuildings + "?building_id=" + buildingId; 
	Ajax_CallBack(url, "", "json", "", true, function(json) {
		if (json == null)
			alert("升级失败");
		
		var ret = json.ret;
		if (ret != null && ret == 0)
			alert("升级成功");
		else
			alert("升级失败");
	}, function(response, error, status) {
		alert("升级失败");
    });
}