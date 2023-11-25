package com.harmony.lesson.service;

import static com.harmony.common.JDBCTemplate.close;
import static com.harmony.common.JDBCTemplate.commit;
import static com.harmony.common.JDBCTemplate.getConnection;
import static com.harmony.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import com.harmony.lesson.dao.LessonDao;
import com.harmony.lesson.dto.Lesson;
import com.harmony.lesson.dto.LessonApply;
import com.harmony.lesson.dto.LessonComment;
import com.harmony.lesson.dto.SaveLesson;
import com.harmony.model.dto.MemberInfo;
import com.harmony.payment.model.dao.PaymentDao;


public class LessonService {
	private LessonDao dao=new LessonDao();
	
	//모든 레슨정보 출력
		public List<Lesson> printLessonAll(int cPage,int numPerpage){
			Connection conn=getConnection();
			List<Lesson> result=dao.printLessonAll(conn, cPage, numPerpage);
			close(conn);
			return result;
		}
	
	// 악기로 필터한 레슨정보 출력
	public List<Lesson> printLessonByFilterInst(String keyword){
		Connection conn=getConnection();
		List<Lesson> result=dao.printLessonByFilterInst(conn, keyword);
		close(conn);
		return result;
	}
	// 장소로 필터한 레슨정보 출력
	public List<Lesson> printLessonByFilterPlace(String keyword){
		Connection conn=getConnection();
		List<Lesson> result=dao.printLessonByFilterPlace(conn, keyword);
		close(conn);
		return result;
	}
	public List<Lesson> printLessonByFilterPrice(String keyword){
		Connection conn=getConnection();
		List<Lesson> result=dao.printLessonByFilterPrice(conn, keyword);
		close(conn);
		return result;
	}
	public List<Lesson> printLessonByFilterTime(String keyword){
		Connection conn=getConnection();
		List<Lesson> result=dao.printLessonByFilterTime(conn, keyword);
		close(conn);
		return result;
	}
	// 제목검색으로 필터
	public List<Lesson> printLessonByFilterTitle(String keyword){
		Connection conn=getConnection();
		List<Lesson> result=dao.printLessonByFilterTitle(conn, keyword);
		close(conn);
		return result;
	}
	// 리뷰순정렬
	public List<Lesson> printLessonByReviews(int cPage,int numPerpage){
		Connection conn=getConnection();
		List<Lesson> result=dao.printLessonByReviews(conn, cPage, numPerpage);
		close(conn);
		return result;
	}
	// 별점평균순정렬
	public List<Lesson> printLessonByStarAvg(int cPage,int numPerpage){
		Connection conn=getConnection();
		List<Lesson> result=dao.printLessonByStarAvg(conn, cPage, numPerpage);
		close(conn);
		return result;
	}
	public List<Lesson> printLessonByKeyword(String viewAndRecent,int cPage,int numPerpage){
		Connection conn=getConnection();
		List<Lesson> result=dao.printLessonByKeyword(conn, viewAndRecent, cPage, numPerpage);
		close(conn);
		return result;
	}
	public int printLessonCount() {
		Connection conn=getConnection();
		int result=dao.printLessonCount(conn);
		close(conn);
		return result;
	}
	// 게시글번호로 레슨찾기
	public Lesson selectLessonByNo(int no) {
		Connection conn=getConnection();
		Lesson l = dao.selectLessonByNo(conn, no);
		close(conn);
		return l;
	}
	// 게시글번호로 레슨찾기 join 레슨게시글 강사
	public Lesson selectLessonByNoJoin(int boardNo) {
		Connection conn=getConnection();
		Lesson l = dao.selectLessonByNoJoin(conn, boardNo);
		close(conn);
		return l;
	}
	
	// 레슨 번호로 레슨정보가져오기 && 조회수출력
	public Lesson selectLessonByNo(int no, boolean readResult) {
		Connection conn=getConnection();
		Lesson l = dao.selectLessonByNo(conn, no);
		if(l!=null && !readResult) {
			int result = dao.updateLessonReadCount(conn, no);
			
			if(result>0) {
				commit(conn);
				l.setBoardView(l.getBoardView()+1);
			}
		}
		close(conn);
		return l;
	}
	// 레슨 신청 정보 번호로 리뷰가져오기
	public List<LessonApply> selectReviewByNo(int no) {
		Connection conn = getConnection();
		List<LessonApply> l = dao.selectReviewByNo(conn,no);
		close(conn);
		return l;
	}
	// 리뷰개수
	public int selectReviewCountByNo(int no) {
		Connection conn=getConnection();
		int result=dao.selectReviewCountByNo(conn, no);
		close(conn);
		return result;
	}
	
