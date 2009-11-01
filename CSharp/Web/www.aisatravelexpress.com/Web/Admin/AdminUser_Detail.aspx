<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="AdminUser_Detail.aspx.cs" Inherits="Web.Admin.AdminUser_Detail" %>

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
            <table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
	            <tr>
		            <td align="center" class="DivTitle1" height="30">管理员管理</td>
	            </tr>
            </table>
            <table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr bgcolor="#E3E3E3">
                    <td height="30" align="center">
                        用户名：<asp:TextBox ID="AdminUser_Name" runat="server" MaxLength="30"></asp:TextBox>
                    </td>
                    <td align="center">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;呢称：<asp:TextBox ID="AdminUser_NickName" runat="server" MaxLength="30"></asp:TextBox>
                    </td>
                </tr>
                <tr bgcolor="#D0D0D0">
	                <td height="30" align="center">
	                    &nbsp;&nbsp;&nbsp;&nbsp;密码：<asp:TextBox ID="AdminUser_PassWord" runat="server" TextMode="Password" MaxLength="16" Width="149"></asp:TextBox>
                    </td>
	                <td align="center">
	                    确认密码：<asp:TextBox ID="AdminUser_PassWord1" runat="server" TextMode="Password" MaxLength="16" Width="149"></asp:TextBox>
                    </td>
	            </tr>
	            <tr bgcolor="#E3E3E3">
	                <td height="30" align="center" id="AdminUser_PassWord_TD" runat="server">
	                    原密码：<asp:TextBox ID="AdminUser_PassWord2" runat="server" TextMode="Password" MaxLength="16" Width="149"></asp:TextBox></td>
	                <td height="30" align="center" id="AdminUser_Status_TD" runat="server">
	                    &nbsp;&nbsp;权限：
	                    <asp:RadioButton ID="AdminUser_Status1" runat="server" GroupName="AdminUser_Status" Text="管理员" />
                        <asp:RadioButton ID="AdminUser_Status2" runat="server" GroupName="AdminUser_Status" Text="普通用户" Checked="true" />
                    </td>
	            </tr>
	            <tr bgcolor="#D0D0D0">
	                <td id="AdminUser_AddTime" height="30" colspan="2" align="center" runat="server">创建时间：</td>
	            </tr>
	            <tr bgcolor="#E3E3E3">
	                <td height="30" colspan="2" align="center">
                        <asp:Button ID="AdminUser_Submit" runat="server" Text=" 添加 " onclick="AdminUser_Submit_Click" />
                        &nbsp;
                        <asp:Button ID="AdminUser_Reset" runat="server" Text=" 重置 " OnClientClick="window.location.href=window.location.href;"  />
                        &nbsp;
                        <asp:Button ID="AdminUser_Close" runat="server" Text=" 关闭 " OnClientClick="window.close();" />
                    </td>
	            </tr>
            </table>
        </div>
    </form>
</body>
</html>
