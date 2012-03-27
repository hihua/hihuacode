var WebRecruit = "servlet/WebRecruit";

function requestRecruit(userName, cityId, soldierName, count, callback) {
	var url = WebRecruit + "?username=" + userName + "&cityid=" + cityId + "&soldiername=" + soldierName + "&count=" + count; 
	Ajax_CallBack(url, "", "json", "", true, function(json) {
		if (json != null && json.ret == 0)
			callback(true);
		else
			callback(false);		
	}, function(response, error, status) {
		callback(false);
    });
}