var WebTransport = "servlet/WebTransport";

function requestTransport(fromTownId, toTownId, resources, callback) {
	var url = WebTransport + "?from_town_id=" + fromTownId + "&to_town_id=" + toTownId + "&" + resources; 
	Ajax_CallBack(url, "", "json", "", true, function(json) {
		if (json == null)
			alert("运送失败");
		
		var ret = json.ret;
		if (ret != null && ret == 0)
			callback();			
		else
			alert("运送失败");
	}, function(response, error, status) {
		alert("运送失败");
    });
}