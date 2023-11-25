package com.harmony.payment.controller.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.harmony.payment.service.PaymentService;

/**
 * Servlet implementation class AjaxRefundRequestServlet
 */
@WebServlet("/refund/request.do")
public class AjaxRefundRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxRefundRequestServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String payHisNo = request.getParameter("payHisNo");
		String refundReason = request.getParameter("refundReason");
		
		int result = PaymentService.getService().requestRefund(payHisNo, refundReason);
		
		JsonObject json = new JsonObject();
		
		if(result>0) {
			json.addProperty("result", "success");
		}else {
			json.addProperty("result", "fail");
		}
		
		Gson gson = new Gson();
		
		gson.toJson(json,response.getWriter());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
