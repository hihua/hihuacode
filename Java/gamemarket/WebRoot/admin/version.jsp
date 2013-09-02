<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include/header.jsp"%>
<table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="Table1">
	<tr>
		<td align="center" height="60">			
			开始日期：<input type="text" id="version_start_date"/>&nbsp;
			结束日期：<input type="text" id="version_end_date"/>&nbsp;
			<input type="button" value=" 查询 " onclick="queryVersion(this)" />
		</td>		
	</tr>
</table>
<table id="versions" width="70%" align="center" cellpadding="0" cellspacing="0" border="1" class="Table1">
	<tr align="center" height="40">	
		<td width="25%">版本名称</td>		
		<td width="25%">登录次数</td>
		<td width="25%">登录总数</td>
		<td width="25%">时间</td>		
	</tr>
</table>
<script type="text/javascript">
	var servlet = "version";		
	function queryVersion(obj) {
		var date = new Date();		
		var versionStartDate = $("#version_start_date").val();
		var versionEndDate = $("#version_end_date").val();
				
		if (versionStartDate.length == 0 && versionEndDate.length == 0) {
			alert("请输入开始或结束时间");
			return;		
		}
		
		if (versionStartDate.length > 0 && !checkDate(versionStartDate)) {
			$("#version_start_date").focus();
			alert("开始日期请输入正确的日期格式，如" + date.pattern("yyyy-MM-dd"));
			return;	
		}
		
		if (versionEndDate.length > 0 && !checkDate(versionEndDate)) {
			$("#version_end_date").focus();
			alert("结束日期请输入正确的日期格式，如" + date.pattern("yyyy-MM-dd"));
			return;	
		}
				
		var startDate = "";
		var endDate = "";
		
		if (versionStartDate.length > 0)
			startDate = getTimestamp(versionStartDate);
			
		if (versionEndDate.length > 0)
			endDate = getTimestamp(versionEndDate);
			
		if (versionStartDate.length > 0 && versionEndDate.length > 0 && startDate > endDate) {
			$("#version_start_date").focus();
			alert("开始日期不能大于结束日期");
			return;		
		}						
			
		$(obj).attr("disabled", true);
		var body = "command=0&version_start_date=" + startDate + "&version_end_date=" + endDate;
		request(servlet, body, onQueryversion, obj);	
	}
	
	function onQueryversion(code, content, obj) {
		$(obj).attr("disabled", false);
		clearTable("#versions");		
		switch(code) {
			case 0: {
				if (content != null) {
					$.each(content, function(idx, version) {					
						var versionName = version.version_name;
						var versionTimes = version.version_times;
						var versionTotal = version.version_total;
						var versionDate = new Date(version.version_date * 1000);
						
						var html = "<tr align=\"center\" height=\"40\">";					
						html += "<td><span>" + versionName + "</span></td>";					
						html += "<td><span>" + versionTimes + "</span></td>";
						html += "<td><span>" + versionTotal + "</span></td>";
						html += "<td><span>" + versionDate.pattern("yyyy-MM-dd HH:mm:ss") + "</span></td>";
							
						$("#versions").append(html);	
					});
				}				
			}
			break;
			
			case 1:
				alert("查询失败");
				break;					
			
			default:
				alert("查询失败");
				break;
		}		
	}
	
	function setDate() {
		var date = new Date();		
		$("#version_start_date").val(date.pattern("yyyy-MM-dd"));		
	}
	
	setDate();
</script>
<%@ include file="include/bottom.jsp"%>