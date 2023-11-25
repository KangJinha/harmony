package com.harmony.admin.controller.ajax;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.harmony.admin.model.dto.Carousel;
import com.harmony.admin.service.AdminService;
import com.harmony.common.exception.HarmonyException;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class AjaxCarouselManageServlet
 */
@WebServlet(name="ajaxCarouselManage",urlPatterns = "/admin/ajax/carouselManage.do")
public class AjaxCarouselManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxCarouselManageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String path = getServletContext().getRealPath("/image/carousel/");
		int fileMaxSize = 1024*1024*100;
		String encode = "UTF-8";
		DefaultFileRenamePolicy renamePolicy = new DefaultFileRenamePolicy();
		MultipartRequest mr = new MultipartRequest(request, path, fileMaxSize, encode, renamePolicy);
		Gson gson = new Gson();
		JsonObject result = new JsonObject();
		String oldImage = mr.getParameter("oldImage");
		String newImage = mr.getFilesystemName("newImage");
		boolean oldFileDeleteFlag = false;
		boolean newFileDeleteFlag = false;
		try {
			String type = mr.getParameter("type");
			System.out.println(type);
			if(type.equals("insert")||type.equals("edit")) {
				int crslNo = 0;
				if(type.equals("edit")) crslNo = Integer.parseInt(mr.getParameter("crslNo"));
				String crslName = mr.getParameter("crslName");
				String crslPageLink = mr.getParameter("pagelink")!=null?mr.getParameter("pagelink"):"#";
				Date crslWriteDate = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(mr.getParameter("startDate")).getTime());
				Date crslEndDate = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(mr.getParameter("endDate")).getTime());
				int crslIntervalMs = Integer.parseInt(mr.getParameter("crslIntervalMs")!=null?mr.getParameter("intervalms"):"4000");
				int crslViewRank = Integer.parseInt(mr.getParameter("crslViewRank")!=null?mr.getParameter("viewrank"):"3");
				
				Carousel c = Carousel.builder().crslNo(crslNo)
											   .crslName(crslName)
											   .crslPageLink(crslPageLink)
											   .crslWriteDate(crslWriteDate)
											   .crslEndDate(crslEndDate)
											   .crslIntervalMs(crslIntervalMs)
											   .crslViewRank(crslViewRank)
											   .build();
				if(newImage!=null)c.setCrslImgLink(newImage);
				else c.setCrslImgLink(oldImage);
				if(type.equals("insert")) {
					System.out.println("insert-start");
					c = AdminService.getService().insertCarousel(c);
					if(c.getCrslNo()>0) {
						result.addProperty("result", "success");
						result.addProperty("body", NewCarouselBuilder.carouselBuilder(c, request.getContextPath()));
						result.addProperty("crslNo", c.getCrslNo());
					}else {
						result.addProperty("result", "fail");
						if(newImage!=null) newFileDeleteFlag = true;
					}
				}else {
					System.out.println("edit-start");
					c= AdminService.getService().updateCarousel(c);
					if(c.getCrslNo()>0) {
						result.addProperty("result", "success");
						result.addProperty("body", NewCarouselBuilder.carouselBuilder(c, request.getContextPath()));
						result.addProperty("crslNo", c.getCrslNo());
						if(newImage!=null) oldFileDeleteFlag = true;
					}else {
						result.addProperty("result", "fail");
						if(newImage!=null) newFileDeleteFlag = true;
					}
				}
			}else if(type.equals("delete")) {
				String crslNo = mr.getParameter("crslNo");
				int deleteResult = AdminService.getService().deleteCarousel(crslNo);
				if(deleteResult>0) {
					result.addProperty("result", "success");
					oldFileDeleteFlag = true;
					newFileDeleteFlag = true;
				}else {
					result.addProperty("result", "fail");
					newFileDeleteFlag = true;
				}
			}else {
				throw new NullPointerException();
			}
			
			
		}catch(NullPointerException|ParseException|NumberFormatException e) {
			newFileDeleteFlag = true;
			e.printStackTrace();
			throw new HarmonyException("필요한 파라미터 값이 입력되지 않았습니다.");
		}finally {
			//새로운 캐러셀의 이미지가 mic.webp나 headset.webp(기본이미지들)이면 삭제하지 않음
			if(oldImage!=null && (oldImage.equals("mic.webp")||oldImage.equals("headset.webp")))oldFileDeleteFlag = false;
			if(newImage!=null && (newImage.equals("mic.webp")||newImage.equals("headset.webp")))newFileDeleteFlag = false;
			File delFile = null;
			if(oldFileDeleteFlag&&oldImage!=null) {
				delFile = new File(path+"/"+oldImage);
				if(delFile.exists()) delFile.delete();
			}
			if(newFileDeleteFlag&&newImage!=null) {
				delFile = new File(path+"/"+newImage);
				if(delFile.exists()) delFile.delete();
			}
		}
		
		
		response.setContentType("application/json;charset=utf-8");
		gson.toJson(result, response.getWriter());
	}

}
