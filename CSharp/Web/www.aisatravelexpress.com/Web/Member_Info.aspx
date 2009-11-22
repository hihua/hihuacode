<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Member_Info.aspx.cs" Inherits="Web.Member_Info" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head id="Head1" runat="server">
    <meta http-equiv="x-ua-compatible" content="ie=7" />
    <title>华捷国际旅游</title>
    <link type="text/css" href="css/aisa.css" rel="stylesheet" />
    <link type="text/css" href="css/window.css" rel="stylesheet" />
    <script type="text/javascript">
	    function getNames(obj,name,tij)
	    {	
		    var p = document.getElementById(obj);
		    var plist = p.getElementsByTagName(tij);
		    var rlist = new Array();
		    for(i=0;i<plist.length;i++)
		    {
			    if(plist[i].getAttribute("name") == name)
			    {
				    rlist[rlist.length] = plist[i];
			    }
		    }
		    
		    return rlist;
	    }

		function win_pic(obj,name)
		{ 
			var p = obj.parentNode.getElementsByTagName("li");
			var p1 = getNames(name,"f","div"); // document.getElementById(name).getElementsByTagName("div");
			for(i=0;i<p1.length;i++)
			{
				if(obj==p[i])
				{
					p[i].className = "win_pic_li1";
					p1[i].className = "win_pic_txt";
				}
				else
				{
					p[i].className = "win_pic_li2";
					p1[i].className = "win_pic_txt2";
				}
			}
		}
		
		function ShowDiv(Pos)
		{
		    for (var i = 1;i <= 3;i++)
		    {
		        var div = document.getElementById("Div" + Pos);
		        if (div != null)
		        {
		            if (i == Pos)
		                div.style.display = "block";
		            else
		                div.style.display = "none";
		        }
		    }		
		}
	</script>
</head>
<body>
    <form id="form1" runat="server">
        <div id="window">
            <div class="win_top">
		        <div class="win_logo"></div>
		        <div class="win_pic">
			        <div class="win_picimg"><a href="#"><img src="images/window_3.jpg" alt="" /></a></div>
			        <ul>
				        <li class="win_pic_li1" style="height:22px; margin-bottom:-1px;" onmouseover="win_pic(this,'foda1')" onclick="register0410('qzone',1)">会员信息</li>
				        <li class="win_pic_li2" style="height:22px; margin-bottom:-1px;" onmouseover="win_pic(this,'foda1')" onclick="register0410('qzone',2)">修改密码</li>
				        <li class="win_pic_li2" style="height:22px; margin-bottom:-1px;" onmouseover="win_pic(this,'foda1')" onclick="register0410('qzone',3)">查询消费记录</li>
			        </ul>
                </div>
	        </div>
	        <div id="foda1">
	            <div id="Div1" class="win_pic_txt">
		            <dl>
			            <dt>帐&nbsp;&nbsp;&nbsp;&nbsp;号：</dt>
			            <dd><asp:TextBox ID="Member_Account" runat="server" ToolTip="输入帐号" Width="155" MaxLength="30"></asp:TextBox></dd>
		            </dl>
		            <dl>
			            <dt>介&nbsp;绍&nbsp;码：</dt>
			            <dd><asp:TextBox ID="Member_Serial" runat="server" ToolTip="介绍码" Width="155" MaxLength="50"></asp:TextBox></dd>
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
			                <asp:CheckBox ID="Member_Months_5" runat="server" Text="5月" />
			                <asp:CheckBox ID="Member_Months_6" runat="server" Text="6月" />
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
			            <dd style=" padding-top:15px;">
			                &nbsp;
			                <asp:Button ID="Member_Submit" runat="server" Text="确定注册" CssClass="win_input" OnClick="Member_Submit_Click" />
			            </dd>
		            </dl>         
		        </div>
		        <div id="Div2" class="win_pic_txt2" style="padding-left:100px; padding-right:100px; width:365px;" onclick="register0410('qzone',2)">
		            <dl style=" padding-top:100px;" class="win_pic_txt2dl">
			            <dt>输入旧密码：</dt>
			            <dd><asp:TextBox ID="Member_PassWord" runat="server" ToolTip="输入旧密码" Width="155" MaxLength="20"></asp:TextBox></dd>            		
		            </dl>
		            <dl class="win_pic_txt2dl">
			            <dt>输入新密码：</dt>
			            <dd><asp:TextBox ID="Member_PassWord1" runat="server" ToolTip="输入旧密码" Width="155" MaxLength="20"></asp:TextBox></dd>            		
		            </dl>
		            <dl class="win_pic_txt2dl">
			            <dt>确认新密码：</dt>
			            <dd><asp:TextBox ID="Member_PassWord2" runat="server" ToolTip="输入旧密码" Width="155" MaxLength="20"></asp:TextBox></dd>            		
		            </dl>
		            <dl class="win_pic_txt2dl" style=" padding-left:100px; padding-top:10px;">
			            <dt>
			                <asp:Button ID="Member_PassWord_Reset" runat="server" Text="重新填写" CssClass="win_input" OnClientClick="Member_PassWord.vale='';Member_PassWord1.vale='';Member_PassWord2.vale='';" />
			            </dt>
			            <dd style="padding-top:15px;">
			                <asp:Button ID="Member_PassWord_Submit" runat="server" Text="确认修改" CssClass="win_input" />
			            </dd>
		            </dl>            	
	            </div>    	            	        
	            <div id="Div3" class="win_pic_txt2" onclick="register0410('qzone',3)">
			        <div style="height:435px; width:540px; border:1px solid #ffffff; overflow: scroll; margin:0 auto; margin-top:20px;">
		                <dl style="color:#797979; float:left; text-indent:10px; width:540px;">你的账号是：alex</dl>
	                    <dl class="win_pic_txtdl_1">
		                    <dd style=" float:left;">你的佣金为：50(美元)</dd>
		                    <dd style=" float:left; padding-left:100px;">成功介绍旅行次数为：10次</dd>	
		                </dl>
	                    <dl class="win_pic_txtdl1">
	                        <dd style=" float:left;">你现在的积分为:400</dd>
		                    <dd style=" float:left; padding-left:100px;">总消费为2000元</dd>
	    	            </dl>
    	                <dl class="win_pic_txtdl2">
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

