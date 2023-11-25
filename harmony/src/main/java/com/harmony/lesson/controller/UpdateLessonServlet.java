package com.harmony.lesson.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harmony.lesson.dto.Lesson;
import com.harmony.lesson.service.LessonService;

/**
 * Servlet implementation class UpdateLesson
 */
@WebServlet("/lesson/updateLesson.do")
public class UpdateLessonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateLessonServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int no = Integer.parseInt(request.getParameter("no"));
		// 레슨정보테이블에서 레슨정보가져오기
		Lesson lesson = new LessonService().selectLessonByNo(no);
		Lesson time = new LessonService().selectTimeByNo(no);
		
		System.out.println(lesson);
		
		request.setAttribute("time", time);
		request.setAttribute("lesson", lesson);
		request.getRequestDispatcher("/views/lesson/updateLesson.jsp")
				.forward(request, response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
