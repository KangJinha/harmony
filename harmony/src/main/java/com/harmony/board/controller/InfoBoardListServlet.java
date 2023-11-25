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
 * Servlet implementation class InfoBoardListServlet
 */
@WebServlet("/infoBoardList.do")
public class InfoBoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InfoBoardListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int cPage,numPerpage=5;
		//cPage=현재페이지 numPerpage=보여주는 페이지갯수(5)
		try {
			cPage=Integer.parseInt(request.getParameter("cPage"));
		}catch(NumberFormatException e) {
			cPage=1;
		}
		List<InfoBoard> boards=new InfoBoardService().selectBoard(cPage, numPerpage);
		//1~5 6~10 페이징처리하여 boards에 넣음
		
		for (InfoBoard board : boards) {
	        String categoryName = new InfoBoardService().selectCategoryNameByNo(board.getInfBrdCatNo());
	        String tagName = new InfoBoardService().selectTagNameByNo(board.getInfBrdTagNo());
	        int commentCount = new InfoBoardService().getCommentCount(board.getInfBrdNo());
	        request.setAttribute("categoryName" + board.getInfBrdNo(), categoryName);
	        request.setAttribute("tagName" + board.getInfBrdNo(), tagName);
	        request.setAttribute("commentCount" + board.getInfBrdNo(), commentCount);

	    }
		
		int totalData=new InfoBoardService().selectBoardCount();
		int totalPage=(int)Math.ceil((double)totalData/numPerpage);
		int pageBarSize=5;
//		int pageNo=((cPage-1)/pageBarSize)*pageBarSize+1;
//		int pageEnd=pageNo+pageBarSize-1;
//		
//		StringBuffer pageBar=new StringBuffer();
//		if(pageNo==1) {
//			pageBar.append("<span>[이전]</span>");
//		}else {
//			pageBar.append("<a href='"+request.getRequestURI()+"?cPage="+(pageNo-1)+"'>");
//			pageBar.append("[이전]</a>");
//		}
//		
//		while(!(pageNo>pageEnd||pageNo>totalPage)) {
//			if(cPage==pageNo) {
//				pageBar.append("<span>"+pageNo+"</span>");
//			}else {
//				pageBar.append("<a href='"+request.getRequestURI()+"?cPage="+pageNo+"'>");
//				pageBar.append(pageNo);
//				pageBar.append("</a>");
//			}
//			pageNo++;
//		}
//		
//		if(pageNo>totalPage) {
//			pageBar.append("<span>[다음]</span>");
//		}else {
//			pageBar.append("<a href='"+request.getRequestURI()+"?cPage="+pageNo+"'>");
//			pageBar.append("[다음]");
//			pageBar.append("</a>");
//		}
		
		String pageBar = PageBarBuilder.pageBarBuilder(cPage, numPerpage, totalData, pageBarSize, request.getRequestURI());
		//위에 주석단거는 이거 한줄로 표기
		
		request.setAttribute("boards", boards);
		request.setAttribute("pageBar",pageBar);
		request.getRequestDispatcher("/views/board/infoBoardList.jsp")
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
