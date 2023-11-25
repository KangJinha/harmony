<%@page import="javax.sound.midi.MidiChannel"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.harmony.model.dto.MemberInfo" %>

<%@ include file="/views/common/header.jsp" %> 
<%MemberInfo mi = (MemberInfo)request.getSession().getAttribute("MemberInfo"); %>
<%Member m = (Member)request.getSession().getAttribute("loginMember"); %>


<head>
<script src="//code.jquery.com/jquery-3.7.1.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="http://code.jquery.com/jquery-latest.js"></script>
<style>
    .map_wrap {position:relative;width:100%;height:350px;}
    .title {font-weight:bold;display:block;}
    .hAddr {position:absolute;left:10px;top:10px;border-radius: 2px;background:#fff;background:rgba(255,255,255,0.8);z-index:1;padding:5px;}
    #centerAddr {display:block;margin-top:2px;font-weight: normal;}
    .bAddr {padding:5px;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;}
	
	#profile{
	border-radius:100px;
	cursor:pointer;
		}
	#profiledata{
	display:none;
	}

</style>

<title>마이페이지</title>
</head>
<body>
	
	<div class="w3-content w3-container w3-margin-top">
		<div class="w3-container w3-card-4">
			<div class="w3-center w3-large w3-margin-top">
				<h3>추가정보 입력</h3>
				<h4>안하면 불이익이 있을수도 있고 없을수도 있습니다</h4>
			</div>
			<div>
				<form id="myForm" action="<%=request.getContextPath() %>/member/addIntroduceServlet.do" method="post" enctype="multipart/form-data">
					<input type="hidden" name="memNo" value="<%=m.getMemNo()%>">
	
					<p>
						<label>프로필사진</label> 
						
						<img src="<%=request.getContextPath() %>/image/profile/profile.png"
					width="150" height="150" id="profile" >
					<input type="file" id="profiledata" name="profilephoto">
					</p>
				
					<script>
						$("#profile").click(e=>{
							$("#profiledata").click();
						})
						$("#profiledata").change(e=>{
							const reader=new FileReader();
							reader.readAsDataURL(e.target.files[0]);
							reader.onload=e=>{
								const path=e.target.result;
								$("#profile").attr("src",path);
							}
						})
					</script>
					<p>
						<label>이름</label> 
						<input class="w3-input" type="text" id="name" name="name"> 
					</p>
					<p>
						<label>나이</label> 
						<input class="w3-input" type="number" id="age" name="age" placeholder="필수입력값입니다" > 
					</p>
					<p>
						<label>학교</label> 
						<input class="w3-input" type="text"  name="school" > 
					</p>
					<p>
						<label>학과</label> 
						<input class="w3-input" type="text" name="department"> 
					</p>	
				
						<select class="w3-select" name="schoolstate"> 
							<option value="졸업">졸업</option>
							<option value="재학">재학</option>
							<option value="중퇴">중퇴</option>
						</select>
					
					<p class="border-bottom pb-3">
						<label>성별</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						남 &nbsp; <input class="w3-radio" id="gender" name="gender" type="radio" value="남" checked>
					&nbsp;	여 &nbsp; <input class="w3-radio" id="gender" name="gender" type="radio" value="여">
						
					</p>
				
					<p>
						<label>활동지역</label> 
						<input class="w3-input" type="text" id="sample5_address" name="activityarea" placeholder="주소">
					<input type="button" onclick="sample5_execDaumPostcode()" value="주소 검색">
					<div id="map" style="width:300px;height:300px;margin-top:10px;display:none"></div>
					</p>
					<p>
						<label>관심장르</label>
						재즈 <input type="checkbox" class="w3-checkbox" name="genre" value="G_1">
						클래식 <input type="checkbox" class="w3-checkbox" name="genre" value="G_2">
						일렉트로닉 <input type="checkbox" class="w3-checkbox" name="genre" value="G_3">
						힙합 <input type="checkbox" class="w3-checkbox" name="genre" value="G_4">
						인디 <input type="checkbox" class="w3-checkbox" name="genre" value="G_5">
						락 <input type="checkbox" class="w3-checkbox" name="genre" value="G_6">
						케이팝 <input type="checkbox" class="w3-checkbox" name="genre" value="G_7">
						기타 <input type="checkbox" class="w3-checkbox" name="genre" value="G_8">
				
						
						
					</p>
					<!-- <p>
						<label>관심장르</label>
						<select class="w3-select" name="genre" >
							<option value="jazz">재즈</option>
							<option value="classic">클래식</option>
							<option value="electronic">일렉트로닉</option>
							<option value="hiphop">힙합</option>
							<option value="indie">인디</option>
							<option value="rock">락</option>
							<option value="kpop">케이팝</option>
							<option value="others">기타</option>
						</select>
					</p> -->
						<p>
						<label>관심분야</label>
				드럼	<input class="w3-checkbox" type="checkbox"  name="interest" value="INST_1">
				베이스	<input type="checkbox" class="w3-checkbox" name="interest" value="INST_2">
				기타	<input type="checkbox" class="w3-checkbox" name="interest" value="INST_4">
				피아노	<input type="checkbox" class="w3-checkbox" name="interest" value="INST_5">	
				작곡	<input type="checkbox" class="w3-checkbox" name="interest" value="INST_6">
				색소폰	<input type="checkbox" class="w3-checkbox" name="interest" value="INST_7">
				트럼펫	<input type="checkbox" class="w3-checkbox" name="interest" value="INST_8">					
				플룻	<input type="checkbox" class="w3-checkbox" name="interest" value="INST_9">	
				바이올린 <input type="checkbox" class="w3-checkbox" name="interest" value="INST_10">	
				첼로	<input type="checkbox" class="w3-checkbox" name="interest" value="INST_11">	
				더블베이스 <input type="checkbox" class="w3-checkbox" name="interest" value="INST_3">	
				퍼커션	<input type="checkbox" class="w3-checkbox" name="interest" value="INST_12">	
				보컬	<input type="checkbox" class="w3-checkbox" name="interest" value="INST_13">
				믹싱	<input type="checkbox" class="w3-checkbox" name="interest" value="INST_14">	
				기타(오카리나,하모니카 등)	<input type="checkbox" class="w3-checkbox" name="interest" value="INST_15">
					
					</p>
					<!-- <p>
						<label>관심분야</label>
						<select class="w3-select" name="interest">
							<option value="drum">드럼</option>
							<option value="bass">베이스</option>
							<option value="guitar">기타</option>
							<option value="piano">피아노</option>
							<option value="composing">작곡</option>
							<option value="saxophone">색소폰</option>
							<option value="trumpet">트럼펫</option>
							<option value="flute">플룻</option>
							<option value="violin">바이올린</option>
							<option value="chello">첼로</option>
							<option value="doublebass">더블베이스</option>
							<option value="percussion">퍼커션</option>
							<option value="vocal">보컬</option>
							<option value="mixing">믹싱(DAW)</option>
							<option value="others">기타(오카리나,하모니카 등)</option>
						</select>
					</p> -->
					<p>
						<label>경력</label>				
						<input class="w3-input" type="text"  name="memcareer" >						
					</p>
					<p>
						<label>한줄소개</label> 
						<input class="w3-input" type="text"  name="introduce" > 
					</p>
					<p>
						<label>연주영상 파일</label>  <button type="button" class="vidoeplus">추가</button>	
						<input class="w3-input" type="file"  name="videofile" > 
							
					</p>
					<p>
						<label>연주영상 링크</label> 
						<input class="w3-input" type="text"  name="videolink" > 
					</p>
					<p>
						<label>음원 파일</label> <button type="button" class="musicplus">추가</button>	
						<input class="w3-input" type="file"  name="musicfile">
						
					</p>
					<p>
						<label>음원 링크</label> 
						<input class="w3-input" type="text"  name="musiclink" > 
					</p>
					<p>
						<label>이메일</label> 
						<input class="w3-input" type="text"  name="email" value="<%=mi.getEmail()%>"> 
					</p>
					<p class="w3-center">
						<button type="submit" id="joinBtn" class="w3-button w3-block w3-black w3-ripple w3-margin-top w3-round">확인</button>
					</p>
				</form>
			</div>
		</div>
	</div>
	<script>
		
		$(".vidoeplus").click((()=>{
			let count=0;
			return e=>{
			//js createElement()
			//jquery $("<p>")
				console.log(e.target);
				const $input=$("<input>")
				.attr({"type":"file","name":"videofile"+(++count)})
				.addClass("w3-input");
				$(e.target).next().after($input);
			}
		})());
	</script>
	<script>
		
		$(".musicplus").click((()=>{
			let count=0;
			return e=>{
			//js createElement()
			//jquery $("<p>")
				console.log(e.target);
				const $input=$("<input>")
				.attr({"type":"file","name":"videofile"+(++count)})
				.addClass("w3-input");
				$(e.target).next().after($input);
			}
		})());
	</script>
	
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=cc95c9b42927b4f8a48697f198c5594e&libraries=services"></script>
<script>
    var mapContainer = document.getElementById('map'), // 지도를 표시할 div
        mapOption = {
            center: new daum.maps.LatLng(37.537187, 127.005476), // 지도의 중심좌표
            level: 5 // 지도의 확대 레벨
        };

    //지도를 미리 생성
    var map = new daum.maps.Map(mapContainer, mapOption);
    //주소-좌표 변환 객체를 생성
    var geocoder = new daum.maps.services.Geocoder();
    //마커를 미리 생성
    var marker = new daum.maps.Marker({
        position: new daum.maps.LatLng(37.537187, 127.005476),
        map: map
    });


    function sample5_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                var addr = data.address; // 최종 주소 변수

                // 주소 정보를 해당 필드에 넣는다.
                document.getElementById("sample5_address").value = addr;
                // 주소로 상세 정보를 검색
                geocoder.addressSearch(data.address, function(results, status) {
                    // 정상적으로 검색이 완료됐으면
                    if (status === daum.maps.services.Status.OK) {

                        var result = results[0]; //첫번째 결과의 값을 활용

                        // 해당 주소에 대한 좌표를 받아서
                        var coords = new daum.maps.LatLng(result.y, result.x);
                        // 지도를 보여준다.
                        mapContainer.style.display = "block";
                        map.relayout();
                        // 지도 중심을 변경한다.
                        map.setCenter(coords);
                        // 마커를 결과값으로 받은 위치로 옮긴다.
                        marker.setPosition(coords)
                    }
                });
            }
        }).open();
    }
</script>

</body>
<%@ include file="/views/common/footer.jsp" %> 