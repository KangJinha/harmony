<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List,com.harmony.admin.model.dto.Carousel" %>
<%@ include file="/views/admin/views/common/header.jsp" %>
<% List<Carousel> carousels = (List<Carousel>)request.getAttribute("carousels"); %>
        <main class="h-full pb-16 overflow-y-auto">
          <div class="container px-6 mx-auto grid">
	          <h2 class="my-6 text-2xl font-semibold text-gray-700 dark:text-gray-200">
	              메인화면 캐러셀 관리
	          </h2>
	          <h4 class="mb-4 text-lg font-semibold text-gray-600 dark:text-gray-300">
              	효율적인 표시를 위해 게시되는 캐러셀의 수를 5개 정도로 유지해주세요
              <button class="btn btn-warning" onclick="addNewBlankCarousel();">캐러셀 추가하기</button>
              </h4>
	          <div class="row row-cols-2 w-full whitespace-no-wrap" id="cardcontainer">
	           	<% if(carousels !=null && carousels.size()>0){ %>
	           	<%for(Carousel c : carousels){ %>
	          	<div class="card col" id="card_<%=c.getCrslNo()%>">
	          		<form method="post" enctype="multipart/form-data" class="align-items-center needs-validation" novalidate onchange="frmchange(event);" onsubmit="validate(event,'edit');">
	          			<div class="card-header input-group input-group-sm">
          					<span class="input-group-text">관리번호</span>
          					<input class="form-control form-control-plaintext" type="number" name="crslNo" value="<%=c.getCrslNo()%>" style="width:50px" readOnly>
          					<span class="input-group-text">이름</span>
          					<input class="form-control" type="text" name="crslName" placeholder="이름을입력해주세요" value="<%=c.getCrslName()%>">
	          			</div>
	          			<input type="hidden" name="oldImage" value="<%=c.getCrslImgLink() %>">
		          		<img class="card-img-top" src="<%=request.getContextPath()+"/image/carousel/"+c.getCrslImgLink()%>" alt="carousel_<%=c.getCrslName()%>_image">
		          		<ul class="list-group list-group-flush">
		          			<li class="list-group-item">
		          				<div class="input-group input-group-sm">
			          				<span class="input-group-text">이미지수정</span>
			          				<input class="form-control" type="file" name="newImage" placeholder="이미지변경시에만 입력해주세요" accept="image/*"  onchange="changeThumbnail(event);">
			          			</div>
		          			</li>
		          			<li class="list-group-item">
		          				<div class="input-group input-group-sm">
			          				<span class="input-group-text">게시일</span>
			          				<input class="form-control" type="date" name="startDate" placeholder="게시시작일" value="<%=c.getCrslWriteDate()%>" required>
			          				<span class="input-group-text">~</span>
			          				<input class="form-control" type="date" name="endDate" placeholder="게시종료일" value="<%=c.getCrslEndDate()%>" required>
			          			</div>
		          			</li>
		          			<li class="list-group-item">
		          				<div class="input-group input-group-sm">
			          				<span class="input-group-text">표시순서</span>
			          				<input class="form-control" type="number" name="viewrank" placeholder="순번" value="<%=c.getCrslViewRank()%>">
			          				<span class="input-group-text">표시시간</span>
			          				<input class="form-control" type="number" name="intervalms" placeholder="유지시간 단위 ms" value="<%=c.getCrslIntervalMs()%>">
			          				<span class="input-group-text">ms</span>
			          			</div>
		          			</li>
		          			<li class="list-group-item">
		          				<div class="input-group input-group-sm">
			          				<span class="input-group-text">페이지링크</span>
			          				<input class="form-control" type="text" name="pagelink" placeholder="클릭시 이동할 주소를 입력해주세요" value="<%=c.getCrslPageLink()%>">
			          			</div>
		          			</li>
		          			<li class="list-group-item">
		          				<div class="row justify-content-between">
		          					<div class="col-2">
		          					<%switch(c.getStatus()){
		          					case "PENDING":%> 
		          						<span class="px-2 py-1 font-semibold leading-tight text-orange-700 bg-orange-100 rounded-full dark:text-white dark:bg-orange-600">게시대기중</span>
		          						<%break;
                        			case "ACTIVE":%> 
                        				<span class="px-2 py-1 font-semibold leading-tight text-green-700 bg-green-100 rounded-full dark:bg-green-700 dark:text-green-100">게시중</span>
                        				<%break;
                        			case "EXPIRED":%>
                        				<span class="px-2 py-1 font-semibold leading-tight text-gray-700 bg-gray-100 rounded-full dark:text-gray-100 dark:bg-gray-700">게시종료됨</span>
                        			<%break;} %>
                        			</div>
			          				<div class="col-2 flex justify-content-end text-sm" name="buttonBar">
			          				  <button type="button"  class="flex items-center justify-between px-2 py-2 text-sm font-medium leading-5 text-purple-600 rounded-lg dark:text-gray-400 focus:outline-none focus:shadow-outline-gray" name="resetBtn" aria-label="Reset" onclick="doreset(event);">
			                            <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="w-5 h-5 bi bi-arrow-repeat" viewBox="0 0 20 20">
										  <path d="M11.534 7h3.932a.25.25 0 0 1 .192.41l-1.966 2.36a.25.25 0 0 1-.384 0l-1.966-2.36a.25.25 0 0 1 .192-.41zm-11 2h3.932a.25.25 0 0 0 .192-.41L2.692 6.23a.25.25 0 0 0-.384 0L.342 8.59A.25.25 0 0 0 .534 9z"/>
										  <path fill-rule="evenodd" d="M8 3c-1.552 0-2.94.707-3.857 1.818a.5.5 0 1 1-.771-.636A6.002 6.002 0 0 1 13.917 7H12.9A5.002 5.002 0 0 0 8 3zM3.1 9a5.002 5.002 0 0 0 8.757 2.182.5.5 0 1 1 .771.636A6.002 6.002 0 0 1 2.083 9H3.1z"/>
										</svg>
			                          </button>
			                          <button type="submit"  class="flex items-center justify-between px-2 py-2 text-sm font-medium leading-5 text-purple-600 rounded-lg dark:text-gray-400 focus:outline-none focus:shadow-outline-gray" name="editBtn" aria-label="Edit">
			                            <svg class="w-5 h-5" aria-hidden="true" fill="currentColor" viewBox="0 0 20 20">
			                              <path d="M13.586 3.586a2 2 0 112.828 2.828l-.793.793-2.828-2.828.793-.793zM11.379 5.793L3 14.172V17h2.828l8.38-8.379-2.83-2.828z"></path>
			                            </svg>
			                          </button>
			                          <button type="button" class="flex items-center justify-between px-2 py-2 text-sm font-medium leading-5 text-purple-600 rounded-lg dark:text-gray-400 focus:outline-none focus:shadow-outline-gray" name="deleteBtn" aria-label="Delete" onclick="doajax(event,'delete');">
			                            <svg class="w-5 h-5" aria-hidden="true" fill="currentColor" viewBox="0 0 20 20">
			                              <path fill-rule="evenodd" d="M9 2a1 1 0 00-.894.553L7.382 4H4a1 1 0 000 2v10a2 2 0 002 2h8a2 2 0 002-2V6a1 1 0 100-2h-3.382l-.724-1.447A1 1 0 0011 2H9zM7 8a1 1 0 012 0v6a1 1 0 11-2 0V8zm5-1a1 1 0 00-1 1v6a1 1 0 102 0V8a1 1 0 00-1-1z" clip-rule="evenodd"></path>
			                            </svg>
			                          </button>
			                        </div>
		                        </div>
		          			</li>
		          		</ul>
	          		</form>
	          	</div>
	          	<%}} %>
	          </div>
          </div>
        </main>
        <script>
        	const contextPath = "<%=request.getContextPath()%>";
        </script>
        <script src="<%=request.getContextPath()%>/views/admin/assets/js/carouselManage.js"></script>
<%@ include file="/views/admin/views/common/footer.jsp" %>