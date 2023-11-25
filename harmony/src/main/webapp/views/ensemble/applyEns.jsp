<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script src="<%=request.getContextPath() %>/js/jquery-3.7.1.min.js"></script>
<html>
<%@ page import = "com.harmony.ensemble.model.dto.EnsembleBoardWantPart,
					java.util.List" %>
<%
	List<EnsembleBoardWantPart> parts = (List<EnsembleBoardWantPart>)request.getAttribute("wantPart");
%>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.choice{
	display: flex;
	justify-content: center;
	padding : 10%;
/* 	border  : 1px solid red; */
}


</style>
</head>
<body>
<%-- <form action = "<%=request.getContextPath()%>/ensemble/applyEnd.do"> --%>
	<div class="guide">
		<h4>지원 파트를 선택해주세요.</h4>
	</div>

	<div class="choice">
	</div> 

	<div class="submit_container">
		<input type="button" id="submit_btn" onclick="sendData();" value="신청하기">
	</div>
<!-- 	<input type="hidden" name="boardNo" id="boardNo" value=""> -->
<!-- </form> -->

</body>
</html>
<script>
 const insts = $("#part",opener.document).text(); 
//  const test =$('#boardNo').val($("#boardNo", opener.document).val())
// 	console.log(test);	
 
 
	let instArr = insts.split(',');	
	
	for(let i of instArr){
		console.log(i);
		const val=i.trim();
		const $radio = $("<label><input type='radio' name='inst' value='" + val + "'>"+val+"</label><br>");
		$('.choice').append($radio);
	}	
	
	const sendData=()=>{
		const checkedData=$("input[type=radio]:checked").val();
		const boardNo=$("input[type=hidden]").val();
		$.post('<%=request.getContextPath()%>/ensemble/applyEnd.do',
				{inst:checkedData,boardNo:$("#boardNo", opener.document).val()})
		.done(e=>{
			alert("신청하기 성공");
			close();
		})
		
	}
	
 </script>