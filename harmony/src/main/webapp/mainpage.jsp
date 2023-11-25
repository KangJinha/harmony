<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.harmony.admin.model.dto.Carousel" %>
<%@ include file="/views/common/header.jsp" %>
<% List<Carousel> carouselList = (List<Carousel>)request.getAttribute("carouselList"); %>
<section class="container">
	<% if(carouselList !=null){ %>
	<div id="harmonyMainCarousel" class="carousel slide" data-bs-ride="carousel">
		<div class="carousel-indicators">
			<%for(int i=0; i<carouselList.size(); i++){ %>
				<button type="button" data-bs-target="#harmonyMainCarousel" data-bs-slide-to="<%=i %>" <%=i==0?"class=\"active\" aria-current=\"true\" ":"" %>aria-label="Slide <%=(i+1)%>"></button>
			<%} %>
		</div>
		<div class="carousel-inner">
			<%for(int i=0;i<carouselList.size(); i++){ %>
			<div class="carousel-item<%=i==0?" active":"" %>" data-bs-interval="<%=carouselList.get(i).getCrslIntervalMs()%>">
				<a href="<%=carouselList.get(i).getCrslPageLink()%>">
					<img src="<%=request.getContextPath() %>/image/carousel/<%=carouselList.get(i).getCrslImgLink() %>" class="d-block w-100" alt="<%=carouselList.get(i).getCrslName()%>">
				</a>
			</div>
			<%} %>
		</div>
		<button class="carousel-control-prev" type="button" data-bs-target="#harmonyMainCarousel"
			data-bs-slide="prev">
			<span class="carousel-control-prev-icon" aria-hidden="true"></span>
			<span class="visually-hidden">Previous</span>
		</button>
		<button class="carousel-control-next" type="button" data-bs-target="#harmonyMainCarousel"
			data-bs-slide="next">
			<span class="carousel-control-next-icon" aria-hidden="true"></span>
			<span class="visually-hidden">Next</span>
		</button>
	</div>
	<%} %>
</section>
<div class="footer_box">
<%@ include file="/views/common/footer.jsp" %>
</div>
