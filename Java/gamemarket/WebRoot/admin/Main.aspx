<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Main.aspx.cs" Inherits="Web.Admin.Main" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
    <title>华捷国际旅游后台</title>
    <link type="text/css" href="css.css" rel="stylesheet" />
    <script type="text/javascript" src="../Js/jquery.js"></script>
</head>
<body>
    <form id="form1" runat="server">
        <div>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
	            <tr>
    	            <td valign="top" height="100%" width="162">
    		            <iframe frameborder="0" id="Tools" name="Tools" scrolling="no" src="Tools.aspx" width="162"></iframe>
    	            </td>
    	            <td bgcolor="#A4B6D7" width="9" valign="middle" id="Banner">
    		            <table border="0" cellpadding="0" cellspacing="0">
      			            <tr>
					            <td>						
						            <br />
						            <br />
						            <br />
						            <br />
						            <br />
						            <br />
						            <br />
						            <br />
						            <br />
						            <br />
						            <br />
						            <br />
						            <br />
						            <br />
						            <br />
						            <br />
						            <br />
						            <br />
						            <br />
						            <br />
						            <br />
					            </td>
      			            </tr>
    		            </table>
    	            </td>
		            <td valign="top">
			            <table cellspacing="0" cellpadding="0" width="100%" border="0">
				            <tr>
					            <td align="center" width="100%" height="25" background="images/titlebg.gif"><span style="color:#330099; font-size:16px; font-weight:bold">网站管理后台</span></td>
				            </tr>
				            <tr>
					            <td><iframe frameborder="0" id="Main" name="Main" scrolling="yes" src="Welcome.aspx" width="100%"></iframe></td>
				            </tr>
			            </table>			
		            </td>
  	            </tr>
            </table>    
        </div>
    </form>
</body>
<script type="text/javascript">
$("#Tools").css("height", Number($(document).height()));
$("#Main").css("height", Number($(document).height() - 30));
</script>
</html>
