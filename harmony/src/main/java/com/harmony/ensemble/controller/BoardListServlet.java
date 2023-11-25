package com.harmony.ensemble.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harmony.common.PageBarBuilder;
import com.harmony.ensemble.model.dto.VEnsList;
import com.harmony.ensemble.model.service.EnsembleService;

/**
 * Servlet implementation class BoardList
 */
@WebServlet("/ensemble/boardList.do")
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		EnsembleService es = new EnsembleService();
		//DB에서 전체 게시글 가져오기
		int cPage=5;
		int numPerpage=6;
		try {
			cPage=Integer.parseInt(request.getParameter("cPage"));
		}catch(NumberFormatException e) {
			cPage=1;
		}
		List<VEnsList> boards = es.selectBoardList(cPage, numPerpage);
		
		int totalData=es.selectBoardCount();
		int totalPage=(int)Math.ceil((double)totalData/numPerpage);
		int pageBarSize=5;
		int pageNo=((cPage-1)/pageBarSize)*pageBarSize+1;
		int pageEnd=pageNo+pageBarSize-1;
		
		
		
		
		String pageBar = PageBarBuilder.pageBarBuilder(cPage, numPerpage, totalData, pageBarSize, request.getRequestURI());
		

		System.out.println(boards);
		request.setAttribute("boards", boards); 
		request.setAttribute("pageBar",pageBar);
		
		
		request.getRequestDispatcher("/views/ensemble/boardList.jsp")
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
