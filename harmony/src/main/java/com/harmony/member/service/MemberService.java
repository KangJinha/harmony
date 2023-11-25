package com.harmony.member.service;

import static com.harmony.common.JDBCTemplate.close;
import static com.harmony.common.JDBCTemplate.commit;
import static com.harmony.common.JDBCTemplate.getConnection;
import static com.harmony.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import com.harmony.model.dao.MemberDao;
import com.harmony.model.dto.GenreAll;
import com.harmony.model.dto.InterestAll;
import com.harmony.model.dto.Member;
import com.harmony.model.dto.MemberInfo;
import com.harmony.model.dto.MemberMusic;
import com.harmony.model.dto.MemberVideo;

public class MemberService {
	private MemberDao dao= new MemberDao();
	
	public Member selectMemberByKakaoId(String kakaoNum){
	Connection conn = getConnection();
	Member result = dao.selectMemberByKakaoId(conn,kakaoNum);
	close(conn);
	return result;
	}
	public List<GenreAll> selectGenreAll() {
		Connection conn =getConnection();
		List<GenreAll> result = dao.selectGenreAll(conn);
		close(conn);
		return result;
	}
	public List<InterestAll> selectInterestAll(){
		Connection conn = getConnection();
		List<InterestAll> result = dao.selectInterestAll(conn);
		close(conn);
		return result;
	}
//	public List<MemberVideo> selectVideoLink(String memNo) {
//		
//		Connection conn = getConnection();
//		List<MemberVideo> result = dao.selectVideoLink(conn,memNo);
//		close(conn);
//		return result;
//	}
	
	public Member insertMember(Member m,MemberInfo mi) {
		Connection conn = getConnection();
		String memNo = dao.selectMemNoFromDual(conn);
		Member result = null;
		m.setMemNo(memNo);
		int resultMember =dao.insertMember(conn,m);
		if(resultMember>0) {
			mi.setMemNo(memNo);
			int resultInfo = dao.insertMemberInfo(conn, mi);
			if(resultInfo>0) {
				commit(conn);
				result = dao.selectMemberByKakaoId(conn,m.getMemKakaoNum());
			}else rollback(conn);
		} else rollback(conn);
		close(conn);
		return result;
	}
	public int addintroduce(MemberInfo mi) { 
		Connection conn = getConnection();
		System.out.println("찐확인"+mi);
		MemberInfo result= null;
		int resultAdd = dao.addintroduce(conn,mi);
		int resultGenre = 0;
		int resultInterest =0;
		int resultMusic = 0;
		int resultVideo = 0;
		try {
		if(resultAdd>0) {
			if(mi.getGenre()!=null) {
				resultGenre = dao.insertGenre(conn,mi.getMemNo(),mi);
				if(resultGenre==0) {
					resultGenre=1;
				}
			}
			if(mi.getInterest()!=null) {
				resultInterest =dao.insertInstrument(conn,mi.getMemNo(),mi);
				if(resultInterest==0) {
					resultInterest=1;
				}
			}
			if(!mi.getMemberMusic().isEmpty()) {
				for(MemberMusic mm:mi.getMemberMusic()) {
					mm.setMemNo(mi.getMemNo());
					resultMusic=dao.insertMusic(conn,mm);
					if(resultMusic==0) {
						rollback(conn);
						throw new IllegalArgumentException("입력실패");
					}
				}
			}else {
				resultMusic=1;
			}
			if(!mi.getMemberVideo().isEmpty()) {
				for(MemberVideo mv:mi.getMemberVideo()) {
					mv.setMemNo(mi.getMemNo());
					 resultVideo=dao.insertVideo(conn,mv);
					if(resultVideo==0) {
						rollback(conn);
						throw new IllegalArgumentException("입력실패");
					}
				}
			}else {
				resultVideo=1;
			}
//			if(resultGenre!=1||resultMusic!=1||resultVideo!=1||resultInterest!=1) {
//				rollback(conn);
//				throw new IllegalArgumentException("여기서입력실패");//
//			}
//			commit(conn);
		}else {
			rollback(conn);
			throw new IllegalArgumentException("입력실패");
		}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(conn);
		}
		System.out.println(resultAdd);
		return resultAdd;
	}
	public int UpdateIntroduce(MemberInfo mi) { 
		Connection conn = getConnection();
		MemberInfo result= null;
		int resultAdd = dao.updateIntroduce(conn,mi);
		int resultGenre = 0;
		int resultInterest =0;
		int resultMusic = 0;
		int resultVideo = 0;
		try {
		if(resultAdd>0) {
			if(mi.getGenre()!=null) {
				dao.deleteGenre(conn,mi.getMemNo());
				resultGenre = dao.insertGenre(conn,mi.getMemNo(),mi);
				commit(conn);
				if(resultGenre==0) {
					rollback(conn);
					throw new IllegalArgumentException("장르입력실패");
				}
			}
			if(mi.getInterest()!=null) {
				dao.deleteInstrument(conn,mi.getMemNo());
				resultInterest =dao.insertInstrument(conn,mi.getMemNo(),mi);
				commit(conn);
				if(resultInterest==0) {
					rollback(conn);
					throw new IllegalArgumentException("악기입려실패");
				}
			}
			if(!mi.getMemberMusic().isEmpty()) {
				for(MemberMusic mm:mi.getMemberMusic()) {
					mm.setMemNo(mi.getMemNo());
					resultMusic=dao.updateMusic(conn,mm);
					if(resultMusic!=0) {
						commit(conn);
					}
				}
			}else {
				rollback(conn);

			}
			if(!mi.getMemberVideo().isEmpty()) {
				dao.deleteVideo(conn,mi.getMemNo());
				for(MemberVideo mv:mi.getMemberVideo()) {
					mv.setMemNo(mi.getMemNo());
					 resultVideo=dao.insertVideo(conn,mv);
					if(resultVideo!=0) {
						commit(conn);
					}
				}
			}else {
				rollback(conn);
				
			}
			
			commit(conn);
		}else {
			rollback(conn);
			throw new IllegalArgumentException("입력실패");
		}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(conn);
		}		
		return resultAdd;
	}
	
	public MemberInfo selectMemberInfo(String memNo) {//memNo와 같은 아이의 memberInfo 가져오기
		MemberInfo result=null;
		List<MemberVideo> videoInfo = null;
		List<MemberMusic> musicInfo = null;
		String []genre = null;
		String []interest=null;
		Connection conn=getConnection();
		
		result=dao.selectMemberInfo(conn,memNo);
		if(result!=null) {
			videoInfo = dao.selectMemberVideoByMemberNo(conn,memNo);
			musicInfo = dao.selectMemberMusicByMemberNo(conn,memNo);
			genre = dao.selectMemberGenreByMemberNo(conn,memNo);
			interest = dao.selectMemberInterestByMemberNo(conn, memNo);
		}
		result.setMemberVideo(videoInfo);
		result.setMemberMusic(musicInfo);
		result.setGenre(genre);
		result.setInterest(interest);
		close(conn);
		return result;
		
	}

}
