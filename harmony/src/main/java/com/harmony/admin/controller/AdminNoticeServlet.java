package com.harmony.admin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harmony.admin.model.dto.Notice;
import com.harmony.admin.service.AdminService;
import com.harmony.common.exception.HarmonyException;

/**
 * Servlet implementation class AdminNoticeServlet
 */
@WebServlet("/admin/notice.do")
public class AdminNoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminNoticeServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int no = Integer.parseInt(request.getParameter("no")!=null?request.getParameter("no"):"-1");
		if(no<0) {
			throw new HarmonyException("해당 공지사항을 찾을 수 없습니다.");
		}
		Notice notice = AdminService.getService().selectNoticeByNo(no,true);
		
		request.setAttribute("notice", notice);
		
		request.getRequestDispatcher("/views/admin/views/noticeview.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
