package com.harmony.payment.model.dao;

import static com.harmony.common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.harmony.payment.model.dto.PaymentApplyDate;
import com.harmony.payment.model.dto.PaymentView;
import com.harmony.payment.model.dto.RefundList;

public class PaymentDao {
	
	private static PaymentDao dao = new PaymentDao();

	private PaymentDao() {
		String path = PaymentDao.class.getResource("/sql/payment/payment.properties").getPath();
		try(FileReader fr = new FileReader(path)) {
			sql.load(fr);
		}catch(IOException e) {
			e.printStackTrace();
		}
	};
	Properties sql = new Properties();
	
	public static PaymentDao getDao() {
		return PaymentDao.dao;
	}
	
	public int insertPayment(Connection conn, int applyNo, int totalAmount) {
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("insertPayment"));
			pstmt.setInt(1, applyNo);
			pstmt.setInt(2, totalAmount);
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}
	
	public PaymentView selectApplyInfo(Connection conn, int applyNo) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PaymentView result = null;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("selectApplyInfo"));
			pstmt.setInt(1, applyNo);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = getApplyInfo(rs);
				do {
					result.getDates().add(getApplyDate(rs));
				}while(rs.next());
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	private PaymentApplyDate getApplyDate(ResultSet rs) throws SQLException{
		return PaymentApplyDate.builder()
				.startTime(rs.getTimestamp("START_TIME"))
				.endTime(rs.getTimestamp("END_TIME"))
				.dayOfWeek(rs.getString("DAY_OF_WEEK"))
				.build();
	}

	private PaymentView getApplyInfo(ResultSet rs) throws SQLException{
		return PaymentView.builder()
				.applyNo(rs.getInt("APPLY_NO"))
				.studentMemNo(rs.getString("STUDENT_MEMNO"))
				.lessonTimes(rs.getInt("LESSON_TIMES"))
				.instName(rs.getString("INST_NAME"))
				.applyDate(rs.getDate("APPLY_DATE"))
				.status(rs.getString("APPLY_ACCEPT"))
				.price(rs.getInt("PRICE"))
				.place(rs.getString("PLACE"))
				.teacherMemNo(rs.getString("TEACHER_MEMNO"))
				.teacherName(rs.getString("TEACHER_NAME"))
				.teacherGender(rs.getString("TEACHER_GENDER"))
				.teacherEmail(rs.getString("TEACHER_EMAIL"))
				.teacherIntroduce(rs.getString("TEACHER_INTRODUCE"))
				.paymentStatus(rs.getString("PAYSTATE"))
				.payHisNo(rs.getString("PAYHISNO"))
				.build();
	}
	
	public String getPayHisNo(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("getPayHisNo"));
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getString(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	public int selectPaymentInfoCount(Connection conn, int applyNo) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = -1;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("selectPaymentInfoCount"));
			pstmt.setInt(1, applyNo);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	public String selectPaymentNoByApplyNo(Connection conn, int applyNo) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("selectPaymentNoByApplyNo"));
			pstmt.setInt(1, applyNo);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getString(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	public int updatePaymentSuccess(Connection conn, String merchant_uid, String imp_uid) {
		PreparedStatement pstmt = null;
		int result = -1;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("updatePaymentSuccess"));
			pstmt.setString(1, imp_uid);
			pstmt.setString(2, merchant_uid);
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int updateRefundRequest(Connection conn, String payHisNo) {
		PreparedStatement pstmt = null;
		int result = -1;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("updateRefundRequest"));
			pstmt.setString(1, payHisNo);
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int insertRefundRequest(Connection conn, String payHisNo, String refundReason) {
		PreparedStatement pstmt = null;
		int result = -1;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("insertRefundRequest"));
			pstmt.setString(1, payHisNo);
			pstmt.setString(2, refundReason);
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int getRefundTotalData(Connection conn, String type, String keyword) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = -1;
		String query = sql.getProperty("getRefundTotalData");
		query = query.replace("#where", type!=null?type+" = ? ":"");
		try {
			pstmt = conn.prepareStatement(query);
			if(type!=null) {
				pstmt.setString(1, keyword);
			}
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	public List<RefundList> selectRefundList(Connection conn, String type, String keyword, int cPage, int numPerPage) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RefundList> result = new ArrayList<>();
		int startData = (cPage -1)*numPerPage +1;
		int endData = cPage * numPerPage;
		String query = sql.getProperty("selectRefundList");
		query = query.replace("#where", type!=null?type+" = ?":"");
		try {
			pstmt = conn.prepareStatement(query);
			int count = 1;
			if(type!=null) {
				pstmt.setString(count++, keyword);
			}
			pstmt.setInt(count++, startData);
			pstmt.setInt(count++, endData);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				result.add(getRefundList(rs));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	private RefundList getRefundList(ResultSet rs) throws SQLException{
		return RefundList.builder()
				.refundHisNo(rs.getInt("REFUND_HIS_NO"))
				.payHisNo(rs.getString("PAYHISNO"))
				.refundReqDate(rs.getDate("REFUND_REQ_DATE"))
				.refundReason(rs.getString("REFUND_REASON"))
				.refundState(rs.getString("STATE_DETAIL"))
				.refundStateCode(rs.getString("STATE_CODE"))
				.applyNo(rs.getInt("APPLY_NO"))
				.impUid(rs.getString("IMP_UID"))
				.price(rs.getInt("PRICE"))
				.payReqDate(rs.getDate("PAY_REQ_DATE"))
				.build();
	}

	public List<String[]> getStateCode(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String[]> result = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql.getProperty("getStateCode"));
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String code = rs.getString("PAY_STATE_CODE");
				String name = rs.getString("PAY_STATE_NAME");
				if(Integer.parseInt(code)%100<10) {
					continue;
				}
				result.add(new String[] {code, name});
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	public RefundList selectRefundByNo(Connection conn, int no) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RefundList result = null;
		
		try {
			pstmt = conn.prepareStatement(sql.getProperty("selectRefundByNo"));
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = getRefundList(rs);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	public int refundSuccess(Connection conn, String payHisCode) {
		PreparedStatement pstmt = null;
		int result = -1;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("refundSuccess"));
			pstmt.setString(1, payHisCode);
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int refundReject(Connection conn, String payHisCode) {
		PreparedStatement pstmt = null;
		int result = -1;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("refundReject"));
			pstmt.setString(1, payHisCode);
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int getCurrval(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = -1;
		try {
			pstmt = conn.prepareStatement("SELECT LES_APPLY_SEQ.CURRVAL FROM DUAL");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}
}
