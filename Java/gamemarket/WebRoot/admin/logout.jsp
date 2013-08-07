<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include/header.jsp"%>
<script type="text/javascript">
	function logout() {
		var body = "command=2";
		request("admin", body, onLogout);
		window.top.location.href = "login.jsp";
	}
	
	function onLogout(code, result) {
		
	}
	
	logout();
</script>
<%@ include file="include/bottom.jsp"%>