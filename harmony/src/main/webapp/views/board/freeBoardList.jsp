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

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/board/freeCommunity.css"
	type="text/css">


<div class="content">
	<div class="main">
		<div class="board">
			<div class="info-container">
				<h3 class="info-board">
					<a href="<%=request.getContextPath()%>/infoBoardList.do">정보 게시판</a>
				</h3>
				<h3 class="free-board">
					<a href="<%=request.getContextPath()%>/freeBoardList.do">자유 게시판</a>
				</h3>
				<h3 class="notice-board">
					<a href="<%=request.getContextPath()%>/notice.do">공지 게시판</a>
				</h3>
			</div>
		</div>
		
		

		<div class="main-container">
			<div class="filter">
				<div class="sort">
						<select id="sort-select-free" name="sort">
							<option value="latest">최신등록순</option>
							<option value="oldest">오래된순</option>
							<option value="views">조회수</option>
							<option value="comments">댓글수</option>
						</select>
				</div>
				<div class="post-writer-button-div">
					<button class="post-writer-button"
						onclick="location.assign('<%=request.getContextPath()%>/board/freeboardWrite.do')">
						글쓰기</button>
				</div>
			</div>

			<div class="post">
				<%
				if (!boards.isEmpty()) {
					for (FreeBoard board : boards) {
						String titleImgName = board.getFreBrdTitleImg();
						String titleImgUrl = (titleImgName != null && !titleImgName.isEmpty())
						? request.getContextPath() + "/upload/board/" + titleImgName
						: null;
				%>
				<div class="individual-post">
					<a
						href="<%=request.getContextPath()%>/board/freeboardView.do?no=<%=board.getFreBrdNo()%>">
				
						<div class="post-main">
							<div>
								<section class="post-content">
									<h3><%=board.getFreBrdTitle()%></h3>
									<p><%=board.getFreBrdContent()%></p>
								</section>
								<p class="post-writer"><%=board.getFreBrdWriter()%></p>
							</div>
							<%
							if (titleImgUrl != null) {
							%>
							<img src="<%=titleImgUrl%>">
							<%
							}
							%>
						</div>
						<div class="post-footer">
							<div class="view-comment">
								<div class="views-comments-container">
									<span>조회수 <%=board.getFreBrdViews()%> </span> <span>댓글 <%=request.getAttribute("commentCount" + board.getFreBrdNo())%></span>
								</div>
								<span class="post-date"><%=board.getFreBrdDate()%></span>
							</div>
						</div>
					</a>
				</div>
				<%
				}
				}
				%>
			</div>


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

			<div class="pagination-form">
				<ul class="pagination">
					<%=request.getAttribute("pageBar")%>
				</ul>
			</div>
		</div>
	</div>
</div>

<script>
	document.getElementById('sort-select-free').addEventListener('change',
			function() {
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
<%@ include file="/views/common/footer.jsp"%>
