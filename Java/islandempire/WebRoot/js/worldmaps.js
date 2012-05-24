var WebWorldMaps = "servlet/WebWorldMaps";

function requestWorldMaps(x, y, callback) {
	var url = WebWorldMaps + "?x=" + x + "&y=" + y; 
	Ajax_CallBack(url, "", "json", "", true, function(json) {
		if (json != null)
			callback(json);		
	}, function(response, error, status) {
        return null;
    });
}