<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Hi+Melody&family=Nanum+Gothic+Coding&display=swap" rel="stylesheet">    
    
    
<%@ include file="/views/common/header.jsp" %>  
<%@ page import= "java.util.List, 
					com.harmony.ensemble.model.dto.Genre" %>
<%
	List<Genre> genre = (List<Genre>)request.getAttribute("genre");
%>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/ensemble/enrollTeam.css" type="text/css">

<section>

<div class="enroll_container">
	<div>
		<span>팀명</span>
		<input type="text" id="teamName" >
	</div>

	<div>	
		<label for="genre">장르</label>
		<select name="genre" id="genre" >
			<%if(!genre.isEmpty()) {
				for(Genre g : genre){ %>
					<option value="<%=g.getGenreCode() %>"><%=g.getGenreName() %></option>
			
			<%	} 
			}%>
		</select>
	</div>		
	<div>
		<p>구분</p>
		<label>
		    <input type='radio' class="single_chk" name='type' value='취미' style="display:none">
			<span class="single_chk_span">취미</span>
		</label>
		<label>
		 	<input type='radio' class="single_chk" name='type' value='전문' style="display:none">
			<span class="single_chk_span">전문</span>
		</label>
	</div>		
	<div>
		<input type="button" value="합주일정추가" name="schedule" id="schedule" 
				onclick="addSchedule();">
	</div>
		<span id="sch_result">
		</span>
	<div>
		<p>한 줄 소개</p>
		<textarea cols="30" rows="3" id="detail"></textarea>
	</div>
	<div>
		<p>영상</p>
		<input type="file" id="video" multiple accept="video/*">
	</div>
	<div>
		<p>음원</p>
		<input type="file" id="music" multiple accept="audio/*">
	</div>
	<div class="add_mem">
	
		<input type="text" name="searchKeyword" size="25" id="searchKeyword"
	       				placeholder="검색할 이메일" >
		<input type="button" value="멤버추가" onclick="addTeamMem();"> 
	</div>
	<div>
		<span id="add_result">
			
		</span>	
	</div>	
	<div class="submit_container">
		<input type="button" value="등록" id="submit" >
	</div>

	
</div>


<input type="hidden" id="dayOfWeek" name="dayOfWeek">
<input type="hidden" id="startTime" name="startTime">
<input type="hidden" id="endTime" name="startTime">

<input type="hidden" class="position" name="position">
<input type="hidden" id="inst">

<input type="hidden" id="memNo" name="memNo">


</section>


<script>

const addSchedule =()=>{
	open("<%=request.getContextPath()%>/ensemble/addSchedule.do", "_blank", "width=500, height=400");
	
}

const addTeamMem =()=>{
	open("<%=request.getContextPath()%>/ensemble/searchEmail.do?keyword="+$('#searchKeyword').val()
			,"_blank","width=500, height=400");
	
}


$(document).ready(function(){
	$('.single_chk_span').click(function(){
		$(this).toggleClass('selected');
	$('.single_chk_span').not(this).removeClass('selected');
});


});
</script>
<script>
		
	
	$("#submit").click(e=>{

		
		const form = new FormData();
		
		const teamName = $("#teamName").val();
		const genre = $("#genre").val();
		const type = $(".single_chk:checked").val(); 
		const detail = $("#detail").val();
		const dayOfWeek = $("#dayOfWeek").val();
		const startTime = $("#startTime").val();
		const endTime = $("#endTime").val();
		const inst = $("#inst").val();
		const position = $(".position").val();
		const memNo = $("#memNo").val();
		
		
		form.append("teamName", teamName);
		form.append("genre", genre);
		form.append("type", type);
		form.append("detail", detail);
		form.append("dayOfWeek",dayOfWeek);
		form.append("startTime", startTime);
		form.append("endTime",endTime);
		form.append("inst",inst);
		form.append("position",position);
		form.append("memNo", memNo);
		
		const videoInput=$("#video");
		$.each(videoInput[0].files, (i,v)=>{
			form.append("video"+i,v);
		});
			
		const musicInput=$("#music");
		$.each(musicInput[0].files, (i,m)=>{
			form.append("music"+i,m);

		});	
			
		$.ajax({
			url: "<%=request.getContextPath()%>/ensemble/enrollTeamEnd.do",
			data:form,
			type:"post",
			processData:false,
			contentType:false,
			success:data=>{
				alert("등록 성공");
				location.assign('<%=request.getContextPath()%>')
			},
			error:(r,e)=>{
				alert("등록 실패");
			},
			complete:()=>{
				videoInput.val('');
				musicInput.val('');
			}
		});
	});
			

</script>

<%@ include file="/views/common/footer.jsp" %>