package com.harmony.lesson.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harmony.lesson.dto.SaveLesson;
import com.harmony.lesson.service.LessonService;

/**
 * Servlet implementation class SaveLessonServlet
 */
@WebServlet("/lesson/savelesson.do")
public class SaveLessonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveLessonServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		String memNo = request.getParameter("memNo");
		
		String loc,msg;
		// 찜 이력이 있는지 확인
		SaveLesson s = new LessonService().saveLessonConfirm(boardNo, memNo);
		
		if(s==null) {
			int result = new LessonService().saveLesson(boardNo, memNo);
			if (result>0) {
				msg ="레슨 찜성공 :)";
				loc ="/lesson/lessonInfo.do?no="+boardNo;
				
				request.setAttribute("loc", loc);
				request.setAttribute("msg", msg);
				request.getRequestDispatcher("/views/lesson/common/msg.jsp")
						.forward(request, response);
			}
		}else {
			int result = new LessonService().deleteSavedLesson(boardNo, memNo);
			if(result>0) {
				msg ="찜 해제 :(";
				loc ="/lesson/lessonInfo.do?no="+boardNo;
				
				request.setAttribute("loc", loc);
				request.setAttribute("msg", msg);
				request.getRequestDispatcher("/views/lesson/common/msg.jsp")
						.forward(request, response);
			}
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
