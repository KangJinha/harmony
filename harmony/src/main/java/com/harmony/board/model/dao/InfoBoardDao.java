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

import com.harmony.board.info.model.dto.InfoBoard;
import com.harmony.board.info.model.dto.InfoCommentBoard;



public class InfoBoardDao {
	private Properties sql = new Properties();
	{
		String path = InfoBoardDao.class.getResource("/board/board_sql.properties").getPath();
		try (FileReader fr = new FileReader(path)) {
			sql.load(fr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<InfoBoard> selectBoard(Connection conn, int cPage, int numPerpage) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<InfoBoard> result = new ArrayList<>();
		try {
			pstmt = conn.prepareCall(sql.getProperty("selectBoard"));
			// 전체글을 5개씩 보는거, 최신등록순 정렬
			pstmt.setInt(1, (cPage - 1) * numPerpage + 1);
			// cpage 1일떄 1
			// cpage 2일떄 6
			pstmt.setInt(2, cPage * numPerpage);
			// 1 * 5 5
			// 2 * 5 10
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result.add(getBoard(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}
	// 이 코드는 전체글을 5개씩 보여주는 코드 (result에 저장하고 반환하는 코드)

	public int selectBoardCount(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("selectBoardCount"));
			rs = pstmt.executeQuery();
			if (rs.next())
				result = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}
	//이코드는 게시글 갯수를 카운트하는 코드
	
	public List<InfoBoard> selectBoardByCategoryTagRegion(Connection conn, String category, String tag, String region, int cPage, int numPerpage) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<InfoBoard> result = new ArrayList<>();
		try {
			pstmt = conn.prepareCall(sql.getProperty("selectBoardByCategoryTagRegion"));
			pstmt.setString(1, category);
			pstmt.setString(2, category);
			pstmt.setString(3, tag);
			pstmt.setString(4, tag);
			pstmt.setString(5, region);
			pstmt.setString(6, region);
			pstmt.setInt(7, (cPage - 1) * numPerpage + 1);
			pstmt.setInt(8, cPage * numPerpage);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result.add(getBoard(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	public int selectBoardCountByCategoryTagRegion(Connection conn, String category, String tag, String region) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int result = 0;
		try {
			pstmt = conn.prepareCall(sql.getProperty("selectBoardCountByCategoryTagRegion"));
			pstmt.setString(1, category);
			pstmt.setString(2, category);
			pstmt.setString(3, tag);
			pstmt.setString(4, tag);
			pstmt.setString(5, region);
			pstmt.setString(6, region);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				result = rset.getInt(1);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return result;
	}


	
	public int insertBoard(Connection conn, InfoBoard b) {
		PreparedStatement pstmt=null;
		int result=0;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("insertBoard"));
	        pstmt.setString(1, b.getInfBrdWriter());  
	        pstmt.setString(2, b.getInfBrdTitle());  
	        pstmt.setString(3, b.getInfBrdContent());  
	        pstmt.setString(4, b.getInfBrdTitleImg());  
	        pstmt.setString(5, b.getInfBrdRegion());
	        pstmt.setString(6, b.getInfBrdCatNo());
	        pstmt.setString(7, b.getInfBrdTagNo());
	        
	        result=pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}return result;
	}
	

	
	public int updateBoardReadCount(Connection conn, int no) {
		PreparedStatement pstmt=null;
		int result=0;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("updateBoardReadcount"));
			pstmt.setInt(1, no);
			result=pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}return result;
	}
	
	public InfoBoard selectBoardByNo(Connection conn, int no) {
		//번호가 no인 게시글을 데이터베이스에서 찾아서 InfoBoard 객체를 생성하고 반환하는 코드를 작성
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		InfoBoard b=null;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("selectBoardByNo"));
			pstmt.setInt(1, no);
			rs=pstmt.executeQuery();
			if(rs.next()) b=getBoard(rs);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}return b;
	  
	}
	
	public int insertBoardComment(Connection conn, InfoCommentBoard bc) {
	    PreparedStatement pstmt = null;
	    int result = 0;
	    try {
	        pstmt = conn.prepareStatement(sql.getProperty("insertBoardComment"));
	        pstmt.setInt(1, bc.getInfBrdNo());
	        pstmt.setString(2, bc.getInfComWriter());
	        pstmt.setString(3, bc.getInfComContent());
	        if (bc.getInfComNoRef() != null) {
	            pstmt.setInt(4, bc.getInfComNoRef());
	        } else {
	            pstmt.setNull(4, java.sql.Types.INTEGER);
	        }
	        pstmt.setInt(5, bc.getInfComLevel());
	        
	        result = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(pstmt);
	    }
	    return result;
	}
	
	public List<InfoCommentBoard> selectBoardComments(Connection conn, int boardNo) {
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    List<InfoCommentBoard> comments = new ArrayList<>();

	    try {
	        pstmt = conn.prepareStatement(sql.getProperty("selectBoardComment"));
	        pstmt.setInt(1, boardNo);
	        rs = pstmt.executeQuery();
	        
	        while(rs.next()) {
	            InfoCommentBoard comment = getComment(rs);
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
	
	
	public int deleteInfoBoard(Connection conn, int boardNo) {
	    PreparedStatement pstmt = null;
	    int result = 0;
	    try {
	        pstmt = conn.prepareStatement(sql.getProperty("deleteInfoBoardComment"));
	        pstmt.setInt(1, boardNo);
	        pstmt.executeUpdate();

	        close(pstmt); 

	        pstmt = conn.prepareStatement(sql.getProperty("deleteInfoBoard"));
	        pstmt.setInt(1, boardNo);
	        result = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(pstmt); 
	    }
	    return result;
	}

	public int deleteInfoComment(Connection conn, int commentNo) {
	    PreparedStatement pstmt = null;
	    int result = 0;
	    try {
	        pstmt = conn.prepareStatement(sql.getProperty("deleteInfoComment"));
	        pstmt.setInt(1, commentNo);
	        result = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(pstmt);
	    }
	    return result;
	}
	
	
	public List<InfoBoard> searchInfoBoards(Connection conn, String searchType, String query, int cPage, int numPerPage) {
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    List<InfoBoard> results = new ArrayList<>();
	    String sqlQuery = "";

	    int startRow = (cPage - 1) * numPerPage + 1;
	    int endRow = cPage * numPerPage;

	    switch (searchType) {
	        case "title":
	            sqlQuery = sql.getProperty("searchInfoBoardTitle");
	            break;
	        case "content":
	            sqlQuery = sql.getProperty("searchInfoBoardContent");
	            break;
	        case "both":
	            sqlQuery = sql.getProperty("searchInfoBoardBoth");
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
	            results.add(getBoard(rs));
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
	                sqlKey = "countSearchInfoBoardTitle";
	                break;
	            case "content":
	                sqlKey = "countSearchInfoBoardContent";
	                break;
	            case "both":
	                sqlKey = "countSearchInfoBoardBoth";
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
	
	public String selectCategoryNameByNo(Connection conn, String no) {
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String categoryName = null;
	    try {
	        pstmt = conn.prepareStatement(sql.getProperty("selectCategoryNameByNo"));
	        pstmt.setString(1, no);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            categoryName = rs.getString("INF_CAT_NAME");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(rs);
	        close(pstmt);
	    }
	    return categoryName;
	}

	public String selectTagNameByNo(Connection conn, String no) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String tagName = null;
        try {
            pstmt = conn.prepareStatement(sql.getProperty("selectTagNameByNo"));
            pstmt.setString(1, no);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                tagName = rs.getString("INF_BRD_TAG_NAME");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }
        return tagName;
    }
	
	 public int selectBoardCommentCount(Connection conn, int boardNo) {
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        int commentCount = 0;
	        try {
	            pstmt = conn.prepareStatement(sql.getProperty("selectBoardCommentCount"));
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

	 public int updateBoard(Connection conn, InfoBoard b) {
	        PreparedStatement pstmt=null;
	        int result=0;
	        try {
	            pstmt=conn.prepareStatement(sql.getProperty("updateInfoBoard"));
	            pstmt.setString(1, b.getInfBrdTitle());
	            pstmt.setString(2, b.getInfBrdContent());
	            pstmt.setString(3, b.getInfBrdTitleImg());
	            pstmt.setString(4, b.getInfBrdRegion());
	            pstmt.setString(5, b.getInfBrdCatNo());
	            pstmt.setString(6, b.getInfBrdTagNo());
	            pstmt.setInt(7, b.getInfBrdNo());
	            result=pstmt.executeUpdate();
	        } catch(SQLException e) {
	            e.printStackTrace();
	        } finally {
	            close(pstmt);
	        }
	        return result;
	    }
	 
	 
	 public List<InfoBoard> selectPopularBoard(Connection conn) {
		    PreparedStatement pstmt = null;
		    ResultSet rs = null;
		    List<InfoBoard> popularBoards = new ArrayList<>();
		    try {
		        pstmt = conn.prepareStatement(sql.getProperty("selectPopularBoard"));
		        rs = pstmt.executeQuery();
		        while (rs.next()) {
		            popularBoards.add(getBoard(rs));
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        close(rs);
		        close(pstmt);
		    }
		    return popularBoards;
		}

	
	private InfoBoard getBoard(ResultSet rs) throws SQLException {
		return InfoBoard.builder()
				.infBrdNo(rs.getInt("inf_brd_no"))
				.infBrdWriter(rs.getString("inf_brd_writer"))
				.infBrdTitle(rs.getString("inf_brd_title"))
				.infBrdContent(rs.getString("inf_brd_content"))
				.infBrdTitleImg(rs.getString("inf_brd_title_img"))
				.infBrdRegion(rs.getString("inf_brd_region"))
				.infBrdCatNo(rs.getString("inf_brd_cat_no"))
				.infBrdTagNo(rs.getString("inf_brd_tag_no"))
				.infBrdRegDate(rs.getDate("inf_brd_reg_date"))
				.build();
		
		//rs에 db에서 가져온 dto를 넣음
	}
	
	private InfoCommentBoard getComment(ResultSet rs) throws SQLException {
	    Integer infComNoRef = rs.getObject("INF_COM_NO_REF", Integer.class);
	    return InfoCommentBoard.builder()
	            .infComNo(rs.getInt("INF_COM_NO"))
	            .infBrdNo(rs.getInt("INF_BRD_NO"))
	            .infComWriter(rs.getString("INF_COM_WRITER"))
	            .infComContent(rs.getString("INF_COM_CONTENT"))
	            .infComDate(rs.getTimestamp("INF_COM_DATE"))
	            .infComNoRef(infComNoRef)
	            .infComLevel(rs.getInt("INF_COM_LEVEL"))
	            .build();
	}
	
	
}
