package com.harmony.ensemble.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harmony.ensemble.model.dto.VChkApply;
import com.harmony.ensemble.model.service.EnsembleService;

/**
 * Servlet implementation class CheckApplyServlet
 */
@WebServlet("/ensemble/chkApply.do")
public class ChkApplyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChkApplyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EnsembleService es = new EnsembleService();
		String boardNo = request.getParameter("boardNo");
		
		List<VChkApply> applyList = es.selectApplyByBoardNo(boardNo);
		
		request.setAttribute("applyList", applyList);
		request.getRequestDispatcher("/views/ensemble/chkApply.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
