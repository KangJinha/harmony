package com.harmony.board.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harmony.board.model.service.InfoBoardService;

/**
 * Servlet implementation class InfoCommentDeleteServlet
 */
@WebServlet("/board/deleteComment.do")
public class InfoCommentDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InfoCommentDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int commentNo = Integer.parseInt(request.getParameter("no"));
        int boardNo = Integer.parseInt(request.getParameter("boardNo")); // 게시글 번호 추가
        int result = new InfoBoardService().deleteInfoComment(commentNo);

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        if (result > 0) {
            out.println("<script>");
            out.println("alert('댓글 삭제에 성공하였습니다.');");
            out.println("location.href='" + request.getContextPath() + "/board/boardView.do?no=" + boardNo + "';");
            out.println("</script>");
        } else {
            request.setAttribute("msg", "댓글 삭제에 실패하였습니다.");
            request.setAttribute("loc", "/board/boardList.do");
            request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
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
