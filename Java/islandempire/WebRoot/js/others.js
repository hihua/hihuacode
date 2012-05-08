var WebOthers = "servlet/WebOthers";

function requestOthers(username, townId, callback) {
	var url = WebOthers + "?command=0&username=" + username + "&town_id=" + townId; 
	Ajax_CallBack(url, "", "json", "", true, function(json) {
		if (json != null)
			callback(json);		
	}, function(response, error, status) {
        return null;
    });
}

function requestOtherTowns(username, townId, callback) {
	var url = WebOthers + "?command=1&username=" + username + "&town_id=" + townId; 
	Ajax_CallBack(url, "", "json", "", true, function(json) {
		if (json != null)
			callback(json, townId);		
	}, function(response, error, status) {
        return null;
    });
}

function requestOtherMessages(username, page, callback) {
	var url = WebOthers + "?command=2&username=" + username + "&page=" + page;
	Ajax_CallBack(url, "", "json", "", true, function(json) {
		if (json != null)
			callback(json, username, page);		
	}, function(response, error, status) {
        return null;
    });
}