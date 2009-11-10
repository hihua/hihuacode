<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="News_Detail.aspx.cs" Inherits="Web.Admin.News_Detail" %>
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
		            <td align="center" class="DivTitle1" height="30"><asp:Label ID="News_Name" runat="server"></asp:Label></td>
	            </tr>
            </table>
            <table width="100%" align="left" border="0" cellpadding="0" cellspacing="0">                		        		
		        <tr bgcolor="#E3E3E3">
		            <td align="right" height="30">语言：</td>
			        <td>
                        <asp:DropDownList ID="News_LanguageID" runat="server"></asp:DropDownList>
                    </td>
		        </tr>
		        <tr bgcolor="#E3E3E3" runat="server" id="News_AddTime_TD">
		            <td align="right" height="30">添加时间：</td>
			        <td><asp:Label ID="News_AddTime" runat="server"></asp:Label></td>
		        </tr>
		        <tr bgcolor="#D0D0D0">
		            <td align="right" height="30">标题：</td>
			        <td>
                        <asp:TextBox ID="News_Title" runat="server" MaxLength="200" Width="780px"></asp:TextBox>
                    </td>
		        </tr>
		        <tr bgcolor="#E3E3E3">
			        <td align="right">编辑内容：</td>
			        <td>
			            <FCKeditorV2:FCKeditor ID="News_Content" runat="server" Height="600">
                        </FCKeditorV2:FCKeditor>
                    </td>
		        </tr>		
		        <tr bgcolor="#D0D0D0">
	  	  	        <td colspan="2" align="center" height="40">	
			            <asp:Button ID="News_Submit" runat="server" Text=" 提交 " onclick="News_Submit_Click" />
			            &nbsp;
                        <asp:Button ID="News_Reset" runat="server" Text=" 重置 " OnClientClick="window.location.href=window.location.href;" />
                        &nbsp;
                        <asp:Button ID="News_Close" runat="server" Text=" 关闭 " OnClientClick="window.close();" />							
			        </td>
		        </tr>
  	        </table>    
        </div>
    </form>
</body>
</html>
