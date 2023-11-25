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

import com.harmony.common.PageBarBuilder;
import com.harmony.payment.model.dto.RefundList;
import com.harmony.payment.service.PaymentService;

/**
 * Servlet implementation class AdminRefundManageServlet
 */
@WebServlet("/admin/manage/refund.do")
public class AdminRefundManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminRefundManageServlet() {
        super();
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
			if(c.getName().equals("admin_report_numPerPage")) {
				numPerPage = Integer.parseInt(c.getValue());
			}
			if(c.getName().equals("admin_report_pageBarSize")) {
				pageBarSize = Integer.parseInt(c.getValue());
			}
		}
		String type = request.getParameter("type");
		String keyword = request.getParameter("keyword");
		List<Map<String,String>> filters = new ArrayList<>();
		if(type!=null && keyword!=null) {
			filters.add(Map.of(type, keyword));
		}
		
		List<RefundList> refund = PaymentService.getService().selectRefundList(type, keyword, cPage, numPerPage);
		request.setAttribute("refundList", refund);
		
		List<String[]>stateCodes = PaymentService.getService().getStateCode();
		request.setAttribute("stateCodes", stateCodes);
		
		request.setAttribute("pageBar", PageBarBuilder.pageBarBuilder(cPage, numPerPage, numPerPage, pageBarSize, request.getRequestURI(), filters));
		
		request.getRequestDispatcher("/views/admin/views/refundManage.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
