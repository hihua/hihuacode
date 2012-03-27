var WebWorldMap = "servlet/WebWorldMap";

function requestWorldMap(userName, x, y, width, callback) {
	var url = WebWorldMap + "?username=" + userName + "&x=" + x + "&y=" + y + "&width=" + width; 
	Ajax_CallBack(url, "", "json", "", true, function(json) {
		if (json != null)
			callback(json, userName);
	}, function(response, error, status) {
        
    });
}