<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.harmony.admin.model.dto.QNAList" %>
<%@ include file="/views/admin/views/common/header.jsp" %>
<% List<QNAList> qnalist = (List<QNAList>)request.getAttribute("QNAList"); %>
<% String pageBar = (String)request.getAttribute("pageBar"); %>
        <main class="h-full pb-16 overflow-y-auto">
          <div class="container px-6 mx-auto grid">
	          <h2 class="my-6 text-2xl font-semibold text-gray-700 dark:text-gray-200">
	              문의사항게시판
	          </h2>
          </div>
		  <div class="w-full bg-white whitespace-no-wrap rounded-lg px-2 py-1" style="min-height: 300px">
	          <table class="table table-white table-hover">
	          	<thead>
          		<tr class="text-xs font-semibold text-center">
          			<th class="col" scope="col">NO</th>
          			<th class="col" scope="col">CATEGORY</th>
          			<th class="col" scope="col">WRITER</th>
          			<th class="col" scope="col">PROCESS</th>
          			<th class="col" scope="col">DATE</th>
          		</tr>
          	</thead>
          	<tbody>
          		<%if(qnalist!=null&&qnalist.size()>0){ %>
          		<%for(QNAList q:qnalist){ %>
          		<tr class="text-xs">
          			<th class="text-center" scope="row"><%=q.getQstNo() %></td>
          			<td class="text-center"><%=q.getQstCategoryName() %></td>
          			<td class="text-center"><%=q.getQstWriter() %></td>
          			<td class="text-center">
          				<%switch(q.getQstCode()){
          				case "100": %>
          					<span class="px-2 py-1 font-semibold leading-tight text-gray-700 bg-gray-100 rounded-full dark:text-gray-100 dark:bg-gray-700">확인중</span>
          				<% break;
          				case "200": %>
          					<span class="px-2 py-1 font-semibold leading-tight text-green-700 bg-green-100 rounded-full dark:bg-green-700 dark:text-green-100">처리완료</span >
       					<%	break;
          				case "300": %>
          					<span class="px-2 py-1 font-semibold leading-tight text-orange-700 bg-orange-100 rounded-full dark:text-white dark:bg-orange-600">처리중</span>
          				<% break;
          				} %>
          			</td>
          			<td class="text-center"><%=q.getWriteDate() %></td>
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
          				
          			</td>
          		</tr>
          	</tfoot>
	          </table>
          </div>          
        </main>
        <script>
        	const viewPath = '<%=request.getContextPath()%>/admin/qna.do';
        	const writePath = '<%=request.getContextPath()%>/admin/qna/write.do';
        </script>
        <script src="<%=request.getContextPath()%>/views/admin/assets/js/boardList.js"></script>
<%@ include file="/views/admin/views/common/footer.jsp" %>