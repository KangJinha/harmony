package com.harmony.lesson.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harmony.board.model.service.InfoBoardService;
import com.harmony.common.PageBarBuilder;
import com.harmony.lesson.dto.Lesson;
import com.harmony.lesson.service.LessonService;

/**
 * Servlet implementation class FindLessonServlet
 */
@WebServlet("/lesson/findLesson.do")
public class FindLessonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindLessonServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int cPage=1;
		int numPerpage=12;
		
		//cPage=현재페이지 numPerpage=보여주는 페이지갯수(5)
		try {
			cPage=Integer.parseInt(request.getParameter("cPage"));
		}catch(NumberFormatException e) {
			cPage=1;
		}
		List<Lesson> lessons = new LessonService().printLessonByStarAvg(cPage, numPerpage);
		
		
		int totalData=new LessonService().printLessonCount();
		int totalPage=(int)Math.ceil((double)totalData/numPerpage);
		int pageBarSize=5;
		String pageBar = PageBarBuilder.pageBarBuilder(cPage, numPerpage, totalData, pageBarSize, request.getRequestURI());
		
		
		request.setAttribute("lessons", lessons);
		request.setAttribute("pageBar",pageBar);
		request.getRequestDispatcher("/views/lesson/findLesson.jsp")
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
