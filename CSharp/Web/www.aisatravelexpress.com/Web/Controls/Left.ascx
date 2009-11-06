<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="Left.ascx.cs" Inherits="Web.Controls.Left" %>
<%@ Register src="Login.ascx" tagname="Login" tagprefix="Controls_Login" %>
<%@ Register src="Form.ascx" tagname="Form" tagprefix="Controls_Form" %>
<%@ Register src="Service.ascx" tagname="Service" tagprefix="Controls_Service" %>
<%@ Register src="Offers.ascx" tagname="Offers" tagprefix="Controls_Offers" %>

<div class="inside_content_leftanv">
    <Controls_Login:Login ID="Web_Login" runat="server" />
    <div style="clear:both;"></div>
    <Controls_Form:Form ID="Web_Form" runat="server" />
    <div style="clear:both;"></div>
    <Controls_Service:Service ID="Web_Service" runat="server" />
    <div style="clear:both;"></div>
    <Controls_Offers:Offers ID="Web_Offers" runat="server" />		                                		  
    <div style="clear:both;"></div>        		  
</div>