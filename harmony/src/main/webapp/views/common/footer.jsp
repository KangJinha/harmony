<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<footer class="container bg-yellow " id="harmony_footer">
			<div class="row">
				<div class="col-md-4 pt-5">
					<h2 class="border-bottom pb-3 border-light">이음줄</h2>
					<ul class="list-unstyled footer-link-list">
						<li> 연락처 </li>
						<li> 평일 10:00 ~ 18:00 </li>
						<li> 점심시간 12:50 ~ 13:50 제외</li>
						<li> 주말/공휴일 제외</li>
						<li> <a href="<%=request.getContextPath() %>/admin/">관리자페이지</a></li>
					</ul>
				</div>
				<div class="col-md-4 pt-5">
					<h2 class="border-bottom pb-3 border-light">팀원</h2>
					<ul class="list-unstyled footer-link-list">
						<li> 이재연 : ljydev6@gmail.com</li>
						<li> 강진하 : plutory23@gmail.com</li>
						<li> 이보연 : stuctu99@gmail.com</li>
						<li> 최경현 : ckhapf@gmail.com</li>
						<li> 유채화 : ych6681@gmail.com</li>
					</ul>
				</div>
				<div class="col-md-4 pt-5">
				</div>
		</div>
	</footer>
	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/message/message.css">
	<div class="modal fade" id="modalMessageBox" tabindex="-1"
	aria-labelledby="exampleModalLabel" aria-hidden="true" >
		<div
			class="modal-dialog modal-dialog-centered modal-dialog-scrollable modal-xl">
			<div class="modal-content">
				<div class="modal-header px-3 py-2">
					<h3 class="modal-title fs-5 font-semibold tracking-wide text-left text-gray-500">Message Box</h3>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<div class="container-fluid" id="modal-container">
						<div class="w-full mb-8 overflow-hidden rounded-lg shadow-xs">
							<div class="row row-cols-2">
								<div class="col-4 list-group px-3 py-1" id="messageListContainer" >
									<div class="list-group-item list-group-item-action message-list d-flex flex-row" style="align-items: center;">
										<div>
											<svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="#000" class="bi bi-envelope" viewBox="0 0 16 16">
												<path d="M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2zm2-1a1 1 0 0 0-1 1v.217l7 4.2 7-4.2V4a1 1 0 0 0-1-1zm13 2.383-4.708 2.825L15 11.105zm-.034 6.876-5.64-3.471L8 9.583l-1.326-.795-5.64 3.47A1 1 0 0 0 2 13h12a1 1 0 0 0 .966-.741M1 11.105l4.708-2.897L1 5.383z"></path>
											</svg>
										</div>
									</div>
								</div>
								<div class="col-8" id="messageContainer">
									
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	<script>
		const contextPath = '<%=request.getContextPath()%>';
	</script>
	<script src="<%=request.getContextPath()%>/js/message/message.js"></script>
</body>
</html>