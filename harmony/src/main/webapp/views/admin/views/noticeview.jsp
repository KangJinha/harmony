<%@page import="com.harmony.admin.model.dto.NoticeAttachFile"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.harmony.admin.model.dto.Notice, com.harmony.admin.model.dto.NoticeAttachFile" %>
<%@ include file="/views/admin/views/common/header.jsp" %>
<% Notice notice = (Notice)request.getAttribute("notice"); %>
        <main class="h-full pb-16 overflow-y-auto">
          <div class="container px-6 mx-auto grid">
	          <h2 class="my-6 text-2xl font-semibold text-gray-700 dark:text-gray-200">
	              공지사항게시판
	          </h2>
	          <div class="w-full bg-white whitespace-no-wrap rounded-lg px-2 py-1">
	          <table class="w-full">
	          	<thead class="bg-gray-50 text-gray-500">
	          		<tr class="tracking-wide">
	          			<th class="col px-3"colspan="4"><%=notice.getTitle() %></th>
	          		</tr>
	          		<tr class="border-b">
	          			<th class="col-auto px-3"><%=notice.getNoticeWriter()%></th>
	          			<th class="col-7">
	          			<th class="col-auto flex">
	          				<span class="inline-flex px-2 py-1 items-center">
	          				<svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="currentColor" class="bi bi-clock-history" viewBox="0 0 20 20">
							  <path d="M8.515 1.019A7 7 0 0 0 8 1V0a8 8 0 0 1 .589.022l-.074.997zm2.004.45a7.003 7.003 0 0 0-.985-.299l.219-.976c.383.086.76.2 1.126.342l-.36.933zm1.37.71a7.01 7.01 0 0 0-.439-.27l.493-.87a8.025 8.025 0 0 1 .979.654l-.615.789a6.996 6.996 0 0 0-.418-.302zm1.834 1.79a6.99 6.99 0 0 0-.653-.796l.724-.69c.27.285.52.59.747.91l-.818.576zm.744 1.352a7.08 7.08 0 0 0-.214-.468l.893-.45a7.976 7.976 0 0 1 .45 1.088l-.95.313a7.023 7.023 0 0 0-.179-.483zm.53 2.507a6.991 6.991 0 0 0-.1-1.025l.985-.17c.067.386.106.778.116 1.17l-1 .025zm-.131 1.538c.033-.17.06-.339.081-.51l.993.123a7.957 7.957 0 0 1-.23 1.155l-.964-.267c.046-.165.086-.332.12-.501zm-.952 2.379c.184-.29.346-.594.486-.908l.914.405c-.16.36-.345.706-.555 1.038l-.845-.535zm-.964 1.205c.122-.122.239-.248.35-.378l.758.653a8.073 8.073 0 0 1-.401.432l-.707-.707z"/>
							  <path d="M8 1a7 7 0 1 0 4.95 11.95l.707.707A8.001 8.001 0 1 1 8 0v1z"/>
							  <path d="M7.5 3a.5.5 0 0 1 .5.5v5.21l3.248 1.856a.5.5 0 0 1-.496.868l-3.5-2A.5.5 0 0 1 7 9V3.5a.5.5 0 0 1 .5-.5z"/>
							</svg>
	          				<%=notice.getWriteDate() %>
	          				</span>
          				</th>
	          			<th class="col-auto"><%=notice.getViewCount() %></th>
	          		</tr>
	          	</thead>
	          	<tbody class="border-b">
	          		<tr>
	          			<td colspan="4">
	          				<div class="container"  style="min-height: 300px">
							  <%if(notice.getAttachFileList().size()>0){ %>
	          					<div class="col d-flex justify-content-end">
	          						<div class="btn-group">
									  <button class="btn btn-secondary btn-sm dropdown-toggle d-flex items-center" type="button" data-bs-toggle="dropdown" aria-expanded="false">
									  <img src="<%=request.getContextPath()%>/image/admin/common/file.png" width="15" height="15">
									  첨부파일
									  </button>
									  <ul class="dropdown-menu">
									  <%for(NoticeAttachFile f : notice.getAttachFileList()){ %>
									  	<li><a class="dropdown-item" href="#"><%=f.getOriName() %> </a></li>
									  <%} %>
									  </ul>
									</div>
	          					</div>
							  <%} %>
		          				<%=notice.getContent() %>
	          				</div>
	          			</td>
	          		</tr>
	          	</tbody>
	          	<tfoot class="bg-gray-50 text-gray-500">
	          		<tr>
	          			<td colspan="4">
	          				<div class="col flex justify-content-end text-sm" name="buttonBar">
	                          <button type="button"  class="flex items-center justify-between px-2 py-2 text-sm font-medium leading-5 text-purple-600 rounded-lg dark:text-gray-400 focus:outline-none focus:shadow-outline-gray" name="editBtn" aria-label="Edit">
	                            <svg class="w-5 h-5" aria-hidden="true" fill="currentColor" viewBox="0 0 20 20">
	                              <path d="M13.586 3.586a2 2 0 112.828 2.828l-.793.793-2.828-2.828.793-.793zM11.379 5.793L3 14.172V17h2.828l8.38-8.379-2.83-2.828z"></path>
	                            </svg>
	                          </button>
	                          <button type="button" class="flex items-center justify-between px-2 py-2 text-sm font-medium leading-5 text-purple-600 rounded-lg dark:text-gray-400 focus:outline-none focus:shadow-outline-gray" name="deleteBtn" aria-label="Delete">
	                            <svg class="w-5 h-5" aria-hidden="true" fill="currentColor" viewBox="0 0 20 20">
	                              <path fill-rule="evenodd" d="M9 2a1 1 0 00-.894.553L7.382 4H4a1 1 0 000 2v10a2 2 0 002 2h8a2 2 0 002-2V6a1 1 0 100-2h-3.382l-.724-1.447A1 1 0 0011 2H9zM7 8a1 1 0 012 0v6a1 1 0 11-2 0V8zm5-1a1 1 0 00-1 1v6a1 1 0 102 0V8a1 1 0 00-1-1z" clip-rule="evenodd"></path>
	                            </svg>
	                          </button>
	                        </div>
	          			</td>
	          		</tr>
	          	</tfoot>
	          </table>
          </div>
          </div>
		</main>
		<script>
			const viewno = '<%=notice.getNoticeNo()%>';
        	const editPath = '<%=request.getContextPath()%>/admin/notice/write.do';
        	const deletePath = '<%=request.getContextPath()%>/admin/notice/delete.do';
        </script>
        <script src="<%=request.getContextPath()%>/views/admin/assets/js/boardView.js"></script>
<%@ include file="/views/admin/views/common/footer.jsp" %>