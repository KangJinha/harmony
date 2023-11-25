package com.harmony.admin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harmony.admin.model.dto.QNAList;
import com.harmony.admin.service.AdminService;
import com.harmony.common.PageBarBuilder;

/**
 * Servlet implementation class AdminQnAListServlet
 */
@WebServlet("/admin/qnalist.do")
public class AdminQnAListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminQnAListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int cPage = Integer.parseInt(request.getParameter("cPage")!=null?request.getParameter("cPage"):"1");
		Cookie[] cookies = request.getCookies();
		int numPerPage = 10;
		int pageBarSize = 5;
		for(Cookie c : cookies) {
			if(c.getName().equals("admin_QNA_numPerPage")) {
				numPerPage = Integer.parseInt(c.getValue());
			}
			if(c.getName().equals("admin_QNA_pageBarSize")) {
				pageBarSize = Integer.parseInt(c.getValue());
			}
		}
		String type = request.getParameter("type");
		String keyword = request.getParameter("keyword");
		List<Map<String,String>> filters = new ArrayList<>();
		if(type!=null && keyword!=null) {
			filters.add(Map.of(type, keyword));
		}
		int totalData = AdminService.getService().getQNATotalData(type,keyword);
		List<QNAList> QNAList = AdminService.getService().selectQNAList(type,keyword,cPage,numPerPage);
		
		request.setAttribute("QNAList", QNAList);
		request.setAttribute("pageBar", PageBarBuilder.pageBarBuilder(cPage, numPerPage, totalData, pageBarSize, request.getRequestURI(),filters));
		request.getRequestDispatcher("/views/admin/views/qnalist.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
