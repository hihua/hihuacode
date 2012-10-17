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

function requestMyEquipment(townId, callback) {
	var url = WebMyTowns + "?command=3&town_id=" + townId; 
	Ajax_CallBack(url, "", "json", "", true, function(json) {
		if (json != null)
			callback(townId, json);		
	}, function(response, error, status) {
        return null;
    });
}

function requestPostMyMessage(to, from, subject, body) {
	var url = WebMyTowns + "?command=4";
	var content = "to=" + to + "&from=" + from + "&subject=" + subject + "&body=" + body;
	Ajax_CallBack(url, content, "json", "", true, function(json) {
		if (json == null)
			alert("发送失败");
		else {
			var ret = json.ret;
			if (ret != null && ret == 0)
				alert("发送成功");	
			else
				alert("发送失败");
		}		
	}, function(response, error, status) {
		alert("发送失败");
    });
}

function requestMyBattle(id, mailId, callback) {
	var url = WebMyTowns + "?command=5&mail_id=" + mailId; 
	Ajax_CallBack(url, "", "json", "", true, function(json) {
		if (json != null)
			callback(id, mailId, json);		
	}, function(response, error, status) {
        return null;
    });
}