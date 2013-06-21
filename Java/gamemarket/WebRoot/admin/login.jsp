<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include/header.jsp"%>
<script type="text/javascript">
	function login() {
		var username = $("#username").val();
		var password = $("#password").val();
		var code = $("#code").val();
		if (username.length == 0) {
			alert("请输入管理员账户");
			return;
		}			
		
		if (password.length == 0) {
			alert("请输入管理员密码");
			return;
		}
		
		if (code.length == 0) {
			alert("请输入安全验证码");
			return;
		}
		
		var body = "command=1&username=" + username + "&password=" + password + "&code=" + code;
		request("admin", body, onLogin);
	}
	
	function onLogin(code, result) {
		switch (code) {
			case 0:
				window.location.href = "main.jsp";
				break;
				
			case 1:
				alert("用户名密码错误");
				$("#code").val("");
				setCode();
				break;
			
			case 2:
				alert("参数错误");
				$("#code").val("");
				setCode();
				break;			
						
			case 3:
				alert("验证码错误");
				$("#code").val("");
				setCode();
				break;
				
			default:
				alert("登录失败");
				$("#code").val("");
				setCode();
				break;
		}
	}
	
	function setCode() {
		var imagecode = $("#imagecode");
		imagecode.attr("src", "../servlet/imagecode");		
	}
</script>
<table width="640" border="0" cellspacing="0" cellpadding="0" align="center">
	<tr>
		<td height="390" valign="top" background="images/admin.gif">
			<table width="640" height="276" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="338" height="109">&nbsp;</td>
					<td width="302">&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td valign="top">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="25%" height="26">&nbsp;</td>
								<td colspan="2">&nbsp;</td>
								<td width="8%">&nbsp;</td>
							</tr>
							<tr>
								<td height="30"><span>管理员账户</span>:</td>
								<td height="30" colspan="2"><input type="text" id="username" maxlength="30" style="width:120px"></td>
								<td height="30">&nbsp;</td>
							</tr>
							<tr>
								<td height="30"><span>管理员密码</span>:</td>
								<td height="30" colspan="2"><input type="password" id="password" maxlength="30" style="width:120px"></td>
								<td height="30">&nbsp;</td>
							</tr>
							<tr>
								<td height="30"><span>安全验证码</span>:</td>
								<td width="30%" height="30"><input type="text" id="code" maxlength="4" style="width:80px"></td>
								<td width="37%"><img id="imagecode" src="../servlet/imagecode" onclick="setCode()" style="cursor:pointer" alt="" /></td>
								<td height="30">&nbsp;</td>
							</tr>
							<tr>
								<td height="30" colspan="3">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="26%" height="34">&nbsp;</td>
											<td width="25%"><input type="button" value=" 登录 " onclick="login()" /></td>
											<td><input type="button" value=" 重置 " onclick="window.location.href=window.location.href" /></td>
										</tr>
									</table></td>
								<td height="30">&nbsp;</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<%@ include file="include/bottom.jsp"%>