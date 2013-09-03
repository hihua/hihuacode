<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include/header.jsp"%>
<table id="add" width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="Table1" style="display:none;">
	<tr>
		<td align="center" height="60">
			版本ID：<input type="text" id="version_code" maxlength="8" size="10" />&nbsp;
			版本名称：<input type="text" id="version_name" maxlength="8" size="10" />&nbsp;
			当前状态：<span id="packet_status" style="color:#000000"></span>&nbsp;							
			<input type="button" id="packet_button" value=" 打包 " disabled="disabled" onclick="addPacket(this)" />&nbsp;&nbsp;
			<a href="" id="packet_download" class="AdminToolsLink2" style="display:none;"></a>&nbsp;&nbsp;
			<input type="button" id="packet_sync" value=" 同步 " disabled="disabled" onclick="syncPacket(this)" />	
		</td>		
	</tr>
</table>
<table id="upgrades" width="100%" align="center" cellpadding="0" cellspacing="0" border="1" class="Table1">
	<tr align="center" height="40">
		<td width="5%">全选<input type="checkbox" id="select_all" name="select_all" onclick="selectall(this)" /></td>
		<td width="13%">用户名</td>
		<td width="8%">渠道ID</td>
		<td width="15%">渠道名称</td>
		<td width="7%">当前版本ID</td>
		<td width="10%">当前版本名称</td>
		<td width="8%">文件大小</td>
		<td width="13%">更新时间</td>		
		<td width="6%">强制更新</td>
		<td width="15%">操作</td>				
	</tr>
