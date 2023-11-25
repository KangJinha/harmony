package com.harmony.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harmony.board.free.model.dto.FreeBoard;
import com.harmony.board.free.model.dto.FreeCommentBoard;
import com.harmony.board.model.service.FreeBoardService;

/**
 * Servlet implementation class FreeBoardViewServlet
 */
@WebServlet("/board/freeboardView.do")
public class FreeBoardViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FreeBoardViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String noParam = request.getParameter("no");
        int no = noParam != null ? Integer.parseInt(noParam) : 0;

        FreeBoardService freeBoardService = new FreeBoardService();
        FreeBoard b = freeBoardService.selectBoardByNo(no,true);
        List<FreeCommentBoard> comments = freeBoardService.selectFreeBoardComments(no);

        request.setAttribute("FreeBoard", b);
        request.setAttribute("comments", comments);
        //setAttribute로 jsp의 getAttribute에 보냄(freeboard)
	    //setAttribute 로 키값과 벨류값을 가져오고
	    //jsp에서 getAttribute를 이용해 키값을 가져와 화면에 나타냄

        request.getRequestDispatcher("/views/board/freeBoardView.jsp")
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
