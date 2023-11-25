package com.harmony.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harmony.model.dto.Member;

/**
 * Servlet implementation class FreeBoardWriteServlet
 */
@WebServlet("/board/freeboardWrite.do")
public class FreeBoardWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FreeBoardWriteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인한 사용자 정보를 세션에서 가져옴
	    Member loginMember = (Member) request.getSession().getAttribute("loginMember");

	    // 로그인하지 않은 사용자의 경우 로그인 페이지로 보냄
	    if(loginMember == null) {
	        response.sendRedirect(request.getContextPath() + "/loginServlet.do");
	        return;
	    }
		
		request.getRequestDispatcher("/views/board/freeBoardWrite.jsp")
		.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
