<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Hi+Melody&family=Nanum+Gothic+Coding&display=swap" rel="stylesheet">    
    
<%@ page import="java.util.List, 
				com.harmony.ensemble.model.dto.VEnsList" %>
<%
	List<VEnsList> boards = (List<VEnsList>)request.getAttribute("boards");
	List<VEnsList> filterBoard = (List<VEnsList>)request.getAttribute("filterBoard");
	
%>   
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/ensemble/boardList.css" type="text/css">
<%@ include file="/views/common/header.jsp" %>  
<main>
<aside>

	<div class="menu_bar ">

	<h3 class="toggle1">구분</h3>
    <ul>
    	<li>
		    <input class="inst_chk" type="checkbox" name="inst" value="취미" style="display:none">
			<span class="inst_chk_span">취미</span>
		</li>
	    <li>
	    	<input class="inst_chk" type="checkbox" name="inst" value="전문" style="display:none">
			<span class="inst_chk_span">전문</span>
		</li>
	</ul>
			
			
			<h3 class="toggle1">파트</h3>
		
			<ul>
			
			   <li>
				   <input class="inst_chk" type="checkbox" name="inst" value="보컬" style="display:none">
				   <span class="inst_chk_span">보컬</span>
			   </li>
			   <li>
				   <input class="inst_chk" type="checkbox" name="inst" value="건반" style="display:none">
				   <span class="inst_chk_span">건반</span>
			   </li>
			   <li>
				   <input class="inst_chk" type="checkbox" name="inst" value="드럼" style="display:none">
				   <span class="inst_chk_span">드럼</span>
			   </li>
			   <li>
				   <input class="inst_chk" type="checkbox" name="inst" value="기타" style="display:none">
				   <span class="inst_chk_span">기타</span>
			   </li>
			   <li>
				   <input class="inst_chk" type="checkbox" name="inst" value="베이스" style="display:none">
				   <span class="inst_chk_span">베이스</span>
			   </li>
			   <li>
				   <input class="inst_chk" type="checkbox" name="inst" value="바이올린" style="display:none">
				   <span class="inst_chk_span">바이올린</span>
			   </li>
			   <li>
				   <input class="inst_chk" type="checkbox" name="inst" value="퍼커션" style="display:none">
				   <span class="inst_chk_span">퍼커션</span>
			   </li>
			   <li>
				   <input class="inst_chk" type="checkbox" name="inst" value="색소폰" style="display:none">
				   <span class="inst_chk_span">색소폰</span>
			   </li>
			   <li>
				   <input class="inst_chk" type="checkbox" name="inst" value="플룻" style="display:none">
				   <span class="inst_chk_span">플룻</span>
			   </li>
			   <li>
				   <input class="inst_chk" type="checkbox" name="inst" value="일렉트로닉" style="display:none">
				   <span class="inst_chk_span">일렉트로닉</span>
			   </li>
			   <li>
				   <input class="inst_chk" type="checkbox" name="inst" value="더블베이스" style="display:none">
				   <span class="inst_chk_span">더블베이스</span>
			   </li>
			   <li>
				   <input class="inst_chk" type="checkbox" name="inst" value="첼로" style="display:none">
				   <span class="inst_chk_span">첼로</span>
			   </li>
			   <li>
				   <input class="inst_chk" type="checkbox" name="inst" value="트럼펫" style="display:none">
				   <span class="inst_chk_span">트럼펫</span>
			   </li>
			</ul>
			<h3 class="toggle1">장르</h3>
			<ul>
			   <li>
				   <input class="inst_chk" type="checkbox" name="inst" value="가요" style="display:none">
				   <span class="inst_chk_span">가요</span>
			   </li>
			   <li>
				   <input class="inst_chk" type="checkbox" name="inst" value="팝" style="display:none">
				   <span class="inst_chk_span">팝</span>
			   </li>
			   <li>
				   <input class="inst_chk" type="checkbox" name="inst" value="재즈" style="display:none">
				   <span class="inst_chk_span">재즈</span>
			   </li>
			   <li>
				   <input class="inst_chk" type="checkbox" name="inst" value="힙합" style="display:none">
				   <span class="inst_chk_span">힙합</span>
			   </li>
			   <li>
				   <input class="inst_chk" type="checkbox" name="inst" value="일렉트로닉" style="display:none">
				   <span class="inst_chk_span">일렉트로닉</span>
			   </li>
			   <li>
				   <input class="inst_chk" type="checkbox" name="inst" value="락" style="display:none">
				   <span class="inst_chk_span">락</span>
			   </li>
			   <li>
				   <input class="inst_chk" type="checkbox" name="inst" value="모던락" style="display:none">
				   <span class="inst_chk_span">모던락</span>
			   </li>
			   <li>
				   <input class="inst_chk" type="checkbox" name="inst" value="펑크" style="display:none">
				   <span class="inst_chk_span">펑크</span>
			   </li>
			   <li>
				   <input class="inst_chk" type="checkbox" name="inst" value="인디" style="display:none">
				   <span class="inst_chk_span">인디</span>
			   </li>
			   <li>
				   <input class="inst_chk" type="checkbox" name="inst" value="트로트" style="display:none">
				   <span class="inst_chk_span">트로트</span>
			   </li>
			</ul>
			<h3 class="toggle1">지역</h3>
			<ul>
			   <li>
				  <input class="inst_chk" type="checkbox" name="inst" value="서울" style="display:none">
				   <span class="inst_chk_span">서울</span>
			   </li>
			   <li>
			   <input class="inst_chk" type="checkbox" name="inst" value="경기도" style="display:none">
				   <span class="inst_chk_span">경기도</span>
			   </li>
			   <li>
				   <input class="inst_chk" type="checkbox" name="inst" value="인천" style="display:none">
				   <span class="inst_chk_span">인천</span>
			   </li>
			   <li>
				   <input class="inst_chk" type="checkbox" name="inst" value="강원도" style="display:none">
				   <span class="inst_chk_span">강원도</span>
			   </li>
			   <li>
				   <input class="inst_chk" type="checkbox" name="inst" value="충청도" style="display:none">
				   <span class="inst_chk_span">충청도</span>
			   </li>
			   <li>
				   <input class="inst_chk" type="checkbox" name="inst" value="전라도" style="display:none">
				   <span class="inst_chk_span">전라도</span>
			   </li>
			   <li>
				   <input class="inst_chk" type="checkbox" name="inst" value="부산" style="display:none">
				   <span class="inst_chk_span">부산</span>
			   </li>
			   <li>
				   <input class="inst_chk" type="checkbox" name="inst" value="제주" style="display:none">
				   <span class="inst_chk_span">제주</span>
			   </li>
			</ul>
		
		</div>

