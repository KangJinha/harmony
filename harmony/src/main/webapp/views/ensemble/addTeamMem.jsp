<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import= "java.util.List,
				com.harmony.ensemble.model.dto.Inst,
				com.harmony.model.dto.Member,
				com.harmony.model.dto.MemberInfo" %>
<%@ include file="/views/common/header.jsp" %>  
<!DOCTYPE html>
<html>
<head>
  <script src="http://code.jquery.com/jquery-3.7.1.js"></script> 
<meta charset="UTF-8">
<%
List<Inst> inst = (List<Inst>)request.getAttribute("inst");
/* Member member = (Member)request.getAttribute("member");
String memberChk = (String)request.getAttribute("memberChk"); */

%>
<title>Insert title here</title>
</head>
<body>
<!-- 
-팀원추가
고유번호 : seq('TM'||TEAM_MEMBER_SEQ.NEXTVAL) ->서버
팀번호 : seq (selectSeq : ET_00) ->서버
악기번호 : seq (INST_00)  
회원번호 : seq -> 서버
가입일 : DATE ->default
탈퇴일 : DATE -> 팀원제외/탈퇴 페이지
비회원성별 -> 합주모집글에서
비회원나이 -> 합주모집글에서 -->

<div class="add_mem_container">
	

	<div>
		<label for="inst">파트</label>
		<select name="inst" id="inst">
			<%if(!inst.isEmpty()) {
				for(Inst i : inst){ %>
					<option value="<%=i.getInstCode() %>">
						<%=i.getInstName() %>
					</option>
			
				<%}
			}%>
		</select>
	</div>
	<div>
		<p>구분</p>
		<label><input type="radio" name="position" class="position" value="LEADER">리더</label>		
		<label><input type="radio" name="position" class="position" value="MEMBER">멤버</label> 
	</div>
	
	<div class="submit-container">
		<input type="button" id="addMember" value="등록">
	</div>
	

<script>


$(document).ready(()=>{
	if('<%=request.getAttribute("result")%>'=='fail'){
	 	alert('<%=request.getAttribute("msg")%>'); 
	 	close();
	}
});

$('#addMember').click((e)=>{
	
	if($('#inst option:selected').val() != null &&
		$('.position:checked').val() !=null){ 
			
		const memNo = '<%=request.getAttribute("memNo")%>'
		console.log(memNo);
			$("#inst",opener.document).val($('#inst option:selected').val());
			$(".position",opener.document).val($('.position:checked').val());
			$("#memNo",opener.document).val(memNo);
			
			
			
			$("#add_result",opener.document).append($('#searchKeyword',opener.document).val()+ " ");
			$("#add_result",opener.document).append($('#inst option:selected').text() +" (" );
			$("#add_result",opener.document).append($('.position:checked').val()+ "), <br>");
			
		
			close();
	}else{
		
		alert("정보를 입력해주세요.");
	}

	
	});
	
	</script>

</div>

</body>
</html>