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
@WebServlet("/LeftBarFilterTitleServlet.do")
public class LeftBarFilterTitleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LeftBarFilterTitleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		String keyword = request.getParameter("keyword");
		System.out.println(keyword);
		
		List<Lesson> lessons = new LessonService().printLessonByFilterTitle(keyword);
		
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
