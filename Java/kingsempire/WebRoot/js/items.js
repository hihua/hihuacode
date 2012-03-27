var WebItems = "servlet/WebItems";

function requestItems(userName, callback) {
	var url = WebItems + "?command=0&username=" + userName; 
	Ajax_CallBack(url, "", "json", "", true, function(json) {
		if (json != null)
			callback(json, userName);		
	}, function(response, error, status) {
        
    });
}

function requestUseItems(userName, cityId, itemType, number, callback) {
	var url = WebItems + "?command=1&username=" + userName + "&cityid=" + cityId + "&itemtype=" + itemType + "&number=" + number; 
	Ajax_CallBack(url, "", "json", "", true, function(json) {
		if (json != null && json.ret == 0)
			callback(true);
		else
			callback(false);
	}, function(response, error, status) {
		callback(false);
    });
}

function requestOtherItems(userName, owner, callback) {
	var url = WebItems + "?command=2&username=" + userName + "&owner=" + owner; 
	Ajax_CallBack(url, "", "json", "", true, function(json) {
		if (json != null)
			callback(json, owner);		
	}, function(response, error, status) {
        
    });
}