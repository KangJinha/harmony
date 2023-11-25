package com.harmony.admin.controller.ajax;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.harmony.admin.model.dto.Report;
import com.harmony.admin.service.AdminService;
import com.harmony.common.exception.HarmonyException;

/**
 * Servlet implementation class AjaxReportManageServlet
 */
@WebServlet("/admin/ajax/report.do")
public class AjaxReportManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxReportManageServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int rptNo = 0;
		try {
			rptNo = Integer.parseInt(request.getParameter("no"));
			if(rptNo<=0) {
				throw new RuntimeException();
			}
		}catch(Exception e) {
			throw new HarmonyException("유효하지 않은 신고번호입니다.");
		}
		Report report = AdminService.getService().selectReportByNo(rptNo);
		List<String[]> category = AdminService.getService().getReportCategory();
		
		
		Gson gson = new Gson();
		JsonObject json = new JsonObject();
		json.addProperty("report", gson.toJson(report,Report.class));
		json.addProperty("category", gson.toJson(category));
		
		
		gson.toJson(json,response.getWriter());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
