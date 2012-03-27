var WebMessages = "servlet/WebMessages";

function requestMessages(userName, callback) {
	var url = WebMessages + "?command=0&username=" + userName; 
	Ajax_CallBack(url, "", "json", "", true, function(json) {
		if (json != null)
			callback(json, userName);
	}, function(response, error, status) {
        
    });
}

function requestOtherMessages(userName, to, page, callback) {
	var url = WebMessages + "?command=1&username=" + userName + "&to=" + to + "&page=" + page; 
	Ajax_CallBack(url, "", "json", "", true, function(json) {
		if (json != null)
			callback(json, userName, to, page);
	}, function(response, error, status) {
        
    });
}