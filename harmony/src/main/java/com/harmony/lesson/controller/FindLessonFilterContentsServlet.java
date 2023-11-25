package com.harmony.lesson.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.harmony.board.model.service.InfoBoardService;
import com.harmony.common.PageBarBuilder;
import com.harmony.lesson.dto.Lesson;
import com.harmony.lesson.service.LessonService;

/**
 * Servlet implementation class FindLessonServlet
 */
@WebServlet("/FindLessonFilterContentsServlet.do")
public class FindLessonFilterContentsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindLessonFilterContentsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 조회수 최근등록순
		String viewAndRecent = request.getParameter("viewAndRecent");
		String no = request.getParameter("no");
		
		int cPage=1;
		int numPerpage=12;
		
		//cPage=현재페이지 numPerpage=보여주는 페이지갯수(5)
		try {
			cPage=Integer.parseInt(request.getParameter("cPage"));
		}catch(NumberFormatException e) {
			cPage=1;
		}
		
		List<Lesson> lessons = null;
		if (viewAndRecent!=null) {
			lessons = new LessonService().printLessonByKeyword(viewAndRecent, cPage, numPerpage);
		} else if(no!=null){
			lessons = new LessonService().printLessonByReviews(cPage, numPerpage);
		} else {
			lessons = new LessonService().printLessonByStarAvg(cPage, numPerpage);
		}
		
		
		int totalData=new LessonService().printLessonCount();
		int totalPage=(int)Math.ceil((double)totalData/numPerpage);
		int pageBarSize=5;
		String pageBar = PageBarBuilder.pageBarBuilder(cPage, numPerpage, totalData, pageBarSize, request.getRequestURI());
		
		
		request.setAttribute("lessons", lessons);
		request.setAttribute("pageBar",pageBar);
		response.setContentType("application/json;charset=utf-8");
		new Gson().toJson(lessons,response.getWriter());
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
