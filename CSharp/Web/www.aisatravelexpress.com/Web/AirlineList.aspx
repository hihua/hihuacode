<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="AirlineList.aspx.cs" Inherits="Web.AirlineList" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
    <title>华捷国际旅游</title>
    <link type="text/css" href="css/aisa.css" rel="stylesheet" />
    <link type="text/css" href="css/gray.css" rel="stylesheet" />
    <script type="text/javascript">
        function SetAirlineList()
        {
            var AirlineList = "";
            for (var i = 1; i < 9; i++) 
            {
                var Value = document.getElementById("checkbox" + i);
                if (Value != null && Value.checked == true)
                    AirlineList += Value.value + "\r\n";
            }

            window.parent.opener.document.getElementById("LowFare_Airline_Text").value = AirlineList;
            window.close();            
        }
    </script>
</head>
<body>
    <form id="form1" runat="server">
        <div>
            <table width="200" align="center" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td colspan="2" style="height:50px" align="left">Search All Airlines</td>
                </tr>
    	        <tr>
        	        <td width="90%">Air Canada</td>
                    <td width="10%"><input type="checkbox" name="checkbox1" id="checkbox1" value="Air Canada" /></td>
                </tr>
                <tr>
        	        <td width="90%">Air China</td>
                    <td width="10%"><input type="checkbox" name="checkbox2" id="checkbox2" value="Air China" /></td>
                </tr>
                <tr>
        	        <td width="90%">Air France</td>
                    <td width="10%"><input type="checkbox" name="checkbox3" id="checkbox3" value="Air France" /></td>
      	        </tr>
      	        <tr>
        	        <td width="90%">Alaska Airlines</td>
                    <td width="10%"><input type="checkbox" name="checkbox4" id="checkbox4" value="Alaska Airlines" /></td>
      	        </tr>
      	        <tr>
        	        <td width="90%">All Nippon Airways</td>
                    <td width="10%"><input type="checkbox" name="checkbox5" id="checkbox5" value="All Nippon Airways" /></td>
      	        </tr>
      	        <tr>
        	        <td width="90%">America West Airlines</td>
                    <td width="10%"><input type="checkbox" name="checkbox6" id="checkbox6" value="America West Airlines" /></td>
      	        </tr>
      	        <tr>
        	        <td width="90%">American Airlines</td>
                    <td width="10%"><input type="checkbox" name="checkbox7" id="checkbox7" value="American Airlines" /></td>
      	        </tr>
      	        <tr>
        	        <td width="90%">Asiana</td>
                    <td width="10%"><input type="checkbox" name="checkbox8" id="checkbox8" value="Asiana" /></td>
      	        </tr>
      	        <tr>
                    <td colspan="2" style="height:30px"><input type="submit" name="Confirm" id="Confirm" value="Confirm" onclick="SetAirlineList();" /></td>
                </tr>
            </table>
        </div>
    </form>
</body>
</html>
