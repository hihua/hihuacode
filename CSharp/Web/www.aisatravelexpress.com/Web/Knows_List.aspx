<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Knows_List.aspx.cs" Inherits="Web.Knows_List" %>
<%@ Register src="Controls/Top.ascx" tagname="Top" tagprefix="Controls_Top" %>
<%@ Register src="Controls/Left.ascx" tagname="Left" tagprefix="Controls_Left" %>
<%@ Register src="Controls/Mid.ascx" tagname="Mid" tagprefix="Controls_Mid" %>
<%@ Register src="Controls/Tail.ascx" tagname="Tail" tagprefix="Controls_Tail" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <meta http-equiv="x-ua-compatible" content="ie=7" />
    <title>华捷国际旅游</title>
    <link type="text/css" href="css/aisa.css" rel="stylesheet" />
</head>
<body style="background:url(images/inside_12.jpg) repeat-x #e9faff top;">
    <form id="form1" runat="server">        
        <Controls_Top:Top ID="Web_Top" runat="server" />                     
        <div class="inside_content" style=" background-color:#ddf6fe">
	        <div class="inside_content_left"></div>
	        <div class="inside_content_center">
		        <table width="200" height="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="ddf6fe">
                    <tr>
                        <td align="left" valign="top" bgcolor="ddf6fe" height="10px">
                            <Controls_Left:Left ID="Web_Left" runat="server" />        		
		                </td>
			            <td rowspan="2" valign="top" bgcolor="#ddf6fe">
			                <div class="inside_content_rightanv" style="height:910px;">
			                    <img src="images/inside_e.jpg" alt="" />
			                    <div id="Knows_Lists" class="inside_content_rightlink" runat="server">
				                    <span class="inside_content_rightlink2"></span>
				                    <span class="inside_content_rightlink3"><asp:HyperLink ID="HyperLink_Title" runat="server" class="nav10"></asp:HyperLink>>><asp:HyperLink ID="HyperLink_Knows" runat="server" class="nav10"></asp:HyperLink></span>
				                    <span style="float:right;"><span style=" float:left; padding-top:2px;"><asp:HyperLink ID="HyperLink_Knows_ClassID_1" runat="server"></asp:HyperLink>&nbsp;<asp:HyperLink ID="HyperLink_Knows_ClassID_2" runat="server"></asp:HyperLink>&nbsp;<asp:HyperLink ID="HyperLink_Knows_ClassID_3" runat="server"></asp:HyperLink></span><img src="images/inside_8.jpg" alt="" /></span>				                    
				                </div>		
				                <div id="AirportCode" runat="server" style="margin-left:20px;">
				                    <div style="float:left; font-weight:bold">Search by Airport Code:</div>
				                    <table id="AirportCode_Table" width="100%" cellpadding="0" cellspacing="0" border="0" runat="server">				                    
				                    </table>
				                </div>
				                <br />
			                    <div id="AirportName" runat="server" style="margin-left:20px;">
			                        <div style="float:left; font-weight:bold">Search by Airport Name:</div>
			                        <table id="AirportName_Table" width="100%" cellpadding="0" cellspacing="0" border="0" runat="server">				                    
				                    </table>
			                    </div>                                			
		                    </div>
		                </td>
                    </tr>
                    <tr>
                        <td align="left" valign="bottom" bgcolor="#ddf6fe"><img src="images/inside_6.jpg" width="254" alt="" /></td>
                    </tr>
                </table>        	
	        </div>
	        <div class="inside_content_right"></div>
	        <div style="clear:both;"></div>
	        <Controls_Tail:Tail ID="Web_Tail" runat="server" />
        </div>
    </form>
</body>
</html>
