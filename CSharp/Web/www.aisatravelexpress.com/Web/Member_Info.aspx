<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Member_Info.aspx.cs" Inherits="Web.Member_Info" %>

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
        
		function ShowDiv(Obj, Pos)
		{
		    var li = Obj.parentNode.getElementsByTagName("li");
		    if (li == null)
		        return li;
		        
		    for (var i = 0;i < li.length;i++)
		    {
		        if (li != null)
		        {
			        if (li[i] == Obj)			        
				        li[i].className = "win_pic_li1";
				    else
				        li[i].className = "win_pic_li2";				        			        
			    }
		    }
		
		    for (var i = 1;i <= 3;i++)
		    {
		        var div = document.getElementById("Div" + i);
		        if (div != null)
		        {
		            if (i == Pos)		            
		                div.className = "win_pic_txt";		            
		            else
		                div.className = "win_pic_txt2";
		        }
		    }		
		}
		
		function ClearPassWord()
		{
		    var Text;
		    Text = document.getElementById("Member_PassWord");
		    if (Text != null)
		        Text.value = "";
		        
		    Text = document.getElementById("Member_PassWord1");
		    if (Text != null)
		        Text.value = "";
		        
		    Text = document.getElementById("Member_PassWord2");
		    if (Text != null)
		        Text.value = "";		
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
			        <ul>
				        <li id="Li1" class="win_pic_li1" style="height:22px; margin-bottom:-1px;" onmouseover="ShowDiv(this,1)" runat="server">会员信息</li>
				        <li id="Li2" class="win_pic_li2" style="height:22px; margin-bottom:-1px;" onmouseover="ShowDiv(this,2)" runat="server">修改密码</li>
				        <li id="Li3" class="win_pic_li2" style="height:22px; margin-bottom:-1px;" onmouseover="ShowDiv(this,3)" runat="server">查询消费记录</li>
			        </ul>
                </div>
	        </div>
	        <div id="foda1">
	            <div id="Div1" class="win_pic_txt" runat="server">
		            <dl>
			            <dt>帐&nbsp;&nbsp;&nbsp;&nbsp;号：</dt>
			            <dd><asp:TextBox ID="Member_Account" runat="server" ToolTip="输入帐号" Width="155" MaxLength="30"></asp:TextBox><span style="color:Red">*</span></dd>
		            </dl>
		            <dl>
			            <dt>介&nbsp;绍&nbsp;码：</dt>
			            <dd><asp:TextBox ID="Member_Serial" runat="server" ToolTip="介绍码" Width="155" MaxLength="50"></asp:TextBox></dd>
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
                            <input type="checkbox" name="checkbox1" id="checkbox1" value="Air Canada" style="display:none" <%Response.Write(GetMemberAirlines("Air Canada"));%> />
                            <input type="checkbox" name="checkbox2" id="checkbox2" value="Air China" style="display:none" <%Response.Write(GetMemberAirlines("Air China"));%> />
                            <input type="checkbox" name="checkbox3" id="checkbox3" value="Air France" style="display:none" <%Response.Write(GetMemberAirlines("Air France"));%> />
                            <input type="checkbox" name="checkbox4" id="checkbox4" value="Alaska Airlines" style="display:none" <%Response.Write(GetMemberAirlines("Alaska Airlines"));%> />
                            <input type="checkbox" name="checkbox5" id="checkbox5" value="All Nippon Airways" style="display:none" <%Response.Write(GetMemberAirlines("All Nippon Airways"));%> />
                            <input type="checkbox" name="checkbox6" id="checkbox6" value="America West Airlines" style="display:none" <%Response.Write(GetMemberAirlines("America West Airlines"));%> />
                            <input type="checkbox" name="checkbox7" id="checkbox7" value="American Airlines" style="display:none" <%Response.Write(GetMemberAirlines("American Airlines"));%> />
                            <input type="checkbox" name="checkbox8" id="checkbox8" value="Asiana" style="display:none" <%Response.Write(GetMemberAirlines("Asiana"));%> />
                            <input type="checkbox" name="checkbox9" id="checkbox9" value="British Airways" style="display:none" <%Response.Write(GetMemberAirlines("British Airways"));%> />
                            <input type="checkbox" name="checkbox10" id="checkbox10" value="Cathay Pacific" style="display:none" <%Response.Write(GetMemberAirlines("Cathay Pacific"));%> />
                            <input type="checkbox" name="checkbox11" id="checkbox11" value="China Airlines" style="display:none" <%Response.Write(GetMemberAirlines("China Airlines"));%> />
                            <input type="checkbox" name="checkbox12" id="checkbox12" value="China Eastern Airlines" style="display:none" <%Response.Write(GetMemberAirlines("China Eastern Airlines"));%> />
                            <input type="checkbox" name="checkbox13" id="checkbox13" value="China Southern Airlines" style="display:none" <%Response.Write(GetMemberAirlines("China Southern Airlines"));%> />
                            <input type="checkbox" name="checkbox14" id="checkbox14" value="Continental" style="display:none" <%Response.Write(GetMemberAirlines("Continental"));%> />
                            <input type="checkbox" name="checkbox15" id="checkbox15" value="Delta Airlines" style="display:none" <%Response.Write(GetMemberAirlines("Delta Airlines"));%> />
                            <input type="checkbox" name="checkbox16" id="checkbox16" value="Dragon Air" style="display:none" <%Response.Write(GetMemberAirlines("Dragon Air"));%> />
                            <input type="checkbox" name="checkbox17" id="checkbox17" value="Frontier Airlines" style="display:none" <%Response.Write(GetMemberAirlines("Frontier Airlines"));%> />
                            <input type="checkbox" name="checkbox18" id="checkbox18" value="Hainan Airlines" style="display:none" <%Response.Write(GetMemberAirlines("Hainan Airlines"));%> />
                            <input type="checkbox" name="checkbox19" id="checkbox19" value="Japan Airlines" style="display:none" <%Response.Write(GetMemberAirlines("Japan Airlines"));%> />
                            <input type="checkbox" name="checkbox20" id="checkbox20" value="KLM" style="display:none" <%Response.Write(GetMemberAirlines("KLM"));%> />
                            <input type="checkbox" name="checkbox21" id="checkbox21" value="Korean Air" style="display:none" <%Response.Write(GetMemberAirlines("Korean Air"));%> />
                            <input type="checkbox" name="checkbox22" id="checkbox22" value="Lufthansa" style="display:none" <%Response.Write(GetMemberAirlines("Lufthansa"));%> />
                            <input type="checkbox" name="checkbox23" id="checkbox23" value="Malysian Airlines" style="display:none" <%Response.Write(GetMemberAirlines("Malysian Airlines"));%> />
                            <input type="checkbox" name="checkbox24" id="checkbox24" value="Northwest Airlines" style="display:none" <%Response.Write(GetMemberAirlines("Northwest Airlines"));%> />
                            <input type="checkbox" name="checkbox25" id="checkbox25" value="Philippine Airlines" style="display:none" <%Response.Write(GetMemberAirlines("Philippine Airlines"));%> />
                            <input type="checkbox" name="checkbox26" id="checkbox26" value="SAS" style="display:none" <%Response.Write(GetMemberAirlines("SAS"));%> />
                            <input type="checkbox" name="checkbox27" id="checkbox27" value="Shanghai Airlines" style="display:none" <%Response.Write(GetMemberAirlines("Shanghai Airlines"));%> />
                            <input type="checkbox" name="checkbox28" id="checkbox28" value="Singapore Airlines" style="display:none" <%Response.Write(GetMemberAirlines("Singapore Airlines"));%> />
                            <input type="checkbox" name="checkbox29" id="checkbox29" value="Spirit Airlines" style="display:none" <%Response.Write(GetMemberAirlines("Spirit Airlines"));%> />
                            <input type="checkbox" name="checkbox30" id="checkbox30" value="Thai Airways Intl" style="display:none" <%Response.Write(GetMemberAirlines("Thai Airways Intl"));%> />
                            <input type="checkbox" name="checkbox31" id="checkbox31" value="United Airlines" style="display:none" <%Response.Write(GetMemberAirlines("United Airlines"));%> />
                            <input type="checkbox" name="checkbox32" id="checkbox32" value="US Airways" style="display:none" <%Response.Write(GetMemberAirlines("US Airways"));%> />
                            <input type="checkbox" name="checkbox33" id="checkbox33" value="Vietnam Airlines" style="display:none" <%Response.Write(GetMemberAirlines("Vietnam Airlines"));%> />
                            <input type="checkbox" name="checkbox34" id="checkbox34" value="Virgin Atlantic" style="display:none" <%Response.Write(GetMemberAirlines("Virgin Atlantic"));%> />                                                            
                        </dd>
		            </dl>		   
		            <dl style="padding-left:200px; padding-top:10px;">
			            <dt>			                
                            <asp:Button ID="Member_Reset" runat="server" Text="重新填写" CssClass="win_input" OnClientClick="window.location.href=window.location.href;" />
                            &nbsp;
			            </dt>
			            <dd style="padding-top:15px;">
			                &nbsp;
			                <asp:Button ID="Member_Submit" runat="server" Text="确定修改" CssClass="win_input" OnClick="Member_Submit_Click" />
			            </dd>
		            </dl>         
		            <div style="clear:both; height:20px;"></div>            
		            <dl><span>如有介绍码,请正确填写,会享受优惠折扣;带*号的是必填项,请如实填写,谢谢</span></dl>	                       
		        </div>
		        <div id="Div2" class="win_pic_txt2" style="padding-left:100px; padding-right:100px; width:365px;" runat="server">
		            <dl style=" padding-top:100px;" class="win_pic_txt2dl">
			            <dt>输入旧密码：</dt>
			            <dd><asp:TextBox ID="Member_PassWord" runat="server" ToolTip="输入旧密码" Width="155" MaxLength="20" TextMode="Password"></asp:TextBox></dd>            		
		            </dl>
		            <dl class="win_pic_txt2dl">
			            <dt>输入新密码：</dt>
			            <dd><asp:TextBox ID="Member_PassWord1" runat="server" ToolTip="输入旧密码" Width="155" MaxLength="20" TextMode="Password"></asp:TextBox></dd>            		
		            </dl>
		            <dl class="win_pic_txt2dl">
			            <dt>确认新密码：</dt>
			            <dd><asp:TextBox ID="Member_PassWord2" runat="server" ToolTip="输入旧密码" Width="155" MaxLength="20" TextMode="Password"></asp:TextBox></dd>            		
		            </dl>
		            <dl class="win_pic_txt2dl" style=" padding-left:100px; padding-top:10px;">
			            <dt>
			                <asp:Button ID="Member_PassWord_Reset" runat="server" Text="重新填写" CssClass="win_input" OnClientClick="ClearPassWord();return false;" />
			                &nbsp;
			            </dt>
			            <dd style="padding-top:15px;">
			                &nbsp;
			                <asp:Button ID="Member_PassWord_Submit" runat="server" Text="确认修改" CssClass="win_input" OnClick="Member_PassWord_Submit_Click" />
			            </dd>
		            </dl>            	
	            </div>    	            	        
	            <div id="Div3" class="win_pic_txt2" runat="server">
			        <div style="height:435px; width:540px; border:1px solid #ffffff; overflow: scroll; margin:0 auto; margin-top:20px;">
		                <dl style="color:#797979; float:left; text-indent:10px; width:540px;">你的账号是：<asp:Label ID="Member_Consumption_Account" runat="server"></asp:Label></dl>
	                    <dl class="win_pic_txtdl_1" id="Div_Consumption" runat="server">
		                    <dd style=" float:left;">你的佣金为：<asp:Label ID="Member_Consumption_Commission" runat="server"></asp:Label>(美元)</dd>
		                    <dd style=" float:left; padding-left:100px;">成功介绍旅行次数为：<asp:Label ID="Member_Consumption_Times" runat="server"></asp:Label>次</dd>	
		                </dl>
	                    <dl class="win_pic_txtdl1">
	                        <dd style=" float:left;">你现在的积分为:<asp:Label ID="Member_Consumption_Points" runat="server"></asp:Label></dd>
		                    <dd style=" float:left; padding-left:100px;">总消费为<asp:Label ID="Member_Consumption_Consumption" runat="server"></asp:Label>元</dd>
	    	            </dl>
    	                <dl id="Member_Consumption_List" class="win_pic_txtdl2" runat="server">
	                        <dt class="win_pic_txtdl3" style="margin-top:-5px;">出行时间</dt>
	                        <dt class="win_pic_txtdl4" style="margin-top:-5px;">类型</dt>
	                        <dt class="win_pic_txtdl3" style="margin-top:-5px;">出发地</dt>
	                        <dt class="win_pic_txtdl3" style="margin-top:-5px;">目标地点</dt>
	                        <dt class="win_pic_txtdl4" style="margin-top:-5px;">票价</dt>
	                        <dt class="win_pic_txtdl3" style="margin-top:-5px;">获得积分</dt>	                        
                        </dl>	
	                </div>
	            </div>	
	        </div>	
	        <div class="win_pic_footer"></div>	
	        <div class="win_footer"></div>
	    </div>
    </form>
</body>
</html>

