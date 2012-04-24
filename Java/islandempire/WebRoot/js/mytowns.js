var WebMyTowns = "servlet/WebMyTowns";

function requestMyTowns(id, callback) {
	var url = WebMyTowns + "?command=0&id=" + id; 
	Ajax_CallBack(url, "", "json", "", true, function(json) {
		if (json != null)
			callback(json, id);		
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