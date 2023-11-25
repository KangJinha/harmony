<%@page import="com.harmony.lesson.dto.Lesson"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	List<Lesson> lessons = (List<Lesson>)request.getAttribute("lessons");
%>
	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/bootstrap.css">
	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/navbarStyle.css">
	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/footerStyle.css">
	<script src="<%=request.getContextPath() %>/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath() %>/js/jquery-3.7.1.min.js"></script>
    <script src="https://kit.fontawesome.com/8f05e1d322.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/lesson/findLesson.css">
       	<%for(Lesson l : lessons){
       	if(!lessons.isEmpty()) {%>
          <div class="lessonList" onclick="location.href='<%=request.getContextPath()%>/lesson/lessonInfo.do?no=<%=l.getBoardNo() %>'">
          <%if(l.getBoardImg()!=null) {%>
            <div class="lessonListImageBox"><img src="<%=request.getContextPath()%>/upload/lesson/<%=l.getBoardImg()%>" width="100%" height="200px"></div>
            <%} else { %>
            <div class="lessonListImageBox"><img src="<%=request.getContextPath()%>/images/default.jpg" width="200px" height="200px"></div>
            <%} %>
            <div class="lessonListTitle"><%=l.getBoardTitle() %>&nbsp;</div>
            <div class="lessonView">
            	<i class="fa-regular fa-eye fa-sm">&nbsp;<%=l.getBoardView() %></i>
            </div>
          </div>
          		<%} %>
         	<%} %>
