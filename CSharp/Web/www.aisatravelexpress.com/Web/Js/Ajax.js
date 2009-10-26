function Ajax_GetPost(Url, PostData, ValueType, IsAsync)
{       
    var Method = "GET";
    var DataType = "html";
    var Async = false;
    
    if (PostData != "")
        Method = "POST";
            
    if (ValueType != "")
        DataType = ValueType;
        
    if (IsAsync == true)
        Async = true;            
    
    if (DataType.toLowerCase() == "xml")
    {        
        return $.ajax(
        {
            url: Url, 
            dataType: DataType, 
            data: PostData, 
            type: Method, 
            cache: false,
            async: Async
        
        }).responseXML;        
    }
    else
    {
        return $.ajax(
        {
            url: Url, 
            dataType: DataType, 
            data: PostData, 
            type: Method, 
            cache: false,
            async: Async
        
        }).responseText;    
    }
}          

function Ajax_Control(Url, PostData, ControlID, ValueType, IsAppend, IsAsync)
{
    var ControlID = "#" + ControlID;
    var Method = "GET";
    var DataType = "html";
    var Async = false;                     
    
    if (PostData != "")
        Method = "POST";
    
    if (ValueType != "")
        DataType = ValueType;
        
    if (IsAsync == true)
        Async = true;
       
    $.ajax(
    { 
        url: Url, 
        dataType: DataType, 
        data: PostData,
        type: Method, 
        cache: false, 
        async: Async,
        success: function(content)
        {
            if (IsAppend == 0)    
                $(ControlID).empty(); 
         
            if (DataType == "html")
                $(ControlID).html($(ControlID).html() + content)
            else
                $(ControlID).text($(ControlID).text() + content);
        }        
    });
}
       
function Ajax_SelectControl(Url, PostData, ControlID, IsAppend, IsAsync, ParentNode, ChildNode_Value, ChildNode_Name)
{
    var ControlID = "#" + ControlID;
    var Method = "GET";
    var Async = false;    
                     
    if (PostData != "")
        Method = "POST";
    
    if (IsAsync == true)
        Async = true;
        
    if (DataType.toLowerCase() == "xml")
    {        
        return $.ajax(
        {
            url: Url, 
            dataType: DataType, 
            data: PostData, 
            type: Method, 
            cache: false,
            async: Async
        
        }).responseXML;        
    }
    else
    {
        return $.ajax(
        {
            url: Url, 
            dataType: DataType, 
            data: PostData, 
            type: Method, 
            cache: false,
            async: Async
        
        }).responseText;    
    }
    
    $.ajax(
    { 
        url: Url, 
        dataType: "xml", 
        data: PostData,
        type: Method, 
        cache: false, 
        async: Async, 
        success: function(content)
        {   
            if (content.xml == "")
                return;
        
            if (IsAppend == 0)    
                $(ControlID).empty();     
        
            $(content).find(ParentNode).each(function()
            {                        
                var OptionValue = $(this).children(ChildNode_Value).text();
                var OptionName = $(this).children(ChildNode_Name).text(); 
                OptionName = HTMLEncode(OptionName);
                               
                var Option = "<option value=\"" + OptionValue + "\">" + OptionName + "</option>";
                $(Option).appendTo(ControlID);
            });
        }
    });
}

function Ajax_CallBack(Url, PostData, ValueType, IsAsync, SuccessCall)
{
    var Method = "GET";
    var DataType = "html";
    var Async = false;
    
    if (PostData != "")
        Method = "POST";
            
    if (ValueType != "")
        DataType = ValueType;
        
    if (IsAsync == true)
        Async = true;
            
    if (DataType.toLowerCase() == "xml")
    {        
        return $.ajax(
        {
            url: Url, 
            dataType: DataType, 
            data: PostData, 
            type: Method, 
            cache: false,
            async: Async,            
            success: function(xml) {
                if (SuccessCall) SuccessCall(xml);
                else return xml;
            }        
        })      
    }
    else
    {
        return $.ajax(
        {
            url: Url, 
            dataType: DataType, 
            data: PostData, 
            type: Method, 
            cache: false,
            async: Async,
            success: function(html) {
                if (SuccessCall) SuccessCall(html);
                else return html;
            }        
        })
    }
}

var request = 
{
	QueryString : function(val) 
	{
		var uri = window.location.search;
 		var re = new RegExp("" +val+ "\=([^\&\?]*)", "ig");
		return ((uri.match(re))?(uri.match(re)[0].substr(val.length+1)):null);
	},

	QueryStrings : function() 
	{
		var uri = window.location.search;
		var re = /\w*\=([^\&\?]*)/ig;
		var retval=[];
		while ((arr = re.exec(uri)) != null)
		retval.push(arr[0]);
		return retval;
	},

	setQuery : function(val1, val2) 
	{
		var a = this.QueryStrings();
		var retval = "";
		var seted = false;
		var re = new RegExp("^" +val1+ "\=([^\&\?]*)$", "ig");
		for(var i=0; i<a.length; i++) 
		{
			if (re.test(a[i])) 
			{
				seted = true;
				a[i] = val1 +"="+ val2;
			}
		}
		
 		retval = a.join("&");
		return "?" +retval+ (seted ? "" : (retval ? "&" : "") +val1+ "=" +val2);
	}
}