package com.harmony.admin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harmony.admin.model.dto.AdminMember;
import com.harmony.admin.model.dto.Qna;
import com.harmony.admin.service.AdminService;

/**
 * Servlet implementation class AdminQnAWriteServlet
 */
@WebServlet("/admin/qna/write.do")
public class AdminQnAWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminQnAWriteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getContextPath()+"/admin/qnalist.do");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String qstNo = request.getParameter("qstNo");
		String process = request.getParameter("process");
		String ansCont = request.getParameter("ansCont");
		
		AdminMember loginAdmin = (AdminMember)request.getSession().getAttribute("loginAdmin");
		
		Qna qna = Qna.builder().qstNo(Integer.parseInt(qstNo)).processCode(process).answerContent(ansCont).answer(loginAdmin.getAdminNo()).build();
		
		int result = AdminService.getService().updateQNA(qna);
		if(result>0) {
			response.sendRedirect(request.getContextPath()+"/admin/qnalist.do");
		}else {
			response.sendRedirect(request.getContextPath()+"/admin/qna.do?no="+qstNo);
		}
		
	}

}
