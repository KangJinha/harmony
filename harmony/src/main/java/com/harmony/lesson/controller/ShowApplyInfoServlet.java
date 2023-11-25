package com.harmony.lesson.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harmony.lesson.dto.Lesson;
import com.harmony.lesson.dto.LessonApply;
import com.harmony.lesson.service.LessonService;
import com.harmony.model.dto.Member;
import com.harmony.model.dto.MemberInfo;

/**
 * Servlet implementation class ShowApplyInfoServlet
 */
@WebServlet("/lesson/showApplyInfo.do")
public class ShowApplyInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowApplyInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int boardNo = Integer.parseInt(request.getParameter("no"));
		
		Member loginMember = (Member)request.getSession().getAttribute("loginMember");
		String memNo = loginMember.getMemNo();
		
		// memNo로 내가 신청한 상담신청정보 가져오기
		LessonApply lessonApplyInfo = new LessonService().showApplyInfoByNo(memNo);
		System.out.println(lessonApplyInfo);
		Lesson l = new LessonService().selectLessonByNo(boardNo);
		
		//레슨 게시글번호로 레슨시간,요일정보
		Lesson time = new LessonService().selectTimeByNo(boardNo);
		// 선생님번호가져오기
		String tNo= l.getTeacherNo();
		// 강사정보 가져오기
		MemberInfo mi = new LessonService().selectMemberInfoByTeacherNo(tNo);
		
		
		if (loginMember!=null) {
			memNo = loginMember.getMemNo();
			LessonApply applyInfo = new LessonService().showApplyInfo(memNo);
			request.setAttribute("applyInfo", applyInfo);
			LessonApply showApplyBtn = new LessonService().showApplyBtn(loginMember.getMemNo(),boardNo);
			request.setAttribute("showApplyBtn", showApplyBtn);
		}
		
		request.setAttribute("teacherInfo", mi);
		request.setAttribute("time", time);
		request.setAttribute("lesson", l);
		request.setAttribute("lessonApplyInfo", lessonApplyInfo);
		request.getRequestDispatcher("/views/lesson/showApplyInfo.jsp")
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