</aside>



<section>
<!-- <div class="include_pageBar"> -->
	<div class="button_container">
		<input type="button" value="합주팀등록" class="top_btn"
				onclick="location.assign('<%=request.getContextPath()%>/ensemble/enrollTeam.do')">
		<input type="button" value="팀 페이지" class="top_btn" 
				onclick="location.assign('<%=request.getContextPath()%>/ensemble/teamProfile.do')">
	</div>
		<article>
	      <div class="board_container">
	     
	            
	   
	     <%if(!boards.isEmpty()){  
				for(VEnsList b:boards){%> 
	      	
	        <div class="board_list" onclick="location.assign('<%=request.getContextPath()%>/ensemble/boardView.do?ensBoardNo=<%=b.getEnsBoardNo()%>')">
	        	<div class="list_top_container">
		        	<div class="title_container">
			       		<h3><%=b.getEnsBoardTitle()%></h3>
			      	
		        	</div>
		        	<div class="type_container">
		        			<h5><%=b.getEnsTeamType() %></h5>
		        	</div>
	        	</div>
	        	<div class="img_container">
		        	<img src = "https://previews.123rf.com/images/valentint/valentint1702/valentint170201265/71753888-%EC%9D%8C%ED%91%9C-%EC%95%84%EC%9D%B4%EC%BD%98%EC%9E%85%EB%8B%88%EB%8B%A4-%ED%9D%B0%EC%83%89-%EB%B0%94%ED%83%95%EC%97%90-%EC%A3%BC%ED%99%A9%EC%83%89-%EC%9D%B8%ED%84%B0%EB%84%B7-%EB%B2%84%ED%8A%BC%EC%9E%85%EB%8B%88%EB%8B%A4.jpg" 
		        	style="width=80px; height:80px; border-radius: 50%;" class="profile_img">
	      			<h4><%=b.getEnsTeamName() %></h4>
	        	</div>
	       		<hr>
	       		<ul>
	       			<li><%=b.getGenreName() %></li>
	       			<li><%=b.getInstrument() %></li>
	       			<li><%=b.getEnsLocation() %></li>
	       		</ul>
	        </div>
			<%}
		}%>
		</div>
	    </article>
	<div class="pageBar_container">
		<div id="pageBar"> 
		 		<%= request.getAttribute("pageBar") %> 
		</div>    
	</div>  
	    
