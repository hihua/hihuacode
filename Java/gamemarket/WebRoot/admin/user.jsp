<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include/header.jsp"%>
<table id="add" width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="Table1" style="display:none;">
	<tr>
		<td align="center" height="60">		
			用户名：<input type="text" id="admin_username" maxlength="20" />&nbsp;
			密码：<input type="password" id="admin_password" maxlength="20" />&nbsp;
			描述：<input type="text" id="admin_desc" maxlength="30" />&nbsp;
			<input type="button" value=" 提交 " onclick="addAdmin()" />			
		</td>		
	</tr>
</table>
<table id="admins" width="100%" align="center" cellpadding="0" cellspacing="0" border="1" class="Table1">
	<tr align="center" height="40">
		<td width="10%">用户名</td>
		<td width="25%">描述</td>
		<td width="10%">身份</td>
		<td width="30%">密码</td>
		<td width="15%">创建时间</td>
		<td width="10%">操作</td>
	</tr>
</table>
<script type="text/javascript">
	var servlet = "admin";
	function getInfo() {
		var body = "command=0";
		request(servlet, body, onGetInfo);
	}
	
	function onGetInfo(code, content) {
		if (code == 0) {		
			var adminParent = content.admin_parent;
						
			if (adminParent == 0 || adminParent == 1) {
				getAdmins(adminParent);
				$("#add").show();
			}							
		}
	}
	
	function getAdmins(adminParent) {
		var body = "";
		if (adminParent == 0)
			body = "command=3";
		else
			body = "command=7";		
			
		request(servlet, body, onGetAdmins);
	}
		
	function onGetAdmins(code, content) {
		clearTable("#admins");
		switch (code) {
			case 0: {
				$.each(content, function(idx, admin) {
					var adminId = admin.admin_id;
					var adminUsername = admin.admin_username;
					var adminDesc = admin.admin_desc;
					var adminParent = admin.admin_parent;
					var adminDate = new Date(admin.admin_date * 1000);			
				
					var html = "";
					if (adminParent == 1)
						html += "<tr align=\"center\" height=\"40\" bgcolor=\"#F0F0F0\">";
					else
						html += "<tr align=\"center\" height=\"40\">";
						
					html += "<td><span>" + adminUsername + "</span></td>";
					html += "<td><input type=\"text\" id=\"admin_desc_" + adminId + "\" maxlength=\"30\" value=\"" + adminDesc + "\" />&nbsp;<input type=\"button\" value=\" 修改 \" onclick=\"modifyDesc(" + adminId + ")\" /></td>";
					if (adminParent == 1)
						html += "<td><span>管理员</span></td>";
					else
						html += "<td><span>普通用户</span></td>";					
					
					html += "<td>新密码:<input type=\"password\" id=\"admin_password_" + adminId + "\" maxlength=\"20\" />&nbsp;<input type=\"button\" value=\" 修改 \" onclick=\"modifyPassword(" + adminId + ")\" /></td>";
					html +=	"<td><span>" + adminDate.pattern("yyyy-MM-dd HH:mm:ss") + "</span></td>";
					html +=	"<td><input type=\"button\" value=\" 删除 \" onclick=\"delAdmin(" + adminId + ")\" /></td>";		
					$("#admins").append(html);			
				});	
			}
			break;
			
			case 1:
				alert("查询失败");
				break;
				
			case 2:				
				alert("查询失败,没有权限");
				break;
				
			default:
				alert("查询失败");
				break;						
		}	
	}
	
	function addAdmin() {		
		var adminUsername = $("#admin_username").val();
		var adminPassword = $("#admin_password").val();
		var adminDesc = $("#admin_desc").val();
		
		if (window.confirm("是否添加")) {
			if (adminUsername.length == 0) {
				alert("请输入用户名");
				return;			
			}
			
			if (adminPassword.length == 0) {
				alert("请输入密码");
				return;			
			}
			
			var body = "command=6&admin_username=" + encodeURIComponent(adminUsername) + "&admin_password=" + encodeURIComponent(adminPassword) + "&admin_desc=" + encodeURIComponent(adminDesc);
			request(servlet, body, onAddAdmin);			
		}		
	}
	
	function onAddAdmin(code, content) {
		switch (code) {
			case 0:
				getInfo();
				alert("添加成功");
				break;
				
			case 1:
				alert("添加失败");
				break;
				
			case 2:
				alert("添加失败,没有权限");
				break;
				
			case 3:
				alert("添加失败,已经有相同用户名");
				break;
				
			default:
				alert("添加失败");
				break;
		}				
	}
	
	function delAdmin(adminId) {		
		if (window.confirm("是否删除")) {
			var body = "command=4&admin_id=" + adminId;
			request(servlet, body, onDelAdmin);
		}
	}
	
	function onDelAdmin(code, content) {
		switch (code) {
			case 0:
				getInfo();
				alert("删除成功");
				break;
				
			case 1:
				alert("删除失败");
				break;
				
			case 2:
				alert("删除失败,没有权限");
				break;
				
			case 3:
				alert("删除失败,没该用户");
				break;
				
			default:
				alert("删除失败");
				break;		
		}	
	}
	
	function modifyDesc(adminId) {
		if (window.confirm("是否修改")) {
			var desc = $("#admin_desc_" + adminId).val();
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
				
			case 3:
				alert("修改失败,没该用户");
				break;
				
			default:
				alert("修改失败");
				break;		
		}		
	}
	
	function modifyPassword(adminId) {
		if (window.confirm("是否修改")) {			
			var adminPassword = $("#admin_password_" + adminId).val();
			if (adminPassword.length > 0) {
				var body = "command=8&admin_id=" + adminId + "&admin_password=" + encodeURIComponent(adminPassword);
				request(servlet, body, onModifyPassword);
			} else
				alert("请输入新密码");
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