<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
<%@ page import="com.harmony.board.free.model.dto.FreeBoard"%>
<%
FreeBoard board = (FreeBoard) request.getAttribute("board");
%>
<%@ include file="/views/common/header.jsp"%>

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/board/freeCommunityEdit.css"
	type="text/css">

<script type="text/javascript"
	src="<%=request.getContextPath()%>/board/smarteditor/js/service/HuskyEZCreator.js"
	charset="utf-8"></script>
<script type="text/javascript"
	src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script>
	function save() {
		oEditors.getById["txtContent"].exec("UPDATE_CONTENTS_FIELD", []);
		var content = document.getElementById("smartEditor").value;
		alert(document.getElementById("txtContent").value);
		return;
	}
</script>

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

		<form id="postForm"
			action='<%=request.getContextPath()%>/board/FreeBoardEditEnd.do'
			method="post" enctype="multipart/form-data"
			onsubmit="return submitPost()">

			<div class="freepostwrite">
				<h3>자유게시판 글수정</h3>
			</div>

			<input type="hidden" name="no" value="<%=board.getFreBrdNo()%>">
			
			<div class="write-title">
				<input type="text" id="post-title" name="title" style="width: 100%;"
					value="<%=board.getFreBrdTitle()%>">
			</div>

			<div class="post-write">
				<textarea class="form-control" rows="20" name="bo_content"
					id="bo_content" style="width: 100%;"><%=board.getFreBrdContent()%></textarea>
			</div>

			<div class="post-attach">
				<label>이미지 # </label>
				<div class="post-input">
					<%
					if (board.getFreBrdTitleImg() != null && !board.getFreBrdTitleImg().isEmpty()) {
					%>
					<p>
						현재 이미지:
						<%=board.getFreBrdTitleImg()%></p>
					<%
					}
					%>
					<p>
						이미지 수정: <input type="file" name="upfile" />
					</p>

				</div>
			</div>
			<div class="post-submit">
				<button type="submit" class="submit-post-button">등록</button>
			</div>

		</form>
	</div>
</div>
</div>

<script>

var oEditors = [];

nhn.husky.EZCreator.createInIFrame({
    oAppRef: oEditors,
    elPlaceHolder: "bo_content",
    sSkinURI: "<%=request.getContextPath()%>/board/smarteditor/SmartEditor2Skin.html",
    fCreator: "createSEditor2"
});

	function submitPost() {
		oEditors.getById["bo_content"].exec("UPDATE_CONTENTS_FIELD", []);

		var title = document.getElementById("post-title").value.trim();
		if (title === "") {
			alert("제목을 입력해주세요.");
			return false;
		}

		var content = document.getElementById("bo_content").value.trim();
		if (content === "") {
			alert("본문을 입력해주세요.");
			return false;
		}

		return true;
	}
</script>

<%@ include file="/views/common/footer.jsp"%>
