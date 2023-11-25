<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/header.jsp" %>  
<%@ page import="com.harmony.model.dto.MemberInfo" %> 
<%MemberInfo mi = (MemberInfo)request.getSession().getAttribute("MemberInfo"); %>
<style>
.container2 {
  width: 100%;
  height: 80vh;
  display: flex;
  align-items: center;
  justify-content: center;
  
}
.profil{
	width: 400px;
	height: 500px;
	margin-top : 100px;
	margin-bottom : 10%;
	
	background: rgba(0, 0, 0, 0.1);
}
.pofileTable{
	margin-top:10px
}
</style>
<section class="container2">
<div class="profil">
	<img src="<%=request.getContextPath() %>/image/profile/profile.png" height="150px"><br>
	<a>한줄소개</a>
	<table class="pofileTable">
		<tr>
			<td>나이</td>
			<td></td>
		</tr>
		<tr>
			<td>성별</td>
			<td>남</td>
		</tr>
		<tr>
			<td>장르</td>
			<td>클래식</td>
		</tr>
		<tr>
			<td>악기</td>
			<td>기타</td>
		</tr>
		<tr>
			<td>활동지역</td>
			<td>경기도</td>
		</tr>
		<tr>
			<td>학력</td>
			<td>서울대</td>
		</tr>
		<tr>
			<td>경력</td>
			<td>null</td>
		</tr>
		<tr>
			<td>연주영상</td>
			<td>link</td>
		</tr>
		<tr>
			<td>음원</td>
			<td>link</td>
		</tr>
		
		
	</table>
</div>
</section>
<%@ include file="/views/common/footer.jsp" %> 