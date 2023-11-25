<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ include file="/views/common/header.jsp"%>

<section>
	<div class="container text-center">
		<div class="row align-items-center">
	        <h2 class="h2 col">에러가 발생했습니다!</h2>
        </div>
		<div class="row align-items-center">
			<svg class="col" fill="#ffc107" viewBox="0 0 20 20" style="width:100px; height:100px;">
	              <path fill-rule="evenodd" d="M13.477 14.89A6 6 0 015.11 6.524l8.367 8.368zm1.414-1.414L6.524 5.11a6 6 0 018.367 8.367zM18 10a8 8 0 11-16 0 8 8 0 0116 0z" clip-rule="evenodd"></path>
	        </svg>
		</div>
		<div class="row align-items-center">
	        <h2 class="h2 col"><%=exception!=null?exception.getMessage():"" %></h2>
        </div>
        <div class="row align-items-center">
	        <p class="col">관리자에게 문의해주세요.</p>
        </div>
	</div>
</section>

<%@ include file="/views/common/footer.jsp"%>