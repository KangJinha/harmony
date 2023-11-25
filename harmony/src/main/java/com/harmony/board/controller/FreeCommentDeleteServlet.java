package com.harmony.board.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harmony.board.model.service.FreeBoardService;

/**
 * Servlet implementation class FreeCommentDeleteServlet
 */
@WebServlet("/board/freeCommentDelete.do")
public class FreeCommentDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FreeCommentDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int commentNo = Integer.parseInt(request.getParameter("no"));
        int boardNo = Integer.parseInt(request.getParameter("boardNo"));
        int result = new FreeBoardService().deleteFreeComment(commentNo);

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        if (result > 0) {
            out.println("<script>");
            out.println("alert('댓글이 삭제되었습니다.');");
            out.println("location.href='" + request.getContextPath() + "/board/freeboardView.do?no=" + boardNo + "';"); 
            out.println("</script>");
        } else {
            out.println("<script>");
            out.println("alert('댓글 삭제에 실패하였습니다.');");
            out.println("history.back();");
            out.println("</script>");
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
