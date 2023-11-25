<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Hi+Melody&family=Nanum+Gothic+Coding&display=swap" rel="stylesheet">    
    
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/ensemble/boardWrite.css" type="text/css">
<%@ page import= "java.util.List, 
					com.harmony.ensemble.model.dto.Inst" %>
<%
	List<Inst> inst = (List<Inst>)request.getAttribute("inst");
%>

<%@ include file="/views/common/header.jsp" %>  

<section>
<form id="myForm">
<div class="boardContainer">
		<div>
			<span class="title_span">제목</span>
			<input type="text" name="title" id="title" class="form-control">
		</div>
		
		
		<div class="part_container">
				<span class="title_span">모집 파트</span>
			<table class="part_table">
			<tr>
				<td>
			   		<input class="inst_chk" type="checkbox" name="inst" value="INST_1" style="display:none">
					<span class="inst_chk_span">드럼</span>
		   		</td>
		   		<td>
			   		<input class="inst_chk" type="checkbox" name="inst" value="INST_2" style="display:none">
					<span class="inst_chk_span">베이스</span>
		    	</td>
		    	<td>
			    	<input class="inst_chk" type="checkbox" name="inst" value="INST_3" style="display:none">
					<span class="inst_chk_span">더블베이스</span>
		    	</td>
		    	<td>
			    	<input class="inst_chk" type="checkbox" name="inst" value="INST_4" style="display:none">
					<span class="inst_chk_span">기타</span>
		    	</td>
		    	<td>
			    	<input class="inst_chk" type="checkbox" name="inst" value="INST_5" style="display:none">
					<span class="inst_chk_span">피아노</span>
		    	</td>
		    	<td>
			    	<input class="inst_chk" type="checkbox" name="inst" value="INST_6" style="display:none">
					<span class="inst_chk_span">작곡</span>
		    	</td>
		    	<td>
			    	<input class="inst_chk" type="checkbox" name="inst" value="INST_7" style="display:none">
					<span class="inst_chk_span">색소폰</span>
		    	</td>
		    </tr>	
		    <tr>
		    	<td>
			    	<input class="inst_chk" type="checkbox" name="inst" value="INST_8" style="display:none">
					<span class="inst_chk_span">트럼펫</span>
		    	</td>
		    	<td>
			    	<input class="inst_chk" type="checkbox" name="inst" value="INST_9" style="display:none">
					<span class="inst_chk_span">플룻</span>
		    	</td>
		    	<td>	
			    	<input class="inst_chk" type="checkbox" name="inst" value="INST_10" style="display:none">
					<span class="inst_chk_span">바이올린</span>
		    	</td>
		    	<td>
			    	<input class="inst_chk" type="checkbox" name="inst" value="INST_11" style="display:none">
					<span class="inst_chk_span">첼로</span>
		    	</td>
		    	<td>
			    	<input class="inst_chk" type="checkbox" name="inst" value="INST_12" style="display:none">
					<span class="inst_chk_span">퍼커션</span>
		    	</td>
		    	<td>
			    	<input class="inst_chk" type="checkbox" name="inst" value="INST_13" style="display:none">
					<span class="inst_chk_span">보컬</span>
		        </td>
		        <td>
		        <input class="inst_chk" type="checkbox" name="inst" value="INST_14" style="display:none">
				<span class="inst_chk_span">믹싱(DAW)</span>
				</td>
			</tr>
		</table>
	
		</div>
		
		
		
		
		<div class="loc_container">
			<table class="loc_table">
				<tr>
					<td>
						<span class="title_span">지역</span>
					</td>
					<td>	
						<input type="text" name="location" id="location" class="form-control"><br>
					</td>		
				<tr>	
					<td>
						<span class="title_span">장소</span>
					</td>
					<td>	
						<input type="text" id="sample6_postcode" placeholder="우편번호" class="form-control">
					</td>
				
					<td>
						<input type="button" onclick="sample6_execDaumPostcode()" id="addr_btn" value="주소 검색" class="form-control"><br>
					</td>
				</tr>	
				<tr>	
					<td>	
			<!-- 			<input type="text" id="sample6_detailAddress" placeholder="상세주소"><br> -->
					</td>
					<td>
						<input type="text" id="sample6_address" placeholder="주소" class="form-control"><br>
				
						<input type="text" id="sample6_extraAddress" placeholder="참고항목" class="form-control">
					</td>
				</tr>
			</table>
				<input type="hidden" name="place" id="place">
		</div>		
			<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
			
			<script>
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
// 			                document.getElementById("sample6_detailAddress").focus();
			                
			                //합쳐서 hidden 인풋에 넣기!
			                document.getElementById("place").value= addr+ " "+extraAddr;
			                
			            }
			        }).open();
			    }
			</script>
	
		<div>
			<p class="title_span">상세 설명</p>
			  <textarea rows="5" cols="50" name="detail" id="detail" class="form-control"></textarea>
		</div>
	
		<div class="btn_container">
			<input type="button" id="board_end" value="등록">
