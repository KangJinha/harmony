package com.harmony.ensemble.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.harmony.ensemble.model.dto.EnsembleBoardWantPart;
import com.harmony.ensemble.model.dto.VEnsList;
import com.harmony.ensemble.model.service.EnsembleService;

/**
 * Servlet implementation class DeleteBoardServlet
 */
@WebServlet("/ensemble/deleteBoard.do")
public class DeleteBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteBoardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EnsembleService es = new EnsembleService();
		String boardNo = request.getParameter("boardNo");
		System.out.println("서블릿 boardNo: "+ boardNo );
		int result = es.deleteBoard(boardNo);
		
		if(result>0) System.out.println("글 삭제 성공");
		else System.out.println("글 삭제 실패");
	
//		
//		result = es.deleteWantPart(boardNo);
//		
//		if(result>0) System.out.println("모집파트 삭제 성공");
//		else System.out.println("모집파트 삭제 실패");

		response.sendRedirect(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
