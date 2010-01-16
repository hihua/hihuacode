<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Booking.aspx.cs" Inherits="Web.Admin.Booking" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
    <title>华捷国际旅游后台</title>
    <link type="text/css" href="css.css" rel="stylesheet" />
    <script type="text/javascript">
        function ActionSubmit(Action_ID, Booking_ID)
        {
            var URL = "Booking_Detail.aspx?Action_ID=" + Action_ID + "&Booking_ID=" + Booking_ID;                                
            switch (Action_ID)
            {
                case 1:
                    window.open(URL, "Booking", "toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=700px,height=620px");   
                    break;
                    
                case 2:
                    window.open(URL, "Booking", "toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=700px,height=620px");   
                    break;                    
                    
                case 3:
                    if (confirm("确实删除"))
                        window.open(URL, "Booking", "toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=700px,height=620px");                    
                        
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
		            <td align="center" class="DivTitle1" height="30"><asp:Label ID="Booking_Name" runat="server">机票预订</asp:Label></td>
	            </tr>
            </table>
            <table width="150%" border="0" align="center" cellpadding="0" cellspacing="0" class="Table1">
	            <tr bgcolor="#C0C0C0">
		            <td align="left">
		                <asp:TextBox ID="Search_Content" runat="server" Width="200"></asp:TextBox>
		                <asp:DropDownList ID="Search_Method" runat="server">
                            <asp:ListItem Value="0" Selected="True">==搜索形式==</asp:ListItem>
                            <asp:ListItem Value="1">订位代号</asp:ListItem>
                            <asp:ListItem Value="2">航空公司</asp:ListItem>
                            <asp:ListItem Value="3">联系人</asp:ListItem>
                            <asp:ListItem Value="4">电话号码</asp:ListItem>
                            <asp:ListItem Value="5">电子邮件</asp:ListItem>
                            <asp:ListItem Value="6">跟进客服帐号</asp:ListItem>
                            <asp:ListItem Value="7">跟进客服呢称</asp:ListItem>                            
                        </asp:DropDownList>
                        <asp:DropDownList ID="Search_State" runat="server">
                            <asp:ListItem Value="0" Selected="True">==订单状态==</asp:ListItem>
                            <asp:ListItem Value="1">正常(时间内)</asp:ListItem>
                            <asp:ListItem Value="2">快结束</asp:ListItem>
                            <asp:ListItem Value="3">已完成</asp:ListItem>
                            <asp:ListItem Value="4">过期(没确认)</asp:ListItem>
                        </asp:DropDownList>                 	      	            					                        
                        <asp:Button ID="Search_Submit" runat="server" Text=" 搜索 " onclick="Search_Submit_Click" />
                        <asp:Button ID="Search_Refresh" runat="server" Text=" 刷新 " onclick="Search_Refresh_Click" />
	  	            </td>
	  	            <td align="right"><asp:Button ID="Booking_Add" runat="server" Text="  添加  " /></td>	  	            
	            </tr>
            </table>
            <table id="g_MainTable" width="150%" border="1" align="center" cellpadding="0" cellspacing="0" class="Table1" runat="server">
                <tr align="center" bgcolor="#C0C0C0" style="height:30px">
                    <td>订位代号</td>	                
	                <td>航空公司</td>
	                <td>联系人</td>
	                <td>人数</td>
	                <td>电话号码</td>
	                <td>电子邮件</td>
	                <td>客服帐号</td>
	                <td>客服呢称</td>
	                <td>类别</td>
	                <td>状态</td>
	                <td>录入时间</td>
	                <td>到期时间</td>
	                <td>确认时间</td>
	                <td>处理</td>
                </tr>
            </table>
            <table width="150%" border="0" align="center" cellpadding="0" cellspacing="0">
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
