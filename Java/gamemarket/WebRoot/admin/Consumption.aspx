<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Consumption.aspx.cs" Inherits="Web.Admin.Consumption" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
    <title>华捷国际旅游后台</title>
    <link type="text/css" href="css.css" rel="stylesheet" />
    <script type="text/javascript">
        function ActionSubmit(Action_ID, Consumption_ID)
        {
            var URL = "Consumption_Detail.aspx?Action_ID=" + Action_ID + "&Consumption_ID=" + Consumption_ID;                                
            switch (Action_ID)
            {
                case 1:
                    window.open(URL,"Consumption","toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=700px,height=620px");   
                    break;
                    
                case 2:
                    window.open(URL,"Consumption","toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=700px,height=620px");   
                    break;                    
                    
                case 3:
                    if (confirm("确实删除"))
                        window.open(URL,"Consumption","toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=700px,height=620px");                    
                        
                    break;                        
            }        
        }
        
        function ActionMember(Action_ID, Member_ID)
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
            <table align="center" border="0" width="150%" cellpadding="0" cellspacing="0">
	            <tr>
		            <td align="center" class="DivTitle1" height="30"><asp:Label ID="Consumption_Name" runat="server">消费列表</asp:Label></td>
	            </tr>
            </table>
            <table width="150%" border="0" align="center" cellpadding="0" cellspacing="0" class="Table1">
	            <tr bgcolor="#C0C0C0">
		            <td align="left">
		                <asp:TextBox ID="Search_Content" runat="server" Width="200"></asp:TextBox>
		                <asp:DropDownList ID="Search_Method" runat="server">
                            <asp:ListItem Value="0" Selected="True">==搜索形式==</asp:ListItem>
                            <asp:ListItem Value="1">定位代号</asp:ListItem>
                            <asp:ListItem Value="2">类型(1单程,2双程,3多程)</asp:ListItem>
                            <asp:ListItem Value="3">出发地</asp:ListItem>
                            <asp:ListItem Value="4">目的地</asp:ListItem>
                            <asp:ListItem Value="5">票价</asp:ListItem>
                            <asp:ListItem Value="6">低价</asp:ListItem>
                            <asp:ListItem Value="7">积分</asp:ListItem>
                            <asp:ListItem Value="8">积分</asp:ListItem>
                            <asp:ListItem Value="9">会员帐号</asp:ListItem>
                            <asp:ListItem Value="10">会员号</asp:ListItem>
                            <asp:ListItem Value="11">推荐会员帐号</asp:ListItem>
                            <asp:ListItem Value="12">推荐会员号</asp:ListItem>
                            <asp:ListItem Value="13">跟进客服帐号</asp:ListItem>
                            <asp:ListItem Value="14">跟进客服呢称</asp:ListItem>
                        </asp:DropDownList>
                        <asp:DropDownList ID="Search_Year" runat="server">
                            <asp:ListItem Value="选择年份" Selected="True"></asp:ListItem>
                        </asp:DropDownList>                 	      	            					
                        <asp:DropDownList ID="Search_Month" runat="server">
                            <asp:ListItem Value="选择月份" Selected="True"></asp:ListItem>
                            <asp:ListItem Value="1">1月</asp:ListItem>
                            <asp:ListItem Value="2">2月</asp:ListItem>
                            <asp:ListItem Value="3">3月</asp:ListItem>
                            <asp:ListItem Value="4">4月</asp:ListItem>
                            <asp:ListItem Value="5">5月</asp:ListItem>
                            <asp:ListItem Value="6">6月</asp:ListItem>
                            <asp:ListItem Value="7">7月</asp:ListItem>
                            <asp:ListItem Value="8">8月</asp:ListItem>
                            <asp:ListItem Value="9">9月</asp:ListItem>
                            <asp:ListItem Value="10">10月</asp:ListItem>
                            <asp:ListItem Value="11">11月</asp:ListItem>
                            <asp:ListItem Value="12">12月</asp:ListItem>
                        </asp:DropDownList>
                        <asp:Button ID="Search_Submit" runat="server" Text=" 搜索 " onclick="Search_Submit_Click" />
                        <asp:Button ID="Search_Refresh" runat="server" Text=" 刷新 " onclick="Search_Refresh_Click" />
	  	            </td>	  	            
	            </tr>
            </table>    
            <table id="g_MainTable" width="150%" border="1" align="center" cellpadding="0" cellspacing="0" class="Table1" runat="server">
                <tr align="center" bgcolor="#C0C0C0" style="height:30px">
                    <td>序号</td>	                
	                <td>订位代号</td>
	                <td>类型</td>
	                <td>出发地</td>
	                <td>目的地</td>
	                <td>票价</td>
	                <td>利润</td>
	                <td>日期</td>
	                <td>积分</td>
	                <td>会员帐号</td>
	                <td>会员号</td>
	                <td>推荐会员帐号</td>
	                <td>推荐会员号</td>
	                <td>跟进客服用户名</td>
	                <td>跟进客服呢称</td>
	                <td>录入时间</td>
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
