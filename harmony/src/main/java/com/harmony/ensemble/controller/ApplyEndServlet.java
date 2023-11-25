package com.harmony.ensemble.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harmony.ensemble.model.service.EnsembleService;
import com.harmony.model.dto.Member;

/**
 * Servlet implementation class ApplyEnd
 */
@WebServlet("/ensemble/applyEnd.do")
public class ApplyEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApplyEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EnsembleService es = new EnsembleService();
		
		String wantPart = request.getParameter("inst");
		String instNo = es.selectInstNoByName(wantPart);
		
	
		Member loginMember = (Member)request.getSession().getAttribute("loginMember");
		String loginMemNo = loginMember.getMemNo();
		
		String boardNo = request.getParameter("boardNo");
//		System.out.println("ApplyEndServlet boardNo: "+ boardNo);
		
	
		
		int result = es.selectPartIndex(boardNo, instNo,loginMemNo);
		
		
//		request.getRequestDispatcher("/views/ensemble/boardList.do").forward(request, response);
//		response.sendRedirect(request.getContextPath());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
