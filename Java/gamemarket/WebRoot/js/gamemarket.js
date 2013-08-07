function request(url, body, callback) {
	url = "../servlet/" + url;
	Ajax_CallBack(url, body, "json", "", true, function(json) {
		var code = getCode(json);
		switch (code) {
			case -1:
				alert("超时，请重新登录");
				window.top.location.href = "login.jsp";
				break;
				
			case 0:
				var content = getContent(json);
				callback(code, content);
				break;
				
			default:
				callback(code, null);
				break;
		}		
	}, function(response, error, status) {
		callback(-3, null);
    });
}

function upload(url, form, callback) {	
	url = "../servlet/" + url;	
	Ajax_Submit(url, form, "json", true, function(json) {
		var code = getCode(json);
		switch (code) {
			case -1:
				alert("超时，请重新登录");
				window.top.location.href = "login.jsp";
				break;
				
			case 0:
				var content = getContent(json);
				callback(code, content);
				break;
				
			default:
				callback(code, null);
				break;
		}		
	}, function(response, error, status) {
		callback(-3, null);
    });	
}

function getCode(json) {
	if (json == null)
		return -2;			
	
	return json.code;	
}

function getContent(json) {
	if (json == null)
		return null;
	
	return json.content;
}

function clearTable(name) {
	var table = $(name);
	var trs = table.find("tr");
	var length = trs.length;
	while (length > 1) {			
		trs.eq(1).remove();
		trs = table.find("tr");
		length = trs.length;		
	}
}

function checkDate(sDate) {		
	var iaMonthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];		
	var year, month, day;
	
	var iaDate = sDate.toString().split("-");
	if (iaDate.length != 3) 
		return false;
		
	if (iaDate[1].length > 2 || iaDate[2].length > 2) 
		return false;

	year = parseFloat(iaDate[0]);
	month = parseFloat(iaDate[1]);
	day = parseFloat(iaDate[2]);

	if (year < 1700 || year > 2030) 
		return false;
		
	if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) 
		iaMonthDays[1] = 29;
	
	if (month < 1 || month > 12) 
		return false;
		
	if (day < 1 || day > iaMonthDays[month - 1]) 
		return false;
	
	return true;
}

function getTimestamp(date) {
    var arr = date.split("-");
    var d = new Date(Date.UTC(arr[0], arr[1] - 1, arr[2], 0, 0, 0));
    return d.getTime() / 1000 - 8 * 3600;
}