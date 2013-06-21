<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Login.aspx.cs" Inherits="Web.Admin.Login" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>华捷国际旅游后台</title>    
    <link type="text/css" href="css.css" rel="stylesheet" />
</head>
<body>
    <form id="form1" runat="server">
        <div>
            <table width="640" border="0" cellspacing="0" cellpadding="0" align="center">
	            <tr>
      	            <td height="390" valign="top" background="images/admin.gif">
			            <table width="640" height="276" border="0" cellpadding="0" cellspacing="0">
        		            <tr>
          			            <td width="338" height="109">&nbsp;</td>
          			            <td width="302">&nbsp;</td>
        		            </tr>
				            <tr>
				  	            <td>&nbsp;</td>
				  	            <td valign="top">						            
						            <table width="100%" border="0" cellspacing="0" cellpadding="0">
						  	            <tr>
								            <td width="25%" height="26">&nbsp;</td>
								            <td colspan="2">&nbsp;</td>
								            <td width="8%">&nbsp;</td>
						  	            </tr>
						  	            <tr>
								            <td height="30"><span>管理员帐号</span>:</td>
								            <td height="30" colspan="2">
                                                <asp:TextBox ID="AdminUser_Name" runat="server" MaxLength="30"></asp:TextBox>
                                            </td>
								            <td height="30">&nbsp;</td>
						  	            </tr>
						  	            <tr>
								            <td height="30"><span>管理员密码</span>:</td>
								            <td height="30" colspan="2">
                                                <asp:TextBox ID="AdminUser_PassWord" runat="server" MaxLength="16" Width="149" TextMode="Password"></asp:TextBox>
                                            </td>
								            <td height="30">&nbsp;</td>
						  	            </tr>
						  	            <tr>
								            <td height="30"><span>安全验证码:</span></td>
								            <td width="30%" height="30">
                                                <asp:TextBox ID="Code" runat="server" Width="90"></asp:TextBox>
                                            </td>
								            <td width="37%"><img src="Code.aspx" onclick="this.src='Code.aspx';" style="cursor:pointer" alt=""/></td>
								            <td height="30">&nbsp;</td>
						  	            </tr>
						   	            <tr>
								            <td height="30" colspan="3">
									            <table width="100%" border="0" cellspacing="0" cellpadding="0">
							  			            <tr>
											            <td width="26%" height="34">&nbsp;</td>
											            <td width="25%"><asp:Button ID="Login_Submit" runat="server" Text=" 登录 " onclick="Login_Submit_Click" /></td>
											            <td><asp:Button ID="Login_Reset" runat="server" Text=" 重置 " OnClientClick="window.location.href=window.location.href;" /></td>
										            </tr>
									            </table>
								            </td>
								            <td height="30">&nbsp;</td>
						  	            </tr>
						            </table>				  		            
				  	            </td>
				            </tr>
      		            </table>
		            </td>
	            </tr>
            </table>
        </div>
    </form>
</body>
</html>
