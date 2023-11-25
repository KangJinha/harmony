package com.harmony.message.model.dao;

import static com.harmony.common.JDBCTemplate.close;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.harmony.message.model.dto.Message;

public class MessageDao {
	private static MessageDao dao = new MessageDao();
	private MessageDao() {
		String path = MessageDao.class.getResource("/sql/message/message.properties").getPath();
		try(FileReader fr = new FileReader(path)){
			sql.load(fr);
		}catch(Exception e) {
			e.printStackTrace();
		}
	};
	private Properties sql = new Properties();
	
	public static MessageDao getDao() {
		return MessageDao.dao;
	}
	
	public int sendMessage(Connection conn, Message msg) {
		PreparedStatement pstmt = null;
		int result = -1;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("sendMessage"));
			// INSERT INTO TBL_MESSAGE VALUES(SEQ_MSG_NO.NEXTVAL,?,?,?,DEFAULT,NULL,?)
			pstmt.setString(1,msg.getReceiveMem());
			pstmt.setString(2,msg.getSendMem());
			pstmt.setString(3,msg.getContent());
			pstmt.setString(4,msg.getCatCode().name());
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int getUnreadMessage(Connection conn, String memNo) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("getUnreadMessage"));
			pstmt.setString(1, memNo);
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

	public List<Message> selectMessageByMemno(Connection conn, String memno) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Message> result = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql.getProperty("selectMessageByMemno"));
			pstmt.setString(1, memno);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				result.add(getMessage(rs));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	private Message getMessage(ResultSet rs) throws SQLException{
		return Message.builder()
				.msgNo(rs.getString("MSG_NO"))
				.receiveMem(rs.getString("MSG_RECEIVE_MEM"))
				.sendMem(rs.getString("MSG_SEND_MEM"))
				.content(rs.getString("MSG_CONTENT"))
				.sendDate(rs.getDate("MSG_SEND_DATE"))
				.readState(rs.getString("MSG_READ_STATE"))
				.catCode(Message.catType.valueOf(rs.getString("MSG_CAT_CODE")))
				.build();
	}

	public int readMessage(Connection conn, int msgNo) {
		PreparedStatement pstmt = null;
		int result = -1;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("readMessage"));
			pstmt.setInt(1, msgNo);
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public Message selectMessageByMessageNo(Connection conn, int messageNo) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Message result = null;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("selectMessageByMessageNo"));
			pstmt.setInt(1, messageNo);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = getMessage(rs);
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
