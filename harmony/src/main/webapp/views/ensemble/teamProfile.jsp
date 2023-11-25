
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/header.jsp" %>  
    
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Hi+Melody&family=Nanum+Gothic+Coding&display=swap" rel="stylesheet">    
    
<%@ 
page import="java.util.List, 
			com.harmony.ensemble.model.dto.EnsembleTeamComment,
			com.harmony.ensemble.model.dto.EnsembleTeam,
			com.harmony.ensemble.model.dto.EnsembleTeamTime,
			com.harmony.ensemble.model.dto.EnsembleTeamMusic,
			com.harmony.ensemble.model.dto.EnsembleTeamVideo,
			com.harmony.ensemble.model.dto.Genre,
			com.harmony.ensemble.model.dto.EnsembleTeamComment,
			com.harmony.ensemble.model.dto.MemberProfile" 
%>
<script src="https://kit.fontawesome.com/a6c64592e2.js" crossorigin="anonymous"></script>
<%
EnsembleTeam team=(EnsembleTeam)request.getAttribute("team");
List<EnsembleTeamTime> time = (List<EnsembleTeamTime>)request.getAttribute("time");
List<EnsembleTeamMusic> music = (List<EnsembleTeamMusic>)request.getAttribute("music");
List<EnsembleTeamVideo> video = (List<EnsembleTeamVideo>)request.getAttribute("video");
List<Genre> genre = (List<Genre>)request.getAttribute("genre");
List<MemberProfile> members = (List<MemberProfile>)request.getAttribute("members");

%>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/ensemble/teamProfile.css" type="text/css">


<main>
<input type="hidden" name="teamNo" value="<%=team.getEnsTeamNo()%>">
	<section>
		<div class="team_container">
	
			<div class="btn_container">
				<input type="button" value="모집 글 등록" id="board_write_btn" 
					onclick="location.assign('<%=request.getContextPath()%>/ensemble/boardWrite.do')">
			</div>
			
			<%if(team!=null){ %>
				<div class="teamName_container">
					<p><b>팀명</b></p>
					<%=team.getEnsTeamName() %>
					<br>
				</div>
				<div class="hr_container">
					<hr>
				</div>
				<div>
					<p><b>장르</b></p>
					<% if(!genre.isEmpty()){
							for(Genre g: genre){%>
								<%if(team.getEnsGenreNo().equals(g.getGenreCode())){ %>
									<%=g.getGenreName() %>
								<%} %>
						<%}
					}%>
				</div>
				<div class="time_container">
					<p><b>합주 일정</b></p>
				<%if(!time.isEmpty()){ 
					for(EnsembleTeamTime t : time){%>
					<%=t.getEnsStartTime() %> <br>
							~ <br>
					<%=t.getEnsEndTime() %>
					<%} 
				}%>
				</div>
				<div class="hr_container">
					<hr>
				</div>		
				<div class="video_container">
					<p><b>영상</b></p>
					<%if(!video.isEmpty()){ 
					for(EnsembleTeamVideo v : video){%>
					<%=v.getVReName() %> <br>
					<%} 
				}%>
				<video src="https://media.istockphoto.com/id/933641342/ko/%EB%B9%84%EB%94%94%EC%98%A4/%ED%82%A4%EB%B3%B4%EB%93%9C-%EC%98%A4%EB%A5%B4%EA%B0%84-%EB%98%90%EB%8A%94-%ED%94%BC%EC%95%84%EB%85%B8-%EC%82%B0%ED%83%80-%EB%AA%A8%EC%9E%90%EC%97%90-%EC%9E%AC%EB%AF%B8-%EC%9E%88%EB%8A%94-%EA%B3%A0%EC%96%91%EC%9D%B4-%EC%9E%AC%EC%83%9D.mp4?s=mp4-640x640-is&k=20&c=lVo-NN-mzc5P6YYJvQtgzJI2zGQtn9u286QEmoewgeM="
						controls width="350" height="300"></video>
				</div>
				<div class="hr_container">
					<hr>
				</div>
				<div class="detail_container">
				<div class="music_container">
					<p><b>음원</b></p>
					<%if(!music.isEmpty()){ 
					for(EnsembleTeamMusic m : music){%>
						<%=m.getMReName() %> <br>
					<%} 
				}%>
						 <audio src="https://artlist.io/royalty-free-music/song/midnight/73829" controls></audio> <br><br>
				
				</div>
				<div class="hr_container">
					<hr>
				</div>
				<div class="detail_container">
					<p><b>한 줄 소개</b></p>
					<%=team.getEnsTeamInfo() %>		
					
				</div>
<!-- 				<div class="hr_container"> -->
<!-- 					<hr> -->
<!-- 				</div>		 -->
<!-- 				<div class="comments_container"> -->
<!-- 					<p><b>댓글 보기</b></p> -->
<!-- 				</div> -->
				
			<%} %>
			</div>
		</section>		
	<aside>
		<div class="mem_profile_container">
			<br><br>
			<p><b>멤버 프로필</b></p>
			<%if(!members.isEmpty()){ 
				for(MemberProfile m : members){%>
					<div class="profile_card">
						<br>
							<div class="hr_container">
								<hr>
							</div>
						<br>
						<i class="fa-solid fa-user"></i>
						<%=m.getEnsMemPosition() %>
						<br>
						<a href="<%=request.getContextPath()%>">
							<%=m.getMemInfoEmail() %>
						</a>
						<br>
						<%=m.getInstName() %>
						<br>
						<span>가입 날짜 :</span>
						<%=m.getEnsMemJoinDate() %>
						<br>
							<div class="hr_container">
								<hr>
							</div>
						<br>
					</div>
					
			<%	}
			} %>
		</div>
	</aside>
</main>

<%@ include file="/views/common/footer.jsp" %>  