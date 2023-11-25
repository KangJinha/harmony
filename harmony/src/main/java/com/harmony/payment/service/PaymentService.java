package com.harmony.payment.service;

import static com.harmony.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import com.harmony.payment.model.dao.PaymentDao;
import com.harmony.payment.model.dto.PaymentView;
import com.harmony.payment.model.dto.RefundList;

public class PaymentService {
	private static PaymentService service = new PaymentService();
	private PaymentService() {};
	
	public static PaymentService getService() {
		return PaymentService.service;
	}
	
	public PaymentView selectPaymentView(int applyNo) {
		Connection conn = getConnection();
		PaymentView result = PaymentDao.getDao().selectApplyInfo(conn, applyNo);
		close(conn);
		return result;
	}

	public String insertHistory(int applyNo, int totalAmount) {
		Connection conn = getConnection();
		String payHisNo = null;
		int result = PaymentDao.getDao().selectPaymentInfoCount(conn,applyNo);
		if(result==0) {
			result = PaymentDao.getDao().insertPayment(conn, applyNo, totalAmount);
			if(result>0) {
				payHisNo = PaymentDao.getDao().getPayHisNo(conn);
				commit(conn);
			}else {
				rollback(conn);
			}
		}else {
			payHisNo = PaymentDao.getDao().selectPaymentNoByApplyNo(conn,applyNo);
		}
		close(conn);
		return payHisNo;
	}

	public int updatePayment(String merchant_uid, String imp_uid) {
		Connection conn = getConnection();
		int result = PaymentDao.getDao().updatePaymentSuccess(conn,merchant_uid,imp_uid);
		if(result>0)commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}

	public int requestRefund(String payHisNo, String refundReason) {
		Connection conn = getConnection();
		int paymentResult = PaymentDao.getDao().updateRefundRequest(conn,payHisNo);
		int refundResult = PaymentDao.getDao().insertRefundRequest(conn,payHisNo,refundReason);
		int result = paymentResult * refundResult;
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		return result;
	}

	public int getRefundTotalData(String type, String keyword) {
		Connection conn = getConnection();
		int result = PaymentDao.getDao().getRefundTotalData(conn,type,keyword);
		close(conn);
		return result;
	}

	public List<RefundList> selectRefundList(String type, String keyword, int cPage, int numPerPage) {
		Connection conn = getConnection();
		List<RefundList> target = PaymentDao.getDao().selectRefundList(conn, type, keyword, cPage, numPerPage);
		close(conn);
		return target;
	}

	public List<String[]> getStateCode() {
		Connection conn = getConnection();
		List<String[]> result = PaymentDao.getDao().getStateCode(conn);
		close(conn);
		return result;
	}

	public RefundList selectRefundByNo(int no) {
		Connection conn = getConnection();
		RefundList result = PaymentDao.getDao().selectRefundByNo(conn, no);
		close(conn);
		return result;
	}

	public int refundSuccess(String payHisCode, int refundHisNo) {
		Connection conn = getConnection();
		int result = PaymentDao.getDao().refundSuccess(conn, payHisCode);
		if(result>0)commit(conn);
		else rollback(conn);
		close(conn);
		return 0;
	}

	public int refundreject(String payHisCode, int refundHisNo) {
		Connection conn = getConnection();
		int result = PaymentDao.getDao().refundReject(conn, payHisCode);
		if(result>0)commit(conn);
		else rollback(conn);
		close(conn);
		return 0;
	}
	
}
