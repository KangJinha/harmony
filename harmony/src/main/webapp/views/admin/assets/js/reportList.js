/**
 * 
 */

const myModal = document.getElementById('modal');

$('tbody > tr').click(e=>{
	const $target = $(e.currentTarget);
	console.log($target.attr('data-bs-value'));
	$.ajax({
		type:"get",
	    url:requestPath+'/admin/ajax/report.do?no='+$target.attr('data-bs-value'),
	    data:{},
	    contentType:'application/json',
	    dataType:'json',
	    success:function(result){
			console.log(result);
	    	console.log("success : ", JSON.parse(result.report));
			modalData(JSON.parse(result.report));				    	
	    },
	    error:function(e){
	        console.log("error : ", e);
	        alert('조회 중 오류가 발생하였습니다.');
	        
	    }
    });
});
const modalData = (data)=>{
	const $frm = $('#report-modal-form');
	$frm[0].reset();
	$('#rptNo').val(data.reportNo);
	const $category = $('#category');
	$category.html('');
	let $catSpan;
	switch(data.catCode){
		case 'lesson':
			$catSpan = $('<span class="px-2 py-1 font-semibold leading-tight text-green-700 bg-green-100 rounded-full dark:bg-green-700 dark:text-green-100">').text('레슨');
			break;
		case 'ensemble':
			$catSpan = $('<span class="px-2 py-1 font-semibold leading-tight text-orange-700 bg-orange-100 rounded-full dark:text-white dark:bg-orange-600">').text('합주');
			break;
		case 'community':
			$catSpan = $('<span class="px-2 py-1 font-semibold leading-tight text-red-700 bg-red-100 rounded-full dark:text-red-100 dark:bg-red-700">').text('커뮤니티');
			break;
	}
	$category.append($catSpan);
	$('#reporter').val(data.reporter);
	$('#reportee').val(data.reportee);
	const $reportContent = $('#reportContent');
	$reportContent.html(data.content);
	const $processcode = $('#processcode');
	$processcode.children().prop('selected',false);
	$processcode.find('[value="'+data.proCode+'"]').prop('selected',true);
	const $processContent = $('#processContent');
	let processResult = data.result;
	$processContent.val(processResult!=null?processResult:'');
}

const templateChange = (e)=>{
	const $select = $(e.target);
	$('#processContent').val($select.val());
};