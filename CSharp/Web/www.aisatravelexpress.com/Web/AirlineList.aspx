<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="AirlineList.aspx.cs" Inherits="Web.AirlineList" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
    <title>华捷国际旅游</title>
    <link type="text/css" href="css/aisa.css" rel="stylesheet" />
    <link type="text/css" href="css/gray.css" rel="stylesheet" />
    <script type="text/javascript" src="Js/jquery.js"></script>
</head>
<body>
    <form id="form1" runat="server">
        <div>
            <table id="Tb" width="200" align="center" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td colspan="2" style="height:50px" align="left">Search All Airlines</td>
                </tr>    	              	        
            </table>
        </div>
    </form>
    <script type="text/javascript">
        function GetAirlineList() 
        {           
            for (var i = 1; i <= 34; i++) 
            {
                var Value = window.parent.opener.document.getElementById("checkbox" + i);
                if (Value.checked == true) 
                {
                    var Html = "";
                    Html += "<tr>";
                    Html += "<td width=\"90%\">" + Value.value + "</td>";
                    Html += "<td width=\"10%\"><input type=\"checkbox\" name=\"checkbox" + i + "\" id=\"checkbox" + i + "\" value=\"" + Value.value + "\" checked=\"checked\" /></td>";
                    Html += "</tr>";

                    $("#Tb").append(Html);
                }
                else 
                {
                    var Html = "";
                    Html += "<tr>";
                    Html += "<td width=\"90%\">" + Value.value + "</td>";
                    Html += "<td width=\"10%\"><input type=\"checkbox\" name=\"checkbox" + i + "\" id=\"checkbox" + i + "\" value=\"" + Value.value + "\" /></td>";
                    Html += "</tr>";

                    $("#Tb").append(Html);
                }
            }

            var Html = "";
            Html += "<tr>";
            Html += "<td colspan=\"2\" style=\"height:30px\"><input type=\"submit\" name=\"Confirm\" id=\"Confirm\" value=\"Confirm\" onclick=\"SetAirlineList();\" /></td>";
            Html += "</tr>";

            $("#Tb").append(Html);
        }

        function SetAirlineList() 
        {
            for (var i = 1; i <= 34; i++) 
            {
                var Value = document.getElementById("checkbox" + i);
                if (Value != null && Value.checked == true)                 
                    window.parent.opener.document.getElementById("checkbox" + i).checked = true;
                else
                    window.parent.opener.document.getElementById("checkbox" + i).checked = false;
            }

            window.close();
        }

        GetAirlineList();
    </script>
</body>
</html>
