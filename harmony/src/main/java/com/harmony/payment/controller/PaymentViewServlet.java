package com.harmony.payment.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harmony.common.exception.HarmonyException;
import com.harmony.model.dto.Member;
import com.harmony.payment.model.dto.PaymentView;
import com.harmony.payment.service.PaymentService;

/**
 * Servlet implementation class PaymentViewServlet
 */
@WebServlet("/payment/view.do")
public class PaymentViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaymentViewServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String no = request.getParameter("no");
		int applyNo = -1;
		PaymentView view = null;
		try {
			applyNo = Integer.parseInt(no);
			if(applyNo<0) {
				throw new RuntimeException();
			}
			view = PaymentService.getService().selectPaymentView(applyNo);
			
			if(view == null) {
				throw new RuntimeException();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new HarmonyException("유효하지 않은 번호입니다.");
		}
		
		Member loginMember = (Member)request.getSession().getAttribute("loginMember");
		
		if(!loginMember.getMemNo().equals(view.getStudentMemNo())&&!loginMember.getMemNo().equals(view.getTeacherMemNo())) {
			throw new HarmonyException("권한이 부족합니다.");
		}
		
		request.setAttribute("paymentView", view);
		request.getRequestDispatcher("/views/payment/showPayment.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
