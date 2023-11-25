package com.harmony.lesson.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harmony.lesson.dto.Lesson;
import com.harmony.lesson.dto.LessonApply;
import com.harmony.lesson.service.LessonService;
import com.harmony.message.controller.SendMessage;
import com.harmony.message.model.dto.Message;
import com.harmony.message.model.dto.Message.catType;

/**
 * Servlet implementation class ApplyLesson
 */
@WebServlet("/apply/applyLesson.do")
public class ApplyLessonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApplyLessonServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		String memNo = request.getParameter("memNo");
		String place = request.getParameter("place");
		int count = Integer.parseInt(request.getParameter("count"));
		String teacherNo = request.getParameter("teacherNo");
		// teacherNo으로 MEM_NO가져오기
		Lesson findMemNo = new LessonService().applyFindMemNo(teacherNo);
		String applyMemNo=findMemNo.getMemNo();
		
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(System.currentTimeMillis());
		Timestamp startTime = Timestamp.valueOf(formatter.format(date) +" "+ request.getParameter("startTime"));
		Timestamp endTime = Timestamp.valueOf(formatter.format(date) +" "+ request.getParameter("endTime"));
		String[] day = request.getParameterValues("hopeDay");
		
		LessonApply applyMem = LessonApply
				.builder()
				.boardNo(boardNo)
				.memNo(memNo)
				.applyPlace(place)
				.applyNumberOfTimes(count)
				.lessonStartTime(startTime)
				.lessonEndTime(endTime)
				.lessonDay(day)
				.build();
		
		
		
		// 레슨신청되어있는지 확인
		LessonApply result2 =new LessonService().showApplyBtn(memNo,boardNo);
		//LessonApply result2 = new LessonService().showApplyInfo(memNo);
		String msg,loc;
		
		// 레슨신청
		
		if (result2==null) {
			int result = new LessonService().applyLesson(applyMem);
			if(result>0) {
				
				msg = "레슨 상담 신청에 성공하셨습니다. :)";
				loc = "/lesson/findLesson.do";
				SendMessage.sendMessage(Message.builder().sendMem(memNo).receiveMem(applyMemNo).catCode(catType.LESSON)
						.content("<p>새로운 레슨 신청이 도착했습니다.</p><br><p><a href=\""+request.getContextPath()+"/lesson/showApplyInfo.do?no="+boardNo+"\">[바로가기]</a></p>")
						.build());
				SendMessage.sendMessage(Message.builder().sendMem(applyMemNo).receiveMem(memNo).catCode(catType.LESSON)
						.content("<p>새로운 레슨을 신청했습니다. </p><br><p><a href=\""+request.getContextPath()+"/payment/view.do?no="+result+"\">[바로가기]</a></p>")
						.build());
			} else {
				msg = "신청실패 :(";
				loc = "/lesson/findLesson.do";
			}
		}
		else {
			msg = "이미 상담신청이 되어있습니다 :(";
			loc = "/lesson/findLesson.do";
		}
		
		
		
		
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		request.setAttribute("applyMemNo", applyMemNo);
		
		request.getRequestDispatcher("/views/lesson/common/msg.jsp")
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
