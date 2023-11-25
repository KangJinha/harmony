package com.harmony.admin.controller.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.harmony.common.exception.HarmonyException;
import com.harmony.payment.model.dto.RefundList;
import com.harmony.payment.service.PaymentService;

/**
 * Servlet implementation class AjaxRefundManageServlet
 */
@WebServlet("/admin/refund/refund.do")
public class AjaxRefundManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxRefundManageServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int no=-1;
		try {
			no = Integer.parseInt(request.getParameter("no"));
			if(no<=0) {
				throw new RuntimeException();
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new HarmonyException("유효하지 않은 번호입니다.");
		}
		RefundList refund = PaymentService.getService().selectRefundByNo(no);
		Gson gson = new Gson();
		JsonObject json = new JsonObject();
		
		json.addProperty("refund", gson.toJson(refund, RefundList.class));
		
		gson.toJson(json,response.getWriter());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
