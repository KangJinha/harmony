<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.harmony.payment.model.dto.PaymentView, com.harmony.payment.model.dto.PaymentApplyDate, java.util.List" %>
<%@ include file="/views/common/header.jsp"%>
<% PaymentView view = (PaymentView)request.getAttribute("paymentView");%>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=b314c47810b31c3c487d6f6ad04d71b1&libraries=services"></script>
<section class="container">
	<div class="w-100 px-5 py-3 mb-3" style="background-color: #ffc107; border-radius: 10px">
		<h3 class="h3" style="color: white">내 신청정보 보기</h3>
	</div>
	<div class="row row-cols-2">
		<div class="col">
			<div class="d-flex flex-column mb-3">
				<div class="input-group">
					<span class="input-group-text">선생님정보</span>
				</div>
				<div class="d-flex flex-row input-group">
					<span class="input-group-text">이름</span>
					<input class="form-control plane-text" type="text" id="teacherName" value="<%=view.getTeacherName()%>" readOnly>
				</div>
				<div class="d-flex flex-row input-group">
					<span class="input-group-text">성별</span>
					<input class="form-control plane-text" type="text" id="teacherGender" value="<%=view.getTeacherGender()%>" readOnly>
				</div>
				<div class="d-flex flex-row input-group">
					<span class="input-group-text">이메일</span>
					<input class="form-control plane-text" type="text" id="teacherEmail" value="<%=view.getTeacherEmail()%>" readOnly>
				</div>
				<div>
					<span class="input-group-text" id="teacherIntroduce"><%=view.getTeacherIntroduce() %></span>
				</div>
			</div>
			<div class="d-flex flex-column mb-3">
				<div class="input-group">
					<span class="input-group-text">위치</span>
				</div>
				<div class="detailsContainer_content">
                   <div id="map" style="width:100%;height:400px;"></div>
               </div>
			</div>
		</div>
		<div class="col">
			<div class="d-flex flex-column mb-3">
				<div class="input-group">
					<span class="input-group-text">레슨정보</span>
				</div>
				<%if(view.getDates()!=null && view.getDates().size()>0){ %>
				<%for(PaymentApplyDate d : view.getDates()){ %>
				<div class="d-flex flex-row input-group">
					<span class="input-group-text">요일</span>
					<input class="form-control plane-text" type="text" value="<%=d.getDayOfWeek()%>" readOnly>
					<input class="form-control" type="time" value="<%=String.valueOf(d.getStartTime()).substring(11, 16)%>" readOnly>
					<span class="input-group-text">~</span>
					<input class="form-control" type="time" value="<%=String.valueOf(d.getEndTime()).substring(11,16)%>" readOnly>
				</div>
				<%}} %>
				<div class="d-flex flex-row input-group">
					<span class="input-group-text">횟수</span>
					<input class="form-control plane-text" type="text" id="times" value="<%=view.getLessonTimes()%>" readOnly>
					<span class="input-group-text">횟수당 가격</span>
					<input class="form-control text-end" type="text" id="price" value="<%=view.getPrice()%>" readOnly>
					<span class="input-group-text">원</span>
				</div>
				<div class="d-flex flex-row input-group mb-3">
					<form  class="d-flex flex-row input-group mb-3">
					<input type="hidden" name="applyNo" id="applyNo" value="<%=view.getApplyNo()%>">
					<span class="input-group-text">총 가격</span>
					<input class="form-control text-end" type="text" name="totalprice" id="totalprice" value="<%=view.getLessonTimes()*view.getPrice()%>" readOnly>
					<span class="input-group-text">원</span>
					</form>
				</div>
				<div class="d-flex justify-content-center">
					<%if(view.getPaymentStatus()!=null && !view.getPaymentStatus().equals("100")){ %>
					<button class="btn btn-danger btn-lg" data-bs-toggle="modal" data-bs-target="#refundmodal" 
					<%=view.getPaymentStatus().equals("110")||view.getPaymentStatus().equals("210")||view.getPaymentStatus().equals("310")||view.getPaymentStatus().equals("410")||view.getPaymentStatus().equals("411")?"disabled":"" %>
					>환불하기</button>
					
					<%}else if(view.getStatus().equals("Y")){ %>
					<button class="btn btn-warning btn-lg" onclick="payment();">결제하기</button>
					<%}else if(view.getStatus().equals("C")){ %>
					<span class="d-inline-block" tabindex="0" data-bs-toggle="popover" data-bs-trigger="hover focus" data-bs-placement="bottom" data-bs-content="강사가 확인중입니다.">
					  <button class="btn btn-warning btn-lg" type="button" disabled>결제하기</button>
					</span>
					<%}else{ %>
					<span class="d-inline-block" tabindex="0" data-bs-toggle="popover" data-bs-trigger="hover focus" data-bs-placement="bottom" data-bs-content="강사가 거절한 신청정보입니다.">
					  <button class="btn btn-warning btn-lg" type="button" disabled>----</button>
					</span>
					<%} %>
				</div>
			</div>
		</div>
	</div>
