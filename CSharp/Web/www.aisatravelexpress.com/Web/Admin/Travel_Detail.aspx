<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Travel_Detail.aspx.cs" Inherits="Web.Admin.Travel_Detail" %>
<%@ Register Assembly="FredCK.FCKeditorV2" Namespace="FredCK.FCKeditorV2" TagPrefix="FCKeditorV2" %>

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
            <table width="100%" align="center" border="0" cellpadding="0" cellspacing="0">
	            <tr>
		            <td align="center" class="DivTitle1" height="30"><asp:Label ID="Travel_Title" runat="server"></asp:Label></td>
	            </tr>
            </table>
            <table id="g_MainTable" width="100%" align="left" border="0" cellpadding="0" cellspacing="0" runat="server">                		        		
                <tr bgcolor="#E3E3E3" runat="server" id="Travel_AddTime_TD">
		            <td align="right" height="30">添加时间：</td>
			        <td><asp:Label ID="Travel_AddTime" runat="server"></asp:Label></td>
		        </tr>
		        <tr bgcolor="#E3E3E3">
		            <td align="right" height="30">语言：</td>
			        <td>
                        <asp:DropDownList ID="Travel_LanguageID" runat="server"></asp:DropDownList>
                    </td>
		        </tr>		        
		        <tr bgcolor="#D0D0D0">
		            <td align="right" height="30">分类：</td>
			        <td>
                        <asp:DropDownList ID="Travel_TypeID" runat="server"></asp:DropDownList>
                    </td>
		        </tr>
		        <tr bgcolor="#E3E3E3">
		            <td align="right" height="30">线路编号：</td>
			        <td>
                        <asp:TextBox ID="Travel_Code" runat="server" MaxLength="50"></asp:TextBox>
                    </td>
		        </tr>
		        <tr bgcolor="#E3E3E3">
		            <td align="right" height="30">标题：</td>
			        <td>
                        <asp:TextBox ID="Travel_Name" runat="server" MaxLength="200" Width="360px"></asp:TextBox>
                    </td>
		        </tr>
		        <tr bgcolor="#D0D0D0">
		            <td align="right" height="30">价格：</td>
			        <td>
                        <asp:TextBox ID="Travel_Price" runat="server" MaxLength="20"></asp:TextBox>
                    </td>
		        </tr>
		        <tr bgcolor="#E3E3E3">
		            <td align="right" height="30">积分信息：</td>
			        <td>
                        <asp:TextBox ID="Travel_Points" runat="server" MaxLength="8"></asp:TextBox>
                    </td>
		        </tr>
		        <tr bgcolor="#D0D0D0">
		            <td align="right" height="30">出团开始日期：</td>
			        <td>
                        <asp:TextBox ID="Travel_StartDate" runat="server" MaxLength="20"></asp:TextBox>
                    </td>
		        </tr>
		        <tr bgcolor="#E3E3E3">
		            <td align="right" height="30">出团结束日期：</td>
			        <td>
                        <asp:TextBox ID="Travel_EndDate" runat="server" MaxLength="20"></asp:TextBox>
                    </td>
		        </tr>
		        <tr bgcolor="#D0D0D0">
		            <td align="right" height="30">主要景点：</td>
			        <td>
                        <asp:TextBox ID="Travel_Views" runat="server" TextMode="MultiLine" MaxLength="4000" Height="120px" Width="360px"></asp:TextBox>
                    </td>
		        </tr>
		        <tr bgcolor="#E3E3E3">
		            <td align="right" height="30">详细行程：</td>
			        <td>
                        <FCKeditorV2:FCKeditor ID="Travel_Route" runat="server" Height="600">
                        </FCKeditorV2:FCKeditor>
                    </td>
		        </tr>
		        <tr bgcolor="#D0D0D0">
		            <td align="right" height="30">预览图1：</td>
			        <td>
                        <asp:Image ID="Travel_PreView1_Image" runat="server" /><br />
                        <asp:FileUpload ID="Travel_PreView1" runat="server" />
                    </td>
		        </tr>		        
                <tr bgcolor="#E3E3E3">
		            <td align="right" height="30">预览图2：</td>
			        <td>
                        <asp:Image ID="Travel_PreView2_Image" runat="server" /><br />
                        <asp:FileUpload ID="Travel_PreView2" runat="server" />
                    </td>
		        </tr>
		        <tr bgcolor="#D0D0D0">
		            <td align="right" height="30">精彩瞬间：</td>
			        <td>
                        <asp:TextBox ID="Travel_PreViews_Num" runat="server" MaxLength="8"></asp:TextBox>
                        <asp:Button ID="Travel_PreViews_Buttom" runat="server" Text=" 确定 " />
                    </td>
		        </tr>
		        <tr bgcolor="#E3E3E3">
		            <td align="right" height="30">出发地：</td>
			        <td>
                        <asp:TextBox ID="Travel_StartAddr" runat="server" MaxLength="500" Width="300px"></asp:TextBox>
                    </td>
		        </tr>
		        <tr bgcolor="#D0D0D0">
		            <td align="right" height="30">结束地：</td>
			        <td>
                        <asp:TextBox ID="Travel_EndAddr" runat="server" MaxLength="500" Width="300px"></asp:TextBox>
                    </td>
		        </tr>
		        <tr bgcolor="#E3E3E3">
	  	  	        <td colspan="2" align="center" height="40">	
			            <asp:Button ID="Travel_Submit" runat="server" Text=" 提交 " onclick="Travel_Submit_Click" />
			            &nbsp;
                        <asp:Button ID="Travel_Reset" runat="server" Text=" 重置 " OnClientClick="window.location.href=window.location.href;" />
                        &nbsp;
                        <asp:Button ID="Travel_Close" runat="server" Text=" 关闭 " OnClientClick="window.close();" />							
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
