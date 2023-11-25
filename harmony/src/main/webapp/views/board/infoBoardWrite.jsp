<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%@ page
	import="java.util.List,com.harmony.board.info.model.dto.InfoBoard"%>
<%
List<InfoBoard> boards = (List<InfoBoard>) request.getAttribute("boards");
%>
<%@ include file="/views/common/header.jsp"%>

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/board/infoCommunityWrite.css"
	type="text/css">


<script type="text/javascript" src="<%=request.getContextPath()%>/board/smarteditor/js/service/HuskyEZCreator.js" charset="utf-8"></script>
    <script type="text/javascript" src="//code.jquery.com/jquery-1.11.0.min.js"></script>
    <script>function save() { oEditors.getById["txtContent"].exec("UPDATE_CONTENTS_FIELD", []); var content = document.getElementById("smartEditor").value; alert(document.getElementById("txtContent").value); return; }</script>
	
	
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

        <form id="postForm" action='<%=request.getContextPath()%>/board/boardWriteEnd.do' enctype="multipart/form-data"
            method="post" onsubmit="return submitPost()">
            <div class="infopostwrite">
                <h3>정보게시판 글쓰기</h3>
            </div>
            <div class="form-field-container">


                <div class="form-field1">
                    <select name="category" id="categorySelect">
                        <option value="" disabled selected>카테고리를 선택해주세요.</option>
                        <option value="cat1">공연</option>
                        <option value="cat2">입시</option>
                        <option value="cat3">오디션</option>
                        <option value="cat4">버스킹</option>
                    </select>
                </div>

                <div class="form-field2">
                    <select name="tag" id="tagSelect">
                        <option value="" disabled selected>태그 선택</option>
                        <option value="tag1">정보</option>
                        <option value="tag2">후기</option>
                        <option value="tag3">TIP</option>
                        <option value="tag4">이벤트</option>
                    </select>
                </div>

                <div class="form-field3">
                    <select name="region" id="regionSelect">
                        <option value="" disabled selected>지역 선택</option>
                        <option value="서울">서울</option>
                        <option value="경기도">경기도</option>
                        <option value="강원도">강원도</option>
                        <option value="충청북도">충청북도</option>
                        <option value="충청남도">충청남도</option>
                        <option value="전라북도">전라북도</option>
                        <option value="전라남도">전라남도</option>
                        <option value="경상북도">경상북도</option>
                        <option value="경상남도">경상남도</option>
                        <option value="제주도">제주도</option>
                    </select>
                </div>
            </div>

            <div class="write-title">
                <input type="text" id="post-title" name="title" placeholder="제목을 입력해 주세요.">
            </div>
            
            <div class="post-write">
                <textarea class="form-control" rows="20" name="bo_content" id="bo_content"
                    style="width:100%;"></textarea>
            </div>
            
            <div class="post-attach">
                    <label>이미지 # </label>
                <div class="post-input">
                    <input type="file" name="upfile" >
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
			
			var category = document.querySelector('select[name="category"]').value;
			if (category === "") {
				alert("카테고리를 선택해주세요.");
				return false;
			}

			var tag = document.querySelector('select[name="tag"]').value;
			if (tag === "") {
				alert("태그를 선택해주세요.");
				return false;
			}

			var region = document.querySelector('select[name="region"]').value;
			if (region === "") {
				alert("지역을 선택해주세요.");
				return false;
			}

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