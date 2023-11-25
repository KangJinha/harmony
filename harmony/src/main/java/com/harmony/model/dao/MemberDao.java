package com.harmony.model.dao;

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

import com.harmony.model.dto.GenreAll;
import com.harmony.model.dto.InterestAll;
import com.harmony.model.dto.Member;
import com.harmony.model.dto.MemberInfo;
import com.harmony.model.dto.MemberMusic;
import com.harmony.model.dto.MemberVideo;

public class MemberDao {
	private Properties sql = new Properties();
	{
		String path= MemberDao.class.getResource("/sql/member/member_sql.properties").getPath();
		try (FileReader fr= new FileReader(path);){
			sql.load(fr);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public List<GenreAll> selectGenreAll(Connection conn) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<GenreAll> genreAll=new ArrayList<GenreAll>();
		try {
			pstmt=conn.prepareStatement(sql.getProperty("selectGenreAll"));
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				genreAll.add(getGenreAll(rs));
			}	
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}return genreAll;
		}
	public List<InterestAll> selectInterestAll(Connection conn){
		PreparedStatement pstmt= null;
		ResultSet rs =null;
		List<InterestAll> interestAll = new ArrayList<InterestAll>();
		try {
			pstmt=conn.prepareStatement(sql.getProperty("selectInterestAll"));
			rs=pstmt.executeQuery();
			while(rs.next()) {
				interestAll.add(getInterestAll(rs));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}return interestAll;
	}
	
	public Member selectMemberByKakaoId(Connection conn, String kakaoNum) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Member m= null;
	try {
		pstmt=conn.prepareStatement(sql.getProperty("selectMemberByKakaoId"));
		pstmt.setString(1, kakaoNum);
		
		rs=pstmt.executeQuery();
		if(rs.next()) m=getMember(rs);
	}catch(SQLException e) {
		e.printStackTrace();
	}finally {
		close(rs);
		close(pstmt);
	}
		return m;
	}
//	public List<MemberVideo> selectVideoLink(Connection conn, String memNo) {
//		PreparedStatement pstmt=null;
//		ResultSet rs = null;
//		List<MemberVideo> result= new ArrayList<MemberVideo>();
//		try {
//			pstmt=conn.prepareStatement(sql.getProperty("selectVideoLink"));
//			pstmt.setString(1, memNo);
//			rs=pstmt.executeQuery();
//			while(rs.next()) {
//				result.add(getMemberVideo(rs));
//			}
//		}catch(SQLException e) {
//			e.printStackTrace();
//		}finally {
//			close(rs);
//			close(pstmt);
//		}
//		return result;
//	}
	public int insertMember(Connection conn,Member m) {
		PreparedStatement pstmt=null;
		int result=0;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("insertMember"));
			pstmt.setString(1, m.getMemNo());
			pstmt.setString(2, m.getMemAuthority());
			pstmt.setString(3, m.getMemKakaoNum());
			result=pstmt.executeUpdate();
		
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}return result;
	}
	public int insertMemberInfo(Connection conn,MemberInfo mi) {
		PreparedStatement pstmt=null;
		int result=0;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("insertMemberInfo"));
			pstmt.setString(1, mi.getMemNo());
			pstmt.setString(2, mi.getEmail());
			result= pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}return result;
	}
	public String selectMemNoFromDual(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("selectMemNoFromDual"));
			rs=pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getString(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}return result;
	}
	
	public int addintroduce(Connection conn,MemberInfo mi) {
		PreparedStatement pstmt= null;
		int result =0;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("addintroduce"));
			pstmt.setString(1, mi.getMemNo());
			pstmt.setString(2,mi.getActivityArea());
			pstmt.setString(3,mi.getIntroduce());
			pstmt.setString(4, mi.getProfilPhoto());
			pstmt.setString(5, mi.getSchool());
			pstmt.setString(6, mi.getDepartment());
			pstmt.setString(7, mi.getSchoolState());
			pstmt.setString(8, mi.getName());
			pstmt.setString(9, mi.getGender());
			pstmt.setInt(10, mi.getAge());
			pstmt.setString(11,mi.getEmail());
			pstmt.setString(12,mi.getMemCareer());
			pstmt.setString(13, mi.getMemNo());
			result=pstmt.executeUpdate();
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			
			close(pstmt);
			
		}return result;
				
	}
	public int updateIntroduce(Connection conn,MemberInfo mi) {
		PreparedStatement pstmt= null;
		int result =0;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("updateIntroduce"));
			pstmt.setString(1, mi.getMemNo());
			pstmt.setString(2,mi.getActivityArea());
			pstmt.setString(3,mi.getIntroduce());
			pstmt.setString(4, mi.getProfilPhoto());
			pstmt.setString(5, mi.getSchool());
			pstmt.setString(6, mi.getDepartment());
			pstmt.setString(7, mi.getSchoolState());
			pstmt.setString(8, mi.getName());
			pstmt.setString(9, mi.getGender());
			pstmt.setInt(10, mi.getAge());
			pstmt.setString(11,mi.getEmail());
			pstmt.setString(12,mi.getMemCareer());
			pstmt.setString(13, mi.getMemNo());
			result=pstmt.executeUpdate();
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			
			close(pstmt);
			
		}return result;
				
	}
	public int updateMusic(Connection conn,MemberMusic mm) {
		PreparedStatement pstmt=null;
		int result=0;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("updateMusic"));
			pstmt.setString(1,mm.getMemNo());
			pstmt.setString(2, mm.getMusicType());
			pstmt.setString(3,mm.getMusicLink());
			pstmt.setString(4,mm.getMemNo());
			result=pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}return result;
	}
	public int updateVideo(Connection conn,MemberVideo mv) {
		PreparedStatement pstmt=null;
		int result=0;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("updateVideo"));
			pstmt.setString(1,mv.getMemNo());
			pstmt.setString(2, mv.getVideoType());
			pstmt.setString(3,mv.getVideoLink());
			pstmt.setString(4, mv.getMemNo());
			result=pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}return result;
	}
	public int insertMusic(Connection conn,MemberMusic mm) {
		PreparedStatement pstmt=null;
		int result=0;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("insertMusic"));
			pstmt.setString(1,mm.getMemNo());
			pstmt.setString(2, mm.getMusicType());
			pstmt.setString(3,mm.getMusicLink());
			result=pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}return result;
	}
	public int insertVideo(Connection conn,MemberVideo mv) {
		PreparedStatement pstmt=null;
		int result=0;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("insertVideo"));
			pstmt.setString(1,mv.getMemNo());
			pstmt.setString(2, mv.getVideoType());
			pstmt.setString(3,mv.getVideoLink());
			result=pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}return result;
	}
	public int deleteVideo(Connection conn,String memNo) {
		PreparedStatement pstmt=null;
		int result=0;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("deleteVideo"));
			pstmt.setString(1, memNo);
			result=pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}return result;
	}
	public MemberInfo selectMemberInfo(Connection conn,String memNo) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		MemberInfo mi=null;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("selectMemberInfo"));
			pstmt.setString(1,memNo);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				mi = getMemberInfo(rs);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}return mi;
	}
	
	public int deleteInstrument(Connection conn, String memberNo) {
		PreparedStatement pstmt=null;
		int result=0;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("deleteInstrument"));
			pstmt.setString(1,memberNo);
			result=pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}return result;
	}
	public int deleteGenre(Connection conn, String memberNo) {
		PreparedStatement pstmt=null;
		int result=0;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("deleteGenre"));
			pstmt.setString(1,memberNo);
			result=pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}return result;
	}
	public List<MemberVideo> selectMemberVideoByMemberNo(Connection conn,String memNo) {
		PreparedStatement pstmt=null;
		ResultSet rs= null;
		List<MemberVideo> mv = new ArrayList<MemberVideo>();
		try {
			pstmt=conn.prepareStatement(sql.getProperty("selectMemberVideoByMemberNo"));
			pstmt.setString(1, memNo);		
			rs=pstmt.executeQuery();
			while(rs.next()) {
				mv.add(getMemberVideo(rs));
			}			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}return mv;
	}
	public String[] selectMemberGenreByMemberNo(Connection conn, String memNo) {
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		List<String> genreList = new ArrayList<String>();
		try {
			pstmt=conn.prepareStatement(sql.getProperty("selectMemberGenreByMemberNo"));
			pstmt.setString(1, memNo);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				genreList.add(rs.getString("GENRE_NAME"));
			}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}
		String[] genre = new String[genreList.size()];
		genre= genreList.toArray(genre);
		return genre;
		
	}
	public String[] selectMemberInterestByMemberNo(Connection conn, String memNo) {
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		List<String> interestList = new ArrayList<String>();
		try {
			pstmt=conn.prepareStatement(sql.getProperty("selectMemberInterestByMemberNo"));
			pstmt.setString(1, memNo);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				interestList.add(rs.getString("INST_NAME"));
			}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}
		String[] interest = new String[interestList.size()];
		interest= interestList.toArray(interest);
		return interest;
		
	}
	public List<MemberMusic> selectMemberMusicByMemberNo(Connection conn,String memNo) {
		PreparedStatement pstmt=null;
		ResultSet rs= null;
		List<MemberMusic> mm = new ArrayList<MemberMusic>();
		try {
			pstmt=conn.prepareStatement(sql.getProperty("selectMemberMusicByMemberNo"));
			pstmt.setString(1, memNo);		
			rs=pstmt.executeQuery();
			while(rs.next()) {
				mm.add(getMemberMusic(rs));
			}			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}return mm;
	}
	public int insertGenre(Connection conn,String memNo,MemberInfo mi) {
		PreparedStatement pstmt=null;
		int result=0;
		try{
			pstmt=conn.prepareStatement(sql.getProperty("insertGenre"));
//			String[] genres = mi.getGenre();
			
			for(String genre : mi.getGenre()) {
			pstmt.setString(1, memNo);
			pstmt.setString(2, genre);
			pstmt.addBatch();
			pstmt.clearParameters();
			}
			int[] results = pstmt.executeBatch();
			result = 1;
			
			for(int i : results) {
				if(i== -2) {// 실행은 했는데 몇번째거를 실행했는지 모를땐 -2가 뜨고 
					i=1;
				}else if(i==-3) { //실패했을땐 -3 이뜬다
					i=0;
				}
				result *= i;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}return result;
		
	}
	public int insertInstrument(Connection conn,String memNo,MemberInfo mi) {
		PreparedStatement pstmt=null;
		int result=0;
		try{
			pstmt=conn.prepareStatement(sql.getProperty("insertInstrument"));
//			String[] genres = mi.getGenre();
			
			for(String interest : mi.getInterest()) {
			pstmt.setString(1, memNo);
			pstmt.setString(2, interest);
			pstmt.addBatch();
			pstmt.clearParameters();
			}
			int[] results = pstmt.executeBatch();
			result = 1;
			
			for(int i : results) {
				if(i== -2) {// 실행은 했는데 몇번째거를 실행했는지 모를땐 -2가 뜨고 
					i=1;
				}else if(i==-3) { //실패했을땐 -3 이뜬다
					i=0;
				}
				result *= i;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}return result;
		
	}
	public int updateGenre(Connection conn,String memNo,MemberInfo mi) {
		PreparedStatement pstmt=null;
		int result=0;
		try{
			pstmt=conn.prepareStatement(sql.getProperty("updateGenre"));
			
			for(String genre : mi.getGenre()) {
			pstmt.setString(1, memNo);
			pstmt.setString(2, genre.trim());
			pstmt.setString(3, memNo);
			pstmt.addBatch();
			pstmt.clearParameters();
			}
			int[] results = pstmt.executeBatch();
			result = 1;
			
			for(int i : results) {
				if(i== -2) {// 실행은 했는데 몇번째거를 실행했는지 모를땐 -2가 뜨고 
					i=1;
				}else if(i==-3) { //실패했을땐 -3 이뜬다
					i=0;
				}
				result *= i;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}return result;
		
	}
	public int updateInstrument(Connection conn,String memNo,MemberInfo mi) {
		PreparedStatement pstmt=null;
		int result=0;
		try{
			pstmt=conn.prepareStatement(sql.getProperty("updateInstrument"));
//			String[] genres = mi.getGenre();
			
			for(String interest : mi.getInterest()) {
			pstmt.setString(1, memNo);
			pstmt.setString(2, interest);
			pstmt.setString(3, memNo);
			pstmt.addBatch();
			pstmt.clearParameters();
			}
			int[] results = pstmt.executeBatch();
			result = 1;
			
			for(int i : results) {
				if(i== -2) {// 실행은 했는데 몇번째거를 실행했는지 모를땐 -2가 뜨고 
					i=1;
				}else if(i==-3) { //실패했을땐 -3 이뜬다
					i=0;
				}
				result *= i;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}return result;
		
	}
	
	
	public static Member getMember(ResultSet rs) throws SQLException{
		return Member.builder()
						.memNo(rs.getString("MEM_NO"))
						.memAuthority(rs.getString("mem_authority"))
						.memKakaoNum(rs.getString("mem_kakao_num"))
						.build();
	
	}
	public static GenreAll getGenreAll(ResultSet rs) throws SQLException {
		return GenreAll.builder()
						.genreCode(rs.getString("GENRE_CODE"))
						.genreName(rs.getString("GENRE_NAME"))
						.build();
	}
	public static InterestAll getInterestAll(ResultSet rs) throws SQLException {
		return InterestAll.builder()
						.instCode(rs.getString("INST_CODE"))
						.instName(rs.getString("INST_NAME"))
						.build();
						
	}
	public static MemberInfo getMemberInfo(ResultSet rs) throws SQLException{
		return MemberInfo.builder()
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
	public static MemberVideo getMemberVideo(ResultSet rs) throws SQLException{
		return MemberVideo.builder()
				.memNo(rs.getString("MEM_NO"))
				.videoType(rs.getString("MEM_VIDEO_TYPE"))
				.videoLink(rs.getString("MEM_VIDEO_LINK"))
				.build();
	}
	public static MemberMusic getMemberMusic(ResultSet rs) throws SQLException{
		return MemberMusic.builder()
				.memNo(rs.getString("MEM_NO"))
				.musicType(rs.getString("MEM_MUSIC_TYPE"))
				.musicLink(rs.getString("MEM_MUSIC_LINK"))
				.build();
	}
}
