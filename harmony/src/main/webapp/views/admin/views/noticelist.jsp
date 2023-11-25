<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List,com.harmony.admin.model.dto.NoticeList" %>
<%@ include file="/views/admin/views/common/header.jsp" %>
<% List<NoticeList> noticeList = (List<NoticeList>)request.getAttribute("noticeList"); %>
<% String pageBar = (String)request.getAttribute("pageBar"); %>
        <main class="h-full pb-16 overflow-y-auto">
          <div class="container px-6 mx-auto grid">
	          <h2 class="my-6 text-2xl font-semibold text-gray-700 dark:text-gray-200">
	              공지사항게시판
	          </h2>
          </div>
          <div class="w-full bg-white whitespace-no-wrap rounded-lg px-2 py-1" style="min-height: 300px">
          <table class="table table-white table-hover">
          	<thead>
          		<tr class="text-xs font-semibold text-center">
          			<th class="col" scope="col">NO</th>
          			<th class="col-md-8" scope="col">TITLE</th>
          			<th class="col" scope="col">WRITER</th>
          			<th class="col" scope="col">DATE</th>
          			<th class="col" scope="col">VIEWS</th>
          			<th class="col" scope="col">FILE</th>
          		</tr>
          	</thead>
          	<tbody>
          		<%if(noticeList!=null&&noticeList.size()>0){ %>
          		<%for(NoticeList n:noticeList){ %>
          		<tr class="text-xs">
          			<th class="text-center" scope="row"><%=n.getNoticeNo() %></td>
          			<td class="text-truncate"><%=n.getTitle() %></td>
          			<td class="text-center"><%=n.getWriter() %></td>
          			<td class="text-center"><%=n.getWriteDate() %></td>
          			<td class="text-center"><%=n.getViewCount() %></td>
          			<td class=""><%=n.isHasFile()?"<img src=\""+request.getContextPath()+"/image/admin/common/file.png\" style=\"width:15px;height:15px;\" alt=\"첨부파일있음\">":"" %></td>
          		</tr>
          		<%}}else{ %>
          		<tr>
          			<td class="text-sm-center" colspan="6">검색결과가 존재하지 않습니다.</td>
          		</tr>
          		<%} %>
          	</tbody>
          	<tfoot>
          		<tr>
          			<td colspan="5">
          				<%=pageBar %>
          			</td>
          			<td>
          				<button type="button"  class="flex items-center justify-between px-2 py-2 text-sm font-medium leading-5 text-purple-600 rounded-lg dark:text-gray-400 focus:outline-none focus:shadow-outline-gray" name="writeBtn" aria-label="write">
                           <svg class="w-5 h-5" aria-hidden="true" fill="currentColor" viewBox="0 0 20 20">
                             <path d="M13.586 3.586a2 2 0 112.828 2.828l-.793.793-2.828-2.828.793-.793zM11.379 5.793L3 14.172V17h2.828l8.38-8.379-2.83-2.828z"></path>
                           </svg>
	                    </button>
          			</td>
          		</tr>
          	</tfoot>
          </table>
          </div>
        </main>
        <script>
        	const viewPath = '<%=request.getContextPath()%>/admin/notice.do';
        	const writePath = '<%=request.getContextPath()%>/admin/notice/write.do';
        </script>
        <script src="<%=request.getContextPath()%>/views/admin/assets/js/boardList.js"></script>
<%@ include file="/views/admin/views/common/footer.jsp" %>