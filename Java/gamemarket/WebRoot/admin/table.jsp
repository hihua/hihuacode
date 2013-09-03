<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include/header.jsp"%>
<table id="add" width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="Table1">
	<tr>
		<td align="center" height="60">			
			渠道名称：<input type="text" id="table_name" maxlength="30" />&nbsp;
			<input type="button" value=" 添加 " onclick="addTable(this)" />
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
	var packet = "packet";
	function getTables() {
		var body = "command=1";
		request(servlet, body, onGetTables, null);	
	}
	
	function onGetTables(code, content, obj) {
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
							html += "<td><input type=\"button\" value=\" 打包 \" onclick=\"addPacket(this, " + tableId + ")\" /></td>";
						else
							html += "<td><input type=\"button\" value=\" 打包 \" onclick=\"addPacket(this, " + tableId + ")\" />&nbsp;<input type=\"button\" value=\" 删除 \" onclick=\"delTable(this, " + tableId + ")\" /></td>";
							
						$("#tables").append(html);	
					});
				}
			}
			break;
		}
	}
	
	function addTable(obj) {
		var tableName = $("#table_name").val();
		if (window.confirm("是否添加")) {
			if (tableName.length == 0) {
				alert("请输入渠道名称");
				return;			
			}
			
			$(obj).attr("disabled", true);
			var body = "command=0&table_name=" + encodeURIComponent(tableName);
			request(servlet, body, onAddTable, obj);
		}
	}
	
	function onAddTable(code, content, obj) {
		$(obj).attr("disabled", false);
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
	
	function delTable(obj, tableId) {
		if (window.confirm("是否删除")) {
			$(obj).attr("disabled", true);
			var body = "command=2&table_id=" + tableId;
			request(servlet, body, onDelTable, obj);
		}		
	}
	
	function onDelTable(code, content, obj) {
		$(obj).attr("disabled", false);
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
	
	function addPacket(obj, tableId) {
		if (window.confirm("是否打包")) {
			$(obj).attr("disabled", true);
			var body = "command=0&table_id=" + tableId;
			request(packet, body, onAddPacket, obj);
		}
	}
	
	function onAddPacket(code, content, obj) {
		$(obj).attr("disabled", false);
		switch (code) {
			case 0:
				alert("打包成功");
				break;
			
			case 1:
				alert("打包失败,参数错误");
				break;
				
			case 2:
				alert("打包失败,没有该渠道信息");
				break;
				
			case 3:
				alert("打包失败,没有权限");
				break;
				
			case 4:
				alert("打包失败,应用正在升级");
				break;
				
			case 5:				
				alert("打包失败,创建文件夹失败");
				break;
				
			case 6:
				alert("打包失败,复制文件夹失败");
				break;
				
			case 7:
				alert("打包失败,没找到渠道名称文件");
				break;
				
			case 8:
				alert("打包失败,修改渠道名称失败");
				break;
				
			case 9:
				alert("打包失败,调用脚本失败");
				break;
				
			case 10:
				alert("打包失败,执行脚本失败");
				break;
				
			case 11:				
				alert("打包失败,复制文件失败");
				break;
				
			case 12:				
				alert("打包失败,更新数据失败");
				break;
				
			default:
				alert("打包失败" + code);
				break;
		}
	}
	
	getTables();
</script>
<%@ include file="include/bottom.jsp"%>