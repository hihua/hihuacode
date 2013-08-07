<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include/header.jsp"%>
<script type="text/javascript">
	function refresh() {
		window.setTimeout(onfun, 120 * 1000);		
	}
	
	function onfun() {
		window.location.href = window.location.href;
	}
	
	refresh();
</script>
<%@ include file="include/bottom.jsp"%>