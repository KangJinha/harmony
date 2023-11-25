<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import = "java.util.List, 
					com.harmony.ensemble.model.dto.VChkApply"
 %>
 <%
 	List<VChkApply> applyList = (List<VChkApply>)request.getAttribute("applyList");
 %>
 <link rel="stylesheet" href="<%=request.getContextPath() %>/css/ensemble/chkApply.css" type="text/css">
 <script src="http://code.jquery.com/jquery-3.7.1.js"></script> 
<html>
<head>
<meta charset="UTF-8">
</head>
<body>

<form action="<%=request.getContextPath() %>/ensemble/chkApplyEnd.do">

<%if(!applyList.isEmpty()){ 
	int index=0;
	for(VChkApply a : applyList){%>
		<%if(!a.getEnsApproval().equals("Y")){ %>
			<%=a.getMemInfoEmail() %>	
			<%=a.getInstName() %>
			
			<input type="hidden" value="<%=a.getEnsPartIndex() %>"  id="partIndex" name="partIndex">
			<input type="hidden" value="<%=a.getEnsBoardNo() %>"  name="boardNo">
			<input type="submit" value="수락"> 
			<br>
		<% }%>

		
		<%}	
	} %>
	
	<input type="button" onclick="close_popup();" value="닫기">
	
	
<!-- 		<div class="submit_container"> -->
<!-- 			<input type="button" id="submit_btn" onclick="sendData();" value="완료"> -->
<!-- 		</div> -->
	
	
</form>
</body>

<script>

const close_popup =()=>{
	window.close();
}

$(document).ready(function(){
	$('.single_chk_span').click(function(){
		
		$(this).parents("div").find('.single_chk_span').removeClass('selected');
		$(this).addClass('selected');
		
		console.log($('.single_chk').val());

	});
});
		
</script>

</html>