</table>
<script type="text/javascript">
	var servlet = "upgrade";
	var packet = "packet";
	function getPacket() {
		var body = "command=1";
		request(packet, body, onGetPacket, null);
	}
	
	function onGetPacket(code, content, obj) {				
		switch (code) {
			case 0: {
				if (content != null) {
					var versionCode = content.version_code;
					var versionName = content.version_name;
					var packetStatus = content.packet_status;
					var packetFilename = content.packet_filename;
					var packetLastTime = content.packet_lasttime;
															
					switch (packetStatus) {
						case 0: {
							$("#version_code").val("");
							$("#version_name").val("");							
							$("#packet_status").text("正常");						
							$("#packet_download").hide();
							$("#packet_button").attr("disabled", false);
							$("#packet_sync").attr("disabled", true);
						}
						break;
						
						case 1: {
							$("#version_code").val(versionCode);
							$("#version_name").val(versionName);							
							$("#packet_status").text("打包中...");
							$("#packet_download").hide();
							$("#packet_button").attr("disabled", false);
							$("#packet_sync").attr("disabled", true);
						}
						break;
						
						case 2: {
							$("#version_code").val(versionCode);
							$("#version_name").val(versionName);							
							$("#packet_status").text("打包完成，未同步");
							if (packetFilename != null && packetLastTime != null) {
								var packetDate = new Date(packetLastTime * 1000);	
								$("#packet_download").attr("href", "../upgrade/" + packetFilename);
								$("#packet_download").html("下载：" + packetDate.pattern("yyyy-MM-dd HH:mm:ss"));
								$("#packet_download").show();								
							}
							
							$("#packet_button").attr("disabled", false);
							$("#packet_sync").attr("disabled", false);
						}
						break;
						
						case 3: {
							$("#version_code").val(versionCode);
							$("#version_name").val(versionName);							
							$("#packet_status").text("同步中...");
							if (packetFilename != null && packetLastTime != null) {
								var packetDate = new Date(packetLastTime * 1000);	
								$("#packet_download").attr("href", "../upgrade/" + packetFilename);
								$("#packet_download").html("下载：" + packetDate.pattern("yyyy-MM-dd HH:mm:ss"));
								$("#packet_download").show();							
							}
							
							$("#packet_button").attr("disabled", true);
							$("#packet_sync").attr("disabled", false);
						}
						break;
					}
				}	
			}
			break;
		}		
	}
	
	function addPacket(obj) {
		if (window.confirm("是否打包")) {
			var versionCode = $("#version_code").val();
			var versionName = $("#version_name").val();
			
			if (versionCode.length == 0) {
				alert("请输入版本ID");
				return;			
			}
			
			if (versionName.length == 0) {
				alert("请输入版本名称");
				return;			
			}
			
			$(obj).attr("disabled", true);
			var body = "command=2&version_code=" + versionCode + "&version_name=" + encodeURIComponent(versionName);
			request(packet, body, onAddPacket, obj);
		}		
	}
	
	function onAddPacket(code, content, obj) {
		$(obj).attr("disabled", false);
		getPacket();
		switch (code) {
			case 0:
				alert("打包成功");
				break;	
			
			case 1:				
				alert("打包失败,参数错误");
				break;
			
			case 2:
				alert("打包失败,没有权限");
				break;
				
			case 3:
				alert("打包失败,系统错误");
				break;
				
			case 4:
				alert("打包失败,同步中不能打包");				
				break;
				
			case 5:
				alert("打包失败,创建文件夹失败");
				break;
				
			case 6:
				alert("打包失败,更新状态失败");
				break;
				
			case 7:
				alert("打包失败,创建项目文件夹失败");
				break;
				
			case 8:
				alert("打包失败,复制文件夹失败");
				break;
				
			case 9:
				alert("打包失败,没找到渠道名称文件");
				break;
				
			case 10:
				alert("打包失败,修改渠道名称失败");
				break;
				
			case 11:
				alert("打包失败,没找到版本文件");
				break;
				
			case 12:
				alert("打包失败,修改版本失败");
				break;
				
			case 13:
				alert("打包失败,调用脚本失败");
				break;
				
			case 14:
				alert("打包失败,执行脚本失败");
				break;
				
			case 15:
				alert("打包失败,复制文件失败");
				break;
				
			case 16:
				alert("打包失败,更新数据失败");
				break;
				
			default:
				alert("打包失败" + code);
				break;
		}
	}
	
	function syncPacket(obj) {
		if (window.confirm("是否同步")) {
			var upgradeTables = "";
			var checkboxs = $("input[name='select_upgrade']:checked");
		    $.each(checkboxs, function(idx, checkbox) {
		    	upgradeTables += "," + $(checkbox).val();		    			    
		    });
		    
		    if (upgradeTables.length == 0) {
		    	alert("请选择要同步的渠道");
				return;
		    }
		    
		    upgradeTables = upgradeTables.substring(1);
		    		    
			$(obj).attr("disabled", true);
			var body = "command=3&upgrade_tables=" + upgradeTables;
			request(packet, body, onSyncPacket, obj);
		}		
	}
	
	function onSyncPacket(code, content, obj) {
		$(obj).attr("disabled", false);
		$("#select_all").attr("checked", false);
		getPacket();
		switch (code) {
			case 0:
				getUpgrades();
				alert("同步成功");
				break;	
			
			case 1:				
				alert("同步失败,参数错误");
				break;
			
			case 2:
				alert("同步失败,没有权限");
				break;
				
			case 3:
				alert("同步失败,打包中或同步中");
				break;
				
			case 4:
				alert("同步失败,创建文件夹失败");				
				break;
				
			case 5:
				alert("同步失败,复制文件夹失败");
				break;
				
			case 6:
				alert("同步失败,更新状态失败");
				break;
											
			default:
				alert("同步失败" + code);
				break;
		}
	}
	
	function getUpgrades() {
		var body = "command=1";
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
						html += "<td><input type=\"checkbox\" id=\"select_" + tableId + "\" name=\"select_upgrade\" value=\"" + tableId + "\" /></td>";
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
		
	function delUpgrade(obj, tableId) {
		if (window.confirm("是否删除")) {
			$(obj).attr("disabled", true);
			var body = "command=0&upgrade_table=" + tableId;
			request(servlet, body, onDelUpgrade, obj);	
		}		
	}
	
	function onDelUpgrade(code, content, obj) {
		$(obj).attr("disabled", false);
		switch (code) {
			case 0:				
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
			var body = "command=3&upgrade_table=" + tableId + "&upgrade_force=" + upgradeForce;
			request(servlet, body, onUpdateForce, obj);	
		}		
	}
	
	function onUpdateForce(code, content, obj) {
		$(obj).attr("disabled", false);
		switch (code) {
			case 0:				
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
	
	function getInfo() {
		var body = "command=0";
		request("admin", body, onInfo, null);
	}
	
	function onInfo(code, content, obj) {
		switch (code) {
			case 0: {
				if (content != null) {
					var adminParent = content.admin_parent;						
					if (adminParent == 0) {
						getPacket();
						$("#add").show();
					}
				}				
			}
			break;				
		}		
	}
	
	function selectall(obj) {
		var selected = obj.checked;		
		var checkboxs = $("input[name='select_upgrade']");
		$.each(checkboxs, function(idx, checkbox) {
			$(checkbox).attr("checked", selected);
		});				
	}
		
	getInfo();
	getUpgrades();
</script>
<%@ include file="include/bottom.jsp"%>