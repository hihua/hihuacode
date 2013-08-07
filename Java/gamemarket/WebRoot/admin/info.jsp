<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include/header.jsp"%>
<table width="100%" align="center" cellpadding="0" cellspacing="0" border="1" class="Table1">
	<tr align="center" height="40">
		<td width="10%">用户名</td>
		<td width="25%">描述</td>
		<td width="50%">密码</td>
		<td width="15%">创建时间</td>
	</tr>
	<tr align="center" height="40">
		<td><span id="admin_username"></span></td>
		<td><input type="text" id="admin_desc" maxlength="30" />&nbsp;<input type="button" value=" 修改 " onclick="modifyDesc()" /></td>
		<td>原密码:<input type="password" id="admin_oldpassword" maxlength="20" />&nbsp;新密码:<input type="password" id="admin_password" maxlength="20" />&nbsp;<input type="button" value=" 修改 " onclick="modifyPassword()" /></td>
		<td><span id="admin_date"></span></td>
	</tr>
</table>
<script type="text/javascript">
	var servlet = "admin";
	var adminId = 0;
	function getInfo() {
		var body = "command=0";
		request(servlet, body, onInfo);
	}
	
	function onInfo(code, content) {
		switch (code) {
			case 0: {
				if (content != null) {
					adminId = content.admin_id;
					var adminUsername = content.admin_username;
					var adminDesc = content.admin_desc;
					var adminDate = new Date(content.admin_date * 1000);
					
					$("#admin_username").text(adminUsername);
					$("#admin_desc").val(adminDesc);
					$("#admin_date").text(adminDate.pattern("yyyy-MM-dd HH:mm:ss"));
				}
			}
			break;
			
			default: {
				$("#admin_username").text("");
				$("#admin_desc").val("");
				$("#admin_date").text("");
			}
			break;		
		}
	}
	
	function modifyDesc() {
		if (window.confirm("是否修改")) {
			var desc = $("#admin_desc").val();
			if (desc.length > 0) {
				var body = "command=5&admin_id=" + adminId + "&admin_desc=" + encodeURIComponent(desc);
				request(servlet, body, onModifyDesc);
			} else
				alert("请输入描述");		
		}
	}
	
	function onModifyDesc(code, content) {
		switch (code) {
			case 0:
				getInfo();
				alert("修改成功");
				break;
				
			case 1:
				alert("修改失败");
				break;
				
			case 2:
				alert("修改失败,没有权限");
				break;
				
			default:
				alert("修改失败");
				break;		
		}	
	}
	
	function modifyPassword() {
		if (window.confirm("是否修改密码")) {
			var adminOldPassword = $("#admin_oldpassword").val();
			var adminPassword = $("#admin_password").val();
			if (adminOldPassword.length > 0) {
				if (adminPassword.length > 0) {
					var body = "command=8&admin_id=" + adminId + "&admin_oldpassword=" + encodeURIComponent(adminOldPassword) + "&admin_password=" + encodeURIComponent(adminPassword);
					request(servlet, body, onModifyPassword);
				} else
					alert("请输入新密码");				
			} else
				alert("请输入旧密码");		
		}
	}
	
	function onModifyPassword(code, content) {
		switch (code) {
			case 0:
				alert("修改成功");
				break;
				
			case 1:
				alert("修改失败");
				break;
			
			case 2:
				alert("修改失败,没有权限");				
				break;			
						
			case 3:
				alert("修改失败,旧密码不正确");				
				break;
				
			case 4:
				alert("修改失败,旧密码不能为空");				
				break;
				
			case 5:
				alert("修改失败,没该用户");				
				break;
				
			default:
				alert("修改失败");
				break;
		}
	}
	
	getInfo();
</script>
<%@ include file="include/bottom.jsp"%>