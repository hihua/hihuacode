<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include/header.jsp"%>
<table id="add" width="100%" align="center" cellpadding="0" cellspacing="0" border="1" class="Table1" style="display:none;">
	<tr>
		<td align="center" height="60">		
			用户名：<input type="text" id="admin_username" maxlength="20" />&nbsp;
			密码：<input type="password" id="admin_password" maxlength="20" />&nbsp;
			描述：<input type="text" id="admin_desc" maxlength="30" />&nbsp;
			<input type="button" value=" 提交 " onclick="add()" />			
		</td>		
	</tr>
</table>
<table id="admins" width="100%" align="center" cellpadding="0" cellspacing="0" border="1" class="Table1">
	<tr align="center" height="40">
		<td width="10%">用户名</td>
		<td width="25%">描述</td>
		<td width="40%">密码</td>
		<td width="15%">创建时间</td>
		<td width="10%">操作</td>
	</tr>
</table>
<script type="text/javascript">
	function getInfo() {
		var body = "command=0";
		request("admin", body, onInfo);
	}
	
	function onInfo(code, result) {
		if (code == 0) {		
			var adminParent = result.admin_parent;
						
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
			
		request("admin", body, onAdmins);
	}
	
	function clearTable() {
		var table = $("#admins");
		var trs = table.find("tr");
		var length = trs.length;
		while (length > 1) {			
			trs.eq(1).remove();
			trs = table.find("tr");
			length = trs.length;		
		}
	}
	
	function onAdmins(code, result) {
		if (code == 0) {
			clearTable();
			$.each(result, function(idx, admin) {
				var adminId = admin.admin_id;
				var adminUsername = admin.admin_username;
				var adminDesc = admin.admin_desc;
				var adminParent = admin.admin_parent;
				var adminDate = new Date(admin.admin_date * 1000);			
			
				var html = "";
				if (adminParent == 1)
					html += "<tr align=\"center\" height=\"40\" bgcolor=\"#cccccc\">";
				else
					html += "<tr align=\"center\" height=\"40\">";
					
				html += "<td><span>" + adminUsername + "</span></td>";
				html += "<td><input type=\"text\" id=\"admin_desc_" + adminId + "\" maxlength=\"30\" value=\"" + adminDesc + "\" />&nbsp;<input type=\"button\" value=\" 修改 \" onclick=\"modifyDesc(" + adminId + ")\" /></td>";
				html += "<td>新密码:<input type=\"password\" id=\"admin_password_" + adminId + "\" maxlength=\"20\" />&nbsp;<input type=\"button\" value=\" 修改 \" onclick=\"modifyPassword(" + adminId + ")\" /></td>";
				html +=	"<td><span>" + adminDate.pattern("yyyy-MM-dd HH:mm:ss") + "</span></td>";
				html +=	"<td><input type=\"button\" value=\" 删除 \" onclick=\"del(" + adminId + ")\" /></td>";		
				$("#admins").append(html);			
			});		
		}
	}
	
	function add() {		
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
			request("admin", body, onAdd);			
		}		
	}
	
	function onAdd(code, result) {
		switch (code) {
			case 0:
				getInfo();
				alert("添加成功");
				break;
				
			case 1:
				alert("添加失败");
				break;
				
			case 2:
				alert("添加失败,已经有相同用户名");
				break;
				
			default:
				alert("添加失败");
				break;
		}				
	}
	
	function del(adminId) {		
		if (window.confirm("是否删除")) {
			var body = "command=4&admin_id=" + adminId;
			request("admin", body, onDel);
		}
	}
	
	function onDel(code, result) {
		if (code == 0) {
			getInfo();
			alert("删除成功");
		}
	}
	
	function modifyDesc(adminId) {
		if (window.confirm("是否修改")) {
			var desc = $("#admin_desc_" + adminId).val();
			if (desc.length > 0) {
				var body = "command=5&admin_id=" + adminId + "&admin_desc=" + encodeURIComponent(desc);
				request("admin", body, onModifyDesc);
			} else
				alert("请输入描述");
		}
	}
	
	function onModifyDesc(code, result) {
		getInfo();
		if (code == 0)
			alert("修改成功");	
		else	
			alert("修改失败");	
	}
	
	function modifyPassword(adminId) {
		if (window.confirm("是否修改")) {			
			var adminPassword = $("#admin_password_" + adminId).val();
			if (adminPassword.length > 0) {
				var body = "command=8&admin_id=" + adminId + "&admin_password=" + encodeURIComponent(adminPassword);
				request("admin", body, onModifyPassword);
			} else
				alert("请输入新密码");
		}
	}
	
	function onModifyPassword(code, result) {
		switch (code) {
			case 0:
				alert("修改成功");
				break;
				
			case 1:
				alert("修改失败");
				break;
			
			case 2:
				alert("旧密码不能为空");				
				break;			
						
			case 3:
				alert("旧密码不正确");				
				break;
				
			case 4:
				alert("没有权限");				
				break;
				
			default:
				alert("修改失败");
				break;
		}
	}
	
	getInfo();
</script>
<%@ include file="include/bottom.jsp"%>