package com.harmony.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harmony.board.info.model.dto.InfoBoard;
import com.harmony.board.model.service.InfoBoardService;
import com.harmony.common.PageBarBuilder;

/**
 * Servlet implementation class InfoBoardSearchServlet
 */
@WebServlet("/board/InfoBoardSearch.do")
public class InfoBoardSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InfoBoardSearchServlet() {
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

		List<InfoBoard> searchResults = new InfoBoardService().searchBoards(searchType, query, cPage, numPerPage);
		
		for (InfoBoard board : searchResults) {
	        String categoryName = new InfoBoardService().selectCategoryNameByNo(board.getInfBrdCatNo());
	        String tagName = new InfoBoardService().selectTagNameByNo(board.getInfBrdTagNo());
	        int commentCount = new InfoBoardService().getCommentCount(board.getInfBrdNo());
	        request.setAttribute("categoryName" + board.getInfBrdNo(), categoryName);
	        request.setAttribute("tagName" + board.getInfBrdNo(), tagName);
	        request.setAttribute("commentCount" + board.getInfBrdNo(), commentCount);

	    }
		int totalData = new InfoBoardService().getSearchResultCount(searchType, query);

		String pageBar = PageBarBuilder.pageBarBuilderForSearch(cPage, numPerPage, totalData, 5, request.getRequestURI(), searchType, query);

		request.setAttribute("searchResults", searchResults);
		request.setAttribute("pageBar", pageBar);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/board/infoBoardList.jsp");
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
