package com.harmony.admin.model.dao;

import static com.harmony.common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.harmony.admin.model.dto.AdminMember;
import com.harmony.admin.model.dto.Carousel;
import com.harmony.admin.model.dto.Notice;
import com.harmony.admin.model.dto.NoticeAttachFile;
import com.harmony.admin.model.dto.NoticeList;
import com.harmony.admin.model.dto.QNAList;
import com.harmony.admin.model.dto.Qna;
import com.harmony.admin.model.dto.Report;
import com.harmony.admin.model.dto.ReportList;

public class AdminDao {
	private static AdminDao dao = new AdminDao();
	private Properties sql = new Properties();
	private AdminDao() {
		String path = AdminDao.class.getResource("/sql/admin/admin.properties").getPath();
		try (FileReader fr = new FileReader(path)){
			sql.load(fr);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static AdminDao getDao() {
		return AdminDao.dao;
	}
	
	public List<Carousel> selectActiveCarousels(Connection conn){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Carousel> result = new ArrayList<>();
		
		try{
			pstmt = conn.prepareStatement(sql.getProperty("selectActiveCarousel"));
			rs = pstmt.executeQuery();
			while(rs.next()) {
				result.add(getCarousel(rs));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}
	
	private Carousel getCarousel(ResultSet rs) throws SQLException{
		return Carousel.builder().crslNo(rs.getInt("CRSL_NO"))
								 .crslName(rs.getString("CRSL_NAME"))
								 .crslImgLink(rs.getString("CRSL_IMG_LINK"))
								 .crslPageLink(rs.getString("CRSL_PAGE_LINK"))
								 .crslWriteDate(rs.getDate("CRSL_WRITE_DATE"))
								 .crslEndDate(rs.getDate("CRSL_END_DATE"))
								 .crslIntervalMs(rs.getInt("CRSL_INTERVAL_MS"))
								 .crslViewRank(rs.getInt("CRSL_VIEW_RANK"))
								 .status(rs.getString("STATUS"))
								 .build();
	}

	public AdminMember adminLogin(Connection conn, AdminMember login) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AdminMember member = null;
		
		try{
			pstmt = conn.prepareStatement(sql.getProperty("adminLogin"));
			pstmt.setString(1, login.getAdminId());
			pstmt.setString(2, login.getAdminPw());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				member = AdminMember.builder().adminId(rs.getString("ADMIN_ID"))
											  .adminName(rs.getString("ADMIN_NAME"))
											  .adminNo(rs.getString("ADMIN_NO"))
											  .build();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return member;
	}

	public List<Carousel> selectAllCarousels(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Carousel> result = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql.getProperty("selectAllCarousel"));
			rs = pstmt.executeQuery();
			while(rs.next()) {
				result.add(getCarousel(rs));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	public int deleteCarousel(Connection conn, String crslNo) {
		PreparedStatement pstmt = null;
		int result = -1;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("deleteCarousel"));
			pstmt.setString(1, crslNo);
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	public int getCrslSeqNo(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = -1;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("getCrslSeqNo"));
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int insertCarousel(Connection conn, Carousel c) {
		PreparedStatement pstmt = null;
		int result = -1;
		String query = sql.getProperty("insertCarousel");
		StringBuilder cols = new StringBuilder();
		StringBuilder vals = new StringBuilder();
		
		cols.append("CRSL_NO,");
		vals.append("? ,");
		if(c.getCrslName()!=null) {
			cols.append("CRSL_NAME,");
			vals.append("? ,");
		}
		if(c.getCrslImgLink()!=null) {
			cols.append("CRSL_IMG_LINK,");
			vals.append("? ,");
		}
		if(c.getCrslPageLink()!=null) {
			cols.append("CRSL_PAGE_LINK,");
			vals.append("? ,");
		}
		if(c.getCrslWriteDate()!=null) {
			cols.append("CRSL_WRITE_DATE,");
			vals.append("? ,");
		}
		if(c.getCrslEndDate()!=null) {
			cols.append("CRSL_END_DATE,");
			vals.append("? ,");
		}
		if(c.getCrslIntervalMs()>=0) {
			cols.append("CRSL_INTERVAL_MS,");
			vals.append("? ,");
		}
		if(c.getCrslViewRank()>=0) {
			cols.append("CRSL_VIEW_RANK,");
			vals.append("? ,");
		}
		query = query.replace("#cols", cols.substring(0, cols.length()-1).toString());
		query = query.replace("#vals", vals.substring(0, vals.length()-1).toString());
		try {
			pstmt = conn.prepareStatement(query);
			int count = 1;
			pstmt.setInt(count++, c.getCrslNo());
			if(c.getCrslName()!=null) {
				pstmt.setString(count++, c.getCrslName());
			}
			if(c.getCrslImgLink()!=null) {
				pstmt.setString(count++, c.getCrslImgLink());
			}
			if(c.getCrslPageLink()!=null) {
				pstmt.setString(count++, c.getCrslPageLink());
			}
			if(c.getCrslWriteDate()!=null) {
				pstmt.setDate(count++, c.getCrslWriteDate());
			}
			if(c.getCrslEndDate()!=null) {
				pstmt.setDate(count++, c.getCrslEndDate());
			}
			if(c.getCrslIntervalMs()>=0) {
				pstmt.setInt(count++, c.getCrslIntervalMs());
			}
			if(c.getCrslViewRank()>=0) {
				pstmt.setInt(count++, c.getCrslViewRank());
			}
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public Carousel getCrslByCrslNo(Connection conn, int seqCrslNo) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Carousel result = null;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("selectCarouselByCrslNo"));
			pstmt.setInt(1, seqCrslNo);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = getCarousel(rs);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	public int updateCarousel(Connection conn, Carousel c) {
		PreparedStatement pstmt = null;
		int result = -1;
		String query = sql.getProperty("updateCarousel");
		StringBuilder cols = new StringBuilder();
		
		if(c.getCrslName()!=null) {
			cols.append("CRSL_NAME = ?,");
		}
		if(c.getCrslImgLink()!=null) {
			cols.append("CRSL_IMG_LINK = ?,");
		}
		if(c.getCrslPageLink()!=null) {
			cols.append("CRSL_PAGE_LINK = ?,");
		}
		if(c.getCrslWriteDate()!=null) {
			cols.append("CRSL_WRITE_DATE = ?,");
		}
		if(c.getCrslEndDate()!=null) {
			cols.append("CRSL_END_DATE = ?,");
		}
		if(c.getCrslIntervalMs()>=0) {
			cols.append("CRSL_INTERVAL_MS = ?,");
		}
		if(c.getCrslViewRank()>=0) {
			cols.append("CRSL_VIEW_RANK = ?,");
		}
		
		query = query.replace("#cols", cols.substring(0, cols.length()-1).toString());
		try {
			pstmt = conn.prepareStatement(query);
			int count = 1;
			if(c.getCrslName()!=null) {
				pstmt.setString(count++, c.getCrslName());
			}
			if(c.getCrslImgLink()!=null) {
				pstmt.setString(count++, c.getCrslImgLink());
			}
			if(c.getCrslPageLink()!=null) {
				pstmt.setString(count++, c.getCrslPageLink());
			}
			if(c.getCrslWriteDate()!=null) {
				pstmt.setDate(count++, c.getCrslWriteDate());
			}
			if(c.getCrslEndDate()!=null) {
				pstmt.setDate(count++, c.getCrslEndDate());
			}
			if(c.getCrslIntervalMs()>=0) {
				pstmt.setInt(count++, c.getCrslIntervalMs());
			}
			if(c.getCrslViewRank()>=0) {
				pstmt.setInt(count++, c.getCrslViewRank());
			}
			pstmt.setInt(count++, c.getCrslNo());
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}
	
	public List<NoticeList> selectNoticeList(Connection conn, String type, String keyword, int cPage, int numPerPage) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<NoticeList> result = new ArrayList<>();
		String query = sql.getProperty("selectNoticeList");
		int startData = (cPage -1)*numPerPage +1;
		int endData = cPage * numPerPage;
		if(type!=null&&keyword!=null) {
			query = query.replace("#where","WHERE "+type+"=?");
		}else {
			query = query.replace("#where", "");
		}
		try{
			pstmt = conn.prepareStatement(query);
			int count = 1;
			if(type!=null&&keyword!=null) {
				pstmt.setString(count++, keyword);
			}
			pstmt.setInt(count++, startData);
			pstmt.setInt(count++, endData);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				result.add(getNoticeList(rs));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}
	
	public int getNoticeTotalData(Connection conn, String type, String keyword) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = -1;
		String query = sql.getProperty("getNoticeTotalData");
		if(type!=null&&keyword!=null) {
			query = query.replace("#where", "WHERE "+type+"= ?");
		}else {
			query = query.replace("#where", "");
		}
		try {
			pstmt = conn.prepareStatement(query);
			if(type!=null&&keyword!=null) {
				pstmt.setString(1, keyword);
			}
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}
	
	public NoticeList getNoticeList(ResultSet rs) throws SQLException{
		return NoticeList.builder().noticeNo(rs.getInt("NOTICE_NO"))
								   .writer(rs.getString("WRITER"))
								   .title(rs.getString("TITLE"))
								   .writeDate(rs.getDate("WRITE_DATE"))
								   .viewCount(rs.getInt("VIEW_COUNT"))
								   .hasFile(rs.getInt("DATACOUNT")>0)
								   .build();
	}

	public Notice getNoticeByNo(Connection conn, int no) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Notice result = null;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("selectNoticeByNo"));
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = getNotice(rs);
				do {
					NoticeAttachFile file = getNoticeFile(rs);
					if(file !=null) {
						result.getAttachFileList().add(file);
					}
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

	private NoticeAttachFile getNoticeFile(ResultSet rs) throws SQLException{
		NoticeAttachFile result = null;
		if(rs.getInt("FILE_NO")>0) {
			result = NoticeAttachFile.builder().noticeFileNo(rs.getInt("FILE_NO"))
												.noticeNo(rs.getInt("NO"))
												.oriName(rs.getString("ORINAME"))
												.reName(rs.getString("RENAME"))
												.build();
		}
		return result;
	}

	private Notice getNotice(ResultSet rs) throws SQLException{
		return Notice.builder().noticeNo(rs.getInt("NO"))
							   .title(rs.getString("TITLE"))
							   .noticeWriter(rs.getString("WRITER"))
							   .content(rs.getString("CONTENT"))
							   .writeDate(rs.getDate("WRITE_DATE"))
							   .viewCount(rs.getInt("VIEW_COUNT"))
							   .build();
	}

	public int addNoticeViewCount(Connection conn, int no) {
		PreparedStatement pstmt = null;
		int result = -1;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("addNoticeViewCount"));
			pstmt.setInt(1, no);
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int insertNotice(Connection conn, Notice notice) {
		PreparedStatement pstmt = null;
		int result = -1;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("insertNotice"));
			pstmt.setInt(1, notice.getNoticeNo());
			pstmt.setString(2, notice.getNoticeWriter());
			pstmt.setString(3, notice.getTitle());
			pstmt.setCharacterStream(4, new StringReader(notice.getContent()), notice.getContent().length());
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int insertNoticeFile(Connection conn, Notice notice) {
		PreparedStatement pstmt = null;
		int[] resultArray = null;
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("insertNoticeFile"));
			for(NoticeAttachFile f :notice.getAttachFileList()) {
				pstmt.setInt(1, notice.getNoticeNo());
				pstmt.setString(2, f.getOriName());
				pstmt.setString(3, f.getReName());
				pstmt.addBatch();
				pstmt.clearParameters();
			}
			resultArray = pstmt.executeBatch();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		if(resultArray!=null&& resultArray.length>0) {
			int temp = 1;
			for(int i:resultArray) {
				if(i==-2) {
					temp *= 1;
				}else if(i==-3) {
					temp *= 0;
				}else {
					temp *=i;
				}
			}
			result = result + temp;
		}
		return result;
	}
	
	public int getNoticeSequence(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = -1;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("getNoticeSequence"));
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

	public int updateNotice(Connection conn, Notice notice) {
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("updateNotice"));
			pstmt.setString(1, notice.getTitle());
			pstmt.setCharacterStream(2, new StringReader(notice.getContent()), notice.getContent().length());
			pstmt.setInt(3, notice.getNoticeNo());
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int deleteNoticeFileByBrdNo(Connection conn, int noticeNo) {
		PreparedStatement pstmt = null;
		int result = -1;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("deleteNoticeFileByBrdNo"));
			pstmt.setInt(1, noticeNo);
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int deleteNoticeFileByFileNo(Connection conn, String[] delFile) {
		PreparedStatement pstmt = null;
		int[] results = null;
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("deleteNoticeFileByFileNo"));
			for(String f : delFile) {
				pstmt.setString(1, f);
				pstmt.addBatch();
			}
			results = pstmt.executeBatch();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		if(results != null && results.length>0) {
			int temp = 1;
			for(int i:results) {
				if(i==-2) {
					temp *= 1;
				}else if(i==-3) {
					temp *= 0;
				}else {
					temp *=i;
				}
			}
			result = result + temp;
		}
		return result;
	}

	public String[] getDelFileList(Connection conn, String[] delFile) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[] result = new String[delFile.length];
		String query = sql.getProperty("getDelFileList");
		
		StringBuilder sb = new StringBuilder();
		sb.append("?");
		sb.append(", ?".repeat(delFile.length-1));
		
		query = query.replace("#vals", sb.toString());
		try {
			pstmt = conn.prepareStatement(sql.getProperty(query));
			for(int i=0; i<delFile.length; i++) {
				pstmt.setString(i+1, delFile[i]);
			}
			rs = pstmt.executeQuery();
			for(int i=0; i<delFile.length; i++) {
				if(rs.next()) {
					result[i] = rs.getString(1);
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	public List<NoticeAttachFile> getDelFIleListByNoticeNo(Connection conn, int noticeNo) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<NoticeAttachFile> result = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql.getProperty("getDelFileListByBrdNo"));
			pstmt.setInt(1, noticeNo);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				result.add(getNoticeAttachFile(rs));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	private NoticeAttachFile getNoticeAttachFile(ResultSet rs) throws SQLException{
		return NoticeAttachFile.builder().reName(rs.getString(1)).build();
	}

	public int deleteNotice(Connection conn, int noticeNo) {
		PreparedStatement pstmt = null;
		int result = -1;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("deleteNotice"));
			pstmt.setInt(1, noticeNo);
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int getQNATotalData(Connection conn, String type, String keyword) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = sql.getProperty("getQNATotalData");
		int result = -1;
		if(type!=null && !type.equals("")) {
			switch(type) {
				case "code": query = query.replace("#cols", "WHERE QST_CODE = ?"); break;
				case "category": query = query.replace("#cols", "WHERE QST_CATEGORY = ?");break;
			}
		}else {
			query = query.replace("#cols", "");
		}
		try {
			pstmt = conn.prepareStatement(query);
			int count = 1;
			if(type!=null && !type.equals("")) {
				pstmt.setString(count++, keyword);
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

	public List<QNAList> selectQNAList(Connection conn, String type, String keyword, int cPage, int numPerPage) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<QNAList> result = new ArrayList<>();
		String query = sql.getProperty("selectQNAList");
		int startData = (cPage -1)*numPerPage +1;
		int endData = cPage * numPerPage;
		if(type!=null && !type.equals("")) {
			switch(type) {
			case "code":query = query.replace("#cols", "WHERE QST_CODE = ?"); break;
			case "category":query = query.replace("#cols", "WHERE QST_CATEGORY = ?");break;
			}
		}else {
			query = query.replace("#cols", "");
		}
		try {
			pstmt = conn.prepareStatement(query);
			int count = 1;
			if(type!=null && !type.equals("")) {
				pstmt.setString(count++, keyword);
			}
			pstmt.setInt(count++, startData);
			pstmt.setInt(count++, endData);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				result.add(getQNAList(rs));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	private QNAList getQNAList(ResultSet rs) throws SQLException{
		return QNAList.builder()
				.qstNo(rs.getInt("QST_NO"))
				.qstWriter(rs.getString("QST_WRITER"))
				.qstCategory(rs.getString("QST_CATEGORY"))
				.qstCode(rs.getString("QST_CODE"))
				.writeDate(rs.getDate("WRITE_DATE"))
				.qstCategoryName(rs.getString("QST_CATEGORY_NAME"))
				.build();
	}

	public Qna getQnaByQnaNo(Connection conn, int no) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Qna result = null;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("selectQNAbyQNAno"));
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = getQna(rs);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	private Qna getQna(ResultSet rs) throws SQLException{
		return Qna.builder()
				.qstNo(rs.getInt("BRD_QST_NO"))
				.qstWriter(rs.getString("BRD_QST_WRITER"))
				.catNo(rs.getString("BRD_QST_CAT_NO"))
				.catName(rs.getString("BRD_ADMIN_CAT_NAME"))
				.processCode(rs.getString("BRD_QST_PROCESS_CODE"))
				.content(rs.getString("BRD_QST_CONTENT"))
				.writeDate(rs.getDate("BRD_QST_WRITE_DATE"))
				.answer(rs.getString("BRD_QST_ANSWER"))
				.answerName(rs.getString("ADMIN_NAME"))
				.answerContent(rs.getString("BRD_QST_ANSWER_CONTENT"))
				.ansDate(rs.getDate("BRD_QST_ANSWER_WRITE_DATE")!=null?rs.getDate("BRD_QST_ANSWER_WRITE_DATE"):new Date(System.currentTimeMillis()))
				.build();
	}

	public List<String[]> getQnaCatList(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String[]> result = new ArrayList<>();
		
		try {
			pstmt = conn.prepareStatement(sql.getProperty("getQnaCatList"));
			rs = pstmt.executeQuery();
			while(rs.next()) {
				result.add(new String[] {rs.getString("BRD_ADMIN_CAT_CODE"),rs.getString("BRD_ADMIN_CAT_NAME")});
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	public List<String[]> getQnaProList(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String[]> result = new ArrayList<>();
		
		try {
			pstmt = conn.prepareStatement(sql.getProperty("getQnaProList"));
			rs = pstmt.executeQuery();
			while(rs.next()) {
				result.add(new String[] {rs.getString("BRD_ADMIN_PROCESS_CODE"),rs.getString("BRD_ADMIN_PROCESS_NAME")});
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	public int updateQNA(Connection conn, Qna qna) {
		PreparedStatement pstmt = null;
		int result = -1;
		System.out.println(qna);
		try {
			pstmt = conn.prepareStatement(sql.getProperty("updateQNA"));
			pstmt.setString(1, qna.getProcessCode());
			pstmt.setString(2, qna.getAnswer());
			pstmt.setString(3, qna.getAnswerContent());
			pstmt.setInt(4, qna.getQstNo());
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int getReportTotalData(Connection conn, String type, String keyword) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = sql.getProperty("getReportTotalData");
		query = query.replace("#where", type!=null?"WHERE "+type+" = ? ":"");
		int result = -1;
		try{
			pstmt = conn.prepareStatement(query);
			if(type!=null) {
				pstmt.setString(1, keyword);
			}
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
		}catch(SQLException e) {
			
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	public List<ReportList> getReportList(Connection conn, String type, String keyword, int cPage, int numPerPage) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ReportList> result = new ArrayList<>();
		String query = sql.getProperty("getReportList");
		int startData = (cPage -1)*numPerPage +1;
		int endData = cPage * numPerPage;
		query = query.replace("#where", type!=null?"WHERE "+type+" = ? ":"");
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
				result.add(getReportList(rs));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	private ReportList getReportList(ResultSet rs) throws SQLException{
		return ReportList.builder()
				.reportNo(rs.getInt("NO"))
				.catCode(rs.getString("CAT_CODE"))
				.proCode(rs.getString("PRO_CODE"))
				.reporter(rs.getString("REPORTER"))
				.reportee(rs.getString("REPORTEE"))
				.reportDate(rs.getDate("REPORT_DATE"))
				.build();
	}

	public Report selectReportByNo(Connection conn, int rptNo) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Report result = null;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("selectReportByNo"));
			pstmt.setInt(1, rptNo);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = getReport(rs);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	private Report getReport(ResultSet rs) throws SQLException{
		return Report.builder()
				.reportNo(rs.getInt("BRD_REPORT_NO"))
				.catCode(rs.getString("BRD_REPORT_CAT_CODE"))
				.catName(rs.getString("BRD_ADMIN_CAT_NAME"))
				.proCode(rs.getString("BRD_REPORT_PROCESS_CODE"))
				.reporter(rs.getString("BRD_REPORT_REPORTER"))
				.reportee(rs.getString("BRD_REPORT_REPORTEE"))
				.content(rs.getString("BRD_REPORT_CONTENT"))
				.reportDate(rs.getDate("BRD_REPORT_DATE"))
				.result(rs.getString("BRD_REPORT_PROCESS_RESULT"))
				.resultDate(rs.getDate("BRD_REPORT_PROCESS_DATE"))
				.build();
	}

	public List<String[]> getReportCatList(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String[]> result = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql.getProperty("getReportCatList"));
			rs = pstmt.executeQuery();
			while(rs.next()) {
				result.add(new String[] {rs.getString("BRD_ADMIN_CAT_CODE"),rs.getString("BRD_ADMIN_CAT_NAME")});
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	public List<String[]> getReportProCode(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String[]> result = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql.getProperty("getReportProList"));
			rs = pstmt.executeQuery();
			while(rs.next()) {
				result.add(new String[]{rs.getString("BRD_ADMIN_PROCESS_CODE"),rs.getString("BRD_ADMIN_PROCESS_NAME")});
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