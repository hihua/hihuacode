<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="FCKeditor.aspx.cs" Inherits="Web.Admin.FCKeditor" %>

<%@ Register Assembly="FredCK.FCKeditorV2" Namespace="FredCK.FCKeditorV2" TagPrefix="FCKeditorV2" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
    <title>无标题页</title>
</head>
<body>
    <form id="form1" runat="server">        
        <div>
            <p id="PostedAlertBlock" style="color: Red" runat="server" visible="false"></p>
            <FCKeditorV2:FCKeditor ID="FCKeditor1" runat="server" Height="480">
            </FCKeditorV2:FCKeditor>
            <div id="PostedDataBlock" runat="server" visible="false">
		        <p>
			        Posted data:
		        </p>
		        <pre><asp:Label ID="LblPostedData" runat="server"></asp:Label></pre>
	        </div>
            <asp:Button ID="Button1" runat="server" onclick="Button1_Click" Text="Button" />
        </div>        
    </form>
</body>
</html>
