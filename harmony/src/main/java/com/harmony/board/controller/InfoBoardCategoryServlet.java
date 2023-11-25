package com.harmony.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harmony.board.info.model.dto.InfoBoard;
import com.harmony.board.model.service.InfoBoardService;
import com.harmony.common.PageBarBuilder;

/**
 * Servlet implementation class InfoBoardCategoryServlet
 */
@WebServlet("/community/searchcommunity.do")
public class InfoBoardCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public InfoBoardCategoryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String category = request.getParameter("category");
        String tag = request.getParameter("tag");
        String region = request.getParameter("region");

        
      
        
        if (category == null) category = "";
        if (tag == null) tag = "";
        if (region == null) region = "";
        
        if ("all".equals(category)) category = "";
        if ("all".equals(tag)) tag = "";
        if ("all".equals(region)) region = "";
        

        int cPage, numPerpage=5;

        try {
            cPage = Integer.parseInt(request.getParameter("cPage"));
        } catch (NumberFormatException e) {
            cPage = 1;
        }

        List<InfoBoard> boards = new InfoBoardService().selectBoardByCategoryTagRegion(category, tag, region, cPage, numPerpage);

        for (InfoBoard board : boards) {
	        String categoryName = new InfoBoardService().selectCategoryNameByNo(board.getInfBrdCatNo());
	        String tagName = new InfoBoardService().selectTagNameByNo(board.getInfBrdTagNo());
	        int commentCount = new InfoBoardService().getCommentCount(board.getInfBrdNo());
	        request.setAttribute("categoryName" + board.getInfBrdNo(), categoryName);
	        request.setAttribute("tagName" + board.getInfBrdNo(), tagName);
	        request.setAttribute("commentCount" + board.getInfBrdNo(), commentCount);

	    }
        
        
        int totalData = new InfoBoardService().selectBoardCountByCategoryTagRegion(category, tag, region);
        int pageBarSize = 5;

        String pageBar = PageBarBuilder.pageBarBuilderWithCategoryTagRegion(cPage, numPerpage, totalData, pageBarSize, request.getRequestURI(), category, tag, region);

        request.setAttribute("pageBar", pageBar);
        request.setAttribute("boards", boards);

        request.getRequestDispatcher("/views/board/infoBoardList.jsp").forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
