package com.harmony.board.model.dao;

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

import com.harmony.board.free.model.dto.FreeBoard;
import com.harmony.board.free.model.dto.FreeCommentBoard;
import com.harmony.board.info.model.dto.InfoBoard;

public class FreeBoardDao {
	
	private Properties sql = new Properties();
	{
		String path = InfoBoardDao.class.getResource("/board/board_sql.properties").getPath();
		try (FileReader fr = new FileReader(path)) {
			sql.load(fr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public List<FreeBoard> selectFreeBoard(Connection conn, int cPage, int numPerPage, String sortOption) {
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    List<FreeBoard> result = new ArrayList<>();
	    String sqlQuery = sql.getProperty("selectFreeBoard"); 

	    if (sortOption.equals("oldest")) {
	        sqlQuery = sql.getProperty("selectFreeBoardOldest");
	    } else if (sortOption.equals("views")) {
	        sqlQuery = sql.getProperty("selectFreeBoardByViews");
	    } else if (sortOption.equals("comments")) {
	        sqlQuery = sql.getProperty("selectFreeBoardByComments");
	    }

	    try {
	        pstmt = conn.prepareStatement(sqlQuery);
	        pstmt.setInt(1, (cPage - 1) * numPerPage + 1);
	        pstmt.setInt(2, cPage * numPerPage);
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            result.add(getFreeBoard(rs));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(rs);
	        close(pstmt);
	    }
	    return result;
	}
	
	public int selectFreeBoardCount(Connection conn) {
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    int result = 0;
	    try {
	        pstmt = conn.prepareStatement(sql.getProperty("selectFreeBoardCount"));
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            result = rs.getInt(1); 
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(rs);
	        close(pstmt);
	    }
	    return result;
	    //자유게시판의 전체 게시글 수를 조회
	}


	public FreeBoard selectFreeBoardByNo(Connection conn, int no) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        FreeBoard freeBoard = null;
        try {
            pstmt = conn.prepareStatement(sql.getProperty("selectFreeBoardByNo"));
            pstmt.setInt(1, no);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                freeBoard = getFreeBoard(rs); 
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }
        
        return freeBoard;
        //특정 번호(no)의 게시글을 조회
    }
	
	
	
	public int insertFreeBoard(Connection conn, FreeBoard b) {
		PreparedStatement pstmt=null;
		int result=0;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("insertFreeBoard"));
	        pstmt.setString(1, b.getFreBrdWriter());  
	        pstmt.setString(2, b.getFreBrdTitle());  
	        pstmt.setString(3, b.getFreBrdContent());  
	        pstmt.setString(4, b.getFreBrdTitleImg());
	        
	        result=pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}return result;
		//새로운 게시글을 데이터베이스에 삽입하는 메소드
	}

	public int insertFreeBoardComment(Connection conn, FreeCommentBoard fc) {
	    PreparedStatement pstmt = null;
	    int result = 0;
	    try {
	        pstmt = conn.prepareStatement(sql.getProperty("insertFreeBoardComment"));
	        pstmt.setInt(1, fc.getFreBrdNo());
	        pstmt.setString(2, fc.getFreComWriter());
	        pstmt.setString(3, fc.getFreComContent());
	        if (fc.getFreComRefNo() != null) {
	            pstmt.setInt(4, fc.getFreComRefNo());
	        } else {
	            pstmt.setNull(4, java.sql.Types.INTEGER);
	        }
	        pstmt.setInt(5, fc.getFreComLevel());
	        
	        result = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(pstmt);
	    }
	    return result;
	}
	
	
	public List<FreeCommentBoard> selectFreeBoardComments(Connection conn, int boardNo) {
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    List<FreeCommentBoard> comments = new ArrayList<>();

	    try {
	        pstmt = conn.prepareStatement(sql.getProperty("selectFreeBoardComment")); 
	        pstmt.setInt(1, boardNo);
	        rs = pstmt.executeQuery();
	        
	        while(rs.next()) {
	            FreeCommentBoard comment = getFreeComment(rs);
	            comments.add(comment);
	        }
	    } catch(SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(rs);
	        close(pstmt);
	    }
	    
	    return comments;
	}
	public List<FreeBoard> searchFreeBoards(Connection conn, String searchType, String query, int cPage, int numPerPage) {
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    List<FreeBoard> results = new ArrayList<>();
	    String sqlQuery = "";

	    int startRow = (cPage - 1) * numPerPage + 1;
	    int endRow = cPage * numPerPage;

	    switch (searchType) {
	        case "title":
	            sqlQuery = sql.getProperty("searchFreeBoardTitle");
	            break;
	        case "content":
	            sqlQuery = sql.getProperty("searchFreeBoardContent");
	            break;
	        case "both":
	            sqlQuery = sql.getProperty("searchFreeBoardBoth");
	            break;
	    }

	    try {
	        pstmt = conn.prepareStatement(sqlQuery);
	        pstmt.setString(1, "%" + query + "%");
	        if ("both".equals(searchType)) {
	            pstmt.setString(2, "%" + query + "%");
	            pstmt.setInt(3, startRow);
	            pstmt.setInt(4, endRow);
	        } else {
	            pstmt.setInt(2, startRow);
	            pstmt.setInt(3, endRow);
	        }

	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            results.add(getFreeBoard(rs));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(rs);
	        close(pstmt);
	    }
	    return results;
	}
	public int getSearchResultCount(Connection conn, String searchType, String query) {
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    int count = 0;

	    try {
	        String sqlKey = "";
	        switch (searchType) {
	            case "title":
	                sqlKey = "countSearchFreeBoardTitle";
	                break;
	            case "content":
	                sqlKey = "countSearchFreeBoardContent";
	                break;
	            case "both":
	                sqlKey = "countSearchFreeBoardBoth";
	                break;
	        }

	        String sqlQuery = sql.getProperty(sqlKey);
	        pstmt = conn.prepareStatement(sqlQuery);
	        pstmt.setString(1, "%" + query + "%");
	        if ("both".equals(searchType)) {
	            pstmt.setString(2, "%" + query + "%");
	        }

	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            count = rs.getInt(1);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(rs);
	        close(pstmt);
	    }

	    return count;
	}
	
