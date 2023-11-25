<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.List,com.harmony.board.free.model.dto.FreeBoard"%>
<%-- <%

List<FreeBoard> boards = (List<FreeBoard>) request.getAttribute("boards");
String pageBar = (String) request.getAttribute("pageBar");
%> --%>
<%
List<FreeBoard> boards = null;
if (request.getAttribute("searchResults") != null) {
	boards = (List<FreeBoard>) request.getAttribute("searchResults");
} else {
	boards = (List<FreeBoard>) request.getAttribute("boards");
}
String pageBar = (String) request.getAttribute("pageBar");
%>

<%@ include file="/views/common/header.jsp"%>

<link rel="stylesheet" href="styles.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/board/freeCommunity.css"
	type="text/css">

<div class="content">
	<div class="infoboard">
		<div class="info-container">
			<h3 class="info">
				<a href="<%=request.getContextPath()%>/infoBoardList.do">정보 게시판</a>
			</h3>
			<h3 class="free">
				<a href="<%=request.getContextPath()%>/freeBoardList.do">자유 게시판</a>
			</h3>
		</div>
	</div>
	<div class="content-detail">
		<div class="sort">
			<select id="sort-select-free" name="sort">
				<option value="latest">최신등록순</option>
				<option value="oldest">오래된순</option>
				<option value="views">조회수</option>
				<option value="comments">댓글수</option>
			</select>
		</div>

		<div class="main-sort-container">
			<table class="table">
				<thead>
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>작성자</th>
						<th>날짜</th>
						<th>첨부파일</th>
						<th>조회수</th>
						<th>댓글수</th>
					</tr>
				</thead>
				<tbody>
					<%
					for (FreeBoard board : boards) {
					%>
					<tr>
						<td style="text-align: center;"><%=board.getFreBrdNo()%></td>
						<td style="text-align: center;"><a
							href="<%=request.getContextPath()%>/board/freeboardView.do?no=<%=board.getFreBrdNo()%>">
								<%=board.getFreBrdTitle()%>
						</a></td>
						<td style="text-align: center;"><%=board.getFreBrdWriter()%></td> 
						<td style="text-align: center;"><%=board.getFreBrdDate()%></td>
						<td style="text-align: center;"><%=board.getFreBrdTitleImg() != null ? "Yes" : "No"%></td>
						<td style="text-align: center;"><%=board.getFreBrdViews()%></td>
						<td style="text-align: center;"><%=request.getAttribute("commentCount" + board.getFreBrdNo())%></td>
					</tr>
					<%
					}
					%>
				</tbody>
			</table>
			<form action="<%=request.getContextPath()%>/board/FreeBoardSearch.do"
				method="get" class="search-form">
				<select name="searchType" class="search-select">
					<option value="both">제목+본문</option>
					<option value="title">제목</option>
					<option value="content">본문</option>
				</select> <input type="text" name="query" placeholder="검색어 입력"
					class="search-input">
				<button type="submit" class="search-button">
					<img src="<%=request.getContextPath()%>/image/board/search2.png">
				</button>
			</form>
			<div class="text-center">
				<ul class="pagintion">
					<%=pageBar%>
				</ul>
			</div>
		</div>

		<div class="write-button-container">
			<button
				onclick="location.assign('<%=request.getContextPath()%>/board/freeboardWrite.do')">
				자유게시판글쓰기</button>
		</div>
	</div>
</div>
<%@ include file="/views/common/footer.jsp"%>

<script>
document.getElementById('sort-select-free').addEventListener('change', function() {
    var selectedSort = this.value;
    var currentUrl = new URL(window.location.href);
    currentUrl.searchParams.set('sort', selectedSort);
    window.location.href = currentUrl.toString();
});

window.onload = function() {
    var currentUrl = new URL(window.location.href);
    var selectedSort = currentUrl.searchParams.get('sort');
    if (selectedSort != null) {
        document.getElementById('sort-select-free').value = selectedSort;
    }
};
</script>
