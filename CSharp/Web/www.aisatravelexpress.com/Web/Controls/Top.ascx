<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="Top.ascx.cs" Inherits="Web.Controls.Top" %>

<div class="inside">
	<div class="inside_topanv">
		<a href="Index.aspx" class="inside_topanv_a nav8">首页</a>
		<a href="#" class="inside_topanv_a nav8">邮件</a>
		<asp:HyperLink ID="ChangeLanguage" NavigateUrl="../ChangeLanguage.aspx" runat="server" style="display:block; float:left;" CssClass="nav8">Engilsh</asp:HyperLink>		
	</div>
	<div style=" height:89px; width:510px; margin:17px 0 0 0; ">
		<a href="Article.aspx?Article_ClassID=1" style=" margin:4px 0 0 0;" class="inside_nav2 inside_anv_icon"></a>
		<a href="#" style=" margin:4px 0 0 23px;" class="inside_nav3 inside_anv_icon"></a>
		<a href="#" style=" margin:4px 0 0 23px;" class="inside_nav4 inside_anv_icon"></a>
		<a href="News_List.aspx?News_ClassID=1" style=" margin:4px 0 0 23px;" class="inside_nav5 inside_anv_icon"></a>
		<a href="#" style=" margin:4px 0 0 23px;" class="inside_nav6 inside_anv_icon"></a>
		<a href="Article.aspx?Article_ClassID=2" style=" margin:4px 0 0 23px;" class="inside_nav7 inside_anv_icon"></a>
	</div>	
</div>
<div class="inside_top"></div>