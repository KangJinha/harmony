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
	//내가신청한 신청정보
	Lesson lesson = (Lesson)request.getAttribute("lesson");
	Lesson time = (Lesson)request.getAttribute("time");
	MemberInfo tInfo = (MemberInfo)request.getAttribute("teacherInfo");
	LessonApply showApplyBtn = (LessonApply)request.getAttribute("showApplyBtn");
	
	Timestamp AstartTime = (Timestamp)showApplyBtn.getLessonStartTime();
	Timestamp AendTime = (Timestamp)showApplyBtn.getLessonEndTime();
	
	String AstartString = String.valueOf(AstartTime);
	String AEndString = String.valueOf(AendTime);
	
	String AAstartTime = AstartString.substring(11, 16);
	String AAendTime = AEndString.substring(11, 16);
	
	String[] AtimeStrings = showApplyBtn.getLessonDay();
	String AtimeString = Arrays.toString(AtimeStrings);
	
	
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
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/lesson/showApplyInfo.css">
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=b314c47810b31c3c487d6f6ad04d71b1&libraries=services"></script>
	<section class="container w-50">
		<%if(deadline=='N'){ %>
		<div class="recruit">모집중</div>
		<%} else {%>
		<div>마감</div>
		<%} %>
		<div class="container">
			<div class="upperBar">
                <h2 style="color: white;">나의 상담 신청 정보</h2>
                <div class="cud">
                    <button style="color: #98B629">상담수락</button>
                    <button style="color: #2F4858">상담거절</button>
                </div>
            </div>
			<article class="lessonInfo d-flex flex-column gap-2">
                <div class="imgSubmitSection d-flex gap-3">
                    <div class="imgContainer w-50">
                        <div class="detailImg">
                    	
                        <%if(lesson.getBoardImg()!=null) {%>
                            <img alt="이미지" src="<%=request.getContextPath()%>/upload/lesson/<%=lesson.getBoardImg()%>" width="100%">
                        <%} else { %>
                        	<img src="<%=request.getContextPath()%>/image/lesson/default.jpg" width="100%">
                        <%} %>
                        </div>
                    </div>
                    <div class="submitContainer w-50">
                        <div class="lessonSubmit d-flex flex-column">
                        	<%if(loginMember!=null){ %>
                        	<input type="hidden" value="<%=loginMember.getMemNo() %>" name="memNo">
                        	<%} %>
                        	<input type="hidden" value="<%=lesson.getBoardNo()%>" name="boardNo">
                            <div class="labelBox d-flex flex-column p-4 mb-4">
                                <div class="mb-3"><h5>상담신청 정보</h5></div>
                               	  <div>
                                      <div>장소</div>
                                      <input readonly class="btn btn-outline-warning" type="text" value="<%=showApplyBtn.getApplyPlace() %>">
                                  </div>
                                  <div>
                                      <div>시작 시간</div>
                                      <input readonly class="btn btn-outline-warning" type="text" value="<%=AAstartTime%>">
                                  </div>

                                  <div>
                                      <div>종료 시간</div>
                                      <input readonly class="btn btn-outline-warning" type="text" value="<%=AAendTime%>">
                                  </div>
                                  <div>
                                      <div>요일</div>
                                      <input readonly class="btn btn-outline-warning" type="text" value="<%=AtimeString%>">
                                  </div>
                                  <div>
                                      <div>횟수</div>
                                      <input readonly class="btn btn-outline-warning" type="text" value="<%=showApplyBtn.getApplyNumberOfTimes()%>">
                                  </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="detailInfoSection">
                    	<div>
	                        <div class="detailInfoBar d-flex justify-content-center gap-3">
	                        	<button>선생님정보</button>
	                            <button id="areaBtn">지역정보</button>
	                            <button id="lessonBtn">레슨정보</button>
	                            <button id="teacherBtn">강사정보</button>
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
                                  <div>레슨정보</div>
                              </div>
                              <div class="detailsContainer_content">
                                  <div> 
                                      <div>레슨 악기</div>
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
                                      <div>레슨비(회당)</div>
                                      <input readonly class="btn btn-outline-warning" type="text" value="<%=lesson.getBoardPrice() %>">
                                  </div>
                                  
                                  <div>
                                      <div>시작 시간</div>
                                      <input readonly class="btn btn-outline-warning" type="text" value="<%=startTime%>">
                                  </div>

                                  <div>
                                      <div>종료 시간</div>
                                      <input readonly class="btn btn-outline-warning" type="text" value="<%=endTime%>">
                                  </div>
                                  
                                  <div>
                                      희망 요일
                                      <input readonly class="btn btn-outline-warning" type="text" value="<%=timeString%>">
                                  </div> 
                              </div>
                          </div>
                            <!-- 강사정보 -->
                          <div class="detailsContainer">
                              <div class="detailsContainer_title" id="teacherInfo">
                                  <div>강사정보</div>
                              </div>
                              <div class="detailsContainer_content">
                                  <div>
                                  	활동지역 <%=tInfo.getActivityArea() %> <br>
                                  	한줄소개 <%=tInfo.getIntroduce() %> <br>
                                  	학교 <%=tInfo.getSchool() %> <br>
                                  	학과 <%=tInfo.getDepartment() %> <br>
                                  	재학정보 <%=tInfo.getSchoolState() %> <br>
                                  	성별 <%=tInfo.getGender() %> <br>
                                  	경력 <%=tInfo.getMemCareer() %>
                                  </div>
                              </div>
                          </div>
                          <!-- 리뷰 -->
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