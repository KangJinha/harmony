package com.harmony.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harmony.board.free.model.dto.FreeBoard;
import com.harmony.board.info.model.dto.InfoBoard;
import com.harmony.board.model.service.FreeBoardService;
import com.harmony.common.PageBarBuilder;

/**
 * Servlet implementation class FreeBoardSearchServlet
 */
@WebServlet("/board/FreeBoardSearch.do")
public class FreeBoardSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FreeBoardSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchType = request.getParameter("searchType");
		String query = request.getParameter("query");
		int cPage, numPerPage=5;

		try {
			cPage=Integer.parseInt(request.getParameter("cPage"));
		} catch(NumberFormatException e) {
			cPage=1;
		}

		List<FreeBoard> searchResults = new FreeBoardService().searchBoards(searchType, query, cPage, numPerPage);
		
		for (FreeBoard board : searchResults) {
	        int commentCount = new FreeBoardService().getCommentCount(board.getFreBrdNo());
	        request.setAttribute("commentCount" + board.getFreBrdNo(), commentCount);

	    }
		int totalData = new FreeBoardService().getSearchResultCount(searchType, query);

		String pageBar = PageBarBuilder.pageBarBuilderForSearch(cPage, numPerPage, totalData, 5, request.getRequestURI(), searchType, query);

		request.setAttribute("searchResults", searchResults);
		request.setAttribute("pageBar", pageBar);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/board/freeBoardList.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
