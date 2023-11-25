package com.harmony.lesson.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harmony.lesson.dto.Lesson;
import com.harmony.lesson.dto.LessonApply;
import com.harmony.lesson.dto.LessonComment;
import com.harmony.lesson.dto.SaveLesson;
import com.harmony.lesson.service.LessonService;
import com.harmony.member.service.MemberService;
import com.harmony.model.dto.Member;
import com.harmony.model.dto.MemberInfo;

/**
 * Servlet implementation class LessonInfo
 */
@WebServlet("/lesson/lessonInfo.do")
public class LessonInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LessonInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//레슨 게시글번호
		int no = Integer.parseInt(request.getParameter("no"));
		System.out.println(no);
		
		Cookie[] cookies = request.getCookies();
		String readLesson ="";
		boolean readResult = false;
		for(Cookie c : cookies) {
			String name = c.getName();
			if(name.equals("readLesson")) {
				readLesson = c.getValue();
				if(readLesson.contains("|"+no+"|")) {
					readResult = true;
				}
				break;
			}
		}
		
		if(!readResult) {
			Cookie c = new Cookie("readLesson",readLesson + "|"+no+"|");
			c.setMaxAge(60*60*1);
			response.addCookie(c);
		}
		
		
		//레슨 게시글번호로 레슨정보가져오기 + 조회수
		Lesson lesson = new LessonService().selectLessonByNo(no,readResult);
		String tNo= lesson.getTeacherNo();
		//레슨 게시글번호로 레슨시간,요일정보
		Lesson time = new LessonService().selectTimeByNo(no);
		//레슨 게시글 번호로 리뷰가져오기
		List<LessonApply> reviews = new LessonService().selectReviewByNo(no);
		// 리뷰개수 가져오기
		int reviewsCount = new LessonService().selectReviewCountByNo(no);
		// 레슨 게시글번호로 레슨 리뷰댓글가져오기
		List<LessonComment> co = new LessonService().selectReviewComment(no);
		// 강사정보 가져오기
		MemberInfo mi = new LessonService().selectMemberInfoByTeacherNo(tNo);
		
		Member loginMember = (Member)request.getSession().getAttribute("loginMember");
		if(loginMember!=null) {
			// 찜이력가져오기
			SaveLesson heart = new LessonService().saveLessonConfirm(no, loginMember.getMemNo());
			request.setAttribute("heart", heart);
//			LessonApply apply = new LessonService().showApplyInfo(loginMember.getMemNo());
//			request.setAttribute("apply", apply);
			// 레슨신청 & 레슨신청시간 & 레슨테이블조인해서 레슨신청했으면 신청정보보기 버튼표시
			LessonApply showApplyBtn = new LessonService().showApplyBtn(loginMember.getMemNo(),no);
			request.setAttribute("showApplyBtn", showApplyBtn);
			System.out.println("신청정보" + showApplyBtn);
		}
		// 레슨신청되어있는지 확인
		
		
		request.setAttribute("teacherInfo", mi);
		request.setAttribute("time", time);
		request.setAttribute("lesson", lesson);
		request.setAttribute("review", reviews);
		request.setAttribute("reviewsCount", reviewsCount);
		request.setAttribute("co", co);
		request.getRequestDispatcher("/views/lesson/lessonInfo.jsp")
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
