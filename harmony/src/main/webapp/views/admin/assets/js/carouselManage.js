/**
 * 
 */
(() => {
  'use strict'
})();
const addNewBlankCarousel = ()=>{
	const today = new Date();
	let year = today.getFullYear();
	let month = ('0'+(today.getMonth()+1)).slice(-2);
	let day = ('0'+today.getDate()).slice(-2);
	const $div = $('<div>').addClass('card newcard').html('<form method="post" enctype="multipart/form-data" class="align-items-center needs-validation" novalidate onchange="frmchange(event);" onsubmit="validate(event,\'insert\');">'+
	          			'<div class="card-header input-group input-group-sm">'+
          					'<span class="input-group-text">관리번호</span>'+
          					'<input class="form-control form-control-plaintext" type="number" name="crslNo" value="" style="width:50px" disabled>'+
          					'<span class="input-group-text">이름</span>'+
          					'<input class="form-control" type="text" name="crslName" placeholder="이름을입력해주세요" value="">'+
	          			'</div>'+
		          		'<img class="card-img-top">'+
		          		'<ul class="list-group list-group-flush">'+
		          			'<li class="list-group-item">'+
		          				'<div class="input-group input-group-sm">'+
			          				'<span class="input-group-text">이미지수정</span>'+
			          				'<input class="form-control" type="file" name="newImage" placeholder="이미지변경시에만 입력해주세요" accept="image/*" onchange="changeThumbnail(event);">'+
			          			'</div>'+
		          			'</li>'+
		          			'<li class="list-group-item">'+
		          				'<div class="input-group input-group-sm">'+
			          				'<span class="input-group-text">게시일</span>'+
			          				'<input class="form-control" type="date" name="startDate" placeholder="게시시작일" value="'+(year+'-'+month+'-'+day)+'" required>'+
			          				'<span class="input-group-text">~</span>'+
			          				'<input class="form-control" type="date" name="endDate" placeholder="게시종료일" value="" required>'+
			          			'</div>'+
		          			'</li>'+
		          			'<li class="list-group-item">'+
		          				'<div class="input-group input-group-sm">'+
			          				'<span class="input-group-text">표시순서</span>'+
			          				'<input class="form-control" type="number" name="viewrank" placeholder="순번" value="">'+
			          				'<span class="input-group-text">표시시간</span>'+
			          				'<input class="form-control" type="number" name="intervalms" placeholder="유지시간 단위 ms" value="">'+
			          				'<span class="input-group-text">ms</span>'+
			          			'</div>'+
		          			'</li>'+
		          			'<li class="list-group-item">'+
		          				'<div class="input-group input-group-sm">'+
			          				'<span class="input-group-text">페이지링크</span>'+
			          				'<input class="form-control" type="text" name="pagelink" placeholder="클릭시 이동할 주소를 입력해주세요" value="">'+
			          			'</div>'+
		          			'</li>'+
		          			'<li class="list-group-item">'+
		          				'<div class="row justify-content-between">'+
		          					'<div class="col-2">'+
		          						'<span class="px-2 py-1 font-semibold leading-tight text-orange-700 bg-orange-100 rounded-full dark:text-white dark:bg-orange-600">게시대기중</span>'+
                        			'</div>'+
			          				'<div class="col-2 flex justify-content-end text-sm" name="buttonBar">'+
			          				  '<button type="button"  class="flex items-center justify-between px-2 py-2 text-sm font-medium leading-5 text-purple-600 rounded-lg dark:text-gray-400 focus:outline-none focus:shadow-outline-gray" name="newResetBtn" aria-label="Reset" onclick="donewreset(event);">'+
			                            '<svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="w-5 h-5 bi bi-arrow-repeat" viewBox="0 0 20 20">'+
										  '<path d="M11.534 7h3.932a.25.25 0 0 1 .192.41l-1.966 2.36a.25.25 0 0 1-.384 0l-1.966-2.36a.25.25 0 0 1 .192-.41zm-11 2h3.932a.25.25 0 0 0 .192-.41L2.692 6.23a.25.25 0 0 0-.384 0L.342 8.59A.25.25 0 0 0 .534 9z"/>'+
										  '<path fill-rule="evenodd" d="M8 3c-1.552 0-2.94.707-3.857 1.818a.5.5 0 1 1-.771-.636A6.002 6.002 0 0 1 13.917 7H12.9A5.002 5.002 0 0 0 8 3zM3.1 9a5.002 5.002 0 0 0 8.757 2.182.5.5 0 1 1 .771.636A6.002 6.002 0 0 1 2.083 9H3.1z"/>'+
										'</svg>'+
			                          '</button>'+
			                          '<button type="submit"  class="flex items-center justify-between px-2 py-2 text-sm font-medium leading-5 text-purple-600 rounded-lg dark:text-gray-400 focus:outline-none focus:shadow-outline-gray" name="newInsertBtn" aria-label="Insert">'+
				                         '<svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="w-5 h-5 bi bi-pencil-square" viewBox="0 0 20 20">'+
											 '<path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>'+
											 '<path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>'+
										 '</svg>'+
			                          '</button>'+
			                          '<button type="button" class="flex items-center justify-between px-2 py-2 text-sm font-medium leading-5 text-purple-600 rounded-lg dark:text-gray-400 focus:outline-none focus:shadow-outline-gray" name="newDeleteBtn" aria-label="Delete" onclick="donewdelete(event);">'+
			                            '<svg class="w-5 h-5" aria-hidden="true" fill="currentColor" viewBox="0 0 20 20">'+
			                              '<path fill-rule="evenodd" d="M9 2a1 1 0 00-.894.553L7.382 4H4a1 1 0 000 2v10a2 2 0 002 2h8a2 2 0 002-2V6a1 1 0 100-2h-3.382l-.724-1.447A1 1 0 0011 2H9zM7 8a1 1 0 012 0v6a1 1 0 11-2 0V8zm5-1a1 1 0 00-1 1v6a1 1 0 102 0V8a1 1 0 00-1-1z" clip-rule="evenodd"></path>'+
			                            '</svg>'+
			                          '</button>'+
			                        '</div>'+
		                        '</div>'+
		          			'</li>'+
		          		'</ul>'+
	          		'</form>');
	$('#cardcontainer').append($div);
	$('main').animate({scrollTop : $div.offset().top},500);
};
const changeThumbnail = (e)=>{
	const $target = $(e.target);
	const $img = $target.closest('form').find('img');
	let fileReader = new FileReader();
	fileReader.readAsDataURL($target[0].files[0]);
	fileReader.onloadend = (e)=>{
		$img.attr('src',e.target.result);
	};
};
const frmchange = (e)=>{
	let startdate = new Date($(e.currentTarget).find('input[name="startDate"]').val());
	let enddate = new Date($(e.currentTarget).find('input[name="endDate"]').val());
	if(enddate-startdate>0){
		$(e.currentTarget).find('input[name="endDate"]')[0].setCustomValidity("");
	}else{
		$(e.currentTarget).find('input[name="endDate"]')[0].setCustomValidity("종료는 시작보다 우선할 수 없습니다.");
	}
}
const validate = (e,type)=>{
	e.target.classList.add('was-validated');
	let startdate = new Date($(e.target).find('input[name="startDate"]').val());
	let enddate = new Date($(e.target).find('input[name="endDate"]').val());
	if(enddate-startdate<0 || !e.target.checkValidity()){
		e.preventDefault();
		e.stopPropagation();
		return false;
	}
	doajax(e,type);
}

