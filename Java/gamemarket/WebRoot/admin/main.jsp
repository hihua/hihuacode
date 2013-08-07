<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include/header.jsp"%>
<table id="table" border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  	<tr>
      	<td valign="top" height="100%" width="162">
       		<iframe frameborder="0" id="menu" scrolling="no" src="menu.jsp" width="162" height="100%"></iframe>
      	</td>
      	<td bgcolor="#A4B6D7" width="9" valign="middle" id="banner">&nbsp;</td>
   		<td valign="top" height="100%">
    		<table cellspacing="0" cellpadding="0" width="100%" height="90%" border="0">
     			<tr>
      				<td align="center" valign="top" width="100%" height="25" background="images/titlebg.gif"><span style="color:#330099;font-size:16px;font-weight:bold">网站管理后台</span></td>
     			</tr>
     			<tr>
      				<td height="100%"><iframe frameborder="0" id="main" name="main" scrolling="auto" src="welcome.jsp" width="100%" height="100%"></iframe></td>
     			</tr>
    		</table>			
    	</td>
	</tr>
</table>
<script type="text/javascript">
function getPageSize() {
    var xScroll, yScroll;
    if (window.innerHeight && window.scrollMaxY) {
        xScroll = window.innerWidth + window.scrollMaxX;
        yScroll = window.innerHeight + window.scrollMaxY;    
	} else {
		if (document.body.scrollHeight > document.body.offsetHeight) {
		 	xScroll = document.body.scrollWidth;            
		 	yScroll = document.body.scrollHeight;        
		} else { 
			xScroll = document.body.offsetWidth;            
			yScroll = document.body.offsetHeight;        
		}    
	}    
	
	var windowWidth, windowHeight;    
	if (self.innerHeight) {
		if (document.documentElement.clientWidth) {
			windowWidth = document.documentElement.clientWidth;        
		} else {
			windowWidth = self.innerWidth;        
		}
		
		windowHeight = self.innerHeight;    
	} else {
		if (document.documentElement && document.documentElement.clientHeight) { 
			windowWidth = document.documentElement.clientWidth;            
			windowHeight = document.documentElement.clientHeight;        
		} else {            
			if (document.body) { 
				windowWidth = document.body.clientWidth;                
				windowHeight = document.body.clientHeight;            
			}
		}
	}           
	
	if (yScroll < windowHeight) {        
		pageHeight = windowHeight;    
	} else {        
		pageHeight = yScroll;    
	}        
	
	if (xScroll < windowWidth) {
		pageWidth = xScroll;    
	} else {        
		pageWidth = windowWidth;    
	}    
	
	arrayPageSize = new Array(pageWidth, pageHeight, windowWidth, windowHeight);    
	return arrayPageSize;
}

var array = getPageSize();
$("#table").css("height", array[1]);
</script>
<%@ include file="include/bottom.jsp"%>