<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Member.aspx.cs" Inherits="Web.Admin.Member" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
    <title>华捷国际旅游后台</title>
    <link type="text/css" href="css.css" rel="stylesheet" />
    <script type="text/javascript" src="../Js/jquery.js"></script>
    <script type="text/javascript">
        function ActionSubmit(Action_ID, Member_ID)
        {
            var URL = "Member_Detail.aspx?Action_ID=" + Action_ID + "&Member_ID=" + Member_ID;                                
            switch (Action_ID)
            {
                case 1:
                    window.open(URL,"Member","toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=630px,height=660px");   
                    break;
                    
                case 2:
                    window.open(URL,"Member","toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=630px,height=660px");   
                    break;                    
                    
                case 3:
                    if (confirm("确实删除"))
                        window.open(URL,"Member","toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=630px,height=660px");                    
                    
                    break;
                        
                case 4:
                    if (confirm("确实转换"))
                        window.open(URL,"Member","toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=630px,height=660px");                    
                
                    break;            
            }        
        }
    </script>
</head>
<body>
    <form id="form1" runat="server">
        <div>
            <table align="center" border="0" width="120%" cellpadding="0" cellspacing="0">
	            <tr>
		            <td align="center" class="DivTitle1" height="30">会员管理</td>
	            </tr>
            </table>
            <table width="120%" border="0" align="center" cellpadding="0" cellspacing="0" class="Table1">
	            <tr bgcolor="#C0C0C0">
		            <td align="left">
		                <asp:TextBox ID="Search_Content" runat="server" Width="200"></asp:TextBox>
		                <asp:DropDownList ID="Search_Method" runat="server">
                            <asp:ListItem Value="0" Selected="True">==搜索形式==</asp:ListItem>
                            <asp:ListItem Value="1">帐号</asp:ListItem>
                            <asp:ListItem Value="2">中文名</asp:ListItem>
                            <asp:ListItem Value="3">英文名</asp:ListItem>
                            <asp:ListItem Value="4">工作类型</asp:ListItem>
                            <asp:ListItem Value="5">电话</asp:ListItem>
                            <asp:ListItem Value="6">手机号码</asp:ListItem>
                            <asp:ListItem Value="7">电子邮件</asp:ListItem>
                            <asp:ListItem Value="8">居住地址</asp:ListItem>
                            <asp:ListItem Value="9">常用航空公司</asp:ListItem>
                            <asp:ListItem Value="10">会员号</asp:ListItem>
                            <asp:ListItem Value="11">会员级别</asp:ListItem>
                            <asp:ListItem Value="12">介绍码</asp:ListItem>
                        </asp:DropDownList>	            					
                        <asp:Button ID="Search_Submit" runat="server" Text=" 搜索 " onclick="Search_Submit_Click" />
                        <asp:Button ID="Search_Refresh" runat="server" Text=" 刷新 " onclick="Search_Refresh_Click" />
	  	            </td>	  	                                
	            </tr>
            </table>
            <table id="g_MainTable" width="120%" border="1" align="center" cellpadding="0" cellspacing="0" class="Table1" runat="server">
                <tr align="center" bgcolor="#C0C0C0" style="height:30px">
                    <td>序号</td>	                
	                <td>会员号</td>
	                <td>性别</td>
	                <td>帐号</td>
	                <td>会员级别</td>
	                <td>中文名</td>
	                <td>英文名</td>	                
	                <td>电话</td>
	                <td>手机号码</td>
	                <td>电子邮件</td>
	                <td>注册时间</td>
	                <td>处理</td>
                </tr>
            </table>
            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td align="left">
                        <asp:LinkButton ID="Previous_Page" runat="server" Visible="false" CssClass="AdminToolsLink2" onclick="Previous_Page_Click">上一页</asp:LinkButton>                        
                        <asp:LinkButton ID="Next_Page" runat="server" Visible="false" CssClass="AdminToolsLink2" onclick="Next_Page_Click">下一页</asp:LinkButton>
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
