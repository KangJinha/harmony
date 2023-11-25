package com.harmony.member.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harmony.member.service.MemberService;
import com.harmony.model.dto.GenreAll;
import com.harmony.model.dto.InterestAll;
import com.harmony.model.dto.Member;
import com.harmony.model.dto.MemberInfo;


/**
 * Servlet implementation class MyPageServlet
 */
@WebServlet("/member/myPageServlet.do")
public class MyPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyPageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Member m = (Member)request.getSession().getAttribute("loginMember"); 
		System.out.println(m);
		MemberInfo mi = new MemberService().selectMemberInfo(m.getMemNo());
//		List genreList=List.of(Map.of("genre",value,"genreNo",no),
//				Map.of("genre",value,"genreNo",no),
//				Map.of("genre",value,"genreNo",no)
//				)
		List<GenreAll> genreAll = new MemberService().selectGenreAll(); 
		List<InterestAll> interestAll = new MemberService().selectInterestAll();
//		List<String> interestAll= new MemberService();
		System.out.println(genreAll);
//		for(int i = 0; i<mi.getMemberVideo().size(); i++) {
//			String link = mi.getMemberVideo().get(i).getVideoLink();
//		}
//		List<MemberVideo> mv = new MemberService().selectVideoLink(m.getMemNo());
//		System.out.println("내가찾는"+mv);
		System.out.println(mi);
		request.setAttribute("MemberInfo", mi);
		request.setAttribute("GenreAll",genreAll);
		request.setAttribute("InterestAll",interestAll);
//		request.setAttribute("MemberVideo", mv);
//		request.setAttribute("mv", mv);
		System.out.println("이거 진짜 내가찾는거다"+mi);
		request.getRequestDispatcher("/views/member/mypage.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
