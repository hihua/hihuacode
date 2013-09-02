<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include/header.jsp"%>
<form method="post" style="margin: 0px" enctype="multipart/form-data" onsubmit="return addUpgrade(this);">
	<table id="add" width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="Table1">
		<tr>
			<td align="center" height="60">
				渠道名称：<select id="upgrade_table" name="upgrade_table"></select>&nbsp;
				强制更新：<input type="checkbox" id="upgrade_force" name="upgrade_force" value="1"/>&nbsp;
				选择文件：<input type="file" id="upgrade_filename" name="upgrade_filename"/>&nbsp;
				<input type="submit" value=" 添加 " />			
			</td>		
		</tr>
	</table>
</form>
<table id="upgrades" width="100%" align="center" cellpadding="0" cellspacing="0" border="1" class="Table1">
	<tr align="center" height="40">
		<td width="13%">用户名</td>
		<td width="8%">渠道ID</td>
		<td width="15%">渠道名称</td>
		<td width="7%">当前版本ID</td>
		<td width="13%">当前版本名称</td>
		<td width="8%">文件大小</td>
		<td width="13%">更新时间</td>		
		<td width="6%">强制更新</td>		
		<td width="17%">操作</td>				
	</tr>
</table>
<script type="text/javascript">
	var servlet = "upgrade";
	function getUpgrade() {
		var body = "command=3";
		request(servlet, body, onGetUpgrade, null);
	}
	
	function onGetUpgrade(code, content, obj) {
		var upgradeTable = $("#upgrade_table");
		upgradeTable.empty();
		
		switch (code) {
			case 0: {
				if (content != null) {
					var i = 0;
					$.each(content, function(idx, table) {
						var tableId = table.table_id;
						var tableName = table.table_name;
						
						if (i == 0)
							upgradeTable.append("<option value=\"" + tableId + "\" selected=\"selected\">" + tableName + "</option>");
						else
						 	upgradeTable.append("<option value=\"" + tableId + "\">" + tableName + "</option>");
						 	
						i++; 
					});
				}	
			}
			break;
		}		
	}
	
	function getUpgrades() {
		var body = "command=2";
		request(servlet, body, onGetUpgrades, null);
	}
	
	function onGetUpgrades(code, content, obj) {
		clearTable("#upgrades");
		switch (code) {
			case 0: {
				if (content != null) {
					$.each(content, function(idx, upgrade) {
						var adminUsername = upgrade.admin_username;
						var adminDesc = upgrade.admin_desc;
						var tableId = upgrade.table_id;
						var tableName = upgrade.table_name;
						var upgradeId = upgrade.upgrade_id;
						var upgradeVersionCode = upgrade.upgrade_version_code;
						var upgradeVersionName = upgrade.upgrade_version_name;
						var upgradeDate = new Date(upgrade.upgrade_date * 1000);
						var upgradeFilename = upgrade.upgrade_filename;
						var upgradeFileSize = upgrade.upgrade_filesize;
						upgradeFileSize = upgradeFileSize / 1024.0;
						upgradeFileSize = upgradeFileSize.toFixed(0);
						var upgradeForce = upgrade.upgrade_force;
						
						var html = "<tr align=\"center\" height=\"40\">";
						html += "<td><span>" + adminUsername + "</span></td>";				
						html += "<td><span>" + tableId + "</span></td>";
						html += "<td><span>" + tableName + "</span></td>";
						html += "<td><span>" + upgradeVersionCode + "</span></td>";
						html += "<td><span>" + upgradeVersionName + "</span></td>";	
						html += "<td><span>" + upgradeFileSize + "K</span></td>";	
						html += "<td><span>" + upgradeDate.pattern("yyyy-MM-dd HH:mm:ss") + "</span></td>";	
						
						if (upgradeForce == 1)
							html += "<td><input type=\"checkbox\" id=\"upgrade_force_" + tableId + "\" name=\"upgrade_force_" + tableId + "\" checked=\"checked\"/></td>";
						else
							html += "<td><input type=\"checkbox\" id=\"upgrade_force_" + tableId + "\" name=\"upgrade_force_" + tableId + "\"/></td>";
							
						html += "<td>";
						if (upgradeFilename != null && upgradeFilename.length > 0)
							html += "<input type=\"button\" value=\" 下载\" onclick=\"download('" + upgradeFilename + "')\" />&nbsp;";				
						
						html += "<input type=\"button\" value=\" 修改\" onclick=\"updateForce(this, " + tableId + ")\" />";
						
						if (tableId == 0)
							html += "&nbsp;";
						else
							html += "&nbsp;<input type=\"button\" value=\" 删除 \" onclick=\"delUpgrade(this, " + tableId + ")\" />";
						
						html += "</td>";
						$("#upgrades").append(html);
					});
				}
			}
			break;					
		}			
	}
	
	function addUpgrade(form) {
		var upgradeTable = $(form.upgrade_table).val();
		var upgradeFilename = $(form.upgrade_filename).val();
		var upgradeForce = $(form.upgrade_force);
				
		if (window.confirm("是否添加")) {
			if (upgradeTable.length == 0) {
				alert("请选择渠道名称");
				return false;			
			}
			
			if (upgradeFilename.length == 0) {
				alert("请选择文件名称");
				return false;			
			}
			
			var url = servlet + "?command=0";									
			upload(url, form, onAddUpgrade);			
			return false;			
		} else
			return false;
	}
	
	function onAddUpgrade(code, content) {		
		switch (code) {
			case 0:
				getUpgrade();
				getUpgrades();
				alert("添加成功");
				break;
				
			case 1:
				alert("添加失败");
				break;
				
			case 2:
				alert("添加失败,上传文件失败");
				break;
				
			case 3:
				alert("添加失败,参数错误");
				break;
				
			case 4:
				alert("添加失败,没有该渠道信息");
				break;
				
			case 5:
				alert("添加失败,没有权限");
				break;
				
			default:
				alert("添加失败" + code);
				break;
		}	
	}
	
	function delUpgrade(obj, tableId) {
		if (window.confirm("是否删除")) {
			$(obj).attr("disabled", true);
			var body = "command=1&upgrade_table=" + tableId;
			request(servlet, body, onDelUpgrade, obj);	
		}		
	}
	
	function onDelUpgrade(code, content, obj) {
		$(obj).attr("disabled", false);
		switch (code) {
			case 0:
				getUpgrade();
				getUpgrades();
				alert("删除成功");
				break;
				
			case 1:
				alert("删除失败");
				break;
				
			case 2:
				alert("删除失败,没有该渠道信息");
				break;	
				
			case 3:
				alert("删除失败,没有权限");
				break;
				
			default:
				alert("删除失败" + code);
				break;
		}		
	}
	
	function updateForce(obj, tableId) {
		if (window.confirm("是否修改")) {
			var upgradeForce = 0;
			if ($("#upgrade_force_" + tableId).attr("checked"))
				upgradeForce = 1;
	
			$(obj).attr("disabled", true);
			var body = "command=4&upgrade_table=" + tableId + "&upgrade_force=" + upgradeForce;
			request(servlet, body, onUpdateForce, obj);	
		}		
	}
	
	function onUpdateForce(code, content, obj) {
		$(obj).attr("disabled", false);
		switch (code) {
			case 0:
				getUpgrade();
				getUpgrades();
				alert("修改成功");
				break;
				
			case 1:
				alert("修改失败");
				break;
				
			case 2:
				alert("修改失败,没有权限");
				break;
				
			case 3:
				alert("修改失败,没有该渠道信息");
				break;							
				
			default:
				alert("修改失败" + code);
				break;
		}	
	}
	
	function download(filename) {
		var url = "../" + servlet + "/" + filename;	
		window.open(url);			
	}
	
	getUpgrade();
	getUpgrades();
</script>
<%@ include file="include/bottom.jsp"%>