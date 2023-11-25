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
 * Servlet implementation class EnrollLessonServlet
 */
@WebServlet("/lesson/enrollLesson.do")
public class EnrollLessonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EnrollLessonServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 조인한 회원번호 + 레슨 게시글 가져오기
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		Lesson l = new LessonService().selectLessonByNoJoin(boardNo);
		System.out.println(l.getTeacherNo());
		
		request.setAttribute("lesson", l);
		request.getRequestDispatcher("/views/lesson/enrollLesson.jsp")
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