<!-- </div> -->
</section>

</main>

<script>

$(document).ready(function() {
	
   $('.inst_chk').on('change', function() {
/* 	 	let filter_val = $(".inst_chk:checked").val();
   		console.log(filter_val); 
   		 */
	   	    let filter_values = $(".inst_chk:checked").map(function() {
	   	        return $(this).val();
	   	    }).get();
	
	   	    console.log(filter_values);
	
	   	   /*  jQuery.ajaxSettings.traditional=true; */
		   		$.ajax({
		   			url: "<%=request.getContextPath()%>/ensemble/ajaxFilter.do",
		   			type : "post",
		   			traditional: true,
		   			datatype: "json",
		   			data : {filter_values:JSON.stringify(filter_values)},
		   			success : data=>{
		   								console.log(data);
		   								console.log(data.length)
		   								const $data = JSON.parse(data);
		   								$('.board_container').html('');
		   								$data.forEach((ens,i)=>{
		   									
		   									console.log(ens);
// 		   								  $('div').remove('.board_list');
		   									
		   								  const board_list = $('<div class="board_list">').attr('onclick','location.assign("<%=request.getContextPath()%>/ensemble/boardView.do?ensBoardNo='+ens.ensBoardNo+'")');
								  	       const list_top_container = $('<div class="list_top_container">');
								  	   	   const title_container = $('<div class="title_container">');
								  	   	   const h3 = $('<h3>');
								  	   	   const type_container = $('<div class="type_container">');
								  	   	   const h5 = $('<h5>');
								  	   	   const img_container = $('<div class="img_container">');
								  	   	   const img = $('<img src = "<%=request.getContextPath()%>/image/ensemble/ensembleDefault.jpg" style="width=80px; height:80px; border-radius: 50%;" class="profile_img">');
								  	   	   const h4 = $('<h4>');
								  	   	   const hr = $('<hr>');
								  	   	   const ul = $('<ul>');
								  	   	   const li1 = $('<li>');
								  	   	   const li2 = $('<li>');
								  	   	   const li3 = $('<li>');
								  	   	   
								  	   	   $(title_container).append(h3);
								  	   	   $(list_top_container).append(title_container);
								  	 
								  	   	   $(type_container).append(h5);
								  	   	   $(list_top_container).append(type_container);
								  	   	   $(board_list).append(list_top_container);
								  	   	  
								  	   	   $(img_container).append(img);
								  	   	   $(img_container).append(h4);
								  	   	   $(board_list).append(img_container);
								  	  	   $(board_list).append(hr);
								  	
								  	  	   $(ul).append(li1);
								  	  	   $(ul).append(li2);
								  	  	   $(ul).append(li3);
								  	  	   $(board_list).append(ul);
			  	  	   
								  	 	h3.text(ens.ensBoardTitle);
									    h5.text(ens.ensTeamType);
									    h4.text(ens.ensTeamName);
									    
									    li1.text(ens.genreName);
									    li2.text(ens.instrument);
									    li3.text(ens.ensLocation);
							 

									    $('.board_container').append(board_list);
									    
		   								});
							   		
		   			}
		   		});
	   	   

	   	    
	   	 }); 
  
    
});



</script>

<script>


	$(".toggle1").next().hide();

   $(document).ready(function(){
   
      $(".toggle1").click(function(e){
         $(e.target).next().slideToggle();      
      });


   });

	$('.inst_chk_span').click((e)=>{
		
		const $target = $(e.target);
		if($target.attr("class").includes('single')){
			$target.parents("tr").find(".selected").removeClass("selected");
		}
		$target.prev().click();		
	});
	$('.inst_chk').click((e)=>{
		const $target = $(e.target);
		console.log($target.next());
		$target.next().toggleClass('selected');
	});

	
</script>
	

<%@ include file="/views/common/footer.jsp" %>  