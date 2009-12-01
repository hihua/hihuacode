<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Member_Reg.aspx.cs" Inherits="Web.Member_Reg" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
    <meta http-equiv="x-ua-compatible" content="ie=7" />
    <title>华捷国际旅游</title>
    <link type="text/css" href="css/aisa.css" rel="stylesheet" />
    <link type="text/css" href="css/window.css" rel="stylesheet" />
</head>
<body style="background-color:#EEFBFF;">
    <form id="form1" runat="server">
        <div id="window">
            <div class="win_top">
		        <div class="win_logo"></div>
		        <div class="win_pic">
			        <div class="win_picimg"><a href="#" onclick="window.close();return false;"><img src="images/window_3.jpg" alt="" /></a></div>			        
                </div>
	        </div>
	        <div id="foda1">
	            <div class="win_pic_txt">
		            <dl>
			            <dt>帐&nbsp;&nbsp;&nbsp;&nbsp;号：</dt>
			            <dd><asp:TextBox ID="Member_Account" runat="server" ToolTip="输入帐号" Width="155" MaxLength="30"></asp:TextBox></dd>
		            </dl>
		            <dl>
			            <dt>介&nbsp;绍&nbsp;码：</dt>
			            <dd><asp:TextBox ID="Member_Serial" runat="server" ToolTip="介绍码" Width="155" MaxLength="50"></asp:TextBox></dd>
		            </dl>
		            <dl>
			            <dt>密&nbsp;&nbsp;&nbsp;&nbsp;码：</dt>
			            <dd><asp:TextBox ID="Member_PassWord1" runat="server" ToolTip="密码" Width="155" MaxLength="20" TextMode="Password"></asp:TextBox></dd>
		            </dl>
		            <dl>
			            <dt>确认密码：</dt>
			            <dd><asp:TextBox ID="Member_PassWord2" runat="server" ToolTip="确认密码" Width="155" MaxLength="20" TextMode="Password"></asp:TextBox></dd>
		            </dl>
		            <dl>
			            <dt>中&nbsp;文&nbsp;名：</dt>
			            <dd><asp:TextBox ID="Member_Name_CN" runat="server" ToolTip="中文名" Width="155" MaxLength="50"></asp:TextBox></dd>
		            </dl>
		            <dl>
			            <dt>英&nbsp;文&nbsp;名：</dt>
			            <dd><asp:TextBox ID="Member_Name_EN" runat="server" ToolTip="英文名" Width="155" MaxLength="50"></asp:TextBox></dd>
		            </dl>
		            <dl>
			            <dt>性&nbsp;&nbsp;&nbsp;&nbsp;别：</dt>
			            <dd style="text-indent:1em; width:159px;">			                                            
                            <asp:RadioButton ID="Member_Male" GroupName="Member_Sex" runat="server" Text="男" Checked="true" />
                            <asp:RadioButton ID="Member_Female" GroupName="Member_Sex" runat="server" Text="女" />
			            </dd>
		            </dl>
			        <dl>
			            <dt>工作类型：</dt>
			            <dd><asp:TextBox ID="Member_Work" runat="server" ToolTip="工作类型" Width="155" MaxLength="50"></asp:TextBox></dd>
		            </dl>
		            <dl>
			            <dt>电&nbsp;&nbsp;&nbsp;&nbsp;话：</dt>
			            <dd><asp:TextBox ID="Member_Tel" runat="server" ToolTip="电话" Width="155" MaxLength="30"></asp:TextBox></dd>
		            </dl>
		            <dl>
			            <dt>手机号码：</dt>
			            <dd><asp:TextBox ID="Member_Mobile" runat="server" ToolTip="手机号码" Width="155" MaxLength="30"></asp:TextBox></dd>
		            </dl>
		            <dl>
			            <dt>电子邮件：</dt>
			            <dd><asp:TextBox ID="Member_Email" runat="server" ToolTip="电子邮件" Width="422" MaxLength="100"></asp:TextBox></dd>
		            </dl>
		            <dl>
			            <dt>居住地址：</dt>
			            <dd><asp:TextBox ID="Member_Address" runat="server" ToolTip="居住地址" Width="422" MaxLength="200"></asp:TextBox></dd>
		            </dl>
		            <dl>
			            <dt>公司名称：</dt>
			            <dd><asp:TextBox ID="Member_Company_Name" runat="server" ToolTip="公司名称" Width="155" MaxLength="100"></asp:TextBox></dd>
		            </dl>
			            <dl>
			            <dt>公司电话：</dt>
			            <dd><asp:TextBox ID="Member_Company_Tel" runat="server" ToolTip="公司电话" Width="155" MaxLength="30"></asp:TextBox></dd>
		            </dl>
		            <dl>
			            <dt>公司地址：</dt>
			            <dd><asp:TextBox ID="Member_Company_Address" runat="server" ToolTip="公司地址" Width="422" MaxLength="200"></asp:TextBox></dd>
		            </dl>
		            <dl>
			            <dt>预计出行月份：（多选）1-12月：</dt>
			            <dd style="width:490px; line-height:30px; letter-spacing:1px; padding-left:25px; text-indent:0px;">			                
			                <asp:CheckBox ID="Member_Months_1" runat="server" Text="1月" />
			                <asp:CheckBox ID="Member_Months_2" runat="server" Text="2月" />
			                <asp:CheckBox ID="Member_Months_3" runat="server" Text="3月" />
			                <asp:CheckBox ID="Member_Months_4" runat="server" Text="4月" />
			                &nbsp;<asp:CheckBox ID="Member_Months_5" runat="server" Text="5月" />
			                &nbsp;<asp:CheckBox ID="Member_Months_6" runat="server" Text="6月" /><br />
			                <asp:CheckBox ID="Member_Months_7" runat="server" Text="7月" />
			                <asp:CheckBox ID="Member_Months_8" runat="server" Text="8月" />
			                <asp:CheckBox ID="Member_Months_9" runat="server" Text="9月" />
			                <asp:CheckBox ID="Member_Months_10" runat="server" Text="10月" />
			                <asp:CheckBox ID="Member_Months_11" runat="server" Text="11月" />
			                <asp:CheckBox ID="Member_Months_12" runat="server" Text="12月" />			                
			            </dd>
		            </dl>
		            <dl>
			            <dt>常用航空公司：</dt>
			            <dd><asp:TextBox ID="Member_Airlines" runat="server" ToolTip="常用航空公司" Width="350" MaxLength="100"></asp:TextBox></dd>
		            </dl>		   
		            <dl style="padding-left:200px; padding-top:10px;">
			            <dt>			                
                            <asp:Button ID="Member_Reset" runat="server" Text="重新填写" CssClass="win_input" OnClientClick="window.location.href=window.location.href;" />
                            &nbsp;
			            </dt>
			            <dd style="padding-top:15px;">
			                &nbsp;
			                <asp:Button ID="Member_Submit" runat="server" Text="确定注册" CssClass="win_input" OnClick="Member_Submit_Click" />
			            </dd>
		            </dl>         
		        </div>         
	        </div>	
	        <div class="win_pic_footer"></div>	
	        <div class="win_footer"></div>
	    </div>
    </form>
</body>
</html>
