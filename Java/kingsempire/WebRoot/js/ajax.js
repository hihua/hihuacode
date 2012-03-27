function Ajax_CallBack(Url, PostData, ValueType, CType, IsAsync, SuccessCall, FailCall) {
    var Method = "GET";
    var DataType = "html";
    var Async = false;
    var ContentType = "application/x-www-form-urlencoded";
    
    if (PostData != null && PostData != "")
        Method = "POST";
            
    if (ValueType != null && ValueType != "")
        DataType = ValueType;
        
    if (IsAsync == true)
        Async = true;
    
    if (CType != null && CType != "")
    	ContentType = CType;
            
    if (DataType.toLowerCase() == "xml") {
    	if (Method == "GET") {
    		return $.ajax({
                url: Url, 
                dataType: DataType,                
                type: Method, 
                cache: false,
                async: Async,            
                success: function(xml) {
                    if (SuccessCall) SuccessCall(xml);
                    else return xml;
                },
                error: function(response, error, status) {
                	if (FailCall) FailCall(response, error, status);
                	else return "";            		
                }
            })
    	} else {
    		return $.ajax({
                url: Url, 
                dataType: DataType, 
                contentType: ContentType,
                data: PostData, 
                type: Method, 
                cache: false,
                async: Async,            
                success: function(xml) {
                    if (SuccessCall) SuccessCall(xml);
                    else return xml;
                },
                error: function(response, error, status) {
                	if (FailCall) FailCall(response, error, status);
                	else return "";            		
                }
            })    		
    	}              
    } else {
    	if (Method == "GET") {
    		return $.ajax({
                url: Url, 
                dataType: DataType,                
                type: Method, 
                cache: false,
                async: Async,
                success: function(html) {
                    if (SuccessCall) SuccessCall(html);
                    else return html;
                },
                error: function(response, error, status) {
                	if (FailCall) FailCall(response, error, status);
                	else return "";            		
                }
            })
    	} else {
    		return $.ajax({
                url: Url, 
                dataType: DataType, 
                contentType: ContentType, 
                data: PostData, 
                type: Method, 
                cache: false,
                async: Async,
                success: function(html) {
                    if (SuccessCall) SuccessCall(html);
                    else return html;
                },
                error: function(response, error, status) {
                	if (FailCall) FailCall(response, error, status);
                	else return "";            		
                }
            })    		
    	}        
    }
}

var request = {
	QueryString : function(val) {
		var uri = window.location.search;
 		var re = new RegExp("" +val+ "\=([^\&\?]*)", "ig");
		return ((uri.match(re))?(uri.match(re)[0].substr(val.length+1)):null);
	},

	QueryStrings : function() {
		var uri = window.location.search;
		var re = /\w*\=([^\&\?]*)/ig;
		var retval=[];
		while ((arr = re.exec(uri)) != null)
		retval.push(arr[0]);
		return retval;
	},

	setQuery : function(val1, val2) {
		var a = this.QueryStrings();
		var retval = "";
		var seted = false;
		var re = new RegExp("^" +val1+ "\=([^\&\?]*)$", "ig");
		for(var i=0; i<a.length; i++) {
			if (re.test(a[i])) {
				seted = true;
				a[i] = val1 +"="+ val2;
			}
		}
		
 		retval = a.join("&");
		return "?" +retval+ (seted ? "" : (retval ? "&" : "") +val1+ "=" +val2);
	}
}

String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {     
    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {
        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);     
    } else {
        return this.replace(reallyDo, replaceWith);     
    }     
}     

Date.prototype.pattern = function(fmt) {        
    var o = {        
	    "M+" : this.getMonth() + 1,   
	    "d+" : this.getDate(),
	    "h+" : this.getHours() % 12 == 0 ? 12 : this.getHours() % 12,   
	    "H+" : this.getHours(),
	    "m+" : this.getMinutes(),
	    "s+" : this.getSeconds(),
	    "q+" : Math.floor((this.getMonth() + 3) / 3),
	    "S"  : this.getMilliseconds()
    };
    
    var week = {        
	    "0" : "\u65e5",        
	    "1" : "\u4e00",        
	    "2" : "\u4e8c",        
	    "3" : "\u4e09",        
	    "4" : "\u56db",        
	    "5" : "\u4e94",        
	    "6" : "\u516d"       
    };
    
    if (/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    
    if (/(E+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "\u661f\u671f" : "\u5468") : "")+week[this.getDay()+""]);        
        
    for (var k in o) {        
        if (new RegExp("("+ k +")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    }
    
    return fmt;        
}   
