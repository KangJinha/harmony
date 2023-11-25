<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.harmony.model.dto.MemberInfo,java.util.List,com.harmony.model.dto.MemberVideo,java.util.Arrays,com.harmony.model.dto.GenreAll
					,com.harmony.model.dto.InterestAll" %>

<%@ include file="/views/common/header.jsp"%>
<%MemberInfo mi = (MemberInfo)request.getAttribute("MemberInfo"); %>
<% List<MemberVideo> memberVideos = (List<MemberVideo>) request.getAttribute("memberVideos"); %>
<%List<GenreAll> genreAll = (List<GenreAll>)request.getAttribute("GenreAll"); %>
<%List<InterestAll> ia =(List<InterestAll>)request.getAttribute("InterestAll"); %>
<style>
	.my-title,.my-content{
		display:flex;
		justify-content: center;
		align-items: center;
		min-height:100px;
		
	}
	.my-content{
		justify-content: center;
	}
	.my-title>span{
		font-weight:bolder;
	}
	.mypage-container{
		min-height:500px;
		
	}
	.profile-img{
		width:150px;
		height:150px;
		border-radius:100px;
	}
	
	
</style>
<form action="<%=request.getContextPath()%>/member/mypage.do" method="post" enctype="multipart/form-data">
<input type="hidden" name="memNo" value=<%=mi.getMemNo() %>>
<input type="hidden" name="email" value=<%=mi.getEmail() %>>
<div class="cont_personal" >
	<div class="container mypage-container"> <h3>마이페이지</h3>
		<div class="row">
			<div class="col-4 my-title">
				<span>프로필사진</span>
			</div>
			<div class="col-8 my-content">
				
			 <img src="<%=request.getContextPath() %>/upload/<%=mi.getProfilPhoto() %>"
					width="150" height="150" id="profile" >
				
					<input type="file" id="profiledata" name="profilephoto" >
					<script>
						$("#profile").click(e=>{
							$("#profiledata").click();
						})
						$("#profiledata").change(e=>{
							const reader=new FileReader();
							reader.readAsDataURL(e.target.files[0]);
							reader.onload=e=>{
								const path=e.target.result;
								$("#profile").attr("src",path);
							}
						})
					</script>
			</div>
		</div>
		<div class="row">
			<div class="col-4 my-title">
				<span>이름</span>
			</div>
			<div class="col-8 my-content">
				<input type="text" name="name" value="<%=mi.getName()%>">
			</div>
		</div>
		<div class="row">
			<div class="col-4 my-title">
				<span>나이</span>
			</div>
			<div class="col-8 my-content">
				<input type="text" name="age" value="<%=mi.getAge()%>">
			</div>
		</div>
		<div class="row">
			<div class="col-4 my-title">
				<span>한줄소개</span>
			</div>
			<div class="col-8 my-content">
				<input type="text" name="introduce" value="<%=mi.getIntroduce()%>">
			</div>
		</div>	
		<div class="row">
			<div class="col-4 my-title">
				<span>학교</span>
			</div>
			<div class="col-8 my-content">
				<input type="text" name="school" value="<%=mi.getSchool()%>">
				<span>전공</span>
				<input type="text" name="department" value="<%=mi.getDepartment()%>">
			</div>
		</div>
		<div class="row">
			<div class="col-4 my-title">
				<span>성별</span>
			</div>
			<div class="col-8 my-content">
				남<input type="radio" name="gender" value="남" <%=mi.getGender().equals("남")?"checked":"" %>>
				
				여<input type="radio" name="gender" value="여" <%=mi.getGender().equals("여")?"checked":"" %>>
			</div>
		</div>	
		<div class="row">
			<div class="col-4 my-title">
				<span>활동지역</span>
			</div>
			<div class="col-8 my-content">
				<input type="text" name="activityarea" value="<%=mi.getActivityArea()%>">
			</div>
		</div>	
		<div class="row">
			<div class="col-4 my-title">
				<span>악기</span>
			</div>
			<div class="col-8 my-content d-flex row row-col-8">
					<%if(!ia.isEmpty()){
					for(InterestAll interest : ia){%>
			<div class="col-auto">
					<label>
						<input type="checkbox" name="interest"  
						value="<%=interest.getInstCode()%>" 
						<%= Arrays.asList(mi.getInterest()).contains(interest.getInstName())?"checked":"" %>>
						<%=interest.getInstName()%>
					</label>
				</div>
					
				<%	}
				}%>
			</div>
		</div>	
		<div class="row">
			<div class="col-4 my-title">
				<span>장르</span>
			</div>
			<div class="col-8 my-content">
			
				<%if(!genreAll.isEmpty()){
					for(GenreAll genre : genreAll){%>
					<label>
						<input type="checkbox" name="genre"  value="<%=genre.getGenreCode()%>"<%=Arrays.stream(mi.getGenre()).anyMatch(t->t.equals(genre.getGenreName()))?"checked":"" %>>
						<%=genre.getGenreName()%>
					</label>
				<%	}
				}%>
			</div>
		</div>	
		<div class="row">
			<div class="col-4 my-title">
				<span>경력</span>
			</div>
			<div class="col-8 my-content">
				<input type="text" name="memcareer" value="<%=mi.getMemCareer()%>">
			</div>
		</div>	
		<div class="row">
			<div class="col-4 my-title">
				<span>연주영상</span>
			</div>
			<div class="col-8 my-content">
			
			 <input type="file" name="video" value=""> 
			 <% for(MemberVideo video : mi.getMemberVideo()) {%>
    		<%if(video.getVideoLink()!=null&&video.getVideoType().equals("FILE")){ %>
    <video width="320" height="240" controls >
        <source src="<%=request.getContextPath() %>/upload/<%=video.getVideoLink() %>" type="video/mp4">
    </video>
<% }
    }%>  
			</div>
		</div>	
		<div class="row">
			<div class="col-4 my-title">
				<span>연주링크</span>
			</div>
			<div class="col-8 my-content">
			<%boolean isVideoLinkCreated = false;
			for(MemberVideo video: mi.getMemberVideo()){ %>
			<%if(video.getVideoType().equals("LINK")&&video.getVideoLink()!=null){%>
					 <input type="text" name="videolink" value="<%=video.getVideoLink()%>">
				<%
					isVideoLinkCreated= true;
					break;
			}
			}
			if(!isVideoLinkCreated){
			%>
					<input type="text" name="videolink">
			<%
			}
			%>		
			</div>
		</div>	
		<div class="row">
			<div class="col-4 my-title">
				<span>음원</span>
				
			</div>
			<div class="col-8 my-content">
				<input type="text" name="music1" value="문의 : 010-3618-8520" readonly>
			</div>
		</div>	
		<div class="row">
			<div class="col-4 my-title">
				<span>음원링크</span>
			</div>
			<div class="col-8 my-content">
				<input type="text" name="musiclink1" value="문의 : 010-3618-8520" readonly>
			</div>
		</div>	
		
		<input type="submit" value="수정하기">
		
		</div>		
	</div>
	
		</form>
		




<%@ include file="/views/common/footer.jsp"%>