	public Lesson selectTimeByNo(int no) {
		Connection conn=getConnection();
		Lesson result=dao.selectTimeByNo(conn, no);
		close(conn);
		return result;
	}
	//레슨 신청하기
	public int applyLesson(LessonApply member) {
		Connection conn=getConnection();
		int result=dao.applyLesson(conn, member);
		int result2=dao.applyLessonTime(conn, member);
		int result3 = dao.applyCurrentVal(conn);
		
		if(result>0 && result2>0 && result3>0) {
			commit(conn);
			result3 = PaymentDao.getDao().getCurrval(conn);
			// 신청정보의 currval 가져오기
		}
		else rollback(conn);
		close(conn);
		return result3;
	}
	
	public int insertLesson(Lesson l) {
		Connection conn=getConnection();
		int result=dao.insertLesson(conn, l);
		// 1. 시퀀스가져오기
		// 2. CURRVAL사용하기
		int result2=dao.insertLessonTime(conn,l);
		
		if(result>0 && result2>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	public int updateLesson(Lesson l) {
		Connection conn=getConnection();
		int result=dao.updateLesson(conn, l);
		// 1. 시퀀스가져오기
		// 2. CURRVAL사용하기
		int result2=dao.updateLessonTime(conn,l);
		
		if(result>0 && result2>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	public int deleteLesson(int no) {
		Connection conn=getConnection();
		int result2=dao.deleteLessonTime(conn, no);
		int result=dao.deleteLesson(conn, no);
		
		if(result2>0 && result>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	// 찜이되었는지 확인
	public SaveLesson saveLessonConfirm(int boardNo, String userNo){
		Connection conn=getConnection();
		SaveLesson result = dao.saveLessonConfirm(conn,boardNo,userNo);
		close(conn);
		return result;
	}
	// 찜하기
	public int saveLesson(int boardNo, String userNo){
		Connection conn=getConnection();
		int result = dao.saveLesson(conn,boardNo,userNo);
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	// 찜삭제
	public int deleteSavedLesson(int boardNo, String userNo){
		Connection conn=getConnection();
		int result = dao.deleteSavedLesson(conn,boardNo,userNo);
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	
	public int insertLessonComment(LessonComment lc) {
		Connection conn = getConnection();
		int result= dao.insertLessonComment(conn,lc);
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	
	public List<LessonComment> selectReviewComment(int no) {
		Connection conn = getConnection();
		List<LessonComment> list = dao.selectReviewComment(conn, no);
		close(conn);
		return list;
	}
	
	public int deleteReply(int reviewNo) {
		Connection conn=getConnection();
		int result=dao.deleteReply(conn, reviewNo);
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	
	public MemberInfo selectMemberInfoByTeacherNo(String no) {
		Connection conn=getConnection();
		MemberInfo result=dao.selectMemberInfoByTeacherNo(conn, no);
		close(conn);
		return result;
	}
	
	// 선생님번호로 멤버번호찾기
	public Lesson applyFindMemNo(String no) {
		Connection conn=getConnection();
		Lesson result=dao.applyFindMemNo(conn, no);
		close(conn);
		return result;
	}
	// 레슨신청정보와 레슨신청일시 테이블을 조인하여 레슨신청정보가져오기
	public LessonApply showApplyInfo(String memNo) {
		Connection conn=getConnection();
		LessonApply result=dao.showApplyInfo(conn, memNo);
		close(conn);
		return result;
	}
	public LessonApply showApplyInfoByNo(String memNo) {
		Connection conn=getConnection();
		LessonApply result=dao.showApplyInfoByNo(conn, memNo);
		close(conn);
		return result;
	}
	public LessonApply showApplyBtn(String memNo, int boardNo) {
		Connection conn=getConnection();
		LessonApply result=dao.showApplyBtn(conn, memNo, boardNo);
		close(conn);
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
}
