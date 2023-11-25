<%@page import="com.harmony.lesson.dto.SaveLesson"%>
<%@page import="com.harmony.model.dto.MemberInfo"%>
<%@page import="com.harmony.lesson.dto.LessonComment"%>
<%@page import="com.harmony.lesson.dto.LessonApply"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="com.harmony.lesson.dto.Lesson"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Lesson lesson = (Lesson)request.getAttribute("lesson");
	Lesson time = (Lesson)request.getAttribute("time");
	List<LessonApply> reviews = (List<LessonApply>)request.getAttribute("review");
	int reviewsCount = (int)request.getAttribute("reviewsCount");
	List<LessonComment> cos = (List<LessonComment>)request.getAttribute("co");
	MemberInfo tInfo = (MemberInfo)request.getAttribute("teacherInfo");
	SaveLesson heart = (SaveLesson)request.getAttribute("heart");
	LessonApply showApplyBtn = (LessonApply)request.getAttribute("showApplyBtn");
	
	//타임스탬프형식 변환
	Timestamp TstartTime = (Timestamp)time.getLessonStartTime();
	Timestamp TendTime = (Timestamp)time.getLessonEndTime();
	
	String startString = String.valueOf(TstartTime);
	String EndString = String.valueOf(TendTime);
	
	String startTime = startString.substring(11, 16);
	String endTime = EndString.substring(11, 16);
	
	//배열을 문자열로 변환
	String[] timeStrings = time.getDay();
	String timeString = Arrays.toString(timeStrings);
	
	//악기 변환
	String inst = lesson.getInstNo();
	//마감정보
	char deadline = lesson.getBoardDeadline();
