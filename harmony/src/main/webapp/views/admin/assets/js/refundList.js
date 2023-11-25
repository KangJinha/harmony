/**
 * 
 */

const myModal = document.getElementById('modal');

$('tbody > tr').click(e=>{
	const $target = $(e.currentTarget);
	console.log($target.attr('data-bs-value'));
	$.ajax({
		type:"get",
	    url:requestPath+'/admin/refund/refund.do?no='+$target.attr('data-bs-value'),
	    data:{},
	    contentType:'application/json',
	    dataType:'json',
	    success:function(result){
			console.log(result);
	    	console.log("success : ", JSON.parse(result.refund));
			modalData(JSON.parse(result.refund));				    	
	    },
	    error:function(e){
	        console.log("error : ", e);
	        alert('조회 중 오류가 발생하였습니다.');
	        
	    }
    });
});
const modalData = (data)=>{
	const $frm = $('#refund-modal-form');
	$frm[0].reset();
	const $refundStatus = $('#refundStatus');
	$refundStatus.html('');
	let $stateSpan;
	switch(data.refundStateCode){
		case '110':
			$stateSpan = $('<span class="px-2 py-1 font-semibold leading-tight text-orange-700 bg-orange-100 rounded-full dark:text-white dark:bg-orange-600">').text('환불대기중');
			break;
		case '210':
			$stateSpan = $('<span class="px-2 py-1 font-semibold leading-tight text-green-700 bg-green-100 rounded-full dark:bg-green-700 dark:text-green-100">').text('환불완료');
			break;
		case '410':
			$stateSpan = $('<span class="px-2 py-1 font-semibold leading-tight text-red-700 bg-red-100 rounded-full dark:text-red-100 dark:bg-red-700">').text('환불취소');
			break;
		case '411':
			$stateSpan = $('<span class="px-2 py-1 font-semibold leading-tight text-red-700 bg-red-100 rounded-full dark:text-red-100 dark:bg-red-700">').text('환불거절');
			break;
	}
	$refundStatus.append($stateSpan);
	$('#payday').val(data.payReqDate);
	$('#price').val(data.price);
	$('#impUid').val(data.impUid);
	$('#payHisCode').val(data.payHisNo);
	$('#refundHisNo').val(data.refundHisNo);
	const $reportContent = $('#refundReason');
	$reportContent.html(data.refundReason);
	const $refundbtncontainer = $('#refundbtncontainer');
	$refundbtncontainer.html('');
	if(data.refundStateCode == '110'){
		$refundbtncontainer.html('<button type="button" class="btn btn-danger py-2" onclick="dorefund(event,\'accept\');">환불하기</button>'+
	'<button type="button" class="btn btn-secondary py-2" onclick="dorefund(event,\'reject\');">거부하기</button>');
	}
	const $stateDetail = $('#stateDetail');
	let stateDetail = data.refundState;
	$stateDetail.val(stateDetail!=null?stateDetail:'');
}

const dorefund = (e,type)=>{
	const impUid = $('#impUid').val();
	const payHisCode = $('#payHisCode').val();
	const refundHisNo = $('#refundHisNo').val();
	$.ajax({
		type:"post",
	    url:requestPath+'/ajax/refund/refund.do',
	    data:{type:type,impUid:impUid,payHisCode:payHisCode,refundHisNo:refundHisNo},
	    dataType:'json',
	    success:function(result){
			console.log(result);
			if(result.result == 'success'){
	    	console.log("success : ", result.result);
	    	alert('환불처리에 성공하였습니다.')
	    	location.reload(true);
	    	}else{
			console.log("success : ", result.result);
	    	alert('환불처리에 실패하였습니다.')
			}
	    },
	    error:function(e){
	        console.log("error : ", e);
	        alert('환불처리 중 오류가 발생하였습니다.');
	        
	    }
    });
}
