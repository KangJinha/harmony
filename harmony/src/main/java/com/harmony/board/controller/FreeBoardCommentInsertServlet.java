package com.harmony.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harmony.board.free.model.dto.FreeCommentBoard;
import com.harmony.board.model.service.FreeBoardService;
import com.harmony.model.dto.Member;

/**
 * Servlet implementation class FreeBoardCommentInsertServlet
 */
@WebServlet("/board/freeInsertComment.do")
public class FreeBoardCommentInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FreeBoardCommentInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		        request.setCharacterEncoding("UTF-8");
		        int freBrdNo = Integer.parseInt(request.getParameter("boardRef"));
		        Member loginMember = (Member) request.getSession().getAttribute("loginMember");
		        if(loginMember == null) {
		            response.sendRedirect(request.getContextPath() + "/loginServlet.do");
		            return;
		        }
		        String freComWriter = loginMember.getMemNo();
		        String freComContent = request.getParameter("content");
		        int freComLevel = Integer.parseInt(request.getParameter("level"));
		        String freComRefNoStr = request.getParameter("freComNoRef");
		        Integer freComRefNo = (freComRefNoStr != null && !freComRefNoStr.isEmpty()) ? 
		                                    Integer.parseInt(freComRefNoStr) : null;

		        FreeCommentBoard comment = FreeCommentBoard.builder()
		            .freBrdNo(freBrdNo)
		            .freComWriter(freComWriter)
		            .freComContent(freComContent)
		            .freComLevel(freComLevel)
		            .freComRefNo(freComRefNo)
		            .build();

		        FreeBoardService service = new FreeBoardService();
		        int result = service.insertFreeBoardComment(comment);

		        if (result > 0) {
		            response.sendRedirect(request.getContextPath() + "/board/freeboardView.do?no=" + freBrdNo); 
		        } else {
		            request.getRequestDispatcher("/views/common/harmonyExceptionPage.jsp").forward(request, response);
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
