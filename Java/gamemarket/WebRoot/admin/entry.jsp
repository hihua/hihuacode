<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include/header.jsp"%>
<table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="Table1">
	<tr>
		<td align="center" height="60">
			渠道名称：<select id="entry_table"></select>&nbsp;
			开始日期：<input type="text" id="entry_start_date"/>&nbsp;
			结束日期：<input type="text" id="entry_end_date"/>&nbsp;
			<input type="button" value=" 查询 " onclick="queryEntry()" />
		</td>		
	</tr>
</table>
<table id="entrys" width="80%" align="center" cellpadding="0" cellspacing="0" border="1" class="Table1">
	<tr align="center" height="40">	
		<td width="20%">渠道ID</td>	
		<td width="20%">开通次数</td>
		<td width="20%">登录次数</td>
		<td width="20%">登录总数</td>
		<td width="20%">时间</td>		
	</tr>
</table>
<script type="text/javascript">
	var servlet = "entry";
	function getEntry() {
		var body = "command=0";
		request(servlet, body, onGetEntry);	
	}
	
	function onGetEntry(code, content) {
		var entryTable = $("#entry_table");
		entryTable.empty();
		
		if (code == 0) {			
			if (content != null) {
				var i = 0;
				$.each(content, function(idx, table) {
					var tableId = table.table_id;
					var tableName = table.table_name;
					
					if (i == 0)
						entryTable.append("<option value=\"" + tableId + "\" selected=\"selected\">" + tableName + "</option>");
					else
					 	entryTable.append("<option value=\"" + tableId + "\">" + tableName + "</option>");
					 	
					i++; 
				});
			}			
		}		
	}
	
	function queryEntry() {
		var date = new Date();
		var entryTable = $("#entry_table").val();
		var entryStartDate = $("#entry_start_date").val();
		var entryEndDate = $("#entry_end_date").val();
		
		if (entryTable.length == 0) {
			alert("请选择渠道名称");
			return;
		}
		
		if (entryStartDate.length == 0 && entryEndDate.length == 0) {
			alert("请输入开始或结束时间");
			return;		
		}
		
		if (entryStartDate.length > 0 && !checkDate(entryStartDate)) {
			$("#entry_start_date").focus();
			alert("开始日期请输入正确的日期格式，如" + date.pattern("yyyy-MM-dd"));
			return;	
		}
		
		if (entryEndDate.length > 0 && !checkDate(entryEndDate)) {
			$("#entry_end_date").focus();
			alert("结束日期请输入正确的日期格式，如" + date.pattern("yyyy-MM-dd"));
			return;	
		}
				
		var startDate = "";
		var endDate = "";
		
		if (entryStartDate.length > 0)
			startDate = getTimestamp(entryStartDate);
			
		if (entryEndDate.length > 0)
			endDate = getTimestamp(entryEndDate);
			
		if (entryStartDate.length > 0 && entryEndDate.length > 0 && startDate > endDate) {
			$("#entry_start_date").focus();
			alert("开始日期不能大于结束日期");
			return;		
		}						
			
		var body = "command=1&entry_table=" + entryTable + "&entry_start_date=" + startDate + "&entry_end_date=" + endDate;
		request(servlet, body, onQueryEntry);	
	}
	
	function onQueryEntry(code, content) {
		clearTable("#entrys");
		switch(code) {
			case 0: {
				if (content != null) {
					$.each(content, function(idx, entry) {
						var entryId = entry.entry_id;
						var entryTable = entry.entry_table;
						var entryOpen = entry.entry_open;
						var entryTimes = entry.entry_times;
						var entryTotal = entry.entry_total;
						var entryDate = new Date(entry.entry_date * 1000);
						
						var html = "<tr align=\"center\" height=\"40\">";					
						html += "<td><span>" + entryTable + "</span></td>";
						html += "<td><span>" + entryOpen + "</span></td>";
						html += "<td><span>" + entryTimes + "</span></td>";
						html += "<td><span>" + entryTotal + "</span></td>";
						html += "<td><span>" + entryDate.pattern("yyyy-MM-dd HH:mm:ss") + "</span></td>";
							
						$("#entrys").append(html);	
					});
				}				
			}
			break;
			
			case 1:
				alert("查询失败");
				break;					
			
			case 2:
				alert("查询失败,没有没有该渠道信息");
				break;
			
			case 3:
				alert("查询失败,没有权限");
				break;
			
			default:
				alert("查询失败");
				break;
		}
	}
	
	function setDate() {
		var date = new Date();		
		$("#entry_start_date").val(date.pattern("yyyy-MM-dd"));		
	}
			
	getEntry();
	setDate();
</script>
<%@ include file="include/bottom.jsp"%>