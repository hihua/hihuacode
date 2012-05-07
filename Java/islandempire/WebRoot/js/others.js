var WebOthers = "servlet/WebOthers";

function requestOthers(username, townId, callback) {
	var url = WebMyTowns + "?command=0&username=" + username + "&town_id=" + townId; 
	Ajax_CallBack(url, "", "json", "", true, function(json) {
		if (json != null)
			callback(json);		
	}, function(response, error, status) {
        return null;
    });
}

function requestOtherTowns(username, townId, callback) {
	var url = WebMyTowns + "?command=1&username=" + username + "&town_id=" + townId; 
	Ajax_CallBack(url, "", "json", "", true, function(json) {
		if (json != null)
			callback(json, townId);		
	}, function(response, error, status) {
        return null;
    });
}

function requestOtherMessages(username, page, callback) {
	var url = WebMyTowns + "?command=2&username=" + username + "&town_id=" + townId;  
	Ajax_CallBack(url, "", "json", "", true, function(json) {
		if (json != null)
			callback(json);		
	}, function(response, error, status) {
        return null;
    });
}