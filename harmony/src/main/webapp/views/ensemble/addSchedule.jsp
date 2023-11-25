<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
  <script src="http://code.jquery.com/jquery-3.7.1.js"></script> 
 <style>
.single_chk_span:hover{
	 cursor : pointer;
    color:rgb(250, 112, 61);
    font-weight: bold;
}

.selected{
	color : orange;
	font-weight : bold;
}
	
.time_container{
	display: flex;
/*	border:1px solid purple; */
	flex-direction:column;	
	justify-content: flex-start;
}

.time_container > div{
	margin: 15px;
}	
.submit_container{
/*	border:1px solid blue;*/
	display: flex;
	justify-content: end;
	padding-right:50%;
	padding-bottom: 5%;
}

</style>
</head>
<body>

<div class="time_container">
	<div class="day_container">
		<p>합주 요일</p>
		
			<label>
			    <input type='radio' class="single_chk" name='days' value='월' style="display:none">
				<span class="single_chk_span">월</span>
			</label>
			<label>
			    <input type='radio' class="single_chk" name='days' value='화' style="display:none">
				<span class="single_chk_span">화</span>
			</label>
			<label>
			    <input type='radio' class="single_chk" name='days' value='수' style="display:none">
				<span class="single_chk_span">수</span>
			</label>
			<label>
			    <input type='radio' class="single_chk" name='days' value='목' style="display:none">
				<span class="single_chk_span">목</span>
			</label>
			<label>
			    <input type='radio' class="single_chk" name='days' value='금' style="display:none">
				<span class="single_chk_span">금</span>
			</label>
			<label>
			    <input type='radio' class="single_chk" name='days' value='토' style="display:none">
				<span class="single_chk_span">토</span>
			</label>
			<label>
			    <input type='radio' class="single_chk" name='days' value='일' style="display:none">
				<span class="single_chk_span">일</span>
			</label>
		
		
	
	</div>
	<div>
		<p>합주 시작 시간</p>
		<input type="time" name="time" id="startTime">
	</div>
	<div>
		<p>합주 종료 시간</p>
		<input type="time" name="time" id="endTime" >
	</div>
	
	<div class="submit_container">
		<input type="button" value="제출" id="submit">
	</div>

</div>	

<script>



$('#submit').click((e)=>{
	
	if($('.single_chk:checked').val() != null &&
		$('#startTime').val().length>0 &&
		$('#endTime').val().length>0){
			$("#dayOfWeek",opener.document).val($('.single_chk:checked').val());
			$("#startTime",opener.document).val($('#startTime').val()+':00.0');
			$("#endTime",opener.document).val($("#endTime").val()+':00.0');
			
			$("#sch_result",opener.document).append($('.single_chk:checked').val()+ '요일 ');
			$("#sch_result",opener.document).append($('#startTime').val() + " - ");
			$("#sch_result",opener.document).append($('#endTime').val()+ "<br>");
			
			close();

		}else{
			alert("일정을 입력해주세요.")
	}
	
	});

$(document).ready(function(){
	$('.single_chk_span').click(function(){
		$(this).toggleClass('selected');
	$('.single_chk_span').not(this).removeClass('selected'); 
	
	});
});
</script>
</body>
</html>