const doajax = (e,type)=>{
	e.preventDefault();
	const $frm = $(e.target).closest('form');
	switch(type){
		case 'insert': insertCarousel($frm[0]); break;
		case 'edit': editCarousel($frm[0]); break;
		case 'delete': deleteCarousel($frm[0]); break;
	}
};
//$('button[name="editBtn"]').on('click',(e)=>{
//	e.preventDefault();
//	const $frm = $(e.target).closest('form');
//	editCarousel($frm[0]);
//});
const doreset = (e)=>{
	const $frm = $(e.target).closest('form');
	const $img = $frm.find('img');
	$img.attr('src',contextPath+'/image/carousel/'+$frm.find('[name="oldImage"]').val());
	$frm[0].reset();
}

//$('button[name="newInsertBtn"]').on('click',(e)=>{
//	e.preventDefault();
//	const $frm = $(e.target).closest('form');
//	insertCarousel($frm[0]);
//});
const donewdelete = (e)=>{
	e.preventDefault();
	const $frm = $(e.target).closest('form');
	$frm.parent().remove();
}
const donewreset = (e)=>{
	const $frm = $(e.target).closest('form');
	const $img = $frm.find('img');
	$img.attr('src','');
	$frm[0].reset();
}

const insertCarousel = (form)=>{
	const formData = new FormData(form); 
 	formData.append('type','insert');
    $.ajax({
		type:"post",
		enctype:'multipart/form-data',
	    url:contextPath+'/admin/ajax/carouselManage.do',
	    data:formData,
	    dataType:'json',
	    processData:false,
	    contentType:false,
	    cache:false,
	    success:function(data){
	    	console.log("success : ", data);
	    	alert('성공적으로 입력되었습니다.');
	    	const $div = $('<div>').addClass('card').attr('id','card_'+data.crslNo).html(data.body);
	    	$(form).parent().remove();
	    	$('#cardcontainer').append($div);
	    },
	    error:function(e){
	        console.log("error : ", e);
	        alert('입력중 오류가 발생하였습니다.');
	        
	    }
    });
};

const editCarousel = (form)=>{
	const formData = new FormData(form); 
 	formData.append('type','edit');
    $.ajax({
		type:"post",
		enctype:'multipart/form-data',
	    url:contextPath+'/admin/ajax/carouselManage.do',
	    data:formData,
	    dataType:'json',
	    processData:false,
	    contentType:false,
	    cache:false,
	    success:function(data){
	    	console.log("success : ", data);
	    	alert('성공적으로 수정되었습니다.');
	    	$(form).parent().html(data.body);
	    },
	    error:function(e){
	        console.log("error : ", e);
	        alert('수정중 오류가 발생하였습니다.');
	        
	    }
    });
};

const deleteCarousel = (form)=>{
	const formData = new FormData(form); 
 	formData.append('type','delete');
    $.ajax({
		type:"post",
		enctype:'multipart/form-data',
	    url:contextPath+'/admin/ajax/carouselManage.do',
	    data:formData,
	    dataType:'json',
	    processData:false,
	    contentType:false,
	    cache:false,
	    success:function(data){
	    	console.log("success : ", data);
	    	alert('성공적으로 삭제되었습니다.');
	    	$(form).parent().remove();
	    },
	    error:function(e){
	        console.log("error : ", e);
	        alert('삭제중 오류가 발생하였습니다.');
	        
	    }
    });
};

