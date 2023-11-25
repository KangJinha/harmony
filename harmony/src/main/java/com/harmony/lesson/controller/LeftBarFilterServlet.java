package com.harmony.lesson.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.If;

import com.google.gson.Gson;
import com.harmony.lesson.dto.Lesson;
import com.harmony.lesson.service.LessonService;

/**
 * Servlet implementation class LeftBarFilterServlet
 */
@WebServlet("/LeftBarFilterServlet.do")
public class LeftBarFilterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LeftBarFilterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		String keyword = request.getParameter("keyword");
		System.out.println(keyword);
		
		List<Lesson> lessons = null;
		if(keyword.length()>5 && keyword.contains("INST_")) { // 악기
			lessons = new LessonService().printLessonByFilterInst(keyword);
		} else if (keyword.length()==2) { // 장소
			lessons = new LessonService().printLessonByFilterPlace(keyword);
		} else if (keyword.length()==7 || keyword.equals("협의가능")) { // 가격
			lessons = new LessonService().printLessonByFilterPrice(keyword);
		} else if (keyword.length()==4){ // 시간대
			lessons = new LessonService().printLessonByFilterTime(keyword);
		}
		
//		else if (keyword.equals("12")||keyword.equals("18")||keyword.equals("24")){ // 시간대
//			lessons = new LessonService().printLessonByFilterTime(keyword);
//		}
		
		request.setAttribute("lessons", lessons);
		response.setContentType("application/json;charset=utf-8");
		new Gson().toJson(lessons,response.getWriter());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
