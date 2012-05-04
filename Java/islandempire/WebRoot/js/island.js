var WebIsland = "servlet/WebIsland";

function requestIsland(x, y, callback) {
	var url = WebIsland + "?x=" + x + "&y=" + y; 
	Ajax_CallBack(url, "", "json", "", true, function(json) {
		if (json != null)
			callback(json);		
	}, function(response, error, status) {
        return null;
    });
}