	public int updateFreeBoard(Connection conn, FreeBoard b) {
        PreparedStatement pstmt=null;
        int result=0;
        try {
            pstmt=conn.prepareStatement(sql.getProperty("updateFreeBoard"));
            pstmt.setString(1, b.getFreBrdTitle());
            pstmt.setString(2, b.getFreBrdContent());
            pstmt.setString(3, b.getFreBrdTitleImg());
            pstmt.setInt(4, b.getFreBrdNo());
            result=pstmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return result;
    }
	
	public int updateBoardReadCount(Connection conn, int no) {
		PreparedStatement pstmt=null;
		int result=0;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("updateFreeBoardReadcount"));
			pstmt.setInt(1, no);
			result=pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}return result;
	}
	
	public int deleteFreeBoard(Connection conn, int boardNo) {
	    PreparedStatement pstmt = null;
	    int result = 0;
	    try {
	        pstmt = conn.prepareStatement(sql.getProperty("deleteFreeBoardComment"));
	        pstmt.setInt(1, boardNo);
	        pstmt.executeUpdate();

	        close(pstmt); 

	        pstmt = conn.prepareStatement(sql.getProperty("deleteFreeBorad"));
	        pstmt.setInt(1, boardNo);
	        result = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(pstmt); 
	    }
	    return result;
	}
	
	public int deleteFreeComment(Connection conn, int commentNo) {
	    PreparedStatement pstmt = null;
	    int result = 0;
	    try {
	        pstmt = conn.prepareStatement(sql.getProperty("deleteFreeComment"));
	        pstmt.setInt(1, commentNo);
	        result = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(pstmt);
	    }
	    return result;
	}
	public int selectFreeBoardCommentCount(Connection conn, int boardNo) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int commentCount = 0;
        try {
            pstmt = conn.prepareStatement(sql.getProperty("selectFreeBoardCommentCount"));
            pstmt.setInt(1, boardNo);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                commentCount = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }
        return commentCount;
    }
	
	
	

	
	
	private FreeBoard getFreeBoard(ResultSet rs) throws SQLException {
	    return FreeBoard.builder()
	            .freBrdNo(rs.getInt("FRE_BRD_NO"))
	            .freBrdWriter(rs.getString("FRE_BRD_WRITER"))
	            .freBrdTitle(rs.getString("FRE_BRD_TITLE"))
	            .freBrdContent(rs.getString("FRE_BRD_CONTENT"))
	            .freBrdTitleImg(rs.getString("FRE_BRD_TITLE_IMG"))
	            .freBrdDate(rs.getDate("FRE_BRD_DATE"))
	            .freBrdViews(rs.getInt("FRE_BRD_VIEWS"))
	            .build();
	}
	//esultSet으로부터 데이터를 읽어와 FreeBoard 객체를 생성하는 메소드
	
	private FreeCommentBoard getFreeComment(ResultSet rs) throws SQLException {
	    Integer freComRefNo = rs.getObject("FRE_COM_REF_NO", Integer.class); 
	    return FreeCommentBoard.builder()
	            .freComNo(rs.getInt("FRE_COM_NO"))
	            .freBrdNo(rs.getInt("FRE_BRD_NO"))
	            .freComWriter(rs.getString("FRE_COM_WRITER"))
	            .freComContent(rs.getString("FRE_COM_CONTENT"))
	            .freComDate(rs.getTimestamp("FRE_COM_DATE"))
	            .freComRefNo(freComRefNo)
	            .freComLevel(rs.getInt("FRE_COM_LEVEL"))
	            .build();
	
  }
	
}
	

