package com.harmony.lesson.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.harmony.board.exception.BadAccessException;
import com.harmony.lesson.dto.Lesson;
import com.harmony.lesson.service.LessonService;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class EnrollLessonServlet
 */
@WebServlet("/update/updateEndLesson.do")
public class UpdateLessonEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateLessonEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!ServletFileUpload.isMultipartContent(request)) {
			throw new BadAccessException("잘못된 접근입니다. 관리자에게 문의하세요 :(");
		} else {
			// 파일을 저장할 위치를 절대경로로 가져오기(루트부터 시작)
			String path = getServletContext().getRealPath("/upload/lesson/");
			int maxSize = 1024*1024*10;
			String encoding = "UTF-8";
			DefaultFileRenamePolicy dfr = new DefaultFileRenamePolicy();
			
			// 매개변수값을 이용해서 MultipartRequest객체 생성
			MultipartRequest mr = new MultipartRequest(request, path, maxSize, encoding, dfr);
			
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date(System.currentTimeMillis());
			
			int boardNo = Integer.parseInt(mr.getParameter("boardNo"));
			String teacherNum = mr.getParameter("teacherNum");
			String instNo = mr.getParameter("inst");
			String title = mr.getParameter("title");
			String content = mr.getParameter("content");
			String place = mr.getParameter("address");
			String price = mr.getParameter("price");
//			String imgFile = mr.getParameter("upfile");
//			Timestamp startTime = mr.getParameter("startTime");
//			Timestamp endTime = mr.getParameter("endTime");
			Timestamp startTime = Timestamp.valueOf(formatter.format(date) +" "+ mr.getParameter("startTime"));
			Timestamp endTime = Timestamp.valueOf(formatter.format(date) +" "+mr.getParameter("endTime"));
			String[] day = mr.getParameterValues("hopeDay");
					
			// MultipartRequest클래스가 제공하는 메소드를 이용해서 파일 이름 가져오기
			String ori = mr.getOriginalFileName("upfile");
			String rename = mr.getFilesystemName("upfile");
			
			Lesson l = Lesson.builder()
					.boardNo(boardNo)
					.teacherNo(teacherNum)
					.instNo(instNo)
					.boardTitle(title)
					.boardContent(content)
					.boardPlace(place)
					.boardPrice(price)
					.boardImg(rename)
					.lessonStartTime(startTime)
					.lessonEndTime(endTime)
					.day(day)
					.build();
			
			int result = new LessonService().updateLesson(l);
			
			String msg,loc;
			if(result>0) {
				msg = "수정성공 :)";
				loc = "/lesson/findLesson.do";
			} else {
				msg = "수정실패 :(";
				loc = "/lesson/findLesson.do";
			}
			request.setAttribute("msg", msg);
			request.setAttribute("loc", loc);
			
			request.getRequestDispatcher("/views/lesson/common/msg.jsp")
				.forward(request, response);
		}
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
