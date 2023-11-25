package com.harmony.lesson.dao;

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

import com.harmony.lesson.dto.Lesson;
import com.harmony.lesson.dto.LessonApply;
import com.harmony.lesson.dto.LessonComment;
import com.harmony.lesson.dto.SaveLesson;
import com.harmony.model.dto.MemberInfo;

public class LessonDao {
	private Properties sql=new Properties();
	{
		String path=LessonDao.class
				.getResource("/sql/lesson/lesson_sql.properties").getPath();
		try(FileReader fr=new FileReader(path)) {
			sql.load(fr);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
		
		// 검색창에 있는 레슨 다 나오게하기
		public List<Lesson> printLessonAll(Connection conn,int cPage,int numPerpage){
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			List<Lesson> result=new ArrayList<>();
			try {
				pstmt=conn.prepareStatement(sql.getProperty("printLessonAll"));
				pstmt.setInt(1, (cPage-1)*numPerpage+1);
				pstmt.setInt(2, cPage*numPerpage);
				rs=pstmt.executeQuery();
				while(rs.next()) {
					result.add(getLesson(rs));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}return result;
		}
		// 음악으로 필터한 레슨 다 나오게하기
			public List<Lesson> printLessonByFilterInst(Connection conn, String keyword){
				PreparedStatement pstmt=null;
				ResultSet rs=null;
				List<Lesson> result=new ArrayList<>();
				try {
					pstmt=conn.prepareStatement(sql.getProperty("printLessonByFilterInst"));
					pstmt.setString(1, keyword);
					rs=pstmt.executeQuery();
					while(rs.next()) {
						result.add(getLesson(rs));
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}finally {
					close(rs);
					close(pstmt);
				}return result;
			}
			// 장소으로 필터한 레슨 다 나오게하기
			public List<Lesson> printLessonByFilterPlace(Connection conn, String keyword){
				PreparedStatement pstmt=null;
				ResultSet rs=null;
				List<Lesson> result=new ArrayList<>();
				try {
					pstmt=conn.prepareStatement(sql.getProperty("printLessonByFilterPlace"));
					pstmt.setString(1, "%"+keyword+"%");
					rs=pstmt.executeQuery();
					while(rs.next()) {
						result.add(getLesson(rs));
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}finally {
					close(rs);
					close(pstmt);
				}return result;
			}
			// 가격으로 필터한 레슨 다 나오게하기
			public List<Lesson> printLessonByFilterPrice(Connection conn, String keyword){
				PreparedStatement pstmt=null;
				ResultSet rs=null;
				List<Lesson> result=new ArrayList<>();
				try {
					pstmt=conn.prepareStatement(sql.getProperty("printLessonByFilterPrice"));
					pstmt.setString(1, keyword);
					rs=pstmt.executeQuery();
					while(rs.next()) {
						result.add(getLesson(rs));
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}finally {
					close(rs);
					close(pstmt);
				}return result;
			}
			// 시간대로 필터한 레슨 다 나오게하기
			public List<Lesson> printLessonByFilterTime(Connection conn, String keyword){
				PreparedStatement pstmt=null;
				ResultSet rs=null;
				List<Lesson> result=new ArrayList<>();
				try {
					pstmt=conn.prepareStatement(sql.getProperty("printLessonByFilterTime"));
					pstmt.setString(1, keyword);
					pstmt.setString(2, keyword);
					rs=pstmt.executeQuery();
					while(rs.next()) {
						result.add(getLesson(rs));
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}finally {
					close(rs);
					close(pstmt);
				}return result;
			}
			// 제목으로 필터한 레슨 다 나오게하기
				public List<Lesson> printLessonByFilterTitle(Connection conn, String keyword){
					PreparedStatement pstmt=null;
					ResultSet rs=null;
					List<Lesson> result=new ArrayList<>();
					try {
						pstmt=conn.prepareStatement(sql.getProperty("printLessonByFilterTitle"));
						pstmt.setString(1, "%"+keyword+"%");
						rs=pstmt.executeQuery();
						while(rs.next()) {
							result.add(getLesson(rs));
						}
					}catch(SQLException e) {
						e.printStackTrace();
					}finally {
						close(rs);
						close(pstmt);
					}return result;
				}
		// 리뷰순 정렬
		public List<Lesson> printLessonByReviews(Connection conn, int cPage,int numPerpage){
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			List<Lesson> result=new ArrayList<>();
			try {
				pstmt=conn.prepareStatement(sql.getProperty("printLessonByReviews"));
				pstmt.setInt(1, (cPage-1)*numPerpage+1);
				pstmt.setInt(2, cPage*numPerpage);
				rs=pstmt.executeQuery();
				while(rs.next()) {
					result.add(getLesson(rs));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}return result;
		}
		// 별점평균순 정렬
			public List<Lesson> printLessonByStarAvg(Connection conn, int cPage,int numPerpage){
				PreparedStatement pstmt=null;
				ResultSet rs=null;
				List<Lesson> result=new ArrayList<>();
				try {
					pstmt=conn.prepareStatement(sql.getProperty("printLessonByStarAvg"));
					pstmt.setInt(1, (cPage-1)*numPerpage+1);
					pstmt.setInt(2, cPage*numPerpage);
					rs=pstmt.executeQuery();
					while(rs.next()) {
						result.add(getLesson(rs));
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}finally {
					close(rs);
					close(pstmt);
				}return result;
			}
		// 조회수 최근등록순 정렬
		public List<Lesson> printLessonByKeyword(Connection conn, String viewAndRecent,int cPage,int numPerpage){
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			List<Lesson> result=new ArrayList<>();
			String query = sql.getProperty("printLessonByKeyword");
			query=query.replace("#COL", viewAndRecent);
			try {
				pstmt=conn.prepareStatement(query);
				pstmt.setInt(1, (cPage-1)*numPerpage+1);
				pstmt.setInt(2, cPage*numPerpage);
				rs=pstmt.executeQuery();
				while(rs.next()) {
					result.add(getLesson(rs));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}return result;
		}
		// 레슨 숫자가져오기
		public int printLessonCount(Connection conn) {
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			int result=0;
			try {
				pstmt=conn.prepareStatement(sql.getProperty("printLessonCount"));
				rs=pstmt.executeQuery();
				if(rs.next()) result=rs.getInt(1);
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}return result;
		}
		// 번호로 레슨찾기
		public Lesson selectLessonByNo(Connection conn, int no) {
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			Lesson l=null;
			try {
				pstmt=conn.prepareStatement(sql.getProperty("selectLessonByNo"));
				pstmt.setInt(1, no);
				rs=pstmt.executeQuery();
				if(rs.next()) l=getLesson(rs);
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}return l;
		}
		// 게시글번호로 레슨찾기 join사용
			public Lesson selectLessonByNoJoin(Connection conn, int boardNo) {
				PreparedStatement pstmt=null;
				ResultSet rs=null;
				Lesson l=null;
				try {
					pstmt=conn.prepareStatement(sql.getProperty("selectLessonByNoJoin"));
					pstmt.setInt(1, boardNo);
					rs=pstmt.executeQuery();
					//if(rs.next()) l=getLesson(rs);
					if(rs.next()) l=Lesson.builder()
							.teacherNo(rs.getString("teacher_number"))
							.boardNo(rs.getInt("board_no"))
							.build();
				}catch(SQLException e) {
					e.printStackTrace();
				}finally {
					close(rs);
					close(pstmt);
				}return l;
			}
		// 번호로 레슨 요일정보찾기
		public Lesson selectTimeByNo(Connection conn, int no) {
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			Lesson l=null;
			try {
				pstmt=conn.prepareStatement(sql.getProperty("selectTimeByNo"));
				pstmt.setInt(1, no);
				rs=pstmt.executeQuery();
				if(rs.next()) l=getLessonTime(rs);
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}return l;
		}
		
		// lesson 등록
		public int insertLesson(Connection conn, Lesson l) {
			PreparedStatement pstmt=null;
			int result=0;
			try {
				pstmt=conn.prepareStatement(sql.getProperty("insertLesson"));
				pstmt.setString(1, l.getTeacherNo());
				pstmt.setString(2, l.getInstNo());
				pstmt.setString(3, l.getBoardTitle());
				pstmt.setString(4, l.getBoardContent());
				pstmt.setString(5, l.getBoardPlace());
				pstmt.setString(6, l.getBoardPrice());
				pstmt.setString(7, l.getBoardImg());
				result=pstmt.executeUpdate();
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				close(pstmt);
			}return result;
		}
		// lesson 시간, 요일정보 등록
		public int insertLessonTime(Connection conn, Lesson l) {
			PreparedStatement pstmt=null;
			int result=0;
			try {
				pstmt=conn.prepareStatement(sql.getProperty("insertLessonTime"));
				pstmt.setTimestamp(1, l.getLessonStartTime());
				pstmt.setTimestamp(2, l.getLessonEndTime());
				pstmt.setString(3, String.join(",", l.getDay()));
				result=pstmt.executeUpdate();
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				close(pstmt);
			}return result;
		}
		
		// 레슨 상담신청
		public int applyLesson(Connection conn, LessonApply m) {
			PreparedStatement pstmt=null;
			int result=0;
			try {
				pstmt=conn.prepareStatement(sql.getProperty("applyLesson"));
				pstmt.setInt(1, m.getBoardNo());
				pstmt.setString(2, m.getMemNo());
				pstmt.setString(3, m.getApplyPlace());
				pstmt.setInt(4, m.getApplyNumberOfTimes());
				result=pstmt.executeUpdate();
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				close(pstmt);
			}return result;
		}
		public int applyCurrentVal(Connection conn) {
			PreparedStatement pstmt=null;
			ResultSet rs = null;
			int result=0;
			try {
				pstmt=conn.prepareStatement(sql.getProperty("applyCurrentVal"));
				rs = pstmt.executeQuery();
				if(rs.next()) result=rs.getInt(1);
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				close(pstmt);
			}return result;
		}
		public int applyLessonTime(Connection conn, LessonApply m) {
			PreparedStatement pstmt=null;
			int result=0;
			try {
				pstmt=conn.prepareStatement(sql.getProperty("applyLessonTime"));
				pstmt.setTimestamp(1, m.getLessonStartTime());
				pstmt.setTimestamp(2, m.getLessonEndTime());
				pstmt.setString(3, String.join(",", m.getLessonDay()));
				result=pstmt.executeUpdate();
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				close(pstmt);
			}return result;
		}
		
		// lesson 수정
		public int updateLesson(Connection conn, Lesson l) {
			PreparedStatement pstmt=null;
			int result=0;
			try {
				pstmt=conn.prepareStatement(sql.getProperty("updateLesson"));
				pstmt.setString(1, l.getInstNo());
				pstmt.setString(2, l.getBoardTitle());
				pstmt.setString(3, l.getBoardContent());
				pstmt.setString(4, l.getBoardPlace());
				pstmt.setString(5, l.getBoardPrice());
				pstmt.setString(6, l.getBoardImg());
				pstmt.setInt(7, l.getBoardNo());
				result=pstmt.executeUpdate();
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				close(pstmt);
			}return result;
		}
		// lesson 시간, 요일정보 수정
		public int updateLessonTime(Connection conn, Lesson l) {
			PreparedStatement pstmt=null;
			int result=0;
			try {
				pstmt=conn.prepareStatement(sql.getProperty("updateLessonTime"));
				pstmt.setTimestamp(1, l.getLessonStartTime());
				pstmt.setTimestamp(2, l.getLessonEndTime());
				pstmt.setString(3, String.join(",", l.getDay()));
				pstmt.setInt(4, l.getBoardNo());
				result=pstmt.executeUpdate();
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				close(pstmt);
			}return result;
		}
		// lesson 시간, 요일정보 삭제
			public int deleteLessonTime(Connection conn, int no) {
				PreparedStatement pstmt=null;
				int result=0;
				try {
					pstmt=conn.prepareStatement(sql.getProperty("deleteLessonTime"));
					pstmt.setInt(1, no);
					result=pstmt.executeUpdate();
				}catch(SQLException e) {
					e.printStackTrace();
				}finally {
					close(pstmt);
				}return result;
			}
		// lesson 삭제
			public int deleteLesson(Connection conn, int no) {
				PreparedStatement pstmt=null;
				int result=0;
				try {
					pstmt=conn.prepareStatement(sql.getProperty("deleteLesson"));
					pstmt.setInt(1, no);
					result=pstmt.executeUpdate();
				}catch(SQLException e) {
					e.printStackTrace();
				}finally {
					close(pstmt);
				}return result;
			}
		// lesson 조회수 카운트
		public int updateLessonReadCount(Connection conn, int no) {
			PreparedStatement pstmt=null;
			int result=0;
			try {
				pstmt=conn.prepareStatement(sql.getProperty("updateLessonReadCount"));
				pstmt.setInt(1, no);
				result=pstmt.executeUpdate();
				
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				close(pstmt);
			}return result;
		}
		//리뷰가져오기
		public List<LessonApply> selectReviewByNo(Connection conn, int no) {
			PreparedStatement pstmt =null;
			ResultSet rs = null;
			List<LessonApply> l = new ArrayList<>();
			try {
				pstmt=conn.prepareStatement(sql.getProperty("selectReviewByNo"));
				pstmt.setInt(1, no);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					l.add(getLessonApplyReview(rs));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(rs);
				close(pstmt);
			} return l;
		}
		// 리뷰숫자가져오기
		public int selectReviewCountByNo(Connection conn, int no) {
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			int result=0;
			try {
				pstmt=conn.prepareStatement(sql.getProperty("selectReviewCountByNo"));
				pstmt.setInt(1, no);
				rs=pstmt.executeQuery();
				if(rs.next()) result=rs.getInt(1);
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}return result;
		}
		public SaveLesson saveLessonConfirm(Connection conn, int boardNo, String userNo) {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			SaveLesson result = null;
			try {
				pstmt=conn.prepareStatement(sql.getProperty("saveLessonConfirm"));
				pstmt.setInt(1, boardNo);
				pstmt.setString(2, userNo);
				rs=pstmt.executeQuery();
				if(rs.next()) {
					result=SaveLesson.builder()
							.BoardNo(boardNo)
							.memNo(userNo)
							.build();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(rs);
				close(pstmt);
			} return result;
		}
		
		public int saveLesson(Connection conn, int boardNo, String userNo) {
			PreparedStatement pstmt = null;
			int result = 0;
			try {
				pstmt=conn.prepareStatement(sql.getProperty("saveLesson"));
				pstmt.setInt(1, boardNo);
				pstmt.setString(2, userNo);
				result=pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(pstmt);
			} return result;
		}
		public int deleteSavedLesson(Connection conn, int boardNo, String userNo) {
			PreparedStatement pstmt = null;
			int result = 0;
			try {
				pstmt=conn.prepareStatement(sql.getProperty("deleteSavedLesson"));
				pstmt.setInt(1, boardNo);
				pstmt.setString(2, userNo);
				result=pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(pstmt);
			} return result;
		}
		
		
		
		public int insertLessonComment(Connection conn, LessonComment lc) {
			PreparedStatement pstmt = null;
			int result = 0;
			try {
				pstmt = conn.prepareStatement(sql.getProperty("insertLessonComment"));
				pstmt.setInt(1, lc.getReviewNo());
				pstmt.setString(2, lc.getCommentContent());
				//pstmt.setString(5, lc.getCommentRef()==0?null:
				//					String.valueOf(lc.getCommentRef()));
				//문자로들어오면 알아서 숫자로 형변환해서 오라클에 들어간다
				result = pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(pstmt);
			} return result;
			
		}
		
		public List<LessonComment> selectReviewComment(Connection conn, int no){
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<LessonComment> result = new ArrayList<>();
			try {
				pstmt= conn.prepareStatement(sql.getProperty("selectReviewComment"));
//				pstmt.setInt(1, no);
				rs=pstmt.executeQuery();
				while(rs.next()) {
					result.add(
							LessonComment.builder()
							.commentNo(rs.getInt("comment_no"))
							.reviewNo(rs.getInt("lesson_review_no"))
							.commentContent(rs.getString("comment_content"))
							.commentDate(rs.getDate("comment_enroll_date"))
							.build()
							);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(rs);
				close(pstmt);
			} return result;
		}
		
		public int deleteReply(Connection conn,int reviewNo) {
			PreparedStatement pstmt = null;
			int result = 0;
			try {
				pstmt= conn.prepareStatement(sql.getProperty("deleteReply"));
				pstmt.setInt(1, reviewNo);
				result = pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(pstmt);
			} return result;
		}
		
		public MemberInfo selectMemberInfoByTeacherNo(Connection conn, String no) {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			MemberInfo m = null;
			try {
				pstmt= conn.prepareStatement(sql.getProperty("selectMemberInfoByTeacherNo"));
				pstmt.setString(1, no);
				rs=pstmt.executeQuery();
				if(rs.next()) m=getMemberInfo(rs);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(rs);
				close(pstmt);
			} return m;
		}
		// 레슨게시글 강사테이블조인해서 회원번호 가져오기
		public Lesson applyFindMemNo(Connection conn,String no){
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			Lesson result=null;
			try {
				pstmt=conn.prepareStatement(sql.getProperty("applyFindMemNo"));
				pstmt.setString(1, no);
				rs=pstmt.executeQuery();
				if(rs.next()) {
					result = Lesson.builder()
							.memNo(rs.getString("MEM_NO"))
							.build();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}return result;
		}
		public LessonApply showApplyInfoByNo(Connection conn,String memNo){
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			LessonApply result=null;
			try {
				pstmt=conn.prepareStatement(sql.getProperty("showApplyInfoByNo"));
				pstmt.setString(1, memNo);
				rs=pstmt.executeQuery();
				if(rs.next()) {
					result = getLessonApply(rs);
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}return result;
		}
		// 레슨신청정보와 레슨신청일시 테이블을 조인하여 레슨신청정보가져오기 멤버넘버로찾기
		public LessonApply showApplyInfo(Connection conn,String memNo){
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			LessonApply result=null;
			try {
				pstmt=conn.prepareStatement(sql.getProperty("showApplyInfo"));
				pstmt.setString(1, memNo);
				rs=pstmt.executeQuery();
				if(rs.next()) {
					result = getLessonApply(rs);
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}return result;
		}
		public LessonApply showApplyBtn(Connection conn,String memNo, int boardNo){
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			LessonApply result=null;
			try {
				pstmt=conn.prepareStatement(sql.getProperty("showApplyBtn"));
				pstmt.setString(1, memNo);
				pstmt.setInt(2, boardNo);
				rs=pstmt.executeQuery();
				if(rs.next()) {
					result = getLessonApply(rs);
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}return result;
		}
		
		private Lesson getLesson(ResultSet rs) throws SQLException {
			return Lesson.builder()
					.boardNo(rs.getInt("board_no"))
					.teacherNo(rs.getString("teacher_number"))
					.instNo(rs.getString("inst_no"))
					.boardTitle(rs.getString("board_title"))
					.boardContent(rs.getString("board_content"))
					.boardDate(rs.getTimestamp("board_date"))
					.boardView(rs.getInt("board_view_count"))
					.boardPlace(rs.getString("board_place"))
					.boardPrice(rs.getString("board_price"))
					.boardImg(rs.getString("board_image"))
					.boardDeadline(rs.getString("board_deadline").charAt(0))
					.viewCnt(rs.getInt("cnt"))
					.reviewPoint(rs.getDouble("avg"))
					.build();
		}
		private Lesson getLessonTime(ResultSet rs) throws SQLException {
			return Lesson.builder()
					.lessonStartTime(rs.getTimestamp("lesson_start_time"))
					.lessonEndTime(rs.getTimestamp("lesson_end_time"))
					.day(rs.getString("lesson_day_of_week").split(","))
					.build();
		}
		
		private LessonApply getLessonApply(ResultSet rs) throws SQLException {
			return LessonApply.builder()
					.applyNo(rs.getInt("apply_no"))
					.boardNo(rs.getInt("board_no"))
					.memNo(rs.getString("mem_no"))
					.applyPlace(rs.getString("apply_place"))
					.applyNumberOfTimes(rs.getInt("apply_number_of_times"))
					.applyDate(rs.getDate("apply_date"))
					.applyAccept(rs.getString("apply_accept").charAt(0))
					.lessonStartTime(rs.getTimestamp("LESSON_START_TIME"))
					.lessonEndTime(rs.getTimestamp("LESSON_END_TIME"))
					.lessonDay(rs.getString("LESSON_DAY_OF_WEEK").split(","))
					.build();
		}
		private LessonApply getLessonApplyReview(ResultSet rs) throws SQLException {
			return LessonApply.builder()
					.boardNo(rs.getInt("board_no"))
					.reviewNo(rs.getInt("review_no"))
					.memNo(rs.getString("mem_no"))
					.reviewPoint(rs.getInt("lesson_review_point"))
					.review(rs.getString("lesson_review"))
					.reviewEnrollDate(rs.getDate("review_enroll_date"))
					.build();
		}
		
		public static MemberInfo getMemberInfo(ResultSet rs) throws SQLException{
			return MemberInfo.builder()
					.teacherNo(rs.getString("TEACHER_NUMBER"))
				    .memNo(rs.getString("MEM_NO"))
					.activityArea(rs.getString("MEM_INFO_ACTIVITY_AREA"))
					.introduce(rs.getString("MEM_INFO_INTRODUCE"))
					.profilPhoto(rs.getString("MEM_INFO_PROFIL_PHOTO"))
					.school(rs.getString("MEM_INFO_SCHOOL"))
					.department(rs.getString("MEM_INFO_DEPARTMENT"))
					.schoolState(rs.getString("MEM_INFO_SCHOOL_STATE"))
					.name(rs.getString("MEM_INFO_NAME"))
					.gender(rs.getString("MEM_INFO_GENDER"))
					.age(rs.getInt("MEM_INFO_AGE"))
					.email(rs.getString("MEM_INFO_EMAIL"))
					.memCareer(rs.getString("MEM_INFO_CAREER"))
					.build();
		}
	
}
