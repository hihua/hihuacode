<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Article.aspx.cs" Inherits="Web.Admin.Article" %>
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
		            <td align="center" class="DivTitle1" height="30"><%GetArticleTXT();%></td>
	            </tr>
            </table>
            <table width="100%" align="left" border="0" cellpadding="0" cellspacing="0">		        		
		        <tr bgcolor="#E3E3E3">
		            <td align="right" height="30">语言：</td>
			        <td>
                        <asp:DropDownList ID="Article_LanguageID" runat="server" AutoPostBack="true" onselectedindexchanged="Article_LanguageID_SelectedIndexChanged">
                        </asp:DropDownList>
                    </td>
		        </tr>
		        <tr bgcolor="#D0D0D0">
			        <td align="right">编辑内容：</td>
			        <td>
			            <FCKeditorV2:FCKeditor ID="Article_Content" runat="server" Height="600">
                        </FCKeditorV2:FCKeditor>
                    </td>
		        </tr>		
		        <tr bgcolor="#E3E3E3">
	  	  	        <td colspan="2" align="center" height="40">	
			            <asp:Button ID="Article_Submit" runat="server" Text=" 提交 " onclick="Article_Submit_Click" />
			            &nbsp;
                        <asp:Button ID="Article_Reset" runat="server" Text=" 重置 " OnClientClick="window.location.href=window.location.href;" />
                        &nbsp;
                        <asp:Button ID="Article_Close" runat="server" Text=" 关闭 " OnClientClick="window.close();" />							
			        </td>
		        </tr>
  	        </table>
        </div>
    </form>
</body>
</html>
