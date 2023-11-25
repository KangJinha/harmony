<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.harmony.model.dto.Member" %>
<% Member loginMember = (Member)request.getSession().getAttribute("loginMember"); %>
<% int unreadMessage = (String)request.getAttribute("unReadMessage")!=null?Integer.parseInt((String)request.getAttribute("unReadMessage")):0; %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>이제 너에게 음악을 알려줄게 - 이음줄</title>
	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/bootstrap.css">
	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/navbarStyle.css">
	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/footerStyle.css">
	<script src="<%=request.getContextPath() %>/js/popper.min.js"></script>
	<script src="<%=request.getContextPath() %>/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath() %>/js/jquery-3.7.1.min.js"></script>
	<style>
	@font-face {
	    font-family: 'GangwonEdu_OTFBoldA';
	    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2201-2@1.0/GangwonEdu_OTFBoldA.woff') format('woff');
	    font-weight: normal;
	    font-style: normal;
	}
	*{
		font-family: 'GangwonEdu_OTFBoldA';
	}
	</style>
</head>
<body>
	<header>
		<nav class="navbar navbar-expand-lg navbar-light">
			<div class="container d-flex justify-content-between align-items-center">
				<a class="navbar-brand logo h1 align-self-center" href="<%=request.getContextPath()%>">
					<img src="<%=request.getContextPath() %>/image/slur_logo1.png" alt="이음줄로고">
				</a>
				<div class="align-self-center navbar-collapse flex-fill d-lg-flex justify-content-lg-between"
					id="harmony_main_nav">
					<div class="flex-fill">
						<ul class="nav navbar-nav d-flex flex-row justify-content-between mx-lg-auto">
							<li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>">메인페이지</a></li>
							<li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/lesson/findLesson.do">레슨찾기</a></li>
							<li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ensemble/boardList.do">합주찾기</a></li>
							<li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/infoBoardList.do">커뮤니티</a></li>
						</ul>
					</div>
				</div>
				<div class="navbar align-self-center d-flex">
					<ul class="nav navbar-nav d-flex flex-row justify-content-between mx-lg-auto">
					<%if(loginMember==null){ %>
						<li class="nav-item">
							<a class="btn nav-link" href="<%=request.getContextPath()%>/loginServlet.do">로그인</a>
						</li>
					<%}else{ %>
						<li class="nav-item">
							<a class="nav-link btn-warning position-relative" role="button" data-bs-toggle="modal" data-bs-target="#modalMessageBox" onclick="getMessageList('<%=loginMember.getMemNo()%>')">
								<i class="nav-icon bi bi-envelope">
									<svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-envelope" viewBox="0 0 16 16">
									  <path d="M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V4Zm2-1a1 1 0 0 0-1 1v.217l7 4.2 7-4.2V4a1 1 0 0 0-1-1H2Zm13 2.383-4.708 2.825L15 11.105V5.383Zm-.034 6.876-5.64-3.471L8 9.583l-1.326-.795-5.64 3.47A1 1 0 0 0 2 13h12a1 1 0 0 0 .966-.741ZM1 11.105l4.708-2.897L1 5.383v5.722Z"/>
									</svg>
								</i>
								<%if(unreadMessage>0){%>
								<span class="position-absolute top-45 start-70 translate-middle badge rounded-pill bg-danger">
								    <%=unreadMessage%>
								    <span class="visually-hidden">unread messages</span>
								</span>
								<%} %>
							</a>
						</li>
						<li class="nav-item">
							<a class="nav-link btn-warning" href="<%=request.getContextPath() %>/member/myPageServlet.do">
								<i class="nav-icon bi bi-person-vcard-fill">
									<svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-person-vcard-fill" viewBox="0 0 16 16">
									  <path d="M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V4Zm9 1.5a.5.5 0 0 0 .5.5h4a.5.5 0 0 0 0-1h-4a.5.5 0 0 0-.5.5ZM9 8a.5.5 0 0 0 .5.5h4a.5.5 0 0 0 0-1h-4A.5.5 0 0 0 9 8Zm1 2.5a.5.5 0 0 0 .5.5h3a.5.5 0 0 0 0-1h-3a.5.5 0 0 0-.5.5Zm-1 2C9 10.567 7.21 9 5 9c-2.086 0-3.8 1.398-3.984 3.181A1 1 0 0 0 2 13h6.96c.026-.163.04-.33.04-.5ZM7 6a2 2 0 1 0-4 0 2 2 0 0 0 4 0Z"/>
									</svg>
								</i>
							</a>
						</li>
						<li class="nav-item">
							<a class="btn nav-link btn-warning" href="https://kauth.kakao.com/oauth/logout?client_id=7a71de50fa7db8d6bb2395a8a5fba504&logout_redirect_uri=http://<%=request.getServerName()%>:<%=request.getServerPort()%><%=request.getContextPath()%>/logout.do">로그아웃</a>
						</li>
					<%} %>
					</ul>
				</div>
			</div>
		</nav>
	</header>