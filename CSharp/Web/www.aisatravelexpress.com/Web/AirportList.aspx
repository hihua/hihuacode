<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="AirportList.aspx.cs" Inherits="Web.AirportList" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
    <title>华捷国际旅游</title>
    <script type="text/javascript">
        var City_TextBox = "<%Response.Write(City_TextBox);%>";
        function SetCity(City)
        {
            if (City != "" && City_TextBox != "")
            {
                window.parent.opener.document.getElementById(City_TextBox).value = City;
                window.close();
            }
        }
    </script>
    
    <style type="text/css">
    a.AdminToolsLink1 
    {	
	    font-size: 10px;
	    color: #0000FF;
    }
    
    a.AdminToolsLink1:link 
    {
	    text-decoration: none;
	    color:#0000FF;
    }
    
    a.AdminToolsLink1:visited 
    {
	    text-decoration: none;
	    color: #0000FF;
    }
    
    a.AdminToolsLink1:hover  
    {
	    text-decoration: underline;
	    color: #FF0000;
    }
    
    a.AdminToolsLink1:active  
    {
	    text-decoration: none;
	    color: #000000;
    }
    
    a.AdminToolsLink2 
    {	
	    font-size: 12px;
	    color: #0000FF;
    }
    
    a.AdminToolsLink2:link 
    {
	    text-decoration: none;
	    color:#0000FF;
    }
    
    a.AdminToolsLink2:visited 
    {
	    text-decoration: none;
	    color: #0000FF;
    }
    
    a.AdminToolsLink2:hover  
    {
	    text-decoration: underline;
	    color: #FF0000;
    }
    
    a.AdminToolsLink2:active  
    {
	    text-decoration: none;
	    color: #000000;
    }
    
    body 
    {
        font-family: Verdana, Arial, Tahoma, Helvetica, sans-serif;
        font-size: 10px;
        margin-left: 10px;
        margin-top: 10px;
        margin-right: 10px;
        margin-bottom: 10px;
    }
    
    .pre 
    {
    	font-family: Courier New, courier, monospace;
    	font-size: 12px;
    	letter-spacing: 4px;
    }
    
    .title 
    {
        font-family: Verdana, Arial, Tahoma, Helvetica, sans-serif;
        font-size: 12px;
        font-weight: bold;
    }
    </style>
</head>
<body>
    <form id="form1" runat="server">
        <div>
            <a name="TOP"></a>
            <div class="title">
                <a href="?City_Country=0&City_TextBox=<%Response.Write(City_TextBox);%>" class="AdminToolsLink2">All Cities</a> | <a href="?City_Country=1&City_TextBox=<%Response.Write(City_TextBox);%>" class="AdminToolsLink2">China Cities</a> | <a href="?City_Country=2&City_TextBox=<%Response.Write(City_TextBox);%>" class="AdminToolsLink2">US Cities</a>
            </div><br/>
            <div class="pre"><a href="#A">A</a>|<a href="#B">B</a>|<a href="#C">C</a>|<a href="#D">D</a>|<a href="#E">E</a>|<a href="#F">F</a>|<a href="#G">G</a>|<a href="#H">H</a>|<a href="#I">I</a>|<a href="#J">J</a>|<a href="#K">K</a>|<a href="#L">L</a>|<a href="#M">M</a><br/>
            <a href="#N">N</a>|<a href="#O">O</a>|<a href="#P">P</a>|<a href="#Q">Q</a>|<a href="#R">R</a>|<a href="#S">S</a>|<a href="#T">T</a>|<a href="#U">U</a>|<a href="#V">V</a>|<a href="#W">W</a>|<a href="#X">X</a>|<a href="#Y">Y</a>|<a href="#Z">Z</a></div>

            <%Response.Write(o_CityList.ToString());%>                
        </div>
    </form>
</body>
</html>
