<%@page import="com.harmony.admin.model.dto.AdminMember"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="
com.harmony.admin.model.dto.Notice, com.harmony.admin.model.dto.NoticeAttachFile,
com.harmony.admin.model.dto.AdminMember" %>
<%@ include file="/views/admin/views/common/header.jsp" %>
<% Notice notice = (Notice)request.getAttribute("notice"); %>
<% AdminMember loginAdmin = (AdminMember)request.getSession().getAttribute("loginAdmin"); %>
	<style>
		.ck-editor__editable[role="textbox"] {
            /* editing area */
            min-height: 200px;
        }
        .ck-content .image {
            /* block images */
            max-width: 80%;
            margin: 20px auto;
        }
	</style>
        <main class="h-full pb-16 overflow-y-auto">
          <div class="container px-6 mx-auto grid">
	          <h2 class="my-6 text-2xl font-semibold text-gray-700 dark:text-gray-200">
	              공지사항게시판
	          </h2>
	          <div class="w-full bg-white whitespace-no-wrap rounded-lg px-2 py-1">
	          <form action="<%=request.getContextPath() %>/admin/notice/write.do" method="post" enctype="multipart/form-data">
	          <input type="hidden"  name="type" value="<%=request.getAttribute("type")%>" readOnly>
   	          <input type="hidden"  name="no" value="<%=notice!=null?notice.getNoticeNo():""%>" readOnly>
	          <input type="hidden"  name="writer" value="<%=notice!=null?notice.getNoticeWriter():loginAdmin.getAdminNo()%>" readOnly>
	          <table class="w-full">
	          	<thead class="bg-gray-50 text-gray-500">
	          		<tr class="tracking-wide border-b">
	          			<th class="col px-3 input-group"colspan="4"><span class="input-group-text">제목</span><input type="text" class="form-control" name="title" value="<%=notice!=null?notice.getTitle():"" %>" required></th>
	          		</tr>
	          	</thead>
	          	<tbody class="border-b">
	          		<tr>
	          			<td colspan="4">
	          				<div class="container" style="min-height: 300px">
	          					<div class="row justify-content-between">
	          					<div class="col-sm-2">AttachFile</div>
	          					<div class="col-sm-8 inline-flex justify-content-end">
	          					<div class="row d-flex justify-content-end">
	          					<%if(notice!=null&&notice.getAttachFileList().size()>0){ %>
	          						<div id="delFileChkboxList">
									  <%for(NoticeAttachFile f : notice.getAttachFileList()){ %>
									  <input type="checkbox" name="delFile" value="<%=f.getNoticeFileNo() %>" style="display:none;" data-bs-value="<%=f.getNoticeFileNo() %>">
									  <input type="checkbox" name="dFileName" value="<%=f.getReName() %>" style="display:none;" data-bs-value="<%=f.getNoticeFileNo() %>">
									  <%} %>
								  </div>
							  	<%} %>
		          						<ul class="row" id="attachFileList">
								  <%if(notice!=null&&notice.getAttachFileList().size()>0){ %>
									  <%for(NoticeAttachFile f : notice.getAttachFileList()){ %>
		          							<li class="inline-flex justify-content-end items-center w-full" value="<%=f.getNoticeFileNo() %>">
			          							<img src="<%=request.getContextPath()%>/image/admin/common/file.png" width="15" height="15">
				          						<span><%=f.getOriName() %></span>
				          						<button type="button" name="delFileBtn" onclick="addDelfile(event);">
				          							<svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" fill="#fc3f3f" class="bi bi-x-square-fill" viewBox="0 0 16 16">
													  <path d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2zm3.354 4.646L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 1 1 .708-.708z"/>
													</svg>
				          						</button>
		          							</li>
									  <%} %>
							  	<%} %>
									  </ul>
								</div>
									  <div class="inline-flex items-center mx-3">
									  <a class="btn btn-success btn-lg" name="addFileBtn" onclick="addfile(event);">
			          						<svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" fill="#ffffff" class="bi bi-file-earmark-plus-fill" viewBox="0 0 16 16">
											  <path d="M9.293 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V4.707A1 1 0 0 0 13.707 4L10 .293A1 1 0 0 0 9.293 0zM9.5 3.5v-2l3 3h-2a1 1 0 0 1-1-1zM8.5 7v1.5H10a.5.5 0 0 1 0 1H8.5V11a.5.5 0 0 1-1 0V9.5H6a.5.5 0 0 1 0-1h1.5V7a.5.5 0 0 1 1 0z"/>
											</svg>
											</a>
									  </div>
	          					</div>
	          					</div>
	          					<textarea name="content" id="editor">
		          				<%=notice!=null?notice.getContent():"" %>
		          				</textarea>
		          				</div>
	          				</div>
	          			</td>
	          		</tr>
	          	</tbody>
	          	<tfoot class="bg-gray-50 text-gray-500">
	          		<tr>
	          			<td colspan="4">
	          				<div class="col flex justify-content-end text-sm" name="buttonBar">
	                          <button type="submit"  class="flex items-center justify-between px-2 py-2 text-sm font-medium leading-5 text-purple-600 rounded-lg dark:text-gray-400 focus:outline-none focus:shadow-outline-gray" name="editBtn" aria-label="Edit">
	                            <svg class="w-5 h-5" aria-hidden="true" fill="currentColor" viewBox="0 0 20 20">
	                              <path d="M13.586 3.586a2 2 0 112.828 2.828l-.793.793-2.828-2.828.793-.793zM11.379 5.793L3 14.172V17h2.828l8.38-8.379-2.83-2.828z"></path>
	                            </svg>
	                          </button>
	                        </div>
	          			</td>
	          		</tr>
	          	</tfoot>
	          </table>
	          </form>
          </div>
          </div>
		</main>
		<script src="<%=request.getContextPath()%>/api/ckeditor5/build/ckeditor.min.js"></script>
		<script>
			ClassicEditor
				.create(document.querySelector('#editor'),{
					simpleUpload: {
			            // The URL that the images are uploaded to.
			            uploadUrl: 'http://example.com',

			            // Enable the XMLHttpRequest.withCredentials property.
			            withCredentials: true,

			            // Headers sent along with the XMLHttpRequest to the upload server.
			            headers: {
			                'X-CSRF-TOKEN': 'CSRF-Token',
			                Authorization: 'Bearer <JSON Web Token>'
			            }
			        }
				})
				.catch( error=>{
					console.log(error);
				});
		</script>
		<script>
		const contextpath = '<%=request.getContextPath()%>';
		</script>
		<script src="<%=request.getContextPath()%>/views/admin/assets/js/boardWrite.js"></script>
<%@ include file="/views/admin/views/common/footer.jsp" %>