package com.harmony.payment.controller.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.harmony.payment.controller.PaymentModule;
import com.harmony.payment.model.dto.PaymentView;
import com.harmony.payment.service.PaymentService;

/**
 * Servlet implementation class AjaxPaymentPrepareServlet
 */
@WebServlet("/payment/ajax/prepare.do")
public class AjaxPaymentPrepareServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxPaymentPrepareServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		Gson gson = new Gson();
		
		int applyNo = Integer.parseInt(request.getParameter("applyNo"));
		
		PaymentView view = PaymentService.getService().selectPaymentView(applyNo);
		int totalAmount = view.getLessonTimes() * view.getPrice();
		String merchantUid = PaymentService.getService().insertHistory(applyNo, totalAmount);
		if(merchantUid !=null) {
		PaymentModule.getModule().preValidate(merchantUid, totalAmount);
		}
		
		JsonObject json = new JsonObject();
		json.addProperty("merchantUid", merchantUid);
		gson.toJson(json, response.getWriter());
	}

}
