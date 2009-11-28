<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Tools.aspx.cs" Inherits="Web.Admin.Tools" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>华捷国际旅游后台</title>
    <link type="text/css" href="css.css" rel="stylesheet" />
    <script type="text/javascript" src="../Js/jquery.js"></script>
</head>
<body bgcolor="#799AE1">
    <form id="form1" runat="server">
        <div>            
            <table id="Table1" align="left" cellspacing="0" cellpadding="0" width="158">
                <tr>
		            <td><img width="158" height="38" src="images/title.gif" alt=""/></td>
	            </tr>
	            <tr>
		            <td class="ToolsSpan1" onmouseover="this.className='ToolsSpan2';" onmouseout="this.className='ToolsSpan1';" background="images/title_bg_quit.gif" height="25">
			            <span style="color:#215DC6; font-weight:bold; cursor:pointer" onclick="window.top.location.href='../Index.aspx'">回到首页</span>
			            | 
			            <span style="color:#215DC6; font-weight:bold; cursor:pointer" onclick="window.top.location.href='Logout.aspx'">退出</span>		
		            </td>
	            </tr>
	            <tr>
		            <td height="25">&nbsp;</td>
	            </tr>
	            <tr>
		            <td id="Menu1" class="ToolsSpan1" onmouseover="this.className='ToolsSpan2';" onclick="ShowSubMenu(1)" onmouseout="this.className='ToolsSpan1';" style="cursor:pointer" background="images/menudown.gif" height="25">
			            <span>用户管理</span>
		            </td>
	            </tr>
	            <tr>
		            <td id="SubMenu1" style="display:none">
			            <div class="ToolsSpan3" style="width: 158px">
				            <table cellspacing="3" cellpadding="0" width="130" align="center">
					            <tr>
						            <td><a href="#" target="Main" onclick="window.open('AdminUser_Detail.aspx','AdminUser','toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=680px,height=510px');return false;" class="IndexMenuLink1">添加管理员</a></td>
						        </tr>
					            <tr>
						            <td><a href="AdminUser.aspx" target="Main" class="IndexMenuLink1">管理员管理</a></td>
						        </tr>
						        <tr>
						            <td><a href="MSN.aspx" target="Main" class="IndexMenuLink1">MSN管理</a></td>
						        </tr>						        					            
				            </table>
			            </div>
			            <br />		
		            </td>
	            </tr>
	            <tr>
		            <td id="Menu2" class="ToolsSpan1" onmouseover="this.className='ToolsSpan2';" onclick="ShowSubMenu(2)" onmouseout="this.className='ToolsSpan1';" style="cursor:pointer" background="images/menudown.gif" height="25">
			            <span>文章管理</span>
		            </td>
	            </tr>
	            <tr>
		            <td id="SubMenu2" style="display:none">
			            <div class="ToolsSpan3" style="width: 158px">
				            <table cellspacing="3" cellpadding="0" width="130" align="center">
					            <tr>
						            <td><a href="#" target="Main" onclick="window.open('Article.aspx?Article_ClassID=1','Article','toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=1024px,height=700px');return false;" class="IndexMenuLink1">关于华捷</a></td>
						        </tr>
					            <tr>
						            <td><a href="#" target="Main" onclick="window.open('Article.aspx?Article_ClassID=2','Article','toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=1024px,height=700px');return false;" class="IndexMenuLink1">关于我们</a></td>
						        </tr>
						        <tr>
						            <td><a href="#" target="Main" onclick="window.open('Article.aspx?Article_ClassID=3','Article','toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes,width=1024px,height=700px');return false;" class="IndexMenuLink1">成为会员享受什么好处</a></td>
						        </tr>
						        <tr>
						            <td><a href="News.aspx?News_ClassID=1" target="Main" class="IndexMenuLink1">优惠资讯</a></td>
						        </tr>
						        <tr>
						            <td><a href="News.aspx?News_ClassID=2" target="Main" class="IndexMenuLink1">行业资讯</a></td>
						        </tr>
						        <tr>
						            <td><a href="News.aspx?News_ClassID=3" target="Main" class="IndexMenuLink1">表格下载</a></td>
						        </tr>
						        <tr>
						            <td><a href="Knows.aspx?Knows_ClassID=1" target="Main" class="IndexMenuLink1">旅游需知</a></td>
						        </tr>
						        <tr>
						            <td><a href="Travel.aspx" target="Main" class="IndexMenuLink1">旅游路线</a></td>
						        </tr>					            
				            </table>
			            </div>
			            <br />		
		            </td>
	            </tr>
	            <tr>
		            <td id="Menu3" class="ToolsSpan1" onmouseover="this.className='ToolsSpan2';" onclick="ShowSubMenu(3)" onmouseout="this.className='ToolsSpan1';" style="cursor:pointer" background="images/menudown.gif" height="25">
			            <span>会员管理</span>
		            </td>
	            </tr>
	            <tr>
		            <td id="SubMenu3" style="display:none">
			            <div class="ToolsSpan3" style="width: 158px">
				            <table cellspacing="3" cellpadding="0" width="130" align="center">					            
						        <tr>
						            <td><a href="Member.aspx" target="Main" class="IndexMenuLink1">会员信息</a></td>
						        </tr>
						        <tr>
						            <td><a href="LowFare.aspx" target="Main" class="IndexMenuLink1">机票问价</a></td>
						        </tr>						        					            
				            </table>
			            </div>
			            <br />		
		            </td>
	            </tr>
	            <tr>
                    <td><iframe src="RefreshSession.aspx" scrolling="no" width="0" height="0" frameborder="0"></iframe></td>
                </tr>
            </table>            
        </div>
    </form>
</body>
<script type="text/javascript">
function ShowSubMenu(ID)
{
	if ($("#SubMenu" + ID).css("display") == "none")
	{
		$("#SubMenu" + ID).css("display", "block");
		$("#Menu" + ID).attr("background", "images/menuup.gif");
	}
	else
	{
		$("#SubMenu" + ID).css("display", "none");
		$("#Menu" + ID).attr("background", "images/menudown.gif");
	}
}
</script>
</html>
