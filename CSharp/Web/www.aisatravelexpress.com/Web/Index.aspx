<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Index.aspx.cs" Inherits="Web.Index" %>

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
		        <div class="home_top_login">
				    <a class="home_top_a" href="#" title="中文版"></a>
				    <a class="home_top_a" style="margin-top:20px;" href="#" title="英文版"></a>
				    <dl class="home_top_input">
					    <dt class="home_top_input2" style="float:left;">
						    <input name="1" type="text" class="input" />
						    <input name="1" type="text" class="input" style="margin-left:50px;" />						
					    </dt>
					    <dd style=" float:left;"><a class="input_a" href="#" title="GO"></a></dd>
					    <div style="clear:both;"></div>
					    <dt class="home_top_input3">
						    <a href="#" class="input3">成为会员能享受什么好处？</a>
						    <a href="#" class="input3" style="margin-left:22px;">立即注册</a>
						    <a href="#" class="input3">忘记密码</a>						
					    </dt>				
				    </dl>
		        </div>	
	        </div>
	        <div class="home_nav">
		        <a href="Article.aspx?Article_ClassID=1" style=" margin:4px 0 0 56px!important; margin:3px 0 0 28px;" class="nav2 home_nav_icon"></a>
		        <a href="#" style=" margin:4px 0 0 20px;" class="nav3 home_nav_icon"></a>
		        <a href="#" style=" margin:4px 0 0 22px;" class="nav4 home_nav_icon"></a>
		        <a href="#" style=" margin:4px 0 0 22px;" class="nav5 home_nav_icon"></a>
		        <a href="#" style=" margin:4px 0 0 22px;" class="nav6 home_nav_icon"></a>
		        <a href="Article.aspx?Article_ClassID=2" style=" margin:4px 0 0 22px;" class="nav7 home_nav_icon"></a>
        		<dl class="home_nav_link">
			        <a href="#" class="nav8">表格下载</a>
			        <a href="#" class="nav8" style="padding-left:45px;">在线客服</a>		
		        </dl>
		        <div class="fod">
		            <dl class="fod_v1">
				        <a><img id="imgq1" onmouseover="changeImgg(1)" src="images/home_iconn_1.jpg" alt="" /></a>
					</dl>
				    <span class="dd">&nbsp;&nbsp;</span>
				    <dl class="fod_v2">
				    	<a><img id="imgq2" onmouseover="changeImgg(2)" src="images/home_iconss_2.jpg" alt="" /></a>
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
	                    <div class="home_comternt_dl"><a href="#" class="nav8">更多>>></a></div>
	                </div>        	  
	                <div id="rollImgs2" class="home_comtent_txt2" style="display:none;">
	  	                <ul id="News_ClassID_2_Controls" runat="server">	  	                    
	                    </ul>        	 
	                    <div class="home_comternt_dl"><a href="#" class="nav8">更多>>></a></div>
	                </div>
	            </div>	  
	        </div>
	        
	        <div class="home_footer">
	            <p style="color:#666666;"><a href="#">关于华捷</a> | <a href="#">旅游线路</a> | <a href="#">机票问价</a> | <a href="#">最新资讯</a> | <a href="#">旅游需知</a> | <a href="#">联系我们</a> | <a href="#">表格下载</a> | <a href="#">在线客服</a></p>
                <p>Copyright<span style="font-size:20px;">&reg;</span>2009 Aisa travel express. All Rights Reserved</p>
            </div>
        </div>
    </form>
</body>
</html>