<!-- 				<input type="button" onclick="submit_form()">등록</button> -->
		</div>
</div>
</form>
</section>
<script>
///	function submit_form(){   2번째꺼,, gson이 안됨ㅜ
// 		let parts = $('input[name="inst"]:checked').map(function(){
// 			return $(this).val();
// 		}).get();
		
// 		let location = $('#location').val();
// 		let title= $('#title').val();
// 		let place = $('#place').val();
// 		let detail = $('#detail').val();
		
// 		let formData = new FormData();
// 		formData.append("parts", JSON.stringify(parts));
// 		formData.append("location", location);
// 		formData.append("title",title);
// 		formData.append("detail",detail);
		
// 		$.ajax({
// 			type: "POST",
<%-- 			url: "<%=request.getContextPath()%>/ensemble/boardWriteEnd.do", --%>
// 			data: formData,
// 			processData: false,
// 			contentType: false,
// 			success: function(data){
// 				console.log(data);
// 			},
// 			error: function(error){
// 				console.log(error);
// 			}
// 		});
// 	}
</script>

<script>
 $("#board_end").click(e=>{

	
	const form = new FormData();
	
	const parts = $('.inst_chk:checked').map(function(){
		return $(this).val();
	}).get();
	
	const location = $('#location').val();
	const place =  $('#place').val();
	const detail = $('#detail').val();
	const title = $('#title').val();
	
	
	form.append("parts", parts);
	form.append("location", location);
	form.append("place", place);
	form.append("detail", detail);
	form.append("title",title);
	
	
	$.ajax({
		url: "<%=request.getContextPath()%>/ensemble/boardWriteEnd.do",
		data:form,
		type:"post",
		processData:false,
		contentType:false,
		success:data=>{
			alert("등록 성공");
			window.location.assign('<%=request.getContextPath()%>');
		},
		error:(r,e)=>{
			alert("등록 실패");
		},
		complete:()=>{
		}
	});
	
});	
	

</script>


<script>
	
// 	$("#board_end").click(e=>{   첫 번째거... gson 안됨..\
// 		const want_part = $('.inst_chk:checked').map(function(){
// 			return $(this).next().text();
// 		}).get();
		
// 		const location = $('#location').val();
// 		const place =  $('#place').val();
// 		const detail = $('#detail').val();
// 		const title = $('#title').val();

// 		const ensBoard = {
// 			ensLocation: location,
// 			ensPlace: place,
// 			ensDetail: detail,
// 			ensBoardTitle: title
// 		};
		
// 		const ensembleBoardWantPart = {
// 			ensInstNo : want_part
// 		};
		
// 		console.log(want_part);
// 		console.log(ensembleBoardWantPart);
		
<%-- 		$.post("<%=request.getContextPath()%>/ensemble/boardWriteEnd.do", --%>
// 					{
// 						data: JSON.stringify(ensBoard), 
// 						part: JSON.stringify(ensembleBoardWantPart)		
// 					},
// 					data=>{
// 						console.log(data);
// 					}
// 				);
// 	});
</script>
<script>
$('.inst_chk_span').click((e)=>{
	const $target = $(e.target);
	$target.prev().click();		
});
$('.inst_chk').click((e)=>{
	const $target = $(e.target);
	console.log($target.next());
	$target.next().toggleClass('selected');
});


</script>

<%@ include file="/views/common/footer.jsp" %>