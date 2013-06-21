<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include/header.jsp"%>
<table id="table1" align="left" cellspacing="0" cellpadding="0" width="158">
	<tr>
		<td><img width="158" height="38" src="images/title.gif" alt="" /></td>
	</tr>
	<tr>
		<td class="ToolsSpan1" onmouseover="this.className='ToolsSpan2'" onmouseout="this.className='ToolsSpan1'" background="images/title_bg_quit.gif" height="25">			 
			<span style="color:#215DC6; font-weight:bold; cursor:pointer" onclick="window.top.location.href='logout.jsp'">退出</span>
		</td>
	</tr>
	<tr>
		<td height="25">&nbsp;</td>
	</tr>
	<tr>
		<td id="menu1" class="ToolsSpan1" onmouseover="this.className='ToolsSpan2'" onclick="showSubMenu(1)" onmouseout="this.className='ToolsSpan1'" style="cursor:pointer" background="images/menudown.gif" height="25">
			<span>用户管理</span>
		</td>
	</tr>
	<tr>
		<td id="submenu1" style="display:none">
			<div class="ToolsSpan3" style="width:158px">
				<table id="user" cellspacing="3" cellpadding="0" width="130" align="center">
					<tr>
						<td><a href="info.jsp" target="main" class="IndexMenuLink1">用户信息</a></td>
					</tr>										
				</table>
			</div> 
			<br />
		</td>
	</tr>
	<tr>
		<td id="menu2" class="ToolsSpan1" onmouseover="this.className='ToolsSpan2'" onclick="showSubMenu(1)" onmouseout="this.className='ToolsSpan1'" style="cursor:pointer" background="images/menudown.gif" height="25">
			<span>应用管理</span>
		</td>
	</tr>
	<tr>
		<td id="submenu2" style="display:none">
			<div class="ToolsSpan3" style="width:158px">
				<table cellspacing="3" cellpadding="0" width="130" align="center">
					<tr>
						<td><a href="packet.jsp" target="main" class="IndexMenuLink1">在线打包</a></td>
					</tr>
					<tr>
						<td><a href="statistics.jsp" target="main" class="IndexMenuLink1">统计信息</a></td>
					</tr>										
				</table>
			</div> 
			<br />
		</td>
	</tr>
	<tr>
		<td><iframe src="refresh.jsp" scrolling="no" width="0" height="0" frameborder="0"></iframe></td>
	</tr>
</table>
<script type="text/javascript">
	function showSubMenu(id) {
		if ($("#submenu" + id).css("display") == "none") {
			$("#submenu" + id).css("display", "block");
			$("#menu" + id).attr("background", "images/menuup.gif");
		} else {
			$("#submenu" + id).css("display", "none");
			$("#menu" + id).attr("background", "images/menudown.gif");
		}
	}
	
	function getInfo() {
		var body = "command=0";
		request("admin", body, onInfo);
	}
	
	function onInfo(code, result) {
		if (code == 0) {		
			var adminParent = result.admin_parent;
						
			if (adminParent == 0 || adminParent == 1)
				showMenu();
		}
	}
	
	function clearTable() {
		var table = $("#user");
		var trs = table.find("tr");
		var length = trs.length;
		while (length > 1) {			
			trs.eq(1).remove();
			trs = table.find("tr");
			length = trs.length;		
		}
	}
	
	function showMenu() {
		clearTable();	
		var html = "<tr><td><a href=\"user.jsp\" target=\"main\" class=\"IndexMenuLink1\">用户管理</a></td></tr>";
		$("#user").append(html);
	}
		
	$(document.body).css("background-color", "#799AE1");
	getInfo();
</script>
<%@ include file="include/bottom.jsp"%>