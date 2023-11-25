<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/views/admin/views/common/header.jsp" %>
        <main class="h-full pb-16 overflow-y-auto">
          <!-- Remove everything INSIDE this div to a really blank page -->
          <div class="container px-6 mx-auto grid">
	          <h2 class="my-6 text-2xl font-semibold text-gray-700 dark:text-gray-200">
	              이음줄 관리자페이지에 오신것을 환영합니다.
	          </h2>
          	<img src="<%=request.getContextPath()%>/views/admin/assets/img/login-office.jpeg" style="width:70%;">
          </div>
        </main>
<%@ include file="/views/admin/views/common/footer.jsp" %>