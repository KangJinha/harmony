package com.harmony.ensemble.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.harmony.ensemble.model.service.EnsembleService;

/**
 * Servlet implementation class ChkApplyEndServlet
 */
@WebServlet("/ensemble/chkApplyEnd.do")
public class ChkApplyEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChkApplyEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		Gson gson = new Gson();
		
		EnsembleService es = new EnsembleService();
	    
		String[] wantParts = gson.fromJson(request.getParameter("wantParts"), String[].class);
		
		
		String partIndex = request.getParameter("partIndex");
		String boardNo = request.getParameter("boardNo");
		
		int result = es.changeApproval(partIndex);
		
		if(result>0) System.out.println("수락 성공");
		
		response.sendRedirect(request.getContextPath()+"/ensemble/chkApply.do?boardNo="+boardNo);
//		response.sendRedirect(request.getContextPath()+"/views/ensemble/chkApply.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
