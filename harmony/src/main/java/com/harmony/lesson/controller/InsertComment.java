package com.harmony.lesson.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harmony.lesson.dto.LessonComment;
import com.harmony.lesson.service.LessonService;

/**
 * Servlet implementation class InsertComment
 */
@WebServlet("/lesson/insertComment.do")
public class InsertComment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertComment() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		int reviewNo=Integer.parseInt(request.getParameter("reviewNo"));
		String content = request.getParameter("content");
		
		LessonComment lc = LessonComment.builder()
				.reviewNo(reviewNo)
				.commentContent(content)
				.build();
		
		int result = new LessonService().insertLessonComment(lc);
		
		String msg,loc;
		if(result>0) {
			msg="댓글을 등록하셨습니다.";
			loc="/lesson/lessonInfo.do?no="+boardNo;
		} else {
			msg="댓글 등록에 실패하셨습니다.";
			loc="/lesson/lessonInfo.do?no="+boardNo;
		}
		
		request.setAttribute("msg",msg);
		request.setAttribute("loc",loc);
		
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
