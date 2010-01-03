<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="LowFare.aspx.cs" Inherits="Web.Admin.LowFare" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">    
    <title>华捷国际旅游后台</title>
    <link type="text/css" href="css.css" rel="stylesheet" />
    <script type="text/javascript" src="../Js/jquery.js"></script>
    <script type="text/javascript">
        function ActionSubmit(Action_ID, LowFare_ID)
        {
            var URL = "LowFare_Detail.aspx?Action_ID=" + Action_ID + "&LowFare_ID=" + LowFare_ID;                                
            switch (Action_ID)
            {
                case 1:
                    window.open(URL,"LowFare","toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=700px,height=620px");   
                    break;
                    
                case 2:
                    window.open(URL,"LowFare","toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=700px,height=620px");   
                    break;                    
                    
                case 3:
                    if (confirm("确实删除"))
                        window.open(URL,"LowFare","toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=700px,height=620px");                    
                        
                    break;
                    
                case 4:
                    if (confirm("确实转换"))
                        window.open(URL,"LowFare","toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=700px,height=620px");                    
                        
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
		            <td align="center" class="DivTitle1" height="30"><asp:Label ID="LowFare_Name" runat="server">机票问价</asp:Label></td>
	            </tr>
            </table>
            <table width="150%" border="0" align="center" cellpadding="0" cellspacing="0" class="Table1">
	            <tr bgcolor="#C0C0C0">
		            <td align="left">
		                <asp:TextBox ID="Search_Content" runat="server" Width="200"></asp:TextBox>
		                <asp:DropDownList ID="Search_Method" runat="server">
                            <asp:ListItem Value="0" Selected="True">==搜索形式==</asp:ListItem>
                            <asp:ListItem Value="1">类别(1双程，2单程，3多程)</asp:ListItem>
                            <asp:ListItem Value="2">LowFare_Adults</asp:ListItem>
                            <asp:ListItem Value="3">LowFare_Children</asp:ListItem>
                            <asp:ListItem Value="4">LowFare_Infants</asp:ListItem>
                            <asp:ListItem Value="5">LowFare_Airline</asp:ListItem>
                            <asp:ListItem Value="6">LowFare_Class</asp:ListItem>                            
                            <asp:ListItem Value="7">会员用户名</asp:ListItem>
                            <asp:ListItem Value="8">会员号</asp:ListItem>
                            <asp:ListItem Value="9">跟进客服用户名</asp:ListItem>
                            <asp:ListItem Value="10">跟进客服呢称</asp:ListItem>                       
                        </asp:DropDownList>
                        <asp:DropDownList ID="LowFare_Status" runat="server">
                            <asp:ListItem Value="0" Selected="True">==是否处理==</asp:ListItem>
                            <asp:ListItem Value="1">否</asp:ListItem>
                            <asp:ListItem Value="2">是</asp:ListItem>
                        </asp:DropDownList>                                         	      	            				
                        <asp:Button ID="Search_Submit" runat="server" Text=" 搜索 " onclick="Search_Submit_Click" />
                        <asp:Button ID="Search_Refresh" runat="server" Text=" 刷新 " onclick="Search_Refresh_Click" />
	  	            </td>	  	            
	            </tr>
            </table>            
            <table id="g_MainTable" width="150%" border="1" align="center" cellpadding="0" cellspacing="0" class="Table1" runat="server">
                <tr align="center" bgcolor="#C0C0C0" style="height:30px">
                    <td>序号</td>
                    <td>状态</td>	                
	                <td>类别</td>	               
	                <td>Adults</td>
	                <td>Children</td>
	                <td>Infants</td>
	                <td>Airline</td>
	                <td>Class</td>
	                <td>会员用户名</td>
	                <td>会员号</td>
	                <td>客服用户名</td>
	                <td>客服呢称</td>
	                <td>添加时间</td>
	                <td>处理时间</td>
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
