﻿<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Index.aspx.cs" Inherits="Web.Index" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <meta http-equiv="x-ua-compatible" content="ie=7" />    
    <title>华捷国际旅游</title>
    <link type="text/css" href="css/aisa.css" rel="stylesheet" />
    <script type="text/javascript" src="js/aisa.js"></script>
</head>
<body>
    <script type="text/javascript">
	    function showShop(flag)
        {
            if (flag == 1)
            {
	            document.getElementById("arrowImg1").src = "#";
	            document.getElementById("arrowImg2").src = "#";	
	            document.getElementById("shop1").style.display = "block"; 
	            document.getElementById("shop2").style.display = "none"; 
            }
            else
            {
	            document.getElementById("arrowImg1").src = "#";
	            document.getElementById("arrowImg2").src = "#";	
	            document.getElementById("shop1").style.display = "none";
 	            document.getElementById("shop2").style.display = "block"; 
            }
        }
        
        function showSend()
        {
            document.getElementById("cardImg").src = "#";
            document.getElementById("send").style.display = "block";
            document.getElementById("fill").style.display = "none";
        }
        
        function showFill()
        {
            document.getElementById("cardImg").src = "#";
            document.getElementById("send").style.display = "none";
            document.getElementById("fill").style.display = "block";
        }

        var rollTime = 1;
        function changeImgg(flag)
        {
            for(var i = 1;i < 3;i++)
            {
                document.getElementById("rollImgs" + i).style.display = "none"; 	
                document.getElementById("imgq" + i).src = "images/home_iconss_" + i + ".jpg";
            }
            
            document.getElementById("rollImgs" + flag).style.display = "block";
            document.getElementById("imgq" + flag).src = "images/home_iconn_" + flag + ".jpg";
        }

        window.setInterval("AutoChangeRoll()", 3000);
    </script>
    <form id="form1" runat="server">
        <div style="width:1165px; margin:0 auto; height:auto;">
	        <div class="home_top">
	            <div style="width:1165px; height:205px;">
	                <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" width="1165" height="205" title="华捷旅游">
                        <param name="WMODE" value="transparent"/>
		                <param name="movie" value="images/home.swf" />
                        <param name="quality" value="high" />                        
                    </object>
	            </div>
		        <div class="home_top_login">
				    <dl id="Login_Input" class="home_top_input" runat="server">
					    <dt class="home_top_input2" style="float:left;">
					        <span style="width:37px; height:18px; background:url(images/home_6.jpg) no-repeat; display:block; float:left; margin-right:10px; margin-top:3px;"></span>					
						    <span style="display:block; float:left;"><asp:TextBox ID="Member_Account_Name" runat="server" CssClass="input" MaxLength="30"></asp:TextBox></span>
						    <span style="display:block; float:left; background:url(images/home_7.jpg) no-repeat; width:40px; height:17px; margin-top:3px;"></span>						
						    <span style="display:block; float:left;"><asp:TextBox ID="Member_Account_PassWord" runat="server" CssClass="input" MaxLength="20" TextMode="Password" style="margin-left:10px;"></asp:TextBox></span>
					    </dt>
					    <dd style="float:left;"><asp:LinkButton ID="Member_Account_Submit" runat="server" ToolTip="登录" CssClass="input_a" onclick="Member_Account_Submit_Click"></asp:LinkButton></dd>
					    <div style="clear:both;"></div>
					    <dt class="home_top_input3">
						    <a href="Article.aspx?Article_ClassID=3" class="input3">成为会员能享受什么好处？</a>
						    <a href="#" class="input3" style="margin-left:22px;" onclick="window.open('Member_Reg.aspx','Member','toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=630px,height=660px');return false;">立即注册</a>
						    <a href="Member_Forget.aspx" class="input3">忘记密码</a>						
					    </dt>				
				    </dl>
				    <dl id="Login_Info" class="home_top_input" runat="server">
					    <p class="inside2_login" style="line-height:18px;padding-left:15px; padding-top:3px;"><strong id="Member_Name_CN" runat="server"></strong> 您好! 欢迎你的登录,你的积分是：<span id="Member_Points" runat="server"></span></p>
					    <p class="inside2_login" style="line-height:18px;padding-left:15px;">你的账号是：<span id="Member_Account" runat="server"></span> | 会员等级为：<span id="Member_Level" runat="server"></span></p>
					    <p class="inside2_login" style="font-size:12px; letter-spacing:1px; line-height:18px; padding-left:15px;">
					        <asp:LinkButton ID="Member_Info_Detail" runat="server" CssClass="input3" OnClientClick="window.open('Member_Info.aspx?Member_Info_ID=1','Member','toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=630px,height=660px');return false;">修改会员信息</asp:LinkButton> | 
					        <asp:LinkButton ID="Member_Info_PassWord" runat="server" CssClass="input3" OnClientClick="window.open('Member_Info.aspx?Member_Info_ID=2','Member','toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=630px,height=660px');return false;">修改密码</asp:LinkButton> | 
					        <asp:LinkButton ID="Member_Info_Consumption" runat="server" CssClass="input3" OnClientClick="window.open('Member_Info.aspx?Member_Info_ID=3','Member','toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=630px,height=660px');return false;">查询消费纪录</asp:LinkButton> | 
					        <asp:HyperLink ID="Member_Quit" NavigateUrl="../Member_Quit.aspx" runat="server" CssClass="input3">退出登陆</asp:HyperLink>
					    </p>						
				    </dl>
		        </div>	
	        </div>
	        <div class="home_nav">
		        <a href="Article.aspx?Article_ClassID=1" style=" margin:4px 0 0 56px!important; margin:3px 0 0 28px;" class="nav2 home_nav_icon"></a>
		        <a href="Travel_List.aspx?Travel_TypeID=1" style=" margin:4px 0 0 20px;" class="nav3 home_nav_icon"></a>
		        <a href="LowFare.aspx" style=" margin:4px 0 0 22px;" class="nav4 home_nav_icon"></a>
		        <a href="News_List.aspx?News_ClassID=1" style=" margin:4px 0 0 22px;" class="nav5 home_nav_icon"></a>
		        <a href="Knows_List.aspx?Knows_ClassID=0" style=" margin:4px 0 0 22px;" class="nav6 home_nav_icon"></a>
		        <a href="Article.aspx?Article_ClassID=2" style=" margin:4px 0 0 22px;" class="nav7 home_nav_icon"></a>
        		<dl class="home_nav_link">
			        <a href="News_List.aspx?News_ClassID=4" class="nav8">表格下载</a>
			        <a href="#" class="nav8" style="padding-left:45px;">在线客服</a>		
		        </dl>
		        <div class="fod_v">
		            <dl class="fod_v1">
				        <a href="News_List.aspx?News_ClassID=1"><img id="imgq1" onmouseover="changeImgg(1)" src="images/home_iconn_1.jpg" alt="" /></a>
					</dl>
				    <span class="dd">&nbsp;&nbsp;</span>
				    <dl class="fod_v2">
				    	<a href="News_List.aspx?News_ClassID=2"><img id="imgq2" onmouseover="changeImgg(2)" src="images/home_iconss_2.jpg" alt="" /></a>
			        </dl>
			    </div>		
	        </div>
	        <div class="home_content">
	            <div class="home_content_demo">
			        <div id="rollImg1">
	    		        <a href="#" target="_blank"><img src="images/home_photo_1.jpg" border="0" alt="" /></a>
			        </div>
	   		        <div id="rollImg2" style="display:none">
	   			        <a href="#" target="_blank"><img src="images/home_photo_2.jpg" border="0" alt="" /></a>
			        </div>
	    	        <div id="rollImg3" style="display:none">
	    		        <a href="#" target="_blank"><img src="images/home_photo_1.jpg" border="0" alt="" /></a>
			        </div>
	                <div class="home_content_icon">
	    		        <div class="home_content_photo">
					        <a href="#"><img id="img1" onmouseover="changeImg(1)" src="images/home_icon_1.jpg" alt="" /></a>
			            </div>
        		        <div class="home_content_photo">
					        <a href="#"><img id="img2" onmouseover="changeImg(2)" src="images/home_icon_2.jpg" alt="" /></a>
			            </div>
        		        <div class="home_content_photo">
        		            <a href="#"><img id="img3" onmouseover="changeImg(3)" src="images/home_icon_3.jpg" alt="" /></a>
        		        </div>
	                </div>
	            </div>
	            <div id="foda1">
	                <div id="rollImgs1" class="home_comtent_txt">
	  	                <ul id="News_ClassID_1_Controls" runat="server">	  	                    
	                    </ul>        	 
	                    <div id="News_ClassID_1_More" class="home_comternt_dl" runat="server"><a href="News_List.aspx?News_ClassID=1" class="nav8">更多>>></a></div>
	                </div>        	  
	                <div id="rollImgs2" class="home_comtent_txt2" style="display:none;">
	  	                <ul id="News_ClassID_2_Controls" runat="server">	  	                    
	                    </ul>        	 
	                    <div id="News_ClassID_2_More" class="home_comternt_dl" runat="server"><a href="News_List.aspx?News_ClassID=2" class="nav8">更多>>></a></div>
	                </div>
	            </div>	  
	        </div>	        
	        <div class="home_footer">
	            <p style="color:#666666;"><a href="Article.aspx?Article_ClassID=1">关于华捷</a> | <a href="Travel_List.aspx?Travel_TypeID=1">旅游线路</a> | <a href="LowFare.aspx">机票问价</a> | <a href="News_List.aspx?News_ClassID=1">最新资讯</a> | <a href="Knows_List.aspx?Knows_ClassID=0">旅游需知</a> | <a href="Article.aspx?Article_ClassID=2">联系我们</a> | <a href="News_List.aspx?News_ClassID=3">表格下载</a></p>
                <p>Copyright<span style="font-size:20px;">&reg;</span>2009 Aisa travel express. All Rights Reserved</p>
            </div>
        </div>
    </form>
</body>
</html>
