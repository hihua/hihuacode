<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Consumption_Detail.aspx.cs" Inherits="Web.Admin.Consumption_Detail" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
    <title>华捷国际旅游后台</title>
    <link type="text/css" href="css.css" rel="stylesheet" />
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
            <table width="100%" align="center" border="0" cellpadding="0" cellspacing="0">
                <tr>
	                <td align="center" class="DivTitle1" height="30"><asp:Label ID="Consumption_Name" Text="消费录入" runat="server"></asp:Label></td>
                </tr>
            </table>
            <table id="g_MainTable" width="100%" align="left" border="0" cellpadding="0" cellspacing="0" runat="server">                		        		
		        <tr bgcolor="#E3E3E3">
		            <td align="right" height="30">定位代号：</td>
			        <td>
                        <asp:TextBox ID="Consumption_Serial" runat="server" MaxLength="40"></asp:TextBox>
                        <asp:RadioButton ID="Consumption_Serial_RadioButton1" GroupName="Consumption_Serial_RadioButton" Text="SB" runat="server" Checked="true" />
                        <asp:RadioButton ID="Consumption_Serial_RadioButton2" GroupName="Consumption_Serial_RadioButton" Text="WP" runat="server" />
                    </td>
		        </tr>
		        <tr bgcolor="#D0D0D0">
		            <td align="right" height="30">类型：</td>
			        <td>
                        <asp:RadioButton ID="Consumption_Type1" GroupName="Consumption_Type" Text="单程" runat="server" Checked="true" />
                        <asp:RadioButton ID="Consumption_Type2" GroupName="Consumption_Type" Text="双程" runat="server" />
                        <asp:RadioButton ID="Consumption_Type3" GroupName="Consumption_Type" Text="多程" runat="server" />
                    </td>
		        </tr>
		        <tr bgcolor="#E3E3E3">
		            <td align="right" height="30">出发地：</td>
			        <td>
                        <asp:TextBox ID="Consumption_Src" runat="server" MaxLength="200" Width="280px"></asp:TextBox>
                    </td>
		        </tr>
		        <tr bgcolor="#D0D0D0">
		            <td align="right" height="30">目的地：</td>
			        <td>
                        <asp:TextBox ID="Consumption_Dest" runat="server" MaxLength="200" Width="280px"></asp:TextBox>
                    </td>
		        </tr>
		        <tr bgcolor="#E3E3E3">
		            <td align="right" height="30">票价：</td>
			        <td>
                        <asp:TextBox ID="Consumption_Price" runat="server" MaxLength="8"></asp:TextBox>
                    </td>
		        </tr>
		        <tr bgcolor="#D0D0D0">
		            <td align="right" height="30">低价：</td>
			        <td>
                        <asp:TextBox ID="Consumption_DePrice" runat="server" MaxLength="8"></asp:TextBox>
                    </td>
		        </tr>
		        <tr bgcolor="#E3E3E3">
		            <td align="right" height="30">积分：</td>
			        <td>
                        <asp:TextBox ID="Consumption_Points" runat="server" MaxLength="8"></asp:TextBox>
                    </td>
		        </tr>
		        <tr bgcolor="#D0D0D0">
		            <td align="right" height="30">佣金：</td>
			        <td>
                        <asp:TextBox ID="Consumption_Commission" runat="server" Text="0" MaxLength="8"></asp:TextBox>
                    </td>
		        </tr>
		        <tr bgcolor="#E3E3E3">
		            <td align="right" height="30">日期：</td>
			        <td>
                        <asp:TextBox ID="Consumption_Date" runat="server" MaxLength="30"></asp:TextBox>
                    </td>
		        </tr>
		        <tr bgcolor="#D0D0D0">
		            <td align="right" height="30">会员：</td>
			        <td>
                        <asp:DropDownList ID="Consumption_Org_Member_ID" runat="server"></asp:DropDownList>                        
                        <asp:LinkButton ID="Consumption_Org_Member_Account" runat="server"></asp:LinkButton>
                        &nbsp;
                        <asp:Label ID="Consumption_Org_Member_Serial" runat="server"></asp:Label>
                    </td>                    
		        </tr>
		        <tr id="Consumption_Com_Member_TD" bgcolor="#D0D0D0" runat="server">
		            <td align="right" height="30">推举会员：</td>
			        <td>
                        <asp:LinkButton ID="Consumption_Com_Member_Account" runat="server" CssClass="AdminToolsLink2"></asp:LinkButton>
                        &nbsp;
                        <asp:Label ID="Consumption_Com_Member_Serial" runat="server"></asp:Label>
                    </td>                    
		        </tr>
		        <tr id="Consumption_Admin_Name_TD" bgcolor="#D0D0D0" runat="server">
		            <td align="right" height="30">跟进客服用户名：</td>
			        <td>
                        <asp:Label ID="Consumption_Admin_Name" runat="server" Text="Label"></asp:Label>
                    </td>                    
		        </tr>
		        <tr id="Consumption_Admin_NickName_TD" bgcolor="#D0D0D0" runat="server">
		            <td align="right" height="30">跟进客服呢称：</td>
			        <td>
                        <asp:Label ID="Consumption_Admin_NickName" runat="server" Text="Label"></asp:Label>
                    </td>                    
		        </tr>		        
		        <tr bgcolor="#E3E3E3">
		            <td align="right" height="30">备注：</td>
			        <td>
                        <asp:TextBox ID="Consumption_Remark" runat="server" MaxLength="30" Height="100px" TextMode="MultiLine" Width="280px"></asp:TextBox>
                    </td>
		        </tr>
		        <tr id="Consumption_AddTime_TD" bgcolor="#E3E3E3" runat="server">
		            <td align="right" height="30">录入时间：</td>
			        <td>
                        <asp:Label ID="Consumption_AddTime" runat="server"></asp:Label>
                    </td>
		        </tr>
		        <tr bgcolor="#D0D0D0">		            
			        <td height="30" colspan="2" align="center">
                        <asp:Button ID="Consumption_Submit" runat="server" Text=" 添加 " OnClientClick="if (confirm('确实添加')) { return true; } else { return false; } " onclick="Consumption_Submit_Click" />&nbsp;
                        <asp:Button ID="Consumption_Reset" runat="server" Text=" 重置 " OnClientClick="window.location.href=window.location.href;return false;" />&nbsp;
                        <asp:Button ID="Consumption_Close" runat="server" Text=" 关闭 " OnClientClick="window.close();return false;" />&nbsp;
                    </td>                    
		        </tr>
		    </table>    
        </div>
    </form>
</body>
</html>
