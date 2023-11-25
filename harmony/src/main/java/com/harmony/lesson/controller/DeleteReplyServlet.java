package com.harmony.lesson.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harmony.lesson.service.LessonService;

/**
 * Servlet implementation class DeleteReplyServlet
 */
@WebServlet("/lesson/deletereply.do")
public class DeleteReplyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteReplyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		
		System.out.println(reviewNo);
		
		int result = new LessonService().deleteReply(reviewNo);
		
		String msg,loc;
		if (result>0) {
			msg="댓글삭제완료";
			loc="/lesson/lessonInfo.do?no="+boardNo;
		} else {
			msg="삭제실패";
			loc="/lesson/lessonInfo.do?no="+boardNo;
		}
		
		request.setAttribute("loc", loc);
		request.setAttribute("msg", msg);
		request.getRequestDispatcher("/views/lesson/common/msg.jsp").forward(request, response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
