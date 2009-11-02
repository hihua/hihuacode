<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="AdminUser.aspx.cs" Inherits="Web.Admin.AdminUser" %>

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
            <table align="center" border="0" width="80%" cellpadding="0" cellspacing="0">
	            <tr>
		            <td align="center" class="DivTitle1" height="30">管理员管理</td>
	            </tr>
            </table>
            <table id="g_MainTable" width="80%" border="1" align="center" cellpadding="0" cellspacing="0" class="Table1" runat="server">
                <tr height="30" align="center" bgcolor="#C0C0C0">
                    <td width="10%">序号</td>
	                <td width="23%">用户名</td>
	                <td width="23%">呢称</td>
	                <td width="14%">权限</td>
	                <td width="20%">创建时间</td>
	                <td width="10%">处理</td>
                </tr>
            </table>
        </div>
    </form>
</body>
</html>
