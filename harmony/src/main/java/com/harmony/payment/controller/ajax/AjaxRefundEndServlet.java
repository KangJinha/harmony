package com.harmony.payment.controller.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.harmony.common.exception.HarmonyException;
import com.harmony.payment.controller.PaymentModule;
import com.harmony.payment.service.PaymentService;

/**
 * Servlet implementation class AjaxRefundEndServlet
 */
@WebServlet("/ajax/refund/refund.do")
public class AjaxRefundEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxRefundEndServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String impUid = request.getParameter("impUid");
		System.out.println(impUid);
		String payHisCode = request.getParameter("payHisCode");
		System.out.println(payHisCode);
		String type = request.getParameter("type");
		System.out.println(type);
		int refundHisNo = -1;
		try {
		refundHisNo = Integer.parseInt(request.getParameter("refundHisNo"));
		if(refundHisNo<0) {
			throw new RuntimeException();
		}
		}catch(Exception e) {
			throw new HarmonyException("유효하지 않은 환불번호입니다.");
		}
		boolean result = false;
		if(type.equals("accept")) {
			boolean refundresult = PaymentModule.getModule().refund(impUid, payHisCode);
			if(refundresult) {
				int dbupdateResult = PaymentService.getService().refundSuccess(payHisCode,refundHisNo);
				if(dbupdateResult>0) {
					result = true;
				}else {
					result = false;
				}
			}
		}else if(type.equals("reject")) {
			int dbupdateResult = PaymentService.getService().refundreject(payHisCode,refundHisNo);
			if(dbupdateResult>0) {
				result = true;
			}else {
				result = false;
			}
		}else {
			throw new HarmonyException("지정되지 않은 타입입니다."+type);
		}
		
		Gson gson = new Gson();
		
		JsonObject json = new JsonObject();
		json.addProperty("type", type);
		if(result) {
			json.addProperty("result", "success");
		}else {
			json.addProperty("result", "fail");
		}
		
		gson.toJson(json,response.getWriter());
	}

}
