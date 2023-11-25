package com.harmony.ensemble.model.service;

import static com.harmony.common.JDBCTemplate.close;
import static com.harmony.common.JDBCTemplate.getConnection;
import static com.harmony.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import com.harmony.ensemble.model.dao.EnsembleDao;
import com.harmony.ensemble.model.dto.EnsembleBoard;
import com.harmony.ensemble.model.dto.EnsembleBoardApply;
import com.harmony.ensemble.model.dto.EnsembleBoardWantPart;
import com.harmony.ensemble.model.dto.EnsembleMember;
import com.harmony.ensemble.model.dto.EnsembleTeam;
import com.harmony.ensemble.model.dto.EnsembleTeamMusic;
import com.harmony.ensemble.model.dto.EnsembleTeamTime;
import com.harmony.ensemble.model.dto.EnsembleTeamVideo;
import com.harmony.ensemble.model.dto.Genre;
import com.harmony.ensemble.model.dto.Inst;
import com.harmony.ensemble.model.dto.MemberProfile;
import com.harmony.ensemble.model.dto.VBoardView;
import com.harmony.ensemble.model.dto.VChkApply;
import com.harmony.ensemble.model.dto.VEnsList;

public class EnsembleService {

	private EnsembleDao dao = new EnsembleDao();

	
	public int deleteBoard(String boardNo) {
		Connection conn = getConnection();
		
		List<String> partIndexes = dao.partIndexForDelete(conn, boardNo);
		
		int result=-1;
		int result2=-1;
		int result3=-1;
		int result4=-1;
		
		if(!partIndexes.isEmpty()) {
				for(String part : partIndexes) {
					result = dao.deleteApply(conn, part);
				}
			}else {
				rollback(conn);
				throw new IllegalArgumentException("파트인덱스 빔");
			}

		
		if(result>=0) {
			result2 = dao.deleteWantPart(conn, boardNo);
			
			}else {
				rollback(conn);
				throw new IllegalArgumentException("신청테이블 삭제 실패");
			}
		
		if(result2>=0) {
			result3 = dao.deleteBoard(conn, boardNo);
			}else {
				rollback(conn);
				throw new IllegalArgumentException("글테이블 삭제 실패");
			}
			
		
		close(conn);
		System.out.println("글 삭제 성공");
		return result3;
	
	}
//	
//	public int deleteWantPart(String boardNo) {
//		Connection conn = getConnection();
//		int result = dao.deleteWantPart(conn, boardNo);
//		close(conn);
//		return result;
//	}
	
	public List<VChkApply> selectApplyByBoardNo(String boardNo){
		Connection conn = getConnection();
		List<VChkApply> applyList = dao.selectApplyByBoardNo(conn, boardNo);
		close(conn);
		return applyList;
		
	}
	
	public List<MemberProfile> selectMemProfile(String teamNo){
		Connection conn = getConnection();
		List<MemberProfile> result = dao.selectMemProfile(conn, teamNo);
		close(conn);
		return result;
	}
	
	
	public int changeApproval(String partIndex) {
		Connection conn = getConnection();
		int result = dao.changeApproval(conn, partIndex);
		close(conn);
		return result;
	}
	
	public int selectPartIndex(String boardNo, String instNo,String loginMemNo) {
		Connection conn = getConnection();
		EnsembleBoardApply apply = dao.selectPartIndex(conn, boardNo, instNo, loginMemNo);
		System.out.println("서비스 apply: "+ apply);
		int result = insertApply(apply); //아래 메소드 호출
		close(conn);
		return result;
	}
	
	
	public int insertApply(EnsembleBoardApply apply) {
		Connection conn = getConnection();
		int result = dao.insertApply(conn, apply);
		close(conn);
		return result;
	}
	
	public String selectInstNoByName(String instName) {
		Connection conn = getConnection();
		String instNo = dao.selectInstNoByName(conn, instName);
		close(conn);
		return instNo;
	}
	
	
	public List<VEnsList> filterValues(String[] values) {
		Connection conn = getConnection();
		List<VEnsList> result = null;
		
		for(String value : values) {
			result = dao.filterValues(conn, value);
		}
		close(conn);
		return result;
	}

	public VBoardView selectBoardView(String ensBoardNo) {
		Connection conn = getConnection();
		VBoardView board = dao.selectBoardView(conn, ensBoardNo);
		close(conn);
		return board;
	}
	
  public List<VEnsList> selectBoardList(int cPage,int numPerpage){ 
		  Connection conn=getConnection();
		  List<VEnsList> result=dao.selectBoardList(conn, cPage, numPerpage);
		  close(conn); 
		  return result; 
	  }
	  
	public int selectBoardCount() {
		Connection conn=getConnection();
		int result=dao.selectBoardCount(conn);
		close(conn);
		return result;
	}

	/*
	 * public List<EnsembleBoardWantPart> selectPartsByBoardNo(List<EnsembleBoard>
	 * boards){ Connection conn = getConnection(); List<EnsembleBoardWantPart> parts
	 * = new ArrayList<>();
	 * 
	 * if(!boards.isEmpty()) { for(EnsembleBoard board : boards) { parts =
	 * dao.selectPartsByBoardNo(conn, board); } }else throw new
	 * IllegalArgumentException("boars is empty");
	 * 
	 * return parts;
	 * 
	 * }
	 */