</section>
<script>
const popoverTriggerList = document.querySelectorAll('[data-bs-toggle="popover"]')
const popoverList = [...popoverTriggerList].map(popoverTriggerEl => new bootstrap.Popover(popoverTriggerEl))
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

geocoder.addressSearch('<%=view.getPlace()%>', function(result, status) { 
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
        content: '<div style="width:150px;text-align:center;padding:6px 0;"><%=view.getPlace()%></div>'
    });
    infowindow.open(map, marker);

    // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
    map.setCenter(coords);
    map.setDraggable(false);
} 
});
</script>
<script src="https://cdn.iamport.kr/v1/iamport.js"></script>
<script>
	IMP.init('imp42878223');
	
const payment = ()=>{
	const applyNo = $('#applyNo').val();
	console.log(applyNo);
	const totalprice = $('#totalprice').val();
	console.log(totalprice);
	$.ajax({
		type:"post",
	    url:'<%=request.getContextPath()%>/payment/ajax/prepare.do',
	    data:{applyNo:applyNo},
	    dataType:'json',
	    success:function(result){
	    	console.log(result)
			requestPay(result.merchantUid, totalprice);
	    },
	    error:function(e){
	        console.log("error : ", e);
	        alert('조회 중 오류가 발생하였습니다.');
	        
	    }
    });
};
	
function requestPay(payhisno, totalprice) {
    IMP.request_pay({
      pg: "kcp.{AO09C}",
      merchant_uid: payhisno,   // 주문번호
      amount: totalprice,                         // 숫자 타입
    }, function (rsp) {
    	if(rsp.success){
    		console.log(rsp.imp_uid);
    		console.log(rsp.merchant_uid);
    	$.ajax({
    		type:"post",
    	    url:'<%=request.getContextPath()%>/payment/ajax/payend.do',
    	    data:{merchant_uid:rsp.merchant_uid,
    	    	imp_uid:rsp.imp_uid},
    	    dataType:'json',
    	    success:function(data){
    	    	console.log(data);
    	    	if(data.result == 'success'){
    	    		alert('결제가 성공하였습니다.');
    	    		location.reload(true);
    	    	}else{
    	    		alert('결제에 실패하였습니다.');
    	    		
    	    	}
    	    },
    	    error:function(e){
    	        console.log("error : ", e);
    	        alert('조회 중 오류가 발생하였습니다.');
    	        
    	    }
        });
    	}
    });
  }
</script>
<div class="modal fade" id="refundmodal" tabindex="-1"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div
		class="modal-dialog modal-dialog-centered modal-dialog-scrollable modal-xl">
		<div class="modal-content">
			<form id="refund-modal-form" method="post" onsubmit="refundRequest(event);">
			<div class="modal-header bg-gray-50 dark:bg-gray-800">
				<h1 class="modal-title fs-5 font-semibold tracking-wide text-left text-gray-500 dark:text-gray-400 ">환불신청하기</h1>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<div class="container-fluid" id="modal-container">
					<div class="w-full mb-8 overflow-hidden rounded-lg shadow-xs">
						<div class="row mb-3 border-b px-3 py-1 d-flex flex-column">
							<div class="col input-group-text">
								환불사유
							</div>
							<div class="col">
								<input type="hidden" name="payHisNo" value="<%=view.getPayHisNo()%>" id="payHisNo">
								<textarea class="form-control w-full" name="refundReason" rows="3" style="resize:vertical;" id="refundReason"></textarea>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="submit" class="btn btn-danger">환불신청</button>
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
			</div>
			</form>
		</div>
	</div>
</div>
<script>

const refundRequest = (e)=>{
	e.preventDefault();
	$.ajax({
		type:"post",
	    url:'<%=request.getContextPath()%>/refund/request.do',
	    data:{payHisNo:$('#payHisNo').val(),refundReason:$('#refundReason').val()},
	    dataType:'json',
	    success:function(data){
	    	console.log("success : ", data);
	    	alert('성공적으로 신청되었습니다.');
	    	location.reload(true);
	    },
	    error:function(e){
	        console.log("error : ", e);
	        alert('신청중 오류가 발생하였습니다.');
	        
	    }
    });
	
};

</script>
<%@ include file="/views/common/footer.jsp"%>