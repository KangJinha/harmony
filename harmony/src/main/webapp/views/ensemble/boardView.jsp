<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Hi+Melody&family=Nanum+Gothic+Coding&display=swap" rel="stylesheet">    
    
<script src="https://kit.fontawesome.com/a6c64592e2.js" crossorigin="anonymous"></script>    
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/ensemble/boardView.css" type="text/css">
<%@ include file="/views/common/header.jsp" %>  
<%@ page import = "com.harmony.ensemble.model.dto.VBoardView" %>
<%
	VBoardView b = (VBoardView)request.getAttribute("board");
%>


<section>

	<div class="board_container">
		<%if(b!=null){%>
		<div class="top_container">
			<div class="title">
				<b><%= b.getEnsBoardTitle() %></b>
			</div>
			<div class="type">
				<div class="type_shape">
					<%= b.getEnsTeamType() %>
				</div>
			</div>
		</div>
		<div class="team_container">
			<div class="teamName">
				<span>팀 이름 :</span>
				<%= b.getEnsTeamName() %>
				<i class="fa-solid fa-users-line icon" onclick="location.assign('<%=request.getContextPath() %>/ensemble/profileFromBoard.do?teamNo=<%=b.getEnsTeamNo() %>')"></i>
			</div>
			<div class="writer">
				<span>팀 리더 : </span>
				<%= b.getEnsWriter()%>	
			</div>
			<div class="genre">
				<span>장르 : </span>
				<%= b.getGenreName() %>
			</div>
			<div class="wantPart">
				<span>모집 파트: </span>
				<span id="part"><%= b.getInstrument() %>	</span>			
			</div>
					<input type="hidden" id="want_part" value="<%=b.getInstrument() %>">
					<input type="hidden" id="board_no" value="<%=b.getEnsBoardNo() %>"> 
			<div class="location">
				<span>장소: </span>
				<%= b.getEnsLocation() %> <br>
				<%= b.getEnsPlace() %>		
			</div>	
			<div class="detail">
				<span>상세 설명: </span>
				<%= b.getEnsDetail() %>		
			</div>
			<div class="date">
				<span>등록 날짜:</span>
				<%= b.getEnsBoardDate() %>		
			</div>
				<input type="hidden" value="<%=b.getEnsBoardNo() %>" id="boardNo">
			<%}%>
		
		
			<div class="apply_container">
				<button type="button" onclick="apply_btn();" class="apply_btn">
					신청하기
				</button>
				<br><br>
				<%if(loginMember!=null && b.getEnsWriter().equals(loginMember.getMemNo())) {%>
					<button type="button" onclick="check_apply();" class="check_apply" >
						신청자 확인
					</button>
					<button type="button" onclick="delete_board();" class="delete_board">
						글 삭제
					</button>
					
				<%} %>
			</div>
	</div>


</section>

<script>

const delete_board =()=>{
	
	let result = confirm('정말 삭제하시겠습니까?');
	
	if(result){
		location.assign('<%=request.getContextPath()%>/ensemble/deleteBoard.do?boardNo='+$('#boardNo').val());		
	}	else{
		
	}
}


const apply_btn =()=>{

	open("<%=request.getContextPath()%>/ensemble/insert.do", "_blank", "width=400, height=300");
}

const check_apply =()=>{
	open("<%=request.getContextPath()%>/ensemble/chkApply.do?boardNo="+$('#boardNo').val(), "_blank", "width=400, height=300");

}

</script>
<%@ include file="/views/common/footer.jsp" %>  