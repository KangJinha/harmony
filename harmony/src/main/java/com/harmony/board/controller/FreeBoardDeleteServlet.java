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
 * Servlet implementation class FreeBoardDeleteServlet
 */
@WebServlet("/board/freeBoardDelete.do")
public class FreeBoardDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FreeBoardDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int boardNo = Integer.parseInt(request.getParameter("no"));
        int result = new FreeBoardService().deleteFreeBoard(boardNo);

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        String contextPath = request.getContextPath();

        if (result > 0) {
            out.println("<script>");
            out.println("alert('게시글이 삭제되었습니다.');");
            out.println("window.location.href = '" + contextPath + "/freeBoardList.do';");
            out.println("</script>");
        } else {
            out.println("<script>");
            out.println("alert('게시글 삭제에 실패하였습니다.');");
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