%>
<%@ include file="/views/common/header.jsp"%>
    <script src="https://kit.fontawesome.com/8f05e1d322.js" crossorigin="anonymous"></script>
    <script src="https://unpkg.com/typeit@8.7.1/dist/index.umd.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/lesson/lessonInfo.css">
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=b314c47810b31c3c487d6f6ad04d71b1&libraries=services"></script>
	<section class="container w-50">
		<%if(deadline=='N'){ %>
		<div class="recruit">모집중</div>
		<%} else {%>
		<div>마감</div>
		<%} %>
        <div><h2 id="title" style="color: #2F4858"><%=lesson.getBoardTitle() %></h2></div>
		<div class="container">
			<div class="upperBar">
				<div class="category">
					<input class="btn" value="악기 >" onclick="location.href='<%=request.getContextPath()%>/lesson/findLesson.do'" readonly>
	                <input class="btn" readonly type="text" 
                                      <% switch (inst) {
                                      	case "INST_1" :%>value="드럼";break;
                                      <% case "INST_2" :%>value="베이스";break;
                                      <% case "INST_3" :%>value="더블베이스";break;
                                      <% case "INST_4" :%>value="기타";break;
                                      <% case "INST_5" :%>value="피아노";break;
                                      <% case "INST_6" :%>value="작곡";break;
                                      <% case "INST_7" :%>value="색소폰";break;
                                      <% case "INST_8" :%>value="트럼펫";break;
                                      <% case "INST_9" :%>value="플룻";break;
                                      <% case "INST_10" :%>value="바이올린";break;
                                      <% case "INST_11" :%>value="첼로";break;
                                      <% case "INST_12" :%>value="퍼커션";break;
                                      <% case "INST_13" :%>value="보컬";break;
                                      <% case "INST_14" :%>value="믹싱(DAW)";break;
                                      <% case "INST_15" :%>value="ETC";break;
                                      <%} %>
                                      >
                 </div>
                 <!--  -->
                <%if(loginMember!=null && loginMember.getMemAuthority().equals("TEACHER") && lesson.getTeacherNo().equals(tInfo.getTeacherNo())){ %>
                <div class="cud">
                	<a href="<%=request.getContextPath()%>/lesson/enrollLesson.do?boardNo=<%=lesson.getBoardNo()%>">레슨등록</a>
                    <button onclick="location.href='<%=request.getContextPath()%>/lesson/updateLesson.do?no=<%=lesson.getBoardNo()%>'">수정하기</button>
                    <button id="deleteLesson">삭제하기</button>
                </div>
                <%} %>
                <!-- 현재의 게시글넘버랑 로그인한멤버넘버랑 로그인한 멤버가 신청했을때만 보여줌 -->
                <%if(showApplyBtn!=null && showApplyBtn.getBoardNo()==lesson.getBoardNo()) {%>
                <button style="color: #2F4858" id="showApplyInfo" onclick="location.href='<%=request.getContextPath()%>/lesson/showApplyInfo.do?no=<%=lesson.getBoardNo()%>'">신청정보보기</button>
            	<%} %>
            </div>
			<article class="lessonInfo d-flex flex-column gap-2">
                <div class="imgSubmitSection d-flex gap-3">
                    <div class="imgContainer w-50">
                        <div class="detailImg">
	                        <%if(loginMember!=null){ %>
		                        <div class="saveLessonPosition d-flex" onclick="location.replace('<%=request.getContextPath()%>/lesson/savelesson.do?boardNo=<%=lesson.getBoardNo()%>&memNo=<%=loginMember.getMemNo()%>')">
		                            <%if(heart!=null){ %>
		                            	<i class="saveLesson fa-solid fa-heart fa-xs"></i>
		                    			<div style="color: #98B629">찜하고 있어요!</div>
			                    	<%}else{ %>
			                    		<i class="fa-solid fa-heart fa-xs" style="color: #006776"></i>
			                    		<div style="color: #006776">찜해주세요 :)</div>
			                    	<%} %>
		                    	</div>
	                    	<%}else{ %>
		                    	<div class="saveLessonPosition d-flex" onclick="alert('로그인 후 이용할 수 있는 서비스입니다.')">
		                            <i class="fa-solid fa-heart fa-xs"></i>
		                    	</div>
	                    	<%} %>	
                    	
                        <%if(lesson.getBoardImg()!=null) {%>
                            <img alt="이미지" src="<%=request.getContextPath()%>/upload/lesson/<%=lesson.getBoardImg()%>" width="100%">
                        <%} else { %>
                        	<img src="<%=request.getContextPath()%>/image/lesson/default.jpg" width="100%">
                        <%} %>
                        </div>
                    </div>
                    <div class="submitContainer w-50">
                        <div class="lessonSubmit d-flex flex-column">
                        	<form id="lessonConsultSubmit" action="<%=request.getContextPath() %>/apply/applyLesson.do?teacherNo=<%=lesson.getTeacherNo() %>" method="post" onsubmit="return confirm('상담을 신청하시겠습니까?')";>
                        	<!-- 93번째줄때문에 로그인 해야함 분기처리중요!-->
                        	<%if(loginMember!=null){ %>
                        	<input type="hidden" value="<%=loginMember.getMemNo() %>" name="memNo">
                        	<%} %>
                        	<input type="hidden" value="<%=lesson.getBoardNo()%>" name="boardNo">
                            <div class="labelBox d-flex flex-column p-4 mb-4">
                                <div style="color: #2F4858" class="mb-3"><h5>레슨상담신청</h5></div>
                                <label>
                                    <div>장소</div>
                                    <select class="form-select" name="place" id="place" required>
                                        <option value="서울">서울</option>
                                        <option value="인천">인천</option>
                                        <option value="경기">경기</option>
                                        <option value="강원">강원</option>
                                        <option value="충북">충북</option>
                                        <option value="충남">충남</option>
                                        <option value="전북">전북</option>
                                        <option value="전남">전남</option>
                                        <option value="경북">경북</option>
                                        <option value="경남">경남</option>
                                        <option value="제주">제주</option>
                                    </select>
                                </label>
                                <label>
                                    <div>시작시간</div>
	                                    <select class="form-select" name="startTime" id="startTime">
                                                <option value="09:00:00.0">09:00</option>
                                                <option value="10:00:00.0">10:00</option>
                                                <option value="11:00:00.0">11:00</option>
                                                <option value="12:00:00.0">12:00</option>
                                                <option value="13:00:00.0">13:00</option>
                                                <option value="14:00:00.0">14:00</option>
                                                <option value="15:00:00.0">15:00</option>
                                                <option value="16:00:00.0">16:00</option>
                                                <option value="17:00:00.0">17:00</option>
                                                <option value="18:00:00.0">18:00</option>
                                                <option value="19:00:00.0">19:00</option>
                                                <option value="20:00:00.0">20:00</option>
                                                <option value="21:00:00.0">21:00</option>
                                                <option value="22:00:00.0">22:00</option>
                                         </select>
                                </label>
                                <label>
                                    <div>종료시간</div>
	                                    <select class="form-select" name="endTime" id="endTime">
                                                <!-- <option value="09:00:00.0">09:00</option> -->
                                                <option value="09:00:00.0">09:00</option>
                                                <option value="10:00:00.0">10:00</option>
                                                <option value="11:00:00.0">11:00</option>
                                                <option value="12:00:00.0">12:00</option>
                                                <option value="13:00:00.0">13:00</option>
                                                <option value="14:00:00.0">14:00</option>
                                                <option value="15:00:00.0">15:00</option>
                                                <option value="16:00:00.0">16:00</option>
                                                <option value="17:00:00.0">17:00</option>
                                                <option value="18:00:00.0">18:00</option>
                                                <option value="19:00:00.0">19:00</option>
                                                <option value="20:00:00.0">20:00</option>
                                                <option value="21:00:00.0">21:00</option>
                                                <option value="22:00:00.0">22:00</option>
                                         </select>
                                </label>
                                <label>
                                    <div>요일</div>
                                        <input class="btn-check" type="checkbox" name='hopeDay' id="mon" value="월"> <label class="btn btn-outline-warning" for="mon">월</label>
                                        <input class="btn-check" type="checkbox" name='hopeDay' id="tue" value="화"> <label class="btn btn-outline-warning" for="tue">화</label>
                                        <input class="btn-check" type="checkbox" name='hopeDay' id="wed" value="수"> <label class="btn btn-outline-warning" for="wed">수</label>
                                        <input class="btn-check" type="checkbox" name='hopeDay' id="thur" value="목"> <label class="btn btn-outline-warning" for="thur">목</label>
                                        <input class="btn-check" type="checkbox" name='hopeDay' id="fri" value="금"> <label class="btn btn-outline-warning" for="fri">금</label>
                                        <input class="btn-check" type="checkbox" name='hopeDay' id="sat" value="토" checked> <label class="btn btn-outline-warning" for="sat">토</label>
                                        <input class="btn-check" type="checkbox" name='hopeDay' id="sun" value="일"> <label class="btn btn-outline-warning" for="sun">일</label>
                                </label>
                                <label>
                                    <div>회당 레슨시간</div>
                                    <select class="form-select" name="place" id="">
                                        <option value="1">1</option>
                                        <option value="2">2</option>
                                        <option value="3">3</option>
                                    </select>
                                </label>
                                <label>
                                    <div>횟수</div> 
                                    <select class="form-select" name="count" id="">
                                        <option value="1">1</option>
                                        <option value="2">2</option>
                                        <option value="3">3</option>
                                    </select>
                                </label>
                                <!-- <label>
                                    <div>가격</div>
                                    <button>1회*가격</button>
                                </label> -->
                                
                            </div>
                            <div>
	                            <%if(loginMember!=null) {%>
	                                <input type="submit" value="상담신청">
	                            <%} else {%>
	                            	<input type="button" value="상담신청" readonly="readonly" onclick="alert('로그인이 필요한 서비스입니다.');">
	                            <%} %>
                            </div>
                            </form>
                        </div>
                    </div>
                </div>

                <div class="detailInfoSection">
                    	<div>
	                        <div class="reviewInfo"> <i class="fa-solid fa-comment-dots"></i>&nbsp;리뷰 <%=reviewsCount %>건 &nbsp; <i class="fa-solid fa-binoculars"></i>&nbsp;조회수&nbsp;<%=lesson.getBoardView() %>회 </div>
	                        <div class="detailInfoBar d-flex justify-content-center gap-3">
	                            <button id="areaBtn">지역정보</button>
	                            <button id="lessonBtn">레슨정보</button>
	                            <button id="teacherBtn">강사정보</button>
	                            <button id="reviewBtn">리뷰</button>
	                            <div class="upup"><i class="fa-regular fa-circle-up fa-lg"></i></div>
	                        </div>
	                      	<div class="detailsContainer mt-3" id="areaInfo">
                              <div class="detailsContainer_title">
                                  <div>지역정보</div>
                              </div>
                              <div class="detailsContainer_content">
                                  <div id="map" style="width:100%;height:400px;"></div>
                              </div>
                          	</div>
               				<div class="detailsContainer">
                              <div class="detailsContainer_title" id="lessonInfo">
                                  <div class="colorNavy">레슨정보</div>
                              </div>
                              <div class="detailsContainer_content">
                                  <div> 
                                      <div class="colorNavy">레슨 악기</div>
                                      <input readonly class="btn btn-outline-warning" type="text" 
                                      <% switch (inst) {
                                      	case "INST_1" :%>value="드럼";break;
                                      <% case "INST_2" :%>value="베이스";break;
                                      <% case "INST_3" :%>value="더블베이스";break;
                                      <% case "INST_4" :%>value="기타";break;
                                      <% case "INST_5" :%>value="피아노";break;
                                      <% case "INST_6" :%>value="작곡";break;
                                      <% case "INST_7" :%>value="색소폰";break;
                                      <% case "INST_8" :%>value="트럼펫";break;
                                      <% case "INST_9" :%>value="플룻";break;
                                      <% case "INST_10" :%>value="바이올린";break;
                                      <% case "INST_11" :%>value="첼로";break;
                                      <% case "INST_12" :%>value="퍼커션";break;
                                      <% case "INST_13" :%>value="보컬";break;
                                      <% case "INST_14" :%>value="믹싱(DAW)";break;
                                      <% case "INST_15" :%>value="ETC";break;
                                      <%} %>
                                      >
                                  </div>
                                  <div>
                                      <div class="colorNavy">레슨비(회당)</div>
                                      <input readonly class="btn btn-outline-warning" type="text" value="<%=lesson.getBoardPrice() %>">
                                  </div>
                                  
                                  <div>
                                      <div class="colorNavy">시작 시간</div>
                                      <input readonly class="btn btn-outline-warning" type="text" value="<%=startTime %>">
                                  </div>

                                  <div>
                                      <div class="colorNavy">종료 시간</div>
                                      <input readonly class="btn btn-outline-warning" type="text" value="<%=endTime %>">
                                  </div>
                                  
                                  <div>
                                      <div class="colorNavy">희망 요일</div> 
                                      <input readonly class="btn btn-outline-warning" type="text" value="<%=timeString %>">
                                  </div> 
                              </div>
                          </div>
                            <!-- 강사정보 -->
                          <div class="detailsContainer">
                              <div class="detailsContainer_title" id="teacherInfo">
                                  <div class="colorNavy">강사정보</div>
                              </div>
                              <div class="detailsContainer_content">
                                  <div class="colorNavy">
                                  	활동지역 <%=tInfo.getActivityArea() %> <br>
                                  	한줄소개 <%=tInfo.getIntroduce() %> <br>
                                  	학교 <%=tInfo.getSchool() %> <br>
                                  	학과 <%=tInfo.getDepartment() %> <br>
                                  	재학정보 <%=tInfo.getSchoolState() %> <br>
                                  	경력 
                                  	<%=tInfo.getMemCareer() %> 
                                  </div>
                              </div>
                          </div>
                          <!-- 리뷰 -->
                          <div class="rewiewContainer" id="reviewInfo">
                              <div class="mb-3">리뷰</div>
                              <%for(LessonApply l : reviews){ %>
                              <div class="avataMemberStarDate">
                              	<div><i class="fa-solid fa-user-astronaut fa-lg"></i></div>
                              	<div class="memberStarDate">
                              		<div>
                               			<%=l.getMemNo() %>
                               		</div>
                               		<div class="starDate">
                               			<%for(int i=0;i<l.getReviewPoint();i++){ %>
                               				<i class="fa-solid fa-star"></i>
                               			<%} %>
                                		<%=l.getReviewPoint() %> |
                                		<%=l.getReviewEnrollDate() %>
                               		</div>
                              	</div>
                              </div>
                              
                              <div class="reviewContent">
                               	<div class="review">
                               		<%=l.getReview() %>
                               	</div>
                               	<br>
                               	<!-- 코멘트를 반복문으로 가져옴 -->
                               	<%for(LessonComment co : cos){ %>
                               	<!-- 등록할때 코멘트의 리뷰넘버랑 레슨신청시의 리뷰넘버랑 같으면 가져오기-->
                               	<%if(!cos.isEmpty() && co.getReviewNo()==l.getReviewNo()) {%>
                               	<div class="teacher-comment">
                               		<div class="avataMemberStarDate">
                               			<div><i class="fa-solid fa-comment-dots"></i></div>
		                              	<div class="memberStarDate">
		                              		<div>
		                              			TEACHER
		                               		</div>
		                               		<div class="starDate">
		                                		<%=co.getCommentDate() %>
		                               		</div>
		                              	</div>
	                              	</div>
	                              	<div class="review">
                               			<%=co.getCommentContent() %>
                               			<%if(loginMember!=null && loginMember.getMemAuthority().equals("TEACHER") && lesson.getTeacherNo().equals(tInfo.getTeacherNo())) {%>
                               			<button id="deleteReply" class="btn btn-sm btn-warning" 
                               			onclick="location.href='<%=request.getContextPath()%>/lesson/deletereply.do?reviewNo=<%=l.getReviewNo()%>&boardNo=<%=lesson.getBoardNo()%>'">삭제</button>
                               			<%} %>
                               		</div>
                               	</div>
                               	<%} %>
                               	<%} %>
                               	<div id="comment-container">
									<div class="comment-editor">
										<form action="<%=request.getContextPath() %>/lesson/insertComment.do" method="post">
											<input type="hidden" name="boardNo" value="<%=lesson.getBoardNo()%>">
											<input type="hidden" name="reviewNo" value="<%=l.getReviewNo()%>">
											<textarea class="form-control" name="content" cols="55" rows="3" style="resize: none;"></textarea>
											<br>
											<%if(loginMember!=null && loginMember.getMemAuthority().equals("TEACHER") && lesson.getTeacherNo().equals(tInfo.getTeacherNo())){ %>
											<!-- 등록할때 코멘트의 리뷰넘버랑 레슨신청시의 리뷰넘버랑 같게만들어서 등록-->
											<button class="btn btn-outline-warning" type="submit" id="btn-insert">댓글등록</button>
											<%} else{%>
											<button class="btn btn-outline-warning" disabled>댓글등록</button>
											<%} %>
										</form>
									</div>
								</div>
                               	<hr>
                              </div>
                              <%} %>
                          </div>
                    </div>
                </div>
            </article>
		</div>
	</section>
	<script>
		let observer = new IntersectionObserver((e)=>{
			/* 감시중인 박스가 화면에 등장하면 여기 코드를 실행해준다 */
			e.forEach((boxs)=>{
				if (boxs.isIntersecting) {
					boxs.target.style.opacity = 1;
				} else {
					boxs.target.style.opacity = 0;
				}
			});
		});
		let div = document.querySelectorAll('div');
		console.log(div.length);
		for (let i = 0; i < div.length; i++) {
			observer.observe(div[i])
		};
		
		/* 내가원하는 html요소를 감시해줌 */
	</script>
	<script>
		document.addEventListener('DOMContentLoaded', () => {
			new TypeIt('#title')
			.pause(1000)
			.go();
		});
	</script>
	
	<script>
		$(".comment-editor>form>textarea[name=content]").click(e=>{
			if (<%=loginMember==null %>) {
				alert("로그인 후 선생님만 이용할 수 있는 서비스입니다.");
			}
		});
	</script>
	<script>
	    areaBtn.addEventListener('click', () => {
	        window.scrollBy({top: areaInfo.getBoundingClientRect().top, behavior: 'smooth'});
	    });
	    lessonBtn.addEventListener('click', () => {
	        window.scrollBy({top: lessonInfo.getBoundingClientRect().top, behavior: 'smooth'});
	    });
	    
	    teacherBtn.addEventListener('click', () => {
	        window.scrollBy({top: teacherInfo.getBoundingClientRect().top, behavior: 'smooth'});
	    });
	    reviewBtn.addEventListener('click', () => {
	        window.scrollBy({top: reviewInfo.getBoundingClientRect().top, behavior: 'smooth'});
	    });
	    
	    $(".upup").click(()=>{
	    	$(window).scrollTop(0);
	    });
	    
	</script>
	<script>
	/* 시간선택변경시 */
		$("#startTime").change(()=>{
	        let startTime = ($("#startTime").val()).substr(0,2);
	        let endTime = ($("#endTime").val()).substr(0,2);
	        $("#endTime option").removeAttr("disabled");
	        // if(startTime>=endTime){
	        //     alert("종료시간은 시작시간보다 커야합니다");
	        //     for(let i=0; i<=10-endTime;i++){
	        //         $("#endTime option:eq("+i+")").attr("disabled", true);
	        //     }
	        // }
	        for(let i=0; i<=startTime-9;i++){
	            $("#endTime option:eq("+i+")").attr("disabled", true);
	        }
	    });
	    
		$("#deleteLesson").click(()=>{
	    	if (confirm("정말 삭제하시겠습니까?") == true){ //확인
				 location.href='<%=request.getContextPath()%>/lesson/deleteLesson.do?no=<%=lesson.getBoardNo()%>'
			 }else{   //취소
			     return false;
			 }
	    })
	    
	    /* function consult() {
		    if (confirm("신청하시겠습니까?") == true) {
		        let form = document.getElementById("lessonConsultSubmit");
		        form.submit();
			    } else {
			    	return false;
			    }
		} */
			 
	</script>
	<script>
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	    mapOption = {
	        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
	        level: 3 // 지도의 확대 레벨
	    };  
	// 지도를 생성합니다
	var map = new kakao.maps.Map(mapContainer, mapOption); 
	
	// 주소-좌표 변환 객체를 생성합니다
	var geocoder = new kakao.maps.services.Geocoder();
	
	// 주소로 좌표를 검색합니다
		
		geocoder.addressSearch('<%=lesson.getBoardPlace()%>', function(result, status) { 
	    // 정상적으로 검색이 완료됐으면 
	     if (status === kakao.maps.services.Status.OK) {
	
	        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
	
	        // 결과값으로 받은 위치를 마커로 표시합니다
	        var marker = new kakao.maps.Marker({
	            map: map,
	            position: coords
	        });
	
	        // 인포윈도우로 장소에 대한 설명을 표시합니다
	        var infowindow = new kakao.maps.InfoWindow({
	            content: '<div style="width:150px;text-align:center;padding:6px 0;"><%=lesson.getBoardPlace()%></div>'
	        });
	        infowindow.open(map, marker);
	
	        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
	        map.setCenter(coords);
	        map.setDraggable(false);
	    } 
	});
	</script>
	
<%@ include file="/views/common/footer.jsp" %>