﻿<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Travel.aspx.cs" Inherits="Web.Admin.Travel" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
    <title>华捷国际旅游后台</title>
    <link type="text/css" href="css.css" rel="stylesheet" />
    <script type="text/javascript" src="../Js/jquery.js"></script>
    <script type="text/javascript">
        function ActionSubmit(Action_ID, Travel_ID)
        {
            var URL = "Travel_Detail.aspx?Action_ID=" + Action_ID + "&Travel_ID=" + Travel_ID;                                
            switch (Action_ID)
            {
                case 1:
                    window.open(URL,"Travel","toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=1024px,height=700px");   
                    break;
                    
                case 2:
                    window.open(URL,"Travel","toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=1024px,height=700px");   
                    break;                    
                    
                case 3:
                    if (confirm("确实删除"))
                        window.open(URL,"Travel","toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=1024px,height=700px");                    
                        
                    break;            
            }        
        }
    </script>
</head>
<body>
    <form id="form1" runat="server">
        <div>
            <table align="center" border="0" width="150%" cellpadding="0" cellspacing="0">
	            <tr>
		            <td align="center" class="DivTitle1" height="30"><asp:Label ID="Travel_Title" runat="server"></asp:Label></td>
	            </tr>
            </table>
            <table width="150%" border="0" align="center" cellpadding="0" cellspacing="0" class="Table1">
	            <tr bgcolor="#C0C0C0">
		            <td align="left">
		                <asp:TextBox ID="Search_Content" runat="server" Width="200"></asp:TextBox>
		                <asp:DropDownList ID="Search_Method" runat="server">
                            <asp:ListItem Value="0" Selected="True">==搜索形式==</asp:ListItem>
                            <asp:ListItem Value="1">按标题</asp:ListItem>
                            <asp:ListItem Value="2">按内容</asp:ListItem>
                        </asp:DropDownList>                 
	      	            <asp:DropDownList ID="Travel_LanguageID" runat="server">
                            <asp:ListItem Value="0" Selected="True">==语言==</asp:ListItem>
                        </asp:DropDownList>
                        <asp:DropDownList ID="Travel_TypeID" runat="server">
                            <asp:ListItem Value="0" Selected="True">==分类==</asp:ListItem>
                            <asp:ListItem Value="1">国外旅游</asp:ListItem>
                            <asp:ListItem Value="2">中国旅游</asp:ListItem>
                        </asp:DropDownList>					
                        <asp:Button ID="Search_Submit" runat="server" Text=" 搜索 " />
                        <asp:Button ID="Search_Refresh" runat="server" Text=" 刷新 " />
	  	            </td>
	  	            <td align="right"><asp:Button ID="Travel_Add" runat="server" Text="  添加  " /></td>                    
	            </tr>
            </table>            
            <table id="g_MainTable" width="150%" border="1" align="center" cellpadding="0" cellspacing="0" class="Table1" runat="server">
                <tr align="center" bgcolor="#C0C0C0" style="height:30px">
                    <td>序号</td>	                
	                <td>语言</td>
	                <td>分类</td>
	                <td>名称</td>
	                <td>线路编号</td>
	                <td>价格</td>
	                <td>出发地</td>
	                <td>结束地</td>
	                <td>积分信息</td>
	                <td>出团日期</td>
                </tr>
            </table>
            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td align="left">
                        <asp:LinkButton ID="Previous_Page" runat="server" Visible="false" CssClass="AdminToolsLink2">上一页</asp:LinkButton>                        
                        <asp:LinkButton ID="Next_Page" runat="server" Visible="false" CssClass="AdminToolsLink2">下一页</asp:LinkButton>
                        &nbsp;
                        <asp:Label ID="Current_Page" runat="server" Text=""></asp:Label>
                        <asp:Label ID="Splite_Page" runat="server" Text="/"></asp:Label>
                        <asp:Label ID="Total_Page" runat="server" Text=""></asp:Label>
                    </td>
                    <td align="right">总数：<asp:Label ID="Total_Count" runat="server" Text="0"></asp:Label></td>
                </tr>
            </table>
        </div>
    </form>
</body>
</html>
