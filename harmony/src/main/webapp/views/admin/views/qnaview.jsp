<%@page import="com.harmony.admin.model.dto.NoticeAttachFile"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List,com.harmony.admin.model.dto.Qna"%>
<%@ include file="/views/admin/views/common/header.jsp"%>
<%
Qna qna = (Qna) request.getAttribute("qna");
List<String[]> category = (List<String[]>) request.getAttribute("catList");
List<String[]> process = (List<String[]>) request.getAttribute("proList");
%>
<main class="h-full pb-16 overflow-y-auto">
	<div class="container px-6 mx-auto grid">
		<h2
			class="my-6 text-2xl font-semibold text-gray-700 dark:text-gray-200">
			문의사항게시판</h2>
		<div class="w-full bg-white whitespace-no-wrap rounded-lg px-2 py-1">
			<form action="<%=request.getContextPath() %>/admin/qna/write.do" method="post" onsubmit="frmValidate(event);">
				<div class="container">
					<div class="row mb-3 border-b px-2 py-1">
						<div class="col-sm-4 mb-3">
							<div class="input-group input-group-sm">
								<span class="input-group-text">No.</span>
								<input class="form-control" type="text" class="form-control" name="qstNo" value="<%=qna.getQstNo()%>" readOnly>
							</div>
						</div>
						<div class="col-sm-4 mb-3">
							<div class="input-group input-group-sm">
								<span class="input-group-text">Cat.</span>
								<select class="form-select" name="category" disabled>
									<%
									for (String[] l : category) {
									%>
									<option value="<%=l[0]%>"
										<%=l[0].equals(qna.getCatNo()) ? "selected" : ""%>><%=l[1]%></option>
									<%
									}
									%>
								</select>
							</div>
						</div>
						<div class="col-sm-4 mb-3">
							<div class="input-group input-group-sm">
								<span class="input-group-text">process.</span> <select
									class="form-select" name="process">
									<%
									for (String[] l : process) {
									%>
									<option value="<%=l[0]%>"
										<%=l[0].equals(qna.getProcessCode()) ? "selected" : ""%>><%=l[1]%></option>
									<%
									}
									%>
								</select>
							</div>
						</div>
						<div class="col-sm-4 mb-3">
							<div class="input-group input-group-sm">
								<span class="input-group-text">작성일</span>
								<input type="date" class="form-control" name="writeDate" value="<%=qna.getWriteDate()%>" disabled>
							</div>
						</div>
						<div class="col-sm-4 mb-3">
							<div class="input-group input-group-sm">
								<span class="input-group-text">답변일</span>
								<input type="date" class="form-control" name="answerwriteDate" value="<%=qna.getAnsDate()%>" disabled>
							</div>
						</div>
						<div class="col-sm-4 mb-3">
							<div class="input-group input-group-sm">
								<span class="input-group-text">작성자</span>
								<input type="text" class="form-control" name="writer" value="<%=qna.getQstWriter()%>" disabled>
							</div>
						</div>
					</div>
				</div>
				<div class="container" >
					<div class="row">
						<div class="col-lg-6 border-b" style="min-height: 300px;"><%=qna.getContent()%></div>
						<div class="col-lg-6 border-b" style="min-height: 300px;">
							<textarea class="form-control w-full" name="ansCont" rows="11" style="resize: none;"><%=qna.getAnswerContent()!=null?qna.getAnswerContent():""%></textarea>
						</div>
					</div>
				</div>
				<div class="row container flex ">
					<div class="col items-center flex justify-content-end">
						<button type="submit" class="btn btn-warning">답변하기</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</main>
<script>
</script>
<%@ include file="/views/admin/views/common/footer.jsp"%>