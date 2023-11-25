package com.harmony.ensemble.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.harmony.ensemble.model.dto.VEnsList;
import com.harmony.ensemble.model.service.EnsembleService;

/**
 * Servlet implementation class AjaxFilter
 */
@WebServlet("/ensemble/ajaxFilter.do")
public class AjaxFilterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxFilterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		Gson gson = new Gson();
		
		EnsembleService es = new EnsembleService();
	    
		String[] values = gson.fromJson(request.getParameter("filter_values"), String[].class);
	
		
		List<VEnsList> result = es.filterValues(values);
		gson.toJson(gson.toJson(result),response.getWriter());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
