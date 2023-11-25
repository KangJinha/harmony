<%@page import="java.awt.print.Printable"%>
<%@page import="java.io.Console"%>
<%@page import="javax.tools.DocumentationTool.Location"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%
	String memberChk=(String)request.getAttribute("memberChk");
	String result = (String)request.getAttribute("result");
%>    
<script src="http://code.jquery.com/jquery-3.7.1.js"></script> 
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<div id="check-container">
	<!-- 검색한 이메일이 테이블에 있으면 -->
	<%if(result=="1"){%>
	<%=request.getAttribute("msg")%> 
		<%}else{%>
	
		<%=request.getAttribute("msg") %>
	<%} %>
	<button type="button" onclick="closeBtn();">닫기</button>

<script>

	const closeBtn =()=>{
		close();
	}
</script>

	
	
	

<%-- 
	<%if(result){ %>
		[<span><%=request.getParameter("searchEmail") %></span>]는 기존 회원이 아닙니다.
		<br><br>
		<button type="button" >닫기</button>
	<%}else{ %>
		[<span id="duplicated"><%=request.getParameter("searchEmail") %></span>]는 기존 회원!
		<br><br>
		<!-- 아이디 재입력창 구성 -->
		<form action="<%=request.getContextPath() %>/member/searchEmail.do" method="get">
			<input type="text" name="searchEmail" id="searchEmail">
			<input type="submit" value="중복검사" >
		</form>
	<%} %> --%>
	</div>	
<script>
	
</script>

</body>
</html>