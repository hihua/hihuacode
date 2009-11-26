<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="Login.ascx.cs" Inherits="Web.Controls.Login" %>

<div id="Member_Reg" class="inside_content_column inside_content_button1" runat="server">
    <span class="inside_content_login">帐号：密码：</span>
    <div class="inside_content_input">
    	<span><asp:TextBox ID="Member_Account_Name" runat="server" CssClass="input" MaxLength="30"></asp:TextBox></span>
    	<span><asp:TextBox ID="Member_Account_PassWord" runat="server" CssClass="input" MaxLength="20" TextMode="Password"></asp:TextBox></span>
    </div>
	<asp:LinkButton ID="Member_Account_Submit" runat="server" ToolTip="登录" CssClass="inside_content_inputgo" onclick="Member_Account_Submit_Click"></asp:LinkButton>
	<span style=" float:left; width:200px; text-align:center; line-height:20px; margin-top:10px;">
		<a href="#" class="input3" onclick="window.open('Member_Reg.aspx','Member','toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=630px,height=660px');return false;">立即注册</a>
		<a href="#" class="input3">忘记密码</a>
	</span>
	<span style="float:left; width:200px; text-align:center; margin-top:5px;">
		<a href="Article.aspx?Article_ClassID=3" class="input3">成为会员能享受什么好处？</a>
    </span>			
</div>
<div id="Member_Record" class="inside_content_column inside_content_button1" runat="server">
    <p class="inside2_login"><span id="Member_Name_CN" runat="server"></span>!欢迎你的登录</p>
    <p class="inside2_login">你的账号是：<span id="Member_Account" runat="server"></span></p>
    <p class="inside2_login">你的积分是：<span id="Member_Points" runat="server"></span></p>
    <p class="inside2_login">会员等级为：<span id="Member_Level" runat="server"></span></p>
    <p class="inside2_login" style="font-size:13px; letter-spacing:1px;"><asp:LinkButton ID="Member_Info_Detail" runat="server" CssClass="input3" OnClientClick="window.open('Member_Info.aspx?Member_Info_ID=1','Member','toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=630px,height=660px');return false;">修改会员信息</asp:LinkButton>|<asp:LinkButton ID="Member_Info_PassWord" runat="server" CssClass="input3" OnClientClick="window.open('Member_Info.aspx?Member_Info_ID=2','Member','toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=630px,height=660px');return false;">修改密码</asp:LinkButton></p>
    <p class="inside2_login" style="font-size:13px; letter-spacing:1px;"><asp:LinkButton ID="Member_Info_Consumption" runat="server" CssClass="input3" OnClientClick="window.open('Member_Info.aspx?Member_Info_ID=3','Member','toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=630px,height=660px');return false;">查询消费纪录</asp:LinkButton>|<asp:HyperLink ID="Member_Quit" NavigateUrl="../Member_Quit.aspx" runat="server" CssClass="input3">退出登陆</asp:HyperLink></p>					
</div>
