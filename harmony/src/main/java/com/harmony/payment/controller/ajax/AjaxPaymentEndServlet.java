package com.harmony.payment.controller.ajax;

import java.io.Console;
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
 * Servlet implementation class AjaxPaymentEndServlet
 */
@WebServlet("/payment/ajax/payend.do")
public class AjaxPaymentEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxPaymentEndServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String merchant_uid = request.getParameter("merchant_uid");
		String imp_uid = request.getParameter("imp_uid");
		System.out.println("merchant_uid : "+merchant_uid);
		System.out.println("imp_uid : "+imp_uid);
		JsonObject json = new JsonObject();
		int result = PaymentService.getService().updatePayment(merchant_uid, imp_uid);
		System.out.println(result);;
		if(result > 0) {
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
