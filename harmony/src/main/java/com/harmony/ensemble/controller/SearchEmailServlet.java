package com.harmony.ensemble.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harmony.ensemble.model.dto.Inst;
import com.harmony.ensemble.model.service.EnsembleService;
/**
 * Servlet implementation class SearchEmail
 */
@WebServlet("/ensemble/searchEmail.do")
public class SearchEmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchEmailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//이메일 검색 
		//사용자가 전달한 이메일이 DB(TBL_MEMBER_INFO)에 있는지 확인
		
		List<Inst> inst = new EnsembleService().searchAllInst();
		
		request.setAttribute("inst", inst);
	
		
		EnsembleService es = new EnsembleService();
//		
		String userEmail = request.getParameter("keyword");
		String memNo= es.selectMemberByEmail(userEmail); //검색한 이메일의 회원넘버
		
		System.out.println("searchEmailServlet 검색한이메일: "+ userEmail);
		System.out.println("검색한 이메일의 회원 넘버: " + memNo);
		
		if(!memNo.equals("")) {
			//검색한 이메일이 회원 테이블에 있으면
			request.setAttribute("memNo", memNo);
		}else{
			//검색한 이메일이 회원 테이블에 없음
			request.setAttribute("msg","존재하지 않는 회원입니다.");
			request.setAttribute("result", "fail");
			
	
		}
//		
		request.getRequestDispatcher("/views/ensemble/addTeamMem.jsp").forward(request, response);
////		String instCode = request.getParameter("inst");
//		
//		MemberEns loginMember = (MemberEns)request.getSession().getAttribute("loginMember");
//		int result = 0;
//		
//			String loginMemNo = loginMember.getMemNo();
//	
//			//로그인한 멤버가 회원테이블에 있는지 - LEADER
//			
//		
//			String loginEmail = loginMember.getMemInfoEmail();
			//----------------------
		
				
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
