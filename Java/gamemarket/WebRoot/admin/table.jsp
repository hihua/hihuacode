<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include/header.jsp"%>
<table id="add" width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="Table1">
	<tr>
		<td align="center" height="60">			
			渠道名称：<input type="text" id="table_name" maxlength="30" />&nbsp;
			<input type="button" value=" 添加 " onclick="addTable()" />
		</td>		
	</tr>
</table>
<table id="tables" width="75%" align="center" cellpadding="0" cellspacing="0" border="1" class="Table1">
	<tr align="center" height="40">
		<td width="15%">用户名</td>
		<td width="20%">用户描述</td>
		<td width="10%">渠道ID</td>
		<td width="20%">渠道名称</td>
		<td width="15%">渠道创建时间</td>
		<td width="20%">操作</td>				
	</tr>
</table>
<script type="text/javascript">
	var servlet = "table";
	function getTables() {
		var body = "command=1";
		request(servlet, body, onGetTables);	
	}
	
	function onGetTables(code, content) {
		clearTable("#tables");
		switch (code) {
			case 0: {
				if (content != null) {
					$.each(content, function(idx, table) {
						var adminUsername = table.admin_username;
						var adminDesc = table.admin_desc;
						var tableId = table.table_id;
						var tableName = table.table_name;
						var tableDate = new Date(table.table_date * 1000);
						
						var html = "<tr align=\"center\" height=\"40\">";					
						html += "<td><span>" + adminUsername + "</span></td>";
						html += "<td><span>" + adminDesc + "</span></td>";
						html += "<td><span>" + tableId + "</span></td>";
						html += "<td><span>" + tableName + "</span></td>";
						html += "<td><span>" + tableDate.pattern("yyyy-MM-dd HH:mm:ss") + "</span></td>";
						
						if (tableId == 0)
							html += "<td>&nbsp;</td>";
						else
							html += "<td><input type=\"button\" value=\" 删除 \" onclick=\"delTable(" + tableId + ")\" /></td>";
							
						$("#tables").append(html);	
					});
				}
			}
			break;
		}
	}
	
	function addTable() {
		var tableName = $("#table_name").val();
		if (window.confirm("是否添加")) {
			if (tableName.length == 0) {
				alert("请输入渠道名称");
				return;			
			}
			
			var body = "command=0&table_name=" + encodeURIComponent(tableName);
			request(servlet, body, onAddTable);
		}
	}
	
	function onAddTable(code, content) {
		switch (code) {
			case 0:
				getTables();
				alert("添加成功");
				break;
			
			case 1:				
				alert("添加失败");
				break;
				
			case 2:				
				alert("添加失败,请输入渠道名称");
				break;
				
			default:
				alert("添加失败" + code);
				break;
		}			
	}
	
	function delTable(tableId) {
		if (window.confirm("是否删除")) {
			var body = "command=2&table_id=" + tableId;
			request(servlet, body, onDelTable);
		}		
	}
	
	function onDelTable(code, content) {
		switch (code) {
			case 0:
				getTables();
				alert("删除成功");
				break;
			
			case 1:				
				alert("删除失败");
				break;
				
			case 2:				
				alert("删除失败,没有权限");
				break;
				
			case 3:				
				alert("删除失败,没有该渠道信息");
				break;
				
			default:
				alert("删除失败" + code);
				break;
		}			
	}
	
	getTables();
</script>
<%@ include file="include/bottom.jsp"%>