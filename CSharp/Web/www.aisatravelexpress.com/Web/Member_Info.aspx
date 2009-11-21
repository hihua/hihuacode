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
	            <div class="win_pic_txt"onclick="register0410('qzone',1)" name="f">
		            <dl>
			            <dt>帐&nbsp;&nbsp;&nbsp;&nbsp;号：</dt>
			            <dd><input name="" type="text" title="输入帐号" style="width:155px;"/></dd>
		            </dl>
		            <dl>
			            <dt>介&nbsp;绍&nbsp;码：</dt>
			            <dd><input name="" type="text" title="介绍码" style="width:155px;"/></dd>
		            </dl>
		            <dl>
			            <dt>中&nbsp;文&nbsp;名：</dt>
			            <dd><input name="" type="text" title="中文名" style="width:155px;"/></dd>
		            </dl>
		            <dl>
			            <dt> 英&nbsp;文&nbsp;名：</dt>
			            <dd><input name="" type="text" title="英文名" style="width:155px;"/></dd>
		            </dl>
		            <dl>
			            <dt>性&nbsp;&nbsp;&nbsp;&nbsp;别：</dt>
			            <dd style="text-indent:1em; width:159px;">
			                <input name="" type="radio" style="margin-right:5px; margin-top:-5px;" value=""/>男
			                <input name="" type="radio" style="margin-right:5px; margin-top:-5px; margin-left:5px;" value="" checked="checked"/>女
			            </dd>
		            </dl>
			        <dl>
			            <dt>工作类型：</dt>
			            <dd><input name="" type="text" title="工作类型" style="width:155px;"/></dd>
		            </dl>
		            <dl>
			            <dt>电&nbsp;&nbsp;&nbsp;&nbsp;话：</dt>
			            <dd><input name="" type="text" title="电话" style="width:155px;"/></dd>
		            </dl>
		            <dl>
			            <dt>手机号码：</dt>
			            <dd><input name="" type="text" title="手机号码" style="width:155px;"/></dd>
		            </dl>
		            <dl>
			            <dt>居住地址：</dt>
			            <dd><input name="" type="text" title="居住地址" style="width:422px;"/></dd>
		            </dl>
		            <dl>
			            <dt>公司名称：</dt>
			            <dd><input name="" type="text" title="公司名称" style="width:155px;"/></dd>
		            </dl>
			            <dl>
			            <dt>公司电话：</dt>
			            <dd><input name="" type="text" title="公司电话" style="width:155px;"/></dd>
		            </dl>
		            <dl>
			            <dt>公司地址：</dt>
			            <dd><input name="" type="text" title="公司地址" style="width:422px;"/></dd>
		            </dl>
		            <dl>
			            <dt>预计出行月份：（多选）1-12月：</dt>
			            <dd style="width:490px; line-height:30px; letter-spacing:1px; padding-left:25px; text-indent:0px;">
			                <input name="" type="checkbox" value="" title="出行月份" style="margin-top:-5px;"/>1月
			                <input name="" type="checkbox" value="" title="出行月份" style="margin-top:-5px;"/>2月
			                <input name="" type="checkbox" value="" title="出行月份" style="margin-top:-5px;"/>3月
			                <input name="" type="checkbox" value="" title="出行月份" style="margin-top:-5px;"/>4月
			                <input name="" type="checkbox" value="" title="出行月份" style="margin-top:-5px;"/>5月
			                <input name="" type="checkbox" value="" title="出行月份" style="margin-top:-5px;"/>6月
			                <input name="" type="checkbox" value="" title="出行月份" style="margin-top:-5px;"/>7月
			                <input name="" type="checkbox" value="" title="出行月份" style="margin-top:-5px;"/>8月
			                <input name="" type="checkbox" value="" title="出行月份" style="margin-top:-5px;"/>9月
			                <input name="" type="checkbox" value="" title="出行月份" style="margin-top:-5px;"/>10月
			                <input name="" type="checkbox" value="" title="出行月份" style="margin-top:-5px;"/>11月
			                <input name="" type="checkbox" value="" title="出行月份" style="margin-top:-5px;"/>12月
			            </dd>
		            </dl>
		            <dl>
			            <dt>常用航空公司：</dt>
			            <dd><input name="" type="text" title="常用航空公司" style="width:350px;"/></dd>
		            </dl>
		            <dl style=" padding-left:200px; padding-top:10px;">
			            <dt>
			                <input name="取消修改" type="reset" value="取消修改" style="padding-top:3px; margin-right:10px; border:1px solid #0278c2; background-color:#e9faff; color:#0278c2;"/>
			            </dt>
			            <dd style=" padding-top:15px;">
			                <input name="确定修改" type="submit" value="确定修改" style="padding-top:3px; border:1px solid #0278c2; background-color:#e9faff; color:#0278c2;" />
			            </dd>
		            </dl>
		        </div>    	            	        
	            <div class="win_pic_txt2" onclick="register0410('qzone',3)" name="f">
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
	                        <dt class="win_pic_txtdl4 win_pic_txtdl_none">2009-10-10</dt>
	                        <dt class="win_pic_txtdl4 win_pic_txtdl_none">双程</dt>
	                        <dt class="win_pic_txtdl4 win_pic_txtdl_none">美国芝加哥</dt>
	                        <dt class="win_pic_txtdl3 win_pic_txtdl_none">中国北京</dt>
	                        <dt class="win_pic_txtdl4 win_pic_txtdl_none">1000</dt>
	                        <dt class="win_pic_txtdl3 win_pic_txtdl_none">200</dt>
	                        <dt class="win_pic_txtdl4">2009-10-10</dt>
	                        <dt class="win_pic_txtdl4">双程</dt>
	                        <dt class="win_pic_txtdl4">美国芝加哥</dt>
	                        <dt class="win_pic_txtdl3">中国北京</dt>
	                        <dt class="win_pic_txtdl4">1000</dt>
	                        <dt class="win_pic_txtdl3">200</dt>
	                        <dt class="win_pic_txtdl4 win_pic_txtdl_none">2009-10-10</dt>
	                        <dt class="win_pic_txtdl4 win_pic_txtdl_none">双程</dt>
	                        <dt class="win_pic_txtdl4 win_pic_txtdl_none">美国芝加哥</dt>
	                        <dt class="win_pic_txtdl3 win_pic_txtdl_none">中国北京</dt>
	                        <dt class="win_pic_txtdl4 win_pic_txtdl_none">1000</dt>
	                        <dt class="win_pic_txtdl3 win_pic_txtdl_none">200</dt>
	                        <dt class="win_pic_txtdl4">2009-10-10</dt>
	                        <dt class="win_pic_txtdl4">双程</dt>
	                        <dt class="win_pic_txtdl4">美国芝加哥</dt>
	                        <dt class="win_pic_txtdl3">中国北京</dt>
	                        <dt class="win_pic_txtdl4">1000</dt>
	                        <dt class="win_pic_txtdl3">200</dt>
	                        <dt class="win_pic_txtdl4 win_pic_txtdl_none">2009-10-10</dt>
	                        <dt class="win_pic_txtdl4 win_pic_txtdl_none">双程</dt>
	                        <dt class="win_pic_txtdl4 win_pic_txtdl_none">美国芝加哥</dt>
	                        <dt class="win_pic_txtdl3 win_pic_txtdl_none">中国北京</dt>
	                        <dt class="win_pic_txtdl4 win_pic_txtdl_none">1000</dt>
	                        <dt class="win_pic_txtdl3 win_pic_txtdl_none">200</dt>
	                        <dt class="win_pic_txtdl4">2009-10-10</dt>
	                        <dt class="win_pic_txtdl4">双程</dt>
	                        <dt class="win_pic_txtdl4">美国芝加哥</dt>
	                        <dt class="win_pic_txtdl3">中国北京</dt>
	                        <dt class="win_pic_txtdl4">1000</dt>
	                        <dt class="win_pic_txtdl3">200</dt>
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

