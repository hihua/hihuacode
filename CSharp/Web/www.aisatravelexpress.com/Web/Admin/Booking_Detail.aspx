<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Booking_Detail.aspx.cs" Inherits="Web.Admin.Booking_Detail" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
    <title>华捷国际旅游后台</title>
    <link type="text/css" href="css.css" rel="stylesheet" />
    <link type="text/css" href="../css/aisa.css" rel="stylesheet" />
    <link type="text/css" href="../css/gray.css" rel="stylesheet" />
    <script type="text/javascript" src="../Js/jquery.js"></script>
    <script type="text/javascript" src="../Js/Ajax.js"></script>
    <script type="text/javascript" src="../Js/lhgcalendar.js"></script>
</head>
<body>
    <form id="form1" runat="server">
        <div>
            <table width="100%" align="center" border="0" cellpadding="0" cellspacing="0">
                <tr>
	                <td align="center" class="DivTitle1" height="30"><asp:Label ID="Booking_Name" Text="机票预订" runat="server"></asp:Label></td>
                </tr>
            </table>
            <table id="g_MainTable" width="100%" align="left" border="0" cellpadding="0" cellspacing="0" runat="server" visible="true">                		        		
		        <tr bgcolor="#E3E3E3">
		            <td align="right" height="30">订位代号：</td>
			        <td><asp:TextBox ID="Booking_Seq" runat="server" MaxLength="100"></asp:TextBox></td>
		        </tr>
		        <tr bgcolor="#D0D0D0">
		            <td align="right" height="30">航空公司：</td>
			        <td><asp:TextBox ID="Booking_Airline" runat="server" MaxLength="100"></asp:TextBox></td>
		        </tr>
		        <tr bgcolor="#E3E3E3">
		            <td align="right" height="30">联系人：</td>
			        <td><asp:TextBox ID="Booking_Contact" runat="server" MaxLength="30"></asp:TextBox></td>
		        </tr>
		        <tr bgcolor="#D0D0D0">
		            <td align="right" height="30">人数：</td>
			        <td><asp:TextBox ID="Booking_Num" runat="server" MaxLength="4"></asp:TextBox></td>
		        </tr>
		        <tr bgcolor="#E3E3E3">
		            <td align="right" height="30">电话号码：</td>
			        <td><asp:TextBox ID="Booking_Tel" runat="server" MaxLength="30"></asp:TextBox></td>
		        </tr>
		        <tr bgcolor="#D0D0D0">
		            <td align="right" height="30">电子邮件：</td>
			        <td><asp:TextBox ID="Booking_Email" runat="server" MaxLength="50"></asp:TextBox></td>
		        </tr>
		        <tr bgcolor="#E3E3E3" id="TD_AdminUser_ID" runat="server">
		            <td align="right" height="30">跟进客服：</td>
			        <td><asp:DropDownList ID="Booking_AdminUser_ID" runat="server"></asp:DropDownList></td>                    
		        </tr>
		        <tr bgcolor="#D0D0D0">
		            <td align="right" height="30">类别：</td>                    
			        <td>
			            <asp:RadioButton ID="Booking_Kind1" GroupName="Booking_Kind" Text="正常" Checked="true" runat="server" />
			            &nbsp;
			            <asp:RadioButton ID="Booking_Kind2" GroupName="Booking_Kind" Text="假位" runat="server" />
			        </td>
		        </tr>
		        <tr bgcolor="#E3E3E3" id="TD_Booking_State" runat="server">
		            <td align="right" height="30">状态：</td>                    
			        <td><asp:DropDownList ID="Booking_State" runat="server"><asp:ListItem Value="0" Text="未确认" Selected="True"></asp:ListItem><asp:ListItem Value="1" Text="已确认"></asp:ListItem></asp:DropDownList></td>
		        </tr>
		        <tr bgcolor="#D0D0D0" id="TD_AddTime" runat="server">
		            <td align="right" height="30">录入时间：</td>                                    
			        <td><asp:Label ID="Booking_AddTime" runat="server" Text=""></asp:Label></td>
		        </tr>
		        <tr bgcolor="#E3E3E3">
		            <td align="right" height="30">结束时间：</td>                    
			        <td>
			            <asp:TextBox ID="Booking_LastTime" runat="server" MaxLength="50"></asp:TextBox>
			            &nbsp;
			            <img src="../images/calendar.gif" alt="" onclick="lhgcalendar('Booking_LastTime',this);" />
			        </td>
		        </tr>
		        <tr bgcolor="#D0D0D0" id="TD_ComitTime" runat="server">
		            <td align="right" height="30">确认时间：</td>                                    
			        <td><asp:Label ID="Booking_ComitTime" runat="server" Text=""></asp:Label></td>
		        </tr>
		        <tr bgcolor="#E3E3E3">		            
			        <td height="30" colspan="2" align="center">
                        <asp:Button ID="Booking_Submit" runat="server" Text=" 添加 " OnClientClick="if (confirm('确实提交')) { return true; } else { return false; } " onclick="Booking_Submit_Click" />&nbsp;
                        <asp:Button ID="Booking_Reset" runat="server" Text=" 重置 " OnClientClick="window.location.href=window.location.href;return false;" />&nbsp;
                        <asp:Button ID="Booking_Close" runat="server" Text=" 关闭 " OnClientClick="window.close();return false;" />&nbsp;
                    </td>                    
		        </tr>
		    </table>
		    <table id="g_TipsTable" width="100%" align="left" border="0" cellpadding="0" cellspacing="0" runat="server" visible="false">
  	            <tr>
  	                <td height="30"><asp:Label ID="TipsMessage" runat="server" Text=""></asp:Label></td>                      
  	            </tr>
  	            <tr>
  	                <td height="30"><asp:HyperLink ID="TipsLink1" runat="server" CssClass="AdminToolsLink2"></asp:HyperLink></td>                                           
  	            </tr>
  	            <tr>
  	                <td height="30"><asp:LinkButton ID="TipsLink2" runat="server" CssClass="AdminToolsLink2" OnClientClick="window.close();">关闭</asp:LinkButton></td>                                                                 
  	            </tr>
  	        </table>
        </div>
    </form>
</body>
</html>
