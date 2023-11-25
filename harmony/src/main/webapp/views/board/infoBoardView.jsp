<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List,com.harmony.board.info.model.dto.InfoBoard,com.harmony.board.info.model.dto.InfoCommentBoard,com.harmony.model.dto.Member" %>
<%
InfoBoard board = (InfoBoard) request.getAttribute("InfoBoard");
List<InfoCommentBoard> comments = (List<InfoCommentBoard>) request.getAttribute("comments");
%>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<%@ include file="/views/common/header.jsp"%>
<%
    String loggedInUser = loginMember != null ? loginMember.getMemNo() : null;
%>

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/board/infoboardView.css"
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

		<div class="infoboardviewcontainer">
			<div class="infoboardview">
				<div class="header">
					<div class="boradselect">
						<a href="<%=request.getContextPath()%>/infoBoardList.do">정보 게시판</a> > <a href="#"><%= request.getAttribute("categoryName") %></a>
					</div>
					<div class="title">
						<h1>
							<span class="highlight">[<%= request.getAttribute("tagName") %>]</span>
							<%=board.getInfBrdTitle()%></h1>
						<p>
							지역 |
							<%=board.getInfBrdRegion()%></p>
					</div>
				</div>

				<div class="user-profile">
					<div class="profile-info">
						<img
							src="<%=request.getContextPath()%>/image/board/write-user.png"
							class="write-user"> <span>작성자 | <%=board.getInfBrdWriter()%></span>
					</div>
					<div class="profile-icon">
						<span> <a href="#comments-section"><img
								src="<%=request.getContextPath()%>/image/board/comment3.png"></a></span>
					</div>
				</div>

				<div class="post-content">
                <p><%=board.getInfBrdContent()%></p>

                <% if (board.getInfBrdTitleImg() != null && !board.getInfBrdTitleImg().isEmpty()) { %>
                    <div class="attached-image">
                        <img src="<%=request.getContextPath()%>/upload/board/<%=board.getInfBrdTitleImg()%>">
                    </div>
                <% } %>
                </div>

				<div class="post-buttons">
    <% 
    // 게시글 작성자 또는 어드민"temp001"인 경우에만 수정, 삭제 버튼 표시
    if ("temp001".equals(loggedInUser) || board.getInfBrdWriter().equals(loggedInUser)) { 
    %>
        <button class="post-edit-btn" onclick="editBoard(<%=board.getInfBrdNo()%>)">글 수정</button>
        <button class="post-delete-btn" onclick="confirmDeletion(<%=board.getInfBrdNo()%>)">글 삭제</button>
    <% } %>
</div>

				<div class="comment-container" id="comments-section">
					<div class="comment-form">
						<div class="comments-view">
							<h2>댓글</h2>
						</div>


						<div class="comment-editor">
							<form class="comment-editor-form"
								action="<%=request.getContextPath()%>/board/insertComment.do"
								method="post">
								<input type="hidden" name="boardRef"
									value="<%=board.getInfBrdNo()%>"> <input type="hidden"
									name="level" value="1"> <input type="hidden"
									name="infComNoRef" value=""> <input type="text"
									id="post-comment" name="content" placeholder="댓글을 입력해주세요.">

								<button type="submit" id="btn-insert" class="comment-btn">등록</button>
							</form>
						</div>




						<%if (comments != null && !comments.isEmpty()) { %>
						<div class="comment-text-buttons">
							<%for (InfoCommentBoard comment : comments) {
								if (comment.getInfComLevel() == 1) {%>
							<div class="comment-content">
								<div>
									<img
										src="<%=request.getContextPath()%>/image/board/comment_user.png"
										class="write-user"> <span><%=comment.getInfComWriter()%>
										| <%=board.getInfBrdRegion()%></span>
								</div>
								<div class="comment_buttons">
									<div class="comment-text">
										<p><%=comment.getInfComContent()%></p>
									</div>
									<div class="buttons-container">
               
                    <button class="btn-reply" value="<%=comment.getInfComNo()%>">답글</button>
                     <% 
                // 댓글 작성자 또는 어드민인 경우에만 수정, 삭제 버튼 표시
                if ("temp001".equals(loggedInUser) || comment.getInfComWriter().equals(loggedInUser)) { 
                %>
                    <button class="delete-btn"
                        onclick="confirmCommentDeletion('<%=request.getContextPath()%>/board/deleteComment.do', <%=comment.getInfComNo()%>, <%=board.getInfBrdNo()%>)">삭제</button>
                <% } %>
            </div>
								</div>
							</div>
							<%} else {%>
							<div class="reply-container">
								<div class="reply-user">
									<img src="<%=request.getContextPath()%>/image/board/comment_user.png"> <span><%=comment.getInfComWriter()%>
										| <%=board.getInfBrdRegion()%></span>
								</div>
								<div class="reply-comment">
									<p><%=comment.getInfComContent()%></p>
									<div class="buttons-container">
									   <% 
    // 게시글 작성자 또는 어드민"temp001"인 경우에만 수정, 삭제 버튼 표시
    if ("temp001".equals(loggedInUser) || comment.getInfComWriter().equals(loggedInUser)) { 
    %>
										<button class="delete-btn"
											onclick="confirmCommentDeletion('<%=request.getContextPath()%>/board/deleteComment.do', <%=comment.getInfComNo()%>, <%=board.getInfBrdNo()%>)">삭제</button>
									   <% } %>
									</div>
								</div>
							</div>
								<%}
								}%>
							<%}%>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script>
	function editBoard(boardNo) {
	    if(confirm('게시글을 수정하시겠습니까?')) {
	        window.location.href = '<%=request.getContextPath()%>/board/editBoard.do?no=' + boardNo;
	    }
	}
	
	function confirmDeletion(boardNo) {
	    if(confirm('게시글을 삭제하시겠습니까?')) {
	        window.location.href = '<%=request.getContextPath()%>/board/deleteBoard.do?no=' + boardNo;
	    }
	}
	function confirmCommentDeletion(deleteUrl, commentNo, boardNo) {
	    if (confirm('댓글을 삭제하시겠습니까?')) {
	        window.location.href = deleteUrl + '?no=' + commentNo + '&boardNo=' + boardNo;
	    }
	}

	$(".btn-reply").click(e => {
	    // 답글 입력창이 이미 존재하면 함수를 종료
	    if ($(e.target).closest(".comment-content").next().hasClass("reply-form")) {
	        return;
	    }

	    // 새로운 reply-form div 생성
	    const $div = $("<div>").addClass("reply-form");
	    const $form = $(".comment-editor>form").clone();
	    console.log($form);
	    $form.find("input[name=level]").val("2"); // 대댓글 번호 설정
	    $form.find("textarea").attr("rows", "1"); // 답글 입력창 크기 조정
	    $form.find("button").removeAttr("id").addClass("btn-insert2"); // 버튼 ID 제거 및 클래스 추가
	    $form.find("input[name=infComNoRef]").val($(e.target).val()); // 대댓글 참조 번호 설정
	    $div.append($form);

	    // 해당 comment-content 다음에 reply-form div 삽입
	    $(e.target).closest(".comment-content").after($div);
	});


	    </script>

	<%@ include file="/views/common/footer.jsp"%>