package com.harmony.admin.controller.ajax;

import com.harmony.admin.model.dto.Carousel;

public class NewCarouselBuilder {
	public static String carouselBuilder(Carousel c,String contextPath) {
		StringBuilder sb = new StringBuilder();
		sb.append("<form method=\"post\" enctype=\"multipart/form-data\" class=\"align-items-center needs-validation\" novalidate onchange=\"frmchange(event);\" onsubmit=\"validate(event,'edit');\">");
		sb.append("<div class=\"card-header input-group input-group-sm\">");
		sb.append("<span class=\"input-group-text\">관리번호</span>");
		sb.append("<input class=\"form-control form-control-plaintext\" type=\"number\" name=\"crslNo\" value=\""+c.getCrslNo()+"\" style=\"width:50px\" readOnly>");
		sb.append("<span class=\"input-group-text\">이름</span>");
		sb.append("<input class=\"form-control\" type=\"text\" name=\"crslName\" placeholder=\"이름을입력해주세요\" value=\""+c.getCrslName()+"\">");
		sb.append("</div>");
		sb.append("<input type=\"hidden\" name=\"oldImage\" value=\""+c.getCrslImgLink()+"\">");
		sb.append("<img class=\"card-img-top\" src=\""+contextPath+"/image/carousel/"+c.getCrslImgLink()+"\" alt=\"carousel_"+c.getCrslName()+"_image\">");
		sb.append("<ul class=\"list-group list-group-flush\"><li class=\"list-group-item\">");
		sb.append("<div class=\"input-group input-group-sm\"><span class=\"input-group-text\">이미지수정</span>");
		sb.append("<input class=\"form-control\" type=\"file\" name=\"newImage\" placeholder=\"이미지변경시에만 입력해주세요\" accept=\"image/*\">");
		sb.append("</div></li><li class=\"list-group-item\"><div class=\"input-group input-group-sm\">");
		sb.append("<span class=\"input-group-text\">게시일</span><input class=\"form-control\" type=\"date\" name=\"startDate\" placeholder=\"게시시작일\" value=\""+c.getCrslWriteDate()+"\" required>");
		sb.append("<span class=\"input-group-text\">~</span>");
		sb.append("<input class=\"form-control\" type=\"date\" name=\"endDate\" placeholder=\"게시종료일\" value=\""+c.getCrslEndDate()+"\" required>");
		sb.append("</div></li><li class=\"list-group-item\"><div class=\"input-group input-group-sm\">");
		sb.append("<span class=\"input-group-text\">표시순서</span><input class=\"form-control\" type=\"number\" name=\"viewrank\" placeholder=\"순번\" value=\""+c.getCrslViewRank()+"\">");
		sb.append("<span class=\"input-group-text\">표시시간</span>");
		sb.append("<input class=\"form-control\" type=\"number\" name=\"intervalms\" placeholder=\"유지시간 단위 ms\" value=\""+c.getCrslIntervalMs()+"\">");
		sb.append("<span class=\"input-group-text\">ms</span></div></li>");
		sb.append("<li class=\"list-group-item\"><div class=\"input-group input-group-sm\">");
		sb.append("<span class=\"input-group-text\">페이지링크</span>");
		sb.append("<input class=\"form-control\" type=\"text\" name=\"pagelink\" placeholder=\"클릭시 이동할 주소를 입력해주세요\" value=\""+c.getCrslPageLink()+"\">");
		sb.append("</div></li><li class=\"list-group-item\"><div class=\"row justify-content-between\"><div class=\"col-2\">");
		switch(c.getStatus()) {
		case "PENDING":
			sb.append("<span class=\"px-2 py-1 font-semibold leading-tight text-orange-700 bg-orange-100 rounded-full dark:text-white dark:bg-orange-600\">게시대기중</span>");
		break;
		case "ACTIVE":
			sb.append("<span class=\"px-2 py-1 font-semibold leading-tight text-green-700 bg-green-100 rounded-full dark:bg-green-700 dark:text-green-100\">게시중</span>");
		break;
		case "EXPIRED":
			sb.append("<span class=\"px-2 py-1 font-semibold leading-tight text-gray-700 bg-gray-100 rounded-full dark:text-gray-100 dark:bg-gray-700\">게시종료됨</span>");
		break;
		}
		sb.append("</div><div class=\"col-2 flex justify-content-end text-sm\" name=\"buttonBar\">");
		sb.append("<button type=\"button\"  class=\"flex items-center justify-between px-2 py-2 text-sm font-medium leading-5 text-purple-600 rounded-lg dark:text-gray-400 focus:outline-none focus:shadow-outline-gray\" name=\"resetBtn\" aria-label=\"Reset\" onclick=\"doreset(event);\">");
		sb.append("<svg xmlns=\"http://www.w3.org/2000/svg\" fill=\"currentColor\" class=\"w-5 h-5 bi bi-arrow-repeat\" viewBox=\"0 0 20 20\"><path d=\"M11.534 7h3.932a.25.25 0 0 1 .192.41l-1.966 2.36a.25.25 0 0 1-.384 0l-1.966-2.36a.25.25 0 0 1 .192-.41zm-11 2h3.932a.25.25 0 0 0 .192-.41L2.692 6.23a.25.25 0 0 0-.384 0L.342 8.59A.25.25 0 0 0 .534 9z\"/><path fill-rule=\"evenodd\" d=\"M8 3c-1.552 0-2.94.707-3.857 1.818a.5.5 0 1 1-.771-.636A6.002 6.002 0 0 1 13.917 7H12.9A5.002 5.002 0 0 0 8 3zM3.1 9a5.002 5.002 0 0 0 8.757 2.182.5.5 0 1 1 .771.636A6.002 6.002 0 0 1 2.083 9H3.1z\"/></svg></button>");
		sb.append("<button type=\"submit\"  class=\"flex items-center justify-between px-2 py-2 text-sm font-medium leading-5 text-purple-600 rounded-lg dark:text-gray-400 focus:outline-none focus:shadow-outline-gray\" name=\"editBtn\" aria-label=\"Edit\">");
		sb.append("<svg class=\"w-5 h-5\" aria-hidden=\"true\" fill=\"currentColor\" viewBox=\"0 0 20 20\"><path d=\"M13.586 3.586a2 2 0 112.828 2.828l-.793.793-2.828-2.828.793-.793zM11.379 5.793L3 14.172V17h2.828l8.38-8.379-2.83-2.828z\"></path></svg></button>");
		sb.append("<button type=\"button\" class=\"flex items-center justify-between px-2 py-2 text-sm font-medium leading-5 text-purple-600 rounded-lg dark:text-gray-400 focus:outline-none focus:shadow-outline-gray\" name=\"deleteBtn\" aria-label=\"Delete\" onclick=\"doajax(event,'delete');\">");
		sb.append("<svg class=\"w-5 h-5\" aria-hidden=\"true\" fill=\"currentColor\" viewBox=\"0 0 20 20\">");
		sb.append("<path fill-rule=\"evenodd\" d=\"M9 2a1 1 0 00-.894.553L7.382 4H4a1 1 0 000 2v10a2 2 0 002 2h8a2 2 0 002-2V6a1 1 0 100-2h-3.382l-.724-1.447A1 1 0 0011 2H9zM7 8a1 1 0 012 0v6a1 1 0 11-2 0V8zm5-1a1 1 0 00-1 1v6a1 1 0 102 0V8a1 1 0 00-1-1z\" clip-rule=\"evenodd\"></path>");
		sb.append("</svg></button></div></div></li></ul></form>");
		return sb.toString();
	}
}
