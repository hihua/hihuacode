function request(url, body, callback) {
	url = "../servlet/" + url;
	Ajax_CallBack(url, body, "json", "", true, function(json) {
		var code = getCode(json);
		if (code == 0) {
			var result = getResult(json);
			callback(code, result);
		} else
			callback(code, null);
	}, function(response, error, status) {
		callback(-2, null);
    });
}

function getCode(json) {
	if (json == null)
		return -1;
	
	return json.code;	
}

function getResult(json) {
	if (json == null)
		return null;
	
	return json.result;
}