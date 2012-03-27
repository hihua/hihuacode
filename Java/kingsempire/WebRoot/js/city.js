function CityInfo(cityId, cityName) {
	this.cityId = cityId;
	this.cityName = cityName;
	
	CityInfo.prototype.getCityId = function() {
		return this.cityId;
	};
			
	CityInfo.prototype.getCityName = function() {
		return this.cityName;
	};
}

function UserInfo(username) {
	this.userName = username;
	this.cityInfos = new Array();
		
	UserInfo.prototype.getUserName = function() {
		return this.userName;
	};
	
	UserInfo.prototype.getCityInfos = function() {
		return this.cityInfos;
	};	
}

var WebCities = "servlet/WebCities";

function requestCount(userName, cityInfos) {
	var array = "";
	var url = WebCities + "?command=0&username=" + userName;	
	Ajax_CallBack(url, "", "json", "", true, function(json) {
		if (json == null)
            return null;			
		
		$.each(json.cities, function(idx, city) {
			var cityId = city.cityid;
			var cityName = city.cityname;
			if (cityInfos.length == 0)				
				cityInfos.push(new CityInfo(cityId, cityName));
			else {				
				var found = false;
				for (var i = 0;i < cityInfos.length;i++) {					
					var cityInfo = cityInfos[i];
					if (cityInfo.getCityId() == cityId) {
						found = true;
						break;
					}						
				}
				
				if (!found)
					cityInfos.push(new CityInfo(cityId, cityName));						
			}			
		});				
	}, function(response, error, status) {
        
    });	
}

function requestCities(userName, cityId, callback) {
	var url = WebCities + "?command=1&username=" + userName + "&cityid=" + cityId; 
	Ajax_CallBack(url, "", "json", "", true, function(json) {
		if (json != null)
			callback(json, cityId);		
	}, function(response, error, status) {
        return null;
    });
}

function requestUpdateTime(userName, cityId, callback) {
	var url = WebCities + "?command=2&username=" + userName + "&cityid=" + cityId;
	return Ajax_CallBack(url, "", "json", "", true, function(json) {
		if (json != null)
			callback(json, cityId)		
	}, function(response, error, status) {
        return null;
    });
}

function requestCity(userName, owner, cityId, callback) {
	var url = WebCities + "?command=3&username=" + userName + "&owner=" + owner + "&cityid=" + cityId; 
	Ajax_CallBack(url, "", "json", "", true, function(json) {
		if (json != null)
			callback(json, cityId);		
	}, function(response, error, status) {
        return null;
    });
}

function requestConfigureWorkers(userName, cityId, type, number, callback) {
	var url = WebCities + "?command=4&username=" + userName + "&cityid=" + cityId + "&type=" + type + "&number=" + number; 
	Ajax_CallBack(url, "", "json", "", true, function(json) {
		if (json != null && json.ret == 0)
			callback(true);
		else
			callback(false);		
	}, function(response, error, status) {
		callback(false);
    });
}