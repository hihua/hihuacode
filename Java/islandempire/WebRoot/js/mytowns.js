var WebMyTowns = "servlet/WebMyTowns";

function requestMyTowns(townId, callback) {
	var url = WebMyTowns + "?command=0&town_id=" + townId; 
	Ajax_CallBack(url, "", "json", "", true, function(json) {
		if (json != null)
			callback(json, townId);		
	}, function(response, error, status) {
        return null;
    });
}

function requestMyMessages(callback) {
	var url = WebMyTowns + "?command=1"; 
	Ajax_CallBack(url, "", "json", "", true, function(json) {
		if (json != null)
			callback(json);		
	}, function(response, error, status) {
        return null;
    });
}

function requestMyRanks(callback) {
	var url = WebMyTowns + "?command=2"; 
	Ajax_CallBack(url, "", "json", "", true, function(json) {
		if (json != null)
			callback(json);		
	}, function(response, error, status) {
        return null;
    });
}