package com.harmony.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harmony.board.info.model.dto.InfoCommentBoard;
import com.harmony.board.model.service.InfoBoardService;
import com.harmony.model.dto.Member;

/**
 * Servlet implementation class InfoBoardCommentInsertServlet
 */
@WebServlet("/board/insertComment.do")
public class InfoBoardCommentInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InfoBoardCommentInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
    	int infBrdNo = Integer.parseInt(request.getParameter("boardRef"));
        Member loginMember = (Member) request.getSession().getAttribute("loginMember");
        if(loginMember == null) {
            response.sendRedirect(request.getContextPath() + "/loginServlet.do");
            return;
        }
        String infComWriter = loginMember.getMemNo();
        String infComContent = request.getParameter("content");
        int infComLevel = Integer.parseInt(request.getParameter("level"));
        String infComNoRefStr = request.getParameter("infComNoRef");
        Integer infComNoRef = (infComNoRefStr != null && !infComNoRefStr.isEmpty()) ? 
                                    Integer.parseInt(infComNoRefStr) : null;

        InfoCommentBoard comment = InfoCommentBoard.builder()
            .infBrdNo(infBrdNo)
            .infComWriter(infComWriter)
            .infComContent(infComContent)
            .infComLevel(infComLevel)
            .infComNoRef(infComNoRef)
            .build();

        InfoBoardService service = new InfoBoardService();
        int result = service.insertBoardComment(comment);

        if (result > 0) {
            response.sendRedirect(request.getContextPath() + "/board/boardView.do?no=" + infBrdNo);
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
