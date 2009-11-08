<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="News.aspx.cs" Inherits="Web.Admin.News" %>

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
            <table align="center" border="0" width="100%" cellpadding="0" cellspacing="0">
	            <tr>
		            <td align="center" class="DivTitle1" height="30"><asp:Label ID="News_Name" runat="server"></asp:Label></td>
	            </tr>
            </table>
            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="Table1">
	            <tr bgcolor="#C0C0C0">
		            <td align="left">
		                <asp:TextBox ID="Search_Content" runat="server" Width="200"></asp:TextBox>
		                <asp:DropDownList ID="Search_Method" runat="server">
                            <asp:ListItem Value="0" Selected="True">==搜索形式==</asp:ListItem>
                            <asp:ListItem Value="1">按标题</asp:ListItem>
                            <asp:ListItem Value="2">按内容</asp:ListItem>
                        </asp:DropDownList>                 
	      	            <asp:DropDownList ID="News_LanguageID" runat="server">
                            <asp:ListItem Value="0" Selected="True">==语言==</asp:ListItem>
                        </asp:DropDownList>					
                        <asp:Button ID="Search_Submit" runat="server" Text=" 搜索 " />
                        <asp:Button ID="Search_Refresh" runat="server" Text=" 刷新 " />
	  	            </td>
	            </tr>
            </table>
            <table id="g_MainTable" width="100%" border="1" align="center" cellpadding="0" cellspacing="0" class="Table1" runat="server">
                <tr height="30" align="center" bgcolor="#C0C0C0">
                    <td>序号</td>
	                <td>分类</td>
	                <td>语言</td>
	                <td>标题</td>
	                <td>创建时间</td>
	                <td>处理</td>
                </tr>
            </table>
        </div>
    </form>
</body>
</html>
