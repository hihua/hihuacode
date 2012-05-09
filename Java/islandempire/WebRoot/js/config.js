var WebConfig = "servlet/WebConfig";

function requestConfig(content, callback) {
	var url = WebConfig; 
	Ajax_CallBack(url, content, "xml", "", true, function(xml) {
		if (xml == null) {
			if (content.length == 0)
				alert("获取设置失败");
			else
				alert("提交设置失败");
		} else
			callback(xml);													
	}, function(response, error, status) {
		if (content.length == 0)
			alert("获取设置失败");
		else
			alert("提交设置失败");
    });
}