/**
 * 
 */

const getReadMessageIcon = ()=>{
	return $('<div>').append($('<svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-envelope-open" viewBox="0 0 16 16">').html('<path d="M8.47 1.318a1 1 0 0 0-.94 0l-6 3.2A1 1 0 0 0 1 5.4v.817l5.75 3.45L8 8.917l1.25.75L15 6.217V5.4a1 1 0 0 0-.53-.882l-6-3.2ZM15 7.383l-4.778 2.867L15 13.117zm-.035 6.88L8 10.082l-6.965 4.18A1 1 0 0 0 2 15h12a1 1 0 0 0 .965-.738ZM1 13.116l4.778-2.867L1 7.383v5.734ZM7.059.435a2 2 0 0 1 1.882 0l6 3.2A2 2 0 0 1 16 5.4V14a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V5.4a2 2 0 0 1 1.059-1.765l6-3.2"/></path>'));
}

 
 const addUnReadMessageList = (data)=>{
	 const $div = $('<div class="list-group-item list-group-item-action message-list d-flex flex-row" style="align-items: center;" onclick="openMessage(event,\''+data.msgNo+'\')">');
	 const $icondiv = $('<div>').append($('<svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="#000" class="bi bi-envelope" viewBox="0 0 16 16">').html('<path d="M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2zm2-1a1 1 0 0 0-1 1v.217l7 4.2 7-4.2V4a1 1 0 0 0-1-1zm13 2.383-4.708 2.825L15 11.105zm-.034 6.876-5.64-3.471L8 9.583l-1.326-.795-5.64 3.47A1 1 0 0 0 2 13h12a1 1 0 0 0 .966-.741M1 11.105l4.708-2.897L1 5.383z"></path>'));
	 const $spandiv = $('<div class="px-2 d-flex flex-column">'); 
	 let $category;
	 switch(data.catCode){
		 case 'NOTICE':
			 $category = $('<span class="px-2 text-nowrap message category-notice">').text('공지');
			 break;
		 case 'MEMBER':
			 $category = $('<span class="px-2 text-nowrap message category-member">').text('개인');
			 break;
		 case 'LESSON':
			 $category = $('<span class="px-2 text-nowrap message category-lesson">').text('레슨');
			 break;
		 case 'ENSEMBLE':
			 $category = $('<span class="px-2 text-nowrap message category-ensemble">').text('합주');
			 break;
	 }
	 let $sender = $('<span>').text(data.sendMem);
	 $spandiv.append($category).append($sender);
	 $div.append($icondiv).append($spandiv);
	 return $div;
 };
  const addReadMessageList = (data)=>{
	 const $div = $('<div class="list-group-item list-group-item-action message-list d-flex flex-row" style="align-items: center;" onclick="openMessage(event,\''+data.msgNo+'\')">');
	 const $svg = $('<svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="#000" class="bi bi-envelope-open" viewBox="0 0 16 16">').html('<path d="M8.47 1.318a1 1 0 0 0-.94 0l-6 3.2A1 1 0 0 0 1 5.4v.817l5.75 3.45L8 8.917l1.25.75L15 6.217V5.4a1 1 0 0 0-.53-.882l-6-3.2ZM15 7.383l-4.778 2.867L15 13.117zm-.035 6.88L8 10.082l-6.965 4.18A1 1 0 0 0 2 15h12a1 1 0 0 0 .965-.738ZM1 13.116l4.778-2.867L1 7.383v5.734ZM7.059.435a2 2 0 0 1 1.882 0l6 3.2A2 2 0 0 1 16 5.4V14a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V5.4a2 2 0 0 1 1.059-1.765l6-3.2"/></path>');
	 const $icondiv = $('<div>').append($svg);
	 const $spandiv = $('<div class="px-2 d-flex flex-column">'); 
	 let $category;
	 switch(data.catCode){
		 case 'NOTICE':
			 $category = $('<span class="px-2 text-nowrap message category-notice">').text('공지');
			 break;
		 case 'MEMBER':
			 $category = $('<span class="px-2 text-nowrap message category-member">').text('개인');
			 break;
		 case 'LESSON':
			 $category = $('<span class="px-2 text-nowrap message category-lesson">').text('레슨');
			 break;
		 case 'ENSEMBLE':
			 $category = $('<span class="px-2 text-nowrap message category-ensemble">').text('합주');
			 break;
	 }
	 let $sender = $('<span>').text(data.sendMem);
	 $spandiv.append($category).append($sender);
	 $div.append($icondiv).append($spandiv);
	 return $div;
 };
 const getMessageList=(memno)=>{
	 //ajax
	 $.ajax({
		type:"get",
	    url:contextPath+'/message/list.do?no='+memno,
	    data:{},
	    contentType:'application/json',
	    dataType:'json',
	    success:function(result){
	    	console.log("success : ", JSON.parse(result.messageList));
			messageListData(JSON.parse(result.messageList));				    	
	    },
	    error:function(e){
	        console.log("error : ", e);
	        alert('조회 중 오류가 발생하였습니다.');
	        
	    }
    });
 }
 const messageListData = (data)=>{
	 const $listContainer = $('#messageListContainer');
	 $listContainer.html('');
	 const $messageList = $(data);
	 $messageList.each((i,v)=>{
		 if(v.readState == "NOTREAD"){
			 $listContainer.append(addUnReadMessageList(v));
		 }else{
			 $listContainer.append(addReadMessageList(v));
		 }
	 });
 }
 const openMessage = (e,no)=>{
	 const $target = $(e.currentTarget);
	 $target.parent().children().removeClass('active');
	 $target.addClass('active');
	 //ajax
	 $.ajax({
		type:"get",
	    url:contextPath+'/message/message.do?no='+no,
	    data:{},
	    contentType:'application/json',
	    dataType:'json',
	    success:function(result){
	    	console.log("success : ", JSON.parse(result.message));
			messageData(JSON.parse(result.message));			    	
	    },
	    error:function(e){
	        console.log("error : ", e);
	        alert('조회 중 오류가 발생하였습니다.');
	    }
    });
 }
 
 const messageData = (data)=>{
	 const $msgContainer = $('#messageContainer');
	 $msgContainer.html('');
	 const $msgInfo = $('<div class="row row-cols-auto">');
	 const $msgCategory = $('<div class="col">');
	 let $category;
	 switch(data.catCode){
		 case 'NOTICE':
			 $category = $('<span class="px-2 py-2 text-nowrap message category-notice">').text('공지');
			 break;
		 case 'MEMBER':
			 $category = $('<span class="px-2 py-2 text-nowrap message category-member">').text('개인');
			 break;
		 case 'LESSON':
			 $category = $('<span class="px-2 py-2 text-nowrap message category-lesson">').text('레슨');
			 break;
		 case 'ENSEMBLE':
			 $category = $('<span class="px-2 py-2 text-nowrap message category-ensemble">').text('합주');
			 break;
	 }
	 $msgCategory.append($category);
	 const $sender = $('<div class="col input-group" style="width: auto;">').append($('<span class="input-group-text">').text('보낸이')).append($('<input type="text" class="form-control">').val(data.sendMem));
	 const $sendDate = $('<div class="col input-group" style="width: auto;">').append($('<span class="input-group-text">').text('보낸날짜')).append($('<input type="text" class="form-control">').val(data.sendDate));
	 $msgInfo.append($msgCategory).append($sender).append($sendDate);
	 $msgContainer.append($msgInfo);
	 const $msg = $('<div class="row">').html(data.content);
	 $msgContainer.append($msg);
 };