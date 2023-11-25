package com.harmony.admin.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harmony.admin.model.dto.AdminMember;
import com.harmony.admin.service.AdminService;
import com.harmony.common.exception.HarmonyException;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/adminLogin.do")
public class AdminLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/views/admin/views/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String pw = request.getParameter("password");
		
		AdminMember login = AdminMember.builder().adminId(id).adminPw(pw).build();
		
		AdminMember loginMember = AdminService.getService().adminLogin(login);
		if(loginMember !=null) {
			request.getSession().setAttribute("loginAdmin", loginMember);
			response.sendRedirect(request.getContextPath()+"/admin/main.do");
		}else {
			throw new HarmonyException("어드민의 아이디 또는 비밀번호가 맞지 않습니다.");
		}
	}

}