	/*
	 * public List<EnsembleTeam> selectTeamByTeamNo(List<EnsembleBoard> boards) {
	 * Connection conn = getConnection(); List<EnsembleTeam> team = new
	 * ArrayList<EnsembleTeam>();
	 * 
	 * if(!boards.isEmpty()) { for(EnsembleBoard board : boards) { team =
	 * dao.selectTeamByTeamNo(conn, board); } }else { throw new
	 * IllegalArgumentException("boars is empty"); } close(conn); return team; }
	 *
	 */
	/*
	 * public int selectBoardCount() { Connection conn=getConnection(); int
	 * result=dao.selectBoardCount(conn); close(conn); return result; }
	 */

	public int insertEnsBoard(EnsembleBoard board, List<EnsembleBoardWantPart> partList) {

		Connection conn = getConnection();

		int result = dao.insertEnsBoard(conn, board);

		if (result > 0) {
			if (!partList.isEmpty()) {
				for (EnsembleBoardWantPart part : partList) {
					System.out.println(part + "서비스!");
					int result2 = dao.insertWantPart(conn, part);
					if (result2 == 0) {
						rollback(conn);
						throw new IllegalArgumentException("모집파트 등록 에러");
					}
				}
			}
		} else {
			rollback(conn);
			throw new IllegalArgumentException("글 등록 에러");
		}
		close(conn);
		return result;
	}

	public int insertTeam(EnsembleTeam ensTeam, List<EnsembleTeamMusic> musicList, List<EnsembleTeamVideo> videoList,
			List<EnsembleTeamTime> timeList, List<EnsembleMember> ensMemList) {
		Connection conn = getConnection();

		int result = dao.insertTeam(conn, ensTeam);

		if (result > 0) {

			if (!timeList.isEmpty()) {
				for (EnsembleTeamTime time : timeList) {
					int result2 = dao.insertTime(conn, time);
					if (result2 == 0) {
						rollback(conn);
						throw new IllegalArgumentException("합주 일정 에러");
					}
				}
			}

			if (!musicList.isEmpty()) {
				for (EnsembleTeamMusic music : musicList) {
					int result3 = dao.insertMusic(conn, music);
					if (result3 == 0) {
						rollback(conn);
						throw new IllegalArgumentException("음원 업로드 에러");
					}
				}
			}
			if (!videoList.isEmpty()) {
				for (EnsembleTeamVideo video : videoList) {
					int result4 = dao.insertVideo(conn, video);
					if (result4 == 0) {
						rollback(conn);
						throw new IllegalArgumentException("비디오 업로드 에러");
					}
				}
			}

			if (!ensMemList.isEmpty()) {
				for (EnsembleMember ensMem : ensMemList) {
					int result5 = dao.insertEnsMember(conn, ensMem);
					if (result5 == 0) {
						rollback(conn);
						throw new IllegalArgumentException("팀원 등록 에러");
					}
				}
			}

		} else {
			rollback(conn);
			throw new IllegalArgumentException("팀테이블 등록 에러");
		}
		close(conn);
		return result;
	}

	public List<EnsembleTeamTime> selectTimes(String teamNo) {
		Connection conn = getConnection();
		List<EnsembleTeamTime> result = dao.selectTimes(conn, teamNo);
		close(conn);
		return result;
	}

	public List<EnsembleTeamMusic> selectMusics(String teamNo) {
		Connection conn = getConnection();
		List<EnsembleTeamMusic> result = dao.selectMusics(conn, teamNo);
		close(conn);
		return result;
	}

	public List<EnsembleTeamVideo> selectVideos(String teamNo) {
		Connection conn = getConnection();
		List<EnsembleTeamVideo> result = dao.selectVideos(conn, teamNo);
		close(conn);
		return result;
	}

	public List<Inst> searchAllInst() {
		Connection conn = getConnection();
		List<Inst> result = dao.searchAllInst(conn);
		close(conn);
		return result;
	}

	public List<Genre> searchAllGenre() {
		Connection conn = getConnection();
		List<Genre> result = dao.searchAllGenre(conn);
		close(conn);
		return result;
	}

	public String selectMemberByEmail(String userEmail) {
		Connection conn = getConnection();
		String memNo = dao.selectMemberByEmail(conn, userEmail);
		close(conn);
		return memNo;
	}

	public String selectSeq() { // 팀 번호 가져오는 메소드
		Connection conn = getConnection();

		String seq = dao.selectSeq(conn);

		return seq;

	}

	public String selectBoardSeq() {
		Connection conn = getConnection();

		String seq = dao.selectBoardSeq(conn);
		close(conn);
		return seq;

	}

	public String selectTeamNoByMemNo(String loginMemNo) {

		Connection conn = getConnection();
		String teamNo = dao.selectTeamNoByMemNo(conn, loginMemNo);
		close(conn);
		return teamNo;
	}

	public EnsembleTeam selectTeamByNo(String teamNo) {
		Connection conn = getConnection();
		EnsembleTeam team = dao.selectTeamByNo(conn, teamNo);
		close(conn);
		return team;
	}

}
