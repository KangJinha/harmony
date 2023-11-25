<%@page import="com.harmony.lesson.dto.Lesson"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/header.jsp"%>
<%
	Lesson lesson = (Lesson)request.getAttribute("lesson");
%>
    <script src="https://kit.fontawesome.com/8f05e1d322.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/lesson/enrollLesson.css">
   	<section class="container w-50">
		<div class="container">
            <form action="<%=request.getContextPath() %>/enroll/enrollEndLesson.do" method="post" enctype="multipart/form-data">
            <!-- 카데고리 상태, 등록버튼 -->
			<div class="upperBar">
                <div></div>
                <div class="mb-3">
                    <input type="submit" value="등록하기">
                </div>
            </div>
			<article class="lessonInfo gap-2">
                <div class="imgSubmitSection d-flex gap-3">
                    <div class="imgContainer w-50 d-flex flex-column">
                        <h5>이미지업로드</h5>
                        <input type="file" name="upfile" class="imgUpload form-control" accept="image/*" required="required">
                    </div>
                    <div class="submitContainer w-50">
                        <div class="lessonSubmit d-flex flex-column">
                            <div class="labelBox d-flex flex-column p-4 mb-4">
                                <div class="enrollLessonInfo mb-3">
                                    <h5>타이틀, 내용등록</h5>
                                    <input type="text" name="title" placeholder="타이틀" required>
                                    <input type="text" name="content" placeholder="설명" required>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="detailInfoSection">
                    <div>
                        <div class="detailInfoBar d-flex justify-content-center gap-3">
                            <div>지역정보</div>
                            <!-- 시간대, 가격 등등... -->
                            <div>레슨정보</div>
                            <!-- teacherNum 회원정보(memNo)를 사용해 Join으로 강사번호 가져오기-->
                            <input type="hidden" value="<%=lesson.getTeacherNo() %>" name="teacherNum">
                        </div>
                        <div class="detailInfo d-flex mt-3">
                            <div class="detailsContainer">
                                <div class="detailsContainer_title">
                                    <div>지역정보</div>
                                </div>
                                <div class="detailsContainer_content">
                                    <div class="detailsContainer_content-inputpost">
                                        <input type="text" id="sample6_postcode" placeholder="우편번호"><br>
                                        <input type="button" class="btn btn-outline-warning" onclick="sample6_execDaumPostcode()" value="우편번호 찾기"><br>
                                        <input type="text" id="sample6_address" name="address" placeholder="주소" required><br>
                                        <input type="text" id="sample6_detailAddress" placeholder="상세주소"><br>
                                        <input type="text" id="sample6_extraAddress" placeholder="주소이름">
                                    </div>
                                </div>
                            </div>
                            <div class="detailsContainer">
                                <div class="detailsContainer_title">
                                    <div>레슨정보</div>
                                </div>
                                <div class="detailsContainer_content">
                                    <div class="d-flex flex-column"> 
                                        <div class="mb-3">레슨 악기</div>
                                        <div class="detailsContainer_content-selectInst">
                                            <div class="mb-3">
                                                <input class="btn-check" type="radio" name="inst" value="INST_1" id="drum" required><label class="btn btn-outline-warning" for="drum">드럼</label>
                                                <input class="btn-check" type="radio" name="inst" value="INST_2" id="bass"><label class="btn btn-outline-warning" for="bass">베이스</label>
                                                <input class="btn-check" type="radio" name="inst" value="INST_3" id="doublebass"><label class="btn btn-outline-warning" for="doublebass">더블베이스</label>
                                                <input class="btn-check" type="radio" name="inst" value="INST_4" id="gittar"><label class="btn btn-outline-warning" for="gittar">기타</label>
                                                <input class="btn-check" type="radio" name="inst" value="INST_5" id="piano"><label class="btn btn-outline-warning" for="piano">피아노</label>
                                                <input class="btn-check" type="radio" name="inst" value="INST_6" id="composition"><label class="btn btn-outline-warning" for="composition">작곡</label>
                                                <input class="btn-check" type="radio" name="inst" value="INST_7" id="saxophone"><label class="btn btn-outline-warning" for="saxophone">색소폰</label>
                                            </div>
                                            <div>
                                                <input class="btn-check" type="radio" name="inst" value="INST_8" id="trumpet"><label class="btn btn-outline-warning" for="trumpet">트럼펫</label>
                                                <input class="btn-check" type="radio" name="inst" value="INST_9" id="flute"><label class="btn btn-outline-warning" for="flute">플룻</label>
                                                <input class="btn-check" type="radio" name="inst" value="INST_10" id="violin"><label class="btn btn-outline-warning" for="violin">바이올린</label>
                                                <input class="btn-check" type="radio" name="inst" value="INST_11" id="cello"><label class="btn btn-outline-warning" for="cello">첼로</label>
                                                <input class="btn-check" type="radio" name="inst" value="INST_12" id="percussion"><label class="btn btn-outline-warning" for="percussion">퍼커션</label>
                                                <input class="btn-check" type="radio" name="inst" value="INST_13" id="vocal"><label class="btn btn-outline-warning" for="vocal">보컬</label>
                                                <input class="btn-check" type="radio" name="inst" value="INST_14" id="mixing"><label class="btn btn-outline-warning" for="mixing">믹싱</label>
                                                <input class="btn-check" type="radio" name="inst" value="INST_15" id="etc"><label class="btn btn-outline-warning" for="etc">ETC</label>
                                            </div>
                                        </div>
                                    </div>
                                    <div>
                                        <div>희망 레슨비(회당)</div>
                                        <select class="form-select" name="price" id="hopeFee">
                                            <option value="100,000">100,000</option>
                                            <option value="150,000">150,000</option>
                                            <option value="200,000">200,000</option>
                                            <option value="250,000">250,000</option>
                                            <option value="협의가능">협의가능</option>
                                        </select>
                                    </div>
                                    
                                    <div>
                                        <div>희망 시작 시간</div>
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
                                    </div>
                                    <div>
                                        <div>희망 종료 시간</div>
                                            <select class="form-select" name="endTime" id="endTime">
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
                                    </div>
                                    
                                    <div>
                                        희망 요일
                                        <input class="btn-check" type="checkbox" name='hopeDay' id="mon" value="월"> <label class="btn btn-outline-warning" for="mon">월</label>
                                        <input class="btn-check" type="checkbox" name='hopeDay' id="tue" value="화"> <label class="btn btn-outline-warning" for="tue">화</label>
                                        <input class="btn-check" type="checkbox" name='hopeDay' id="wed" value="수"> <label class="btn btn-outline-warning" for="wed">수</label>
                                        <input class="btn-check" type="checkbox" name='hopeDay' id="thur" value="목"> <label class="btn btn-outline-warning" for="thur">목</label>
                                        <input class="btn-check" type="checkbox" name='hopeDay' id="fri" value="금"> <label class="btn btn-outline-warning" for="fri">금</label>
                                        <input class="btn-check" type="checkbox" name='hopeDay' id="sat" value="토" checked> <label class="btn btn-outline-warning" for="sat">토</label>
                                        <input class="btn-check" type="checkbox" name='hopeDay' id="sun" value="일"> <label class="btn btn-outline-warning" for="sun">일</label>
                                    </div> 
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </article>
            </form>
		</div>
	</section>
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=b314c47810b31c3c487d6f6ad04d71b1&libraries=services"></script>
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
		<script>
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
        
        const imgUpload = document.querySelector('.imgUpload');
        function getImageFiles(e) {
        const files = e.currentTarget.files;
            [...files].forEach(file => {
                if (!file.type.match("image/.*")) {
                alert('이미지 파일만 업로드가 가능합니다.');
                return;
            }
        })
        }
        imgUpload.addEventListener('change', getImageFiles);
		
		    function sample6_execDaumPostcode() {
		        new daum.Postcode({
		            oncomplete: function(data) {
		                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

		                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
		                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
		                var addr = ''; // 주소 변수
		                var extraAddr = ''; // 참고항목 변수

		                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
		                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
		                    addr = data.roadAddress;
		                } else { // 사용자가 지번 주소를 선택했을 경우(J)
		                    addr = data.jibunAddress;
		                }

		                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
		                if(data.userSelectedType === 'R'){
		                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
		                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
		                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
		                        extraAddr += data.bname;
		                    }
		                    // 건물명이 있고, 공동주택일 경우 추가한다.
		                    if(data.buildingName !== '' && data.apartment === 'Y'){
		                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
		                    }
		                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
		                    if(extraAddr !== ''){
		                        extraAddr = ' (' + extraAddr + ')';
		                    }
		                    // 조합된 참고항목을 해당 필드에 넣는다.
		                    document.getElementById("sample6_extraAddress").value = extraAddr;
		                
		                } else {
		                    document.getElementById("sample6_extraAddress").value = '';
		                }

		                // 우편번호와 주소 정보를 해당 필드에 넣는다.
		                document.getElementById('sample6_postcode').value = data.zonecode;
		                document.getElementById("sample6_address").value = addr;
		                // 커서를 상세주소 필드로 이동한다.
		                document.getElementById("sample6_detailAddress").focus();
		            }
		        }).open();
		    }
		</script>	
<%@ include file="/views/common/footer.jsp" %>