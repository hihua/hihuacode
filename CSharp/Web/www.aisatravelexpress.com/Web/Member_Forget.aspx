﻿<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Member_Forget.aspx.cs" Inherits="Web.Member_Forget" %>
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
			                    <asp:Image ID="Banner" runat="server" />
			                    <div class="inside_content_rightlink">
				                    <span class="inside_content_rightlink2"></span>
				                    <span class="inside_content_rightlink3"><asp:HyperLink ID="HyperLink_Title" runat="server" class="nav10"></asp:HyperLink>>><asp:HyperLink ID="HyperLink_Member_Forget" runat="server" class="nav10"></asp:HyperLink></span>
				                    <span class="inside_content_rightlink4"></span>
				                </div>
			                    <div class="inside2" runat="server" style="text-align:center">
			                        E-Mail: 
                                    <asp:TextBox ID="Member_Email" runat="server" Width="200"></asp:TextBox>
                                    &nbsp;
                                    <asp:Button ID="Member_Submit" runat="server" Text=" 确定 " onclick="Member_Submit_Click1" />
			                    </div>                 
			                    <div style="clear:both; height:30px"></div>  			
			                    <div style="text-align:center">会把密码发到注册时所填的邮箱, 如注册时填写的邮箱不正确,请联系在线客服人员</div>
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
