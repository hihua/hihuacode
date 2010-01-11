<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Member_Reg.aspx.cs" Inherits="Web.Member_Reg" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
    <meta http-equiv="x-ua-compatible" content="ie=7" />
    <title>华捷国际旅游</title>
    <link type="text/css" href="css/aisa.css" rel="stylesheet" />
    <link type="text/css" href="css/window.css" rel="stylesheet" />
    <script type="text/javascript">
        function AirlineList() 
        {
            window.open('AirlineList.aspx', 'AirlineList', 'toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=300px,height=360px');
        }
    </script>
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
			            <dd><asp:TextBox ID="Member_Account" runat="server" ToolTip="输入帐号" Width="155" MaxLength="30"></asp:TextBox><span style="color:Red">*</span></dd>
		            </dl>
		            <dl>
			            <dt>介&nbsp;绍&nbsp;码：</dt>
			            <dd><asp:TextBox ID="Member_Serial" runat="server" ToolTip="介绍码" Width="155" MaxLength="50"></asp:TextBox></dd>
		            </dl>
		            <dl>
			            <dt>密&nbsp;&nbsp;&nbsp;&nbsp;码：</dt>
			            <dd><asp:TextBox ID="Member_PassWord1" runat="server" ToolTip="密码" Width="155" MaxLength="20" TextMode="Password"></asp:TextBox><span style="color:Red">*</span></dd>
		            </dl>
		            <dl>
			            <dt>确认密码：</dt>
			            <dd><asp:TextBox ID="Member_PassWord2" runat="server" ToolTip="确认密码" Width="155" MaxLength="20" TextMode="Password"></asp:TextBox><span style="color:Red">*</span></dd>
		            </dl>
		            <dl>
			            <dt>中&nbsp;文&nbsp;名：</dt>
			            <dd><asp:TextBox ID="Member_Name_CN" runat="server" ToolTip="中文名" Width="155" MaxLength="50"></asp:TextBox><span style="color:Red">*</span></dd>
		            </dl>
		            <dl>
			            <dt>英&nbsp;文&nbsp;名：</dt>
			            <dd><asp:TextBox ID="Member_Name_EN" runat="server" ToolTip="英文名" Width="155" MaxLength="50"></asp:TextBox><span style="color:Red">*</span></dd>
		            </dl>
		            <dl>
			            <dt>性&nbsp;&nbsp;&nbsp;&nbsp;别：</dt>
			            <dd style="text-indent:1em; width:159px;">			                                            
                            <asp:RadioButton ID="Member_Male" GroupName="Member_Sex" runat="server" Text="男" Checked="true" />
                            <asp:RadioButton ID="Member_Female" GroupName="Member_Sex" runat="server" Text="女" />
			            </dd>
		            </dl>
			        <dl>
			            <dt>&nbsp;工作状态：</dt>
			            <dd>
			                <asp:DropDownList ID="Member_Work" runat="server" ToolTip="工作状态">
			                    <asp:ListItem Value="学生" Selected="True">学生</asp:ListItem>
			                    <asp:ListItem Value="待业">待业</asp:ListItem>
			                    <asp:ListItem Value="在职">在职</asp:ListItem>
			                    <asp:ListItem Value="退休">退休</asp:ListItem>
			                </asp:DropDownList>
			            </dd>                        
		            </dl>
		            <div style="clear:both;"></div>		            
		            <dl>
			            <dt>电&nbsp;&nbsp;&nbsp;&nbsp;话：</dt>
			            <dd><asp:TextBox ID="Member_Tel" runat="server" ToolTip="电话" Width="155" MaxLength="30"></asp:TextBox><span style="color:Red">*</span></dd>
		            </dl>
		            <dl>
			            <dt>手机号码：</dt>
			            <dd><asp:TextBox ID="Member_Mobile" runat="server" ToolTip="手机号码" Width="155" MaxLength="30"></asp:TextBox><span style="color:Red">*</span></dd>
		            </dl>
		            <dl>
			            <dt>电子邮件：</dt>
			            <dd><asp:TextBox ID="Member_Email" runat="server" ToolTip="电子邮件" Width="422" MaxLength="100"></asp:TextBox><span style="color:Red">*</span></dd>
		            </dl>
		            <dl>
			            <dt>居住地址：</dt>
			            <dd><asp:TextBox ID="Member_Address" runat="server" ToolTip="居住地址" Width="422" MaxLength="200"></asp:TextBox><span style="color:Red">*</span></dd>
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
			            <dd>
			                &nbsp;<asp:TextBox ID="Member_Airlines" Width="180" runat="server" MaxLength="1024" ReadOnly="true" Text="Search All Airlines"></asp:TextBox><img src="images/button_list.gif" alt="" onclick="AirlineList();" style="cursor:pointer" />                                                      
                            <input type="checkbox" name="checkbox1" id="checkbox1" value="Air Canada" style="display:none" />
                            <input type="checkbox" name="checkbox2" id="checkbox2" value="Air China" style="display:none" />
                            <input type="checkbox" name="checkbox3" id="checkbox3" value="Air France" style="display:none" />
                            <input type="checkbox" name="checkbox4" id="checkbox4" value="Alaska Airlines" style="display:none" />
                            <input type="checkbox" name="checkbox5" id="checkbox5" value="All Nippon Airways" style="display:none" />
                            <input type="checkbox" name="checkbox6" id="checkbox6" value="America West Airlines" style="display:none" />
                            <input type="checkbox" name="checkbox7" id="checkbox7" value="American Airlines" style="display:none" />
                            <input type="checkbox" name="checkbox8" id="checkbox8" value="Asiana" style="display:none" />
                            <input type="checkbox" name="checkbox9" id="checkbox9" value="British Airways" style="display:none" />
                            <input type="checkbox" name="checkbox10" id="checkbox10" value="Cathay Pacific" style="display:none" />
                            <input type="checkbox" name="checkbox11" id="checkbox11" value="China Airlines" style="display:none" />
                            <input type="checkbox" name="checkbox12" id="checkbox12" value="China Eastern Airlines" style="display:none" />
                            <input type="checkbox" name="checkbox13" id="checkbox13" value="China Southern Airlines" style="display:none" />
                            <input type="checkbox" name="checkbox14" id="checkbox14" value="Continental" style="display:none" />
                            <input type="checkbox" name="checkbox15" id="checkbox15" value="Delta Airlines" style="display:none" />
                            <input type="checkbox" name="checkbox16" id="checkbox16" value="Dragon Air" style="display:none" />
                            <input type="checkbox" name="checkbox17" id="checkbox17" value="Frontier Airlines" style="display:none" />
                            <input type="checkbox" name="checkbox18" id="checkbox18" value="Hainan Airlines" style="display:none" />
                            <input type="checkbox" name="checkbox19" id="checkbox19" value="Japan Airlines" style="display:none" />
                            <input type="checkbox" name="checkbox20" id="checkbox20" value="KLM" style="display:none" />
                            <input type="checkbox" name="checkbox21" id="checkbox21" value="Korean Air" style="display:none" />
                            <input type="checkbox" name="checkbox22" id="checkbox22" value="Lufthansa" style="display:none" />
                            <input type="checkbox" name="checkbox23" id="checkbox23" value="Malysian Airlines" style="display:none" />
                            <input type="checkbox" name="checkbox24" id="checkbox24" value="Northwest Airlines" style="display:none" />
                            <input type="checkbox" name="checkbox25" id="checkbox25" value="Philippine Airlines" style="display:none" />
                            <input type="checkbox" name="checkbox26" id="checkbox26" value="SAS" style="display:none" />
                            <input type="checkbox" name="checkbox27" id="checkbox27" value="Shanghai Airlines" style="display:none" />
                            <input type="checkbox" name="checkbox28" id="checkbox28" value="Singapore Airlines" style="display:none" />
                            <input type="checkbox" name="checkbox29" id="checkbox29" value="Spirit Airlines" style="display:none" />
                            <input type="checkbox" name="checkbox30" id="checkbox30" value="Thai Airways Intl" style="display:none" />
                            <input type="checkbox" name="checkbox31" id="checkbox31" value="United Airlines" style="display:none" />
                            <input type="checkbox" name="checkbox32" id="checkbox32" value="US Airways" style="display:none" />
                            <input type="checkbox" name="checkbox33" id="checkbox33" value="Vietnam Airlines" style="display:none" />
                            <input type="checkbox" name="checkbox34" id="checkbox34" value="Virgin Atlantic" style="display:none" />
			            </dd>
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
		            <div style="clear:both; height:20px;"></div>            
		            <dl><span>如有介绍码,请正确填写,会享受优惠折扣;带*号的是必填项,请如实填写,谢谢</span></dl>	                       
		        </div>      		        
	        </div>	
	        <div class="win_pic_footer"></div>	
	        <div class="win_footer"></div>
	    </div>
    </form>
</body>
</html>
