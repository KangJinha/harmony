package com.harmony.board.model.service;

import static com.harmony.common.JDBCTemplate.close;
import static com.harmony.common.JDBCTemplate.commit;
import static com.harmony.common.JDBCTemplate.getConnection;
import static com.harmony.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import com.harmony.board.free.model.dto.FreeBoard;
import com.harmony.board.free.model.dto.FreeCommentBoard;
import com.harmony.board.info.model.dto.InfoBoard;
import com.harmony.board.model.dao.FreeBoardDao;

public class FreeBoardService {
    private FreeBoardDao dao = new FreeBoardDao();

    public List<FreeBoard> selectFreeBoard(int cPage, int numPerPage, String sortOption) {
        Connection conn = getConnection();
        List<FreeBoard> result = dao.selectFreeBoard(conn, cPage, numPerPage, sortOption);
        close(conn);
        return result;
    } 
    
    public int selectFreeBoardCount() {
        Connection conn = getConnection();
        int result = dao.selectFreeBoardCount(conn);
      //dao의 selectFreeBoardCount메소드(갯수)를 실행해서 result에 저장
        close(conn);
        return result;
    }

    public FreeBoard selectFreeBoardByNo(int no) {
        Connection conn = getConnection();
        FreeBoard b = dao.selectFreeBoardByNo(conn, no);
        close(conn);
        return b;
    }
    
    public int insertFreeBoard(FreeBoard b) {
        Connection conn = getConnection();
        int result = dao.insertFreeBoard(conn, b);
        if(result > 0) {
            commit(conn);
        } else {
            rollback(conn);
        }
        close(conn);
        return result;
    }
    
    public FreeBoard selectBoardByNo(int no,boolean readResult) {
	    Connection conn = getConnection();
	    FreeBoard b = dao.selectFreeBoardByNo(conn, no);

	    if(b!=null&&readResult) {
			int result=dao.updateBoardReadCount(conn,no);
			
			if(result>0) { 
				commit(conn);
				b.setFreBrdViews(b.getFreBrdViews()+1);
			}
			else rollback(conn);
		}
		close(conn);
		return b;
	}
    
    public int insertFreeBoardComment(FreeCommentBoard fc) {
        Connection conn = getConnection();
        int result = dao.insertFreeBoardComment(conn, fc);
        if (result > 0) commit(conn);
        else rollback(conn);
        close(conn);
        return result;
    }
    
    public List<FreeCommentBoard> selectFreeBoardComments(int boardNo) {
        Connection conn = getConnection();
        List<FreeCommentBoard> comments = dao.selectFreeBoardComments(conn, boardNo);
        close(conn);
        return comments;
    }
    //특정 게시판의 댓글 목록을 가져오는 것
    
    
    public int getSearchResultCount(String searchType, String query) {
        Connection conn = getConnection();
        int count = dao.getSearchResultCount(conn, searchType, query);
        close(conn);
        return count;
    }
    
	public int deleteFreeBoard(int boardNo) {
        Connection conn = getConnection();
        int result = dao.deleteFreeBoard(conn, boardNo);
        if (result > 0) commit(conn);
        else rollback(conn);
        close(conn);
        return result;
    }

	public int deleteFreeComment(int commentNo) {
        Connection conn = getConnection();
        int result = dao.deleteFreeComment(conn, commentNo);
        if (result > 0) commit(conn);
        else rollback(conn);
        close(conn);
        return result;
    }
	public List<FreeBoard> searchBoards(String searchType, String query, int cPage, int numPerPage) {
        Connection conn = getConnection();
        List<FreeBoard> results = dao.searchFreeBoards(conn, searchType, query, cPage, numPerPage);
        close(conn);
        return results;
    }
	public int getCommentCount(int boardNo) {
        Connection conn = getConnection();
        int commentCount = dao.selectFreeBoardCommentCount(conn, boardNo);
        close(conn);
        return commentCount;
    }
	public int updateFreeBoard(FreeBoard b) {
        Connection conn = getConnection();
        int result = dao.updateFreeBoard(conn, b);
        if(result > 0) {
            commit(conn);
        } else {
            rollback(conn);
        }
        close(conn);
        return result;
    }

}
