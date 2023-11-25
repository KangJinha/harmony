package com.harmony.board.model.service;

import static com.harmony.common.JDBCTemplate.close;
import static com.harmony.common.JDBCTemplate.commit;
import static com.harmony.common.JDBCTemplate.getConnection;
import static com.harmony.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import com.harmony.board.info.model.dto.InfoBoard;
import com.harmony.board.info.model.dto.InfoCommentBoard;
import com.harmony.board.model.dao.InfoBoardDao;

public class InfoBoardService {
	private InfoBoardDao dao = new InfoBoardDao();

	public List<InfoBoard> selectBoard(int cPage, int numPerpage) {
		Connection conn = getConnection();
		// DB에 연결하는 코드
		List<InfoBoard> result = dao.selectBoard(conn, cPage, numPerpage);
		// dao의 selectBoard메소드(글을 5개씩 보여주기)를 실행해서 result에 저장
		close(conn);
		return result;
	}

	public int selectBoardCount() {
		Connection conn = getConnection();
		int result = dao.selectBoardCount(conn);
		// dao의 selectBoardCount메소드(갯수)를 실행해서 result에 저장
		close(conn);
		return result;
	}

	public List<InfoBoard> selectBoardByCategoryTagRegion(String category, String tag, String region, int cPage,
			int numPerpage) {
		Connection conn = getConnection();
		List<InfoBoard> result = dao.selectBoardByCategoryTagRegion(conn, category, tag, region, cPage, numPerpage);
		close(conn);
		return result;
	}

	public int selectBoardCountByCategoryTagRegion(String category, String tag, String region) {
		Connection conn = getConnection();
		int result = dao.selectBoardCountByCategoryTagRegion(conn, category, tag, region);
		close(conn);
		return result;
	}

	public int insertBoard(InfoBoard b) {
		Connection conn = getConnection();
		int result = dao.insertBoard(conn, b);
		if (result > 0)
			commit(conn);
		else
			rollback(conn);
		close(conn);
		return result;
	}

	public InfoBoard selectBoardByNo(int no) {
		Connection conn = getConnection();
		InfoBoard b = dao.selectBoardByNo(conn, no);
		close(conn);
		return b;
	}

	public int insertBoardComment(InfoCommentBoard bc) {
		Connection conn = getConnection();
		int result = dao.insertBoardComment(conn, bc);
		if (result > 0)
			commit(conn);
		else
			rollback(conn);
		close(conn);
		return result;
	}

	public List<InfoCommentBoard> selectBoardComments(int boardNo) {
		Connection conn = getConnection();
		List<InfoCommentBoard> comments = dao.selectBoardComments(conn, boardNo);
		close(conn);
		return comments;
	}

	public int deleteInfoBoard(int boardNo) {
        Connection conn = getConnection();
        int result = dao.deleteInfoBoard(conn, boardNo);
        if (result > 0) commit(conn);
        else rollback(conn);
        close(conn);
        return result;
    }

	public int deleteInfoComment(int commentNo) {
        Connection conn = getConnection();
        int result = dao.deleteInfoComment(conn, commentNo);
        if (result > 0) commit(conn);
        else rollback(conn);
        close(conn);
        return result;
    }
	
	public List<InfoBoard> searchBoards(String searchType, String query, int cPage, int numPerPage) {
        Connection conn = getConnection();
        List<InfoBoard> results = dao.searchInfoBoards(conn, searchType, query, cPage, numPerPage);
        close(conn);
        return results;
    }
	
	public int getSearchResultCount(String searchType, String query) {
        Connection conn = getConnection();
        int count = dao.getSearchResultCount(conn, searchType, query);
        close(conn);
        return count;
    }
	
	public String selectCategoryNameByNo(String no) {
	    Connection conn = getConnection();
	    String categoryName = dao.selectCategoryNameByNo(conn, no);
	    close(conn);
	    return categoryName;
	}
	
	public String selectTagNameByNo(String no) {
        Connection conn = getConnection();
        String tagName = dao.selectTagNameByNo(conn, no);
        close(conn);
        return tagName;
    }
	
	public int getCommentCount(int boardNo) {
        Connection conn = getConnection();
        int commentCount = dao.selectBoardCommentCount(conn, boardNo);
        close(conn);
        return commentCount;
    }
	
	 public int updateBoard(InfoBoard b) {
	        Connection conn = getConnection();
	        int result = dao.updateBoard(conn, b);
	        if(result > 0) {
	            commit(conn);
	        } else {
	            rollback(conn);
	        }
	        close(conn);
	        return result;
	    }
	 
	 public List<InfoBoard> selectPopularBoards() {
	        Connection conn = getConnection();
	        List<InfoBoard> popularBoards = dao.selectPopularBoard(conn);
	        close(conn);
	        return popularBoards;
	    }


}
