package com.harmony.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harmony.admin.model.dto.Qna;
import com.harmony.admin.service.AdminService;
import com.harmony.common.exception.HarmonyException;

/**
 * Servlet implementation class AdminQnAServlet
 */
@WebServlet("/admin/qna.do")
public class AdminQnAServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminQnAServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int no = -1;
		Qna qna = null;
		try {
			no = Integer.parseInt(request.getParameter("no"));
			if(no<=0) {
				throw new RuntimeException();
			}
			qna = AdminService.getService().getQnaByQnaNo(no);
			if(qna == null) {
				throw new RuntimeException();
			}
		}catch(Exception e) {
			throw new HarmonyException("유효하지 않은 글 번호입니다.");
		}
		request.setAttribute("qna", qna);
		List<String[]> category = AdminService.getService().getQnaCatList();
		request.setAttribute("catList", category);
		List<String[]> process = AdminService.getService().getQnaProList();
		request.setAttribute("proList",process);
		
		request.getRequestDispatcher("/views/admin/views/qnaview.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
